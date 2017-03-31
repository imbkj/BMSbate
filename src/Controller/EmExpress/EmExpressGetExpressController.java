package Controller.EmExpress;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmExpressInfoModel;
import Util.UserInfo;
import bll.EmExpress.EmExpressInfoOperateBll;
import bll.EmExpress.EmExpressInfoSelectBll;

public class EmExpressGetExpressController {
	String id = Executions.getCurrent().getArg().get("daid").toString();
	String tperid = Executions.getCurrent().getArg().get("id").toString();
	private EmExpressInfoSelectBll bll=new EmExpressInfoSelectBll();
	private List<EmExpressInfoModel> list=bll.getEmExpressInfoList(" and expr_id="+id,"");
	private EmExpressInfoModel model=new EmExpressInfoModel();
	private String name=UserInfo.getUsername();
	private Date sendtime=null;
	
	public EmExpressGetExpressController()
	{
		if(list.size()>0)
		{
			model=list.get(0);
			if(model.getExpr_state().equals(2))
			{
				name=model.getExpr_acceptname();
				model.setExpr_sendname(UserInfo.getUsername());
			}
		}
	}
	
	//前台核收快件
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
			EmExpressInfoOperateBll blla=new EmExpressInfoOperateBll();
			String sql="";
			
			//前台核收快递
			if(model.getExpr_state().equals(1))
			{
				sql=",expr_acceptname='"+name+"',expr_accepttime='"+datechange(model.getExpr_accepttime())+"',expr_state=2";
			}
			//前台发送快递
			else if(model.getExpr_state().equals(2))
			{
				sql=",expr_company='"+model.getExpr_company()+"',expr_waynumber='"+model.getExpr_waynumber()+"'" +
						",expr_sendtime='"+datechange(sendtime)+"',expr_state=3,expr_fee='"+model.getExpr_fee()+"'";
			}
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
	
	private String ifempty()
	{
		String str="";
		if(model.getExpr_state().equals(1))
		{
			if(name==null||name.equals(""))
			{
				str="请输入快递核收人姓名";
			}
			else if(model.getExpr_accepttime()==null||model.getExpr_accepttime().equals(""))
			{
				str="请输入快递核收时间";
			}
		}
		else if(model.getExpr_state().equals(2))
		{
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
		}
		return str;
	}

	public EmExpressInfoModel getModel() {
		return model;
	}

	public void setModel(EmExpressInfoModel model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
