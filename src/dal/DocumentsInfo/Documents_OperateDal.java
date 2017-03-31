package dal.DocumentsInfo;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import Conn.dbconn;

public class Documents_OperateDal {
	private dbconn conn = new dbconn();

	// 新增或修改业务材料提交情况表(DocumentsSubmitInfo)
	public int addDocSubmitInfo(String dire_id, String tid, String ifsubmit,
			String count, String handover_people, String addname) {
		try {
			CallableStatement c = conn
					.getcall("DocSubmitInfoAdd_p_lsb(?,?,?,?,?,?,?)");
			c.setString(1, dire_id);
			c.setString(2, tid);
			c.setString(3, ifsubmit);
			c.setString(4, count);
			c.setString(5, handover_people);
			c.setString(6, addname);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(7);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 新增或修改业务材料提交情况表(DocumentsSubmitInfo)
	public int addDocSubmitInfo(String dire_id, String tid, String ifsubmit,
			String count, String handover_people, String addname,
			String out_count) {
		try {
			CallableStatement c = conn
					.getcall("DocSubmitInfoAdd_p_cyj(?,?,?,?,?,?,?,?)");
			c.setString(1, dire_id);
			c.setString(2, tid);
			c.setString(3, ifsubmit);
			c.setString(4, count);
			c.setString(5, handover_people);
			c.setString(6, addname);
			c.setString(7, out_count);
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(8);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 创建业务材料提交情况表空白数据(DocumentsSubmitInfo)
	public Integer addBlankInfo(Integer puzu_id, Integer daid, String username)
			throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer.valueOf(db.callWithReturn("{?=call DocAdd_p_pub(?,?,?)}",
				Types.INTEGER, puzu_id, daid, username).toString());
		return i;

	}

	public Integer getIfSubmit(Integer puzu_id, Integer ifsubmit, String type) {
		Integer i = 0;
		String str = "";
		if ("f".equals(type)) {
			str = " ORDER BY dost_order";
		} else if ("h".equals(type)) {
			str = " ORDER BY dost_order DESC";
		}

		String sql = "select * from DocumentState where dost_ifdelete=0 and dost_puzu_id="
				+ String.valueOf(puzu_id) + str;
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				if (ifsubmit == rs.getInt("dost_dsin_ifsubmit")) {
					rs.next();
					i = rs.getInt("dost_dsin_ifsubmit");
					break;
				} else {
					i = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public int deleteDoc(String puzu_id, String tid) {
		try {
			CallableStatement c = conn.getcall("DocDelete_p_lsb(?,?,?)");
			c.setString(1, puzu_id);
			c.setString(2, tid);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	public int chenckDocSubmitInfo(int dire_id, int tid) {
		int ifsubmit = 0;

		String sql = "select count(*) as count from DocumentsSubmitInfo_Dire_DocInfo_V "
				+ "where dire_puzu_id=" + dire_id + " and dsin_tid=" + tid;

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {

				ifsubmit = rs.getInt("count");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ifsubmit;

	}

	public Integer addDoc(String name, Integer puzuId, String addname) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=DocAdd_p_py(?,?,?)}", Types.INTEGER, name,
					puzuId, addname).toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
