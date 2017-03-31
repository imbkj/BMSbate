package dal;

import java.sql.SQLException;

import Conn.dbconn;

public class PubOfficeDal {
	// 添加数据
	public Integer add(Integer type, Integer tid, String url, Integer state,
			String addname) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into PubOffice(puof_type, puof_tid, puof_url, puof_state, puof_addname, puof_addtime)"
				+ "values(?,?,?,?,?,getdate())";
		try {
			i = db.insertAndReturnKey(sql, type, tid, url, state, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 添加数据
	public Integer add(Integer type, Integer tid, Integer cid, Integer gid,
			String url, Integer state, String addname, String addtime) {
		Integer i = 0;

		return i;
	}
}
