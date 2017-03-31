package Controller.CoAgencies;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.PubProCityModel;
import bll.CoAgencies.CoAg_CompactSelectBll;

public class CoAg_CityListController {
	private Integer coct_id = (Integer)Executions.getCurrent().getArg().get("coct_id");
	private CoAg_CompactSelectBll bll=new CoAg_CompactSelectBll();
	private List<PubProCityModel> list=bll.getCoPubProCityListByCaId(coct_id);
	public List<PubProCityModel> getList() {
		return list;
	}
	public void setList(List<PubProCityModel> list) {
		this.list = list;
	}
	
}
