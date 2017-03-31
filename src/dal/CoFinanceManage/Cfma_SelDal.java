/**
 * 
 */
/**
 * @author Lee
 *
 */
package dal.CoFinanceManage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyAccountModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.CoFinanceMonthlyBillTempletConModel;
import Model.CoFinanceMonthlyBillTempletModel;
import Model.CoFinanceSortAccountssModel;
import Model.CoFinanceTotalAccountModel;
import Model.CoInvoiceModel;
import Model.CollectAmountModel;
import Util.UserInfo;

public class Cfma_SelDal {
	private dbconn conn = new dbconn();

	/**
	 * 客服名称
	 * 
	 * @return
	 */
	public List<String> getClientnameList() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct cola_addname from CoLatencyClient where cola_state=1 ";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("cola_addname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 客服名称
	 * 
	 * @return
	 */
	public List<String> getClientnameListkf() {
		List<String> list = new ArrayList<String>();
		String sql = " select  DISTINCT b.log_name FROM CoBase a ,Login b WHERE a.coba_client=b.log_name AND b.dep_id=2 AND b.log_inure=1 ";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 薪酬负责人名称
	 * 
	 * @return
	 */
	public List<String> getClientnameListxc() {
		List<String> list = new ArrayList<String>();
		String sql = " select  DISTINCT b.log_name FROM CoBase a ,Login b WHERE a.coba_gzaddname=b.log_name AND b.dep_id=13 AND b.log_inure=1";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoFinanceCollectModel> getTodayCollect(String addtime) {
		System.out.println(addtime);
		List<CoFinanceCollectModel> l = new ArrayList<CoFinanceCollectModel>();
		String sql = " select cid,sum(cfco_TotalPaidIn) from CoFinanceCollect  where convert(varchar(200),cfco_addtime,120) like  '"
				+ addtime + "%' and cfco_cfmb_number!='0' group by cid ";
		System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setCid(rs.getInt("cid"));
				l.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 根据cid查询账单
	 * 
	 * @param cid
	 * @return
	 */
	public List<CoFinanceMonthlyBillModel> getBillList(int cid) {
		List<CoFinanceMonthlyBillModel> l = new ArrayList<CoFinanceMonthlyBillModel>();
		String sql = " SELECT cfmb_FinanceConfirm,cfmb_PersonnelConfirm,cfmb_number,cid,cfmb_PersonnelReceivable,cfmb_FinanceReceivable,cfmb_CarryForwardEx,cfmb_LoanBalance,cfmb_TotalPaidIn FROM CoFinanceMonthlyBill  h "
				+ " right join (SELECT * FROM CoFinanceTotalAccount WHERE cfta_state = 1 and cid = ? )  g "
				+ " ON h.cfmb_cfta_id = g.cfta_id WHERE (cfmb_FinanceConfirm = 1  or cfmb_PersonnelConfirm = 1)";
		PreparedStatement pstmt = conn.getpre(sql);
		try {
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoFinanceMonthlyBillModel m = new CoFinanceMonthlyBillModel();
				m.setCfmb_PersonnelReceivable(rs
						.getBigDecimal("cfmb_PersonnelReceivable"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("cfmb_FinanceReceivable"));
				m.setCfmb_CarryForwardEx(rs
						.getBigDecimal("cfmb_CarryForwardEx"));
				m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfmb_FinanceConfirm(rs.getInt("cfmb_FinanceConfirm"));
				m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
				m.setCfmb_number(rs.getString("cfmb_number"));
				l.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 查询收款类别名
	 * 
	 * @return
	 */
	public List<String> getCoPAccountList() {
		List<String> l = new ArrayList<String>();
		l.add(null);
		String sql = " SELECT * FROM CoPAccount ";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				l.add(rs.getString("cpac_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 查询账单
	 * 
	 * @param l
	 * @return
	 */
	public List<CoFinanceCollectModel> getCfcListByCid(
			List<CoFinanceCollectModel> l) {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		StringBuffer sql = new StringBuffer(
				" SELECT cid,cfmb_number,cfmb_PersonnelReceivable,cfmb_FinanceReceivable,cfmb_CarryForwardEx,cfmb_LoanBalance,cfmb_TotalPaidIn FROM CoFinanceMonthlyBill  h "
						+ " right join (SELECT * FROM CoFinanceTotalAccount )  g "
						+ " ON h.cfmb_cfta_id = g.cfta_id WHERE cid IN ( ");
		for (int i = 0; i < l.size(); i++) {
			sql.append(l.get(i).getCid());
			if (i + 1 < l.size()) {
				sql.append(",");
			}
		}
		sql.append(" ) and cfmb_number != '0' and cfta_state = 1 group by cfmb_number,cid,cfmb_PersonnelReceivable,cfmb_FinanceReceivable,cfmb_CarryForwardEx,cfmb_LoanBalance,cfmb_TotalPaidIn ");
		System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setCfco_cfmb_number(rs.getString("cfmb_number"));
				m.setCid(rs.getInt("cid"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("cfmb_FinanceReceivable"));
				m.setCfmb_PersonnelReceivable(rs
						.getBigDecimal("cfmb_PersonnelReceivable"));
				m.setCfmb_CarryForwardEx(rs
						.getBigDecimal("cfmb_CarryForwardEx"));
				m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<CoInvoiceModel> searchCoInvoice(Date startDate, Date endDate) {
		List<CoInvoiceModel> l = new ArrayList<CoInvoiceModel>();
		StringBuffer sql = new StringBuffer(
				" select * from CoInvoice  a "
						+ " left join (select coii_owmonth,coii_coin_id from CoInvoiceInfo where coii_state = 1 group by coii_coin_id,coii_owmonth ) b "
						+ "	on a.coin_id=b.coii_coin_id where coin_idate between ? and ?");
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.getpre(sql.toString());
			pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
			pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoInvoiceModel m = new CoInvoiceModel();
				m.setCoin_id(rs.getInt("coin_id"));
				m.setOwnmonth(rs.getInt("coii_owmonth"));
				m.setCoin_addname(rs.getString("coin_addname"));
				m.setCoin_idate(rs.getString("coin_idate"));
				m.setCoin_invoiceid(rs.getString("coin_invoiceid"));
				m.setCoin_title(rs.getString("coin_title"));
				m.setCoin_codeid(rs.getString("coin_codeid"));
				m.setCoin_total(rs.getBigDecimal("coin_total"));
				m.setReceivename(rs.getString("coin_receivename"));
				l.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return l;
	}

	/**
	 * 根据账单号查询账单
	 * 
	 * @param cid
	 * @return
	 */
	public List<CoFinanceMonthlyBillSortAccountModel> getCfmbList(
			String cfmb_number) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		String sql = " SELECT cfsa_cpac_name,cfsa_Receivable,cfsa_PaidIn,cfsa_addtime FROM CoFinanceMonthlyBillSortAccount WHERE cfsa_cfmb_number = ? and cfsa_state = 1 ";
		PreparedStatement pstmt = conn.getpre(sql);
		try {
			pstmt.setString(1, cfmb_number);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoFinanceMonthlyBillSortAccountModel m = new CoFinanceMonthlyBillSortAccountModel();
				m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
				m.setCfsa_Receivable(rs.getBigDecimal("cfsa_Receivable"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfsa_PaidIn"));
				m.setCfsa_addtime(rs.getString("cfsa_addtime"));
				m.sumImbalance();
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据cid查询收款
	 * 
	 * @param cid
	 * @return
	 */
	public List<CoFinanceMonthlyBillModel> getCfcList(int cid, String ownmonth,
			Date sd, Date ed, String remark, String count) {
		List<CoFinanceMonthlyBillModel> list = new ArrayList<CoFinanceMonthlyBillModel>();

		java.sql.Date ssd = null;
		java.sql.Date eed = null;

		if (sd != null) {
			ssd = new java.sql.Date(sd.getTime());
		}
		if (ed != null) {
			eed = new java.sql.Date(ed.getTime());
		}
		CallableStatement csmt = conn
				.getcall("CoFinanceCollectQuery2_gxj(?,?,?,?,?,?)");

		try {

			csmt.setInt(1, cid);
			csmt.setString(2, ownmonth);
			csmt.setDate(3, ssd);
			csmt.setDate(4, eed);
			csmt.setString(5, count);
			csmt.setString(6, remark);

			// System.out.println("i want"+sql);
			// ResultSet rs = conn.GRS(sql);
			ResultSet rs = csmt.executeQuery();
			// pstmt.setInt(1, cid);
			// pstmt.setInt(2, cid);
			// ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoFinanceMonthlyBillModel m = new CoFinanceMonthlyBillModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfco_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfco_remark(rs.getString("cfmb_remark"));
				m.setCfmb_number(rs.getString("cfmb_number"));
				m.setCfmb_name(rs.getString("cfmb_name"));
				m.setCfmb_addtime(rs.getString("addtime"));
				m.setCfco_addname(rs.getString("cfdm_addname"));
				m.setCfmb_PersonnelReceivable(rs
						.getBigDecimal("cfmb_PersonnelReceivable"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("cfmb_FinanceReceivable"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
				m.setCfmb_CarryForwardEx(rs
						.getBigDecimal("cfmb_CarryForwardEx"));
				m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
				m.setCfmb_FinanceConfirm(rs.getInt("cfmb_FinanceConfirm"));
				m.sumTotalReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据条件查询实收款总额
	 * 
	 * @param cid
	 * @return
	 */
	public List<CoFinanceSortAccountssModel> getCoFinanceSortAccountssModellist(
			String strwhere) {
		List<CoFinanceSortAccountssModel> list = new ArrayList<CoFinanceSortAccountssModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT isnull(cfco_TotalPaidIn,0) cfco_TotalPaidIn , isnull(cfco_TotalPaidIn,0)-isnull(cfss_Receivable,0) cfta_Balance,a.cid cid FROM ( ");
		sql.append("SELECT SUM(cfco_TotalPaidIn) cfco_TotalPaidIn,cid  FROM CoFinanceCollect where cfco_state=1 GROUP ");
		sql.append("BY cid ) a left join ( ");
		sql.append("SELECT SUM(cfss_Receivable) cfss_Receivable,cid  FROM CoFinanceSortAccountss where cfss_state=1 ");

		sql.append("GROUP BY  cid) b ON a.cid=b.cid  where 1=1 ");
		sql.append(strwhere);

		System.out.println(sql.toString());

		try {
			list = db.find(sql.toString(), CoFinanceSortAccountssModel.class,
					null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 根据条件查询实收款
	 * 
	 * @param cid
	 * @return
	 */
	public List<CoFinanceSortAccountssModel> getCoFinanceSortAccountssModellist1(
			String strwhere) {
		List<CoFinanceSortAccountssModel> list = new ArrayList<CoFinanceSortAccountssModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();

		sql.append(" select [cfss_id],[cfss_cfso_id],a.[cid],[ownmonth],[cfss_cpac_name],[cfss_Receivable]");
		sql.append(" ,[cfss_PaidIn],[cfss_addtime],[cfss_state],[cfss_fpstate],[cfss_yystate]");
		sql.append(" ,[cfss_addname],[cfss_remark] remark,[cfss_modname],[cfss_modtime],coba_company,coba_client ");
		sql.append(" ,cfss_type,cfss_fpfrist,cfss_allin from CoFinanceSortAccountss a ");
		sql.append("inner JOIN (select cid,coba_company,coba_client from CoBase) b ON a.cid=b.CID  where 1=1 ");
		sql.append(strwhere);

		try {
			list = db.find(sql.toString(), CoFinanceSortAccountssModel.class,
					null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<CoFinanceCollectModel> getCollectListPrint(List<String> l) {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		StringBuffer sql = new StringBuffer(
				" SELECT * FROM CoFinanceMonthlyBillSortAccount WHERE cfsa_cfmb_number in ( ");
		for (int i = 0; i < l.size(); i++) {
			sql.append("'");
			sql.append(l.get(i));
			sql.append("'");
			if (i + 1 < l.size()) {
				sql.append(",");
			}
		}
		sql.append(" ) AND cfsa_state = 1 ");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
				m.setCfco_cfmb_number(rs.getString("cfsa_cfmb_number"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfsa_PaidIn"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// public List<CoFinanceCollectModel> getCollectLists(String ownmonth,
	// Date sdate, Date edate, int coinid, String remark, String count) {
	// java.sql.Date sd = null;
	// java.sql.Date ed = null;
	//
	// if (sdate != null) {
	// sd = new java.sql.Date(sdate.getTime());
	// }
	// if (edate != null) {
	// ed = new java.sql.Date(edate.getTime());
	// }
	// List<CoFinanceCollectModel> list = new
	// ArrayList<CoFinanceCollectModel>();
	// CallableStatement csmt = conn
	// .getcall("CoFinanceCollectQuery_gxj(?,?,?,?,?,?)");
	// try {
	//
	// csmt.setString(1, ownmonth);
	// csmt.setDate(2, sd);
	// csmt.setDate(3, ed);
	// csmt.setString(4, remark);
	// csmt.setString(5, count;
	// csmt.setString(6, UserInfo.getUserid());
	// System.out.println("userinfo"+UserInfo.getUserid());
	//
	// // System.out.println("i want"+sql);
	// // ResultSet rs = conn.GRS(sql);
	// ResultSet rs = csmt.executeQuery();
	// while (rs.next()) {
	// CoFinanceCollectModel m = new CoFinanceCollectModel();
	// m.setCoin_id(rs.getInt("coin_id"));
	// m.setOwnmonth(rs.getInt("ownmonth"));
	// m.setCfco_remark(rs.getString("cfco_remark"));
	// m.setCid(rs.getInt("cid"));
	// m.setCoba_company(rs.getString("coba_company"));
	// m.setCfco_cfmb_number(rs.getString("cfsa_cfmb_number"));
	// m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
	// m.setCfsa_PaidIn(rs.getBigDecimal("cfsa_PaidIn"));
	// m.setCfco_TotalPaidIn(rs.getBigDecimal("cfco_TotalPaidIn"));
	// m.setCoba_client(rs.getString("coba_client"));
	// m.setMsg_a(rs.getString("msg_a"));
	// m.setCfco_addtime(rs.getString("addtime"));
	// m.setShortname(rs.getString("coba_shortname"));
	// m.setShortspell(rs.getString("coba_shortspell"));
	// m.setCfmb_WriteOffs(String.valueOf(rs.getInt("cfmb_WriteOffs")));
	// m.setCfmb_PersonnelConfim(String.valueOf(rs
	// .getInt("cfmb_PersonnelConfirm")));
	// m.setCfmb_FinanceConfim(String.valueOf(rs
	// .getInt("cfmb_FinanceConfirm")));
	// m.setCfco_id(rs.getInt("cfco_id"));
	// list.add(m);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return list;
	// }

	// 获取台账应收月份
	public List<String> getCoFinanceOwnmonth() {
		List<String> list = new LinkedList<String>();
		String sql = "select distinct ownmonth from CoFinanceMonthlyAccount order by ownmonth desc";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取财务部用户
	public List<String> getCwLoginname() {
		List<String> list = new LinkedList<String>();
		String sql = "select log_name from login l left join department dept on l.dep_id=dept.dep_id where dep_name='财务部'";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取公司每月应收统计表数据
	public List<CoFinanceMonthlyAccountModel> getMonthlyAccount(String where) {
		List<CoFinanceMonthlyAccountModel> list = new LinkedList<CoFinanceMonthlyAccountModel>();
		StringBuilder sql = new StringBuilder();
		sql.append("select co.CID,co.coba_company,co.coba_client,cfma_id,cfma.cfma_cfta_id,cfma.ownmonth,cfma_PersonnelReceivable,cfma_FinanceReceivable,cfma_addtime,cfma_remark,cfma_state,");
		sql.append("totalPaidIn=isnull(cfmb.totalPaidIn,0),LoanBalance=isnull(cfmb.LoanBalance,0),CarryForwardEx=isnull(cfmb.CarryForwardEx,0),billTotalReceivable=ISNULL(cfmb.billTotalReceivable,0),");
		sql.append("PersonnelNoConfirm=(case ISNULL(cfmb.PersonnelConfirm,0) when 0 then 0 else 1 end),FinanceNoConfirm=(case ISNULL(cfmb.FinanceConfirm,0) when 0 then 0 else 1 end)");
		sql.append(" from CoFinanceMonthlyAccount cfma inner join CoFinanceTotalAccount cfta on cfma.cfma_cfta_id=cfta.cfta_id");
		sql.append(" left join (select cfmb_cfta_id,ownmonth,billTotalReceivable=SUM(cfmb_PersonnelReceivable+cfmb_FinanceReceivable),SUM(cfmb_TotalPaidIn) as totalPaidIn,SUM(cfmb_LoanBalance) as LoanBalance,SUM(cfmb_CarryForwardEx) as CarryForwardEx,PersonnelConfirm=SUM(case cfmb_PersonnelConfirm when 0 then 1 else 0 end),FinanceConfirm=SUM(case cfmb_FinanceConfirm when 0 then 1 else 0 end) from CoFinanceMonthlyBill where cfmb_state=1 group by cfmb_cfta_id,ownmonth) cfmb  ");
		sql.append(" on cfmb.cfmb_cfta_id=cfma.cfma_cfta_id and cfmb.ownmonth=cfma.ownmonth ");
		sql.append(" inner join ( select cid,coba_company,coba_namespell,coba_client from CoBase where coba_servicestate=1 ) co on cfta.cid=co.CID ");
		sql.append(" where cfma.cfma_state=1 ");
		sql.append(where);
		System.out.println(sql.toString());
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				CoFinanceMonthlyAccountModel m = new CoFinanceMonthlyAccountModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCfma_id(rs.getInt("cfma_id"));
				m.setCfma_cfta_id(rs.getInt("cfma_cfta_id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfma_PersonnelReceivable(rs
						.getBigDecimal("cfma_PersonnelReceivable"));
				m.setCfma_FinanceReceivable(rs
						.getBigDecimal("cfma_FinanceReceivable"));
				m.setTotalPaidIn(rs.getBigDecimal("totalPaidIn"));
				m.setTotalLoanBalance(rs.getBigDecimal("LoanBalance"));
				m.setTotalCarryForwardEx(rs.getBigDecimal("CarryForwardEx"));
				m.setCfma_remark(rs.getString("cfma_remark"));
				m.setCfma_state(rs.getInt("cfma_state"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setExistsPersonnelConfirm(rs.getInt("PersonnelNoConfirm"));
				m.setExistsFinanceNoConfirm(rs.getInt("FinanceNoConfirm"));
				m.setBillTotalReceivable(rs
						.getBigDecimal("billTotalReceivable"));
				m.sumReceivable();
				m.sumImbalance();
				m.existsNoBill();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取公司每月应收统计表数据
	public CoFinanceMonthlyAccountModel getMonthlyAccountByCfmaId(int cfma_id) {
		CoFinanceMonthlyAccountModel m = new CoFinanceMonthlyAccountModel();
		dbconn db = new dbconn();
		String sql = "select co.cid,co.coba_company,co.coba_client,cfma_id,cfma.cfma_cfta_id,cfma.ownmonth,cfma_PersonnelReceivable,cfma_FinanceReceivable"
				+ " from CoFinanceMonthlyAccount cfma "
				+ " left join CoFinanceTotalAccount cfta on cfma.cfma_cfta_id=cfta.cfta_id "
				+ " left join ( select cid,coba_company,coba_namespell,coba_client from CoBase where coba_servicestate=1) co on cfta.cid=co.CID "
				+ " where cfma_id=?";
		List<CoFinanceMonthlyAccountModel> list = new ListModelList<>();
		try {
			list = db.find(sql, CoFinanceMonthlyAccountModel.class, null,
					cfma_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			m = list.get(0);
		}
		/*
		 * StringBuilder sql = new StringBuilder(); sql.append(
		 * "select co.CID,co.coba_company,co.coba_client,cfma_id,cfma.cfma_cfta_id,cfma.ownmonth,cfma_PersonnelReceivable,cfma_FinanceReceivable"
		 * ); sql.append(" from CoFinanceMonthlyAccount cfma "); sql.append(
		 * " left join CoFinanceTotalAccount cfta on cfma.cfma_cfta_id=cfta.cfta_id "
		 * ); sql.append(
		 * " left join ( select cid,coba_company,coba_namespell,coba_client from CoBase where coba_servicestate=1) co on cfta.cid=co.CID "
		 * ); sql.append(" where cfma_id=?"); PreparedStatement psmt =
		 * conn.getpre(sql.toString()); try { psmt.setInt(1, cfma_id); ResultSet
		 * rs = psmt.executeQuery(); rs.next(); m.setCid(rs.getInt("cid"));
		 * m.setCoba_company(rs.getString("coba_company"));
		 * m.setCfma_id(rs.getInt("cfma_id"));
		 * m.setCfma_cfta_id(rs.getInt("cfma_cfta_id"));
		 * m.setOwnmonth(rs.getInt("ownmonth"));
		 * m.setCfma_PersonnelReceivable(rs
		 * .getBigDecimal("cfma_PersonnelReceivable"));
		 * m.setCfma_FinanceReceivable(rs
		 * .getBigDecimal("cfma_FinanceReceivable"));
		 * m.setCoba_client(rs.getString("coba_client"));
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		return m;
	}

	// 获取公司总账表数据
	public List<CoFinanceTotalAccountModel> getTotalAccount(String where) {
		List<CoFinanceTotalAccountModel> list = new LinkedList<CoFinanceTotalAccountModel>();
		StringBuilder sql = new StringBuilder();
		sql.append("select cfta.cid,co.coba_company,co.coba_client,cfta_id,cfta_Balance,");
		sql.append("PersonnelReceivable=isnull(cfma_PersonnelReceivable,0),FinanceReceivable=isnull(cfma_FinanceReceivable,0),");
		sql.append("TotalPaidIn=ISNULL(cfco.cfco_TotalPaidIn,0),LoanBalance=ISNULL(cflo.cflo_LoanBalance,0),CarryForwardEx=ISNULL(cfcf.cfcf_CarryForwardEx,0)");
		sql.append(" from CoFinanceTotalAccount cfta ");
		sql.append(" left join (select cid,coba_company,coba_client from CoBase) co on cfta.cid=co.CID");
		sql.append(" left join (select cfma_cfta_id,SUM(cfma_PersonnelReceivable) as cfma_PersonnelReceivable,SUM(cfma_FinanceReceivable) as cfma_FinanceReceivable from CoFinanceMonthlyAccount where cfma_state=1 group by cfma_cfta_id) cfma ");
		sql.append(" on cfma.cfma_cfta_id=cfta.cfta_id ");
		sql.append(" left join (select cid,cfco_TotalPaidIn=SUM(cfco_TotalPaidIn) from CoFinanceCollect where cfco_state=1 group by cid) cfco on cfco.cid=cfta.cid ");
		sql.append(" left join (select cid,cflo_LoanBalance=SUM(cflo_LoanBalance) from CoFinanceLoan where cflo_state=1 group by cid)cflo on cflo.cid=cfta.cid ");
		sql.append(" left join (select cid,cfcf_CarryForwardEx=SUM(cfcf_CarryForwardEx) from CoFinanceCarryForward where cflo_state=1 group by cid)cfcf on cfcf.cid=cfta.cid ");
		sql.append(" where 1=1 ");
		sql.append(where);
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				CoFinanceTotalAccountModel m = new CoFinanceTotalAccountModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCfta_id(rs.getInt("cfta_id"));
				m.setCfta_Balance(rs.getBigDecimal("cfta_Balance"));
				m.setPersonnelReceivable(rs
						.getBigDecimal("PersonnelReceivable"));
				m.setFinanceReceivable(rs.getBigDecimal("FinanceReceivable"));
				m.setTotalPaidIn(rs.getBigDecimal("TotalPaidIn"));
				m.setLoanBalance(rs.getBigDecimal("LoanBalance"));
				m.setCarryForwardEx(rs.getBigDecimal("CarryForwardEx"));
				m.sumReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 公司总账合计
	public CoFinanceTotalAccountModel getCompanyTotalAccount(int cid) {
		CoFinanceTotalAccountModel m = new CoFinanceTotalAccountModel();
		StringBuilder sql = new StringBuilder();
		sql.append("select co.*,cfta_id,cfta_Balance,");
		sql.append("PersonnelReceivable=isnull(cfma_PersonnelReceivable,0),FinanceReceivable=isnull(cfma_FinanceReceivable,0),");
		sql.append("TotalPaidIn=ISNULL(cfco.cfco_TotalPaidIn,0),LoanBalance=ISNULL(cflo.cflo_LoanBalance,0),CarryForwardEx=ISNULL(cfcf.cfcf_CarryForwardEx,0)");
		sql.append(" from CoFinanceTotalAccount cfta ");
		sql.append(" left join (select cid,coba_company,coba_client from CoBase) co on cfta.cid=co.CID");
		sql.append(" left join (select cfma_cfta_id,SUM(cfma_PersonnelReceivable) as cfma_PersonnelReceivable,SUM(cfma_FinanceReceivable) as cfma_FinanceReceivable from CoFinanceMonthlyAccount where cfma_state=1 group by cfma_cfta_id) cfma ");
		sql.append(" on cfma.cfma_cfta_id=cfta.cfta_id ");
		sql.append(" left join (select cid,cfco_TotalPaidIn=SUM(cfco_TotalPaidIn) from CoFinanceCollect where cfco_state=1 group by cid) cfco on cfco.cid=cfta.cid ");
		sql.append(" left join (select cid,cflo_LoanBalance=SUM(cflo_LoanBalance) from CoFinanceLoan where cflo_state=1 group by cid)cflo on cflo.cid=cfta.cid ");
		sql.append(" left join (select cid,cfcf_CarryForwardEx=SUM(cfcf_CarryForwardEx) from CoFinanceCarryForward where cflo_state=1 group by cid)cfcf on cfcf.cid=cfta.cid ");
		sql.append(" where cfta.cid=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m.setCid(rs.getInt("cid"));
			m.setCoba_company(rs.getString("coba_company"));
			m.setCoba_client(rs.getString("coba_client"));
			m.setCfta_id(rs.getInt("cfta_id"));
			m.setCfta_Balance(rs.getBigDecimal("cfta_Balance"));
			m.setPersonnelReceivable(rs.getBigDecimal("PersonnelReceivable"));
			m.setFinanceReceivable(rs.getBigDecimal("FinanceReceivable"));
			m.setTotalPaidIn(rs.getBigDecimal("TotalPaidIn"));
			m.setLoanBalance(rs.getBigDecimal("LoanBalance"));
			m.setCarryForwardEx(rs.getBigDecimal("CarryForwardEx"));
			m.sumReceivable();
			m.sumImbalance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取账单数据
	public List<CoFinanceMonthlyBillModel> getMonthlyBill(int cfta_id,
			int ownmonth) {
		List<CoFinanceMonthlyBillModel> list = new LinkedList<CoFinanceMonthlyBillModel>();
		StringBuilder sql = new StringBuilder();
		sql.append("select cfmb_number,cfmb_name,cfmb_cfta_id,ownmonth,cfmb_PersonnelReceivable,cfmb_FinanceReceivable,cfmb_TotalPaidIn,cfmb_LoanBalance,cfmb_CarryForwardEx,cfmb_PersonnelConfirm,cfmb_FinanceConfirm,cfmb_Confirm,cfmb_WriteOffs from CoFinanceMonthlyBill where cfmb_cfta_id=? and ownmonth=? and cfmb_state=1");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cfta_id);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				CoFinanceMonthlyBillModel m = new CoFinanceMonthlyBillModel();
				m.setCfmb_cfta_id(rs.getInt("cfmb_cfta_id"));
				m.setCfmb_number(rs.getString("cfmb_number"));
				m.setCfmb_name(rs.getString("cfmb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfmb_PersonnelReceivable(rs
						.getBigDecimal("cfmb_PersonnelReceivable"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("cfmb_FinanceReceivable"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
				m.setCfmb_CarryForwardEx(rs
						.getBigDecimal("cfmb_CarryForwardEx"));
				m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
				m.setCfmb_FinanceConfirm(rs.getInt("cfmb_FinanceConfirm"));
				m.setCfmb_Confirm(rs.getInt("cfmb_Confirm"));
				m.setCfmb_WriteOffs(rs.getInt("cfmb_WriteOffs"));
				m.sumTotalReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据搜索条件获取账单数据
	public List<CoFinanceMonthlyBillModel> getAllBill(String where) {
		List<CoFinanceMonthlyBillModel> list = new LinkedList<CoFinanceMonthlyBillModel>();
		StringBuilder sql = new StringBuilder();
		sql.append("select co.coba_company,cfmb.ownmonth,cfmb_number,cfmb_name,cfmb_cfta_id,ownmonth,cfmb_PersonnelReceivable,cfmb_FinanceReceivable,cfmb_TotalPaidIn,cfmb_LoanBalance,cfmb_CarryForwardEx,cfmb_PersonnelConfirm,cfmb_FinanceConfirm,cfmb_Confirm,cfmb_WriteOffs ");
		sql.append(" from CoFinanceMonthlyBill cfmb ");
		sql.append(" inner join CoFinanceTotalAccount cfta on cfta.cfta_id=cfmb.cfmb_cfta_id ");
		sql.append(" inner join (select cid,coba_company,coba_namespell,coba_client from CoBase where coba_servicestate=1) co on cfta.cid=co.CID ");
		sql.append(" where cfmb_state=1 ");
		sql.append(where);
		sql.append(" order by co.cid,cfmb_number");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				CoFinanceMonthlyBillModel m = new CoFinanceMonthlyBillModel();
				m.setCfmb_cfta_id(rs.getInt("cfmb_cfta_id"));
				m.setCfmb_number(rs.getString("cfmb_number"));
				m.setCfmb_name(rs.getString("cfmb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCompany(rs.getString("coba_company"));
				m.setCfmb_PersonnelReceivable(rs
						.getBigDecimal("cfmb_PersonnelReceivable"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("cfmb_FinanceReceivable"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
				m.setCfmb_CarryForwardEx(rs
						.getBigDecimal("cfmb_CarryForwardEx"));
				m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
				m.setCfmb_FinanceConfirm(rs.getInt("cfmb_FinanceConfirm"));
				m.setCfmb_Confirm(rs.getInt("cfmb_Confirm"));
				m.setCfmb_WriteOffs(rs.getInt("cfmb_WriteOffs"));
				m.sumTotalReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该公司所有账单数据
	public List<CoFinanceMonthlyBillModel> getCompanyAllBill(int cfta_id) {
		List<CoFinanceMonthlyBillModel> list = new LinkedList<CoFinanceMonthlyBillModel>();
		StringBuilder sql = new StringBuilder();
		sql.append("select cfmb_number,cfmb_name,cfmb_cfta_id,ownmonth,cfmb_PersonnelReceivable,cfmb_FinanceReceivable,cfmb_TotalPaidIn,cfmb_LoanBalance,cfmb_CarryForwardEx,cfmb_PersonnelConfirm,cfmb_FinanceConfirm,cfmb_Confirm,cfmb_WriteOffs from CoFinanceMonthlyBill where cfmb_cfta_id=? and cfmb_state=1 order by ownmonth desc");
		System.out.println(cfta_id);
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cfta_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				CoFinanceMonthlyBillModel m = new CoFinanceMonthlyBillModel();
				m.setCfmb_cfta_id(rs.getInt("cfmb_cfta_id"));
				m.setCfmb_number(rs.getString("cfmb_number"));
				m.setCfmb_name(rs.getString("cfmb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfmb_PersonnelReceivable(rs
						.getBigDecimal("cfmb_PersonnelReceivable"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("cfmb_FinanceReceivable"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
				m.setCfmb_CarryForwardEx(rs
						.getBigDecimal("cfmb_CarryForwardEx"));
				m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
				m.setCfmb_FinanceConfirm(rs.getInt("cfmb_FinanceConfirm"));
				m.setCfmb_Confirm(rs.getInt("cfmb_Confirm"));
				m.setCfmb_WriteOffs(rs.getInt("cfmb_WriteOffs"));
				m.sumTotalReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 公司台账按月查询详细
	public CoFinanceMonthlyAccountModel getMonthlyCompanyView(int cid,
			int ownmonth) {
		CoFinanceMonthlyAccountModel m = new CoFinanceMonthlyAccountModel();
		try {
			ResultSet rs = conn.GRS("exec CoFinanceMonthlyCompanyView_p_lwj "
					+ cid + "," + ownmonth);
			rs.next();
			m.setCid(rs.getInt("cid"));
			m.setCoba_company(rs.getString("coba_company"));
			m.setCoba_client(rs.getString("coba_client"));
			m.setCfma_id(rs.getInt("cfma_id"));
			m.setCfma_cfta_id(rs.getInt("cfma_cfta_id"));
			m.setOwnmonth(rs.getInt("ownmonth"));
			m.setCfma_PersonnelReceivable(rs
					.getBigDecimal("cfma_PersonnelReceivable"));
			m.setCfma_FinanceReceivable(rs
					.getBigDecimal("cfma_FinanceReceivable"));
			m.setTotalPaidIn(rs.getBigDecimal("TotalPaidIn"));
			m.setTotalLoanBalance(rs.getBigDecimal("LoanBalance"));
			m.setTotalCarryForwardEx(rs.getBigDecimal("CarryForwardEx"));
			m.setCfma_remark(rs.getString("cfma_remark"));
			m.setCfma_state(rs.getInt("cfma_state"));
			m.sumReceivable();
			m.sumImbalance();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 查询公司台账详细
	public List<CoFinanceMonthlyAccountModel> getMonthlyCompanyFinance(int cid) {
		List<CoFinanceMonthlyAccountModel> list = new ArrayList<CoFinanceMonthlyAccountModel>();
		CoFinanceMonthlyAccountModel m = null;
		try {
			ResultSet rs = conn.GRS("exec CoFinanceMonthlyCompanyView_p_lwj "
					+ cid + "," + 0);
			while (rs.next()) {
				m = new CoFinanceMonthlyAccountModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCfma_id(rs.getInt("cfma_id"));
				m.setCfma_cfta_id(rs.getInt("cfma_cfta_id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfma_PersonnelReceivable(rs
						.getBigDecimal("cfma_PersonnelReceivable"));
				m.setCfma_FinanceReceivable(rs
						.getBigDecimal("cfma_FinanceReceivable"));
				m.setTotalPaidIn(rs.getBigDecimal("TotalPaidIn"));
				m.setTotalLoanBalance(rs.getBigDecimal("LoanBalance"));
				m.setTotalCarryForwardEx(rs.getBigDecimal("CarryForwardEx"));
				m.setCfma_remark(rs.getString("cfma_remark"));
				m.setCfma_state(rs.getInt("cfma_state"));
				m.sumReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取公司台账财务科目
	public List<CoFinanceMonthlyBillSortAccountModel> getMonthlySortAccount(
			int cid, int ownmonth) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		CoFinanceMonthlyBillSortAccountModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cfsa.cfsa_cpac_name,cfsa_Receivable,PaidIn=isnull(s.PaidIn,0),imbalance=ISNULL(s.PaidIn,0)-ISNULL(cfsa_Receivable,0) ");
		sql.append("from CoFinanceSortAccount cfsa ");
		sql.append("left join (select cfta.cid,cfmb.ownmonth,cfsa.cfsa_cpac_name,PaidIn=ISNULL(sum(cfsa.cfsa_PaidIn),0) from CoFinanceMonthlyBillSortAccount cfsa ");
		sql.append("inner join CoFinanceMonthlyBill cfmb on cfsa.cfsa_cfmb_number=cfmb.cfmb_number ");
		sql.append("inner join CoFinanceTotalAccount cfta on cfmb_cfta_id=cfta.cfta_id ");
		sql.append("group by cfta.cid,cfmb.ownmonth,cfsa.cfsa_cpac_name) s on cfsa.cid=s.cid and cfsa.ownmonth=s.ownmonth and cfsa.cfsa_cpac_name=s.cfsa_cpac_name ");
		sql.append("where cfsa.cid=? and cfsa.ownmonth=? and cfsa_state=1");

		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, cid);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceMonthlyBillSortAccountModel();
				m.setCfsa_cpac_name(rs.getString("cfsa_cpac_name"));
				m.setCfsa_Receivable(rs.getBigDecimal("cfsa_Receivable"));
				m.setCfsa_PaidIn(rs.getBigDecimal("PaidIn"));
				m.setImbalance(rs.getBigDecimal("imbalance"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 统计当月销账数据
	public int[] getOwnmonthCount(int ownmonth) {
		int[] count = new int[5];
		String sql = "select allbill=COUNT(cfmb_number),df=sum(case when cfmb_WriteOffs=0 then 1 else 0 end)  from CoFinanceMonthlyBill where ownmonth=? and cfmb_state=1";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			count[0] = rs.getInt("allbill");
			count[1] = rs.getInt("df");
			count[2] = count[0] - count[1];
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	// 获取账单生成模板
	public List<CoFinanceMonthlyBillTempletModel> getBillTemplet() {
		List<CoFinanceMonthlyBillTempletModel> list = new ArrayList<CoFinanceMonthlyBillTempletModel>();
		CoFinanceMonthlyBillTempletModel m = null;
		String sql = "select cfmt_id,cfmt_name,cfmt_prefix from CoFinanceMonthlyBillTemplet where cfmt_state=1 order by cfmt_order";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoFinanceMonthlyBillTempletModel();
				m.setCfmt_name(rs.getString("cfmt_name"));
				m.setCfmt_prefix(rs.getString("cfmt_prefix"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取账单生成模板
	public List<CoFinanceMonthlyBillTempletModel> getBillTemplet(int depid) {
		List<CoFinanceMonthlyBillTempletModel> list = new ArrayList<CoFinanceMonthlyBillTempletModel>();
		CoFinanceMonthlyBillTempletModel m = null;
		String sql = "";
		//if (depid == 13) {
			sql = "select cfmt_id,cfmt_name,cfmt_prefix from CoFinanceMonthlyBillTemplet where cfmt_state=1 and cfmt_prefix<>'CO' order by cfmt_order";
		//} else {
		//	sql = "select cfmt_id,cfmt_name,cfmt_prefix from CoFinanceMonthlyBillTemplet where cfmt_state=1 order by cfmt_order";
		//}
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoFinanceMonthlyBillTempletModel();
				m.setCfmt_name(rs.getString("cfmt_name"));
				m.setCfmt_prefix(rs.getString("cfmt_prefix"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取当月未分配账单业务的合同信息(未加账单)
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletCompact(
			int cid, int ownmonth) {
		List<CoFinanceMonthlyBillTempletConModel> list = new ArrayList<CoFinanceMonthlyBillTempletConModel>();
		CoFinanceMonthlyBillTempletConModel m = null;
		try {
			ResultSet rs = conn.GRS("exec CoFinanceCompactSel_p_lwj " + cid
					+ "," + ownmonth);
			while (rs.next()) {
				m = new CoFinanceMonthlyBillTempletConModel();
				m.setId(rs.getInt("coco_id"));
				m.setName(rs.getString("coco_compactid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取当月未分配账单业务的雇员信息(未加账单)
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletEmbase(
			int cid, int ownmonth) {
		List<CoFinanceMonthlyBillTempletConModel> list = new ArrayList<CoFinanceMonthlyBillTempletConModel>();
		CoFinanceMonthlyBillTempletConModel m = null;
		try {
			ResultSet rs = conn.GRS("exec CoFinanceEmbaseSel_p_lwj " + cid
					+ "," + ownmonth);
			while (rs.next()) {
				m = new CoFinanceMonthlyBillTempletConModel();
				m.setId(rs.getInt("efba_id"));
				m.setWinId(rs.getInt("gid"));
				m.setName(rs.getString("efba_emba_name"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取当月公司业务委托地区信息(未加账单)
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletCommissionOutCity(
			int cid, int ownmonth) {
		List<CoFinanceMonthlyBillTempletConModel> list = new ArrayList<CoFinanceMonthlyBillTempletConModel>();
		CoFinanceMonthlyBillTempletConModel m = null;
		String sql = "select distinct ppc.id,ppc.name from EmFinanceCommissionOut efco inner join EmFinanceBase efba on efco.efco_efba_id=efba.efba_id inner join CoAgencyBaseCityRel cabc on efco.efco_cabc_id=cabc.cabc_id inner join PubProCity ppc on cabc.cabc_ppc_id=ppc.id where efco.ownmonth=? and efba.cid=? and efba_id is not null  and efco.efco_cfmb_number='0'";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			psmt.setInt(2, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceMonthlyBillTempletConModel();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取当月公司的服务项目信息(未加账单)
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletItem(
			int cid, int ownmonth) {
		List<CoFinanceMonthlyBillTempletConModel> list = new ArrayList<CoFinanceMonthlyBillTempletConModel>();
		CoFinanceMonthlyBillTempletConModel m = null;
		try {
			String strsql = "CoFinanceItemSel_p_lwj(" + cid + "," + ownmonth
					+ ")";
			CallableStatement callsm = conn.getcall(strsql);
			callsm.execute();
			do {
				ResultSet rs = callsm.getResultSet();
				while (rs.next()) {
					m = new CoFinanceMonthlyBillTempletConModel();
					m.setId(rs.getInt("id"));
					m.setName(rs.getString("item"));
					list.add(m);
				}
			} while (callsm.getMoreResults());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 收款纪录
	public List<CoFinanceCollectModel> getCollectLog(int cid) {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		CoFinanceCollectModel m = null;
		String sql = "select cfco_id,cid,ownmonth,cfco_cfmb_number,cfco_TotalPaidIn,cfco_remark,cfco_addname,cfco_addtime=CONVERT(varchar(100), cfco_addtime, 120) from CoFinanceCollect where cid=? and cfco_state=1 order by cfco_addtime desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceCollectModel();
				m.setCfco_id(rs.getInt("cfco_id"));
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfco_cfmb_number(rs.getString("cfco_cfmb_number"));
				m.setCfco_TotalPaidIn(rs.getBigDecimal("cfco_TotalPaidIn"));
				m.setCfco_remark(rs.getString("cfco_remark"));
				m.setCfco_addname(rs.getString("cfco_addname"));
				m.setCfco_addtime(rs.getString("cfco_addtime"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 收款纪录
	public List<CoFinanceCollectModel> getCollectlk(String wheresql)
			throws Exception {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		CoFinanceCollectModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.cid,coba_company,coba_client,cfco_TotalPaidIn,month,imbalance,cfta_updatetime,cfco_TotalPaidIn-imbalance balance,CONVERT(varchar(6),cfdm_addtime, 112) cfdm_addtime FROM  ");
		sql.append(" (select cid,coba_company,coba_client,coba_gzaddname from CoBase ) a left join ( select  cid,sum(cfco_TotalPaidIn) cfco_TotalPaidIn   ");
		sql.append(" ,MAX(ownmonth) month  FROM CoFinanceCollect GROUP BY cid ) b ON a.CID=b.cid  ");
		sql.append(" left join (select  cid,cfta_Balance imbalance,cfta_updatetime  from CoFinanceTotalAccount )c ON a.CID=c.cid  ");
		sql.append(" LEFT JOIN (SELECT cid,MAX(cfdm_addtime) cfdm_addtime FROM CoFinanceDrawMoney where cfdm_state=1 GROUP BY cid) d on a.CID=d.cid  ");
		sql.append(" WHERE cfco_TotalPaidIn IS NOT NULL  ");
		sql.append(wheresql);
		// sql.append(" ORDER BY  month desc, cfta_updatetime  ");
		// String sql =
		// "select cfco_id,cid,ownmonth,cfco_cfmb_number,cfco_TotalPaidIn,cfco_remark,cfco_addname,cfco_addtime=CONVERT(varchar(100), cfco_addtime, 120) from CoFinanceCollect where cid=? and cfco_state=1 order by cfco_addtime desc";

		System.out.println(sql.toString());
		ResultSet rs = conn.GRS(sql.toString());
		try {

			while (rs.next()) {
				m = new CoFinanceCollectModel();
				m.setMonth(rs.getInt("month"));
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
			 
				m.setCfco_TotalPaidIn(rs.getBigDecimal("cfco_TotalPaidIn"));
				m.setImbalance(rs.getBigDecimal("imbalance"));
				m.setBalance(rs.getBigDecimal("balance"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCfdm_addtime(rs.getString("cfdm_addtime"));
				m.setCfta_updatetime(rs.getString("cfta_updatetime"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询开票收款记录
	public List<CoFinanceCollectModel> getCollectLogList(int cid) {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,sum(a.cfco_TotalPaidIn)cfco_TotalPaidIn,"
				+ " max(coin_id)coin_id,SUM(coin_total)total"
				+ " from CoFinanceCollect a"
				+ " left join coinvoice b on a.cfco_id=b.coin_cfco_id"
				+ " where cid=? and cfco_state=1" + " group by a.cid";

		try {
			list = db.find(sql, CoFinanceCollectModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询收款列表
	public List<CoFinanceCollectModel> getCollectLogInfo(int cid, String order) {
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.ownmonth,a.cfco_TotalPaidIn,cfco_addname,cfco_addtime,"
				+ " max(coin_id)coin_id,SUM(coin_total)total"
				+ " from CoFinanceCollect a"
				+ " left join coinvoice b on a.cfco_id=b.coin_cfco_id"
				+ " where cid=? and cfco_state=1"
				+ " group by a.cid,a.ownmonth,a.cfco_TotalPaidIn,cfco_addname,cfco_addtime";
		if (order != null) {
			if (!order.equals("")) {
				sql = sql + order;
			}
		}
		try {
			list = db.find(sql, CoFinanceCollectModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取到cobase的信息
	public List<CoBaseModel> getCoinfoList() {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		String sql = "SELECT cid,coba_company,coba_client,coba_ufclass,coba_ufid,isnull(msg_a.msg_a,-1)msg_a FROM cobase left join (select smwr_tid,case when syme_log_id= ? then 2 when symr_log_id=  ? then symr_readstate end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and (symr_log_id=? or syme_log_id=? ) and  smwr_table='CoFinanceCollect' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on cid=msg_a.smwr_tid ";
		try {
			PreparedStatement pstmt = conn.getpre(sql);
			pstmt.setString(1, UserInfo.getUserid());
			pstmt.setString(2, UserInfo.getUserid());
			pstmt.setString(3, UserInfo.getUserid());
			pstmt.setString(4, UserInfo.getUserid());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoBaseModel m = new CoBaseModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setMsg_a(rs.getString("msg_a"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取到cobase的信息
	public List<CollectAmountModel> getCoinfoListall() {

		List<CollectAmountModel> listamlist = new ArrayList<CollectAmountModel>();

		String sqlam = "SELECT cid,coba_company,coba_client,"
				+ "fileManageFee,serviceFee,sheBaoFee,activityFee,bodyTestFee,"
				+ "businessProtectFee,bookFee,salaryOfAfterTax,oMoveFee,houseRestore,"
				+ "finanServiceFee,businessServiceFee,recruitServiceFee,residencePermitFee,"
				+ "lasscFee,deformityFee,other  " +

				" FROM View_CoFinanceMonthlyBillSortAccount  ";

		try {
			listamlist = conn.find(sqlam, CollectAmountModel.class, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listamlist;
	}

	// 获取实收账单
	public List<CoFinanceCollectModel> getCollectList(String ownmonth,
			Date sdate, Date edate, String remark) {
		java.sql.Date sd = null;
		java.sql.Date ed = null;

		if (sdate != null) {
			sd = new java.sql.Date(sdate.getTime());
		}
		if (edate != null) {
			ed = new java.sql.Date(edate.getTime());
		}
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		CallableStatement csmt = conn
				.getcall("CoFinanceCollectQuery_gxj(?,?,?,?,?)");

		// 根据cid获取实收账单
		try {

			csmt.setString(1, ownmonth);
			csmt.setDate(2, sd);
			csmt.setDate(3, ed);
			csmt.setString(4, remark);
			csmt.setString(5, UserInfo.getUserid());

			ResultSet rs = csmt.executeQuery();
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfco_remark(rs.getString("cfdm_remark"));
				m.setCid(rs.getInt("cid"));
				m.setCfco_cfmb_number(rs.getString("cfco_cfmb_number"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setMsg_a(rs.getString("msg_a"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取实收账单
	public List<CoFinanceCollectModel> getCollectListsum(String ownmonth,
			Date sdate, Date edate, String remark) {
		java.sql.Date sd = null;
		java.sql.Date ed = null;

		if (sdate != null) {
			sd = new java.sql.Date(sdate.getTime());
		}
		if (edate != null) {
			ed = new java.sql.Date(edate.getTime());
		}
		List<CoFinanceCollectModel> list = new ArrayList<CoFinanceCollectModel>();
		CallableStatement csmt = conn
				.getcall("CoFinanceCollectQuery_gxj(?,?,?,?,?)");

		// 根据cid获取实收账单
		try {

			csmt.setString(1, ownmonth);
			csmt.setDate(2, sd);
			csmt.setDate(3, ed);
			csmt.setString(4, remark);
			csmt.setString(5, UserInfo.getUserid());

			ResultSet rs = csmt.executeQuery();
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfco_remark(rs.getString("cfdm_remark"));
				m.setCid(rs.getInt("cid"));
				m.setCfco_cfmb_number(rs.getString("cfco_cfmb_number"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setMsg_a(rs.getString("msg_a"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据账单号查询项目费用
	public List<CoFinanceMonthlyBillSortAccountModel> getAmountList(
			String number) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		String sql = "SELECT * FROM CoFinanceMonthlyBillSortAccount WHERE cfsa_cfmb_number = ? ";

		try {
			list = conn.find(sql, CoFinanceMonthlyBillSortAccountModel.class,
					null, number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据账单号查询项目费用
	public List<CoFinanceMonthlyBillSortAccountModel> getAmountListsum(
			String number) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ArrayList<CoFinanceMonthlyBillSortAccountModel>();
		String sql = "SELECT * FROM CoFinanceMonthlyBillSortAccount WHERE cfsa_cfmb_number = ? ";

		try {
			list = conn.find(sql, CoFinanceMonthlyBillSortAccountModel.class,
					null, number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 添加发票领取人
	public int addInvoiceReceive(CoInvoiceModel m) {
		int row = 0;
		String sql = " UPDATE CoInvoice SET coin_receivename = ? WHERE coin_id = ? ";
		Object[] objs = { m.getReceivename(), m.getCoin_id() };
		try {
			row = conn.updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
}