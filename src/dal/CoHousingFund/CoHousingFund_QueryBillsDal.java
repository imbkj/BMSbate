package dal.CoHousingFund;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import Conn.dbconn;
import Model.CoHousingFundPayAmountModel;
import Model.CoHousingFundPaymentModel;
import Model.CoShebaoPayAmountModel;

public class CoHousingFund_QueryBillsDal extends dbconn {

	/**
	 * 根据cfpaid查询补缴记录详情
	 * 
	 * @param cfpaid
	 * @return
	 */
	public List<CoHousingFundPaymentModel> getPaymentBillsList(int cfpaid) {
		List<CoHousingFundPaymentModel> l = new ArrayList<CoHousingFundPaymentModel>();
		String sql = " SELECT cofp_isaccount,CONVERT(varchar(50),cofp_querydate,120)cofp_querydate,cofp_addname,CONVERT(varchar(50),cofp_addtime,120)cofp_addtime FROM CoHousingFundPayment WHERE cofp_cfpa_id= ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cfpaid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundPaymentModel m = new CoHousingFundPaymentModel();
				m.setIsAccount(rs.getString("cofp_isaccount"));
				m.setQueryDateString(rs.getString("cofp_querydate"));
				m.setAddname(rs.getString("cofp_addname"));
				m.setAddtimeString(rs.getString("cofp_addtime"));
				l.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return l;
	}

	/**
	 * 添加公积金补缴到账状况
	 * 
	 * @return
	 */
	public int addPayment(CoHousingFundPayAmountModel m) {
		int row = 0;
		String username = Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString();
		String sql = " INSERT INTO CoHousingFundPayment (cofp_cfpa_id,cofp_isaccount,cofp_querydate,cofp_addname,cofp_addtime) VALUES(?,?,?,?,getDate())";
		Object[] objs = { m.getCfpa_id(), m.getCofp_isaccount(),
				new java.sql.Date(m.getCofp_queryDate().getTime()), username };
		try {
			row = updatePrepareSQL(sql, objs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 添加社保到账情况
	 * 
	 * @return
	 */
	public int addQueryBills(CoHousingFundPayAmountModel m) {
		int row = 0;
		String username = Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString();
		String sql = " INSERT INTO CoHousingFundQueryBillsCondition(cqbc_cfpa_id,cqbc_isaccount,cqbc_querydate,cqbc_addname,cqbc_addtime) VALUES (?,?,?,?,getDate()) ";
		Object[] objs = { m.getCfpa_id(), m.getCqbc_isaccount(),
				new java.sql.Date(m.getCqbc_queryDate().getTime()), username };
		try {
			row = updatePrepareSQL(sql, objs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 更新公积金缴存表(从补缴添加)
	 */
	public int addPaymentAmount(CoHousingFundPayAmountModel m) {
		int row = 0;
		String username = Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString();
		String sql = " INSERT INTO CoHousingFundPayAmount(cfpa_chfz_id,cfpa_payment,cfpa_ownmonth,cfpa_addname,cfpa_addtime) VALUES(?,?,?,?,getDate()) ";
		Object[] objs = { m.getChfz_id(), m.getPayment(), m.getOwnmonth(),
				username };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 添加公积金缴存(从缴存添加)
	 */
	public int addPayAmount(CoHousingFundPayAmountModel m) {
		int row = 0;
		String username = Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString();
		String sql = " INSERT INTO CoHousingFundPayAmount(cfpa_chfz_id,cfpa_bodycount,cfpa_amount,cfpa_ownmonth,cfpa_addname,cfpa_addtime) VALUES(?,?,?,?,?,getDate()) ";
		Object[] objs = { m.getChfz_id(), m.getBodyCount(), m.getAmount(),
				m.getOwnmonth(), username };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 更新公积金缴存 (从补缴更新)
	 */
	public void updatePaymentAmount(CoHousingFundPayAmountModel m) {
		String sql = " UPDATE CoHousingFundPayAmount SET cfpa_payment=? WHERE cfpa_id = ? ";
		Object[] objs = { m.getPayment(), m.getCfpa_id() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新公积金缴存表 (从缴存更新)
	 */
	public void updatePayAmount(CoHousingFundPayAmountModel m) {
		String sql = " UPDATE CoHousingFundPayAmount SET cfpa_bodycount = ?,cfpa_amount=? WHERE cfpa_id = ? ";
		Object[] objs = { m.getBodyCount(), m.getAmount(), m.getCfpa_id() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询公积金缴存是否存在
	 * 
	 * @return
	 */
	public CoHousingFundPayAmountModel getPayAmount(
			CoHousingFundPayAmountModel m) {
		CoHousingFundPayAmountModel mm = new CoHousingFundPayAmountModel();
		int row = 0;
		String sql = " select COUNT(*)row,cfpa_id from CoHousingFundPayAmount where cfpa_chfz_id = ? and cfpa_ownmonth = ? group by cfpa_id ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, m.getChfz_id());
			pstmt.setString(2, m.getOwnmonth());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				mm.setCount(rs.getInt("row"));
				mm.setCfpa_id(rs.getInt("cfpa_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mm;
	}

	/**
	 * 根据cfpaid查询到账详情
	 * 
	 * @param cfpaid
	 * @return
	 */
	public List<CoHousingFundPaymentModel> getBillsList(int cfpaid) {
		List<CoHousingFundPaymentModel> l = new ArrayList<CoHousingFundPaymentModel>();
		String sql = " SELECT cqbc_isaccount,CONVERT(varchar(50),cqbc_querydate,120)cqbc_querydate,CONVERT(varchar(50),cqbc_addtime,120)cqbc_addtime,cqbc_addname FROM CoHousingFundQueryBillsCondition WHERE cqbc_cfpa_id = ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cfpaid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundPaymentModel m = new CoHousingFundPaymentModel();
				m.setIsAccount(rs.getString("cqbc_isaccount"));
				m.setQueryDateString(rs.getString("cqbc_querydate"));
				m.setAddtimeString(rs.getString("cqbc_addtime"));
				m.setAddname(rs.getString("cqbc_addname"));
				l.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 查询公积金到账记录
	 * 
	 * @return
	 */
	public List<CoHousingFundPayAmountModel> getQueryBillsList() {
		List<CoHousingFundPayAmountModel> l = new ArrayList<CoHousingFundPayAmountModel>();
		String sql = " select cohf_id,chfz_id,a.ownmonth,cohf_houseid,cohf_company,cohf_client,cohf_cpp,chfz_number,cohf_lastday,cofp_isaccount,CONVERT(varchar(50),cofp_querydate,120)cofp_querydate,Cfpa_payment,Cfpa_bodycount,Cfpa_amount,Cqbc_isaccount,Cfpa_id from CoHousingFund a "
				+ "left join (SELECT * from CoHousingFundZb b "
				+ " left join (select c.Cfpa_id,c.Cfpa_chfz_id,cofp_isaccount,cofp_querydate,Cfpa_payment,cqbc_isaccount,Cfpa_bodycount,Cfpa_amount from CoHousingFundPayAmount c left join (select * from CoHousingFundPayment where cofp_isaccount='已到帐')d on c.Cfpa_id=d.cofp_cfpa_id left join (select * from CoHousingFundQueryBillsCondition where Cqbc_isaccount='已到帐')e on c.Cfpa_id=e.Cqbc_cfpa_id ) f "
				+ " on b.chfz_id=f.Cfpa_chfz_id) g "
				+ " on a.cohf_id = g.chfz_cohf_id "
				+ " order by Cqbc_isaccount,chfz_id desc ";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundPayAmountModel m = new CoHousingFundPayAmountModel();
				m.setCohf_houseid(rs.getString("cohf_houseid"));
				m.setChfz_id(rs.getInt("chfz_id"));
				m.setOwnmonth(rs.getString("ownmonth"));
				m.setCohf_id(rs.getInt("cohf_id"));
				m.setCompanyname(rs.getString("cohf_company"));
				m.setCilent(rs.getString("cohf_client"));
				m.setCohf_cpp(rs.getDouble("cohf_cpp"));
				m.setChfz_number(rs.getString("chfz_number"));
				m.setLastDay(rs.getInt("cohf_lastday"));
				m.setCofp_isaccount(rs.getString("cofp_isaccount"));
				m.setCofp_queryDateString(rs.getString("cofp_querydate"));
				m.setPayment(rs.getBigDecimal("Cfpa_payment"));
				m.setBodyCount(rs.getInt("Cfpa_bodycount"));
				m.setAmount(rs.getBigDecimal("Cfpa_amount"));
				m.setCqbc_isaccount(rs.getString("Cqbc_isaccount"));
				m.setCfpa_id(rs.getInt("Cfpa_id"));
				l.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

}
