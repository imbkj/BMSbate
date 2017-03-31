package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.EmBenefit.EmWelf_DetailBll;

import Model.EmWelfareModel;

public class EmWelf_DetailController {
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmWelf_DetailBll bll = new EmWelf_DetailBll();
	private EmWelfareModel em = new EmWelfareModel();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private String name = Executions.getCurrent().getArg().get("name").toString();
	private String sortid = Executions.getCurrent().getArg().get("sortid").toString();

	public EmWelf_DetailController() {
		
		em.setEmwf_embf_id(eadaId);
		em.setEmwf_name(name);
		em.setEmwf_sortid(sortid);
	
		
		setList(em);
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(EmWelfareModel ewm) {
		this.list = bll.getList(ewm);
	}

}
