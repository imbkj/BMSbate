package Controller.CoSocialInsurance;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


import bll.CoCompact.CoCompact_OperateBll;
import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

import Model.CoBaseModel;
import Model.CoShebaoChangeModel;
import Model.PubAreaCoShebaoModel;
import Model.PubBankModel;
import Model.PubHealthModel;
import Model.PubIndustryFirstModel;
import Model.PubIndustryModel;
import Model.TaskBatchModel;
import Util.DateUtil;
import Util.UserInfo;

public class CoSocialInsurance_AddController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	private CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();
	// 下拉列表
	private List<CoBaseModel> cobaseList = new ListModelList<>();// 公司
	private CoBaseModel coBaseModel = new CoBaseModel();
	private List<PubIndustryFirstModel> industryFirstList;// 行业一级
	private PubIndustryFirstModel industryFirstModel=new PubIndustryFirstModel();
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

	private String company;
	private Integer daid;
	private Integer taprId;
	private Date ukeytruetime, ukeyfailtime;
	private List<String> csbc_sbaddstr = bll.getShebaoArealist(); // 执行人
	private List<PubBankModel> zkbanklist = bll.getBankList("");// 制卡银行信息
	private List<PubBankModel> zkbranlist = new ArrayList<PubBankModel>();// 制卡银行支行信息

	// 控制显示
	private boolean dj;
	private boolean jg;

	public CoSocialInsurance_AddController() {
		try {
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());
			taprId = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
			if (daid != null) {
				List<TaskBatchModel> tmlist = bll.gettaskinfo(daid);
				Integer cid=0;
				if (tmlist.size()>0) {
					String s = tmlist.get(0).getTaba_remark();
					String[] str =s.split(",");
					cid=Integer.valueOf(str[0]);
				}
				
				//List<CoBaseModel> list = bll.getcobase(daid);
				List<CoBaseModel> list = bll.getcobaseinfo(cid);
				
				company = list.get(0).getCoba_company();
				coBaseModel.setCid(list.get(0).getCid());
				coBaseModel.setCoba_shortname(list.get(0).getCoba_shortname());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			comboinit();
			fieldinit();
			addtype();
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

		setCobaseList(bll.getCobaList(coBaseModel.getCid()));
		
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
	}

	/**
	 * 根据新增类型控制页面显示
	 */
	@Command("addtype")
	@NotifyChange({ "dj", "jg" })
	public void addtype() {
		if (m.getCsbc_addtype().equals("缴存登记")) {
			setDj(true);
			setJg(false);
		} else {
			setDj(false);
			setJg(true);
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
				if (industryFirstModel.getId().toString().equals(m.getF_id().toString())) {
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
		/*townList1.clear();
		if (areaModel.getId() != null) {
			for (PubAreaCoShebaoModel m : townList) {
				if (areaModel.getId().toString().equals(m.getA_id().toString())) {
					townList1.add(m);
				}
			}
		}
		if (townList1.size() == 0) {
			townList1.add(0, new PubAreaCoShebaoModel());
		}
		townModel = townList1.get(0);
		townChange();*/
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

	/**
	 * 提交
	 */
	@Command("submit")
	@NotifyChange({ "m", "" })
	public void submit(@BindingParam("a") final Window win) {
		if (coBaseModel.getCid() == null
				|| (coBaseModel.getCid() != null && coBaseModel.getCid()
						.equals(0))) {
			Messagebox.show("请选择单位全称!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			fieldhandle();
			try {

				if (m.getCsbc_coridcard() != null
						&& !m.getCsbc_coridcard().equals("")) {
					/*
					 * if (!IdcardUtil.validateCard(m.getCsbc_coridcard())) {
					 * Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
					 * Messagebox.OK, Messagebox.ERROR); return; }
					 */
				}

				if (ukeytruetime != null) {
					m.setCosb_ukeytruetime(DateToStr(ukeytruetime));
				}
				if (ukeyfailtime != null) {
					m.setCosb_ukeyfailtime(DateToStr(ukeyfailtime));
				}

				if (ukeytruetime != null
						&& ukeyfailtime != null
						&& DateUtil.datediff(ukeytruetime, ukeyfailtime, "d") < 0) {
					Messagebox.show("Ukey有效日期录入有误.", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				if (dj && m.getCosb_branchbank() != null
						&& !m.getCosb_branchbank().equals("")
						&& (m.getCosb_cardbank() == null || m
								.getCosb_cardbank().equals(""))) {
					Messagebox.show("请先选择社保卡制卡银行，再选制卡支行!", "ERROR",
							Messagebox.OK, Messagebox.ERROR);
				} else {
					String err = "";
					err = isEmpty();
					if (err == "") {
						String[] str = new CoSocialInsurance_OperateBll().add(
								m, coBaseModel.getCoba_shortname());

						if (str[0].equals("1")) {
							if (daid != null && daid > 0) {
								CoCompact_OperateBll bll = new CoCompact_OperateBll();
								bll.completeMission(daid, taprId);

							}
							Messagebox.show(str[1], "INFORMATION",
									Messagebox.OK, Messagebox.INFORMATION);
							win.detach();
							// Executions.sendRedirect("/CoSocialInsurance/CoSocialInsurance_Add.zul");
						} else {
							Messagebox.show(str[1], "ERROR", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show(err, "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 判断必填项
	private String isEmpty() {
		String err = "";
		if (m.getCsbc_addtype() != null) {
			if (m.getCsbc_addtype().equals("缴存登记")) {
				if(company==null||company.equals(""))
				{
					err = "单位全称不能为空";
				}
				else if (m.getCsbc_comid() == null || m.getCsbc_comid().equals("")) {
					err = "组织机构代码不能为空";
				} else if (m.getCsbc_regid() == null
						|| m.getCsbc_regid().equals("")) {
					err = "工商注册号不能为空";
				} else if (m.getCsbc_ecoclass() == null
						|| m.getCsbc_ecoclass().equals("")) {
					err = "经济类型不能为空";
				}
				/*else if (m.getCsbc_iclassfirst() == null
						|| m.getCsbc_iclassfirst().equals("")) {
					err = "行业一级不能为空";
				} 
				else if (m.getCsbc_industryclass() == null
						|| m.getCsbc_industryclass().equals("")) {
					err = "行业二级不能为空";
				}*/ else if (m.getCsbc_forms() == null
						|| m.getCsbc_forms().equals("")) {
					err = "组成形式不能为空";
				} else if (areaModel == null || areaModel.equals("")) {
					err = "企业所在割区不能为空";
				}
				/* else if (townModel == null || townModel.equals("")) {
					err = "镇不能为空";
				}*/
				else if (m.getCsbc_street() == null
						|| m.getCsbc_street().equals("")) {
					err = "街道不能为空";
				} else if (m.getCsbc_regadd() == null
						|| m.getCsbc_regadd().equals("")) {
					err = "企业注册地址不能为空";
				} else if (m.getCsbc_pastcode() == null
						|| m.getCsbc_pastcode().equals("")) {
					err = "企业邮箱不能为空";
				} else if (m.getCsbc_corname() == null
						|| m.getCsbc_corname().equals("")) {
					err = "法人姓名不能为空";
				} else if (m.getCsbc_coridcard() == null
						|| m.getCsbc_coridcard().equals("")) {
					err = "法人证件号不能为空";
				} else if (m.getCsbc_bankcode() == null
						|| m.getCsbc_bankcode().equals("")) {
					err = "托收银行编码不能为空";
				} else if (m.getCsbc_bankname() == null
						|| m.getCsbc_bankname().equals("")) {
					err = "银行全称(营业部全称)不能为空";
				}

				else if (m.getCsbc_payapply() == null
						|| m.getCsbc_payapply().equals("")) {
					err = "收据送达方式不能为空";
				} else if (m.getCosb_cardbank() == null
						|| m.getCosb_cardbank().equals("")) {
					err = "社保卡制卡银行不能为空";
				} else if (m.getCosb_branchbank() == null
						|| m.getCosb_branchbank().equals("")) {
					err = "社保卡制卡详细支行";
				}
			} else {
				if(company==null||company.equals(""))
				{
					err = "单位全称不能为空";
				}
				else if (m.getCsbc_pwd() == null || m.getCsbc_pwd().equals("")) {
					err = "密码不能为空";
				} 
			}
		}
		return err;
	}

	/**
	 * 字段处理
	 */
	public void fieldhandle() {
		try {
			m.setCid(coBaseModel.getCid());
			m.setOwnmonth(Integer.parseInt(new SimpleDateFormat("yyyyMM")
					.format(new Date())));
			m.setCsbc_sorarea(areaModel.getName());
			m.setCsbc_town(townModel.getName());
			if (m.getCsbc_heaname() != null && !m.getCsbc_heaname().isEmpty()) {
				m.setCsbc_heaname(m.getCsbc_heaname().substring(1));
			}
			m.setCsbc_addname(UserInfo.getUsername());
			m.setCsbc_laststate(0);
			m.setCsbc_state(1);
			m.setCsbc_tzlstate(0);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("数据处理出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 选择银行事件
	@Command
	@NotifyChange("zkbranlist")
	public void changebank(@BindingParam("cb") Combobox cb) {
		Integer bankid = 0;
		if (cb.getValue() != null && !cb.getValue().equals("")) {
			bankid = cb.getSelectedItem().getValue();
		}
		if (bankid > 0) {
			zkbranlist = bll.getBankBranchList(" and bran_bank_id=" + bankid);
			m.setCosb_branchbank("");
		}
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}

	public List<String> getCsbc_sbaddstr() {
		return csbc_sbaddstr;
	}

	public void setCsbc_sbaddstr(List<String> csbc_sbaddstr) {
		this.csbc_sbaddstr = csbc_sbaddstr;
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

	public List<PubIndustryFirstModel> getIndustryFirstList() {
		return industryFirstList;
	}

	public void setIndustryFirstList(List<PubIndustryFirstModel> industryFirstList) {
		this.industryFirstList = industryFirstList;
	}

	public boolean isDj() {
		return dj;
	}

	public void setDj(boolean dj) {
		this.dj = dj;
	}

	public boolean isJg() {
		return jg;
	}

	public void setJg(boolean jg) {
		this.jg = jg;
	}

	public List<PubAreaCoShebaoModel> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<PubAreaCoShebaoModel> areaList) {
		this.areaList = areaList;
	}

	public List<PubAreaCoShebaoModel> getTownList() {
		return townList;
	}

	public void setTownList(List<PubAreaCoShebaoModel> townList) {
		this.townList = townList;
	}

	public List<PubAreaCoShebaoModel> getStreetList() {
		return streetList;
	}

	public void setStreetList(List<PubAreaCoShebaoModel> streetList) {
		this.streetList = streetList;
	}

	public List<PubAreaCoShebaoModel> getTownList1() {
		return townList1;
	}

	public void setTownList1(List<PubAreaCoShebaoModel> townList1) {
		this.townList1 = townList1;
	}

	public List<PubAreaCoShebaoModel> getStreetList1() {
		return streetList1;
	}

	public void setStreetList1(List<PubAreaCoShebaoModel> streetList1) {
		this.streetList1 = streetList1;
	}

	public PubAreaCoShebaoModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(PubAreaCoShebaoModel areaModel) {
		this.areaModel = areaModel;
	}

	public PubAreaCoShebaoModel getTownModel() {
		return townModel;
	}

	public void setTownModel(PubAreaCoShebaoModel townModel) {
		this.townModel = townModel;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getUkeytruetime() {
		return ukeytruetime;
	}

	public void setUkeytruetime(Date ukeytruetime) {
		this.ukeytruetime = ukeytruetime;
	}

	public Date getUkeyfailtime() {
		return ukeyfailtime;
	}

	public void setUkeyfailtime(Date ukeyfailtime) {
		this.ukeyfailtime = ukeyfailtime;
	}

	public List<PubBankModel> getZkbanklist() {
		return zkbanklist;
	}

	public void setZkbanklist(List<PubBankModel> zkbanklist) {
		this.zkbanklist = zkbanklist;
	}

	public List<PubBankModel> getZkbranlist() {
		return zkbranlist;
	}

	public void setZkbranlist(List<PubBankModel> zkbranlist) {
		this.zkbranlist = zkbranlist;
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
