package impl;

import dal.SystemControl.AlarmMainDal;
import service.SysAlarmListService;

public class SysAlarmListImpl implements SysAlarmListService {

	
	/*
	 * @see service.SysAlarmListService#UpdateList(java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer UpdateList(Integer itemId, Integer logId) {
		Integer i = 0;
		AlarmMainDal dal = new AlarmMainDal();
		try {
			i = dal.UpdateAlarmListNum(itemId, logId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/*
	 * @see service.SysAlarmListService#AddList()
	 */
	public Integer AddList(Integer logId, String userName) {
		Integer i = 0;
		AlarmMainDal dal = new AlarmMainDal();
		try {
			i = dal.AlarmListAdd(logId, userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	@Override
	public Integer UpdateList(Integer itemId, Integer rolId, Integer logId) {
		// TODO Auto-generated method stub
		return null;
	}

}
