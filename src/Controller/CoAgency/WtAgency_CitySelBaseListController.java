package Controller.CoAgency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import bll.CoAgency.WtAgency_DisCitySelBll;

public class WtAgency_CitySelBaseListController {
	private List<CoAgencyBaseModel> baselist;
	private final CoAgencyBaseCityRelModel citymodel = (CoAgencyBaseCityRelModel) Executions.getCurrent().getArg().get("citymodel");
	private WtAgency_DisCitySelBll bll;
	private String city=citymodel.getCity();

	public WtAgency_CitySelBaseListController() {
		bll = new WtAgency_DisCitySelBll();
		baselist = bll.getCoAgBaseListByCity(city);
	}

	public List<CoAgencyBaseModel> getBaselist() {
		return baselist;
	}
	
	//打开新增机构服务政策页面
	@Command
	public void openzul(@BindingParam("model") CoAgencyBaseModel model)
	{
		citymodel.setCabc_id(model.getCrModel().getCabc_id());
		citymodel.setId(model.getCrModel().getId());
		Map map=new HashMap();
		map.put("model", citymodel);
		map.put("type", "机构政策");
		map.put("classname", model.getCoab_name());
		Window window=(Window)Executions.createComponents("/CoServicePolicy/SePy_CityPolicyInfoAdd.zul", null, map);
		window.doModal();
	}
	
	//打开新增机构政策通知页面
	@Command
	public void opennotezul(@BindingParam("model") CoAgencyBaseModel model)
	{
		citymodel.setCabc_id(model.getCrModel().getCabc_id());
		citymodel.setId(model.getCrModel().getId());
		Map map=new HashMap();
		map.put("model", citymodel);
		map.put("type", "机构通知");
		map.put("classname", model.getCoab_name());
		Window window=(Window)Executions.createComponents("/CoPolicyNotice/PoNo_InfoAdd.zul", null, map);
		window.doModal();
	}
	
	// 机构管理
		@Command("manage")
		public void manage(@BindingParam("cabc_id") int cabc_id) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("cabc_id", cabc_id);
			Window window = (Window) Executions.createComponents(
					"../CoAgency/WtAgency_Manage.zul", null, map);
			window.doModal();
		}

}
