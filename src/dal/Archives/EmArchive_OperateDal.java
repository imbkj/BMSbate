package dal.Archives;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmArchiveRemarkModel;

public class EmArchive_OperateDal {
	private dbconn conn = new dbconn();

	// 修改档案信息
	public int updateEmArchive(EmArchiveModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmArchive_Update_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEmar_id());
			c.setString(2, m.getEmar_censusregister());
			c.setInt(3, m.getEmar_crbelongs());
			c.setString(4, m.getEmar_idcard());
			c.setString(5, m.getEmar_party());
			c.setString(6, "" + m.getEmar_partybelongs());
			c.setString(7, m.getEmar_partydate());
			c.setString(8, m.getEmar_degree());
			c.setString(9, m.getEmar_school());
			c.setString(10, m.getEmar_specialty());
			c.setString(11, m.getEmar_gradate());
			c.setString(12, m.getEmar_marrystate());
			c.setString(13, m.getEmar_fertilitystate());
			c.setString(14, m.getEmar_workdate());
			c.setString(15, m.getEmar_caste());
			c.setString(16, m.getEmar_casteassessdate());
			c.setString(17, m.getEmar_peoplefoldmode());
			c.setString(18, m.getEmar_link());
			c.setString(19, m.getEmar_inciicdate());
//			c.setInt(20, m.getEmar_specialdata());
//			c.setString(21, m.getEmar_address());
			c.setString(20, m.getEmar_archiveclass());
//			c.setString(23, m.getEmar_wtmode());
			c.setString(21, m.getEmar_archiveplace());
			c.setString(22, m.getEmar_archivesource());
			c.setString(23, m.getEmar_archivefoldreason());
			c.setString(24, m.getEmar_archivefoldmode());
			c.setString(25, m.getEmar_archivefolddate());
			c.setString(26, m.getEmar_transferorderid());
//			c.setString(30, m.getEmar_surrogatecardid());
			c.setString(27, m.getEmar_surrogateid());
			c.setString(28, m.getEmar_promisesdate());
			c.setString(29, m.getEmar_prodate());
			c.setString(30, m.getEmar_firstdeadline());
			c.setString(31, m.getEmar_continuedeadline());
//			c.setInt(36, m.getEmar_colhj());
			c.setString(32, m.getEmar_archiveremovermode());
			c.setString(33, m.getEmar_archiveremovereason());
			c.setString(34, m.getEmar_archiveremovedate());
			c.setString(35, m.getEmar_addname());
			c.setString(36, m.getEmar_archiveremoveplace());
			c.setString(37, m.getEmar_szresume());
			c.registerOutParameter(38, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(38);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 添加档案备注信息
	public int addReamrk(EmArchiveRemarkModel model) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "insert into EmArchiveRemark(eare_tid,eare_trid,eare_content,eare_addname,gid) ";
			sql = sql + " values(2," + model.getEare_trid() + ",'"
					+ model.getEare_content() + "','" + model.getEare_addname()
					+ "',"+model.getGid()+")";
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 修改档案
	public int EmArchiveEdit(EmArchiveModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_EDIT_p_cyj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getEmar_id());
			c.setString(2, m.getEmar_rspaykind());
			c.setString(3, m.getEmar_rsinvoice());
			c.setString(4, m.getEmar_hjpaykind());
			c.setString(5, m.getEmar_hjinvoice());
			c.setString(6, m.getEmar_remark());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(7);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 时间格式转换
	private Date timechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}
}
