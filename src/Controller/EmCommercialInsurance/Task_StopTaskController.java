package Controller.EmCommercialInsurance;

import impl.MessageImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import Model.SysMessageModel;
import Util.UserInfo;
import bll.Taskflow.Task_controlBll;

public class Task_StopTaskController {
	private final int tali_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("tali_id").toString());
	private final String tali_name = Executions.getCurrent().getArg()
			.get("tali_name").toString();
	private final int gid = Integer.parseInt(Executions.getCurrent()
			.getArg().get("gid").toString());
	private final int tb_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("tb_id").toString());
	private final String client = Executions.getCurrent().getArg()
			.get("client").toString();
	private Task_controlBll bll;
	private String remark = "";

	public Task_StopTaskController() {

	}

	@Command
	public void stopTask(@BindingParam("win") Window win) {
		try {
			if (Messagebox.show("确认需要终止编号为：" + tali_id + "的任务单吗？", "操作提示",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				bll = new Task_controlBll();
				int i = bll.stopTask(tali_id, remark, UserInfo.getUsername());
				if (i == 1) {
					Messagebox.show("操作成功，请自行刷新列表。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					
					// 发邮件和系统短信
					MessageService msgservice = new MessageImpl(
							"EmCommercialInsurance", tb_id);
					LoginDal d = new LoginDal();
					SysMessageModel sysm = new SysMessageModel();
					String msgstr = "编号" + gid + "商业保险连带人信息被退回："+remark;
					sysm.setSyme_title("商业保险连带人信息被退回");
					sysm.setSyme_content(msgstr);// 短信内容
					sysm.setSyme_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// 发件人id
					sysm.setEmail(1);
					sysm.setEmailtitle(msgstr);
					sysm.setSymr_name(client);// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname(client));// ;
					msgservice.Add(sysm);
					sysm.setSymr_name("张志强");// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname("张志强"));// ;
					msgservice.Add(sysm);
					
					win.detach();
				} else if (i == -1) {
					Messagebox.show("该步骤已处理,无法继续操作。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else if (i == -2) {
					Messagebox.show("该步骤不允许终止。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTali_id() {
		return tali_id;
	}

	public String getTali_name() {
		return tali_name;
	}

}
