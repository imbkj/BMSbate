package dal.EmSalary;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmSalaryDataModel;

public class EmSalary_AudtingDal {
	private static dbconn conn = new dbconn();

	// 工资审核通过
	public int audtingEmSalary(int tbrb_id) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_audting_p_lwj(?,?)");
			c.setInt(1, tbrb_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 工资待发放确认
	public int payAudtingEmSalary(int tbrb_id) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_payAudting_p_lwj(?,?)");
			c.setInt(1, tbrb_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 工资审核退回
	public int returnEmSalary(int tbrb_id) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_return_p_lwj(?,?)");
			c.setInt(1, tbrb_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 工资暂停发放
	public int holdEmSalary(EmSalaryDataModel m) {
		String sql = "update EmSalaryData  set esda_ifhold=? where esda_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, m.getEsda_ifhold());
			psmt.setInt(2, m.getEsda_id());
			psmt.execute();
			return 1;
		} catch (SQLException e) {
			return 0;
		}
	}

	// 检测工资批量审核单是否已无有效数据（返回true将终止任务单）
	public boolean existEmSalaryEffAll(int taba_id) {
		ResultSet rs = null;
		String sql = "select COUNT(tbrb_id) as count from TaskBatchRelBusiness where tbrb_state=1 and tbrb_taba_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, taba_id);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 检测工资批量审核单是否已审核完所有数据（返回true将转至下一步）
	public boolean existEmSalaryAudtingAll(int taba_id) {
		ResultSet rs = null;
		String sql = "select COUNT(tbrb_id) as count from TaskBatchRelBusiness where  tbrb_customInt=0 and tbrb_state=1 and tbrb_taba_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, taba_id);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 检测工资批量待发放确认是否已确认完所有数据（返回true将转至下一步）
	public boolean existEmSalaryPayAudtingAll(int taba_id) {
		ResultSet rs = null;
		String sql = "select COUNT(tbrb_id) as count from TaskBatchRelBusiness tb left join (select esda_id,esda_payment_state from EmSalaryData where esda_oof_state=0) ed on tb.tbrb_data_id=ed.esda_id where  esda_payment_state!=4 and tbrb_state=1 and tbrb_taba_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, taba_id);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 检测工资批量发放确认是否已发放完所有数据（返回true将转至下一步）
	public boolean existEmSalaryPayAll(int taba_id) {
		ResultSet rs = null;
		String sql = "select COUNT(tbrb_id) as count from TaskBatchRelBusiness tb left join (select esda_id,esda_payment_state from EmSalaryData where esda_oof_state=0) ed on tb.tbrb_data_id=ed.esda_id where  esda_payment_state!=2 and tbrb_state=1 and tbrb_taba_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, taba_id);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
