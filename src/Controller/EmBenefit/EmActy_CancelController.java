package Controller.EmBenefit;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;

public class EmActy_CancelController {
	private String cancelcase;
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	
	
	public String getCancelcase() {
		return cancelcase;
	}



	public void setCancelcase(String cancelcase) {
		this.cancelcase = cancelcase;
	}



	@Command
	public void cancel(@BindingParam("win") Window win)
	{
		if(cancelcase!=null&&!cancelcase.equals(""))
		{
			EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
			Integer k=obll.EmWelfare_cancel(id, cancelcase);
			if(k>0)
			{
				Messagebox.show("取消成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("取消失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请输入取消原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

}
