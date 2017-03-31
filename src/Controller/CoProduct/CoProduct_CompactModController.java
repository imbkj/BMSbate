package Controller.CoProduct;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoProduct.cpd_ListBll;
import bll.CoProduct.cpd_addBll;

import Model.CoPAccountModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Util.RegexUtil;
import Util.UserInfo;

public class CoProduct_CompactModController {

	private CopCompactModel m = new CopCompactModel();
	private List<CopCompactModel> cpcrList;
	private List<CoProductModel> coprList;
	private List<CoProductModel> scoprList = new ListModelList<>();
	private ListModelList<CoPclassModel> classlist;
	private ListModelList<CoPAccountModel> accountlist;
	Integer daid;

	private String copr_name = "";
	private String cpac_name = "";
	private String copc_name = "";
	private String copr_type = "";
	private List<CoProductModel> cityList = new ListModelList<>();
	private CoProductModel cityModel = new CoProductModel();

	public CoProduct_CompactModController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		cpd_ListBll bll = new cpd_ListBll();
		cpd_addBll bll1 = new cpd_addBll();
		try {
			List<CopCompactModel> list = bll.getCopCompactList(" and cpct_id="
					+ daid);
			if (list.size() > 0) {
				m = list.get(0);
			}

			coprList = bll.getcoproduct(" and copr_state=2");
			setCityList(bll.getCopCityList());
			cityList.add(0, null);
			cityModel = cityList.get(0);
			this.classlist = new ListModelList<CoPclassModel>(bll1.getclass());
			classlist.add(0, null);
			this.accountlist = new ListModelList<CoPAccountModel>(
					bll1.getaccount());
			cpcrList = bll.getCopComRelList(daid);

			handleCoprList();
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理产品列表 1.判断是否勾选
	 * 
	 */
	public void handleCoprList() {
		try {
			if (coprList.size() > 0) {
				for (CoProductModel copM : coprList) {
					copM.setIfChecked(false);
					for (CopCompactModel cpctM : cpcrList) {
						if (copM.getCopr_id().equals(cpctM.getCopr_id())) {
							copM.setIfChecked(true);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "scoprList" })
	public void search() {
		try {
			if (coprList.size() > 0) {
				scoprList.clear();
				for (CoProductModel m : coprList) {
					if (!copr_name.isEmpty()) {
						if (!RegexUtil.isExists(copr_name, m.getCopr_name())) {
							continue;
						}
					}
					if (!cpac_name.isEmpty()) {
						if (!cpac_name.equals(m.getCpac_name())) {
							continue;
						}
					}
					if (!copc_name.isEmpty()) {
						if (!copc_name.equals(m.getCopc_name())) {
							continue;
						}
					}
					if (!copr_type.isEmpty()) {
						if (!copr_type.equals(m.getCopr_type())) {
							continue;
						}
					}
					if (cityModel != null) {
						if (!cityModel.getId().equals(m.getId())) {
							continue;
						}
					}

					scoprList.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 全选
	 * 
	 * @param checked
	 */
	@Command("allcheck")
	@NotifyChange({ "scoprList" })
	public void allcheck(@BindingParam("checked") Boolean checked) {
		for (CoProductModel copM : scoprList) {
			copM.setIfChecked(checked);
		}
	}

	/**
	 * 提交
	 * 
	 * @param window
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window window) {
		if (m.getCpct_shortname().isEmpty()) {
			Messagebox.show("请输入合同类型简称!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			cpd_addBll bll = new cpd_addBll();
			Integer row = 0;

			try {
				//修改合同编号
				row = bll.CopCompactMod(m);

				if (row > 0) {
					//删除关系表里的所有数据
					row = bll.deleteCopComRelByCpctId(daid);
						
//					if (row.equals(cpcrList.size())) {
						Integer a = 0;
						Integer b = 0;
						Integer id = 0;
						for (CoProductModel copM : coprList) {
							if (copM.getIfChecked()) {
								a++;
								CopCompactModel cpcrM = new CopCompactModel();

								cpcrM.setCpcr_copr_id(copM.getCopr_id());
								cpcrM.setCpcr_cpct_id(daid);
								cpcrM.setCpcr_addname(UserInfo.getUsername());
								cpcrM.setCpcr_state(1);

								id = bll.CopComReladd(cpcrM);
								if (id > 0) {
									b++;
								}
							}
						}
						if (a.equals(b)) {
							Messagebox.show("保存成功!", "INFORMATION",
									Messagebox.OK, Messagebox.INFORMATION);
							window.detach();
						} else {
							Messagebox.show("勾选的产品保存失败!", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
						}
//					} 
//					else {
//						Messagebox.show("产品删除失败!", "ERROR", Messagebox.OK,
//								Messagebox.ERROR);
//					}
				} else {
					Messagebox.show("合同类型修改失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("保存出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public CopCompactModel getM() {
		return m;
	}

	public void setM(CopCompactModel m) {
		this.m = m;
	}

	public List<CopCompactModel> getCpcrList() {
		return cpcrList;
	}

	public void setCpcrList(List<CopCompactModel> cpcrList) {
		this.cpcrList = cpcrList;
	}

	public List<CoProductModel> getCoprList() {
		return coprList;
	}

	public void setCoprList(List<CoProductModel> coprList) {
		this.coprList = coprList;
	}

	public List<CoProductModel> getScoprList() {
		return scoprList;
	}

	public void setScoprList(List<CoProductModel> scoprList) {
		this.scoprList = scoprList;
	}

	public final String getCopr_name() {
		return copr_name;
	}

	public final String getCpac_name() {
		return cpac_name;
	}

	public final String getCopc_name() {
		return copc_name;
	}

	public final String getCopr_type() {
		return copr_type;
	}

	public final void setCopr_name(String copr_name) {
		this.copr_name = copr_name;
	}

	public final void setCpac_name(String cpac_name) {
		this.cpac_name = cpac_name;
	}

	public final void setCopc_name(String copc_name) {
		this.copc_name = copc_name;
	}

	public final void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	public List<CoProductModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CoProductModel> cityList) {
		this.cityList = cityList;
	}

	public CoProductModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(CoProductModel cityModel) {
		this.cityModel = cityModel;
	}

	public ListModelList<CoPclassModel> getClasslist() {
		return classlist;
	}

	public void setClasslist(ListModelList<CoPclassModel> classlist) {
		this.classlist = classlist;
	}

	public ListModelList<CoPAccountModel> getAccountlist() {
		return accountlist;
	}

	public void setAccountlist(ListModelList<CoPAccountModel> accountlist) {
		this.accountlist = accountlist;
	}
}
