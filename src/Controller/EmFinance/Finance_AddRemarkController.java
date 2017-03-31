package Controller.EmFinance;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.cfma_OperateBll;
import bll.EmFinance.EmFinance_SelectBll;

public class Finance_AddRemarkController {
	private String billno=(String) Executions.getCurrent().getArg().get("billno");
	private EmFinance_SelectBll bll = new EmFinance_SelectBll();
	private String remark="";
	
	public Finance_AddRemarkController()
	{
		remark=bll.getBillRemark(billno);
		
	}
	
	
	@Command
	public void summit(@BindingParam("win") Window win)
	{
		cfma_OperateBll obll = new cfma_OperateBll();
		if(remark!=null&&!remark.equals(""))
		{
			Integer k=obll.billRemarkAdd(billno, remark);
			if(k>0)
			{
				Messagebox.show("添加成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("添加失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
