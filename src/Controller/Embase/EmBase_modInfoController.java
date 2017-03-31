package Controller.Embase;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouseSetBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmbaseListBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.SystemControl.SystLogInfoBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoglistModel;
import Model.EmHouseCpp;
import Model.EmShebaoFormulaModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Model.SystLogModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.EmailUtil;
import Util.EntitiesComparedUtils;
import Util.IdcardUtil;
import Util.MonthListUtil;
import Util.RedirectUtil;
import Util.StringFormat;
import Util.TelUtil;
import Util.UserInfo;

public class EmBase_modInfoController {

	private Integer gid = (Integer) Executions.getCurrent().getArg().get("gid");
	private Integer cid = (Integer) Executions.getCurrent().getArg().get("cid");
	private Integer embaId = (Integer) Executions.getCurrent().getArg()
			.get("embaId");

	private EmbaseModel ebm = new EmbaseModel();
	private EmbaseModel ebmold = new EmbaseModel();

	private List<EmbaseModel> emList = new ListModelList<>();
	private List<CoglistModel> coglist = new ListModelList<>();

	// 模块显示控制
	private boolean sb = false; // 社保
	private boolean house = false; // 公积金
	private boolean sbbj = false;
	private boolean sbDoc = false;
	private boolean sbM1 = false;
	private boolean sbM2 = false;
	private boolean sbM3 = false;
	private boolean housebj = false;
	private boolean gjjDoc = false;
	private boolean savebl = true;

	/* 下拉列表绑定 */
	private List<PubCodeConversionModel> degreeList;
	private List<String> folkList;
	private List<PubCodeConversionModel> partyList;
	private List<EmShebaoFormulaModel> formulaList;
	private List<EmHouseCpp> cpList;
	private String[] ownmonthList; // 社保补缴所属月 update列表
	private String[] houseOwnmonthList;// 公积金补缴所属月

	private String[] feeownmonthList; // 社保补缴所属月
	private String[] housefeeOwnmonthList;// 公积金补缴所属月

	// 日期格式
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 特殊字段
	private Date birth = null;// 生日
	private Date graduation = null;// 毕业时间
	private PubCodeConversionModel dgModel = new PubCodeConversionModel();// 文化程度
	private String excompanystate = "";// 原单位是否已封存
	private EmShebaoFormulaModel formula;// 社保医疗类型信息
	private boolean sb_ifStop;
	private boolean house_ifStop;

	private String userId;
	private String username;

	private EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private EmbaseListBll embasebll = new EmbaseListBll();
	private String[] ownmonthListsb;
	private String[] ownmonthListgjj;

	// 窗体对象
	private Window win;
	// 智翼通接口传入值
	private EmZYTModel emztM;

	private Integer emba_emhb_ownmonth; // 公积金所属月
	private Integer emba_emsb_ownmonth; // 社保所属月

	private Integer sbfwownmonth; // 社保服务起始月
	private Integer sbsfownmonth; // 社保收费起始日

	private Integer gjjfwownmonth; // 公积金服务起始月
	private Integer gjjsfownmonth; // 公积金费起始日

	private Integer sbbjsownmonth; // 社保补缴服务起始月
	private Integer sbbjeownmonth; // 社保补缴终止始日

	private Integer gjjbjsownmonth; // 公积金补缴服务起始月
	private Integer gjjbjeownmonth; // 公积金补缴终止始日

	private Integer sbtzownmonth; // 社保台帐月
	private Integer gjjtzownmont; // 公积金台账月

	public EmBase_modInfoController() throws ParseException {

		ownmonthListsb = ownmonthListgjj = embasebll
				.getLastOwnmonthByNowf(true);

		setEmList(embaId);
		Calendar calendar = new GregorianCalendar();
		Date nowDate = new Date(); // 获取当前时间
		calendar.setTime(nowDate);

		userId = UserInfo.getUserid();
		username = UserInfo.getUsername();
		if (emList.size() > 0) {
			ebm = emList.get(0);
			ebmold = (EmbaseModel) ebm.clone();
			//
			if ("是".equals(ebm.getEmba_emsb_foreigner())) {
				setFormulaList(1);

				// setFormulaList(0);
			} else {
				setFormulaList(0);
				// setFormulaList(1);
			}
			for (EmShebaoFormulaModel m : formulaList) {
				if (m.getEmsf_title().equals(ebm.getEmba_formula())) {
					formula = m;
				}
			}
			setFolkList();
			// sbGjjOwnmonth(bll.getSbUpdateOwnmonth(),bll.houseOwnmonth());

			sbtzownmonth = bll.getSbUpdateOwnmonth();
			gjjtzownmont = bll.houseOwnmonth();

			if (ebm.getEmba_state() == 2) {
				if (!sbbj)// 补缴清除社保
				{

					ebm.setEmba_emsb_m1("0");
					ebm.setEmba_emsb_m2("0");
					ebm.setEmba_emsb_m3("0");
					ebm.setEmba_emsb_r1(0);
					ebm.setEmba_emsb_r2(0);
					ebm.setEmba_emsb_r3(0);
					ebm.setEmba_emsb_jlbj1(0);
					ebm.setEmba_emsb_jlbj2(0);
					ebm.setEmba_emsb_jlbj3(0);
					ebm.setEmba_modname(UserInfo.getUsername());
					Integer i = bll.modInfo(ebm);
				}

				if (!housebj) // 补缴清除公积金
				{
					ebm.setEmba_emhb_startdate("0");
					ebm.setEmba_emhb_stopdate("0");
					ebm.setEmba_emhb_total(BigDecimal.ZERO);
					ebm.setEmba_modname(UserInfo.getUsername());
					Integer i = bll.modInfo(ebm);
				}

			}

			sbbj();
			gjjbj();

			try {
				if (ebm.getEmba_birth() != null) {
					birth = sdf.parse(ebm.getEmba_birth());
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 获取智翼通接口转过来的数据
			try {
				emztM = (EmZYTModel) Executions.getCurrent().getArg()
						.get("emztM");
			} catch (Exception e) {
				emztM = null;
			}

			if (emztM != null) {
				BigDecimal b;
				try {// 社保基数
					b = new BigDecimal(emztM.getEmzt_ylradix());
					ebm.setEmba_sb_radix(b.setScale(2, BigDecimal.ROUND_DOWN));// 社保去小数位
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {// 住房公积金基数
					b = new BigDecimal(emztM.getEmzt_houseradix());
					ebm.setEmba_house_radix(b.setScale(2,
							BigDecimal.ROUND_HALF_UP));// 四舍五入
				} catch (Exception e) {
					// TODO: handle exception
				}

				if ("".equals(ebm.getEmba_formula())
						|| ebm.getEmba_formula() == null
						|| "NULL".equals(ebm.getEmba_formula())) {// 判断是否已经选择过社保模板
					// 获取社保模板
					EmZYT_SelectBll zytSbll = new EmZYT_SelectBll();
					if ("是".equals(ebm.getEmba_emsb_foreigner())) {
						ebm.setEmba_formula(zytSbll.getShebaoFormula(
								emztM.getEmzt_cityremark(), 1));
					} else {
						ebm.setEmba_formula(zytSbll.getShebaoFormula(
								emztM.getEmzt_cityremark(), 0));
					}

					for (EmShebaoFormulaModel m : formulaList) {
						if (m.getEmsf_title().equals(ebm.getEmba_formula())) {
							formula = m;
							ebm.setEmba_sb_hj(m.getEmsf_hj());
						}
					}
				}

			}
		}
	}

	@Command
	@NotifyChange({ "housebj", "ebm" })
	public void gjjbj() {
		// gjjtzownmont=gjjtzownmontp;
		// ebm.setEmba_emhb_ownmonth(gjjtzownmont.toString());

		int monthcheck;
		if (ebm.getEmba_emhb_ownmonth() != null) {
			gjjtzownmont = Integer.parseInt(ebm.getEmba_emhb_ownmonth());
		}
		
		if (Integer.valueOf(DateStringChange.getOwnmonth())>gjjtzownmont) {
			gjjtzownmont=Integer.valueOf(DateStringChange.getOwnmonth());
		}
		house_ifStop = sbll.gjjOnair(cid, gid, gjjtzownmont, null); // 公积金接单日

		gjjfwownmonth = bll.getownmonth(gid);
		gjjsfownmonth = bll.getownmonth2(gid);
		
		if (house_ifStop) {
			if (gjjfwownmonth>gjjtzownmont) {
				monthcheck=Integer.parseInt(DateStringChange
						.ownmonthAddoneMonth(gjjtzownmont.toString()));
			}else {
				monthcheck = Integer.parseInt(DateStringChange
					.ownmonthAddoneMonth(DateStringChange.getOwnmonth()));
			}
		} else {
			monthcheck = Integer.parseInt(DateStringChange.getOwnmonth());
		}
		// 初始化

		housebj = false;
		ebm.setEmba_emhb_feeownmonth("0");
		ebm.setEmba_emhb_startdate("0");
		ebm.setEmba_emhb_stopdate("0");

		gjjbjsownmonth = 0;
		gjjbjeownmonth = 0;

		

		if (gjjtzownmont > monthcheck) {
			emba_emhb_ownmonth = gjjtzownmont;
			gjjbjsownmonth = gjjfwownmonth;
			gjjbjeownmonth = Integer.valueOf(DateStringChange.ownmonthAdd(
					gjjtzownmont.toString(), -1));

		} else {

			if (house_ifStop) {
				if (gjjfwownmonth > Integer.parseInt(DateStringChange
						.getOwnmonth())) {
					// 参保:服务起始月
					emba_emhb_ownmonth = gjjfwownmonth;
				}

				else {
					// 参保:自然月+1
					emba_emhb_ownmonth = Integer.valueOf(DateStringChange
							.ownmonthAdd(DateStringChange.getOwnmonth(), 1));
					// 补缴:服务起始月--自然月

					gjjbjsownmonth = gjjfwownmonth;
					// 补缴结束月
					gjjbjeownmonth = Integer.parseInt(DateStringChange
							.getOwnmonth());

				}
			} else {
				if (gjjfwownmonth > Integer.parseInt(DateStringChange
						.getOwnmonth())) {
					// 参保:服务起始月
					emba_emhb_ownmonth = gjjfwownmonth;

				} else if (gjjfwownmonth == Integer.parseInt(DateStringChange
						.getOwnmonth())) {
					// 参保:服务起始月
					emba_emhb_ownmonth = gjjfwownmonth;

				} else {
					// 参保:自然月
					// 补缴:服务起始月--自然月-1

					emba_emhb_ownmonth = Integer.parseInt(DateStringChange
							.getOwnmonth());
					gjjbjsownmonth = gjjfwownmonth;
					gjjbjeownmonth = Integer.valueOf(DateStringChange
							.ownmonthAdd(DateStringChange.getOwnmonth(), -1));

				}

			}

		}

		// 公积金
		if (gjjfwownmonth != null && gjjfwownmonth > 0) {
			if (ebm.getEmba_house_place() != null) {
				house = ebm.getEmba_house_place().equals("本地") ? true : false;
				if (house) {
					ebm.setEmba_emhb_ownmonth(emba_emhb_ownmonth.toString());
					if (gjjbjsownmonth != null && gjjbjsownmonth > 0
							&& gjjbjsownmonth <= gjjbjeownmonth) {

						housebj = true;
						ebm.setEmba_emhb_feeownmonth(gjjsfownmonth.toString());
						ebm.setEmba_emhb_startdate(gjjbjsownmonth.toString());
						ebm.setEmba_emhb_stopdate(gjjbjeownmonth.toString());
						setCoglist(gid, "公积金");
						/*
						 * if (coglist.size() > 0) { if
						 * (coglist.get(0).getCoco_house().equals("独立开户")) {
						 * gjjDoc = true; } }
						 */
					} else {
						housebj = false;
						ebm.setEmba_emhb_feeownmonth("0");
						ebm.setEmba_emhb_startdate("0");
						ebm.setEmba_emhb_stopdate("0");

					}
					setCpList(cid, gid, emba_emhb_ownmonth);
					if (cpList.size() > 0) {
						if (ebm.getEmba_house_cpp() == null
								|| ebm.getEmba_house_cpp().equals("")) {
							ebm.setEmba_house_cpp(cpList.get(0).getCp());
						}
					}
					sumhbTotal();
				}

			}

		}
	}

	@Command
	@NotifyChange({ "sbbj", "sbM1", "sbM2", "sbM3", "ebm", "savebl" })
	public void sbbj() {

		Emsi_SelBll sbbll = new Emsi_SelBll();
		if (ebm.getEmba_state() == 5) {
			savebl = false;
		}

		if (ebm.getEmba_emsb_ownmonth() != null) {
			sbtzownmonth = Integer.parseInt(ebm.getEmba_emsb_ownmonth());
		}
		// sbtzownmonth=sbtzownmonthp;
		// ebm.setEmba_emsb_ownmonth(sbtzownmonth.toString());
		sbbjsownmonth = 0;
		sbbjeownmonth = 0;
		sbbj = false;
		setSbM1(false);
		setSbM2(false);
		setSbM3(false);

		if (ebm.getEmba_state() == 2) {

			ebm.setEmba_emsb_m1("0");
			ebm.setEmba_emsb_m2("0");
			ebm.setEmba_emsb_m3("0");
			ebm.setEmba_emsb_r1(0);
			ebm.setEmba_emsb_r2(0);
			ebm.setEmba_emsb_r3(0);
			ebm.setEmba_emsb_jlbj1(0);
			ebm.setEmba_emsb_jlbj2(0);
			ebm.setEmba_emsb_jlbj3(0);
		}
		sb_ifStop = sbbll.ifStop(); // 社保截单日

		sbfwownmonth = bll.getsbownmonth(gid);
		sbsfownmonth = bll.getsbownmonth2(gid);

		int monthcheck;
		if (sb_ifStop) {
			monthcheck = Integer.parseInt(DateStringChange
					.ownmonthAddoneMonth(DateStringChange.getOwnmonth()));
		} else {
			monthcheck = Integer.parseInt(DateStringChange.getOwnmonth());
		}

		if (sbtzownmonth > monthcheck) {
			emba_emsb_ownmonth = sbtzownmonth;
			sbbjsownmonth = sbfwownmonth;
			sbbjeownmonth = Integer.valueOf(DateStringChange.ownmonthAdd(
					sbtzownmonth.toString(), -1));

		} else {
			if (sb_ifStop) {
				if (sbfwownmonth > Integer.parseInt(DateStringChange
						.getOwnmonth())) {
					// 参保:服务起始月
					emba_emsb_ownmonth = sbfwownmonth;
				} else {
					// 参保:自然月+1
					emba_emsb_ownmonth = Integer.valueOf(DateStringChange
							.ownmonthAdd(DateStringChange.getOwnmonth(), 1));
					// 补缴:服务起始月--自然月

					sbbjsownmonth = sbfwownmonth;
					// 补缴结束月
					sbbjeownmonth = Integer.parseInt(DateStringChange
							.getOwnmonth());

				}
			} else {
				if (sbfwownmonth > Integer.parseInt(DateStringChange
						.getOwnmonth())) {
					// 参保:服务起始月
					emba_emsb_ownmonth = sbfwownmonth;

				} else if (sbfwownmonth == Integer.parseInt(DateStringChange
						.getOwnmonth())) {
					// 参保:服务起始月
					emba_emsb_ownmonth = sbfwownmonth;

				} else {
					// 参保:自然月
					// 补缴:服务起始月--自然月-1

					emba_emsb_ownmonth = Integer.parseInt(DateStringChange
							.getOwnmonth());

					sbbjsownmonth = sbfwownmonth;

					sbbjeownmonth = Integer.valueOf(DateStringChange
							.ownmonthAdd(DateStringChange.getOwnmonth(), -1));

				}

			}
		}

		// 社保
		if (sbfwownmonth != null && sbfwownmonth > 0) {

			if (ebm.getEmba_sb_place() != null) {
				sb = ebm.getEmba_sb_place().equals("本地") ? true : false;
				ebm.setEmba_emsb_ownmonth(emba_emsb_ownmonth.toString());

				if (sb) {

					if (sbbjsownmonth != null && sbbjsownmonth > 0) {
						sbbj = true;
						ebm.setEmba_emsb_feeownmonth(sbsfownmonth.toString());

						if (DateUtil.datediff(sbbjeownmonth.toString(),
								sbbjsownmonth.toString(), "M") >= 3) {
							sbbjsownmonth = Integer.valueOf(DateStringChange
									.ownmonthAdd(sbbjeownmonth.toString(), -2));

						}

						ebm.setEmba_emsb_m1(sbbjsownmonth.toString());
						setSbM1(true);

						if (sbbjeownmonth
								- Integer.valueOf(DateStringChange.ownmonthAdd(
										sbbjsownmonth.toString(), 1)) >= 0) {
							ebm.setEmba_emsb_m2(DateStringChange.ownmonthAdd(
									sbbjsownmonth.toString(), 1));
							setSbM2(true);
						}
						if (sbbjeownmonth
								- Integer.valueOf(DateStringChange.ownmonthAdd(
										sbbjsownmonth.toString(), 2)) >= 0) {
							ebm.setEmba_emsb_m3(DateStringChange.ownmonthAdd(
									sbbjsownmonth.toString(), 2));
							setSbM3(true);
						}

						setCoglist(gid, "社保");
						/*
						 * if (coglist.size() > 0) { if
						 * (coglist.get(0).getCoco_shebao().equals("独立开户")) {
						 * sbDoc = true; } }
						 */

					} else {
						sbbj = false;
						setSbM1(false);
						setSbM2(false);
						setSbM3(false);

						ebm.setEmba_emsb_m1("0");
						ebm.setEmba_emsb_m2("0");
						ebm.setEmba_emsb_m3("0");
						ebm.setEmba_emsb_r1(0);
						ebm.setEmba_emsb_r2(0);
						ebm.setEmba_emsb_r3(0);
						ebm.setEmba_emsb_jlbj1(0);
						ebm.setEmba_emsb_jlbj2(0);
						ebm.setEmba_emsb_jlbj3(0);

					}
				}
			}
		}

	}

	//
	// // 计算社保公积金所属月份及补缴月份,
	// public void sbGjjOwnmonth(Integer sbtzownmonthp,Integer gjjtzownmontp) {
	// //sbtzownmonth = bll.getSbUpdateOwnmonth();// 台帐所属月
	//
	//
	//
	//
	//
	//
	// }

	@Command
	public void winInfo(@BindingParam("a") Window winD) {
		win = winD;
	}

	/**
	 * 检查邮箱格式
	 * 
	 * @param email
	 */
	@Command
	@NotifyChange({ "ebm" })
	public void checkEmailSimple(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmailSimple(email)) {
					Messagebox.show("邮箱格式错误!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					ebm.setEmba_email(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查邮箱真实性
	 * 
	 * @param email
	 */
	@Command
	public void checkEmail(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmail(email)) {
					Messagebox.show("邮箱不存在!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Messagebox.show("邮箱真实存在!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证固定电话
	 * 
	 */
	@Command
	@NotifyChange({ "ebm" })
	public void checkPhone() {
		try {
			if (ebm.getEmba_phone() != null && !ebm.getEmba_phone().isEmpty()) {
				if (!TelUtil.isPhone(ebm.getEmba_phone())) {
					Messagebox.show("家庭电话格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					ebm.setEmba_phone(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证手机号码
	 * 
	 */
	@Command
	@NotifyChange({ "ebm" })
	public void checkMobile() {
		try {
			if (ebm.getEmba_mobile() != null && !ebm.getEmba_mobile().isEmpty()) {
				if (!TelUtil.isMobile(ebm.getEmba_mobile())) {
					Messagebox.show("手机号码格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					ebm.setEmba_mobile(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void computerid_search() {
		try {
			if (ebm.getEmba_name() == null || ebm.getEmba_name().isEmpty()) {
				Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (ebm.getEmba_idcard() == null
					|| ebm.getEmba_idcard().isEmpty()) {
				Messagebox.show("请输入身份证号码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (!IdcardUtil.validateCard(ebm.getEmba_idcard())) {
				Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String url = "/Embase/Embase_Computerid_search.zul";
				String searurl = "http://dgciic:81/ComputeridSearch.aspx?idcard="
						+ ebm.getEmba_idcard();
				Map<String, Object> map = new HashMap<>();
				map.put("url", searurl);

				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 通过身份证获取生日，省份，性别等信息
	 * 
	 */
	@Command
	@NotifyChange({ "ebm", "birth" })
	public void idcardhandle() {
		try {

			if (ebm.getEmba_idcardclass().equals("身份证")) {
				if (ebm.getEmba_idcard() != null
						&& !ebm.getEmba_idcard().isEmpty()) {

					// 检查身份证号码合法性
					if (IdcardUtil.validateCard(ebm.getEmba_idcard())) {
						String idCard = ebm.getEmba_idcard();

						birth = DateStringChange.StringtoDate(IdcardUtil
								.strtodateformat(IdcardUtil
										.getBirthByIdCard(idCard)),
								"yyyy-MM-dd");
						ebm.setEmba_sex(IdcardUtil.getGenderByIdCard(idCard));
						if (ebm.getEmba_native() == null
								|| ebm.getEmba_native().isEmpty()) {
							ebm.setEmba_native(IdcardUtil
									.getProvinceByIdCard(idCard));
						}
					} else {
						Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 模板选择
	@Command("selFormula")
	@NotifyChange("ebm")
	public void selFormula(@BindingParam("mod") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			this.formula = (EmShebaoFormulaModel) cb.getSelectedItem()
					.getValue();
			ebm.setEmba_sb_hj(((EmShebaoFormulaModel) cb.getSelectedItem()
					.getValue()).getEmsf_hj());

		}
	}

	@Command
	@NotifyChange("ebm")
	public void sumhbTotal() {
		if (ebm.getEmba_emhb_radix() != null) {

			Double radix = ebm.getEmba_emhb_radix().doubleValue();
			BigDecimal total = new BigDecimal(radix
					* ebm.getEmba_house_cpp()
					* 2
					* (DateUtil.datediff(ebm.getEmba_emhb_stopdate(),
							ebm.getEmba_emhb_startdate(), "M") + 1));
			ebm.setEmba_emhb_total(total.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
	}

	@Command
	public void submit() {
		Datebox dbbirth = (Datebox) win.getFellow("birth");
		if (dbbirth.getValue() != null) {
			ebm.setEmba_birth(sdf.format(dbbirth.getValue()));
		}

		if (ebm.getEmba_idcard() == null || ebm.getEmba_idcard().equals("")) {
			Messagebox.show("请输入身份证", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (ebm.getEmba_idcardclass().equals("身份证")) {
				if (ebm.getEmba_idcard() != null
						&& !ebm.getEmba_idcard().isEmpty()) {
					// 检查身份证号码合法性
					if (!IdcardUtil.validateCard(ebm.getEmba_idcard())) {
						Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
				} else {
					Messagebox.show("身份证加载错误,请联系IT部!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
			if (bll.haveIDcard(ebm.getCid(), ebm.getGid(),
					ebm.getEmba_idcard(), ebm.getEmba_idcardclass())) {
				Messagebox.show("身份证已存在!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

		}

		if (sb) {
			if (ebm.getEmba_sb_radix() != null) {
				if (ebm.getEmba_sb_radix().compareTo(new BigDecimal(99999)) == 1) {
					Messagebox.show("社保基数大于等于10万.不能提交", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox.show("请录入社保基数", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		if (sbbj) {
			if (sbM1) {
				if (ebm.getEmba_emsb_r1() == null
						|| ebm.getEmba_emsb_r1().equals("") || ebm.getEmba_emsb_r1().equals(0)) {
					Messagebox.show("请录入社保补缴基数", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
			if (sbM2) {
				if (ebm.getEmba_emsb_r2() == null
						|| ebm.getEmba_emsb_r2().equals("") || ebm.getEmba_emsb_r2().equals(0)) {
					Messagebox.show("请录入社保补缴基数", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
			if (sbM3) {
				if (ebm.getEmba_emsb_r3() == null
						|| ebm.getEmba_emsb_r3().equals("") || ebm.getEmba_emsb_r3().equals(0)) {
					Messagebox.show("请录入社保补缴基数", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
		}
		if (house) {
			if (ebm.getEmba_house_radix() != null
					&& !ebm.getEmba_house_radix().equals("")) {
			} else {
				Messagebox.show("请录入公积金基数", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ebm.getEmba_houseid() != null
					&& !ebm.getEmba_houseid().equals("")) {
				ebm.setEmba_houseid(StringFormat.replaceSpace(ebm
						.getEmba_houseid()));
				if (ebm.getEmba_houseid().length() != 11) {
					Messagebox.show("公积金账号是11位数字!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
		}

		if (housebj) {
			if (ebm.getEmba_emhb_ownmonth() == null
					|| ebm.getEmba_emhb_ownmonth().equals("")) {
				Messagebox.show("请录入公积金补缴月份", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ebm.getEmba_emhb_feeownmonth() == null
					|| ebm.getEmba_emhb_feeownmonth().equals("")) {
				Messagebox.show("请录入公积金补缴收费月份", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			/*
			 * if (ebm.getEmba_houseid() == null ||
			 * ebm.getEmba_houseid().equals("")) {
			 * Messagebox.show("请录入公积金补缴个人账号", "ERROR", Messagebox.OK,
			 * Messagebox.ERROR); return; }
			 */
			if (ebm.getEmba_emhb_startdate() == null
					|| ebm.getEmba_emhb_startdate().equals("")) {
				Messagebox.show("请录入公积金补缴起始月", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ebm.getEmba_emhb_stopdate() == null
					|| ebm.getEmba_emhb_stopdate().equals("")) {
				Messagebox.show("请录入公积金补缴终止月", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (ebm.getEmba_emhb_reason() == null
					|| ebm.getEmba_emhb_reason().equals("")) {
				Messagebox.show("请录入公积金补缴原因", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ebm.getEmba_emhb_radix() == null
					|| ebm.getEmba_emhb_radix().equals("")) {
				Messagebox.show("请录入公积金补缴基数", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ebm.getEmba_house_cpp() == null
					|| ebm.getEmba_house_cpp().equals("")) {
				Messagebox.show("请录入公积金补缴比例", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (ebm.getEmba_emhb_total() == null
					|| ebm.getEmba_emhb_total().equals("")) {
				Messagebox.show("请录入公积金补缴金额", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		ebm.setEmba_modname(UserInfo.getUsername());
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							/*
							 * Grid gd1 = (Grid) win.getFellow("docsbGrid");
							 * Grid gd2 = (Grid) win.getFellow("docgjjGrid");
							 * 
							 * if (sbDoc) { DocumentsInfo_OperationController
							 * sbdoc = new DocumentsInfo_OperationController();
							 * sbdoc.deleteDoc("94", ebm.getEmba_id()
							 * .toString()); sbdoc.AddsubmitDoc(gd1,
							 * ebm.getEmba_id() .toString()); } if (gjjDoc) {
							 * DocumentsInfo_OperationController housedoc = new
							 * DocumentsInfo_OperationController();
							 * housedoc.deleteDoc("95", ebm.getEmba_id()
							 * .toString()); housedoc.AddsubmitDoc(gd1,
							 * ebm.getEmba_id() .toString()); }
							 */
							Integer i = bll.modInfo(ebm);
							if (i > 0) {

								SystLogModel logm = new SystLogModel();
								SystLogInfoBll logBll = new SystLogInfoBll();
								String strlog = "";
								try {
									strlog = EntitiesComparedUtils
											.OldToNewReflect(ebmold, ebm);

								} catch (NoSuchMethodException
										| IllegalAccessException
										| IllegalArgumentException
										| InvocationTargetException e) {
									e.printStackTrace();
								}
								logm.setContent(strlog); // 修改内容或新增内容
								logm.setAddname(UserInfo.getUsername());
								// m.setIP(ip);
								logm.setGID("");
								logm.setClass1("员工信息");
								logBll.addSystLog(logm);

								if (sb) {
									// 判断该身份证号或电脑号下是否已存在社保在册数据
									Emsi_SelBll sbBll = new Emsi_SelBll();
									String[] chkStr = sbBll.chkIfShebao(gid);
									if (chkStr != null) {
										Messagebox.show("提交失败，该员工在另一家公司-("
												+ chkStr[1] + ")" + chkStr[2]
												+ "-存在在册社保数据，请先操作停交再操作社保！",
												"ERROR", Messagebox.OK,
												Messagebox.ERROR);
										return;
									}
								}
								
								System.out.println(12345678);
								
								Integer j = bll.createInfo(ebm, formula);

								if (j > 0) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);

								} else {
									Messagebox.show("社保公积金数据异常,请联系IT处理.",
											"操作提示", Messagebox.OK,
											Messagebox.ERROR);
								}
								/*
								 * Messagebox.show("操作成功!", "操作提示",
								 * Messagebox.OK, Messagebox.INFORMATION);
								 */
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}

						}
					}
				});

	}

	public boolean isSb_ifStop() {
		return sb_ifStop;
	}

	public void setSb_ifStop(boolean sb_ifStop) {
		this.sb_ifStop = sb_ifStop;
	}

	public EmbaseModel getEbm() {
		return ebm;
	}

	public void setEbm(EmbaseModel ebm) {
		this.ebm = ebm;
	}

	public List<EmbaseModel> getEmList() {
		return emList;
	}

	public void setEmList(Integer id) {
		this.emList = bll.embaseinfo(id);
	}

	public List<PubCodeConversionModel> getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(List<PubCodeConversionModel> degreeList) {
		this.degreeList = degreeList;
	}

	public List<String> getFolkList() {
		return folkList;
	}

	public void setFolkList() {
		this.folkList = bll.getFolkList();
	}

	public List<PubCodeConversionModel> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<PubCodeConversionModel> partyList) {
		this.partyList = partyList;
	}

	public Date getGraduation() {
		return graduation;
	}

	public void setGraduation(Date graduation) {
		this.graduation = graduation;
	}

	public PubCodeConversionModel getDgModel() {
		return dgModel;
	}

	public void setDgModel(PubCodeConversionModel dgModel) {
		this.dgModel = dgModel;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getExcompanystate() {
		return excompanystate;
	}

	public void setExcompanystate(String excompanystate) {
		this.excompanystate = excompanystate;
	}

	public boolean isSb() {
		return sb;
	}

	public void setSb(boolean sb) {
		this.sb = sb;
	}

	public boolean isHouse() {
		return house;
	}

	public void setHouse(boolean house) {
		this.house = house;
	}

	public List<EmShebaoFormulaModel> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(int ifForeign) {
		this.formulaList = bll.getFormula(ifForeign);
	}

	public EmShebaoFormulaModel getFormula() {
		return formula;
	}

	public void setFormula(EmShebaoFormulaModel formula) {
		this.formula = formula;
	}

	public List<EmHouseCpp> getCpList() {
		return cpList;
	}

	public void setCpList(Integer cid, Integer gid, Integer ownmonth) {
		this.cpList = bll.getCpp(cid, gid, ownmonth);
	}

	public List<CoglistModel> getCoglist() {
		return coglist;
	}

	public void setCoglist(Integer gid, String type) {
		this.coglist = bll.cgInfo(gid, type);
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(String[] ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public boolean isSbbj() {
		return sbbj;
	}

	public void setSbbj(boolean sbbj) {
		this.sbbj = sbbj;
	}

	public boolean isSbM1() {
		return sbM1;
	}

	public void setSbM1(boolean sbM1) {
		this.sbM1 = sbM1;
	}

	public boolean isSbM2() {
		return sbM2;
	}

	public void setSbM2(boolean sbM2) {
		this.sbM2 = sbM2;
	}

	public boolean isSbM3() {
		return sbM3;
	}

	public boolean isSavebl() {
		return savebl;
	}

	public void setSavebl(boolean savebl) {
		this.savebl = savebl;
	}

	public void setSbM3(boolean sbM3) {
		this.sbM3 = sbM3;
	}

	public boolean isHousebj() {
		return housebj;
	}

	public void setHousebj(boolean housebj) {
		this.housebj = housebj;
	}

	public String[] getHouseOwnmonthList() {
		return houseOwnmonthList;
	}

	public void setHouseOwnmonthList(String[] houseOwnmonthList) {
		this.houseOwnmonthList = houseOwnmonthList;
	}

	public String[] getFeeownmonthList() {
		return feeownmonthList;
	}

	public void setFeeownmonthList(String[] feeownmonthList) {
		this.feeownmonthList = feeownmonthList;
	}

	public String[] getHousefeeOwnmonthList() {
		return housefeeOwnmonthList;
	}

	public void setHousefeeOwnmonthList(String[] housefeeOwnmonthList) {
		this.housefeeOwnmonthList = housefeeOwnmonthList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSbDoc() {
		return sbDoc;
	}

	public void setSbDoc(boolean sbDoc) {
		this.sbDoc = sbDoc;
	}

	public boolean isGjjDoc() {
		return gjjDoc;
	}

	public void setGjjDoc(boolean gjjDoc) {
		this.gjjDoc = gjjDoc;
	}

	public Integer getSbsfownmonth() {
		return sbsfownmonth;
	}

	public void setSbsfownmonth(Integer sbsfownmonth) {
		this.sbsfownmonth = sbsfownmonth;
	}

	public String[] getOwnmonthListsb() {
		return ownmonthListsb;
	}

	public void setOwnmonthListsb(String[] ownmonthListsb) {
		this.ownmonthListsb = ownmonthListsb;
	}

	public String[] getOwnmonthListgjj() {
		return ownmonthListgjj;
	}

	public void setOwnmonthListgjj(String[] ownmonthListgjj) {
		this.ownmonthListgjj = ownmonthListgjj;
	}

	public Integer getSbtzownmonth() {
		return sbtzownmonth;
	}

	public void setSbtzownmonth(Integer sbtzownmonth) {
		this.sbtzownmonth = sbtzownmonth;
	}

	public Integer getGjjtzownmont() {
		return gjjtzownmont;
	}

	public void setGjjtzownmont(Integer gjjtzownmont) {
		this.gjjtzownmont = gjjtzownmont;
	}

}
