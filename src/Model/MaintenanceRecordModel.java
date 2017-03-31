package Model;

import java.util.Date;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10 维护记录表 的model
 */
public class MaintenanceRecordModel {
	// 主键
	private int id;
	// 申请人
	private String proposer;
	// 申请原因
	private String proposereson;
	// 申请日期
	private Date proposerdate;
	// sql语句
	private String sql;
	// 审批人
	private String approver;
	// 审核状态
	private int status;
	// 审核时间
	private Date audittime;
	// 维护结果
	private String mainresult;
	// 是否需要备份
	private int isbackup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getProposereson() {
		return proposereson;
	}

	public void setProposereson(String proposereson) {
		this.proposereson = proposereson;
	}

	public Date getProposerdate() {
		return proposerdate;
	}

	public void setProposerdate(Date proposerdate) {
		this.proposerdate = proposerdate;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public String getMainresult() {
		return mainresult;
	}

	public void setMainresult(String mainresult) {
		this.mainresult = mainresult;
	}

	public int getIsbackup() {
		return isbackup;
	}

	public void setIsbackup(int isbackup) {
		this.isbackup = isbackup;
	}

}
