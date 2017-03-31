package Controller.Taskflow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import bll.Taskflow.Task_ListBll;
import Model.WfTaskListInfoModel;
import Util.UserInfo;

public class Task_InfoListController  extends SelectorComposer<Component>{
	private List<WfTaskListInfoModel> list=new ArrayList<WfTaskListInfoModel>();
	private List<WfTaskListInfoModel> infolist=new ArrayList<WfTaskListInfoModel>();
	private List<WfTaskListInfoModel> nlist=new ArrayList<WfTaskListInfoModel>();
	private Task_ListBll bll = new Task_ListBll();
	private Integer tali_id;
	private String url="";
	public Task_InfoListController() throws SQLException {
		nlist=bll.getTaskBycoprid(UserInfo.getUsername());
		list=bll.getTaskList(nlist);
	}
	
	@Command
	@NotifyChange("infolist")
	public void selectname(@BindingParam("model") WfTaskListInfoModel model)
	{
		infolist=bll.getTaskInfoList(nlist, model.getTacl_id());
	}
	
	@Command
	@NotifyChange("url")
	public void selecttask(@BindingParam("model") WfTaskListInfoModel model,@BindingParam("inclu") Include inclu)
	{
		tali_id=model.getTali_id();
		url="Task_ProcessInfo.zul?id="+tali_id;
		//Window window=(Window)Executions.createComponents(url, null, null);
		//window
		inclu.setSrc("");
		inclu.setSrc(url);
	}
	
	public List<WfTaskListInfoModel> getList() {
		return list;
	}
	public void setList(List<WfTaskListInfoModel> list) {
		this.list = list;
	}
	public List<WfTaskListInfoModel> getInfolist() {
		return infolist;
	}
	public void setInfolist(List<WfTaskListInfoModel> infolist) {
		this.infolist = infolist;
	}

	public Integer getTali_id() {
		return tali_id;
	}

	public void setTali_id(Integer tali_id) {
		this.tali_id = tali_id;
	}
	
	
}
