package Controller.EmCommercialInsurance;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.CI_Insurant_ListModel;
import Model.EmCommissionOutChangeModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.RedirectUtil;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;
import bll.EmPay.EmPay_OperateBll;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmCommissionOut.EmCommissionOut_ListDal;

public class CI_Insurant_Controller {
	private String gid;
	private int gid1;
	private String cid;
	private int tb_id;
	private boolean check_ciin = false;
	private Datebox dbbirth;
	private String check_ciinMessage;
	CI_Insurant_ListBll bll = new CI_Insurant_ListBll();
	private CI_Insurant_OperateBll ccsaBll = new CI_Insurant_OperateBll();
	private EmPay_OperateBll payBll = new EmPay_OperateBll();

	private int step;

	private ListModelList<CI_Insurant_ListModel> coli_st;// 显示所选商保类型

	private ListModelList<CI_Insurant_ListModel> coli_lst;// 显示所选商保连带人类型

	private ListModelList<CI_Insurant_ListModel> buycount_lst;// 显示所选商保份数

	private ListModelList<CI_Insurant_ListModel> getapply_gid;// 获取gid

	private ListModelList<CI_Insurant_ListModel> coli_emailst;// 判断邮箱是否录入

	private List<EmCommissionOutChangeModel> eclist;// 获取任务单

	private EmbaseModel ebm = new EmbaseModel();

	// 查询客服
	CoLatencyClient_AddBll blllogin = new CoLatencyClient_AddBll();
	List<String> loginlist = blllogin.getLoginInfo();
	int coats1 = 0;
	int coats2 = 0;
	String coastse1 = "";
	String coastse2 = "";

	public CI_Insurant_Controller() {
		step = 0;
		gid = "0";
		cid = "0";

		try {
			gid = Executions.getCurrent().getArg().get("gid").toString();
		} catch (Exception e) {
			gid = "0";
		}

		try {
			// 1；为员工业务中心，2；入职第二页
			step = Integer.parseInt(Executions.getCurrent().getArg()
					.get("step").toString());
		} catch (Exception e) {
			step = 0;
			// TODO: handle exception
		}

		try {
			tb_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
		} catch (Exception e) {
			tb_id = 0;
		}

		System.out.print(tb_id);
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();

		try {

			if (gid.equals("0")) {
				getapply_gid = new ListModelList<CI_Insurant_ListModel>(
						dal.gettb_gid(tb_id));

				coli_lst = new ListModelList<CI_Insurant_ListModel>(
						dal.getcoli_l(Integer.parseInt(getapply_gid.get(0)
								.getGid())));
			} else {
				coli_st = new ListModelList<CI_Insurant_ListModel>(
						dal.getcoli(Integer.parseInt(gid)));

				getapply_gid = new ListModelList<CI_Insurant_ListModel>(
						dal.gettb_gid(tb_id));

				coli_emailst = new ListModelList<CI_Insurant_ListModel>(
						dal.getcoli_emailst(Integer.parseInt(gid)));

				coli_lst = new ListModelList<CI_Insurant_ListModel>(
						dal.getcoli_l(Integer.parseInt(gid)));
			}
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 显示所选商保类型
			// if (checkInit())
			// return;
		try {
			init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Window winC;

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;

		if (coli_st.get(0).getCoba_client().equals("0")) {
			Messagebox.show("该员工未分配商业保险项目，请分配后在添加!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			winC.detach();
		}

		if (coli_emailst.get(0).getCoba_client().equals("0")) {
			Messagebox.show("该员工未录入商保理赔邮箱，请在员工基本信息页面录入!", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			winC.detach();
		}
	}

	private Window sbl_w1;

	@Command("winL")
	public void winL(@BindingParam("a") Window winD) {
		sbl_w1 = winD;

		if (coli_lst.get(0).getCoba_client().equals("0")) {
			Messagebox.show("该员工未分配商业保险连带人项目，请分配后在添加!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			sbl_w1.detach();
		}
	}

	@Command("buycount_on")
	@NotifyChange("buycount_lst")
	public void buycount_on(@BindingParam("emin") CI_Insurant_ListModel emin)
			throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		buycount_lst = new ListModelList<CI_Insurant_ListModel>(
				dal.getbuycount_lst(Integer.parseInt(emin.getGid()),
						emin.getEcin_castsort()));
	}

	// 初始化检查
	private boolean checkInit() {
		if (bll.check_ciin(Integer.parseInt(gid))) {
			check_ciin = true;
			System.out.println(check_ciin);
			check_ciinMessage = "已有该员工商保信息，不能新增!";
			return check_ciin;
		}
		return false;
	}

	// 新增商保
	@Command("ci_insurant_add")
	public void ci_insurant_add(@BindingParam("gridco") Grid gridco,
			@BindingParam("gridco2") Grid gridco2,
			@BindingParam("gridco3") Grid gridco3,
			@BindingParam("embase1") Textbox embase1,
			@BindingParam("embase2") Textbox embase2,
			@BindingParam("embase3") Textbox embase3,
			@BindingParam("embase4") Textbox embase4,
			@BindingParam("embase5") Textbox embase5,
			@BindingParam("embase6") Textbox embase6,
			@BindingParam("ch1") Checkbox ld_state,
			@BindingParam("ch2") Checkbox ld_state2,
			@BindingParam("fl_date") Datebox f_date,
			@BindingParam("sb_w1") Window sb_w1) throws Exception {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();

		Textbox ecin_insurant = (Textbox) gridco2.getCell(0, 1).getChildren()
				.get(0);
		Textbox ecin_idcard = (Textbox) gridco2.getCell(0, 3).getChildren()
				.get(0);
		Textbox ecin_sex = (Textbox) gridco2.getCell(1, 1).getChildren().get(0);
		Textbox ecin_birthday = (Textbox) gridco2.getCell(1, 3).getChildren()
				.get(0);

		String ecin_message = "";
		String ecin_messagestate = "";

		// 数据合法性判断
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Label ecin_castsort = (Label) gridco.getCell(i, 1).getChildren()
					.get(0);
			Combobox ecin_buy_count = (Combobox) gridco.getCell(i, 2)
					.getChildren().get(0);
			Datebox fact_date = (Datebox) gridco.getCell(i, 3).getChildren()
					.get(0);
			Combobox city = (Combobox) gridco2.getCell(2, 1).getChildren()
					.get(0);
			Textbox work = (Textbox) gridco2.getCell(2, 3).getChildren().get(0);
			Combobox compact_qd = (Combobox) gridco2.getCell(3, 1)
					.getChildren().get(0);
			Textbox birthday = (Textbox) gridco2.getCell(1, 3).getChildren()
					.get(0);
			Textbox idcard = (Textbox) gridco2.getCell(0, 3).getChildren()
					.get(0);

			String ef_date = "";
			if (fact_date.getValue() != null) {
				ef_date = ccsaBll.DatetoSting(fact_date.getValue());
			}

			String inmessage[] = dal.getallstate(birthday.getValue(),
					idcard.getValue(), ecin_castsort.getValue(),
					ecin_buy_count.getValue(), ef_date, city.getValue(),
					work.getValue(), compact_qd.getValue());

			if (!inmessage[0].equals("0")) {
				ecin_message = ecin_message + ecin_castsort.getValue() + "："
						+ inmessage[1] + "  ";
				ecin_messagestate = ecin_messagestate + inmessage[0];
			}
		}

		//for (int i = 0; i < gridco3.getRows().getChildren().size(); i++) {
			//Grid gd = (Grid) gridco3.getCell(i, 0);
			// Label ecin_castsort = (Label) gd.getCell(0,
			// 1).getChildren().get(0);
			//Checkbox ld_state = (Checkbox) gd.getCell(0, 1).getChildren()
			//		.get(0);
			//Datebox f_date = (Datebox) gd.getCell(0, 3).getChildren().get(0);

			//if (f_date.getValue() != null) {
			//	lef_date = ccsaBll.DatetoSting(f_date.getValue());
			//}
			// Datebox fact_date = (Datebox) gd.getCell(0,
			// 3).getChildren().get(0);
			// Textbox name = (Textbox) gd.getCell(1, 1).getChildren().get(0);
			// Textbox idcard = (Textbox) gd.getCell(1, 3).getChildren().get(0);
			// Textbox sex = (Combobox) gd.getCell(2, 1).getChildren().get(0);
			// Datebox birthday = (Datebox) gd.getCell(2,
			// 3).getChildren().get(0);
			// Label ecin_sconnection = (Label) gd.getCell(3,
			// 1).getChildren().get(0);
		
		String lef_date = "";
		String castosrt_ad = "";

		int getldl = 0;
		String getld = "";

		if (f_date.getValue() != null) {
			lef_date = ccsaBll.DatetoSting(f_date.getValue());
		}
		
		if (ld_state.isChecked() || ld_state2.isChecked()) {
			getldl = 1;

			if (lef_date.equals("")) {
				Messagebox.show("请录入连带人的生效时间", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				// System.out.println("2");
				return;
			}
			
			if (ld_state.isChecked()) {
				castosrt_ad = coastse2;
			}

			if (ld_state2.isChecked()) {
				castosrt_ad = castosrt_ad + coastse1;
			}
			getld = "51";
		} else {
			getld = "";
			//Messagebox.show("请勾选是否购买连带人险", "操作提示", Messagebox.OK,
			//		Messagebox.ERROR);
			// System.out.println("err");
			//return;

		}
			/*
			 * String birth_date = ""; if (birthday.getValue() != null) {
			 * birth_date = ccsaBll.DatetoSting(birthday.getValue()); }
			 * 
			 * if (!name.getValue().equals("") && ef_date !=
			 * null&&!birth_date.equals("")) { if (name.getValue().equals("")) {
			 * Messagebox.show("请录入连带人姓名", "操作提示", Messagebox.OK,
			 * Messagebox.ERROR); return; } if (birth_date.equals("")) {
			 * Messagebox.show("请录入连带人出生日期", "操作提示", Messagebox.OK,
			 * Messagebox.ERROR); return; } if (sex.getValue().equals("")) {
			 * Messagebox.show("请录入连带人性别", "操作提示", Messagebox.OK,
			 * Messagebox.ERROR); return; }
			 * 
			 * if
			 * (ecin_sconnection.getValue().equals("配偶")&&idcard.getValue().equals
			 * ("")) { Messagebox.show("配偶必须录入身份证号码", "操作提示", Messagebox.OK,
			 * Messagebox.ERROR); return; }
			 * 
			 * String inmessage[] = dal.getallstate(birth_date,
			 * idcard.getValue(), ecin_castsort.getValue(), "", ef_date, "",
			 * "");
			 * 
			 * if (!inmessage[0].equals("0")) { ecin_message = ecin_message +
			 * ecin_castsort.getValue() + "：" + inmessage[1] + "  ";
			 * ecin_messagestate = ecin_messagestate + inmessage[0]; } }
			 */
		//}

		if (!ecin_message.equals("")) {
			System.out.println(ecin_message);
			Messagebox.show(ecin_message, "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			if (!ecin_messagestate.equals("")) {
				return;
			}
		}

		// 主险人新增
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Label ecin_castsort = (Label) gridco.getCell(i, 1).getChildren()
					.get(0);
			Combobox ecin_buy_count = (Combobox) gridco.getCell(i, 2)
					.getChildren().get(0);
			Datebox fact_date = (Datebox) gridco.getCell(i, 3).getChildren()
					.get(0);
			Combobox city = (Combobox) gridco2.getCell(2, 1).getChildren()
					.get(0);
			Textbox work = (Textbox) gridco2.getCell(2, 3).getChildren().get(0);
			Combobox compact_qd = (Combobox) gridco2.getCell(3, 1)
					.getChildren().get(0);
			String ef_date = "";
			if (fact_date.getValue() != null) {
				ef_date = ccsaBll.DatetoSting(fact_date.getValue());
			}

			if (!ef_date.equals("") && ef_date != null) {

				List<CI_Insurant_ListModel> list = dal.getallcid(gid);

				// 合同新增
				String[] message = ccsaBll.add_insurant(gid, list.get(0)
						.getCid(), ecin_castsort.getValue(), ecin_buy_count
						.getValue(), ef_date, ecin_insurant.getValue(),
						ecin_idcard.getValue(), ecin_sex.getValue(),
						ecin_birthday.getValue(), city.getValue(), work
								.getValue(), "", getld, compact_qd.getValue());

				// 弹出提示并跳转页面
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// Executions.sendRedirect("Compact_SginList.zul");
								// //跳转页面
								// w1.detach();

								RedirectUtil util = new RedirectUtil();
								if (step == 1) {
									// 员工业务中心跳转方法:
									util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接

								} else if (step == 2) {

									// 入职第二页跳转方法:
									util.refreshEntrySecondUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
								}

							}
						}
					};

				} else {
					// 弹出提示
					// Messagebox.show(message[1], "操作提示", Messagebox.OK,
					// Messagebox.ERROR);
				}
			}
		}

		if (step == 1) {
			if (getld.equals("51")) {
				System.out.println("00000---00000---xxxx");
				System.out.println(gid);

				List<CI_Insurant_ListModel> list2 = dal.getallcid(gid);
				
				// 连带人新增申请
				String[] message = ccsaBll.apply_insurant(gid, list2.get(0).getCid(),
						getldl, lef_date, castosrt_ad);
			}
		}

		/*
		 * // 连带人新增 for (int i = 0; i < gridco3.getRows().getChildren().size();
		 * i++) { Grid gd = (Grid) gridco3.getCell(i, 0); Label ecin_castsort =
		 * (Label) gd.getCell(0, 1).getChildren().get(0); Datebox fact_date =
		 * (Datebox) gd.getCell(0, 3).getChildren().get(0); Textbox name =
		 * (Textbox) gd.getCell(1, 1).getChildren().get(0); Textbox idcard =
		 * (Textbox) gd.getCell(1, 3).getChildren().get(0); Textbox sex =
		 * (Textbox) gd.getCell(2, 1).getChildren().get(0); Datebox birthday =
		 * (Datebox) gd.getCell(2, 3).getChildren().get(0);
		 * 
		 * String ef_date = ""; if (fact_date.getValue() != null) { ef_date =
		 * ccsaBll.DatetoSting(fact_date.getValue()); }
		 * 
		 * String birth_date = ""; if (birthday.getValue() != null) { birth_date
		 * = ccsaBll.DatetoSting(birthday.getValue()); }
		 * 
		 * if (!name.getValue().equals("") && ef_date !=
		 * null&&!birth_date.equals("")) {
		 * 
		 * List<CI_Insurant_ListModel> list = dal.getallcid(gid); // 合同新增
		 * String[] message = ccsaBll.add_insurant(gid, list.get(0) .getCid(),
		 * ecin_castsort.getValue(), "1", ef_date, name .getValue(),
		 * idcard.getValue(), sex.getValue(), birth_date, "", "",
		 * ecin_insurant.getValue(),""); // 弹出提示并跳转页面 if
		 * (message[0].equals("1")) { EventListener<ClickEvent> clickListener =
		 * new EventListener<Messagebox.ClickEvent>() { public void
		 * onEvent(ClickEvent event) throws Exception { if
		 * (Messagebox.Button.OK.equals(event.getButton())) { //
		 * Executions.sendRedirect("Compact_SginList.zul"); // //跳转页面 //
		 * w1.detach();
		 * 
		 * 
		 * } } };
		 * 
		 * } else { // 弹出提示 Messagebox.show(message[1], "操作提示", Messagebox.OK,
		 * Messagebox.ERROR); } } }
		 */
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);

		Map map = new HashMap<>();
		map.put("url", "../EmCommercialInsurance/CI_Insurant_Base.zul");// 跳转的页面连接
		BindUtils.postGlobalCommand(null, null, "refreshEmUrl", map);

		// sb_w1.detach();

		// 跳转页面：赵敏捷2015-04-10修改
		RedirectUtil util = new RedirectUtil();
		if (step == 1) {
			// 员工业务中心跳转方法:
			util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
		} else if (step == 2) {

			// 入职第二页跳转方法:
			util.refreshEntrySecondUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
		}

	}

	// 单独新增商保连带人
	@Command("ci_insurant_linkadd")
	public void ci_insurant_linkadd(@BindingParam("gridco3") Grid gridco3,
			@BindingParam("sbl_w1") Window sbl_w1) throws Exception {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		String ecin_message = "";
		String ecin_messagestate = "";

		// 数据合法性判断
		for (int i = 0; i < gridco3.getRows().getChildren().size(); i++) {
			Grid gd = (Grid) gridco3.getCell(i, 0);
			Textbox ecin_insurant = (Textbox) gd.getCell(1, 1).getChildren()
					.get(0);
			Label ecin_castsort = (Label) gd.getCell(0, 1).getChildren().get(0);
			Datebox fact_date = (Datebox) gd.getCell(0, 3).getChildren().get(0);
			Textbox name = (Textbox) gd.getCell(1, 1).getChildren().get(0);
			Textbox idcard = (Textbox) gd.getCell(1, 3).getChildren().get(0);
			Textbox sex = (Textbox) gd.getCell(2, 1).getChildren().get(0);
			Datebox birthday = (Datebox) gd.getCell(2, 3).getChildren().get(0);
			Label ecin_sconnection = (Label) gd.getCell(3, 1).getChildren()
					.get(0);

			String ef_date = "";
			if (fact_date.getValue() != null) {
				ef_date = ccsaBll.DatetoSting(fact_date.getValue());
			}

			String birth_date = "";
			if (birthday.getValue() != null) {
				birth_date = ccsaBll.DatetoSting(birthday.getValue());
			}

			if (!name.getValue().equals("") && ef_date != null
					&& !birth_date.equals("")) {
				if (name.getValue().equals("")) {
					Messagebox.show("请录入连带人姓名", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				if (birth_date.equals("")) {
					Messagebox.show("请录入连带人出生日期", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				if (sex.getValue().equals("")) {
					Messagebox.show("请录入连带人性别", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				if (ecin_sconnection.getValue().equals("配偶")
						&& idcard.getValue().equals("")) {
					Messagebox.show("配偶必须录入身份证号码", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}

				String inmessage[] = dal.getallstate(birth_date,
						idcard.getValue(), ecin_castsort.getValue(), "",
						ef_date, "", "", "");

				if (!inmessage[0].equals("0")) {
					ecin_message = ecin_message + ecin_castsort.getValue()
							+ "：" + inmessage[1] + "  ";
					ecin_messagestate = ecin_messagestate + inmessage[0];
				}
			}
		}

		if (!ecin_message.equals("")) {
			Messagebox.show(ecin_message, "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			if (!ecin_messagestate.equals("")) {
				return;
			}
		}

		// 连带人新增
		for (int i = 0; i < gridco3.getRows().getChildren().size(); i++) {
			Grid gd = (Grid) gridco3.getCell(i, 0);
			Textbox ecin_insurant = (Textbox) gd.getCell(1, 1).getChildren()
					.get(0);
			Label ecin_castsort = (Label) gd.getCell(0, 1).getChildren().get(0);
			Datebox fact_date = (Datebox) gd.getCell(0, 3).getChildren().get(0);
			Textbox name = (Textbox) gd.getCell(1, 1).getChildren().get(0);
			Textbox idcard = (Textbox) gd.getCell(1, 3).getChildren().get(0);
			Textbox sex = (Textbox) gd.getCell(2, 1).getChildren().get(0);
			Datebox birthday = (Datebox) gd.getCell(2, 3).getChildren().get(0);
			Label ecin_sconnection = (Label) gd.getCell(3, 1).getChildren()
					.get(0);

			String ef_date = "";
			if (fact_date.getValue() != null) {
				ef_date = ccsaBll.DatetoSting(fact_date.getValue());
			}

			String birth_date = "";
			if (birthday.getValue() != null) {
				birth_date = ccsaBll.DatetoSting(birthday.getValue());
			}

			if (!name.getValue().equals("") && ef_date != null
					&& !birth_date.equals("")) {

				System.out.println("aaa-aaa");
				System.out.println(gid);
				// CI_Insurant_ListDal dal = new CI_Insurant_ListDal();

				if (gid.equals("0")) {
					gid = getapply_gid.get(0).getGid();
				}

				List<CI_Insurant_ListModel> list = dal.getallcid(gid);
				// 合同新增
				String[] message = ccsaBll.add_insurant(gid, list.get(0)
						.getCid(), ecin_castsort.getValue(), "1", ef_date, name
						.getValue(), idcard.getValue(), sex.getValue(),
						birth_date, "", "", name.getValue(), "", "");
				// 弹出提示并跳转页面
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// Executions.sendRedirect("Compact_SginList.zul");
								// //跳转页面
								// w1.detach();
							}
						}
					};

				} else {
					// 弹出提示
					// Messagebox.show(message[1], "操作提示", Messagebox.OK,
					// Messagebox.ERROR);
				}
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		// Executions.sendRedirect("/Embase/Embase_List.zul");
		sbl_w1.detach();

		// 跳转页面：赵敏捷2015-04-10修改
		RedirectUtil util = new RedirectUtil();
		if (step == 1) {
			// 员工业务中心跳转方法:
			util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
		} else if (step == 2) {

			// 入职第二页跳转方法:
			util.refreshEntrySecondUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
		}

	}

	// 商保停缴
	@Command("ci_insurant_out")
	public void ci_insurant_out(@BindingParam("gridco") Grid gridco,
			@BindingParam("gid") Label gid, @BindingParam("cid") Label cid,
			@BindingParam("sb_outw1") Window sb_outw1) throws Exception {

		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式

		SimpleDateFormat dateFormattime = new SimpleDateFormat("HH");

		SimpleDateFormat nowdateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式

		int datetime = Integer.parseInt(dateFormattime.format(now));

		int nowdatetime = Integer.parseInt(nowdateFormat.format(now));

		String nowtime = dateFormat.format(now);

		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Label ecin_castsort = (Label) gridco.getCell(i, 2).getChildren()
					.get(0);
			Datebox fact_date = (Datebox) gridco.getCell(i, 4).getChildren()
					.get(0);

			String st_date = "";
			if (fact_date.getValue() != null) {
				st_date = ccsaBll.DatetoSting(fact_date.getValue());
			}

			if (!st_date.equals("") && st_date != null) {
				int stdatetime = Integer.parseInt(nowdateFormat
						.format(fact_date.getValue()));

				if (ecin_castsort.getValue().substring(0, 1).equals("雇")
						&& datetime > 17) {
					Messagebox.show("雇主险停缴只能在当天17点前操作！请在明天操作停缴！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}

				if (ecin_castsort.getValue().substring(0, 1).equals("增")
						&& datetime > 17) {
					Messagebox.show("雇主险停缴只能在当天17点前操作！请在明天操作停缴！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}

				if (stdatetime < nowdatetime
						&& ecin_castsort.getValue().substring(0, 1).equals("雇")) {
					Messagebox.show("雇主险停缴只能停缴当天或之后天数，请注意停缴时间！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}

				if (stdatetime < nowdatetime
						&& ecin_castsort.getValue().substring(0, 1).equals("增")) {
					Messagebox.show("雇主险停缴只能停缴当天或之后天数，请注意停缴时间！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
		}

		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Label ecin_castsort = (Label) gridco.getCell(i, 2).getChildren()
					.get(0);
			Label ecin_id = (Label) gridco.getCell(i, 6).getChildren().get(0);
			Datebox fact_date = (Datebox) gridco.getCell(i, 4).getChildren()
					.get(0);

			String st_date = "";
			if (fact_date.getValue() != null) {
				st_date = ccsaBll.DatetoSting(fact_date.getValue());
			}

			if (!st_date.equals("") && st_date != null) {
				// 合同新增
				String[] message = ccsaBll.out_insurant(gid.getValue(),
						cid.getValue(), ecin_castsort.getValue(), st_date,
						ecin_id.getValue());
				// 弹出提示并跳转页面
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// Executions.sendRedirect("Compact_SginList.zul");
								// //跳转页面
								// w1.detach();
							}
						}
					};

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		// Executions.sendRedirect("/Embase/Embase_List.zul");
		// sb_outw1.detach();

		// 跳转页面：赵敏捷2015-04-10修改
		RedirectUtil util = new RedirectUtil();
		// if (step == 1) {
		// 员工业务中心跳转方法:
		util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
		// } else if (step == 2) {

		// 入职第二页跳转方法:
		// util.refreshEntrySecondUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");//
		// url为跳转页面连接
		// }

	}

	// 商保停缴
	@Command("dateAll")
	public void dateAll(@BindingParam("gridco") Grid gridco) throws Exception {

		Datebox dateString = (Datebox) gridco.getCell(0, 4).getChildren()
				.get(0);

		String va_date = "";
		if (dateString.getValue() != null) {
			va_date = ccsaBll.DatetoSting(dateString.getValue());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateva = sdf.parse(va_date); // 这个方法是把时间格式转换为字符串格式。

		for (int i = 1; i < gridco.getRows().getChildren().size(); i++) {
			Datebox fact_date = (Datebox) gridco.getCell(i, 4).getChildren()
					.get(0);
			fact_date.setValue(dateva);
		}
	}

	// 商保审核
	@Command("ci_insurant_aut")
	public void ci_insurant_aut(@BindingParam("gridco") Grid gridco)
			throws Exception {
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 7).getChildren().get(0);

			Label tapr_id = (Label) gridco.getCell(i, 8).getChildren().get(0);

			if (chk.isChecked()) {
				String[] message = ccsaBll.aut_insurant((int) chk.getValue(),
						Integer.parseInt(tapr_id.getValue()));
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_Insurant_Aut.zul");
	}

	// 商保申报
	@Command("ci_insurant_autup")
	public void ci_insurant_autup(@BindingParam("gridco") Grid gridco)
			throws Exception {

		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			try {
				Checkbox chk = (Checkbox) gridco.getCell(i, 9).getChildren()
						.get(0);

				Label tapr_id = (Label) gridco.getCell(i, 10).getChildren()
						.get(0);

				if (chk.isChecked()) {
					String[] message = ccsaBll.autup_insurant(
							(int) chk.getValue(),
							Integer.parseInt(tapr_id.getValue()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_Insurant_AutUp.zul");
	}

	// 商保支付
	@Command("ci_insurant_pay")
	public void ci_insurant_pay(@BindingParam("gridco") Grid gridco)
			throws Exception {

		String nowmonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String paynum = "SB" + nowtime;
		String ownmonth = nowmonth;
		String type = "商保费";

		// 添加支付明细
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 8).getChildren().get(0);
			Label fee = (Label) gridco.getCell(i, 6).getChildren().get(0);
			Label tid = (Label) gridco.getCell(i, 10).getChildren().get(0);
			Label gid = (Label) gridco.getCell(i, 11).getChildren().get(0);
			Label cid = (Label) gridco.getCell(i, 12).getChildren().get(0);

			if (chk.isChecked()) {
				int message1 = bll.pay_insurant((int) chk.getValue(), paynum,
						ownmonth);
				int add_message = payBll.add_pay(gid.getValue(),
						cid.getValue(), paynum, ownmonth, type, fee.getValue(),
						tid.getValue());
			}
		}

		// 更新支付费用
		String[] message = payBll.up_pay(paynum, ownmonth, type);
		// 弹出提示并跳转页面
		if (message[0].equals("1")) {
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			Executions.sendRedirect("CI_Insurant_AddPay.zul");

		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	// 商保审核查询
	@Command("search")
	@NotifyChange("ci_insurant_list")
	public void search(@BindingParam("castsort") Combobox castsort)
			throws Exception {
		ci_insurant_list = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_list(castsort.getValue()));// 显示未审核列表
	}

	// 商保审核查询
	@Command("ch_search")
	@NotifyChange("ci_insurant_chlist")
	public void ch_search(@BindingParam("castsort") Combobox castsort,
			@BindingParam("change") Combobox change,
			@BindingParam("state") Combobox state,
			@BindingParam("bm") Combobox bm, @BindingParam("bx") Combobox bx,
			@BindingParam("sb_date") Datebox sb_date,
			@BindingParam("name") Textbox name,
			@BindingParam("client") Combobox client) throws Exception {
		String da_date = "";
		if (sb_date.getValue() != null) {
			da_date = ccsaBll.DatetoSting(sb_date.getValue());
		}

		ci_insurant_chlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_chlist(castsort.getValue(), change.getValue(),
						state.getValue(), bm.getValue(), bx.getValue(),
						da_date, name.getValue(), client.getValue()));// 显示查询信息
	}

	// 商保支付查询
	@Command("search_pay")
	@NotifyChange("ci_insurant_paylist")
	public void search_pay(@BindingParam("castsort") Combobox castsort,
			@BindingParam("date_list") Combobox date_list) throws Exception {
		ci_insurant_paylist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_paylist(castsort.getValue()));// 显示支付列表
	}

	// 商保申报查询
	@Command("search_up")
	@NotifyChange("ci_insurant_autlist")
	public void search_up(@BindingParam("castsort") Combobox castsort,
			@BindingParam("de_date") Combobox de_date) throws Exception {
		ci_insurant_autlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_autlist(castsort.getValue(), de_date.getValue()));// 显示审核列表
	}

	// 导出在册数据报表
	@Command("ch_down")
	public void ch_down(@BindingParam("castsort") Combobox castsort,
			@BindingParam("change") Combobox change,
			@BindingParam("state") Combobox state,
			@BindingParam("bm") Combobox bm, @BindingParam("bx") Combobox bx,
			@BindingParam("sb_date") Datebox sb_date,
			@BindingParam("name") Textbox name,
			@BindingParam("client") Combobox client) throws Exception {

		String da_date = "";
		if (sb_date.getValue() != null) {
			da_date = ccsaBll.DatetoSting(sb_date.getValue());
		}
		ci_zc_excel = new ListModelList<CI_Insurant_ListModel>(bll.ci_zc_excel(
				castsort.getValue(), change.getValue(), state.getValue(),
				bm.getValue(), bx.getValue(), da_date, name.getValue(),
				client.getValue()));// 显示保险在保类型

		String filename = ""; // 文件名
		String absolutePath; // 服务器地址
		String filetype = ".xls"; // 文件类型
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);

		filename = nowtime + filetype;
		absolutePath = FileOperate.getAbsolutePath();

		// 创建exce
		// 读取Excel模板
		Workbook wb = Workbook.getWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Templet/sy_zc.xls"));
		// 使用模板创建
		WritableWorkbook wwb = Workbook.createWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Download/Em_in/" + filename), wb);
		// 生成工作表
		WritableSheet sheet = wwb.getSheet(0);
		// Label label1 = null;
		int rowdata = 0;
		String pid = "";
		for (int i = 0; i < ci_zc_excel.getSize(); i++) {

			rowdata = rowdata + 1;

			pid = String.valueOf(rowdata);
			jxl.write.Label label1 = new jxl.write.Label(0, rowdata, pid);
			sheet.addCell(label1);
			jxl.write.Label label4 = new jxl.write.Label(1, rowdata,
					ci_zc_excel.get(i).getEcin_insurant());
			sheet.addCell(label4);
			jxl.write.Label label8 = new jxl.write.Label(2, rowdata,
					ci_zc_excel.get(i).getEcin_idcard());
			sheet.addCell(label8);
			jxl.write.Label label9 = new jxl.write.Label(3, rowdata,
					ci_zc_excel.get(i).getEcin_sex());
			sheet.addCell(label9);
			jxl.write.Label label10 = new jxl.write.Label(4, rowdata,
					ci_zc_excel.get(i).getEcin_birthday());
			sheet.addCell(label10);
			jxl.write.Label label111 = new jxl.write.Label(5, rowdata,
					ci_zc_excel.get(i).getEcin_work_st());
			sheet.addCell(label111);
			jxl.write.Label label6 = new jxl.write.Label(6, rowdata,
					ci_zc_excel.get(i).getEcin_sconnection());
			sheet.addCell(label6);
			jxl.write.Label label5 = new jxl.write.Label(7, rowdata,
					ci_zc_excel.get(i).getEcin_insurer());
			sheet.addCell(label5);
			jxl.write.Label label15 = new jxl.write.Label(8, rowdata,
					ci_zc_excel.get(i).getEcin_zidcard());
			sheet.addCell(label15);
			jxl.write.Label label14 = new jxl.write.Label(9, rowdata,
					ci_zc_excel.get(i).getEcin_in_date());
			sheet.addCell(label14);
			jxl.write.Label label25 = new jxl.write.Label(10, rowdata,
					ci_zc_excel.get(i).getEcin_st_date());
			sheet.addCell(label25);
			jxl.write.Label label26 = new jxl.write.Label(11, rowdata, "");
			sheet.addCell(label26);
			jxl.write.Label label27 = new jxl.write.Label(12, rowdata, "");
			sheet.addCell(label27);
			jxl.write.Label label217 = new jxl.write.Label(13, rowdata, "");
			sheet.addCell(label217);
			jxl.write.Label label11 = new jxl.write.Label(14, rowdata,
					ci_zc_excel.get(i).getEcin_castsort());
			sheet.addCell(label11);
			jxl.write.Label label12 = new jxl.write.Label(15, rowdata,
					ci_zc_excel.get(i).getEcin_buy_count());
			sheet.addCell(label12);
			jxl.write.Label label18 = new jxl.write.Label(16, rowdata,
					ci_zc_excel.get(i).getEcin_state());
			sheet.addCell(label18);
			jxl.write.Label label29 = new jxl.write.Label(17, rowdata, "");
			sheet.addCell(label29);
			jxl.write.Label label30 = new jxl.write.Label(18, rowdata, "");
			sheet.addCell(label30);
			jxl.write.Label label7 = new jxl.write.Label(19, rowdata,
					ci_zc_excel.get(i).getEcin_company());
			sheet.addCell(label7);
			jxl.write.Label label19 = new jxl.write.Label(20, rowdata,
					ci_zc_excel.get(i).getEcin_client());
			sheet.addCell(label19);
			jxl.write.Label label2 = new jxl.write.Label(21, rowdata,
					ci_zc_excel.get(i).getGid());
			sheet.addCell(label2);
			jxl.write.Label label3 = new jxl.write.Label(22, rowdata,
					ci_zc_excel.get(i).getCid());
			sheet.addCell(label3);
			jxl.write.Label label32 = new jxl.write.Label(23, rowdata,
					ci_zc_excel.get(i).getEcin_cl_count());
			sheet.addCell(label32);
			jxl.write.Label label312 = new jxl.write.Label(24, rowdata,
					ci_zc_excel.get(i).getEcin_in_date());
			sheet.addCell(label312);
		}

		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();

		FileOperate.download("EmCommercialInsurance/File/Download/Em_in/"
				+ filename);
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		// Executions.sendRedirect("CI_Insurant_Check.zul");

	}

	// 导出报表
	@Command("ci_insurant_down")
	public void ci_insurant_down(@BindingParam("gridco") Grid gridco)
			throws Exception {

		String filename = ""; // 文件名
		String absolutePath; // 服务器地址
		String filetype = ".xls"; // 文件类型
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);

		filename = nowtime + filetype;
		absolutePath = FileOperate.getAbsolutePath();

		// 创建exce
		// 读取Excel模板
		Workbook wb = Workbook.getWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Templet/sy_sb.xls"));
		// 使用模板创建
		WritableWorkbook wwb = Workbook.createWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Download/Em_in/" + filename), wb);
		// 生成工作表
		WritableSheet sheet = wwb.getSheet(0);
		// Label label1 = null;
		int rowdata = 3;
		String pid = "";
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 9).getChildren().get(0);

			Label tapr_id = (Label) gridco.getCell(i, 10).getChildren().get(0);

			if (chk.isChecked()) {
				String[] message = ccsaBll.aut_insurant((int) chk.getValue(),
						Integer.parseInt(tapr_id.getValue().toString()));
				if (i < gridco.getRows().getChildren().size())
					ci_excel = new ListModelList<CI_Insurant_ListModel>(
							bll.ci_excel(chk.getValue().toString()));// 显示保险在保类型
				rowdata = rowdata + 1;

				pid = String.valueOf(rowdata - 3);
				jxl.write.Label label1 = new jxl.write.Label(0, rowdata, pid);
				sheet.addCell(label1);
				jxl.write.Label label4 = new jxl.write.Label(1, rowdata,
						ci_excel.get(0).getEcin_insurant());
				sheet.addCell(label4);
				jxl.write.Label label8 = new jxl.write.Label(2, rowdata,
						ci_excel.get(0).getEcin_idcard());
				sheet.addCell(label8);
				jxl.write.Label label9 = new jxl.write.Label(3, rowdata,
						ci_excel.get(0).getEcin_sex());
				sheet.addCell(label9);
				jxl.write.Label label10 = new jxl.write.Label(4, rowdata,
						ci_excel.get(0).getEcin_birthday());
				sheet.addCell(label10);
				jxl.write.Label label111 = new jxl.write.Label(5, rowdata,
						ci_excel.get(0).getEcin_work_st());
				sheet.addCell(label111);
				jxl.write.Label label6 = new jxl.write.Label(6, rowdata,
						ci_excel.get(0).getEcin_sconnection());
				sheet.addCell(label6);
				jxl.write.Label label5 = new jxl.write.Label(7, rowdata,
						ci_excel.get(0).getEcin_insurer());
				sheet.addCell(label5);
				jxl.write.Label label15 = new jxl.write.Label(8, rowdata,
						ci_excel.get(0).getEcin_zidcard());
				sheet.addCell(label15);
				jxl.write.Label label14 = new jxl.write.Label(9, rowdata,
						ci_excel.get(0).getEcin_in_date());
				sheet.addCell(label14);
				jxl.write.Label label25 = new jxl.write.Label(10, rowdata,
						ci_excel.get(0).getEcin_st_date());
				sheet.addCell(label25);
				jxl.write.Label label26 = new jxl.write.Label(11, rowdata, "");
				sheet.addCell(label26);
				jxl.write.Label label27 = new jxl.write.Label(12, rowdata, "");
				sheet.addCell(label27);
				jxl.write.Label label217 = new jxl.write.Label(13, rowdata, "");
				sheet.addCell(label217);
				jxl.write.Label label11 = new jxl.write.Label(14, rowdata,
						ci_excel.get(0).getEcin_castsort());
				sheet.addCell(label11);
				jxl.write.Label label12 = new jxl.write.Label(15, rowdata,
						ci_excel.get(0).getEcin_buy_count());
				sheet.addCell(label12);
				jxl.write.Label label18 = new jxl.write.Label(16, rowdata,
						ci_excel.get(0).getEcin_state());
				sheet.addCell(label18);
				jxl.write.Label label29 = new jxl.write.Label(17, rowdata, "");
				sheet.addCell(label29);
				jxl.write.Label label30 = new jxl.write.Label(18, rowdata, "");
				sheet.addCell(label30);
				jxl.write.Label label7 = new jxl.write.Label(19, rowdata,
						ci_excel.get(0).getEcin_company());
				sheet.addCell(label7);
				jxl.write.Label label19 = new jxl.write.Label(20, rowdata,
						ci_excel.get(0).getEcin_client());
				sheet.addCell(label19);
				jxl.write.Label label2 = new jxl.write.Label(21, rowdata,
						ci_excel.get(0).getGid());
				sheet.addCell(label2);
				jxl.write.Label label3 = new jxl.write.Label(22, rowdata,
						ci_excel.get(0).getCid());
				sheet.addCell(label3);
				jxl.write.Label label32 = new jxl.write.Label(23, rowdata,
						ci_excel.get(0).getEcin_cl_count());
				sheet.addCell(label32);
				jxl.write.Label label312 = new jxl.write.Label(24, rowdata,
						ci_excel.get(0).getEcin_in_date());
				sheet.addCell(label312);
			}
		}

		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();

		FileOperate.download("EmCommercialInsurance/File/Download/Em_in/"
				+ filename);
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_Insurant_Aut.zul");

	}

	// 导出报表
	@Command("ci_insurant_bcdown")
	public void ci_insurant_bcdown(@BindingParam("gridco") Grid gridco)
			throws Exception {

		String filename = ""; // 文件名
		String absolutePath; // 服务器地址
		String filetype = ".xls"; // 文件类型
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);

		filename = nowtime + filetype;
		absolutePath = FileOperate.getAbsolutePath();

		// 创建exce
		// 读取Excel模板
		Workbook wb = Workbook.getWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Templet/sy_sb_change.xls"));
		// 使用模板创建
		WritableWorkbook wwb = Workbook.createWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Download/Em_in/" + filename), wb);
		// 生成工作表
		WritableSheet sheet = wwb.getSheet(0);
		// Label label1 = null;
		int rowdata = 2;
		String pid = "";
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk = (Checkbox) gridco.getCell(i, 11).getChildren()
					.get(0);

			Label tapr_id = (Label) gridco.getCell(i, 12).getChildren().get(0);

			if (chk.isChecked()) {
				String[] message = ccsaBll.aut_bcinsurant((int) chk.getValue(),
						Integer.parseInt(tapr_id.getValue().toString()));

				if (i < gridco.getRows().getChildren().size())
					bci_excel = new ListModelList<CI_Insurant_ListModel>(
							bll.bci_excel(chk.getValue().toString()));// 显示保险在保类型
				rowdata = rowdata + 1;

				pid = String.valueOf(rowdata);
				jxl.write.Label label1 = new jxl.write.Label(0, rowdata, pid);
				sheet.addCell(label1);
				jxl.write.Label label4 = new jxl.write.Label(1, rowdata,
						bci_excel.get(0).getEcin_insurant());
				sheet.addCell(label4);
				jxl.write.Label label8 = new jxl.write.Label(2, rowdata,
						bci_excel.get(0).getEcin_idcard());
				sheet.addCell(label8);
				jxl.write.Label label9 = new jxl.write.Label(3, rowdata,
						bci_excel.get(0).getEcin_sex());
				sheet.addCell(label9);
				jxl.write.Label label10 = new jxl.write.Label(4, rowdata,
						bci_excel.get(0).getEcin_birthday());
				sheet.addCell(label10);
				jxl.write.Label label6 = new jxl.write.Label(5, rowdata,
						bci_excel.get(0).getEcin_sconnection());
				sheet.addCell(label6);
				jxl.write.Label label5 = new jxl.write.Label(6, rowdata,
						bci_excel.get(0).getEcin_insurer());
				sheet.addCell(label5);
				jxl.write.Label label15 = new jxl.write.Label(7, rowdata,
						bci_excel.get(0).getEcin_zidcard());
				sheet.addCell(label15);
				jxl.write.Label label14 = new jxl.write.Label(15, rowdata,
						bci_excel.get(0).getEcin_in_date());
				sheet.addCell(label14);
				jxl.write.Label label11 = new jxl.write.Label(16, rowdata,
						bci_excel.get(0).getEcin_castsort());
				sheet.addCell(label11);
				jxl.write.Label label18 = new jxl.write.Label(9, rowdata,
						bci_excel.get(0).getEcin_state());
				sheet.addCell(label18);
				jxl.write.Label label29 = new jxl.write.Label(10, rowdata,
						bci_excel.get(0).getEcin_state2());
				sheet.addCell(label29);
				jxl.write.Label label7 = new jxl.write.Label(13, rowdata,
						bci_excel.get(0).getEcin_company());
				sheet.addCell(label7);
				jxl.write.Label label19 = new jxl.write.Label(14, rowdata,
						bci_excel.get(0).getEcin_client());
				sheet.addCell(label19);
				jxl.write.Label label312 = new jxl.write.Label(8, rowdata,
						bci_excel.get(0).getEcin_remark());
				sheet.addCell(label312);
			}
		}

		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();

		FileOperate.download("EmCommercialInsurance/File/Download/Em_in/"
				+ filename);
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("CI_Insurant_ChAut.zul");

	}

	@Command("checkall")
	public void checkall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		try {
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 9).getChildren().get(0);
				ckb.setChecked(ck);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Command("bcheckall")
	public void bcheckall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		try {
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				Checkbox ckb = (Checkbox) gd.getCell(i, 11).getChildren()
						.get(0);
				ckb.setChecked(ck);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 审核支付通知
	@Command("ci_insurant_upload")
	public void ci_insurant_upload(
			@BindingParam("emco") CI_Insurant_ListModel emco) {
		Map arg = new HashMap();
		// arg.put("daid", emco.getEbco_id());
		arg.put("cid", 1);
		arg.put("ownmonth", 1);
		arg.put("type", 1);
		Window wnd = (Window) Executions.createComponents(
				"CI_Insurant_AutUpload.zul", null, arg);
		wnd.doModal();
	}

	@Command("checkall_pay")
	public void checkall_pay(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			Checkbox ckb = (Checkbox) gd.getCell(i, 8).getChildren().get(0);
			ckb.setChecked(ck);
		}
	}

	@Command("ecin_del")
	public void ecin_del(@BindingParam("emco") CI_Insurant_ListModel emco)
			throws Exception {
		String[] message = ccsaBll.del_insurant(
				String.valueOf(emco.getEcin_id()),
				String.valueOf(emco.getEcin_tapr_id()));

		if (message[0].equals("1")) {
			// ecin_del = new ListModelList<CI_Insurant_ListModel>(
			// bll.ecin_del(String.valueOf(emco.getEcin_id())));// 显示保险在保类型
			Messagebox.show("删除成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			RedirectUtil util = new RedirectUtil();
			util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_EditBase.zul");// url为跳转页面连接
		} else {
			Messagebox.show("删除失败！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.ERROR, null);
		}
	}

	// 商保信息变更
	@Command("ci_insurant_linkchange")
	public void ci_insurant_linkchange(@BindingParam("gridco3") Grid gridco3,
			@BindingParam("w1") Window w1) throws Exception {
		// 连带人信息变更
		for (int i = 0; i < gridco3.getRows().getChildren().size(); i++) {
			Grid gd = (Grid) gridco3.getCell(i, 0);
			Textbox name = (Textbox) gd.getCell(1, 1).getChildren().get(0);
			Textbox idcard = (Textbox) gd.getCell(1, 3).getChildren().get(0);
			Textbox sex = (Combobox) gd.getCell(2, 1).getChildren().get(0);
			Datebox birthday = (Datebox) gd.getCell(2, 3).getChildren().get(0);
			Label lname = (Label) gd.getCell(0, 3).getChildren().get(0);

			String birth_date = "";
			if (birthday.getValue() != null) {
				birth_date = ccsaBll.DatetoSting(birthday.getValue());
			}

			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> list = dal.getallcid(gid);
			List<CI_Insurant_ListModel> list2 = dal.getfbase(gid,
					lname.getValue());

			if (!list2.get(0).getEcin_insurant().equals(name.getValue())
					|| !list2.get(0).getEcin_idcard().equals(idcard.getValue())
					|| !list2.get(0).getEcin_sex().equals(sex.getValue())
					|| !list2.get(0).getEcin_birthday()
							.equals(birthday.getValue())) {
				// 信息变更新增
				String[] message = ccsaBll.change_insurant(gid,
						name.getValue(), idcard.getValue(), sex.getValue(),
						birth_date, list.get(0).getCid(), lname.getValue());
				// 弹出提示并跳转页面
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								// Executions.sendRedirect("Compact_SginList.zul");
								// //跳转页面
								// w1.detach();
							}
						}
					};

				}
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		// Executions.sendRedirect("/Embase/Embase_editlist.zul");
		w1.detach();

		// 跳转页面：赵敏捷2015-04-10修改
		RedirectUtil util = new RedirectUtil();
		if (step == 1) {
			// 员工业务中心跳转方法:
			util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
		} else if (step == 2) {

			// 入职第二页跳转方法:
			util.refreshEntrySecondUrl("/EmCommercialInsurance/CI_Insurant_Base.zul");// url为跳转页面连接
		}

	}

	private ListModelList<CI_Insurant_ListModel> ci_insurant_castsort;// 显示所选商保类型

	private ListModelList<CI_Insurant_ListModel> ci_insurant_applycastsort;// 显示所选商保连带人类型

	private ListModelList<CI_Insurant_ListModel> ci_insurant_linkcastsort;// 显示所选商保连带人类型

	private ListModelList<CI_Insurant_ListModel> ci_insurant_linkcastsortadd;// 显示所选商保连带人类型

	private ListModelList<CI_Insurant_ListModel> ci_insurant_list;// 显示商保未审核数据

	private ListModelList<CI_Insurant_ListModel> ci_insurant_editlist;// 显示商保未审核数据

	private ListModelList<CI_Insurant_ListModel> ci_insurant_bclist;// 显示商保未审核数据

	private ListModelList<CI_Insurant_ListModel> ci_insurant_paylist;// 显示商保支付数据

	private ListModelList<CI_Insurant_ListModel> ci_insurant_autlist;// 显示商保已审核数据

	private ListModelList<CI_Insurant_ListModel> embaselist;// 显示员工信息

	private ListModelList<CI_Insurant_ListModel> lembaselist;// 显示员工信息

	private ListModelList<CI_Insurant_ListModel> castsortlist;// 显示保险类型

	private ListModelList<CI_Insurant_ListModel> castsortdatelist;// 显示保险审核时间

	private ListModelList<CI_Insurant_ListModel> ci_insurant_castsortout;// 显示保险在保类型

	private ListModelList<CI_Insurant_ListModel> ci_link_list;// 显示保险类型

	private ListModelList<CI_Insurant_ListModel> ci_excel;// 导出excel商保数据

	private ListModelList<CI_Insurant_ListModel> ci_zc_excel;// 导出excel商保在保数据

	private ListModelList<CI_Insurant_ListModel> ecin_del;// 删除商保数据

	private ListModelList<CI_Insurant_ListModel> bci_excel;// 导出excel商保信息变更数据

	private ListModelList<CI_Insurant_ListModel> ci_insurant_blist;// 显示商保数据
	
	private ListModelList<CI_Insurant_ListModel> ci_insurant_lblist;// 显示商保连带人数据

	private ListModelList<CI_Insurant_ListModel> ci_insurant_chlist;// 显示商保查询
	
	String cast_tyadd="";

	public void init() throws SQLException {
		ci_insurant_castsort = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_castsort(gid));// 显示所选商保类型

		ci_insurant_applycastsort = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_applycastsort(gid));// 显示所选商保类型

		if (gid.equals("0")) {
			ci_insurant_linkcastsort = new ListModelList<CI_Insurant_ListModel>(
					bll.ci_insurant_linkcastsort(getapply_gid.get(0).getGid()
							.toString()));// 显示所选商保连带人类型

			ci_insurant_linkcastsortadd = new ListModelList<CI_Insurant_ListModel>(
					bll.ci_insurant_linkcastsortadd(getapply_gid.get(0)
							.getGid().toString()));// 显示所选商保连带人类型
		} else {
			ci_insurant_linkcastsort = new ListModelList<CI_Insurant_ListModel>(
					bll.ci_insurant_linkcastsort(gid));// 显示所选商保连带人类型

			ci_insurant_linkcastsortadd = new ListModelList<CI_Insurant_ListModel>(
					bll.ci_insurant_linkcastsortadd(gid));// 显示所选商保连带人类型
		}
		
		

		for (int i = 0; i < ci_insurant_linkcastsort.size(); i++) {
			
			
			if (ci_insurant_linkcastsort.get(i).getEcin_sconnection()
					.equals("配偶")) {
				coats1 = 1;
				coastse1 = ci_insurant_linkcastsort.get(i).getEcin_castsort()
						+ ",";
			}

			if (ci_insurant_linkcastsort.get(i).getEcin_sconnection()
					.equals("子女")) {
				coats2 = 1;
				coastse2 = ci_insurant_linkcastsort.get(i).getEcin_castsort()
						+ ",";
			}
			
			System.out.println("aaa---");
			System.out.println(coats1);
			System.out.println(coats2);
		}

		embaselist = new ListModelList<CI_Insurant_ListModel>(
				bll.embaselist(gid));// 显示员工信息

		if (gid.equals("0")) {
			lembaselist = new ListModelList<CI_Insurant_ListModel>(
					bll.embaselist(getapply_gid.get(0).getGid().toString()));// 显示员工信息
		} else {
			lembaselist = new ListModelList<CI_Insurant_ListModel>(
					bll.embaselist(gid));// 显示员工信息
		}

		ci_insurant_list = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_list(""));// 显示未审核列表

		ci_insurant_editlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_editlist(gid));// 显示未审核列表

		ci_insurant_bclist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_bchlist());// 显示信息变更未审核列表

		ci_insurant_paylist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_paylist(""));// 显示支付列表

		ci_insurant_autlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_autlist("", ""));// 显示已审核列表

		castsortlist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortlist());// 显示保险类型

		castsortdatelist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortdatelist());// 显示保险类型

		ci_insurant_castsortout = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_castsortout(gid));// 显示保险在保类型

		ci_link_list = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_link_list(gid));// 显示保险在保类型

		try {
			gid1 = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());
			cast_tyadd=ci_insurant_linkcastsortadd.get(0).getEcin_balance_name();
			ci_insurant_lblist = new ListModelList<CI_Insurant_ListModel>(
					bll.ci_insurant_lblist(Integer.parseInt(getapply_gid.get(0).getGid().toString())));// 显示所选商保类型
		} catch (Exception e) {
			gid1 = 0;
		}

		ci_insurant_blist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_blist(Integer.parseInt(gid)));// 显示所选商保类型
		
		

		ci_insurant_chlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_chlist("", "", "", "", "", "", "", ""));// 显示未审核列表
	}

	// 终止流程
	@Command("over")
	public void over(@BindingParam("win") Window win) throws SQLException {
		EmCommissionOut_ListDal dal = new EmCommissionOut_ListDal();
		CI_Insurant_ListDal dal1 = new CI_Insurant_ListDal();
		getapply_gid = new ListModelList<CI_Insurant_ListModel>(
				dal1.gettb_gid(tb_id));
		try {
			System.out.println("xxaaxx");
			System.out.println(getapply_gid.get(0).getEcin_tapr_id());

			eclist = new ListModelList<EmCommissionOutChangeModel>(
					dal.getci_eclist(Integer.parseInt(getapply_gid.get(0)
							.getEcin_tapr_id())));

			Map<String, String> map = new HashMap<String, String>();
			map.put("tali_id", String.valueOf(eclist.get(0).getEcoc_ecos_id()));
			map.put("tali_name",
					String.valueOf(eclist.get(0).getEcoc_addtype()));
			map.put("tb_id",String.valueOf(tb_id));
			map.put("client",getapply_gid.get(0).getEcin_client());
			map.put("gid",getapply_gid.get(0).getGid());
			Window window = (Window) Executions.createComponents(
					"/EmCommercialInsurance/Task_StopTask.zul", null, map);
			window.doModal();
			win.detach();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	// 申请新增商保连带人
		@Command("ci_insurant_linkapply")
		public void ci_insurant_linkapply(@BindingParam("gridco3") Grid gridco3,
				@BindingParam("sbl_w1") Window sbl_w1,
				@BindingParam("castsort") Combobox castsort,
				@BindingParam("ch1") Checkbox ld_state,
				@BindingParam("ch2") Checkbox ld_state2,
				@BindingParam("fl_date") Datebox f_date) throws Exception {
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			String ef_date = "";
			String castosrt_ad = "";

			int getld = 0;

			if (f_date.getValue() != null) {
				ef_date = ccsaBll.DatetoSting(f_date.getValue());
			}
			
			try {
				if (ld_state.isChecked() || ld_state2.isChecked()) {
					getld = 1;

					if (ef_date.equals("")) {
						Messagebox.show("请录入连带人的生效时间", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						// System.out.println("2");
						return;
					}
					
					if (ld_state.isChecked()) {
						castosrt_ad = coastse2;
					}

					if (ld_state2.isChecked()) {
						castosrt_ad = castosrt_ad + coastse1;
					}

				} else {
					Messagebox.show("请勾选是否购买连带人险", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					// System.out.println("err");
					return;

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> list = dal.getallcid(gid);
			// 连带人新增申请
			String[] message = ccsaBll.apply_insurant(gid, list.get(0).getCid(),
					getld, ef_date, castosrt_ad);

			Messagebox.show("操作成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);

			sbl_w1.detach();

			// 跳转页面：赵敏捷2015-04-10修改
			RedirectUtil util = new RedirectUtil();
			if (step == 1) {
				// 员工业务中心跳转方法:
				util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_LinkApply.zul");// url为跳转页面连接
			} else if (step == 2) {

				// 入职第二页跳转方法:
				util.refreshEntrySecondUrl("/EmCommercialInsurance/CI_Insurant_LinkApply.zul");// url为跳转页面连接
			}

		}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_castsort() {
		return ci_insurant_castsort;
	}

	public void setCi_insurant_castsort(
			ListModelList<CI_Insurant_ListModel> ci_insurant_castsort) {
		this.ci_insurant_castsort = ci_insurant_castsort;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_linkcastsort() {
		return ci_insurant_linkcastsort;
	}

	public void setCi_insurant_linkcastsort(
			ListModelList<CI_Insurant_ListModel> ci_insurant_linkcastsort) {
		this.ci_insurant_linkcastsort = ci_insurant_linkcastsort;
	}

	public ListModelList<CI_Insurant_ListModel> getEmbaselist() {
		return embaselist;
	}

	public void setEmbaselist(ListModelList<CI_Insurant_ListModel> embaselist) {
		this.embaselist = embaselist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_list() {
		return ci_insurant_list;
	}

	public void setCi_insurant_list(
			ListModelList<CI_Insurant_ListModel> ci_insurant_list) {
		this.ci_insurant_list = ci_insurant_list;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_autlist() {
		return ci_insurant_autlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_paylist() {
		return ci_insurant_paylist;
	}

	public void setCi_insurant_paylist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_paylist) {
		this.ci_insurant_paylist = ci_insurant_paylist;
	}

	public void setCi_insurant_autlist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_autlist) {
		this.ci_insurant_autlist = ci_insurant_autlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCastsortlist() {
		return castsortlist;
	}

	public void setCastsortlist(
			ListModelList<CI_Insurant_ListModel> castsortlist) {
		this.castsortlist = castsortlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCastsortdatelist() {
		return castsortdatelist;
	}

	public void setCastsortdatelist(
			ListModelList<CI_Insurant_ListModel> castsortdatelist) {
		this.castsortdatelist = castsortdatelist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_castsortout() {
		return ci_insurant_castsortout;
	}

	public void setCi_insurant_castsortout(
			ListModelList<CI_Insurant_ListModel> ci_insurant_castsortout) {
		this.ci_insurant_castsortout = ci_insurant_castsortout;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_link_list() {
		return ci_link_list;
	}

	public void setCi_link_list(
			ListModelList<CI_Insurant_ListModel> ci_link_list) {
		this.ci_link_list = ci_link_list;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_blist() {
		return ci_insurant_blist;
	}

	public void setCi_insurant_blist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_blist) {
		this.ci_insurant_blist = ci_insurant_blist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_chlist() {
		return ci_insurant_chlist;
	}

	public void setCi_insurant_chlist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_chlist) {
		this.ci_insurant_chlist = ci_insurant_chlist;
	}

	public boolean isCheck_ciin() {
		return check_ciin;
	}

	public void setCheck_ciin(boolean check_ciin) {
		this.check_ciin = check_ciin;
	}

	public String getCheck_ciinMessage() {
		return check_ciinMessage;
	}

	public void setCheck_ciinMessage(String check_ciinMessage) {
		this.check_ciinMessage = check_ciinMessage;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_bclist() {
		return ci_insurant_bclist;
	}

	public void setCi_insurant_bclist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_bclist) {
		this.ci_insurant_bclist = ci_insurant_bclist;
	}

	public ListModelList<CI_Insurant_ListModel> getBuycount_lst() {
		return buycount_lst;
	}

	public void setBuycount_lst(
			ListModelList<CI_Insurant_ListModel> buycount_lst) {
		this.buycount_lst = buycount_lst;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_editlist() {
		return ci_insurant_editlist;
	}

	public void setCi_insurant_editlist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_editlist) {
		this.ci_insurant_editlist = ci_insurant_editlist;
	}

	public ListModelList<CI_Insurant_ListModel> getLembaselist() {
		return lembaselist;
	}

	public void setLembaselist(ListModelList<CI_Insurant_ListModel> lembaselist) {
		this.lembaselist = lembaselist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_linkcastsortadd() {
		return ci_insurant_linkcastsortadd;
	}

	public void setCi_insurant_linkcastsortadd(
			ListModelList<CI_Insurant_ListModel> ci_insurant_linkcastsortadd) {
		this.ci_insurant_linkcastsortadd = ci_insurant_linkcastsortadd;
	}

	public EmbaseModel getEbm() {
		return ebm;
	}

	public void setEbm(EmbaseModel ebm) {
		this.ebm = ebm;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_applycastsort() {
		return ci_insurant_applycastsort;
	}

	public void setCi_insurant_applycastsort(
			ListModelList<CI_Insurant_ListModel> ci_insurant_applycastsort) {
		this.ci_insurant_applycastsort = ci_insurant_applycastsort;
	}

	public int getCoats1() {
		return coats1;
	}

	public void setCoats1(int coats1) {
		this.coats1 = coats1;
	}

	public int getCoats2() {
		return coats2;
	}

	public void setCoats2(int coats2) {
		this.coats2 = coats2;
	}

	public String getCoastse1() {
		return coastse1;
	}

	public void setCoastse1(String coastse1) {
		this.coastse1 = coastse1;
	}

	public String getCoastse2() {
		return coastse2;
	}

	public void setCoastse2(String coastse2) {
		this.coastse2 = coastse2;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_lblist() {
		return ci_insurant_lblist;
	}

	public void setCi_insurant_lblist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_lblist) {
		this.ci_insurant_lblist = ci_insurant_lblist;
	}

	public String getCast_tyadd() {
		return cast_tyadd;
	}

	public void setCast_tyadd(String cast_tyadd) {
		this.cast_tyadd = cast_tyadd;
	}

}
