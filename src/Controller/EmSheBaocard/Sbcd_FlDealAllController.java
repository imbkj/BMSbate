package Controller.EmSheBaocard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoCardInfoModel;
import Util.UserInfo;

public class Sbcd_FlDealAllController {
	private String statename = (String)Executions.getCurrent().getArg().get("statename");
	private String sbcd_content = (String)Executions.getCurrent().getArg().get("sbcd_content");
	private Integer docid=Integer.parseInt(Executions.getCurrent().getArg().get("docid").toString());
	private List<EmShebaoCardInfoModel> list = (List<EmShebaoCardInfoModel>)Executions.getCurrent().getArg().get("list");
	private Date fltime;
	private String flname=UserInfo.getUsername(),upbank;
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private boolean ifvisible=false;
	private String nextstate,taketype;
	private String tipsval="请选择下一步的状态";
	private Integer tag=1;
	
	public Sbcd_FlDealAllController()
	{
		if(statename.equals("客服签收"))
		{
			ifvisible=true;
		}
	}
	
	@Command
	public void flsummit(@BindingParam("win") Window win,@BindingParam("gd") Grid gd)
	{
		String sql="";
		if(statename.equals("中心核收"))
		{
			Integer stateid=bll.getState("福利核收");
			sql=",sbcd_flacceptname='"+flname+"'";
			sql=sql+",sbcd_flaccpettime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("福利核收"))
		{
			Integer stateid=bll.getState("已交银行");
			sql=",sbcd_tobankname='"+flname+"'";
			sql=sql+",sbcd_tobanktime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("已交银行"))
		{
			Integer stateid=bll.getState("福利领卡");
			sql=",sbcd_fltakename='"+flname+"'";
			sql=sql+",sbcd_fltaketime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("待打单"))
		{
			Integer stateid=bll.getState("已打单");
			sql=",sbcd_printname='"+flname+"'";
			sql=sql+",sbcd_printtime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		if(sql!=null&&!sql.equals(""))
		{
			EmShebaoCardInfoOperateBll obll=new EmShebaoCardInfoOperateBll();
			Integer k=0;
			if(list!=null)
			{
				for(int i=0;i<list.size();i++)
				{
					String str[]=new String[5];
					EmShebaoCardInfoModel m=list.get(i);
					if(tag==1)
					{
						str=obll.EmShebaoCardUpdate(m, sql);
					}
					else if(tag==2)
					{
						//服务中心在中心签收后选择员工已领则更新员工领卡信息并完结流程
						str=obll.EmShebaoCardUpdateandEnd(m, sql);
					}
					if(str[0]=="1")
					{
						k=k+1;
					}
				}
				if(k>0)
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
	
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public String getSbcd_content() {
		return sbcd_content;
	}
	public void setSbcd_content(String sbcd_content) {
		this.sbcd_content = sbcd_content;
	}
	public Date getFltime() {
		return fltime;
	}
	public void setFltime(Date fltime) {
		this.fltime = fltime;
	}
	public String getUpbank() {
		return upbank;
	}
	public void setUpbank(String upbank) {
		this.upbank = upbank;
	}
	public String getFlname() {
		return flname;
	}
	public void setFlname(String flname) {
		this.flname = flname;
	}


	public boolean isIfvisible() {
		return ifvisible;
	}


	public void setIfvisible(boolean ifvisible) {
		this.ifvisible = ifvisible;
	}


	public String getNextstate() {
		return nextstate;
	}


	public void setNextstate(String nextstate) {
		this.nextstate = nextstate;
	}


	public String getTaketype() {
		return taketype;
	}


	public void setTaketype(String taketype) {
		this.taketype = taketype;
	}

	public String getTipsval() {
		return tipsval;
	}

	public void setTipsval(String tipsval) {
		this.tipsval = tipsval;
	}

	public Integer getDocid() {
		return docid;
	}

	public void setDocid(Integer docid) {
		this.docid = docid;
	}
	
}
