package dal.EmSalary;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import Util.UserInfo;

public class EmSalary_SalaryOperateDal {
	private static dbconn conn = new dbconn();

	// 根据查询语句获取公司算法项目列表
	private ResultSet getCobasSalaryCount(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" select *  from View_CSII_CSIIDI where 1=1   ");
		sql.append(str);
		sql.append(" order by csii_sequence desc");
		System.out.println(sql);
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 新增工资
	public int AddSalary(EmSalaryDataModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_Add_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getOwnmonth());
			c.setInt(4, m.getEsda_if_bms());
			c.setString(5, m.getEsda_addname());
			c.setInt(6, m.getEsda_payment_state());
			c.setInt(7, m.getEsda_oof_state());
			c.setString(8, m.getEsda_remark());
			c.setString(9, m.getEsda_fd_remark());
			c.setInt(10, m.getCfin_id());
			c.setString(11, m.getCsii_itemid());
			c.setString(12, m.getEsda_o_bank());
			c.setString(13, m.getEsda_bank());
			c.setString(14, m.getEsda_ba_name());
			c.setString(15, m.getEsda_bank_account());
			c.setString(16, m.getEsda_nationality());
			c.setInt(17, m.getEsda_usage_type());
			c.setInt(18, m.getEsda_data_type());
			c.setBigDecimal(19, m.getEsda_base_radix());
			c.setBigDecimal(20, m.getEsda_a_base_radix());
			c.setBigDecimal(21, m.getEsda_a_workdays());
			c.setBigDecimal(22, m.getEsda_a_base_salary());
			c.setBigDecimal(23, m.getEsda_days());
			c.setBigDecimal(24, m.getEsda_cqdays());
			c.setBigDecimal(25, m.getEsda_b_cqdays());
			c.setBigDecimal(26, m.getEsda_qqdays());
			c.setBigDecimal(27, m.getEsda_b_qqdays());
			c.setBigDecimal(28, m.getEsda_aded());
			c.setBigDecimal(29, m.getEsda_a_aded());
			c.setBigDecimal(30, m.getEsda_base_salary());
			c.setBigDecimal(31, m.getEsda_siop());
			c.setBigDecimal(32, m.getEsda_hafop());
			c.setBigDecimal(33, m.getEsda_haf());
			c.setBigDecimal(34, m.getEsda_house_radix());
			c.setBigDecimal(35, m.getEsda_house_percent());
			c.setBigDecimal(36, m.getEsda_house_ntl());
			c.setBigDecimal(37, m.getEsda_total_pretax());
			c.setBigDecimal(38, m.getEsda_tax_base());
			c.setBigDecimal(39, m.getEsda_tax());
			c.setBigDecimal(40, m.getEsda_dc());
			c.setBigDecimal(41, m.getEsda_dc_tax());
			c.setBigDecimal(42, m.getEsda_db());
			c.setBigDecimal(43, m.getEsda_db_tax_base());
			c.setBigDecimal(44, m.getEsda_db_tax());
			c.setBigDecimal(45, m.getEsda_stock());
			c.setBigDecimal(46, m.getEsda_stock_tax());
			c.setBigDecimal(47, m.getEsda_write_off());
			c.setBigDecimal(48, m.getEsda_house_bf());
			c.setBigDecimal(49, m.getEsda_pay());
			c.setBigDecimal(50, m.getEsda_col_1());
			c.setBigDecimal(51, m.getEsda_col_2());
			c.setBigDecimal(52, m.getEsda_col_3());
			c.setBigDecimal(53, m.getEsda_col_4());
			c.setBigDecimal(54, m.getEsda_col_5());
			c.setBigDecimal(55, m.getEsda_col_6());
			c.setBigDecimal(56, m.getEsda_col_7());
			c.setBigDecimal(57, m.getEsda_col_8());
			c.setBigDecimal(58, m.getEsda_col_9());
			c.setBigDecimal(59, m.getEsda_col_10());
			c.setBigDecimal(60, m.getEsda_col_11());
			c.setBigDecimal(61, m.getEsda_col_12());
			c.setBigDecimal(62, m.getEsda_col_13());
			c.setBigDecimal(63, m.getEsda_col_14());
			c.setBigDecimal(64, m.getEsda_col_15());
			c.setBigDecimal(65, m.getEsda_col_16());
			c.setBigDecimal(66, m.getEsda_col_17());
			c.setBigDecimal(67, m.getEsda_col_18());
			c.setBigDecimal(68, m.getEsda_col_19());
			c.setBigDecimal(69, m.getEsda_col_20());
			c.setBigDecimal(70, m.getEsda_col_21());
			c.setBigDecimal(71, m.getEsda_col_22());
			c.setBigDecimal(72, m.getEsda_col_23());
			c.setBigDecimal(73, m.getEsda_col_24());
			c.setBigDecimal(74, m.getEsda_col_25());
			c.setBigDecimal(75, m.getEsda_col_26());
			c.setBigDecimal(76, m.getEsda_col_27());
			c.setBigDecimal(77, m.getEsda_col_28());
			c.setBigDecimal(78, m.getEsda_col_29());
			c.setBigDecimal(79, m.getEsda_col_30());
			c.setBigDecimal(80, m.getEsda_col_31());
			c.setBigDecimal(81, m.getEsda_col_32());
			c.setBigDecimal(82, m.getEsda_col_33());
			c.setBigDecimal(83, m.getEsda_col_34());
			c.setBigDecimal(84, m.getEsda_col_35());
			c.setBigDecimal(85, m.getEsda_col_36());
			c.setBigDecimal(86, m.getEsda_col_37());
			c.setBigDecimal(87, m.getEsda_col_38());
			c.setBigDecimal(88, m.getEsda_col_39());
			c.setBigDecimal(89, m.getEsda_col_40());
			c.setBigDecimal(90, m.getEsda_col_41());
			c.setBigDecimal(91, m.getEsda_col_42());
			c.setBigDecimal(92, m.getEsda_col_43());
			c.setBigDecimal(93, m.getEsda_col_44());
			c.setBigDecimal(94, m.getEsda_col_45());
			c.setBigDecimal(95, m.getEsda_col_46());
			c.setBigDecimal(96, m.getEsda_col_47());
			c.setBigDecimal(97, m.getEsda_col_48());
			c.setBigDecimal(98, m.getEsda_col_49());
			c.setBigDecimal(99, m.getEsda_col_50());
			c.setBigDecimal(100, m.getEsda_col_51());
			c.setBigDecimal(101, m.getEsda_col_52());
			c.setBigDecimal(102, m.getEsda_col_53());
			c.setBigDecimal(103, m.getEsda_col_54());
			c.setBigDecimal(104, m.getEsda_col_55());
			c.setBigDecimal(105, m.getEsda_col_56());
			c.setBigDecimal(106, m.getEsda_col_57());
			c.setBigDecimal(107, m.getEsda_col_58());
			c.setBigDecimal(108, m.getEsda_col_59());
			c.setBigDecimal(109, m.getEsda_col_60());
			c.setBigDecimal(110, m.getEsda_col_61());
			c.setBigDecimal(111, m.getEsda_col_62());
			c.setBigDecimal(112, m.getEsda_col_63());
			c.setBigDecimal(113, m.getEsda_col_64());
			c.setBigDecimal(114, m.getEsda_col_65());
			c.setBigDecimal(115, m.getEsda_col_66());
			c.setBigDecimal(116, m.getEsda_col_67());
			c.setBigDecimal(117, m.getEsda_col_68());
			c.setBigDecimal(118, m.getEsda_col_69());
			c.setBigDecimal(119, m.getEsda_col_70());
			c.setBigDecimal(120, m.getEsda_col_71());
			c.setBigDecimal(121, m.getEsda_col_72());
			c.setBigDecimal(122, m.getEsda_col_73());
			c.setBigDecimal(123, m.getEsda_col_74());
			c.setBigDecimal(124, m.getEsda_col_75());
			c.setBigDecimal(125, m.getEsda_col_76());
			c.setBigDecimal(126, m.getEsda_col_77());
			c.setBigDecimal(127, m.getEsda_col_78());
			c.setBigDecimal(128, m.getEsda_col_79());
			c.setBigDecimal(129, m.getEsda_col_80());
			c.setBigDecimal(130, m.getEsda_lw_tax_base());
			c.setBigDecimal(131, m.getEsda_lw_tax());
			c.setBigDecimal(132, m.getEsda_total_adjtax());
			c.setBigDecimal(133, m.getEsda_total_afttax());
			c.setBigDecimal(134, m.getEsda_hafcp());
			c.setBigDecimal(135, m.getEsda_house_cradix());
			c.setBigDecimal(136, m.getEsda_house_cpercent());
			c.registerOutParameter(137, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(137);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 修改工资
	public int UpSalary(EmSalaryDataModel m) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_Update_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEsda_id());
			c.setBigDecimal(2, m.getEsda_base_radix());
			c.setBigDecimal(3, m.getEsda_a_base_radix());
			c.setBigDecimal(4, m.getEsda_a_workdays());
			c.setBigDecimal(5, m.getEsda_a_base_salary());
			c.setBigDecimal(6, m.getEsda_days());
			c.setBigDecimal(7, m.getEsda_cqdays());
			c.setBigDecimal(8, m.getEsda_b_cqdays());
			c.setBigDecimal(9, m.getEsda_qqdays());
			c.setBigDecimal(10, m.getEsda_b_qqdays());
			c.setBigDecimal(11, m.getEsda_aded());
			c.setBigDecimal(12, m.getEsda_a_aded());
			c.setBigDecimal(13, m.getEsda_base_salary());
			c.setBigDecimal(14, m.getEsda_siop());
			c.setBigDecimal(15, m.getEsda_hafop());
			c.setBigDecimal(16, m.getEsda_haf());
			c.setBigDecimal(17, m.getEsda_house_radix());
			c.setBigDecimal(18, m.getEsda_house_percent());
			c.setBigDecimal(19, m.getEsda_house_ntl());
			c.setBigDecimal(20, m.getEsda_total_pretax());
			c.setBigDecimal(21, m.getEsda_tax_base());
			c.setBigDecimal(22, m.getEsda_tax());
			c.setBigDecimal(23, m.getEsda_dc());
			c.setBigDecimal(24, m.getEsda_dc_tax());
			c.setBigDecimal(25, m.getEsda_db());
			c.setBigDecimal(26, m.getEsda_db_tax_base());
			c.setBigDecimal(27, m.getEsda_db_tax());
			c.setBigDecimal(28, m.getEsda_stock());
			c.setBigDecimal(29, m.getEsda_stock_tax());
			c.setBigDecimal(30, m.getEsda_write_off());
			c.setBigDecimal(31, m.getEsda_house_bf());
			c.setBigDecimal(32, m.getEsda_pay());
			c.setBigDecimal(33, m.getEsda_col_1());
			c.setBigDecimal(34, m.getEsda_col_2());
			c.setBigDecimal(35, m.getEsda_col_3());
			c.setBigDecimal(36, m.getEsda_col_4());
			c.setBigDecimal(37, m.getEsda_col_5());
			c.setBigDecimal(38, m.getEsda_col_6());
			c.setBigDecimal(39, m.getEsda_col_7());
			c.setBigDecimal(40, m.getEsda_col_8());
			c.setBigDecimal(41, m.getEsda_col_9());
			c.setBigDecimal(42, m.getEsda_col_10());
			c.setBigDecimal(43, m.getEsda_col_11());
			c.setBigDecimal(44, m.getEsda_col_12());
			c.setBigDecimal(45, m.getEsda_col_13());
			c.setBigDecimal(46, m.getEsda_col_14());
			c.setBigDecimal(47, m.getEsda_col_15());
			c.setBigDecimal(48, m.getEsda_col_16());
			c.setBigDecimal(49, m.getEsda_col_17());
			c.setBigDecimal(50, m.getEsda_col_18());
			c.setBigDecimal(51, m.getEsda_col_19());
			c.setBigDecimal(52, m.getEsda_col_20());
			c.setBigDecimal(53, m.getEsda_col_21());
			c.setBigDecimal(54, m.getEsda_col_22());
			c.setBigDecimal(55, m.getEsda_col_23());
			c.setBigDecimal(56, m.getEsda_col_24());
			c.setBigDecimal(57, m.getEsda_col_25());
			c.setBigDecimal(58, m.getEsda_col_26());
			c.setBigDecimal(59, m.getEsda_col_27());
			c.setBigDecimal(60, m.getEsda_col_28());
			c.setBigDecimal(61, m.getEsda_col_29());
			c.setBigDecimal(62, m.getEsda_col_30());
			c.setBigDecimal(63, m.getEsda_col_31());
			c.setBigDecimal(64, m.getEsda_col_32());
			c.setBigDecimal(65, m.getEsda_col_33());
			c.setBigDecimal(66, m.getEsda_col_34());
			c.setBigDecimal(67, m.getEsda_col_35());
			c.setBigDecimal(68, m.getEsda_col_36());
			c.setBigDecimal(69, m.getEsda_col_37());
			c.setBigDecimal(70, m.getEsda_col_38());
			c.setBigDecimal(71, m.getEsda_col_39());
			c.setBigDecimal(72, m.getEsda_col_40());
			c.setBigDecimal(73, m.getEsda_col_41());
			c.setBigDecimal(74, m.getEsda_col_42());
			c.setBigDecimal(75, m.getEsda_col_43());
			c.setBigDecimal(76, m.getEsda_col_44());
			c.setBigDecimal(77, m.getEsda_col_45());
			c.setBigDecimal(78, m.getEsda_col_46());
			c.setBigDecimal(79, m.getEsda_col_47());
			c.setBigDecimal(80, m.getEsda_col_48());
			c.setBigDecimal(81, m.getEsda_col_49());
			c.setBigDecimal(82, m.getEsda_col_50());
			c.setBigDecimal(83, m.getEsda_col_51());
			c.setBigDecimal(84, m.getEsda_col_52());
			c.setBigDecimal(85, m.getEsda_col_53());
			c.setBigDecimal(86, m.getEsda_col_54());
			c.setBigDecimal(87, m.getEsda_col_55());
			c.setBigDecimal(88, m.getEsda_col_56());
			c.setBigDecimal(89, m.getEsda_col_57());
			c.setBigDecimal(90, m.getEsda_col_58());
			c.setBigDecimal(91, m.getEsda_col_59());
			c.setBigDecimal(92, m.getEsda_col_60());
			c.setBigDecimal(93, m.getEsda_col_61());
			c.setBigDecimal(94, m.getEsda_col_62());
			c.setBigDecimal(95, m.getEsda_col_63());
			c.setBigDecimal(96, m.getEsda_col_64());
			c.setBigDecimal(97, m.getEsda_col_65());
			c.setBigDecimal(98, m.getEsda_col_66());
			c.setBigDecimal(99, m.getEsda_col_67());
			c.setBigDecimal(100, m.getEsda_col_68());
			c.setBigDecimal(101, m.getEsda_col_69());
			c.setBigDecimal(102, m.getEsda_col_70());
			c.setBigDecimal(103, m.getEsda_col_71());
			c.setBigDecimal(104, m.getEsda_col_72());
			c.setBigDecimal(105, m.getEsda_col_73());
			c.setBigDecimal(106, m.getEsda_col_74());
			c.setBigDecimal(107, m.getEsda_col_75());
			c.setBigDecimal(108, m.getEsda_col_76());
			c.setBigDecimal(109, m.getEsda_col_77());
			c.setBigDecimal(110, m.getEsda_col_78());
			c.setBigDecimal(111, m.getEsda_col_79());
			c.setBigDecimal(112, m.getEsda_col_80());
			c.setBigDecimal(113, m.getEsda_lw_tax_base());
			c.setBigDecimal(114, m.getEsda_lw_tax());
			c.setBigDecimal(115, m.getEsda_total_adjtax());
			c.setBigDecimal(116, m.getEsda_total_afttax());
			c.setBigDecimal(117, m.getEsda_hafcp());
			c.setBigDecimal(118, m.getEsda_house_cradix());
			c.setBigDecimal(119, m.getEsda_house_cpercent());
			c.setString(120, m.getEsda_remark());
			c.setString(121, m.getEsda_fd_remark());
			c.setInt(122, m.getEsda_oof_state());
			c.registerOutParameter(123, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(123);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 删除工资
	public int DelSalary(int esda_id) {
		try {
			CallableStatement c = conn.getcall("EmSalaryData_Del_p_lwj(?,?)");
			c.setInt(1, esda_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 根据cfin_id获取算法
	public List<String[]> getAlgorithmInfo(int cfin_id) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = "SELECT csii_col,cfda_formula,cfda_range FROM CFDa_CFIn_V WHERE cfin_id=? ORDER BY cfda_sequence";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cfin_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new String[] { rs.getString("csii_col"),
						rs.getString("cfda_formula"),
						rs.getString("cfda_range") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 确认工资任务单插入TaskBatch表信息
	public int addTaskBatch(String username, String remark) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_ConfirmToTb_p_lwj(?,?,?)");
			c.setString(1, username);
			c.setString(2, remark);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 批量确认工资任务单插入TaskBatchRelBusiness表信息
	public int addTaskBatchRelBusiness(int tbrb_taba_id, int esda_id) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryData_ConfirmToTbrb_p_lwj(?,?,?)");
			c.setInt(1, tbrb_taba_id);
			c.setInt(2, esda_id);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新TaskBatch表流程ID
	public boolean upTaskBatchTaprId(int tapri_id, int dataid) {
		String sql = "update TaskBatch  set taba_tapr_id=? where taba_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, tapri_id);
			psmt.setInt(2, dataid);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			b = false;
		}
		return b;
	}

	// 薪酬负责人设置
	public int setGzUser(int cid, String gzUser, String gzAudUser) {
		try {
			CallableStatement c = conn
					.getcall("EmSalary_CompanySet_p_lwj(?,?,?,?)");
			c.setInt(1, cid);
			c.setString(2, gzUser);
			c.setString(3, gzAudUser);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 将工资表数据复制到Temp表，并将任务单插入TaskBatchRelBusiness表信息
	public int addEmSalaryDataTemp(int tbrb_taba_id, int esda_id, String addname) {
		try {
			CallableStatement c = conn
					.getcall("EmSalaryDataTemp_Add_p_lsb(?,?,?,?)");
			c.setInt(1, tbrb_taba_id);
			c.setInt(2, esda_id);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 新增薪酬信息
	public int addEmSalaryInfo(EmSalaryInfoModel m) {
		try {

			CallableStatement c = conn
					.getcall("EmSalaryInfoAdd_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setInt(2, m.getGid());
			c.setInt(3, m.getCfin_id());
			c.setString(4, m.getEmba_nationality());
			c.setString(5, m.getEsin_taxplace());
			c.setString(6, m.getEsin_salaryplace());
			c.setString(7, m.getEsin_hpro());
			c.setString(8, m.getEsin_addname());
			c.setString(9, m.getEmba_gz_bank());
			c.setString(10, m.getEmba_gz_account());
			c.setString(11, m.getEmba_writeoff_bank());
			c.setString(12, m.getEmba_writeoff_account());
			c.setString(13, m.getEmba_gz_email());
			c.setString(14, m.getEsda_ba_name());
			c.setInt(15, m.getEsin_nexttaxplace_smonth());
			c.registerOutParameter(16, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(16);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 修改电汇审核状态byCID
	public int upTTVState(Integer cid, String payDate) {
		try {

			CallableStatement c = conn
					.getcall("EmSalaryData_TVTState_P_lsb(?,?,?,?)");
			c.setInt(1, cid);
			c.setString(2, payDate);
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 修改电汇审核状态by发放日期
	public int upTTVStateAll(String payDate) {
		try {

			CallableStatement c = conn
					.getcall("EmSalaryData_TVTAllState_P_lsb(?,?,?)");
			c.setString(1, payDate);
			c.setString(2, UserInfo.getUsername());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// 支付模块数据重发
	public int repayEmPay(Integer empa_id, Integer reason) {
		try {

			CallableStatement c = conn
					.getcall("EmPayRePay_P_lsb(?,?,?,?)");
			c.setInt(1, empa_id);
			c.setInt(2, reason);
			c.setString(3, UserInfo.getUsername());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}
}
