package dal.SysMessage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_EditDal {

	public SysMessageModel SysMessageSave(SysMessageModel model)
			throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		SysMessageModel model1 = new SysMessageModel();

		// 创建存储过程的对象
		csmt = db.getcall("SysMessageSave_P_ply(?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			csmt.setInt(1, model.getSyme_id());
			csmt.setString(2, model.getSyme_content());
			csmt.setInt(3, model.getSyme_state());

			// 注册存储过程的返回值
			csmt.registerOutParameter(4, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			model1.setRow(csmt.getInt(4));

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return model1;
	}

	public int deleterecipient(int syme_id) {
		int row = 0;
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		String sql = "delete from SysMessageRecipient where symr_syme_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, syme_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	public int getsymrid(int syme_id, int symr_log_id) {
		int symr_id = 0;
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sql = "select symr_id from sysmessagerecipient "
				+ "where symr_syme_id=" + syme_id + " and symr_log_id="
				+ symr_log_id;

		try {
			rs = db.GRS(sql);
			symr_id = rs.getInt("symr_id");
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return symr_id;
	}

	public List<SysMessageModel> getlist(String tbName, String username,
			Integer userid) {
		List<SysMessageModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message "
				+ " where (symr_name=? or symr_log_id=?) and smwr_table=?"
				+ " group by smwr_tid";
		try {
			list = db.find(sql, SysMessageModel.class, null, username, userid,
					tbName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取被退回数据列表(只根据数据ID不限制用户)
	public List<SysMessageModel> getbacklistById(String tbName, Integer id) {
		List<SysMessageModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message "
				+ " where smwr_table=? and smwr_tid=? group by smwr_tid";
		try {
			list = db.find(sql, SysMessageModel.class, null, tbName, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取被退回数据列表(只根据数据表不限制用户)
	public List<SysMessageModel> getbacklist(String tbName) {
		List<SysMessageModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message "
				+ " where smwr_table=? group by smwr_tid";
		try {
			list = db.find(sql, SysMessageModel.class, null, tbName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
