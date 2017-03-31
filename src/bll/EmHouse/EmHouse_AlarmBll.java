package bll.EmHouse;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.LoginDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseChangeGjjDal;
import dal.EmHouse.Emhouse_BjDal;

import Model.EmHouseBJModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmHouse_AlarmBll {
	// 公积金变更数据预警列表
	public List<EmHouseChangeModel> changeList(EmHouseChangeModel m,
			Integer type, Integer userId) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		m.setEmhc_ifdeclare(type);
		m.setAlarm(true);
		list = dal.getChangeList(m, false, null, false, null,
				"emhc_single,coba_client,coba_company,emhc_name");
		return list;
	}

	// 公积金补缴变更数据预警列表
	public List<EmHouseBJModel> bjlist(EmHouseBJModel m, Integer type,
			Integer userId,Integer depId) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		m.setEmhb_ifdeclare(type);
		list = dal.getAlarmList(m,userId,depId);
		return list;
	}

	// 公积金交单变更数据预警列表
	public List<EmHouseChangeGJJModel> gjjlist(EmHouseChangeGJJModel m,
			Integer type, Integer userId,Integer depId) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		m.setEhcg_ifdeclare(type);
		list=dal.getAlarmList(m,userId,depId);
		return list;
	}

	// 系统短信获取角色列表
	public List<loginroleModel> getuserlist(String rolId) {
		List<loginroleModel> list = new ListModelList<>();
		LoginDal dal = new LoginDal();
		loginroleModel lm = new loginroleModel();
		lm.setLog_inure(1);
		lm.setRolId(rolId);
		list = dal.userInfo(lm, "log_id,log_name", true, "log_name");
		return list;
	}
}
