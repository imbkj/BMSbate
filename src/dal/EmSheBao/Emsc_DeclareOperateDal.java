package dal.EmSheBao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeMAModel;
import Model.EmShebaoSetupModel;
import Util.DateStringChange;
import Util.UserInfo;

public class Emsc_DeclareOperateDal {
	private dbconn conn = new dbconn();

	// 社保补缴申报
	public int BjDeclare(EmShebaoBJModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_BjDeclare_p_lwj(?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getEmsb_ifdeclare());
			if (m.getEmsb_dptime() == null) {
				c.setString(3, null);
			} else {
				c.setString(3, DateStringChange.DatetoSting(m.getEmsb_dptime(),
						"yyyy-MM-dd"));
			}
			c.setString(4, username);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴申报(医疗)
	public int BjJLDeclare(EmShebaoBJModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_BjJLDeclare_p_lsb(?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getEmsb_ifdeclare());
			if (m.getEmsb_dptime() == null) {
				c.setString(3, null);
			} else {
				c.setString(3, DateStringChange.DatetoSting(m.getEmsb_dptime(),
						"yyyy-MM-dd"));
			}
			c.setString(4, username);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴申报修改状态
	public int BjDeclareUpState(EmShebaoBJModel m, String username) {
		try {

			CallableStatement c = conn
					.getcall("EMSC_BjDeclare_up_p_lwj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getEmsb_ifdeclare());
			if (m.getEmsb_dptime() == null) {
				c.setString(3, null);
			} else {
				c.setString(3, DateStringChange.DatetoSting(m.getEmsb_dptime(),
						"yyyy-MM-dd"));
			}
			c.setString(4, m.getEmsb_reason());
			c.setString(5, username);
			c.setInt(6, m.getOwnmonth());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴申报修改状态(医疗)
	public int BjJLDeclareUpState(EmShebaoBJModel m, String username) {
		try {

			CallableStatement c = conn
					.getcall("EMSC_BjJLDeclare_up_p_lsb(?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getEmsb_ifdeclare());
			if (m.getEmsb_dptime() == null) {
				c.setString(3, null);
			} else {
				c.setString(3, DateStringChange.DatetoSting(m.getEmsb_dptime(),
						"yyyy-MM-dd"));
			}
			c.setString(4, m.getEmsb_reason());
			c.setString(5, username);
			c.setInt(6, m.getOwnmonth());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴申报修改状态
	public int BjDeclareUpload(EmShebaoBJModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSC_BjDeclareUpload_p_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsb_uploadfile());
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴申报修改状态
	public int BjJLDeclareUpload(EmShebaoBJModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSC_BjJLDeclareUpload_p_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsb_uploadfile());
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴客服下载PDF文件
	public int BjDeclareDownload(EmShebaoBJModel m) {
		String sql = "UPDATE EmShebaoBJ SET emsb_ifdeclare=7 where emsb_ifdeclare=2 AND id="
				+ m.getId();
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴客服下载PDF文件(医疗)
	public int BjJLDeclareDownload(EmShebaoBJModel m) {
		String sql = "UPDATE EmShebaoBJJL SET esbj_ifdeclare=7 where esbj_ifdeclare=2 AND id="
				+ m.getId();
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 外籍人社保变更申报修改状态
	public int ForeignerDeclareUpState(EmShebaoChangeForeignerModel m,
			String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_ForeignerDeclare_up_p_lwj(?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, Integer.parseInt(m.getEmsc_ifdeclare()));
			c.setString(3, m.getEmsc_reason());
			c.setString(4, username);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴编辑备注
	public int UpFlag(EmShebaoBJModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_BjDeclare_Upflag_p_lwj(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsb_flag());
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保补缴编辑备注(医疗)
	public int UpJLFlag(EmShebaoBJModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_BjDeclare_UpJLflag_p_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsb_flag());
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 外籍人社保变更编辑备注
	public int UpForeignerFlag(EmShebaoChangeForeignerModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_ForeignerDeclare_Upflag_p_lwj(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEmsc_flag());
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保生育津贴编辑备注
	public int UpChangeMaFlag(EmShebaoChangeMAModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_ChangeMADeclare_Upflag_p_lsb(?,?,?,?)");
			c.setInt(1, m.getId());
			c.setString(2, m.getEscm_flag());
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保生育津贴编辑批次号
	public int UpChangeMaBatchNum(EmShebaoChangeMAModel m) {
		String sql = "UPDATE EmShebaoChangeMa SET escm_batchnum='"
				+ m.getEscm_batchnum() + "' where id=" + m.getId();
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保生育津贴编辑批次号
	public int UpChangeMaFee(EmShebaoChangeMAModel m) {
		String sql = "UPDATE EmShebaoChangeMa SET escm_fee='"
				+ String.valueOf(m.getEscm_fee()) + "' where id=" + m.getId();

		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保生育津贴上传申请表和批量表
	public int UpChangeMaAfBfFile(EmShebaoChangeMAModel m, Integer chk_af,
			Integer chk_bf) {
		if (chk_af != 1 && chk_bf != 1) {
			return 0;
		} else {
			String sql_af = "";
			String sql_bf = "";
			if (chk_af == 1) {
				sql_af = ",escm_af_filename='" + m.getEscm_af_filename() + "'";
			}
			if (chk_bf == 1) {
				sql_bf = ",escm_bf_filename='" + m.getEscm_bf_filename() + "'";
			}

			String sql = "UPDATE EmShebaoChangeMa SET escm_confirmtime='"
					+ m.getEscm_confirmtime() + "'" + sql_af + sql_bf
					+ " where id=" + m.getId();
			try {
				PreparedStatement psmt = conn.getpre(sql);
				psmt.execute();
				return 1;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	// 社保设置
	public int Setup(EmShebaoSetupModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_Install_up_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getLastday());
			c.setString(3, m.getLastdayname());
			c.setInt(4, m.getLastdaybj());
			c.setString(5, m.getLastdaynamebj());
			c.setInt(6, m.getOnair());
			c.setString(7, m.getOnairname());
			c.setString(8, m.getReason());
			c.setInt(9, m.getOnairbj());
			c.setString(10, m.getOnairnamebj());
			c.setString(11, m.getReasonbj());
			c.setInt(12, m.getCwday());
			c.setInt(13, m.getFallday());
			c.setInt(14, m.getAuditday());
			c.setString(15, m.getAuditdayname());
			c.setInt(16, m.getMalastday());
			c.setString(17, m.getMalastdayname());
			c.registerOutParameter(18, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(18);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 社保申报分配
	public int Assignment(String client, String shebaodeclare) {
		try {
			CallableStatement c = conn
					.getcall("EMSC_Assignment_up_p_lwj(?,?,?)");
			c.setString(1, client);
			c.setString(2, shebaodeclare);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 新增生育津贴申请
	public int addEscmInfo(EmShebaoChangeMAModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_escmAdd_p_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setInt(2, m.getGid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEscm_ifdeclare());
			c.setString(5, UserInfo.getUsername());
			c.setInt(6, m.getEscm_easylabour());
			c.setInt(7, m.getEscm_easylabourmb());
			c.setInt(8, m.getEscm_dystocia());
			c.setInt(9, m.getEscm_dystociatype());
			c.setInt(10, m.getEscm_dystociamb());
			c.setInt(11, m.getEscm_abortion_fm());
			c.setInt(12, m.getEscm_abortion_nfm());
			c.setInt(13, m.getEscm_setiud());
			c.setInt(14, m.getEscm_getiud());
			c.setInt(15, m.getEscm_tuballigation());
			c.setInt(16, m.getEscm_tubalreversal());
			c.setInt(17, m.getEscm_vasoligation());
			c.setInt(18, m.getEscm_vasostomy());
			c.setString(19, m.getEscm_endoffp());
			c.setString(20, m.getEscm_mobile());
			c.setInt(21, m.getEscm_ifpay());
			c.setInt(22, m.getEscm_ifagree());
			c.setString(23, m.getEscm_remark());
			c.registerOutParameter(24, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(24);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 重新发送生育津贴申请
	public int reSendEscmInfo(EmShebaoChangeMAModel m) {
		try {

			CallableStatement c = conn
					.getcall("EMSI_escmReSend_p_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getGid());
			c.setInt(4, m.getOwnmonth());
			c.setInt(5, m.getEscm_ifdeclare());
			c.setString(6, UserInfo.getUsername());
			c.setInt(7, m.getEscm_easylabour());
			c.setInt(8, m.getEscm_easylabourmb());
			c.setInt(9, m.getEscm_dystocia());
			c.setInt(10, m.getEscm_dystociatype());
			c.setInt(11, m.getEscm_dystociamb());
			c.setInt(12, m.getEscm_abortion_fm());
			c.setInt(13, m.getEscm_abortion_nfm());
			c.setInt(14, m.getEscm_setiud());
			c.setInt(15, m.getEscm_getiud());
			c.setInt(16, m.getEscm_tuballigation());
			c.setInt(17, m.getEscm_tubalreversal());
			c.setInt(18, m.getEscm_vasoligation());
			c.setInt(19, m.getEscm_vasostomy());
			c.setString(20, m.getEscm_endoffp());
			c.setString(21, m.getEscm_mobile());
			c.setInt(22, m.getEscm_ifpay());
			c.setInt(23, m.getEscm_ifagree());
			c.setString(24, m.getEscm_remark());
			c.registerOutParameter(25, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(25);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 变更数据手动申报
	public int declareChangeMAState(EmShebaoChangeMAModel m) {
		try {
			CallableStatement c = conn
					.getcall("EMSI_DeclareChangeMAState_P_lsb(?,?,?,?,?)");
			c.setInt(1, m.getId());
			c.setInt(2, m.getEscm_ifdeclare());
			c.setString(3, m.getEscm_addname());
			c.setString(4, m.getEscm_flag());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 上传生育津贴决定书
	public int ChangeMAUploadDef(EmShebaoChangeMAModel m) {
		if (m.getId() != null && m.getEscm_def_filename() != null
				&& !"".equals(m.getEscm_def_filename())) {
			String sql = "UPDATE EmShebaoChangeMa SET escm_ifdeclare=11,escm_def_filetime=getdate(),escm_def_filename='"
					+ m.getEscm_def_filename() + "' where id=" + m.getId();
			try {
				PreparedStatement psmt = conn.getpre(sql);
				psmt.execute();
				return 1;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 上传生育津贴决定书
	public int ChangeMAConfirmFee(EmShebaoChangeMAModel m) {
		if (m.getId() != null) {
			String sql = "UPDATE EmShebaoChangeMa SET escm_ifdeclare=12,escm_fd_ctime=getdate(),escm_fd_cname='"
					+ UserInfo.getUsername() + "' where id=" + m.getId();
			try {
				PreparedStatement psmt = conn.getpre(sql);
				psmt.execute();
				return 1;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 上传生育津贴决定书
	public int ChangeMAFinish(EmShebaoChangeMAModel m) {
		if (m.getId() != null) {
			String sql = "UPDATE EmShebaoChangeMa SET escm_ifdeclare=13,escm_csd_ftime=getdate(),escm_csd_fname='"
					+ UserInfo.getUsername() + "' where id=" + m.getId();
			try {
				PreparedStatement psmt = conn.getpre(sql);
				psmt.execute();
				return 1;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}
}
