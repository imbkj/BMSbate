package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoChangeSZSIModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class Emsi_Change_SZSI_ColDataController {
	private int escs_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmShebaoChangeSZSIModel ecModel;
	private String reason;

	public Emsi_Change_SZSI_ColDataController() {
		Emsi_SelBll bll = new Emsi_SelBll();
		ecModel = bll.getEmSCSZSIData(escs_id);
		reason = "";
	}

	// 提交
	@Command("changeSZSI")
	public void changeSZSI(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			// 调用新增方法
			Emsi_OperateBll opbll = new Emsi_OperateBll();
			String[] message = opbll.changeSZSIColData(ecModel);
			if ("1".equals(message[0])) {
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				docOC.AddsubmitDoc(gd, String.valueOf(ecModel.getEscs_id()));
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("特殊变更操作出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Command("lx")
	public void lx() {
		RedirectUtil u = new RedirectUtil();

		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ecModel.getGid()
				+ "", "雇员服务中心");
	}

	// 退回
	@Command("szsiReturn")
	public void szsiReturn(@BindingParam("win") Window win) {
		try {
			if (reason != null && !"".equals(reason)) {
				EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();

				String[] message;
				message = dobll.declareSZSIChangeState(ecModel.getEscs_id(),
						ecModel.getEmsc_tapr_id(), 3, "",
						UserInfo.getUsername(), ecModel.getCid(), reason);

				if ("1".equals(message[0])) {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请填写退回原因。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("退回出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmShebaoChangeSZSIModel getEcModel() {
		return ecModel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
