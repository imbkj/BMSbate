package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_OperateBll;

import Model.CoOnlineRegisterInfoModel;
import Util.UserInfo;

public class CoReg_BackController {

	private String reason = "";
	private String addname = "";
	Integer cori_id = 0;
	Integer tapr_id = 0;
	private String step = "";

	public CoReg_BackController() {
		try {
			setAddname(UserInfo.getUsername());
			cori_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			tapr_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
			setStep("上一步");
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		if (reason.isEmpty()) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			try {
				CoOnlineRegisterInfoModel m = new CoOnlineRegisterInfoModel();
				m = new CoReg_ListBll().getCoregInfo(cori_id);
				if (step.equals("上一步")) {
					m.setCori_id(cori_id);
					m.setCori_tapr_id(tapr_id);
					m.setCori_backname(addname);
					m.setCori_backreason(reason);
					m.setCori_state((Integer.parseInt(m.getCori_state()) - 1)
							+ "");

					String[] str = new CoReg_OperateBll().back(m);
					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else if (step.equals("第一步")) {
					m.setCori_id(cori_id);
					m.setCori_tapr_id(tapr_id);
					m.setCori_backname(addname);
					m.setCori_backreason(reason);
					m.setCori_state(0 + "");

					String[] str = new CoReg_OperateBll().backToN(m, 1);
					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}

			} catch (Exception e) {
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}
		}
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
}
