package dal.CoSocialInsurance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import Conn.dbconn;
import Model.CoShebaoPayAmountModel;
import Model.CoShebaoQueryBillsModel;

public class CoShebao_QueryBillsDal extends dbconn {

	/**
	 * 添加社保到账情况
	 * 
	 * @return
	 */
	public int addQueryBills(CoShebaoPayAmountModel m) {
		int row = 0;
		String username = Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString();
		String sql = " INSERT INTO CoShebaoQueryBillsCondition(csqb_cspa_id,csqb_isaccount,csqb_querydate,csqb_addname,csqb_addtime) VALUES (?,?,?,?,getDate()) ";
		Object[] objs = { m.getCspa_id(), m.getIsaccount(),
				new java.sql.Date(m.getQueryDate().getTime()), username };
		try {
			row = updatePrepareSQL(sql, objs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 更新社保缴存表
	 */
	public void updatePayAmount(CoShebaoPayAmountModel m) {
		String sql = " UPDATE CoShebaoPayAmount SET cspa_bodycount = ?,cspa_acount=? WHERE cspa_id = ? ";
		Object[] objs = { m.getBodycount(), m.getAcount(), m.getCspa_id() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 添加社保缴存
	 */
	public int addPayAmount(CoShebaoPayAmountModel m) {
		int row = 0;
		String username = Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString();
		String sql = " INSERT INTO CoShebaoPayAmount(cspa_cosb_id,cspa_bodycount,cspa_acount,cspa_ownmonth,cspa_addname,cspa_addtime) VALUES(?,?,?,?,?,getDate()) ";
		Object[] objs = { m.getCspa_cosb_id(), m.getBodycount(), m.getAcount(),
				m.getOwnmonth(), username };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 查询社保缴存是否存在
	 * 
	 * @return
	 */
	public CoShebaoPayAmountModel getPayAmount(CoShebaoPayAmountModel m) {
		CoShebaoPayAmountModel mm = new CoShebaoPayAmountModel();
		int row = 0;
		String sql = " select COUNT(*)row,cspa_id from CoShebaoPayAmount where cspa_cosb_id = ? and cspa_ownmonth = ? group by cspa_id ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, m.getCspa_cosb_id());
			pstmt.setString(2, m.getOwnmonth());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				mm.setCount(rs.getInt("row"));
				mm.setCspa_id(rs.getInt("cspa_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mm;
	}

	/**
	 * 社保到账详情查询
	 * 
	 * @param cspaid
	 * @return
	 */
	public List<CoShebaoQueryBillsModel> getQueryBillsList(int cspaid) {
		List<CoShebaoQueryBillsModel> l = new ArrayList<CoShebaoQueryBillsModel>();
		String sql = " SELECT csqb_id,csqb_cspa_id,csqb_isaccount,CONVERT(varchar(50),csqb_addtime,120)addtime,CONVERT(varchar(50),csqb_querydate,120)qdate,csqb_addname FROM CoShebaoQueryBillsCondition WHERE csqb_cspa_id = ?";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cspaid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoShebaoQueryBillsModel m = new CoShebaoQueryBillsModel();
				m.setQueryDate(rs.getString("qdate"));
				m.setCsqb_addtime(rs.getString("addtime"));
				m.setCsqb_id(rs.getInt("csqb_id"));
				m.setIsAccount(rs.getString("csqb_isaccount"));
				m.setCsqb_addname(rs.getString("csqb_addname"));
				l.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 查询社保到账记录集合
	 * 
	 * @return
	 */
	public List<CoShebaoPayAmountModel> getPayAmountList() {
		List<CoShebaoPayAmountModel> l = new ArrayList<CoShebaoPayAmountModel>();
		String sql = "select cb.cid,a.cosb_id,cspa_id,cosb_coridcard,cb.coba_company,coba_client,csqb_isaccount,cspa_bodycount,cspa_acount,ownmonth from CoShebao a "
				+ " left join (select * from CoShebaoPayAmount c left join (select * from CoShebaoQueryBillsCondition where csqb_addtime in (select max(csqb_addtime) from CoShebaoQueryBillsCondition group by csqb_cspa_id) )d on c.cspa_id=d.csqb_cspa_id ) b "
				+ " on a.cosb_id = b.cspa_cosb_id "
				+ " left join CoBase cb "
				+ " on cb.cid = a.cid " + " order by csqb_isaccount ";

		try {
			ResultSet rs = GRS(sql);
			System.out.println(sql);
			while (rs.next()) {
				CoShebaoPayAmountModel m = new CoShebaoPayAmountModel();
				m.setCid(rs.getInt("cid"));
				m.setCspa_cosb_id(rs.getInt("cosb_id"));
				m.setCspa_id(rs.getInt("cspa_id"));
				m.setCoirdcard(rs.getString("cosb_coridcard"));
				m.setCompanyname(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setIsaccount(rs.getString("csqb_isaccount"));
				m.setBodycount(rs.getInt("cspa_bodycount"));
				m.setAcount(rs.getBigDecimal("cspa_acount"));
				m.setOwnmonth(rs.getString("ownmonth"));
				l.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

}
