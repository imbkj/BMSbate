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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.MainRecord.MaintenanceRecordBll;
import Model.MaintenanceRecordModel;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10
 */
public class MaintenanceRecord_AddController{
    private MaintenanceRecordModel mm = new MaintenanceRecordModel();
    private List<MaintenanceRecordModel> mainRecordList =new ArrayList<MaintenanceRecordModel>();
	private MaintenanceRecordBll bll= new MaintenanceRecordBll();
	private String proposereson;
	private String sql;

	public MaintenanceRecord_AddController() {
		try {
			mainRecordList=bll.getNotAuditMainRecordList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//新增维护记录
    @Command("submit")
	public void submit() {
    	if(proposereson==null||proposereson.trim()==""){
    		Messagebox.show("申请原因不能为空！", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
    	}else if(sql==null||sql.trim()==""){
    		Messagebox.show("sql语句不能为空！", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
    	}
    	mm.setProposereson(proposereson);
    	mm.setSql(sql);
    	try {
			int a=bll.addMaintenanceRecord(mm);
			if(a>0){
				Messagebox.show("添加成功！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				mainRecordList.clear();//添加成功后先清空
				mainRecordList.addAll(bll.getNotAuditMainRecordList());//再重新查询赋值给mainRecordList，就相当于刷洗grid
				return;
			}else{
				Messagebox.show("添加失败！", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    //删除维护记录
    @Command("delete")
    public void delete(@BindingParam("model") MaintenanceRecordModel model) throws SQLException{
       if(Messagebox.show("确认要删除该维护记录吗？", "操作提示",Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES){
    	   int a=bll.deleteMainRecord(model.getId());                      //根据id删除记录
    	   if(a>0){
    		  Messagebox.show("删除成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
    	   }else{
    		  Messagebox.show("删除失败！", "提示", Messagebox.OK,
					Messagebox.ERROR);
    	   }
    	   mainRecordList.clear();//添加成功后先清空
		   mainRecordList.addAll(bll.getNotAuditMainRecordList());//再重新查询赋值给mainRecordList，就相当于刷洗grid
       }
    }
    //修改维护记录信息
    @Command("update")
    public void update(@ContextParam(ContextType.VIEW) Component view,@BindingParam("model") MaintenanceRecordModel model){
    	Map<String, MaintenanceRecordModel> map = new HashMap<String, MaintenanceRecordModel>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"/MainRecord/MaintenanceRecord_Update.zul",view, map);
		window.doModal();
    }
    //子页面刷新
    @Command("refreshAll")
	@NotifyChange("mainRecordList")
    public void refreshParentWindow(){
    	try {
			mainRecordList=bll.getNotAuditMainRecordList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public String getProposereson() {
		return proposereson;
	}

	public void setProposereson(String proposereson) {
		this.proposereson = proposereson;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	
    
    
}
