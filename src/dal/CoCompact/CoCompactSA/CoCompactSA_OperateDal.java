package dal.CoCompact.CoCompactSA;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Conn.dbconn;
import Model.CoCompactSAModel;

public class CoCompactSA_OperateDal {
	private dbconn conn = new dbconn();

	// 任务单编号更新
	public int updatetaskid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update CoCompactSA  set ccsa_tapr_id=" + tapr_id
				+ " where ccsa_id=" + ccsa_id + "";
		dbconn update = new dbconn();
		try {

			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}

	// 公司合同签回
	public int signCoCompactSA(int ccsa_id, CoCompactSAModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoCompactSASign_P_lsb(?,?,?,?,?)");
			c.setInt(1, ccsa_id);
			c.setString(2, m.getCcsa_returndate());
			c.setString(3, m.getCcsa_signdate());
			c.setString(4, m.getCcsa_signplace());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 公司合同归档
	public int returnCoCompactSA(int coco_id, CoCompactSAModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoCompactSAReturn_P_lsb(?,?,?,?,?,?,?)");
			c.setInt(1, coco_id);
			c.setString(2, m.getCcsa_filedate());
			c.setString(3, m.getCcsa_fileid());
			c.setString(4, m.getCcsa_chs_copies());
			c.setString(5, m.getCcsa_en_copies());
			c.setString(6, m.getCcsa_remark());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 合同补充协议修改
	public boolean UpCoCompactSA(CoCompactSAModel model) {
		try {
			String sql = "update CoCompactSA set ccsa_inuredate=?,ccsa_remark=? where ccsa_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setString(1, model.getCcsa_inuredate());
			psmt.setString(2, model.getCcsa_remark());
			psmt.setInt(3, model.getCcsa_id());
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 更新数据状态
	public boolean UpCoCompactSA(int ccsa_state, int ccsa_id) {
		try {
			// 更新补充协议表状态
			String sql = "update CoCompactSA set ccsa_state=?,ccsa_auditingdate=getdate(),ccsa_coli_state=1 where ccsa_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, ccsa_state);
			psmt.setInt(2, ccsa_id);
			psmt.executeUpdate();

			// 更新操作记录表状态
			String sql2 = "update coofferlistchange set colc_state=1 from CoCompactSA where ccsa_id=colc_ccsa_id and ccsa_id=?";
			PreparedStatement psmt2 = conn.getpre(sql2);
			psmt2.setInt(1, ccsa_id);
			psmt2.executeUpdate();

			dbconn db = new dbconn();
			ResultSet rs = null;
			String sqlco = "select coli_parid from CoOfferList where coli_amount=2 and coli_coco_id="
					+ ccsa_id;
			try {
				System.out.println(sqlco);
				rs = db.GRS(sqlco);
				while (rs.next()) {
					// 将新的产品表状态更新为1，生效
					String sql7 = "update coofferlist set coli_state=1 where coli_id=? and coli_parid=0";
					PreparedStatement psmt7 = conn.getpre(sql7);
					psmt7.setString(1, rs.getString("coli_parid"));
					psmt7.executeUpdate();
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			}

			// 将旧的产品表状态更新为0，禁止
			String sql3 = "update coofferlist set coli_state=0 from CoCompactSA a left join coofferlistchange b on b.colc_ccsa_id=a.ccsa_id where coli_id=colc_coli_id and ccsa_id=?";
			PreparedStatement psmt3 = conn.getpre(sql3);
			psmt3.setInt(1, ccsa_id);
			psmt3.executeUpdate();

			// 将新的产品表状态更新为1，生效
			String sql4 = "update coofferlist set coli_state=1 from CoCompactSA a left join coofferlistchange b on b.colc_ccsa_id=a.ccsa_id where coli_id=colc_ncoli_id and ccsa_id=?";
			PreparedStatement psmt4 = conn.getpre(sql4);
			psmt4.setInt(1, ccsa_id);
			psmt4.executeUpdate();

			// 将旧的coglist表状态更新为0，禁止
			String sql5 = "update coglist set cgli_state=0 from CoCompactSA a left join coofferlistchange b on b.colc_coli_id=a.ccsa_id where cgli_coli_id=colc_coli_id and ccsa_id=? and cgli_stopdate is not null";
			PreparedStatement psmt5 = conn.getpre(sql5);
			psmt5.setInt(1, ccsa_id);
			psmt5.executeUpdate();

			// 将新的coglist表状态更新为1，生效
			String sql6 = "update coglist set cgli_state=1 from CoCompactSA a left join coofferlistchange b on b.colc_coli_id=a.ccsa_id where cgli_coli_id=colc_coli_id and ccsa_id=? and cgli_stopdate is not null";
			PreparedStatement psmt6 = conn.getpre(sql6);
			psmt6.setInt(1, ccsa_id);
			psmt6.executeUpdate();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 打印状态
	public boolean PrintCoCompactSA(int ccsa_state, int ccsa_id, int rwd) {
		try {
			String sql = "update CoCompactSA set ccsa_state=?,ccsa_printdate=getdate(),ccsa_save=? where ccsa_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			System.out.println(sql);
			psmt.setInt(1, ccsa_state);
			if (rwd == 1) {
				psmt.setString(2, "导出");
			} else {
				psmt.setString(2, "申请盖章");
			}
			psmt.setInt(3, ccsa_id);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 公司合同退回
	public int outCoCompact(int coco_id, String remark) {
		try {

			CallableStatement c = conn.getcall("CoCompactSAOut_P_zzq(?,?,?)");
			c.setInt(1, coco_id);
			c.setString(2, remark);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 部门经理审核
	public boolean KfAutCoCompactSA(int ccsa_state, int ccsa_id) {
		try {
			String sql = "update CoCompactSA set ccsa_state=? where ccsa_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			System.out.println(sql);
			psmt.setInt(1, ccsa_state);
			psmt.setInt(2, ccsa_id);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
