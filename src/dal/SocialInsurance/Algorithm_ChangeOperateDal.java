package dal.SocialInsurance;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Conn.dbconn;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import Model.SocialInsuranceClassInfoViewModel;

public class Algorithm_ChangeOperateDal {
	private dbconn conn = new dbconn();

	// 退回社保算法变更
	public void returnAlgorithmChange(SocialInsuranceChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("returnAlgorithmChange_p_lwj(?,?)");
			c.setInt(1, m.getSich_id());
			c.setString(2, m.getSich_ReturnReason());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 重新提交社保算法变更
	public void resubmitAlgorithmChange(SocialInsuranceChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("resubmitAlgorithmChange_p_lwj(?)");
			c.setInt(1, m.getSich_id());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 终止社保变更任务流程
	public void StopTaskAlgorithmChange(SocialInsuranceChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("StopTaskAlgorithmChange_p_lwj(?)");
			c.setInt(1, m.getSich_id());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 更新社保字典变更的基本信息
	public int UpSiAlgorithmChange(SocialInsuranceAlgorithmViewModel m) {
		try {
			CallableStatement c = conn
					.getcall("SiAlgorithmChangeUp_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getSial_id());
			c.setString(2, DatetoSting(m.getSial_execdate()));
			c.setString(3, m.getSial_standard());
			c.setInt(4, m.getSial_sb_standard());
			c.setInt(5, m.getSial_gjj_standard());
			c.setBigDecimal(6, m.getSial_avg_salary());
			c.setBigDecimal(7, m.getSial_lowest_salary());
			c.setString(8, m.getSial_city_remark());
			c.setString(9, m.getSial_sb_remark());
			c.setString(10, m.getSial_gjj_remark());
			c.setString(11, m.getSial_cb_remark());
			c.setString(12, m.getSial_addname());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 更新社保字典详细项目变更信息
	public void UpSiAlgorithmInfoChange(SocialInsuranceClassInfoViewModel m) {
		try {
			CallableStatement c = conn
					.getcall("SiAlgorithmInfoChangeUp_p_lwj(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getSiai_id());
			c.setString(2, m.getSide_decimal());
			c.setBigDecimal(3, m.getSiai_basic_ud());
			c.setBigDecimal(4, m.getSiai_basic_dd());
			c.setBigDecimal(5, m.getSiai_deposit_ud());
			c.setBigDecimal(6, m.getSiai_deposit_dd());
			c.setString(7, m.getSiai_proportion());
			c.setString(8, m.getSiai_algorithm());
			c.setString(9, m.getSiai_remark());
			c.execute();
		} catch (SQLException e) {

		}
	}

	// 新增算法生效
	public int AddSocialInsuranceChangeConfirm(int sich_id, String username) {
		try {
			CallableStatement c = conn
					.getcall("SocialInsuranceChangeConfirmAdd_p_lwj(?,?,?)");
			c.setInt(1, sich_id);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 更新算法生效
	public int UpAddSocialInsuranceChangeConfirm(int sich_id, String username) {
		try {
			CallableStatement c = conn
					.getcall("SocialInsuranceChangeConfirmUpAdd_p_lwj(?,?,?)");
			c.setInt(1, sich_id);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 调整算法生效
	public int UpSocialInsuranceChangeConfirm(int sich_id, String username) {
		try {
			CallableStatement c = conn
					.getcall("SocialInsuranceChangeConfirmUp_p_lwj(?,?,?)");
			c.setInt(1, sich_id);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 调整算法生效
	public int StopSocialInsuranceChangeConfirm(int sich_id, String username) {
		try {
			CallableStatement c = conn
					.getcall("SocialInsuranceChangeConfirmStop_p_lwj(?,?,?)");
			c.setInt(1, sich_id);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	// Date类型转换String
	private String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
		String Date = sdf.format(d);
		return Date;
	}
}
