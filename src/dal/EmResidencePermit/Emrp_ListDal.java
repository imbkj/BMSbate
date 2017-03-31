package dal.EmResidencePermit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoOnlineRegisterInfoModel;
import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;

public class Emrp_ListDal {
	public List<CoOnlineRegisterInfoModel> getEmbaseList() {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select top 200 a.cid,coba_company,coba_shortname,a.gid,emba_name,cori_if_sign_responsebook,cori_if_responsebook,cori_state,cori_id,erin_id,"
				+ " erin_reg_state,emba_idcard,erin_hjtype,case when cori_id is null then 0 else 1 end as zhtype"
				+ " from EmBase a left join (select gid,erin_id,erin_reg_state,erin_hjtype from EmRegistrationInfo where erin_reg_state=1)b on a.gid=b.gid"
				+ " left join (select cori_id,cid,cori_if_sign_responsebook,cori_if_responsebook,cori_state from CoOnlineRegisterInfo where cori_reg_state=1)c on a.cid=c.cid"
				+ " inner join (select cid,coba_company,coba_shortname from CoBase where coba_servicestate=1)d on a.cid=d.CID";
		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmResidencePermitInfoModel> getEmrpList(String str) {
		dbconn db = new dbconn();
		List<EmResidencePermitInfoModel> list = new ArrayList<>();
		String sql = "select erpi_id,a.gid,a.cid,emba_name,coba_shortname,a.ownmonth,erin_id,"
				+ " erpi_w_photo_number,erpi_w_skill_level,erpi_w_a_sz_date,erpi_w_r_date,"
				+ " erpi_w_house_class,erpi_w_r_mode,erpi_t_kind,erpi_handover_people,erpi_dw_entering,"
				+ " erpi_payment_kind,erpi_payment_state,erpi_fee,erpi_fee_state,erpi_if_invoice,"
				+ " erpi_app_type,erpi_app_con,erpi_send_target,"
				+ " convert(nvarchar(16),erpi_invoice_date,120) erpi_invoice_date,erpi_cl_number,"
				+ " erpi_wd_applicant,convert(nvarchar(16),erpi_wd_loan_date,120) erpi_wd_loan_date,"
				+ " convert(nvarchar(16),erpi_ri_date,120) erpi_ri_date,erpi_csd_applicant,"
				+ " convert(nvarchar(16),erpi_csd_loan_date,120) erpi_csd_loan_date,"
				+ " convert(nvarchar(16),erpi_csd_clearing_date,120) erpi_csd_clearing_date,"
				+ " erpi_return_people,convert(nvarchar(16),erpi_return_date,120) erpi_return_date,"
				+ " erpi_return_reason,erpi_laststate,erpi_state,erpi_reg_state,erpi_tzl_state,"
				+ " erpi_remark,erpi_addname,convert(nvarchar(16),erpi_addtime,120) erpi_addtime,statename,"
				+ " case when cori_id is null then 0 else 1 end as zhtype,erpi_tapr_id"
				+ " from EmResidencePermitInfo a inner join EmResidencePermitState b"
				+ " on a.erpi_state=b.stateid inner join EmBase c on a.gid=c.gid"
				+ " inner join CoBase d on a.cid=d.CID"
				+ " left outer join CoOnlineRegisterInfo f on a.cid=f.cid where 1=1"
				+ str;

		try {
			list = db.find(sql, EmResidencePermitInfoModel.class,
					dbconn.parseSmap(EmResidencePermitInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public EmResidencePermitInfoModel getEmrpInfo(Integer erpi_id) {
		dbconn db = new dbconn();
		List<EmResidencePermitInfoModel> list = new ArrayList<>();
		String sql = "select erpi_id,a.gid,a.cid,emba_idcard,emba_name,coba_company,a.ownmonth,erin_id,"
				+ " erpi_w_photo_number,erpi_w_skill_level,erpi_w_a_sz_date,erpi_w_r_date,"
				+ " erpi_w_house_class,erpi_w_r_mode,erpi_t_kind,erpi_handover_people,erpi_dw_entering,"
				+ " erpi_payment_kind,erpi_payment_state,erpi_fee,erpi_fee_state,erpi_if_invoice,"
				+ " erpi_app_type,erpi_app_con,erpi_send_target,erpi_tapr_id,"
				+ " convert(nvarchar(16),erpi_invoice_date,120) erpi_invoice_date,erpi_cl_number,"
				+ " erpi_wd_applicant,convert(nvarchar(16),erpi_wd_loan_date,120) erpi_wd_loan_date,"
				+ " convert(nvarchar(16),erpi_ri_date,120) erpi_ri_date,erpi_csd_applicant,"
				+ " convert(nvarchar(16),erpi_csd_loan_date,120) erpi_csd_loan_date,"
				+ " convert(nvarchar(16),erpi_csd_clearing_date,120) erpi_csd_clearing_date,"
				+ " erpi_return_people,convert(nvarchar(16),erpi_return_date,120) erpi_return_date,"
				+ " erpi_return_reason,erpi_laststate,erpi_state,erpi_reg_state,erpi_tzl_state,"
				+ " erpi_remark,erpi_addname,convert(nvarchar(16),erpi_addtime,120) erpi_addtime,statename,"
				+ " case when cori_id is null then 0 else 1 end as zhtype,"
				+ " dbo.CnCodeConversion(3,'收费状态',erpi_fee_state,1) fee_state,"
				+ " dbo.CnCodeConversion(3,'申办居住证类型',erpi_app_type,1) app_type,"
				+ " dbo.CnCodeConversion(3,'申领居住证条件',erpi_app_con,1) app_con,erpi_ifedit"
				+ " from EmResidencePermitInfo a inner join EmResidencePermitState b"
				+ " on a.erpi_state=b.stateid inner join EmBase c on a.gid=c.gid"
				+ " inner join CoBase d on a.cid=d.CID "
				+ " left outer join CoOnlineRegisterInfo f on a.cid=f.cid where erpi_id="
				+ erpi_id;

		try {
			list = db.find(sql, EmResidencePermitInfoModel.class,
					dbconn.parseSmap(EmResidencePermitInfoModel.class));
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : new EmResidencePermitInfoModel();
	}

	public EmResidencePermitInfoModel getEmrpInfoModel(Integer erpi_id) {
		dbconn db = new dbconn();
		List<EmResidencePermitInfoModel> list = new ArrayList<>();
		String sql = "select erpi_id,a.gid,a.cid,emba_name,coba_company,a.ownmonth,erin_id,"
				+ " erpi_w_photo_number,erpi_w_skill_level,erpi_w_a_sz_date,erpi_w_r_date,"
				+ " erpi_w_house_class,erpi_w_r_mode,erpi_t_kind,erpi_handover_people,erpi_dw_entering,"
				+ " erpi_payment_kind,erpi_payment_state,erpi_fee,erpi_fee_state,erpi_if_invoice,"
				+ " erpi_app_type,erpi_app_con,erpi_send_target,erpi_tapr_id,"
				+ " convert(nvarchar(16),erpi_invoice_date,120) erpi_invoice_date,erpi_cl_number,"
				+ " erpi_wd_applicant,convert(nvarchar(16),erpi_wd_loan_date,120) erpi_wd_loan_date,"
				+ " convert(nvarchar(16),erpi_ri_date,120) erpi_ri_date,erpi_csd_applicant,"
				+ " convert(nvarchar(16),erpi_csd_loan_date,120) erpi_csd_loan_date,"
				+ " convert(nvarchar(16),erpi_csd_clearing_date,120) erpi_csd_clearing_date,"
				+ " erpi_return_people,convert(nvarchar(16),erpi_return_date,120) erpi_return_date,"
				+ " erpi_return_reason,erpi_laststate,erpi_state,erpi_reg_state,erpi_tzl_state,"
				+ " erpi_remark,erpi_addname,convert(nvarchar(16),erpi_addtime,120) erpi_addtime,statename,"
				+ " case when cori_id is null then 0 else 1 end as zhtype,"
				+ " dbo.CnCodeConversion(3,'收费状态',erpi_fee_state,1) fee_state,"
				+ " dbo.CnCodeConversion(3,'申办居住证类型',erpi_app_type,1) app_type,"
				+ " dbo.CnCodeConversion(3,'申领居住证条件',erpi_app_con,1) app_con"
				+ " from EmResidencePermitInfo a inner join EmResidencePermitState b"
				+ " on a.erpi_state=b.stateid inner join EmBase c on a.gid=c.gid"
				+ " inner join CoBase d on a.cid=d.CID"
				+ " left outer join CoOnlineRegisterInfo f on a.cid=f.cid where erpi_id="
				+ erpi_id;
		try {
			list = db.find(sql, EmResidencePermitInfoModel.class,
					dbconn.parseSmap(EmResidencePermitInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : new EmResidencePermitInfoModel();
	}

	public List<EmResidencePermitInfoModel> getStateList(String str) {
		dbconn db = new dbconn();
		List<EmResidencePermitInfoModel> list = new ArrayList<>();
		String sql = "select distinct statename,orderid from EmResidencePermitState where state=1"
				+ str + " order by orderid";
		try {
			list = db.find(sql, EmResidencePermitInfoModel.class,
					dbconn.parseSmap(EmResidencePermitInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmResidencePermitInfoModel> getStateInfoList(String str) {
		dbconn db = new dbconn();
		List<EmResidencePermitInfoModel> list = new ArrayList<>();
		String sql = "select distinct statename,orderid,operate from EmResidencePermitState where state=1"
				+ str + " order by orderid";
		try {
			list = db.find(sql, EmResidencePermitInfoModel.class,
					dbconn.parseSmap(EmResidencePermitInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmResidencePermitInfoModel> getStateRelList(String str,
			Integer erpi_id) {
		List<EmResidencePermitInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,stateid,statename,operate,type,typename,orderid,state,"
				+ " epsr_id,epsr_erpi_id,epsr_stateid,epsr_addname,"
				+ " convert(nvarchar(16),epsr_addtime,120) epsr_addtime,spanstate,"
				+ " convert(nvarchar(10),epsr_statetime,120) epsr_statetime,epsr_remark"
				+ " from EmResidencePermitState a"
				+ " inner join EmResidencePermitStateRel b on a.stateid=b.epsr_stateid"
				+ " where epsr_erpi_id="
				+ erpi_id
				+ str
				+ " order by epsr_id desc";

		try {
			list = db.find(sql, EmResidencePermitInfoModel.class,
					dbconn.parseSmap(EmResidencePermitInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmResidencePermitChangeModel> getErpcList(String str) {
		dbconn db = new dbconn();
		List<EmResidencePermitChangeModel> list = new ArrayList<>();
		String sql = "select erpc_id,a.gid,a.cid,emba_name,coba_shortname,erpc_erin_id,a.ownmonth,"
				+ "erpc_idcard,erpc_laststate,erpc_state,erpc_return_people,erpc_return_date,"
				+ "erpc_return_reason,erpc_addname,erpc_addtime,erpc_tapr_id,"
				+ "case when cori_id is null then 0 else 1 end as zhtype "
				+ "from EmResidencePermitChange a inner join EmBase b on a.gid=b.gid "
				+ "inner join CoBase c on a.cid=c.CID left join CoOnlineRegisterInfo d "
				+ "on a.cid=d.cid where 1=1" + str;
		System.out.println(sql);
		try {
			list = db.find(sql, EmResidencePermitChangeModel.class,
					dbconn.parseSmap(EmResidencePermitChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询居住证信息
	public EmResidencePermitInfoModel getEmResidencePermitInfo(Integer gid) {
		EmResidencePermitInfoModel m = new EmResidencePermitInfoModel();
		String sql = "select * from EmResidencePermitInfo where gid=" + gid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setErin_id(rs.getInt("erin_id"));
				m.setErpi_id(rs.getInt("erpi_id"));
				m.setErpi_state(rs.getInt("erpi_state"));
				m.setErpi_t_kind(rs.getString("erpi_t_kind"));
				m.setErpi_ifedit(rs.getString("erpi_ifedit"));
			}
		} catch (Exception e) {

		}
		return m;
	}

	// 查询员工是否有为办理完成的居住证新办信息
	public EmResidencePermitInfoModel getEmResidencePermit(Integer gid,
			String str) {
		EmResidencePermitInfoModel m = new EmResidencePermitInfoModel();
		String sql = "select * from EmResidencePermitInfo where gid=" + gid
				+ str;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setErin_id(rs.getInt("erin_id"));
				m.setErpi_id(rs.getInt("erpi_id"));
				m.setErpi_state(rs.getInt("erpi_state"));
				m.setErpi_t_kind(rs.getString("erpi_t_kind"));
				m.setErpi_ifedit(rs.getString("erpi_ifedit"));
			}
		} catch (Exception e) {

		}
		return m;
	}

	// 根据cid查询公司就业登记信息
	public CoOnlineRegisterInfoModel getCoOnlineRegisterInfo(Integer cid) {
		CoOnlineRegisterInfoModel model = new CoOnlineRegisterInfoModel();
		try {
			String sql = "select * from CoOnlineRegisterInfo where (cori_reg_type='新开户' or cori_reg_type='接管') and cid="
					+ cid;
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setCori_id(rs.getInt("cori_id"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 根据gid查询员工就业登记信息
	public EmRegistrationInfoModel getEmRegistrationInfo(Integer gid) {
		EmRegistrationInfoModel model = new EmRegistrationInfoModel();
		try {
			String sql = "select * from EmRegistrationInfo where gid=" + gid;
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setErin_id(rs.getInt("erin_id"));
			}
		} catch (Exception e) {

		}
		return model;
	}
}
