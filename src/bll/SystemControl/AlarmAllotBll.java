package bll.SystemControl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.SystemControl.AlarmClassDal;
import dal.SystemControl.AlarmInfoDal;
import dal.SystemControl.AlarmMainDal;
import dal.SystemControl.UserListDal;

import Model.AlarmClassModel;
import Model.AlarmInfoModel;
import Model.AlarmLoginModel;
import Model.AlarmRuleModel;
import Model.DepartmentListModel;
import Model.loginroleModel;

public class AlarmAllotBll {

	/**
	 * @Title: getAlarmClassList
	 * @Description: TODO(查询预警项目类型)
	 * @param id
	 * @return
	 * @return List<AlarmClassModel> 返回类型
	 * @throws
	 */
	public List<AlarmClassModel> getAlarmClassList() {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		AlarmClassDal dal = new AlarmClassDal();
		String sql = "select * from AlarmClass where alcl_state=1 order by alcl_name";
		try {
			list = dal.getAlarmClass(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取类型列表(可全选)
	public List<AlarmClassModel> getAlarmClassAllList() {
		List<AlarmClassModel> list = new ListModelList<AlarmClassModel>();
		AlarmClassDal dal = new AlarmClassDal();
		String sql = "select 0 alcl_id,'全部' alcl_name,0 sort union all select alcl_id,alcl_name,1 from AlarmClass where alcl_state=1 order by sort,alcl_name";
		try {
			list = dal.getAlarmClass(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getAlarmInfoList
	 * @Description: TODO(查询预警项目)
	 * @param id
	 * @return
	 * @return List<AlarmInfoModel> 返回类型
	 * @throws
	 */
	public List<AlarmInfoModel> getAlarmInfoList(Integer id, String name) {
		List<AlarmInfoModel> list = new ListModelList<AlarmInfoModel>();
		AlarmInfoDal dal = new AlarmInfoDal();
		String sql = "select * from AlarmInfo where alin_state=1 and alin_alcl_id like ? and (alin_name like ? or dbo.GetPYM(alin_name,0) like ?) order by alin_alcl_id,alin_name";
		String d_i = id == 0 ? "%" : id.toString() + "%";
		String d_n = name == "" ? "%" : "%" + name + "%";

		try {
			list = dal.getAlarmInfo(sql, d_i, d_n, d_n);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @Title: getDepartmentList
	 * @Description: TODO(查询部门列表)
	 * @param id
	 * @return
	 * @return List<DepartmentListModel> 返回类型
	 * @throws
	 */
	public List<DepartmentListModel> getDepartmentList() {
		List<DepartmentListModel> list = new ListModelList<DepartmentListModel>();
		UserListDal dal = new UserListDal();
		try {
			list = dal.getdepAlllist();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @Title: getloginroleListById
	 * @Description: TODO(通过部门ID查询用户角色,ID为0时查询所有用户角色)
	 * @param id
	 * @return
	 * @return List<loginroleModel> 返回类型
	 * @throws
	 */
	public List<loginroleModel> getloginroleListById(Integer id) {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		UserListDal dal = new UserListDal();
		try {
			list = dal.getloginroleByDeptId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getloginroleListByName
	 * @Description: TODO(查询预警明细列表)
	 * @param id
	 * @return
	 * @return List<loginroleModel> 返回类型
	 * @throws
	 */
	public List<AlarmLoginModel> getloginroleListByPara(Integer iType,
			Integer iState, String rolId, String depId, String alclId,
			String alinId, String rolName, String alinName) {
		List<AlarmLoginModel> list = new ListModelList<>();
		AlarmMainDal dal = new AlarmMainDal();
		rolId = rolId == "0" ? "" : rolId == "" ? "%" : rolId;
		depId = depId == "" ? "%" : depId;
		alclId = alclId == "" ? "%" : alclId;
		alinId = alinId == "0" ? "" : alinId == "" ? "%" : alinId;
		rolName = rolName == "" ? "%" : "%" + rolName + "%";
		alinName = alinName == "" ? "%" : alinName + "%";

		String disName = "*";
		String OrderName = "";
		if (iType == 1) {
			disName = "distinct rol_id,rol_name,dep_id,dep_name";
			OrderName = " order by dep_name,rol_name";
		} else if (iType == 2) {
			disName = "distinct alin_id,alcl_id,alcl_name,alin_name";
			OrderName = " order by alcl_name,alin_name";
		}
		String sql = "select " + disName;
		sql += " from View_Alarm_Login where alin_state=1 and log_inure=1"
				+ " and alli_state =?" + " and rol_id like ?"
				+ " and dep_id like ?" + " and alcl_id like ?"
				+ " and alin_id like ?" + " and rol_name like ?"
				+ " and alin_name like ?" + OrderName;

		try {
			list = dal.getAlarmInfoLoginList(sql, iState, rolId, depId, alclId,
					alinId, rolName, alinName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getloginroleListByName
	 * @Description: TODO(分配报价单查询列表)
	 * @param id
	 * @return
	 * @return List<loginroleModel> 返回类型
	 * @throws
	 */
	public List<AlarmRuleModel> getAlarmRule(Integer iType, Integer iState,
			String rolId, String depId, String alclId, String alinId,
			String rolName, String alinName) {
		List<AlarmRuleModel> list = new ListModelList<>();
		AlarmMainDal dal = new AlarmMainDal();
		rolId = rolId == "0" ? "" : rolId == "" ? "%" : rolId;
		depId = depId == "" ? "%" : depId;
		alclId = alclId == "" ? "%" : alclId;
		alinId = alinId == "0" ? "" : alinId == "" ? "%" : alinId;
		rolName = rolName == "" ? "%" : "%" + rolName + "%";
		alinName = alinName == "" ? "%" : alinName + "%";

		String disName = "*";
		String OrderName = "";
		if (iType == 1) {
			disName = "distinct alin_id,rol_id,rol_name,dep_id,dep_name";
			OrderName = " order by dep_name,rol_name";
		} else if (iType == 2) {
			disName = "distinct alin_id,alcl_id,alcl_name,alin_name";
			OrderName = " order by alcl_name,alin_name";
		}
		String sql = "select " + disName;
		sql += " from View_AlarmRule where alin_state=1 and log_inure=1"
				+ " and arul_state =?" + " and rol_id like ?"
				+ " and dep_id like ?" + " and alcl_id like ?"
				+ " and alin_id like ?" + " and rol_name like ?"
				+ " and alin_name like ?" + OrderName;

		try {
			list = dal.getAlarmAllotList(sql, iState, rolId, depId, alclId,
					alinId, rolName, alinName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 处理更新参数
	public Integer UpdateAlarmRule(List<AlarmRuleModel> list1,
			List<AlarmRuleModel> list2) {
		Integer row = 0;

		AlarmMainDal dal = new AlarmMainDal();
		for (AlarmRuleModel m : list1) {
			row += dal.updateAlarmRule(m.getAlin_id(), m.getRol_id(), 0);
		}
		for (AlarmRuleModel m : list2) {
			row += dal.updateAlarmRule(m.getAlin_id(), m.getRol_id(), 1);
		}

		dal.UpdateAlarmListNum(0, 0);
		return row;
	}

	public Integer updateAlarm(Integer userid) {
		Integer row = 0;
		AlarmMainDal dal = new AlarmMainDal();
		row = dal.UpdateAlarmListNum(0, userid);

		return row;
	}
}
