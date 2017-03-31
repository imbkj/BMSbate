package Controller.EmPay;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmPay.EmPa_SelectBll;

import Model.EmPayModel;

public class EmPa_InfoController {
	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private final EmPa_SelectBll bll = new EmPa_SelectBll();
	private EmPayModel model = (EmPayModel) Executions.getCurrent().getArg()
			.get("model");

	public EmPa_InfoController() {
		list = bll.getEmPayList(" and empa_number='" + model.getEmpa_number()
				+ "' and empa_state>0");
	}

	public List<EmPayModel> getList() {
		return list;
	}

	public void setList(List<EmPayModel> list) {
		this.list = list;
	}
	
	
}
