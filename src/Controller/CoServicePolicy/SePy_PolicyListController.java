package Controller.CoServicePolicy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import Model.CoServicePolicyFileModel;
import Model.CoServicePolicyModel;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

public class SePy_PolicyListController {
	private SePy_CityPolicySelectBll bll=new SePy_CityPolicySelectBll();
	private List<CoServicePolicyModel> list=bll.getList("");
	private CoServicePolicyModel filemodel=new CoServicePolicyModel();
	private String title="",type="",agencies="",city="",addname="";
	private List<CoServicePolicyFileModel> flielist=new ArrayList<CoServicePolicyFileModel>();
	private boolean viable=false,viables=false,wagevisable=false;
	private String slq="";
	private List<String> citylist=bll.getCity();
	//查询
		@Command
		@NotifyChange("list")
		public void search()
		{
			String sql="";
			slq="";
			if(title!=null&&!title.equals(""))
			{
				sql=sql+" and sypo_title like '%"+title+"%'";
			}
			if(type!=null&&!type.equals(""))
			{
				sql=sql+" and sypo_type like '%"+type+"%'";
			}
			if(agencies!=null&&!agencies.equals(""))
			{
				sql=sql+" and (sypo_agencies like '%"+agencies+"%' or sypo_city like '%"+agencies+"%')";
			}
			if(city!=null&&!city.equals(""))
			{
				sql=sql+" and sypo_city like '%"+city+"%'";
			}
			if(addname!=null&&!addname.equals(""))
			{
				sql=sql+" and sypo_addname like '%"+addname+"%'";
			}
			slq=sql;
			list=bll.getList(sql);
		}
		
		@Command
		@NotifyChange({"flielist","viable","viables","wagevisable"})
		public void selectedrow(@BindingParam("vlt") Vlayout vlt)
		{
			viables=true;
			flielist=filemodel.getFilelist();
			if(!flielist.isEmpty())
			{
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
			if(filemodel.getSypo_type()!=null&&filemodel.getSypo_type().equals("当地工资标准"))
			{
				wagevisable=true;
			}
			else
			{
				wagevisable=false;
			}
			
		}
		
		//打开政策指引文件
		@Command
		public void openfile(@BindingParam("url") String url)
		{
			try {
				String[] cmd = new String[5];
				//String url = "D:\\workspace\\BMSbeta\\WebContent\\KnowledgeBase\\file\\tst.xls";
				cmd[0] = "cmd";
				cmd[1] = "/c";
				cmd[2] = "start";
				cmd[3] = " ";
				cmd[4] =url;
				Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
				e.printStackTrace();
				}
		}
	
	//打开修改页面
	@Command
	@NotifyChange("list")
	public void openupdate(@BindingParam("model") CoServicePolicyModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("SePy_InfoUpdate.zul", null, map);
		window.doModal();
		list=bll.getList(slq);
	}
	
	//选择城市
	@Command
	public void checkcity(@BindingParam("lb") Listbox lb,@BindingParam("bd") Bandbox bd)
	{
		String selectedval="";
		for(Listitem item:lb.getSelectedItems())
		{
			if(selectedval==null||selectedval.equals(""))
			{
				selectedval=item.getLabel();
			}
			else
			{
				selectedval=selectedval+"、"+item.getLabel();
			}
		}
		bd.setValue(selectedval);
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

	public List<CoServicePolicyModel> getList() {
		return list;
	}

	public void setList(List<CoServicePolicyModel> list) {
		this.list = list;
	}

	public CoServicePolicyModel getFilemodel() {
		return filemodel;
	}

	public void setFilemodel(CoServicePolicyModel filemodel) {
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

	public List<CoServicePolicyFileModel> getFlielist() {
		return flielist;
	}

	public void setFlielist(List<CoServicePolicyFileModel> flielist) {
		this.flielist = flielist;
	}

	public boolean isWagevisable() {
		return wagevisable;
	}

	public void setWagevisable(boolean wagevisable) {
		this.wagevisable = wagevisable;
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String> citylist) {
		this.citylist = citylist;
	}
	
}
