package dal.SalaryPaper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoSalaryItemInfoModel;
import Model.CoSalaryMbModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import Model.PubEmailModel;
import Model.SalaryPaperCoModel;
import Model.SalarySendTypeModel;

public class SalaryPaperDal extends dbconn {

	public List<SalaryPaperCoModel> getCoSetList() {
		List<SalaryPaperCoModel> l = new ArrayList<SalaryPaperCoModel>();
		String sql = " select coss_id,b.cid,coba_company,coss_carboncopy,coss_ccaddress,coss_secretsend,coss_secretsendaddress,coss_sendstate,coba_shortname,coba_shortspell from CoSalarySet a "
				+ " left join CoBase b  on a.CID = b.cid where a.coss_payrollpapertype is not null and a.coss_payrollpapertype='E-mail工资单' order by coba_shortspell";

		try {
			l = find(sql, SalaryPaperCoModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	// 查询待选择的工资单项目
	public List<CoSalaryItemInfoModel> getWaitSitemList(int cid, int ownmonth) {
		List<CoSalaryItemInfoModel> l = new ArrayList<CoSalaryItemInfoModel>();
		String sql = "SELECT * FROM CoSalaryItemInfo WHERE csii_itemid in (select csii_itemid from CoSalaryItemIDInfo where cid = ? and ownmonth=? group by csii_itemid ) and csii_csd_state = 0 order by csii_sequence";
		Object[] objs = { cid, ownmonth };
		try {
			l = find(sql, CoSalaryItemInfoModel.class, null, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	// 获取工资单model
	public List<SalaryPaperCoModel> getmodellist(int str) {

		List<SalaryPaperCoModel> l = new ArrayList<SalaryPaperCoModel>();
		String sql = "select *,"
				+ "CAST(coss_mfdate AS nvarchar(2))+'日' as coss_mfdatestr,"
				+ "CAST(coss_emailsenddate AS nvarchar(2))+'日' as coss_emailsenddatestr,"
				+ "case coss_ifmfdd when 0 then '否' when 1 then '是' end  coss_ifmfddstr ,"
				+ "case coss_emailouto when 0 then '否' when 1 then '是' end  coss_emailoutostr "
				+ " from CoSalarySet where cid=?";
		Object[] objs = { str };
		try {
			l = find(sql, SalaryPaperCoModel.class, null, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	// 查询已经选择的工资单项目
	public List<CoSalaryItemInfoModel> getIetmList(int cid, int ownmonth) {
		List<CoSalaryItemInfoModel> l = new ArrayList<CoSalaryItemInfoModel>();
		String sql = "SELECT * FROM CoSalaryItemInfo WHERE csii_itemid in (select csii_itemid from CoSalaryItemIDInfo where cid = ? and ownmonth=? group by csii_itemid ) and (csii_csd_state = 1)  order by csii_sequence";
		Object[] objs = { cid, ownmonth };
		try {
			l = find(sql, CoSalaryItemInfoModel.class, null, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	// 保存工资单项目的设置
	public boolean itemMoveSave(List<CoSalaryItemInfoModel> wList,
			List<CoSalaryItemInfoModel> itemList,
			List<CoSalaryItemInfoModel> itemList2) {
		int count = 0;
		int count1 = 0;
		int count2 = 0;
		boolean flag = false;
		Connection conn = null;
		try {
			// 开启事务
			conn = getConn();
			PreparedStatement pstmt = null;
			conn.setAutoCommit(false);
			for (int i = 0; i < wList.size(); i++) {
				String sql = " UPDATE CoSalaryItemInfo SET csii_csd_state = 0 where csii_id="
						+ wList.get(i).getCsii_id();
				pstmt = conn.prepareStatement(sql);
				count = pstmt.executeUpdate() + count;
			}
			for (int i = 0; i < itemList.size(); i++) {
				String sql = " UPDATE CoSalaryItemInfo SET csii_csd_state = 1 where csii_id="
						+ itemList.get(i).getCsii_id();
				pstmt = conn.prepareStatement(sql);
				count1 = pstmt.executeUpdate() + count1;
			}
			for (int i = 0; i < itemList2.size(); i++) {
				String sql = " UPDATE CoSalaryItemInfo SET csii_csd_state = 2 where csii_id="
						+ itemList2.get(i).getCsii_id();
				pstmt = conn.prepareStatement(sql);
				count2 = pstmt.executeUpdate() + count2;
			}
			if (count + count1 + count2 == wList.size() + itemList.size()
					+ itemList2.size()) {
				conn.commit(); // 提交事务
				flag = true;
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
	}

	// 修改工资单项目名称
	public void UpdateItemname(CoSalaryItemInfoModel m) {
		String sql = " UPDATE CoSalaryItemInfo SET csii_item_name = ? WHERE csii_id=?";
		Object[] objs = { m.getCsii_item_name(), m.getCsii_id() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 修改工资单项目英文名
	public void UpdateItemen(CoSalaryItemInfoModel m) {
		String sql = " UPDATE CoSalaryItemInfo SET csii_item_englishname = ? WHERE csii_id=?";
		Object[] objs = { m.getCsii_item_englishname(), m.getCsii_id() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 修改工资单项目别名
	public void UpdateIteman(CoSalaryItemInfoModel m) {
		String sql = " UPDATE CoSalaryItemInfo SET csii_item_anothername = ? WHERE csii_id=?";
		Object[] objs = { m.getCsii_item_anothername(), m.getCsii_id() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 公司设置
	public int coInfoSet(SalaryPaperCoModel m) {
		int row = 0;
		String sql = " UPDATE CoSalarySet SET coss_sendstate=?,coss_carbonCopy=?,coss_ccaddress=?,coss_secretsend=?,coss_secretsendaddress=? WHERE coss_id=?";
		Object[] objs = { m.getCoss_sendstate(), m.getCoss_carbonCopy(),
				m.getCoss_ccaddress(), m.getCoss_secretsend(),
				m.getCoss_secretsendaddress(), m.getCoss_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 公司设置新增
	public int coInfoadd(SalaryPaperCoModel m) {
		dbconn conn = new dbconn();

		try {
			CallableStatement c = conn
					.getcall("CoSalarySet_ADD_zmj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
							+ "?,?)");

			c.setInt(1, m.getCoss_id());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getCoss_sendstate());
			c.setInt(4, m.getCoss_carboncopy());
			c.setString(5, m.getCoss_ccaddress());
			c.setInt(6, m.getCoss_secretsend());
			c.setString(7, m.getCoss_secretsendaddress());
			c.setString(8, m.getCoss_payrollpapertype());
			c.setInt(9, m.getCoss_mfdate(m.getCoss_mfdatestr()));
			c.setString(10, m.getCoss_mfname());
			c.setInt(11, m.getCoss_ifmfdd(m.getCoss_ifmfddstr()));
			c.setInt(12, m.getCoss_emailsenddate(m.getCoss_emailsenddatestr()));
			c.setString(13, m.getCoss_emailtype());
			c.setString(14, m.getCoss_emailas());
			c.setString(15, m.getCoss_emailcs());
			c.setInt(16, m.getCoss_emailouto(m.getCoss_emailoutostr()));

			c.registerOutParameter(1, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(1);
		} catch (SQLException e) {
			return 1;
		}

	}

	// 根据cid得到员工的工资信息
	public List<EmSalaryInfoModel> getPlEsInfoList(String cid) {
		List<EmSalaryInfoModel> l = new ArrayList<EmSalaryInfoModel>();
		String sql = " SELECT esin_id,b.cid,coba_company,b.coba_shortname,c.gid,emba_name,cfin_id,esda_ba_name,esda_bcc_email,esin_addname,esin_taxplace,esin_salaryplace,esin_hpro,esin_tapr_id,esin_cost_Type,esin_attachpassword,esin_cosm_typeurl,esin_cosm_model "
				+ " FROM EmSalaryInfo a "
				+ " left join CoBase b "
				+ " on a.cid = b.CID "
				+ " left join EmBase c "
				+ " on a.gid = c.gid  WHERE b.cid in"
				+ cid
				+ " order by cid,gid asc";

		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				EmSalaryInfoModel m = new EmSalaryInfoModel();
				m.setEsin_id(rs.getInt("esin_id"));
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("emba_name"));
				m.setCfin_id(rs.getInt("cfin_id"));
				m.setEsda_ba_name(rs.getString("esda_ba_name"));
				m.setEsda_bcc_email(rs.getString("esda_bcc_email"));
				m.setEsin_addname(rs.getString("esin_addname"));
				m.setEsin_taxplace(rs.getString("esin_taxplace"));
				m.setEsin_salaryplace(rs.getString("esin_salaryplace"));
				m.setEsin_hpro(rs.getString("esin_hpro"));
				m.setEsin_tapr_id(rs.getInt("esin_tapr_id"));
				m.setEsin_cost_Type(rs.getString("esin_cost_Type"));
				m.setEsin_attachpassword(rs.getString("esin_attachpassword"));
				m.setEsin_cosm_typeurl(rs.getString("esin_cosm_typeurl"));
				m.setEsin_cosm_model(rs.getString("esin_cosm_model"));
				m.setCompany(rs.getString("coba_shortname"));
				l.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	// 获取发送方式信息
	public List<SalarySendTypeModel> getStmList() {
		List<SalarySendTypeModel> l = new ArrayList<SalarySendTypeModel>();
		String sql = "SELECT * FROM CoSalarySendType ";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				SalarySendTypeModel m = new SalarySendTypeModel();
				m.setCost_id(rs.getInt("cost_id"));
				m.setCost_name(rs.getString("cost_name"));
				m.setCost_state(rs.getInt("cost_state"));
				l.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	// 设置发送方式
	public int setSendType(String sendType, EmSalaryInfoModel m) {
		int row = 0;
		String sql = " UPDATE EmSalaryInfo SET esin_cost_type = ? WHERE esin_id=?";
		Object[] objs = { sendType, m.getEsin_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 批量发送方式
	public boolean setPlSendType(String sendType, List<Integer> l) {
		boolean flag = false;
		int row = 0;
		try {
			for (int i = 0; i < l.size(); i++) {
				String sql = " UPDATE EmSalaryInfo SET esin_cost_type = ? WHERE esin_id="
						+ l.get(i);
				Object[] objs = { sendType };
				row = updatePrepareSQL(sql, objs) + row;
			}
			if (row == l.size()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 获取工资单模版
	public List<CoSalaryMbModel> getCsmList() {
		List<CoSalaryMbModel> list = new ArrayList<CoSalaryMbModel>();
		String sql = " SELECT * FROM CoSalaryModel ";
		try {
			list = find(sql, CoSalaryMbModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 设置模版
	public int setModel(int esin_id, String cosmid) {
		int row = 0;
		String sql = " UPDATE EmSalaryInfo SET esin_cosm_model = ? WHERE esin_id = ? ";
		Object[] objs = { cosmid, esin_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	// 批量设置模版
	public boolean PlSetModel(List<Integer> esinids, String cosmid) {
		boolean flag = false;
		int row = 0;
		try {
			for (int i = 0; i < esinids.size(); i++) {
				String sql = " UPDATE EmSalaryInfo SET esin_cosm_model = ? WHERE esin_id="
						+ esinids.get(i);
				Object[] objs = { cosmid };
				row = updatePrepareSQL(sql, objs) + row;
			}
			if (row == esinids.size()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 生成随机密码
	public boolean producePwd(List<EmSalaryInfoModel> esinfoList) {
		boolean flag = false;
		Connection conn = null;
		int count = 0;
		try {
			conn = getConn();
			PreparedStatement pstmt = null;
			conn.setAutoCommit(false);
			for (int i = 0; i < esinfoList.size(); i++) {
				String sql = " UPDATE EmSalaryInfo SET esin_attachpassword="
						+ esinfoList.get(i).getEsin_attachpassword()
						+ " WHERE esin_id=" + esinfoList.get(i).getEsin_id();
				pstmt = conn.prepareStatement(sql);
				count = pstmt.executeUpdate() + count;
			}
			if (count == esinfoList.size()) {
				conn.commit();
				flag = true;
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 工资单数据
	public List<EmSalaryDataModel> getSalaryList(int cid, String ownmonth) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		String sql = " SELECT a.*,esin_id,esin_cost_type,esin_attachpassword,esin_cosm_model,coss_id,coss_sendstate" +
				",coss_carbonCopy,coss_ccaddress,coss_secretsend,coss_secretsendaddress,coss_payrollpapertype,coss_mfdate,coss_mfname," +
				"coss_ifmfdd,coss_emailsenddate,coss_emailtype,coss_emailas,coss_emailcs,coss_emailouto,case when LEN(d.emba_gz_email)>0 then d.emba_gz_email else d.emba_email end as gz_email,f.log_email,g.log_email as client_email " +
				"FROM EmSalaryData a left join EmSalaryInfo b on a.gid = b.gid and a.cid=b.cid " +
				"left join CoSalarySet c on a.cid=c.cid " +
				" left join EmBase d on a.gid=d.gid	" +
				"left join cobase e on a.cid=e.CID " +
				"left join login f on e.coba_gzaddname=f.log_name  "+
				"left join login g on e.coba_client=g.log_name  "+
				" where a.cid = ? and ownmonth = ? order by esda_ba_name  ";
		try {
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cid);
			pstmt.setString(2, ownmonth);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmSalaryDataModel m = new EmSalaryDataModel();
				m.setEsda_id(rs.getInt("esda_id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_if_bms(rs.getInt("esda_if_bms"));
				m.setEsda_addname(rs.getString("esda_addname"));
				m.setEsda_addtime(rs.getString("esda_addtime"));
				m.setEsda_remark(rs.getString("esda_remark"));
				m.setEsda_fd_remark(rs.getString("esda_fd_remark"));
				m.setCfin_id(rs.getInt("cfin_id"));
				m.setCsii_itemid(rs.getString("csii_itemid"));
				m.setEsda_o_bank(rs.getString("esda_o_bank"));
				m.setEsda_bank(rs.getString("esda_bank"));
				m.setEsda_ba_name(rs.getString("esda_ba_name"));
				m.setEsda_bank_account(rs.getString("esda_bank_account"));
				m.setEsda_nationality(rs.getString("esda_nationality"));
				m.setEsda_usage_type(rs.getInt("esda_usage_type"));
				m.setEsda_email(rs.getString("gz_email"));
				//m.setEsda_email("lsb@szciic.com");
				m.setEsda_bcc_email(rs.getString("esda_bcc_email"));
				m.setEsda_email_sender(rs.getString("esda_email_sender"));
				m.setEsda_email_state(rs.getInt("esda_email_state"));
				m.setEsda_email_date(rs.getString("esda_email_date"));
				m.setEsda_oof_state(rs.getInt("esda_oof_state"));
				m.setEsda_confirm_state(rs.getInt("esda_confirm_state"));
				m.setEsda_ifhold(rs.getInt("esda_ifhold"));
				m.setEsda_payment_state(rs.getInt("esda_payment_state"));
				m.setEsda_payment_date(rs.getString("esda_payment_date"));
				m.setEsda_payment_batch(rs.getInt("esda_payment_batch"));
				m.setEsda_data_type(rs.getInt("esda_data_type"));
				m.setEsda_rp_reason(rs.getInt("esda_rp_reason"));
				m.setEsda_history_state(rs.getInt("esda_history_state"));
				m.setEsda_history_date(rs.getString("esda_history_date"));
				m.setEsda_ttv_state(rs.getInt("esda_ttv_state"));
				m.setEsda_ttv_date(rs.getString("esda_ttv_date"));
				m.setEsda_ttv_people(rs.getString("esda_ttv_people"));
				m.setEsda_base_radix(rs.getBigDecimal("esda_base_radix"));
				m.setEsda_a_base_radix(rs.getBigDecimal("esda_a_base_radix"));
				m.setEsda_a_workdays(rs.getBigDecimal("esda_a_workdays"));
				m.setEsda_a_base_salary(rs.getBigDecimal("esda_a_base_salary"));
				m.setEsda_days(rs.getBigDecimal("esda_days"));
				m.setEsda_cqdays(rs.getBigDecimal("esda_cqdays"));
				m.setEsda_b_cqdays(rs.getBigDecimal("esda_b_cqdays"));
				m.setEsda_qqdays(rs.getBigDecimal("esda_qqdays"));
				m.setEsda_b_qqdays(rs.getBigDecimal("esda_b_qqdays"));
				m.setEsda_aded(rs.getBigDecimal("esda_aded"));
				m.setEsda_a_aded(rs.getBigDecimal("esda_a_aded"));
				m.setEsda_base_salary(rs.getBigDecimal("esda_base_salary"));
				m.setEsda_siop(rs.getBigDecimal("esda_siop"));
				m.setEsda_hafop(rs.getBigDecimal("esda_hafop"));
				m.setEsda_haf(rs.getBigDecimal("esda_haf"));
				m.setEsda_house_radix(rs.getBigDecimal("esda_house_radix"));
				m.setEsda_house_percent(rs.getBigDecimal("esda_house_percent"));
				m.setEsda_house_ntl(rs.getBigDecimal("esda_house_ntl"));
				m.setEsda_total_pretax(rs.getBigDecimal("esda_total_pretax"));
				m.setEsda_tax_base(rs.getBigDecimal("esda_tax_base"));
				m.setEsda_tax(rs.getBigDecimal("esda_tax"));
				m.setEsda_lw_tax_base(rs.getBigDecimal("esda_lw_tax_base"));
				m.setEsda_lw_tax(rs.getBigDecimal("esda_lw_tax"));
				m.setEsda_dc(rs.getBigDecimal("esda_dc"));
				m.setEsda_dc_tax(rs.getBigDecimal("esda_dc_tax"));
				m.setEsda_db(rs.getBigDecimal("esda_db"));
				m.setEsda_db_tax_base(rs.getBigDecimal("esda_db_tax_base"));
				m.setEsda_db_tax(rs.getBigDecimal("esda_db_tax"));
				m.setEsda_stock(rs.getBigDecimal("esda_stock"));
				m.setEsda_stock_tax(rs.getBigDecimal("esda_stock_tax"));
				m.setEsda_other(rs.getBigDecimal("esda_other"));
				m.setEsda_other_tax(rs.getBigDecimal("esda_other_tax"));
				m.setEsda_write_off(rs.getBigDecimal("esda_write_off"));
				m.setEsda_house_bf(rs.getBigDecimal("esda_house_bf"));
				m.setEsda_pay(rs.getBigDecimal("esda_pay"));
				m.setEsda_col_1(rs.getBigDecimal("esda_col_1"));
				m.setEsda_col_2(rs.getBigDecimal("esda_col_2"));
				m.setEsda_col_3(rs.getBigDecimal("esda_col_3"));
				m.setEsda_col_4(rs.getBigDecimal("esda_col_4"));
				m.setEsda_col_5(rs.getBigDecimal("esda_col_5"));
				m.setEsda_col_6(rs.getBigDecimal("esda_col_6"));
				m.setEsda_col_7(rs.getBigDecimal("esda_col_7"));
				m.setEsda_col_8(rs.getBigDecimal("esda_col_8"));
				m.setEsda_col_9(rs.getBigDecimal("esda_col_9"));
				m.setEsda_col_10(rs.getBigDecimal("esda_col_10"));
				m.setEsda_col_11(rs.getBigDecimal("esda_col_11"));
				m.setEsda_col_12(rs.getBigDecimal("esda_col_12"));
				m.setEsda_col_13(rs.getBigDecimal("esda_col_13"));
				m.setEsda_col_14(rs.getBigDecimal("esda_col_14"));
				m.setEsda_col_15(rs.getBigDecimal("esda_col_15"));
				m.setEsda_col_16(rs.getBigDecimal("esda_col_16"));
				m.setEsda_col_17(rs.getBigDecimal("esda_col_17"));
				m.setEsda_col_18(rs.getBigDecimal("esda_col_18"));
				m.setEsda_col_19(rs.getBigDecimal("esda_col_19"));
				m.setEsda_col_20(rs.getBigDecimal("esda_col_20"));
				m.setEsda_col_21(rs.getBigDecimal("esda_col_21"));
				m.setEsda_col_22(rs.getBigDecimal("esda_col_22"));
				m.setEsda_col_23(rs.getBigDecimal("esda_col_23"));
				m.setEsda_col_24(rs.getBigDecimal("esda_col_24"));
				m.setEsda_col_25(rs.getBigDecimal("esda_col_25"));
				m.setEsda_col_26(rs.getBigDecimal("esda_col_26"));
				m.setEsda_col_27(rs.getBigDecimal("esda_col_27"));
				m.setEsda_col_28(rs.getBigDecimal("esda_col_28"));
				m.setEsda_col_29(rs.getBigDecimal("esda_col_29"));
				m.setEsda_col_30(rs.getBigDecimal("esda_col_30"));
				m.setEsda_col_31(rs.getBigDecimal("esda_col_31"));
				m.setEsda_col_32(rs.getBigDecimal("esda_col_32"));
				m.setEsda_col_33(rs.getBigDecimal("esda_col_33"));
				m.setEsda_col_34(rs.getBigDecimal("esda_col_34"));
				m.setEsda_col_35(rs.getBigDecimal("esda_col_35"));
				m.setEsda_col_36(rs.getBigDecimal("esda_col_36"));
				m.setEsda_col_37(rs.getBigDecimal("esda_col_37"));
				m.setEsda_col_38(rs.getBigDecimal("esda_col_38"));
				m.setEsda_col_39(rs.getBigDecimal("esda_col_39"));
				m.setEsda_col_40(rs.getBigDecimal("esda_col_40"));
				m.setEsda_col_41(rs.getBigDecimal("esda_col_41"));
				m.setEsda_col_42(rs.getBigDecimal("esda_col_42"));
				m.setEsda_col_43(rs.getBigDecimal("esda_col_43"));
				m.setEsda_col_44(rs.getBigDecimal("esda_col_44"));
				m.setEsda_col_45(rs.getBigDecimal("esda_col_45"));
				m.setEsda_col_46(rs.getBigDecimal("esda_col_46"));
				m.setEsda_col_47(rs.getBigDecimal("esda_col_47"));
				m.setEsda_col_48(rs.getBigDecimal("esda_col_48"));
				m.setEsda_col_49(rs.getBigDecimal("esda_col_49"));
				m.setEsda_col_50(rs.getBigDecimal("esda_col_50"));
				m.setEsda_col_51(rs.getBigDecimal("esda_col_51"));
				m.setEsda_col_52(rs.getBigDecimal("esda_col_52"));
				m.setEsda_col_53(rs.getBigDecimal("esda_col_53"));
				m.setEsda_col_54(rs.getBigDecimal("esda_col_54"));
				m.setEsda_col_55(rs.getBigDecimal("esda_col_55"));
				m.setEsda_col_56(rs.getBigDecimal("esda_col_56"));
				m.setEsda_col_57(rs.getBigDecimal("esda_col_57"));
				m.setEsda_col_58(rs.getBigDecimal("esda_col_58"));
				m.setEsda_col_59(rs.getBigDecimal("esda_col_59"));
				m.setEsda_col_60(rs.getBigDecimal("esda_col_60"));
				m.setEsda_col_61(rs.getBigDecimal("esda_col_61"));
				m.setEsda_col_62(rs.getBigDecimal("esda_col_62"));
				m.setEsda_col_63(rs.getBigDecimal("esda_col_63"));
				m.setEsda_col_64(rs.getBigDecimal("esda_col_64"));
				m.setEsda_col_65(rs.getBigDecimal("esda_col_65"));
				m.setEsda_col_66(rs.getBigDecimal("esda_col_66"));
				m.setEsda_col_67(rs.getBigDecimal("esda_col_67"));
				m.setEsda_col_68(rs.getBigDecimal("esda_col_68"));
				m.setEsda_col_69(rs.getBigDecimal("esda_col_69"));
				m.setEsda_col_70(rs.getBigDecimal("esda_col_70"));
				m.setEsda_col_71(rs.getBigDecimal("esda_col_71"));
				m.setEsda_col_72(rs.getBigDecimal("esda_col_72"));
				m.setEsda_col_73(rs.getBigDecimal("esda_col_73"));
				m.setEsda_col_74(rs.getBigDecimal("esda_col_74"));
				m.setEsda_col_75(rs.getBigDecimal("esda_col_75"));
				m.setEsda_col_76(rs.getBigDecimal("esda_col_76"));
				m.setEsda_col_77(rs.getBigDecimal("esda_col_77"));
				m.setEsda_col_78(rs.getBigDecimal("esda_col_78"));
				m.setEsda_col_79(rs.getBigDecimal("esda_col_79"));
				m.setEsda_col_80(rs.getBigDecimal("esda_col_80"));
				m.setEsin_id(rs.getInt("esin_id"));
				m.setEsin_cost_type(rs.getString("esin_cost_type"));
				m.setEsin_attachpassword(rs.getString("esin_attachpassword"));
				m.setEsin_cosm_model(rs.getString("esin_cosm_model"));
				m.setCoss_sendstate(rs.getInt("coss_sendstate"));
				m.setCoss_carbonCopy(rs.getInt("coss_carbonCopy"));
				m.setCoss_ccaddress(rs.getString("coss_ccaddress"));
				m.setCoss_secretsend(rs.getInt("coss_secretsend"));
				m.setCoss_secretsendaddress(rs
						.getString("coss_secretsendaddress"));
				m.setGzaddname_email(rs.getString("log_email"));
				m.setClient_email(rs.getString("client_email"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 项目名称数据
	public List<EmSalaryBaseAddItemModel> getItemList(int cid, String ownmonth) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		// 状态对吗
		String sql = " select csii_col,csii_item_name,csii_csd_state,chk_cfd,csii_item_anothername,csii_item_englishname from CSII_CFDa_V WHERE  cid=? AND ownmonth=? and csii_csd_state=1  ORDER BY csii_csd_state desc,csii_sequence ";
		try {
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cid);
			pstmt.setString(2, ownmonth);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EmSalaryBaseAddItemModel m = new EmSalaryBaseAddItemModel();
				m.setCsii_col(rs.getString("csii_col"));
				m.setCsii_item_name(rs.getString("csii_item_name"));
				m.setCsii_csd_state(rs.getInt("csii_csd_state"));
				//m.setCfda_id(rs.getInt("chk_cfd"));
				m.setCsii_item_anothername(rs
						.getString("csii_item_anothername"));
				m.setCsii_item_englishname(rs
						.getString("csii_item_englishname"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 把发送信息保存到数据库
	public void saveSendEmail(PubEmailModel pe) {
		String sql = " INSERT INTO PubEmail(puem_title,puem_Content,puem_email,puem_replyto,puem_emailcc,puem_state,puem_truesendtime,puem_addname,puem_addtime,puem_ifhtml,puem_iffile,puem_fileurl) VALUES(?,?,?,?,?,?,?,?,getDate(),?,?,?)";
		Object[] objs = { pe.getPuem_title(), pe.getPuem_content(),
				pe.getPuem_email(), pe.getPuem_replyto(), pe.getPuem_emailcc(),
				pe.getPuem_state(), pe.getPuem_truesendtime(),
				pe.getPuem_addname(), pe.getPuem_ifHTML(), pe.getPuem_iffile(),
				pe.getFileurl() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 改变状态为已发送
	public void updateState(EmSalaryDataModel m) {
		String sql = " UPDATE EmSalaryData SET esda_email_state = ?,esda_email_date=getDate() WHERE esda_id = ? ";
		Object[] objs = { m.getEsda_email_state(), m.getEsda_id() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
