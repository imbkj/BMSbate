package Controller.EmExpress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import bll.EmExpress.EmExpressInfoSelectBll;

public class EmbaseListController {
	private EmExpressInfoSelectBll bll=new EmExpressInfoSelectBll();
	private List<EmbaseModel> list=bll.getEmBaseInfoList(" and emba_state=1");
	private String cobaname="",client="",gid="",embaname="",sql="";
	private List<String> clientlist=bll.getClient();
	
	//添加收件地址
	@Command
	@NotifyChange("list")
	public void address(@BindingParam("model") EmbaseModel model)
	{
		Map map=new HashMap<>();
		map.put("gid", model.getGid());
		map.put("cid", model.getCid());
		map.put("flag", "emba");
		Window window=(Window)Executions.createComponents("EmExpressContactInfoAdd.zul", null, map);
		window.doModal();
		list=bll.getEmBaseInfoList(" and emba_state=1");
	}
	
	//新增快递
	@Command
	@NotifyChange("list")
	public void addexpress(@BindingParam("model") EmbaseModel model)
	{
		Map map=new HashMap<>();
		map.put("gid", model.getGid());
		map.put("cid", model.getCid());
		map.put("flag", "emba");
		Window window=(Window)Executions.createComponents("EmExpressInfoAdd.zul", null, map);
		window.doModal();
		list=bll.getEmBaseInfoList(" and emba_state=1");
	}
	
	//查询
	@Command
	@NotifyChange("list")
	public void search()
	{
		sql="";
		if(cobaname!=null&&!cobaname.equals(""))
		{
			sql=sql+" and coba_shortname like '%"+cobaname+"%'";
		}
		if(client!=null&&!client.equals(""))
		{
			sql=sql+" and coba_client like '%"+client+"%'";
		}
		if(gid!=null&&!gid.equals(""))
		{
			sql=sql+" and a.gid = "+gid;
		}
		
		if(embaname!=null&&!embaname.equals(""))
		{
			sql=sql+" and emba_name = '"+embaname+"'";
		}
		list=bll.getEmBaseInfoList(sql);
	}
	
	public List<EmbaseModel> getList() {
		return list;
	}
	public void setList(List<EmbaseModel> list) {
		this.list = list;
	}

	public String getCobaname() {
		return cobaname;
	}

	public void setCobaname(String cobaname) {
		this.cobaname = cobaname;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getEmbaname() {
		return embaname;
	}

	public void setEmbaname(String embaname) {
		this.embaname = embaname;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}
		

}
