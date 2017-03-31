package Controller.EmZYT;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmZYT.EmZYT_SelectBll;

import Model.EmZYTModel;

public class EmZYT_DetailIndexController {
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private List<EmZYTModel> dataList;

	private String emzt_id = Executions.getCurrent().getArg().get("emzt_id")
			.toString();
	private String sql = "";

	public EmZYT_DetailIndexController() {
		sql = " AND id in(" + emzt_id + ")";
		dataList = sbll.getEmZYTList(sql);

	}

	public List<EmZYTModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmZYTModel> dataList) {
		this.dataList = dataList;
	}

}
