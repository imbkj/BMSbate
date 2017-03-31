package dal.EmCommissionOut;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CI_Insurant_ListModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateFeeDetailModel;
import Model.EmCommissionOutPayUpdateModel;
import Util.DateStringChange;
import Util.Log4jInit;
import Util.UserInfo;

public class EmCommissionOut_OperateDal {

	/**
	 * 任务单编号更新
	 * 
	 * @param ecoc_id
	 * @param tapr_id
	 * @return
	 * @throws Exception
	 */
	public boolean UpdateTaprid(Integer daid, Integer tapr_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update EmCommissionOutChange set ecoc_tapr_id=? where ecoc_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, tapr_id);
			psmt.setInt(2, daid);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row == 1 ? true : false;
	}
	
	/**
	 * 更新年调数据状态
	 * 
	 * @param ecoc_id
	 * @param tapr_id
	 * @return
	 * @throws Exception
	 */
	public boolean Updateemcommissionyearchange(Integer ecyc_state, Integer ecyc_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update EmCommissionYearChange set ecyc_state=? where ecyc_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, ecyc_state);
			psmt.setInt(2, ecyc_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row == 1 ? true : false;
	}
	
	/**
	 * 更新年调数据状态
	 * 
	 * @param ecoc_id
	 * @param tapr_id
	 * @return
	 * @throws Exception
	 */
	public boolean Updateemcommissionyearchangebc(Integer ecyc_state, Integer ecyc_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update EmCommissionYearChange set ecyc_gxstate=? where ecyc_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, ecyc_state);
			psmt.setInt(2, ecyc_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row == 1 ? true : false;
	}


	/**
	 * 新增委托单
	 * 
	 * @param m
	 * @return
	 */
	public Integer addEmcommissionOut(EmCommissionOutChangeModel m,String wt_name) {
		dbconn db = new dbconn();
		Integer id = 0;
		try {
			
			System.out.println(m.getEcoc_id()+",");
			System.out.println(m.getGid()+",");
			System.out.println(m.getCid()+",");
			System.out.println(m.getEcoc_ecou_id()+",");
			System.out.println(m.getEcoc_soin_id()+",");
			System.out.println(m.getEcoc_ecos_id()+",");
			System.out.println(m.getEcoc_addtype()+",");
			System.out.println(m.getEcoc_type()+",");
			System.out.println(m.getEcoc_idcard()+",");
			System.out.println(m.getEcoc_email()+",");
			System.out.println(m.getEcoc_phone()+",");
			System.out.println(m.getEcoc_mobile()+","); 
			System.out.println(m.getEcoc_title_date()+",");
			System.out.println(m.getEcoc_in_date()+",");
			System.out.println(m.getEcoc_com_phone()+",");       
			System.out.println(m.getEcoc_com_principal()+",");
			System.out.println(m.getEcoc_com_company()+",");
			System.out.println(m.getEcoc_domicile()+",");
			System.out.println(m.getEcoc_compact_jud()+",");
			System.out.println(m.getEcoc_compact_f()+",");
			System.out.println(m.getEcoc_compact_l()+",");
			
			System.out.println(m.getEcoc_salary()+",");
			System.out.println(m.getEcoc_sb_base()+",");
			System.out.println(m.getEcoc_house_base()+",");
			System.out.println(m.getEcoc_sb_em_sum()+",");
			System.out.println(m.getEcoc_sb_co_sum()+",");
			System.out.println(m.getEcoc_sb_sum()+",");
			
			System.out.println(m.getEcoc_gjj_em_sum()+",");
			System.out.println(m.getEcoc_gjj_co_sum()+",");
			System.out.println(m.getEcoc_gjj_sum()+",");
			System.out.println(m.getEcoc_welfare_sum()+",");
			System.out.println(m.getEcoc_service_fee()+",");
			System.out.println(m.getEcoc_file_fee()+",");
			
			
			System.out.println(m.getEcoc_sum()+",");
			System.out.println(m.getEcoc_stop_date()+",");
			System.out.println(m.getEcoc_stop_cause()+",");
			System.out.println(m.getEcoc_cancel_cause()+",");
			System.out.println(m.getEcoc_laststate()+",");
			System.out.println(m.getEcoc_state()+",");
			
			System.out.println(m.getEcoc_client()+",");
			System.out.println(m.getEcoc_addname()+",");
			System.out.println(m.getEcoc_addtime()+",");
			System.out.println(m.getEcoc_remark()+",");
			System.out.println(m.getEcoc_tapr_id()+",");
			System.out.println(Integer.parseInt(UserInfo.getUserid())+",");
			System.out.println(m.getEcoc_cancel_id()+",");
			System.out.println(wt_name+",");

			
			
			
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcoc_id(), m.getGid(),
									m.getCid(), m.getEcoc_ecou_id(),
									m.getEcoc_soin_id(), m.getEcoc_ecos_id(),
									m.getEcoc_addtype(), m.getEcoc_type(),
									m.getEcoc_idcard(), m.getEcoc_email(),
									m.getEcoc_phone(), m.getEcoc_mobile(),
									m.getEcoc_title_date(),
									m.getEcoc_in_date(), m.getEcoc_com_phone(),
									m.getEcoc_com_principal(),
									m.getEcoc_com_company(),
									m.getEcoc_domicile(),
									m.getEcoc_compact_jud(),
									m.getEcoc_compact_f(),
									m.getEcoc_compact_l(), m.getEcoc_salary(),
									m.getEcoc_sb_base(),
									m.getEcoc_house_base(),
									m.getEcoc_sb_em_sum(),
									m.getEcoc_sb_co_sum(), m.getEcoc_sb_sum(),
									m.getEcoc_gjj_em_sum(),
									m.getEcoc_gjj_co_sum(),
									m.getEcoc_gjj_sum(),
									m.getEcoc_welfare_sum(),
									m.getEcoc_service_fee(),
									m.getEcoc_file_fee(), m.getEcoc_sum(),
									m.getEcoc_stop_date(),
									m.getEcoc_stop_cause(),
									m.getEcoc_cancel_cause(),
									m.getEcoc_laststate(), m.getEcoc_state(),
									m.getEcoc_client(), m.getEcoc_addname(),
									m.getEcoc_addtime(), m.getEcoc_remark(),
									m.getEcoc_tapr_id(),
									Integer.parseInt(UserInfo.getUserid()),
									m.getEcoc_cancel_id(),wt_name).toString());
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 更新委托历史数据
	 * 
	 * @param m
	 * @return
	 */
	public Integer updateEmcommissionOuthistroy(EmCommissionOutChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;
		System.out.println(Types.INTEGER);
		System.out.println(m.getEcoc_id());
		System.out.println(m.getGid());
		System.out.println(m.getCid());
		System.out.println(m.getEcoc_ecou_id());
		System.out.println(m.getEcoc_soin_id());
		System.out.println(m.getEcoc_ecos_id());
		System.out.println(m.getEcoc_addtype());
		System.out.println(m.getEcoc_type());
		System.out.println(m.getEcoc_idcard());
		System.out.println(m.getEcoc_email());
		System.out.println(m.getEcoc_phone());
		System.out.println(m.getEcoc_mobile());
		System.out.println(m.getEcoc_title_date());
		System.out.println(m.getEcoc_in_date());
		System.out.println(m.getEcoc_com_phone());
		System.out.println(m.getEcoc_com_principal());
		System.out.println(m.getEcoc_com_company());
		System.out.println(m.getEcoc_domicile());
		System.out.println(m.getEcoc_compact_jud());
		System.out.println(m.getEcoc_compact_f());
		System.out.println(m.getEcoc_compact_l());
		System.out.println(m.getEcoc_salary());
		System.out.println(m.getEcoc_sb_base());
		System.out.println(m.getEcoc_house_base());
		System.out.println(m.getEcoc_sb_em_sum());
		System.out.println(m.getEcoc_sb_co_sum());
		System.out.println(m.getEcoc_sb_sum());
		System.out.println(m.getEcoc_gjj_em_sum());
		System.out.println(m.getEcoc_gjj_co_sum());
		System.out.println(m.getEcoc_gjj_sum());
		System.out.println(m.getEcoc_welfare_sum());
		System.out.println(m.getEcoc_service_fee());
		System.out.println(m.getEcoc_file_fee());
		System.out.println(m.getEcoc_sum());
		System.out.println(m.getEcoc_stop_date());
		System.out.println(m.getEcoc_stop_cause());
		System.out.println(m.getEcoc_cancel_cause());
		System.out.println(m.getEcoc_laststate());
		System.out.println(m.getEcoc_state());
		System.out.println(m.getEcoc_client());
		System.out.println(m.getEcoc_addname());
		System.out.println(m.getEcoc_addtime());
		System.out.println(m.getEcoc_remark());
		System.out.println(m.getEcoc_tapr_id());
		System.out.println(Integer.parseInt(UserInfo.getUserid()));
		System.out.println(m.getEcoc_cancel_id().toString());
		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call updateEmCommissionOuthistory_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcoc_id(), m.getGid(),
									m.getCid(), m.getEcoc_ecou_id(),
									m.getEcoc_soin_id(), m.getEcoc_ecos_id(),
									m.getEcoc_addtype(), m.getEcoc_type(),
									m.getEcoc_idcard(), m.getEcoc_email(),
									m.getEcoc_phone(), m.getEcoc_mobile(),
									m.getEcoc_title_date(),
									m.getEcoc_in_date(), m.getEcoc_com_phone(),
									m.getEcoc_com_principal(),
									m.getEcoc_com_company(),
									m.getEcoc_domicile(),
									m.getEcoc_compact_jud(),
									m.getEcoc_compact_f(),
									m.getEcoc_compact_l(), m.getEcoc_salary(),
									m.getEcoc_sb_base(),
									m.getEcoc_house_base(),
									m.getEcoc_sb_em_sum(),
									m.getEcoc_sb_co_sum(), m.getEcoc_sb_sum(),
									m.getEcoc_gjj_em_sum(),
									m.getEcoc_gjj_co_sum(),
									m.getEcoc_gjj_sum(),
									m.getEcoc_welfare_sum(),
									m.getEcoc_service_fee(),
									m.getEcoc_file_fee(), m.getEcoc_sum(),
									m.getEcoc_stop_date(),
									m.getEcoc_stop_cause(),
									m.getEcoc_cancel_cause(),
									m.getEcoc_laststate(), m.getEcoc_state(),
									m.getEcoc_client(), m.getEcoc_addname(),
									m.getEcoc_addtime(), m.getEcoc_remark(),
									m.getEcoc_tapr_id(),
									Integer.parseInt(UserInfo.getUserid()),
									m.getEcoc_cancel_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 委托单补缴新增
	 * 
	 * @param m
	 * @return
	 */
	public Integer addEmcommissionOutBJ(EmCommissionOutChangeModel m,String wt_name) {
		dbconn db = new dbconn();
		Integer id = 0;
		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutBJAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcoc_id(), m.getGid(),
									m.getCid(), m.getEcoc_ecou_id(),
									m.getEcoc_soin_id(), m.getEcoc_ecos_id(),
									m.getEcoc_addtype(), m.getEcoc_type(),
									m.getEcoc_idcard(), m.getEcoc_email(),
									m.getEcoc_phone(), m.getEcoc_mobile(),
									m.getEcoc_title_date(),
									m.getEcoc_in_date(), m.getEcoc_com_phone(),
									m.getEcoc_com_principal(),
									m.getEcoc_com_company(),
									m.getEcoc_domicile(),
									m.getEcoc_compact_jud(),
									m.getEcoc_compact_f(),
									m.getEcoc_compact_l(), m.getEcoc_salary(),
									m.getEcoc_sb_base(),
									m.getEcoc_house_base(),
									m.getEcoc_sb_em_sum(),
									m.getEcoc_sb_co_sum(), m.getEcoc_sb_sum(),
									m.getEcoc_gjj_em_sum(),
									m.getEcoc_gjj_co_sum(),
									m.getEcoc_gjj_sum(),
									m.getEcoc_welfare_sum(),
									m.getEcoc_service_fee(),
									m.getEcoc_file_fee(), m.getEcoc_sum(),
									m.getEcoc_stop_date(),
									m.getEcoc_stop_cause(),
									m.getEcoc_cancel_cause(),
									m.getEcoc_laststate(), m.getEcoc_state(),
									m.getEcoc_client(), m.getEcoc_addname(),
									m.getEcoc_addtime(), m.getEcoc_remark(),
									m.getEcoc_tapr_id(),
									Integer.parseInt(UserInfo.getUserid()),
									m.getEcoc_cancel_id(), m.getEcoc_bjmonth(),wt_name)
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer addFeeDetail(EmCommissionOutFeeDetailChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutFeeDetailAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEofc_id(),
									m.getEofc_ecoc_id(), m.getEofc_eofd_id(),
									m.getEofc_sicl_id(), m.getEofc_name(),
									m.getEofc_content(), m.getEofc_em_base(),
									m.getEofc_co_base(), m.getEofc_cp(),
									m.getEofc_op(), m.getEofc_em_sum(),
									m.getEofc_co_sum(), m.getEofc_month_sum(),
									m.getEofc_start_date(),
									m.getEofc_em_fstart_date(),
									m.getEofc_co_fstart_date(),
									m.getEofc_stop_date(),
									m.getEofc_em_fstop_date(),
									m.getEofc_co_fstop_date(),
									m.getEofc_addtime(), m.getEofc_state(),
									m.getEofc_ecop_id()).toString());

			Log4jInit.toLog("委托外地新增" + id);
		} catch (Exception e) {
			Log4jInit.toLog("委托外地新增异常");
			e.printStackTrace();
		}
		return id;
	}
	
	public Integer addFeeDetailUpdate(int ecoc_id) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			CallableStatement c = db
					.getcall("EmCommissionOutFeeDetailUpdate_P_zzq(?,?)");
			c.setInt(1, ecoc_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	// 更新委托出历史表明细
	public Integer changeFeeDetailhistroy(EmCommissionOutFeeDetailChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutFeeDetailupdatehistroy_p_zmj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEofc_id(),
									m.getEofc_ecoc_id(), m.getEofc_eofd_id(),
									m.getEofc_sicl_id(), m.getEofc_name(),
									m.getEofc_content(), m.getEofc_em_base(),
									m.getEofc_co_base(), m.getEofc_cp(),
									m.getEofc_op(), m.getEofc_em_sum(),
									m.getEofc_co_sum(), m.getEofc_month_sum(),
									m.getEofc_start_date(),
									m.getEofc_em_fstart_date(),
									m.getEofc_co_fstart_date(),
									m.getEofc_stop_date(),
									m.getEofc_em_fstop_date(),
									m.getEofc_co_fstop_date(),
									m.getEofc_addtime(), m.getEofc_state(),
									m.getEofc_ecop_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer addBJFeeDetail(EmCommissionOutFeeDetailChangeModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutBJFeeDetailAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEofc_id(),
									m.getEofc_ecoc_id(), m.getEofc_eofd_id(),
									m.getEofc_sicl_id(), m.getEofc_name(),
									m.getEofc_content(), m.getEofc_em_base(),
									m.getEofc_co_base(), m.getEofc_cp(),
									m.getEofc_op(), m.getEofc_em_sum(),
									m.getEofc_co_sum(), m.getEofc_month_sum(),
									m.getEofc_start_date(),
									m.getEofc_em_fstart_date(),
									m.getEofc_co_fstart_date(),
									m.getEofc_stop_date(),
									m.getEofc_em_fstop_date(),
									m.getEofc_co_fstop_date(),
									m.getEofc_addtime(), m.getEofc_state(),
									m.getEofc_ecop_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer mod(EmCommissionOutChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutChangeMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcoc_id(), m.getGid(),
									m.getCid(), m.getEcoc_ecou_id(),
									m.getEcoc_soin_id(), m.getEcoc_ecos_id(),
									m.getEcoc_addtype(), m.getEcoc_idcard(),
									m.getEcoc_email(), m.getEcoc_phone(),
									m.getEcoc_mobile(), m.getEcoc_title_date(),
									m.getEcoc_in_date(), m.getEcoc_com_phone(),
									m.getEcoc_com_principal(),
									m.getEcoc_com_company(),
									m.getEcoc_domicile(),
									m.getEcoc_compact_jud(),
									m.getEcoc_compact_f(),
									m.getEcoc_compact_l(), m.getEcoc_salary(),
									m.getEcoc_sb_base(),
									m.getEcoc_house_base(),
									m.getEcoc_sb_em_sum(),
									m.getEcoc_sb_co_sum(), m.getEcoc_sb_sum(),
									m.getEcoc_gjj_em_sum(),
									m.getEcoc_gjj_co_sum(),
									m.getEcoc_gjj_sum(),
									m.getEcoc_welfare_sum(),
									m.getEcoc_service_fee(),
									m.getEcoc_file_fee(), m.getEcoc_sum(),
									m.getEcoc_stop_date(),
									m.getEcoc_stop_cause(),
									m.getEcoc_cancel_cause(),
									m.getEcoc_laststate(), m.getEcoc_state(),
									m.getEcoc_client(), m.getEcoc_addname(),
									m.getEcoc_addtime(), m.getEcoc_remark(),
									m.getEcoc_tapr_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer EmOutChangeReSubmit(EmCommissionOutChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutChange_ReSubmit_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcoc_id(), m.getGid(),
									m.getCid(), m.getEcoc_ecou_id(),
									m.getEcoc_soin_id(), m.getEcoc_ecos_id(),
									m.getEcoc_addtype(), m.getEcoc_idcard(),
									m.getEcoc_email(), m.getEcoc_phone(),
									m.getEcoc_mobile(), m.getEcoc_title_date(),
									m.getEcoc_in_date(), m.getEcoc_com_phone(),
									m.getEcoc_com_principal(),
									m.getEcoc_com_company(),
									m.getEcoc_domicile(),
									m.getEcoc_compact_jud(),
									m.getEcoc_compact_f(),
									m.getEcoc_compact_l(), m.getEcoc_salary(),
									m.getEcoc_sb_base(),
									m.getEcoc_house_base(),
									m.getEcoc_sb_em_sum(),
									m.getEcoc_sb_co_sum(), m.getEcoc_sb_sum(),
									m.getEcoc_gjj_em_sum(),
									m.getEcoc_gjj_co_sum(),
									m.getEcoc_gjj_sum(),
									m.getEcoc_welfare_sum(),
									m.getEcoc_service_fee(),
									m.getEcoc_file_fee(), m.getEcoc_sum(),
									m.getEcoc_stop_date(),
									m.getEcoc_stop_cause(),
									m.getEcoc_cancel_cause(),
									m.getEcoc_laststate(), m.getEcoc_state(),
									m.getEcoc_client(), m.getEcoc_addname(),
									m.getEcoc_addtime(), m.getEcoc_remark(),
									m.getEcoc_tapr_id()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer UpdateState(EmCommissionOutChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutUpdateState_P_ply(?,?,?,?,?)}",
									Types.INTEGER, m.getEcoc_id(),
									m.getEcoc_state(), m.getEcoc_addname(),
									m.getRemark(), m.getType()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer FeeDetailChangeMod(EmCommissionOutFeeDetailChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutFeeDetailChangeMod_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEofc_id(),
									m.getEofc_ecoc_id(), m.getEofc_eofd_id(),
									m.getEofc_sicl_id(), m.getEofc_name(),
									m.getEofc_content(), m.getEofc_em_base(),
									m.getEofc_co_base(), m.getEofc_cp(),
									m.getEofc_op(), m.getEofc_em_sum(),
									m.getEofc_co_sum(), m.getEofc_month_sum(),
									m.getEofc_start_date(),
									m.getEofc_em_fstart_date(),
									m.getEofc_co_fstart_date(),
									m.getEofc_stop_date(),
									m.getEofc_em_fstop_date(),
									m.getEofc_co_fstop_date(),
									m.getEofc_addtime(), m.getEofc_state())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer FeeDetailChangeReSubmit(EmCommissionOutFeeDetailChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutFeeDetailChange_ReSubmit_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEofc_id(),
									m.getEofc_ecoc_id(), m.getEofc_eofd_id(),
									m.getEofc_sicl_id(), m.getEofc_name(),
									m.getEofc_content(), m.getEofc_em_base(),
									m.getEofc_co_base(), m.getEofc_cp(),
									m.getEofc_op(), m.getEofc_em_sum(),
									m.getEofc_co_sum(), m.getEofc_month_sum(),
									m.getEofc_start_date(),
									m.getEofc_em_fstart_date(),
									m.getEofc_co_fstart_date(),
									m.getEofc_stop_date(),
									m.getEofc_em_fstop_date(),
									m.getEofc_co_fstop_date(),
									m.getEofc_addtime(), m.getEofc_state())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 离职委托单
	 * 
	 * @param m
	 * @return
	 */
	public Integer EmcommissionOutTer(EmCommissionOutChangeModel m,String wt_name) {
		dbconn db = new dbconn();
		Integer id = 0;
		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutTer_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER,
									m.getEcoc_id(),
									m.getGid(),
									m.getCid(),
									m.getEcoc_ecou_id(),
									m.getEcoc_soin_id(),
									m.getEcoc_ecos_id(),
									m.getEcoc_addtype(),
									m.getEcoc_type(),
									m.getEcoc_idcard(),
									m.getEcoc_email(),
									m.getEcoc_phone(),
									m.getEcoc_mobile(),
									m.getEcoc_title_date(),
									m.getEcoc_in_date(),
									m.getEcoc_com_phone(),
									m.getEcoc_com_principal(),
									m.getEcoc_com_company(),
									m.getEcoc_domicile(),
									m.getEcoc_compact_jud(),
									m.getEcoc_compact_f(),
									m.getEcoc_compact_l(),
									m.getEcoc_salary(),
									m.getEcoc_sb_base(),
									m.getEcoc_house_base(),
									m.getEcoc_sb_em_sum(),
									m.getEcoc_sb_co_sum(),
									m.getEcoc_sb_sum(),
									m.getEcoc_gjj_em_sum(),
									m.getEcoc_gjj_co_sum(),
									m.getEcoc_gjj_sum(),
									m.getEcoc_welfare_sum(),
									m.getEcoc_service_fee(),
									m.getEcoc_file_fee(),
									m.getEcoc_sum(),
									DateStringChange.DatetoSting(
											m.getEcoc_stop_date(), "yyyy-MM-dd"),
									m.getEcoc_stop_cause(),
									m.getEcoc_cancel_cause(),
									m.getEcoc_laststate(), m.getEcoc_state(),
									m.getEcoc_client(), m.getEcoc_addname(),
									m.getEcoc_addtime(), m.getEcoc_remark(),
									m.getEcoc_tapr_id(),
									Integer.parseInt(UserInfo.getUserid()),wt_name)
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer FeeDetailDel(Integer ecoc_id) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call EmCommissionOutFeeDetailDel_P_ply(?)}",
					Types.INTEGER, ecoc_id).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public void CheckUpdateEmOut(Integer ecoc_id) {
		dbconn db = new dbconn();
		CallableStatement csmt = db
				.getcall("EmCommissionOutCheckUpdate_P_ply(?)");
		try {
			csmt.setInt(1, ecoc_id);

			csmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer Back(EmCommissionOutChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call EmCommissionOutBack_P_ply(?,?,?,?,?)}",
					Types.INTEGER, m.getEcoc_id(), m.getEcoc_state(),
					m.getEcoc_addname(), m.getEcoc_remark(), m.getEcoc_type())
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer Cancel(EmCommissionOutChangeModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call EmCommissionOutCancel_P_ply(?,?,?,?,?,?)}",
					Types.INTEGER,
					m.getEcoc_id(),
					m.getEcoc_state(),
					m.getEcoc_cancel_cause(),
					DateStringChange.DatetoSting(m.getEcoc_cancel_date(),
							"yyyy-MM-dd"), m.getEcoc_addname(),
					m.getEcoc_type()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer EmOutPayUpdateTAdd(EmCommissionOutPayUpdateCRTModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutPayUpdateTAdd_P_ply(?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcut_id(),
									m.getEcut_name(), m.getEcut_state(),
									m.getEcut_addname(), m.getEcut_addtime(),
									m.getEcut_excelname(),
									m.getEcut_localexcelname(),
									m.getEcut_titlerow()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer EmOutPayUpdateTMod(EmCommissionOutPayUpdateCRTModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutPayUpdateTMod_P_ply(?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcut_id(),
									m.getEcut_name(), m.getEcut_state(),
									m.getEcut_addname(), m.getEcut_addtime(),
									m.getEcut_excelname(),
									m.getEcut_localexcelname(),
									m.getEcut_titlerow()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public Integer EmOutPayUpdateCAdd(EmCommissionOutPayUpdateCRTModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutPayUpdateCAdd_P_ply(?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcuc_id(),
									m.getEcuc_ecut_id(), m.getEcuc_ecpr_id(),
									m.getEcuc_excel_title(), m.getEcuc_state(),
									m.getEcuc_addname(), m.getEcuc_addtime())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer EmOutPayUpdateCDel(Integer daid) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update EmCommissionOutPayUpdateC set ecuc_state=0 where ecuc_ecut_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, daid);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public boolean isExist_Ecut_name(EmCommissionOutPayUpdateCRTModel m) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecut_id,ecut_name,ecut_state,ecut_addname,ecut_addtime"
				+ " from EmCommissionOutPayUpdateT where ecut_name='"
				+ m.getEcut_name() + "'";

		try {
			list = db.find(sql, EmCommissionOutPayUpdateCRTModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateCRTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Integer EmOutPayUpdateAdd(EmCommissionOutPayUpdateModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutPayUpdateAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEcpu_id(), m.getCid(),
									m.getGid(), m.getEcpu_company(),
									m.getEcpu_name(), m.getEcpu_cabc_id(),
									m.getEcpu_idcard(), m.getEcpu_hk(),
									m.getOwnmonth(), m.getEcpu_change(),
									m.getEcpu_lx(), m.getEcpu_base(),
									m.getEcpu_sb_co_total(),
									m.getEcpu_sb_em_total(),
									m.getEcpu_yl_co_total(),
									m.getEcpu_yl_em_total(),
									m.getEcpu_shy_em_total(),
									m.getEcpu_shy_co_total(),
									m.getEcpu_yil_co_total(),
									m.getEcpu_yil_em_total(),
									m.getEcpu_bchyil_co_total(),
									m.getEcpu_bchyil_em_total(),
									m.getEcpu_shyu_co_total(),
									m.getEcpu_shyu_em_total(),
									m.getEcpu_gsh_co_total(),
									m.getEcpu_gsh_em_total(),
									m.getEcpu_gjj_co_total(),
									m.getEcpu_gjj_em_total(),
									m.getEcpu_bchgjj_co_total(),
									m.getEcpu_bchgjj_em_total(),
									m.getEcpu_sb_total(),
									m.getEcpu_gjj_total(),
									m.getEcpu_bcgjj_total(),
									m.getEcpu_other_total(),
									m.getEcpu_welfare_total(),
									m.getEcpu_total(), m.getEcpu_state(),
									m.getEcpu_addtime(), m.getEcpu_addname(),
									m.getEcpu_remark(), m.getEcpu_client(),
									m.getBjownmonth()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Integer EmOutPayUpdateFeeDetailAdd(
			EmCommissionOutPayUpdateFeeDetailModel m) {
		dbconn db = new dbconn();
		Integer id = 0;

		try {
			id = Integer
					.parseInt(db
							.callWithReturn(
									"{?=call EmCommissionOutPayUpdateFeeDetailAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, m.getEpfd_id(),
									m.getEpfd_ecpu_id(), m.getEpfd_sicl_id(),
									m.getEpfd_ecop_id(), m.getEpfd_name(),
									m.getEpfd_content(), m.getEpfd_base(),
									m.getEpfd_cp(), m.getEpfd_op(),
									m.getEpfd_em_sum(), m.getEpfd_co_sum(),
									m.getEpfd_month_sum(),
									m.getEpfd_start_date(),
									m.getEpfd_em_fstart_date(),
									m.getEpfd_co_fstart_date(),
									m.getEpfd_stop_date(),
									m.getEpfd_em_fstop_date(),
									m.getEpfd_co_fstop_date(),
									m.getEpfd_addtime(), m.getEpfd_state())
							.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	// 获取该委托任务单信息
			public List<EmCommissionOutChangeModel> gettali_list(int tapr_id)
					throws SQLException {
				dbconn db = new dbconn();
				ResultSet rs = null;
				
				List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
				String sqlstr = "select tali_id,tali_name from TaskProcess a left join TaskList b on b.tali_id=a.tapr_tali_id where tapr_id="+tapr_id;
				System.out.println(sqlstr);
				try {
					rs = db.GRS(sqlstr);
					while (rs.next()) {
						EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
						model.setC1(Integer.parseInt(rs.getString("tali_id")));
						model.setCoab_name(rs.getString("tali_name"));
						list.add(model);
					}
				} catch (Exception e) {
					System.out.print(e.toString());
				} finally {
					db.Close();
				}
				return list;
			}

	public Integer EmOutHisAdd(String ownmonth) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call EmCommissionOutHistoryAdd_P_ply(?)}",
					Types.INTEGER, ownmonth).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
}
