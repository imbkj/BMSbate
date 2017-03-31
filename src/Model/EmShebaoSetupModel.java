package Model;

public class EmShebaoSetupModel {
	private int id;
	private int lastday;
	private String lastdayname;
	private int lastdaybj;
	private String lastdaynamebj;
	private int onair;
	private String onairname;
	private String reason;
	private int onairbj;
	private String onairnamebj;
	private String reasonbj;
	private int cwday;
	private int fallday;
	private int auditday;
	private String auditdayname;
	private String lastdate;
	private int malastday;
	private String malastdayname;
	
	public EmShebaoSetupModel() {
		super();
	}

	public EmShebaoSetupModel(int id, int lastday, String lastdayname,
			int lastdaybj, String lastdaynamebj, int onair, String onairname,
			String reason, int onairbj, String onairnamebj, String reasonbj,
			int cwday, int fallday, int auditday, String auditdayname, int malastday, String malastdayname) {
		super();
		this.id = id;
		this.lastday = lastday;
		this.lastdayname = lastdayname;
		this.lastdaybj = lastdaybj;
		this.lastdaynamebj = lastdaynamebj;
		this.onair = onair;
		this.onairname = onairname;
		this.reason = reason;
		this.onairbj = onairbj;
		this.onairnamebj = onairnamebj;
		this.reasonbj = reasonbj;
		this.cwday = cwday;
		this.fallday = fallday;
		this.auditday = auditday;
		this.auditdayname = auditdayname;
		this.malastday = malastday;
		this.malastdayname = malastdayname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getLastdate() {
		return lastdate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
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
