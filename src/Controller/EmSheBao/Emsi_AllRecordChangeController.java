package Controller.EmSheBao;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmSheBao.EmSheBao_DSelectBll;

import Model.EmSheBaoChangeModel;

public class Emsi_AllRecordChangeController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private List<EmSheBaoChangeModel> changeList;
	private EmSheBao_DSelectBll bll = new EmSheBao_DSelectBll();

	public Emsi_AllRecordChangeController() {
		try {
			changeList = bll.getAllChangListByGid(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<EmSheBaoChangeModel> getChangeList() {
		return changeList;
	}

	public void setChangeList(List<EmSheBaoChangeModel> changeList) {
		this.changeList = changeList;
	}

}
