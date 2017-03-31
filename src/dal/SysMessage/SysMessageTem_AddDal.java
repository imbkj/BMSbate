package dal.SysMessage;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SysMessageModel;
import Model.WfClassModel;

public class SysMessageTem_AddDal {

	public SysMessageModel PubMessageTemAdd(SysMessageModel model)
			throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		SysMessageModel model1 = new SysMessageModel();

		// 创建存储过程的对象
		csmt = db.getcall("PubMessageTemAdd_P_ply(?,?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			csmt.setString(1, model.getPmte_model());
			csmt.setString(2, model.getPmte_class());
			csmt.setString(3, model.getPmte_content());
			csmt.setString(4, model.getPmte_addname());
			csmt.setString(5, model.getPmte_type());

			// 注册存储过程的返回值
			csmt.registerOutParameter(6, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			model1.setRow(csmt.getInt(6));

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return model1;
	}

	public List<WfClassModel> getwfclassList() {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<WfClassModel> list = new ArrayList<WfClassModel>();
		String sql = "select 0 wfcl_id,'' wfcl_name union "
				+ "select wfcl_id,wfcl_name from WfClass where wfcl_state=1";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				WfClassModel model = new WfClassModel();
				model.setWfcl_id(rs.getInt("wfcl_id"));
				model.setWfcl_name(rs.getString("wfcl_name"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
}
