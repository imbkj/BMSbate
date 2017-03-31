package dal.CoAgencies;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Conn.dbconn;
import Model.CoAgencyCompactModel;
import Util.UserInfo;

public class CoAg_CompactOperateDal {

	// 新增合同
	public Integer CoAgencyCompactAdd(CoAgencyCompactModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("CoAgencyCompact_Add_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, model.getCoct_compactid());
			c.setString(2, model.getCoct_coagname());
			c.setInt(3, model.getCoct_coagid());
			c.setString(4, model.getCoct_type());
			c.setString(5, model.getCoct_category());
			c.setString(6, model.getCoct_signdate());
			c.setString(7, model.getCoct_effectdate());
			c.setString(8, model.getCoct_expiredate());
			c.setInt(9, model.getCoct_autoextend());
			c.setString(10, model.getCoct_remark());
			c.setString(11, model.getCoct_addname());
			c.registerOutParameter(12, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(12);
		} catch (SQLException e) {
			System.out.println("错误信息：" + e.getMessage());
			return 0;
		}
	}

	// 更新社保卡信息表任务单id
	public boolean updateCoAgencyCompactTaprid(int taprid, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update CoAgencyCompact set coct_tarpid=? where coct_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setInt(2, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 新增合同和机构城市关系信息
	public Integer CoAgencyCompactCityAdd(Integer ctcy_coct_id,
			Integer ctcy_cabc_id, String addname) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[CoAgencyCompactCityRel_Add_P_cyj](?,?,?,?)");
			c.setInt(1, ctcy_coct_id);
			c.setInt(2, ctcy_cabc_id);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 保存合同文件名称（制作合同）
	public Integer updateComapctFilename(CoAgencyCompactModel model) {
		Integer k = 0;
		String date = getNowDate();
		String sql = "update CoAgencyCompact set coct_filename='"
				+ model.getCoct_filename() + "',coct_modtime='" + date + "'";
		sql = sql + ",coct_modname='" + model.getCoct_modname()
				+ "',coct_makename='" + model.getCoct_modname() + "' "
				+ ",coct_maketime='" + date + "',coct_state=1 where coct_id="
				+ model.getCoct_id();
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 审核合同
	public Integer AuditComapct(CoAgencyCompactModel model) {
		String date = getNowDate();
		Integer k = 0;
		String sql = "update CoAgencyCompact set coct_modtime='" + date + "'";
		sql = sql + ",coct_modname='" + model.getCoct_modname()
				+ "',coct_auditname='" + model.getCoct_modname() + "' "
				+ ",coct_audittime='" + date + "',coct_state=2 where coct_id="
				+ model.getCoct_id();
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 退回合同
	public Integer BackComapct(CoAgencyCompactModel model) {
		String date = getNowDate();
		Integer k = 0;
		String sql = "update CoAgencyCompact set coct_modtime='" + date + "'";
		sql = sql + ",coct_modname='" + model.getCoct_modname()
				+ "',coct_backname='" + model.getCoct_modname()+ "' "
				+ ",coct_backtime='" + date + "',coct_state=7,coct_backcase='"+model.getCoct_backcase()+"' where coct_id="
				+ model.getCoct_id();
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 签回合同
	public Integer SignBackComapct(CoAgencyCompactModel model) {
		String date = getNowDate();
		Integer k = 0;
		String sql = "update CoAgencyCompact set coct_modtime='" + date + "'";
		sql = sql + ",coct_modname='" + model.getCoct_modname()
				+ "',coct_signbackname='" + model.getCoct_modname() + "' "
				+ ",coct_signbacktime='" + model.getCoct_signbacktime()
				+ "',coct_state=3 where coct_id=" + model.getCoct_id();
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	// 归档合同
	public Integer ArchiveComapct(CoAgencyCompactModel model) {
		String date = getNowDate();
		Integer k = 0;
		String sql = "update CoAgencyCompact set coct_modtime='" + date + "'";
		sql = sql + ",coct_modname='" + model.getCoct_modname()
				+ "',coct_archivename='" + model.getCoct_modname() + "'"
				+ ",coct_archivetime='" + model.getCoct_signbacktime()
				+ "',coct_archivenumber='" + model.getCoct_archivenumber()
				+ "'," + "coct_ch_amount='" + model.getCoct_ch_amount()
				+ "',coct_en_amount='" + model.getCoct_en_amount() + "',"
				+ "coct_remark='" + model.getCoct_remark()
				+ "',coct_state=4 where coct_id=" + model.getCoct_id();
		dbconn db = new dbconn();
		try {
			k = db.execQuery(sql);
		} catch (Exception e) {

		}
		return k;
	}

	private String getNowDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String nowdate = df.format(new Date());// new Date()为获取当前系统时间
		return nowdate;
	}
}
