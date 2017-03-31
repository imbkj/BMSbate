package bll.MainRecord;

import java.sql.SQLException;
import java.util.List;

import Model.MaintenanceRecordModel;
import dal.MainRecord.MaintenanceRecordDal;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10
 * 维护记录表业务逻辑层
 */
public class MaintenanceRecordBll {
    MaintenanceRecordDal dal = new MaintenanceRecordDal();
    //添加维护表记录
    public int addMaintenanceRecord(MaintenanceRecordModel em) throws SQLException{
    	return dal.addMainRecord(em);
    }
   //查询未审核的维护记录
  	public List<MaintenanceRecordModel> getNotAuditMainRecordList() throws SQLException{
  		return dal.getNotAuditMainRecordList();
  	}
    //根据id删除未审核维护记录
  	public int deleteMainRecord(int id){
  		return dal.deleteMainRecord(id);
  	}
    //更新未审核维护记录
  	public int updateMainRecord(MaintenanceRecordModel mr){
        return dal.updateMainRecord(mr);
  	}
   //多条件查询维护记录
  	public List<MaintenanceRecordModel> getMainRecordList(String strsql) throws SQLException{
  		return dal.getMainRecordList(strsql);
  	}
  //获取维护记录申请时间
  	public List<String> getDayList(){
  		return dal.getDayList();
  	}
  	
  //修改维护记录审核状态
  	public int aduitMainRecord(MaintenanceRecordModel mr){
  		return dal.aduitMainRecord(mr);
  	}
  //修改维护记录备份状态
   public int backUpMainRecord(MaintenanceRecordModel mr){
	   return dal.backUpMainRecord(mr);
   }
  //获取维护记录审核时间
  public List<String> getAduitDayList(){
	  return dal.getAduitDayList();
  }
  //当前用户多条件查询已审核的申请的维护记录
  public List<MaintenanceRecordModel> getAduitMainRecordList(String strsql) throws SQLException{
	  return dal.getAduitMainRecordList(strsql);
  }
  //更新审核维护记录维护结果
  public int updateAduitMainRecord(MaintenanceRecordModel mr){
	  return dal.updateAduitMainRecord(mr);
  }
}
