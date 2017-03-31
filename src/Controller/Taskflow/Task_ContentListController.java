package Controller.Taskflow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;

import bll.Taskflow.TaskProcess_ViewBll;
import bll.Taskflow.Task_ListBll;

import Model.WfTaskListInfoModel;
import Util.UserInfo;

public class Task_ContentListController {
	private Integer tacl_id;
	private List<WfTaskListInfoModel> infolist = new ArrayList<WfTaskListInfoModel>();
	private List<WfTaskListInfoModel> nlist = new ArrayList<WfTaskListInfoModel>();
	private Task_ListBll bll = new Task_ListBll();
	private Integer tali_id;
	private String url = "";

	// 任务单进度变量
	TaskProcess_ViewBll vbll = new TaskProcess_ViewBll();

	public Task_ContentListController() throws SQLException {
		if (Executions.getCurrent().getParameter("id") != null) {
			tacl_id = Integer.valueOf(Executions.getCurrent().getParameter("id").toString());
		}
		if (Executions.getCurrent().getArg().get("id") != null) {
			tacl_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		if (tacl_id>0) {
			nlist = bll.getTaskBycoprid(UserInfo.getUsername(),tacl_id);
			infolist = bll.getTaskInfoList(nlist, tacl_id);
		}
		
	}

	@Command
	@NotifyChange("url")
	public void selecttask(@BindingParam("model") WfTaskListInfoModel model,
			@BindingParam("inclu") Include inclu, @BindingParam("hb") Hbox hb) throws InterruptedException{
		try {
			tali_id = 0;
			tali_id = model.getTali_id();
			url = "../Taskflow/Task_ProcessInfo.zul?id=" + tali_id;
			inclu.invalidate();
		} catch (Exception e) {

		}
	}

	// 查询任务单类型
	@Command
	@NotifyChange("infolist")
	public void search(@BindingParam("val") String val) throws SQLException {
		// List<WfTaskListInfoModel> listt=new ArrayList<WfTaskListInfoModel>();
		// nlist=tbll.getTaskBycoprid(UserInfo.getUsername());
		if (val != null && !val.equals("")) {
			infolist = bll.getTaskInfoList(nlist, tacl_id);
			infolist = bll.getTaskContentList(infolist, val.toString());
		} else {
			infolist = bll.getTaskInfoList(nlist, tacl_id);
		}
	}

	@GlobalCommand("refreshWin")
	public void refreshWin() throws InterruptedException{
		try {
			nlist = bll.getTaskBycoprid(UserInfo.getUsername());
			infolist = bll.getTaskInfoList(nlist, tacl_id);
			BindUtils.postNotifyChange(null, null, this, "infolist");
			if (infolist.size() <= 0) {
				EventQueue<Event> que = EventQueues.lookup(
						"task" + UserInfo.getUserid(), EventQueues.SESSION,
						true);
				que.publish(new Event("Default", null, null));
			}
			Include inc = (Include) Path.getComponent("/taskWin/inclu");
			inc.invalidate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<WfTaskListInfoModel> getInfolist() {
		return infolist;
	}

	public void setInfolist(List<WfTaskListInfoModel> infolist) {
		this.infolist = infolist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
