package Controller.EmSheBao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import Model.EmShebaoSetupModel;
import Util.DateStringChange;
import Util.UserInfo;

public class Emsc_InstallController {
	private int lastday;
	private String lastdayname;
	private int lastdaybj;
	private String lastdaynamebj;
	private int onair;
	private String onairstr;
	private String onairname;
	private String reason;
	private int onairbj;
	private String onairbjstr;
	private String onairnamebj;
	private String reasonbj;
	private int cwday;
	private int fallday;
	private int auditday;
	private String auditdayname;
	private int malastday;
	private String malastdayname;
	private EmShebaoSetupModel emModel;
	private Emsc_DeclareSelBll bll;

	public Emsc_InstallController() {
		bll = new Emsc_DeclareSelBll();
		emModel = bll.getSetup();
		// 页面赋值
		setValue();
	}

	@Command("assigenment")
	public void assigenment() {
		Window window = (Window) Executions.createComponents(
				"Emsc_Assignment.zul", null, null);
		window.doModal();
	}

	@Command("setup")
	@NotifyChange({ "lastdayname", "lastdaynamebj", "onairname", "onairnamebj",
			"auditdayname","malastday","malastdayname" })
	public void setup() {
		try {
			// 比对变更内容
			if (checkCon() > 0) {
				Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
				int i = opbll.Setup(emModel);
				if (i == 1) {
					emModel = bll.getSetup();
					// 页面重新赋值
					setValue();
					// 弹出提示
					Messagebox.show("更新社保设置成功", "操作提示", Messagebox.OK,
							Messagebox.NONE);

				} else {
					// 弹出提示
					Messagebox.show("更新社保设置失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				// 弹出提示
				Messagebox.show("您并未修改任何内容，无需提交。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			// 弹出提示
			Messagebox.show("更新社保设置出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 比对变更内容
	private int checkCon() {
		String username = UserInfo.getUsername() + "修改";
		String now = DateStringChange.DatetoSting(new Date(),
				"yyyy-MM-dd HH:mm:ss");
		int i = 0;
		if (lastday != emModel.getLastday()) {
			emModel.setLastday(lastday);
			emModel.setLastdayname(now + " " + username);
			i++;
		}
		if (lastdaybj != emModel.getLastdaybj()) {
			emModel.setLastdaybj(lastdaybj);
			emModel.setLastdaynamebj(now + " " + username);
			i++;
		}
		if (auditday != emModel.getAuditday()) {
			emModel.setAuditday(auditday);
			emModel.setAuditdayname(now + " " + username);
			i++;
		}
		if (Integer.parseInt(onairstr) != emModel.getOnair()) {
			emModel.setOnair(Integer.parseInt(onairstr));
			emModel.setReason(reason);
			emModel.setOnairname(now + " " + username);
			i++;
		}
		if (Integer.parseInt(onairbjstr) != emModel.getOnairbj()) {
			emModel.setOnairbj(Integer.parseInt(onairbjstr));
			emModel.setReasonbj(reasonbj);
			emModel.setOnairnamebj(now + " " + username);
			i++;
		}
		if (cwday != emModel.getCwday()) {
			emModel.setCwday(cwday);
			i++;
		}
		if (malastday != emModel.getMalastday()) {
			emModel.setMalastday(malastday);
			emModel.setMalastdayname(now + " " + username);
			i++;
		}
		return i;
	}

	// 页面赋值
	private void setValue() {
		lastday = emModel.getLastday();
		lastdayname = "(" + emModel.getLastdayname() + ")";
		lastdaybj = emModel.getLastdaybj();
		lastdaynamebj = "(" + emModel.getLastdaynamebj() + ")";
		onairname = "(" + emModel.getOnairname() + ")";
		reason = emModel.getReason();
		onairnamebj = "(" + emModel.getOnairnamebj() + ")";
		reasonbj = emModel.getReasonbj();
		cwday = emModel.getCwday();
		fallday = emModel.getFallday();
		auditday = emModel.getAuditday();
		auditdayname = "(" + emModel.getAuditdayname() + ")";
		onairstr = String.valueOf(emModel.getOnair());
		onairbjstr = String.valueOf(emModel.getOnairbj());
		malastday = emModel.getMalastday();
		malastdayname = "(" + emModel.getMalastdayname() + ")";
	}

	public String getOnairbjstr() {
		return onairbjstr;
	}

	public void setOnairbjstr(String onairbjstr) {
		this.onairbjstr = onairbjstr;
	}

	public String getOnairstr() {
		return onairstr;
	}

	public void setOnairstr(String onairstr) {
		this.onairstr = onairstr;
	}

	public int getLastday() {
		return lastday;
	}

	public void setLastday(int lastday) {
		this.lastday = lastday;
	}

	public String getLastdayname() {
		return lastdayname;
	}

	public void setLastdayname(String lastdayname) {
		this.lastdayname = lastdayname;
	}

	public int getLastdaybj() {
		return lastdaybj;
	}

	public void setLastdaybj(int lastdaybj) {
		this.lastdaybj = lastdaybj;
	}

	public String getLastdaynamebj() {
		return lastdaynamebj;
	}

	public void setLastdaynamebj(String lastdaynamebj) {
		this.lastdaynamebj = lastdaynamebj;
	}

	public int getOnair() {
		return onair;
	}

	public void setOnair(int onair) {
		this.onair = onair;
	}

	public String getOnairname() {
		return onairname;
	}

	public void setOnairname(String onairname) {
		this.onairname = onairname;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getOnairbj() {
		return onairbj;
	}

	public void setOnairbj(int onairbj) {
		this.onairbj = onairbj;
	}

	public String getOnairnamebj() {
		return onairnamebj;
	}

	public void setOnairnamebj(String onairnamebj) {
		this.onairnamebj = onairnamebj;
	}

	public String getReasonbj() {
		return reasonbj;
	}

	public void setReasonbj(String reasonbj) {
		this.reasonbj = reasonbj;
	}

	public int getCwday() {
		return cwday;
	}

	public void setCwday(int cwday) {
		this.cwday = cwday;
	}

	public int getFallday() {
		return fallday;
	}

	public void setFallday(int fallday) {
		this.fallday = fallday;
	}

	public int getAuditday() {
		return auditday;
	}

	public void setAuditday(int auditday) {
		this.auditday = auditday;
	}

	public String getAuditdayname() {
		return auditdayname;
	}

	public void setAuditdayname(String auditdayname) {
		this.auditdayname = auditdayname;
	}

	public int getMalastday() {
		return malastday;
	}

	public void setMalastday(int malastday) {
		this.malastday = malastday;
	}

	public String getMalastdayname() {
		return malastdayname;
	}

	public void setMalastdayname(String malastdayname) {
		this.malastdayname = malastdayname;
	}

}
