package Controller.EmSheBaocard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoCardInfoModel;
import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_ClientSendcardController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
	private List<EmShebaoCardInfoModel> list=bll.getEmShebaoCardInfoList(" and sbcd_id="+id);
	private Date stafftime=new Date();
	
	public Sbcd_ClientSendcardController()
	{
		if(list.size()>0)
		{
			m=list.get(0);
			if(m.getSbcd_fltakename()==null||m.getSbcd_fltakename().equals(""))
			{
				m.setSbcd_staffname(m.getSbcd_name());
			}
		}
	}
	
	@Command
	public void sendsummit(@BindingParam("win") Window win)
	{
		String sql="";
		if(m.getSbcd_taketype()==null||m.getSbcd_taketype().equals(""))
		{
			Messagebox.show("请选择领卡方式", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(m.getSbcd_staffname()==null||m.getSbcd_staffname().equals(""))
		{
			Messagebox.show("请选择领卡人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(stafftime==null)
		{
			Messagebox.show("请选择员工领卡时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("员工已领");
			m.setSbcd_stafftime(DateToStr(stafftime));
			sql=",sbcd_staffname='"+m.getSbcd_staffname()+"'";
			sql=sql+",sbcd_stafftime='"+m.getSbcd_stafftime()+"'";
			sql=sql+",sbcd_taketype='"+m.getSbcd_taketype()+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
			EmShebaoCardInfoOperateBll obll=new EmShebaoCardInfoOperateBll();
			String str[]=new String[5];
			str=obll.EmShebaoCardUpdateandEnd(m, sql);
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

	public Date getStafftime() {
		return stafftime;
	}

	public void setStafftime(Date stafftime) {
		this.stafftime = stafftime;
	}
}
