package Controller.EmSheBaocard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoCardInfoModel;
import Util.UserInfo;
import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_ClientTakeCardController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
	private List<EmShebaoCardInfoModel> list=bll.getEmShebaoCardInfoList(" and sbcd_id="+id);
	private Date clienttaketime=new Date();
	
	public Sbcd_ClientTakeCardController()
	{
		if(list.size()>0)
		{
			m=list.get(0);
			if(m.getSbcd_clienttakename()==null||m.getSbcd_clienttakename().equals(""))
			{
				m.setSbcd_clienttakename(UserInfo.getUsername());
			}
		}
	}
	
	@Command
	public void clientsummit(@BindingParam("win") Window win)
	{
		if(m.getSbcd_clienttakename()==null||m.getSbcd_clienttakename().equals(""))
		{
			Messagebox.show("请输入客服签收人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(clienttaketime==null)
		{
			Messagebox.show("请选择客服签收时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("客服签收");
			m.setSbcd_clienttaketime(DateToStr(clienttaketime));
			String sql=",sbcd_clienttakename='"+m.getSbcd_clienttakename()+"'";
			sql=sql+",sbcd_clienttaketime='"+m.getSbcd_clienttaketime()+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
			EmShebaoCardInfoOperateBll obll=new EmShebaoCardInfoOperateBll();
			String str[]=obll.EmShebaoCardUpdate(m, sql);
			if(str[0]=="1")
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String str ="";
	   if(date!=null)
	   {
		   str = format.format(date);
	   }
	   return str;
	}

	public EmShebaoCardInfoModel getM() {
		return m;
	}

	public void setM(EmShebaoCardInfoModel m) {
		this.m = m;
	}

	public Date getClienttaketime() {
		return clienttaketime;
	}

	public void setClienttaketime(Date clienttaketime) {
		this.clienttaketime = clienttaketime;
	}
	

}
