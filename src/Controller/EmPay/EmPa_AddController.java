package Controller.EmPay;

import org.zkoss.zk.ui.Executions;

import Model.EmbaseModel;

public class EmPa_AddController {
	private EmbaseModel model = (EmbaseModel) Executions
			.getCurrent().getArg().get("model");

	public EmbaseModel getModel() {
		return model;
	}

	public void setModel(EmbaseModel model) {
		this.model = model;
	}
}
