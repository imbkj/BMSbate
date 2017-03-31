package Controller.CoSocialInsurance;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;
import Model.CoBaseModel;
import Model.CoShebaoChangeModel;
import Model.PubAreaCoShebaoModel;
import Model.PubBankModel;
import Model.PubHealthModel;
import Model.PubIndustryFirstModel;
import Model.PubIndustryModel;
import Util.IdcardUtil;
import Util.UserInfo;

public class CoSocialInsurance_ReAddController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	Integer daid = 0;

	// 下拉列表
	private List<CoBaseModel> cobaseList = new ListModelList<>();// 公司
	private CoBaseModel coBaseModel = new CoBaseModel();

	private List<PubIndustryFirstModel> industryFirstList;// 行业一级
	private PubIndustryFirstModel industryFirstModel = new PubIndustryFirstModel();
	private List<PubIndustryModel> industryList = new ListModelList<>();// 行业二级
	private List<PubIndustryModel> industryList1 = new ListModelList<>();// 行业二级

	private List<PubAreaCoShebaoModel> areaList;// 区域
	private PubAreaCoShebaoModel areaModel = new PubAreaCoShebaoModel();
	private List<PubAreaCoShebaoModel> townList;// 镇
	private List<PubAreaCoShebaoModel> townList1 = new ListModelList<>();
	private PubAreaCoShebaoModel townModel = new PubAreaCoShebaoModel();
	private List<PubAreaCoShebaoModel> streetList;// 街道
	private List<PubAreaCoShebaoModel> streetList1 = new ListModelList<>();
	private List<PubBankModel> bankList;
	private List<PubHealthModel> healthList;// 医疗机构

	public CoSocialInsurance_ReAddController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoChangeInfo(daid));

			comboinit();
			fieldinit();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 字段初始化
	 */
	public void fieldinit() {
		m.setCsbc_addtype("缴存登记");
		m.setCsbc_submission("网上申报");
		m.setCsbc_paytype("电脑托收");
	}

	/**
	 * 下拉列表初始化
	 */
	public void comboinit() {
		CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

		setCobaseList(bll.getCobaList(null));

		setIndustryFirstList(bll.getIndustryFirstList());
		industryFirstList.add(0, new PubIndustryFirstModel());
		setIndustryList(new ListModelList<>(bll.getIndustryList()));

		setAreaList(new ListModelList<>(bll.getAreaList()));
		areaList.add(0, new PubAreaCoShebaoModel());
		setTownList(new ListModelList<>(bll.getTownList()));
		setStreetList(new ListModelList<>(bll.getStreetList()));
		setBankList(new ListModelList<>(bll.getBankList()));
		bankList.add(0, new PubBankModel());
		setHealthList(new ListModelList<>(bll.getHealthList()));
		healthList.add(0, new PubHealthModel());

		if (m.getCsbc_sorarea() != null && !m.getCsbc_sorarea().isEmpty()) {
			for (PubAreaCoShebaoModel m : areaList) {
				if (this.m.getCsbc_sorarea().equals(m.getName())) {
					setAreaModel(m);
					break;
				}
			}
			areaChange();
		}
		if (m.getCsbc_town() != null && !m.getCsbc_town().isEmpty()) {
			for (PubAreaCoShebaoModel m : townList1) {
				if (this.m.getCsbc_town().equals(m.getName())) {
					setTownModel(m);
					break;
				}
			}
			townChange();
		}
	}

	/**
	 * 选择行业一级
	 */
	@Command("industryFirstChange")
	@NotifyChange({ "industryList1", "m" })
	public void industryFirstChange() {
		industryList1.clear();
		if (industryFirstModel.getId() != null) {
			for (PubIndustryModel m : industryList) {
				if (industryFirstModel.getId().toString()
						.equals(m.getF_id().toString())) {
					industryList1.add(m);
				}
			}
		}
		if (industryList1.size() == 0) {
			industryList1.add(new PubIndustryModel());
		}
		this.m.setCsbc_industryclass(industryList1.get(0).getName());
	}

	/**
	 * 选择区域
	 */
	@Command("areaChange")
	@NotifyChange({ "townList1", "streetList1", "m", "townModel" })
	public void areaChange() {
		streetList1.clear();
		if (areaModel.getId() != null) {
			for (PubAreaCoShebaoModel m : streetList) {
				if (areaModel.getId().toString().equals(m.getT_id().toString())) {
					streetList1.add(m);
				}
			}
		}
		if (streetList1.size() == 0) {
			streetList1.add(new PubAreaCoShebaoModel());
		}
		this.m.setCsbc_street(streetList1.get(0).getName());
		/*
		 * townList1.clear(); if (areaModel.getId() != null) { for
		 * (PubAreaCoShebaoModel m : townList) { if
		 * (areaModel.getId().toString().equals(m.getA_id().toString())) {
		 * townList1.add(m); } } } if (townList1.size() == 0) { townList1.add(0,
		 * new PubAreaCoShebaoModel()); } townModel = townList1.get(0);
		 * townChange();
		 */
	}

	/**
	 * 选择镇
	 */
	@Command("townChange")
	@NotifyChange({ "streetList1", "m" })
	public void townChange() {
		streetList1.clear();
		if (townModel.getId() != null) {
			for (PubAreaCoShebaoModel m : streetList) {
				if (townModel.getId().toString().equals(m.getT_id().toString())) {
					streetList1.add(m);
				}
			}
		}
		if (streetList1.size() == 0) {
			streetList1.add(new PubAreaCoShebaoModel());
		}
		this.m.setCsbc_street(streetList1.get(0).getName());
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		flagA: {
			try {
				fieldhandle();
			} catch (Exception e) {
				Messagebox.show("数据处理出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
				break flagA;
			}
			if (m.getCsbc_coridcard() != null
					&& !m.getCsbc_coridcard().equals("")) {
//				if (!IdcardUtil.validateCard(m.getCsbc_coridcard())) {
//					Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
//							Messagebox.ERROR);
//					break flagA;
//				}
			}

			try {
				String[] str = new CoSocialInsurance_OperateBll().resubmit(m);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 数据处理
	 * 
	 */
	public void fieldhandle() {
		m.setCsbc_state(1);
		m.setCsbc_addname(UserInfo.getUsername());
		m.setCsbc_sorarea(areaModel.getName());
		m.setCsbc_town(townModel.getName());
		if (m.getCsbc_heaname() != null && !m.getCsbc_heaname().isEmpty()) {
			m.setCsbc_heaname(m.getCsbc_heaname().substring(1));
		}
	}

	public CoShebaoChangeModel getM() {
		return m;
	}

	public void setM(CoShebaoChangeModel m) {
		this.m = m;
	}

	public List<CoBaseModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(List<CoBaseModel> cobaseList) {
		this.cobaseList = cobaseList;
	}

	public CoBaseModel getCoBaseModel() {
		return coBaseModel;
	}

	public void setCoBaseModel(CoBaseModel coBaseModel) {
		this.coBaseModel = coBaseModel;
	}

	public List<PubIndustryModel> getIndustryList() {
		return industryList;
	}

	public void setIndustryList(List<PubIndustryModel> industryList) {
		this.industryList = industryList;
	}

	public List<PubAreaCoShebaoModel> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<PubAreaCoShebaoModel> areaList) {
		this.areaList = areaList;
	}

	public PubAreaCoShebaoModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(PubAreaCoShebaoModel areaModel) {
		this.areaModel = areaModel;
	}

	public List<PubAreaCoShebaoModel> getTownList() {
		return townList;
	}

	public void setTownList(List<PubAreaCoShebaoModel> townList) {
		this.townList = townList;
	}

	public List<PubAreaCoShebaoModel> getTownList1() {
		return townList1;
	}

	public void setTownList1(List<PubAreaCoShebaoModel> townList1) {
		this.townList1 = townList1;
	}

	public PubAreaCoShebaoModel getTownModel() {
		return townModel;
	}

	public void setTownModel(PubAreaCoShebaoModel townModel) {
		this.townModel = townModel;
	}

	public List<PubAreaCoShebaoModel> getStreetList() {
		return streetList;
	}

	public void setStreetList(List<PubAreaCoShebaoModel> streetList) {
		this.streetList = streetList;
	}

	public List<PubAreaCoShebaoModel> getStreetList1() {
		return streetList1;
	}

	public void setStreetList1(List<PubAreaCoShebaoModel> streetList1) {
		this.streetList1 = streetList1;
	}

	public List<PubBankModel> getBankList() {
		return bankList;
	}

	public void setBankList(List<PubBankModel> bankList) {
		this.bankList = bankList;
	}

	public List<PubHealthModel> getHealthList() {
		return healthList;
	}

	public void setHealthList(List<PubHealthModel> healthList) {
		this.healthList = healthList;
	}

	public List<PubIndustryFirstModel> getIndustryFirstList() {
		return industryFirstList;
	}

	public void setIndustryFirstList(
			List<PubIndustryFirstModel> industryFirstList) {
		this.industryFirstList = industryFirstList;
	}

	public PubIndustryFirstModel getIndustryFirstModel() {
		return industryFirstModel;
	}

	public void setIndustryFirstModel(PubIndustryFirstModel industryFirstModel) {
		this.industryFirstModel = industryFirstModel;
	}

	public List<PubIndustryModel> getIndustryList1() {
		return industryList1;
	}

	public void setIndustryList1(List<PubIndustryModel> industryList1) {
		this.industryList1 = industryList1;
	}

}
