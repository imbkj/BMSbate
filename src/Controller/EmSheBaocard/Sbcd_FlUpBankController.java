package Controller.EmSheBaocard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoCardInfoModel;
import Util.UserInfo;
import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_FlUpBankController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
	private List<EmShebaoCardInfoModel> list=bll.getEmShebaoCardInfoList(" and sbcd_id="+id);
	private Date tobanktime=new Date();
	
	public Sbcd_FlUpBankController()
	{
		if(list.size()>0)
		{
			m=list.get(0);
			if(m.getSbcd_tobankname()==null||m.getSbcd_tobankname().equals(""))
			{
				m.setSbcd_tobankname(UserInfo.getUsername());
			}
		}
	}
	
	@Command
	public void flsummit(@BindingParam("win") Window win)
	{
		if(m.getSbcd_tobankname()==null||m.getSbcd_tobankname().equals(""))
		{
			Messagebox.show("请输入福利提交银行人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else if(tobanktime==null)
		{
			Messagebox.show("请选择福利提交银行时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Integer stateid=bll.getState("已交银行");
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
	
	//弹出退回页面
	@Command
	public void back(@BindingParam("win") Window win)
	{
		Map map=new HashMap<>();
		map.put("model", m);
		map.put("tag", "1");
		Window window =(Window)Executions.createComponents("../EmSheBaocard/Sbcd_FlBack.zul", null, map);
		window.doModal();
		if(map.get("tag")!=null&&!map.get("tag").equals(""))
		{
			win.detach();
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
	
}
