package dal.SysMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.LoginModel;
import Model.SysMessageModel;

public class SysMessage_ReplyDal {

	public LoginModel getSenderModel(int syme_id) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		LoginModel model = new LoginModel();
		String sql = "select syme_log_id,syme_addname,dep_name "
				+ "from SysMessage a left outer join Login b "
				+ "on a.syme_log_id=b.log_id left outer join department c "
				+ "on b.dep_id=c.dep_id where syme_id=" + syme_id;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setLog_id(rs.getInt("syme_log_id"));
				model.setLog_name(rs.getString("syme_addname"));
				model.setDep_name(rs.getString("dep_name"));
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return model;
	}

	public List<LoginModel> getReplyList(int syme_id,int userid) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<LoginModel> list = new ArrayList<LoginModel>();
		String sql = "select symr_log_id log_id,symr_name name,dep_name "
				+ "from SysMessageRecipient a inner join SysMessage b "
				+ "on a.symr_syme_id = b.syme_id left outer join Login c "
				+ "on a.symr_log_id=c.log_id left outer join department d "
				+ "on c.dep_id=d.dep_id where syme_id=" + syme_id
				+ " and symr_log_id<>"+userid;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				LoginModel model = new LoginModel();
				model.setLog_id(rs.getInt("log_id"));
				model.setLog_name(rs.getString("name"));
				model.setDep_name(rs.getString("dep_name"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public int updateReplyState(int symr_id, int state) {
		int row = 0;
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		String sql = "update sysmessagerecipient set symr_replystate="+state+",symr_replytime=getdate() "
				+ "where symr_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, symr_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
}
