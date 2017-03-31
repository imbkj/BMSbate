package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import sun.text.normalizer.UTF16;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;
import bll.EmResidencePermit.Emrp_ChangeBll;
import bsh.util.Util;

import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;
import Model.EmbaseModel;
import Model.HandoverPeopleModel;
import Model.PubCodeConversionModel;
import Model.PubFolkModel;
import Model.ResponsibilityBookModel;
import Model.WorkClassInfoModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmReg_AddController {

	Integer gid;
	String t_kind;

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();

	// 下拉列表绑定
	private List<PubFolkModel> folkList;// 民族
	private PubFolkModel folkM = new PubFolkModel();
	private List<PubCodeConversionModel> eduList;// 文化程度
	private PubCodeConversionModel eduM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> marList;// 婚姻状况
	private PubCodeConversionModel marM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> partyList;// 政治面貌
	private PubCodeConversionModel parM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> hjList;// 户籍类型
	private PubCodeConversionModel hjM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> titleList;// 职称
	private PubCodeConversionModel titleM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> compactList;// 合同性质
	private List<PubCodeConversionModel> compactyearList;// 合同年限
	private PubCodeConversionModel comyM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> skillList;// 职业技能等级
	private PubCodeConversionModel skillM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> comeszList;// 来深居住事由
	private PubCodeConversionModel comeszM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> houseList;// 住所类别
	private PubCodeConversionModel houseM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> housemodeList;// 居住方式
	private PubCodeConversionModel housemodeM = new PubCodeConversionModel();
	private List<WorkClassInfoModel> wcinList;// 工种
	private WorkClassInfoModel wcinM = new WorkClassInfoModel();
	private List<HandoverPeopleModel> handList;// 交接人

	// 特殊字段
	private String is_parttime = "否";// 是否兼职
	private String if_unlimited = "否";// 无固定劳动期限
	private String if_resident_con = "否";// 是否添加居住证转换
	private Date compact_s_date;// 劳动合同开始日期
	private Date compact_e_date;// 劳动合同结束日期
	private Date worktime;// 参加工作时间
	private Date unit_b_date;// 本单位工作起始日期
	private Date come_sz_date;// 来深时间
	private Date r_date;// 入住时间

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private EmbaseModel emml = new EmbaseModel();
	private boolean compactVis = true;
	private EmReg_ListBll bll = new EmReg_ListBll();

	public EmReg_AddController() {
		try {
			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());
			emml = bll.getEmbaseModel(gid);
			if (Executions.getCurrent().getArg().get("type") != null) {
				t_kind = Executions.getCurrent().getArg().get("type")
						.toString();
			} else {
				t_kind = "新办";
			}
			setErm(bll.getEmbaseInfo(gid));
			erm.setErin_t_kind(t_kind);
			init();
			// 调用获取下拉列表方法
			getComboList();
			init();
			erm.setErin_handover_people("林敏");
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void addwin(@BindingParam("win") Window win) {
		EmRegistrationInfoModel ml = new EmRegistrationInfoModel();
		if (gid != null) {
			EmReg_ListBll bllr = new EmReg_ListBll();
			ml = bllr.getInfo(gid);

		}
		if (ml.getErin_id() != null) {
			Messagebox.show("该员工已有就业登记数据!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			refreshUrl();
		} else {
			// 查询公司计划生育责任书是否已到期
			EmReg_ListBll bllr = new EmReg_ListBll();
			ResponsibilityBookModel rebkmodel = bllr.getResponsibilityBook(erm
					.getCid());
			if (rebkmodel.getRebk_id() != null) {
				if (rebkmodel.getRebk_limit() != null
						&& rebkmodel.getRebk_limit().equals("当年有效")) {
					if (rebkmodel.getRebk_signdate() != null) {
						Calendar can = Calendar.getInstance();
						int nowYear = can.YEAR;
						String[] date = rebkmodel.getRebk_signdate().split("-");
						if (date[0] != null) {
							String signYear = date[0];
							if (!signYear.equals(nowYear + ""))
								;
							{
								Messagebox.show("该公司就业登记责任书已到期，不能办理就业登记!",
										"ERROR", Messagebox.OK,
										Messagebox.ERROR);
								refreshUrl();
							}
						}
					}
				} else if (rebkmodel.getRebk_limit() != null
						&& rebkmodel.getRebk_limit().equals("一年有效")) {
					if (rebkmodel.getRebk_signdate() != null) {
						if (rebkmodel.getRebk_signdate() != null) {
							String[] date = rebkmodel.getRebk_signdate().split(
									"-");
							String signdate = "";
							String signyear = "", signmonth = "";
							if (date.length > 0) {
								signyear = date[0];
								signmonth = date[1];
								signdate = date[2];
							}

							Date nowdd = new Date();
							String nowyear = "", nowmonth = "", nowdate = "";
							String nowstr = datechange(nowdd);
							String[] nowdates = nowstr.split("-");
							if (nowdates.length > 0) {
								nowyear = nowdates[0];
								nowmonth = nowdates[1];
								nowdate = nowdates[2];
							}
							Integer k = 0;// 0表示有效
							Integer year = Integer.parseInt(nowyear)
									- Integer.parseInt(signyear);
							if (year <= 1)// 有效年
							{
								if (year == 1) {
									Integer month = Integer.parseInt(nowmonth)
											- Integer.parseInt(signmonth);
									if (month <= 0)// 有效月
									{
										Integer hdate = Integer
												.parseInt(nowdate)
												- Integer.parseInt(signdate);
										if (hdate <= 0) {
											k = 0;
										} else {
											k = 1;
										}
									} else {
										k = 1;
									}
								} else {
									k = 0;
								}
							} else {
								// 无效
								k = 1;
							}
							if (k ==1) {
								Messagebox.show("该公司就业登记责任书已到期，不能办理就业登记!", "ERROR",
										Messagebox.OK, Messagebox.ERROR);
								refreshUrl();
							}
						}

					}
				}
			}
		}
	}

	private void refreshUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshEmUrl("/EmReg/EmReg_List.zul");
	}

	// 初始化数据
	private void init() {
		if (erm.getErin_nowadd() == null || erm.getErin_nowadd().equals("")) {
			erm.setErin_nowadd(emml.getEmba_address());
		}
		if (compact_s_date == null) {
			if (emml.getEbco_incept_date() != null
					&& !emml.getEbco_incept_date().equals("")) {
				compact_s_date = getFormatDate(emml.getEbco_incept_date());
			}
		}
		if (compact_e_date == null) {
			if (emml.getEbco_maturity_date() != null
					&& !emml.getEbco_maturity_date().equals("")) {
				compact_e_date = getFormatDate(emml.getEbco_maturity_date());
			}
		}
		if (erm.getErin_computerid() == null
				|| erm.getErin_computerid().equals("")) {
			erm.setErin_computerid(emml.getEmsc_computerid());
		}
		if (erm.getErin_compact_kind() == null
				|| erm.getErin_compact_kind().equals("")) {
			erm.setErin_compact_kind(emml.getEbco_change());
		}
	}

	// 获取下拉列表
	public void getComboList() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setFolkList(new ListModelList<>(bll.getFolkList()));
			if (erm.getErin_folk() != null && !erm.getErin_folk().isEmpty()) {
				for (PubFolkModel m : folkList) {
					if (m.getPufo_name().equals(erm.getErin_folk())) {
						setFolkM(m);
						break;
					}
				}
			}
			folkList.add(0, new PubFolkModel());

			setEduList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='文化程度'")));
			if (erm.getErin_education() != null
					&& !erm.getErin_education().isEmpty()) {
				for (PubCodeConversionModel m : eduList) {
					if (m.getPcco_cn().equals(erm.getErin_education())) {
						setEduM(m);
						break;
					}
				}
			}
			eduList.add(0, new PubCodeConversionModel());

			setMarList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='婚姻状况'")));
			if (erm.getErin_marital() != null
					&& !erm.getErin_marital().isEmpty()) {
				for (PubCodeConversionModel m : marList) {
					if (m.getPcco_cn().equals(erm.getErin_marital())) {
						setMarM(m);
						break;
					}
				}
			}
			marList.add(0, new PubCodeConversionModel());

			setPartyList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='政治面貌'")));
			if (erm.getErin_party() != null && !erm.getErin_party().isEmpty()) {
				for (PubCodeConversionModel m : partyList) {
					if (m.getPcco_cn().equals(erm.getErin_party())) {
						setParM(m);
						break;
					}
				}
			}
			partyList.add(0, new PubCodeConversionModel());

			setHjList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='户籍类型'")));
			if (erm.getErin_hjtype() != null && !erm.getErin_hjtype().isEmpty()) {
				for (PubCodeConversionModel m : hjList) {
					if (m.getPcco_cn().equals(erm.getErin_hjtype())) {
						setHjM(m);
						break;
					}
				}
			}
			hjList.add(0, new PubCodeConversionModel());

			setTitleList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='职称'")));
			if (erm.getErin_title() != null && !erm.getErin_title().isEmpty()) {
				for (PubCodeConversionModel m : titleList) {
					if (m.getPcco_cn().equals(erm.getErin_title())) {
						setTitleM(m);
						break;
					}
				}
			}
			titleList.add(0, new PubCodeConversionModel());

			setSkillList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='职业技能等级'")));
			if (erm.getErin_skilllevel() != null
					&& !erm.getErin_skilllevel().isEmpty()) {
				for (PubCodeConversionModel m : skillList) {
					if (m.getPcco_cn().equals(erm.getErin_skilllevel())) {
						setSkillM(m);
						break;
					}
				}
			}
			skillList.add(0, new PubCodeConversionModel());

			setWcinList(new ListModelList<>(bll.getWcinList()));
			if (erm.getErin_wcin() != null && !erm.getErin_wcin().isEmpty()) {
				for (WorkClassInfoModel m : wcinList) {
					if (m.getWcin_name().equals(erm.getErin_wcin())) {
						setWcinM(m);
						break;
					}
				}
			}
			wcinList.add(0, new WorkClassInfoModel());

			setHousemodeList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='居住方式'")));
			if (erm.getErin_house_mode() != null
					&& erm.getErin_house_mode() != 0) {
				for (PubCodeConversionModel m : housemodeList) {
					if (m.getPcco_code().equals(erm.getErin_house_mode())) {
						setHousemodeM(m);
						break;
					}
				}
			}
			housemodeList.add(0, new PubCodeConversionModel());

			setHouseList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='住所类别'")));
			if (erm.getErin_house_class() != null
					&& erm.getErin_house_class() != 0) {
				for (PubCodeConversionModel m : houseList) {
					if (m.getPcco_code().equals(erm.getErin_house_class())) {
						setHouseM(m);
						break;
					}
				}
			}
			houseList.add(0, new PubCodeConversionModel());

			setComeszList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='来深居住事由'")));
			if (erm.getErin_come_sz_reason() != null
					&& erm.getErin_come_sz_reason() != 0) {
				for (PubCodeConversionModel m : comeszList) {
					if (m.getPcco_code().equals(erm.getErin_come_sz_reason())) {
						setComeszM(m);
						break;
					}
				}
			}
			comeszList.add(0, new PubCodeConversionModel());

			setCompactList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='合同性质'")));
			compactList.add(0, new PubCodeConversionModel());

			setCompactyearList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='合同年限'")));
			compactyearList.add(0, new PubCodeConversionModel());

			setHandList(new ListModelList<>(bll.getHandoverList()));
			erm.setErin_handover_people(handList.get(0).getHape_username());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("vis1")
	@NotifyChange({ "compactVis", "erm", "comyM" })
	public void vis1() {
		try {
			if (hjM.getPcco_cn().equals("本市城镇")) {
				setCompactVis(true);
			} else {
				setCompactVis(false);
				erm.setErin_compact_kind(null);
				setComyM(compactyearList.get(0));
			}
		} catch (Exception e) {

		}
	}

	/**
	 * @param win
	 *            窗口
	 * @param a
	 *            1、暂存 2、提交
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("a") Integer a) {
		EmReg_OperateBll obll = new EmReg_OperateBll();
		if (a == 1) {
			try {
				if (hjM.getPcco_cn() != null && !hjM.getPcco_cn().equals("")) {
					// 提交处理model的特殊字段
					fieldhandle(1);

					String[] str = obll.addfir(erm, erm.getEmba_name());
					if (str[0].equals("1")) {
						Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("请选择户籍!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else if (a == 2) {
			if (hjM.getPcco_cn() == null || hjM.getPcco_cn().isEmpty()) {
				Messagebox.show("请选择户籍类型!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (compact_s_date == null) {
				Messagebox.show("请输入劳动合同起始日期!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (compact_e_date == null) {
				Messagebox.show("请输入劳动合同结束日期!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (Messagebox.show("是否确认直接将申报数据提交至人事部处理?", "CONFIRM",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				try {
					// 提交处理model的特殊字段
					fieldhandle(2);

					String[] str = obll.add(erm, erm.getEmba_name());
					if (str[0].equals("1")) {

						Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						refreshUrl();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	/**
	 * 提交处理特殊字段
	 * 
	 */
	public void fieldhandle(Integer state) {
		erm.setOwnmonth(new SimpleDateFormat("yyyyMM").format(new Date()));
		erm.setErin_laststate(state - 1);
		erm.setErin_state(state);
		erm.setErin_tzl_state(0);
		erm.setErin_addname(UserInfo.getUsername());

		// 下拉列表
		erm.setErin_folkcode(folkM.getPufo_id() == null ? null : folkM
				.getPufo_id() + "");
		erm.setErin_folk(folkM.getPufo_name());
		erm.setErin_education(eduM.getPcco_cn());
		erm.setErin_educationcode(eduM.getPcco_code());
		erm.setErin_maritalcode(marM.getPcco_code());
		erm.setErin_marital(marM.getPcco_cn());
		erm.setErin_partycode(parM.getPcco_code());
		erm.setErin_party(parM.getPcco_cn());
		erm.setErin_hjtype(hjM.getPcco_cn());
		erm.setErin_hjtypecode(hjM.getPcco_code());
		erm.setErin_title(titleM.getPcco_cn());
		erm.setErin_titlecode(titleM.getPcco_code());
		erm.setErin_compact_ylimit(comyM.getPcco_code() == null ? null
				: Integer.parseInt(comyM.getPcco_code()));
		erm.setErin_is_parttime(is_parttime.equals("否") ? 0 : 1);
		erm.setErin_if_unlimited(if_unlimited.equals("否") ? 0 : 1);
		erm.setErin_if_resident_con(if_resident_con.equals("否") ? 0 : 1);
		erm.setErin_skilllevel(skillM.getPcco_cn());
		erm.setErin_skilllevelcode(skillM.getPcco_code());
		erm.setErin_come_sz_reason(comeszM.getPcco_code() == null ? null
				: Integer.parseInt(comeszM.getPcco_code()));
		erm.setErin_house_class(houseM.getPcco_code() == null ? null : Integer
				.parseInt(houseM.getPcco_code()));
		erm.setErin_house_mode(housemodeM.getPcco_code() == null ? null
				: Integer.parseInt(housemodeM.getPcco_code()));
		erm.setErin_wcin(wcinM.getWcin_name());
		erm.setErin_wcin_code(wcinM.getWcin_code());

		// 日期
		erm.setErin_compact_s_date(compact_s_date == null ? null : sdf
				.format(compact_s_date));
		erm.setErin_compact_e_date(compact_e_date == null ? null : sdf
				.format(compact_e_date));
		erm.setErin_worktime(worktime == null ? null : sdf.format(worktime));
		erm.setErin_unit_b_date(unit_b_date == null ? null : sdf
				.format(unit_b_date));
		erm.setErin_come_sz_date(come_sz_date == null ? null : sdf
				.format(come_sz_date));
		erm.setErin_r_date(r_date == null ? null : sdf.format(r_date));
	}

	public List<PubFolkModel> getFolkList() {
		return folkList;
	}

	public void setFolkList(List<PubFolkModel> folkList) {
		this.folkList = folkList;
	}

	public List<PubCodeConversionModel> getEduList() {
		return eduList;
	}

	public void setEduList(List<PubCodeConversionModel> eduList) {
		this.eduList = eduList;
	}

	public List<PubCodeConversionModel> getMarList() {
		return marList;
	}

	public void setMarList(List<PubCodeConversionModel> marList) {
		this.marList = marList;
	}

	public List<PubCodeConversionModel> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<PubCodeConversionModel> partyList) {
		this.partyList = partyList;
	}

	public List<PubCodeConversionModel> getHjList() {
		return hjList;
	}

	public void setHjList(List<PubCodeConversionModel> hjList) {
		this.hjList = hjList;
	}

	public List<PubCodeConversionModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<PubCodeConversionModel> titleList) {
		this.titleList = titleList;
	}

	public List<PubCodeConversionModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(List<PubCodeConversionModel> compactList) {
		this.compactList = compactList;
	}

	public List<PubCodeConversionModel> getCompactyearList() {
		return compactyearList;
	}

	public void setCompactyearList(List<PubCodeConversionModel> compactyearList) {
		this.compactyearList = compactyearList;
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public PubCodeConversionModel getEduM() {
		return eduM;
	}

	public void setEduM(PubCodeConversionModel eduM) {
		this.eduM = eduM;
	}

	public PubCodeConversionModel getMarM() {
		return marM;
	}

	public void setMarM(PubCodeConversionModel marM) {
		this.marM = marM;
	}

	public PubCodeConversionModel getParM() {
		return parM;
	}

	public void setParM(PubCodeConversionModel parM) {
		this.parM = parM;
	}

	public PubCodeConversionModel getHjM() {
		return hjM;
	}

	public void setHjM(PubCodeConversionModel hjM) {
		this.hjM = hjM;
	}

	public PubCodeConversionModel getTitleM() {
		return titleM;
	}

	public void setTitleM(PubCodeConversionModel titleM) {
		this.titleM = titleM;
	}

	public PubCodeConversionModel getComyM() {
		return comyM;
	}

	public void setComyM(PubCodeConversionModel comyM) {
		this.comyM = comyM;
	}

	public String getIs_parttime() {
		return is_parttime;
	}

	public void setIs_parttime(String is_parttime) {
		this.is_parttime = is_parttime;
	}

	public String getIf_unlimited() {
		return if_unlimited;
	}

	public void setIf_unlimited(String if_unlimited) {
		this.if_unlimited = if_unlimited;
	}

	public String getIf_resident_con() {
		return if_resident_con;
	}

	public void setIf_resident_con(String if_resident_con) {
		this.if_resident_con = if_resident_con;
	}

	public List<HandoverPeopleModel> getHandList() {
		return handList;
	}

	public void setHandList(List<HandoverPeopleModel> handList) {
		this.handList = handList;
	}

	public Date getCompact_s_date() {
		return compact_s_date;
	}

	public void setCompact_s_date(Date compact_s_date) {
		this.compact_s_date = compact_s_date;
	}

	public Date getCompact_e_date() {
		return compact_e_date;
	}

	public void setCompact_e_date(Date compact_e_date) {
		this.compact_e_date = compact_e_date;
	}

	public Date getWorktime() {
		return worktime;
	}

	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}

	public PubFolkModel getFolkM() {
		return folkM;
	}

	public void setFolkM(PubFolkModel folkM) {
		this.folkM = folkM;
	}

	public Date getUnit_b_date() {
		return unit_b_date;
	}

	public void setUnit_b_date(Date unit_b_date) {
		this.unit_b_date = unit_b_date;
	}

	public Date getCome_sz_date() {
		return come_sz_date;
	}

	public void setCome_sz_date(Date come_sz_date) {
		this.come_sz_date = come_sz_date;
	}

	public Date getR_date() {
		return r_date;
	}

	public void setR_date(Date r_date) {
		this.r_date = r_date;
	}

	public List<PubCodeConversionModel> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<PubCodeConversionModel> skillList) {
		this.skillList = skillList;
	}

	public PubCodeConversionModel getSkillM() {
		return skillM;
	}

	public void setSkillM(PubCodeConversionModel skillM) {
		this.skillM = skillM;
	}

	public List<WorkClassInfoModel> getWcinList() {
		return wcinList;
	}

	public void setWcinList(List<WorkClassInfoModel> wcinList) {
		this.wcinList = wcinList;
	}

	public WorkClassInfoModel getWcinM() {
		return wcinM;
	}

	public void setWcinM(WorkClassInfoModel wcinM) {
		this.wcinM = wcinM;
	}

	public List<PubCodeConversionModel> getComeszList() {
		return comeszList;
	}

	public void setComeszList(List<PubCodeConversionModel> comeszList) {
		this.comeszList = comeszList;
	}

	public PubCodeConversionModel getComeszM() {
		return comeszM;
	}

	public void setComeszM(PubCodeConversionModel comeszM) {
		this.comeszM = comeszM;
	}

	public List<PubCodeConversionModel> getHouseList() {
		return houseList;
	}

	public void setHouseList(List<PubCodeConversionModel> houseList) {
		this.houseList = houseList;
	}

	public PubCodeConversionModel getHouseM() {
		return houseM;
	}

	public void setHouseM(PubCodeConversionModel houseM) {
		this.houseM = houseM;
	}

	public List<PubCodeConversionModel> getHousemodeList() {
		return housemodeList;
	}

	public void setHousemodeList(List<PubCodeConversionModel> housemodeList) {
		this.housemodeList = housemodeList;
	}

	public PubCodeConversionModel getHousemodeM() {
		return housemodeM;
	}

	public void setHousemodeM(PubCodeConversionModel housemodeM) {
		this.housemodeM = housemodeM;
	}

	public boolean isCompactVis() {
		return compactVis;
	}

	public void setCompactVis(boolean compactVis) {
		this.compactVis = compactVis;
	}

	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public Date getFormatDate(String date) {
		Date datestr = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			datestr = df.parse(date);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return datestr;
	}

	private String getDateString(String datestr) {
		String dateStr = "";
		Date dates = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dates = df.parse(datestr);
			dateStr = df.format(dates);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return dateStr;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		if (d != null && !d.equals("")) {
			date = time.format(d);
		}
		return date;
	}
}
