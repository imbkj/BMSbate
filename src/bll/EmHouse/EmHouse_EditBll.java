package bll.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.ListModelList;

import bll.SystemControl.SystLogInfoBll;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.CoHousingFundModel;
import Model.CoglistModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmHouseErrModel;
import Model.EmHouseSetupModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Model.SysMessageModel;
import Model.TaskProcessModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;

import dal.LoginDal;
import dal.PubCodeConversionDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseChangeGjjDal;
import dal.EmHouse.EmHouseCompanyIdDal;
import dal.EmHouse.EmHouseSetupDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.EmHouse.EmHouse_ChangeGjjDal;
import dal.EmHouse.Emhouse_BjDal;
import dal.Embase.CoglistDal;
import dal.Embase.EmbaseGdDal;
import dal.Embase.Embasedal;
import dal.SysMessage.SysMessage_EditDal;
import dal.Taskflow.TaskListDal;
import dal.Taskflow.TaskProcessViewDal;
import dal.Taskflow.Task_controlDal;

public class EmHouse_EditBll {

	/**
	 * @Title: gjjItem
	 * @Description: TODO(判断员工是否选择公积金项目)
	 * @param gid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean gjjItem(Integer gid) {
		boolean b = false;
		CoglistDal dal = new CoglistDal();
		List<CoglistModel> list = new ListModelList<>();
		list = dal.coglistGjj(gid);
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 获取合同信息
	public List<CoglistModel> gjjItemInfo(Integer gid) {

		CoglistDal dal = new CoglistDal();
		List<CoglistModel> list = new ListModelList<>();
		list = dal.coglistGjj(gid);

		return list;
	}

	// 获取短信list表
	public List<SysMessageModel> getMsgList(String tbName, String username,
			Integer userid) {
		List<SysMessageModel> list = new ListModelList<>();
		SysMessage_EditDal dal = new SysMessage_EditDal();
		list = dal.getlist(tbName, username, userid);
		return list;
	}

	// 获取收件人信息
	public List<loginroleModel> getuserlist(String rolId) {
		List<loginroleModel> list = new ListModelList<>();
		LoginDal dal = new LoginDal();
		loginroleModel lm = new loginroleModel();
		lm.setLog_inure(1);
		lm.setRolId(rolId);
		list = dal.userInfo(lm, "log_id,log_name", true, "log_name");
		return list;
	}

	// 获取所属月份列表
	public List<String> getOwnmonthList() {
		List<String> list = new ArrayList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		MonthListUtil mu = new MonthListUtil();
		Date nowDate = new Date(); // 获取当前时间
		String nowmonth = DateStringChange.DatetoSting(nowDate, "yyyyMM");
		String[] s_ownmonth = new String[3];

		String ownmonth = dal.getOwnmonth();

		if (ownmonth.equals(nowmonth)) {
			s_ownmonth = MonthListUtil.getMonthList(true, ownmonth, "f", 3);

		} else {
			s_ownmonth = MonthListUtil.getMonthList(true, ownmonth, "f", 3);
		}
		for (int i = 0; i < s_ownmonth.length; i++) {
			list.add(s_ownmonth[i]);
		}

		return list;
	}

	// 获取比例列表
	public List<EmHouseCpp> housecppList(Integer cid, Integer gid) {
		List<EmHouseCpp> list = new ListModelList<>();
		List<CoHousingFundModel> ecList = new ListModelList<>();
		List<CoglistModel> clList = new ListModelList<>();
		EmHouseCompanyIdDal dal = new EmHouseCompanyIdDal();
		CoglistDal cgdal = new CoglistDal();
		Date nowDate = new Date(); // 获取当前时间
		Integer ownmonth = Integer.valueOf(DateStringChange.DatetoSting(
				nowDate, "yyyyMM"));

		Integer single = houseSingle(gid, ownmonth);
		EmHouseCpp ecm = new EmHouseCpp();
		if (single != null) {

			if (single.equals(1)) {
				ecList = dal.getlistByCid(cid);
				if (ecList.size() > 0) {
					ecm.setCp(ecList.get(0).getCohf_cpp());
					ecm.setCpName((int) (Double.valueOf(ecList.get(0)
							.getCohf_cpp()) * 100) + "%");
					list.add(ecm);
				}
			} else {
				clList = cgdal.getCompactInfoByGid(gid, ownmonth);
				if (clList.size() > 0) {
					if (clList.get(0).getCoco_cpp().equals("浮动比例")) {
						/*
						 * for (int i = 5; i <= 20; i++) {
						 * ecm.setCp(Double.valueOf(i) / 100); ecm.setCpName(i +
						 * "%"); list.add(ecm); }
						 */
						CoHousingFund_ListDal cpdal = new CoHousingFund_ListDal();
						list = cpdal.getcpplist();
					} else {
						ecm.setCp(Double.valueOf(clList.get(0).getCoco_cpp()));
						ecm.setCpName((int) (Double.valueOf(clList.get(0)
								.getCoco_cpp()) * 100) + "%");
						list.add(ecm);
					}
				}
			}
		}

		return list;
	}

	// 判断账户
	public Integer houseSingle(Integer gid, Integer ownmonth) {
		Integer i = null;

		if (gid != null) {
			if (ownmonth == null) {
				Date nowDate = new Date(); // 获取当前时间
				String nowmonth = DateStringChange.DatetoSting(nowDate,
						"yyyyMM");
				ownmonth = Integer.valueOf(nowmonth);
			}

			EmHouseChangeDal dal = new EmHouseChangeDal();
			i = dal.emhouseSingle(gid, ownmonth);
		}

		return i;

	}

	// 查询当前月份是否有未处理特殊变更
	public boolean houseChangeSP(EmHouseUpdateModel m) {
		boolean b = false;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		List<EmHouseChangeModel> list = dal.getSpList(m.getOwnmonth(),
				m.getGid());
		if (list.size() > 0) {
			b = true;
		}
		return b;

	}

	// 查询是否有同类型未处理数据
	public boolean houseChangeSame(EmHouseChangeModel m) {
		boolean b = false;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		List<EmHouseChangeModel> list = dal.getSameList(m);
		if (list.size() > 0) {
			b = true;
		}
		return b;

	}

	// 查询是否有同类型未处理数据
	public boolean houseChangeSame2(EmHouseChangeModel m) {
		boolean b = false;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		List<EmHouseChangeModel> list = dal.getSameList(m);
		if (list.size() > 0) {
			b = true;
		}
		return b;

	}

	// 根据员工编号、合同比例获取截单日期
	public boolean getlastDay(Integer gid, Double cpp) {
		boolean b = false;
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		List<CoHousingFundModel> list = dal.tsdate(gid, cpp);
		if (list.size() > 0) {
			Calendar c = new GregorianCalendar();
			Calendar c2 = new GregorianCalendar();
			c.setTime(new Date());
			c2.setTime(new Date());
			c2.set(Calendar.DATE, list.get(0).getCohf_tsday());
			if (c2.get(Calendar.DATE) - c.get(Calendar.DATE) <= 3) {
				if (c2.get(Calendar.DAY_OF_WEEK) == 1
						|| c2.get(Calendar.DAY_OF_WEEK) == 2
						|| c2.get(Calendar.DAY_OF_WEEK) == 3) {
					b = true;
				}
			}
		}

		return b;
	}

	// 判断当月是否截单
	public boolean ifstop(Integer gid, Integer ownmonth, Double cpp) {
		boolean b = false;
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		List<CoHousingFundModel> list = dal.tsdate(gid, cpp);
		if (ownmonth.equals(Integer.valueOf(DateStringChange.getOwnmonth()))) {
			if (list.size() > 0) {
				Calendar c = new GregorianCalendar();
				Calendar c2 = new GregorianCalendar();
				c.setTime(new Date());
				c2.setTime(new Date());
				if (list.get(0).getCohf_single().equals(0)) {
					EmHouseSetupDal dal2 = new EmHouseSetupDal();
					List<EmHouseSetupModel> list2 = dal2.getList();
					c2.set(Calendar.DATE, list2.get(0).getLastday());
				} else {
					c2.set(Calendar.DATE, list.get(0).getCohf_lastday());
				}

				if (c2.get(Calendar.DATE) - c.get(Calendar.DATE) < 0) {
					b = true;
				}
			}
		} else if (ownmonth < Integer.valueOf(DateStringChange.getOwnmonth())) {
			b = true;
		}

		return b;
	}

	// 判断当月中心是否截单
	public boolean ifstopfw(Integer gid, Integer ownmonth, Double cpp) {
		boolean b = false;
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		List<CoHousingFundModel> list = dal.tsdate(gid, cpp);
		if (ownmonth.equals(Integer.valueOf(DateStringChange.getOwnmonth()))) {
			if (list.size() > 0) {
				Calendar c = new GregorianCalendar();
				Calendar c2 = new GregorianCalendar();
				c.setTime(new Date());
				c2.setTime(new Date());
				if (list.get(0).getCohf_single().equals(0)) {
					EmHouseSetupDal dal2 = new EmHouseSetupDal();
					List<EmHouseSetupModel> list2 = dal2.getList();
					c2.set(Calendar.DATE, list2.get(0).getFwday());
				} else {
					c2.set(Calendar.DATE, list.get(0).getCohf_fwday());
				}

				if (c2.get(Calendar.DATE) - c.get(Calendar.DATE) < 0) {
					b = true;
				}
			}
		} else if (ownmonth < Integer.valueOf(DateStringChange.getOwnmonth())) {
			b = true;
		}

		return b;
	}

	// 判断当月补缴是否截单
	public boolean ifbjstop(Integer gid, Integer ownmonth, Double cpp) {
		boolean b = false;
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		List<CoHousingFundModel> list = dal.tsdate(gid, cpp);
		if (ownmonth.equals(Integer.valueOf(DateStringChange.getOwnmonth()))) {
			if (list.size() > 0) {
				Calendar c = new GregorianCalendar();
				Calendar c2 = new GregorianCalendar();
				c.setTime(new Date());
				c2.setTime(new Date());
				if (list.get(0).getCohf_single().equals(0)) {
					EmHouseSetupDal dal2 = new EmHouseSetupDal();
					List<EmHouseSetupModel> list2 = dal2.getList();
					c2.set(Calendar.DATE, list2.get(0).getLastday());
				} else {
					c2.set(Calendar.DATE, list.get(0).getCohf_lastday());
				}
				if (c2.get(Calendar.DATE) - c.get(Calendar.DATE) < 0) {
					b = true;
				}
			}
		} else if (ownmonth < Integer.valueOf(DateStringChange.getOwnmonth())) {
			b = true;
		}

		return b;
	}

	// 判断当月是否禁止提交数据
	public boolean ifpower() {
		boolean b = false;
		EmHouseSetupDal dal = new EmHouseSetupDal();
		List<EmHouseSetupModel> list = dal.getList();
		if (list.size() > 0) {
			if (list.get(0).getOnair().equals(1)) {
				b = true;
			}
		}
		return b;
	}

	// 判断当月是否禁止补缴提交数据
	public boolean ifbjpower() {
		boolean b = false;
		EmHouseSetupDal dal = new EmHouseSetupDal();
		List<EmHouseSetupModel> list = dal.getList();
		if (list.size() > 0) {
			if (list.get(0).getOnairbj().equals(1)) {
				b = true;
			}
		}
		return b;
	}

	// 退回变更数据
	public Integer returnBack(EmHouseChangeModel em) {
		Integer i = 0;
		em.setEmhc_ifdeclare(3);
		em.setEmhc_ifprogress(Integer.valueOf(em.getEmhc_ifprogress()
				.toString().substring(0, 1)
				+ "1"));
		WfBusinessService wfbs = new EmHouse_EditImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		Object[] obj = { "退回数据", em };

		String[] str = null;
		if (em.getEmhc_tapr_id() != null) {
			str = wfs.ReturnToN(obj, em.getEmhc_tapr_id(), 1,
					UserInfo.getUsername());
		} else {
			str = new String[5];

			EmHouseChangeDal dal = new EmHouseChangeDal();
			Integer j = dal.mod(em, em.getEmhc_id(), null, null, null);
			str[0] = j > 0 ? "1" : "0";
		}
		if (str[0].equals("1")) {
			i = 1;
			EmHouseUpdateDal dal = new EmHouseUpdateDal();
			EmHouseUpdateModel m = new EmHouseUpdateModel();
			m.setGid(em.getGid());
			m.setEmhu_ifstop(3);
			dal.mod(m);
			// ControlData(em.getEmhc_id(), em.getOwnmonth());
			// i = 1;
		}

		return i;
	}

	// 交单变更退回(雇员服务中心退回客服)
	public Integer returnGjj(EmHouseChangeGJJModel em) {
		Integer i = 0;
		WfBusinessService wfbs = new EmHouseChangeGjjConfirmImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "退回", em };
		String[] str = null;
		if (em.getEhcg_tapr_id() != null) {
			str = wfs.ReturnToPrev(obj, em.getEhcg_tapr_id(),
					UserInfo.getUsername(), "");

		} else {
			str = new String[5];
			EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
			Integer j = dal.Mod(em, em.getEhcg_id());
			str[0] = j > 0 ? "1" : "0";
		}
		if (str[0].equals("1")) {
			i = 1;
		}

		return i;
	}

	// 获取在册列表
	public List<EmHouseUpdateModel> gethouseList(Integer gid, Integer stop) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		list = dal.houseupdateInfoByGid(gid, stop);

		return list;
	}

	// 比例基数调整获取当前在册数据
	public List<EmHouseUpdateModel> getUpdateList(Integer gid) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		EmHouseSetBll bll = new EmHouseSetBll();
		Integer ownmonth = Integer.valueOf(DateStringChange.ownmonthAdd(bll
				.nowmonth().toString(), -1));
		list = dal.updateList(gid, ownmonth);
		return list;
	}

	// 查询交单变更记录
	public List<EmHouseChangeGJJModel> getGjjList(Integer id) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();
		EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
		em.setEhcg_id(id);
		list = dal.searchList(em);
		return list;
	}

	// 查询交单变更关联数据
	public List<EmHouseChangeGJJModel> getGjjReList(Integer id) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouse_ChangeGjjDal dal = new EmHouse_ChangeGjjDal();
		EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
		em.setEhcg_tid(id);
		list = dal.searchList(em);
		return list;
	}

	// 获取联网核查失败列表
	public List<EmHouseUpdateModel> gethouseupdateAduit() {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateModel em = new EmHouseUpdateModel();
		em.setEmhu_ifstop(2);
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		list = dal.gethouselist(em);
		return list;
	}

	// 更新在册数据
	public Integer modHouseupdate(Integer id, String houseid) {
		Integer i = 0;
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		EmHouseUpdateModel em = new EmHouseUpdateModel();
		em.setEmhu_id(id);
		em.setEmhu_houseid(houseid);
		i = dal.mod(em);
		return i;
	}

	// 获取编辑变更列表
	public List<EmHouseChangeModel> getChangeList(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		list = dal.getChangeList(em, false, null, false, null,
				" emhc_addtime desc");

		return list;
	}

	// 删除变更记录
	public Integer del(Integer id) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		i = dal.del(id);
		return i;
	}

	// 获取补缴编辑变更列表
	public List<EmHouseBJModel> getbjChangeList(EmHouseBJModel em) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		list = dal.housebjList(em, false, null, false, null,
				" emhb_addtime desc", true);

		return list;
	}

	// 获取档案交单变更列表
	public List<EmHouseChangeGJJModel> getgjjChangeList(EmHouseChangeGJJModel em) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		list = dal.getlist(em);
		return list;
	}

	// 获取员工信息
	public List<EmbaseModel> getEmbase(Integer embaId) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();

		list = dal.getEmBaseById(embaId);

		return list;
	}

	// 获取员工信息
	public List<EmbaseModel> getEmbaseByGid(Integer gid) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();

		list = dal.getEmBaseByGid(gid);

		return list;
	}

	// 更新变更及在册表的单位公积金号
	public Integer modHouseId(Integer cid, String houseid, Double cpp) {
		Integer i = 0;
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		i = dal.modHouseId(cid, houseid, cpp);
		return i;

	}

	// 修改在册数据
	public Integer modUpdate(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		EmHouseUpdateModel m = new EmHouseUpdateModel();
		m.setGid(em.getGid());
		m.setEmhu_company(em.getEmhc_company());
		m.setEmhu_companyid(em.getEmhc_companyid());
		m.setEmhu_houseid(em.getEmhc_houseid());
		m.setEmhu_idcard(em.getEmhc_idcard());
		m.setEmhu_radix(em.getEmhc_radix());
		m.setEmhu_hj(em.getEmhc_hj());
		m.setEmhu_mobile(em.getEmhc_mobile());
		m.setEmhu_title(em.getEmhc_title());
		m.setEmhu_degree(em.getEmhc_degree());
		m.setEmhu_wifename(em.getEmhc_wifename());
		m.setEmhu_wifeidcard(em.getEmhc_wifeidcard());
		m.setEmhu_computerid(em.getEmhc_computerid());
		i = dal.mod(m);
		return i;
	}

	// 重新发送
	public Integer modChange(EmHouseChangeModel em) {
		Integer i = 0;
		List<TaskProcessModel> list = new ListModelList<>();

		if (em.getEmhc_tapr_id() != null) {
			TaskListDal dal3 = new TaskListDal();
			list = dal3.getlist(em.getEmhc_tapr_id());
		}

		// 有任务流程
		if (list.size() > 0) {
			// 当前步骤与操作人是否相同
			if (list.get(0).getTapr_starname()
					.equals(list.get(0).getTapr_appointcon())) {
				// 相同时更新状态
				em.setEmhc_ifdeclare(0);

				WfBusinessService wfbs = new EmHouse_EditImpl();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				Object[] obj = { "重新发送", em };
				String[] str = wfs.SkipToN(obj, em.getEmhc_tapr_id(), 4,
						UserInfo.getUsername(), "", 0, "");
				if (str[0].equals("1")) {
					Task_controlDal dal = new Task_controlDal();
					i = dal.updateAppointid(Integer.valueOf(str[2].toString()));
				}
			} else {
				// 不相同只修改信息
				Task_controlDal dal = new Task_controlDal();
				i = dal.updateAppointid(em.getEmhc_tapr_id());
				dal.resumedState(em.getEmhc_tapr_id());
				EmHouseChangeDal dal2 = new EmHouseChangeDal();
				dal2.mod(em, em.getEmhc_id(), null, null, null);
				SystLogInfoBll logBll = new SystLogInfoBll();
				logBll.addLog(null, em.getCid(), em.getGid(), "员工公积金", "重新发送",
						UserInfo.getUsername());
			}
			EmbaseGdDal dal = new EmbaseGdDal();
			dal.mod(6, em.getEmhc_id(), "");
			dal.mod(10, em.getEmhc_id(), "");
		} else {
			// 历史数据处理

			EmHouseChangeDal dal2 = new EmHouseChangeDal();
			i = dal2.mod(em, em.getEmhc_id(), null, null, null);
		}

		return i;
	}

	// 补充转移数据流程
	public void updateChangeAccount(Integer id) {
		EmHouseChangeDal dal = new EmHouseChangeDal();
		List<EmHouseChangeModel> list = dal.getInfoByTid(id);
		WfBusinessService wfbs = new EmHouse_EditImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (list.size() > 0) {
			Map<String, String> map = new HashMap();
			map.put("cid", list.get(0).getCid().toString());
			map.put("gid", list.get(0).getGid().toString());
			for (int j = 0; j < list.size(); j++) {
				Object[] o = { "补充转账户流程", list.get(j) };
				String[] str = wfs.AddTaskToNext(o, "员工公积金变更", list.get(0)
						.getOwnmonth()
						+ list.get(0).getEmhc_name()
						+ "("
						+ list.get(0).getGid() + ")转移账户", 106, UserInfo
						.getUsername(), "", list.get(0).getCid(), "", map);
				if (str[0].equals("1")) {
					list.get(j).setEmhc_id(Integer.valueOf(str[3]));
					Object[] o2 = { "跳过确认", list.get(j) };
					wfs.SkipToN(o2, Integer.valueOf(str[2]), 3,
							UserInfo.getUsername(), "", 0, "");
				}
			}
		}
	}

	// 审核数据
	public Integer aduit(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		i = dal.mod(em, em.getEmhc_id(), null, null, null);
		return i;
	}

	// 修改数据
	public Integer modInfo(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		i = dal.mod(em, em.getEmhc_id(), em.getCid(), em.getGid(), null);
		return i;
	}

	// 修改补缴数据
	public Integer modBJ(EmHouseBJModel em) {
		Integer i = 0;
		Emhouse_BjDal dal = new Emhouse_BjDal();
		if (em.getGid() != null) {
			i = dal.mod(em, em.getEmhb_id(), null, null, 1);
		}

		return i;
	}

	// 退回补缴数据
	public Integer returnBJ(EmHouseBJModel em) {
		Integer i = 0;
		Emhouse_BjDal dal = new Emhouse_BjDal();
		if (em.getGid() != null) {
			i = dal.mod(em, null, null, em.getGid(), 1);
		}

		return i;
	}

	// 补缴退回流程
	public Integer returnBjFlow(EmHouseBJModel em) {
		WfBusinessService wfbs = new EmHouse_bjImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "补缴退回", em };

		String[] str = null;
		if (em.getEmhb_tapr_id() != null) {
			str = wfs.ReturnToN(obj, em.getEmhb_tapr_id(), 1,
					UserInfo.getUsername());
		} else {
			Integer i = returnBJ(em);
			str = new String[5];
			str[0] = i > 0 ? "1" : "0";
		}

		if (str[0].equals("1")) {
			return 1;
		} else {
			return 0;
		}

	}

	// 交单变更客服再次提交
	public Integer modGjjInfo(EmHouseChangeGJJModel em) {

		WfBusinessService wfbs = new EmHouseChangeGjjImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "再次提交", em };

		String[] str = null;
		if (em.getEhcg_tapr_id() != null) {
			str = wfs.PassToNext(obj, em.getEhcg_tapr_id(),
					UserInfo.getUsername(), "", 0, "");
		} else {
			EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
			Integer i = dal.Mod(em, em.getEhcg_id());
			str = new String[5];
			str[0] = i > 0 ? "1" : "0";
		}
		if (str[0].equals("1")) {
			return 1;
		} else {
			return 0;
		}

	}

	// 修改交单变更数据
	public Integer ModGjj(EmHouseChangeGJJModel em) {
		Integer i = 0;
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		if (em.getEhcg_id() != null && em.getEhcg_id() > 0) {
			i = dal.Mod(em, em.getEhcg_id());
			/*
			 * if (i > 0) { EmHouseChangeGJJModel egm = new
			 * EmHouseChangeGJJModel();
			 * egm.setEhcg_modname(em.getEhcg_modname());
			 * egm.setEhcg_ifdeclare(em.getEhcg_ifdeclare()); dal.ModRe(egm,
			 * em.getEhcg_id()); }
			 */
		}

		return i;
	}

	// 交单退回流程
	public Integer returnGjjFlow(EmHouseChangeGJJModel em) {
		WfBusinessService wfbs = new EmHouseChangeGjjImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "交单退回", em };

		String[] str = null;
		if (em.getEhcg_tapr_id() != null) {

			str = wfs.ReturnToN(obj, em.getEhcg_tapr_id(), 1,
					UserInfo.getUsername());
			/*
			 * str = wfs.ReturnToPrev(obj, em.getEhcg_tapr_id(),
			 * UserInfo.getUsername(), "");
			 */
		} else {
			Integer i = ModGjj(em);
			str = new String[5];
			str[0] = i > 0 ? "1" : "0";
		}
		if (str[0].equals("1")) {
			return 1;
		} else {
			return 0;
		}

	}

	// 修改变更状态
	public Integer modCommitState(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		EmHouseUpdateDal dal2 = new EmHouseUpdateDal();
		if (em.getEmhc_ifdeclare() != null) {
			if (em.getEmhc_ifdeclare().equals(4)) {
				i = dal.returnConfirmDate(em.getEmhc_id(), em.getOwnmonth());
			} else if (em.getEmhc_ifdeclare().equals(0)) {
				i = dal.updateConfirmDate(em.getEmhc_id(), em.getOwnmonth());
			}
		}
		if (i > 0) {
			i = em.getEmhc_id();
		}
		return i;

	}

	// 删除补缴数据
	public Integer delbj(Integer id) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		Integer i = dal.delById(id);
		return i;
	}

	// 删除无效补缴入职数据
	public Integer delbjByGid(Integer gid) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		Integer i = dal.delByGid(gid);
		return i;
	}

	// 删除档案交单数据
	public Integer delGjj(Integer id) {
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		Integer i = dal.del(id);
		return i;

	}

	// 调回
	public Integer MoveBack(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		EmHouseSetBll bll = new EmHouseSetBll();
		List<BigDecimal> list = bll.getLimit(em.getEmhc_radix(),
				em.getEmhc_cpp());

		try {
			i = dal.moveinEmhouse(em.getOwnmonth(), em.getGid(), em.getCid(),
					em.getEmhc_companyid(), em.getEmhc_houseid(),
					em.getEmhc_companyid(), em.getEmhc_excompany(),
					em.getEmhc_hj(), list.get(0), em.getEmhc_radix(),
					em.getEmhc_cpp(), em.getEmhc_opp(), list.get(1),
					list.get(1), em.getEmhc_title(), em.getEmhc_degree(),
					em.getEmhc_wifename(), em.getEmhc_wifeidcard(),
					UserInfo.getUsername(), "调入", em.getEmhc_mobile(),
					em.getEmhc_ifdeclare(), em.getEmhc_single(),
					em.getEmhc_remark());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 调入转新增
	public Integer changeNew(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		if (em.getEmhc_id() != null && em.getGid() != null) {
			i = dal.changeNew(em.getEmhc_id(), em.getGid(), em.getOwnmonth());
		}
		return i;
	}

	// 新增转调入
	public Integer changeMove(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		if (em.getEmhc_id() != null && em.getGid() != null) {
			i = dal.changeMove(em.getEmhc_id(), em.getGid(),
					em.getEmhc_houseid(), em.getOwnmonth());
		}
		return i;
	}

	// 比例基数调整
	public Integer changeinfo(EmHouseChangeModel em) {
		Integer i = 0;
		EmHouseChangeDal dal = new EmHouseChangeDal();
		i = dal.changeinfo(em.getGid(), em.getOwnmonth(), em.getEmhc_radix(),
				em.getEmhc_cpp(), em.getEmhc_ifdeclare(), em.getEmhc_remark(),
				em.getEmhc_addname());

		return i;
	}

	// 判断数据是否可退回
	public boolean gobackstate(EmHouseChangeModel em) {
		boolean b = false;
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();
		list = dal.getChangeList(em, false, null, false, null, null);
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 在册数据处理
	public Integer updateData(Integer gid) {
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		Integer i = dal.updateData(gid);
		return i;
	}

	// 在册数据处理
	public Integer ControlData(Integer id, Integer ownmonth) {
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setEmhc_id(id);
		List<EmHouseChangeModel> list = getChangeList(em);
		em.setOwnmonth(ownmonth);
		EmHouseSetBll bll = new EmHouseSetBll();
		boolean b = bll.gjjOnair(list.get(0).getCid(), list.get(0).getGid(),
				ownmonth, list.get(0).getEmhc_single());
		Integer stop = b ? 1 : 0;
		em.setEmhc_addname(UserInfo.getUsername());
		Integer i = dal.controlData(em, stop);
		return i;
	}

	// 在册数据处理
	public Integer DeleteData(Integer id, Integer ownmonth) {
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setEmhc_id(id);
		List<EmHouseChangeModel> list = getChangeList(em);
		em.setOwnmonth(ownmonth);
		EmHouseSetBll bll = new EmHouseSetBll();
		boolean b = bll.gjjOnair(list.get(0).getCid(), list.get(0).getGid(),
				ownmonth, list.get(0).getEmhc_single());
		Integer stop = b ? 1 : 0;
		em.setEmhc_addname(UserInfo.getUsername());
		Integer i = dal.DeleteData(em, stop);
		return i;
	}

	// 获取职称信息
	public List<PubCodeConversionModel> getpcInfo() {

		List<PubCodeConversionModel> list = new ListModelList<>();
		PubCodeConversionDal dal = new PubCodeConversionDal();
		list = dal.getListInfo(35, "职称代码");
		return list;
	}

	// 查询是否有未处理公积金数据
	public boolean gjjQuery(Integer gid, Integer nowmonth) {
		boolean b = false;
		List<EmHouseChangeModel> l1 = new ListModelList<>();
		List<EmHouseChangeGJJModel> l2 = new ListModelList<>();
		List<EmHouseBJModel> l3 = new ListModelList<>();
		EmHouseChangeDal d1 = new EmHouseChangeDal();
		EmHouseChangeGjjDal d2 = new EmHouseChangeGjjDal();
		Emhouse_BjDal d3 = new Emhouse_BjDal();

		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setGid(gid);
		em.setNowmonth(nowmonth);
		em.setDataState(7);
		l1 = d1.getChangeList(em, false, null, true, "a.gid", null);
		if (l1.size() > 0) {
			return true;
		}
		EmHouseChangeGJJModel gm = new EmHouseChangeGJJModel();
		gm.setGid(gid);
		gm.setNowmonth(nowmonth);
		gm.setState(3);
		l2 = d2.getlist(gm);
		if (l2.size() > 0) {
			return true;
		}

		l3 = d3.getlist(gid, nowmonth);
		if (l3.size() > 0) {
			return true;
		}

		return b;
	}

	// 退回公积金数据
	public String[] returnGJJBj(int dataid) {
		String[] result = new String[2];
		Emhouse_BjDal dal = new Emhouse_BjDal();
		boolean i = dal.returnGJJBj(dataid);

		if (i) {
			result[0] = "1";
			result[1] = "退回住房公积金补缴数据成功。";
		} else {
			result[0] = "2";
			result[1] = "退回住房公积金补缴数据失败。";
		}

		return result;
	}

	// 联网核查失败/汇缴失败
	public Integer updateErrData(EmHouseErrModel m) {
		EmHouseChangeDal dal = new EmHouseChangeDal();
		Integer i = 0;
		if (m.getType().equals(1)) {
			i = dal.updateData(m.getGid(), m.getOwnmonth(), 6);
			if (m.getGid() != null && !m.getGid().toString().equals("")) {
				updateData(m.getGid());
			}
		}

		return i;
	}

}
