package dal.CoServicePolicy;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoServicePolicyFileModel;
import Model.CoServicePolicyModel;
import Model.CoServicePolicyTitleModel;

public class SePy_CityPolicyOperateDal {
	// 新增服务政策
	public Integer CoServicePolicyAdd(CoServicePolicyModel model) {
		dbconn db = new dbconn();
		if (model.getSypo_cabc_id() == null) {
			model.setSypo_cabc_id(0);
		}
		if (model.getSypo_cityid() == null) {
			model.setSypo_cityid(0);
		}
		try {
			CallableStatement c = db
					.getcall("[CoServicePolicy_Add_p_cyj](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, model.getOwnmonth());
			c.setString(2, model.getSypo_title());
			c.setString(3, model.getSypo_agencies());
			c.setString(4, model.getSypo_city());
			c.setString(5, model.getSypo_type());
			c.setString(6, model.getSypo_addname());
			c.setString(7, model.getSypo_feedbackbydate());
			c.setString(8, model.getSypo_iffollow());
			c.setString(9, model.getSypo_content());
			c.setString(10, model.getSypo_remark());
			c.setString(11, model.getSypo_addname());
			c.setString(12, model.getSypo_sminwage());
			c.setString(13, model.getSypo_sminwagedate());
			c.setString(14, model.getSypo_minwage());
			c.setString(15, model.getSypo_minwagedate());
			c.setString(16, model.getSypo_minwagestandard());
			c.setString(17, model.getSypo_standarddate());
			c.setString(18, model.getSypo_hourwage());
			c.setString(19, model.getSypo_hourwagedate());
			c.setInt(20, model.getSypo_cityid());
			c.setInt(21, model.getSypo_cabc_id());
			c.setString(22, model.getSypo_class());
			c.setInt(23, model.getSypo_item_id());
			c.registerOutParameter(24, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(24);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 修改服务政策
	public Integer CoServicePolicyUpdate(CoServicePolicyModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoServicePolicy_Update_p_cyj](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, model.getOwnmonth());
			c.setString(2, model.getSypo_title());
			c.setString(3, model.getSypo_agencies());
			c.setString(4, model.getSypo_city());
			c.setString(5, model.getSypo_type());
			c.setString(6, model.getSypo_addname());
			c.setString(7, model.getSypo_feedbackbydate());
			c.setString(8, model.getSypo_iffollow());
			c.setString(9, model.getSypo_content());
			c.setString(10, model.getSypo_remark());
			c.setString(11, model.getSypo_addname());
			c.setString(12, model.getSypo_sminwage());
			c.setString(13, model.getSypo_sminwagedate());
			c.setString(14, model.getSypo_minwage());
			c.setString(15, model.getSypo_minwagedate());
			c.setString(16, model.getSypo_minwagestandard());
			c.setString(17, model.getSypo_standarddate());
			c.setString(18, model.getSypo_hourwage());
			c.setString(19, model.getSypo_hourwagedate());
			c.setInt(20, model.getSypo_id());
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 新增服务政策文件
	public Integer CoServicePolicyFileAdd(CoServicePolicyFileModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoServicePolicyFile_Add_P_cyj](?,?,?,?,?,?,?)");
			c.setInt(1, model.getFile_pono_id());
			c.setString(2, model.getFile_title());
			c.setString(3, model.getFile_type());
			c.setString(4, model.getFile_url());
			c.setString(5, model.getFile_addname());
			c.setString(6, model.getFile_remark());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 修改服务政策文件
	public Integer CoServicePolicyFileUpdate(CoServicePolicyFileModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoServicePolicyFile_Update_P_cyj](?,?,?,?,?,?,?)");
			c.setInt(1, model.getFile_id());
			c.setString(2, model.getFile_title());
			c.setString(3, model.getFile_type());
			c.setString(4, model.getFile_url());
			c.setString(5, model.getFile_addname());
			c.setString(6, model.getFile_remark());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 根据Id把CoServicePolicy表的状态改为0
	public Integer updateCoServicePolicyState(Integer id) {
		Integer k = 0;
		String sql = "update CoServicePolicy set sypo_state=0 where sypo_id="
				+ id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 根据Id把CoServicePolicyFile表的状态改为0
	public Integer updateCoServicePolicyFileState(Integer id) {
		Integer k = 0;
		String sql = "update CoServicePolicyFile set file_state=0 where file_pono_id="
				+ id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	public Integer updateCoServicePolicyType(String note_type,
			Integer note_order, Integer note_id) {
		Integer k = 0;
		String sql = "update CoServicePolicyType set note_type='" + note_type
				+ "'," + "note_order=" + note_order + " where note_id="
				+ note_id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 服务政策标题新增
	public Integer CoServicePolicyTitleAdd(CoServicePolicyTitleModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoServicePolicyTitle_Add_p_cyj](?,?,?,?,?)");
			c.setInt(1, model.getItem_type_id());
			c.setString(2, model.getItem_title());
			c.setString(3, model.getItem_addname());
			c.setInt(4, model.getItem_inftype());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 根据Id把CoServicePolicyTitle表的状态改为0
	public Integer updateCoServicePolicyTitleState(Integer id) {
		Integer k = 0;
		String sql = "update CoServicePolicyTitle set item_state=0 where item_id="
				+ id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}
}
