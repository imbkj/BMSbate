package dal.EmBenefit;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmActyCompactModel;
import Util.UserInfo;

public class EmActyCompactDal {
	public List<EmActyCompactModel> getListById(Integer id) {
		List<EmActyCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select eaco_id,eaco_supp_id,eaco_compactid,eaco_name,eaco_signdate,"
				+ "eaco_inuredate,eaco_stopdate,"
				+ "eaco_returndate,eaco_auditdate,eaco_fileId,eaco_filedate,"
				+ "eaco_auto,case eaco_auto when 1 then '是' else '否' end eaco_auto2,eaco_state,"
				+ "eaco_addname,convert(varchar(10),eaco_addtime,120)eaco_addtime,eaco_remark,"
				+ "eaco_tapr_id,eaco_modname,convert(varchar(10),eaco_modtime,120)eaco_modtime"
				+ " from EmActyCompact where eaco_id=?";
		try {
			list = db.find(sql, EmActyCompactModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmActyCompactModel> getAddNameList(String name) {
		List<EmActyCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct eaco_addname from EmActyCompact where 1=1";
		if (name != null && !name.equals("")) {
			sql = sql + " and eaco_addname like '%" + name + "%'";
		}
		sql = sql + " order by eaco_addname";
		try {
			list = db.find(sql, EmActyCompactModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmActyCompactModel> getNameList(String name, Boolean type) {
		List<EmActyCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select distinct eaco_name from EmActyCompact where eaco_state=1";
		if (type) {
			sql = sql + " and eaco_name like ?";
		} else {
			sql = sql + " and eaco_name = ?";
		}

		try {
			list = db.find(sql, EmActyCompactModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<EmActyCompactModel> getCompactList(String name, Boolean type) {
		List<EmActyCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select distinct eaco_compactid from EmActyCompact where 1=1";
		if (type) {
			name = "%" + name + "%";
			sql = sql + " and eaco_compactid like ?";
		} else {
			sql = sql + " and eaco_compactid = ?";
		}

		try {
			list = db.find(sql, EmActyCompactModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<EmActyCompactModel> getList(EmActyCompactModel eacm,
			Boolean desc) {
		List<EmActyCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select eaco_id,eaco_supp_id,eaco_compactid,eaco_name,eaco_signdate,"
				+ "eaco_inuredate,eaco_stopdate,eaco_backreason,"
				+ "eaco_returndate,eaco_auditdate,eaco_fileId,eaco_filedate,"
				+ "eaco_auto,case eaco_auto when 1 then '是' else '否' end eaco_auto2,"
				+ "eaco_together,case eaco_together when 0 then '未合作' when 1 then '已合作' end eaco_together2,"
				+ "eaco_state,case eaco_state when 0 then '已终止' when 1 then '生效' when 2 then '合同制作' when 3 then '待审核' when 4 then '已审核' when 5 then '退回' when 6 then '已签回' when 7 then '已归档' end eaco_state2,"
				+ "eaco_addname,eaco_addtime,eaco_remark,eaco_file,eaco_tapr_id"
				+ " from EmActyCompact where 1=1";

		if (eacm != null) {
			if (eacm.getEaco_id() != null && !eacm.getEaco_id().equals("")) {
				sql += " and eaco_id=" + eacm.getEaco_id();
			}

			if (eacm.getEaco_name() != null && !eacm.getEaco_name().equals("")) {
				sql = sql + " and eaco_name like '%" + eacm.getEaco_name()
						+ "%'";
			}
			if (eacm.getEaco_compactid() != null
					&& !eacm.getEaco_compactid().equals("")) {
				sql = sql + " and eaco_compactid like '%"
						+ eacm.getEaco_compactid() + "%'";
			}
			if (eacm.getEaco_addname() != null
					&& !eacm.getEaco_addname().equals("")) {
				sql = sql + " and eaco_addname = '" + eacm.getEaco_compactid()
						+ "'";
			}

			if (eacm.getEaco_state() != null
					&& !eacm.getEaco_state().equals("")) {
				if (eacm.getEaco_state().equals("1")) {
					sql = sql + " and eaco_state in(1,4)";
				} else {
					sql = sql + " and eaco_state = " + eacm.getEaco_state();
				}

			}

		}

		if (desc != null && desc == true) {
			sql = sql + " order by eaco_id desc";
		}

		System.out.println(sql);
		try {
			list = db.find(sql, EmActyCompactModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 更新任务流程ID
	public Integer updateTaprId(Integer dataId, Integer taprId)
			throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmActyCompact set eaco_tapr_id=? where eaco_id=?";
		i = db.updatePrepareSQL(sql, taprId, dataId);
		return i;
	}

	public Integer add(EmActyCompactModel eacm) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into EmActyCompact(eaco_supp_id,eaco_compactid,eaco_name,eaco_signdate,eaco_inuredate,eaco_stopdate,eaco_auto,eaco_state,eaco_addname,eaco_addtime,eaco_remark)"
				+ " values(?,?,?,?,?,?,?,?,?,getdate(),?)";
		try {
			i = db.insertAndReturnKey(sql, eacm.getEaco_supp_id(),
					eacm.getEaco_compactid(), eacm.getEaco_name(),
					eacm.getEaco_signdate(), eacm.getEaco_inuredate(),
					eacm.getEaco_stopdate(), eacm.getEaco_auto(),
					eacm.getEaco_state(), eacm.getEaco_addname(),
					eacm.getEaco_remark());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public Integer mod(EmActyCompactModel eacm) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmActyCompact set eaco_modtime=getdate(),eaco_modname=?";
		if (eacm.getEaco_state() != null && !eacm.getEaco_state().equals("")) {
			sql = sql + ",eaco_state=" + eacm.getEaco_state();
		}

		if (eacm.getEaco_together() != null
				&& !eacm.getEaco_together().equals("")) {
			sql = sql + ",eaco_together=" + eacm.getEaco_together();
		}

		if (eacm.getEaco_signdate() != null
				&& !eacm.getEaco_signdate().equals("")) {
			sql = sql + ",eaco_signdate='" + eacm.getEaco_signdate() + "'";

		}

		if (eacm.getEaco_inuredate() != null
				&& !eacm.getEaco_signdate().equals("")) {
			sql = sql + ",eaco_inuredate='" + eacm.getEaco_inuredate() + "'";

		}

		if (eacm.getEaco_stopdate() != null
				&& !eacm.getEaco_signdate().equals("")) {
			sql = sql + ",eaco_stopdate='" + eacm.getEaco_stopdate() + "'";

		}
		if (eacm.getEaco_filedate() != null
				&& !eacm.getEaco_filedate().equals("")) {
			sql = sql + ",eaco_filedate='" + eacm.getEaco_filedate() + "'";

		}
		if (eacm.getEaco_fileId() != null && !eacm.getEaco_fileId().equals("")) {
			sql = sql + ",eaco_fileId='" + eacm.getEaco_fileId() + "'";

		}
		if (eacm.getEaco_backreason() != null
				&& !eacm.getEaco_backreason().equals("")) {
			sql = sql + ",eaco_backreason='" + eacm.getEaco_backreason() + "'";

		}

		if (eacm.getEaco_auto() != null && !eacm.getEaco_auto().equals("")) {
			sql = sql + ",eaco_auto= " + eacm.getEaco_auto();

		}

		if (eacm.getEaco_remark() != null && !eacm.getEaco_remark().equals("")) {
			sql = sql + ",eaco_remark='" + eacm.getEaco_remark() + "'";

		}

		sql = sql + " where eaco_id=?";
		try {
			i = db.updatePrepareSQL(sql, eacm.getEaco_modname(),
					eacm.getEaco_id());
			if (i > 0) {
				i = eacm.getEaco_id();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer del(Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmActyCompact where eaco_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}

	public Integer stopcompact(Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();

		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmActyCompactStop_P_py(?,?)}", Types.INTEGER, id,
					UserInfo.getUsername()).toString());
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

}
