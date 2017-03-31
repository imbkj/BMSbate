package Controller.EmExpress;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmExpress.EmExpressInfoOperateBll;
import bll.EmExpress.EmExpressInfoSelectBll;

import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;

public class EmExpressInfoUpdateController {
	private EmExpressInfoModel model = (EmExpressInfoModel) Executions.getCurrent().getArg()
			.get("model");
	private EmExpressInfoSelectBll bll=new EmExpressInfoSelectBll();
	private List<EmExpressContactInfoModel> list=bll.getEmExpressContactInfoList(" and gid="+model.getGid());

	@Command
	public void submit(@BindingParam("win") Window win)
	{
		EmExpressInfoOperateBll bl=new EmExpressInfoOperateBll();
		Integer k=bl.EmExpressInfoupdate(model);
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
	
	//地址下拉列表
	@Command
	@NotifyChange("model")
	public void changelist(@BindingParam("model") EmExpressContactInfoModel m)
	{
		model.setExct_receivename(m.getExct_receivename());
		model.setExct_mobile(m.getExct_mobile());
		model.setExct_code(m.getExct_code());
		model.setExct_phone(m.getExct_phone());
		model.setExct_id(m.getExct_id());
		model.setExpr_exct_id(m.getExct_id());
	}
	
	public EmExpressInfoModel getModel() {
		return model;
	}
	public void setModel(EmExpressInfoModel model) {
		this.model = model;
	}
	public List<EmExpressContactInfoModel> getList() {
		return list;
	}
	public void setList(List<EmExpressContactInfoModel> list) {
		this.list = list;
	}
	
	
	
}
