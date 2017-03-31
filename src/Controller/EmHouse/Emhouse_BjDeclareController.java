package Controller.EmHouse;


import org.zkoss.zk.ui.Executions;

import Model.EmHouseBJModel;


public class Emhouse_BjDeclareController {
	private EmHouseBJModel model = (EmHouseBJModel) Executions.getCurrent()
			.getArg().get("model");

	public Emhouse_BjDeclareController() {
		
	}

	

	public EmHouseBJModel getModel() {
		return model;
	}

	public void setModel(EmHouseBJModel model) {
		this.model = model;
	}
	
	
}
