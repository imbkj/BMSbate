package Controller.EmSheBao;

import impl.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;

import dal.LoginDal;

import Model.EmShebaoErrModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.SysMessage.Message_SelectBll;
import bll.SysMessage.SysMessage_AddBll;

public class Emsc_FalseCheckMsgController {
	private EmShebaoErrModel model = (EmShebaoErrModel) Executions.getCurrent()
			.getArg().get("model");

	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();

	// 系统短信
	private SysMessage_AddBll smbll = new SysMessage_AddBll();
	private Message_SelectBll sbll = new Message_SelectBll();
	private List<LoginModel> loginList = smbll.getLoginList(" and log_id<>"
			+ UserInfo.getUserid());
	private List<LoginModel> deptlist = new ArrayList<LoginModel>();
	private String selectedname = "";
	private List<LoginModel> selectedlist = new ArrayList<LoginModel>();
	private LoginDal d = new LoginDal();
	private LoginModel ml = new LoginModel();
	private String msgname = "";
	private MessageService msgservice;

	private String normal = "";// 是否正常数据
	private String note = "";// 详细说明
	private String solve = "";// 处理情况
	private String title = "";// 处理情况
	private String content = "";// 处理情况

	public Emsc_FalseCheckMsgController() {
		title = "(" + model.getCid() + ")" + model.getCoba_shortname() + "的("
				+ model.getGid() + ")" + model.getEmse_name();
		getMssageName(model.getCoba_client());
		msgservice = new MessageImpl("EmShebaoErr", model.getId());

	}

	// 选择收件人
	@Command
	@NotifyChange("selectedname")
	public void checkname(@BindingParam("bd") Bandbox bx,
			@BindingParam("model") LoginModel model,
			@BindingParam("lbitem") Listitem item) {
		if (item.isSelected()) {
			selectedlist.add(model);
		} else {
			selectedlist.remove(model);
		}
		getSelectname();
	}

	// 把已选择的收件人串起来
	private void getSelectname() {
		selectedname = "";
		for (LoginModel m : selectedlist) {
			if (selectedname == "") {
				selectedname = m.getLog_name();
			} else {
				selectedname = selectedname + ";" + m.getLog_name();
			}
		}
	}

	// 获取收件人
	private void getMssageName(String msg_name) {
		selectedname = "";
		msgname = "";
		deptlist = new ArrayList<LoginModel>();
		selectedlist = new ArrayList<LoginModel>();

		List<LoginModel> llist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(msg_name);
		m.setLog_id(d.getUserIDByname(msg_name));
		llist.add(m);

		if (llist.size() > 0) {
			for (LoginModel lm : llist) {
				if (lm.getLog_name() != null) {
					ml = lm;
					selectedlist.add(ml);
					getSelectname();
					if (msgname == "") {
						msgname = "'" + lm.getLog_name() + "'";
					} else {
						msgname = msgname + ",'" + lm.getLog_name() + "'";
					}
				}
			}
		}
		deptlist = sbll.getLoginList(msgname);
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("cb") Checkbox cb) {
		if ("".equals(chkPage())) {

			if ("是".equals(normal)) {
				model.setEmse_Normal(1);
			} else if ("否".equals(normal)) {
				model.setEmse_Normal(0);
			}
			model.setEmse_Note(note);
			model.setEmse_Solve(solve);
			model.setEmse_Remark(content);

			String[] message;

			// 处理逻辑检查数据
			message = dobll.declareSheBaoErr(model);

			if (message[0].equals("1")) {
				// 处理调走数据
				if ("被其他公司调走".equals(note)) {
					dobll.updateSheBaoIfstop(model.getGid());
				}

				// 处理大学生医疗
				if ("参加大学生医疗".equals(note)) {
					dobll.updateSheBaoyltype(model.getGid());
				}
				// 小信封
				for (LoginModel m : selectedlist) {
					SysMessageModel model = new SysMessageModel();
					model.setSyme_content(content);// 短信内容
					model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
					model.setSymr_name(m.getLog_name());// 收件人姓名
					model.setSymr_log_id(m.getLog_id());
					if (cb.isChecked()) {
						model.setEmail(1);// 1表示同时发邮件
					} else {
						model.setEmail(0);
					}
					model.setSyme_title(title);
					// 调用方法
					msgservice.Add(model);
				}

				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			// 弹出提示
			Messagebox.show(chkPage(), "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 选择详细说明
	@Command("changeNote")
	@NotifyChange({ "content", "solve", "selectedname", "deptlist" })
	public void changeNote() {
		if ("被其他公司调走".equals(note)) {
			content = "该员工被其他公司调走，单位编号：，目前缴交状态：";
			solve = "已短信通知客服";
		} else if ("已退保或在个人窗口缴交".equals(note)) {
			content = "该员工已退保或在个人窗口缴交。";
			solve = "已短信通知客服";
		} else if ("由大学生医疗转为一档医疗".equals(note)) {
			content = "该员工本月医疗参保类型已由大学生医疗转为一档医疗，请知悉。";
			solve = "已短信通知客服";
		} else if ("参加大学生医疗".equals(note)) {
			content = "该员工本月医疗参保类型为大学生医疗，请知悉。";
			solve = "已短信通知客服";
		} else if ("提取住房公积金".equals(note)) {
			content = "该员工已提取住房公积金，望知会";
			solve = "已短信通知客服";
		} else if ("系统问题".equals(note)) {
			getMssageName(dsbll.getSheBaoIT());
			solve = "已通知信息部解决";
		}

		if (!"系统问题".equals(note)) {
			getMssageName(model.getCoba_client());
		}
	}

	private String chkPage() {
		String result = "";
		if ("".equals(normal)) {
			result = "请选择是否正常数据!";
		} else if ("".equals(note)) {
			result = "请选择详细说明!";
		} else if ("".equals(solve)) {
			result = "请选择解决情况!";
		} else if (selectedlist.size() <= 0) {
			result = "请输入或选择收件人!";
		} else if ("".equals(title)) {
			result = "请输入主题!";
		} else if ("".equals(content)) {
			result = "请输入内容!";
		}
		return result;
	}

	public List<LoginModel> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginModel> loginList) {
		this.loginList = loginList;
	}

	public List<LoginModel> getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(List<LoginModel> deptlist) {
		this.deptlist = deptlist;
	}

	public String getSelectedname() {
		return selectedname;
	}

	public void setSelectedname(String selectedname) {
		this.selectedname = selectedname;
	}

	public String getMsgname() {
		return msgname;
	}

	public void setMsgname(String msgname) {
		this.msgname = msgname;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSolve() {
		return solve;
	}

	public void setSolve(String solve) {
		this.solve = solve;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
