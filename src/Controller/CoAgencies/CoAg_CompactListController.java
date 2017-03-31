package Controller.CoAgencies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import com.sun.java.swing.plaf.windows.resources.windows;

import Model.CoAgencyBaseModel;
import Model.CoAgencyCompactModel;
import Model.PubProCityModel;
import bll.CoAgencies.CoAg_CompactSelectBll;

public class CoAg_CompactListController {
	private CoAg_CompactSelectBll bll = new CoAg_CompactSelectBll();
	
	private CoAgencyCompactModel model = new CoAgencyCompactModel();
	private List<String> addnamelist = bll.getAddname();
	private List<CoAgencyBaseModel> coaglist = new ArrayList<CoAgencyBaseModel>();
	private List<PubProCityModel> citylist = new ArrayList<PubProCityModel>();
	private String sid = Executions.getCurrent().getParameter("id").toString();//id==1表示受托，id==2表示委托
	private String sqls="";
	private List<CoAgencyCompactModel> list =new ArrayList<CoAgencyCompactModel>(); 
	public CoAg_CompactListController() {
		citylist.add(new PubProCityModel());
		citylist.addAll(bll.getCoPubProCityList(""));
		CoAgencyBaseModel ml = new CoAgencyBaseModel();
		coaglist.add(ml);
		coaglist.addAll(bll.getCoAgencyBaseList(""));
		if(sid.equals("1"))
		{
			sqls=" and coct_type='受托合同'";
		}
		else
		{
			sqls=" and coct_type='委托合同'";
		}
		list=bll.getWtCompactList(sqls);
	}

	@Command
	@NotifyChange("list")
	public void search() {
		list = bll.getList(model,sqls);
	}

	// 弹出更新页面
	@Command
	public void updateinfo(@BindingParam("model") CoAgencyCompactModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		String url = "../CoAgencies/CoAg_CompactUpdate.zul";
		if (model.getCoct_type().equals("委托合同")) {
			url = "../CoAgencies/CoAg_WtCompactUpdate.zul";
		}
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 弹出基本信息页面
	@Command
	public void info(@BindingParam("model") CoAgencyCompactModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		String url = "../CoAgencies/CoAg_stCompact.zul";
		if (model.getCoct_type().equals("委托合同")) {
			url = "../CoAgencies/CoAg_WtCompact.zul";
		}
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 打开查看合同内容页面
	@Command
	public void lookinfo(@BindingParam("model") CoAgencyCompactModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		String url = "/CoAgencies/CoAg_CompactInfo.zul";
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	public List<CoAgencyCompactModel> getList() {
		return list;
	}

	public void setList(List<CoAgencyCompactModel> list) {
		this.list = list;
	}

	public CoAgencyCompactModel getModel() {
		return model;
	}

	public void setModel(CoAgencyCompactModel model) {
		this.model = model;
	}

	public List<String> getAddnamelist() {
		return addnamelist;
	}

	public void setAddnamelist(List<String> addnamelist) {
		this.addnamelist = addnamelist;
	}

	public List<CoAgencyBaseModel> getCoaglist() {
		return coaglist;
	}

	public void setCoaglist(List<CoAgencyBaseModel> coaglist) {
		this.coaglist = coaglist;
	}

	public List<PubProCityModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<PubProCityModel> citylist) {
		this.citylist = citylist;
	}

}
