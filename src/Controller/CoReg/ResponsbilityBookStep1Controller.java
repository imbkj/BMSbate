package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoOnlineRegisterInfoModel;
import Model.ResponsbilityBookModel;
import Util.UserInfo;
import bll.CoReg.CoReg_Bll;
import bll.CoReg.CoReg_SpOperateBll;

public class ResponsbilityBookStep1Controller {
	private ResponsbilityBookModel r = new ResponsbilityBookModel();
	private CoReg_Bll bll = new CoReg_Bll();
	private CoOnlineRegisterInfoModel m = new CoOnlineRegisterInfoModel();
	private Date date;
	private int coriid;
	private int daid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private int taprid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	public ResponsbilityBookStep1Controller() {
		r = bll.getRbbm(daid);
		coriid = r.getRebk_cori_id();
		m = bll.getCoRim(coriid);
		date = new Date();
	}

	@Command
	public void submit(@BindingParam("gd") Grid gd,
			@BindingParam("win") Window win) {
		System.out.println("daid=" + daid);
		try {
			r.setRebk_rs_taketime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date));
			r.setRebk_rs_takename(UserInfo.getUsername());
			String[] message = docOC.AddchkHaveTo(gd);
			if (message[0] == "1") {
				docOC.AddsubmitDoc(gd, String.valueOf(daid));
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

	public CoOnlineRegisterInfoModel getM() {
		return m;
	}

	public void setM(CoOnlineRegisterInfoModel m) {
		this.m = m;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
