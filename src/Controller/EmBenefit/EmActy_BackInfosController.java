package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmWelfareModel;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmActy_BackInfosController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	private EmWelfareModel model=new EmWelfareModel();
	private List<EmWelfareModel> list =bll.getLists(" and emwf_id="+id);
	
	public EmActy_BackInfosController()
	{
		if(list.size()>0)
		{
			model=list.get(0);
		}
	}

	public EmWelfareModel getModel() {
		return model;
	}

	public void setModel(EmWelfareModel model) {
		this.model = model;
	}
	
	
}
