package Controller.EmSheBao;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmSheBao.EmSheBao_DSelectBll;

import Model.EmShebaoUpdateModel;

public class Emsi_AllRecordController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private List<EmShebaoUpdateModel> sbDataList;
	private Integer gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());

	public Emsi_AllRecordController() {
		sbDataList = dsbll.getAllShebaoByGid(gid);
	}

	public List<EmShebaoUpdateModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmShebaoUpdateModel> sbDataList) {
		this.sbDataList = sbDataList;
	}

}
