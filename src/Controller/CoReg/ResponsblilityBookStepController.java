package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoOnlineRegisterInfoModel;
import Model.ResponsbilityBookModel;
import Util.UserInfo;
import bll.CoReg.CoReg_Bll;
import bll.CoReg.CoReg_SpOperateBll;

public class ResponsblilityBookStepController {

	private Date date;
	private ResponsbilityBookModel r = new ResponsbilityBookModel();
	private CoReg_Bll bll = new CoReg_Bll();
	private CoOnlineRegisterInfoModel m = new CoOnlineRegisterInfoModel();
	private int coriid;
	private int cid;
	private Date cDate;
	private String datename;
	private Date reg_date;
	private int daid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private int taprid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());

	public ResponsblilityBookStepController() {
		init();
		reg_date = new Date();
		date = new Date();
		cDate = new Date();
		coriid = r.getRebk_cori_id();
		cid = r.getCid();
		m = bll.getCoRim(coriid);
		System.out.println(r.getRebk_step_state());
		
	}

	public void init() {
		r = bll.getRbbm(daid);
		if (r.getRebk_step_state() == 1) {
			datename = "人事领取材料和表格时间";
		} else if (r.getRebk_step_state() == 2) {
			datename = "客服领取材料与表格";
		} else if (r.getRebk_step_state() == 3) {
			datename = "人事确认材料时间";
		} else if (r.getRebk_step_state() == 4) {
			datename = "人事办理完成日期";
		}
	}

	@Command
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		if (r.getRebk_step_state() == 1) {
			r.setRebk_rs_taketime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date));
			r.setRebk_rs_takename(UserInfo.getUsername());
		} else if (r.getRebk_step_state() == 2) {
			r.setRebk_client_taketime(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(date));
			r.setRebk_client_takename(UserInfo.getUsername());
		} else if (r.getRebk_step_state() == 3) {
			r.setRebk_rs_signtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date));
			r.setRebk_rs_signname(UserInfo.getUsername());
		} else if (r.getRebk_step_state() == 4) {
			r.setRebk_rs_oktime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date));
			r.setRebk_signdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(cDate));
			r.setRebk_signname(UserInfo.getUsername());
			// 修改基本信息
			bll.modCorim(m);

		}
		CoReg_SpOperateBll bll = new CoReg_SpOperateBll();
		String[] str = bll.reSponStep(r, taprid, cid);
		if (str[0].equals("1")) {
			Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public Date getcDate() {
		return cDate;
	}

	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}

	public String getDatename() {
		return datename;
	}

	public void setDatename(String datename) {
		this.datename = datename;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ResponsbilityBookModel getR() {
		return r;
	}

	public void setR(ResponsbilityBookModel r) {
		this.r = r;
	}

	public CoOnlineRegisterInfoModel getM() {
		return m;
	}

	public void setM(CoOnlineRegisterInfoModel m) {
		this.m = m;
	}

}
