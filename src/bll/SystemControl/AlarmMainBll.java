package bll.SystemControl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.SystemControl.AlarmMainDal;

import Model.AlarmLoginModel;

public class AlarmMainBll {
	/**
	 * @Title: getalAlarmLoginListById
	 * @Description: TODO(通过预警类型ID查询预警项目列表)
	 * @param id
	 * @return
	 * @return List<AlarmLoginModel> 返回类型
	 * @throws
	 */
	public List<AlarmLoginModel> getAlarmLoginListById(Integer id) {
		List<AlarmLoginModel> list = new ListModelList<AlarmLoginModel>();
		AlarmMainDal dal = new AlarmMainDal();
		String sql = "select * from View_Alarm_Login where alcl_state=1 and alin_state=1 and alli_state=1 and log_inure=1 and alcl_id =?";
		try {
			list = dal.getAlarmInfoLoginList(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getAlarmLoginListByPara
	 * @Description: TODO(查询预警信息)
	 * @param id
	 * @param userId
	 * @return
	 * @return List<AlarmLoginModel> 返回类型
	 * @throws
	 */
	public List<AlarmLoginModel> getAlarmLoginListByPara(Integer id,
			Integer userId) {
		List<AlarmLoginModel> list = new ListModelList<AlarmLoginModel>();
		AlarmMainDal dal = new AlarmMainDal();
		String sql = "select distinct alcl_id,alcl_name,alin_id,alin_name,alin_url,alin_warning,"
				+ "alli_num,alcl_state,alin_state,alli_state,"
				+ "isnull(alcl_sort,0)alcl_sort,isnull(alin_sort,0)alin_sort,p1,p2,p3"
				+ " from View_Alarm_Login"
				+ " where alcl_state=1 and alin_state=1 and alli_state=1"
				+ " and log_inure=1 and alcl_id =?"
				+ " and log_id =?"
				+ " order by alin_sort";
		try {
			System.out.println(sql);
			list = dal.getAlarmInfoLoginList(sql, id, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取类型列表
	public List<AlarmLoginModel> getAlarmLoginDisClassName(Integer userId) {
		List<AlarmLoginModel> list = new ListModelList<AlarmLoginModel>();
		AlarmMainDal dal = new AlarmMainDal();
		String sql = "select distinct alcl_id,alcl_name,alcl_sort"
				+ " from View_Alarm_Login"
				+ " where alcl_state=1 and alin_state=1 and alli_state=1 and log_inure=1 and log_id=?"
				+ " order by alcl_sort";
		try {
			list = dal.getAlarmInfoLoginList(sql, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
