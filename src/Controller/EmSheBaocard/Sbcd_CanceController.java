package Controller.EmSheBaocard;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;

import Model.EmShebaoCardInfoModel;

public class Sbcd_CanceController {
	private EmShebaoCardInfoModel model = (EmShebaoCardInfoModel) Executions
			.getCurrent().getArg().get("model");
	private String content = "";
	private Map map=Executions.getCurrent().getArg();
	
	@Command
	public void CancelSubmit(@BindingParam("win") Window win)
	{
		if(content==null||content.equals(""))
		{
			Messagebox.show("请输入取消原因", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		EmShebaoCardInfoOperateBll obll=new EmShebaoCardInfoOperateBll();
		String[] str=obll.EmShebaoCardCancelAndEnd(model, content);
		if(str[0]=="1")
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			map.put("flag", "1");
			win.detach();
		}
		else
		{
			Messagebox.show("取消失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
