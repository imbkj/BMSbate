package Controller.CoHousingFund;

import impl.MessageImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Model.SysMessageModel;
import Util.SingleBllFactory;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class CoHouse_pwd_backController {

	private CoHousingFundPwdChangeModel m = (CoHousingFundPwdChangeModel) Executions
			.getCurrent().getArg().get("cfpc");
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();

	// 提交退回信息
	@Command
	public void submit(@BindingParam("win") Window win) {
		m.setCfpc_state(4); // 退回状态为4

		// 做退回操作时要发送一条短信给前道
		MessageService msgservice = new MessageImpl("CoHousingFundPwdChange",
				m.getCfpc_id());
		SysMessageModel model = new SysMessageModel();
		model.setSyme_content("在专办员申报中:" + m.getBackReason());// 短信内容
		model.setSyme_url("");
		model.setSyme_reply_id(0);
		model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
		model.setSmwr_type("");
		model.setSymr_name(m.getCfpc_addname());// 收件人姓名

		boolean flag = cfpb.changeStatus(m);
		if (flag) {
			msgservice.Add(model); // 发送系统短信
			Messagebox.show("退回成功", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("退回失败", "error", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public CoHousingFundPwdChangeModel getM() {
		return m;
	}

	public void setM(CoHousingFundPwdChangeModel m) {
		this.m = m;
	}

}
