package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateModel;
import Util.RegexUtil;

public class EmCommissionOutPayUpdate_OperateListController {

	private List<EmCommissionOutPayUpdateCRTModel> fieldList = new ListModelList<>();
	private List<EmCommissionOutPayUpdateModel> puList;
	private List<EmCommissionOutPayUpdateModel> spuList = new ListModelList<>();

	private List<CoAgencyBaseCityRelViewModel> cityList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel cityM = null;
	CoAgencyBaseCityRelViewModel oldcityM = null;
	private List<CoAgencyBaseCityRelViewModel> coabList;
	private List<CoAgencyBaseCityRelViewModel> scoabList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel coabM = null;
	CoAgencyBaseCityRelViewModel oldcoabM = null;
	private List<String> ownmonthList = new ListModelList<>();
	private String ownmonth = "";
	private String company = "";
	private String name = "";
	private String idcard = "";
	private String cid = "";
	private String gid = "";

	String oldcompany = "";
	String oldname = "";
	String oldidcard = "";
	String oldcid = "";
	String oldgid = "";
	String oldownmonth = "";

	public EmCommissionOutPayUpdate_OperateListController() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			setFieldList(bll.getTableFieldList("EmCommissionOutPayUpdate"));
			addField();
			setPuList(bll.getEmOutPayUpdateList(""));
			setCityList(bll.getPayUpdateCityList());
			cityList.add(0, null);
			setCoabList(bll.getPayUpdateCoagencyList());
			citySelect();
			setOwnmonthList(bll.getPayUpdateOwnmonthList());
			if (ownmonthList.size() > 0) {
				ownmonth = ownmonthList.get(0);
			}

			if (puList.size() > 0) {
				for (int i = 0; i < puList.size(); i++) {
					EmCommissionOutPayUpdateModel pum = puList.get(i);
					List<Object> objsList = pum.getObjsList();

					for (EmCommissionOutPayUpdateCRTModel crtm : fieldList) {
						if (crtm.getEcpr_ecpu_field() != null) {
							Object obj = pum
									.getField(crtm.getEcpr_ecpu_field());
							objsList.add(obj);
						}
					}
				}

				search();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手工增加表头
	 * 
	 */
	public void addField() {
		if (fieldList.size() > 0) {
			EmCommissionOutPayUpdateCRTModel tempM = new EmCommissionOutPayUpdateCRTModel();

			tempM.setEcpr_ecpu_fieldstr("委托城市");
			tempM.setEcpr_ecpu_field("city");
			fieldList.add(2, tempM);

			tempM = new EmCommissionOutPayUpdateCRTModel();
			tempM.setEcpr_ecpu_fieldstr("委托机构");
			tempM.setEcpr_ecpu_field("coab_name");
			fieldList.add(3, tempM);

			tempM = new EmCommissionOutPayUpdateCRTModel();
			tempM.setEcpr_ecpu_fieldstr("缴纳月份");
			tempM.setEcpr_ecpu_field("ownmonth");
			fieldList.add(4, tempM);
		}
	}

	/**
	 * 检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "spuList" })
	public void search() {
		try {
			if (puList.size() > 0) {
				spuList.clear();
				for (EmCommissionOutPayUpdateModel pum : puList) {
					if (!company.isEmpty()) {
						if (!RegexUtil.isExists(company, pum.getEcpu_company())) {
							continue;
						}
					}
					if (!name.isEmpty()) {
						if (!RegexUtil.isExists(name, pum.getEcpu_name())) {
							continue;
						}
					}
					if (!idcard.isEmpty()) {
						if (!RegexUtil.isExists(idcard, pum.getEcpu_idcard())) {
							continue;
						}
					}
					if (cityM != null) {
						if (cityM.getCabc_ppc_id() != pum.getPpc_id()) {
							continue;
						}
					}
					if (coabM != null) {
						if (coabM.getCabc_id() != pum.getEcpu_cabc_id()) {
							continue;
						}
					}
					if (!ownmonth.isEmpty()) {
						if (!ownmonth.equals(pum.getOwnmonth().toString()
								.trim())) {
							continue;
						}
					}
					if (!cid.isEmpty()) {
						if (!RegexUtil.isExists(cid, pum.getCid() == null ? ""
								: pum.getCid().toString().trim())) {
							continue;
						}
					}
					if (!gid.isEmpty()) {
						if (!RegexUtil.isExists(gid, pum.getGid() == null ? ""
								: pum.getGid().toString().trim())) {
							continue;
						}
					}

					spuList.add(pum);
				}

				searchCheck();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("检索出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 搜索检查
	 * 
	 */
	public void searchCheck() {
		if (spuList.size() == 0) {
			Messagebox.show("没有符合的数据!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);

			company = oldcompany;
			name = oldname;
			idcard = oldidcard;
			cid = oldcid;
			gid = oldgid;
			cityM = oldcityM;
			coabM = oldcoabM;
			ownmonth = oldownmonth;

			search();
		} else if (spuList.size() > 0) {
			oldcompany = company;
			oldcid = cid;
			oldgid = gid;
			oldidcard = idcard;
			oldname = name;
			oldcityM = cityM;
			oldcoabM = coabM;
			oldownmonth = ownmonth;

			citySelect();
		}
	}

	@Command("citySelect")
	@NotifyChange({ "scoabList" })
	public void citySelect() {
		try {
			scoabList.clear();

			for (CoAgencyBaseCityRelViewModel m : coabList) {
				if (cityM != null) {
					if (cityM.getCabc_ppc_id() != m.getCabc_ppc_id()) {
						continue;
					}
				}

				scoabList.add(m);
			}
			scoabList.add(0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 弹出导入窗口
	 * 
	 */
	@Command("importExcel")
	@NotifyChange({ "fieldList", "spuList", "cityList", "coabList" })
	public void importExcel() {
		Window window = (Window) Executions.createComponents(
				"/EmCommissionOut/EmCommissionOutPayUpdate_ImportExcel.zul",
				null, null);
		window.doModal();

		init();
	}

	public List<EmCommissionOutPayUpdateCRTModel> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<EmCommissionOutPayUpdateCRTModel> fieldList) {
		this.fieldList = fieldList;
	}

	public List<EmCommissionOutPayUpdateModel> getPuList() {
		return puList;
	}

	public void setPuList(List<EmCommissionOutPayUpdateModel> puList) {
		this.puList = puList;
	}

	public List<EmCommissionOutPayUpdateModel> getSpuList() {
		return spuList;
	}

	public void setSpuList(List<EmCommissionOutPayUpdateModel> spuList) {
		this.spuList = spuList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CoAgencyBaseCityRelViewModel> cityList) {
		this.cityList = cityList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public CoAgencyBaseCityRelViewModel getCityM() {
		return cityM;
	}

	public void setCityM(CoAgencyBaseCityRelViewModel cityM) {
		this.cityM = cityM;
	}

	public CoAgencyBaseCityRelViewModel getCoabM() {
		return coabM;
	}

	public void setCoabM(CoAgencyBaseCityRelViewModel coabM) {
		this.coabM = coabM;
	}

	public final String getOwnmonth() {
		return ownmonth;
	}

	public final String getCompany() {
		return company;
	}

	public final String getName() {
		return name;
	}

	public final String getIdcard() {
		return idcard;
	}

	public final void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setCompany(String company) {
		this.company = company;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<String> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public List<CoAgencyBaseCityRelViewModel> getScoabList() {
		return scoabList;
	}

	public void setScoabList(List<CoAgencyBaseCityRelViewModel> scoabList) {
		this.scoabList = scoabList;
	}
}
