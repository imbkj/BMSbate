package dal.Embase;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class Embase_ReEntryOperateDal {
	// 重新入职——恢复入职
	public Integer EmBase_ReEntry(Integer gid, String addname) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db.getcall("EmBase_ReEntry_p_cyj(?,?,?)");
			c.setInt(1, gid);
			c.setString(2, addname);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			System.out.println("数据库操作错误：" + e.getMessage());
			return 0;
		}
	}

	// 删除商保change的数据
	public Integer deletesahngbao(Integer id) {
		Integer k = 0;
		dbconn db = new dbconn();
		try {
			String sql = "delete from EmComInsuranceChange where ecic_id=" + id;
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 删除劳动合同change的数据
	public Integer deletesEmCompact(Integer id) {
		Integer k = 0;
		dbconn db = new dbconn();
		try {
			String sql = "delete from EmBaseCompactChange where ebcc_id=" + id;
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 根据emshebaochange的id把社保在册表的数据更新
	public Integer updateEmshebao(Integer id) {
		Integer k = 0;
		dbconn db = new dbconn();
		try {
			String sql = "update emshebaoupdate set esiu_ifstop=0 "
					+ "where esiu_ifstop=1 and gid in(select gid "
					+ "from EmSheBaoChange where ID=" + id + ")";
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 根据emhousechange的id把公积金在册表的数据更新
	public Integer updateEmHouse(Integer id) {
		Integer k = 0;
		dbconn db = new dbconn();
		try {
			String sql = "update EmHouseUpdate set emhu_ifstop=0  "
					+ "where emhu_ifstop=1 and gid in(select gid "
					+ "from EmHouseChange where emhc_id=" + id + ")";
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 根据emhousechange的id把公积金在册表的数据更新
	public Integer deleteEmHouse(Integer id) {
		Integer k = 0;
		dbconn db = new dbconn();
		try {
			String sql = "delete from EmHouseChange where emhc_id=" + id;
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}
}
