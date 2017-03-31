package Controller.EmBenefit;

import java.util.List;
import Controller.EmBenefit.EmBenfit_EmwfEmBaseListController;

import org.zkoss.zk.ui.Executions;

import bll.EmBenefit.EmBenefit_comitListBll;

import Model.EmWelfareModel;

public class EmBenfit_EmBaseListController {
	String cid = Executions.getCurrent().getArg().get("cid")+"";
	String emwf_name = (String)Executions.getCurrent().getArg().get("emwf_name");
	private EmBenefit_comitListBll bll=new EmBenefit_comitListBll();
	private List<EmWelfareModel> list=bll.getEmWelfareList(" and cid="+cid+" and embf_name='"+emwf_name+"'");
	EmBenfit_EmwfEmBaseListController dd =new EmBenfit_EmwfEmBaseListController();
	public EmBenfit_EmBaseListController()
	{
		EmWelfareModel m=new EmWelfareModel();
		m.setCid(2332);
		System.out.println("con="+dd.cobaseList);
		dd.cobaseList.add(m);
	}
	
	public List<EmWelfareModel> getList() {
		return list;
	}
	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}
	
}
