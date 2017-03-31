package dal.CoReg;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoOnlineRegisterInfoModel;
import Model.ResponsbilityBookModel;
import Util.UserInfo;

public class CoReg_Dal extends dbconn {

	// 修改就业登记信息
	public int modCorim(CoOnlineRegisterInfoModel m) {
		int row = 0;
		String sql = "UPDATE CoOnlineRegisterInfo SET cori_legal_person=?,cori_reg_fund=?,cori_oi_code=?,cori_bl_code=?,cori_reg_date=?,cori_ws_name=?,cori_ws_tel=?,cori_rp_t_address=?,cori_rp_t_tel=?,cori_oaa_place=?,cori_address=? WHERE cori_id=?";
		Object[] objs = { m.getCori_legal_person(), m.getCori_reg_fund(),
				m.getCori_oi_code(), m.getCori_bl_code(), m.getCori_reg_date(),
				m.getCori_ws_name(), m.getCori_ws_tel(),
				m.getCori_rp_t_address(), m.getCori_rp_t_tel(),
				m.getCori_oaa_place(), m.getCori_address(), m.getCori_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int modReSponStep(ResponsbilityBookModel m) {
		System.out.println("rebk_id" + m.getRebk_id());
		int row = 0;
		try {
			row = Integer
					.valueOf(callWithReturn(
							"{?=call modReSponStep_gxj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
							Types.INTEGER, m.getRebk_id(),
							m.getRebk_signdate(), m.getRebk_signname(),
							m.getRebk_enddate(), m.getRebk_limit(),
							m.getRebk_rs_taketime(), m.getRebk_rs_takename(),
							m.getRebk_client_taketime(),
							m.getRebk_client_takename(),
							m.getRebk_rs_signtime(), m.getRebk_rs_signname(),
							m.getRebk_rs_oktime(), m.getRebk_state(),
							m.getRebk_addtime(), m.getRebk_addname(),
							m.getRebk_modtime(), m.getRebk_modname(),
							m.getRebk_booktype(), m.getRebk_step_state() + 1,
							m.getRebk_cori_id(), m.getRebk_need_doc(),
							m.getRebk_rs_doctime()).toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(row);
		return row;
	}

	public CoOnlineRegisterInfoModel getCoRim(Object... objs) {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
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
				+ " cori_sz_puzu_id,cori_wd_puzu_id"
				+ " from CoOnlineRegisterInfo a inner join CoOnlineRegisterState b on a.cori_state=b.stateid"
				+ " inner join CoBase c on a.cid=c.CID" + " where cori_id= ? ";

		try {
			list = find(sql, CoOnlineRegisterInfoModel.class,
					parseSmap(CoOnlineRegisterInfoModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0);
	}

	// 根据id获得对象
	public ResponsbilityBookModel getRbbm(int rebkid) {
		List<ResponsbilityBookModel> list = new ArrayList<>();
		String sql = " SELECT rebk_id,cid,CONVERT(varchar(50),rebk_signdate,120)rebk_signdate,rebk_signname,rebk_enddate,rebk_limit,CONVERT(varchar(50),rebk_rs_taketime,120)rebk_rs_taketime,rebk_rs_takename,CONVERT(varchar(50),rebk_client_taketime,120)rebk_client_taketime,rebk_client_takename,CONVERT(varchar(50),rebk_rs_signtime,120)rebk_rs_signtime,rebk_rs_signname,CONVERT(varchar(50),rebk_rs_oktime,120)rebk_rs_oktime,rebk_state,CONVERT(varchar(50),rebk_addtime,120)rebk_addtime,rebk_addname,CONVERT(varchar(50),rebk_modtime,120)rebk_modtime,rebk_modname,rebk_cori_id,rebk_step_state,rebk_need_doc,CONVERT(varchar(50),rebk_rs_doctime,120)doctime FROM ResponsibilityBook WHERE rebk_id = "
				+ rebkid;
		try {
			// list = find(sql, ResponsbilityBookModel.class,
			// parseSmap(ResponsbilityBookModel.class));
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				ResponsbilityBookModel m = new ResponsbilityBookModel();
				m.setRebk_id(rs.getInt("rebk_id"));
				m.setCid(rs.getInt("cid"));
				m.setRebk_signdate(rs.getString("rebk_signdate"));
				m.setRebk_signname(rs.getString("rebk_signname"));
				m.setRebk_enddate(rs.getString("rebk_enddate"));
				m.setRebk_limit(rs.getString("rebk_limit"));
				m.setRebk_rs_taketime(rs.getString("rebk_rs_taketime"));
				m.setRebk_rs_takename(rs.getString("rebk_rs_takename"));
				m.setRebk_client_taketime(rs.getString("rebk_client_taketime"));
				m.setRebk_client_takename(rs.getString("rebk_client_takename"));
				m.setRebk_rs_signtime(rs.getString("rebk_rs_signtime"));
				m.setRebk_rs_signname(rs.getString("rebk_rs_signname"));
				m.setRebk_rs_oktime(rs.getString("rebk_rs_oktime"));
				m.setRebk_state(rs.getInt("rebk_state"));
				m.setRebk_addtime(rs.getString("rebk_addtime"));
				m.setRebk_addname(rs.getString("rebk_addname"));
				m.setRebk_modtime(rs.getString("rebk_modtime"));
				m.setRebk_modname(rs.getString("rebk_modname"));
				m.setRebk_cori_id(rs.getInt("rebk_cori_id"));
				m.setRebk_step_state(rs.getInt("rebk_step_state"));
				m.setRebk_need_doc(rs.getString("rebk_need_doc"));
				m.setRebk_rs_doctime(rs.getString("doctime"));
				list.add(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		return list.get(0);
	}

	public int InsertRbb(ResponsbilityBookModel m) {
		Integer id = 0;
		try {
			id = Integer.valueOf(callWithReturn(
					"{?=call ResponsbilityBookAdd_gxj(?,?,?)}", Types.INTEGER,
					m.getCid(), m.getRebk_cori_id(), UserInfo.getUsername())
					.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	//就业登记接管修改注册信息
	public Integer CoBaseRegUpdate(CoBaseModel m)
	{
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("CoBaseRegUpdate_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setString(2,m.getCoba_regagent());
			c.setString(3,m.getCoba_companymanager());
			c.setString(4,m.getCoba_address());
			c.setString(5,m.getCoba_certificate());
			c.setString(6,m.getCoba_bregid());
			c.setString(7,m.getCoba_regexpires());
			c.setString(8,m.getCoba_regtime());
			c.setString(9,m.getCoba_reglimit());
			c.setString(10,m.getCoba_organcode());
			c.setString(11,m.getCoba_orregtime());
			c.setString(12,m.getCoba_organdeadline());
			c.setString(13,m.getCoba_regaccountpwd());
			c.setString(14,m.getCoba_regaccount());
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(15);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 计生责任书新增就业登记表
	public int addRbb(CoOnlineRegisterInfoModel m) {

		Integer id = 0;
		dbconn db = new dbconn();

		try {
			id = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoOnlineRegisterInfoAdd_gxj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getCid(), m.getOwnmonth(),
									m.getCori_reg_type(),
									m.getCori_reg_transact_type(),
									m.getCori_reg_account(),
									m.getCori_reg_pwd(),
									m.getCori_industry_type(),
									m.getCori_reg_fund(),
									m.getCori_belong_unit(),
									m.getCori_legal_person(),
									m.getCori_lp_tel(), m.getCori_fax(),
									m.getCori_unti_principal(),
									m.getCori_up_tel(), m.getCori_postcode(),
									m.getCori_open_date(),
									m.getCori_operate_s_date(),
									m.getCori_ss_code(), m.getCori_salary(),
									m.getCori_stock_unit1(),
									m.getCori_stock_unit2(),
									m.getCori_stock_unit3(),
									m.getCori_stock_unit4(),
									m.getCori_if_build(), m.getCori_if_pass(),
									m.getCori_if_tell(), m.getCori_all_p(),
									m.getCori_sz_p(), m.getCori_foreign_p(),
									m.getCori_sign_contract_p(),
									m.getCori_if_give(),
									m.getCori_if_build_sign_in(),
									m.getCori_wtime_type(),
									m.getCori_hour_per_day(),
									m.getCori_hour_per_week(),
									m.getCori_day_per_week(),
									m.getCori_if_ot(), m.getCori_i_work(),
									m.getCori_sc_man_hour(),
									m.getCori_salary_date(),
									m.getCori_if_arrear(),
									m.getCori_if_lowest(),
									m.getCori_if_ar_ot(),
									m.getCori_if_join_ss(), m.getCori_join_p(),
									m.getCori_if_happen_ld(),
									m.getCori_if_kid(), m.getCori_if_union(),
									m.getCwin_id(), m.getCori_reg_date(),
									m.getCori_oi_code(), m.getCori_bl_code(),
									m.getCori_oaa_place(), m.getCori_ws_name(),
									m.getCori_ws_tel(),
									m.getCori_rp_t_address(),
									m.getCori_rp_t_tel(), m.getCori_address(),
									m.getCori_state(), m.getCori_backname(),
									m.getCori_backtime(),
									m.getCori_backreason(), m.getCori_remark(),
									m.getCori_addname(),
									m.getCori_if_responsebook(),
									m.getCori_if_sign_responsebook(),
									m.getCori_web_password(),
									m.getCori_reg_state()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
}
