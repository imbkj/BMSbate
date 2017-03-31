package dal.SocialInsurance;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Conn.dbconn;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import Model.SocialInsuranceClassInfoViewModel;

public class AlgorithmOperateDal {
	private dbconn conn = new dbconn();

	// 新增社保字典基本信息
	public int AddSiAlgorithm(SocialInsuranceAlgorithmViewModel m, int addtype) {
		try {
			CallableStatement c = conn
					.getcall("SiAlgorithmAdd_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			if (addtype == 1) {
				c.setInt(1, m.getSoin_cabc_id());
			} else if (addtype == 2) {
				c.setInt(1, m.getSoin_id());
			}
			c.setString(2, m.getSoin_title());
			c.setString(3, DatetoSting(m.getSial_execdate()));
			c.setString(4, m.getSial_standard());
			c.setInt(5, m.getSial_sb_standard());
			c.setInt(6, m.getSial_gjj_standard());
			c.setBigDecimal(7, m.getSial_avg_salary());
			c.setBigDecimal(8, m.getSial_lowest_salary());
			c.setString(9, m.getSial_city_remark());
			c.setString(10, m.getSial_sb_remark());
			c.setString(11, m.getSial_gjj_remark());
			c.setString(12, m.getSial_cb_remark());
			c.setString(13, m.getSial_addname());
			c.setInt(14, addtype);
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(15);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 新增社保字典详细项目
	public void AddSiAlgorithmInfo(SocialInsuranceClassInfoViewModel m,
			int sial_id) {
		try {
			CallableStatement c = conn
					.getcall("SiAlgorithmInfoAdd_p_lwj(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, sial_id);
			c.setInt(2, m.getSicl_id());
			c.setInt(3, m.getSiai_side_id());
			c.setBigDecimal(4, m.getSiai_basic_ud());
			c.setBigDecimal(5, m.getSiai_basic_dd());
			c.setBigDecimal(6, m.getSiai_deposit_ud());
			c.setBigDecimal(7, m.getSiai_deposit_dd());
			c.setString(8, m.getSiai_proportion());
			c.setString(9, m.getSiai_algorithm());
			c.setString(10, m.getSiai_remark());
			c.setString(11, m.getSide_decimal());
			c.execute();
		} catch (SQLException e) {

		}
	}

	// 更新社保字典基本信息
	public int UpSiAlgorithm(SocialInsuranceAlgorithmViewModel m) {
		try {
			CallableStatement c = conn
					.getcall("SiAlgorithmUp_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
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

	// 禁止社保算法
	public int StopSiAlgorithm(int sial_id, String reason, String username) {
		try {
			CallableStatement c = conn
					.getcall("SiAlgorithmStop_p_lwj(?,?,?,?)");
			c.setInt(1, sial_id);
			c.setString(2, reason);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 更新社保字典详细项目
	public void UpSiAlgorithmInfo(SocialInsuranceClassInfoViewModel m) {
		try {
			CallableStatement c = conn
					.getcall("SiAlgorithmInfoUp_p_lwj(?,?,?,?,?,?,?,?,?)");
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

	// 新增社保字典库变更
	public int AddSocialInsuranceChange(SocialInsuranceChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("SocialInsuranceChangeAdd_p_lwj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getSich_cabc_id());
			c.setInt(2, m.getSich_change_type());
			c.setInt(3, m.getSich_soin_id());
			c.setInt(4, m.getSich_sial_id());
			c.setString(5, m.getSich_title());
			c.setString(6, m.getSich_Reason());
			c.setString(7, m.getSich_addname());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 新增社保算法变更
	public int AddSocialInsuranceAlgorithmChange(
			SocialInsuranceAlgorithmViewModel m, int siac_sich_id) {
		try {
			CallableStatement c = conn
					.getcall("SocialInsuranceAlgorithmChangeAdd_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, siac_sich_id);
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

	// 新增社保算法项目变更表
	public void AddSocialInseranceAlgorithmInfoChange(
			SocialInsuranceClassInfoViewModel m, int saic_siac_id) {
		try {
			CallableStatement c = conn
					.getcall("SocialInseranceAlgorithmInfoChangeAdd_p_lwj(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, saic_siac_id);
			c.setInt(2, m.getSicl_id());
			c.setInt(3, m.getSiai_side_id());
			c.setBigDecimal(4, m.getSiai_basic_ud());
			c.setBigDecimal(5, m.getSiai_basic_dd());
			c.setBigDecimal(6, m.getSiai_deposit_ud());
			c.setBigDecimal(7, m.getSiai_deposit_dd());
			c.setString(8, m.getSiai_proportion());
			c.setString(9, m.getSiai_algorithm());
			c.setString(10, m.getSiai_remark());
			c.setString(11, m.getSide_decimal());
			c.execute();
		} catch (SQLException e) {

		}
	}

	// 更新SocialInsuranceChange表流程ID
	public boolean upSocialInsuranceChangeTaprId(int tapr_id, int dataid) {
		String sql = "update SocialInsuranceChange set sich_tapr_id=? where sich_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, tapr_id);
			psmt.setInt(2, dataid);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 检测是否已有该算法
	public boolean existAlName(int cabc_id, String alName) {
		ResultSet rs = null;
		String sql = "select COUNT(soin_id) as count from SocialInsurance where soin_cabc_id=? and soin_title=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cabc_id);
			psmt.setString(2, alName);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	// 检测变更信息中是否已有该算法未生效
	public boolean existChangeAlName(int cabc_id, String alName) {
		ResultSet rs = null;
		String sql = "select COUNT(sich_id) as count from SocialInsuranceChange where sich_change_type=1 and (sich_state=0 or sich_state=2) and sich_cabc_id=? and sich_title=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cabc_id);
			psmt.setString(2, alName);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	// 检测标准是否已有相同的执行时间（非当前算法）
	public boolean existExecdateExceptSial(int soin_id, Date sial_execdate,
			int sial_id) {
		ResultSet rs = null;
		String sql = "select count(sial_id) as count from SocialInsuranceAlgorithm where sial_soin_id=? and sial_execdate=? and sial_id!=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, soin_id);
			psmt.setString(2, DatetoSting(sial_execdate));
			psmt.setInt(3, sial_id);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	// 检测标准是否已有相同的执行时间
	public boolean existExecdate(int soin_id, Date sial_execdate) {
		ResultSet rs = null;
		String sql = "select count(sial_id) as count from SocialInsuranceAlgorithm where sial_soin_id=? and sial_execdate=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, soin_id);
			psmt.setString(2, DatetoSting(sial_execdate));
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				// add
				return true;
			} else {
				// update
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	// 检测变更信息中是否已有该年月的算法调整或更新未生效数据
	public boolean existChangeExecdate(int soin_id, Date sial_execdate) {
		ResultSet rs = null;
		String sql = "select COUNT(sich_id) as count from SocialInsuranceChange sich left join SocialInsuranceAlgorithmChange siac on siac.siac_sich_id=sich.sich_id where (sich_state=0 or sich_state=2) and sich_soin_id=? and siac_execdate=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, soin_id);
			psmt.setString(2, DatetoSting(sial_execdate));
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

	// 检测变更信息中是否已有该标准未生效的数据
	public boolean existChangeSoinId(int soin_id) {
		ResultSet rs = null;
		String sql = "select COUNT(sich_id) as count from SocialInsuranceChange where (sich_state=0 or sich_state=2) and sich_soin_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, soin_id);
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

	// Date类型转换String
	private String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
		String Date = sdf.format(d);
		return Date;
	}
}
