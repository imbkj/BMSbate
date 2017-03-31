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
import Util.UserInfo;
import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_CenterUpBankController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
	private List<EmShebaoCardInfoModel> list=bll.getEmShebaoCardInfoList(" and sbcd_id="+id);
	private Date tobanktime=new Date();
	private Date cosp_bsal_time=new Date();
	private String cosp_bsal_caliname;
	
	public Sbcd_CenterUpBankController()
	{
		if(list.size()>0)
		{
			m=list.get(0);
			if(m.getSbcd_tobankname()==null||m.getSbcd_tobankname().equals(""))
			{
				m.setSbcd_tobankname(UserInfo.getUsername());
			}
			cosp_bsal_caliname=m.getCosp_bsal_caliname();
		}
	}
	
	@Command
	public void centetsummit(@BindingParam("win") Window win)
	{
		if(m.getSbcd_tobankname()==null||m.getSbcd_tobankname().equals(""))
		{
			Messagebox.show("请输入中心提交银行人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(tobanktime==null)
		{
			Messagebox.show("请选择中心提交银行时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("中心已交银行");
			m.setSbcd_tobanktime(DateToStr(tobanktime));
			String sql=",sbcd_tobankname='"+m.getSbcd_tobankname()+"'";
			sql=sql+",sbcd_tobanktime='"+m.getSbcd_tobanktime()+"'";
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
	
	//发放盖章表格
	@Command
	public void centetstamp(@BindingParam("win") Window win)
	{
		if(cosp_bsal_caliname==null||cosp_bsal_caliname.equals(""))
		{
			Messagebox.show("请填写盖章表格交接人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(cosp_bsal_time==null)
		{
			Messagebox.show("请填写盖章表格交接时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("待盖章");
			m.setSbcd_stamptime(DateToStr(cosp_bsal_time));
			String sql=",sbcd_stampname='"+cosp_bsal_caliname+"'";
			sql=sql+",sbcd_stamptime='"+m.getSbcd_stamptime()+"'";
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
	
	//中心制卡
	@Command
	public void centermakecard(@BindingParam("win") Window win)
	{
		if(m.getSbcd_centermakename()==null||m.getSbcd_tobankname().equals(""))
		{
			Messagebox.show("请输入制卡人姓名", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(tobanktime==null)
		{
			Messagebox.show("请选择中心制卡时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("中心制卡");
			m.setSbcd_centermaketime(DateToStr(tobanktime));
			String sql=",sbcd_centermakename='"+m.getSbcd_centermakename()+"'";
			sql=sql+",sbcd_centermaketime='"+m.getSbcd_centermaketime()+"'";
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
	
	public EmShebaoCardInfoModel getM() {
		return m;
	}

	public void setM(EmShebaoCardInfoModel m) {
		this.m = m;
	}

	public Date getTobanktime() {
		return tobanktime;
	}

	public void setTobanktime(Date tobanktime) {
		this.tobanktime = tobanktime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCosp_bsal_time() {
		return cosp_bsal_time;
	}

	public void setCosp_bsal_time(Date cosp_bsal_time) {
		this.cosp_bsal_time = cosp_bsal_time;
	}
	
	public String getCosp_bsal_caliname() {
		return cosp_bsal_caliname;
	}

	public void setCosp_bsal_caliname(String cosp_bsal_caliname) {
		this.cosp_bsal_caliname = cosp_bsal_caliname;
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
}
