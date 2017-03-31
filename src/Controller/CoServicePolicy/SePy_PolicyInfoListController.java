package Controller.CoServicePolicy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

public class SePy_PolicyInfoListController {
	private SePy_CityPolicySelectBll bll=new SePy_CityPolicySelectBll();
	private List<CoAgencyBaseModel> list=bll.getWtAgencyList("");
	private List<CoAgencyBaseModel> colist=bll.getCoAgencyList("");
	private List<String> citylist=bll.getCity();
	private String city="",coagename="";
	
	
	//查询
	@Command
	@NotifyChange("list")
	public void search()
	{
		String sql="";
		if(city!=null&&!city.equals(""))
		{
			sql=sql+" and name='"+city+"'";
		}
		if(coagename!=null&&!coagename.equals(""))
		{
			sql=sql+" and coab_name='"+coagename+"'";
		}
		list=bll.getWtAgencyList(sql);
	}
	
	//选择城市
	@Command
	@NotifyChange({"colist","coagename"})
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
		colist=bll.getCoAgencyList(selectedval);
		coagename="";
	}
	
	//选择城市
	@Command
	@NotifyChange("colist")
	public void checkcitys(@BindingParam("cb") Combobox cb,@BindingParam("cocb") Combobox cocb)
	{
		if(cb.getValue()!=null&&!cb.getValue().equals(""))
		{
			colist=bll.getCoAgencyBaseList(" and name='"+cb.getValue()+"'");
			cocb.setValue("");
			coagename="";
		}
	}
	
	//打开详细页面
	@Command
	public void openzul(@BindingParam("model") CoAgencyBaseModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("SePy_PolicyInfo.zul", null, map);
		window.doModal();
	}

	public List<CoAgencyBaseModel> getColist() {
		return colist;
	}

	public void setColist(List<CoAgencyBaseModel> colist) {
		this.colist = colist;
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String> citylist) {
		this.citylist = citylist;
	}

	public List<CoAgencyBaseModel> getList() {
		return list;
	}

	public void setList(List<CoAgencyBaseModel> list) {
		this.list = list;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoagename() {
		return coagename;
	}

	public void setCoagename(String coagename) {
		this.coagename = coagename;
	}
	
	
}
