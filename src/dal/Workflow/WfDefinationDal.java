package dal.Workflow;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;
import Conn.dbconn;
import Model.WfClassModel;
import Model.WfDefinationModel;

public class WfDefinationDal {

	/**
	 * 读取WfDefination列表
	 * 
	 * @param i
	 * @return list
	 * @throws SQLException
	 */
	public List<WfDefinationModel> getWfDefinationModelsById(Integer i)
			throws SQLException {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		dbconn db = new dbconn();
		String sql = "select *,case wfde_state when 1 then '生效' else '取消' end wfde_stateName from WfDefination where wfde_id=?";
		try {
			list = db.find(sql, WfDefinationModel.class, dbconn.parseSmap(
					WfDefinationModel.class, "wfde_id", "wfde_wfcl_id",
					"wfde_code", "wfde_name", "wfde_remark", "wfde_addname",
					"wfde_addtime", "wfde_updatename", "wfde_updatetime",
					"wfde_state","wfde_stateName"), i);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 读取任务名称
	 * 
	 * @param sql
	 * @param objects
	 * @return list
	 * @throws SQLException

	 */
	public List<WfDefinationModel> getName(String sql, Object... objects)
			throws SQLException {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		dbconn db = new dbconn();

		try {
			list = db.find(sql, WfDefinationModel.class,
					dbconn.parseSmap(WfDefinationModel.class, "wfde_name"),
					objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 读取任务标识码
	 * 
	 * @param sql
	 * @param objects
	 * @return list
	 * @throws SQLException
	 */
	public List<WfDefinationModel> getCode(String sql, Object... objects)
			throws SQLException {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		dbconn db = new dbconn();

		try {
			list = db.find(sql, WfDefinationModel.class,
					dbconn.parseSmap(WfDefinationModel.class, "wfde_code"),
					objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 读取添加人列表
	 * 
	 * @param sql
	 * @param objects
	 * @return list
	 * @throws SQLException
	 */
	public List<WfDefinationModel> getAddName(String sql, Object... objects)
			throws SQLException {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		dbconn db = new dbconn();

		try {
			list = db.find(sql, WfDefinationModel.class,
					dbconn.parseSmap(WfDefinationModel.class, "wfde_addname"),
					objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 读取WfDefination列表
	 * 
	 * @param sql
	 * @param objects
	 * @return list 单表
	 * @throws SQLException
	 */
	public List<WfDefinationModel> getDefinationModelsBySQL2(String sql,
			Object... objects) throws SQLException {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		dbconn db = new dbconn();

		try {
			list = db.find(sql, WfDefinationModel.class, dbconn.parseSmap(
					WfDefinationModel.class, "wfde_id", "wfde_wfcl_id",
					"wfde_code", "wfde_name", "wfde_remark", "wfde_addname",
					"wfde_addtime", "wfde_updatename", "wfde_updatetime",
					"wfde_state","wfde_stateName"), objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 读取WfDefination和WfClass列表
	 * 
	 * @param sql
	 * @param objects
	 * @return list 两个表联合查询
	 * @throws SQLException
	 */
	public List<WfDefinationModel> getDefinationModelsBySQL(String sql,
			Object... objects) throws SQLException {
		List<WfDefinationModel> list = new ListModelList<WfDefinationModel>();
		dbconn db = new dbconn();

		try {
			list = db.find(sql, WfDefinationModel.class, dbconn.parseSmap(
					WfDefinationModel.class, "wfde_id", "wfde_wfcl_id",
					"wfde_code", "wfde_name", "wfde_remark", "wfde_addname",
					"wfde_addtime", "wfde_updatename", "wfde_updatetime",
					"wfde_state", "wfcl_name","wfde_stateName"), objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 新增任务
	 * 
	 * @param wfd
	 * @return
	 */
	public Integer insertWfDefination(WfDefinationModel wfd) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call WfDefinationAdd_p_py(?,?,?,?,?,?)}",
					Types.INTEGER, wfd.getWfde_wfcl_id(), wfd.getWfde_code(),
					wfd.getWfde_name(), wfd.getWfde_remark(),
					wfd.getWfde_addname(), 0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}
	
	/**
	 * 修改任务
	 * 
	 * @param wfd
	 * @return
	 */
	public Integer UpdateWfDefination(WfDefinationModel wfd) {
		Integer i = 0;
		dbconn db = new dbconn();
		
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call WfDefinationMod_p_py(?,?,?,?,?,?,?,?)}",
					Types.INTEGER,wfd.getWfde_id(), wfd.getWfde_wfcl_id(),wfd.getWfde_name(), wfd.getWfde_code(),
					 wfd.getWfde_remark(),
					wfd.getWfde_updatename(),wfd.getWfde_state(), 0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}
	
	
}
