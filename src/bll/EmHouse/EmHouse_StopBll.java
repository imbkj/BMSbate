package bll.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.Embase.Embasedal;

import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.loginroleModel;
import Util.ConstantsUtil;
import Util.DateStringChange;
import Util.DateUtil;
import Util.Log4jInit;
import Util.MonthListUtil;
import Util.UserInfo;

public class EmHouse_StopBll {

	// 刷新在册数据
	public void updateData(Integer gid) {
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		dal.updateData(gid);
	}

	// 获取员工基本信息
	public List<EmbaseModel> getembaseInfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();

		list = dal.getEmBaseById(id);

		return list;
	}

	// 获取所属月份列表
	public List<String> getOwnmonthList(Integer gid) {
		List<String> list = new ArrayList<>();
		MonthListUtil mu = new MonthListUtil();
		String[] s_ownmonth = new String[3];
		EmHouseSetBll bll = new EmHouseSetBll();
		Integer ownmonth = bll.nowmonth2(gid);
		s_ownmonth = mu.getMonthList(true, ownmonth.toString(), "f", 3);
		for (String s : s_ownmonth) {
			list.add(s);
		}
		return list;
	}

	// 停交转换所属月份到上月底
	// 201601->2015-12-31
	public List<String> getStopMonthList(List<String> list) {
		List<String> l = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String s : list) {
			String s1 = DateStringChange.ownmonthAdd(s, -1);
			Date d1 = DateStringChange.StringtoDate(s1, "yyyyMM");
			Date d2 = DateUtil.getLastDay(d1);
			s = sdf.format(d2);
			l.add(s);
		}
		return l;
	}

	// 获取公积金在册信息
	public List<EmHouseUpdateModel> getListById(Integer cid, Integer gid) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		try {
			list = dal.getListById(cid, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 离职获取公积金在册信息
	public List<EmHouseUpdateModel> getListById2(Integer cid, Integer gid) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		try {
			list = dal.getListById(cid, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取所属月份列表
	public List<String> getOwnmonthList() {
		List<String> list = new ArrayList<>();
		String[] s_ownmonth = new String[3];
		String nowmonth = ConstantsUtil.ownmonth;
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		MonthListUtil mu = new MonthListUtil();

		String ownmonth = dal.getOwnmonth();

		if (ownmonth.equals(nowmonth)) {
			s_ownmonth = MonthListUtil.getMonthList(true, ownmonth, "f", 3);
		} else {
			s_ownmonth = MonthListUtil.getMonthList(true,
					mu.getNextMonth(ownmonth), "f", 3);
		}
		for (int i = 0; i < s_ownmonth.length; i++) {
			list.add(s_ownmonth[i]);
		}

		return list;
	}

	// 停交公积金
	public Integer stopEmhouse(EmHouseChangeModel ecm) {
		Integer i = 0;

		WfBusinessService wfbs = new EmHouse_StopImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		Object[] obj = { "新增停交", ecm };

		String[] str = wfs.AddTaskToNext(obj, "员工公积金变更", ecm.getOwnmonth()
				+ ecm.getEmhc_name() + "(" + ecm.getGid() + ")停交公积金", 36,
				ecm.getEmhc_addname(), "", ecm.getCid(), "");

		if (str[0].equals("1")) {
			i = 1;
		} else {
			Log4jInit.toLog("公积金|停交|" + ecm.getEmhc_name());
		}
		return i;
	}

}
