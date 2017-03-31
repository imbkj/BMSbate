package dal.SystemControl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.AlarmClassModel;
import Model.AlarmInfoModel;

public class AlarmInfoDal {

	/**
	 * @Title: getAlarmInfo
	 * @Description: TODO(查询AlarmInfo数据)
	 * @param sql
	 * @param objects
	 * @return
	 * @return List<AlarmInfoModel> 返回类型
	 * @throws
	 */
	public List<AlarmInfoModel> getAlarmInfo(String sql, Object... objects) {
		List<AlarmInfoModel> list = new ListModelList<AlarmInfoModel>();
		dbconn db = new dbconn();
		try {
			list = db.find(sql, AlarmInfoModel.class,
					dbconn.parseSmap(AlarmInfoModel.class), objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: AddAlarmInfo
	 * @Description: TODO(新增预警项目)
	 * @param am
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer AddAlarmInfo(AlarmInfoModel am) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {

			i = Integer.valueOf(db.callWithReturn(
					"{?=call AlarmInfoAdd_P_py(?,?,?,?,?,?,?,?)}",
					Types.INTEGER, am.getAlin_alcl_id(), am.getAlin_name(),
					am.getAlin_content(), am.getAlin_url(), am.getAlin_sql(),
					am.getAlin_warning(), am.getAlin_addname(), 0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;

	}
	
	public Integer addRule(){
		Integer i =0;
		dbconn db = new dbconn();
		String sql="insert into AlarmRule" +
				" select distinct alin_id,null,rol_id,null,GETDATE(),'系统',null,null,0" +
				" from AlarmInfo a inner join View_loginrole b on 1=1" +
				" where not exists(select 1 from AlarmRule where arul_alin_id=a.alin_id and arul_rolId=b.rol_id)";
		try {
			i=db.insertAndReturnKey(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: ModAlarmInfo
	 * @Description: TODO(修改预警项目)
	 * @param am
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer ModAlarmInfo(AlarmInfoModel am) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call AlarmInfoMod_P_py(?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, am.getAlin_id(), am.getAlin_alcl_id(),
					am.getAlin_name(), am.getAlin_content(), am.getAlin_url(),
					am.getAlin_sql(), am.getAlin_state(), am.getAlin_warning(),
					am.getAlin_modname(), 0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;

	}

}
