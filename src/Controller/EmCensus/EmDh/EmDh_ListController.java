package Controller.EmCensus.EmDh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmDh_ListController {
	private String name="";
	private String cid="";
	private String gid="";
	private String idcard="";
	private String company="";
	private String address="";
	private String emstate="";
	private String client="";
	private EmDh_SelectBll bll=new EmDh_SelectBll();
	private String str="";
	private List<EmbaseModel> embaselist=bll.getEmbaseInfo(str);
	
	
	//查询
	@Command
	@NotifyChange("embaselist")
	public void search()
	{
		str="";
		if(name!=null&&name!=""&&!name.equals(""))
		{
			str=str+" and emba_name like '%"+name+"%'";
		}
		if(cid!=null&&cid!=""&&!cid.equals(""))
		{
			str=str+" and a.cid ='"+cid+"'";
		}
		if(gid!=null&&gid!=""&&!gid.equals(""))
		{
			str=str+" and a.gid ='"+gid+"'";
		}
		if(idcard!=null&&idcard!=""&&!idcard.equals(""))
		{
			str=str+" and emba_idcard ='"+idcard+"'";
		}
		if(company!=null&&company!=""&&!company.equals(""))
		{
			str=str+" and (b.coba_shortname like '%"+company+"%' or coba_company like '%"+company+"%')";
		}
		if(address!=null&&address!=""&&!address.equals(""))
		{
			str=str+" and emba_wt ='"+address+"'";
		}
		if(emstate!=null&&emstate!=""&&!emstate.equals(""))
		{
			if(emstate=="在职"||emstate.equals("在职"))
			{
				str=str+" and emba_state =1";
			}
			else if(emstate=="离职"||emstate.equals("离职"))
			{
				str=str+" and emba_state =0";
			}
		}
		if(client!=null&&client!=""&&!client.equals(""))
		{
			str=str+" and b.coba_client='"+client+"'";
		}
		embaselist=bll.getEmbaseInfo(str);
	}
		
	//打开调户申请页面
	@Command
	@NotifyChange("embaselist")
	public void openZUL(@BindingParam("model") final EmbaseModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window) Executions.createComponents("EmDh_Add.zul", null, map);
		window.doModal();
		embaselist=bll.getEmbaseInfo(str);
	}
	
	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}
	public void setEmbaselist(List<EmbaseModel> embaselist) {
		this.embaselist = embaselist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmstate() {
		return emstate;
	}
	public void setEmstate(String emstate) {
		this.emstate = emstate;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	
}
