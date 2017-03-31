package Controller.EmExpress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.EmbaseModel;
import bll.EmExpress.EmExpressInfoSelectBll;

public class CobaseListController {
	private EmExpressInfoSelectBll bll=new EmExpressInfoSelectBll();
	private List<CoBaseModel> list=bll.getCoBaseInfoList(" and coba_servicestate=1");
	private String cobaname="",client="",cid="",sql="";
	private List<String> clientlist=bll.getClient();
	
	//添加收件地址
	@Command
	@NotifyChange("list")
	public void address(@BindingParam("model") CoBaseModel model)
	{
		Map map=new HashMap<>();
		map.put("cid", model.getCid());
		map.put("flag", "coba");
		Window window=(Window)Executions.createComponents("EmExpressContactInfoAdd.zul", null, map);
		window.doModal();
		list=bll.getCoBaseInfoList(" and coba_servicestate=1");
	}
	
	//新增快递
	@Command
	@NotifyChange("list")
	public void addexpress(@BindingParam("model") CoBaseModel model)
	{
		Map map=new HashMap<>();
		map.put("cid", model.getCid());
		map.put("flag", "coba");
		Window window=(Window)Executions.createComponents("EmExpressInfoAdd.zul", null, map);
		window.doModal();
		list=bll.getCoBaseInfoList(" and coba_servicestate=1");
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
		if(cid!=null&&!cid.equals(""))
		{
			sql=sql+" and a.cid = "+cid;
		}
		list=bll.getCoBaseInfoList(sql);
	}
	
	public List<CoBaseModel> getList() {
		return list;
	}
	public void setList(List<CoBaseModel> list) {
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}
	
}
