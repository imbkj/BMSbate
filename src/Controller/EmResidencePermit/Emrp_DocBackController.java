package Controller.EmResidencePermit;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmResidencePermit.Emrp_OperateBll;

public class Emrp_DocBackController {

	private Integer daid = 0;
	private Integer gid = 0;

	public Emrp_DocBackController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		Emrp_OperateBll bll = new Emrp_OperateBll();
		try {
			String[] str = bll.DocBack(daid, gd);
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
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}
}
