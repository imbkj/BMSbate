package Controller.EmBaseCompact;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import com.sun.java.swing.plaf.windows.resources.windows;

import Conn.dbconn;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CI_InsurantClaimModel;
import bll.EmBaseCompact.EmBaseCompact_AddBll;
import bll.EmBaseCompact.EmBaseCompact_OperateBll;

public class EmBaseCompact_QdController {

	private EmBaseCompact_OperateBll ccsaBll = new EmBaseCompact_OperateBll();
	private ListModelList<CI_InsurantClaimModel> embasecompact_remarklist;// 获取商保索赔备注
	String ebcc_id="";

	public EmBaseCompact_QdController() throws SQLException {
		try {
			ebcc_id = String.valueOf(Executions.getCurrent().getArg()
					.get("daid").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EmBaseCompact_AddBll bll = new EmBaseCompact_AddBll();
		embasecompact_remarklist = new ListModelList<CI_InsurantClaimModel>(
				bll.embasecompact_remarklist(ebcc_id));// 获取商保索赔备注
	}

	// 待待订劳动合同信息
	@Command("qdcompact")
	public void qdcompact(@BindingParam("embase2") Combobox embase2,
			@BindingParam("embase1") Datebox embase1,
			@BindingParam("ebcc_id") Label ebcc_id,
			@BindingParam("ebcc_tapr_id") Label ebcc_tapr_id,
			@BindingParam("gd") Grid gd) throws Exception {
		EmBaseCompact_AddBll bll = new EmBaseCompact_AddBll();
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		String tid = ebcc_id.getValue();// 可以为空白
		String doType = "a";// 不能为空，必须填 a 或 u

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
				// cwq1.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			Messagebox.show("请录入“合同签订方式”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	// 测试用
	public int add(String content) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn.getcall("DocAddTest_p_lsb(?,?)");
			System.out.println(content);
			c.setString(1, content);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (Exception e) {
			return 0;
		}
	}

	@Command("remark_add")
	public void remark_add(@BindingParam("ebcc_id") Label ebcc_id,
			@BindingParam("content") Textbox content,
			@BindingParam("w1") final Window w1)
			throws Exception {		
		EmBaseCompact_AddBll bll = new EmBaseCompact_AddBll();
		// 备注新增
		String[] message = bll.remark_add(ebcc_id.getValue(),content.getValue());

		if (message[0].equals("1")) {
			Messagebox.show("操作成功！", "操作提示", Messagebox.OK, Messagebox.NONE);
			w1.detach();
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public ListModelList<CI_InsurantClaimModel> getEmbasecompact_remarklist() {
		return embasecompact_remarklist;
	}

	public void setEmbasecompact_remarklist(
			ListModelList<CI_InsurantClaimModel> embasecompact_remarklist) {
		this.embasecompact_remarklist = embasecompact_remarklist;
	}
	
	
}
