package dal.Workflow;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.WfClassModel;
import Model.WfDefinationModel;

public class WfClassDal {
	
	/** 
	* @Title: getWfClassModelsById 
	* @Description: TODO(获取任务类型) 
	* @param i
	* @return
	* @throws SQLException
	* @return List<WfClassModel>    返回类型 
	* @throws 
	*/
	public List<WfClassModel> getWfClassModelsById(Integer i)
			throws SQLException {
		List<WfClassModel> list = new ListModelList<WfClassModel>();
		dbconn db = new dbconn();

		String sql = "select * from WfClass where wfcl_id=?";
		try {
			list = db.find(sql, WfClassModel.class,
					dbconn.parseSmap(WfClassModel.class, "wfcl_id"), i);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}
	
	
	/** 
	* @Title: getName 
	* @Description: TODO(获取任务名称) 
	* @param sql
	* @param objects
	* @return
	* @throws SQLException
	* @return List<WfClassModel>    返回类型 
	* @throws 
	*/
	public List<WfClassModel> getName(String sql, Object... objects)
			throws SQLException {
		List<WfClassModel> list = new ListModelList<WfClassModel>();
		dbconn db = new dbconn();

		try {
			list = db.find(sql, WfClassModel.class, dbconn.parseSmap(
					WfClassModel.class, "wfcl_name"), objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	/** 
	* @Title: getClassModelsBySQL 
	* @Description: TODO(查询任务模型) 
	* @param sql
	* @param objects
	* @return
	* @throws SQLException
	* @return List<WfClassModel>    返回类型 
	* @throws 
	*/
	public List<WfClassModel> getClassModelsBySQL(String sql, Object... objects)
			throws SQLException {
		List<WfClassModel> list = new ListModelList<WfClassModel>();
		dbconn db = new dbconn();

		try {
			list = db.find(sql, WfClassModel.class, dbconn.parseSmap(
					WfClassModel.class, "wfcl_id", "wfcl_name", "wfcl_remark",
					"wfcl_addname", "wfcl_addtime", "wfcl_updatename",
					"wfcl_updatetime", "wfcl_state"), objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
