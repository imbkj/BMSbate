package dal.EmHouse;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmHouseGJJModel;
import Model.EmHouseGJJMonthModel;
import Util.CheckString;

public class EmHouseGJJDal {

	// 查询列表
	public List<EmHouseGJJModel> getList(String name) {
		List<EmHouseGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.ownmonth,emhu_company,a.emhu_companyid,cohf_cpp,a.emhu_single,cohf_client,"
				+ " num,emhu_total,isnull(num2,0)num2,convert(money,isnull(emhu_total2,0))emhu_total2 "
				+ " from ("
				+ "		select  a.ownmonth,cohf_cpp,case a.emhu_single when 1 then cohf_client else null end cohf_client, emhu_company,a.emhu_companyid,a.emhu_single,COUNT(*)num,sum(emhu_total)emhu_total"
				+ " 	from EmHouseGJJ a"
				+ " 	inner join CoHousingFund b on a.emhu_companyid=b.cohf_houseid and cohf_state=1"
				+ " 	group by a.ownmonth,cohf_cpp,emhu_company,a.emhu_companyid,a.emhu_single,case a.emhu_single when 1 then cohf_client else null end"
				+ "	) as a "
				+ " left join ("
				+ "		select ownmonth,emhu_companyid,emhu_cpp,case emhu_single when 1 then 1 else 0 end emhu_single,COUNT(*)num2,SUM(emhu_radix*emhu_cpp*2)emhu_total2 "
				+ " 	from EmHouseUpdate"
				+ " 		where ownmonth in (select top 1 ownmonth from emhousegjj) and emhu_ifstop=0"
				+ "		group by ownmonth,emhu_companyid,emhu_cpp,case emhu_single when 1 then 1 else 0 end"
				+ ")c on a.emhu_single=c.emhu_single and a.ownmonth=c.ownmonth and a.emhu_companyid=c.emhu_companyid and a.cohf_cpp=c.emhu_cpp"
				+ " where 1=1";
		if (!name.equals("")) {
			if (CheckString.isNum(name)) {
				sql = sql + " and (a.cid=" + name + " or a.emhu_companyid="
						+ name + ")";
			} else {
				sql = sql + " and emhu_company like '%" + name + "%'";
			}
		}

		sql = sql + " order by emhu_single,cohf_cpp,emhu_company";
		System.out.println(sql);
		try {
			list = db.find(sql.toString(), EmHouseGJJModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询人数
	public Integer getNum(String name, Integer type) {
		Integer i = 0;
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*)num "
				+ "from EmHouseGJJ a "
				+ "inner join (select distinct cid,cohf_houseid,cohf_cpp,cohf_single from CoHousingFund where cohf_state=1) b on a.emhu_companyid=b.cohf_houseid and ((cohf_single=1 and a.cid=b.cid) or (cohf_single=0 and b.cid is null))");
		sql.append(" where 1=1");

		if (type != null) {
			if (type.equals(0)) {
				sql.append(" and emhu_single in(0,2)");
			} else {
				sql.append(" and emhu_single=" + type);
			}

		}

		if (!name.equals("")) {
			if (CheckString.isNum(name)) {
				sql.append(" and (a.cid=" + name + " or emhu_companyid=" + name
						+ ")");
			} else {
				sql.append(" and emhu_company like '%" + name + "%'");
			}
		}

		try {
			i = Integer.valueOf(db.findReturn(sql.toString()).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public Integer getRecordByCompanyId(String companyid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "select count(*)n from EmHouseGJJ where emhu_companyid=?";
		try {
			i = (Integer) db.findReturn(sql, companyid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// 查询公积金单位数据是否已上传
	public Integer getRecordByOwnmonth() {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "select count(*)n from EmHouseGJJ where ownmonth in (select top 1 ownmonth from emhouseupdate)";
		try {
			i = (Integer) db.findReturn(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public List<EmHouseGJJModel> getListByCompanyid(String companyid) {
		List<EmHouseGJJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from EmHouseGJJ where emhu_companyid=?";
		try {
			list = db.find(sql, EmHouseGJJModel.class, null, companyid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 添加数据
	public Integer add(EmHouseGJJModel ehgm) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into EmHouseGJJ(emhu_CompanyID,emhu_Company,emhu_idcard,ownmonth,emhu_HouseID,emhu_name,emhu_radix,emhu_total,emhu_filename,emhu_single,emhu_addname)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			i = db.insertAndReturnKey(sql, ehgm.getEmhu_companyid(),
					ehgm.getEmhu_company(), ehgm.getEmhu_idcard(),
					ehgm.getOwnmonth(), ehgm.getEmhu_houseid(),
					ehgm.getEmhu_name(), ehgm.getEmhu_radix(),
					ehgm.getEmhu_total(), ehgm.getEmhu_filename(),
					ehgm.getEmhu_single(), ehgm.getEmhu_addname());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 更新台后数据
	public void update() {

		dbconn db = new dbconn();
		try {
			db.callWithReturn("{?=call EmHouse_FeeExcelin_P_py}", Types.INTEGER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Integer mod() {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update a set gid=b.gid,cid=b.cid,emhu_cpp=b.emhu_cpp,emhu_opp=b.emhu_opp,"
				+ "emhu_cp=b.emhu_cp,emhu_op=b.emhu_op,emhu_hj=b.emhu_hj,emhu_computerid=b.emhu_computerid,"
				+ "emhu_mobile=b.emhu_mobile,emhu_title=b.emhu_title,emhu_wifename=b.emhu_wifename,emhu_wifeidcard=b.emhu_wifeidcard,"
				+ "emhu_degree=b.emhu_degree,emhu_single=case a.emhu_company when '深圳中智经济技术合作有限公司' then 0 else 1 end"
				+ " from EmHouseGJJ a"
				+ " inner join (select * from EmHouseUpdate where emhu_id in (select MAX(emhu_id) from EmHouseUpdate group by emhu_idcard2)) b on a.emhu_idcard2 =b.emhu_idcard2";
		try {
			i = db.updatePrepareSQL(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 清空数据
	public Integer clear() {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "truncate table EmHouseGJJ";
		i = db.execQuery(sql);

		return i;
	}

	/**
	 * @Title: Del
	 * @Description: TODO(按单位编号删除删除数据)
	 * @param id
	 *            单位编号
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer Del(String id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmHouseGJJ where emhu_companyid=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
