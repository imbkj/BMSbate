package Controller.EmCensus.EmDh;

import impl.MessageImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import Model.EmDhModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmdH_LxTakeIntroductionController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmDh_SelectBll bll = new EmDh_SelectBll();
	private List<EmDhModel> dhmodellist = bll.getEmDhInfo(" and a.id=" + id);
	private EmDhModel dhmodel = new EmDhModel();

	public EmdH_LxTakeIntroductionController() {
		if (dhmodellist.size() > 0) {
			dhmodel = dhmodellist.get(0);
		}
	}

	@Command
	public void submit(@BindingParam("win") Window win) {
		if (dhmodel.getEmdh_time12() == null) {
			Messagebox
					.show("请选择领取介绍信日期", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		EmDh_LxConreoller lxController = new EmDh_LxConreollerImpl("领取介绍信");
		String[] str = lxController.edit(dhmodel);
		if (str[0].equals("1")) {
			String content = dhmodel.getEmdh_name() + "("
					+ dhmodel.getGid() + ")调户已完成，请及时变更员工的其他项目信息，如档案调入、户口新增";
			String tittle = dhmodel.getEmdh_name() + "("
					+ dhmodel.getGid() + ")调户完成通知";
			sendMsg(dhmodel.getEmdh_client(), content, tittle);
			Messagebox.show("提交成功", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 重置流程
	@Command("beginagain")
	public void beginagain(@BindingParam("win") final Window win) {
		final EmDhModel model = new EmDhModel();
		model.setId(Integer.parseInt(id));
		if (tperid != null && !tperid.equals("")) {
			model.setEmdh_taprid(Integer.parseInt(tperid));
		}
		model.setEmdh_name(dhmodel.getEmdh_name());
		model.setEmdh_dhtype(dhmodel.getEmdh_dhtype());
		EmDh_LxConreoller lxController = new EmDh_LxConreollerImpl("重置流程");
		String[] str = lxController.edit(model);
		if (str[0].equals("1")) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else if (str[0].equals("-1")) {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void openZul(@BindingParam("openType") String openType) {
		if (openType != null && !openType.equals("")) {
			EmDh_OpenFactory factory = new EmDh_OpenFactoryImpl(openType);
			EmDh_OpenZul openZul = factory.OpenZulFactory();
			openZul.open(dhmodel);
		}
	}

	public EmDhModel getDhmodel() {
		return dhmodel;
	}

	public void setDhmodel(EmDhModel dhmodel) {
		this.dhmodel = dhmodel;
	}

	// 发短信
	private void sendMsg(String symr_name, String content, String tittle) {
		// 发短信
		MessageService msgservice = new MessageImpl("EmDH",
				Integer.parseInt(id));
		SysMessageModel msgmodel = new SysMessageModel();
		msgmodel.setSyme_content(content);// 短信内容
		msgmodel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
		msgmodel.setSyme_title(tittle);
		LoginDal d = new LoginDal();
		msgmodel.setSymr_name(symr_name);// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname(symr_name));
		msgmodel.setEmail(1);// 同时发邮件
		msgmodel.setEmailtitle(tittle);
		msgservice.Add(msgmodel);
	}
}
