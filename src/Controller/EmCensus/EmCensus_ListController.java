package Controller.EmCensus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCensus.EmCensus_SelectBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;
import Model.EmDhModel;
import Model.EmbaseModel;

public class EmCensus_ListController {
	private String name="";
	private String cid="";
	private String gid="";
	private String idcard="";
	private String company="";
	private String address="";
	private String emstate="";
	private String client="";
	EmCensus_SelectBll bll=new EmCensus_SelectBll();
	private String str="";
	private String strs=" and gid in( select distinct(gid) from emdh a " +
			"inner join cobase b on a.cid=b.cid where emdh_state=6 and emdh_ifhk='是')";
	private List<EmbaseModel> embaselist=bll.getEmbaseInfo(strs);
	
	
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
		embaselist=bll.getEmbaseInfo(str+strs);
	}
	
	//打开入户申请页面
	@Command
	@NotifyChange("embaselist")
	public void openZUL(@BindingParam("model") final EmbaseModel model)
	{
		EmDh_SelectBll dhbll=new EmDh_SelectBll();
		List<EmDhModel> dhmodellist=dhbll.getEmDhInfo(" and emdh_state=6 and a.gid="+model.getGid());
		EmDhModel dhmodel=new EmDhModel();
		if(dhmodellist.size()<=0)
		{
			Messagebox.show("该员工没有调户信息，不能落户", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			dhmodel=dhmodellist.get(0);
			if(dhmodel.getEmdh_ifhk()=="是"||dhmodel.getEmdh_ifhk().equals("是"))
			{
			Map map=new HashMap<>();
			map.put("model", model);
			map.put("dhmodel", dhmodel);
			Window window=(Window) Executions.createComponents("EmCensusList_Add.zul", null, map);
			window.doModal();
			embaselist=bll.getEmbaseInfo(str+strs);
			}
			else
			{
				Messagebox.show("该员工没有户口挂靠服务，不能办理落户", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
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
