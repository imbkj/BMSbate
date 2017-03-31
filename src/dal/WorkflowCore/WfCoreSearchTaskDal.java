/**
 * @Classname WfCoreSearchTaskDal
 * @ClassInfo 查询任务单数据库访问类
 * @author 李文洁
 * @Date 2013-10-24
 */
package dal.WorkflowCore;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.WfTaskListInfoModel;
import Util.MapStringChange;

public class WfCoreSearchTaskDal {
	private static dbconn conn = new dbconn();

	public List<WfTaskListInfoModel> SearchCanOpTaskByUser(String username) {

		List<WfTaskListInfoModel> taskList = new ArrayList<WfTaskListInfoModel>();
		/*
		 * try {
		 * 
		 * taskList = conn.call(WfTaskListInfoModel.class,
		 * "{call WfCoreCanOpTaskByUser_P_lwj(?)}", null, username); } catch
		 * (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } if (taskList.size() > 0) { for
		 * (WfTaskListInfoModel m : taskList) {
		 * m.setSearchConMap(MapStringChange.StringToMap(m
		 * .getTali_searchCon())); } }
		 */
		WfTaskListInfoModel m = null;

		try {
			ResultSet rs = conn.GRS("exec WfCoreCanOpTaskByUser_P_lwj "
					+ username);
			
			while (rs.next()) {
				m = new WfTaskListInfoModel();
				m.setTacl_id(rs.getInt("tacl_id"));
				m.setTacl_name(rs.getString("tacl_name"));
				m.setTali_id(rs.getInt("tali_id"));
				m.setTali_name(rs.getString("tali_name"));
				m.setTali_addname(rs.getString("tali_addname"));
				m.setTali_addtime(rs.getString("tali_addtime"));
				m.setTapr_id(rs.getInt("tapr_id"));
				m.setSearchConMap(MapStringChange.StringToMap(rs
						.getString("tali_searchCon")));
				m.setTali_urgentState(rs.getInt("tali_urgentState"));
				m.setTapr_urgentState(rs.getInt("tapr_urgentState"));
				m.setTapr_lastState(rs.getInt("tapr_lastState"));
				taskList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return taskList;
	}
	
	public List<WfTaskListInfoModel> SearchCanOpTaskByUser(String username,Integer taclId) {

		List<WfTaskListInfoModel> taskList = new ArrayList<WfTaskListInfoModel>();

		WfTaskListInfoModel m = null;
		System.out.println(taclId);
		try {
			ResultSet rs = conn.GRS("exec WfCoreCanOpTaskByUserAndTaclId_P_lwj '"
					+ username+"',"+taclId);
			
			while (rs.next()) {
				m = new WfTaskListInfoModel();
				m.setTacl_id(rs.getInt("tacl_id"));
				m.setTacl_name(rs.getString("tacl_name"));
				m.setTali_id(rs.getInt("tali_id"));
				m.setTali_name(rs.getString("tali_name"));
				m.setTali_addname(rs.getString("tali_addname"));
				m.setTali_addtime(rs.getString("tali_addtime"));
				m.setTapr_id(rs.getInt("tapr_id"));
				m.setSearchConMap(MapStringChange.StringToMap(rs
						.getString("tali_searchCon")));
				m.setTali_urgentState(rs.getInt("tali_urgentState"));
				m.setTapr_urgentState(rs.getInt("tapr_urgentState"));
				m.setTapr_lastState(rs.getInt("tapr_lastState"));
				taskList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return taskList;
	}
}