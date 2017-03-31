package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmReg.EmReg_OperateDal;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;
import bll.EmResidencePermit.Emrp_ChangeBll;
import bll.EmResidencePermit.Emrp_ChangeSelectBll;

import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;
import Model.HandoverPeopleModel;
import Model.PubCodeConversionModel;
import Model.PubFolkModel;
import Model.WorkClassInfoModel;
import Util.UserInfo;

public class EmReg_ModController {

	List<EmRegistrationInfoModel> list = new ListModelList<>();
	Integer daid = 0;
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
	private List<PubCodeConversionModel> inszwayList;// 户口进入深圳方式
	private PubCodeConversionModel inszwayM = new PubCodeConversionModel();
	private Integer puz_id = 22;
	private String ul = "../EmReg/datalist.zul?pubid=12";

	// 特殊字段
	private String is_parttime;// 是否兼职
	private String if_unlimited;// 无固定劳动期限
	private String if_resident_con;// 是否添加居住证转换
	private Date compact_s_date;// 劳动合同开始日期
	private Date compact_e_date;// 劳动合同结束日期
	private Date worktime;// 参加工作时间
	private Date unit_b_date;// 本单位工作起始日期
	private Date come_sz_date;// 来深时间
	private Date r_date;// 入住时间
	private Date in_sz_date;// 户口进入深圳日期

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private boolean compactVis;
	private EmReg_ListBll bll = new EmReg_ListBll();
	private Date erin_getdata_date;// 客服交资料日期

	private boolean mustvis = true, szmustvis = false, nszmustvis = false;

	@SuppressWarnings("unchecked")
	public EmReg_ModController() {
		try {

			try {
				daid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("daid").toString());
			} catch (Exception e) {
				list = (List<EmRegistrationInfoModel>) Executions.getCurrent()
						.getArg().get("list");
				daid = list.get(0).getErin_id();
			}

			setErm(bll.getEmRegInfo(daid, ""));

			// 调用获取下拉列表方法
			getComboList();
			// 特殊字段初始化
			fieldinit();
			vis1();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("vis1")
	@NotifyChange({ "compactVis", "erm", "comyM", "inszwayM", "in_sz_date",
			"ul", "szmustvis", "nszmustvis" })
	public void vis1() {
		try {
			if (hjM.getPcco_cn() != null && hjM.getPcco_cn().equals("本市城镇")) {
				setCompactVis(true);
				szmustvis = true;
				nszmustvis = false;
				boolean flag = bll.ifUpdata(12, daid);
				if (flag) {
					ul = "../EmReg/DataUpdateList.zul?pubid=12&typeid=2&tid="
							+ daid;
				} else {
					ul = "../EmReg/datalist.zul?pubid=12&typeid=1";
				}
			} else {
				nszmustvis = true;
				szmustvis = false;
				setCompactVis(false);
				erm.setErin_compact_kind(null);
				setComyM(compactyearList.get(0));
				in_sz_date = null;
				erm.setErin_in_sz_remark(null);
				setInszwayM(inszwayList.get(0));
				boolean flag = bll.ifUpdata(22, daid);
				if (flag) {
					ul = "../EmReg/DataUpdateList.zul?pubid=22&typeid=2&tid="
							+ daid;
				} else {
					ul = "../EmReg/datalist.zul?pubid=22&typeid=1";
				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 特殊字段初始化
	 * 
	 */
	public void fieldinit() {
		try {
			is_parttime = erm.getErin_is_parttime() == 1 ? "是" : "否";
			if_unlimited = erm.getErin_if_unlimited() == 1 ? "是" : "否";
			if_resident_con = erm.getErin_if_resident_con() == 1 ? "是" : "否";
			compact_s_date = erm.getErin_compact_s_date() == null ? null : sdf
					.parse(erm.getErin_compact_s_date());
			compact_e_date = erm.getErin_compact_e_date() == null ? null : sdf
					.parse(erm.getErin_compact_e_date());
			worktime = erm.getErin_worktime() == null ? null : sdf.parse(erm
					.getErin_worktime());
			unit_b_date = erm.getErin_unit_b_date() == null ? null : sdf
					.parse(erm.getErin_unit_b_date());
			System.out.println("date=" + erm.getErin_unit_b_date());
			come_sz_date = erm.getErin_come_sz_date() == null ? null : sdf
					.parse(erm.getErin_come_sz_date());
			r_date = erm.getErin_r_date() == null ? null : sdf.parse(erm
					.getErin_r_date());
			in_sz_date = erm.getErin_in_sz_date() == null ? null : sdf
					.parse(erm.getErin_in_sz_date());

			if (erm.getErin_getdata_date() != null) {
				erin_getdata_date = sdf.parse(erm.getErin_getdata_date());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("初始化数据出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
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

			setInszwayList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='户口进入深圳方式' "
							+ "order by pcco_code")));
			if (erm.getErin_hj_in_sz_way() != null
					&& erm.getErin_hj_in_sz_way() != 0) {
				for (PubCodeConversionModel m : inszwayList) {
					if (m.getPcco_code()
							.equals(erm.getErin_hj_in_sz_way() + "")) {
						setInszwayM(m);
						break;
					}
				}
			}
			inszwayList.add(0, new PubCodeConversionModel());

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
					if (m.getPcco_code().equals(erm.getErin_house_mode() + "")) {
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
					if (m.getPcco_code().equals(erm.getErin_house_class() + "")) {
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
					if (m.getPcco_code().equals(
							erm.getErin_come_sz_reason() + "")) {
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
			if (erm.getErin_compact_ylimit() != null
					&& erm.getErin_compact_ylimit() != 0) {
				for (PubCodeConversionModel m : compactyearList) {
					if (m.getPcco_code().equals(
							erm.getErin_compact_ylimit() + "")) {
						setComyM(m);
						break;
					}
				}
			}
			compactyearList.add(0, new PubCodeConversionModel());

			setHandList(new ListModelList<>(bll.getHandoverList()));
		} catch (Exception e) {
			e.printStackTrace();
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
			@BindingParam("a") Integer a, @BindingParam("gd") Grid docGrid) {

		if (a == 1) {
			try {
				// 提交处理model的特殊字段
				fieldhandle(1);

				Integer id = new EmReg_OperateDal().mod(erm);
				if (id > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", daid);
					BindUtils.postGlobalCommand(null, null, "adddata", map);
					Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else if (a == 2) {
			String result = mustIsEmploy();
			if (result != null && !result.equals("0") && !result.equals("")) {
				Messagebox.show(result, "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				try {
					EmReg_OperateBll obll = new EmReg_OperateBll();

					// 提交处理model的特殊字段
					fieldhandle(2);

					String[] str = obll.readd(erm);
					if (str[0].equals("1")) {
						if (if_resident_con != null
								&& if_resident_con.equals("是")) {
							Integer erpi_id = bll.getEmResidencePermit(erm
									.getGid());// 查询是否有居住证信息
							if (erpi_id <= 0)// 如果没有居住证信息则先添加居住证信息
							{
								/********** 员工户籍如果是非深户就需要办理居住证，深户就不用办理居住证 *************/
								if (hjM != null && !hjM.equals("本市城镇")) {
									EmResidencePermitInfoModel pm = new EmResidencePermitInfoModel();
									pm.setGid(erm.getGid());
									pm.setCid(erm.getCid());
									pm.setOwnmonth((new SimpleDateFormat(
											"yyyyMM").format(new Date())));
									pm.setErin_id(daid);
									erpi_id = obll.EmResidencePermitInfoAdd(pm);
								}
							}
							if (hjM != null && !hjM.equals("本市城镇")) {
								// 查询是否有转换还没有完成的居住证转换信息
								Emrp_ChangeSelectBll chbll = new Emrp_ChangeSelectBll();
								List<EmResidencePermitChangeModel> chlist = chbll
										.getChangeList(" and erpc_state in(1,2)  and a.gid="
												+ erm.getGid());
								EmResidencePermitChangeModel m = new EmResidencePermitChangeModel();
								m.setGid(erm.getGid());
								m.setCid(erm.getCid());
								m.setOwnmonth(new SimpleDateFormat("yyyyMM")
										.format(new Date()));
								m.setErpc_idcard(erm.getErin_idcard());
								m.setErpc_laststate(0);
								m.setErpc_state(1);
								m.setErpc_addname(UserInfo.getUsername());
								new Emrp_ChangeBll().changeadd(m,
										erm.getEmba_name());
							}
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", daid);
						BindUtils.postGlobalCommand(null, null, "adddata", map);
						Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
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

	// 判断必填项
	private String mustIsEmploy() {
		String error_code = "0";
		/******************** 判断深户与非深户都必填项 *****************************/
		if (hjM.getPcco_cn() == null || hjM.getPcco_cn().isEmpty()) {
			error_code = "请选择户籍类型!";
		} else if (folkM == null || folkM.getPufo_name() == null
				|| folkM.getPufo_name().equals("")) {
			error_code = "请选择民族!";
		} else if (eduM == null || eduM.getPcco_cn() == null
				|| eduM.getPcco_cn().equals("")) {
			error_code = "请选择文化程度!";
		} else if (parM == null || parM.getPcco_cn() == null
				|| parM.getPcco_cn().equals("")) {
			error_code = "请选择政治面貌!";
		} else if (marM == null || marM.getPcco_cn() == null
				|| marM.getPcco_cn().equals("")) {
			error_code = "请选择婚姻状况!";
		} else if (erm.getErin_nowadd() == null
				|| erm.getErin_nowadd().equals("")) {
			error_code = "请选择现居住地址!";
		} else if (skillM == null || skillM.getPcco_cn() == null
				|| skillM.getPcco_cn().equals("")) {
			error_code = "请选择职业技能!";
		} else if (titleM == null || titleM.getPcco_cn() == null
				|| titleM.getPcco_cn().equals("")) {
			error_code = "请选择职称!";
		} else if (compact_s_date == null) {
			error_code = "请输入劳动合同起始日期!";
		} else if (compact_e_date == null) {
			error_code = "请输入劳动合同结束日期!";
		}
		/******************** 判断深户与非深户都必填项END *****************************/

		/******************** 判断深户必填项 *****************************/
		if (error_code == "0") {
			if (hjM.getPcco_cn() != null && hjM.getPcco_cn().equals("本市城镇")) {
				if (erm.getErin_hjadd() == null
						|| erm.getErin_hjadd().equals("")) {
					error_code = "请输入户籍地址!";
				}
			}
		}
		/******************** 判断深户必填项End *****************************/

		/******************** 判断非深户必填项 *****************************/
		if (error_code == "0") {
			if (hjM.getPcco_cn() != null && !hjM.getPcco_cn().equals("本市城镇")) {
				if (erm.getErin_address_number() == null
						|| erm.getErin_address_number().equals("")) {
					error_code = "请输入居住房屋地址编码!";
				} else if (come_sz_date == null) {
					error_code = "请选择来深时间!";
				} else if (erm.getErin_idcard_address() == null
						|| erm.getErin_idcard_address().equals("")) {
					error_code = "请输入身份证完整住址!";
				} else if (houseM == null || houseM.getPcco_cn() == null
						|| houseM.getPcco_cn().equals("")) {
					error_code = "请输入住所类型!";
				} else if (erm.getErin_s_place() == null
						|| erm.getErin_s_place().equals("")) {
					error_code = "请输服务处所!";
				} else if (housemodeM == null
						|| housemodeM.getPcco_cn() == null
						|| housemodeM.getPcco_cn().equals("")) {
					error_code = "请输选择居住方式!";
				} else if (r_date == null) {
					error_code = "请选择入住时间!";
				}
			}
		}
		/******************** 判断非深户必填项End *****************************/
		return error_code;
	}

	/**
	 * 提交处理特殊字段
	 * 
	 */
	public void fieldhandle(Integer state) {
		erm.setOwnmonth(new SimpleDateFormat("yyyyMM").format(new Date()));
		erm.setErin_state(state);
		erm.setErin_addname(UserInfo.getUsername());

		// 下拉列表
		erm.setErin_folkcode(folkM.getPufo_id() + "");
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
		erm.setErin_hj_in_sz_way(inszwayM.getPcco_code() == null ? null
				: Integer.parseInt(inszwayM.getPcco_code()));
		erm.setErin_is_parttime(is_parttime.equals("否") ? 0 : 1);
		erm.setErin_if_unlimited(if_unlimited.equals("否") ? 0 : 1);
		erm.setErin_if_resident_con(if_resident_con.equals("是") ? 1 : 0);
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
		erm.setErin_in_sz_date(in_sz_date == null ? null : sdf
				.format(in_sz_date));
		erm.setErin_getdata_date(erin_getdata_date == null ? null : sdf
				.format(erin_getdata_date));
	}

	/************* 弹出联系员工页面 ************************/
	@Command
	public void link() {
		Map map = new HashMap<>();
		map.put("id", daid);
		map.put("table", "EmRegistrationInfo");
		Window window = (Window) Executions.createComponents(
				"../EmReg/EmReg_Contact.zul", null, map);
		window.doModal();
	}

	// 弹出退回页面
	@Command
	public void back(@BindingParam("win") Window win) {
		Map map = new HashMap<>();
		map.put("daid", erm.getErin_id());
		map.put("flag", "1");
		Window window = (Window) Executions.createComponents(
				"../EmReg/EmReg_CenterBack.zul", null, map);
		window.doModal();
		if (map.get("flag").equals("2")) {
			win.detach();
		}

	}

	// 获取图像号
	@Command
	@NotifyChange("erm")
	public void getphotoNumber() {
		String photoNumber = Util.PhotoNumberUtil.getphotoNumber(erm
				.getErin_idcard());
		if (photoNumber != null && !photoNumber.equals("")) {
			erm.setErin_photo_number(photoNumber);
		} else {
			Messagebox.show("没有查到该员工的图像号", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public List<PubFolkModel> getFolkList() {
		return folkList;
	}

	public void setFolkList(List<PubFolkModel> folkList) {
		this.folkList = folkList;
	}

	public PubFolkModel getFolkM() {
		return folkM;
	}

	public void setFolkM(PubFolkModel folkM) {
		this.folkM = folkM;
	}

	public List<PubCodeConversionModel> getEduList() {
		return eduList;
	}

	public void setEduList(List<PubCodeConversionModel> eduList) {
		this.eduList = eduList;
	}

	public PubCodeConversionModel getEduM() {
		return eduM;
	}

	public void setEduM(PubCodeConversionModel eduM) {
		this.eduM = eduM;
	}

	public List<PubCodeConversionModel> getMarList() {
		return marList;
	}

	public void setMarList(List<PubCodeConversionModel> marList) {
		this.marList = marList;
	}

	public PubCodeConversionModel getMarM() {
		return marM;
	}

	public void setMarM(PubCodeConversionModel marM) {
		this.marM = marM;
	}

	public List<PubCodeConversionModel> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<PubCodeConversionModel> partyList) {
		this.partyList = partyList;
	}

	public PubCodeConversionModel getParM() {
		return parM;
	}

	public void setParM(PubCodeConversionModel parM) {
		this.parM = parM;
	}

	public List<PubCodeConversionModel> getHjList() {
		return hjList;
	}

	public void setHjList(List<PubCodeConversionModel> hjList) {
		this.hjList = hjList;
	}

	public PubCodeConversionModel getHjM() {
		return hjM;
	}

	public void setHjM(PubCodeConversionModel hjM) {
		this.hjM = hjM;
	}

	public List<PubCodeConversionModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<PubCodeConversionModel> titleList) {
		this.titleList = titleList;
	}

	public PubCodeConversionModel getTitleM() {
		return titleM;
	}

	public void setTitleM(PubCodeConversionModel titleM) {
		this.titleM = titleM;
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

	public PubCodeConversionModel getComyM() {
		return comyM;
	}

	public void setComyM(PubCodeConversionModel comyM) {
		this.comyM = comyM;
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

	public List<HandoverPeopleModel> getHandList() {
		return handList;
	}

	public void setHandList(List<HandoverPeopleModel> handList) {
		this.handList = handList;
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

	public boolean isCompactVis() {
		return compactVis;
	}

	public void setCompactVis(boolean compactVis) {
		this.compactVis = compactVis;
	}

	public List<PubCodeConversionModel> getInszwayList() {
		return inszwayList;
	}

	public void setInszwayList(List<PubCodeConversionModel> inszwayList) {
		this.inszwayList = inszwayList;
	}

	public Date getIn_sz_date() {
		return in_sz_date;
	}

	public void setIn_sz_date(Date in_sz_date) {
		this.in_sz_date = in_sz_date;
	}

	public PubCodeConversionModel getInszwayM() {
		return inszwayM;
	}

	public void setInszwayM(PubCodeConversionModel inszwayM) {
		this.inszwayM = inszwayM;
	}

	public Integer getPuz_id() {
		return puz_id;
	}

	public void setPuz_id(Integer puz_id) {
		this.puz_id = puz_id;
	}

	public String getUl() {
		return ul;
	}

	public void setUl(String ul) {
		this.ul = ul;
	}

	public Date getErin_getdata_date() {
		return erin_getdata_date;
	}

	public void setErin_getdata_date(Date erin_getdata_date) {
		this.erin_getdata_date = erin_getdata_date;
	}

	public boolean isMustvis() {
		return mustvis;
	}

	public void setMustvis(boolean mustvis) {
		this.mustvis = mustvis;
	}

	public boolean isSzmustvis() {
		return szmustvis;
	}

	public void setSzmustvis(boolean szmustvis) {
		this.szmustvis = szmustvis;
	}

	public boolean isNszmustvis() {
		return nszmustvis;
	}

	public void setNszmustvis(boolean nszmustvis) {
		this.nszmustvis = nszmustvis;
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
