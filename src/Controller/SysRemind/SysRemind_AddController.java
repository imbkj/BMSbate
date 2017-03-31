package Controller.SysRemind;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.sun.mail.dsn.message_deliverystatus;

import bll.SysMessage.SysMessage_AddBll;
import bll.SysRemind.SysRemindBll;

import Model.DepartmentListModel;
import Model.LoginModel;
import Model.PubRemindModel;
import Util.UserInfo;

public class SysRemind_AddController {

	private List<LoginModel> loginList = new ListModelList<LoginModel>();
	private List<LoginModel> selectloginliList = new ListModelList<LoginModel>();
	private List<DepartmentListModel> deptList = new ListModelList<DepartmentListModel>();
	private DepartmentListModel depModel = new DepartmentListModel();
	private String name = "";
	private String content = "";
	private String username = "";
	private int userid = 0;
	private Date remindtime = null;
	private boolean ifsms = false;
	private boolean ifemail = true;

	// 初始化
	public SysRemind_AddController() throws SQLException {
		SysMessage_AddBll bll = new SysMessage_AddBll();
		userid = Integer.parseInt(UserInfo.getUserid());
		username = UserInfo.getUsername();
		setLoginList(bll.getLoginList(" and log_id<>" + userid));
		setSelectloginliList(bll.getLoginList(" and log_id=" + userid));
		setDeptList(bll.getDeptList());
	}

	@Command("initltb")
	public void initltb(@BindingParam("ltb") Listbox ltb) {
		ltb.setMultiple(true);
	}

	// 接收人单个添加
	@Command("addsingleselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void addsingleselect(@BindingParam("selectitem") LoginModel item) {
		if (!selectloginliList.contains(item)) {
			selectloginliList.add(item);
			loginList.remove(item);
		}
	}

	// 接收人单个移除
	@Command("removesingleselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void removesingleselect(@BindingParam("selectitem") LoginModel item) {
		loginList.add(item);
		selectloginliList.remove(item);
	}

	// 接收人多个添加
	@Command("addselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void addselect(@BindingParam("selectitems") Set<Listitem> items) {
		if (items.size() > 0) {
			for (Listitem item : items) {
				if (!selectloginliList.contains((LoginModel) item.getValue())) {
					selectloginliList.add((LoginModel) item.getValue());
					loginList.remove((LoginModel) item.getValue());
				}
			}
		} else {
			Messagebox.show("请至少选择一人!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 接收人多个移除
	@Command("removeselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void removeselect(@BindingParam("selectitems") Set<Listitem> items) {
		if (items.size() > 0) {
			List<LoginModel> temList = new ListModelList<LoginModel>();
			for (Listitem item : items) {
				loginList.add((LoginModel) item.getValue());
				temList.add((LoginModel) item.getValue());
			}
			selectloginliList.removeAll(temList);
		} else {
			Messagebox.show("请至少选择一人!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 接收人全部添加
	@Command("allselect")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void allselect() {
		if (loginList.size() > 0) {
			selectloginliList.addAll(loginList);
			loginList.clear();
		}
	}

	// 接收人全部移除
	@Command("allremove")
	@NotifyChange({ "selectloginliList", "loginList" })
	public void allremove() {
		if (selectloginliList.size() > 0) {
			loginList.addAll(selectloginliList);
			selectloginliList.clear();
		}
	}

	// 检索
	@Command("search")
	@NotifyChange("loginList")
	public void search() {
		String str = "";
		String str1 = "";
		if (selectloginliList.size() > 0) {
			for (int i = 0; i < selectloginliList.size(); i++) {
				if (i == selectloginliList.size() - 1) {
					str1 += selectloginliList.get(i).getLog_id();
				} else {
					str1 += selectloginliList.get(i).getLog_id() + ",";
				}
			}
			str += " and log_id not in(" + str1 + ")";
		}
		if (depModel.getDep_id() != 0) {
			str += " and b.dep_id=" + depModel.getDep_id() + "";
		}
		if (!name.isEmpty()) {
			str += " and log_name like '%" + name + "%'";
		}
		SysMessage_AddBll bll = new SysMessage_AddBll();
		setLoginList(bll.getLoginList(str));
	}
	
	//提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win) throws Exception{
		if (selectloginliList.size() == 0) {
			Messagebox.show("请选择提醒人!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else if (!ifsms && !ifemail) {
			Messagebox.show("请至少选择一种提醒方式!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else if (remindtime == null) {
			Messagebox.show("请输入提醒时间!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else if (content.isEmpty()) {
			Messagebox.show("请输入提醒内容!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else {

			int issucces = 0;
			SysRemindBll bll = new SysRemindBll();
			PubRemindModel prModel = new PubRemindModel();

			prModel.setContent(content);
			prModel.setRemindtime(new SimpleDateFormat("yyyy-MM-dd hh:mm")
					.format(remindtime));
			prModel.setSmsid(ifsms ? 1 : 0);
			prModel.setEmailid(ifemail ? 1 : 0);
			prModel.setAddname(username);
			prModel.setLog_id(userid);

			issucces = bll.PubRemindAdd(prModel, selectloginliList);

			if (issucces == 1) {
				if (Messagebox.show("提交成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					win.detach();
				}
			} else {
				Messagebox.show("提交失败,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
		}
	}

	public List<LoginModel> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginModel> loginList) {
		this.loginList = loginList;
	}

	public List<LoginModel> getSelectloginliList() {
		return selectloginliList;
	}

	public void setSelectloginliList(List<LoginModel> selectloginliList) {
		this.selectloginliList = selectloginliList;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRemindtime() {
		return remindtime;
	}

	public void setRemindtime(Date remindtime) {
		this.remindtime = remindtime;
	}

	public boolean isIfsms() {
		return ifsms;
	}

	public void setIfsms(boolean ifsms) {
		this.ifsms = ifsms;
	}

	public boolean isIfemail() {
		return ifemail;
	}

	public void setIfemail(boolean ifemail) {
		this.ifemail = ifemail;
	}

}
