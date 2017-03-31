package bll.EmPay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.DepartmentModel;
import Model.EmPayBackLogModel;
import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import Model.empayTaskModel;
import Util.StringUtil;
import dal.EmPay.EmPa_SelectDal;
import dal.SystemControl.Login_SelectDal;

public class EmPa_SelectBll {
	private final EmPa_SelectDal dal = new EmPa_SelectDal();

	// 获取员工名称列表
	public List<EmbaseModel> getEmbaseList(String str) {
		if (str.equals("")) {
			return dal.getEmbaseList2(str);
		} else {
			return dal.getEmbaseList(str);
		}

	}

	// 获取员工名称列表
	public List<EmbaseModel> getEmbaseInfoList(EmbaseModel m) {
		String sql = "";
		if (m.getGid() != null) {
			sql = sql + " and a.gid=" + m.getGid();
		}
		if (m.getCid() != null) {
			sql = sql + " and a.cid=" + m.getCid();
		}
		if (m.getCoba_company() != null && !m.getCoba_company().equals("")) {
			sql = sql + " and coba_company like '%" + m.getCoba_company()
					+ "%'";
		}
		if (m.getCoba_client() != null && !m.getCoba_client().equals("")) {
			sql = sql + " and coba_client='" + m.getCoba_client() + "'";
		}
		if (m.getEmba_name() != null && !m.getEmba_name().equals("")) {
			sql = sql + " and emba_name='" + m.getEmba_name() + "'";
		}
		if (m.getEmba_idcard() != null && !m.getEmba_idcard().equals("")) {
			sql = sql + " and emba_idcard='" + m.getEmba_idcard() + "'";
		}
		if (m.getStatename() != null && !m.getStatename().equals("")) {
			if (m.getStatename().equals("离职")) {
				sql = sql + " and emba_state=0";
			} else {
				sql = sql + " and emba_state<>0";
			}
		}
		return getEmbaseList(sql);
	}

	// 查询个人支付信息
	public List<EmPayModel> getEmPayList(String str) {
		return dal.getEmPayList(str);
	}

	public List<EmPayModel> getEmPayGroupList(String str) {
		return dal.getEmPayGroupList(str);
	}

	// 根据任务单编号获取当前的任务名称和节点
	public TaskProcessViewModel getStep(int tapr_id) {
		return dal.getStep(tapr_id);
	}

	// 根据批次查询个人支付信息
	public List<EmPayModel> getEmPayInfoList(String str) {
		return dal.getEmPayInfoList(str);
	}

	// 查询个人支付信息
	public List<EmPayModel> getEmPayInfoList(EmPayModel m) {
		String sql = "";
		if (m.getGid() != null) {
			sql = sql + " and a.gid=" + m.getGid();
		}
		if (m.getCid() != null) {
			sql = sql + " and a.cid=" + m.getCid();
		}
		if (m.getCoba_company() != null && !m.getCoba_company().equals("")) {
			sql = sql + " and coba_company like '%"
					+ StringUtil.replaceSpace(m.getCoba_company()) + "%'";
		}
		if (m.getCoba_client() != null && !m.getCoba_client().equals("")) {
			sql = sql + " and coba_client='" + m.getCoba_client() + "'";
		}
		if (m.getEmba_name() != null && !m.getEmba_name().equals("")) {
			sql = sql + " and emba_name='" + m.getEmba_name() + "'";
		}
		if (m.getName() != null && !m.getName().equals("")) {
			sql = sql
					+ " and exists (select 1 from empay where empa_name like '%"
					+ StringUtil.replaceSpace(m.getName())
					+ "%' and empa_number=a.empa_number)";
		}
		if (m.getEmba_idcard() != null && !m.getEmba_idcard().equals("")) {
			sql = sql + " and emba_idcard='" + m.getEmba_idcard() + "'";
		}
		if (m.getState_name() != null && !m.getState_name().equals("")) {
			if (m.getState_name().equals("客户经理待审核")) {
				sql = sql + " and empa_state=1";
			} else if (m.getState_name().equals("部门经理待审核")) {
				sql = sql + " and empa_state=2";
			} else if (m.getState_name().equals("总经理助理待审批")) {
				sql = sql + " and empa_state=3";
			} else if (m.getState_name().equals("票据待审核")) {
				sql = sql + " and empa_state in(41,42)";
			} else if (m.getState_name().equals("财务待审核")) {
				sql = sql + " and ((empa_number like '报销%' and empa_state =5) or (empa_number like '个税%' and empa_state in(41,42)))";
			} else if (m.getState_name().equals("总经理待审核")) {
				sql = sql + " and empa_state=7";
			} else if (m.getState_name().equals("出纳待发")) {
				sql = sql + " and empa_state=8";
			} else if (m.getState_name().equals("已发")) {
				sql = sql + " and empa_state=6";
			} else if (m.getState_name().equals("退回")) {
				sql = sql + " and empa_state=9";
			}
		}
		if (m.getEmpa_state() != null && !m.getEmpa_state().equals("")) {
			sql = sql + " and empa_state=" + m.getEmpa_state();
		}
		if (m.getOwnmonth() != null) {
			sql = sql + " and ownmonth=" + m.getOwnmonth();
		}
		if (m.getEmpa_class() != null && !m.getEmpa_class().equals("")) {
			sql = sql + " and empa_class='" + m.getEmpa_class() + "'";
		}
		if (m.getEmpa_payclass() != null && !m.getEmpa_payclass().equals("")) {
			sql = sql + " and empa_payclass='" + m.getEmpa_payclass() + "'";
		}
		if (m.getEmpa_paytype() != null && !m.getEmpa_paytype().equals("")) {
			sql = sql + " and empa_paytype='" + m.getEmpa_paytype() + "'";
		}
		if (m.getEmpa_number() != null && !m.getEmpa_number().equals("")) {
			sql = sql + " and empa_number='" + m.getEmpa_number() + "'";
		}
		if (m.getEmpa_addname() != null && !m.getEmpa_addname().equals("")) {
			sql = sql + " and empa_addname='" + m.getEmpa_addname() + "'";
		}
		if (m.getAddtime() != null && !m.getAddtime().equals("")) {
			sql = sql + " and convert(varchar(10),empa_addtime,120)='"
					+ DatetoStr(m.getAddtime()) + "'";
		}
		if (m.getDep_id() != null && m.getDep_id() > 0) {
			sql = sql + " and empa_addname in(select log_name from Login "
					+ "where dep_id=" + m.getDep_id() + ")";
		}
		
		if (m.getDep_name()!=null && !m.getDep_name().equals("")) {
			sql = sql + " and empa_addname in(select log_name from View_loginrole "
					+ "where dep_name='" + m.getDep_name() + "')";
		}
		return getEmPayInfoList(sql);
	}

	// 根据empa_id查询退回原因
	public List<EmPayBackLogModel> getEmPayBackList(String empa_number) {
		return dal.getEmPayBackList(empa_number);
	}

	public List<empayTaskModel> getEmPayTaskList(String empa_number,
			int task_step) {
		return dal.getEmPayTaskList(empa_number, task_step);
	}

	// 根据empa_id查询退回原因
	public EmPayBackLogModel getEmPayBack(String empa_number) {
		return dal.getEmPayBack(empa_number);
	}

	// String格式转换成ownmonth
	public static String DatetoStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = "";
		try {
			Date = String.valueOf(sdf.format(date));
		} catch (Exception e) {

		}
		return Date;
	}
	
	

	// 根据empa_number查询退回原因
	public EmPayTaskListModel getEmPayTaskListByNumber(String empa_number) {
		return dal.getEmPayTaskListByNumber(empa_number);
	}

	// 根据empa_number查询退回原因
	public EmPayTaskListModel getEmPayTaskListById(int prtk_id) {
		return dal.getEmPayTaskListById(prtk_id);
	}
	
	//查询部门信息
	public List<DepartmentModel> getDeptList(){
		Login_SelectDal sDal = new Login_SelectDal();
		return sDal.getDepartmentList();
	}
}
