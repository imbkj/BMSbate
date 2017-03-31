package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.CoFinanceManage.Cfma_AgencyWriteOffSelBll;

import Model.CoFinanceAgencyWriteOffInfoModel;

public class Cfma_AgencyWriteOffInfoController {
	private final int cawo_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cawo_id").toString());
	private List<CoFinanceAgencyWriteOffInfoModel> wtList;
	private List<CoFinanceAgencyWriteOffInfoModel> stList;

	public Cfma_AgencyWriteOffInfoController() {
		Cfma_AgencyWriteOffSelBll bll = new Cfma_AgencyWriteOffSelBll();
		wtList = bll.getWriteOffInfoList(cawo_id, 1);
		stList = bll.getWriteOffInfoList(cawo_id, 2);
	}

	public List<CoFinanceAgencyWriteOffInfoModel> getWtList() {
		return wtList;
	}

	public List<CoFinanceAgencyWriteOffInfoModel> getStList() {
		return stList;
	}

}
