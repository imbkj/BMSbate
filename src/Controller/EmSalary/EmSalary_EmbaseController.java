package Controller.EmSalary;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;

import Model.EmSalaryBaseSel_viewModel;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_EmbaseController extends SelectorComposer<Component>{
	private static final long serialVersionUID = 1L;
	private final int esda_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("esda_id").toString());
	private EmSalaryBaseSel_viewModel baseModel;
	
	public EmSalary_EmbaseController(){
		EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
		baseModel = bll.getSalaryEmBase(esda_id);
	}

	public EmSalaryBaseSel_viewModel getBaseModel() {
		return baseModel;
	}

	public void setBaseModel(EmSalaryBaseSel_viewModel baseModel) {
		this.baseModel = baseModel;
	}
	
	
}
