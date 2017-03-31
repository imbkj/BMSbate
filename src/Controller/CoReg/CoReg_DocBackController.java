package Controller.CoReg;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoOnlineRegisterInfoModel;
import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_OperateBll;

public class CoReg_DocBackController {

	Integer cori_id,taprid;
	private CoOnlineRegisterInfoModel com = new CoOnlineRegisterInfoModel();
	
	
	public CoReg_DocBackController() {
		try {
			cori_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			taprid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
			setCom(new CoReg_ListBll().getCoregInfo(cori_id));
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			String[] str = new CoReg_OperateBll().DocBack(cori_id, gd);
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Command("docback")
	public void docback(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			String[] str = new CoReg_OperateBll().CoRegDocBack(com, gd);
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public CoOnlineRegisterInfoModel getCom() {
		return com;
	}

	public void setCom(CoOnlineRegisterInfoModel com) {
		this.com = com;
	}
}
