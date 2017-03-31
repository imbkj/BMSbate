package Controller.EmSheBaocard;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import Model.EmShebaoCardInfoModel;

public class Sbcd_EditStateController {
	private EmShebaoCardInfoModel model=(EmShebaoCardInfoModel) Executions.getCurrent().getArg().get("m");
	private String cancelcase="";
	
	@Command
	public void summit(@BindingParam("statename") Combobox statename,@BindingParam("win") Window win)
	{
		if(statename.getValue()==null||statename.getValue().equals(""))
		{
			Messagebox.show("请选择状态", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(cancelcase==null||cancelcase.equals(""))
		{
			Messagebox.show("取消原因不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		int stateid=Integer.parseInt(statename.getSelectedItem().getValue().toString());
		EmShebaoCardInfoOperateBll bll = new EmShebaoCardInfoOperateBll();
		String[] str=bll.EmShebaoCardCancelAndEnd(model,cancelcase, stateid, statename.getValue());
		if(str[0]=="1")
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public EmShebaoCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmShebaoCardInfoModel model) {
		this.model = model;
	}

	public String getCancelcase() {
		return cancelcase;
	}

	public void setCancelcase(String cancelcase) {
		this.cancelcase = cancelcase;
	}
	
}
