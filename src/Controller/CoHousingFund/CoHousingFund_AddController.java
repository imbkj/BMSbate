package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoBaseModel;
import Model.CoHousingFundChangeModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.PubAreaSZModel;
import Model.PubCoEcoclassModel;
import Model.PubCoNatureModel;
import Model.PubGjBankModel;
import Model.PubIdcardTypeModel;
import Model.PubIndustryModel;
import Model.PubMemberShipModel;
import Model.PubTsbankModel;
import Model.TaskBatchModel;
import Util.IdcardUtil;
import Util.UserInfo;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoHousingFund.CoHousingFundZbBll;
import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class CoHousingFund_AddController {

	private CoHousingFundChangeModel m = new CoHousingFundChangeModel();
	private List<CoHousingFundChangeModel> cobaseList = new ListModelList<>();
	private CoHousingFundChangeModel cobaModel = new CoHousingFundChangeModel();
	private List<PubCoNatureModel> conatureList = new ListModelList<>();
	private List<PubCoEcoclassModel> coecoclassList = new ListModelList<>();
	private List<PubMemberShipModel> membershipList = new ListModelList<>();
	private List<PubAreaSZModel> areaszList = new ListModelList<>();
	private List<PubGjBankModel> gjbankList = new ListModelList<>();
	private List<PubTsbankModel> tsbankList = new ListModelList<>();
	private List<PubIdcardTypeModel> idcardtypeList = new ListModelList<>();
	private List<PubIndustryModel> industryList = new ListModelList<>();
	private List<Integer> cppList = new ListModelList<>();
	private boolean dj = true;
	private boolean jg;
	private boolean isshow = true;
	private boolean isshowmod = true;
	private boolean ispwdadd = false;
	private Integer daid;
	private Integer taprId;
	private String nowdate = null;
	private Date chfc_createtime;
	private String addRemark;
	private String addzbRemark;
	private int yearLimit;
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private Window win = (Window) Path.getComponent("/wInfo");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String pwd;

	public CoHousingFund_AddController() {
		m.setVis_modzb(true);
		m.setVis_isaddpwd(false);
		nowdate = getDateForm();
		try {
			CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());
			taprId = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
			if (daid != null) {
				List<TaskBatchModel> tmlist = bll.gettaskinfo(daid);
				Integer cid = 0;
				if (tmlist.size() > 0) {
					String s = tmlist.get(0).getTaba_remark();
					String[] str = s.split(",");
					cid = Integer.valueOf(str[0]);
				}
				List<CoBaseModel> list = bll.getcobaseinfo(cid);

				if (list.size() > 0) {
					cobaModel.setChfc_company(list.get(0).getCoba_company());
					cobaModel.setCoba_company(list.get(0).getCompany());
					cobaModel.setCid(list.get(0).getCid());
					cobaModel
							.setCoba_shortname(list.get(0).getCoba_shortname());
					cobaModel.setChfc_banktsacc(list.get(0).getCompany());
					cobaModel.setChfc_client(list.get(0).getCoba_client());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			fieldinit();
			addtype();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void fieldinit() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();

		m.setChfc_addtype("缴存登记");
		m.setChfc_zgtype("法人");
		m.setIspwd("1");
		m.setIshavepwd("1");
		m.setIsaddzb1("1");
		m.setIsmodzb1("1");
		ispwdcheck();
		m.setFirmonth(new Date());
		m.setOwnmonthDate(new Date());
		m.setChfc_department("无");
		setCobaseList(bll.getCobaList(cobaModel.getCid()));
		setConatureList(bll.getCoNatureList());
		conatureList.add(0, null);
		setCoecoclassList(bll.getCoEcoclassList());
		coecoclassList.add(0, null);
		setAreaszList(bll.getAreaSzList());
		areaszList.add(0, null);
		setGjbankList(bll.getGjBankList());
		gjbankList.add(0, null);
		setTsbankList(bll.getTsBankList());
		tsbankList.add(0, null);
		setMembershipList(bll.getMemberShipList());
		membershipList.add(0, null);
		setIdcardtypeList(bll.getIdcardTypeList());
		idcardtypeList.add(0, null);
		/*
		 * for (int i = 5; i <= 20; i++) { cppList.add(i); }
		 */

		cppList = bll.getHouseCppList(cobaModel.getCid());

		if (cppList.size() > 0) {
			m.setCpp(cppList.get(0));
		}
		if (cppList.size() == 1) {
			for (int i = 5; i <= 12; i++) {
				cppList.add(i);
			}
		}
		industryList = bll.pubIndustry();
	}

	@Command("addtype")
	@NotifyChange({ "dj", "jg", "m" })
	public void addtype() {
		if (m.getChfc_addtype().equals("缴存登记")) {
			setDj(true);
			setJg(false);
		} else if (m.getChfc_addtype().equals("账户接管")) {
			setDj(false);
			setJg(true);
			ispwdcheck();
			ishavepwdcheck();
		}
		companychange();
	}

	@Command
	@NotifyChange({ "m" })
	public void companychange() {
		m.setChfc_comid(cobaModel.getChfc_comid());
		m.setChfc_address(cobaModel.getChfc_address());
		m.setChfc_corname(cobaModel.getChfc_corname());
		m.setChfc_banktsacc(cobaModel.getCoba_company());
	}

	@Command
	@NotifyChange({ "m" })
	public void gjbankchange() {
		m.setChfc_banklk(m.getChfc_bankgj());
	}

	@Command
	@NotifyChange({ "m", "isshowmod" })
	public void ispwdcheck() {
		if (m.getIspwd().equals("1")) {
			m.setVis_modzb(true);
			m.setIsmodzb("1");
			isshowmod = true;
		} else {
			m.setVis_modzb(false);
			m.setIsmodzb("0");
			isshowmod = false;
		}
	}

	@Command
	@NotifyChange({ "m", "isshowmod", "ispwdadd" })
	public void ishavepwdcheck() {
		if (m.getIshavepwd().equals("1")) {
			m.setVis_modzb(true);
			m.setVis_modzb1(true);
			m.setIsmodzb("1");
			m.setIsmodzb1("1");
			m.setIsaddzb1("0");
			isshowmod = true;
			ispwdadd = false;
		} else {
			m.setVis_modzb(false);
			m.setVis_modzb1(false);
			m.setIsmodzb("0");
			m.setIsmodzb1("0");
			m.setIsaddzb1("1");
			isshowmod = false;
			ispwdadd = true;
		}
	}

	@Command
	@NotifyChange({ "m" })
	public void bankjcchange() {
		m.bankjc_handle();
	}

	/**
	 * 提交
	 * 
	 */
	@Command
	public void submit(@BindingParam("gd") Grid gd,
			@BindingParam("gd1") Grid gd1, @BindingParam("gd2") Grid gd2,
			@BindingParam("gd3") Grid gd3, @BindingParam("jczbgd") Grid gdkfzb,
			@BindingParam("khgd") Grid gdkf) {
		if (cobaModel.getCid() == null) {
			Messagebox.show("请选择单位!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {
			try {
				fieldhandle();
				if (m.getChfc_coridtype() != null
						&& m.getChfc_coridtype().equals("身份证")) {
					if (m.getChfc_coridcard() != null) {
						if (!IdcardUtil.validateCard(m.getChfc_coridcard())) {
							Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
				}
				if (m.getChfc_cpp() == null) {
					Messagebox.show("比例不正确!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				String err = "";
				if (m.getChfc_addtype() != null
						&& m.getChfc_addtype().equals("缴存登记")) {
					err = isEmploy();
				}

				if (dj == true) { // 打开缴存登记时才添加开户材料
					String[] m1 =docOC.AddchkHaveTo(gdkf);
					if (!m1[0].equals("1")) {
						return;
					}
					String[] m2 =docOC.AddchkHaveTo(gdkfzb);
					if (!m2[0].equals("1")) {
						return;
					}
				}
				if (jg == true) {
					String[] m3 =docOC.AddchkHaveTo(gd1);
					if (!m3[0].equals("1")) {
						return;
					}
				}
				if (dj == true && isshow == true) {
					String[] m4 = docOC.AddchkHaveTo(gd);
					if (!m4[0].equals("1")) {
						return;
					}
				}
				if (jg == true && isshowmod == true && m.getVis_modzb() == true) {
					String[] m5 = docOC.AddchkHaveTo(gd2);
					if (!m5[0].equals("1")) {
						return;
					}
				}
				if (jg == true && ispwdadd == true && isshowmod == false) {
					String[] m6 = docOC.AddchkHaveTo(gd3);
					if (!m6[0].equals("1")) {
						return;
					}
				}
				
				if (jg==true) {
					if (m.getChfc_bankgj()==null || m.getChfc_bankgj().equals("")) {
						Messagebox.show("请填写归集银行", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
					if (m.getChfc_banklk()==null || m.getChfc_banklk().equals("")) {
						Messagebox.show("请填写缴存银行", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}

				String[] str = new String[5];
				if (err.equals("")) {
					str = new CoHousingFund_OperateBll().add(m);
					if (str[0].equals("1")) {
						if (daid != null && daid > 0) {
							CoCompact_OperateBll bll = new CoCompact_OperateBll();
							bll.completeMission(daid, taprId);
						}
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();

						Executions
								.sendRedirect("/CoHousingFund/CoHousingFund_Add.zul");
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}

					CoHousingFund_PwdBll cfpb = new CoHousingFund_PwdBll();
					int cohf_id = cfpb.getcohfid(cobaModel.getCid());

					if (dj == true) { // 打开缴存登记时才添加开户材料
						String[] message = docOC.AddchkHaveTo(gdkf);
						if (message[0] == "1") {
							docOC.AddsubmitDoc(gdkf, String.valueOf(cohf_id)); // 添加开户材料
						}

						// 新增专办员
						CoHousingFundZbChangeModel cfzc = new CoHousingFundZbChangeModel();
						cfzc.setOwnmonth(Integer.parseInt(new SimpleDateFormat(
								"yyyyMM").format(m.getOwnmonthDate())));
						cfzc.setCfzc_addname(UserInfo.getUsername());
						cfzc.setCfzc_cohf_id(cohf_id);
						cfzc.setCfzc_addtype("新增专办员");
						cfzc.setCfzc_remark(addzbRemark);
						cfzc.setCfzc_chfz_id(0);
						cfzc.setCfzc_state(1);
						String[] messagezb = docOC.AddchkHaveTo(gdkfzb);
						if (messagezb[0] == "1") {
							CoHousingFundZbBll cfzb = new CoHousingFundZbBll();
							int cfzc_id = -1;
							cfzc_id = cfzb.addZbChange(cfzc);
							if (cfzc_id != -1) {
								docOC.AddsubmitDoc(gdkfzb,
										String.valueOf(cfzc_id));
							}
						}
					}
					if (jg == true) {
						// 新增专办员
						CoHousingFundZbChangeModel cfzc = new CoHousingFundZbChangeModel();
						cfzc.setOwnmonth(Integer.parseInt(new SimpleDateFormat(
								"yyyyMM").format(m.getOwnmonthDate())));
						cfzc.setCfzc_addname(UserInfo.getUsername());
						cfzc.setCfzc_cohf_id(cohf_id);
						cfzc.setCfzc_addtype("新增专办员");
						cfzc.setCfzc_remark(addzbRemark);
						cfzc.setCfzc_chfz_id(0);
						cfzc.setCfzc_state(1);
						String[] messagezb = docOC.AddchkHaveTo(gd1);
						if (messagezb[0] == "1") {
							CoHousingFundZbBll cfzb = new CoHousingFundZbBll();
							int cfzc_id = -1;
							cfzc_id = cfzb.addZbChange(cfzc);
							if (cfzc_id != -1) {
								docOC.AddsubmitDoc(gd1, String.valueOf(cfzc_id));
							}
						}
					}
					// 缴存登记办理密钥 登记页面为true 接管页面为false 并且选择是办理密钥 isshow为true
					if (dj == true && isshow == true /* && str[0].equals("1") */) {
						System.out.println(1);
						// 创建一个密钥change对象
						CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
						cfpc.setCfpc_yearlimit(this.yearLimit);
						cfpc.setOwnmonth(Integer.parseInt(new SimpleDateFormat(
								"yyyyMM").format(m.getOwnmonthDate())));
						System.out.println(cfpc.getOwnmonth());
						cfpc.setCfpc_remark(addRemark);
						cfpc.setCfpc_chfp_id(0);
						cfpc.setCfpc_addtype("申请数字证书");
						cfpc.setCfpc_addname(UserInfo.getUsername());
						cfpc.setCfpc_cohf_id(cohf_id);
						cfpc.setCfpc_state(1);
						int cfpc_id = -1;
						String[] message = docOC.AddchkHaveTo(gd);
						if (message[0] == "1") {
							cfpc_id = cfpb.addPwdChange(cfpc, null);
							if (cfpc_id != -1) {
								docOC.AddsubmitDoc(gd, String.valueOf(cfpc_id));
							}
						}
					}
					// 账户接管办理密钥变更 接管为true ，有密钥为true并且确认变更为true
					if (jg == true && isshowmod == true
							&& m.getVis_modzb() == true
					/* && str[0].equals("1") */) {
						System.out.println(2);
						// 新增密钥
						// 创建一个密钥change对象
						CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
						cfpc.setCfpc_yearlimit(this.yearLimit);
						cfpc.setOwnmonth(Integer.parseInt(new SimpleDateFormat(
								"yyyyMM").format(m.getOwnmonthDate())));
						System.out.println(cfpc.getOwnmonth());
						cfpc.setCfpc_flag(1); // 这个参数代表是从账户接管添加的数据
						cfpc.setCfpc_remark(addRemark);
						cfpc.setCfpc_chfp_id(0);
						cfpc.setCfpc_addtype("申请数字证书");
						cfpc.setCfpc_addname(UserInfo.getUsername());
						cfpc.setCfpc_cohf_id(cohf_id);
						cfpc.setCfpc_state(1);
						int cfpc_id = -1;
						String[] message = docOC.AddchkHaveTo(gd2);
						if (message[0] == "1") {
							cfpc_id = cfpb.addPwdChange(cfpc, null);
							if (cfpc_id != -1) {
								docOC.AddsubmitDoc(gd2, String.valueOf(cfpc_id));
							}
						}

					}

					// 如果在接管页面，并且pwd不为空，并且选择的是有密钥
					if (m.getVis_modzb() == true && pwd != null
							&& !"".equals(pwd)) {
						CoHousingFundPwdModel p = new CoHousingFundPwdModel();
						p.setChfp_pwd(pwd);
						p.setChfp_addname(UserInfo.getUsername());
						p.setChfp_cohf_id(cohf_id);
						cfpb.addPassWord(p);
					}

					// 账户接管办理密钥 是否添加密钥为true ，有密钥为false并且确认变更为true
					if (jg == true && ispwdadd == true && isshowmod == false
					/* && str[0].equals("1") */) {
						System.out.println(3);
						// 新增密钥
						// 创建一个密钥change对象
						CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
						cfpc.setCfpc_yearlimit(this.yearLimit);
						cfpc.setOwnmonth(Integer.parseInt(new SimpleDateFormat(
								"yyyyMM").format(m.getOwnmonthDate())));
						System.out.println(cfpc.getOwnmonth());
						cfpc.setCfpc_remark(addRemark);
						cfpc.setCfpc_chfp_id(0);
						cfpc.setCfpc_addtype("申请数字证书");
						cfpc.setCfpc_addname(UserInfo.getUsername());
						cfpc.setCfpc_cohf_id(cohf_id);
						cfpc.setCfpc_state(1);
						int cfpc_id = -1;
						String[] message = docOC.AddchkHaveTo(gd3);
						if (message[0] == "1") {
							cfpc_id = cfpb.addPwdChange(cfpc, null);
							if (cfpc_id != -1) {
								docOC.AddsubmitDoc(gd3, String.valueOf(cfpc_id));
							}
						}

					}

				} else {
					Messagebox.show(err, "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	}

	// 判断必填项是否有填写
	private String isEmploy() {
		String err = "";
		if (m.getChfc_comid() == null || m.getChfc_comid().equals("")) {
			err = "组织机构代码不能为空";
		} else if (m.getChfc_address() == null
				|| m.getChfc_address().equals("")) {
			err = "单位地址不能为空";
		} else if (m.getChfc_industry() == null
				|| m.getChfc_industry().equals("")) {
			err = "行业代码不能为空";
		} else if (m.getChfc_pastal() == null || m.getChfc_pastal().equals("")) {
			err = "邮政编码不能为空";
		} else if (m.getChfc_nature() == null || m.getChfc_nature().equals("")) {
			err = "性质分类不能为空";
		} else if (m.getChfc_attached() == null
				|| m.getChfc_attached().equals("")) {
			err = "隶属关系不能为空";
		} else if (m.getChfc_ecoclass() == null
				|| m.getChfc_ecoclass().equals("")) {
			err = "企业经济类型不能为空";
		} else if (m.getChfc_tsday()==null || m.getChfc_tsday().equals("")) {
			err = "托收日不能为空";
		} else if (m.getFirmonth()==null || m.getFirmonth().equals("")) {
			err = "首次托收月不能为空";
		} else if (m.getChfc_tsday()!=null && (m.getChfc_tsday()<1 || m.getChfc_tsday()>31)) {
			err ="托收日有误";
		}
		return err;
	}

	/**
	 * 字段处理
	 * 
	 */
	public void fieldhandle() {
		try {
			m.month_handle();
			m.cpp_handle();
			m.setCid(cobaModel.getCid());
			m.setChfc_company(cobaModel.getCoba_company());
			m.setCoba_shortname(cobaModel.getCoba_shortname());
			m.setChfc_client(cobaModel.getCoba_client());
			m.setChfc_ispwd(m.getIspwd().equals("1") ? 1 : 0);
			m.setChfc_addname(UserInfo.getUsername());
			m.setChfc_laststate(0);
			m.setChfc_state(1);
			m.setChfc_tzlstate(0);
			m.setChfc_if_update_compact(false);
			if (chfc_createtime != null) {
				m.setChfc_createtime(sdf.format(chfc_createtime));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据处理出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 显示专办员录入
	@Command
	@NotifyChange({ "isshow" })
	public void showview(@BindingParam("show") int show) {
		if (show == 1) {
			isshow = true;
		} else {
			isshow = false;
		}
	}

	// 显示密钥变更
	@Command
	@NotifyChange({ "isshowmod" })
	public void showmod(@BindingParam("show") int show) {
		if (show == 1) {
			isshowmod = true;
		} else {
			isshowmod = false;
		}
	}

	// 显示密钥添加
	@Command
	@NotifyChange({ "ispwdadd" })
	public void showadd(@BindingParam("show") int show) {
		if (show == 1) {
			ispwdadd = true;
		} else {
			ispwdadd = false;
		}
	}

	@Command
	public void yearLimit(@BindingParam("addLimit") int limit) {
		this.yearLimit = limit;
	}

	public boolean isIspwdadd() {
		return ispwdadd;
	}

	public void setIspwdadd(boolean ispwdadd) {
		this.ispwdadd = ispwdadd;
	}

	public String getAddzbRemark() {
		return addzbRemark;
	}

	public void setAddzbRemark(String addzbRemark) {
		this.addzbRemark = addzbRemark;
	}

	public boolean isIsshowmod() {
		return isshowmod;
	}

	public void setIsshowmod(boolean isshowmod) {
		this.isshowmod = isshowmod;
	}

	public String getAddRemark() {
		return addRemark;
	}

	public void setAddRemark(String addRemark) {
		this.addRemark = addRemark;
	}

	public boolean isIsshow() {
		return isshow;
	}

	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}

	public CoHousingFundChangeModel getM() {
		return m;
	}

	public void setM(CoHousingFundChangeModel m) {
		this.m = m;
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

	public List<CoHousingFundChangeModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(List<CoHousingFundChangeModel> cobaseList) {
		this.cobaseList = cobaseList;
	}

	public CoHousingFundChangeModel getCobaModel() {
		return cobaModel;
	}

	public void setCobaModel(CoHousingFundChangeModel cobaModel) {
		this.cobaModel = cobaModel;
	}

	public final List<PubCoNatureModel> getConatureList() {
		return conatureList;
	}

	public final List<PubCoEcoclassModel> getCoecoclassList() {
		return coecoclassList;
	}

	public final List<PubAreaSZModel> getAreaszList() {
		return areaszList;
	}

	public final List<PubGjBankModel> getGjbankList() {
		return gjbankList;
	}

	public final List<PubTsbankModel> getTsbankList() {
		return tsbankList;
	}

	public final void setConatureList(List<PubCoNatureModel> conatureList) {
		this.conatureList = conatureList;
	}

	public final void setCoecoclassList(List<PubCoEcoclassModel> coecoclassList) {
		this.coecoclassList = coecoclassList;
	}

	public final void setAreaszList(List<PubAreaSZModel> areaszList) {
		this.areaszList = areaszList;
	}

	public final void setGjbankList(List<PubGjBankModel> gjbankList) {
		this.gjbankList = gjbankList;
	}

	public final void setTsbankList(List<PubTsbankModel> tsbankList) {
		this.tsbankList = tsbankList;
	}

	public List<PubMemberShipModel> getMembershipList() {
		return membershipList;
	}

	public void setMembershipList(List<PubMemberShipModel> membershipList) {
		this.membershipList = membershipList;
	}

	public List<PubIdcardTypeModel> getIdcardtypeList() {
		return idcardtypeList;
	}

	public void setIdcardtypeList(List<PubIdcardTypeModel> idcardtypeList) {
		this.idcardtypeList = idcardtypeList;
	}

	public List<Integer> getCppList() {
		return cppList;
	}

	public void setCppList(List<Integer> cppList) {
		this.cppList = cppList;
	}

	public String getNowdate() {
		return nowdate;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}

	public Date getChfc_createtime() {
		return chfc_createtime;
	}

	public void setChfc_createtime(Date chfc_createtime) {
		this.chfc_createtime = chfc_createtime;
	}

	private String getDateForm() {
		Date date = new Date();
		String datestr = null;
		try {
			SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
			if (date != null) {
				datestr = form.format(date);
				datestr = datestr + "01";
				// date=forms.parse(datestr);
			}
		} catch (Exception e) {

		}
		return datestr;

	}

	public List<PubIndustryModel> getIndustryList() {
		return industryList;
	}

	public void setIndustryList(List<PubIndustryModel> industryList) {
		this.industryList = industryList;
	}

	
	
}
