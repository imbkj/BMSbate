package Controller.EmHouseCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;

import Model.EmbaseModel;

public class EmHouse_EmbaseInfoListController {
	private EmHouse_MakeCardInfoSelectBll bll=new EmHouse_MakeCardInfoSelectBll();
	private List<EmbaseModel> list=bll.getEmBaseInfo("");
	private List<String> clientlist=bll.getLoginInfo();
	
	//查询
	
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("nametype") String nametype,@BindingParam("name") String name,
			@BindingParam("client") String client,@BindingParam("comtype") String comtype,
			@BindingParam("comname") String comname,@BindingParam("housestate") String housestate,
			@BindingParam("embastate") String embastate)
	{
		String sql="";
		if(name!=null&&!name.equals(""))
		{
			if(nametype!=null&&!nametype.equals(""))
			{
				if(nametype.equals("员工姓名"))
				{
					sql=sql+" and emba_name='"+name+"'";
				}
				else if(nametype.equals("身份证号"))
				{
					sql=sql+" and emba_idcard='"+name+"'";
				}
				else if(nametype.equals("员工编号"))
				{
					sql=sql+" and gid='"+name+"'";
				}
			}
		}
		
		if(comname!=null&&!comname.equals(""))
		{
			if(comtype!=null&&!comtype.equals(""))
			{
				if(comtype.equals("单位简称"))
				{
					sql=sql+" and (coba_shortname='"+comname+"' or coba_company='"+comname+"')";
				}
				else if(comtype.equals("单位编号"))
				{
					sql=sql+" and a.cid='"+comname+"'";
				}
			}
		}
		if(client!=null&&!client.equals(""))
		{
			sql=sql+" and coba_client='"+client+"'";
		}
		if(housestate!=null&&!housestate.equals(""))
		{
			if(housestate.equals("已参加公积金"))
			{
				sql=sql+" and num>0";
			}
			else
			{
				sql=sql+" and (num<=0 or num is null)";
			}
		}
		
		if(embastate!=null&&!embastate.equals(""))
		{
			if(embastate.equals("在职"))
			{
				sql=sql+" and emba_state=1";
			}
			else
			{
				sql=sql+" and emba_state=0";
			}
		}
		list=bll.getEmBaseInfo(sql);
	}
	
	@Command
	public void submit(@BindingParam("model") EmbaseModel model)
	{
		Map map=new HashMap<>();
		map.put("gid", model.getGid());
		Window window=(Window)Executions.createComponents("HuCard_MakeCardInfoAdd.zul", null, map);
		window.doModal();
	}
	public List<EmbaseModel> getList() {
		return list;
	}
	public void setList(List<EmbaseModel> list) {
		this.list = list;
	}
	public List<String> getClientlist() {
		return clientlist;
	}
	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}
	
	
}
