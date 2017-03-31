package dal.EmReg;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoOnlineRegisterInfoModel;
import Model.EmRegContactModel;
import Model.EmRegistrationInfoModel;
import Model.EmbaseModel;
import Model.HandoverPeopleModel;
import Model.PubCodeConversionModel;
import Model.PubFolkModel;
import Model.ResponsibilityBookModel;
import Model.WorkClassInfoModel;

public class EmReg_ListDal {

	public List<CoOnlineRegisterInfoModel> getEmbaseList() {
		List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select top 100 cid,coba_company,coba_shortname,gid,emba_name,cori_if_sign_responsebook,"
				+ " cori_if_responsebook,cori_state,zhtype,cori_id"
				+ " from view_cobase_embase_coonline a "
				+ " where gid not in(select gid from EmRegistrationInfo where erin_reg_state in(1,2))"
				+ " and gid in( select distinct(gid) from CoGList a inner join CoOfferList b "
				+ " on a.cgli_coli_id=b.coli_id where coli_name='劳动用工手续办理')"
				+ " and coba_servicestate=1 order by cid";

		try {
			list = db.find(sql, CoOnlineRegisterInfoModel.class,
					dbconn.parseSmap(CoOnlineRegisterInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public EmRegistrationInfoModel getEmbaseInfo(Object... objs) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cid,coba_company,coba_shortname,gid,emba_name,emba_idcard erin_idcard,"
				+ "emba_folk erin_folk,emba_sex,convert(nvarchar(10),emba_birth,120) emba_birth,"
				+ "emba_education erin_education,emba_marital erin_marital,emba_computerid erin_computerid,"
				+ "emba_party erin_party,emba_hjtype erin_hjtype,emba_hjadd erin_hjadd,"
				+ "dbo.CnCodeConversion(16,'职业技能等级',emba_skilllevel,2) erin_skilllevelcode,"
				+ "dbo.CnCodeConversion(16,'居住方式',emba_houseclass,1) erin_house_mode,"
				+ "dbo.CnCodeConversion(16,'住所类别',emba_housetype,1) erin_house_class,"
				+ "convert(nvarchar(16),emba_compactstart,120) erin_compact_s_date,"
				+ "convert(nvarchar(16),emba_compactend,120) erin_compact_e_date"
				+ " from view_cobase_embase_coonline where gid=? order by cid";
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class), objs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list.get(0);
	}

	public List<PubCodeConversionModel> getPubCodeList(String str) {
		List<PubCodeConversionModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select pcco_id,pucl_id,pcco_name,pcco_cn,pcco_code"
				+ " from PubCodeConversion where 1=1" + str;
		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<PubFolkModel> getFolkList() {
		List<PubFolkModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT pufo_id, pufo_name FROM PubFolk";
		try {
			list = db.find(sql, PubFolkModel.class,
					dbconn.parseSmap(PubFolkModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<WorkClassInfoModel> getWcinList() {
		List<WorkClassInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT wcin_code, wcin_name, wcin_addname,"
				+ "convert(nvarchar(16),wcin_addtime,120) wcin_addtime FROM WorkClassInfo";
		try {
			list = db.find(sql, WorkClassInfoModel.class,
					dbconn.parseSmap(WorkClassInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<HandoverPeopleModel> getHandoverList() {
		List<HandoverPeopleModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT hape_username,hape_channel FROM HandoverPeople"
				+ " WHERE hape_state=1 AND hape_type='就业登记' AND hape_channel"
				+ " like '%前道%' ORDER BY hape_order";
		try {
			list = db.find(sql, HandoverPeopleModel.class,
					dbconn.parseSmap(HandoverPeopleModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public String getSql(Integer flag, String cols, String cols1,
			String wherestr) {
		dbconn db = new dbconn();
		String sql = "";

		try {
			CallableStatement csmt = db
					.getcall("EmRegistrationInfoSelect_P_ply(?,?,?,?,?)");

			csmt.setInt(1, flag);
			csmt.setString(2, cols);
			csmt.setString(3, cols1);
			csmt.setString(4, wherestr);
			csmt.registerOutParameter(5, java.sql.Types.NVARCHAR);

			csmt.execute();
			sql = csmt.getString(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}

	public List<EmRegistrationInfoModel> getEmRegList(Integer flag, String str) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String col = "erin_id,a.gid,a.cid,erin_state,erin_addname,a.ownmonth,erin_datakeeptype,"
				+ "convert(nvarchar(16),erin_addtime,120) erin_addtime,statename,"
				+ "coba_shortname,emba_name,erin_hjtype,cori_sz_puzu_id,cori_wd_puzu_id,erin_t_kind,cori_id,"
				+ "case erin_hjtype when '本市城镇' then '深户' else '非深户' end as is_sh,"
				+ "case when cori_id is null then 0 else 1 end as zhtype,erin_idcard,coba_client,"
				+ "dbo.CnCodeConversion(16,'就业类型',emba_regtype,2) erin_regtypecode,"
				+ "erin_skilllevelcode,erin_phone,erin_mobile,erin_epname,erin_epmobile,erin_birthcontrol,"
				+ "erin_former_name,erin_educationcode,erin_folkcode,erin_partycode,erin_maritalcode,"
				+ "erin_hjtypecode,convert(nvarchar(16),erin_worktime,120) erin_worktime,erin_titlecode,"
				+ "convert(nvarchar(10),erin_compact_s_date,120) erin_compact_s_date,"
				+ "convert(nvarchar(10),erin_compact_e_date,120) erin_compact_e_date,"
				+ "cast(erin_salary as nvarchar(50)) erin_salary,erin_laststate,"
				+ "convert(nvarchar(10),erin_unit_b_date,120) erin_unit_b_date,"
				+ "erin_photo_number,erin_address_number,erin_idcard_address,erin_tzl_state,"
				+ "convert(nvarchar(10),erin_come_sz_date,120) erin_come_sz_date,erin_house_class,"
				+ "convert(nvarchar(10),erin_r_date,120) erin_r_date,erin_house_mode,erin_wcin_code,"
				+ "erin_stop_state,case erin_stop_state when 0 then '未终止' when 1 then '待终止' when 2 then '已终止' when 3 then '已解约' when 4 then '自动终止' end as stop_statename,erin_tapr_id";

		String col1 = "erin_id,a.gid,a.cid,erin_state,erin_addname,erin_addtime,statename,coba_shortname,"
				+ "emba_name,erin_hjtype,cori_sz_puzu_id,cori_wd_puzu_id,erin_t_kind,cori_id,erin_idcard,"
				+ "emba_regtype,erin_skilllevelcode,erin_phone,erin_mobile,a.ownmonth,erin_datakeeptype,"
				+ "erin_epname,erin_epmobile,erin_birthcontrol,erin_laststate,erin_tzl_state,"
				+ "erin_former_name,erin_educationcode,erin_folkcode,erin_partycode,erin_maritalcode,"
				+ "erin_hjtypecode,erin_worktime,erin_titlecode,erin_compact_s_date,erin_compact_e_date,"
				+ "erin_salary,erin_unit_b_date,erin_photo_number,erin_address_number,erin_idcard_address,"
				+ "erin_come_sz_date,erin_house_class,erin_r_date,erin_house_mode,erin_wcin_code,erin_stop_state,coba_client,erin_tapr_id";

		String sql = getSql(flag, col, col1, str);
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmRegistrationInfoModel> getStateList(String str) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct statename,orderid from EmRegistrationState where state=1"
				+ str + " order by orderid";
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmRegistrationInfoModel> getStateInfoList(String str) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct statename,orderid,operate from EmRegistrationState where state=1"
				+ str + " order by orderid";
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public EmRegistrationInfoModel getEmRegInfo(Integer daid, String str) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select erin_id,a.gid,a.cid,a.ownmonth,erin_t_kind,erin_idcard,erin_folk,"
				+ "erin_educationcode,erin_education,erin_marital,erin_party,erin_hjadd,erin_nowadd,"
				+ "erin_title,erin_titlecode,erin_computerid,convert(nvarchar(10),erin_unit_b_date,120) erin_unit_b_date,"
				+ "convert(nvarchar(10),erin_worktime,120) erin_worktime,"
				+ "erin_if_resident_con,erin_handover_people,erin_former_name,"
				+ "erin_photo_number,erin_hjtypecode,erin_hjtype,erin_getdata_date,erin_dw_entering,"
				+ "erin_wcin_code,erin_oe_type,erin_compact_kind,erin_if_unlimited,erin_compact_ylimit,"
				+ "convert(nvarchar(10),erin_compact_s_date,120) erin_compact_s_date,"
				+ "convert(nvarchar(10),erin_compact_e_date,120) erin_compact_e_date,"
				+ "erin_hj_in_sz_way,erin_in_sz_remark,cast(erin_salary as nvarchar(50)) erin_salary,"
				+ "erin_is_parttime,erin_idcard_address,erin_address_number,"
				+ "convert(nvarchar(10),erin_come_sz_date,120) erin_come_sz_date,"
				+ "erin_come_sz_reason,erin_house_class,erin_s_place,erin_house_mode,"
				+ "convert(nvarchar(10),erin_r_date,120) erin_r_date,"
				+ "erin_file_place,erin_in_file_people,erin_get_people,erin_stop_state,erin_stop_reason,"
				+ "convert(nvarchar(10),erin_stop_date,120) erin_stop_date,erin_stop_people,"
				+ "erin_back_people,convert(nvarchar(10),erin_back_date,120) erin_back_date,erin_back_reason,"
				+ "erin_laststate,erin_state,erin_tzl_state,erin_remark,erin_addname,"
				+ "convert(nvarchar(16),erin_addtime,120) erin_addtime,erin_reg_state,"
				+ "coba_company,coba_shortname,cori_sz_puzu_id,cori_wd_puzu_id,emba_name,erin_tapr_id,"
				+ "case when cori_id is null then 0 else 1 end as zhtype,emba_sex,"
				+ "convert(nvarchar(10),emba_birth,120) emba_birth,erin_skilllevel,"
				+ "dbo.CnCodeConversion(16,'来深居住事由',erin_come_sz_reason,1) come_sz_reason,"
				+ "dbo.CnCodeConversion(16,'住所类别',erin_house_class,1) house_class,"
				+ "dbo.CnCodeConversion(16,'居住方式',erin_house_mode,1) house_mode,"
				+ "convert(nvarchar(16),erin_in_sz_date,120) erin_in_sz_date,"
				+ "dbo.CnCodeConversion(16,'户口进入深圳方式',erin_hj_in_sz_way,1) hj_in_sz_way,"
				+ "dbo.CnCodeConversion(16,'就业类型',erin_oe_type,1) oe_type,erin_oe_type,erin_wcin,"
				+ "erin_phone,erin_mobile,erin_epname,erin_epphone,erin_epmobile,erin_birthcontrol,erin_datakeeptype "
				+ "from EmRegistrationInfo a inner join CoBase b on a.cid=b.CID "
				+ "left join CoOnlineRegisterInfo c on a.cid=c.cid inner join EmBase d on a.gid=d.gid "
				+ "where erin_id=" + daid + str;
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmRegistrationInfoModel m = new EmRegistrationInfoModel();
		if (list.size() > 0) {
			m = list.get(0);
		}
		return m;
	}

	public List<EmRegistrationInfoModel> getEmRegInfoList(String daids,
			String str) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select erin_id,a.gid,a.cid,a.ownmonth,erin_t_kind,erin_idcard,erin_folk,"
				+ "erin_educationcode,erin_education,erin_marital,erin_party,erin_hjadd,erin_nowadd,"
				+ "erin_title,erin_titlecode,erin_computerid,"
				+ "convert(nvarchar(10),erin_worktime,120) erin_worktime,"
				+ "erin_if_resident_con,erin_handover_people,erin_former_name,"
				+ "erin_photo_number,erin_hjtypecode,erin_hjtype,"
				+ "erin_wcin_code,erin_oe_type,erin_compact_kind,erin_if_unlimited,erin_compact_ylimit,"
				+ "convert(nvarchar(10),erin_compact_s_date,120) erin_compact_s_date,"
				+ "convert(nvarchar(10),erin_compact_e_date,120) erin_compact_e_date,"
				+ "erin_hj_in_sz_way,erin_in_sz_remark,cast(erin_salary as nvarchar(50)) erin_salary,"
				+ "erin_is_parttime,erin_idcard_address,erin_address_number,"
				+ "convert(nvarchar(10),erin_come_sz_date,120) erin_come_sz_date,"
				+ "erin_come_sz_reason,erin_house_class,erin_s_place,erin_house_mode,"
				+ "convert(nvarchar(10),erin_r_date,120) erin_r_date,"
				+ "erin_file_place,erin_in_file_people,erin_get_people,erin_stop_state,erin_stop_reason,"
				+ "convert(nvarchar(10),erin_stop_date,120) erin_stop_date,erin_stop_people,"
				+ "erin_back_people,convert(nvarchar(10),erin_back_date,120) erin_back_date,erin_back_reason,"
				+ "erin_laststate,erin_state,erin_tzl_state,erin_remark,erin_addname,"
				+ "convert(nvarchar(16),erin_addtime,120) erin_addtime,erin_reg_state,erin_datakeeptype,"
				+ "coba_company,coba_shortname,cori_sz_puzu_id,cori_wd_puzu_id,emba_name,erin_tapr_id "
				+ "from EmRegistrationInfo a inner join CoBase b on a.cid=b.CID "
				+ "left join CoOnlineRegisterInfo c on a.cid=c.cid inner join EmBase d on a.gid=d.gid "
				+ "where erin_id in(" + daids + ")" + str;
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean isDocExists(Integer daid, Integer puzuid) {
		Integer count = 0;
		dbconn db = new dbconn();
		String sql = "select COUNT(*) count from DocumentsSubmitInfo where dsin_tid="
				+ daid
				+ " and "
				+ " dsin_dire_id in(select dire_id from DipzRelation where dire_puzu_id="
				+ puzuid + ")";

		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count == 0 ? false : true;
	}

	public List<EmRegistrationInfoModel> getStateRelList(String str,
			Integer erin_id) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,stateid,statename,operate,type,typename,orderid,state,"
				+ " ersr_id,ersr_erin_id,ersr_stateid,ersr_addname,"
				+ " convert(nvarchar(16),ersr_addtime,120) ersr_addtime,spanstate,"
				+ " convert(nvarchar(10),ersr_statetime,120) ersr_statetime,ersr_remark"
				+ " from EmRegistrationState a"
				+ " inner join EmRegistrationStateRel b on a.stateid=b.ersr_stateid"
				+ " where ersr_erin_id="
				+ erin_id
				+ str
				+ " order by ersr_id desc";

		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据gid获取就业登记信息
	public EmRegistrationInfoModel getInfo(Integer gid) {
		EmRegistrationInfoModel model = new EmRegistrationInfoModel();
		String sql = "select * from EmRegistrationInfo where gid=" + gid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setErin_id(rs.getInt("erin_id"));
				model.setCid(rs.getInt("cid"));
				model.setErin_idcard(rs.getString("erin_idcard"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 查询就业登记终止数据
	public List<EmRegistrationInfoModel> getEmRegList(String str) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		// String sql =
		// "select erin_id,a.gid,a.cid,erin_state,erin_addname,a.ownmonth," +
		// "convert(nvarchar(16),erin_addtime,120) erin_addtime,statename,coba_shortname,"
		// +
		// "emba_name,erin_hjtype,cori_sz_puzu_id,cori_wd_puzu_id,erin_t_kind,cori_id,"
		// +
		// "case erin_hjtype when '本市城镇' then '深户' else '非深户' end as is_sh,case when "
		// +
		// "cori_id is null then 0 else 1 end as zhtype,erin_idcard,dbo.CnCodeConversion(16,"
		// +
		// "'就业类型',emba_regtype,2) erin_regtypecode,erin_skilllevelcode,erin_phone,erin_mobile,"
		// +
		// "erin_epname,erin_epmobile,erin_birthcontrol,erin_former_name,erin_educationcode,"
		// +
		// "erin_folkcode,erin_partycode,erin_maritalcode,erin_hjtypecode,convert(nvarchar(16),"
		// +
		// "erin_worktime,120) erin_worktime,erin_titlecode,convert(nvarchar(10),"
		// +
		// "erin_compact_s_date,120) erin_compact_s_date,convert(nvarchar(10),"
		// +
		// "erin_compact_e_date,120) erin_compact_e_date,cast(erin_salary as nvarchar(50)) "
		// +
		// "erin_salary,erin_laststate,convert(nvarchar(10),erin_unit_b_date,120) erin_unit_b_date,"
		// +
		// "erin_photo_number,erin_address_number,erin_idcard_address,erin_tzl_state,"
		// +
		// "convert(nvarchar(10),erin_come_sz_date,120) erin_come_sz_date,erin_house_class,"
		// +
		// "convert(nvarchar(10),erin_r_date,120) erin_r_date,erin_house_mode,erin_wcin_code,"
		// +
		// "erin_stop_state,case erin_stop_state when 0 then '未终止' when 1 then '待终止' when 2 "
		// +
		// "then '已终止' when 3 then '已解约' when 4 then '自动终止' end as stop_statename,"
		// +
		// "convert(nvarchar(16),max(case ersr_stateid when 11 Then ersr_statetime "
		// +
		// "else null end),120) as 撤销,convert(nvarchar(16),max(case ersr_stateid when 2 "
		// +
		// "Then ersr_statetime else null end),120) as 客服确认提交,convert(nvarchar(16),"
		// +
		// "max(case ersr_stateid when 3 Then ersr_statetime else null end),120) as 人事签收申报材料,"
		// +
		// "convert(nvarchar(16),max(case ersr_stateid when 4 Then ersr_statetime else null end),120) "
		// +
		// "as 人事网上申报完成,convert(nvarchar(16),max(case ersr_stateid when 5 Then ersr_statetime "
		// +
		// "else null end),120) as 客服收需盖章材料,convert(nvarchar(16),max(case ersr_stateid when 6 "
		// +
		// "Then ersr_statetime else null end),120) as 人事收已盖章材料,convert(nvarchar(16),"
		// +
		// "max(case ersr_stateid when 7 Then ersr_statetime else null end),120) as 政府部门办理完成,"
		// +
		// "convert(nvarchar(16),max(case ersr_stateid when 8 Then ersr_statetime else null "
		// +
		// "end),120) as 员工领取就业登记证明 from EmRegistrationInfo a inner join EmRegistrationStateRel b "
		// +
		// "on a.erin_id=b.ersr_erin_id inner join EmRegistrationState c on a.erin_state=c.stateid "
		// +
		// "inner join CoBase d on a.cid=d.CID inner join EmBase e on a.gid=e.gid  left join "
		// +
		// "CoOnlineRegisterInfo f on a.cid=f.cid where c.state=1 and c.type=1 and type=1  and "
		// +
		// " state=1 and erin_stop_state<>0 "+str+"group by erin_id,a.gid,a.cid,erin_state,erin_addname,"
		// +
		// "erin_addtime,statename,coba_shortname,emba_name,erin_hjtype,cori_sz_puzu_id,"
		// +
		// "cori_wd_puzu_id,erin_t_kind,cori_id,erin_idcard,emba_regtype,erin_skilllevelcode,"
		// +
		// "erin_phone,erin_mobile,a.ownmonth,erin_epname,erin_epmobile,erin_birthcontrol,"
		// +
		// "erin_laststate,erin_tzl_state,erin_former_name,erin_educationcode,erin_folkcode,"
		// +
		// "erin_partycode,erin_maritalcode,erin_hjtypecode,erin_worktime,erin_titlecode,"
		// +
		// "erin_compact_s_date,erin_compact_e_date,erin_salary,erin_unit_b_date,erin_photo_number,"
		// +
		// "erin_address_number,erin_idcard_address,erin_come_sz_date,erin_house_class,erin_r_date,"
		// +
		// "erin_house_mode,erin_wcin_code,erin_stop_state order by erin_id desc";
		String sql = "select erin_stop_state,erin_id,a.gid,a.cid,erin_state,erin_addname,a.ownmonth,"
				+ "convert(nvarchar(16),erin_addtime,120) erin_addtime,coba_shortname,emba_name,coba_client,"
				+ "erin_hjtype,erin_t_kind,case erin_hjtype when '本市城镇' then '深户' else '非深户' end as is_sh,"
				+ "erin_idcard,dbo.CnCodeConversion(16,'就业类型',emba_regtype,2) erin_regtypecode,emba_idcard,"
				+ "erin_skilllevelcode,erin_phone,erin_mobile,erin_epname,erin_epmobile,erin_birthcontrol,"
				+ "erin_former_name,erin_educationcode,erin_folkcode,erin_partycode,erin_maritalcode,"
				+ "erin_hjtypecode,convert(nvarchar(16),erin_worktime,120) erin_worktime,erin_titlecode,"
				+ "convert(nvarchar(10),erin_compact_s_date,120) erin_compact_s_date,"
				+ "convert(nvarchar(10),erin_stop_date,120) erin_stop_date,convert(nvarchar(10),erin_p_stop_date,120) erin_p_stop_date,"
				+ "convert(nvarchar(10),erin_compact_e_date,120) erin_compact_e_date,erin_handover_people,"
				+ "cast(erin_salary as nvarchar(50)) erin_salary,erin_laststate,erin_stop_people,"
				+ "convert(nvarchar(10),erin_unit_b_date,120) erin_unit_b_date,erin_photo_number,"
				+ "erin_address_number,erin_idcard_address,erin_tzl_state,convert(nvarchar(10),"
				+ "erin_come_sz_date,120) erin_come_sz_date,erin_house_class,erin_stop_reason,"
				+ "convert(nvarchar(10),erin_r_date,120) erin_r_date,erin_house_mode,erin_wcin_code,"
				+ "case erin_stop_state when 0 then '未终止' when 1 then '待终止' when 2 then "
				+ "'已终止' when 3 then '已解约' when 4 then '自动终止' end as stop_statename,erin_datakeeptype "
				+ "from EmRegistrationInfo a inner join embase b on a.gid=b.gid "
				+ "inner join CoBase c on a.cid=c.cid where erin_stop_state<>0 "
				+ str;
		sql = sql + " order by erin_stop_state";
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmRegistrationInfoModel> getStateTimeInfoList(Integer erin_id) {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select stateid,ersr_statetime from EmRegistrationState a "
				+ " inner join EmRegistrationStateRel b on a.stateid=b.ersr_stateid "
				+ " and ersr_erin_id=" + erin_id + " and typename='后道状态'";
		try {
			list = db.find(sql, EmRegistrationInfoModel.class,
					dbconn.parseSmap(EmRegistrationInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 根据gid获取员工信息
	public EmbaseModel getEmbaseModel(Integer gid) {
		EmbaseModel model = new EmbaseModel();
		String sql = "select convert(nvarchar(10),ebco_incept_date,120) ebco_incept_date,"
				+ "convert(nvarchar(10),ebco_maturity_date,120) ebco_maturity_date,ebco_change,"
				+ "a.* from embase a left join EmBaseCompact b on a.gid=b.gid and ebco_state<6 "
				+ " left join emshebaochange c on a.gid=c.cid and emsc_Computerid is not null"
				+ " where a.gid=" + gid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setGid(rs.getInt("gid"));
				model.setEmba_address(rs.getString("emba_address"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEmsc_computerid(rs.getString("emsc_computerid"));
				model.setEbco_change(rs.getString("ebco_change"));
			}

		} catch (Exception e) {
		}
		return model;
	}

	// 根据业务id和材料id查询是否已有交接材料
	public boolean ifUpdata(Integer dire_puzu_id, Integer tid) {
		boolean flag = false;
		String sql = "SELECT * FROM DocumentsSubmitInfo_Dire_DocInfo_V where dsin_state>0 AND dire_puzu_id="
				+ dire_puzu_id + " AND dsin_tid=" + tid;
		System.out.println("sql=" + sql);
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	// 根据gid查询所是否已有居住证信息
	public Integer getEmResidencePermit(Integer gid) {
		Integer k = 0;
		String sql = " select * from EmResidencePermitInfo where gid=" + gid
				+ " ORDER BY erin_id DESC";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("erpi_id");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 查询是否有材料
	public boolean isexistdata(Integer puzu_id) {
		boolean flag = false;
		String sql = "SELECT b.dire_id FROM DocumentsInfo a LEFT JOIN DipzRelation b "
				+ "ON a.doin_id=b.dire_doin_id LEFT JOIN PubZul c ON b.dire_puzu_id=c.puzu_id "
				+ "WHERE c.puzu_id="
				+ puzu_id
				+ " AND a.doin_state=1 AND b.dire_state=1";
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

	// 根据id查询终止原因
	public String getStopreson(Integer erin_id) {
		String reason = "";
		String sql = "select * from EmRegistrationInfo where erin_id="
				+ erin_id;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				reason = rs.getString("erin_stop_reason");
			}
		} catch (Exception e) {

		}
		return reason;
	}

	// 获取客服
	public List<String> getLogin() {
		List<String> list = new ArrayList<String>();
		try {
			String sql = "select log_name name from login where log_inure=1 order by log_spell";
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据cid查询公司的就业登记责任书信息
	public ResponsibilityBookModel getResponsibilityBook(Integer cid) {
		ResponsibilityBookModel model = new ResponsibilityBookModel();
		String sql = "select * from ResponsibilityBook where cid=" + cid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setRebk_id(rs.getInt("rebk_id"));
				model.setRebk_signdate(rs.getString("rebk_signdate"));
				model.setRebk_limit(rs.getString("rebk_limit"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 获取联系记录
	public List<EmRegContactModel> getEmRegContactList(Integer id, String table) {
		List<EmRegContactModel> list = new ArrayList<EmRegContactModel>();
		try {
			String sql = "select * from EmRegContact where cont_erin_id=" + id
					+ " and cont_tablename='" + table + "'";
			dbconn db = new dbconn();
			list = db.find(sql, EmRegContactModel.class,
					dbconn.parseSmap(EmRegContactModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取联系记录
	public List<EmRegContactModel> getEmRegContactListByGid(Integer gid) {
		List<EmRegContactModel> list = new ArrayList<EmRegContactModel>();
		try {
			String sql = "select * from EmRegContact where gid=" + gid;
			dbconn db = new dbconn();
			list = db.find(sql, EmRegContactModel.class,
					dbconn.parseSmap(EmRegContactModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
