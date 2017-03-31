package Controller.EmReg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

import Model.EmRegistrationInfoModel;
import Util.UserInfo;

public class EmReg_StopDealController {
	private EmRegistrationInfoModel model= (EmRegistrationInfoModel) Executions.getCurrent().getArg().get("model");
	private Date erin_handover_date;
	private Date erin_auditing_date;
	private Date erin_upload_date;
	private Date erin_declare_date;
	private Date erin_wd_seal_date;
	private Date erin_hpp_date;
	private Date erin_success_date;
	private Date erin_in_file_date;
	private Date erin_get_date;
	private String remark;
	private EmReg_ListBll bll=new EmReg_ListBll();
	private List<EmRegistrationInfoModel> list=bll.getStateTimeInfoList(model.getErin_id());
	
	
	public EmReg_StopDealController()
	{
		for(EmRegistrationInfoModel m:list)
		{
			getDate(m);
		}
	}
	
	private Date getDate(EmRegistrationInfoModel m)
	{
		Date d=null;
		if(m.getErsr_statetime()!=null)
		{
			if(m.getStateid()==2)
			{
				erin_handover_date=StrToDate(m.getErsr_statetime());//交接时间
			}
			else if(m.getStateid()==4)
			{
				erin_declare_date=StrToDate(m.getErsr_statetime());//申报时间
			}
			else if(m.getStateid()==6)
			{
				erin_wd_seal_date=StrToDate(m.getErsr_statetime());//收盖章日期
			}
			else if(m.getStateid()==7)
			{
				erin_success_date=StrToDate(m.getErsr_statetime());//完成日期
			}
			else if(m.getStateid()==8)
			{
				erin_get_date=StrToDate(m.getErsr_statetime());//领取日期
			}
		}
		return d;
	}
	
	//提交
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		Integer k=0,l=0;
		EmReg_OperateBll obll=new EmReg_OperateBll();
		if(erin_handover_date!=null)
		{
			EmRegistrationInfoModel m=new EmRegistrationInfoModel();
			m.setErin_state(2);
			m.setErsr_addname(UserInfo.getUsername()); 
			m.setErsr_statetime(DateToStr(erin_handover_date));
			m.setErsr_remark("");
			m.setErin_id(model.getErin_id());
			k=k+obll.UpdateTime(m);
			l=1;
		}
		if(erin_declare_date!=null)
		{
			EmRegistrationInfoModel m=new EmRegistrationInfoModel();
			m.setErin_state(4);
			m.setErsr_addname(UserInfo.getUsername()); 
			m.setErsr_statetime(DateToStr(erin_declare_date));
			m.setErsr_remark("");
			m.setErin_id(model.getErin_id());
			k=k+obll.UpdateTime(m);
			l=1;
		}
		if(erin_wd_seal_date!=null)
		{
			EmRegistrationInfoModel m=new EmRegistrationInfoModel();
			m.setErin_state(6);
			m.setErsr_addname(UserInfo.getUsername()); 
			m.setErsr_statetime(DateToStr(erin_wd_seal_date));
			m.setErsr_remark("");
			m.setErin_id(model.getErin_id());
			k=k+obll.UpdateTime(m);
			l=1;
		}
		if(erin_success_date!=null)
		{
			EmRegistrationInfoModel m=new EmRegistrationInfoModel();
			m.setErin_state(7);
			m.setErsr_addname(UserInfo.getUsername()); 
			m.setErsr_statetime(DateToStr(erin_success_date));
			m.setErsr_remark("");
			m.setErin_id(model.getErin_id());
			k=k+obll.UpdateTime(m);
			l=1;
		}
		if(erin_get_date!=null)
		{
			EmRegistrationInfoModel m=new EmRegistrationInfoModel();
			m.setErin_state(8);
			m.setErsr_addname(UserInfo.getUsername());
			m.setErsr_statetime(DateToStr(erin_get_date));
			m.setErsr_remark("");
			m.setErin_id(model.getErin_id());
			k=k+obll.UpdateTime(m);
			l=1;
		}
		if(k>0&&l>0)
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else if(k==0&&l==0)
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   Date date = null;
	   try {
		   if(str!=null)
		   {
			   date = format.parse(str);
		   }
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String str = format.format(date);
	   return str;
	} 
	public EmRegistrationInfoModel getModel() {
		return model;
	}
	public void setModel(EmRegistrationInfoModel model) {
		this.model = model;
	}
	public Date getErin_handover_date() {
		return erin_handover_date;
	}
	public void setErin_handover_date(Date erin_handover_date) {
		this.erin_handover_date = erin_handover_date;
	}
	public Date getErin_auditing_date() {
		return erin_auditing_date;
	}
	public void setErin_auditing_date(Date erin_auditing_date) {
		this.erin_auditing_date = erin_auditing_date;
	}
	public Date getErin_upload_date() {
		return erin_upload_date;
	}
	public void setErin_upload_date(Date erin_upload_date) {
		this.erin_upload_date = erin_upload_date;
	}
	public Date getErin_declare_date() {
		return erin_declare_date;
	}
	public void setErin_declare_date(Date erin_declare_date) {
		this.erin_declare_date = erin_declare_date;
	}
	public Date getErin_wd_seal_date() {
		return erin_wd_seal_date;
	}
	public void setErin_wd_seal_date(Date erin_wd_seal_date) {
		this.erin_wd_seal_date = erin_wd_seal_date;
	}
	public Date getErin_hpp_date() {
		return erin_hpp_date;
	}
	public void setErin_hpp_date(Date erin_hpp_date) {
		this.erin_hpp_date = erin_hpp_date;
	}
	public Date getErin_success_date() {
		return erin_success_date;
	}
	public void setErin_success_date(Date erin_success_date) {
		this.erin_success_date = erin_success_date;
	}
	public Date getErin_in_file_date() {
		return erin_in_file_date;
	}
	public void setErin_in_file_date(Date erin_in_file_date) {
		this.erin_in_file_date = erin_in_file_date;
	}
	public Date getErin_get_date() {
		return erin_get_date;
	}
	public void setErin_get_date(Date erin_get_date) {
		this.erin_get_date = erin_get_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
}
