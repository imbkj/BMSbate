package dal.EmPay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmPayBackLogModel;
import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import Model.empayTaskModel;

public class EmPa_SelectDal {
	// 获取员工名称列表
	public List<EmbaseModel> getEmbaseList(String str) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,coba_company,coba_shortname,a.gid,emba_name,emba_idcard,"
				+ "emba_mobile,emba_email,isnull(esda_ba_name,emba_name) emba_ba_name,"
				+ "coba_client,emba_state,case isnull(emba_writeoff_account,'') when '' then "
				+ " emba_gz_account else emba_writeoff_account end emba_gz_account,"
				+ "case isnull(emba_writeoff_account,'') when '' then emba_gz_bank else "
				+ " emba_writeoff_bank end emba_gz_bank"
				+ " from EmBase a"
				+ " inner join CoBase b "
				+ " on a.cid=b.cid "
				+"  left join emsalaryinfo c on a.gid=c.gid and a.cid=c.cid "
				+ "where 1=1 "
				+ str + " order by emba_state desc,coba_company,emba_name";
		System.out.println(sql);
		try {
			list = db.find(sql.toString(), EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmbaseModel> getEmbaseList2(String str) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select top 100 a.cid,coba_company,coba_shortname,a.gid,emba_name,emba_idcard,"
				+ "emba_mobile,emba_email,isnull(esda_ba_name,emba_name) emba_ba_name,"
				+ "coba_client,emba_state,case isnull(emba_writeoff_account,'') when '' then "
				+ " emba_gz_account else emba_writeoff_account end emba_gz_account,"
				+ "case isnull(emba_writeoff_account,'') when '' then emba_gz_bank else "
				+ " emba_writeoff_bank end emba_gz_bank"
				+ " from EmBase a"
				+ " inner join CoBase b "
				+ " on a.cid=b.cid "
				+"  left join emsalaryinfo c on a.gid=c.gid and a.cid=c.cid "
				+ "where 1=1 "
				+ str + " order by emba_state desc,coba_company,emba_name";
		
		try {
			list = db.find(sql.toString(), EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询个人支付信息
	public List<EmPayModel> getEmPayList(String str) {
		List<EmPayModel> list = new ListModelList<EmPayModel>();
		dbconn db = new dbconn();
		String sql = "select id,coba_company,coba_shortname,coba_client,emba_name,emba_idcard,"
				+ "emba_state,convert(varchar(10),empa_addtime,120) empa_paytime,"
				+ "case empa_state when 0 then '取消' when 1 then '待审核' when 2 then '经理已审核' "
				+ " when 3 then '部门经理已审核' when 41 then '部门经理已审核' when 42 then '总经理助理已审核'"
				+ " when 5 then '票据已审核' when 7 then '财务已审核' when 8 then '待发放'"
				+ " when 9 then '退回' when 6 then '已发放' end state_name,a.*"
				+ " from EmPay a inner join CoBase b on a.cid=b.CID"
				+ " inner join EmBase c on a.gid=c.gid where 1=1"
				+ str
				+ " order by empa_state desc,empa_addtime desc";

		try {
			list = db.find(sql.toString(), EmPayModel.class,
					dbconn.parseSmap(EmPayModel.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询个人支付信息(导出EXCEL)
	public List<EmPayModel> getEmPayGroupList(String str) {
		List<EmPayModel> list = new ListModelList<EmPayModel>();
		dbconn db = new dbconn();
		String sql = "select count(*)num,sum(isnull(empa_aftertax,0))empa_aftertax,sum(isnull(empa_tax,0))empa_tax,sum(isnull(empa_fee,0))empa_fee,"
				+ "coba_shortname,empa_class,ownmonth,ownmonthend,"
				+ "empa_payclass,convert(varchar(255),empa_remark)empa_remark,"
				+ "empa_paymenttype,empa_manager_checkname,empa_depmanager_checkname,empa_finance_checkname"
				+ " from EmPay a inner join CoBase b on a.cid=b.CID"
				+ " inner join EmBase c on a.gid=c.gid "
				+ " where 1=1 "
				+ str
				+ " group by coba_shortname,empa_class,ownmonth,ownmonthend,"
				+ "empa_payclass,convert(varchar(255),empa_remark),"
				+ "empa_paymenttype,empa_manager_checkname,empa_depmanager_checkname,empa_finance_checkname"
				+ " order by coba_shortname desc";

		try {
			list = db.find(sql.toString(), EmPayModel.class,
					dbconn.parseSmap(EmPayModel.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据批次查询个人支付信息
	public List<EmPayModel> getEmPayInfoList(String str) {
		List<EmPayModel> list = new ListModelList<EmPayModel>();
		dbconn db = new dbconn();
		String sql = "select distinct(empa_number) empa_number,SUM(empa_fee)empa_feetotal,ownmonth,"
				+ "SUM(empa_tax)empa_taxtotal,sum(isnull(empa_aftertax,empa_fee))empa_aftertaxtotal,empa_addname,"
				+ "convert(varchar(10),empa_addtime,120)empa_addtime,empa_state,"
				+ "case empa_state when 0 then '取消' when 1 then '待审核' when 2 then '客户经理已审核' "
				+ " when 3 then '部门经理已审核' when 41 then '部门经理已审核' when 42 then '总经理助理已审核'"
				+ " when 5 then '票据已审核' when 7 then '财务已审核' when 8 then '待发放'"
				+ " when 9 then '退回' when 6 then '已发放' end state_name,empa_paytype,"
				+ "max(empa_name)empa_name,max(coba_company)coba_company"
				+ " from EmPay a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " where isnull(empa_number,'')!='' "
				+ str
				+ " GROUP BY empa_number,ownmonth,empa_addname,"
				+ " convert(varchar(10),empa_addtime,120),empa_state,empa_paytype"
				+ " order by empa_addtime desc";
		try {
			list = db.find(sql.toString(), EmPayModel.class,
					dbconn.parseSmap(EmPayModel.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据empa_id查询退回原因
	public List<EmPayBackLogModel> getEmPayBackList(String empa_number) {
		List<EmPayBackLogModel> list = new ArrayList<EmPayBackLogModel>();
		dbconn db = new dbconn();
		String sql = "select back_id,back_case,back_step,back_addname,"
				+ "convert(varchar(19),back_addtime,120) back_addtime,back_empa_number "
				+ " from empaybacklog where back_empa_number=?"
				+ " order by back_addtime desc";
		try {
			list = db.find(sql.toString(), EmPayBackLogModel.class,
					dbconn.parseSmap(EmPayBackLogModel.class), empa_number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据empa_number查询退回原因
	public EmPayBackLogModel getEmPayBack(String empa_number) {
		EmPayBackLogModel model = new EmPayBackLogModel();
		dbconn db = new dbconn();
		String sql = "select * from empaybacklog where back_state=1 and back_empa_number='"
				+ empa_number + "'";
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setBack_addname(rs.getString("back_addname"));
				model.setBack_case(rs.getString("back_case"));
				model.setBack_id(rs.getInt("back_id"));
				model.setBack_step(rs.getInt("back_step"));
				model.setBack_state(rs.getInt("back_state"));
				model.setBack_empa_number(rs.getString("back_empa_number"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	// 根据empa_number查询任务单
	public EmPayTaskListModel getEmPayTaskListById(int patk_id) {
		EmPayTaskListModel model = new EmPayTaskListModel();
		dbconn db = new dbconn();
		String sql = "select * from EmPayTaskList where patk_id=" + patk_id;
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setPatk_number(rs.getString("patk_number"));
				model.setPatk_id(rs.getInt("patk_id"));
				model.setPatk_tapr_id(rs.getInt("patk_tapr_id"));
				model.setPatk_addname(rs.getString("patk_addname"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	// 根据empa_number查询任务单
	public EmPayTaskListModel getEmPayTaskListByNumber(String empa_number) {
		EmPayTaskListModel model = new EmPayTaskListModel();
		dbconn db = new dbconn();
		String sql = "select * from EmPayTaskList where patk_state=1 and patk_number='"
				+ empa_number + "'";
		ResultSet rs = null;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setPatk_number(rs.getString("patk_number"));
				model.setPatk_id(rs.getInt("patk_id"));
				model.setPatk_tapr_id(rs.getInt("patk_tapr_id"));
				model.setPatk_addname(rs.getString("patk_addname"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	// 根据任务单编号获取当前的任务名称和节点
	public TaskProcessViewModel getStep(int tapr_id) {
		String sql = "select wfno_step,wfno_name from View_WfNode_TaskProcess"
				+ " where wfno_state=1 and tapr_id=" + tapr_id;
		TaskProcessViewModel m = new TaskProcessViewModel();
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			if (rs.next()) {
				m.setWfno_name(rs.getString("wfno_name"));
				m.setWfno_step(rs.getInt("wfno_step"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}

	public List<empayTaskModel> getEmPayTaskList(String empa_number,
			int task_step) {
		List<empayTaskModel> list = new ArrayList<empayTaskModel>();
		String sql = "select distinct(task_name),task_step from EmPayTask "
				+ " where task_number='"
				+ empa_number
				+ "' and task_step<"
				+ task_step
				+ " group by task_number,task_name,task_step order by task_step";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				empayTaskModel m = new empayTaskModel();
				m.setTask_name(rs.getString("task_name"));
				m.setTask_step(rs.getInt("task_step"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
