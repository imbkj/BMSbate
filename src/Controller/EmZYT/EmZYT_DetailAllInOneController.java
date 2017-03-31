package Controller.EmZYT;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmZYTModel;
import bll.EmZYT.EmZYT_SelectBll;

public class EmZYT_DetailAllInOneController {
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private List<EmZYTModel> dataList;

	private String gid = Executions.getCurrent().getArg().get("gid")
			.toString();
	private String sql = "";

	public EmZYT_DetailAllInOneController() {
		sql = " AND gid="+gid;
		dataList = sbll.getEmZYTList(sql);
	}

	public List<EmZYTModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmZYTModel> dataList) {
		this.dataList = dataList;
	}
}
