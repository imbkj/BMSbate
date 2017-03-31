package Controller.EmBaseCompact;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
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

import bll.EmBaseCompact.EmBaseCompactSA_AddBll;
import bll.EmBaseCompact.EmBaseCompact_OperateBll;;

public class EmBaseCompactSA_AddController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Label gid;

	@Wire
	private Label cid;

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
	private Textbox embase21;

	@Wire
	private Label ebcc_id;
	
	@Wire
	private Label ebcu_tapr_id;

	@Wire
	private Window w1;
	
	@Wire
	private Window w4;
	
	@Wire
	private Window w5;

	EmBaseCompactSA_AddBll bll = new EmBaseCompactSA_AddBll();
	
	private EmBaseCompact_OperateBll ccsaBll = new EmBaseCompact_OperateBll();

	// 判断有无试用期
	@Listen("onClick = #compactchange")
	public void compactchange() {
		System.out.println("bbbb");
	}

	// 添加劳动合同信息
	@Listen("onClick = #addemcompact")
	public void addemcompact() {
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

		if (embase2.getValue().equals("")) {
			Messagebox.show("请选择“合同结束期限”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (embase2.getValue().equals("有固定期限") && embase3.getValue().equals("")) {
			Messagebox.show("请录入“合同到期时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		// 判断合同起始时间是否为空
		if (!embase1.getValue().equals("")) {
			bll.add_emcompact(cid.getValue(), gid.getValue(), em1,
					embase2.getValue(), em2, em3, em4, embase8.getValue(),
					embase9.getValue(), embase10.getValue(),
					embase11.getValue(), embase12.getValue(),
					embase13.getValue(), embase14.getValue(),
					embase15.getValue(), embase16.getValue(),
					embase17.getValue(), embase18.getValue(),
					embase19.getValue(), embase20.getValue(),
					embase21.getValue());

			// 判断是否插入数据
			if (bll.Dochek() > 0) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Executions.sendRedirect("EmbaseCompact_Add.zul");
						}
					}
				};
				Messagebox.show("提交成功!", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				Messagebox.show("提交失败!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入“合同起始时间”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	// 签回劳动合同信息
	@Listen("onClick = #signemcompact")
	public void signemcompact() throws Exception {
		String em1 = "";
		if (embase1.getValue() != null) {
			em1 = bll.DatetoSting(embase1.getValue());
		}

		// 判断合同起始时间是否为空
		if (!embase1.getValue().equals("")) {
			String[] message = ccsaBll.sign_EmcompactSA(Integer.parseInt(ebcc_id.getValue()),Integer.parseInt(ebcu_tapr_id.getValue()),em1);
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
				//Executions.sendRedirect("../EmBaseCompact/EmbaseCompactSA_SignList.zul");
				w4.detach();
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
			if (!embase1.getValue().equals("")) {
				System.out.println("xxxxxxxxxx");
				System.out.println(Integer.parseInt(ebcu_tapr_id.getValue()));
				String[] message = ccsaBll.filing_EmcompactSA(Integer.parseInt(ebcc_id.getValue()),Integer.parseInt(ebcu_tapr_id.getValue()),em1);
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
					//Executions.sendRedirect("../EmBaseCompact/EmbaseCompactSA_FilingList.zul");
					w5.detach();
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
}
