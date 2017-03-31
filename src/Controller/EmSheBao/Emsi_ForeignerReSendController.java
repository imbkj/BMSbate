package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoChangeForeignerModel;
import Util.UserInfo;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;

public class Emsi_ForeignerReSendController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmShebaoChangeForeignerModel efModel;
	private Emsc_DeclareSelBll selbll;

	public Emsi_ForeignerReSendController() {
		selbll = new Emsc_DeclareSelBll();
		efModel = selbll.getForeignerChangeById(id);
	}

	@Command("reSend")
	public void confirmBj(@BindingParam("win") Window win) {
		if (Messagebox.show("是否确认重新发送该社保申报信息？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			try {
				// 修改状态为未申报
				efModel.setEmsc_ifdeclare("7");
				Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
				String[] mes = opbll.ForeignerDeclareUpState(efModel,
						UserInfo.getUsername());
				if ("1".equals(mes[0])) {
					// 成功提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					// 错误提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("重新发送社保补缴出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public EmShebaoChangeForeignerModel getEfModel() {
		return efModel;
	}

}
