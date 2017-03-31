package Controller.CoPolicyNotice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;
import Util.FileOperate;
import bll.CoPolicyNotice.PoNo_SelectBll;

public class PoNo_InfoListController {
	private PoNo_SelectBll bll=new PoNo_SelectBll();
	private List<CoPolicyNoticeModel> list=bll.getList("");
	private List<CoPolicyNoticeFileModel> flielist=new ArrayList<CoPolicyNoticeFileModel>();
	private CoPolicyNoticeModel filemodel=new CoPolicyNoticeModel();
	private boolean viable=false,viables=false;
	private String ownmonth="",title="",type="",agencies="",city="",addname="";
	String slq="";

	@Command
	@NotifyChange({"flielist","viable","viables"})
	public void selectedrow(@BindingParam("vlt") Vlayout vlt)
	{
		flielist=filemodel.getFilelist();
		if(flielist.size()>0)
		{
			viable=true;
		}
		else
		{
			viable=false;
		}
		if(filemodel==null)
		{
			viables=false;
		}
		else
		{
			viables=true;
		}
	}
	
	//打开政策指引文件
	@Command
	public void openfile(@BindingParam("url") String url,@BindingParam("win") Window win)
	{
			//String[] cmd = new String[5];
			//String url = "D:\\workspace\\BMSbeta\\WebContent\\KnowledgeBase\\file\\tst.xls";
//			cmd[0] = "cmd";
//			cmd[1] = "/c";
//			cmd[2] = "start";
//			cmd[3] = " ";
//			cmd[4] =url;
//			Runtime.getRuntime().exec(cmd);
//			} catch (IOException e) {
//			e.printStackTrace();
//			}
		try{
			FileOperate file=new FileOperate();
			System.out.println(url);
			file.download(url);
		}
		catch(Exception e)
		{
			Clients.showNotification("找不到该文件","info",win,"middle_center",3000);
			e.printStackTrace();
			System.out.println("e="+e.getMessage());
		}
		
	}
	
	//查询
	@Command
	@NotifyChange("list")
	public void search()
	{
		String sql="";
		slq="";
		if(ownmonth!=null&&!ownmonth.equals(""))
		{
			sql=sql+" and ownmonth="+ownmonth;
		}
		if(title!=null&&!title.equals(""))
		{
			sql=sql+" and pono_title like '%"+title+"%'";
		}
		if(type!=null&&!type.equals(""))
		{
			sql=sql+" and pono_type like '%"+type+"%'";
		}
		if(agencies!=null&&!agencies.equals(""))
		{
			sql=sql+" and pono_agencies like '%"+agencies+"%'";
		}
		if(city!=null&&!city.equals(""))
		{
			sql=sql+" and pono_city like '%"+city+"%'";
		}
		if(addname!=null&&!addname.equals(""))
		{
			sql=sql+" and pono_addname like '%"+addname+"%'";
		}
		slq=sql;
		list=bll.getList(sql);
	}
	
	//打开修改页面
	@Command
	@NotifyChange("list")
	public void openupdate(@BindingParam("model") CoPolicyNoticeModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("PoNo_InfoUpdate.zul", null, map);
		window.doModal();
		list=bll.getList(slq);
	}
	
	@Command
	@NotifyChange("list")
	public void add()
	{
		Window window=(Window)Executions.createComponents("PoNo_Add.zul", null, null);
		window.doModal();
		list=bll.getList(slq);
	}
	
	
	public List<CoPolicyNoticeModel> getList() {
		return list;
	}

	public void setList(List<CoPolicyNoticeModel> list) {
		this.list = list;
	}

	public List<CoPolicyNoticeFileModel> getFlielist() {
		return flielist;
	}

	public void setFlielist(List<CoPolicyNoticeFileModel> flielist) {
		this.flielist = flielist;
	}

	public CoPolicyNoticeModel getFilemodel() {
		return filemodel;
	}

	public void setFilemodel(CoPolicyNoticeModel filemodel) {
		this.filemodel = filemodel;
	}

	public boolean isViable() {
		return viable;
	}

	public void setViable(boolean viable) {
		this.viable = viable;
	}

	public boolean isViables() {
		return viables;
	}

	public void setViables(boolean viables) {
		this.viables = viables;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAgencies() {
		return agencies;
	}

	public void setAgencies(String agencies) {
		this.agencies = agencies;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}
	
}
