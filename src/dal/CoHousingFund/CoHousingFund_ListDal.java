package dal.CoHousingFund;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Model.EmHouseCpp;
import Model.PubAreaSZModel;
import Model.PubCoEcoclassModel;
import Model.PubCoNatureModel;
import Model.PubGjBankModel;
import Model.PubIdcardTypeModel;
import Model.PubMemberShipModel;
import Model.PubStateModel;
import Model.PubStateRelModel;
import Model.PubTsbankModel;
import Util.UserInfo;

public class CoHousingFund_ListDal {
	// 查询托收日列表
	public List<CoHousingFundModel> tsDateList() {
		List<CoHousingFundModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cohf_tsday from CoHousingFund where cohf_State=1";
		try {
			list = db.find(sql, CoHousingFundModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询托收日
	public List<CoHousingFundModel> tsdate(Integer gid, Double cpp) {
		List<CoHousingFundModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cohf_tsday,cohf_lastday,cohf_fwday,cohf_single"
				+ " from embase a"
				+ " inner join cobase g on a.cid=g.cid"
				+ " inner join login h on g.coba_client=h.log_name and log_inure=1"
				+ " inner join coglist b on a.gid=b.gid and cgli_stopdate is null"
				+ " inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='住房公积金服务'"
				+ " inner join CoOffer d on c.coli_coof_id=d.coof_id"
				+ " inner join (select coco_id,cid,coco_cpp,case coco_house when '中智开户' then 0 when '独立开户' then 1 end single from CoCompact where coco_state>3 and coco_state!=9) e on d.coof_coco_id=e.coco_id"
				+ " inner join CoHousingFund f on f.cohf_state=1 and e.single=f.cohf_single"
				+ "  and ((e.single=1 and a.cid=f.cid) or (e.single=0 and f.cid is null and cohf_bankjc=case dep_id when 2 then '中信银行' else '中国银行' end "
				+ "	and convert(decimal(10,2),cohf_cpp)=convert(decimal(10,2),?)))"
				+ " where a.gid=?";
		try {
			list = db.find(sql, CoHousingFundModel.class, null, cpp, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoBaseModel> getcobaseList(Integer id) {
		List<CoBaseModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,'('+coba_spell+')'+coba_company coba_company,coba_company company,coba_shortname,coba_client "
				+ "from CoCompact a "
				+ "inner join CoBase b on a.cid=b.cid "
				+ "inner join CoOffer c on a.coco_id=c.coof_coco_id "
				+ "inner join CoOfferList d on  c.coof_id=d.coli_coof_id	"
				+ "where coli_id=?";

		try {
			list = db.find(sql, CoBaseModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoBaseModel> getcobaseinfoList(Integer cid) {
		List<CoBaseModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,'('+coba_spell+')'+coba_company coba_company,coba_company company,coba_shortname,coba_client "
				+ "from CoCompact a "
				+ "inner join CoBase b on a.cid=b.cid "
				+ "where a.cid=?";

		try {
			list = db.find(sql, CoBaseModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundChangeModel> getCobaList(Integer cid) {
		List<CoHousingFundChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cid,'('+coba_spell+')'+coba_company company,coba_company,coba_shortname,"
				+ "coba_client chfc_client,coba_organcode chfc_comid,coba_address chfc_address,"
				+ "coba_companymanager chfc_corname"
				+ " from cobase where coba_servicestate=1 and cid not in(select cid from cohousingfund"
				+ " where cohf_state in(1,2) and cid is not null) ";
		if (cid != null) {
			if (!cid.equals("")) {
				sql = sql + " and cid=" + cid;
			}
		}
		sql = sql + " order by coba_spell";

		try {
			list = db.find(sql, CoHousingFundChangeModel.class,
					dbconn.parseSmap(CoHousingFundChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundModel> getlist(CoHousingFundModel cm) {
		List<CoHousingFundModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cohf_id,cid,cohf_company,ownmonth,cohf_houseid,cohf_addtype,cohf_cpp,cohf_tsday,cohf_lastday,cohf_state,cohf_if_edit,"
				+ "convert(varchar(10),year(GETDATE()))+'-'+convert(varchar(10),month(GETDATE()))+'-'+convert(varchar(10),cohf_lastday)lastdate "
				+ "from CoHousingFund where 1=1";
		if (cm.getCohf_state() != null) {
			if (!cm.getCohf_state().equals("")) {
				sql = sql + " and cohf_state=" + cm.getCohf_state();
			}
		}
		if (cm.getInure() != null) {
			if (cm.getInure()) {
				sql = sql + " and isnull(cohf_state,0)!=0";
			}
		}
		if (cm.getCohf_single() != null) {
			if (!cm.getCohf_single().equals("")) {
				sql = sql + " and cohf_single=" + cm.getCohf_single();
			}
		}

		if (cm.getCid() != null) {
			if (!cm.getCid().equals("")) {
				sql = sql + " and cid=" + cm.getCid();
			}
		}

		if (cm.getCohf_company() != null) {
			if (!cm.getCohf_company().equals("")) {
				sql = sql + " and cohf_company like '%" + cm.getCohf_company()
						+ "%'";
			}
		}

		if (cm.getCohf_cpp() != null) {
			if (!cm.getCohf_cpp().equals("")) {
				sql = sql + " and cohf_cpp like '" + cm.getCohf_cpp() + "'";
			}
		}
		if (cm.getCohf_lastday() != null) {
			if (!cm.getCohf_lastday().equals("")) {
				sql = sql + " and cohf_lastday=" + cm.getCohf_lastday();
			}
		}

		sql += " order by cohf_tsday";
		try {
			list = db.find(sql, CoHousingFundModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseCpp> getcpplist() {
		List<EmHouseCpp> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cohf_houseid companyid,cohf_cpp cp,cohf_cpp op,convert(varchar(10),(cohf_cpp*100))+'%' cpName "
				+ " from CoHousingFund a"
				+ " where cid is null and cohf_state=1" + " order by cohf_cpp";
		try {
			list = db.find(sql, EmHouseCpp.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseCpp> getcpplist(Integer cid) {
		List<EmHouseCpp> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cohf_houseid companyid,cohf_cpp cp,cohf_cpp op,convert(varchar(10),(cohf_cpp*100))+'%' cpName,cohf_bankjc jc "
				+ " from CoHousingFund a"
				+ " inner join cobase b on b.cid=?"
				+ " inner join login c on b.coba_Client=c.log_name and log_inure=1"
				+ " where a.cid is null and cohf_state=1 and ((dep_id=2 and cohf_bankjc='中信银行') or (dep_id!=2 and cohf_bankjc='中国银行'))"
				+ " order by cohf_cpp";
		try {
			list = db.find(sql, EmHouseCpp.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundModel> getListByHouseId(String houseId) {
		List<CoHousingFundModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cohf_id,cid,cohf_houseid,cohf_cpp,cohf_single from CoHousingFund where  cohf_houseid=?";
		try {
			list = db.find(sql, CoHousingFundModel.class, null, houseId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubCoNatureModel> getCoNatureList() {
		List<PubCoNatureModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from pubconature";
		try {
			list = db.find(sql, PubCoNatureModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubCoEcoclassModel> getCoEcoclassList() {
		List<PubCoEcoclassModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from pubcoecoclass";
		try {
			list = db.find(sql, PubCoEcoclassModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubGjBankModel> getGjBankList() {
		List<PubGjBankModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from pubgjbank order by bank";
		try {
			list = db.find(sql, PubGjBankModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubTsbankModel> getTsBankList() {
		List<PubTsbankModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from pubtsbank";
		try {
			list = db.find(sql, PubTsbankModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubAreaSZModel> getAreaSzList() {
		List<PubAreaSZModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from pubareasz";
		try {
			list = db.find(sql, PubAreaSZModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubMemberShipModel> getMemberShipList() {
		List<PubMemberShipModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from PubMemberShip";
		try {
			list = db.find(sql, PubMemberShipModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubIdcardTypeModel> getIdcardTypeList() {
		List<PubIdcardTypeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from PubIdcardType";
		try {
			list = db.find(sql, PubIdcardTypeModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubStateRelModel> getHosList(Integer daid, String str) {
		List<PubStateRelModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select pbsr_id,pbsr_daid,pbsr_statename,pbsr_content,pbsr_type,pbsr_addtime,"
				+ "pbsr_addname,pbsr_statetime,pbsr_remark from PubStateRel"
				+ " where pbsr_daid=" + daid + str + " order by pbsr_id desc";

		try {
			list = db.find(sql, PubStateRelModel.class,
					dbconn.parseSmap(PubStateRelModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubStateModel> getStateList(String str) {
		List<PubStateModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select statename,operate,stateid from PubState where state=1"
				+ str + " order by orderid";

		try {
			list = db.find(sql, PubStateModel.class,
					dbconn.parseSmap(PubStateModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundChangeModel> getCoHoChangeList(String str) {
		List<CoHousingFundChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select chfc_id,a.cid,coba_client,b.coba_shortname,b.coba_company,ownmonth,chfc_cohf_id,"
				+ "chfc_houseid,chfc_addtype,chfc_cpp,chfc_client,chfc_bankts,chfc_banktsid,"
				+ "chfc_banktsacc,chfc_bankjc,chfc_bankjcid,chfc_bankgj,chfc_banklk,chfc_tsday,"
				+ "chfc_addtime,chfc_addname,chfc_laststate,chfc_state,chfc_tzlstate,chfc_remark,"
				+ "chfc_sorid,chfc_stop_type,chfc_stop_reason,chfc_return_reason,chfc_comid,"
				+ "chfc_zgtype,chfc_address,chfc_area,chfc_pastal,chfc_nature,chfc_ecoclass,"
				+ "chfc_industry,chfc_attached,chfc_corname,chfc_coridtype,chfc_coridcard,"
				+ "chfc_cortel,chfc_department,chfc_departmenttel,chfc_createtime,chfc_regid,"
				+ "chfc_taxpayerid,chfc_jbdepartment,chfc_contactname,chfc_contacttel,chfc_contactmail,"
				+ "chfc_contactmobile,chfc_firmonth,chfc_ispwd,chfc_tapr_id,chfc_changestr,chfc_puzu_id,"
				+ "chfc_completetime,c.statename,case when chfc_ispwd=1 then '有' else '无' end as ispwd,"
				+ "chfc_if_update_compact,cast(chfc_cpp*100 as int)cpp,chfc_company,chfc_lastemcount,"
				+ "chfc_lastsum,chfc_start_month,chfc_end_month,chfc_last_month,chfc_ifstop_low,"
				+ "chfc_ifstop_hj,chfc_backreason,chfc_tapr_id,chfc_file,chfc_recevicedate "
				+ " from CoHousingFundChange a "
				+ "	left join (select CID,coba_company,coba_client,coba_shortname from CoBase)b on a.cid=b.CID"
				+ " left join PubState c on a.chfc_state=c.stateid and c.type='cogjj'"
				+ " where 1=1"
				+ " and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and dat_selected=1 ) "
				+ str
				+ "  order by case coba_client when '"
				+ UserInfo.getUsername()
				+ "' then 0 else  1 end ,  chfc_id desc";
		System.out.println(sql);
		try {
			list = db.find(sql, CoHousingFundChangeModel.class,
					dbconn.parseSmap(CoHousingFundChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundChangeModel> getCoHoChangeList(Integer id) {
		List<CoHousingFundChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select chfc_id,a.cid,b.coba_shortname,b.coba_company,ownmonth,chfc_cohf_id,"
				+ "chfc_houseid,chfc_addtype,chfc_cpp,chfc_client,chfc_bankts,chfc_banktsid,"
				+ "chfc_banktsacc,chfc_bankjc,chfc_bankjcid,chfc_bankgj,chfc_banklk,chfc_tsday,"
				+ "chfc_addtime,chfc_addname,chfc_laststate,chfc_state,chfc_tzlstate,chfc_remark,"
				+ "chfc_sorid,chfc_stop_type,chfc_stop_reason,chfc_return_reason,chfc_comid,"
				+ "chfc_zgtype,chfc_address,chfc_area,chfc_pastal,chfc_nature,chfc_ecoclass,"
				+ "chfc_industry,chfc_attached,chfc_corname,chfc_coridtype,chfc_coridcard,"
				+ "chfc_cortel,chfc_department,chfc_departmenttel,chfc_createtime,chfc_regid,"
				+ "chfc_taxpayerid,chfc_jbdepartment,chfc_contactname,chfc_contacttel,chfc_contactmail,"
				+ "chfc_contactmobile,chfc_firmonth,chfc_ispwd,chfc_tapr_id,chfc_changestr,chfc_puzu_id,"
				+ "chfc_completetime,c.statename,case when chfc_ispwd=1 then '1' else '0' end as ispwd,"
				+ "chfc_if_update_compact,cast(chfc_cpp*100 as int)cpp,chfc_company,chfc_lastemcount,"
				+ "chfc_lastsum,chfc_start_month,chfc_end_month,chfc_last_month,chfc_ifstop_low,"
				+ "chfc_ifstop_hj,chfc_backreason"
				+ " from CoHousingFundChange a left join (select CID,coba_company,coba_shortname"
				+ " from CoBase)b on a.cid=b.CID left join PubState c on a.chfc_state=c.stateid"
				+ " and c.type='cogjj' where 1=1";
		if (id != null && id > 0) {
			sql += " and chfc_id=" + id;
		}
		try {
			list = db.find(sql, CoHousingFundChangeModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List<CoHousingFundModel> getCoHoList(String str, boolean mod) {
		List<CoHousingFundModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		if (mod) {
			str += " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			str += " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}

		String sql = "select cohf_id,a.cid,coba_shortname,coba_company,ownmonth,cohf_houseid,"
				+ "cohf_addtype,cohf_cpp,cohf_client,cohf_bankts,cohf_banktsid,cohf_banktsacc,"
				+ "cohf_bankjc,cohf_bankjcid,cohf_bankgj,cohf_banklk,cohf_tsday,cohf_lastday,"
				+ "cohf_addtime,cohf_addname,cohf_state,cohf_sorid,cohf_stop_type,cohf_stop_reason,"
				+ "cohf_comid,cohf_zgtype,cohf_address,cohf_area,cohf_pastal,cohf_nature,cohf_ecoclass,"
				+ "cohf_industry,cohf_attached,cohf_corname,cohf_coridtype,cohf_coridcard,cohf_cortel,"
				+ "cohf_department,cohf_departmenttel,cohf_createtime,cohf_regid,cohf_taxpayerid,"
				+ "cohf_jbdepartment,cohf_contactname,cohf_contacttel,cohf_contactmail,cohf_contactmobile,"
				+ "cohf_firmonth,cohf_ispwd,cohf_single,cohf_completetime,cohf_manstate,cohf_if_edit,"
				+ "convert(nvarchar(10),cohf_addtime,120)cohf_addtimeString,cohf_company,"
				+ "convert(nvarchar(10),cohf_completetime,120)cohf_completetimeString,"
				+ "case when cohf_ispwd=1 then '有' else '无' end as ispwd,coba_client,"
				+ "cast(cohf_cpp*100 as int)cpp,cohf_start_month,cohf_end_month,cohf_if_low,cohf_if_hj,"
				+ "cast(convert(nvarchar(6),dateadd(month,-1,getdate()),112) as int)last_month,"
				+ "case cohf_state when 1 then '服务中' when 2 then '申报中'"
				+ " when 0 then '终止服务' end as statename,"
				+ "(select COUNT(emhu_id) from EmHouseUpdate where emhu_ifstop=0 and cid=a.cid)em_notstop_count"
				+ " from CoHousingFund a left join CoBase b on a.cid=b.CID where 1=1"
				+ str

				+ "  order by case coba_client when '"
				+ UserInfo.getUsername()
				+ "' then 0 else  1 end , case cohf_state when 1 then 0 when 2 then 1 else 3 end,cohf_id desc";

		try {
			list = db.find(sql, CoHousingFundModel.class,
					dbconn.parseSmap(CoHousingFundModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundZbModel> getZbList(String str) {
		List<CoHousingFundZbModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select chfz_id,chfz_cohf_id,chfz_chfc_id,ownmonth,chfz_name,chfz_number,"
				+ "chfz_tel,chfz_mobile,chfz_mail,chfz_addname,chfz_addtime,chfz_state,chfz_completetime,"
				+ "convert(nvarchar(10),chfz_completetime,120)chfz_completetimeString"
				+ " from CoHousingFundZb where 1=1" + str;

		try {
			list = db.find(sql, CoHousingFundZbModel.class,
					dbconn.parseSmap(CoHousingFundZbModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundZbChangeModel> getZbChangeList(String str) {
		List<CoHousingFundZbChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select statename,b.cid,coba_shortname,coba_company,cfzc_id,cfzc_chfz_id,"
				+ "cfzc_cohf_id,cfzc_chfc_id,a.ownmonth,cfzc_addtype,cfzc_name,cfzc_number,"
				+ "cfzc_tel,cfzc_mobile,cfzc_mail,cfzc_addname,"
				+ "cfzc_state,cfzc_laststate,cfzc_tzlstate,cfzc_tapr_id,cfzc_remark,"
				+ "convert(nvarchar(10),cfzc_addtime,120)cfzc_addtimeString"
				+ " from CoHousingFundZbChange a inner join CoHousingFundChange b on a.cfzc_chfc_id=b.chfc_id"
				+ " inner join (select CID,coba_company,coba_shortname from CoBase)c on b.cid=c.CID"
				+ " left join PubState d on a.cfzc_state=d.stateid and d.type='cogjjzb' where 1=1"
				+ str;

		try {
			list = db.find(sql, CoHousingFundZbChangeModel.class,
					dbconn.parseSmap(CoHousingFundZbChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundPwdModel> getPwdList(String str) {
		List<CoHousingFundPwdModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select chfp_id,chfp_cohf_id,chfp_chfc_id,ownmonth,chfp_zb_name,chfp_zb_number,"
				+ "chfp_yearlimit,chfp_startdate,chfp_enddate,chfp_addname,chfp_addtime,chfp_state,"
				+ "chfp_completetime,convert(nvarchar(10),chfp_completetime,120)chfp_completetimeString,"
				+ "convert(nvarchar(10),chfp_addtime,120)chfp_addtimeString"
				+ " from CoHousingFundPwd where 1=1" + str;

		try {
			list = db.find(sql, CoHousingFundPwdModel.class,
					dbconn.parseSmap(CoHousingFundPwdModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundPwdChangeModel> getPwdChangeList(String str) {
		List<CoHousingFundPwdChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select statename,b.cid,coba_shortname,coba_company,cfpc_id,cfpc_chfp_id,"
				+ "cfpc_cohf_id,cfpc_chfc_id,a.ownmonth,cfpc_addtype,cfpc_zb_name,cfpc_zb_number,"
				+ "cfpc_yearlimit,cfpc_startdate startdate,cfpc_enddate enddate,cfpc_addname,cfpc_state,"
				+ "cfpc_laststate,cfpc_tzlstate,cfpc_tapr_id,cfpc_remark,"
				+ "convert(nvarchar(10),cfpc_addtime,120)cfpc_addtimeString,"
				+ "convert(nvarchar(10),cfpc_startdate,120)cfpc_startdate,"
				+ "convert(nvarchar(10),cfpc_enddate,120)cfpc_enddate"
				+ " from CoHousingFundPwdChange a inner join CoHousingFundChange b on a.cfpc_chfc_id=b.chfc_id"
				+ " inner join (select CID,coba_company,coba_shortname from CoBase)c on b.cid=c.CID"
				+ " left join PubState d on a.cfpc_state=d.stateid and d.type='cogjjpwd' where 1=1"
				+ str;

		try {
			list = db.find(sql, CoHousingFundPwdChangeModel.class,
					dbconn.parseSmap(CoHousingFundPwdChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getCoHoClientList(String str, boolean mod) {
		List<String> list = new ArrayList<>();
		dbconn db = new dbconn();

		if (mod) {
			str += " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			str += " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}

		String sql = "select distinct(coba_client)coba_client from CoHousingFund a"
				+ " inner join CoBase b on a.cid=b.CID where a.cid>0 and coba_client is not null "
				+ str;

		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("coba_client"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundInforChangeModel> getInforChangeList(String str) {
		List<CoHousingFundInforChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cfic_id,cfic_chfc_id,cfic_changestyle,cfic_changetype,cfic_changeold,"
				+ "cfic_changenew,cfic_state,cfic_addtime from CoHousingFundInforChange where 1=1"
				+ str;
		try {
			list = db.find(sql, CoHousingFundInforChangeModel.class,
					dbconn.parseSmap(CoHousingFundInforChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundChangeModel> getLastMonth_EmCount_Sum(String str) {
		List<CoHousingFundChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select count(*)chfc_lastemcount,"
				+ "cast(isnull(SUM(emhu_cp+emhu_op),0) as money)chfc_lastsum"
				+ " from emhouse where ownmonth=convert(varchar(6),dateadd(month,-1,getdate()),112)"
				+ str;
		try {
			list = db.find(sql, CoHousingFundChangeModel.class,
					dbconn.parseSmap(CoHousingFundChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据任务单编号查询任务单状态
	public Integer getTaskState(Integer tapr_id) {
		String sql = "select tali_state,* from TaskProcess a inner join TaskList b "
				+ "on a.tapr_tali_id=b.tali_id where tapr_id=" + tapr_id;
		Integer state = 2;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				state = rs.getInt("tali_state");
			}
		} catch (Exception e) {

		}
		return state;
	}

	// 根据cid查询公积金接管或者新增数据
	public CoHousingFundChangeModel getCoHouseingTapId(Integer cid) {
		CoHousingFundChangeModel model = new CoHousingFundChangeModel();
		try {
			String sql = "select chfc_tapr_id,chfc_addtype,* from CoHousingFundChange "
					+ "where (chfc_addtype='账户接管' or chfc_addtype='缴存登记') and cid="
					+ cid;
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setChfc_tapr_id(rs.getInt("chfc_tapr_id"));
				model.setChfc_addtype(rs.getString("chfc_addtype"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 获取缴存银行列表
	public List<CoHousingFundModel> getjcList() {
		List<CoHousingFundModel> list = new ListModelList<>();

		dbconn db = new dbconn();
		String sql = "select distinct cohf_bankjc from cohousingfund where cohf_state=1";
		try {
			list = db.find(sql, CoHousingFundModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseCpp> getcpList(Integer gid) {
		dbconn db = new dbconn();
		List<EmHouseCpp> list = new ListModelList<>();
		String sql = "select distinct round(h.cohf_cpp,2) cp,cohf_houseid companyid"
				+ " from EmHouseUpdate a"
				+ " inner join coglist b on a.gid=b.gid and cgli_stopdate is null"
				+ " inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='住房公积金服务'"
				+ " inner join CoOffer d on c.coli_coof_id=d.coof_id"
				+ " inner join CoCompact e on d.coof_coco_id=e.coco_id"
				+ " inner join cobase f on a.cid=f.CID"
				+ " inner join login g on f.coba_client=g.log_name"
				+ " inner join CoHousingFund h on cohf_state=1 and a.emhu_single=h.cohf_single and ("
				+ "	(h.cohf_single=1 and a.cid=h.cid) "
				+ "or (h.cohf_single=0 and ((dep_id=2 and cohf_bankjc='中信银行') or (dep_id!=2 and cohf_bankjc='中国银行')) and case e.coco_cpp when '浮动比例' then round(h.cohf_cpp,2) else round(e.coco_cpp,2) end=round(h.cohf_cpp,2)))"
				+ " where a.gid=?" + " order by round(h.cohf_cpp,2)";
		try {
			list = db.find(sql, EmHouseCpp.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
