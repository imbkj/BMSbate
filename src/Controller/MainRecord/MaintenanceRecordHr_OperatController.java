package Controller.MainRecord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Model.MaintenanceRecordModel;
import bll.MainRecord.MaintenanceRecordBll;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10
 */
public class MaintenanceRecordHr_OperatController {
	 private MaintenanceRecordModel mm = new MaintenanceRecordModel();
	 private List<MaintenanceRecordModel> mainRecordList =new ArrayList<MaintenanceRecordModel>();
	 private MaintenanceRecordBll bll= new MaintenanceRecordBll();
	 private List<String> list=null;
	 private String proposer="";
	 private String proposereson="";
	 private String proposerdate="";
	 private String status="";
	 private String isbackup="";

	public MaintenanceRecordHr_OperatController() {
		    serch();
		    list=bll.getDayList();//获月
	}
	//按条件查询维护记录
	public void serch(){
		StringBuilder strsql= new StringBuilder();
		if(!proposer.trim().equals("")){
			strsql.append(" and proposer like '%"+proposer+"%'" );
		}
		if(!proposereson.trim().equals("")){
			strsql.append(" and proposereson like '%"+proposereson+"%'" );
		}
		if(!proposerdate.trim().equals("")){
			strsql.append(" and CONVERT(varchar(100), proposerdate, 112)='"+proposerdate+"'" );
		}
		if(!status.trim().equals("")&&status.trim().equals("已审核")){
			strsql.append(" and status=1 " );
		}
		if(!status.trim().equals("")&&status.trim().equals("未审核")){
			strsql.append(" and status=0 " );
		}
		if(!isbackup.trim().equals("")&&isbackup.trim().equals("已备份")){
			strsql.append(" and isbackup=1 " );
		}
		if(!isbackup.trim().equals("")&&isbackup.trim().equals("未备份")){
			strsql.append(" and isbackup=0 " );
		}
		strsql.append(" ");
		try {
			mainRecordList = bll.getMainRecordList(strsql.toString());//查询
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//查询按钮事件
	@Command("findCore")
	@NotifyChange("mainRecordList")
	public void findCore(){
		serch();
	}
	//修改审核状态
	@Command("audit")
	@NotifyChange("mainRecordList")
	public void audit(@BindingParam("m") MaintenanceRecordModel m){
		if(m.getStatus()==0){
			  m.setStatus(1);
		}else{
			 m.setStatus(0);
		}
		int i=bll.aduitMainRecord(m);
		if(i>0){
			
		}else{
			Messagebox.show("操作失败！", "提示", Messagebox.OK,Messagebox.ERROR);
		}
	}
	//修改备份状态
	@Command("backup")
	@NotifyChange("mainRecordList")
	public void backup(@BindingParam("m") MaintenanceRecordModel m){
		if(m.getIsbackup()==0){
			  m.setIsbackup(1);
		}else{
			 m.setIsbackup(0);
		}
		int i=bll.backUpMainRecord(m);
		if(i>0){
			
		}else{
			Messagebox.show("操作失败！", "提示", Messagebox.OK,Messagebox.ERROR);
		}
	}
	//全选当前页记录
	@Command("checkAll")
	public void checkAll(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox allCk) {
		//int num = gd.getPageSize();
		//if (mainRecordList.size() < num) {
			  int num = mainRecordList.size();
		//}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null && gd.getCell(i, 0) instanceof Cell) {
				Cell cell = (Cell) gd.getCell(i, 0);//java.lang.ClassCastException: org.zkoss.zul.Label cannot be cast to org.zkoss.zul.Cell
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					ck.setChecked(allCk.isChecked());
				}
			}
		}
	}
	//选着的记录
	@Command("checkCb")
	public void checkCb(@BindingParam("cel") Cell cell) {
		Row row = (Row) cell.getParent();
		if (row.getChildren() != null && row.getChildren().size() > 0) {
			Cell rcell = (Cell) row.getChildren().get(0);
			if (rcell.getChildren() != null && rcell.getChildren().size() > 1) {
				Checkbox cb = (Checkbox) rcell.getChildren().get(1);
				if (cb.isChecked()) {
					cb.setChecked(false);
				} else {
					cb.setChecked(true);
				}
			}
		}
	}
	
	//批量审核
	@Command("batchReview")
	@NotifyChange("mainRecordList")
	public void batchReview(@BindingParam("gd") Grid gd){
		List<MaintenanceRecordModel> checkList= new ArrayList<MaintenanceRecordModel>();
		//int num = gd.getPageSize();
		//if(mainRecordList.size()<num){
			int num=mainRecordList.size();
		//}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null && gd.getCell(i, 0) instanceof Cell) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					if (ck.isChecked()) {
						MaintenanceRecordModel md = ck.getValue();
						checkList.add(md);
					}
				}
			}
		}
		if(checkList.size()>0){
			 for(int j=0;j<checkList.size();j++){
				 MaintenanceRecordModel mr= checkList.get(j);
				 if(mr.getStatus()==0){
					  mr.setStatus(1);
				 }else{
					 mr.setStatus(0);
				 }
				 int i=bll.aduitMainRecord(mr);//修改审核状态
				 if(i>0){
					
				 }else{
					Messagebox.show("操作失败！", "提示", Messagebox.OK,Messagebox.ERROR);
				 }
			 }
		}else{
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//批量备份
	@Command("batchBackup")
	@NotifyChange("mainRecordList")
	public void batchBackup(@BindingParam("gd") Grid gd){
		List<MaintenanceRecordModel> checkList= new ArrayList<MaintenanceRecordModel>();
		//int num = gd.getPageSize();
		//if(mainRecordList.size()<num){
			int num=mainRecordList.size();
		//}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null && gd.getCell(i, 0) instanceof Cell) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					if (ck.isChecked()) {
						MaintenanceRecordModel md = ck.getValue();
						checkList.add(md);
					}
				}
			}
		}
		if(checkList.size()>0){
			 for(int j=0;j<checkList.size();j++){
				 MaintenanceRecordModel mr= checkList.get(j);
				 if(mr.getIsbackup()==0){
					  mr.setIsbackup(1);
				}else{
					 mr.setIsbackup(0);
				}
				int i=bll.backUpMainRecord(mr);
				if(i>0){
					
				}else{
					Messagebox.show("操作失败！", "提示", Messagebox.OK,Messagebox.ERROR);
				}
			 }
		}else{
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	//添加、修改维护结果
	@Command("maintain")
	public void maintain(@ContextParam(ContextType.VIEW) Component view,@BindingParam("m") MaintenanceRecordModel m){
    	Map<String, MaintenanceRecordModel> map = new HashMap<String, MaintenanceRecordModel>();
		map.put("model", m);
		Window window = (Window) Executions.createComponents(
				"/MainRecord/MaintenanceRecordHr_Update.zul",view, map);
		window.doModal();
    }
    //子页面刷新
    @Command("refreshAll")
	@NotifyChange("mainRecordList")
    public void refreshParentWindow(){
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
	public String getProposerdate() {
		return proposerdate;
	}
	public void setProposerdate(String proposerdate) {
		this.proposerdate = proposerdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsbackup() {
		return isbackup;
	}
	public void setIsbackup(String isbackup) {
		this.isbackup = isbackup;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	
	 
}
