package Controller.EmFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import bll.EmFinanceManage.Finance_CFMLogBll;

import Model.SystLogModel;

public class Finance_CFMLogInfoController {
	private List<SystLogModel> cfmLog = new ListModelList<>();
	private Integer cid;
	private String addtime;

	private Finance_CFMLogBll bll = new Finance_CFMLogBll();

	public Finance_CFMLogInfoController() {
		if (Executions.getCurrent().getArg().get("cid") != null) {
			cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
					.toString());
		}
		if (Executions.getCurrent().getArg().get("addtime") != null) {
			addtime = Executions.getCurrent().getArg().get("addtime")
					.toString();
		}
		cfmLog = bll.getModLog(cid, addtime);
	}

	public List<SystLogModel> getCfmLog() {
		return cfmLog;
	}

	public void setCfmLog(List<SystLogModel> cfmLog) {
		this.cfmLog = cfmLog;
	}

}
