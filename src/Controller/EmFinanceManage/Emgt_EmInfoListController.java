package Controller.EmFinanceManage;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmGatheringModel;
import bll.EmFinanceManage.emgt_selectBll;

public class Emgt_EmInfoListController {
	private Integer gid=(Integer) Executions.getCurrent().getArg().get("gid");
	private emgt_selectBll bll = new emgt_selectBll();
	private List<EmGatheringModel> list =new ArrayList<EmGatheringModel>(); 
	private String sql="";
	
	public Emgt_EmInfoListController()
	{
		sql=" and a.gid="+gid;
		list=bll.getEmGatheringList(sql);
	}

	public List<EmGatheringModel> getList() {
		return list;
	}

	public void setList(List<EmGatheringModel> list) {
		this.list = list;
	}
}
