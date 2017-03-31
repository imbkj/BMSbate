package Controller.EmCommissionOut.Standard;

import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.Standard.Standard_ListBll;
import bll.EmCommissionOut.Standard.Standard_OperateBll;

import Model.EmCommissionOutStandardModel;
import Util.UserInfo;

public class Standard_BackController {

	private EmCommissionOutStandardModel m = new EmCommissionOutStandardModel();
	Integer daid = 0;

	public Standard_BackController() {
		try {
			Standard_ListBll bll = new Standard_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			setM(bll.getStandardInfo1(daid));
			m.setEosr_addname(UserInfo.getUsername());
			m.setEosr_statetime(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		if (m.getEosr_remark() == null || m.getEosr_remark().isEmpty()) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			try {
				String[] str = new String[5];

				m.setEcos_state(5);

				str = new Standard_OperateBll().Return(m);
				if (str[0].equals("1")) {
					Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
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

	public EmCommissionOutStandardModel getM() {
		return m;
	}

	public void setM(EmCommissionOutStandardModel m) {
		this.m = m;
	}
}
