package dal.CoFinanceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoFinanceAgencyCollectModel;
import Model.CoFinanceAgencyModel;
import Model.CoFinanceAgencyMonthlyBillModel;
import Model.CoFinanceMonthlyBillModel;

public class Cfma_AgencySelDal {
	private dbconn conn = new dbconn();

	// 获取机构总账表数据
	public List<CoFinanceAgencyModel> getTotalAccount() {
		List<CoFinanceAgencyModel> list = new ArrayList<CoFinanceAgencyModel>();
		CoFinanceAgencyModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cfat_id,cfat_coab_id,coab.coab_province,coab.coab_city,coab.coab_name,personnelReceivable=ISNULL(cfab.PersonnelReceivable,0),cfat_Balance,");
		sql.append("financeReceivable=ISNULL(cfab.FinanceReceivable,0),chargeOff=ISNULL(cfab.ChargeOff,0),totalPaidIn=ISNULL(cfac.TotalPaidIn,0),WriteOffEx=ISNULL(cfab.WriteOffEx,0) ");
		sql.append("from CoFinanceAgencyTotalAccount cfat ");
		sql.append("left join (select cfab_coab_id,PersonnelReceivable=SUM(cfab_PersonnelReceivable),FinanceReceivable=SUM(cfab_FinanceReceivable),ChargeOff=SUM(cfab_ChargeOff),WriteOffEx=SUM(cfab_WriteOffEx) from CoFinanceAgencyMonthlyBill group by cfab_coab_id) cfab on cfat.cfat_coab_id=cfab.cfab_coab_id ");
		sql.append("left join (select cfac_coab_id,TotalPaidIn=SUM(cfac_TotalPaidIn) from CoFinanceAgencyCollect group by cfac_coab_id) cfac on cfat.cfat_coab_id=cfac.cfac_coab_id ");
		sql.append("inner join (select coab_id,coab_province,coab_city,coab_name from CoAgencyBase cb inner join CoAgencyBaseService cs on cb.coab_id=cs.coas_coab_id and coas_type=2) coab on coab.coab_id=cfat.cfat_coab_id");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new CoFinanceAgencyModel();
				m.setCfat_id(rs.getInt("cfat_id"));
				m.setCfat_coab_id(rs.getInt("cfat_coab_id"));
				m.setProvince(rs.getString("coab_province"));
				m.setCity(rs.getString("coab_city"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setPersonnelReceivable(rs
						.getBigDecimal("personnelReceivable"));
				m.setFinanceReceivable(rs.getBigDecimal("financeReceivable"));
				m.setChargeOff(rs.getBigDecimal("chargeOff"));
				m.setTotalPaidIn(rs.getBigDecimal("totalPaidIn"));
				m.setCfat_Balance(rs.getBigDecimal("cfat_Balance"));
				m.setTotalWriteOffEx(rs.getBigDecimal("WriteOffEx"));
				m.sumReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据主键获取机构总账表数据
	public CoFinanceAgencyModel getTotalAccount(int cfat_id) {
		CoFinanceAgencyModel m = new CoFinanceAgencyModel();
		StringBuilder sql = new StringBuilder();
		sql.append("select cfat_id,cfat_coab_id,coab.coab_province,coab.coab_city,coab.coab_name,personnelReceivable=ISNULL(cfab.PersonnelReceivable,0),cfat_Balance,");
		sql.append("financeReceivable=ISNULL(cfab.FinanceReceivable,0),chargeOff=ISNULL(cfab.ChargeOff,0),totalPaidIn=ISNULL(cfac.TotalPaidIn,0),WriteOffEx=ISNULL(cfab.WriteOffEx,0) ");
		sql.append("from CoFinanceAgencyTotalAccount cfat ");
		sql.append("left join (select cfab_coab_id,PersonnelReceivable=SUM(cfab_PersonnelReceivable),FinanceReceivable=SUM(cfab_FinanceReceivable),ChargeOff=SUM(cfab_ChargeOff),WriteOffEx=SUM(cfab_WriteOffEx) from CoFinanceAgencyMonthlyBill group by cfab_coab_id) cfab on cfat.cfat_coab_id=cfab.cfab_coab_id ");
		sql.append("left join (select cfac_coab_id,TotalPaidIn=SUM(cfac_TotalPaidIn) from CoFinanceAgencyCollect group by cfac_coab_id) cfac on cfat.cfat_coab_id=cfac.cfac_coab_id ");
		sql.append("inner join (select coab_id,coab_province,coab_city,coab_name from CoAgencyBase cb inner join CoAgencyBaseService cs on cb.coab_id=cs.coas_coab_id and coas_type=2) coab on coab.coab_id=cfat.cfat_coab_id ");
		sql.append("where cfat.cfat_id=?");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cfat_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCfat_id(rs.getInt("cfat_id"));
				m.setCfat_coab_id(rs.getInt("cfat_coab_id"));
				m.setProvince(rs.getString("coab_province"));
				m.setCity(rs.getString("coab_city"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setPersonnelReceivable(rs
						.getBigDecimal("personnelReceivable"));
				m.setFinanceReceivable(rs.getBigDecimal("financeReceivable"));
				m.setChargeOff(rs.getBigDecimal("chargeOff"));
				m.setTotalPaidIn(rs.getBigDecimal("totalPaidIn"));
				m.setCfat_Balance(rs.getBigDecimal("cfat_Balance"));
				m.setTotalWriteOffEx(rs.getBigDecimal("WriteOffEx"));
				m.sumReceivable();
				m.sumImbalance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据cabc_id获取委托入机构每月帐单
	public List<CoFinanceAgencyMonthlyBillModel> getAgencyMonthlyBill(
			int coab_id) {
		List<CoFinanceAgencyMonthlyBillModel> list = new ArrayList<CoFinanceAgencyMonthlyBillModel>();
		CoFinanceAgencyMonthlyBillModel m = null;
		String sql = "select * from CoFinanceAgencyMonthlyBill where cfab_coab_id=? order by ownmonth desc";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceAgencyMonthlyBillModel();
				m.setCfab_number(rs.getString("cfab_number"));
				m.setCfab_coab_id(rs.getInt("cfab_coab_id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfab_FinanceReceivable(rs
						.getBigDecimal("cfab_FinanceReceivable"));
				m.setCfab_PersonnelReceivable(rs
						.getBigDecimal("cfab_PersonnelReceivable"));
				m.setCfab_PaidIn(rs.getBigDecimal("cfab_PaidIn"));
				m.setCfab_ChargeOff(rs.getBigDecimal("cfab_ChargeOff"));
				m.setCfab_WriteOffEx(rs.getBigDecimal("cfab_WriteOffEx"));
				m.sumReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据主键获取委托入机构每月帐单
	public CoFinanceAgencyMonthlyBillModel getAgencyMonthlyBillModel(
			String cfab_number) {
		CoFinanceAgencyMonthlyBillModel m = new CoFinanceAgencyMonthlyBillModel();
		StringBuilder sql = new StringBuilder();
		sql.append("select *,addtime=CONVERT(varchar(100), cfab_addtime, 120) from CoFinanceAgencyMonthlyBill cfab ");
		sql.append("inner join (select coab_id,coab_province,coab_city,coab_name from CoAgencyBase cb inner join CoAgencyBaseService cs on cb.coab_id=cs.coas_coab_id and coas_type=2) coab on coab.coab_id=cfab.cfab_coab_id ");
		sql.append("inner join (select cfat_coab_id,cfat_Balance=ISNULL(cfat_Balance,0) from CoFinanceAgencyTotalAccount) cfat on cfat.cfat_coab_id=cfab.cfab_coab_id ");
		sql.append("where cfab_number=?");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, cfab_number);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCfab_number(rs.getString("cfab_number"));
				m.setCfab_coab_id(rs.getInt("cfab_coab_id"));
				m.setProvince(rs.getString("coab_province"));
				m.setCity(rs.getString("coab_city"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfab_FinanceReceivable(rs
						.getBigDecimal("cfab_FinanceReceivable"));
				m.setCfab_PersonnelReceivable(rs
						.getBigDecimal("cfab_PersonnelReceivable"));
				m.setCfab_PaidIn(rs.getBigDecimal("cfab_PaidIn"));
				m.setCfab_ChargeOff(rs.getBigDecimal("cfab_ChargeOff"));
				m.setCfat_Balance(rs.getBigDecimal("cfat_Balance"));
				m.setCfab_addtime(rs.getString("addtime"));
				m.setCfab_WriteOffEx(rs.getBigDecimal("cfab_WriteOffEx"));
				m.sumReceivable();
				m.sumImbalance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据coab_id获取收款纪录
	public List<CoFinanceAgencyCollectModel> getAgencyCollectList(int coab_id) {
		List<CoFinanceAgencyCollectModel> list = new ArrayList<CoFinanceAgencyCollectModel>();
		CoFinanceAgencyCollectModel m = null;
		String sql = "select cfac_id,cfac_cfab_number,cfac_TotalPaidIn,cfac_remark,cfac_addname,cfac_addtime=CONVERT(varchar(100), cfac_addtime, 120) from CoFinanceAgencyCollect where cfac_coab_id=? and cfac_state=1 order by cfac_addtime desc";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceAgencyCollectModel();
				m.setCfac_id(rs.getInt("cfac_id"));
				m.setCfac_cfab_number(rs.getString("cfac_cfab_number"));
				m.setCfac_TotalPaidIn(rs.getBigDecimal("cfac_TotalPaidIn"));
				m.setCfac_addname(rs.getString("cfac_addname"));
				m.setCfac_addtime(rs.getString("cfac_addtime"));
				m.setCfac_remark(rs.getString("cfac_remark"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 按机构账单查询公司账单列表
	public List<CoFinanceMonthlyBillModel> getCoFinanceMonthlyBillList(
			int coab_id, int ownmonth) {
		List<CoFinanceMonthlyBillModel> list = new ArrayList<CoFinanceMonthlyBillModel>();
		CoFinanceMonthlyBillModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cb.*,coba.* from CoFinanceMonthlyBill cb ");
		sql.append("inner join (select coco_id,cabc_id from CoCompact) co on cfmb_code=co.coco_id ");
		sql.append("inner join (select coab_id from CoAgencyBase cb inner join CoAgencyBaseService cs on cb.coab_id=cs.coas_coab_id and coas_type=2) coab on coab.coab_id=co.cabc_id ");
		sql.append("inner join CoFinanceTotalAccount cfta on cb.cfmb_cfta_id=cfta.cfta_id ");
		sql.append("inner join (select CID,coba_company,coba_client from CoBase)coba on cfta.cid=coba.CID ");
		sql.append("where cb.cfmb_name='委托入账单' and cb.cfmb_prefix='CP' and coab.coab_id=? and cb.ownmonth=? ");
		sql.append("order by cb.cfmb_PersonnelConfirm desc,coba.CID");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceMonthlyBillModel();
				m.setCfmb_cfta_id(rs.getInt("cfmb_cfta_id"));
				m.setCfmb_number(rs.getString("cfmb_number"));
				m.setCfmb_name(rs.getString("cfmb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfmb_PersonnelConfirm(rs.getInt("cfmb_PersonnelConfirm"));
				m.setCfmb_PersonnelReceivable(rs
						.getBigDecimal("cfmb_PersonnelReceivable"));
				m.setCfmb_FinanceReceivable(rs
						.getBigDecimal("cfmb_FinanceReceivable"));
				m.setCfmb_TotalPaidIn(rs.getBigDecimal("cfmb_TotalPaidIn"));
				m.setCfmb_LoanBalance(rs.getBigDecimal("cfmb_LoanBalance"));
				m.setCfmb_CarryForwardEx(rs
						.getBigDecimal("cfmb_CarryForwardEx"));
				m.setCid(rs.getInt("cid"));
				m.setCompany(rs.getString("coba_company"));
				m.setClient(rs.getString("coba_client"));
				m.sumTotalReceivable();
				m.sumImbalance();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据coab_id获取收款纪录
	public CoAgencyBaseModel getAgency(int coab_id) {
		CoAgencyBaseModel m = null;
		String sql = "select coab_name,coab_city from CoAgencyBase cb inner join CoAgencyBaseService cs on cb.coab_id=cs.coas_coab_id and coas_type=2 where coab_id=?";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_city(rs.getString("coab_city"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
}
