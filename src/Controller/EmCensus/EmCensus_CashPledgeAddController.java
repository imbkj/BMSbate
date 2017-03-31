package Controller.EmCensus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCensus.EmCensus_OperateBll;

import Model.EmHJBorrowCardModel;
import Util.UserInfo;

public class EmCensus_CashPledgeAddController {
	private EmHJBorrowCardModel model = (EmHJBorrowCardModel) Executions
			.getCurrent().getArg().get("model");
	
	private Date cashpledtime;
	
	public EmCensus_CashPledgeAddController()
	{
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		if(model.getEhbc_cashpledtime()!=null&&!model.getEhbc_cashpledtime().equals(""))
		{
			try {
				cashpledtime=format.parse(model.getEhbc_cashpledtime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Command
	public void summit(@BindingParam("win") Window win)
	{
		if(model.getEhbc_cashpledname()==null||model.getEhbc_cashpledname().equals(""))
		{
			Messagebox.show("请输入交押金人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(cashpledtime==null)
		{
			Messagebox.show("请选择交押金时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(model.getEhbc_fee()==null||model.getEhbc_fee().equals(""))
		{
			Messagebox.show("请输入补交押金金额", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			EmCensus_OperateBll bll=new EmCensus_OperateBll();
			String sql="";
			sql=sql+",ehbc_fee='"+model.getEhbc_fee()+"'";
			sql=sql+",ehbc_feeeditname='"+UserInfo.getUsername()+"'";
			sql=sql+",ehbc_feeedittime=getdate()";
			sql=sql+",ehbc_cashpledname='"+model.getEhbc_cashpledname()+"'";
			sql=sql+",ehbc_cashpledtime='"+datetostr(cashpledtime)+"'";
			Integer k=bll.updateBorrowCardInfo(model, sql);
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
		
	}


	private String datetostr(Date date)
	{
		String str="";
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date!=null)
		{
			str=format.format(date);
		}
		return str;
	}
	public EmHJBorrowCardModel getModel() {
		return model;
	}


	public void setModel(EmHJBorrowCardModel model) {
		this.model = model;
	}


	public Date getCashpledtime() {
		return cashpledtime;
	}


	public void setCashpledtime(Date cashpledtime) {
		this.cashpledtime = cashpledtime;
	}
	
	
}
