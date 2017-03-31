package Controller.CoAgency;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.CoAgency.BaseInfo_SelBll;

import Model.CoAgencyBaseCityRelModel;

public class WtAgency_ServiceCityController {
	int coab_id = (Integer) Executions.getCurrent().getArg().get("coab_id");
	private List<CoAgencyBaseCityRelModel> serviceCityList;

	public WtAgency_ServiceCityController() {
		BaseInfo_SelBll bll = new BaseInfo_SelBll();
		serviceCityList = bll.getServiceCityList(coab_id);
	}

	public List<CoAgencyBaseCityRelModel> getServiceCityList() {
		return serviceCityList;
	}

}
