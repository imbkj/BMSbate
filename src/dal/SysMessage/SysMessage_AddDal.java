package dal.SysMessage;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoOfferModel;
import Model.DepartmentListModel;
import Model.LoginModel;
import Model.SysMessageModel;

public class SysMessage_AddDal {

	public List<LoginModel> getLoginList(String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<LoginModel> list = new ArrayList<LoginModel>();
		String sql = "select * from login a left outer join department b on a.dep_id=b.dep_id "
				+ "where log_inure=1 " + str + " order by log_id";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				LoginModel model = new LoginModel();
				model.setLog_id(rs.getInt("log_id"));
				model.setLog_name(rs.getString("log_name"));
				model.setDep_id(rs.getInt("dep_id"));
				model.setDep_name(rs.getString("dep_name"));
				model.setLog_mobile(rs.getString("log_mobile"));
				model.setLog_email(rs.getString("log_email"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public List<DepartmentListModel> getdeplist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<DepartmentListModel> list = new ArrayList<DepartmentListModel>();
		String sql = "select 0 dep_id,'' dep_name union select dep_id,dep_name from department";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				DepartmentListModel model = new DepartmentListModel();
				model.setDep_id(rs.getInt("dep_id"));
				model.setDep_name(rs.getString("dep_name"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public SysMessageModel SysMessageAdd(SysMessageModel model)
			throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		SysMessageModel model1 = new SysMessageModel();

		// 创建存储过程的对象
		csmt = db.getcall("SysMessageAdd_P_ply(?,?,?,?,?,?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			csmt.setString(1, model.getSyme_content());
			csmt.setString(2, model.getSyme_addname());
			csmt.setInt(3, model.getSyme_state());
			csmt.setInt(4, model.getSyme_log_id());
			csmt.setInt(5, model.getSyme_reply_id());
			csmt.setString(6,model.getSyme_title());
			csmt.setString(7, model.getSyme_url());
			csmt.setString(8, model.getSyme_para());

			// 注册存储过程的返回值
			csmt.registerOutParameter(9, java.sql.Types.INTEGER);
			csmt.registerOutParameter(10, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			model1.setRow(csmt.getInt(9));
			model1.setSyme_id(csmt.getInt(10));

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return model1;
	}

	public int SysMessageRecpAdd(SysMessageModel model) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		int row = 0;

		// 创建存储过程的对象
		csmt = db.getcall("SysMessageRecpAdd_P_ply(?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			csmt.setInt(1, model.getSymr_syme_id());
			csmt.setInt(2, model.getSymr_log_id());
			csmt.setString(3, model.getSymr_name());
			csmt.setInt(4, model.getSymr_state());

			// 注册存储过程的返回值
			csmt.registerOutParameter(5, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			row = csmt.getInt(5);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return row;
	}
}
