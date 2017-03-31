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

public class Sbcd_IfToClientController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
	private List<EmShebaoCardInfoModel> list=bll.getEmShebaoCardInfoList(" and sbcd_id="+id);
	private Date stafftime=new Date();
	private boolean ifvisible=false;
	private String tipsval="请选择下一步的状态",nextstate;
	
	public Sbcd_IfToClientController()
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
	public void ifsummit(@BindingParam("win") Window win)
	{
		String sql="";
		Integer tag=1;
		if(nextstate==null||nextstate.equals(""))
		{
			Messagebox.show("请选择状态", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState(nextstate);
			//转交客服
			if(nextstate.equals("转交客服"))
			{
				sql=sql+",sbcd_stateid='"+stateid+"'";
			}
			//员工已领
			else
			{
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
					m.setSbcd_stafftime(DateToStr(stafftime));
					sql=",sbcd_staffname='"+m.getSbcd_staffname()+"'";
					sql=sql+",sbcd_stafftime='"+m.getSbcd_stafftime()+"'";
					sql=sql+",sbcd_taketype='"+m.getSbcd_taketype()+"'";
					sql=sql+",sbcd_stateid='"+stateid+"'";
					tag=2;
				}
			}
			if(!sql.equals(""))
			{
				EmShebaoCardInfoOperateBll obll=new EmShebaoCardInfoOperateBll();
				String str[]=new String[5];
				//转交客服
				if(tag==1)
				{
					str=obll.EmShebaoCardUpdate(m, sql);
				}
				//员工已领
				else
				{
					str=obll.EmShebaoCardUpdateandEnd(m, sql);
				}
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
	}
	
	@Command
	@NotifyChange("ifvisible")
	public void changestate(@BindingParam("val") String val)
	{
		if(val!=null&&!val.equals(""))
		{
			if(val.equals("转交客服"))
			{
				ifvisible=false;
			}
			else
			{
				ifvisible=true;
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

	public boolean isIfvisible() {
		return ifvisible;
	}

	public void setIfvisible(boolean ifvisible) {
		this.ifvisible = ifvisible;
	}

	public String getTipsval() {
		return tipsval;
	}

	public void setTipsval(String tipsval) {
		this.tipsval = tipsval;
	}

	public String getNextstate() {
		return nextstate;
	}

	public void setNextstate(String nextstate) {
		this.nextstate = nextstate;
	}	
}
