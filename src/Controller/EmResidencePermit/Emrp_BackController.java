package Controller.EmResidencePermit;

import impl.MessageImpl;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import bll.EmResidencePermit.Emrp_ListBll;
import bll.EmResidencePermit.Emrp_OperateBll;

import Model.EmResidencePermitInfoModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class Emrp_BackController {

	private EmResidencePermitInfoModel epm = new EmResidencePermitInfoModel();
	private Map map = Executions.getCurrent().getArg();
	private String step = "上一步";
	private Integer daid = 0;

	public Emrp_BackController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setEpm(new Emrp_ListBll().getEmrpInfo(daid));
			epm.setErpi_return_people(UserInfo.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		if (epm.getErpi_return_reason() == null
				|| epm.getErpi_return_reason().isEmpty()) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			try {
				String[] str = new String[5];

				if (step.equals("上一步")) {
					epm.setErpi_state(12);
					str = new Emrp_OperateBll().back(epm);
				} else if (step.equals("第一步")) {
					epm.setErpi_state(13);
					str = new Emrp_OperateBll().backToN(epm, 2);
				}
				if (str[0].equals("1")) {
					Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					map.put("flag", "1");
					String content = epm.getEmba_name() + "居住证信息被"
							+ epm.getErpi_return_people() + "退回";
					String tittle = "居住证退回";
					sendMsg(epm.getErpi_addname(), content, tittle);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 服务中心退回
	@Command
	public void centerback(@BindingParam("win") Window win) {
		if (epm.getErpi_return_reason() == null
				|| epm.getErpi_return_reason().isEmpty()) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			try {
				String[] str = new String[5];
				epm.setErpi_state(12);
				str = new Emrp_OperateBll().CenterbackToN(epm);
				if (str[0].equals("1")) {
					Messagebox.show("退回成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					map.put("flag", "1");
					String content = epm.getEmba_name() + "居住证信息被"
							+ epm.getErpi_return_people() + "退回";
					String tittle = "居住证退回";
					sendMsg(epm.getErpi_addname(), content, tittle);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 发短信
	private void sendMsg(String symr_name, String content, String tittle) {
		// 发短信
		MessageService msgservice = new MessageImpl("EmResidencePermitInfo",daid);
		SysMessageModel msgmodel = new SysMessageModel();
		msgmodel.setSyme_content(content);// 短信内容
		msgmodel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
		msgmodel.setSyme_title(tittle);
		LoginDal d = new LoginDal();
		msgmodel.setSymr_name(symr_name);// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname(symr_name));
		msgmodel.setEmail(0);
		msgmodel.setEmailtitle(tittle);
		msgservice.Add(msgmodel);
	}

	public EmResidencePermitInfoModel getEpm() {
		return epm;
	}

	public void setEpm(EmResidencePermitInfoModel epm) {
		this.epm = epm;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
}
