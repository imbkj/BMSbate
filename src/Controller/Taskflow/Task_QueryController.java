package Controller.Taskflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.North;
import org.zkoss.zul.Window;

import bll.Taskflow.Task_QueryBll;
import Model.TaskClassModel;
import Model.TaskListModel;
import Model.TaskProcessListModel;
import Model.loginroleModel;
import Util.UserInfo;

public class Task_QueryController extends SelectorComposer<Component> {
	private List<TaskProcessListModel> tasklist;
	private List<loginroleModel> userList;
	private List<loginroleModel> depList;
	private List<loginroleModel> rolList;
	private List<TaskClassModel> tcList;
	private List<TaskListModel> tlList;

	private Integer userId = Integer.valueOf(UserInfo.getUserid());
	private Task_QueryBll bll = new Task_QueryBll();

	public Task_QueryController() {
		setTasklist(userId, 0, 0, 0, 0, "");
		setDepList(userId, "");
		setRolList(userId, 0, "");
		setUserList(userId, 0, 0, "");
		setTcList();
		setTlList();
	}

	@Command("Search")
	@NotifyChange("tasklist")
	public void Search(@BindingParam("a") loginroleModel depModel,
			@BindingParam("b") loginroleModel rolModel,
			@BindingParam("c") loginroleModel userModel,
			@BindingParam("d") TaskClassModel tcmModel,
			@BindingParam("e") TaskListModel tlmModel,@BindingParam("f") North north) {
		Integer depid = 0;
		Integer rolid = 0;
		Integer userid = 0;
		Integer tcid = 0;
		String tlid = "";
		if (depModel != null) {
			depid = depModel.getDep_id();
		}
		if (rolModel != null) {
			rolid = rolModel.getRol_id();
		}
		if (userModel != null) {
			userid = userModel.getLog_id();
		}
		if (tcmModel != null) {
			tcid = tcmModel.getTacl_id();
		}
		if (tlmModel != null) {
			tlid = tlmModel.getTali_name();
		}
		setTasklist(userId, depid, rolid, userid, tcid, tlid);
		north.setOpen(false);
	}

	@Command("updateUserlist")
	@NotifyChange("userList")
	public void updateUserlist(@BindingParam("a") InputEvent e,
			@BindingParam("b") loginroleModel depModel,
			@BindingParam("c") loginroleModel rolModel) {
		Integer depid = 0;
		Integer rolid = 0;
		if (depModel != null) {
			depid = depModel.getDep_id();
		}
		if (rolModel != null) {
			rolid = rolModel.getRol_id();
		}
		setUserList(userId, depid, rolid, e.getValue());
	}

	@Command("updateDeplist")
	@NotifyChange({ "depList", "rolList", "userList" })
	public void updateDeplist(@BindingParam("a") InputEvent e) {
		setDepList(userId, e.getValue());
	}

	@Command("changeDep")
	@NotifyChange({ "rolList", "userList" })
	public void changeDep(@BindingParam("a") loginroleModel depModel,
			@BindingParam("b") Combobox cbRol,
			@BindingParam("c") Combobox cbUser) {
		Integer depid = 0;
		Integer rolid = 0;
		if (depModel != null) {
			depid = depModel.getDep_id();
		}
		cbRol.setValue("");
		cbUser.setValue("");
		setRolList(userId, depid, "");
		setUserList(userId, depid, rolid, "");
	}

	@Command("updateRollist")
	@NotifyChange({ "rolList", "userList" })
	public void updateRollist(@BindingParam("a") InputEvent e,
			@BindingParam("b") loginroleModel depModel) {
		Integer depid = 0;
		if (depModel != null) {
			depid = depModel.getDep_id();
		}
		setRolList(userId, depid, e.getValue());
	}

	@Command("changeRol")
	@NotifyChange("userList")
	public void changeRol(@BindingParam("a") loginroleModel rolModel,
			@BindingParam("b") loginroleModel depModel,
			@BindingParam("c") Combobox cbUser) {
		Integer depid = 0;
		Integer rolid = 0;
		if (depModel != null) {
			depid = depModel.getDep_id();
		}
		if (rolModel != null) {
			rolid = rolModel.getRol_id();
		}
		cbUser.setValue("");
		setUserList(userId, depid, rolid, "");
	}

	@Command("LinkMission")
	public void LinkMission(@BindingParam("a") TaskProcessListModel tplModel) {
		Map tplMap=new HashMap();
		tplMap.put("tplModel",tplModel);
		Window window = (Window)Executions.createComponents("Task_history.zul",null, tplMap);
		window.doModal();
	}

	public List<TaskProcessListModel> getTasklist() {
		return tasklist;
	}

	public void setTasklist(Integer logid, Integer depid, Integer rolid,
			Integer userid, Integer taclName, String taliName) {
		this.tasklist = bll.getTaskProcessList(logid, depid, rolid, userid,
				taclName, taliName);
	}

	public List<loginroleModel> getUserList() {
		return userList;
	}

	public void setUserList(Integer logid, Integer depid, Integer rolid,
			String username) {
		this.userList = bll.getUserList(logid, depid, rolid, username);
	}

	public List<loginroleModel> getDepList() {
		return depList;
	}

	public void setDepList(Integer logid, String depname) {
		this.depList = bll.getDepList(logid, depname);
	}

	public List<loginroleModel> getRolList() {
		return rolList;
	}

	public void setRolList(Integer logid, Integer depid, String rolname) {
		this.rolList = bll.getRolList(logid, depid, rolname);
	}

	public List<TaskClassModel> getTcList() {
		return tcList;
	}

	public void setTcList() {
		this.tcList = bll.getTaskClasslist();
	}

	public List<TaskListModel> getTlList() {
		return tlList;
	}

	public void setTlList() {
		this.tlList = bll.getTasklist();
	}

}
