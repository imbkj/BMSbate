package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoChangeForeignerModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_CheckOperateBll;

public class Emsi_Foreigner_ColDataController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmShebaoChangeForeignerModel efModel;
	private Emsc_DeclareSelBll selbll;
	// 页面值：原因
	private String reason = "";

	public Emsi_Foreigner_ColDataController() {
		selbll = new Emsc_DeclareSelBll();
		efModel = selbll.getForeignerChangeById(id);
	}

	// 提交转下一步
	@Command("pass")
	public void pass(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			Emsi_CheckOperateBll opBll = new Emsi_CheckOperateBll();
			String[] message = opBll.foreignerCheckPass(id,
					efModel.getEmsc_tapr_id(), reason, UserInfo.getUsername());
			if ("1".equals(message[0])) {
				// 处理材料
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				docOC.AddsubmitDoc(gd, String.valueOf(id));
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回
	@Command("FsbReturn")
	public void FsbReturn(@BindingParam("win") Window win) {
		try {
			if (reason != null && !"".equals(reason)) {
				Emsi_CheckOperateBll opBll = new Emsi_CheckOperateBll();
				String[] message = opBll.foreignerCheckReturn(id,
						efModel.getEmsc_tapr_id(), reason,
						UserInfo.getUsername());
				if ("1".equals(message[0])) {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请在备注处，填写退回原因。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("退回出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command("lx")
	public void lx() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + efModel.getGid()
				+ "", "雇员服务中心");
	}

	public EmShebaoChangeForeignerModel getEfModel() {
		return efModel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
