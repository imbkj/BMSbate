package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmActyCompactModel;
import Model.TaskListModel;
import bll.EmBenefit.EmActy_compactAuditBll;

public class EmActy_compactPrintController {
	private Integer taprId = 0;
	private Integer daid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer look = 0;
	private Integer audit = 0;
	private Integer taclId = 0;
	private String company = "";
	private String compactid = "";
	private String reason = "";
	private boolean dis = false;

	private EmActy_compactAuditBll bll = new EmActy_compactAuditBll();

	public EmActy_compactPrintController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			taprId = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
		}
		if (Executions.getCurrent().getArg().get("look") != null) {
			look = Integer.valueOf(Executions.getCurrent().getArg().get("look")
					.toString());
		}
		if (Executions.getCurrent().getArg().get("audit") != null) {
			audit = Integer.valueOf(Executions.getCurrent().getArg()
					.get("audit").toString());
		}
		EmActyCompactModel em = new EmActyCompactModel();
		em.setEaco_id(daid);
		List<EmActyCompactModel> compactlist = bll.getCompactList(em, false);

		if (compactlist.size() > 0) {
			if (look.equals(0)) {
				look = compactlist.get(0).getEaco_state();
			}
			if (compactlist.get(0).getEaco_state().equals(5)) {
				company = compactlist.get(0).getEaco_name();
				compactid = compactlist.get(0).getEaco_compactid();
				reason = compactlist.get(0).getEaco_backreason();
				dis = true;
			}

		}

		List<TaskListModel> list = bll.gettaclId(taprId);
		if (list.size() > 0) {
			taclId = list.get(0).getTali_tacl_id();
		}

	}

	public Integer getTaprId() {
		return taprId;
	}

	public void setTaprId(Integer taprId) {
		this.taprId = taprId;
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public Integer getLook() {
		return look;
	}

	public void setLook(Integer look) {
		this.look = look;
	}

	public Integer getTaclId() {
		return taclId;
	}

	public void setTaclId(Integer taclId) {
		this.taclId = taclId;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isDis() {
		return dis;
	}

	public void setDis(boolean dis) {
		this.dis = dis;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompactid() {
		return compactid;
	}

	public void setCompactid(String compactid) {
		this.compactid = compactid;
	}

}
