package Controller.EmResidencePermit;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmResidencePermit.Emrp_ChangeBll;

import Model.EmResidencePermitChangeModel;
import Util.UserInfo;

public class Emrp_ChangeBackController {
	private EmResidencePermitChangeModel m = (EmResidencePermitChangeModel) Executions
			.getCurrent().getArg().get("model");
	private String backcase = "";
	private Map map=Executions.getCurrent().getArg();

	@Command
	public void submit(@BindingParam("win") Window win) {
		if (backcase != null && !backcase.equals("")) {
			m.setEpcr_remark(backcase);//把退回原因放到备注中
			m.setErpc_addname(UserInfo.getUsername());
			m.setErpc_state(4);
			Emrp_ChangeBll bll = new Emrp_ChangeBll();
			String[] str = bll.EmrpBack(m);
			if (str[0] == "1") {
				map.put("flag", "1");
				Messagebox.show("退回成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请输入退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getBackcase() {
		return backcase;
	}

	public void setBackcase(String backcase) {
		this.backcase = backcase;
	}

}
