package Controller.EmCensus;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCensus.EmCensus_OperateBll;

import Model.EmHJBorrowCardModel;

public class EmCensus_BorrowCardCancelController {
	private EmHJBorrowCardModel model=(EmHJBorrowCardModel) Executions.getCurrent().getArg().get("model");
	private String cancelcause="";
	private Map map= Executions.getCurrent().getArg();
	
	@Command
	public void cancel(@BindingParam("win") Window win)
	{
		if(cancelcause==null||cancelcause.equals(""))
		{
			Messagebox
			.show("请填写取消原因", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		EmCensus_OperateBll obll = new EmCensus_OperateBll();
		if (model.getEhbc_tarpid() != null) {
			obll.endjk(model, model.getEhbc_tarpid());
		}
		int k=obll.updateBorrowCardInfo(model, ",ehbc_state = 5,ehbc_backcase='"+cancelcause+"'");
		if(k>0)
		{
			map.put("flag", "1");
			Messagebox
			.show("取消成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}else
		{
			Messagebox
			.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getCancelcause() {
		return cancelcause;
	}

	public void setCancelcause(String cancelcause) {
		this.cancelcause = cancelcause;
	}
	
}
