package dal.SystemControl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.AlarmLoginModel;
import Model.AlarmRuleModel;

/**
 * @author ray
 * 
 */
public class AlarmMainDal {

	/**
	 * @Title: getaAlarmInfoLoginListById
	 * @Description: TODO(查询项目列表)
	 * @param id
	 * @return
	 * @return List<AlarmInfoLoginDal> 返回类型
	 * @throws
	 */
	public List<AlarmLoginModel> getAlarmInfoLoginList(String sql,
			Object... objects) throws SQLException {
		List<AlarmLoginModel> list = new ListModelList<AlarmLoginModel>();
		dbconn db = new dbconn();
		list = db.find(sql, AlarmLoginModel.class, null, objects);
		return list;
	}

	/**
	 * @Title: getAlarmAllotList
	 * @Description: TODO(分配预警项目列表)
	 * @param sql
	 * @param objects
	 * @return
	 * @throws SQLException
	 * @return List<AlarmRuleModel> 返回类型
	 * @throws
	 */
	public List<AlarmRuleModel> getAlarmAllotList(String sql, Object... objects)
			throws SQLException {
		List<AlarmRuleModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		list = db.find(sql, AlarmRuleModel.class, null, objects);
		return list;
	}

	/**
	 * @Title: UpdateAlarmList
	 * @Description: TODO(按项目分类更新预警项目分配表)
	 * @param id
	 * @param rolIdList
	 * @return
	 * @throws SQLException
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer updateAlarmRule(Integer id, Integer rolId, Integer state) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = db.updatePrepareSQL(
					"update AlarmRule set arul_state=? where arul_alin_id=? and arul_rolid=?",
					state, id, rolId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}


	/**
	 * @Title: UpdateAlarmListNum
	 * @Description: TODO(更新预警项目计数值)
	 * @param itemId
	 * @param rolId
	 * @param logId
	 * @return
	 * @throws SQLException
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer UpdateAlarmListNum(Integer itemId,Integer logId) {
		Integer i = 0;
		String s_i = itemId == 0 ? "" : itemId.toString();
		String s_l = logId == 0 ? "" : logId.toString();
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call AlarmListUpdateNum_P_py(?,?,?,?)}", Types.INTEGER,
					s_i, 0, s_l, 0).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: AlarmListAdd
	 * @Description: TODO(新增员工预警项目列表)
	 * @param logId
	 * @param userName
	 * @return
	 * @throws SQLException
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer AlarmListAdd(Integer logId, String userName)
			throws SQLException {
		Integer i = 0;

		dbconn db = new dbconn();
		i = Integer.valueOf(db.callWithReturn(
				"{?=call AlarmListAdd_P_py(?,?,?)}", Types.INTEGER, logId,
				userName, 0).toString());
		return i;
	}

}
