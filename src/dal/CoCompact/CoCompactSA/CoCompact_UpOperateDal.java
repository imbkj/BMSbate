package dal.CoCompact.CoCompactSA;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoCompactSAModel;

public class CoCompact_UpOperateDal {
	private dbconn conn = new dbconn();

	// 任务单编号更新
	public int updatetaskid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update CoFileAuditing  set cfau_tapr_id=" + tapr_id
				+ " where cfau_id=" + ccsa_id + "";
		dbconn update = new dbconn();
		try {

			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}

	// 公司合同签回
	public int signCoCompactSA(int ccsa_id, CoCompactSAModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoCompactSASign_P_lsb(?,?,?,?,?)");
			c.setInt(1, ccsa_id);
			c.setString(2, m.getCcsa_returndate());
			c.setString(3, m.getCcsa_signdate());
			c.setString(4, m.getCcsa_signplace());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 公司合同归档
	public int returnCoCompactSA(int coco_id, CoCompactSAModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoCompactSAReturn_P_lsb(?,?,?,?,?,?,?)");
			c.setInt(1, coco_id);
			c.setString(2, m.getCcsa_filedate());
			c.setString(3, m.getCcsa_fileid());
			c.setString(4, m.getCcsa_chs_copies());
			c.setString(5, m.getCcsa_en_copies());
			c.setString(6, m.getCcsa_remark());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 1;
		}
	}
	
	//合同补充协议修改
	public boolean UpCoCompactSA(CoCompactSAModel model){
		try{
			String sql="update CoCompactSA set ccsa_inuredate=?,ccsa_remark=? where ccsa_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setString(1, model.getCcsa_inuredate());	
			psmt.setString(2, model.getCcsa_remark());
			psmt.setInt(3, model.getCcsa_id());	
			psmt.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	//更新数据状态
	public boolean UpCoCompactSA(int ccsa_state,int ccsa_id){
		try{
			String sql="update CoCompactSA set ccsa_state=?,ccsa_auditingdate=getdate() where ccsa_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, ccsa_state);	
			psmt.setInt(2, ccsa_id);	
			psmt.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	//打印状态
		public boolean PrintCoCompactSA(int ccsa_state,int ccsa_id){
			try{
				String sql="update CoCompactSA set ccsa_state=?,ccsa_printdate=getdate() where ccsa_id=?";
				PreparedStatement psmt = conn.getpre(sql);
				psmt.setInt(1, ccsa_state);	
				psmt.setInt(2, ccsa_id);	
				psmt.executeUpdate();
				return true;
			}catch(Exception e){
				return false;
			}
		}
}
