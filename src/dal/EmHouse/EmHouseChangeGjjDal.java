package dal.EmHouse;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import com.sun.org.apache.xpath.internal.operations.And;

import Conn.dbconn;
import Model.EmHouseBJModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Util.UserInfo;

public class EmHouseChangeGjjDal {

	// 查询变更信息
	public List<EmHouseChangeGJJModel> getInfoById(Integer id) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();

		return list;
	}

	// 查询变更信息
	public List<EmHouseChangeGJJModel> getInfoByGid(Integer gid) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ehcg_content,ehcg_id,gid,cid,ownmonth, ehcg_company, "
				+ "ehcg_name, ehcg_change, ehcg_content, ehcg_changevalue,ehcg_tapr_id, ehcg_addtime, ehcg_addname,ehcg_ifdeclare,"
				+ " ehcg_declareTime , ehcg_declareName,ehcg_single, ehcg_shebaoid, ehcg_remark,ehcg_ifbase,"
				+ "ehcg_ifsb,case ehcg_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' end statename "
				+ " from emhousechangegjj where gid=?";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseChangeGJJModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<EmHouseChangeGJJModel> getlist(EmHouseChangeGJJModel em) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ehcg_content,ehcg_id,gid,cid,ownmonth, ehcg_company, "
				+ "ehcg_name, ehcg_change, ehcg_content, ehcg_changevalue,ehcg_tapr_id, ehcg_addtime, ehcg_addname,ehcg_ifdeclare,"
				+ "ehcg_declareTime , ehcg_declareName,ehcg_single, ehcg_shebaoid, ehcg_remark,ehcg_ifbase,"
				+ "ehcg_ifsb,case ehcg_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' end statename,"
				+ " ehcg_name_p, ehcg_name_n, ehcg_idcardclass_p, ehcg_idcardclass_n, ehcg_idcard_p, ehcg_idcard_n, ehcg_sex_p, ehcg_sex_n, ehcg_hj_p,"
				+ " ehcg_hj_n, ehcg_sbid_p, ehcg_sbid_n, ehcg_marry_p, ehcg_marry_n, ehcg_wifename_p, ehcg_wifename_n, ehcg_wifeidcard_p, ehcg_wifeidcard_n, ehcg_title_p,"
				+ " ehcg_title_n, ehcg_degree_p, ehcg_degree_n, ehcg_email_p, ehcg_email_n, ehcg_phone_p, ehcg_phone_n"
				+ " from emhousechangegjj where 1=1";

		if (em.getEhcg_id() != null) {
			if (!em.getEhcg_id().equals("")) {
				sql += " and ehcg_id=" + em.getEhcg_id();
			}
		}

		if (em.getNowmonth() != null) {
			if (!em.getNowmonth().equals("")) {
				sql += " and ownmonth>=" + em.getNowmonth();
			}
		}

		if (em.getGid() != null) {
			if (!em.getGid().equals("")) {
				sql += " and gid=" + em.getGid();
			}
		}

		if (em.getState() != null) {
			if (!em.getState().equals("")) {
				if (em.getState().equals(1)) {
					sql += " and ehcg_ifdeclare in (0,3,4)";
				} else if (em.getState().equals(3)) {
					sql += " and ehcg_ifdeclare in (1,2)";
				}
			}
		}
		sql += " order by ehcg_declaretime desc,ehcg_addtime desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseChangeGJJModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseChangeGJJModel> getAlarmList(EmHouseChangeGJJModel m,
			Integer userId, Integer depId) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select  ehcg_id, a.cid, gid, ehcg_name, ehcg_company, ownmonth, "
				+ " ehcg_change,ehcg_content, ehcg_addname,ehcg_addtime,"
				+ " ehcg_modtime, ehcg_declarename,"
				+ "ehcg_declaretime, ehcg_ifdeclare, "
				+ " ehcg_remark, ehcg_tapr_id,"
				+ "case ehcg_ifdeclare when 0 then '未申报' when 1 then '已申報' when 2 then '申报中' "
				+ "when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '申报中' "
				+ "else '状态异常' end+case when ehcg_tapr_id>0 then case when tapr_appointcon=coba_client then '' else '(中心)' end else '' end states,"
				+ "coba_client,smwr_tid,symr_readstate "
				+ " from EmHousechangegjj a"
				+ " inner join cobase b on a.cid=b.CID"
				+ " left join TaskProcess c on a.ehcg_tapr_id=c.tapr_id"
				+ " left join (select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message where smwr_table='emhousechangegjj' group by smwr_tid)f on a.ehcg_id=f.smwr_tid "
				+ " where ehcg_ifdeclare =? and ownmonth>=201508"
				+ " and a.CID in ( select cid from DataPopedom where log_id=? and  Dat_edited=1 )"
				+ " and tapr_id is not null and tapr_state!=5"
				+ " and (tapr_appointid=-1 and tapr_appointcon in (select name from dbo.GetChildren(?)) or 3=?)";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseChangeGJJModel.class, null,
					m.getEhcg_ifdeclare(), userId, userId, depId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取节点ID
	public Integer getTaprId(Integer id) throws SQLException {
		Integer i = 0;
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ehcg_tapr_id from EmHouseChangeGJJ where ehcg_id=?";
		list = db.find(sql, EmHouseChangeGJJModel.class,
				dbconn.parseSmap(EmHouseChangeGJJModel.class), id);
		i = list.get(0).getEhcg_tapr_id();

		return i;
	}

	// 修改状态
	public Integer updateState(Integer id, Integer state, String name) {
		Integer i = 0;
		dbconn db = new dbconn();
		if (id != null && id > 0) {

			String sql = "update EmHouseChangeGJJ set ehcg_modtime=getdate(),ehcg_modname=?,ehcg_ifdeclare=?"
					+ " where ehcg_id=?";
			try {
				i = db.updatePrepareSQL(sql, name, state, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 更新数据
	public Integer Mod(EmHouseChangeGJJModel em, Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChangeGJJ set ehcg_modname=?,ehcg_modtime=getdate()";
		if (em.getEhcg_ifdeclare() != null) {
			if (!em.getEhcg_ifdeclare().equals("")) {
				sql = sql + " ,ehcg_ifdeclare=" + em.getEhcg_ifdeclare();
			}
		}
		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql = sql + " ,ownmonth=" + em.getOwnmonth();
			}
		}

		if (em.getEhcg_content() != null && !em.getEhcg_content().equals("")) {
			sql = sql + " ,ehcg_content='" + em.getEhcg_content() + "'";
		}
		if (em.getEhcg_name_p() != null && !em.getEhcg_name_p().equals("")) {
			sql = sql + " ,ehcg_name_p='" + em.getEhcg_name_p() + "'";
		}
		if (em.getEhcg_name_n() != null && !em.getEhcg_name_n().equals("")) {
			sql = sql + " ,ehcg_name_n='" + em.getEhcg_name_n() + "'";
		}
		if (em.getEhcg_idcardclass_p() != null
				&& !em.getEhcg_idcardclass_p().equals("")) {
			sql = sql + " ,ehcg_idcardclass_p='" + em.getEhcg_idcardclass_p()
					+ "'";
		}
		if (em.getEhcg_idcardclass_n() != null
				&& !em.getEhcg_idcardclass_n().equals("")) {
			sql = sql + " ,ehcg_idcardclass_n='" + em.getEhcg_idcardclass_n()
					+ "'";
		}
		if (em.getEhcg_idcard_p() != null && !em.getEhcg_idcard_p().equals("")) {
			sql = sql + " ,ehcg_idcard_p='" + em.getEhcg_idcard_p() + "'";
		}
		if (em.getEhcg_idcard_n() != null && !em.getEhcg_idcard_n().equals("")) {
			sql = sql + " ,ehcg_idcard_n='" + em.getEhcg_idcard_n() + "'";
		}
		if (em.getEhcg_sex_p() != null && !em.getEhcg_sex_p().equals("")) {
			sql = sql + " ,ehcg_sex_p='" + em.getEhcg_sex_p() + "'";
		}
		if (em.getEhcg_sex_n() != null && !em.getEhcg_sex_n().equals("")) {
			sql = sql + " ,ehcg_sex_n='" + em.getEhcg_sex_n() + "'";
		}
		if (em.getEhcg_hj_p() != null && !em.getEhcg_hj_p().equals("")) {
			sql = sql + " ,ehcg_hj_p='" + em.getEhcg_hj_p() + "'";
		}
		if (em.getEhcg_hj_n() != null && !em.getEhcg_hj_n().equals("")) {
			sql = sql + " ,ehcg_hj_n='" + em.getEhcg_hj_n() + "'";
		}
		if (em.getEhcg_sbid_p() != null && !em.getEhcg_sbid_p().equals("")) {
			sql = sql + " ,ehcg_sbid_p='" + em.getEhcg_sbid_p() + "'";
		}
		if (em.getEhcg_sbid_n() != null && !em.getEhcg_sbid_n().equals("")) {
			sql = sql + " ,ehcg_sbid_n='" + em.getEhcg_sbid_n() + "'";
		}
		if (em.getEhcg_marry_p() != null && !em.getEhcg_marry_p().equals("")) {
			sql = sql + " ,ehcg_marry_p='" + em.getEhcg_marry_p() + "'";
		}
		if (em.getEhcg_marry_n() != null && !em.getEhcg_marry_n().equals("")) {
			sql = sql + " ,ehcg_marry_n='" + em.getEhcg_marry_n() + "'";
		}
		if (em.getEhcg_wifename_p() != null
				&& !em.getEhcg_wifename_p().equals("")) {
			sql = sql + " ,ehcg_wifename_p='" + em.getEhcg_wifename_p() + "'";
		}
		if (em.getEhcg_wifename_n() != null
				&& !em.getEhcg_wifename_n().equals("")) {
			sql = sql + " ,ehcg_wifename_n='" + em.getEhcg_wifename_n() + "'";
		}
		if (em.getEhcg_wifeidcard_p() != null
				&& !em.getEhcg_wifeidcard_p().equals("")) {
			sql = sql + " ,ehcg_wifeidcard_p='" + em.getEhcg_wifeidcard_p()
					+ "'";
		}
		if (em.getEhcg_wifeidcard_n() != null
				&& !em.getEhcg_wifeidcard_n().equals("")) {
			sql = sql + " ,ehcg_wifeidcard_n='" + em.getEhcg_wifeidcard_n()
					+ "'";
		}
		if (em.getEhcg_title_p() != null && !em.getEhcg_title_p().equals("")) {
			sql = sql + " ,ehcg_title_p='" + em.getEhcg_title_p() + "'";
		}
		if (em.getEhcg_title_n() != null && !em.getEhcg_title_n().equals("")) {
			sql = sql + " ,ehcg_title_n='" + em.getEhcg_title_n() + "'";
		}
		if (em.getEhcg_degree_p() != null && !em.getEhcg_degree_p().equals("")) {
			sql = sql + " ,ehcg_degree_p='" + em.getEhcg_degree_p() + "'";
		}
		if (em.getEhcg_degree_n() != null && !em.getEhcg_degree_n().equals("")) {
			sql = sql + " ,ehcg_degree_n='" + em.getEhcg_degree_n() + "'";
		}
		if (em.getEhcg_phone_p() != null && !em.getEhcg_phone_p().equals("")) {
			sql = sql + " ,ehcg_phone_p='" + em.getEhcg_phone_p() + "'";
		}
		if (em.getEhcg_phone_n() != null && !em.getEhcg_phone_n().equals("")) {
			sql = sql + " ,ehcg_phone_n='" + em.getEhcg_phone_n() + "'";
		}
		if (em.getEhcg_email_p() != null && !em.getEhcg_email_p().equals("")) {
			sql = sql + " ,ehcg_email_p='" + em.getEhcg_email_p() + "'";
		}
		if (em.getEhcg_email_n() != null && !em.getEhcg_email_n().equals("")) {
			sql = sql + " ,ehcg_email_n='" + em.getEhcg_email_n() + "'";
		}
		if (em.getEhcg_remark() != null && !em.getEhcg_remark().equals("")) {
			sql = sql + " ,ehcg_remark='" + em.getEhcg_remark() + "'";
		}

		if (em.getEhcg_declareName() != null) {
			if (!em.getEhcg_declareName().equals("")) {
				sql = sql + " ,ehcg_declareName='" + em.getEhcg_declareName()
						+ "'";
			}
		}
		if (em.getEhcg_declareTime() != null) {
			if (!em.getEhcg_declareTime().equals("")) {
				sql = sql + " ,ehcg_declareTime='" + em.getEhcg_declareTime()
						+ "'";
			}
		}
		if (em.getEhcg_addname() != null) {
			if (!em.getEhcg_addname().equals("")) {
				sql = sql + " ,ehcg_addname='" + em.getEhcg_addname() + "'";
			}
		}

		sql = sql + " where ehcg_id=?";
		if (id != null) {
			try {
				i = db.updatePrepareSQL(sql, em.getEhcg_modname(), id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;

	}

	// 更新数据
	public Integer ModRe(EmHouseChangeGJJModel em, Integer tid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChangeGJJ set ehcg_modname=?,ehcg_modtime=getdate()";
		if (em.getEhcg_ifdeclare() != null) {
			if (!em.getEhcg_ifdeclare().equals("")) {
				sql = sql + " ,ehcg_ifdeclare=" + em.getEhcg_ifdeclare();
			}
		}
		if (em.getEhcg_declareName() != null) {
			if (!em.getEhcg_declareName().equals("")) {
				sql = sql + " ,ehcg_declareName='" + em.getEhcg_declareName()
						+ "'";
			}
		}
		if (em.getEhcg_declareTime() != null) {
			if (!em.getEhcg_declareTime().equals("")) {
				sql = sql + " ,ehcg_declareTime='" + em.getEhcg_declareTime()
						+ "'";
			}
		}

		sql = sql + " where ehcg_tid=?";
		if (tid != null && tid > 0) {
			try {
				i = db.updatePrepareSQL(sql, em.getEhcg_modname(), tid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;

	}

	// 更新任务流程ID
	public Integer updateTaprId(Integer dataId, Integer taprId)
			throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChangeGJJ set ehcg_tapr_id=? where ehcg_id=?";
		i = db.updatePrepareSQL(sql, taprId, dataId);
		return i;
	}

	public Integer add(EmHouseChangeGJJModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call EmHouse_ChangeGjj_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, em.getCid(), em.getGid(),
									em.getOwnmonth(), em.getEhcg_name_p(),
									em.getEhcg_name_n(),
									em.getEhcg_idcardclass_p(),
									em.getEhcg_idcardclass_n(),
									em.getEhcg_idcard_p(),
									em.getEhcg_idcard_n(), em.getEhcg_sex_p(),
									em.getEhcg_sex_n(), em.getEhcg_hj_p(),
									em.getEhcg_hj_n(), em.getEhcg_sbid_p(),
									em.getEhcg_sbid_n(), em.getEhcg_marry_p(),
									em.getEhcg_marry_n(),
									em.getEhcg_wifename_p(),
									em.getEhcg_wifename_n(),
									em.getEhcg_wifeidcard_p(),
									em.getEhcg_wifeidcard_n(),
									em.getEhcg_title_p(), em.getEhcg_title_n(),
									em.getEhcg_degree_p(),
									em.getEhcg_degree_n(),
									em.getEhcg_email_p(), em.getEhcg_email_n(),
									em.getEhcg_phone_p(), em.getEhcg_phone_n(),
									em.getEhcg_remark(), em.getEhcg_addname())
							.toString());
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public Integer del(Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from emhousechangegjj where ehcg_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

}
