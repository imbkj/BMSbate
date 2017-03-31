package Controller.CoServicePolicy;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoServicePolicy.SePy_CityPolicyOperateBll;

import Model.CoServicePolicyTypeModel;

public class SyPy_TypeUpdateController {
	private CoServicePolicyTypeModel model = (CoServicePolicyTypeModel)Executions.getCurrent().getArg().get("model");

	@Command
	public void update(@BindingParam("win") Window win)
	{
		if(model.getNote_type()==null||model.getNote_type().equals(""))
		{
			Messagebox.show("类型名称不能为空","提示",Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			SePy_CityPolicyOperateBll bll=new SePy_CityPolicyOperateBll();
			Integer k=bll.updateCoServicePolicyType(model.getNote_type(), model.getNote_order(), model.getNote_id());
			if(k>0)
			{
				Messagebox.show("修改成功","提示",Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("修改失败","提示",Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public CoServicePolicyTypeModel getModel() {
		return model;
	}

	public void setModel(CoServicePolicyTypeModel model) {
		this.model = model;
	}
	
}
