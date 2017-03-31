package Controller.EmBaseCompact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.github.abel533.echarts.Grid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CI_Insurant_ListModel;
import Util.FileOperate;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmBaseCompact.EmBaseCompact_AddBll;
import bll.EmBaseCompact.EmBaseCompact_OperateBll;

public class EmBaseCompact_AddController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	String username = UserInfo.getUsername();
	String user = UserInfo.getUserid();
	private String gid;
	private String cid;
	private String filename;
	private String vername;
	private String covername;
	private Media media;
	private boolean check_ciin = true;
	private String check_ciinMessage;
	private int step;

	private Window win2 = (Window) Executions.getCurrent().getArg().get("win1");

	private List<CI_Insurant_ListModel> embasecost;

	@Wire
	private Datebox embase1;

	@Wire
	private Combobox embase2;

	@Wire
	private Datebox embase3;

	@Wire
	private Combobox embase4;

	@Wire
	private Datebox embase5;

	@Wire
	private Datebox embase6;

	@Wire
	private Combobox embase7;

	@Wire
	private Textbox embase8;

	@Wire
	private Textbox embase9;

	@Wire
	private Textbox embase10;

	@Wire
	private Textbox embase11;

	@Wire
	private Textbox embase12;

	@Wire
	private Textbox embase13;

	@Wire
	private Textbox embase14;

	@Wire
	private Combobox embase15;

	@Wire
	private Textbox embase16;

	@Wire
	private Textbox embase17;

	@Wire
	private Combobox embase18;

	@Wire
	private Textbox embase19;

	@Wire
	private Textbox embase20;

	@Wire
	private Textbox compact_type;

	@Wire
	private Textbox embase21;

	@Wire
	private Label ebcc_id;

	@Wire
	private Label ebcc_tapr_id;

	@Wire
	private Window w1;

	@Wire
	private Window w2;

	@Wire
	private Window w3;

	@Wire
	private Window wq1;

	@Wire
	private Window wq2;

	@Wire
	private org.zkoss.zul.Grid docGrid;

	EmBaseCompact_AddBll bll = new EmBaseCompact_AddBll();
	private EmBaseCompact_OperateBll ccsaBll = new EmBaseCompact_OperateBll();

	public EmBaseCompact_AddController() {

		try {
			gid = Executions.getCurrent().getArg().get("gid").toString();
			// 1；为员工业务中心，2；入职第二页
			step = Integer.parseInt(Executions.getCurrent().getArg()
					.get("step").toString());

		} catch (Exception e) {
			gid = "0";
		}
		cid = "0";

		/*
		 * if (bll.check_ciin(Integer.parseInt(gid))) { check_ciin = false;
		 * Messagebox.show("该员工已有劳动合同，无法重复操作！", "操作提示", Messagebox.OK,
		 * Messagebox.ERROR); // System.out.println(check_ciin); }
		 */
	}

	// 初始化检查
	// private boolean checkInit() {
	// if (bll.check_ciin(Integer.parseInt(gid))) {
	// check_ciin = true;
	// System.out.println(check_ciin);
	// check_ciinMessage = "已有该员工商保信息，不能新增!";
	// return check_ciin;
	// }
	// return false;
	// }

	@Listen("onCreate = #w1")
	public void w1() throws Exception {
		if (bll.check_ciin(Integer.parseInt(gid))) {
			Messagebox.show("该员工已有劳动合同，无法重复操作！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			w1.detach();
		}
	}

	@Listen("onCreate = #w2")
	public void w2() throws Exception {
		if (!bll.check_ciin(Integer.parseInt(gid))) {
			Messagebox.show("该员工无劳动合同信息或已终止，无法操作续签，请做新签操作！", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			w2.detach();
		}

		if (!bll.ch_xadd(Integer.parseInt(gid))) {
			Messagebox.show("该员工合同到期日未到续签时间或无固定合同期限，无法操作续签！", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			w2.detach();
		}
	}

	@Listen("onCreate = #w3")
	public void w3() throws Exception {
		if (!bll.check_ciin(Integer.parseInt(gid))) {
			Messagebox.show("该员工无劳动合同信息或已终止，无法操作终止！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			w3.detach();
		}
	}

	// 添加劳动合同信息
	@Listen("onClick = #addemcompact")
	public void addemcompact() throws Exception {
		// 日期判断
		String em1 = "";
		if (embase1.getValue() != null) {
			em1 = bll.DatetoSting(embase1.getValue());
		}

		String em2 = "";
		if (embase3.getValue() != null) {
			em2 = bll.DatetoSting(embase3.getValue());
		}

		String em3 = "";
		if (embase5.getValue() != null) {
			em3 = bll.DatetoSting(embase5.getValue());
		}

		String em4 = "";
		if (embase6.getValue() != null) {
			em4 = bll.DatetoSting(embase6.getValue());
		}

		if (compact_type.getValue().equals("")) {
			Messagebox.show("请选择“劳动合同类型”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase2.getValue().equals("")) {
			Messagebox.show("请选择“合同结束期限”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase2.getValue().equals("有固定期限") && em2.equals("")) {
			Messagebox.show("请录入“合同到期时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		DateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");

		if (embase2.getValue().equals("有固定期限") && !em2.equals("")) {
			Date d1 = dfd.parse(em1);
			Date d2 = dfd.parse(em2);
			long diff = d2.getTime() - d1.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			System.out.println(days);

			if (!compact_type.getValue().equals("实习生协议")
					&& !compact_type.getValue().equals("全外包合同")&& !compact_type.getValue().equals("退休人员协议")) {
				if (embase2.getValue().equals("有固定期限")) {

					if (days < 729) {
						Messagebox.show("“合同结束期限”录入有误！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
		}

		if (embase7.getValue().equals("1")
				&& (embase8.getValue().equals("")
						|| embase9.getValue().equals("")
						|| embase10.getValue().equals("") || embase11
						.getValue().equals(""))) {
			Messagebox.show("请录入“月工资第一项”相关内容!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase7.getValue().equals("2")
				&& (embase12.getValue().equals("") || embase13.getValue()
						.equals(""))) {
			Messagebox.show("请录入“月工资第二项”相关内容!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase15.getValue().equals("")) {
			Messagebox.show("请选择“工资发放方式”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase16.getValue().equals("")) {
			Messagebox
					.show("请录入工作地点！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (embase17.getValue().equals("")) {
			Messagebox
					.show("请录入工作岗位！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		if (embase2.getValue().equals("有固定期限") && em2.equals("")) {
			Date dt1 = df.parse(em4);
			Date dt2 = df.parse(em3);

			if (dt1.getTime() < dt2.getTime()) {
				Messagebox.show("试用期结束时间不能大于起始时间！”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		// 判断合同起始时间是否为空
		if (!embase1.getValue().equals("")) {
			// 合同新增
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> list = dal.getallcid(gid);

			String[] message = ccsaBll.add_Emcompact(list.get(0).getCid(), gid,
					em1, embase2.getValue(), em2, em3, em4, embase8.getValue(),
					embase9.getValue(), embase10.getValue(),
					embase11.getValue(), embase12.getValue(),
					embase13.getValue(), embase14.getValue(),
					embase15.getValue(), embase16.getValue(),
					embase17.getValue(), embase18.getValue(),
					embase19.getValue(), embase20.getValue(),
					embase21.getValue(), embase4.getValue(),
					compact_type.getValue());
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
								util.refreshEmUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
							} else if (step == 2) {

								// 入职第二页跳转方法:
								util.refreshEntrySecondUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
							}

						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
				// Executions.sendRedirect("/Embase/Embase_List.zul");
				w1.detach();

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);

			}
		} else {
			Messagebox.show("请录入“合同起始时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	// 待待订劳动合同信息
	@Listen("onClick = #qdcompact")
	public void qdcompact() throws Exception {
		String em1 = "";
		if (embase1.getValue() != null) {
			em1 = bll.DatetoSting(embase1.getValue());
		}

		// 判断合同起始时间是否为空
		if (embase2.getValue() != null) {
			String[] message = ccsaBll.qd_Emcompact(
					Integer.parseInt(ebcc_id.getValue()),
					Integer.parseInt(ebcc_tapr_id.getValue()),
					embase2.getValue(), em1);
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
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
				// Executions.sendRedirect("/EmBaseCompact/EmBaseCompact_SignList.zul");
				wq1.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入“合同签回时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	// 待待订劳动合同信息查询
	@Listen("onClick = #chcompact")
	public void chcompact() throws Exception {

		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getebcccid(ebcc_id.getValue()
				.toString());

		Map arg = new HashMap();
		arg.put("cid", list.get(0).getCid());
		Window wnd = (Window) Executions.createComponents(
				"/CoServePromise/CoPromise_Info.zul", null, arg);
		wnd.doModal();
	}

	// 签回劳动合同信息
	@Listen("onClick = #signemcompact")
	public void signemcompact() throws Exception {
		String em1 = "";
		if (embase1.getValue() != null) {
			em1 = bll.DatetoSting(embase1.getValue());
		}

		// 判断合同起始时间是否为空
		if (embase1.getValue() != null) {
			String[] message = ccsaBll.sign_Emcompact(
					Integer.parseInt(ebcc_id.getValue()),
					Integer.parseInt(ebcc_tapr_id.getValue()), em1);
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			String tid = ebcc_id.getValue();// 可以为空白
			String doType = "a";// 不能为空，必须填 a 或 u
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

				try {
					// a是新增；u是修改
					String[] message1 = new String[1];

					// 新增
					if ("a".equals(doType)) {
						// 调用方法
						message1 = docOC.AddchkHaveTo(docGrid);

						// 判断材料必选项是否已选
						if (message1[0].equals("1")) {
							// 新增业务数据，并返回业务表ID
							// tid = String.valueOf(add(tid.toString())); // 测试用

							// 判断业务id是否为空
							if (!tid.equals("") && !tid.equals("0")) {
								// 调用内联页方法submitDoc(Grid gd)
								message1 = docOC.AddsubmitDoc(docGrid, tid);

							}

						}
					} else if ("u".equals(doType)) {
						// 调用方法
						message1 = docOC.UpsubmitDoc(docGrid, tid);
					}
					Messagebox.show(message1[1], "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);

					// 修改

				} catch (Exception e) {
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
				// Executions.sendRedirect("/EmBaseCompact/EmBaseCompact_SignList.zul");
				wq1.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入“合同签回时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	// 归档劳动合同信息
	@Listen("onClick = #filingemcompact")
	public void filingemcompact() throws Exception {
		String em1 = "";
		if (embase1.getValue() != null) {
			em1 = bll.DatetoSting(embase1.getValue());
		}

		// 判断合同起始时间是否为空
		if (embase1.getValue() != null) {
			String[] message = ccsaBll.filing_Emcompact(
					Integer.parseInt(ebcc_id.getValue()),
					Integer.parseInt(ebcc_tapr_id.getValue()), em1);
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
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
				// Executions.sendRedirect("/EmBaseCompact/EmBaseCompact_FilingList.zul");
				wq2.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入“合同归档时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	// 续签劳动合同信息
	@Listen("onClick = #renemcompact")
	public void renemcompact() throws Exception {

		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getallcid(gid);
		// 日期判断
		String em1 = "";
		if (embase1.getValue() != null) {
			em1 = bll.DatetoSting(embase1.getValue());
		}

		String em2 = "";
		if (embase3.getValue() != null) {
			em2 = bll.DatetoSting(embase3.getValue());
		}

		String em3 = "";
		if (embase5.getValue() != null) {
			em3 = bll.DatetoSting(embase5.getValue());
		}

		String em4 = "";
		if (embase6.getValue() != null) {
			em4 = bll.DatetoSting(embase6.getValue());
		}

		if (compact_type.getValue().equals("")) {
			Messagebox.show("请选择“劳动合同类型”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase2.getValue().equals("")) {
			Messagebox.show("请选择“合同结束期限”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase2.getValue().equals("有固定期限") && em2.equals("")) {
			Messagebox.show("请录入“合同到期时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		DateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");

		if (embase2.getValue().equals("有固定期限") && !em2.equals("")) {
			Date d1 = dfd.parse(em1);
			Date d2 = dfd.parse(em2);
			long diff = d2.getTime() - d1.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			System.out.println(days);

			if (!compact_type.getValue().equals("实习生协议")
					&& !compact_type.getValue().equals("全外包合同")&& !compact_type.getValue().equals("退休人员协议")) {
				if (embase2.getValue().equals("有固定期限")) {

					if (days < 729) {
						Messagebox.show("“合同结束期限”录入有误！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
		}

		// 判断合同起始时间是否为空
		if (!embase1.getValue().equals("")) {
			// 合同新增
			String[] message = ccsaBll.ren_emcompact(list.get(0).getCid(), gid,
					em1, embase2.getValue(), em2, em3, em4, embase8.getValue(),
					embase9.getValue(), embase10.getValue(),
					embase11.getValue(), embase12.getValue(),
					embase13.getValue(), embase14.getValue(),
					embase15.getValue(), embase16.getValue(),
					embase17.getValue(), embase18.getValue(),
					embase19.getValue(), embase20.getValue(),
					embase21.getValue(), embase4.getValue(),
					compact_type.getValue());
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
								util.refreshEmUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
							} else if (step == 2) {

								// 入职第二页跳转方法:
								util.refreshEntrySecondUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
							}
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
				// Executions.sendRedirect("/Embase/Embase_List.zul");
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入“合同起始时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	// 修改劳动合同信息
	@Listen("onClick = #editemcompact")
	public void editemcompact() throws Exception {
		// 日期判断
		String em1 = "";
		if (embase1.getValue() != null) {
			em1 = bll.DatetoSting(embase1.getValue());
		}

		String em2 = "";
		if (embase3.getValue() != null) {
			em2 = bll.DatetoSting(embase3.getValue());
		}

		String em3 = "";
		if (embase5.getValue() != null) {
			em3 = bll.DatetoSting(embase5.getValue());
		}

		String em4 = "";
		if (embase6.getValue() != null) {
			em4 = bll.DatetoSting(embase6.getValue());
		}

		if (compact_type.getValue().equals("")) {
			Messagebox.show("请选择“劳动合同类型”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase2.getValue().equals("")) {
			Messagebox.show("请选择“合同结束期限”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase2.getValue().equals("有固定期限") && em2.equals("")) {
			Messagebox.show("请录入“合同到期时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		DateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");

		if (embase2.getValue().equals("有固定期限") && !em2.equals("")) {
			Date d1 = dfd.parse(em1);
			Date d2 = dfd.parse(em2);
			long diff = d2.getTime() - d1.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			System.out.println(days);

			if (!compact_type.getValue().equals("实习生协议")
					&& !compact_type.getValue().equals("全外包合同")&& !compact_type.getValue().equals("退休人员协议")) {
				if (embase2.getValue().equals("有固定期限")) {

					if (days < 729) {
						Messagebox.show("“合同结束期限”录入有误！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
		}

		// 判断合同起始时间是否为空
		if (!embase1.getValue().equals("")) {
			// 合同新增
			String[] message = ccsaBll.edit_emcompact(cid, ebcc_id.getValue(),
					em1, embase2.getValue(), em2, em3, em4, embase8.getValue(),
					embase9.getValue(), embase10.getValue(),
					embase11.getValue(), embase12.getValue(),
					embase13.getValue(), embase14.getValue(),
					embase15.getValue(), embase16.getValue(),
					embase17.getValue(), embase18.getValue(),
					embase19.getValue(), embase20.getValue(),
					embase21.getValue(), embase4.getValue(),
					compact_type.getValue());
			// 弹出提示并跳转页面
			if (!message[0].equals("0")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("Compact_SginList.zul");
							// //跳转页面
							// w1.detach();

							RedirectUtil util = new RedirectUtil();
							if (step == 1) {
								// 员工业务中心跳转方法:
								util.refreshEmUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
							} else if (step == 2) {

								// 入职第二页跳转方法:
								util.refreshEntrySecondUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
							}
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
				// Executions.sendRedirect("/EmBaseCompact/EmBaseCompact_AddDoc.zul?daid="+ebcc_id.getValue()+"&amp;user="+user+"&amp;id="+message[0]);
				Map arg = new HashMap();
				arg.put("daid", ebcc_id.getValue());
				arg.put("id", message[0]);

				w1.detach();
				win2.detach();

				Window wnd = (Window) Executions.createComponents(
						"/EmBaseCompact/EmBaseCompact_AddDoc.zul", null, arg);
				wnd.doModal();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入“合同起始时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	@Command("browse")
	@NotifyChange("filename")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if (media.getFormat().equals("txt")) {
			this.media = null;
			Messagebox.show("无法上传txt文件。", "操作提示", Messagebox.OK,
					Messagebox.NONE);

		} else {
			setFilename(media.getName());
		}
	}

	// 合同终止
	@Command("endemcompact")
	public void endemcompact(@BindingParam("w3") Window w3,
			@BindingParam("embasex1") Datebox embasex1) throws Exception {
		// 日期判断
		String em1 = "";
		if (embasex1.getValue() != null) {
			em1 = bll.DatetoSting(embasex1.getValue());
		}

		System.out.println("----");
		System.out.println(em1);

		try {
			// 判断合同起始时间是否为空
			if (!embasex1.getValue().equals("")) {
				if (this.media != null) {
					// 获取用户名
					String username = UserInfo.getUsername();
					// 编辑文件名
					String path = "OfficeFile/DownLoad/EmBaseCompact/";

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					Date date = new Date();
					String nowtime = sdf.format(date);
					String name = path + "EmbaseCompactEnd" + nowtime + cid
							+ "." + media.getFormat();
					// 上传文件
					if (FileOperate.upload(media, name)) {

						bll.end_emcompact(gid, em1, "EmbaseCompactEnd"
								+ nowtime + cid + "." + media.getFormat(),
								username);
						// 判断是否插入数据
						if (bll.Dochek5() > 0) {
							EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
								public void onEvent(ClickEvent event)
										throws Exception {
									if (Messagebox.Button.OK.equals(event
											.getButton())) {
										// Executions.sendRedirect("/Embase/Embase_List.zul");

										RedirectUtil util = new RedirectUtil();
										if (step == 1) {
											// 员工业务中心跳转方法:
											util.refreshEmUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
										} else if (step == 2) {

											// 入职第二页跳转方法:
											util.refreshEntrySecondUrl("/EmBaseCompact/EmBaseCompact_Base.zul");// url为跳转页面连接
										}
									}
								}
							};
							Messagebox
									.show("提交成功!",
											"操作提示",
											new Messagebox.Button[] { Messagebox.Button.OK },
											Messagebox.INFORMATION,
											clickListener);
							w3.detach();
						} else {
							Messagebox.show("提交失败!", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show("请录入“合同终止时间”", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				} else {
					Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			}
		} catch (Exception e) {
			Messagebox.show("文件上传出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

}
