package dal.CoReg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Controller.systemWindowController;
import Model.CoBaseModel;
import Model.CoOnlineRegisterInfoModel;
import Model.DocumentsInfoModel;
import Model.ResponsibilityBookModel;

public class CoReg_ListDal {

	public List<CoBaseModel> getCobaList() {
		List<CoBaseModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cid,coba_company,coba_shortname,coba_client "
				+ "from cobase where coba_servicestate=1 and cid not in("
				+ "select cid from CoOnlineRegisterInfo where cori_state between 1 and 11)";

		try {
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getPubIndustry() {
		List<String> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select name from PubTrade";

		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public CoBaseModel getCobaseInfo(Object... objs) {
		List<CoBaseModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select coba_postcode,coba_companymanager,coba_reg_fund,coba_industytype,coba_company,coba_shortname,cast(coba_reg_fund as nvarchar(50)) coba_reg_fund,coba_companymanager "
				+ "from cobase where cid=? and coba_servicestate=1";

		try {
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0);
	}

	public List<CoOnlineRegisterInfoModel> getCoRegOpList() {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cori_id,a.cid,coba_shortname,coba_company,ownmonth,cori_reg_type, cori_tackovertime,"
				+ " cori_reg_transact_type,cori_state,case when cori_isrevoke=1 then '已撤销'"
				+ " when cori_isback=1 then '退回' else statename end as statename,cori_addname,"
				+ " convert(nvarchar(16),cori_addtime,120) cori_addtime,cori_tapr_id,cori_tzl_state,"
				+ " cori_isrevoke,cori_isback,cori_if_sign_responsebook,cori_if_responsebook,cori_need_doc"
				+ " from CoOnlineRegisterInfo a inner join CoOnlineRegisterState b"
				+ " on a.cori_state=b.stateid inner join CoBase c on a.cid=c.CID"
				+ " where b.state=1 and b.typename='后道状态' and cori_restate=1"
				+ " order by cori_id desc";

		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOnlineRegisterInfoModel> getqdCoRegOpList() {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cori_id,a.cid,coba_shortname,coba_company,ownmonth,cori_reg_type, cori_tackovertime,"
				+ " cori_reg_transact_type,cori_state,case when cori_isrevoke=1 then '已撤销'"
				+ " when cori_isback=1 then '退回' else statename end as statename,cori_addname,"
				+ " convert(nvarchar(16),cori_addtime,120) cori_addtime,cori_tapr_id,cori_tzl_state,"
				+ " cori_isrevoke,cori_isback,cori_if_sign_responsebook,cori_if_responsebook,cori_need_doc"
				+ " from CoOnlineRegisterInfo a inner join CoOnlineRegisterState b"
				+ " on a.cori_state=b.stateid inner join CoBase c on a.cid=c.CID"
				+ " where b.state=1 and b.typename='前道状态' and cori_restate=1"
				+ " order by cori_id desc";

		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOnlineRegisterInfoModel> getstateList(String str) {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,stateid,statename,operate from CoOnlineRegisterState"
				+ " where state=1" + str + " order by stateid";

		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getownmonthList() {
		List<String> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct ownmonth from CoOnlineRegisterInfo";

		try {
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public CoOnlineRegisterInfoModel getCoregInfo(Object... objs) {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cori_id,a.cid,ownmonth,coba_shortname,cori_reg_type,cori_reg_transact_type,cori_reg_account,cori_reg_pwd,"
				+ " cori_industry_type,cast(cori_reg_fund as nvarchar(50)) cori_reg_fund,cori_need_doc,"
				+ " cori_belong_unit,cori_legal_person,cori_lp_tel,cori_fax,cori_unti_principal,"
				+ " cori_up_tel,cori_postcode,cori_open_date,cori_operate_s_date,cori_ss_code,cori_salary,cori_stock_unit1,"
				+ " cori_stock_unit2,cori_stock_unit3,cori_stock_unit4,cori_if_build,cori_if_pass,cori_if_tell,cori_all_p,cori_sz_p,"
				+ " cori_foreign_p,cori_sign_contract_p,cori_if_give,cori_if_build_sign_in,cori_wtime_type,cori_hour_per_day,"
				+ " cori_hour_per_week,cori_day_per_week,cori_if_ot,cori_i_work,cori_sc_man_hour,cori_salary_date,cori_if_arrear,"
				+ " cori_if_lowest,cori_if_ar_ot,cori_if_join_ss,cori_join_p,cori_if_happen_ld,cori_if_kid,cori_if_union,cori_if_responsebook,"
				+ " cwin_id,convert(nvarchar(16),cori_reg_date,120) cori_reg_date,cori_oi_code,cori_web_password,"
				+ " cori_bl_code,cori_oaa_place,cori_ws_name,cori_ws_tel,cori_rp_t_address,cori_rp_t_tel,"
				+ " cori_address,cori_state,cori_backname,convert(nvarchar(16),cori_backtime,120) cori_backtime,cori_backreason,cori_remark,cori_addname,"
				+ " convert(nvarchar(16),cori_addtime,120) cori_addtime,cori_tapr_id,statename,"
				+ " coba_company,cori_if_sign_responsebook,cori_isrevoke,cori_isback,"
				+ " cori_sz_puzu_id,cori_wd_puzu_id, cori_tackovertime "
				+ " from CoOnlineRegisterInfo a inner join CoOnlineRegisterState b on a.cori_state=b.stateid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " where cori_id=? and b.state=1 and b.typename='后道状态' and cori_restate=1";
		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public CoOnlineRegisterInfoModel getCoregInfobyCid(Object... objs) {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cori_id,a.cid,ownmonth,cori_reg_type,cori_reg_transact_type,cori_reg_account,cori_reg_pwd,"
				+ " cori_industry_type,cast(cori_reg_fund as nvarchar(50)) cori_reg_fund,cori_need_doc,"
				+ " cori_belong_unit,cori_legal_person,cori_lp_tel,cori_fax,cori_unti_principal,"
				+ " cori_up_tel,cori_postcode,cori_open_date,cori_operate_s_date,cori_ss_code,cori_salary,cori_stock_unit1,"
				+ " cori_stock_unit2,cori_stock_unit3,cori_stock_unit4,cori_if_build,cori_if_pass,cori_if_tell,cori_all_p,cori_sz_p,"
				+ " cori_foreign_p,cori_sign_contract_p,cori_if_give,cori_if_build_sign_in,cori_wtime_type,cori_hour_per_day,"
				+ " cori_hour_per_week,cori_day_per_week,cori_if_ot,cori_i_work,cori_sc_man_hour,cori_salary_date,cori_if_arrear,"
				+ " cori_if_lowest,cori_if_ar_ot,cori_if_join_ss,cori_join_p,cori_if_happen_ld,cori_if_kid,cori_if_union,cori_if_responsebook,"
				+ " cwin_id,convert(nvarchar(16),cori_reg_date,120) cori_reg_date,cori_oi_code,cori_web_password,"
				+ " cori_bl_code,cori_oaa_place,cori_ws_name,cori_ws_tel,cori_rp_t_address,cori_rp_t_tel,"
				+ " cori_address,cori_state,cori_backname,convert(nvarchar(16),cori_backtime,120) cori_backtime,cori_backreason,cori_remark,cori_addname,"
				+ " convert(nvarchar(16),cori_addtime,120) cori_addtime,cori_tapr_id,statename,"
				+ " coba_company,cori_if_sign_responsebook,cori_isrevoke,cori_isback,"
				+ " cori_sz_puzu_id,cori_wd_puzu_id, cori_tackovertime "
				+ " from CoOnlineRegisterInfo a inner join CoOnlineRegisterState b on a.cori_state=b.stateid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " where a.cid=? and b.state=1 and b.typename='后道状态' and cori_restate=1";

		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CoOnlineRegisterInfoModel();
		}

	}

	public List<CoOnlineRegisterInfoModel> getStateRelList(String str,
			Integer cori_id) {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select statename,operate,crsr_addname,convert(nvarchar(16),crsr_addtime,120) crsr_addtime,"
				+ " convert(nvarchar(16),crsr_statetime,120) crsr_statetime"
				+ " from CoOnlineRegisterState a inner join CoOnlineRegisterStateRel b"
				+ " on a.stateid=b.crsr_stateid"
				+ " where crsr_cori_id="
				+ cori_id + str;

		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DocumentsInfoModel> getDocList(Object... objs) {
		List<DocumentsInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cast(dire_id as nvarchar(50)) dire_id,"
				+ " cast(dire_doin_id as nvarchar(50)) dire_doin_id,"
				+ " cast(dire_puzu_id as nvarchar(50)) dire_puzu_id,"
				+ " cast(dire_state as nvarchar(50)) dire_state,doin_content from DocumentsInfo a"
				+ " inner join DipzRelation b on a.doin_id=b.dire_doin_id"
				+ " where b.dire_puzu_id=? order by dire_id";

		try {
			list = db.find(sql, DocumentsInfoModel.class,
					dbconn.parseSmap(DocumentsInfoModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据公司就业登记信息表id获取责任书表Id
	public Integer getRebk_id(Integer cori_id) {
		Integer id = null;
		String sql = "select * from ResponsibilityBook where rebk_cori_id="
				+ cori_id;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				id = rs.getInt("rebk_id");
			}
		} catch (Exception e) {

		}
		return id;
	}

	/*
	 * 查询公司是否已有就业登记信息
	 */
	public boolean ifExists(Integer cid, String str) {
		boolean flag = false;
		String sql = "select * from CoOnlineRegisterInfo where cid=" + cid
				+ str;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	/*
	 * 查询公司是否已有就业登记信息
	 */
	public CoOnlineRegisterInfoModel coregInfo(Integer cid, String str) {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		CoOnlineRegisterInfoModel model = new CoOnlineRegisterInfoModel();
		String sql = "select * from CoOnlineRegisterInfo where cid=" + cid
				+ str;
		dbconn db = new dbconn();
		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class));
			if (list.size() > 0) {
				model=list.get(0);
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 根据cid查询cori_id
	public int getCori_id(int cid) {
		int result = 0;
		String sql = " SELECT cori_id FROM CoOnlineRegisterInfo WHERE cid = ? ";
		dbconn db = new dbconn();
		PreparedStatement pstmt = db.getpre(sql);
		try {
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt("cori_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 根据公司就业登记信息表id获取责任书表Id
	public List<ResponsibilityBookModel> getResponsibilityBookList(Integer cid) {
		ResponsibilityBookModel model = new ResponsibilityBookModel();
		List<ResponsibilityBookModel> list = new ArrayList<ResponsibilityBookModel>();

		String sql = "select * from ResponsibilityBook where cid=" + cid;
		dbconn db = new dbconn();
		try {
			list = db.find(sql, ResponsibilityBookModel.class,
					dbconn.parseSmap(ResponsibilityBookModel.class));
		} catch (Exception e) {

		}
		return list;
	}

}
