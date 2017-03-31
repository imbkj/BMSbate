package Controller.CoProduct;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoPAccountModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Util.RegexUtil;
import Util.UserInfo;
import bll.CoProduct.cpd_ListBll;
import bll.CoProduct.cpd_addBll;

public class CoProduct_CompactAddController {
	private CopCompactModel m = new CopCompactModel();
	private List<CoProductModel> coprList;
	private List<CoProductModel> scoprList = new ListModelList<>();
	private ListModelList<CoPclassModel> classlist;
	private ListModelList<CoPAccountModel> accountlist;

	private String copr_name = "";
	private String cpac_name = "";
	private String copc_name = "";
	private String copr_type = "";
	private List<CoProductModel> cityList = new ListModelList<>();
	private CoProductModel cityModel = new CoProductModel();

	public CoProduct_CompactAddController() {
		try {
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
			coprList = bll.getcoproduct(" and copr_state=2");
			for (CoProductModel copM : coprList) {
				copM.setIfChecked(false);
			}
			setCityList(bll.getCopCityList());
			cityList.add(0, null);
			cityModel = cityList.get(0);
			this.classlist = new ListModelList<CoPclassModel>(bll1.getclass());
			classlist.add(0, null);
			this.accountlist = new ListModelList<CoPAccountModel>(
					bll1.getaccount());

			search();
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
			Integer cpct_id = 0;

			try {
				m.setCpct_addname(UserInfo.getUsername());
				m.setCpct_state(1);

				cpct_id = bll.CopCompactAdd(m);

				if (cpct_id > 0) {
					Integer a = 0;
					Integer b = 0;
					Integer id = 0;
					for (CoProductModel copM : coprList) {
						if (copM.getIfChecked()) {
							a++;
							CopCompactModel cpcrM = new CopCompactModel();

							cpcrM.setCpcr_copr_id(copM.getCopr_id());
							cpcrM.setCpcr_cpct_id(cpct_id);
							cpcrM.setCpcr_addname(UserInfo.getUsername());
							cpcrM.setCpcr_state(1);

							id = bll.CopComReladd(cpcrM);
							if (id > 0) {
								b++;
							}
						}
					}
					if (a.equals(b)) {
						Messagebox.show("新增成功!", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						window.detach();
					} else {
						Messagebox.show("勾选的产品新增失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("合同类型新增失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("新增出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public final CopCompactModel getM() {
		return m;
	}

	public final List<CoProductModel> getCoprList() {
		return coprList;
	}

	public final List<CoProductModel> getScoprList() {
		return scoprList;
	}

	public final ListModelList<CoPclassModel> getClasslist() {
		return classlist;
	}

	public final ListModelList<CoPAccountModel> getAccountlist() {
		return accountlist;
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

	public final List<CoProductModel> getCityList() {
		return cityList;
	}

	public final CoProductModel getCityModel() {
		return cityModel;
	}

	public final void setM(CopCompactModel m) {
		this.m = m;
	}

	public final void setCoprList(List<CoProductModel> coprList) {
		this.coprList = coprList;
	}

	public final void setScoprList(List<CoProductModel> scoprList) {
		this.scoprList = scoprList;
	}

	public final void setClasslist(ListModelList<CoPclassModel> classlist) {
		this.classlist = classlist;
	}

	public final void setAccountlist(ListModelList<CoPAccountModel> accountlist) {
		this.accountlist = accountlist;
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

	public final void setCityList(List<CoProductModel> cityList) {
		this.cityList = cityList;
	}

	public final void setCityModel(CoProductModel cityModel) {
		this.cityModel = cityModel;
	}
}
