package dal.CoCompact;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoCompactCppAduitModel;
import Model.CoCompactModel;
import Model.CoCompactTemAddModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;

public class CoCompact_OperateDal {
	private dbconn conn = new dbconn();

	public List<CoCompactModel> getlist(Integer id) {
		List<CoCompactModel> list = new ListModelList<>();
		String sql = "select coco_id,a.cid cid2,coco_compactid,coco_houseid,coco_cpp,coco_opp,"
				+ "coba_company company,coba_shortname"
				+ " from cocompact a"
				+ " inner join cobase b on a.cid=b.cid" + " where coco_id=?";
		dbconn db = new dbconn();
		try {
			list = db.find(sql, CoCompactModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 公司合同生成模板
	public int addCompactTemp(int cola_id, String coof_id, CoCompactModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoCompactTempAdd_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, cola_id);
			c.setString(2, coof_id);
			c.setString(3, m.getCoco_compacttype());
			c.setString(4, m.getCoco_servicearea());
			c.setString(5, m.getCoco_inuredate());
			c.setString(6, m.getCoco_remark());
			c.setString(7, m.getCoco_addname());
			c.setString(8, m.getCoco_shebao());
			c.setString(9, m.getCoco_house());
			c.setString(10, m.getCoco_cpp());
			c.setString(11, m.getCoco_opp());
			c.setString(12, m.getCoco_gzmonth());
			c.setString(13, m.getCoco_gsmonth());
			c.setInt(14, m.getCoco_sbfee());
			c.setInt(15, m.getCoco_housefee());
			c.setInt(16, m.getCoco_sbperfee());
			c.setInt(17, m.getCoco_gsfee());
			c.setInt(18, m.getCoco_gzpay());
			c.setInt(19, m.getCoco_houseperfee());
			c.setInt(20, m.getCoco_gzperfee());
			c.setInt(21, m.getCoco_ifgzpay());
			c.setString(22, m.getCoco_gs());
			c.setString(23, m.getCoco_compactclass());
			c.registerOutParameter(24, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(24);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 任务单编号更新
	public int updatetaskid(int coco_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update cocompact  set coco_tapr_id=" + tapr_id
				+ " where coco_id=" + coco_id + "";
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

	// 公司合同修改
	public int updateCompactTemp(CoCompactModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoCompactTempUpdate_P_lsb(?,?,?,?,?)");
			c.setInt(1, m.getCoco_id());
			c.setString(2, m.getCoco_servicearea());
			c.setString(3, m.getCoco_inuredate());
			c.setString(4, m.getCoco_remark());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 公司合同签回
	public int signCoCompact(int coco_id, CoCompactModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoCompactSign_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, coco_id);
			c.setString(2, m.getCoco_returndate());
			c.setString(3, m.getCoco_signdate());
			c.setString(4, m.getCoco_signplace());
			c.setString(5, m.getCoco_indate());
			c.setString(6, m.getCoco_delay());
			c.setString(7, m.getCoco_money());
			c.setString(8, m.getCoco_invoice());
			c.setInt(9, m.getCoco_paydate());
			c.setDouble(10, m.getCoco_fw_p());
			c.setDouble(11, m.getCoco_fl_p());
			c.setDouble(12, m.getCoco_dk_p());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(13);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 公司合同归档
	public int returnCoCompact(int coco_id, CoCompactModel m) {
		try {
			System.out.println(m.getCoco_filedate());
			System.out.println(m.getCoco_indate());
			CallableStatement c = conn
					.getcall("CoCompactReturn_P_lsb(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, coco_id);
			c.setString(2, m.getCoco_filedate());
			// c.setString(3, m.getCoco_fileid());
			c.setString(3, m.getCoco_chs_copies());
			c.setString(4, m.getCoco_en_copies());
			c.setString(5, m.getCoco_remark());
			c.setString(6, m.getCoco_delay());
			c.setString(7, m.getCoco_indate());
			c.setInt(8, m.getCoco_paydate());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 公司合同退回
	public int outCoCompact(int coco_id, String remark) {
		try {

			CallableStatement c = conn.getcall("CoCompactOut_P_zzq(?,?,?)");
			c.setInt(1, coco_id);
			c.setString(2, remark);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	public List<CoOfferModel> getCoOfferList(int coco_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferModel> list = new ArrayList<CoOfferModel>();
		String sqlstr = "select *,cpct_shortname coof_compacttype from CoOffer a inner join CopCompact b on a.coof_cpct_id=b.cpct_id where coof_state=3 and coof_coco_id="
				+ coco_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferModel model = new CoOfferModel();
				model.setCoof_id(rs.getInt("coof_id"));
				model.setCoof_name(rs.getString("coof_name"));
				model.setCoof_quotemode(rs.getString("coof_quotemode"));
				model.setCoof_compacttype(rs.getString("coof_compacttype"));
				model.setCoof_addname(rs.getString("coof_addname"));
				model.setCoof_addtime(rs.getDate("coof_addtime"));
				model.setCoof_state(rs.getInt("coof_state"));
				model.setCoof_remark(rs.getString("coof_remark"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferInfoList(int coco_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from view_coofferlist where coco_id="
				+ coco_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();

				model.setCoof_id(rs.getInt("coof_id"));
				model.setCoof_name(rs.getString("coof_name"));
				model.setColi_id(rs.getInt("coli_id"));
				model.setColi_pclass(rs.getString("coli_pclass"));
				model.setColi_name(rs.getString("coli_name"));
				model.setColi_city(rs.getString("coli_city"));
				model.setColi_standard(rs.getString("coli_standard"));
				model.setColi_fee(rs.getBigDecimal("coli_fee"));
				model.setColi_cpfc_name(rs.getString("coli_cpfc_name"));
				model.setColi_amount(rs.getInt("coli_amount"));
				model.setColi_remark(rs.getString("coli_remark"));
				model.setColi_content(rs.getString("coli_content"));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferInfoListBycolaid(int cola_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from view_coofferlist where coof_cola_id="
				+ cola_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();

				model.setCoof_id(rs.getInt("coof_id"));
				model.setCoof_name(rs.getString("coof_name"));
				model.setColi_id(rs.getInt("coli_id"));
				model.setColi_pclass(rs.getString("coli_pclass"));
				model.setColi_name(rs.getString("coli_name"));
				model.setColi_city(rs.getString("coli_city"));
				model.setColi_standard(rs.getString("coli_standard"));
				model.setColi_fee(rs.getBigDecimal("coli_fee"));
				model.setCpfc_name(rs.getString("cpfc_name"));
				model.setColi_amount(rs.getInt("coli_amount"));
				model.setColi_remark(rs.getString("coli_remark"));
				model.setColi_content(rs.getString("coli_content"));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public boolean MakeCoCompact(int coco_state, int coco_id) {
		try {
			String sql = "update CoCompact set coco_state=? where coco_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, coco_state);
			psmt.setInt(2, coco_id);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer updateCoCompact(CoCompactCppAduitModel cm, Integer id) {
		dbconn db = new dbconn();
		Integer i = 0;
		if (id != null && id > 0) {

			String sql = "update CoCompact set coco_houseid=?,coco_cpp=?,coco_opp=?"
					+ " where coco_id=?";
			try {
				i = db.updatePrepareSQL(sql, cm.getCoca_houseid(),
						cm.getCoca_cpp(), cm.getCoca_cpp(),
						cm.getCoca_coco_id());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	public boolean AutCoCompact(int coco_state, int coco_id, String coco_class,
			int aut_st) {
		try {
			if (!coco_class.equals("")) {
				String sql = "update CoCompact set coco_state=?,coco_class=?,coco_autst=? where coco_id=?";
				PreparedStatement psmt = conn.getpre(sql);
				psmt.setInt(1, coco_state);
				psmt.setString(2, coco_class);
				psmt.setInt(3, aut_st);
				psmt.setInt(4, coco_id);
				psmt.executeUpdate();

				System.out.println(coco_state);
				System.out.println(coco_class);
				System.out.println(aut_st);
				System.out.println(coco_id);
				System.out
						.println("update CoCompact set coco_state=?,coco_class=?,coco_autst=? where coco_id=?");
			} else {
				String sql = "update CoCompact set coco_state=?,coco_autst=? where coco_id=?";
				PreparedStatement psmt = conn.getpre(sql);
				psmt.setInt(1, coco_state);
				psmt.setInt(2, aut_st);
				psmt.setInt(3, coco_id);
				psmt.executeUpdate();
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean UpCoCompact(int coco_state, int coco_id, int rwd,
			int coco_autst) {
		try {
			String sql = "update CoCompact set coco_state=?,coco_save=?,coco_autst=? where coco_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, coco_state);
			if (rwd == 1) {
				psmt.setString(2, "导出");
			} else {
				psmt.setString(2, "申请盖章");
			}
			psmt.setInt(3, coco_autst);
			psmt.setInt(4, coco_id);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//合同盖章
	public boolean GzCoCompact(int coco_state, int coco_id, int coco_autst) {
		try {
			String sql = "update CoCompact set coco_state=?,coco_autst=? where coco_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, coco_state);
			psmt.setInt(2, coco_autst);
			psmt.setInt(3, coco_id);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 当个报价单
	public CoCompactTemAddModel getPageVisible(int coof_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		CoCompactTemAddModel model = new CoCompactTemAddModel();
		String sqlstr = "select "
				+ "(select COUNT(*) from CoOfferList where coli_coof_id="
				+ coof_id + " and coli_name='社会保险服务') sb,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id="
				+ coof_id + " and coli_name='住房公积金服务') gjj,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id="
				+ coof_id + " and coli_name='劳动用工手续办理') jy,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id="
				+ coof_id + " and coli_name like '%工资%') gz,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id="
				+ coof_id + " and coli_name like '%个税%') gs";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				model.setSbVis(rs.getInt("sb") == 0 ? false : true);
				model.setGjjVis(rs.getInt("gjj") == 0 ? false : true);
				model.setJyVis(rs.getInt("jy") == 0 ? false : true);
				model.setGzVis(rs.getInt("gz") == 0 ? false : true);
				model.setGsVis(rs.getInt("gs") == 0 ? false : true);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return model;
	}

	// 多个报价单
	public CoCompactTemAddModel getPageVisible(String coof_ids)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		CoCompactTemAddModel model = new CoCompactTemAddModel();
		String sqlstr = "select "
				+ "(select COUNT(*) from CoOfferList where coli_coof_id in("
				+ coof_ids + ") and coli_name='社会保险服务') sb,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id in("
				+ coof_ids + ") and coli_name='住房公积金服务') gjj,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id in("
				+ coof_ids + ") and coli_name='劳动用工手续办理') jy,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id in("
				+ coof_ids + ") and coli_name like '%工资%') gz,"
				+ "(select COUNT(*) from CoOfferList where coli_coof_id in("
				+ coof_ids + ") and coli_name like '%个税%') gs";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				model.setSbVis(rs.getInt("sb") == 0 ? false : true);
				model.setGjjVis(rs.getInt("gjj") == 0 ? false : true);
				model.setJyVis(rs.getInt("jy") == 0 ? false : true);
				model.setGzVis(rs.getInt("gz") == 0 ? false : true);
				model.setGsVis(rs.getInt("gs") == 0 ? false : true);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return model;
	}

	// 根据合同编号获取
	public CoCompactTemAddModel getPageVisible2(String coli_coco_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		CoCompactTemAddModel model = new CoCompactTemAddModel();
		String sqlstr = "select "
				+ "(select COUNT(*) from CoOfferList a left join CoOffer b on a.coli_coof_id=b.coof_id where coof_coco_id="
				+ coli_coco_id
				+ " and coli_name='社会保险服务') sb,"
				+ "(select COUNT(*) from CoOfferList a left join CoOffer b on a.coli_coof_id=b.coof_id where coof_coco_id="
				+ coli_coco_id
				+ " and coli_name='住房公积金服务') gjj,"
				+ "(select COUNT(*) from CoOfferList a left join CoOffer b on a.coli_coof_id=b.coof_id where coof_coco_id="
				+ coli_coco_id
				+ " and coli_name='劳动用工手续办理') jy,"
				+ "(select COUNT(*) from CoOfferList a left join CoOffer b on a.coli_coof_id=b.coof_id where coof_coco_id="
				+ coli_coco_id
				+ " and coli_name like '%工资%') gz,"
				+ "(select COUNT(*) from CoOfferList a left join CoOffer b on a.coli_coof_id=b.coof_id where coof_coco_id="
				+ coli_coco_id
				+ " and coli_name like '%税%') gs,"
				+ "(select COUNT(*) from CoOfferList a left join CoOffer b on a.coli_coof_id=b.coof_id where coof_coco_id="
				+ coli_coco_id + ")coco";
		// System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				if (rs.getInt("coco") != 0) {
					model.setSbVis(rs.getInt("sb") == 0 ? false : true);
					model.setGjjVis(rs.getInt("gjj") == 0 ? false : true);
					model.setJyVis(rs.getInt("jy") == 0 ? false : true);
					model.setGzVis(rs.getInt("gz") == 0 ? false : true);
					model.setGsVis(rs.getInt("gs") == 0 ? false : true);
				} else {
					model.setSbVis(true);
					model.setGjjVis(true);
					model.setJyVis(true);
					model.setGzVis(true);
					model.setGsVis(true);
				}

			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return model;
	}

	// 公司合同生成模板
	public int addCompactInfo(CoCompactModel m) {
		try {

			CallableStatement c = conn
					.getcall("CoCompactAdd_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid2());
			c.setString(2, m.getCoco_returndate());
			c.setString(3, m.getCoco_compacttype());
			c.setString(4, m.getCoco_servicearea());
			c.setString(5, m.getCoco_inuredate());
			c.setString(6, m.getCoco_remark());
			c.setString(7, m.getCoco_addname());
			c.setString(8, m.getCoco_shebao());
			c.setString(9, m.getCoco_house());
			c.setString(10, m.getCoco_cpp());
			c.setString(11, m.getCoco_opp());
			c.setString(12, m.getCoco_gzmonth());
			c.setString(13, m.getCoco_gsmonth());
			c.setInt(14, m.getCoco_sbfee());
			c.setInt(15, m.getCoco_housefee());
			c.setInt(16, m.getCoco_sbperfee());
			c.setInt(17, m.getCoco_gsfee());
			c.setInt(18, m.getCoco_gzpay());
			c.setString(19, m.getCoco_signdate());
			c.setString(20, m.getCoco_indate());
			c.setString(21, m.getCoco_filedate());

			c.setString(22, m.getCoco_signplace());
			c.setString(23, m.getCoco_delay());
			c.setString(24, m.getCoco_money());
			c.setString(25, m.getCoco_invoice());
			c.setString(26, m.getCoco_fileid());
			c.setString(27, m.getCoco_chs_copies());
			c.setString(28, m.getCoco_en_copies());
			c.setString(29, m.getCoco_compactid());

			c.setInt(30, m.getCoco_houseperfee());
			c.setInt(31, m.getCoco_gzperfee());
			c.setInt(32, m.getCoco_ifgzpay());
			c.setString(33, m.getCoco_gs());
			c.setInt(34, m.getCoco_paydate());
			c.registerOutParameter(35, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(35);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 公司合同生成模板(财务外地委托合同)
	public int addCSCompactInfo(CoCompactModel m) {
		try {

			CallableStatement c = conn
					.getcall("CoCompact_CSAdd_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid2());
			c.setString(2, m.getCoco_returndate());
			c.setString(3, m.getCoco_compacttype());
			c.setString(4, m.getCoco_servicearea());
			c.setString(5, m.getCoco_inuredate());
			c.setString(6, m.getCoco_remark());
			c.setString(7, m.getCoco_addname());
			c.setString(8, m.getCoco_shebao());
			c.setString(9, m.getCoco_house());
			c.setString(10, m.getCoco_cpp());
			c.setString(11, m.getCoco_opp());
			c.setString(12, m.getCoco_gzmonth());
			c.setString(13, m.getCoco_gsmonth());
			c.setInt(14, m.getCoco_sbfee());
			c.setInt(15, m.getCoco_housefee());
			c.setInt(16, m.getCoco_sbperfee());
			c.setInt(17, m.getCoco_gsfee());
			c.setInt(18, m.getCoco_gzpay());
			c.setString(19, m.getCoco_signdate());
			c.setString(20, m.getCoco_indate());
			c.setString(21, m.getCoco_filedate());

			c.setString(22, m.getCoco_signplace());
			c.setString(23, m.getCoco_delay());
			c.setString(24, m.getCoco_money());
			c.setString(25, m.getCoco_invoice());
			c.setString(26, m.getCoco_fileid());
			c.setString(27, m.getCoco_chs_copies());
			c.setString(28, m.getCoco_en_copies());
			c.setString(29, m.getCoco_compactid());

			c.setInt(30, m.getCoco_houseperfee());
			c.setInt(31, m.getCoco_gzperfee());
			c.setInt(32, m.getCoco_ifgzpay());
			c.setString(33, m.getCoco_gs());
			c.setInt(34, m.getCoco_paydate());
			c.setString(35, m.getCity());
			c.setString(36, m.getAgency());
			
			c.registerOutParameter(37, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(37);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 更新每月付款日和增值税比例
	public int upCocoPaydate(CoCompactModel m) {
		try {
			String sql = "update CoCompact set coco_paydate=?,coco_fl_p=?,coco_fw_p=?,coco_dk_p=? where coco_id=?";
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, m.getCoco_paydate());
			psmt.setDouble(2, m.getCoco_fl_p());
			psmt.setDouble(3, m.getCoco_fw_p());
			psmt.setDouble(4, m.getCoco_dk_p());
			psmt.setInt(5, m.getCoco_id());
			psmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			return 0;
		}
	}
}
