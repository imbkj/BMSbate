package dal.CoPolicyNotice;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;

public class PoNo_OperateDal {
	// 新增政策通知
	public Integer CoPolicyNoticeAdd(CoPolicyNoticeModel model) {
		dbconn db = new dbconn();
		if (model.getPono_cityid() == null) {
			model.setPono_cityid(0);
		}
		if (model.getPono_cabc_id() == null) {
			model.setPono_cabc_id(0);
		}
		try {
			CallableStatement c = db
					.getcall("[CoPolicyNotice_Add_P_cyj](?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, model.getOwnmonth());
			c.setString(2, model.getPono_title());
			c.setString(3, model.getPono_agencies());
			c.setString(4, model.getPono_city());
			c.setString(5, model.getPono_type());
			c.setString(6, model.getPono_addname());
			c.setString(7, model.getPono_feedbackbydate());
			c.setString(8, model.getPono_iffollow());
			c.setString(9, model.getPono_content());
			c.setString(10, model.getPono_remark());
			c.setString(11, model.getPono_addname());
			c.setInt(12, model.getPono_cityid());
			c.setInt(13, model.getPono_cabc_id());
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 修改政策通知
	public Integer CoPolicyNoticeUpdate(CoPolicyNoticeModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoPolicyNotice_Update_P_cyj](?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, model.getOwnmonth());
			c.setString(2, model.getPono_title());
			c.setString(3, model.getPono_agencies());
			c.setString(4, model.getPono_city());
			c.setString(5, model.getPono_type());
			c.setString(6, model.getPono_addname());
			c.setString(7, model.getPono_feedbackbydate());
			c.setString(8, model.getPono_iffollow());
			c.setString(9, model.getPono_content());
			c.setString(10, model.getPono_remark());
			c.setString(11, model.getPono_addname());
			c.setInt(12, model.getPono_id());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 新增政策通知文件
	public Integer CoPolicyNoticeFileAdd(CoPolicyNoticeFileModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoPolicyNoticeFile_Add_P_cyj](?,?,?,?,?,?,?)");
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

	// 新增政策通知文件
	public Integer CoPolicyNoticeFileUpdate(CoPolicyNoticeFileModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoPolicyNoticeFile_update_P_cyj](?,?,?,?,?,?,?)");
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

	// 根据Id把CoPolicyNotice表的状态改为0
	public Integer updateCoPolicyNoticeState(Integer id) {
		Integer k = 0;
		String sql = "update CoPolicyNotice set pono_state=0 where pono_id="
				+ id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 根据Id把CoPolicyNoticeFile表的状态改为0
	public Integer updateCoPolicyNoticeFileState(Integer id) {
		Integer k = 0;
		String sql = "update CoPolicyNoticeFile set file_state=0 where file_pono_id="
				+ id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 业务与通知信息关联
	public Integer CoPolicyNoticeRelation(CoPolicyNoticeModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoPolicyNoticeRelation_Add_P_cyj](?,?,?,?,?)");
			c.setInt(1, model.getPono_id());
			c.setString(2, model.getCpnr_type());
			c.setInt(3, model.getCpnr_data_id());
			c.setString(4, model.getCpnr_addname());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 根据业务类型和业务表id删除信息
	public Integer delNoticeRelation(String type, Integer id) {
		Integer k = 0;
		String sql = "delete from CoPolicyNoticeRelation where Cpnr_type='"
				+ type + "' and Cpnr_data_id=" + id;
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}
}
