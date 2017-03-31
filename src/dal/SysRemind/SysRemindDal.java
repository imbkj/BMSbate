package dal.SysRemind;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.PubRemindModel;
import Util.plyUtil;

public class SysRemindDal {

	public int PubRemindAdd(PubRemindModel prModel) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		int row = 0;

		// 创建存储过程的对象
		csmt = db.getcall("PubRemindAdd_P_ply(?,?,?,?,?,?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			csmt.setString(1, prModel.getContent());
			csmt.setString(2, prModel.getMobile());
			csmt.setString(3, prModel.getEmail());
			csmt.setString(4, prModel.getRemindtime());
			csmt.setString(5, prModel.getRemindname());
			csmt.setInt(6, prModel.getSmsid());
			csmt.setInt(7, prModel.getEmailid());
			csmt.setString(8, prModel.getAddname());
			csmt.setInt(9, prModel.getLog_id());

			// 注册存储过程的返回值
			csmt.registerOutParameter(10, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			row = csmt.getInt(10);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return row;
	}

	public PubRemindModel getRemindCount(int log_id) {
		ResultSet rs = null;
		PubRemindModel prModel = new PubRemindModel();
		dbconn db = new dbconn();
		String sql = "SELECT (select COUNT(*) from pubremind where log_id="
				+ log_id + " and state=0) notRemind,"
				+ "(select COUNT(*) from pubremind where log_id=" + log_id
				+ " and state=1) isRemind";

		try {
			rs = db.GRS(sql);

			while (rs.next()) {
				prModel.setNotRemindcount(rs.getInt("notremind"));
				prModel.setIsRemindcount(rs.getInt("isremind"));
			}

			db.Close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return prModel;
	}

	public PubRemindModel getNextRemind(int log_id) {
		ResultSet rs = null;
		PubRemindModel prModel = new PubRemindModel();
		dbconn db = new dbconn();
		String sql = "select top 1 CONVERT(nvarchar(16),remindtime,120) remindtime,"
				+ "content from pubremind where log_id="
				+ log_id
				+ " and state=0 order by id desc";

		try {
			rs = db.GRS(sql);

			while (rs.next()) {
				prModel.setRemindtime(rs.getString("remindtime"));
				prModel.setContent(rs.getString("content"));
			}

			db.Close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return prModel;
	}

	public List<PubRemindModel> getRemindList(int log_id, int state) {
		ResultSet rs = null;
		List<PubRemindModel> list = new ArrayList<PubRemindModel>();
		dbconn db = new dbconn();
		String sql = "select id,content,remindname,CONVERT(nvarchar(16),remindtime,120) remindtime,"
				+ "addname,CONVERT(nvarchar(16),addtime,120) addtime,"
				+ "log_id,state,case when smsid=0 and emailid<>0 then 'email' "
				+ "when smsid<>0 and emailid=0 then '手机短信' when smsid<>0 "
				+ "and emailid<>0 then '手机短信、email' else '' end as sendclass "
				+ "from pubremind where log_id="
				+ log_id
				+ " and state="
				+ state + " order by id desc";

		try {
			rs = db.GRS(sql);

			while (rs.next()) {
				PubRemindModel prModel = new PubRemindModel();
				prModel.setId(rs.getInt("id"));
				prModel.setContent(rs.getString("content"));
				prModel.setRemindname(rs.getString("remindname"));
				prModel.setRemindtime(rs.getString("remindtime"));
				prModel.setAddname(rs.getString("addname"));
				prModel.setAddtime(rs.getString("addtime"));
				prModel.setLog_id(rs.getInt("log_id"));
				prModel.setState(rs.getInt("state"));
				prModel.setSendclass(rs.getString("sendclass"));
				list.add(prModel);
			}

			db.Close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
}
