package dal.EmHouse;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import com.sun.jmx.remote.util.OrderClassLoaders;

import Conn.dbconn;
import Model.EmHouseErrMonthModel;
import Util.UserInfo;

public class EmHouseErrMonthDal {
	/**
	 * @Title: getListByOwnmonth
	 * @Description: TODO(检查当前月份是否有台前逻辑检查数据)
	 * @return
	 * @return List<EmHouseErrMonthModel> 返回类型
	 * @throws
	 */
	public List<EmHouseErrMonthModel> getListByOwnmonth() {
		List<EmHouseErrMonthModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from EmHouseErrMonth where ownmonth in (select top 1 ownmonth from emhouseupdate)";
		try {
			list = db.find(sql, EmHouseErrMonthModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseErrMonthModel> getList(EmHouseErrMonthModel em,
			boolean distinct, String columns) {
		List<EmHouseErrMonthModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ";
		if (distinct && columns != null && !columns.equals("")) {
			sql = sql + " distinct " + columns;
		} else {
			sql = sql
					+ "a.*,coba_shortname,coba_company,coba_client,case emhe_single when 0 then '中智大户' else '独立开户' end single,cohf_tsday";
		}
		sql = sql
				+ " from EmHouseErrMonth a"
				+ " left join cobase b on a.cid=b.cid"
				+ " left join CoHousingFund c on a.emhe_companyid=c.cohf_houseid and a.emhe_single=c.cohf_single "
				+ " and ((cohf_single=1 and b.cid=c.cid) or (cohf_single=0 and c.cid is null))"
				+ " where 1=1";
		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql = sql + " and a.ownmonth=" + em.getOwnmonth();
			}
		}
		if (em.getCohf_tsday() != null) {
			if (!em.getCohf_tsday().equals("")) {
				sql = sql + " and cohf_tsday=" + em.getCohf_tsday();
			}
		}

		if (em.getEmhe_single() != null) {
			if (!em.getEmhe_single().equals("")) {
				sql = sql + " and emhe_single=" + em.getEmhe_single();
			}
		}
		if (em.getEmhe_err() != null) {
			if (!em.getEmhe_err().equals("")) {
				sql = sql + " and emhe_err like '%" + em.getEmhe_err() + "%'";
			}
		}
		if (em.getCoba_client() != null) {
			if (!em.getCoba_client().equals("")) {
				sql = sql + " and coba_client like '%" + em.getCoba_client()
						+ "%'";
			}
		}
		if (em.getCoba_shortname() != null) {
			if (!em.getCoba_shortname().equals("")) {
				sql = sql + " and coba_shortname like '%"
						+ em.getCoba_shortname() + "%'";
			}
		}
		if (em.getEmhe_name() != null) {
			if (!em.getEmhe_name().equals("")) {
				sql = sql + " and emhe_name like '%" + em.getEmhe_name() + "%'";
			}
		}
		if (em.getEmhe_idcard() != null) {
			if (!em.getEmhe_idcard().equals("")) {
				sql = sql + " and emhe_idcard like '%" + em.getEmhe_idcard()
						+ "%'";
			}
		}
		if (em.getEmhe_houseid() != null) {
			if (!em.getEmhe_houseid().equals("")) {
				sql = sql + " and emhe_houseid like '%" + em.getEmhe_houseid()
						+ "%'";
			}
		}

		sql = sql + " order by ";

		if (distinct && columns != null && !columns.equals("")) {
			sql = sql + columns;
		} else {
			sql = sql
					+ "a.ownmonth,emhe_err,emhe_single,coba_shortname,emhe_name";
		}
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseErrMonthModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer checkerr() {
		dbconn db = new dbconn();
		Integer i = 0;
		try {
			i = (Integer) db.callWithReturn(
					"{?=call EmHouse_GJJErrMonth_P_py(?)}", Types.INTEGER,
					UserInfo.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer Del() {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "delete from EmHouseErrMonth where ownmonth in (select top 1 ownmonth from emhouseupdate)";
		try {
			i = db.updatePrepareSQL(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public Integer freshData() {
		dbconn db = new dbconn();
		Integer i = 0;
		try {
			i = (Integer) db.callWithReturn(
					"{?=call EmHouse_GJJErrMonth_P_py()}", Types.INTEGER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
