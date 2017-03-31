package Controller.EmFinance;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmFinanceWtModel;
import Model.EmFinanceZYTModel;
import bll.EmFinance.EmFinance_SelectBll;

public class Finance_DifferentListController {
	private EmFinanceZYTModel model = (EmFinanceZYTModel) Executions.getCurrent().getArg().get("model");
	private EmFinance_SelectBll bll=new EmFinance_SelectBll();
	private List<EmFinanceWtModel> fiList=bll.getWtFiList(model.getName(),model.getOwnmonth(),model.getCid());
	private List<EmFinanceZYTModel> fzList=bll.getWtZYTList(model.getName(),model.getOwnmonth(),model.getCid());
	
	public List<EmFinanceWtModel> getFiList() {
		return fiList;
	}
	public void setFiList(List<EmFinanceWtModel> fiList) {
		this.fiList = fiList;
	}
	public List<EmFinanceZYTModel> getFzList() {
		return fzList;
	}
	public void setFzList(List<EmFinanceZYTModel> fzList) {
		this.fzList = fzList;
	}
}
