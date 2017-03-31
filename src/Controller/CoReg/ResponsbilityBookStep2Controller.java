package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_Bll;
import bll.CoReg.CoReg_SpOperateBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.ResponsbilityBookModel;
import Util.UserInfo;

public class ResponsbilityBookStep2Controller {
	private int daid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private int taprid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private ResponsbilityBookModel r = new ResponsbilityBookModel();
	private Date date;
	private CoReg_Bll bll = new CoReg_Bll();

	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	public ResponsbilityBookStep2Controller() {
		r = bll.getRbbm(daid);
		date = new Date();
	}

	@Command
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			r.setRebk_client_taketime(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(date));
			r.setRebk_client_takename(UserInfo.getUsername());
			String[] message = docOC.UpchkHaveTo(gd);
			if (message[0] == "1") {
				docOC.UpsubmitDoc(gd, String.valueOf(daid));
				CoReg_SpOperateBll bll = new CoReg_SpOperateBll();
				String[] str = bll.reSponStep(r, taprid, r.getCid());
				if (str[0].equals("1")) {
					Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getDaid() {
		return daid;
	}

	public void setDaid(int daid) {
		this.daid = daid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
