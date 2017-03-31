package dal.EmSalary;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoFormulaInfoModel;
import Model.CoSalaryItemFormulaModel;
import Model.CoSalaryItemIDInfoModel;
import Model.EmBaseESInCFInModel;
import Model.EmSalaryBaseAdd_viewModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmTXTFileInfoModel;
import Model.EmTXTFileSetModel;
import Util.UserInfo;

public class ItemFormula_OperateDal {
	private dbconn conn = new dbconn();

	// 更改工资项目顺序
	public int changeItemsSequence(String type, int csii_id) {
		try {
			CallableStatement c = conn
					.getcall("CoSalaryItemInfo_Sequence_p_lsb(?,?,?)");
			c.setString(1, type);
			c.setInt(2, csii_id);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 新增工资项目
	public int addSalaryItem(CoSalaryItemFormulaModel m, String csii_addname) {
		try {
			CallableStatement c = conn
					.getcall("CoSalaryItemInfo_Add_P_lsb(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setString(2, m.getCsii_itemid());
			c.setString(3, m.getCsii_item_name());
			c.setInt(4, m.getCsia_id());
			c.setString(5, m.getCfin_id());
			c.setInt(6, m.getCsii_fd_state());
			c.setInt(7, m.getCiin_id());
			c.setInt(8, m.getCsii_ifzero());
			c.setString(9, csii_addname);
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(10);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改工资项目
	public int updateSalaryItem(CoSalaryItemFormulaModel m) {
		try {

			CallableStatement c = conn
					.getcall("CoSalaryItemInfo_Update_P_lsb(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCsii_id());
			c.setString(2, m.getCsii_item_name());
			c.setInt(3, m.getCsia_id());
			c.setString(4, m.getCfin_id());
			c.setInt(5, m.getCiin_id());
			c.setInt(6, m.getCsii_fd_state());
			c.setInt(7, m.getCsii_ifzero());
			c.setInt(8, m.getOwnmonth());
			c.setString(9, m.getCsii_addname());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(10);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 删除工资项目
	public int delSalaryItem(CoSalaryItemFormulaModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoSalaryItemInfo_Delete_P_lsb(?,?,?,?)");
			c.setInt(1, m.getCsii_id());
			c.setInt(2, m.getOwnmonth());
			c.setString(3, m.getCsii_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(4);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 新增指定月份的默认项目
	public int finishSalaryItemId(CoSalaryItemIDInfoModel m) {
		try {

			CallableStatement c = conn
					.getcall("CoSalaryItemIDInfo_Finish_P_lsb(?,?,?)");
			c.setInt(1, m.getCsii_id());
			c.setString(2, m.getCsii_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 新增指定月份的默认项目
	public String[] addSalaryItemId(CoSalaryItemFormulaModel m) {
		String[] message = new String[2];
		try {

			CallableStatement c = conn
					.getcall("CoSalaryItemIDInfo_Add_P_lsb(?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setInt(2, m.getOwnmonth());
			c.setString(3, m.getCsii_addname());
			c.registerOutParameter(4, java.sql.Types.VARCHAR);
			c.registerOutParameter(5, java.sql.Types.VARCHAR);
			c.execute();

			message[0] = c.getString(4);
			message[1] = c.getString(5);

			return message;
		} catch (SQLException e) {
			message[0] = "1";
			message[1] = "0";
			return message;
		}
	}

	// 复制项目算法组合
	public int copySalaryFormulaItem(CoSalaryItemFormulaModel m, int c_cid,
			int c_ownmonth) {
		try {
			/*
			 * System.out.println(m.getCid());
			 * System.out.println(m.getOwnmonth()); System.out.println(c_cid);
			 * System.out.println(c_ownmonth);
			 * System.out.println(m.getCsii_addname());
			 */
			CallableStatement c = conn
					.getcall("CoSalaryItemInfo_Copy_P_lsb(?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setInt(2, m.getOwnmonth());
			c.setInt(3, c_cid);
			c.setInt(4, c_ownmonth);
			c.setString(5, m.getCsii_addname());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 检查工资项目算法组合是否可以修改
	public int chkSalaryFormulaItem(CoSalaryItemFormulaModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoSalaryItemIDInfo_Chk_P_lsb(?,?,?)");
			c.setInt(1, m.getCid());
			c.setInt(2, m.getOwnmonth());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 检查算法是否可以修改
	public int chkFormula(CoFormulaInfoModel m) {
		try {

			CallableStatement c = conn.getcall("CoFormulaInfo_Chk_P_lsb(?,?)");
			c.setInt(1, m.getCfin_id());
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(2);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 更改算法内容顺序
	public int changeForSequence(String type, int cfda_id) {
		try {

			CallableStatement c = conn
					.getcall("CoFormulaData_Sequence_p_lsb(?,?,?)");
			c.setString(1, type);
			c.setInt(2, cfda_id);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 删除算法内容
	public int delFormulaData(CoSalaryItemFormulaModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoFormulaData_Delete_P_lsb(?,?,?)");
			c.setString(1, m.getCfda_id());
			c.setString(2, m.getCsii_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 删除算法组合
	public int delFormulaInfo(CoFormulaInfoModel m) {
		try {

			CallableStatement c = conn
					.getcall("CoFormulaInfo_Delete_P_lsb(?,?,?)");
			c.setInt(1, m.getCfin_id());
			c.setString(2, m.getCfin_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 新增算法组合
	public int addFormulaInfo(int cid, String cfin_name, String cfin_remark,
			int c_cfin_id, String addname) {
		try {

			CallableStatement c = conn
					.getcall("CoFormulaInfo_Add_P_lsb(?,?,?,?,?,?)");
			c.setInt(1, cid);
			c.setString(2, cfin_name);
			c.setString(3, cfin_remark);
			c.setInt(4, c_cfin_id);
			c.setString(5, addname);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(6);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 新增算法内容
	public int addFormulaData(CoSalaryItemFormulaModel m) {
		try {

			CallableStatement c = conn
					.getcall("CoFormulaData_Add_P_lsb(?,?,?,?,?,?,?,?)");
			c.setString(1, m.getCfin_id());
			c.setInt(2, m.getCsii_id());
			c.setString(3, m.getCfda_formula());
			c.setString(4, m.getCfda_t_formula());
			c.setString(5, m.getCfda_range());
			c.setString(6, m.getCfda_t_range());
			c.setString(7, m.getCsii_addname());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(8);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改算法内容
	public int updateFormulaData(CoSalaryItemFormulaModel m) {
		try {

			CallableStatement c = conn
					.getcall("CoFormulaData_Update_P_lsb(?,?,?,?,?,?,?,?)");
			c.setString(1, m.getCfda_id());
			c.setString(2, m.getCfin_id());
			c.setString(3, m.getCfda_formula());
			c.setString(4, m.getCfda_t_formula());
			c.setString(5, m.getCfda_range());
			c.setString(6, m.getCfda_t_range());
			c.setString(7, m.getCsii_addname());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(8);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改算法内容
	public int assFormulaInfo(EmBaseESInCFInModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoFormulaInfo_Assign_P_lsb(?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setInt(2, m.getGid());
			c.setString(3, m.getCfin_id());
			c.setString(4, m.getEsin_addname());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 删除txt数据
	public int delTXT(EmTXTFileInfoModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_TXTDelete_P_lsb(?,?,?)");
			c.setInt(1, m.getEtfi_id());
			c.setString(2, m.getEtfi_txt_people());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改账户名
	public int updateTXTBN(EmTXTFileInfoModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_BN_Update_P_lsb(?,?,?,?)");
			c.setInt(1, m.getEtfi_id());
			c.setString(2, m.getEtfi_ba_name());
			c.setString(3, m.getEtfi_txt_people());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(4);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改账户名(未报盘)
	public int updateESDABN(EmSalaryBaseSel_viewModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_upBaName_P_lsb(?,?,?,?)");
			c.setInt(1, m.getEsda_id());
			c.setString(2, m.getEsda_ba_name());
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(4);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 生成历史数据
	public int addHistoryData(EmTXTFileInfoModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryHistoryData_Add_P_lsb(?,?,?,?)");
			c.setInt(1, m.getEsda_id());
			c.setString(2, m.getEtfi_payment_batch());
			c.setString(3, m.getEtfi_txt_people());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(4);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 更新txt状态
	public int updateTXTState(EmTXTFileInfoModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_TXTState_P_lsb(?,?,?,?)");
			c.setString(1, m.getEtfi_txt_date());
			c.setString(2, m.getEtfi_payment_batch());
			c.setString(3, m.getEtfi_txt_people());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(4);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 更新CoSalaryItemIDInfo表流程ID
	public boolean upCoSalaryItemIDInfoTaprId(int tapr_id, int dataid) {
		String sql = "update CoSalaryItemIDInfo  set csii_tapr_id=? where csii_id=?";
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

	// 更新EmSalaryData表流程ID
	public boolean upEmSalaryDataTaprId(int tapr_id, int dataid) {
		String sql = "update EmSalaryData  set esda_tapr_id=? where esda_id=?";
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

	// 更新EmSalaryInfo表流程ID
	public boolean upEmSalaryInfoTaprId(int tapr_id, int dataid) {
		String sql = "update EmSalaryInfo  set esin_tapr_id=? where esin_id=?";
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

	// 工资重发原因
	public int repayReason(EmSalaryBaseSel_viewModel m, String username) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_RePay_P_lsb(?,?,?,?)");
			c.setInt(1, m.getEsda_id());
			c.setString(2, m.getEsda_rp_reason());
			c.setString(3, username);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(4);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改银行信息
	public int upBankInfo(EmSalaryBaseSel_viewModel esdaM,
			EmSalaryBaseAdd_viewModel embaM, String username) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_UpBank_P_lsb(?,?,?,?,?,?,?,?)");
			c.setInt(1, esdaM.getEsda_id());
			c.setString(2, esdaM.getEsda_ba_name());
			c.setString(3, embaM.getEmba_gz_bank());
			c.setString(4, embaM.getEmba_gz_account());
			c.setString(5, embaM.getEmba_writeoff_bank());
			c.setString(6, embaM.getEmba_writeoff_account());
			c.setString(7, username);
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(8);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 复制EmSalaryData表数据到EmSalaryDataTemp
	public int addESDTInfo(EmSalaryBaseSel_viewModel esdaM, String username) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryDataTemp_Add_p_lsb(?,?,?)");
			c.setInt(1, esdaM.getEsda_id());
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改工资项目清零状态为临时清零
	public int upZeroItemState(String str) {
		String sql = "UPDATE CoSalaryItemInfo SET csii_ifzero=2 WHERE csii_id IN("
				+ str + ")";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新算法内容及顺序
	public int upFormulaSeq(Integer cfda_id, Integer cfda_sequence,
			String cfda_formula, String cfda_t_formula) {
		String sql = "UPDATE CoFormulaData SET cfda_sequence=" + cfda_sequence
				+ ",cfda_formula='" + cfda_formula + "',cfda_t_formula='"
				+ cfda_t_formula + "' WHERE cfda_id=" + cfda_id;
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新算法顺序
	public int upFormulaSeq(Integer cfda_id, Integer cfda_sequence) {
		String sql = "UPDATE CoFormulaData SET cfda_sequence=" + cfda_sequence
				+ " WHERE cfda_id=" + cfda_id;
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新项目顺序
	public int upItemSeq(Integer csii_id, Integer csii_sequence) {
		String sql = "UPDATE CoSalaryItemInfo SET csii_sequence="
				+ csii_sequence + " WHERE csii_id=" + csii_id;
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新项目顺序
	public int changeSalaryItemsDis(Integer csii_id, Integer csii_fd_state) {
		String sql = "UPDATE CoSalaryItemInfo SET csii_fd_state="
				+ csii_fd_state + ",csii_csd_state=" + csii_fd_state
				+ " WHERE csii_id=" + csii_id;
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 单独设置非清零状态
	public int changeIfZero(Integer csii_id, Integer state) {
		String sql = "UPDATE CoSalaryItemInfo SET csii_ifzero=" + state
				+ " WHERE csii_id=" + csii_id;
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 添加当日银行余额
	public int addTXTFileSet(BigDecimal balance) {
		String sql = "insert into emtxtfileset(etfs_balance,etfs_remaining,etfs_addname) values("
				+ balance
				+ ","
				+ balance
				+ ",'"
				+ UserInfo.getUsername()
				+ "')";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新当日银行剩余金额
	public int updateTXTremaining(EmTXTFileSetModel m) {
		String sql = "update emtxtfileset set etfs_remaining="
				+ m.getEtfs_remaining() + "  where etfs_id=" + m.getEtfs_id();
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新工资项目名
	public int updateSalaryItemName(CoSalaryItemFormulaModel m) {
		String sql = "update CoSalaryItemInfo set csii_item_name=? where csii_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, m.getCsii_item_name());
			psmt.setInt(2, m.getCsii_id());
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 更新算法内容的项目名
	public int updateSalaryFormula(String newName, String oldName,
			Integer cfin_id) {
		String sql = "update CoFormulaData set cfda_t_formula=REPLACE(cfda_t_formula,'['+'"
				+ oldName
				+ "'+']','['+'"
				+ newName
				+ "'+']'),cfda_t_range=REPLACE(cfda_t_range,'['+'"
				+ oldName
				+ "'+']','['+'" + newName + "'+']') where cfin_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cfin_id);
			psmt.execute();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}
}
