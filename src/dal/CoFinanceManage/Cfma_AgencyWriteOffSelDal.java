package dal.CoFinanceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoFinanceAgencyMonthlyBillModel;
import Model.CoFinanceAgencyWriteOffInfoModel;
import Model.CoFinanceAgencyWriteOffModel;

public class Cfma_AgencyWriteOffSelDal {
	private dbconn conn = new dbconn();

	// 获取冲销机构列表
	public List<CoAgencyBaseModel> getAgencyList() {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoAgencyBaseModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select coab_id,coab_name,coab_province,coab_city,coab_setuptype from CoAgencyBase coab ");
		sql.append("where exists (select coas_id from CoAgencyBaseService where coas_coab_id=coab.coab_id and coas_type=1 and coas_state=1) ");
		sql.append("and exists (select coas_id from CoAgencyBaseService where coas_coab_id=coab.coab_id and coas_type=2 and coas_state=1) ");
		sql.append("and coab.coab_state=1 order by coab_name");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_province(rs.getString("coab_province"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托出冲销账单
	public List<CoFinanceAgencyMonthlyBillModel> getWtWriteOffBill(int coab_id) {
		List<CoFinanceAgencyMonthlyBillModel> list = new ArrayList<CoFinanceAgencyMonthlyBillModel>();
		CoFinanceAgencyMonthlyBillModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select espa_id,ep.ownmonth,espa_sf_fee from emshouldpayaut ep ");
		sql.append("left join (select cawi_id,cawi.ownmonth,cawi_code from CoFinanceAgencyWriteOffInfo cawi inner join CoFinanceAgencyWriteOff cawo on cawo.cawo_id=cawi.cawi_cawo_id where cawi_type=1) ca on ca.cawi_code=ep.espa_id ");
		sql.append("where espa_state=0 and coab_id=? and cawi_id is null");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceAgencyMonthlyBillModel();
				m.setCfab_number(rs.getString("espa_id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setImbalance(rs.getBigDecimal("espa_sf_fee"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托入冲销账单
	public List<CoFinanceAgencyMonthlyBillModel> getStWriteOffBill(int coab_id) {
		List<CoFinanceAgencyMonthlyBillModel> list = new ArrayList<CoFinanceAgencyMonthlyBillModel>();
		CoFinanceAgencyMonthlyBillModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cfab_number,cfab.ownmonth,cfab_PersonnelReceivable,cfab_FinanceReceivable,cfab_PaidIn,cfab_ChargeOff,cfab_WriteOffEx,comCou=coco.coCou-com.comCou from CoFinanceAgencyMonthlyBill cfab ");
		sql.append("left join (select co.cabc_id,cb.ownmonth,comCou=COUNT(*) from CoFinanceMonthlyBill cb inner join (select coco_id,cabc_id from CoCompact) co on cfmb_code=co.coco_id where cb.cfmb_name='委托入账单' and cb.cfmb_prefix='CP'  and cfmb_PersonnelConfirm=1 group by co.cabc_id,cb.ownmonth) com ");
		sql.append("on com.cabc_id=cfab.cfab_coab_id and com.ownmonth=cfab.ownmonth ");
		sql.append("left join (select cabc_id,coCou=COUNT(*) from CoCompact group by cabc_id) coco on coco.cabc_id=cfab.cfab_coab_id ");
		sql.append("where cfab_coab_id=? ");
		sql.append("and not exists(select cawi_id from CoFinanceAgencyWriteOffInfo cawi inner join CoFinanceAgencyWriteOff cawo on cawo.cawo_id=cawi.cawi_cawo_id where cawo.cawo_coab_id=cfab.cfab_coab_id and ownmonth=cfab.ownmonth and cawi_type=2) ");
		sql.append("order by ownmonth");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceAgencyMonthlyBillModel();
				m.setCfab_number(rs.getString("cfab_number"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCfab_FinanceReceivable(rs
						.getBigDecimal("cfab_FinanceReceivable"));
				m.setCfab_PersonnelReceivable(rs
						.getBigDecimal("cfab_PersonnelReceivable"));
				m.setCfab_PaidIn(rs.getBigDecimal("cfab_PaidIn"));
				m.setCfab_ChargeOff(rs.getBigDecimal("cfab_ChargeOff"));
				m.setCfab_WriteOffEx(rs.getBigDecimal("cfab_WriteOffEx"));
				m.setComCou(rs.getInt("comCou"));
				m.sumReceivable();
				m.sumImbalance();
				m.setImbalance(m.getImbalance().abs());
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取冲销记录
	public List<CoFinanceAgencyWriteOffModel> getWriteOffList(int coab_id) {
		List<CoFinanceAgencyWriteOffModel> list = new ArrayList<CoFinanceAgencyWriteOffModel>();
		CoFinanceAgencyWriteOffModel m = null;
		String sql = "select *,addtime=CONVERT(varchar(100), cawo_addtime, 20) from CoFinanceAgencyWriteOff where cawo_coab_id=? order by cawo_id desc";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceAgencyWriteOffModel();
				m.setCawo_id(rs.getInt("cawo_id"));
				m.setCawo_writeOffEx(rs.getBigDecimal("cawo_writeOffEx"));
				m.setCawo_addname(rs.getString("cawo_addname"));
				m.setCawo_addtime(rs.getString("addtime"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取冲销明细记录
	public List<CoFinanceAgencyWriteOffInfoModel> getWriteOffInfoList(
			int cawo_id, int type) {
		List<CoFinanceAgencyWriteOffInfoModel> list = new ArrayList<CoFinanceAgencyWriteOffInfoModel>();
		CoFinanceAgencyWriteOffInfoModel m = null;
		String sql = "select * from CoFinanceAgencyWriteOffInfo where cawi_cawo_id=? and cawi_type=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cawo_id);
			psmt.setInt(2, type);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoFinanceAgencyWriteOffInfoModel();
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCawi_writeOffEx(rs.getBigDecimal("cawi_writeOffEx"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
