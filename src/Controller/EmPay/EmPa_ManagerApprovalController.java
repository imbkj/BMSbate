package Controller.EmPay;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmPayModel;
import bll.EmPay.EmPa_OperateBll;

public class EmPa_ManagerApprovalController {
	private EmPayModel model=(EmPayModel) Executions.getCurrent().getArg().get("model");

	@Command
	public void submit(@BindingParam("win") Window win)
	{
		model.setApprovalType("总经理");
		final EmPa_OperateBll bll=new EmPa_OperateBll();
		int k=bll.Approval(model);
		if(k>0)
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public EmPayModel getModel() {
		return model;
	}

	public void setModel(EmPayModel model) {
		this.model = model;
	}

}
