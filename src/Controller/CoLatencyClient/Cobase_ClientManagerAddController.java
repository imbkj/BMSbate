package Controller.CoLatencyClient;

import impl.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.poi.util.Beta;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.LoginDal;

import service.MessageService;

import Model.CoBaseModel;
import Model.CoLatencyClientModel;
import Model.CoServiceRequestModel;
import Model.PubTradeModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.SystemControl.Data_PopedomBll;

public class Cobase_ClientManagerAddController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private CoBaseModel cmodel=new CoBaseModel();
	private CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
	private CoServiceRequestSelectBll sbll=new CoServiceRequestSelectBll();
	private List<PubTradeModel> tradelist=bll.getTradeIndo();
	private CoServiceRequestModel servicemodel=new CoServiceRequestModel();
	private List<String> datelistt=new ArrayList<String>();//天的列表
	private CoLatencyClientModel frommodel = new CoLatencyClientModel();
	private String sign="",dtdservice="",wt="",fservice="";
	private List<String> loginlist=new ArrayList<String>();
	private List<String> managerlist=sbll.getClientManagerlist(Integer.parseInt(id));
	public Cobase_ClientManagerAddController()
	{
		cmodel=sbll.getCobaseInfo(" and cid="+id);
		loginlist=sbll.getLoginlistByCid(Integer.parseInt(id));
		servicemodel=sbll.getRequestInfo(" and a.cid="+id);
		datelistt=datelistAdd();
		frommodel=sbll.getCoLatencyClient(Integer.parseInt(id));
	}
	
	//把数据的数字转为中文
	private void changeCH()
	{
		if(servicemodel.getCsqe_dtdservice()==1)
		{
			dtdservice="是";
		}
		else if(servicemodel.getCsqe_dtdservice()==0)
		{
			dtdservice="否";
		}
		
		if(servicemodel.getCsqe_wt()==1)
		{
			wt="是";
		}
		else if(servicemodel.getCsqe_wt()==0)
		{
			wt="否";
		}
		if(servicemodel.getCsqe_fservice()==1)
		{
			fservice="是";
		}
		else if(servicemodel.getCsqe_fservice()==0)
		{
			fservice="否";
		}
			
	}
	
	
	//分配客服经理
	@Command
	public void summit(@BindingParam("win") Window win,@BindingParam("clientmanager") String clientmanager)
	{
		if(clientmanager!=null&&!clientmanager.equals(""))
		{
			String sql=",coba_clientmanager='"+clientmanager+"'";
			String[] str=new String[5];
			str=bll.CobaseUpdate(sql, Integer.parseInt(tperid), Integer.parseInt(id));
			
			//添加客服经理权限
			//潜在客户添加人权限添加
			Data_PopedomBll ddll =new Data_PopedomBll();
			ddll.Data_Popedomaddkfjl(frommodel.getCola_id(), clientmanager,Integer.parseInt(id));
			
			if(str[0]=="1")
			{
				String content=cmodel.getCoba_shortname()+"客服助理已分配，客服助理为："+clientmanager;
				String tittle="客服助理分配";
				sendMsg(cmodel.getCoba_addname(),content,tittle);
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请选择客服助理", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//分配客服代表
	@Command
	public void clientadd(@BindingParam("win") Window win,@BindingParam("client") String client)
	{
		int msg=0;
		if(client!=null&&!client.equals(""))
		{
			String sql=",coba_client='"+client+"'";
			String[] str=new String[5];
			str=bll.CobaseUpdate(sql, Integer.parseInt(tperid), Integer.parseInt(id));
			//添加客服及上级权限
			msg=bll.Data_Popedomadd(Integer.parseInt(id), client);
			if(str[0]=="1")
			{
				String content=cmodel.getCoba_shortname()+"客服已分配，客服代表为："+client;
				sendMsg(cmodel.getCoba_clientmanager(),content,"客服代表分配完成");
				if(cmodel.getCoba_addname()!=null&&cmodel.getCoba_clientmanager()!=null)
				{
					if(!cmodel.getCoba_addname().equals(cmodel.getCoba_clientmanager()))
					{
						sendMsg(cmodel.getCoba_addname(),content,"客服代表分配完成");
					}
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				
				MessageService msgservice=new MessageImpl("CoBase",Integer.parseInt(id));
				SysMessageModel msgmodel=new SysMessageModel();
						
				LoginDal d =new LoginDal();
			 
				String tittle="请分配中心员服";
				msgmodel.setEmail(1);
				msgmodel.setEmailtitle(tittle);
				msgmodel.setSyme_content("请分配公司编号:"+cmodel.getCid()+"的员服人员");
				msgmodel.setSymr_name("曾琴娜");// 收件人姓名
				msgmodel.setSymr_log_id(d.getUserIDByname("曾琴娜"));// ;
				msgservice.Add(msgmodel);
				msgmodel.setSymr_name("林敏");// 收件人姓名
				msgmodel.setSymr_log_id(d.getUserIDByname("林敏"));// ;
				msgservice.Add(msgmodel);
				msgmodel.setSymr_name("郭姗姗");// 收件人姓名
				msgmodel.setSymr_log_id(d.getUserIDByname("郭姗姗"));// ;
				msgservice.Add(msgmodel);
				
				msgmodel.setSymr_name("赵敏捷");// 收件人姓名
				msgmodel.setSymr_log_id(d.getUserIDByname("赵敏捷"));// ;
				msgservice.Add(msgmodel);
				
				win.detach();
				
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请选择客服代表", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//发短信
	private void sendMsg(String symr_name,String content,String tittle)
	{
		//发短信
		MessageService msgservice=new MessageImpl("CoBase",Integer.parseInt(id));
		SysMessageModel msgmodel=new SysMessageModel();
		msgmodel.setSyme_content(content);//短信内容
		msgmodel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));//发件人id
		msgmodel.setSyme_title(tittle);
		LoginDal d =new LoginDal();
		msgmodel.setSymr_name(symr_name);//收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname(symr_name));
		msgservice.Add(msgmodel);

//		 
//		tittle=tittle+"，请分配中心员服";
//		msgmodel.setEmail(1);
//		msgmodel.setEmailtitle(tittle);
//		msgmodel.setSymr_name("曾琴娜");// 收件人姓名
//		msgmodel.setSymr_log_id(d.getUserIDByname("曾琴娜"));// ;
//		msgservice.Add(msgmodel);
//		msgmodel.setSymr_name("林敏");// 收件人姓名
//		msgmodel.setSymr_log_id(d.getUserIDByname("林敏"));// ;
//		msgservice.Add(msgmodel);
//		msgmodel.setSymr_name("郭姗姗");// 收件人姓名
//		msgmodel.setSymr_log_id(d.getUserIDByname("郭姗姗"));// ;
//		msgservice.Add(msgmodel);

		//发邮件
		
		
	}
		
		//通知开发人员
		@Command
		public void summitend(@BindingParam("win") Window win)
		{
			String[] str=new String[5];
			str=bll.CobaseUpdate("", Integer.parseInt(tperid), Integer.parseInt(id));
			if(str[0]=="1")
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	
		private List<String> datelistAdd()
		{
			List<String> li=new ArrayList<String>();
			for(int i=1;i<=31;i++)
			{
				li.add(i+"日");
			}
			return li;
		}
		
	public CoBaseModel getCmodel() {
		return cmodel;
	}
	public void setCmodel(CoBaseModel cmodel) {
		this.cmodel = cmodel;
	}
	public List<PubTradeModel> getTradelist() {
		return tradelist;
	}
	public void setTradelist(List<PubTradeModel> tradelist) {
		this.tradelist = tradelist;
	}
	public CoServiceRequestModel getServicemodel() {
		return servicemodel;
	}
	public void setServicemodel(CoServiceRequestModel servicemodel) {
		this.servicemodel = servicemodel;
	}
	public List<String> getDatelistt() {
		return datelistt;
	}
	public void setDatelistt(List<String> datelistt) {
		this.datelistt = datelistt;
	}
	public CoLatencyClientModel getFrommodel() {
		return frommodel;
	}
	public void setFrommodel(CoLatencyClientModel frommodel) {
		this.frommodel = frommodel;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public List<String> getLoginlist() {
		return loginlist;
	}
	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public String getDtdservice() {
		return dtdservice;
	}

	public void setDtdservice(String dtdservice) {
		this.dtdservice = dtdservice;
	}

	public String getWt() {
		return wt;
	}

	public void setWt(String wt) {
		this.wt = wt;
	}

	public String getFservice() {
		return fservice;
	}

	public void setFservice(String fservice) {
		this.fservice = fservice;
	}

	public List<String> getManagerlist() {
		return managerlist;
	}

	public void setManagerlist(List<String> managerlist) {
		this.managerlist = managerlist;
	}
	
	
}
