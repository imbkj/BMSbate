package Controller.Archives;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.Archives.EmArchive_SelectBll;

import Model.EmArchiveRemarkModel;

public class Archives_RemarkController {
	private int gid=(Integer) Executions.getCurrent().getArg().get("gid");
	private EmArchive_SelectBll bll=new EmArchive_SelectBll();
	private String str=" and gid="+gid+" AND eare_state =1 order by eare_id desc ";
	private List<EmArchiveRemarkModel> model=bll.getEmArchiveRemarkInfo(str);
	
	public List<EmArchiveRemarkModel> getModel() {
		return model;
	}

	public void setModel(List<EmArchiveRemarkModel> model) {
		this.model = model;
	}
}
