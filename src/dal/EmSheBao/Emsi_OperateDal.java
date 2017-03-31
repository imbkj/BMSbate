package dal.EmSheBao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;

public class Emsi_OperateDal {
	private static dbconn conn = new dbconn();

	// 社保新增
	public int newin(EmShebaoUpdateModel m, int changeId) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_Newin_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, changeId);
			c.setInt(3, m.getFormulaid());
			c.setInt(4, m.getOwnmonth());
			c.setInt(5, m.getEsiu_radix());
			c.setString(6, m.getMobile());
			c.setString(7, m.getWorker());
			c.setString(8, m.getHand());
			c.setString(9, m.getFolk());
			c.setInt(10, m.getIfdeclare());
			c.setString(11, m.getEsiu_remark());
			c.setBigDecimal(12, m.getEsiu_ylcp());
			c.setBigDecimal(13, m.getEsiu_ylop());
			c.setBigDecimal(14, m.getEsiu_jlcp());
			c.setBigDecimal(15, m.getEsiu_jlop());
			c.setBigDecimal(16, m.getEsiu_syucp());
			c.setBigDecimal(17, m.getEsiu_syuop());
			c.setBigDecimal(18, m.getEsiu_syecp());
			c.setBigDecimal(19, m.getEsiu_syeop());
			c.setBigDecimal(20, m.getEsiu_gscp());
			c.setBigDecimal(21, m.getEsiu_gsop());
			c.setString(22, m.getEsiu_addname());
			c.registerOutParameter(23, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(23);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保独立户接管
	public int takeOver(EmShebaoUpdateModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_TakeOver_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getFormulaid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEsiu_radix());
			c.setString(5, m.getMobile());
			c.setInt(6, m.getIfdeclare());
			c.setString(7, m.getEsiu_remark());
			c.setBigDecimal(8, m.getEsiu_ylcp());
			c.setBigDecimal(9, m.getEsiu_ylop());
			c.setBigDecimal(10, m.getEsiu_jlcp());
			c.setBigDecimal(11, m.getEsiu_jlop());
			c.setBigDecimal(12, m.getEsiu_syucp());
			c.setBigDecimal(13, m.getEsiu_syuop());
			c.setBigDecimal(14, m.getEsiu_syecp());
			c.setBigDecimal(15, m.getEsiu_syeop());
			c.setBigDecimal(16, m.getEsiu_gscp());
			c.setBigDecimal(17, m.getEsiu_gsop());
			c.setString(18, m.getEsiu_addname());
			c.registerOutParameter(19, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(19);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保调入
	public int movein(EmShebaoUpdateModel m, int changeId) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_Movein_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, changeId);
			c.setInt(3, m.getFormulaid());
			c.setInt(4, m.getOwnmonth());
			c.setString(5, m.getEsiu_computerid());
			c.setInt(6, m.getEsiu_radix());
			c.setString(7, m.getMobile());
			c.setInt(8, m.getIfdeclare());
			c.setString(9, m.getEsiu_remark());
			c.setBigDecimal(10, m.getEsiu_ylcp());
			c.setBigDecimal(11, m.getEsiu_ylop());
			c.setBigDecimal(12, m.getEsiu_jlcp());
			c.setBigDecimal(13, m.getEsiu_jlop());
			c.setBigDecimal(14, m.getEsiu_syucp());
			c.setBigDecimal(15, m.getEsiu_syuop());
			c.setBigDecimal(16, m.getEsiu_syecp());
			c.setBigDecimal(17, m.getEsiu_syeop());
			c.setBigDecimal(18, m.getEsiu_gscp());
			c.setBigDecimal(19, m.getEsiu_gsop());
			c.setString(20, m.getWorker());
			c.setString(21, m.getHand());
			c.setString(22, m.getFolk());
			c.setString(23, m.getEsiu_addname());
			c.registerOutParameter(24, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(24);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保调回
	public int moveback(EmShebaoUpdateModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_Moveback_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getFormulaid());
			c.setInt(3, m.getOwnmonth());
			c.setString(4, m.getEsiu_computerid());
			c.setInt(5, m.getEsiu_radix());
			c.setString(6, m.getMobile());
			c.setInt(7, m.getIfdeclare());
			c.setString(8, m.getEsiu_remark());
			c.setBigDecimal(9, m.getEsiu_ylcp());
			c.setBigDecimal(10, m.getEsiu_ylop());
			c.setBigDecimal(11, m.getEsiu_jlcp());
			c.setBigDecimal(12, m.getEsiu_jlop());
			c.setBigDecimal(13, m.getEsiu_syucp());
			c.setBigDecimal(14, m.getEsiu_syuop());
			c.setBigDecimal(15, m.getEsiu_syecp());
			c.setBigDecimal(16, m.getEsiu_syeop());
			c.setBigDecimal(17, m.getEsiu_gscp());
			c.setBigDecimal(18, m.getEsiu_gsop());
			c.setString(19, m.getEsiu_addname());
			c.registerOutParameter(20, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(20);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 外籍人社保新增
	public int newinForeigner(EmShebaoUpdateModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_ForeignerNewin_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getFormulaid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEsiu_radix());
			c.setString(5, m.getMobile());
			c.setString(6, m.getEsiu_officialrank());
			c.setString(7, m.getWorker());
			c.setString(8, m.getHand());
			c.setString(9, m.getFolk());
			c.setInt(10, m.getIfdeclare());
			c.setString(11, m.getEmsc_s8());
			c.setBigDecimal(12, m.getEsiu_ylcp());
			c.setBigDecimal(13, m.getEsiu_ylop());
			c.setBigDecimal(14, m.getEsiu_jlcp());
			c.setBigDecimal(15, m.getEsiu_jlop());
			c.setBigDecimal(16, m.getEsiu_syucp());
			c.setBigDecimal(17, m.getEsiu_syuop());
			c.setBigDecimal(18, m.getEsiu_syecp());
			c.setBigDecimal(19, m.getEsiu_syeop());
			c.setBigDecimal(20, m.getEsiu_gscp());
			c.setBigDecimal(21, m.getEsiu_gsop());
			c.setString(22, m.getEsiu_addname());
			c.registerOutParameter(23, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(23);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 外籍人社保调入
	public int moveinForeigner(EmShebaoUpdateModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_ForeignerMovein_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getFormulaid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEsiu_radix());
			c.setString(5, m.getMobile());
			c.setInt(6, m.getIfdeclare());
			c.setString(7, m.getEmsc_s8());
			c.setBigDecimal(8, m.getEsiu_ylcp());
			c.setBigDecimal(9, m.getEsiu_ylop());
			c.setBigDecimal(10, m.getEsiu_jlcp());
			c.setBigDecimal(11, m.getEsiu_jlop());
			c.setBigDecimal(12, m.getEsiu_syucp());
			c.setBigDecimal(13, m.getEsiu_syuop());
			c.setBigDecimal(14, m.getEsiu_syecp());
			c.setBigDecimal(15, m.getEsiu_syeop());
			c.setBigDecimal(16, m.getEsiu_gscp());
			c.setBigDecimal(17, m.getEsiu_gsop());
			c.setString(18, m.getEsiu_addname());
			c.setString(19, m.getEsiu_computerid());
			c.registerOutParameter(20, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(20);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 外籍人社保调回
	public int movebackForeigner(EmShebaoUpdateModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_ForeignerMoveback_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getFormulaid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEsiu_radix());
			c.setString(5, m.getMobile());
			c.setInt(6, m.getIfdeclare());
			c.setString(7, m.getEmsc_s8());
			c.setBigDecimal(8, m.getEsiu_ylcp());
			c.setBigDecimal(9, m.getEsiu_ylop());
			c.setBigDecimal(10, m.getEsiu_jlcp());
			c.setBigDecimal(11, m.getEsiu_jlop());
			c.setBigDecimal(12, m.getEsiu_syucp());
			c.setBigDecimal(13, m.getEsiu_syuop());
			c.setBigDecimal(14, m.getEsiu_syecp());
			c.setBigDecimal(15, m.getEsiu_syeop());
			c.setBigDecimal(16, m.getEsiu_gscp());
			c.setBigDecimal(17, m.getEsiu_gsop());
			c.setString(18, m.getEsiu_addname());
			c.setString(19, m.getEsiu_computerid());
			c.registerOutParameter(20, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(20);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 修改工资
	public int upSalary(EmShebaoUpdateModel m, int changeId) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_SalaryUpdate_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, changeId);
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEsiu_radix());
			c.setInt(5, m.getIfdeclare());
			c.setString(6, m.getEsiu_remark());
			c.setBigDecimal(7, m.getEsiu_ylcp());
			c.setBigDecimal(8, m.getEsiu_ylop());
			c.setBigDecimal(9, m.getEsiu_jlcp());
			c.setBigDecimal(10, m.getEsiu_jlop());
			c.setBigDecimal(11, m.getEsiu_syucp());
			c.setBigDecimal(12, m.getEsiu_syuop());
			c.setBigDecimal(13, m.getEsiu_syecp());
			c.setBigDecimal(14, m.getEsiu_syeop());
			c.setBigDecimal(15, m.getEsiu_gscp());
			c.setBigDecimal(16, m.getEsiu_gsop());
			c.setString(17, m.getEsiu_addname());
			c.registerOutParameter(18, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(18);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 档案修改
	public int upFile(EmShebaoUpdateModel m, String con, int changeId) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_FileUp_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getGid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEsiu_radix());
			c.setString(5, m.getMobile());
			c.setInt(6, m.getIfdeclare());
			c.setString(7, m.getEsiu_remark());
			c.setString(8, m.getEsiu_yl());
			c.setString(9, m.getEsiu_yltype());
			c.setString(10, m.getEsiu_gs());
			c.setString(11, m.getEsiu_sye());
			c.setString(12, m.getEsiu_syu());
			c.setBigDecimal(13, m.getEsiu_ylcp());
			c.setBigDecimal(14, m.getEsiu_ylop());
			c.setBigDecimal(15, m.getEsiu_jlcp());
			c.setBigDecimal(16, m.getEsiu_jlop());
			c.setBigDecimal(17, m.getEsiu_syucp());
			c.setBigDecimal(18, m.getEsiu_syuop());
			c.setBigDecimal(19, m.getEsiu_syecp());
			c.setBigDecimal(20, m.getEsiu_syeop());
			c.setBigDecimal(21, m.getEsiu_gscp());
			c.setBigDecimal(22, m.getEsiu_gsop());
			c.setInt(23, m.getEsiu_ifsame());
			c.setString(24, con);
			c.setString(25, m.getEsiu_addname());
			c.setInt(26, changeId);
			c.registerOutParameter(27, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(27);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保停交
	public int stop(EmShebaoUpdateModel m, int changeId) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_Stop_p_lwj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, changeId);
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getIfdeclare());
			c.setString(5, m.getEsiu_stopreason());
			c.setString(6, m.getEsiu_remark());
			c.setString(7, m.getEsiu_addname());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 特殊变更
	public int changeSZSI(EmShebaoChangeSZSIModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_ChangeSZSI_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getOwnmonth());
			c.setString(3, m.getEscs_change());
			c.setString(4, m.getEscs_content());
			c.setString(5, m.getEscs_changehj());
			c.setString(6, m.getEscs_changename());
			c.setString(7, m.getEscs_changeidcard());
			c.setString(8, m.getEscs_changeylid());
			c.setString(9, m.getEscs_changecid());
			c.setString(10, m.getEscs_changeofficialrank());
			c.setString(11, m.getEscs_s8());
			c.setString(12, m.getEscs_addname());
			c.setString(13, m.getEscs_remark());
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 补缴社保
	public int addbj(EmShebaoBJModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_Bj_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getOwnmonth());
			c.setInt(3, m.getEmsb_feeownmonth());
			c.setString(4, m.getEmsb_computerid());
			c.setString(5, m.getEmsb_hj());
			c.setInt(6, m.getEmsb_startmonth());
			c.setInt(7, m.getEmsb_stopmonth());
			c.setInt(8, m.getEmsb_radix());
			c.setBigDecimal(9, m.getEmsb_totalcp());
			c.setBigDecimal(10, m.getEmsb_totalop());
			c.setInt(11, m.getEmsb_ifdeclare());
			c.setString(12, m.getEmsb_addname());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 补缴社保(医疗)
	public int addJLbj(EmShebaoBJModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BjJL_p_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getOwnmonth());
			c.setInt(3, m.getEmsb_feeownmonth());
			c.setString(4, m.getEmsb_computerid());
			c.setString(5, m.getEmsb_hj());
			c.setInt(6, m.getEmsb_startmonth());
			c.setInt(7, m.getEmsb_stopmonth());
			c.setInt(8, m.getEmsb_radix());
			c.setBigDecimal(9, m.getEmsb_totalcp());
			c.setBigDecimal(10, m.getEmsb_totalop());
			c.setInt(11, m.getEmsb_ifdeclare());
			c.setString(12, m.getEmsb_addname());
			c.setString(13, m.getEmsb_yltype());
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 外籍人社保变更确认
	public int ForeignerChangeDeclare(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_ForeignerChangeDeclare_up_p_lwj(?,?)");
			c.setInt(1, m.getId());
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保变更确认
	public int ChangeDeclare(EmSheBaoChangeModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_ChangeDeclare_up_p_lwj(?,?)");
			c.setInt(1, m.getId());
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴确认
	public int BjDeclare(EmShebaoBJModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BjDeclare_up_p_lwj(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, username);
			c.setInt(3, m.getEmsb_ifdeclare());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴确认(医疗)
	public int BjJLDeclare(EmShebaoBJModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_BjJLDeclare_up_p_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, username);
			c.setInt(3, m.getEmsb_ifdeclare());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴删除
	public int DelBj(int id) {
		try {
			CallableStatement c = conn.getcall("EMSC_Bj_Del_p_lwj(?,?)");
			c.setInt(1, id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴删除(医疗)
	public int DelBjJL(int id) {
		try {
			CallableStatement c = conn.getcall("EMSC_BjJL_Del_p_lsb(?,?)");
			c.setInt(1, id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保变更删除
	public int DelChange(int id, int type, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_Change_Del_p_lwj(?,?,?,?)");
			c.setInt(1, id);
			c.setInt(2, type);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新EmShebaoChange表流程ID
	public boolean upEmShebaoChangeTaprId(int tapr_id, int dataid) {
		String sql = "update EmShebaoChange  set emsc_tapr_id=? where ID=?";
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

	// 更新EmShebaoChangeForeigner表流程ID
	public boolean upEmShebaoChangeForeignerTaprId(int tapr_id, int dataid) {
		String sql = "update EmShebaoChangeForeigner set emsc_tapr_id=? where ID=?";
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

	// 修改社保变更表状态
	public boolean upEmShebaoChangeIfdeclare(int dataid) {
		String sql = "update EmShebaoChange set emsc_ifdeclare=0 where id=?";
		PreparedStatement psmt = conn.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, dataid);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 修改社保变更表状态
	public boolean upChangeSZSIDeclare(int ifdeclare, int escs_id) {

		String sql = "update EmShebaoChangeSZSI set escs_Ifdeclare=? where escs_id=?";
		if (ifdeclare == 8 || ifdeclare == 0) {
			sql = "update EmShebaoChangeSZSI set escs_confirmtime=getdate(),escs_Ifdeclare=? where escs_id=?";
		}

		PreparedStatement psmt = conn.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, ifdeclare);
			psmt.setInt(2, escs_id);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 修改社保变更表状态(重新发送)
	public boolean upChangeSZSIDeclareRS(int ifdeclare, int escs_id,
			int ownmonth) {

		String sql = "update EmShebaoChangeSZSI set escs_Ifdeclare=?,ownmonth=? where escs_id=?";
		if (ifdeclare == 8 || ifdeclare == 0) {
			sql = "update EmShebaoChangeSZSI set escs_confirmtime=getdate(),escs_Ifdeclare=?,ownmonth=? where escs_id=?";
		}

		PreparedStatement psmt = conn.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, ifdeclare);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, escs_id);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 更新EmShebaoChangeSZSI表流程ID
	public boolean upEmShebaoChangeSZSITaprId(int tapr_id, int dataid) {
		String sql = "update EmShebaoChangeSZSI set escs_tapr_id=? where escs_id=?";
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

	// 更新EmShebaoBJ表流程ID
	public boolean upEmShebaoBJTaprId(int tapr_id, int dataid) {
		String sql = "update EmShebaoBJ set emsb_tapr_id=? where ID=?";
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

	// 更新EmShebaoBJJL表流程ID
	public boolean upEmShebaoBJJLTaprId(int tapr_id, int dataid) {
		String sql = "update EmShebaoBJJL set esbj_tapr_id=? where ID=?";
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

	// 根据GID从Embase表获取姓名
	public String getEmbaseName(int gid) {
		String sql = "select top 1 emba_name from EmBase where gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		String name = "";
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			name = rs.getString("emba_name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	// 撤回社保变更信息
	public int revokeChange(int id, int type, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_Change_Revoke_p_lwj(?,?,?,?)");
			c.setInt(1, id);
			c.setInt(2, type);
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 撤回社保补缴信息
	public int revokeBj(int id, String username) {
		try {
			CallableStatement c = conn.getcall("EMSC_Bj_Revoke_p_lwj(?,?,?)");
			c.setInt(1, id);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 获取雇员社保开户情况
	public int getShebaoSingle(int gid) {
		int single = -1;
		StringBuilder sql = new StringBuilder();
		sql.append("select top 1 (case coco.coco_shebao when '中智开户' then 0 when '独立开户' then 1 when '中智开户(委托)' then 2 end)single ");
		sql.append("from CoGList cogl ");
		sql.append("left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id ");
		sql.append("left join CoOffer coof on coli.coli_coof_id=coof.coof_id ");
		sql.append("left join CoCompact coco on coof.coof_coco_id=coco.coco_id ");
		sql.append("where coli.coli_name='社会保险服务' and cgli_state=1 and cogl.gid=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				single = rs.getInt("single");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return single;
	}

	// 获取独立户是否有Ukey
	public boolean getShebaoUkey(int gid) {
		boolean bool = false;
		String sql = "select COUNT(*) as cou from CoShebao where (cosb_ukey='有' or cosb_ukey='是') and cid=(select cid from EmBase where gid=?)";
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("cou") > 0)
					bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 检查是否有医疗补交
	public boolean getShebaoBJJL(int gid, int ownmonth, int startownmonth) {
		// System.out.println("select COUNT(*) as cou from EmShebaoBJJL where  gid="+gid+" and ownmonth="+ownmonth+" and esbj_startmonth="+startownmonth+"");
		boolean bool = false;
		String sql = "select COUNT(*) as cou from EmShebaoBJJL where  gid=? and ownmonth=? and esbj_startmonth=?";
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, gid);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, startownmonth);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("cou") > 0)
					bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 插入社保在册数据
	public boolean insertShebaoupdate(EmShebaoUpdateModel m) {
		boolean bool = false;
		try {
			CallableStatement c = conn
					.getcall("insertShebaoUpdate_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getFormulaid());
			c.setInt(3, m.getOwnmonth());
			c.setString(4, m.getEsiu_computerid());
			c.setInt(5, m.getEsiu_radix());
			c.setBigDecimal(6, m.getEsiu_ylcp());
			c.setBigDecimal(7, m.getEsiu_ylop());
			c.setBigDecimal(8, m.getEsiu_jlcp());
			c.setBigDecimal(9, m.getEsiu_jlop());
			c.setBigDecimal(10, m.getEsiu_syucp());
			c.setBigDecimal(11, m.getEsiu_syuop());
			c.setBigDecimal(12, m.getEsiu_syecp());
			c.setBigDecimal(13, m.getEsiu_syeop());
			c.setBigDecimal(14, m.getEsiu_gscp());
			c.setBigDecimal(15, m.getEsiu_gsop());
			c.setString(16, m.getEsiu_addname());
			c.registerOutParameter(17, java.sql.Types.INTEGER);
			c.execute();
			if (c.getInt(17) == 1)
				bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 插入社保补缴数据
	public boolean insertShebaoBj(EmShebaoBJModel m) {
		boolean bool = false;
		try {
			CallableStatement c = conn
					.getcall("insertShebaoBj_p_lwj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getOwnmonth());
			c.setInt(3, m.getEmsb_feeownmonth());
			c.setInt(4, m.getEmsb_startmonth());
			c.setInt(5, m.getEmsb_stopmonth());
			c.setInt(6, m.getEmsb_radix());
			c.setBigDecimal(7, m.getEmsb_totalcp());
			c.setBigDecimal(8, m.getEmsb_totalop());
			c.setString(9, UserInfo.getUsername());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			if (c.getInt(10) > 0)
				bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 插入社保补缴数据
	public boolean insertShebaoBjJL(EmShebaoBJModel m) {
		boolean bool = false;
		try {
			CallableStatement c = conn
					.getcall("insertShebaoBjJL_p_lwj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getOwnmonth());
			c.setInt(3, m.getEmsb_feeownmonth());
			c.setInt(4, m.getEmsb_startmonth());
			c.setInt(5, m.getEmsb_stopmonth());
			c.setInt(6, m.getEmsb_radix());
			c.setBigDecimal(7, m.getEmsb_totalcp());
			c.setBigDecimal(8, m.getEmsb_totalop());
			c.setString(9, UserInfo.getUsername());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			if (c.getInt(10) > 0)
				bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	/**
	 * @Title: del
	 * @Description: TODO(入职删除模拟在册数据)
	 * @param gid
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer delupdate(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmShebaoUpdate where gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * @Title: delbj
	 * @Description: TODO(入职删除模拟补缴数据)
	 * @param gid
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer delbj(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmShebaoBJ where emsb_ifdeclare=4 and gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: delbj
	 * @Description: TODO(入职删除模拟医疗补缴数据)
	 * @param gid
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer deljlbj(Integer gid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmShebaoBJJL where esbj_Ifdeclare=4 and gid=?";
		try {
			i = db.updatePrepareSQL(sql, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

}
