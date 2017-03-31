package Controller.CoSocialInsurance;

import impl.MessageImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;

import Model.CoShebaoChangeModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;
import bll.SysMessage.SysMessage_AddBll;

public class CoSocialInsurance_DocBackController {

	Integer daid = 0;
	private Integer puzu_id = 0;

	CoShebaoChangeModel m = new CoShebaoChangeModel();

	public CoSocialInsurance_DocBackController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			m = new CoSocialInsurance_ListBll().getCoShebaoChangeInfo(daid);

			getpuzu_id();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 获取材料id
	 * 
	 */
	public void getpuzu_id() {
		if (m.getCsbc_addtype().equals("缴存登记")) {
			setPuzu_id(36);
		} else if (m.getCsbc_addtype().equals("信息变更")) {
			setPuzu_id(37);
		} else if (m.getCsbc_addtype().equals("账户注销")) {
			setPuzu_id(38);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			String[] str = new CoSocialInsurance_OperateBll().DocBack(m, gd);
			if (str[0].equals("1")) {
				sendMessage();
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public boolean sendMessage() {

		SysMessage_AddBll bll = new SysMessage_AddBll();
		SysMessageModel model = new SysMessageModel();
		List<SysMessageModel> list = new ListModelList<>();
		List<LoginModel> list2 = new ListModelList<>();
		// 传参数
		model.setSyme_content(m.getCoba_company() + "-社保单位账户信息变更已完成");
		model.setSyme_title("社保单位信息变更通知");
		model.setSyme_state(1);
		model.setEmail(1);
		model.setEmailtitle("社保单位信息变更通知");
		MessageService msgservice = new MessageImpl();
		list2 = bll.getLoginList(" and log_name='" + UserInfo.getUsername()
				+ "'");
		model.setSymr_log_id(list2.get(0).getLog_id());
		model.setSymr_name(list2.get(0).getLog_name());
		model.setSymr_state(1);
		list.add(model);
		String[] str = msgservice.Add(model);
		if (str[0].equals("1")) {
			return true;
		}else {
			return false;
		}
	}

	public Integer getPuzu_id() {
		return puzu_id;
	}

	public void setPuzu_id(Integer puzu_id) {
		this.puzu_id = puzu_id;
	}
}
