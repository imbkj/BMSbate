package Controller.EmExpress;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmExpress.EmExpressInfoOperateBll;

import Model.EmExpressContactInfoModel;

public class ExpressAddressInfoUpdateController {
	private EmExpressContactInfoModel model = (EmExpressContactInfoModel)Executions.
			getCurrent().getArg().get("model");
	public ExpressAddressInfoUpdateController() {
		// TODO Auto-generated constructor stub
	}
	
	//修改事件
	@Command
	public void edit(@BindingParam("win") Window win)
	{
		EmExpressInfoOperateBll bll=new EmExpressInfoOperateBll();
		Integer k=bll.EmExpressContactInfoEdit(model);
		if(k>0)
		{
			Messagebox.show("修改成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public EmExpressContactInfoModel getModel() {
		return model;
	}
	public void setModel(EmExpressContactInfoModel model) {
		this.model = model;
	}
	
}
