package Controller.EmExpress;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmExpress.EmExpressInfoOperateBll;

import Model.EmExpressInfoModel;

public class EmExpressInfosUpdateController {
	private List<EmExpressInfoModel> list = (List<EmExpressInfoModel>)Executions.getCurrent().getArg().get("lt");
	private EmExpressInfoModel model=new EmExpressInfoModel();
	private Date sendtime=null;
	
	
	//合并发件提交事件
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		String str=ifempty();
		if(!str.equals(""))
		{
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			for(int i=0;i<list.size();i++)
			{
				EmExpressInfoModel m=list.get(i);
				model.setExpr_tarpid(m.getExpr_tarpid());
				model.setExpr_id(m.getExpr_id());
				EmExpressInfoOperateBll blla=new EmExpressInfoOperateBll();
				String sql="";
				sql=",expr_company='"+model.getExpr_company()+"',expr_waynumber='"+model.getExpr_waynumber()+"'" +
						",expr_sendtime='"+datechange(sendtime)+"',expr_state=3,expr_fee='"+model.getExpr_fee()+"'";
				String[] strs=blla.updateExpress(sql, model);
				if(strs[0]!=null&&strs[0].equals("1"))
				{
					Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
	}
	
	private String ifempty()
	{
		String str="";
		if(model.getExpr_company()==null||model.getExpr_company().equals(""))
		{
			str="请输入快递公司";
		}
		else if(model.getExpr_waynumber()==null||model.getExpr_waynumber().equals(""))
		{
			str="请输入运单号";
		}
		else if(model.getExpr_sendname()==null||model.getExpr_sendname().equals(""))
		{
			str="请输入发件人";
		}
		else if(sendtime==null||sendtime.equals(""))
		{
			str="请输入发送时间";
		}
		else if(model.getExpr_fee()==null||model.getExpr_fee().equals(""))
		{
			str="请输入快递费";
		}
		return str;
	}

	public EmExpressInfoModel getModel() {
		return model;
	}
	public void setModel(EmExpressInfoModel model) {
		this.model = model;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	private String datechange(Date d)
	{
		String date="";
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		if(d!=null&&!d.equals(""))
		{
			date=time.format(d);
		}
		return date;
	}

}
