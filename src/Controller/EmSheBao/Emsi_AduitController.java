package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_OperateBll;

import Model.EmSheBaoChangeModel;
import Util.UserInfo;

public class Emsi_AduitController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmSheBaoChangeModel ecModel;
	private String reason;

	public Emsi_AduitController() {
		Emsc_DeclareSelBll selbll = new Emsc_DeclareSelBll();
		ecModel = selbll.getEmSbChange(id);
	}

	// 审核通过
	@Command("AduitPass")
	public void AduitPass(@BindingParam("win") Window win) {
		try {
			Emsi_OperateBll bll = new Emsi_OperateBll();
			String[] message = bll.aduitPass(ecModel, UserInfo.getUsername());
			if ("1".equals(message[0])) {
				// 成功提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				// 错误提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmSheBaoChangeModel getEcModel() {
		return ecModel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
