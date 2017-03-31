package dal.EmCommercialInsurance;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import com.sun.mail.iap.Response;

import Conn.dbconn;
import Controller.systemWindowController;
import Model.CI_InsurantClaimModel;
import Model.CI_InsurantClaimVistModel;
import Model.CI_Insurant_ListModel;

public class CI_InsurantClaim_ListDal {
	// 获取商保基本信息
	public CI_InsurantClaimModel getci_base(String gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		CI_InsurantClaimModel m = new CI_InsurantClaimModel();
		String sqlstr = "select emba_mobile,emba_sbemail,case when emba_sy_bank IS NULL OR emba_sy_bank='' then eccl_bankname else emba_sy_bank end emba_sy_bank,case when emba_sy_account IS NULL OR emba_sy_account='' then eccl_bankacctid else emba_sy_account end emba_sy_account,(select count(*) from EmCommercialClaim where gid="+gid+") cl_count from embase a left join (select top 1 eccl_bankacctid,eccl_bankname,gid from EmCommercialClaim where gid="+gid+" order by eccl_id desc) b on b.gid=a.gid where a.gid="
				+ gid;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				m.setEccl_mobile(rs.getString("emba_mobile"));
				m.setEccl_email_addname(rs.getString("emba_sbemail"));
				m.setEccl_bankname(rs.getString("emba_sy_bank"));
				m.setEccl_bankacctid(rs.getString("emba_sy_account"));
				m.setEccl_class(rs.getString("cl_count"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return m;
	}
	
	// 获取商保理赔数据统计
		public CI_InsurantClaimModel getci_state() throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;
			CI_InsurantClaimModel m = new CI_InsurantClaimModel();
			String sqlstr = "select (select COUNT(*) from EmCommercialClaim where eccl_state=10) d0_state,(select COUNT(*) from EmCommercialClaim where eccl_state=0) d1_state,(select COUNT(*) from EmCommercialClaim where eccl_state=1) d2_state,(select COUNT(*) from EmCommercialClaim where eccl_state=2) d3_state,(select COUNT(*) from EmCommercialClaim where eccl_state=3) d4_state";
			try {
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					CI_InsurantClaimModel model = new CI_InsurantClaimModel();
					m.setD0_state(rs.getString("d0_state"));
					m.setD1_state(rs.getString("d1_state"));
					m.setD2_state(rs.getString("d2_state"));
					m.setD3_state(rs.getString("d3_state"));
					m.setD4_state(rs.getString("d4_state"));
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return m;
		}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getcid(int ecin_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select cid from EmCommercialClaim where eccl_id="
				+ ecin_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保基本信息
	public List<CI_InsurantClaimModel> getci_castsort_inbase()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select distinct ecin_castsort from EmComInsurance where gid=10068 and ecin_state=1 union all select '↓↓↓↓历史数据↓↓↓↓' union all select distinct ecin_castsort from EmComInsurance where gid=10068 and ecin_state=2";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_castsort(rs.getString("ecin_castsort"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保基本信息
	public List<CI_InsurantClaimModel> getci_name(String gid, String castsort)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select ecin_insurant from EmComInsurance where gid="
				+ gid + " and ecin_castsort='" + castsort + "'";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_insurant(rs.getString("ecin_insurant"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保基本信息
	public List<CI_InsurantClaimModel> getci_class_l(String eccc_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select eccc_name,eccc_id from EmCommercialClaimClass where eccc_L_id="
				+ eccc_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_castsort(rs.getString("eccc_name"));
				model.setEccc_id(rs.getString("eccc_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保基本信息
	public List<CI_InsurantClaimModel> getci_claim_Wtlist(String name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str="";
		if (!name.equals("全部")) {
			str = " and (ecic_company='" + name + "')";
		}
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select eccl_tapr_id,eccl_id,cast(year(eccl_associate_date) as varchar(4))+'-'+cast(month(eccl_associate_date) as varchar(4))+'-'+cast(day(eccl_associate_date) as varchar(4)) getdate,ecic_company bcompany,coba_shortname,eccl_insurant,eccl_insurer,eccl_pay_money,eccl_invoice_count,coba_client,eccl_addname,eccl_castsort from EmCommercialClaim a left join CoBase b on b.cid=a.cid left join EmBase c on c.gid=a.gid left join EmComInsuranceCompact d on d.ecic_insurance_type=a.eccl_castsort where eccl_state=1"+str;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_associate_date(rs.getString("getdate"));
				model.setEccl_castsort(rs.getString("eccl_castsort"));
				model.setEccl_company(rs.getString("coba_shortname"));
				model.setBcompany(rs.getString("bcompany"));
				model.setEccl_insurant(rs.getString("eccl_insurant"));
				model.setEccl_insurer(rs.getString("eccl_insurer"));
				model.setEccl_pay_money(rs.getString("eccl_pay_money"));
				model.setEccl_invoice_count(rs.getString("eccl_invoice_count"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEccl_addname(rs.getString("eccl_addname"));
				model.setEccl_id(rs.getInt("eccl_id"));
				model.setEccl_tapr_id(rs.getString("eccl_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保基本信息
	public List<CI_InsurantClaimModel> getci_claim_Wtoutlist(String name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str="";
		if (!name.equals("")) {
			str = " and (eccl_insurant like '%" + name
					+ "%' or eccl_insurer like '%" + name + "%')";
		}
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select eccl_tapr_id,eccl_id,cast(year(eccl_associate_date) as varchar(4))+'-'+cast(month(eccl_associate_date) as varchar(4))+'-'+cast(day(eccl_associate_date) as varchar(4)) getdate,'' bcompany,coba_shortname,eccl_insurant,eccl_insurer,eccl_pay_money,eccl_invoice_count,coba_client,eccl_addname,eccl_castsort,a.gid,a.cid from EmCommercialClaim a left join CoBase b on b.cid=a.cid left join EmBase c on c.gid=a.gid where eccl_state=0"+str;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_associate_date(rs.getString("getdate"));
				model.setEccl_company(rs.getString("coba_shortname"));
				model.setBcompany(rs.getString("bcompany"));
				model.setEccl_insurant(rs.getString("eccl_insurant"));
				model.setEccl_insurer(rs.getString("eccl_insurer"));
				model.setEccl_pay_money(rs.getString("eccl_pay_money"));
				model.setEccl_invoice_count(rs.getString("eccl_invoice_count"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEccl_addname(rs.getString("eccl_addname"));
				model.setEccl_id(rs.getInt("eccl_id"));
				model.setEccl_tapr_id(rs.getString("eccl_tapr_id"));
				model.setEccl_castsort(rs.getString("eccl_castsort"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 获取商保基本信息
		public List<CI_InsurantClaimModel> getci_claim_Wtoutoverlist(String name)
				throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;
			String str="";
			if (!name.equals("")) {
				str = " and (eccl_insurant like '%" + name
						+ "%' or eccl_insurer like '%" + name + "%')";
			}
			List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
			String sqlstr = "select eccl_tapr_id,eccl_id,cast(year(eccl_associate_date) as varchar(4))+'-'+cast(month(eccl_associate_date) as varchar(4))+'-'+cast(day(eccl_associate_date) as varchar(4)) getdate,'' bcompany,coba_shortname,eccl_insurant,eccl_insurer,eccl_pay_money,eccl_invoice_count,coba_client,eccl_addname,eccl_castsort,a.gid,a.cid from EmCommercialClaim a left join CoBase b on b.cid=a.cid left join EmBase c on c.gid=a.gid where eccl_state=10"+str;
			try {
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					CI_InsurantClaimModel model = new CI_InsurantClaimModel();
					model.setEccl_associate_date(rs.getString("getdate"));
					model.setEccl_company(rs.getString("coba_shortname"));
					model.setBcompany(rs.getString("bcompany"));
					model.setEccl_insurant(rs.getString("eccl_insurant"));
					model.setEccl_insurer(rs.getString("eccl_insurer"));
					model.setEccl_pay_money(rs.getString("eccl_pay_money"));
					model.setEccl_invoice_count(rs.getString("eccl_invoice_count"));
					model.setCoba_client(rs.getString("coba_client"));
					model.setEccl_addname(rs.getString("eccl_addname"));
					model.setEccl_id(rs.getInt("eccl_id"));
					model.setEccl_tapr_id(rs.getString("eccl_tapr_id"));
					model.setEccl_castsort(rs.getString("eccl_castsort"));
					model.setGid(rs.getString("gid"));
					model.setCid(rs.getString("cid"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return list;
		}

	// 获取商保索赔处理中数据
	public List<CI_InsurantClaimModel> getci_claim_autlist(String name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		if (!name.equals("")) {
			str = " and (eccl_insurant like '%" + name
					+ "%' or eccl_insurer like '%" + name + "%')";
		}
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select a.gid,a.cid,eccl_castsort,eccl_tapr_id,eccl_id,cast(year(eccl_sign_date) as varchar(4))+'-'+cast(month(eccl_sign_date) as varchar(4))+'-'+cast(day(eccl_sign_date) as varchar(4)) getdate,'' bcompany,coba_shortname,eccl_insurant,eccl_insurer,eccl_pay_money,eccl_invoice_count,coba_client,eccl_addname,case when eccl_fact_money<>'' then 1 else 0 end chk_state,eccl_fact_money from EmCommercialClaim a left join CoBase b on b.cid=a.cid left join EmBase c on c.gid=a.gid where eccl_state=2"
				+ str;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_associate_date(rs.getString("getdate"));
				model.setEccl_company(rs.getString("coba_shortname"));
				model.setBcompany(rs.getString("bcompany"));
				model.setEccl_insurant(rs.getString("eccl_insurant"));
				model.setEccl_insurer(rs.getString("eccl_insurer"));
				model.setEccl_pay_money(rs.getString("eccl_pay_money"));
				model.setEccl_invoice_count(rs.getString("eccl_invoice_count"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEccl_addname(rs.getString("eccl_addname"));
				model.setEccl_id(rs.getInt("eccl_id"));
				model.setEccl_tapr_id(rs.getString("eccl_tapr_id"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEccl_castsort(rs.getString("eccl_castsort"));
				model.setEccl_fact_money(rs.getString("chk_state"));
				model.setEccl_auditing_money(rs.getString("eccl_fact_money"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保索赔已处理数据
	public List<CI_InsurantClaimModel> getci_claim_autoverlist(String name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		if (!name.equals("")) {
			str = " and (eccl_insurant like '%" + name
					+ "%' or eccl_insurer like '%" + name + "%')";
		}
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select a.gid,a.cid,eccl_castsort,eccl_tapr_id,eccl_id,cast(year(eccl_associate_date) as varchar(4))+'-'+cast(month(eccl_associate_date) as varchar(4))+'-'+cast(day(eccl_associate_date) as varchar(4)) getdate,'' bcompany,coba_shortname,eccl_insurant,eccl_insurer,eccl_pay_money,eccl_invoice_count,coba_client,eccl_addname from EmCommercialClaim a left join CoBase b on b.cid=a.cid left join EmBase c on c.gid=a.gid where eccl_state=3"
				+ str;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_associate_date(rs.getString("getdate"));
				model.setEccl_company(rs.getString("coba_shortname"));
				model.setBcompany(rs.getString("bcompany"));
				model.setEccl_insurant(rs.getString("eccl_insurant"));
				model.setEccl_insurer(rs.getString("eccl_insurer"));
				model.setEccl_pay_money(rs.getString("eccl_pay_money"));
				model.setEccl_invoice_count(rs.getString("eccl_invoice_count"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEccl_addname(rs.getString("eccl_addname"));
				model.setEccl_id(rs.getInt("eccl_id"));
				model.setEccl_tapr_id(rs.getString("eccl_tapr_id"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEccl_castsort(rs.getString("eccl_castsort"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保索赔处理中数据
	public List<CI_InsurantClaimModel> getci_claim_remarklist(String eccl_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select eccr_content,eccr_addname,cast(year(eccr_addtime) as varchar(4))+'-'+cast(month(eccr_addtime) as varchar(4))+'-'+cast(day(eccr_addtime) as varchar(4)) eccr_addtime from EmCommercialClaimRecord where eccr_eccl_id="
				+ eccl_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_remark(rs.getString("eccr_content"));
				model.setEccl_addname(rs.getString("eccr_addname"));
				model.setEccl_addtime(rs.getString("eccr_addtime"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保索赔类型
	public List<CI_InsurantClaimModel> getci_castsort_class(String eccl_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select eccc_name,a.eccc_id from EmCommercialClaimClass a left join (select eccc_id from EmCommercialClaimClass a left join EmCommercialClaim b on b.eccl_castsort=a.eccc_name where eccl_id='"
				+ eccl_id
				+ "') b on b.eccc_id=a.eccc_F_id where b.eccc_id is not null";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_castsort(rs.getString("eccc_name"));
				model.setEccc_id(rs.getString("eccc_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保索赔类型
	public List<CI_InsurantClaimModel> getci_claim_down(String eccl_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select * from EmCommercialClaim a left join EmBase b on b.gid=a.gid where eccl_id="
				+ eccl_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_insurant(rs.getString("eccl_insurant"));
				model.setEccl_insurer(rs.getString("eccl_insurer"));
				model.setEccl_castsort(rs.getString("eccl_castsort"));
				model.setEccl_bankacctid(rs.getString("eccl_bankacctid"));
				model.setEccl_bankname(rs.getString("eccl_bankname"));
				model.setEccl_account(rs.getString("eccl_account"));
				model.setEccl_pay_money(rs.getString("eccl_pay_money"));
				model.setEccl_invoice_count(rs.getString("eccl_invoice_count"));
				model.setIdcard(rs.getString("emba_idcard"));
				model.setGid(rs.getString("gid"));
				model.setEccl_email_change(rs.getString("emba_sbemail"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保索赔类型
	public List<CI_InsurantClaimVistModel> getci_claim_vistlist(String eccl_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimVistModel> list = new ArrayList<CI_InsurantClaimVistModel>();
		String sqlstr = "select eccv_id,eccv_pay_money,eccv_invoice_count,eccv_castsort,cast(year(eccv_visiting_date) as varchar(4))+'-'+cast(month(eccv_visiting_date) as varchar(4))+'-'+cast(day(eccv_visiting_date) as varchar(4)) eccv_visiting_date,eccv_visiting_hospital,eccv_visiting_cause,eccv_remark,eccc_name,eccv_claim_class from EmCommercialClaimVisits a left join EmCommercialClaimClass b on b.eccc_id=a.eccv_claim_bclass where eccv_clid="
				+ eccl_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimVistModel model = new CI_InsurantClaimVistModel();
				model.setEccv_pay_money(rs.getString("eccv_pay_money"));
				model.setEccv_invoice_count(rs.getString("eccv_invoice_count"));
				model.setEccv_castsort(rs.getString("eccv_castsort"));
				model.setEccv_visiting_date(rs.getString("eccv_visiting_date"));
				model.setEccv_visiting_hospital(rs
						.getString("eccv_visiting_hospital"));
				model.setEccv_visiting_cause(rs
						.getString("eccv_visiting_cause"));
				model.setEccv_remark(rs.getString("eccv_remark"));
				model.setEccv_claim_bclass(rs.getString("eccc_name"));
				model.setEccv_claim_class(rs.getString("eccv_claim_class"));
				model.setEccv_id(rs.getInt("eccv_id"));

				model.setPid_list(new ListModelList<CI_InsurantClaimVistModel>(
						getpid_List(rs.getInt("eccv_id"))));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 根据eccv_id获取发票
	public List<CI_InsurantClaimVistModel> getpid_List(int eccv_id)
			throws Exception {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimVistModel> list = new ArrayList<CI_InsurantClaimVistModel>();
		String sqlstr = "select * from EmCommercialClaimInvoice where ecci_viid="
				+ eccv_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimVistModel model = new CI_InsurantClaimVistModel();
				model.setEcci_pid(rs.getString("ecci_pid"));
				model.setEcci_invoice_money(rs.getString("ecci_invoice_money"));
				model.setEcci_west_medicine(rs.getString("ecci_west_medicine"));
				model.setEcci_chinese_medicine(rs
						.getString("ecci_chinese_medicine"));
				model.setEcci_herbal_medicine(rs
						.getString("ecci_herbal_medicine"));
				model.setEcci_exmine_fee(rs.getString("ecci_exmine_fee"));
				model.setEcci_bed_fee(rs.getString("ecci_bed_fee"));
				model.setEcci_check_fee(rs.getString("ecci_check_fee"));
				model.setEcci_ct_fee(rs.getString("ecci_ct_fee"));
				model.setEcci_prove_fee(rs.getString("ecci_prove_fee"));
				model.setEcci_cure_fee(rs.getString("ecci_cure_fee"));
				model.setEcci_ops_fee(rs.getString("ecci_ops_fee"));
				model.setEcci_other_fee(rs.getString("ecci_other_fee"));
				model.setEcci_service_fee(rs.getString("ecci_service_fee"));
				model.setEcci_stuff_fee(rs.getString("ecci_stuff_fee"));
				model.setEcci_register_fee(rs.getString("ecci_register_fee"));
				model.setEcci_nurse_fee(rs.getString("ecci_nurse_fee"));
				model.setEcci_id(rs.getInt("ecci_id"));
				model.setEcci_auditing_money(rs
						.getString("ecci_auditing_money"));
				model.setEcci_claim_total(rs.getString("ecci_claim_total"));
				model.setEcci_state(rs.getString("ecci_state"));
				model.setEcci_rejected_duty(rs.getString("ecci_rejected_duty"));
				model.setEcci_rejected_pdpaid(rs
						.getString("ecci_rejected_pdpaid"));
				model.setEcci_rejected_medicine(rs
						.getString("ecci_rejected_medicine"));
				model.setEcci_rejected_stuff(rs
						.getString("ecci_rejected_stuff"));
				model.setEcci_rejected_check(rs
						.getString("ecci_rejected_check"));
				model.setEcci_rejected_hospital(rs
						.getString("ecci_rejected_hospital"));
				model.setEcci_rejected_other_fee(rs
						.getString("ecci_rejected_other_fee"));
				model.setEcci_delay_case_history(rs
						.getString("ecci_delay_case_history"));
				model.setEcci_delay_invoice(rs.getString("ecci_delay_invoice"));
				model.setEcci_delay_leechdom_list(rs
						.getString("ecci_delay_leechdom_list"));
				model.setEcci_delay_paper(rs.getString("ecci_delay_paper"));
				model.setEcci_delay_check_report(rs
						.getString("ecci_delay_check_report"));
				model.setEcci_delay_other(rs.getString("ecci_delay_other"));
				model.setEcci_rejected_case(rs.getString("ecci_rejected_case"));
				model.setEcci_rejected_no_fee(rs
						.getString("ecci_rejected_no_fee"));
				model.setEcci_rejected_up_fee(rs
						.getString("ecci_rejected_up_fee"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 商保理赔备注添加
	public int getremark_add(String eccl_id, String email, String mobile,
			String content, String aa1, String aa2, String aa3, String username) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimRemark_P_zzq(?,?,?,?,?,?,?,?,?)");
			c.setString(1, eccl_id);
			c.setString(2, email);
			c.setString(3, mobile);
			c.setString(4, content);
			c.setString(5, aa1);
			c.setString(6, aa2);
			c.setString(7, aa3);
			c.setString(8, username);
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商保理赔备注添加
	public int getvist_add(String eccl_id, String cl_castsort, String jz_date,
			String jz_host, String jz_content, String sk_remark,
			String cl_class_la, String cl_class, String username) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimVistAdd_P_zzq(?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, eccl_id);
			c.setString(2, cl_castsort);
			c.setString(3, jz_date);
			c.setString(4, jz_host);
			c.setString(5, jz_content);
			c.setString(6, sk_remark);
			c.setString(7, cl_class);
			c.setString(8, cl_class_la);
			c.setString(9, username);
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商保理赔发票添加
	public int getci_overadd(String eccv_id, String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String em8, String em9, String em10, String em11, String em12,
			String em13, String em14, String em15, String em16, String em17,
			String em18, String eccl_id) {

		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimPidOverAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, eccv_id);
			c.setString(2, em1);
			c.setString(3, em2);
			c.setString(4, em3);
			c.setString(5, em4);
			c.setString(6, em5);
			c.setString(7, em6);
			c.setString(8, em7);
			c.setString(9, em8);
			c.setString(10, em9);
			c.setString(11, em10);
			c.setString(12, em11);
			c.setString(13, em12);
			c.setString(14, em13);
			c.setString(15, em14);
			c.setString(16, em15);
			c.setString(17, em16);
			c.setString(18, em17);
			c.setString(19, em18);
			c.setString(20, eccl_id);
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 商保理赔发票添加
	public int getpid_add(String eccl_id, String eccv_id, String em1,
			String em2, String em3, String em4, String em5, String em6,
			String em7, String em8, String em9, String em10, String em11,
			String em12, String em13, String em14, String em15, String em16,
			String em17, String username) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceClaimPidAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, eccl_id);
			c.setString(2, eccv_id);
			c.setString(3, em1);
			c.setString(4, em2);
			c.setString(5, em3);
			c.setString(6, em4);
			c.setString(7, em5);
			c.setString(8, em6);
			c.setString(9, em7);
			c.setString(10, em8);
			c.setString(11, em9);
			c.setString(12, em10);
			c.setString(13, em11);
			c.setString(14, em12);
			c.setString(15, em13);
			c.setString(16, em14);
			c.setString(17, em15);
			c.setString(18, em16);
			c.setString(19, em17);
			c.setString(20, username);
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 获取商保赔付时间
	public List<CI_InsurantClaimModel> getpf_list() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select distinct CONVERT(varchar(100), eccl_sign_date, 23) eccl_sign_date from EmCommercialClaim order by eccl_sign_date desc";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_castsort(rs.getString("eccl_sign_date"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保索赔查询
	public List<CI_InsurantClaimModel> getci_claim_chlist(String str1,
			String str2, String str3, String str4) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select a.gid,a.cid,eccl_castsort,eccl_tapr_id,eccl_id,cast(year(eccl_associate_date) as varchar(4))+'-'+cast(month(eccl_associate_date) as varchar(4))+'-'+cast(day(eccl_associate_date) as varchar(4)) getdate,ecic_company bcompany,coba_shortname,eccl_insurant,eccl_insurer,eccl_pay_money,eccl_invoice_count,coba_client,eccl_addname,CONVERT(varchar(100), eccl_sign_date, 23) eccl_sign_date,eccl_fact_money from EmCommercialClaim a left join CoBase b on b.cid=a.cid left join EmBase c on c.gid=a.gid  left join EmComInsuranceCompact d on d.ecic_insurance_type=a.eccl_castsort where 1=1"
				+ str1 + str2 + str3 + str4;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_associate_date(rs.getString("getdate"));
				model.setEccl_company(rs.getString("coba_shortname"));
				model.setBcompany(rs.getString("bcompany"));
				model.setEccl_insurant(rs.getString("eccl_insurant"));
				model.setEccl_insurer(rs.getString("eccl_insurer"));
				model.setEccl_pay_money(rs.getString("eccl_pay_money"));
				model.setEccl_invoice_count(rs.getString("eccl_invoice_count"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEccl_addname(rs.getString("eccl_addname"));
				model.setEccl_id(rs.getInt("eccl_id"));
				model.setEccl_tapr_id(rs.getString("eccl_tapr_id"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEccl_castsort(rs.getString("eccl_castsort"));
				model.setEccl_sign_date(rs.getString("eccl_sign_date"));
				model.setEccl_fact_money(rs.getString("eccl_fact_money"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保索赔查询
	public List<CI_InsurantClaimModel> getci_insurant_claddlist(String str1,String str2)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_InsurantClaimModel> list = new ArrayList<CI_InsurantClaimModel>();
		String sqlstr = "select coba_shortname,ecin_insurant,ecin_insurer,coba_client,gid,ecin_castsort,case when ecin_state=1 then '在保' else '停保' end ec_state,a.cid,CONVERT(varchar(100), ecin_in_date, 23) ecin_in_date,CONVERT(varchar(100), ecin_st_date, 23) ecin_st_date,ecin_idcard from EmComInsurance a left join cobase b on b.cid=a.cid where 1=1"
				+ str1+str2;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_InsurantClaimModel model = new CI_InsurantClaimModel();
				model.setEccl_company(rs.getString("coba_shortname"));
				model.setEccl_insurant(rs.getString("ecin_insurant"));
				model.setEccl_insurer(rs.getString("ecin_insurer"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEccl_visiting_count(rs.getString("ecin_idcard"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEccl_castsort(rs.getString("ecin_castsort"));
				model.setEccl_state(rs.getString("ec_state"));
				model.setEccl_addtime(rs.getString("ecin_in_date"));
				model.setEccl_associate_date(rs.getString("ecin_st_date"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 商保理赔待审核提交
		public int getci_outaut(int eccl_id) {

			dbconn conn = new dbconn();
			try {
				CallableStatement c = conn
						.getcall("EmComInsuranceClaimOutAut_P_zzq(?,?)");
				c.setInt(1, eccl_id);
				c.registerOutParameter(2, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(2);

			} catch (SQLException e) {
				return 1;
			}
		}
}
