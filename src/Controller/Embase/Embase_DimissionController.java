package Controller.Embase;

import impl.MessageImpl;
import impl.PubEamilImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Util.DateUtil;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dal.LoginDal;
import dal.EmSheBao.Emsi_OperateDal;

import service.MessageService;
import service.PubEmailService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.CI_Insurant_ListModel;
import Model.CoOfferListModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmRegistrationInfoModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoUpdateModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.PubEmailModel;
import Model.SMSModel;
import Model.SysMessageModel;
import Model.SystLogModel;
import Util.DateStringChange;
import Util.EntitiesComparedUtils;
import Util.FileOperate;
import Util.Log4jInit;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.Archives.Archive_addBll;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBaseCompact.EmBaseCompact_AddBll;
import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBJImpl;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_StopBll;
import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.EmZYT.EmZYT_OperateBll;
import bll.Embase.EmbaseListBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.Embase.Embase_DimissionBll;
import bll.SmsMessage.Sms_GroupSendBll;
import bll.SystemControl.SystLogInfoBll;

public class Embase_DimissionController {
	private EmbaseListBll eBll = new EmbaseListBll();
	private Embase_DimissionBll dBll = new Embase_DimissionBll();

	private DateStringChange dsc = new DateStringChange();
	private List<CoOfferListModel> cflList = new ListModelList<CoOfferListModel>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	private DateUtil du = new DateUtil();

	private String outreason = "";

	private EmbaseModel ebmold = new EmbaseModel();

	private EmbaseModel emM;
	private Integer gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	// 获取用户名
	String username = UserInfo.getUsername();
	private Media media;

	private List<Integer> cgliId = dBll.getCgliId(gid);// 获取报价单数据
	private boolean ifCflsit = false;// 报价单列表显示状态

	private boolean ifshebao = false;// 社会保险显示状态
	private Emsi_SelBll sbBll = new Emsi_SelBll();
	private boolean ifsbaud;
	private boolean ifsbStop;
	private boolean sbaud;
	private String[] sbownmonthList;
	private EmShebaoUpdateModel sbModel;
	private String sbStopReason;

	private boolean ifshebaobj = false;// 社保补交显示状态
	private Emsc_DeclareSelBll sbbjBll = new Emsc_DeclareSelBll();
	Emsc_DeclareOperateBll sbbjObll = new Emsc_DeclareOperateBll();
	private List<EmShebaoBJModel> bjList;
	private String[] sbbjownmonthList;

	private boolean ifgjj = false;// 住房公积金显示状态
	private EmHouse_StopBll gjjBll = new EmHouse_StopBll();
	private EmHouseSetBll gjjSBll = new EmHouseSetBll();
	private List<String> gjjownmonthList = new ArrayList<>();
	private List<EmHouseUpdateModel> gjjList = new ListModelList<>();
	private EmHouseChangeModel gjjModel = new EmHouseChangeModel();
	private boolean gjjJudge;

	private boolean ifgjjbj = false;// 住房公积金补交显示状态
	private String[] gjjbjownmonthList;

	private boolean ifwt = false;// 委托外地显示状态
	private EmCommissionOutListBll wtSBll = new EmCommissionOutListBll();
	private List<EmCommissionOutModel> emoutList = new ListModelList<>();
	private EmCommissionOutModel wtm = new EmCommissionOutModel();
	private EmCommissionOutChangeModel wtcm = new EmCommissionOutChangeModel();
	private List<EmCommissionOutFeeDetailChangeModel> cfeeList = new ListModelList<>();
	private List<String> causeList = new ListModelList<>();
	SimpleDateFormat wtsdf = new SimpleDateFormat("yyyy-MM-dd");
	BigDecimal zero = BigDecimal.ZERO;

	private boolean ifshangbao = false;// 商保显示状态
	private CI_Insurant_ListBll shangbaoBll = new CI_Insurant_ListBll();
	private CI_Insurant_OperateBll shangbaoOBll = new CI_Insurant_OperateBll();
	private ListModelList<CI_Insurant_ListModel> ci_insurant_castsortout;// 显示商保在保类型

	private boolean ifda = false;// 档案显示状态
	private Archive_addBll daABll = new Archive_addBll();
	private EmArchive_SelectBll daEASBll = new EmArchive_SelectBll();
	private List<EmbaseModel> daEmbalist = new ListModelList<EmbaseModel>();
	private List<EmArchiveModel> fidlist;
	private String daContent = "";
	private String archiveType = "";
	private boolean da_chk = false;

	private boolean ifemc = false;// 劳动合同显示状态
	private EmBaseCompact_AddBll emcBll = new EmBaseCompact_AddBll();
	String emcFilename = "";

	private boolean ifeeri = false;// 就业登记显示状态
	private boolean ifeeriStop = false;// 可就业登记终止还是直接退回
	private String[] eeriStopreason = { "合同终止", "单位原因解除", "个人原因解除", "修改个人信息" };

	private List<EmRegistrationInfoModel> emregList;
	private EmReg_ListBll erBll = new EmReg_ListBll();

	// 智翼通接口传入值
	private EmZYTModel emztM;

	Date outdate;// 离职时间
	private String sb_ownmonth;// 社保停交所属月份
	private String house_ownmonth;// 公积金停交所属月份

	private Window windim;

	public Embase_DimissionController() throws SQLException {
		// 获取员工基本信息
		emM = eBll.getEmbaseInfo(" and a.emba_state=1 and a.gid=" + gid);
		ebmold = (EmbaseModel) emM.clone();
		// EmHouse_InfoBll sxbll = new EmHouse_InfoBll();
		// sxbll.updateInfo(gid);
		// 获取报价单信息
		setCflList(cgliId);
		if (cgliId.size() > 0) {
			ifCflsit = true;
		}

		// 判断是否有社保数据
		sbModel = sbBll.getShebaoUpdateByGid(gid);
		if (sbModel != null) {
			if (sbModel.getEsiu_ifstop() == 0) {
				ifshebao = true;

				ifsbStop = sbBll.ifStop();
				// 判断是否停止当月操作社保
				if (ifsbStop) {
					sbownmonthList = sbBll.getOwnmonthByUpOwnmonth(
							String.valueOf(sbModel.getOwnmonth()), false);
				} else {
					sbownmonthList = sbBll.getOwnmonthByUpOwnmonth(
							String.valueOf(sbModel.getOwnmonth()), true);
				}
				// 将格式转换为上月底，如201505转为2015-4-30
				sbownmonthList = dBll.getSBStopMonth(sbownmonthList);
				// ifsbaud = sbBll.ifAud(); 取消
				// sbaud = ifsbaud;
				sbaud = false;
			}
		}

		// 判断是否有社保补交数据
		bjList = sbbjBll
				.getBjInfoByStr(" and bj.emsb_ifdeclare in(0,4,2,7,8,3) and bj.gid="
						+ gid);
		if (bjList.size() > 0) {
			ifshebaobj = true;

			sbbjownmonthList = dBll
					.getSBBjOwnmonthList(" and bj.emsb_ifdeclare in(0,4,2,7,8,3) and bj.gid="
							+ gid);
		}

		// 判断是否有住房公积金数据
		gjjBll.updateData(gid);
		gjjList = gjjBll.getListById2(emM.getCid(), gid);
		if (gjjList.size() > 0) {
			if (gjjList.get(0).getEmhu_ifstop() != null
					&& gjjList.get(0).getEmhu_ifstop().equals(0)) {// 检查是否在册
				ifgjj = true;

				gjjModel.setCid(emM.getCid());
				gjjModel.setGid(gid);
				gjjModel.setEmhc_company(gjjList.get(0).getEmhu_company());
				gjjModel.setEmhc_shortname(gjjList.get(0).getCoba_shortname());
				gjjModel.setEmhc_companyid(gjjList.get(0).getEmhu_companyid());
				gjjModel.setEmhc_name(gjjList.get(0).getEmhu_name());
				gjjModel.setEmhc_idcard(gjjList.get(0).getEmhu_idcard());
				gjjModel.setEmhc_idcardclass(gjjList.get(0)
						.getEmhu_idcardclass());
				gjjModel.setEmhc_hj(gjjList.get(0).getEmhu_hj());
				gjjModel.setEmhc_houseid(gjjList.get(0).getEmhu_houseid());
				gjjModel.setEmhc_mobile(gjjList.get(0).getEmhu_mobile());
				gjjModel.setEmhc_title(gjjList.get(0).getEmhu_title());
				gjjModel.setEmhc_wifename(gjjList.get(0).getEmhu_wifename());
				gjjModel.setEmhc_wifeidcard(gjjList.get(0).getEmhu_wifeidcard());
				gjjModel.setEmhc_degree(gjjList.get(0).getEmhu_degree());

				gjjModel.setEmhc_radix(gjjList.get(0).getEmhu_radix());
				gjjModel.setEmhc_cpp(gjjList.get(0).getEmhu_cpp());
				gjjModel.setEmhc_opp(gjjList.get(0).getEmhu_opp());
				gjjModel.setEmhc_single(gjjList.get(0).getEmhu_single());

				gjjModel.setEmhc_client(gjjList.get(0).getCoba_client());

				gjjModel.setEmhc_change("停交");
				gjjModel.setEmhc_ifprogress(31);
				gjjModel.setEmhc_tid(0);
				gjjModel.setEmhc_addname(username);

				// 判断公积金截止日获取公积金停交月份列表
				Integer gjjfwownmonth; // 公积金服务起始月
				Integer gjjtzownmont; // 公积金台账月
				boolean house_ifStop;
				EmbaseLogin_AddBll abll = new EmbaseLogin_AddBll();
				EmHouseSetBll sbll = new EmHouseSetBll();

				// gjjtzownmont = abll.houseOwnmonth();
				gjjtzownmont = gjjList.get(0).getOwnmonth();// 获取在册数据所属月份
				house_ifStop = sbll.gjjOnair(emM.getCid(), emM.getGid(),
						gjjtzownmont, null); // 公积金接单日

				if (house_ifStop) {
					// 截单社保所属月份+1
					gjjfwownmonth = Integer.valueOf(DateStringChange
							.ownmonthAddoneMonth(gjjtzownmont.toString()));
				} else {
					gjjfwownmonth = gjjtzownmont;
				}

				String[] gjjOwnmonth;
				gjjOwnmonth = MonthListUtil.getMonthList(true,
						String.valueOf(gjjfwownmonth), "f", 6);

				for (int i = 0; i < gjjOwnmonth.length; i++) {
					gjjownmonthList.add(gjjOwnmonth[i]);
				}

				gjjModel.setOwnmonth(gjjfwownmonth);
				/*
				 * gjjModel.setJudge(gjjSBll.gjjaudit( gjjModel.getCid(),
				 * gjjModel.getGid(), Integer.valueOf(gjjownmonthList.get(0)),
				 * gjjSBll.houseSingle(gjjModel.getGid(),
				 * Integer.valueOf(gjjownmonthList.get(0)))));
				 */
				// gjjJudge = gjjModel.isJudge(); 取消
				gjjJudge = false;

				// 将格式转换为上月底，如201505转为2015-4-30
				gjjownmonthList = dBll.getGJJStopMonth(gjjownmonthList);
			}
		}

		// 判断是否有住房公积金补缴数据
		gjjbjownmonthList = dBll
				.getGjjBjOwnmonthList(" and bj.emhb_ifdeclare in(0,4,3) and bj.gid="
						+ gid);
		if (gjjbjownmonthList.length > 1) {
			ifgjjbj = true;
		}

		// 判断是否有委托外地数据
		emoutList = wtSBll.getEmCommOutList(" and ecou_state=1 and a.gid="
				+ gid);
		if (emoutList.size() > 0) {
			wtm = emoutList.get(0);// 赋值到model
			if (wtm.getEcou_state() == 1) {
				ifwt = true;

				wtm.setFeeList(new EmCommissionOutListBll()
						.getFeeDetailList(" and eofd_ecou_id="
								+ wtm.getEcou_id()));

				// 复制在册数据到变更数据的model
				UpdateToChange();

				// 终止原因列表
				causeList.add("辞职");
				causeList.add("取消委托");
				causeList.add("终止劳动合同（自愿）");
				causeList.add("终止劳动合同（非自愿）");
				causeList.add("协商解除劳动合同（自愿）");
				causeList.add("协商解除劳动合同（非自愿）");
				causeList.add("退休");
				causeList.add("死亡");
				causeList.add("公司自行管理，无需退工");
				causeList.add("公司自行管理，需办退工");
				causeList.add("转其他公司管理，无需退工");
				causeList.add("转其他公司管理，需办退工");
				causeList.add("转用工单位（同个委托地，A公司转B公司）");
				causeList.add("福利地转移");
				// wtcm.setEcoc_stop_cause(causeList.get(0));
				wtcm.setEcoc_stop_date(null);
			}
		}

		// 判断是否有商保数据
		ci_insurant_castsortout = new ListModelList<CI_Insurant_ListModel>(
				shangbaoBll.ci_insurant_castsortout(String.valueOf(gid)));// 获取商保在保类型数据
		if (ci_insurant_castsortout.size() > 0) {
			ifshangbao = true;
		}

		// 判断是否有档案数据
		daEmbalist = daABll.getEmbaselist("", "", String.valueOf(gid));
		if (daEmbalist.size() > 0) {
			if (daEmbalist.get(0).getEmar_state() == 1) {
				if (daABll.checkDatum(daEmbalist.get(0).getGid(), 5) == 0) {
					fidlist = daEASBll.getFidInfo(gid);
					if (fidlist.size() > 0) {
						ifda = true;

						// 获取员工的存档机构
						archiveType = dBll.getArchiveType(gid);
					}
				}
			}
		}

		// 判断是否有就业登记数据
		emregList = erBll.getEmRegList(1,
				" and type=1 and typename='前道状态' and state=1 and erin_stop_state=0 and a.gid="
						+ gid);
		if (emregList.size() > 0) {
			ifeeri = true;
			// 判断是操作就业登记数据退回还是就业终止
			if (emregList.get(0).getErin_state() == 7
					|| emregList.get(0).getErin_state() == 8) {
				ifeeriStop = true; // 可以操作终止(如果是false系统直接退回)
			}
		}
		// 判断是否有劳动合同数据
		ifemc = emcBll.check_ciin(gid);

		// 智翼通接口传入值
		try {
			// 获取智翼通接口model数据
			emztM = (EmZYTModel) Executions.getCurrent().getArg().get("emztM");

			// 调用智翼通接口数据携带过来方法
			getEmZYTInfo();
		} catch (Exception e) {
			emztM = null;
		}
	}

	@Command
	public void readInfo(@BindingParam("a") Window win) {
		if (windim == null) {
			windim = win;
		}

		if (emM.getGid() == null) {
			windim.detach();
			Messagebox.show("无法获取员工在职业务信息，请确认该员工是否已离职或者未完成入职!", "提示",
					Messagebox.OK, Messagebox.ERROR);
		}

		if (ifCflsit == false) {
			windim.detach();
			Messagebox.show("请先在分配员工报价单，再操作离职!", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}

		// 如果在册表无数据，判断是否有未来月份的公积金待确定数据
		//if (ifgjj = false) {

			boolean chkgjj = dBll.chkGJJChange(emM.getGid());
			if (chkgjj) {
				windim.detach();
				Messagebox.show("员工存在待确认的住房公积金变更数据，请先删除后再操作离职。!", "提示",
						Messagebox.OK, Messagebox.ERROR);
			}

		//}

	}

	// 提交事件
	@SuppressWarnings("static-access")
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("outdate") Datebox outdate,
			@BindingParam("outreason") Combobox outreason,
			@BindingParam("coofferlist") Grid coofferlist,
			@BindingParam("gdShebao") Grid gdShebao,
			@BindingParam("gdShebaobj") Grid gdShebaobj,
			@BindingParam("gdGjj") Grid gdGjj, @BindingParam("gdWT") Grid gdWT,
			@BindingParam("gdShangbao") Grid gdShangbao,
			@BindingParam("gdDa") Grid gdDa, @BindingParam("gdEmc") Grid gdEmc,
			@BindingParam("gdEeri") Grid gdEeri,
			@BindingParam("gdGjjbj") Grid gdGjjbj) throws Exception {

		// 检查表单
		if (checkPage(outdate, outreason, coofferlist, gdShebao, gdShebaobj,
				gdGjj, gdWT, gdShangbao, gdDa, gdEmc, gdEeri, gdGjjbj)) {

			String fMsg = emM.getEmba_name() + "----";// 最终提示

			// 基本信息离职
			String[] emMsg;
			EmbaseModel m = new EmbaseModel();
			m.setGid(gid);
			m.setEmba_outdate(dsc.DatetoSting(outdate.getValue(), "yyyy-MM-dd"));
			m.setEmba_outreason(outreason.getValue());
			m.setEmba_state(0);
			emMsg = dBll.emBaseDimission(m);

			SystLogModel logm = new SystLogModel();
			SystLogInfoBll logBll = new SystLogInfoBll();
			String strlog = "";
			try {
				strlog = EntitiesComparedUtils.OldToNewReflect(ebmold, m);

			} catch (NoSuchMethodException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			logm.setContent(strlog); // 修改内容或新增内容
			logm.setAddname(UserInfo.getUsername());
			// m.setIP(ip);
			logm.setCid(String.valueOf(ebmold.getCid()));
			logm.setGID(String.valueOf(gid));
			logm.setClass1("员工信息");
			logBll.addSystLog(logm);

			// 报价单项目终止
			if (ifCflsit == true) {// 判断是否有报价单项目

				String cgli_id = "";
				String sd1 = "";
				String[] cgliMsg;

				// 判断是否勾选项目
				if (coofferlist.getRows().getChildren().size() > 0) {
					for (Integer i = 0; i < coofferlist.getRows().getChildren()
							.size(); i++) {

						if (coofferlist.getCell(i, 5) != null) {

							Checkbox ck = (Checkbox) coofferlist.getCell(i, 5)
									.getChildren().get(0);

							if (ck.isChecked()) {

								Datebox db1 = (Datebox) coofferlist
										.getCell(i, 4).getChildren().get(0)
										.getChildren().get(0);

								cgli_id = String.valueOf(ck.getValue());
								// sd1 =
								// String.valueOf(sdf.format(db1.getValue()));
								sd1 = dsc.DatetoSting(db1.getValue(), "yyyyMM");
								Log4jInit.toLog("员工离职|" + gid + "("
										+ emM.getEmba_name() + ")|" + cgli_id
										+ "|" + sd1);
								cgliMsg = dBll.stopCoGlist(gid, cgli_id, sd1,
										username);
								if (!cgliMsg[0].equals("1")) {
									fMsg = fMsg + "存在报价单项目更新不成功；";
								}

							}
						}
					}
				}
			}

			// 社保停交操作
			if (ifshebao == true) {

				Combobox cb = (Combobox) gdShebao.getCell(0, 1).getChildren()
						.get(0).getChildren().get(0);
				// 所属月份
				Textbox tb = (Textbox) gdShebao.getCell(0, 5).getChildren()
						.get(0); // 申请原因
				Combobox cb2 = (Combobox) gdShebao.getCell(0, 3).getChildren()
						.get(0); // 停交原因

				EmShebaoUpdateModel sbm = sbModel;

				sbm.setOwnmonth(Integer.parseInt(dsc.ownmonthAddoneMonth(dsc
						.DatetoSting(dsc.StringtoDate(cb.getSelectedItem()
								.getLabel(), "yyyy-MM-dd"), "yyyyMM"))));

				sbm.setEsiu_remark(tb.getValue());
				sbm.setEsiu_addname(username);
				sbm.setEsiu_stopreason(cb2.getValue());
				if (sbaud) {
					sbm.setIfdeclare(0);
				} else {
					sbm.setIfdeclare(0);
				}

				// 调用停交方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.stop(sbm);
				if (!message[0].equals("1")) {
					fMsg = fMsg + "社保停交不成功；";
				}
			}

			// 社保补交操作
			if (ifshebaobj) {
				Combobox cb = (Combobox) gdShebaobj.getCell(0, 1).getChildren()
						.get(0);// 所属月份

				if (!"不退回数据且不终止任务单".equals(cb.getSelectedItem().getLabel())) {
					EmShebaoBJModel bjModel;
					String[] mes;
					// 循环操作
					for (int i = 0; i < bjList.size(); i++) {
						bjModel = bjList.get(i);

						if (bjModel.getOwnmonth() >= Integer.parseInt(cb
								.getSelectedItem().getLabel())) {// 判断数据是否大于等于所选月份

							bjModel.setEmsb_ifdeclare(3);
							bjModel.setEmsb_reason("员工离职");
							bjModel.setEmsb_addname(username);

							// 判断有无医疗补交数据
							Emsi_OperateDal eDal = new Emsi_OperateDal();
							boolean ifJLBJ = eDal.getShebaoBJJL(
									bjModel.getGid(), bjModel.getOwnmonth(),
									bjModel.getEmsb_startmonth());

							// 判断有无任务单
							if (bjModel.getEmsb_tapr_id() > 0) {// 有任务单
								mes = sbbjObll.BjDeclareUpState(bjModel);

								// 医疗补交
								if (ifJLBJ == true) {
									// 通过养老补交获取医疗补交数据
									EmShebaoBJModel jlM = new EmShebaoBJModel();
									Emsi_SelBll bll = new Emsi_SelBll();
									jlM = bll
											.getBjJLListByBJid(bjModel.getId());
									jlM.setEmsb_ifdeclare(3);
									jlM.setEmsb_reason("员工离职");
									jlM.setEmsb_addname(username);

									sbbjObll.BjJLDeclareUpState(jlM);
								}

							} else {// 无任务单直接退回

								Emsi_OperateBll eobll = new Emsi_OperateBll();
								mes = eobll.BjDeclareUpState(bjModel);

								// 医疗补交
								if (ifJLBJ == true) {
									// 通过养老补交获取医疗补交数据
									EmShebaoBJModel jlM = new EmShebaoBJModel();
									Emsi_SelBll bll = new Emsi_SelBll();
									jlM = bll
											.getBjJLListByBJid(bjModel.getId());
									jlM.setEmsb_ifdeclare(3);
									jlM.setEmsb_reason("员工离职");
									jlM.setEmsb_addname(username);

									eobll.BjJLDeclareUpState(jlM);
								}
							}

							if (!"1".equals(mes[0])) {
								fMsg = fMsg + bjModel.getOwnmonth()
										+ "月社保补交数据退回失败；";
							}
						}
					}
				}

			}

			Log4jInit.toLog("员工离职|" + gid + "(" + emM.getEmba_name() + ")|公积金:"
					+ ifgjj);
			// 住房公积金操作
			if (ifgjj) {

				Combobox cb = (Combobox) gdGjj.getCell(0, 1).getChildren()
						.get(0); // 所属月份
				Textbox tb = (Textbox) gdGjj.getCell(0, 3).getChildren().get(0); // 申请原因

				if (gjjJudge) {
					gjjModel.setEmhc_ifdeclare(0);
				} else {
					gjjModel.setEmhc_ifdeclare(0);
				}

				gjjModel.setOwnmonth(Integer.parseInt(dsc
						.ownmonthAddoneMonth(dsc.DatetoSting(dsc.StringtoDate(
								cb.getSelectedItem().getLabel(), "yyyy-MM-dd"),
								"yyyyMM"))));

				gjjModel.setEmhc_remark(tb.getValue());
				Log4jInit.toLog("员工离职|" + gid + "(" + emM.getEmba_name()
						+ ")|公积金:" + gjjModel.getOwnmonth() + "|"
						+ gjjModel.getCid() + "|" + gjjModel.getGid());
				Integer gjj_i = gjjBll.stopEmhouse(gjjModel);
				if (gjj_i <= 0) {
					fMsg = fMsg + "住房公积金停交不成功；";

					MessageService msgservice = new MessageImpl(
							"emhouseupdate", gjjModel.getEmhc_id());
					LoginDal d = new LoginDal();
					SysMessageModel sysm = new SysMessageModel();
					String msgstr = "系统出错造成员工离职公积金未停缴，请在员工业务中心单独停缴编号:"
							+ gjjModel.getGid() + " 员工的公积金数据；";
					sysm.setSyme_content(msgstr);// 短信内容
					sysm.setSyme_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// 发件人id
					sysm.setSymr_name("赵敏捷");// 收件人姓名
					sysm.setSymr_log_id(59);// 收件人ID;
					sysm.setEmail(1);
					sysm.setEmailtitle("离职公积金未停缴");

					msgservice.Add(sysm);

					sysm.setSymr_name(UserInfo.getUsername());// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// 收件人ID;

					msgservice.Add(sysm);

					sysm.setSymr_name("彭耀");// 收件人姓名
					sysm.setSymr_log_id(23);// 收件人ID;
					msgservice.Add(sysm);
				}
			}

			// 住房公积金补缴
			if (ifgjjbj) {
				Combobox cb = (Combobox) gdGjjbj.getCell(0, 1).getChildren()
						.get(0);// 所属月份

				if (!"不退回数据且不终止任务单".equals(cb.getSelectedItem().getLabel())) {
					// 获取数据
					EmHouse_EditBll bll = new EmHouse_EditBll();
					List<EmHouseBJModel> gjjbjlist = new ListModelList<>();
					EmHouseBJModel gjjbjm = new EmHouseBJModel();
					gjjbjm.setGid(gid);
					gjjbjm.setDataState(2);
					gjjbjlist = bll.getbjChangeList(gjjbjm);

					EmHouseBJModel gjjbjModel;
					String[] mes;
					// 循环操作
					for (int i = 0; i < gjjbjlist.size(); i++) {
						gjjbjModel = gjjbjlist.get(i);

						if (gjjbjModel.getOwnmonth() >= Integer.parseInt(cb
								.getSelectedItem().getLabel())) {// 判断数据是否大于等于所选月份

							gjjbjModel.setEmhb_ifdeclare(3);
							gjjbjModel.setEmhb_reason("员工离职");
							gjjbjModel.setEmhb_addname(username);

							// 判断有无任务单
							Integer gjjbj_tapr_id;
							if (gjjbjModel.getEmhb_tapr_id() != null) {
								gjjbj_tapr_id = gjjbjModel.getEmhb_tapr_id();
							} else {
								gjjbj_tapr_id = 0;
							}
							if (gjjbj_tapr_id > 0) {// 有任务单
								WfBusinessService wfbs = new EmHouse_EditBJImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								Object[] obj = { "补缴退回", gjjbjModel };
								mes = new String[5];
								mes = wfs.StopTask(obj,
										gjjbjModel.getEmhb_tapr_id(), "系统", "");
							} else {// 无任务单直接退回
								EmHouse_EditBll hbjBll = new EmHouse_EditBll();
								mes = hbjBll.returnGJJBj(gjjbjModel
										.getEmhb_id());
							}

							if (!"1".equals(mes[0])) {
								fMsg = fMsg + gjjbjModel.getOwnmonth()
										+ "月住房公积金补交数据退回失败；";

							}
						}
					}
				}
			}

			// 委托外地操作
			if (ifwt) {
				EmCommissionOut_OperateBll wtOBll = new EmCommissionOut_OperateBll();
				for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
					m1.setEofc_stop_date(m1.getTempDate() == null ? null
							: DateStringChange.DatetoSting(
									DateUtil.getLastDay(m1.getTempDate()),
									"yyyy-MM-dd"));
				}
				wtcm.setEcoc_title_date(DateStringChange.DatetoSting(
						wtcm.getEcoc_stop_date(), "yyyy-MM-dd"));
				wtcm.setRemark(wtcm.getEcoc_stop_cause());
				wtcm.setFeeList(cfeeList);

				String[] str = wtOBll.termination(wtcm, wtcm.getEmba_name());

				if (str[0].equals("1")) {
					cfeeList.clear();
					wtcm = null;
					causeList.clear();
				} else if (str[0].equals("0")) {
					fMsg = fMsg + "委托出停交不成功；";

					// 发邮件和系统短信
					MessageService msgservice = new MessageImpl(
							"EmCommissionOutChange", wtcm.getEcoc_id());
					LoginDal d = new LoginDal();
					SysMessageModel sysm = new SysMessageModel();
					String msgstr = "编号" + wtcm.getGid() + "委托外地离职不成功，请及时更进！";
					sysm.setSyme_title("委托外地离职明细出现异常");
					sysm.setSyme_content(msgstr);// 短信内容
					sysm.setSyme_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// 发件人id
					sysm.setEmail(1);
					sysm.setEmailtitle(msgstr);
					sysm.setSymr_name(UserInfo.getUsername());// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// ;
					msgservice.Add(sysm);
					sysm.setSymr_name("张志强");// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname("张志强"));// ;
					msgservice.Add(sysm);
				}
			}

			// 商保操作
			if (ifshangbao) {
				String st_date;
				for (int i = 0; i < gdShangbao.getRows().getChildren().size(); i++) {// 循环判断表单数据是否填写
					Label ecin_castsort = (Label) gdShangbao.getCell(i, 1)
							.getChildren().get(0);
					Label ecin_id = (Label) gdShangbao.getCell(i, 4)
							.getChildren().get(0);
					Datebox fact_date = (Datebox) gdShangbao.getCell(i, 3)
							.getChildren().get(0);

					st_date = "";

					if (fact_date.getValue() != null) {
						st_date = dsc.DatetoSting(fact_date.getValue(),
								"yyyy-MM-dd");
					}

					if (!"".equals(st_date) && st_date != null) {
						String[] message = shangbaoOBll.out_insurant(
								String.valueOf(gid),
								String.valueOf(emM.getCid()),
								ecin_castsort.getValue(), st_date,
								ecin_id.getValue());
						if (!message[0].equals("1")) {
							fMsg = fMsg + "商业保险" + ecin_castsort.getValue()
									+ "停缴不成功；";
						}
					}
				}
			}

			// 档案操作
			if (ifda) {
				Datebox checkoutdate = (Datebox) gdDa.getCell(3, 1)
						.getChildren().get(0);// 调出日期
				Textbox checkoutreason = (Textbox) gdDa.getCell(4, 1)
						.getChildren().get(0);// 调出原因

				EmArchiveModel fmodel = new EmArchiveModel();
				fidlist = daEASBll
						.getEmArchiveInfo(" and emar_state=1 and a.gid=" + gid);
				if (fidlist.size() > 0) {
					fmodel = fidlist.get(0);
				}

				EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(gid);
				model.setCid(emM.getCid());
				model.setEada_type("新增档案调出");
				model.setEada_addname(username);
				model.setEada_final("1");
				model.setEada_checkoutreason(checkoutreason.getValue());
				model.setEada_checkoutdate(checkoutdate.getValue());
				model.setEada_fid("");

				// 新增业务数据，并返回业务表ID
				String[] str = blloper
						.EmArchiveCheckOut(model, "", "1", fmodel);
				if (!str[0].equals("1")) {
					fMsg = fMsg + "档案调出操作不成功；";
				} else {

					// 添加短信数据
					Checkbox ckSMS = (Checkbox) gdDa.getCell(1, 1)
							.getChildren().get(0).getChildren().get(0)
							.getChildren().get(0);
					Textbox tbSMS = (Textbox) gdDa.getCell(1, 1).getChildren()
							.get(0).getChildren().get(0).getChildren().get(1);

					if (ckSMS.isChecked() && !"".equals(tbSMS.getValue())
							&& tbSMS.getValue() != null) {

						SMSModel sModel = new SMSModel();
						sModel.setSendname(username);
						sModel.setMobile(tbSMS.getValue());
						sModel.setContent(daContent);
						sModel.setGid(gid);
						sModel.setIdcard(emM.getEmba_idcard());
						Sms_GroupSendBll bll = new Sms_GroupSendBll();
						try {
							int k = bll.SmsSend(sModel);
							if (k > 0) {// 发送成功
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}

					// 添加email数据
					Checkbox ckEmail = (Checkbox) gdDa.getCell(1, 1)
							.getChildren().get(0).getChildren().get(1)
							.getChildren().get(0);
					Textbox tbEmail = (Textbox) gdDa.getCell(1, 1)
							.getChildren().get(0).getChildren().get(1)
							.getChildren().get(1);

					if (ckEmail.isChecked() && !"".equals(tbEmail.getValue())
							&& tbEmail.getValue() != null) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

						PubEmailModel md = new PubEmailModel();
						md.setPuem_title("档案调动通知");
						md.setPuem_content(daContent);
						md.setPuem_email(tbEmail.getValue());
						md.setPuem_replyto(dBll.getClientInfo(gid).get(0)
								.getLog_email());
						md.setPuem_emailcc("");
						md.setPuem_sendtime(df.format(new Date()));
						md.setPuem_addname(username);
						md.setPuem_ifHTML(0);

						PubEmailService pemail = new PubEamilImpl(md);
						pemail.EmailAdd();
					}

				}
			}

			// 劳动合同操作
			if (ifemc) {
				Datebox stop_date = (Datebox) gdEmc.getCell(0, 1).getChildren()
						.get(0); // 终止日期

				String stop_dateString = "";
				if (stop_date.getValue() != null) {
					stop_dateString = dsc.DatetoSting(stop_date.getValue(),
							"yyyy-MM-dd");
				}

				String emc_url = "";
				if (!"6".equals(UserInfo.getDepID())) {// 全国项目部无需上传文件
					if (this.media != null) {

						// 编辑文件名
						String path = "OfficeFile/DownLoad/EmBaseCompact/";

						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						Date date = new Date();
						String nowtime = sdf.format(date);
						emc_url = path + "EmbaseCompactEnd" + nowtime
								+ String.valueOf(emM.getCid()) + "."
								+ media.getFormat();

						if (FileOperate.upload(media, emc_url)) {
							emc_url = "EmbaseCompactEnd" + nowtime
									+ String.valueOf(emM.getCid()) + "."
									+ media.getFormat();
							emcBll.end_emcompact(String.valueOf(gid),
									stop_dateString, emc_url, username);
						} else {
							Messagebox.show("文件上传出錯。", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				} else {
					emcBll.end_emcompact(String.valueOf(gid), stop_dateString,
							emc_url, username);
				}

				if (emcBll.Dochek5() <= 0) {
					fMsg = fMsg + "劳动合同操作不成功；";
				}
			}

			// 就业登记数据
			if (ifeeri) {
				EmRegistrationInfoModel erm;
				erm = emregList.get(0);
				EmReg_OperateBll eeriOBll = new EmReg_OperateBll();

				// 判断是直接退回还是操作终止
				if (ifeeriStop) {

					// 操作终止
					Combobox stop_reason = (Combobox) gdEeri.getCell(0, 1)
							.getChildren().get(0); // 终止原因
					Datebox stop_date = (Datebox) gdEeri.getCell(0, 3)
							.getChildren().get(0); // 终止日期

					String stop_dateString = "";

					if (stop_date.getValue() != null) {
						stop_dateString = dsc.DatetoSting(stop_date.getValue(),
								"yyyy-MM-dd");
					}

					erm.setErin_stop_date(stop_dateString);
					erm.setErin_stop_people(username);
					erm.setErin_stop_reason(stop_reason.getValue());
					// 调用方法
					String[] str = eeriOBll.erinStopApp(erm);
					if (!str[0].equals("1")) {
						fMsg = fMsg + "就业登记操作不成功；";
					}

				} else {
					// 直接退回
					erm.setErin_state(11);
					erm.setErsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date()));
					erm.setErsr_remark("客服操作该员工就业登记终止，但由于该员工就业登记信息未上传到政府部门系统中，所以业务系统将直接退回该数据。");
					String[] str = eeriOBll.backToN(erm, 2);
					if (!str[0].equals("1")) {
						fMsg = fMsg + "就业登记操作不成功；";
					}

				}
			}

			// 弹出提示并跳转页面
			if (emMsg[0].equals("1")) {
				/*
				 * EventListener<ClickEvent> clickListener = new
				 * EventListener<Messagebox.ClickEvent>() { public void
				 * onEvent(ClickEvent event) throws Exception { if
				 * (Messagebox.Button.OK.equals(event.getButton())) {
				 */
				if (emztM != null) {
					// 智翼通接口数据处理
					if (emztM != null) {
						EmZYT_OperateBll obll = new EmZYT_OperateBll();
						emztM.setEmzt_state(1);
						obll.upEmZYTGid(emztM);
					}

					BindUtils.postGlobalCommand(null, null, "winClose", null);
				} else {
					win.detach();
				}
				/*
				 * } } }; // 弹出提示 Messagebox.show(emMsg[1] + fMsg, "操作提示", new
				 * Messagebox.Button[] { Messagebox.Button.OK },
				 * Messagebox.INFORMATION, clickListener);
				 */
				Messagebox.show(emMsg[1] + fMsg, "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else { // 弹出提示
				Messagebox.show(emMsg[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	}

	// 检测表单
	private boolean checkPage(Datebox outdate, Combobox outreason,
			Grid coofferlist, Grid gdShebao, Grid gdShebaobj, Grid gdGjj,
			Grid gdWT, Grid gdShangbao, Grid gdDa, Grid gdEmc, Grid gdEeri,
			Grid gdGjjbj) {
		boolean b = true;

		// 检查基本信息
		if (outdate.getValue() == null) {
			b = false;
			Messagebox
					.show("请选择离职时间!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return b;
		}
		if (outreason.getValue() == null || "".equals(outreason.getValue())) {
			b = false;
			Messagebox
					.show("请选择离职原因!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return b;
		}

		// 检查报价单
		if (ifCflsit == true) {
			if (coofferlist.getRows().getChildren().size() > 0) {
				for (Integer i = 0; i < coofferlist.getRows().getChildren()
						.size(); i++) {
					if (coofferlist.getCell(i, 5) != null) {
						Checkbox ck = (Checkbox) coofferlist.getCell(i, 5)
								.getChildren().get(0);
						if (ck.isChecked()) {
							Datebox db1 = (Datebox) coofferlist.getCell(i, 4)
									.getChildren().get(0).getChildren().get(0);

							if (DateEmpty(db1)) {
								Messagebox.show("请录入终止收费日", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
								b = false;
								return b;
							}
						}
					}
				}
			}
		}

		// 检查社会保险
		if (ifshebao == true) {
			Combobox cb = (Combobox) gdShebao.getCell(0, 1).getChildren()
					.get(0).getChildren().get(0); // 所属月份
			Textbox tb = (Textbox) gdShebao.getCell(0, 5).getChildren().get(0); // 申请原因
			Combobox cb2 = (Combobox) gdShebao.getCell(0, 3).getChildren()
					.get(0); // 停交原因

			if (cb.getSelectedItem() == null) {
				b = false;
				Messagebox.show("请选择 社会保险 所属月份!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			} /*
			 * else if (sbModel.getEsiu_computerid() == null ||
			 * "".equals(sbModel.getEsiu_computerid())) { b = false;
			 * Messagebox.show("社会保险 电脑号不能为空，如是新增人员，请等待社保局审核数据后系统更新电脑号!",
			 * "操作提示", Messagebox.OK, Messagebox.ERROR); return b; }
			 */else if (cb2.getValue() == null || "".equals(cb2.getValue())) {
				b = false;
				Messagebox.show("请选择 社会保险 停交原因!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			} /*
			 * else if ((tb.getValue() == null || "".equals(tb.getValue())) &&
			 * sbaud) { b = false; Messagebox.show("社会保险 停交变更 需审核，请填写申请原因!",
			 * "操作提示", Messagebox.OK, Messagebox.ERROR); return b; }
			 */
		}

		// 检查社保补交
		if (ifshebaobj) {
			Combobox cb = (Combobox) gdShebaobj.getCell(0, 1).getChildren()
					.get(0);// 所属月份
			if (cb.getSelectedItem() == null) {
				b = false;
				Messagebox.show("请选择 社会补交 所属月份!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}

		// 检查住房公积金
		if (ifgjj) {
			Combobox cb = (Combobox) gdGjj.getCell(0, 1).getChildren().get(0); // 所属月份
			Textbox tb = (Textbox) gdGjj.getCell(0, 3).getChildren().get(0); // 申请原因

			if (cb.getSelectedItem() == null) {
				b = false;
				Messagebox.show("请选择 住房公积金 所属月份!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			} /*
			 * else if ((tb.getValue() == null || "".equals(tb.getValue())) &&
			 * gjjJudge) { b = false; Messagebox.show("住房公积金 停交变更 需审核，请填写申请原因!",
			 * "操作提示", Messagebox.OK, Messagebox.ERROR); return b; }
			 */
		}

		// 检查住房公积金补交
		if (ifgjjbj) {
			Combobox cb = (Combobox) gdGjjbj.getCell(0, 1).getChildren().get(0);// 所属月份
			if (cb.getSelectedItem() == null) {
				b = false;
				Messagebox.show("请选择 住房公积金补缴 所属月份!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}

		// 检查委托外地
		if (ifwt) {
			Datebox wtStopdate = (Datebox) gdWT.getCell(0, 3).getChildren()
					.get(0);
			Combobox cb = (Combobox) gdWT.getCell(0, 1).getChildren().get(0);
			if (DateEmpty(wtStopdate)) {
				Messagebox.show("请录入终止日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				b = false;
				return b;
			}
			if (cb.getSelectedItem() == null) {
				Messagebox.show("请录入终止原因", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				b = false;
				return b;
			}

			if (gdWT.getRows().getChildren().size() > 0) {
				Grid wtFeeList = (Grid) gdWT.getRows().getChildren().get(2)
						.getChildren().get(0).getChildren().get(0);
				for (Integer i = 0; i < wtFeeList.getRows().getChildren()
						.size(); i++) {
					if (wtFeeList.getCell(i, 7) != null) {

						Datebox db1 = (Datebox) wtFeeList.getCell(i, 7)
								.getChildren().get(0);

						if (DateEmpty(db1)) {
							Messagebox.show("请录入停缴日", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
							b = false;
							return b;
						}
					}
				}
			}
		}

		// 检查商保
		if (ifshangbao) {
			String st_date;
			for (int i = 0; i < gdShangbao.getRows().getChildren().size(); i++) {// 循环判断表单数据是否填写
				Label ecin_castsort = (Label) gdShangbao.getCell(i, 1)
						.getChildren().get(0);
				Label ecin_id = (Label) gdShangbao.getCell(i, 4).getChildren()
						.get(0);
				Datebox fact_date = (Datebox) gdShangbao.getCell(i, 3)
						.getChildren().get(0);

				st_date = "";

				if (fact_date.getValue() != null) {
					st_date = dsc.DatetoSting(fact_date.getValue(),
							"yyyy-MM-dd");
				}

				if ("".equals(st_date) || st_date == null) {
					b = false;
					Messagebox.show("请选择 商业保险 所有停缴时间!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					break;
				}

				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式

				SimpleDateFormat dateFormattime = new SimpleDateFormat("HH");

				SimpleDateFormat nowdateFormat = new SimpleDateFormat(
						"yyyyMMdd");// 可以方便地修改日期格式

				int datetime = Integer.parseInt(dateFormattime.format(now));

				int nowdatetime = Integer.parseInt(nowdateFormat.format(now));

				String nowtime = dateFormat.format(now);

				int stdatetime = Integer.parseInt(nowdateFormat
						.format(fact_date.getValue()));

				if (ecin_castsort.getValue().substring(0, 1).equals("雇")
						&& datetime > 17) {
					Messagebox.show("雇主险停缴只能在当天17点前操作！请在明天操作停缴！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					b = false;
					break;
				}

				if (ecin_castsort.getValue().substring(0, 1).equals("增")
						&& datetime > 17) {
					Messagebox.show("雇主险停缴只能在当天17点前操作！请在明天操作停缴！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					b = false;
					break;
				}

				if (stdatetime < nowdatetime
						&& ecin_castsort.getValue().substring(0, 1).equals("雇")) {
					Messagebox.show("雇主险停缴只能停缴当天，请注意停缴时间！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					b = false;
					break;
				}

				if (stdatetime < nowdatetime
						&& ecin_castsort.getValue().substring(0, 1).equals("增")) {
					Messagebox.show("雇主险停缴只能停缴当天，请注意停缴时间！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					b = false;
					break;
				}
			}
		}

		// 检查档案
		if (ifda) {
			Radiogroup rgType = (Radiogroup) gdDa.getCell(0, 1).getChildren()
					.get(0);
			if (rgType.getSelectedItem() == null) {
				b = false;
				Messagebox.show("请选择 档案管理 的存档机构!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
		}

		// 检查劳动合同
		if (ifemc) {

			Datebox stop_date = (Datebox) gdEmc.getCell(0, 1).getChildren()
					.get(0); // 终止日期
			String stop_dateString = "";

			if (stop_date.getValue() != null) {
				stop_dateString = dsc.DatetoSting(stop_date.getValue(),
						"yyyy-MM-dd");
			}
			if ("".equals(stop_dateString) || stop_dateString == null) {
				b = false;
				Messagebox.show("请选择“终止日期!”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
			if (this.media == null && !"6".equals(UserInfo.getDepID())) {
				b = false;
				Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

		}

		// 检查就业登记
		if (ifeeriStop) {
			Combobox stop_reason = (Combobox) gdEeri.getCell(0, 1)
					.getChildren().get(0); // 终止原因
			Datebox stop_date = (Datebox) gdEeri.getCell(0, 3).getChildren()
					.get(0); // 终止日期

			String stop_dateString = "";

			if (stop_date.getValue() != null) {
				stop_dateString = dsc.DatetoSting(stop_date.getValue(),
						"yyyy-MM-dd");
			}

			if (stop_reason.getSelectedItem() == null) {

				b = false;
				Messagebox.show("请选择“终止原因!”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
			if ("".equals(stop_dateString) || stop_dateString == null) {
				b = false;
				Messagebox.show("请选择“终止日期!”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

		}

		return b;
	}

	// 档案短信和email提示内容
	@Command("createMessage")
	@NotifyChange({ "daContent", "da_chk" })
	public void createMessage(@BindingParam("coofferlist") Grid coofferlist,
			@BindingParam("type") int type) {
		// 判断是否有档案保管报价单项目
		if (ifCflsit) {
			boolean chk = false;
			Date daDate = null;
			if (coofferlist.getRows().getChildren().size() > 0) {
				for (Integer i = 0; i < coofferlist.getRows().getChildren()
						.size(); i++) {
					Label cflLabel = (Label) coofferlist.getCell(i, 3)
							.getChildren().get(0);
					if ("服档费".equals(cflLabel.getValue())
							|| "综合管理费".equals(cflLabel.getValue())
							|| "保管服务费".equals(cflLabel.getValue())) {
						if (coofferlist.getCell(i, 5) != null) {
							Checkbox ck = (Checkbox) coofferlist.getCell(i, 5)
									.getChildren().get(0);
							if (ck.isChecked()) {
								Datebox db1 = (Datebox) coofferlist
										.getCell(i, 4).getChildren().get(0)
										.getChildren().get(0);

								if (!DateEmpty(db1)) {
									chk = true;
									daDate = db1.getValue();
								}
							}
						}
						break;
					}
				}

				if (chk) {
					// 获取员工档案存放机构
					String fp = "";
					fp = dBll.getArchivePlace(gid, emM.getCid());
					da_chk = true;
					// 获取对应客服信息
					List<LoginModel> lmList;
					lmList = dBll.getClientInfo(gid);

					if (type == 1) {
						daContent = emM.getEmba_name() + "您好，接"
								+ emM.getCoba_shortname() + "公司通知我司将于"
								+ dsc.DatetoSting(daDate, "yyyyMM")
								+ "终止您的人事档案管理手续，日后如有档案相关事宜请前往" + fp
								+ "办理。如有问题请致电" + lmList.get(0).getLog_tel()
								+ "," + lmList.get(0).getLog_name()
								+ lmList.get(0).getLog_sex() + "【深圳中智】";
					} else {
						daContent = emM.getEmba_name()
								+ "您好，接"
								+ emM.getCoba_shortname()
								+ "公司通知我司将于"
								+ dsc.DatetoSting(daDate, "yyyyMM")
								+ "终止您的人事档案管理手续，请您在"
								+ dsc.DatetoSting(du.getLastDay(du.getDateAdd(
										daDate, "M", 1)), "yyyy-MM-dd")
								+ "日之前来我司办理调档手续（如户口挂靠在我司需同时办理转出），逾期我司将按月收取相关费用。如有问题请致电"
								+ lmList.get(0).getLog_tel() + ","
								+ lmList.get(0).getLog_name()
								+ lmList.get(0).getLog_sex() + "【深圳中智】";
					}

				} else {
					da_chk = false;
					Messagebox.show("请选择报价单项目 服档费 或 综合管理费 或 保管服务费 终止收费月!",
							"操作提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
	}

	// 填入起始日同时勾选
	@Command("setcheck")
	public void setcheck(@BindingParam("a") Grid gd,
			@BindingParam("b") Datebox db) {
		if (db.getValue() != null) {
			Row row = (Row) db.getParent().getParent().getParent();
			Checkbox ck = (Checkbox) gd.getCell(row.getIndex(), 5)
					.getChildren().get(0);
			ck.setChecked(true);
		}
	}

	// 复制起始日
	@Command("copysd")
	public void copysd(@BindingParam("a") Grid gd,
			@BindingParam("b") Integer cellIndex,
			@BindingParam("rowIndex") Integer num) {
		Datebox db1 = (Datebox) gd.getCell(num, cellIndex).getChildren().get(0)
				.getChildren().get(0);

		if (db1.getValue() != null) {
			for (int i = num + 1; i < gd.getRows().getChildren().size(); i++) {

				if (gd.getCell(i, cellIndex) != null) {

					Datebox db2 = (Datebox) gd.getCell(i, cellIndex)
							.getChildren().get(0).getChildren().get(0);

					// if (db2.getValue() == null) {
					Checkbox ck = (Checkbox) gd.getCell(i, 5).getChildren()
							.get(0);
					if (ck.isChecked())
						db2.setValue(db1.getValue());
					// }

				}
			}
		} else {
			/*
			 * for (int i = num + 1; i < gd.getRows().getChildren().size(); i++)
			 * {
			 * 
			 * }
			 */
		}
	}

	// 判断date控件的值是否为空
	public boolean DateEmpty(Datebox db) {
		boolean a = false;
		if (db.getValue() != null) {
			if (db.getValue().equals("")) {
				a = true;
			}
		} else {
			a = true;
		}
		return a;
	}

	// 社会保险所属月份改变判断数据是否需要审核
	@Command("sbownmonthChange")
	@NotifyChange("sbaud")
	public void sbownmonthChange(@BindingParam("ownmonth") String ownmonth) {
		// 格式转换，如：2015-4-30转201505
		ownmonth = dsc.ownmonthAddoneMonth(dsc.DatetoSting(
				dsc.StringtoDate(ownmonth, "yyyy-MM-dd"), "yyyyMM"));

		if (sbBll.ifaud(ownmonth)) {
			sbaud = false;
		} else {
			sbaud = ifsbaud;
		}
	}

	// 住房公积金所属月份改变判断数据是否需要审核
	@Command("gjjownmonthChange")
	@NotifyChange("gjjJudge")
	public void gjjownmonthChange(@BindingParam("ownmonth") String ownmonth) {
		// 格式转换，如：2015-4-30转201505
		ownmonth = dsc.ownmonthAddoneMonth(dsc.DatetoSting(
				dsc.StringtoDate(ownmonth, "yyyy-MM-dd"), "yyyyMM"));

		gjjJudge = gjjSBll.gjjaudit(
				gjjModel.getCid(),
				gjjModel.getGid(),
				Integer.valueOf(ownmonth),
				gjjSBll.houseSingle(gjjModel.getGid(),
						Integer.valueOf(ownmonth)));
	}

	// 填入委托项目终止日期
	@Command("setWTtempDate")
	public void setWTtempDate(@BindingParam("date") Datebox date,
			@BindingParam("index") Integer index) {
		if (date.getValue() != null) {
			cfeeList.get(index).setTempDate(date.getValue());
		}
	}

	// 委托出时间批量修改
	@Command("dateAll")
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class,
			@BindingParam("gdWT") Grid gdWT) {
		try {
			if (sicl_class.equals("all")) {
				date = cfeeList.get(0).getTempDate();
				index = 0;
			}
			for (int i = index + 1; i < cfeeList.size(); i++) {
				if (cfeeList.get(i).getSicl_class().equals(sicl_class)
						|| sicl_class.equals("all")) {
					cfeeList.get(i).setTempDate(date);

					// monthbox赋值
					Grid wtFeeList = (Grid) gdWT.getRows().getChildren().get(2)
							.getChildren().get(0).getChildren().get(0);
					Datebox db1 = (Datebox) wtFeeList.getCell(i, 7)
							.getChildren().get(0);
					db1.setValue(date);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 委托出在册数据复制到变更数据model中
	public void UpdateToChange() {
		try {
			wtcm.setEmba_name(wtm.getEmba_name());
			wtcm.setGid(wtm.getGid());
			wtcm.setCid(wtm.getCid());
			wtcm.setEcoc_ecou_id(wtm.getEcou_id());
			wtcm.setEcoc_soin_id(wtm.getEcou_soin_id());
			wtcm.setEcoc_ecos_id(wtm.getEcou_ecos_id());
			wtcm.setEcoc_addtype("离职");
			wtcm.setEcoc_type("ecocter");
			wtcm.setEcoc_idcard(wtm.getEcou_idcard());
			wtcm.setEcoc_email(wtm.getEcou_email());
			wtcm.setEcoc_phone(wtm.getEcou_phone());
			wtcm.setEcoc_mobile(wtm.getEcou_mobile());
			wtcm.setEcoc_in_date(wtm.getEcou_in_date());
			wtcm.setEcoc_com_phone(wtm.getEcou_com_phone());
			wtcm.setEcoc_com_principal(wtm.getEcou_com_principal());
			wtcm.setEcoc_com_company(wtm.getEcou_com_company());
			wtcm.setEcoc_domicile(wtm.getEcou_domicile());
			wtcm.setEcoc_compact_jud(wtm.getEcou_compact_jud());
			wtcm.setEcoc_compact_f(wtm.getEcou_compact_f());
			wtcm.setEcoc_compact_l(wtm.getEcou_compact_l());
			wtcm.setEcoc_salary(wtm.getEcou_salary());
			wtcm.setEcoc_sb_base(wtm.getEcou_sb_base());
			wtcm.setEcoc_house_base(wtm.getEcou_house_base());
			wtcm.setEcoc_sb_em_sum(zero);
			wtcm.setEcoc_sb_co_sum(zero);
			wtcm.setEcoc_sb_sum(zero);
			wtcm.setEcoc_gjj_em_sum(zero);
			wtcm.setEcoc_gjj_co_sum(zero);
			wtcm.setEcoc_gjj_sum(zero);
			wtcm.setEcoc_welfare_sum(zero);
			wtcm.setEcoc_service_fee(zero);
			wtcm.setEcoc_file_fee(zero);
			wtcm.setEcoc_sum(zero);
			// wtcm.setEcoc_stop_date(new Date());
			wtcm.setEcoc_stop_date(null);
			wtcm.setEcoc_stop_cause(wtm.getEcou_stop_cause());
			wtcm.setEcoc_cancel_cause(wtm.getEcou_cancel_cause());
			wtcm.setEcoc_laststate(0);
			wtcm.setEcoc_state(1);
			wtcm.setEcoc_client(wtm.getEcou_client());
			wtcm.setEcoc_addname(UserInfo.getUsername());
			wtcm.setEcoc_remark("");

			for (EmCommissionOutFeeDetailModel m1 : wtm.getFeeList()) {
				EmCommissionOutFeeDetailChangeModel cm1 = new EmCommissionOutFeeDetailChangeModel();

				cm1.setEofc_eofd_id(m1.getEofd_id());
				cm1.setEofc_sicl_id(m1.getEofd_sicl_id());
				cm1.setEofc_ecop_id(m1.getEofd_ecop_id());
				cm1.setEofc_name(m1.getEofd_name());
				cm1.setEofc_content(m1.getEofd_content());
				cm1.setEofc_em_base(m1.getEofd_em_base());
				cm1.setEofc_co_base(m1.getEofd_co_base());
				cm1.setEofc_cp(m1.getEofd_cp());
				cm1.setEofc_op(m1.getEofd_op());
				cm1.setEofc_em_sum(m1.getEofd_em_sum());
				cm1.setEofc_co_sum(m1.getEofd_co_sum());
				cm1.setEofc_month_sum(zero);
				cm1.setEofc_start_date(m1.getEofd_start_date());
				cm1.setEofc_state(1);
				// cm1.setTempDate(m1.getTempDate() == null ? null :
				// DateUtil.getLastDay(m1.getTempDate()));
				cm1.setTempDate(null);
				cm1.setSicl_class(m1.getSicl_class());

				cfeeList.add(cm1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将智翼通接口数据携带过来
	public void getEmZYTInfo() {
		if (emztM != null) {
			try {
				outdate = dsc.StringtoDate(emztM.getEmzt_outdate(),
						"yyyy-MM-dd");
			} catch (Exception e) {
				outdate = null;
			}

			// 离职原因
			try {
				outreason = emztM.getEmzt_outreason();
			} catch (Exception e) {
				outreason = "";
			}

			if (ifshebao) {
				try {
					sb_ownmonth = dsc.ownmonthAddoneMonth(dsc.DatetoSting(
							dsc.StringtoDate(emztM.getEmzt_ylstop(),
									"yyyy-MM-dd"), "yyyyMM"));

					// 获取传入所属月份的上月最后一天，如：传入201505 返回 2015-04-30
					sb_ownmonth = dBll.getLastMonthLastDay(sb_ownmonth);

					// 遍历下拉框列表，判断是否有对应的所属月份数据
					boolean chkifsb = false;
					for (int i = 0; i < sbownmonthList.length; i++) {
						if (sb_ownmonth.equals(sbownmonthList[i])) {
							chkifsb = true;
							break;
						}
					}
					if (chkifsb == false) {
						sb_ownmonth = "";
					}
				} catch (Exception e) {
					sb_ownmonth = "";
				}

				try {
					if (emztM.getEmzt_outreason().contains("非")) {
						sbStopReason = "非本人意愿中断就业";
					} else {
						if (emztM.getEmzt_outreason().contains("本人意愿")
								|| emztM.getEmzt_outreason().contains("自愿")
								|| emztM.getEmzt_outreason().contains("辞职")) {
							sbStopReason = "本人意愿中断就业";
						} else {
							sbStopReason = "非本人意愿中断就业";
						}
					}

				} catch (Exception e) {
					sbStopReason = "";
				}
			}

			if (ifgjj) {
				try {
					house_ownmonth = dsc.ownmonthAddoneMonth(dsc.DatetoSting(
							dsc.StringtoDate(emztM.getEmzt_housestop(),
									"yyyy-MM-dd"), "yyyyMM"));

					// 获取传入所属月份的上月最后一天，如：传入201505 返回 2015-04-30
					house_ownmonth = dBll.getLastMonthLastDay(house_ownmonth);

					// 遍历下拉框列表，判断是否有对应的所属月份数据
					boolean chkifhouse = false;
					for (int i = 0; i < gjjownmonthList.size(); i++) {
						if (house_ownmonth.equals(gjjownmonthList.get(i))) {
							chkifhouse = true;
							break;
						}
					}
					if (chkifhouse == false) {
						house_ownmonth = "";
					}
				} catch (Exception e) {
					house_ownmonth = "";
				}
			}

		}
	}

	@Command("emcBrowse")
	@NotifyChange("emcFilename")
	public void emcBrowse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if (media.getFormat().equals("txt")) {
			this.media = null;
			Messagebox.show("无法上传txt文件。", "操作提示", Messagebox.OK,
					Messagebox.NONE);

		} else {
			setEmcFilename(media.getName());
		}
	}

	public String getEmcFilename() {
		return emcFilename;
	}

	public void setEmcFilename(String emcFilename) {
		this.emcFilename = emcFilename;
	}

	public EmbaseModel getEmM() {
		return emM;
	}

	public void setEmM(EmbaseModel emM) {
		this.emM = emM;
	}

	public List<CoOfferListModel> getCflList() {
		return cflList;
	}

	public void setCflList(List<Integer> list) {
		this.cflList = dBll.getCoofferlistByColiId(list);
	}

	public boolean isIfCflsit() {
		return ifCflsit;
	}

	public void setIfCflsit(boolean ifCflsit) {
		this.ifCflsit = ifCflsit;
	}

	public boolean isIfshebao() {
		return ifshebao;
	}

	public void setIfshebao(boolean ifshebao) {
		this.ifshebao = ifshebao;
	}

	public boolean isIfshebaobj() {
		return ifshebaobj;
	}

	public void setIfshebaobj(boolean ifshebaobj) {
		this.ifshebaobj = ifshebaobj;
	}

	public boolean isIfgjj() {
		return ifgjj;
	}

	public void setIfgjj(boolean ifgjj) {
		this.ifgjj = ifgjj;
	}

	public boolean isIfwt() {
		return ifwt;
	}

	public void setIfwt(boolean ifwt) {
		this.ifwt = ifwt;
	}

	public boolean isIfshangbao() {
		return ifshangbao;
	}

	public void setIfshangbao(boolean ifshangbao) {
		this.ifshangbao = ifshangbao;
	}

	public boolean isIfda() {
		return ifda;
	}

	public void setIfda(boolean ifda) {
		this.ifda = ifda;
	}

	public boolean isIfsbStop() {
		return ifsbStop;
	}

	public void setIfsbStop(boolean ifsbStop) {
		this.ifsbStop = ifsbStop;
	}

	public boolean isSbaud() {
		return sbaud;
	}

	public void setSbaud(boolean sbaud) {
		this.sbaud = sbaud;
	}

	public String[] getSbownmonthList() {
		return sbownmonthList;
	}

	public void setSbownmonthList(String[] sbownmonthList) {
		this.sbownmonthList = sbownmonthList;
	}

	public boolean isIfemc() {
		return ifemc;
	}

	public void setIfemc(boolean ifemc) {
		this.ifemc = ifemc;
	}

	public boolean isIfeeriStop() {
		return ifeeriStop;
	}

	public void setIfeeriStop(boolean ifeeriStop) {
		this.ifeeriStop = ifeeriStop;
	}

	public List<String> getGjjownmonthList() {
		return gjjownmonthList;
	}

	public void setGjjownmonthList(List<String> gjjownmonthList) {
		this.gjjownmonthList = gjjownmonthList;
	}

	public boolean isGjjJudge() {
		return gjjJudge;
	}

	public void setGjjJudge(boolean gjjJudge) {
		this.gjjJudge = gjjJudge;
	}

	public String[] getSbbjownmonthList() {
		return sbbjownmonthList;
	}

	public void setSbbjownmonthList(String[] sbbjownmonthList) {
		this.sbbjownmonthList = sbbjownmonthList;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_castsortout() {
		return ci_insurant_castsortout;
	}

	public void setCi_insurant_castsortout(
			ListModelList<CI_Insurant_ListModel> ci_insurant_castsortout) {
		this.ci_insurant_castsortout = ci_insurant_castsortout;
	}

	public EmCommissionOutChangeModel getWtcm() {
		return wtcm;
	}

	public void setWtcm(EmCommissionOutChangeModel wtcm) {
		this.wtcm = wtcm;
	}

	public List<String> getCauseList() {
		return causeList;
	}

	public void setCauseList(List<String> causeList) {
		this.causeList = causeList;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getCfeeList() {
		return cfeeList;
	}

	public void setCfeeList(List<EmCommissionOutFeeDetailChangeModel> cfeeList) {
		this.cfeeList = cfeeList;
	}

	public EmCommissionOutModel getWtm() {
		return wtm;
	}

	public void setWtm(EmCommissionOutModel wtm) {
		this.wtm = wtm;
	}

	public List<EmbaseModel> getDaEmbalist() {
		return daEmbalist;
	}

	public void setDaEmbalist(List<EmbaseModel> daEmbalist) {
		this.daEmbalist = daEmbalist;
	}

	public String getDaContent() {
		return daContent;
	}

	public void setDaContent(String daContent) {
		this.daContent = daContent;
	}

	public String getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}

	public String[] getEeriStopreason() {
		return eeriStopreason;
	}

	public void setEeriStopreason(String[] eeriStopreason) {
		this.eeriStopreason = eeriStopreason;
	}

	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	public String getSb_ownmonth() {
		return sb_ownmonth;
	}

	public void setSb_ownmonth(String sb_ownmonth) {
		this.sb_ownmonth = sb_ownmonth;
	}

	public String getHouse_ownmonth() {
		return house_ownmonth;
	}

	public void setHouse_ownmonth(String house_ownmonth) {
		this.house_ownmonth = house_ownmonth;
	}

	public String getSbStopReason() {
		return sbStopReason;
	}

	public void setSbStopReason(String sbStopReason) {
		this.sbStopReason = sbStopReason;
	}

	public String getOutreason() {
		return outreason;
	}

	public void setOutreason(String outreason) {
		this.outreason = outreason;
	}

	public boolean isDa_chk() {
		return da_chk;
	}

	public void setDa_chk(boolean da_chk) {
		this.da_chk = da_chk;
	}

	public boolean isIfgjjbj() {
		return ifgjjbj;
	}

	public void setIfgjjbj(boolean ifgjjbj) {
		this.ifgjjbj = ifgjjbj;
	}

	public String[] getGjjbjownmonthList() {
		return gjjbjownmonthList;
	}

	public void setGjjbjownmonthList(String[] gjjbjownmonthList) {
		this.gjjbjownmonthList = gjjbjownmonthList;
	}

}
