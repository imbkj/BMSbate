package Controller.SysMessage;

import impl.UserInfoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import service.UserInfoService;

import bll.SysMessage.SysMessage_AddBll;
import bll.SysMessage.SysMessage_NotReadListBll;

import Model.DepartmentListModel;
import Model.SysMessageModel;

public class SysMessage_NotReadListController {

	private List<SysMessageModel> notreadlist = new ListModelList<SysMessageModel>();
	private List<DepartmentListModel> deptList = new ListModelList<DepartmentListModel>();
	private String name = "";
	private DepartmentListModel depModel = new DepartmentListModel();

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	// 初始化
	public SysMessage_NotReadListController() throws Exception {
		SysMessage_NotReadListBll bll = new SysMessage_NotReadListBll();
		SysMessage_AddBll addbll = new SysMessage_AddBll();
		int log_id = user.getUserid().equals("err") ? 0 : Integer.parseInt(user
				.getUserid());
		setNotreadlist(bll.getNotReadList(log_id, ""));
		setDeptList(addbll.getDeptList());
	}

	// 弹窗
	@Command("opendetail")
	@NotifyChange("notreadlist")
	public void opendetail(@BindingParam("each") SysMessageModel model) {
		Map map = new HashMap();
		map.put("logid", model.getSyme_log_id());
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_NotReadInfoList.zul", null, map);
		window.doModal();
		search();
	}

	@Command("search")
	@NotifyChange("notreadlist")
	public void search() {
		String str = "";
		if (!name.isEmpty()) {
			str += " and log_name like '%" + name + "%'";
		}
		if (depModel.getDep_id() != 0) {
			str += " and d.dep_id=" + depModel.getDep_id() + "";
		}
		SysMessage_NotReadListBll bll = new SysMessage_NotReadListBll();
		int log_id = user.getUserid().equals("err") ? 0 : Integer.parseInt(user
				.getUserid());
		setNotreadlist(bll.getNotReadList(log_id, str));
	}

	public List<SysMessageModel> getNotreadlist() {
		return notreadlist;
	}

	public void setNotreadlist(List<SysMessageModel> notreadlist) {
		this.notreadlist = notreadlist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DepartmentListModel> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DepartmentListModel> deptList) {
		this.deptList = deptList;
	}

	public DepartmentListModel getDepModel() {
		return depModel;
	}

	public void setDepModel(DepartmentListModel depModel) {
		this.depModel = depModel;
	}

}
