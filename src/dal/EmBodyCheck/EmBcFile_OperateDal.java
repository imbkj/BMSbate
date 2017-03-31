package dal.EmBodyCheck;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmbodyCheckFileModel;

public class EmBcFile_OperateDal {
	// 体检文件新增
	public Integer EmbodyCheckFileAdd(EmbodyCheckFileModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[EmbodyCheckFile_Add_p_cyj](?,?,?,?,?,?,?)");
			c.setInt(1, m.getFile_ebcs_id());
			c.setInt(2, m.getFile_ebsa_id());
			c.setString(3, m.getFile_url());
			c.setString(4, m.getFile_remark());
			c.setString(5, m.getFile_addname());
			c.setString(6, m.getFile_filename());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 体检文件修改
	public Integer EmbodyCheckFileEdit(EmbodyCheckFileModel m) {
		dbconn db = new dbconn();
		Integer k = 0;
		try {
			String sql = "update EmbodyCheckFile set file_url='"
					+ m.getFile_url() + "'," + "file_filename='"
					+ m.getFile_filename() + "',file_remark='"
					+ m.getFile_remark() + "'" + "where file_id="
					+ m.getFile_id();
			k = db.execQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return k;
	}
}
