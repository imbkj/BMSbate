package dal.EmCAFFeeInfo;

import java.sql.ResultSet;
import java.util.ArrayList;

import Conn.dbconn;
import Model.EmCAFFeeInfoCompanyModel;
import Model.EmCAFFeeInfoModel;
import Model.HandoverPeopleModel;

public class EmCAFFeeInfo_SelectDal {
	private static dbconn conn = new dbconn();

	// 获取证件及档案费用信息
	public ArrayList<EmCAFFeeInfoModel> getECFIInfoList(String str,
			String orderBy) {
		ArrayList<EmCAFFeeInfoModel> list = new ArrayList<EmCAFFeeInfoModel>();
		EmCAFFeeInfoModel m = null;
		String sql;
		sql = "SELECT *,ecfi_state ecfistate,case ecfi_class when '档案' then convert(varchar(10),ecfi_sdate,120)+'~<br>'+convert(varchar(10),ecfi_cddate,120) when '户口' then convert(varchar(10),ecfi_sdate,120)+'~<br>'+convert(varchar(10),ecfi_cddate,120) else convert(varchar(10),ownmonth) end ownmonths,case when emap_id>0 then 'F00' else '666' end emp_color FROM ECFI_EmBase_CoBase_V a left join (select emap_id from emarchivepayment where emap_state>0)b on a.ecfi_caf_id=b.emap_id and ecfi_class in ('档案') WHERE 1=1"
				+ str + orderBy;
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmCAFFeeInfoModel();
				m.setName(rs.getString("name"));
				m.setIdcard(rs.getString("idcard"));
				m.setSpell(rs.getString("spell"));
				m.setShortname(rs.getString("shortname"));
				m.setShortspell(rs.getString("shortspell"));
				m.setClient(rs.getString("client"));
				m.setEcfi_id(rs.getInt("ecfi_id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEcfi_caf_id(rs.getInt("ecfi_caf_id"));
				m.setEcfi_class(rs.getString("ecfi_class"));
				m.setEcfi_payment_kind(rs.getString("ecfi_payment_kind"));
				m.setEcfi_payment_state(rs.getString("ecfi_payment_state"));
				m.setEcfi_rec_date(rs.getString("ecfi_rec_date"));
				m.setEcfi_cl_number(rs.getString("ecfi_cl_number"));
				m.setEcfi_csd_xls(rs.getInt("ecfi_csd_xls"));
				m.setEcfi_fee(rs.getInt("ecfi_fee"));
				m.setEcfi_wd_applicant(rs.getString("ecfi_wd_applicant"));
				m.setEcfi_wd_loan_date(rs.getString("ecfi_wd_loan_date"));
				m.setEcfi_ri_date(rs.getString("ecfi_ri_date"));
				m.setEcfi_csd_applicant(rs.getString("ecfi_csd_applicant"));
				m.setEcfi_csd_loan_date(rs.getString("ecfi_csd_loan_date"));
				m.setEcfi_csd_clearing_date(rs
						.getString("ecfi_csd_clearing_date"));
				m.setEcfi_clearing_people(rs.getString("ecfi_clearing_people"));
				m.setEcfi_if_sic(rs.getInt("ecfi_if_sic"));
				m.setEcfi_state(rs.getString("ecfi_state"));
				m.setEcfi_if_return(rs.getInt("ecfi_if_return"));
				m.setEcfi_if_refundment(rs.getInt("ecfi_if_refundment"));
				m.setEcfi_refundment_date(rs.getString("ecfi_refundment_date"));
				m.setEcfi_refundment_people(rs
						.getString("ecfi_refundment_people"));
				m.setEcfi_remark(rs.getString("ecfi_remark"));
				m.setEcfi_addname(rs.getString("ecfi_addname"));
				m.setEcfi_addtime(rs.getString("ecfi_addtime"));
				m.setEcfi_cddate(rs.getString("ecfi_cddate"));
				m.setEcfi_sdate(rs.getString("ecfi_sdate"));
				m.setEcfi_rs_invoice(rs.getString("ecfi_rs_invoice"));
				m.setEcfi_loanstate(rs.getInt("ecfi_loanstate"));
				m.setEcfi_paystate(rs.getString("ecfi_paystate"));
				m.setEcfi_rshj_id(rs.getString("ecfi_rshj_id"));
				m.setLoanstate(rs.getString("loanstate"));
				m.setEcfi_cddate(rs.getString("ecfi_cddate"));
				m.setEcfi_sdate(rs.getString("ecfi_sdate"));
				m.setEcfi_paystate(rs.getString("ecfi_paystate"));
				m.setCddate(rs.getString("cddate"));
				m.setSdate(rs.getString("sdate"));
				m.setRspay(rs.getString("rspay"));
				m.setEcfi_tapr_id(rs.getInt("ecfi_tapr_id"));
				list.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取证件及档案费用信息(根据id)
	public EmCAFFeeInfoModel getECFIInfoById(Integer ecfi_id) {
		EmCAFFeeInfoModel m = null;
		String sql;
		sql = "SELECT ecfi_tapr_id,ecfi_cl_number,name,gid,cid,shortname,ecfi_class,ecfi_payment_state,ecfi_wd_applicant,ecfi_wd_loan_date,ecfi_ri_date,ecfi_csd_applicant,ecfi_csd_loan_date,ecfi_csd_clearing_date,ecfi_if_sic,ecfi_loanstate,loanstate,cddate,sdate,rspay,ownmonth,isnull(emap_file,0) as emap_file,isnull(emap_hj,0) as emap_hj,isnull(emap_op,0) as emap_op,emap_fpay,emap_hpay,emap_finvoice,emap_hinvoice "
				+ "FROM ECFI_EmBase_CoBase_V A "
				+ "LEFT JOIN (select emap_id,EMAP_FILE,EMAP_HJ,EMAP_OP,emap_fpay,emap_hpay,emap_finvoice,emap_hinvoice from emarchivepayment where emap_state=1)B ON A.ECFI_CAF_ID=B.EMAP_ID and ecfi_class in ('档案') "
				+ "WHERE ecfi_id=" + ecfi_id;

		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmCAFFeeInfoModel();
				m.setEcfi_id(ecfi_id);
				m.setName(rs.getString("name"));
				m.setShortname(rs.getString("shortname"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEcfi_class(rs.getString("ecfi_class"));
				m.setEcfi_payment_state(rs.getString("ecfi_payment_state"));
				m.setEcfi_wd_applicant(rs.getString("ecfi_wd_applicant"));
				m.setEcfi_wd_loan_date(rs.getString("ecfi_wd_loan_date"));
				m.setEcfi_ri_date(rs.getString("ecfi_ri_date"));
				m.setEcfi_csd_applicant(rs.getString("ecfi_csd_applicant"));
				m.setEcfi_csd_loan_date(rs.getString("ecfi_csd_loan_date"));
				m.setEcfi_csd_clearing_date(rs
						.getString("ecfi_csd_clearing_date"));
				m.setEcfi_if_sic(rs.getInt("ecfi_if_sic"));
				m.setEcfi_loanstate(rs.getInt("ecfi_loanstate"));
				m.setLoanstate(rs.getString("loanstate"));
				m.setCddate(rs.getString("cddate"));
				m.setSdate(rs.getString("sdate"));
				m.setRspay(rs.getString("rspay"));
				m.setEmap_file(rs.getBigDecimal("emap_file"));
				m.setEmap_hj(rs.getBigDecimal("emap_hj"));
				m.setEmap_op(rs.getBigDecimal("emap_op"));
				m.setEmap_fpay(rs.getString("emap_fpay"));
				m.setEmap_hpay(rs.getString("emap_hpay"));
				m.setEmap_finvoice(rs.getString("emap_finvoice"));
				m.setEmap_hinvoice(rs.getString("emap_hinvoice"));
				m.setEcfi_cl_number(rs.getString("ecfi_cl_number"));
				m.setEcfi_tapr_id(rs.getInt("ecfi_tapr_id"));

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return m;
	}

	// 获取交接人
	public ArrayList<HandoverPeopleModel> getHP(String str) {
		ArrayList<HandoverPeopleModel> list = new ArrayList<HandoverPeopleModel>();
		HandoverPeopleModel m = null;
		String sql;
		sql = "SELECT * FROM HandoverPeople WHERE 1=1" + str;
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new HandoverPeopleModel();
				m.setHape_username(rs.getString("hape_username"));
				list.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 证件收款借款信息
	public EmCAFFeeInfoCompanyModel getECFIGather() {
		EmCAFFeeInfoCompanyModel m = null;
		String sql;
		sql = "SELECT (SELECT ISNULL(SUM(ecfi_fee),0) FROM ECFI_EmBase_CoBase_V WHERE ecfi_rec_date IS NOT NULL AND ecfi_payment_kind='个人付' AND ecfi_payment_state='已付' AND ecfi_if_return=0 AND ecfi_class='居住证')per_fee,(SELECT ISNULL(SUM(ecfi_fee),0) FROM ECFI_EmBase_CoBase_V WHERE ecfi_rec_date IS NOT NULL AND ecfi_payment_kind='个人付' AND ecfi_payment_state='已付' AND ecfi_if_return=0 AND ecfi_class='社保卡')esp_fee,(SELECT ISNULL(SUM(ecfi_fee),0) FROM ECFI_EmBase_CoBase_V WHERE ecfi_class='居住证' AND ecfi_if_return=0)wd_per_fee,(SELECT ISNULL(SUM(ecfi_fee),0) FROM ECFI_EmBase_CoBase_V WHERE ecfi_class='社保卡' AND ecfi_if_return=0)wd_esp_fee";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmCAFFeeInfoCompanyModel();
				m.setPer_fee(rs.getInt("per_fee"));
				m.setEsp_fee(rs.getInt("esp_fee"));
				m.setWd_per_fee(rs.getInt("wd_per_fee"));
				m.setWd_esp_fee(rs.getInt("wd_esp_fee"));
				m.setWd_all_fee(rs.getInt("wd_per_fee")
						+ rs.getInt("wd_esp_fee"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return m;
	}

	public ArrayList<EmCAFFeeInfoCompanyModel> getECFIInfoGAllFee(String str) {
		ArrayList<EmCAFFeeInfoCompanyModel> list = new ArrayList<EmCAFFeeInfoCompanyModel>();
		EmCAFFeeInfoCompanyModel m = null;
		String sql;
		sql = "SELECT ecfi_rec_date,ISNULL(SUM(ecfi_fee),0) AS all_fee FROM ECFI_EmBase_CoBase_V WHERE ecfi_rec_date IS NOT NULL AND ecfi_payment_kind='个人付' AND ecfi_payment_state='已付' AND ecfi_if_return=0 "
				+ str + "  GROUP BY ecfi_rec_date ORDER BY ecfi_rec_date DESC";
		try {

			// 证件收款借款信息
			EmCAFFeeInfoCompanyModel cm = getECFIGather();

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmCAFFeeInfoCompanyModel();
				m.setEcfi_rec_date(rs.getString("ecfi_rec_date"));
				m.setAll_fee(rs.getInt("all_fee"));
				m.setPer_fee(cm.getPer_fee());
				m.setEsp_fee(cm.getEsp_fee());
				m.setWd_per_fee(cm.getWd_per_fee());
				m.setWd_esp_fee(cm.getWd_esp_fee());
				m.setWd_all_fee(cm.getWd_all_fee());
				m.setAll_wd_bfee(rs.getInt("all_fee") - cm.getWd_all_fee());
				list.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
