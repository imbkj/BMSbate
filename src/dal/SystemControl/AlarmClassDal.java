package dal.SystemControl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.AlarmClassModel;
import Model.AlarmLoginModel;

public class AlarmClassDal {
	
	/** 
	* @Title: getAlarmClass 
	* @Description: TODO(通用查询预警类型数据) 
	* @param sql
	* @param objects
	* @return
	* @throws SQLException
	* @return List<AlarmClassModel>    返回类型 
	* @throws 
	*/
	
	public List<AlarmClassModel> getAlarmClass(String sql,Object... objects)
			throws SQLException {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		dbconn db = new dbconn();
		
		try {
			list = db.find(sql, AlarmClassModel.class,
					dbconn.parseSmap(AlarmClassModel.class), objects);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}
	
	
	
	/** 
	* @Title: getAlarmClassByName 
	* @Description: TODO(查询判断生效预警类型) 
	* @param name
	* @return
	* @throws SQLException
	* @return List<AlarmClassModel>    返回类型 
	* @throws 
	*/
	public List<AlarmClassModel> getAlarmClassByName(String name)
			throws SQLException {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		dbconn db = new dbconn();
		String sql = "select distinct alcl_name from AlarmClass where alcl_state=1 and alcl_name=?";

		try {
			list = db.find(sql, AlarmClassModel.class,
					dbconn.parseSmap(AlarmClassModel.class, "alcl_name"), name);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}
	
	
	
	/** 
	* @Title: getAlarmClassNameList 
	* @Description: TODO(获取唯一预警类型列表) 
	* @return
	* @throws SQLException
	* @return List<AlarmClassModel>    返回类型 
	* @throws 
	*/
	public List<AlarmClassModel> getAlarmClassNameList()
			throws SQLException {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		dbconn db = new dbconn();
		String sql = "select distinct alcl_name from AlarmClass";

		try {
			list = db.find(sql, AlarmClassModel.class,
					dbconn.parseSmap(AlarmClassModel.class, "alcl_name"));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}
	
	/** 
	* @Title: getAlarmClassAddNameList 
	* @Description: TODO(获取预警类型添加人列表) 
	* @return
	* @throws SQLException
	* @return List<AlarmClassModel>    返回类型 
	* @throws 
	*/
	public List<AlarmClassModel> getAlarmClassAddNameList()
			throws SQLException {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		dbconn db = new dbconn();
		String sql = "select '全部' alcl_addname,0 sort union all select distinct alcl_addname,1  from AlarmClass order by sort,alcl_addname";

		try {
			list = db.find(sql, AlarmClassModel.class,
					dbconn.parseSmap(AlarmClassModel.class, "alcl_addname"));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}
	
	/** 
	* @Title: getAlarmClassList 
	* @Description: TODO(获取预警类型信息列表) 
	* @param sql
	* @param objects
	* @return
	* @throws SQLException
	* @return List<AlarmClassModel>    返回类型 
	* @throws 
	*/
	public List<AlarmClassModel> getAlarmClassList(String sql,Object...objects)
			throws SQLException {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		dbconn db = new dbconn();
		
		try {
			list = db.find(sql, AlarmClassModel.class,
					dbconn.parseSmap(AlarmClassModel.class, "alcl_id","alcl_name","alcl_state","alcl_addname","alcl_addtime","alcl_modname","alcl_modtime","alcl_stateName"),objects);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}


	/** 
	* @Title: getAlarmLoginModelList 
	* @Description: TODO(获取预警明细信息列表) 
	* @return
	* @throws SQLException
	* @return List<AlarmLoginModel>    返回类型 
	* @throws 
	*/
	public List<AlarmLoginModel> getAlarmLoginModelList() throws SQLException {
		List<AlarmLoginModel> list = new ListModelList<AlarmLoginModel>();
		dbconn db = new dbconn();
		String sql = "select * from View_Alarm_Login";

		try {
			list = db.find(sql, AlarmLoginModel.class, dbconn.parseSmap(
					AlarmLoginModel.class, "alcl_id", "alcl_name", "alin_id",
					"alin_name", "alin_content", "alin_url", "alin_warning",
					"alli_id", "alli_num", "log_id", "log_name", "alcl_state",
					"alin_state", "alli_state", "log_inure"));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}
	
	/** 
	* @Title: AddAlarmClass 
	* @Description: TODO(新增预警类型) 
	* @param am
	* @return
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer AddAlarmClass(AlarmClassModel am){
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call AlarmClassAdd_P_py(?,?,?)}",
					Types.INTEGER,am.getAlcl_name(),am.getAlcl_addname(), 0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
		
	}
	
	/** 
	* @Title: ModAlarmClass 
	* @Description: TODO(修改预警类型) 
	* @param am
	* @return
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer ModAlarmClass(AlarmClassModel am){
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call AlarmClassMod_P_py(?,?,?,?,?)}",
					Types.INTEGER,am.getAlcl_id(),am.getAlcl_name(),am.getAlcl_state(),am.getAlcl_modname(), 0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
		
	}
}
