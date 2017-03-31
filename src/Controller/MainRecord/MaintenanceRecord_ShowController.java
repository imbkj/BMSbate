package Controller.MainRecord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import Model.MaintenanceRecordModel;
import bll.MainRecord.MaintenanceRecordBll;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10
 */
public class MaintenanceRecord_ShowController {
	 private MaintenanceRecordModel mm = new MaintenanceRecordModel();
	 private List<MaintenanceRecordModel> mainRecordList =new ArrayList<MaintenanceRecordModel>();
	 private MaintenanceRecordBll bll= new MaintenanceRecordBll();
	 private List<String> listPro=null;//申请日期列表
	 private List<String> listAdu=null;//审核日期列表
	 private String approver="";//审批人
	 private String audittime="";//审核的时间
	 private String proposereson="";//申请原因
	 private String proposerdate="";//申请日期
	 private String proposer="";//申请人
	 
	public MaintenanceRecord_ShowController() {
		listPro= bll.getDayList();//获取维护记录申请日期列表
		listAdu= bll.getAduitDayList();//获取维护记录审核日期列表
		serch();
	}
	//按条件查询当前用户提交的已审核维护记录
	public void serch(){
		StringBuilder strsql= new StringBuilder();
		if(!approver.trim().equals("")){
			strsql.append(" and approver like '%"+approver+"%'" );
		}
		if(!proposer.trim().equals("")){
			strsql.append(" and proposer like '%"+proposer+"%'" );
		}
		if(!audittime.trim().equals("")){
			strsql.append(" and CONVERT(varchar(100), audittime, 112)='"+audittime+"'" );
		}
		if(!proposereson.trim().equals("")){
			strsql.append(" and proposereson like '%"+proposereson+"%'" );
		}
		if(!proposerdate.trim().equals("")){
			strsql.append(" and CONVERT(varchar(100), proposerdate, 112)='"+proposerdate+"'" );
		}
		strsql.append(" ");
			try {
				mainRecordList = bll.getAduitMainRecordList(strsql.toString());//查询
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	 //查询按钮事件
	 @Command("findAuditCore")
	 @NotifyChange("mainRecordList")
	 public void findAuditCore(){
	 		serch();
	 }
	
	public MaintenanceRecordModel getMm() {
		return mm;
	}
	public void setMm(MaintenanceRecordModel mm) {
		this.mm = mm;
	}
	public List<MaintenanceRecordModel> getMainRecordList() {
		return mainRecordList;
	}
	public void setMainRecordList(List<MaintenanceRecordModel> mainRecordList) {
		this.mainRecordList = mainRecordList;
	}
	public List<String> getListPro() {
		return listPro;
	}
	public void setListPro(List<String> listPro) {
		this.listPro = listPro;
	}
	public List<String> getListAdu() {
		return listAdu;
	}
	public void setListAdu(List<String> listAdu) {
		this.listAdu = listAdu;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getAudittime() {
		return audittime;
	}
	public void setAudittime(String audittime) {
		this.audittime = audittime;
	}
	public String getProposereson() {
		return proposereson;
	}
	public void setProposereson(String proposereson) {
		this.proposereson = proposereson;
	}
	public String getProposerdate() {
		return proposerdate;
	}
	public void setProposerdate(String proposerdate) {
		this.proposerdate = proposerdate;
	}
	public String getProposer() {
		return proposer;
	}
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	 
	 
}
