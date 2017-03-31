package dal.EmCommissionOut;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmPay.EmPay_OperateBll;

import Conn.dbconn;
import Controller.systemWindowController;
import Controller.EmResidencePermit.newExcelImpl;
import Model.EmCommissionOutPayModel;

public class EmCommissionOutPayDal {
	private dbconn conn = new dbconn();
	private EmPay_OperateBll payBll = new EmPay_OperateBll();

	// 显示所有帐单数
	public List<EmCommissionOutPayModel> getempaylist(String city,
			String depcompany) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		String str1 = "";
		if (!city.equals("")) {
			str = " and coab_city='" + city + "'";
		}
		if (!depcompany.equals("")) {
			str1 = " and coab_name='" + depcompany + "'";
		}
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select coab_name,COUNT(distinct ownmonth),SUM(ecpu_total) from EmCommissionOutPayUpdate a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id where 1=1 "
				+ str + str1 + " group by coab_name";
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setEcop_dep_company(rs.getString(1));
				model.setEcop_paycount(rs.getString(2));
				model.setEcop_paytotal(rs.getString(3));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示所有帐单数
	public List<EmCommissionOutPayModel> getownmonthlist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select distinct ownmonth from EmCommissionOutPay";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getemmonthpaylist(
			String ecop_dep_company, String str) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sqlstr1 = "";
		String ys_fee = "";
		String ys_dis_fee = "";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select ownmonth,SUM(ecpu_total),0,0-SUM(ecpu_total),coab_name from EmCommissionOutPayUpdate a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id where coab_name='"
				+ ecop_dep_company + "'  group by ownmonth,coab_name";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				sqlstr1 = "select SUM(ecod_eofd_month_sum),SUM(ecod_eofd_month_sum)-"
						+ rs.getString(2)
						+ " from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecop_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id where a.ownmonth='"
						+ rs.getString(1)
						+ "' and coab_name='"
						+ rs.getString(5) + "'";
				rs1 = db.GRS(sqlstr1);
				while (rs1.next()) {
					ys_fee = rs1.getString(1);
					ys_dis_fee = rs1.getString(2);
				}
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_sf_fee(rs.getString(2));
				model.setEcop_yf_fee(ys_fee);
				model.setEcop_yfsf_dis_fee(ys_dis_fee);
				model.setEcop_dep_company(rs.getString(5));
				if (str.equals("p1") && ys_dis_fee.equals("0")) {
					list.add(model);
				}
				if (str.equals("p2") && !ys_dis_fee.equals("0")) {
					list.add(model);
				}
				if (str.equals("p3") && ys_fee.equals("0")) {
					list.add(model);
				}

				if (!str.equals("p1") && !str.equals("p2") && !str.equals("p3")) {
					list.add(model);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getautpaylist(String ownmonth,
			String ecop_dep_company) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sqlstr1 = "";
		String ys_fee = "";
		String ys_dis_fee = "";
		String ecp_state = "0";
		String ecp_state2 = "0";
		String str = "";
		String str1 = "";
		String sys_ownmonth = "";
		String sys_dep_company = "";
		if (!ownmonth.equals("")) {
			str = " and ownmonth=" + ownmonth;
		}
		if (!ecop_dep_company.equals("")) {
			str1 = " and coab_name='" + ecop_dep_company + "'";
		}
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select ownmonth,coab_name,cabc_id,SUM(ecod_eofd_month_sum)"
				+ " from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecop_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id where 1=1"
				+ str + str1 + "  group by ownmonth,coab_name,cabc_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				sqlstr1 = "select ownmonth,SUM(ecpu_total),SUM(ecpu_total)-"
						+ rs.getString(4)
						+ " from EmCommissionOutPayUpdate a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id where cabc_id= "
						+ rs.getString("cabc_id") + " and ownmonth="
						+ rs.getString("ownmonth")
						+ "  group by ownmonth,coab_name,cabc_id";

				rs1 = db.GRS(sqlstr1);

				while (rs1.next()) {
					ys_fee = rs1.getString(2);
					ys_dis_fee = rs1.getString(3);
				}

				String sqlstr2 = "select COUNT(*),case when count(*)=0 then 0 else COUNT(*)/SUM(ecpu_state) end from EmCommissionOutPayUpdate a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id where coab_name='"
						+ rs.getString("cabc_id")
						+ "' and ownmonth="
						+ rs.getString("ownmonth") + " and ecpu_state<3";

				rs2 = db.GRS(sqlstr2);

				while (rs2.next()) {
					ecp_state = rs2.getString(1);
					ecp_state2 = rs2.getString(2);
				}
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_yf_fee(rs.getString(4));
				model.setEcop_sf_fee(ys_fee);
				model.setEcop_yfsf_dis_fee(ys_dis_fee);
				model.setEcop_dep_company(rs.getString(2));
				model.setEcop_state(ecp_state);
				model.setEcop_cabc_id(ecp_state2);

				list.add(model);

			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getautpaylist1(String ownmonth,
			String ecop_dep_company,String wt_city) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		String str1 = "";
		String str2 = "";
		if (!ownmonth.equals("")) {
			str = " and a.ownmonth=" + ownmonth;
		}
		if (!ecop_dep_company.equals("")) {
			str1 = " and a.coab_name='" + ecop_dep_company + "'";
		}
		if (!wt_city.equals("")) {
			str2 = " and a.city='" + wt_city + "'";
		}
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();

		String sqlstr = "select a.ownmonth,a.coab_name,a.cabc_id,isnull(yf_total,0)+ISNULL(di_total,0),isnull(sf_total,0),isnull(sf_total,0)-isnull(yf_total,0)-ISNULL(di_total,0),a.pay_st-isnull(ov_st,0) pa_st,isnull(pay_st,0) pay_st,isnull(pay_sf,0) pay_sf,a.city from (select ownmonth,c.city,coab_name,cabc_id,SUM(ecod_eofd_month_sum) yf_total,COUNT(distinct ecop_id) pay_st from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel_view c on c.cabc_id=a.ecop_cabc_id group by ownmonth,coab_name,cabc_id,c.city) a left join (select ecpu_cabc_id,ownmonth,SUM(ecpu_total) sf_total,count(distinct gid) pay_sf from EmCommissionOutPayUpdate group by ecpu_cabc_id,ownmonth) b on b.ecpu_cabc_id=a.cabc_id and b.ownmonth=a.ownmonth left join (select ecop_cabc_id,ownmonth,COUNT(distinct ecop_id) ov_st from EmCommissionOutPay  where ecop_state=2 group by ecop_cabc_id,ownmonth) c on c.ecop_cabc_id=a.cabc_id and c.ownmonth=a.ownmonth left join (select ecod_cabc_id,ownmonth,SUM(ecod_total) di_total from EmCommissionOutPayDif where ecod_remark<>'' and ecod_remark is not null group by ecod_cabc_id,ownmonth) d on d.ecod_cabc_id=a.cabc_id and d.ownmonth=a.ownmonth where 1=1 "
				+ str + str1+str2+" order by ABS(isnull(sf_total,0)-isnull(yf_total,0))";

		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_yf_fee(rs.getString(4));
				model.setEcop_sf_fee(rs.getString(5));
				model.setEcop_yfsf_dis_fee(rs.getString(6));
				model.setEcop_dep_company(rs.getString(2));
				model.setEcop_state(rs.getString("pa_st"));
				model.setEcop_cabc_id(rs.getString(3));
				model.setEcop_ch_st(rs.getString("pay_st"));
				model.setEcop_paycount(rs.getString("pay_sf"));
				model.setEcop_yl_di(rs.getString("city"));
				list.add(model);

			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getautpaysflist(String ownmonth,
			String ecop_dep_company) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sqlstr1 = "";
		String ys_fee = "";
		String ys_dis_fee = "";
		String ecp_state = "0";
		String ecp_state2 = "0";
		String str = "";
		String str1 = "";
		String sys_ownmonth = "";
		String sys_dep_company = "";
		if (!ownmonth.equals("")) {
			str = " and ownmonth=" + ownmonth;
		}
		if (!ecop_dep_company.equals("")) {
			str1 = " and coab_name='" + ecop_dep_company + "'";
		}
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select ownmonth,SUM(ecpu_total),0,0-SUM(ecpu_total),coab_name,cabc_id from EmCommissionOutPayUpdateOver a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id where 1=1 "
				+ str + str1 + "  group by ownmonth,coab_name,cabc_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				sqlstr1 = "select SUM(ecod_eofd_month_sum),SUM(ecod_eofd_month_sum)-"
						+ rs.getString(2)
						+ " from EmCommissionOutPayOver a left join EmCommissionOutPayDetailOver b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecop_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id where a.ownmonth='"
						+ rs.getString(1)
						+ "' and coab_name='"
						+ rs.getString(5) + "'";

				rs1 = db.GRS(sqlstr1);
				while (rs1.next()) {
					ys_fee = rs1.getString(1);
					ys_dis_fee = rs1.getString(2);
				}

				String sqlstr2 = "select COUNT(*),case when count(*)=0 then 0 else COUNT(*)/SUM(ecpu_state) end from EmCommissionOutPayUpdateOver a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id where coab_name='"
						+ rs.getString(5)
						+ "' and ownmonth="
						+ rs.getString(1)
						+ " and ecpu_state<3";
				System.out.println(sqlstr2);
				rs2 = db.GRS(sqlstr2);

				while (rs2.next()) {
					ecp_state = rs2.getString(1);
					ecp_state2 = rs2.getString(2);
				}
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_gs_yf(rs.getString(6));
				model.setEcop_sf_fee(rs.getString(2));
				model.setEcop_yf_fee(ys_fee);
				model.setEcop_yfsf_dis_fee(ys_dis_fee);
				model.setEcop_dep_company(rs.getString(5));
				model.setEcop_state(ecp_state);
				model.setEcop_cabc_id(ecp_state2);

				list.add(model);

			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公司帐单月份明细
	public List<EmCommissionOutPayModel> getautpaycolist(String ownmonth,
			String ecop_dep_company) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sqlstr1 = "";
		String ys_fee = "";
		String ys_dis_fee = "";
		String ecp_state = "0";
		String str = "";
		String str1 = "";
		if (!ownmonth.equals("")) {
			str = " and ownmonth=" + ownmonth;
		}
		if (!ecop_dep_company.equals("")) {
			str1 = " and coab_name='" + ecop_dep_company + "'";
		}
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();

		String sqlstr = "select ownmonth,coab_name,cabc_id,SUM(ecod_eofd_month_sum),f.cid,coba_company"
				+ " from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecop_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id left join embase e on e.gid=a.gid left join cobase f on f.cid=e.cid where 1=1"
				+ str
				+ str1
				+ "  group by ownmonth,coab_name,cabc_id,coba_company,f.cid";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				sqlstr1 = "select ownmonth,SUM(ecpu_total),SUM(ecpu_total)-"
						+ rs.getString(4)
						+ ",coab_name,coba_company,d.cid from EmCommissionOutPayUpdate a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id  left join CoBase d on d.CID=a.cid where ownmonth="
						+ rs.getString("ownmonth") + " and cabc_id="
						+ rs.getString("cabc_id") + " and a.cid="
						+ rs.getString("cid")
						+ "  group by ownmonth,coab_name,coba_company,d.cid";

				rs1 = db.GRS(sqlstr1);
				while (rs1.next()) {
					ys_fee = rs1.getString(2);
					ys_dis_fee = rs1.getString(3);
				}

				String sqlstr2 = "select COUNT(*) from EmCommissionOutPayUpdate a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id left join cobase d on d.cid=a.cid where coab_name='"
						+ rs.getString(5)
						+ "' and ownmonth="
						+ rs.getString(1)
						+ " and ecpu_state<2 and coba_company='"
						+ rs.getString(6) + "'";

				rs2 = db.GRS(sqlstr2);

				while (rs2.next()) {
					ecp_state = rs2.getString(1);
				}
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_sf_fee(ys_fee);
				model.setEcop_yf_fee(rs.getString(4));
				model.setEcop_yfsf_dis_fee(ys_dis_fee);
				model.setEcop_dep_company(rs.getString(2));
				model.setEcop_state(ecp_state);
				model.setEcop_company(rs.getString(6));
				model.setCid(rs.getString(5));
				model.setEcop_cabc_id(rs.getString("cabc_id"));
				list.add(model);

			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公司帐单月份明细
	public List<EmCommissionOutPayModel> getautpaycolist1(String ownmonth,
			String cabc_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		String str1 = "";
		if (!ownmonth.equals("")) {
			str = " and a.ownmonth=" + ownmonth;
		}
		if (!cabc_id.equals("")) {
			str1 = " and a.cabc_id='" + cabc_id + "'";
		}
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();

		String sqlstr = "select a.ownmonth,a.coab_name,a.coba_shortname,isnull(yf_total,0),isnull(sf_total,0),isnull(sf_total,0)-isnull(yf_total,0),isnull(ov_st,0)-pa_st ov_st,a.cabc_id,a.cid from (select ownmonth,coab_name,coba_shortname,f.cid,SUM(ecod_eofd_month_sum) yf_total,cabc_id,COUNT(distinct ecop_id) pa_st from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecop_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id left join embase f on f.gid=a.gid left join cobase g on g.cid=f.cid group by ownmonth,coab_name,coba_shortname,f.cid,cabc_id) a left join (select ecpu_cabc_id,ownmonth,SUM(ecpu_total) sf_total,c.CID from EmCommissionOutPayUpdate a left join embase b on b.gid=a.gid left join cobase c on c.CID=b.cid group by ecpu_cabc_id,ownmonth,coba_shortname,c.CID) b on b.ecpu_cabc_id=a.cabc_id and b.ownmonth=a.ownmonth and b.cid=a.cid left join (select ecop_cabc_id,ownmonth,COUNT(*) ov_st,c.CID from EmCommissionOutPay a left join embase b on b.gid=a.gid left join cobase c on c.CID=b.cid where ecop_state=2 group by ecop_cabc_id,ownmonth,coba_shortname,c.CID) c on c.ecop_cabc_id=a.cabc_id and c.ownmonth=a.ownmonth and c.CID=a.cid where 1=1 "
				+ str + str1;

		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_sf_fee(rs.getString(5));
				model.setEcop_yf_fee(rs.getString(4));
				model.setEcop_yfsf_dis_fee(rs.getString(6));
				model.setEcop_dep_company(rs.getString(2));
				model.setEcop_state(rs.getString("ov_st"));
				model.setEcop_company(rs.getString(3));
				model.setCid(rs.getString("cid"));
				model.setEcop_cabc_id(rs.getString("cabc_id"));
				list.add(model);

			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getyfemmonthpaylist(String city,
			String depcompany) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		String str1 = "";
		if (!city.equals("")) {
			str = " and coab_city='" + city + "'";
		}
		if (!depcompany.equals("")) {
			str1 = " and coab_name='" + depcompany + "'";
		}
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select a.ownmonth,coab_name,SUM(ecod_eofd_month_sum) from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecop_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id where 1=1"
				+ str + str1 + " group by ownmonth,coab_name";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_yf_fee(rs.getString(3));
				model.setEcop_dep_company(rs.getString(2));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getembasepaylist(String ownmonth,
			String ecou_dep_company, String company, String st_p)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rsfl = null;
		String sqlstr = "";
		String sqlstr1 = "";
		String sqlfl = "";
		double yl_di = 0;
		double jl_di = 0;
		double gs_di = 0;
		double sye_di = 0;
		double syu_di = 0;
		double house_di = 0;
		double file_di = 0;
		double fw_di = 0;
		double other_di = 0;
		double di_total = 0;
		BigDecimal fl_sffee = new BigDecimal(0);
		BigDecimal yl_sf = new BigDecimal(0);
		BigDecimal yl_yf = new BigDecimal(0);
		BigDecimal jl_sf = new BigDecimal(0);
		BigDecimal jl_yf = new BigDecimal(0);
		BigDecimal sye_sf = new BigDecimal(0);
		BigDecimal sye_yf = new BigDecimal(0);
		BigDecimal syu_sf = new BigDecimal(0);
		BigDecimal syu_yf = new BigDecimal(0);
		BigDecimal gs_sf = new BigDecimal(0);
		BigDecimal gs_yf = new BigDecimal(0);
		BigDecimal house_sf = new BigDecimal(0);
		BigDecimal house_yf = new BigDecimal(0);
		BigDecimal file_sf = new BigDecimal(0);
		BigDecimal file_yf = new BigDecimal(0);
		BigDecimal fw_sf = new BigDecimal(0);
		BigDecimal fw_yf = new BigDecimal(0);
		BigDecimal other_sf = new BigDecimal(0);
		BigDecimal other_yf = new BigDecimal(0);
		BigDecimal total_sf = new BigDecimal(0);
		BigDecimal total_yf = new BigDecimal(0);

		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		sqlstr = "select ownmonth,ecpu_company,ecpu_name,sum(case epfd_name when '养老保险' then epfd_month_sum else 0 end) as ecop_yl_sf,sum(case epfd_name when '医疗保险' then epfd_month_sum else 0 end) as ecop_jl_sf,sum(case epfd_name when '工伤保险' then epfd_month_sum else 0 end) as ecop_gs_sf,sum(case epfd_name when '失业保险' then epfd_month_sum else 0 end) as ecop_sye_sf,sum(case epfd_name when '生育保险' then epfd_month_sum else 0 end) as ecop_syu_sf,sum(case epfd_name when '住房公积金' then epfd_month_sum else 0 end) as ecop_house_sf,sum(case epfd_name when '档案费' then epfd_month_sum else 0 end) as ecop_file_sf,sum(case epfd_name when '服务费' then epfd_month_sum else 0 end) as ecop_fw_sf,sum(case when epfd_name<>'养老保险' and epfd_name<>'工伤保险' and epfd_name<>'医疗保险' and epfd_name<>'生育保险' and epfd_name<>'失业保险' and epfd_name<>'住房公积金' and epfd_name<>'档案费' and epfd_name<>'服务费' then epfd_month_sum else 0 end) as ecop_other_sf,a.gid,sum(ecpu_state)/COUNT(*) ecpu_state,cid,ecpu_cabc_id,ecpu_id,case when sum(e.change)>0 then '有未确认' else '已确认' end ch_st from EmCommissionOutPayUpdate a left join EmCommissionOutPayUpdateFeeDetail b on b.epfd_ecpu_id=a.ecpu_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecpu_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id left join (select count(*) change,gid from EmCommissionOutChange where ecoc_state in (2,3) group by gid) e on e.gid=a.gid where coab_name='"
				+ ecou_dep_company
				+ "' and ownmonth='"
				+ ownmonth
				+ "' and ecpu_company='"
				+ company
				+ "' group by a.gid,cid,ecpu_company,ecpu_name,ownmonth,ecpu_cabc_id,ecpu_id";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				sqlfl = " select SUM(ecpu_welfare_total) from EmCommissionOutPayUpdate where gid= "
						+ rs.getString("gid") + " and ownmonth=" + ownmonth;
				rsfl = db.GRS(sqlfl);
				try {
					while (rsfl.next()) {
						fl_sffee = rsfl.getBigDecimal(1);
					}
				} catch (Exception e) {
					System.out.print(e.toString());
					fl_sffee = new BigDecimal(0);
				}
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEcop_cabc_id(rs.getString("ecpu_cabc_id"));
				model.setEcop_ch_st(rs.getString("ch_st"));
				model.setEcop_company(rs.getString(2));
				model.setEcop_name(rs.getString(3));
				model.setEcop_yl_sf(rs.getString(4));
				model.setEcop_jl_sf(rs.getString(5));
				model.setEcop_gs_sf(rs.getString(6));
				model.setEcop_sye_sf(rs.getString(7));
				model.setEcop_syu_sf(rs.getString(8));
				model.setEcop_house_sf(rs.getString(9));
				model.setEcop_file_sf(rs.getString(10));
				model.setEcop_fw_sf(rs.getString(11));
				model.setEcop_other_sf(rs.getString(12) + fl_sffee);
				model.setEcop_id(rs.getString("ecpu_id"));
				model.setEcop_state(rs.getString("ecpu_state"));
				yl_sf = rs.getBigDecimal(4);
				jl_sf = rs.getBigDecimal(5);
				gs_sf = rs.getBigDecimal(6);
				sye_sf = rs.getBigDecimal(7);
				syu_sf = rs.getBigDecimal(8);
				house_sf = rs.getBigDecimal(9);
				file_sf = rs.getBigDecimal(10);
				fw_sf = rs.getBigDecimal(11);
				other_sf = rs.getBigDecimal(12);
				total_sf = yl_sf
						.add(jl_sf.add(gs_sf.add(sye_sf.add(syu_sf.add(house_sf
								.add(file_sf.add(fw_sf.add(other_sf))))))));

				sqlstr1 = "select isnull(sum(case ecod_eofd_name when '养老保险' then ecod_eofd_month_sum else 0 end),0) as ecop_yl_yf,isnull(sum(case ecod_eofd_name when '医疗保险' then ecod_eofd_month_sum else 0 end),0) as ecop_jl_yf,isnull(sum(case ecod_eofd_name when '工伤保险' then ecod_eofd_month_sum else 0 end),0) as ecop_gs_yf,isnull(sum(case ecod_eofd_name when '失业保险' then ecod_eofd_month_sum else 0 end),0) as ecop_sye_yf,isnull(sum(case ecod_eofd_name when '生育保险' then ecod_eofd_month_sum else 0 end),0) as ecop_syu_yf,isnull(sum(case ecod_eofd_name when '住房公积金' then ecod_eofd_month_sum else 0 end),0) as ecop_house_yf,isnull(sum(case ecod_eofd_name when '档案费' then ecod_eofd_month_sum else 0 end),0) as ecop_file_yf,isnull(sum(case ecod_eofd_name when '服务费' then ecod_eofd_month_sum else 0 end),0) as ecop_fw_yf,isnull(sum(case when ecod_eofd_name<>'养老保险' and ecod_eofd_name<>'工伤保险' and ecod_eofd_name<>'医疗保险' and ecod_eofd_name<>'生育保险' and ecod_eofd_name<>'失业保险' and ecod_eofd_name<>'住房公积金' and ecod_eofd_name<>'档案费' and ecod_eofd_name<>'服务费' then ecod_eofd_month_sum else 0 end),0) as ecop_other_yf,ecop_id from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id where a.ownmonth='"
						+ ownmonth
						+ "' and a.gid="
						+ rs.getString("gid")
						+ " group by ecop_id";
				rs1 = db.GRS(sqlstr1);
				model.setEcop_yl_yf("0");
				model.setEcop_jl_yf("0");
				model.setEcop_gs_yf("0");
				model.setEcop_sye_yf("0");
				model.setEcop_syu_yf("0");
				model.setEcop_house_yf("0");
				model.setEcop_file_yf("0");
				model.setEcop_fw_yf("0");
				model.setEcop_other_yf("0");

				try {

					yl_di = yl_sf.doubleValue();
					jl_di = jl_sf.doubleValue();
					gs_di = gs_sf.doubleValue();
					sye_di = sye_sf.doubleValue();
					syu_di = syu_sf.doubleValue();
					house_di = house_sf.doubleValue();
					file_di = file_sf.doubleValue();
					fw_di = fw_sf.doubleValue();
					other_di = other_sf.doubleValue();
					di_total = total_sf.doubleValue();

					model.setEcop_yl_di(Double.toString(yl_di));
					model.setEcop_jl_di(Double.toString(jl_di));
					model.setEcop_gs_di(Double.toString(gs_di));
					model.setEcop_sye_di(Double.toString(sye_di));
					model.setEcop_syu_di(Double.toString(syu_di));
					model.setEcop_house_di(Double.toString(house_di));
					model.setEcop_file_di(Double.toString(file_di));
					model.setEcop_fw_di(Double.toString(fw_di));
					model.setEcop_other_di(Double.toString(other_di));
					model.setEcop_di_total(Double.toString(di_total));

					while (rs1.next()) {
						sqlfl = "select SUM(ecpp_Fee) from EmCommissionOutPayProduct where ecpp_ecop_id="
								+ rs1.getString("ecop_id");
						rsfl = db.GRS(sqlfl);
						try {
							while (rsfl.next()) {
								fl_sffee = rsfl.getBigDecimal(1);
							}
						} catch (Exception e) {
							System.out.print(e.toString());
							fl_sffee = new BigDecimal(0);
						}
						model.setEcop_yl_yf(rs1.getString(1));
						model.setEcop_jl_yf(rs1.getString(2));
						model.setEcop_gs_yf(rs1.getString(3));
						model.setEcop_sye_yf(rs1.getString(4));
						model.setEcop_syu_yf(rs1.getString(5));
						model.setEcop_house_yf(rs1.getString(6));
						model.setEcop_file_yf(rs1.getString(7));
						model.setEcop_fw_yf(rs1.getString(8));
						model.setEcop_other_yf(rs1.getString(9) + fl_sffee);
						yl_yf = rs1.getBigDecimal(1);
						jl_yf = rs1.getBigDecimal(2);
						gs_yf = rs1.getBigDecimal(3);
						sye_yf = rs1.getBigDecimal(4);
						syu_yf = rs1.getBigDecimal(5);
						house_yf = rs1.getBigDecimal(6);
						file_yf = rs1.getBigDecimal(7);
						fw_yf = rs1.getBigDecimal(8);
						other_yf = rs1.getBigDecimal(9);
						total_yf = yl_yf.add(jl_yf.add(gs_yf.add(sye_yf
								.add(syu_yf.add(house_yf.add(file_yf.add(fw_yf
										.add(other_yf))))))));

						yl_di = yl_sf.subtract(yl_yf).doubleValue();
						jl_di = jl_sf.subtract(jl_yf).doubleValue();
						gs_di = gs_sf.subtract(gs_yf).doubleValue();
						sye_di = sye_sf.subtract(sye_yf).doubleValue();
						syu_di = syu_sf.subtract(syu_yf).doubleValue();
						house_di = house_sf.subtract(house_yf).doubleValue();
						file_di = file_sf.subtract(file_yf).doubleValue();
						fw_di = fw_sf.subtract(fw_yf).doubleValue();
						other_di = other_sf.subtract(other_yf).doubleValue();
						di_total = total_sf.subtract(total_yf).doubleValue();

						model.setEcop_yl_di(Double.toString(yl_di));
						model.setEcop_jl_di(Double.toString(jl_di));
						model.setEcop_gs_di(Double.toString(gs_di));
						model.setEcop_sye_di(Double.toString(sye_di));
						model.setEcop_syu_di(Double.toString(syu_di));
						model.setEcop_house_di(Double.toString(house_di));
						model.setEcop_file_di(Double.toString(file_di));
						model.setEcop_fw_di(Double.toString(fw_di));
						model.setEcop_other_di(Double.toString(other_di));
						model.setEcop_di_total(Double.toString(di_total));
					}
				} catch (Exception e) {
					System.out.print(e.toString());
				}

				if (st_p.equals("p1")
						&& Double.toString(di_total).equals("0.0")) {
					list.add(model);
				}
				if (st_p.equals("p2")
						&& !Double.toString(di_total).equals("0.0")) {
					list.add(model);
				}
				if (st_p.equals("p3")
						&& (Double.toString(di_total).equals(total_sf))) {
					list.add(model);
				}

				if (!st_p.equals("p1") && !st_p.equals("p2")
						&& !st_p.equals("p3")) {
					list.add(model);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		/*
		 * EmCommissionOutPayModel model = new EmCommissionOutPayModel(); sqlstr
		 * =
		 * "select ownmonth,ecpu_company,ecpu_name,sum(case epfd_name when '养老保险' then epfd_month_sum else 0 end) as ecop_yl_sf,sum(case epfd_name when '医疗保险' then epfd_month_sum else 0 end) as ecop_jl_sf,sum(case epfd_name when '工伤保险' then epfd_month_sum else 0 end) as ecop_gs_sf,sum(case epfd_name when '失业保险' then epfd_month_sum else 0 end) as ecop_sye_sf,sum(case epfd_name when '生育保险' then epfd_month_sum else 0 end) as ecop_syu_sf,sum(case epfd_name when '住房公积金' then epfd_month_sum else 0 end) as ecop_house_sf,sum(case epfd_name when '档案费' then epfd_month_sum else 0 end) as ecop_file_sf,sum(case epfd_name when '服务费' then epfd_month_sum else 0 end) as ecop_fw_sf,sum(case when epfd_name<>'养老保险' and epfd_name<>'工伤保险' and epfd_name<>'医疗保险' and epfd_name<>'生育保险' and epfd_name<>'失业保险' and epfd_name<>'住房公积金' and epfd_name<>'档案费' and epfd_name<>'服务费' then epfd_month_sum else 0 end) as ecop_other_sf,gid,ecpu_id,sum(ecpu_state)/COUNT(*) ecpu_state,cid,ecpu_cabc_id from EmCommissionOutPay a left join EmCommissionOutPayFeeDetail b on b.epfd_ecpu_id=a.ecpu_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecpu_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id where coab_name='"
		 * + ecou_dep_company + "' and ownmonth='" + ownmonth +
		 * "' and ecpu_company='" + company +
		 * "' group by gid,cid,ecpu_company,ecpu_name,ownmonth,ecpu_id,ecpu_cabc_id"
		 * ; try { rs = db.GRS(sqlstr); while (rs.next()) {
		 * model.setOwnmonth("0"); model.setGid("0"); model.setCid("0");
		 * model.setEcop_cabc_id("0"); model.setEcop_company("0");
		 * model.setEcop_name("0"); // di_total=0.0; total_sf = 0.0;
		 * 
		 * if (st_p.equals("p1") && Double.toString(di_total).equals("0.0")) {
		 * list.add(model); } if (st_p.equals("p2") &&
		 * !Double.toString(di_total).equals("0.0") &&
		 * !Double.toString(total_yf).equals("0.0") &&
		 * !Double.toString(total_sf).equals("0.0")) { list.add(model); } if
		 * (st_p.equals("p3") && (Double.toString(total_yf).equals("0.0") ||
		 * Double .toString(total_sf).equals("0.0"))) { list.add(model); }
		 * 
		 * if (!st_p.equals("p1") && !st_p.equals("p2") && !st_p.equals("p3")) {
		 * list.add(model); } } } catch (Exception e) {
		 * System.out.print(e.toString()); } finally { db.Close(); }
		 */

		return list;
	}

	private BigDecimal BigDecimal(double doubleValue) {
		// TODO Auto-generated method stub
		return null;
	}

	// 显示系统应付帐单月份明细
	public List<EmCommissionOutPayModel> getyfembasepaylist(String ownmonth,
			String ecou_dep_company) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rsfl = null;
		String sqlstr = "";
		String sqlfl = "";
		Float fl_sffee = null;
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		sqlstr = "select a.ownmonth,coba_shortname,emba_name,sum(case ecod_eofd_name when '养老保险' then ecod_eofd_month_sum else 0 end) as ecop_yl_yf,sum(case ecod_eofd_name when '医疗保险' then ecod_eofd_month_sum else 0 end) as ecop_jl_yf,sum(case ecod_eofd_name when '工伤保险' then ecod_eofd_month_sum else 0 end) as ecop_gs_yf,sum(case ecod_eofd_name when '失业保险' then ecod_eofd_month_sum else 0 end) as ecop_sye_yf,sum(case ecod_eofd_name when '生育保险' then ecod_eofd_month_sum else 0 end) as ecop_syu_yf,sum(case ecod_eofd_name when '住房公积金' then ecod_eofd_month_sum else 0 end) as ecop_house_yf,sum(case ecod_eofd_name when '档案费' then ecod_eofd_month_sum else 0 end) as ecop_file_yf,sum(case ecod_eofd_name when '服务费' then ecod_eofd_month_sum else 0 end) as ecop_fw_yf,sum(case when ecod_eofd_name<>'养老保险' and ecod_eofd_name<>'工伤保险' and ecod_eofd_name<>'医疗保险' and ecod_eofd_name<>'生育保险' and ecod_eofd_name<>'失业保险' and ecod_eofd_name<>'住房公积金' and ecod_eofd_name<>'档案费' and ecod_eofd_name<>'服务费' then ecod_eofd_month_sum else 0 end) as ecop_other_yf,ecop_id,SUM(ecod_eofd_month_sum) total from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join CoAgencyBaseCityRel c on c.cabc_id=a.ecop_cabc_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id left join embase e on e.gid=a.gid left join CoBase f on f.CID=e.cid where ownmonth="
				+ ownmonth
				+ " and coab_name='"
				+ ecou_dep_company
				+ "'  group by a.ownmonth,coba_shortname,emba_name,ecop_id";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				sqlfl = " select SUM(ecop_ecou_welfare_sum) from EmCommissionOutPay where ecop_id= "
						+ rs.getString("ecop_id");
				rsfl = db.GRS(sqlfl);
				try {
					while (rsfl.next()) {
						fl_sffee = rsfl.getFloat(1);
					}
				} catch (Exception e) {
					System.out.print(e.toString());
					fl_sffee = (float) 0;
				}
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(1));
				model.setEcop_company(rs.getString(2));
				model.setEcop_name(rs.getString(3));
				model.setEcop_yl_yf(rs.getString(4));
				model.setEcop_jl_yf(rs.getString(5));
				model.setEcop_gs_yf(rs.getString(6));
				model.setEcop_sye_yf(rs.getString(7));
				model.setEcop_syu_yf(rs.getString(8));
				model.setEcop_house_yf(rs.getString(9));
				model.setEcop_file_yf(rs.getString(10));
				model.setEcop_fw_yf(rs.getString(11));
				model.setEcop_paytotal(rs.getString("total"));
				model.setEcop_other_yf(rs.getString(12) + fl_sffee);
				model.setEcop_id(rs.getString("ecop_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getembasepaylistin(String gid,
			String ownmonth, String cabc_id) throws Exception {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sqlstr = "";
		String sqlstr1 = "";
		String sqlstr2 = "";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		sqlstr = "select ownmonth,ecpu_company,ecpu_name,isnull(ecpu_yl_co_total,0)+isnull(ecpu_yl_em_total,0) as ecop_yl_sf,isnull(ecpu_yil_em_total,0)+isnull(ecpu_yil_co_total,0) as ecop_jl_sf,isnull(ecpu_gsh_em_total,0)+isnull(ecpu_gsh_co_total,0) as ecop_gs_sf,isnull(ecpu_shy_em_total,0)+isnull(ecpu_shy_co_total,0) as ecop_sye_sf,isnull(ecpu_shyu_em_total,0)+isnull(ecpu_shyu_co_total,0) as ecop_syu_sf,isnull(ecpu_gjj_em_total,0)+isnull(ecpu_gjj_co_total,0)+isnull(ecpu_bchgjj_co_total,0)+isnull(ecpu_bchgjj_em_total,0) as ecop_house_sf,isnull(file_fee,0) as ecop_file_sf,isnull(fw_fee,0) as ecop_fw_sf,isnull(ecpu_other_total,0)+isnull(ecpu_welfare_total,0) as ecop_other_sf from EmCommissionOutPayUpdate a left join (select epfd_ecpu_id,SUM(case when epfd_name='档案费' then epfd_month_sum else 0 end) file_fee,SUM(case when epfd_name='服务费' then epfd_month_sum else 0 end) fw_fee from EmCommissionOutPayUpdateFeeDetail group by epfd_ecpu_id) b on b.epfd_ecpu_id=a.ecpu_id where gid="
				+ gid
				+ " and ownmonth="
				+ ownmonth
				+ " and ecpu_cabc_id="
				+ cabc_id;

		try {
			EmCommissionOutPayModel model = new EmCommissionOutPayModel();
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				model.setEcop_company("外地帐单");
				model.setEcop_yl_sf(rs.getString(4));
				model.setEcop_jl_sf(rs.getString(5));
				model.setEcop_gs_sf(rs.getString(6));
				model.setEcop_sye_sf(rs.getString(7));
				model.setEcop_syu_sf(rs.getString(8));
				model.setEcop_house_sf(rs.getString(9));
				model.setEcop_file_sf(rs.getString(10));
				model.setEcop_fw_sf(rs.getString(11));
				model.setEcop_other_sf(rs.getString(12));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		sqlstr1 = "select isnull(sum(case ecod_eofd_name when '养老保险' then ecod_eofd_month_sum else 0 end),0) as ecop_yl_yf,isnull(sum(case ecod_eofd_name when '医疗保险' then ecod_eofd_month_sum else 0 end),0) as ecop_jl_yf,isnull(sum(case ecod_eofd_name when '工伤保险' then ecod_eofd_month_sum else 0 end),0) as ecop_gs_yf,isnull(sum(case ecod_eofd_name when '失业保险' then ecod_eofd_month_sum else 0 end),0) as ecop_sye_yf,isnull(sum(case ecod_eofd_name when '生育保险' then ecod_eofd_month_sum else 0 end),0) as ecop_syu_yf,isnull(sum(case ecod_eofd_name when '住房公积金' then ecod_eofd_month_sum else 0 end),0) as ecop_house_yf,isnull(sum(case ecod_eofd_name when '档案费' then ecod_eofd_month_sum else 0 end),0) as ecop_file_yf,isnull(sum(case ecod_eofd_name when '服务费' then ecod_eofd_month_sum else 0 end),0) as ecop_fw_yf,isnull(sum(case when ecod_eofd_name<>'养老保险' and ecod_eofd_name<>'工伤保险' and ecod_eofd_name<>'医疗保险' and ecod_eofd_name<>'生育保险' and ecod_eofd_name<>'失业保险' and ecod_eofd_name<>'住房公积金' and ecod_eofd_name<>'补充公积金' and ecod_eofd_name<>'档案费' and ecod_eofd_name<>'服务费' then ecod_eofd_month_sum else 0 end),0) as ecop_other_yf from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id where a.ownmonth="
				+ ownmonth
				+ " and a.gid="
				+ gid
				+ " and ecop_cabc_id="
				+ cabc_id;
		rs1 = db.GRS(sqlstr1);

		System.out.println(sqlstr1);
		try {
			EmCommissionOutPayModel model = new EmCommissionOutPayModel();
			while (rs1.next()) {
				model.setEcop_company("系统帐单");
				model.setEcop_yl_sf(rs1.getString(1));
				model.setEcop_jl_sf(rs1.getString(2));
				model.setEcop_gs_sf(rs1.getString(3));
				model.setEcop_sye_sf(rs1.getString(4));
				model.setEcop_syu_sf(rs1.getString(5));
				model.setEcop_house_sf(rs1.getString(6));
				model.setEcop_file_sf(rs1.getString(7));
				model.setEcop_fw_sf(rs1.getString(8));
				model.setEcop_other_sf(rs1.getString(9));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		
		sqlstr2 = "select isnull(sum(case ecod_eofd_name when '养老保险' then ecod_eofd_month_sum else 0 end),0) as ecop_yl_yf,isnull(sum(case ecod_eofd_name when '医疗保险' then ecod_eofd_month_sum else 0 end),0) as ecop_jl_yf,isnull(sum(case ecod_eofd_name when '工伤保险' then ecod_eofd_month_sum else 0 end),0) as ecop_gs_yf,isnull(sum(case ecod_eofd_name when '失业保险' then ecod_eofd_month_sum else 0 end),0) as ecop_sye_yf,isnull(sum(case ecod_eofd_name when '生育保险' then ecod_eofd_month_sum else 0 end),0) as ecop_syu_yf,isnull(sum(case ecod_eofd_name when '住房公积金' then ecod_eofd_month_sum else 0 end),0) as ecop_house_yf,isnull(sum(case ecod_eofd_name when '档案费' then ecod_eofd_month_sum else 0 end),0) as ecop_file_yf,isnull(sum(case ecod_eofd_name when '服务费' then ecod_eofd_month_sum else 0 end),0) as ecop_fw_yf,isnull(sum(case when ecod_eofd_name<>'养老保险' and ecod_eofd_name<>'工伤保险' and ecod_eofd_name<>'医疗保险' and ecod_eofd_name<>'生育保险' and ecod_eofd_name<>'失业保险' and ecod_eofd_name<>'住房公积金' and ecod_eofd_name<>'档案费' and ecod_eofd_name<>'服务费' then ecod_eofd_month_sum else 0 end),0) as ecop_other_yf from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id where a.ownmonth="
				+ ownmonth
				+ " and a.gid="
				+ gid
				+ " and ecop_cabc_id="
				+ cabc_id;
		rs2= db.GRS(sqlstr2);

		System.out.println(sqlstr2);
		try {
			EmCommissionOutPayModel model = new EmCommissionOutPayModel();
			while (rs1.next()) {
				model.setEcop_company("特殊费用");
				model.setEcop_yl_sf(rs1.getString(1));
				model.setEcop_jl_sf(rs1.getString(2));
				model.setEcop_gs_sf(rs1.getString(3));
				model.setEcop_sye_sf(rs1.getString(4));
				model.setEcop_syu_sf(rs1.getString(5));
				model.setEcop_house_sf(rs1.getString(6));
				model.setEcop_file_sf(rs1.getString(7));
				model.setEcop_fw_sf(rs1.getString(8));
				model.setEcop_other_sf(rs1.getString(9));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		
		return list;
	}

	// 显示委托城市
	public List<EmCommissionOutPayModel> getcitylist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select distinct coab_city from EmCommissionOutPayUpdate a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecpu_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setEcop_company(rs.getString(1));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示应付委托城市
	public List<EmCommissionOutPayModel> getyfcitylist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select distinct city from EmCommissionOutPay a left join CoAgencyBaseCityRel_view b on b.cabc_id=a.ecop_cabc_id order by city";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setEcop_company(rs.getString(1));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示委托机构
	public List<EmCommissionOutPayModel> getdepcompanylist(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select distinct coab_name from EmCommissionOutPay a left join CoAgencyBaseCityRel_view b on b.cabc_id=a.ecop_cabc_id where 1=1 "+str+" order by coab_name";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setEcop_dep_company(rs.getString(1));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示委托总费用
	public List<EmCommissionOutPayModel> getwt_sftotal(String cabc_id,
			String ownmonth) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String ecp_state = "0";
		String ecp_total = "0";
		String ecp_dep_company = "";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select COUNT(*) from EmCommissionOutPayUpdate where ecpu_cabc_id="
				+ cabc_id + " and ownmonth=" + ownmonth + " and ecpu_state!=2";
		try {
			rs = db.GRS(sqlstr);

			while (rs.next()) {
				ecp_state = rs.getString(1);
			}

			String sqlstr1 = "select SUM(ecpu_total) from EmCommissionOutPayUpdate where ecpu_cabc_id="
					+ cabc_id
					+ " and ownmonth="
					+ ownmonth
					+ " and ecpu_state=2";

			rs1 = db.GRS(sqlstr1);

			while (rs1.next()) {
				ecp_total = rs1.getString(1);
			}

			String sqlstr2 = "select coab_name from View_wtservicestandard where cabc_id="
					+ cabc_id;

			rs2 = db.GRS(sqlstr2);
			while (rs2.next()) {
				ecp_dep_company = rs2.getString(1);
			}

			EmCommissionOutPayModel model = new EmCommissionOutPayModel();
			model.setEcop_state(ecp_state);
			model.setEcop_paytotal(ecp_total);
			model.setEcop_company(ecp_dep_company);
			list.add(model);
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示委托帐单明细
	public List<EmCommissionOutPayModel> getwt_paylist(String cabc_id,
			String ownmonth, String filename) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String ecp_state = "0";
		String ecp_total = "0";
		String ecp_dep_company = "";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select * from EmCommissionOutPayUpdate where ecpu_cabc_id="
				+ cabc_id + " and ownmonth=" + ownmonth;

		try {

			String sqlstr2 = "select coab_name from View_wtservicestandard where cabc_id="
					+ cabc_id;

			rs2 = db.GRS(sqlstr2);
			while (rs2.next()) {
				ecp_dep_company = rs2.getString(1);
			}

			EmCommissionOutPayModel model = new EmCommissionOutPayModel();

			rs = db.GRS(sqlstr);

			while (rs.next()) {
				payBll.add_pay(rs.getString("gid"), rs.getString("cid"),
						filename, rs.getString("ownmonth"), ecp_dep_company,
						rs.getString("ecpu_total"), rs.getString("ecpu_id"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示委托机构
	public List<EmCommissionOutPayModel> getyfdepcompanylist(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select distinct coab_name from EmCommissionOutPay a left join CoAgencyBaseCityRel b on b.cabc_id=a.ecop_cabc_id left join CoAgencyBase c on c.coab_id=b.cabc_coab_id where 1=1 "
				+ str;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setEcop_dep_company(rs.getString(1));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 委托确认
	public int wt_ok(String chk) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOutPayOK_P_zzq(?,?)");
			c.setString(1, chk);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托确认
	public int wtdate_ok(String gid, String yl_cp, String yl_op, String jl_cp,
			String jl_op, String gs_cp, String gs_op, String syu_cp,
			String syu_op, String sye_cp, String sye_op, String house_cp,
			String house_op, String fw, String file, String other) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOutPayDateOK_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, gid);
			c.setString(2, yl_cp);
			c.setString(3, yl_op);
			c.setString(4, jl_cp);
			c.setString(5, jl_op);
			c.setString(6, gs_cp);
			c.setString(7, gs_op);
			c.setString(8, syu_cp);
			c.setString(9, syu_op);
			c.setString(10, sye_cp);
			c.setString(11, sye_op);
			c.setString(12, house_cp);
			c.setString(13, house_op);
			c.setString(14, fw);
			c.setString(15, file);
			c.setString(16, other);
			c.registerOutParameter(17, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(17);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托帐单确认
	public int getwt_paypass(String cabc_id, String ownmonth) {
		try {
			System.out.println(cabc_id);
			System.out.println(ownmonth);
			CallableStatement c = conn
					.getcall("EmCommissionOutPayPass_P_zzq(?,?,?)");
			c.setString(1, cabc_id);
			c.setString(2, ownmonth);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托帐单确认
	public int getwt_sfpaylist(String ownmonth, String dep_company) {
		try {
			System.out.println(ownmonth);
			System.out.println(dep_company);
			CallableStatement c = conn
					.getcall("EmCommissionOutPayOver_p_zzq(?,?,?)");
			c.setString(1, dep_company);
			c.setString(2, ownmonth);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托公司确认
	public int wtco_ok(String ownmonth, String cid,String cabc_id) {
		try {
			System.out.println("aaaa");
			System.out.println(ownmonth);
			System.out.println( cid);
			System.out.println(cabc_id);
			CallableStatement c = conn
					.getcall("EmCommissionOutPayCoOK_P_zzq(?,?,?,?)");
			c.setString(1, ownmonth);
			c.setString(2, cid);
			c.setString(3, cabc_id);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托二次确认
	public int pass_ok(String chk, int pass_state, String ylcp, String ylop,
			String jlcp, String jlop, String djlcp, String djlop, String syucp,
			String syuop, String gscp, String gsop, String syecp, String syeop,
			String housecp, String houseop, String fwcp, String fwop,
			String filecp, String fileop, String flcp, String flop) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOutPayPassOK_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, chk);
			c.setInt(2, pass_state);
			c.setString(3, ylcp);
			c.setString(4, ylop);
			c.setString(5, jlcp);
			c.setString(6, jlop);
			c.setString(7, djlcp);
			c.setString(8, djlop);
			c.setString(9, syucp);
			c.setString(10, syuop);
			c.setString(11, gscp);
			c.setString(12, gsop);
			c.setString(13, syecp);
			c.setString(14, syeop);
			c.setString(15, housecp);
			c.setString(16, houseop);
			c.setString(17, fwcp);
			c.setString(18, fwop);
			c.setString(19, filecp);
			c.setString(20, fileop);
			c.setString(21, flcp);
			c.setString(22, flop);
			c.registerOutParameter(23, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(23);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托确认
	public int wt_up(String chk, String company, String name, String yl,
			String sye, String jl, String gs, String syu, String house,
			String other, String file, String fw, String total, String gid,
			String cid, String cabc_id, String ownmonth) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOutPayUPM_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, chk);
			c.setString(2, company);
			c.setString(3, name);
			c.setString(4, yl);
			c.setString(5, sye);
			c.setString(6, jl);
			c.setString(7, gs);
			c.setString(8, syu);
			c.setString(9, house);
			c.setString(10, other);
			c.setString(11, file);
			c.setString(12, fw);
			c.setString(13, total);
			c.setString(14, gid);
			c.setString(15, cid);
			c.setString(16, cabc_id);
			c.setString(17, ownmonth);
			c.registerOutParameter(18, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(18);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 委托确认
	public int wt_allup(String chk, String company, String name, String yl,
			String sye, String jl, String gs, String syu, String house,
			String other, String file, String fw, String total, String gid,
			String cid, String cabc_id, String ownmonth) {
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOutPayUPWT_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, chk);
			c.setString(2, company);
			c.setString(3, name);
			c.setString(4, yl);
			c.setString(5, sye);
			c.setString(6, jl);
			c.setString(7, gs);
			c.setString(8, syu);
			c.setString(9, house);
			c.setString(10, other);
			c.setString(11, file);
			c.setString(12, fw);
			c.setString(13, total);
			c.setString(14, gid);
			c.setString(15, cid);
			c.setString(16, cabc_id);
			c.setString(17, ownmonth);
			c.registerOutParameter(18, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(18);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 费用添加
	public int feeadd(String gid, String cid, String ownmonth,
			String cabc_id, String name, String yl,
			String jl, String syu, String gs, String sye, String house,
			String file, String fw, String other, String remark) {

		System.out.println(name);
		System.out.println(yl);
		System.out.println(sye);
		System.out.println(jl);
		System.out.println(gs);
		System.out.println(syu);
		System.out.println(house);
		System.out.println(other);
		System.out.println(file);
		System.out.println(fw);
		System.out.println(gid);
		System.out.println(cid);
		System.out.println(ownmonth);
		System.out.println(cabc_id);
		try {
			CallableStatement c = conn
					.getcall("EmCommissionOutPaySFeeAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, name);
			c.setString(2, yl);
			c.setString(3, sye);
			c.setString(4, jl);
			c.setString(5, gs);
			c.setString(6, syu);
			c.setString(7, house);
			c.setString(8, other);
			c.setString(9, file);
			c.setString(10, fw);
			c.setString(11, gid);
			c.setString(12, cid);
			c.setString(13, ownmonth);
			c.setString(14, cabc_id);
			c.setString(15, remark);
			c.registerOutParameter(16, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(16);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getembasepaylist1(String ownmonth,
			String cabc_id, String cid, String st_p) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sqlstr = "";

		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		sqlstr = "select isnull(ch_st,0) ch_st,a.gid,a.ecop_cabc_id,a.emba_name,a.coba_company,a.cid,a.ownmonth,ISNULL(ecop_yl_sf,0)-ecop_yl_yf yl_di,ISNULL(ecop_jl_sf,0)-ecop_jl_yf jl_di,ISNULL(ecop_gs_sf,0)-ecop_gs_yf gs_di,ISNULL(ecop_sye_sf,0)-ecop_sye_yf sye_di,ISNULL(ecop_syu_sf,0)-ecop_syu_yf syu_di,ISNULL(ecop_house_sf,0)-ecop_house_yf house_di,isnull(ecop_file_sf,0)-ecop_file_yf file_di,isnull(ecop_fw_sf,0)-ecop_fw_yf fw_di,ISNULL(ecop_other_sf,0)-ecop_other_yf other_di,c.ecop_state,c.ecop_id from (select 1 st,a.gid,ecop_cabc_id,emba_name,coba_company,c.cid,ownmonth,sum(case ecod_eofd_name when '养老保险' then ecod_eofd_month_sum else 0 end) as ecop_yl_yf,sum(case ecod_eofd_name when '医疗保险' then ecod_eofd_month_sum else 0 end) as ecop_jl_yf,sum(case ecod_eofd_name when '工伤保险' then ecod_eofd_month_sum else 0 end) as ecop_gs_yf,sum(case ecod_eofd_name when '失业保险' then ecod_eofd_month_sum else 0 end) as ecop_sye_yf,sum(case ecod_eofd_name when '生育保险' then ecod_eofd_month_sum else 0 end) as ecop_syu_yf,sum(case ecod_eofd_name when '住房公积金' then ecod_eofd_month_sum else 0 end) as ecop_house_yf,sum(case ecod_eofd_name when '档案费' then ecod_eofd_month_sum else 0 end) as ecop_file_yf,sum(case ecod_eofd_name when '服务费' then ecod_eofd_month_sum else 0 end) as ecop_fw_yf,sum(case when ecod_eofd_name<>'养老保险' and ecod_eofd_name<>'工伤保险' and ecod_eofd_name<>'医疗保险' and ecod_eofd_name<>'生育保险' and ecod_eofd_name<>'失业保险' and ecod_eofd_name<>'住房公积金' and ecod_eofd_name<>'档案费' and ecod_eofd_name<>'服务费' then ecod_eofd_month_sum else 0 end) as ecop_other_yf from EmCommissionOutPay a left join EmCommissionOutPayDetail b on b.ecod_ecop_id=a.ecop_id left join embase c on c.gid=a.gid left join cobase d on d.CID=c.cid group by a.gid,ecop_cabc_id,emba_name,coba_company,c.cid,ownmonth) a left join (select a.gid,ecpu_cabc_id,emba_name,coba_company,c.cid,ownmonth,SUM(isnull(ecpu_yl_co_total,0)+isnull(ecpu_yl_em_total,0)) ecop_yl_sf,SUM(isnull(ecpu_yil_em_total,0)+isnull(ecpu_yil_co_total,0)+isnull(ecpu_bchyil_em_total,0)+isnull(ecpu_bchyil_em_total,0)) ecop_jl_sf,SUM(isnull(ecpu_gsh_em_total,0)+isnull(ecpu_gsh_co_total,0)) ecop_gs_sf,SUM(isnull(ecpu_shy_em_total,0)+isnull(ecpu_shy_co_total,0)) ecop_sye_sf,SUM(isnull(ecpu_shyu_em_total,0)+isnull(ecpu_shyu_co_total,0)) ecop_syu_sf,SUM(isnull(ecpu_gjj_em_total,0)+isnull(ecpu_gjj_co_total,0)+isnull(ecpu_bchgjj_co_total,0)+isnull(ecpu_bchgjj_em_total,0)) ecop_house_sf,b.ecop_file_sf,b.ecop_fw_sf,SUM(isnull(ecpu_other_total,0)+isnull(ecpu_welfare_total,0)) ecop_other_sf from EmCommissionOutPayUpdate a left join (select epfd_ecpu_id, sum(case epfd_name when '档案费' then epfd_month_sum else 0 end) as ecop_file_sf,sum(case epfd_name when '服务费' then epfd_month_sum else 0 end) as ecop_fw_sf from EmCommissionOutPayUpdateFeeDetail where epfd_name in ('服务费','档案费') group by epfd_ecpu_id) b on b.epfd_ecpu_id=a.ecpu_id left join embase c on c.gid=a.gid left join cobase d on d.CID=c.cid group by a.gid,ecpu_cabc_id,emba_name,coba_company,c.cid,ownmonth,b.ecop_file_sf,b.ecop_fw_sf) b  on b.gid=a.gid and b.ecpu_cabc_id=a.ecop_cabc_id and b.ownmonth=a.ownmonth left join (select ecop_state,ecop_cabc_id,ownmonth,gid,ecop_id from EmCommissionOutPay) c on c.ecop_cabc_id=a.ecop_cabc_id and c.gid=a.gid and c.ownmonth=a.ownmonth left join (select gid,COUNT(*) ch_st from EmCommissionOutChange where ecoc_state in (2,3) group by gid) d on d.gid=a.gid where a.ownmonth="
				+ ownmonth
				+ " and a.ecop_cabc_id="
				+ cabc_id
				+ " and a.cid="
				+ cid;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString(7));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEcop_cabc_id(rs.getString("ecop_cabc_id"));
				model.setEcop_ch_st(rs.getString("ch_st"));
				model.setEcop_company(rs.getString(5));
				model.setEcop_name(rs.getString(4));
				model.setEcop_state(rs.getString("ecop_state"));
				model.setEcop_yl_di(rs.getString("yl_di"));
				model.setEcop_jl_di(rs.getString("jl_di"));
				model.setEcop_gs_di(rs.getString("gs_di"));
				model.setEcop_sye_di(rs.getString("sye_di"));
				model.setEcop_syu_di(rs.getString("syu_di"));
				model.setEcop_house_di(rs.getString("house_di"));
				model.setEcop_file_di(rs.getString("file_di"));
				model.setEcop_fw_di(rs.getString("fw_di"));
				model.setEcop_other_di(rs.getString("other_di"));
				model.setEcop_id(rs.getString("ecop_id"));
				model.setEcop_di_total(String.valueOf(new BigDecimal(rs
						.getString("yl_di")).add(new BigDecimal(rs
						.getString("jl_di")).add(new BigDecimal(rs
						.getString("gs_di")).add(new BigDecimal(rs
						.getString("sye_di")).add(new BigDecimal(rs
						.getString("syu_di")).add(new BigDecimal(rs
						.getString("house_di")).add(new BigDecimal(rs
						.getString("file_di")).add(new BigDecimal(rs
						.getString("fw_di")).add(new BigDecimal(rs
						.getString("other_di"))))))))))));

				if (st_p.equals("p1")
						&& new BigDecimal(rs.getString("yl_di"))
								.add(new BigDecimal(rs.getString("jl_di")).add(new BigDecimal(
										rs.getString("gs_di")).add(new BigDecimal(
										rs.getString("sye_di")).add(new BigDecimal(
										rs.getString("syu_di")).add(new BigDecimal(
										rs.getString("house_di")).add(new BigDecimal(
										rs.getString("file_di")).add(new BigDecimal(
										rs.getString("fw_di"))
										.add(new BigDecimal(rs
												.getString("other_di"))))))))))
								.equals("0.0")) {
					list.add(model);
				}
				if (st_p.equals("p2")
						&& !new BigDecimal(rs.getString("yl_di"))
								.add(new BigDecimal(rs.getString("jl_di")).add(new BigDecimal(
										rs.getString("gs_di")).add(new BigDecimal(
										rs.getString("sye_di")).add(new BigDecimal(
										rs.getString("syu_di")).add(new BigDecimal(
										rs.getString("house_di")).add(new BigDecimal(
										rs.getString("file_di")).add(new BigDecimal(
										rs.getString("fw_di"))
										.add(new BigDecimal(rs
												.getString("other_di"))))))))))
								.equals("0.0")) {
					list.add(model);
				}
				// if (st_p.equals("p3")
				// && (Double.toString(di_total).equals(total_sf))) {
				// list.add(model);
				// }

				if (!st_p.equals("p1") && !st_p.equals("p2")
						&& !st_p.equals("p3")) {
					list.add(model);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return list;
	}

	// 显示所有帐单数
	public List<EmCommissionOutPayModel> getpay_yflist(String cabc_id,
			String ownmonth, String er_st) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		String sqlstr = "";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		if (er_st.equals("1")) {
			sqlstr = "select a.gid,c.cid,emba_name,emba_idcard,coba_company,'外地帐单无数据' a_st from EmCommissionOutPay a left join EmCommissionOutPayUpdate b on b.ecpu_cabc_id=a.ecop_cabc_id and b.ownmonth=a.ownmonth and b.gid=a.gid left join embase c on c.gid=a.gid left join cobase d on d.cid=c.cid where a.ecop_cabc_id="+cabc_id+" and a.ownmonth="+ownmonth+" and b.gid is null";
		} else {
			sqlstr = "select a.gid,c.cid,emba_name,emba_idcard,coba_company,'本地帐单无数据' a_st from EmCommissionOutPayUpdate a left join EmCommissionOutPay b on a.ecpu_cabc_id=b.ecop_cabc_id and a.ownmonth=b.ownmonth and a.gid=b.gid left join embase c on c.gid=a.gid left join cobase d on d.cid=c.cid where a.ecpu_cabc_id="+cabc_id+" and a.ownmonth="+ownmonth+" and b.gid is null";
		}
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEcop_name(rs.getString("emba_name"));
				model.setEcop_dep_company(rs.getString("emba_idcard"));
				model.setEcop_company(rs.getString("coba_company"));
				model.setEcop_state(rs.getString("a_st"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}