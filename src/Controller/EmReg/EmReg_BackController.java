package Controller.EmReg;

import impl.MessageImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import Model.EmRegistrationInfoModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

public class EmReg_BackController {
	private Map map = Executions.getCurrent().getArg();
	Integer daid;
	private EmRegistrationInfoModel m = new EmRegistrationInfoModel();
	private String step = "上一步";
	private String back_case = "";

	public EmReg_BackController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getEmRegInfo(daid, ""));
			m.setErsr_addname(UserInfo.getUsername());

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		if (m.getErsr_remark() == null || m.getErsr_remark().isEmpty()) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			try {
				String[] str = new String[5];

				m.setErsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date()));

				if (step.equals("上一步")) {
					m.setErin_state(9);
					str = new EmReg_OperateBll().back(m);
				} else if (step.equals("第一步")) {
					m.setErin_state(11);
					str = new EmReg_OperateBll().backToN(m, 2);
				}
				if (str[0].equals("1")) {
					String content = m.getEmba_name() + "的就业登记信息被"
							+ UserInfo.getUsername() + "退回";
					String tittle =m.getEmba_name() + "——就业登记退回";
					sendMsg(m.getErin_addname(), content, tittle,"EmRegistrationInfo");
					Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);

					map.put("flag", "2");
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

	// 中心退回
	@Command
	public void centerbvack(@BindingParam("win") Window win) {
		if (back_case != null && !back_case.equals("")) {
			EmReg_OperateBll obll = new EmReg_OperateBll();
			m.setErin_state(9);
			String[] str = new EmReg_OperateBll().StopTask(m);
			if (str[0].equals("1")) {
				String content = m.getEmba_name() + "的就业登记信息被服务中心"
						+ UserInfo.getUsername() + "退回";
				String tittle =m.getEmba_name() + "——就业登记退回";
				sendMsg(m.getErin_addname(), content, tittle,"EmRegistrationInfo");
				Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				
				map.put("flag", "2");
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请填写退回原因", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 发短信
	private void sendMsg(String symr_name, String content, String tittle,
			String table) {
		// 发短信
		MessageService msgservice = new MessageImpl(table,daid);
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

	public EmRegistrationInfoModel getM() {
		return m;
	}

	public void setM(EmRegistrationInfoModel m) {
		this.m = m;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getBack_case() {
		return back_case;
	}

	public void setBack_case(String back_case) {
		this.back_case = back_case;
	}

}
