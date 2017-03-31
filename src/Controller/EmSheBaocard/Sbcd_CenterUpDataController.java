package Controller.EmSheBaocard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoCardInfoModel;
import Util.UserInfo;
import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_CenterUpDataController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private List<EmShebaoCardInfoModel> list=bll.getEmShebaoCardInfoList(" and sbcd_id="+id);
	private EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
	private Date accpetime=new Date();
	
	public Sbcd_CenterUpDataController()
	{
		if(list.size()>0)
		{
			m=list.get(0);
			if(m.getSbcd_centerdataname()==null||m.getSbcd_centerdataname().equals(""))
			{
				m.setSbcd_centerdataname(UserInfo.getUsername());
			}
		}
	}
	
	//中心收盖章资料
	@Command
	public void summit(@BindingParam("gd") Grid gd,@BindingParam("win") Window win)
	{
		if(m.getSbcd_centerdataname()==null||m.getSbcd_centerdataname().equals(""))
		{
			Messagebox.show("请输入收资料人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(accpetime==null)
		{
			Messagebox.show("请选择收资料时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("中心核收");
			m.setSbcd_centerdatatime(DateToStr(accpetime));
			String sql=",sbcd_centerdataname='"+m.getSbcd_centerdataname()+"'";
			sql=sql+",sbcd_centerdatatime='"+m.getSbcd_centerdatatime()+"'";
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

	public Date getAccpetime() {
		return accpetime;
	}

	public void setAccpetime(Date accpetime) {
		this.accpetime = accpetime;
	}
	
	
}
