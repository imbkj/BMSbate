package dal.EmHouse;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmCensusModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeModel;
import Util.UserInfo;

public class Emhouse_BjDal {
	// 查询补缴信息
	public List<EmHouseBJModel> getEmhouseBjInfo(String str, boolean mod) {
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = " select   emhb_addtime,emhb_declaretime,"
				+ "emhb_id,a.cid,a.gid,emhb_name,emhb_company,a.ownmonth,emhb_feemonth,emhb_startmonth,emhb_stopmonth,"
				+ "emhb_houseid,emhb_companyid,emhb_idcard,isnull(emhb_radix,0) emhb_radix,emhb_reason,convert(decimal(18,2),emhb_total) as emhb_total,"
				+ "emhb_cpp,emhb_opp,case emhb_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' "
				+ "when 3 then '退回' else '待确定' end states,emhb_backreason,"
				+ "emhb_addname,emhb_declarename,emhb_ifdeclare,emhb_single,"
				+ "emhb_flag,emhb_flagname,emhb_excelfile,emhb_remark,emhb_tapr_id,"
				+ "coba_client client,cohf_company"
				+ " from EmHouseBJ a"
				+ " inner join cobase c on a.cid=c.cid"
				+ " left join (select distinct gid from EmHouseChange where emhc_change in ('新增','调入') and  emhc_ifdeclare in (0,2))b on a.gid=b.gid"
				+ " left join (select * from CoHousingFund where cohf_state=1)e on a.emhb_companyid=e.cohf_houseid and ((emhb_single=1 and a.cid=e.cid) or (emhb_single=0 and e.cid is null))"
				+ " where 1=1 ";
		if (mod) {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}
		sql = sql + str;
		sql = sql
				+ " order by case emhb_ifdeclare when 0 then 0 when 2 then 1 when 3 then 2 when 4 then 3 when 1 then 4 end ";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseBJModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询补缴信息(受托台账页面调用)
	public List<EmHouseBJModel> getEmhouseBjInfoZYT(String str, boolean mod) {
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = " select   emhb_addtime,emhb_declaretime,"
				+ "emhb_id,cid,a.gid,emhb_name,emhb_company,a.ownmonth,emhb_feemonth,emhb_startmonth,emhb_stopmonth,"
				+ "emhb_houseid,emhb_companyid,emhb_idcard,isnull(emhb_radix,0) emhb_radix,emhb_reason,convert(decimal(18,2),emhb_total) as emhb_total,"
				+ "emhb_cpp,emhb_opp,case emhb_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' "
				+ "when 3 then '退回' else '待确定' end states,emhb_backreason,"
				+ "emhb_addname,emhb_declarename,emhb_ifdeclare,emhb_single,"
				+ "emhb_flag,emhb_flagname,emhb_excelfile,emhb_remark,emhb_tapr_id"
				+ " from EmHouseBJ a"
				+ " left join (select distinct gid from EmHouseChange where emhc_change in ('新增','调入') and  emhc_ifdeclare in (0,2))b on a.gid=b.gid"
				+ " where 1=1 ";
		if (mod) {
			sql = sql
					+ " and CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			sql = sql
					+ " and CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}
		sql = sql + str;
		sql = sql + " order by a.ownmonth desc,a.emhb_startmonth desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseBJModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseBJModel> housebjList(EmHouseBJModel em, boolean top,
			Integer topNum, boolean distinct, String columns, String order,
			boolean mod) {
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ";
		if (distinct) {
			sql = sql + " distinct ";
		}

		if (top) {
			sql = sql + " top " + topNum + " ";
		}

		if (columns != null && !columns.equals("")) {
			sql = sql + columns;
		} else {
			sql = sql
					+ " emhb_id, a.cid, gid, emhb_name, emhb_company, ownmonth, emhb_feemonth, "
					+ "emhb_startmonth, emhb_stopmonth, emhb_houseid, emhb_companyid,emhb_idcard, "
					+ "emhb_radix, emhb_reason, emhb_total, emhb_cpp, emhb_opp, emhb_addname, "
					+ "convert(varchar(19),emhb_addtime,120)emhb_addtime, "
					+ "convert(varchar(19),emhb_modtime,120)emhb_modtime, emhb_declarename,"
					+ "emhb_declaretime, emhb_ifdeclare,emhb_single, emhb_flag, emhb_flagname,"
					+ "emhb_Excelfile, emhb_remark, emhb_backreason,emhb_tapr_id,"
					+ "case emhb_ifdeclare when 0 then '未申报' when 1 then '已申報' when 2 then '申报中' "
					+ "when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '申报中' "
					+ "else '状态异常' end states,coba_client client";
		}
		sql = sql
				+ " from emhousebj a inner join cobase b on a.cid=b.cid where 1=1 ";

		if (mod) {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}

		if (em.getSelf() != null) {
			if (em.getSelf()) {
				if (em.getEmhb_id() != null && !em.getEmhb_id().equals("")) {
					sql += " and emhb_id!=" + em.getEmhb_id();
				}
			}
		}

		if (em.getEmhb_id() != null) {
			if (!em.getEmhb_id().equals("")) {
				sql = sql + " and emhb_id=" + em.getEmhb_id();
			}
		}

		if (em.getOnboard() != null) {
			if (em.getOnboard()) {
				sql += " and (emhb_tapr_id is null or emhb_tapr_id>0)";
			}
		}

		if (em.getSameInfo() != null) {
			if (em.getSameInfo()) {
				if (em.getGid() != null) {
					if (!em.getGid().equals("")) {
						sql = sql
								+ " and (a.gid="
								+ em.getGid()
								+ " or emhb_idcard in(select emba_idcard from embase where gid="
								+ em.getGid() + "))";
					}
				}
			}
		} else {
			if (em.getGid() != null) {
				if (!em.getGid().equals("")) {
					sql = sql + " and a.gid=" + em.getGid();
				}
			}
		}

		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql = sql + " and ownmonth=" + em.getOwnmonth();
			}
		}

		if (em.getEmhb_feemonth() != null) {
			if (!em.getEmhb_feemonth().equals("")) {
				sql = sql + " and emhb_feemonth=" + em.getEmhb_feemonth();
			}
		}

		if (em.getEmhb_company() != null) {
			if (!em.getEmhb_company().equals("")) {
				sql = sql + " and (emhb_company like '%" + em.getEmhb_company()
						+ "%' or a.cid like '" + em.getEmhb_company() + "%')";
			}
		}

		if (em.getEmhb_name() != null) {
			if (!em.getEmhb_name().equals("")) {
				sql = sql + " and (emhb_name like '%" + em.getEmhb_name()
						+ "%' or gid like '" + em.getEmhb_name() + "%')";
			}
		}

		if (em.getEmhb_ifdeclare() != null) {
			if (!em.getEmhb_ifdeclare().equals("")) {
				sql = sql + " and emhb_ifdeclare=" + em.getEmhb_ifdeclare();
			}
		}

		if (em.getDataState() != null) {
			if (em.getDataState().equals(1)) {
				sql = sql + " and emhb_ifdeclare in(0,4)";
			} else if (em.getDataState().equals(2)) {
				sql = sql + " and emhb_ifdeclare in(0,3,4)";
			} else if (em.getDataState().equals(3)) {
				sql = sql + " and emhb_ifdeclare in(0,2)";
			} else if (em.getDataState().equals(4)) {
				sql = sql + " and emhb_ifdeclare in(0,1,2)";
			}
		}

		if (em.getEmhb_reason() != null) {
			if (!em.getEmhb_reason().equals("")) {
				sql = sql + " and emhb_reason ='" + em.getEmhb_reason() + "'";
			}
		}

		if (em.getEmhb_cpp() != null && em.getEmhb_cpp() > 0) {
			sql = sql + " and emhb_cpp =" + em.getEmhb_cpp();

		}

		if (em.getEmhb_single() != null) {
			if (!em.getEmhb_single().equals("")) {
				if (em.getEmhb_single().equals(0)) {
					sql = sql + " and emhb_single in(0,2)";
				} else {
					sql = sql + " and emhb_single =" + em.getEmhb_single();
				}
			}
		}

		if (em.getClient() != null) {
			if (!em.getClient().equals("")) {
				sql = sql
						+ " and a.cid in (select cid from cobase where coba_client ='"
						+ em.getClient() + "')";
			}
		}

		if (em.getDept() != null) {
			if (!em.getDept().equals("")) {
				sql = sql
						+ " and a.cid in (select cid from cobase where coba_client in (select log_name from View_loginrole where dep_name ='"
						+ em.getDept() + "'))";
			}
		}

		if (order != null) {
			if (!order.equals("")) {
				sql = sql + " order by " + order;
			}

		}
		//System.out.println(sql);
		try {
			list = db.find(sql, EmHouseBJModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<EmHouseBJModel> getAlarmList(EmHouseBJModel m, Integer userId,
			Integer depId) {
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select  emhb_id, a.cid, gid, emhb_name, emhb_company, ownmonth, emhb_feemonth, "
				+ "emhb_startmonth, emhb_stopmonth, emhb_houseid, emhb_companyid,emhb_idcard, "
				+ "emhb_radix, emhb_reason, emhb_total, emhb_cpp, emhb_opp, emhb_addname, "
				+ "emhb_addtime, emhb_modtime, emhb_declarename,"
				+ "emhb_declaretime, emhb_ifdeclare,emhb_single, emhb_flag, emhb_flagname,"
				+ "emhb_Excelfile, emhb_remark, emhb_backreason,emhb_tapr_id,"
				+ "case emhb_ifdeclare when 0 then '未申报' when 1 then '已申報' when 2 then '申报中' "
				+ "when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '申报中' "
				+ "else '状态异常' end+case when emhb_tapr_id>0 then case when tapr_appointcon=coba_client then '' else '(中心)' end else '' end states,"
				+ "coba_client client,smwr_tid,symr_readstate "
				+ " from EmHousebj a"
				+ " inner join cobase b on a.cid=b.CID"
				+ " left join TaskProcess c on a.emhb_tapr_id=c.tapr_id"
				+ " left join (select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message where smwr_table='emhousebj' group by smwr_tid)f on a.emhb_id=f.smwr_tid "
				+ " where emhb_ifdeclare =? and ownmonth>=201508"
				+ " and a.CID in ( select cid from DataPopedom where log_id=? and  Dat_edited=1 )"
				+ " and tapr_id is not null and tapr_state!=5"
				+ " and (tapr_appointid=-1 and tapr_appointcon in (select name from dbo.GetChildren(?)) or 3=?)";

		try {
			list = db.find(sql, EmHouseBJModel.class, null,
					m.getEmhb_ifdeclare(), userId, userId, depId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询变更数据
	public List<EmHouseBJModel> getlist(Integer gid, Integer nowmonth) {
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cid,gid from emhousebj "
				+ "where gid=? and ownmonth>=? and emhb_ifdeclare in (1,2)";
		try {
			list = db.find(sql, EmHouseBJModel.class, null, gid, nowmonth);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询统计数
	public List<EmHouseBJModel> getNum(String ownmonth, String declare,
			String single) {
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select count(*)num from emhousebj "
				+ "where ownmonth=? and emhb_ifdeclare = ?";
		if (single != null) {

			if (single.equals(0)) {
				sql = sql + " and emhb_single in(0,2)";
			} else {
				sql = sql + " and emhb_single =" + single;
			}
		}
		try {
			list = db.find(sql, EmHouseBJModel.class,
					dbconn.parseSmap(EmHouseBJModel.class), ownmonth, declare);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public Integer add(EmHouseBJModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into emhousebj(ownmonth,emhb_feemonth,gid,cid,emhb_name,emhb_company,"
				+ "emhb_startmonth,emhb_stopmonth,emhb_houseid,emhb_companyid,emhb_idcard,emhb_radix,"
				+ "emhb_reason,emhb_total,emhb_cpp,emhb_opp,emhb_addname,emhb_addtime,emhb_ifdeclare,emhb_single)values("
				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,?)";
		try {
			System.out.println(em.getEmhb_radix());
			System.out.println(em.getEmhb_total());

			i = db.insertAndReturnKey(sql, em.getOwnmonth(),
					em.getEmhb_feemonth(), em.getGid(), em.getCid(),
					em.getEmhb_name(), em.getEmhb_company(),
					em.getEmhb_startmonth(), em.getEmhb_stopmonth(),
					em.getEmhb_houseid(), em.getEmhb_companyid(),
					em.getEmhb_idcard(), em.getEmhb_radix(),
					em.getEmhb_reason(), em.getEmhb_total(), em.getEmhb_cpp(),
					em.getEmhb_opp(), em.getEmhb_addname(),
					em.getEmhb_ifdeclare(), em.getEmhb_single());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 修改状态
	public Integer updateState(Integer id, Integer state, String name) {
		Integer i = 0;
		dbconn db = new dbconn();
		if (id != null && id > 0) {

			String sql = "update emhousebj set emhb_modtime=getdate(),emhb_modname=?,emhb_ifdeclare=?"
					+ " where emhb_id=?";
			try {
				i = db.updatePrepareSQL(sql, name, state, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	public Integer mod(EmHouseBJModel em, Integer id, Integer cid, Integer gid,
			Integer bstate) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update emhousebj set emhb_modtime=getdate()";
		if (em.getOwnmonth() != null) {

			sql = sql + ",ownmonth=" + em.getOwnmonth();

		}
		if (em.getEmhb_houseid() != null) {
			sql = sql + ",emhb_houseid='" + em.getEmhb_houseid() + "'";
		}
		if (em.getEmhb_startmonth() != null) {
			sql = sql + ",emhb_startmonth='" + em.getEmhb_startmonth() + "'";
		}
		if (em.getEmhb_stopmonth() != null) {
			sql = sql + ",emhb_stopmonth='" + em.getEmhb_stopmonth() + "'";
		}

		if (em.getEmhb_companyid() != null) {
			sql = sql + ",emhb_companyid='" + em.getEmhb_companyid() + "'";
		}
		if (em.getEmhb_radix() != null) {
			sql = sql + ",emhb_radix=" + em.getEmhb_radix();
		}
		if (em.getEmhb_total() != null) {
			sql = sql + ",emhb_total=" + em.getEmhb_total();
		}
		if (em.getEmhb_ifdeclare() != null) {
			sql = sql + ",emhb_ifdeclare=" + em.getEmhb_ifdeclare();
		}

		if (em.getEmhb_feemonth() != null) {
			sql = sql + ",emhb_feemonth='" + em.getEmhb_feemonth() + "'";
		}
		if (em.getEmhb_declarename() != null) {
			sql = sql + ",emhb_declarename='" + em.getEmhb_declarename() + "'";
		}
		if (em.getEmhb_declaretime() != null) {
			sql = sql + ",emhb_declaretime='" + em.getEmhb_declaretime() + "'";
		}
		if (em.getEmhb_addname() != null) {
			sql = sql + ",emhb_addname='" + em.getEmhb_addname() + "'";
		}
		if (em.getEmhb_excelfile() != null) {
			sql = sql + ",emhb_Excelfile='" + em.getEmhb_excelfile() + "'";
		}

		sql = sql + " where 1=1";
		if (id != null) {
			sql = sql + " and emhb_id=" + id;
		}
		if (gid != null) {
			sql = sql + " and gid=" + gid;
		}
		System.out.print(sql);
		try {
			i = db.updatePrepareSQL(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 申报公积金补缴
	public Integer EmhouseBjDeclare(EmHouseBJModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.parseInt(db.callWithReturn(
					"{?=call EmHousebJ_Declare_cyj(?,?,?,?,?)}", Types.INTEGER,
					m.getEmhb_id(), m.getEmhb_ifdeclare(),
					m.getEmhb_declarename(), m.getEmhb_declaretime(),
					m.getEmhb_backreason()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 时间格式转换
	private Date timechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

	public Integer delById(Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from emhousebj where emhb_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer delByGid(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from emhousebj where emhb_tapr_id=0 and gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 获取节点ID
	public Integer getTaprId(Integer id) {
		Integer i = 0;
		List<EmHouseBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emhb_tapr_id from EmHousebj where emhb_id=?";
		try {
			list = db.find(sql, EmHouseBJModel.class,
					dbconn.parseSmap(EmHouseBJModel.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i = list.get(0).getEmhb_tapr_id();

		return i;
	}

	// 更新任务流程ID
	public Integer updateTaprId(Integer dataId, Integer taprId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHousebj set emhb_tapr_id=? where emhb_id=?";
		try {
			i = db.updatePrepareSQL(sql, taprId, dataId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 退回公积金数据
	public boolean returnGJJBj(int dataid) {
		String sql = "update EmHouseBj set emhb_ifdeclare=3 where emhb_id=?";
		dbconn conn = new dbconn();
		PreparedStatement psmt = conn.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, dataid);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
}
