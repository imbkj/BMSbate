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

public class Sbcd_CenterDealAllController {
	private String statename = (String)Executions.getCurrent().getArg().get("statename");
	private String sbcd_content = (String)Executions.getCurrent().getArg().get("sbcd_content");
	private List<EmShebaoCardInfoModel> list = (List<EmShebaoCardInfoModel>)Executions.getCurrent().getArg().get("list");
	private Date fltime,tobanktime,centertaketime;
	private String flname=UserInfo.getUsername(),upbank,sbcd_tobankname=UserInfo.getUsername(),sbcd_centertakename=UserInfo.getUsername();
	private EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
	private boolean ifvisible=false;
	private String nextstate,taketype;
	private String tipsval="请选择下一步的状态";
	private Integer tag=1;
	private Date cosp_bsal_time=new Date();
	private String cosp_bsal_caliname;
	private String sbcd_centermakename=UserInfo.getUsername();
	private Date centermaketime=new Date();
	
	public Sbcd_CenterDealAllController()
	{
		if(statename.equals("客服签收"))
		{
			ifvisible=true;
		}
	}
	
	@Command
	public void flsummit(@BindingParam("win") Window win)
	{
		String sql="";
		if(statename.equals("福利领卡"))
		{
			Integer stateid=bll.getState("中心签收");
			sql=",sbcd_centertakename='"+flname+"'";
			sql=sql+",sbcd_centertaketime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("中心签收"))
		{
			Integer stateid=bll.getState(nextstate);
			//转交客服
			if(nextstate.equals("转交客服"))
			{
				sql=sql+",sbcd_stateid='"+stateid+"'";
			}
			else
			{
				sql=",sbcd_staffname='"+flname+"'";
				sql=sql+",sbcd_stafftime='"+DateToStr(fltime)+"'";
				sql=sql+",sbcd_taketype='"+taketype+"'";
				sql=sql+",sbcd_stateid='"+stateid+"'";
				tag=2;
			}
		}
		else if(statename.equals("转交客服"))
		{
			Integer stateid=bll.getState("客服签收");
			sql=",sbcd_clienttakename='"+flname+"'";
			sql=sql+",sbcd_clienttaketime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("客服签收"))
		{
			Integer stateid=bll.getState("员工已领");
			sql=",sbcd_staffname='"+flname+"'";
			sql=sql+",sbcd_stafftime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_taketype='"+taketype+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("已打单"))
		{
			Integer stateid=bll.getState("待盖章");
			sql=",sbcd_stampname='"+cosp_bsal_caliname+"'";
			sql=sql+",sbcd_stamptime='"+DateToStr(cosp_bsal_time)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("待盖章"))
		{
			Integer stateid=bll.getState("中心核收");
			sql=",sbcd_centerdataname='"+flname+"'";
			sql=sql+",sbcd_centerdatatime='"+DateToStr(fltime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("待制卡"))
		{
			Integer stateid=bll.getState("中心制卡");
			sql=",sbcd_centermakename='"+sbcd_centermakename+"'";
			sql=sql+",sbcd_centermaketime='"+DateToStr(centermaketime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("中心制卡"))
		{
			Integer stateid=bll.getState("中心已交银行");
			sql=",sbcd_tobankname='"+sbcd_tobankname+"'";
			sql=sql+",sbcd_tobanktime='"+DateToStr(tobanktime)+"'";
			sql=sql+",sbcd_stateid='"+stateid+"'";
		}
		else if(statename.equals("中心已交银行"))
		{
			Integer stateid=bll.getState("中心签收");
			sql=",sbcd_centertakename='"+sbcd_centertakename+"'";
			sql=sql+",sbcd_centertaketime='"+DateToStr(centertaketime)+"'";
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

	public Date getTobanktime() {
		return tobanktime;
	}

	public void setTobanktime(Date tobanktime) {
		this.tobanktime = tobanktime;
	}

	public Date getCentertaketime() {
		return centertaketime;
	}

	public void setCentertaketime(Date centertaketime) {
		this.centertaketime = centertaketime;
	}

	public String getSbcd_tobankname() {
		return sbcd_tobankname;
	}

	public void setSbcd_tobankname(String sbcd_tobankname) {
		this.sbcd_tobankname = sbcd_tobankname;
	}

	public String getSbcd_centertakename() {
		return sbcd_centertakename;
	}

	public void setSbcd_centertakename(String sbcd_centertakename) {
		this.sbcd_centertakename = sbcd_centertakename;
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

	public String getSbcd_centermakename() {
		return sbcd_centermakename;
	}

	public void setSbcd_centermakename(String sbcd_centermakename) {
		this.sbcd_centermakename = sbcd_centermakename;
	}

	public Date getCentermaketime() {
		return centermaketime;
	}

	public void setCentermaketime(Date centermaketime) {
		this.centermaketime = centermaketime;
	}
	
}
