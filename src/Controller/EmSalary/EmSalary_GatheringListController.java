package Controller.EmSalary;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmSalary.EmSalary_SalarySelBll;

import Model.EmSalaryPayInfoModel;

public class EmSalary_GatheringListController {
	private List<EmSalaryPayInfoModel> dataList = new ArrayList<EmSalaryPayInfoModel>();
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());
	private EmSalary_SalarySelBll sBll=new EmSalary_SalarySelBll();
	
	public EmSalary_GatheringListController() {
		dataList=sBll.getEmSalaryGatheringInfo(cid);
	}

	public List<EmSalaryPayInfoModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmSalaryPayInfoModel> dataList) {
		this.dataList = dataList;
	}
	
	
}
