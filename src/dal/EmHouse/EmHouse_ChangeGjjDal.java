package dal.EmHouse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmHouseChangeGJJModel;

public class EmHouse_ChangeGjjDal {
	// 查询公积金交单申报信息
	public List<EmHouseChangeGJJModel> getEmHouse_ChangeGjjInfo(String str) {
		List<EmHouseChangeGJJModel> list = new ArrayList<EmHouseChangeGJJModel>();
		dbconn db = new dbconn();
		String sql = "SELECT  coba_shortname,emhu_houseid,emhu_idcard,"
				+ " emhu_ifstop,ehcg_content,ehcg_id,a.gid,a.cid,a.ownmonth, ehcg_company, "
				+ "ehcg_name, ehcg_change, ehcg_content, ehcg_changevalue,ehcg_tapr_id, ehcg_addtime, ehcg_addname,ehcg_ifdeclare,"
				+ " convert(varchar(10),ehcg_declaretime,120) as ehcg_declaretime , ehcg_declarename,ehcg_single, ehcg_shebaoid, ehcg_remark,ehcg_ifbase,"
				+ "ehcg_ifsb,case ehcg_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' end statename "
				+ " From EmHouseChangeGJJ a "
				+ " inner join CoBase as b on a.cid=b.cid "
				+ " inner join embase as c on a.gid=c.gid "
				+ " inner join (select distinct gid, emhu_houseid,emhu_idcard,emhu_ifstop from EmHouseUpdate)d on a.gid=d.gid "
				+ " where ehcg_tid=0 " + str
				+ " order by ehcg_ifdeclare,ehcg_addtime desc";
		try {
			list = db.find(sql, EmHouseChangeGJJModel.class,
					dbconn.parseSmap(EmHouseChangeGJJModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询公积金交单申报信息
	public List<EmHouseChangeGJJModel> getEmHouse_ChangeGjj(
			EmHouseChangeGJJModel em) {
		List<EmHouseChangeGJJModel> list = new ArrayList<EmHouseChangeGJJModel>();
		dbconn db = new dbconn();
		String sql = "SELECT  coba_shortname,emhu_houseid,emhu_idcard,emhu_companyid,"
				+ " ehcg_content,ehcg_id,a.gid,a.cid,a.ownmonth, ehcg_company, "
				+ "ehcg_name, ehcg_change, ehcg_content, ehcg_changevalue,ehcg_tapr_id, ehcg_addtime, ehcg_addname,ehcg_ifdeclare,"
				+ " ehcg_declareTime , ehcg_declareName,ehcg_single, ehcg_shebaoid, ehcg_remark,ehcg_ifbase,"
				+ "ehcg_ifsb,case ehcg_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '未确认' end statename "
				+ " From EmHouseChangeGJJ a "
				+ " inner join CoBase as b on a.cid=b.cid "
				+ " inner join embase as c on a.gid=c.gid "
				+ " inner join (select distinct gid, emhu_houseid,emhu_idcard,emhu_companyid,emhu_ifstop from EmHouseUpdate)d on a.gid=d.gid "
				+ " where ehcg_tid=0 ";
		if (em.getEhcg_id() != null) {
			if (!em.getEhcg_id().equals("")) {
				sql = sql + " and ehcg_id=" + em.getEhcg_id();
			}
		}

		if (em.getEhcg_company() != null) {
			if (!em.getEhcg_company().equals("")) {
				sql = sql + " and ehcg_company like '%" + em.getEhcg_company()
						+ "%'";
			}
		}
		if (em.getEhcg_name() != null) {
			if (!em.getEhcg_name().equals("")) {
				sql = sql + " and ehcg_name like '%" + em.getEhcg_name() + "%'";
			}
		}
		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql = sql + " and ownmonth =" + em.getOwnmonth();
			}
		}
		if (em.getEhcg_ifdeclare() != null) {
			if (!em.getEhcg_ifdeclare().equals("")) {
				sql = sql + " and ehcg_ifdeclare =" + em.getEhcg_ifdeclare();
			}
		}
		if (em.getState() != null) {
			if (em.getState().equals(2)) {
				sql += " and ehcg_ifdeclare in (0,1,2,3)";
			}
		}
		sql = sql + " order by ehcg_ifdeclare,ehcg_addtime desc";
		System.out.print(sql);
		try {
			list = db.find(sql, EmHouseChangeGJJModel.class,
					dbconn.parseSmap(EmHouseChangeGJJModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseChangeGJJModel> ownmonthList() {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct ownmonth from emhousechangegjj where ehcg_ifdeclare in (0,1,2) order by ownmonth desc";
		try {
			list = db.find(sql, EmHouseChangeGJJModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<EmHouseChangeGJJModel> searchList(EmHouseChangeGJJModel em) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "SELECT ehcg_id, gid, a.cid, ownmonth, ehcg_company, ehcg_name, ehcg_change, ehcg_content,ehcg_changevalue,"
				+ " ehcg_addtime, ehcg_addname, ehcg_ifdeclare, ehcg_declareTime, ehcg_declareName, ehcg_single, "
				+ " ehcg_shebaoID, ehcg_remark, ehcg_ifbase, ehcg_ifsb,ehcg_tapr_id,ehcg_tid,coba_client"
				+ " FROM EmHouseChangeGJJ a"
				+ " inner join cobase b on a.cid=b.cid where 1=1";
		if (em.getEhcg_id() != null) {
			if (!em.getEhcg_id().equals("")) {
				sql = sql + " and ehcg_id=" + em.getEhcg_id();
			}
		}
		if (em.getEhcg_tid() != null) {
			if (!em.getEhcg_tid().equals("")) {
				sql = sql + " and ehcg_tid=" + em.getEhcg_tid();
			}
		}
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseChangeGJJModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 更新公积金交单申报
	public int UpdateEmHouse_ChangeGjjInfo(String id, String declareid) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "update EmHouseChangeGJJ set ehcg_ifdeclare="
					+ declareid;
			sql = sql + " where ehcg_id=" + id;
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
}
