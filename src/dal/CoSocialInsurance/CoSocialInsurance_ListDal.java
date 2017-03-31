package dal.CoSocialInsurance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoShebaoChangeModel;
import Model.CoShebaoInforChangeModel;
import Model.CoShebaoModel;
import Model.PubAreaCoShebaoModel;
import Model.PubBankModel;
import Model.PubHealthModel;
import Model.PubIndustryFirstModel;
import Model.PubIndustryModel;
import Model.PubStateModel;
import Model.PubStateRelModel;
import Util.UserInfo;

public class CoSocialInsurance_ListDal {

	public List<CoBaseModel> getcobaseList(Integer id) {
		List<CoBaseModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,'('+coba_spell+')'+coba_company coba_company,coba_shortname "
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

	public List<CoBaseModel> getCobaList(Integer cid) {
		List<CoBaseModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cid,'('+coba_spell+')'+coba_company coba_company,coba_shortname,"
				+ " coba_client,coba_spell from cobase a where coba_servicestate=1 and not exists("
				+ " select 1 from coshebao where cosb_state in(1,2) and cid=a.cid) ";
		if (cid != null) {
			if (!cid.equals("")) {
				sql = sql + " and cid=" + cid;
			}
		}
		sql = sql + " order by coba_spell";

		try {
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保分局list
	public List<String> getShebaoArealist() {
		List<String> ShebaoArealist = new ArrayList<String>();
		ResultSet rs = null;
		String sql = "select * from ShebaoArea";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			// ShebaoArealist.add("");
			while (rs.next()) {
				ShebaoArealist.add(rs.getString("sbe_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ShebaoArealist;
	}

	public List<CoShebaoModel> getlist(CoShebaoModel cm) {
		List<CoShebaoModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cosb_id,cosb_csbc_id,cid,ownmonth,cosb_state from coshebao where 1=1";
		if (cm.getCosb_state() != null) {
			if (!cm.getCosb_state().equals("")) {
				sql = sql + " and cosb_state=" + cm.getCosb_state();
			}
		}
		if (cm.getInure() != null) {
			if (cm.getInure()) {
				sql = sql + " and isnull(cosb_state,0)!=0";
			}
		}

		if (cm.getCid() != null) {
			if (!cm.getCid().equals("")) {
				sql = sql + " and cid=" + cm.getCid();
			}
		}

		try {
			list = db.find(sql, CoShebaoModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<PubIndustryModel> getIndustryList() {
		List<PubIndustryModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,name,f_id from PubIndustry";

		try {
			list = db.find(sql, PubIndustryModel.class,
					dbconn.parseSmap(PubIndustryModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubIndustryFirstModel> getIndustryFirstList() {
		List<PubIndustryFirstModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,name from PubIndustryFirst";

		try {
			list = db.find(sql, PubIndustryFirstModel.class,
					dbconn.parseSmap(PubIndustryFirstModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubAreaCoShebaoModel> getAreaList() {
		List<PubAreaCoShebaoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,name from PubAreaCoShebao where pacs_state=1";

		try {
			list = db.find(sql, PubAreaCoShebaoModel.class,
					dbconn.parseSmap(PubAreaCoShebaoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubAreaCoShebaoModel> getTownList() {
		List<PubAreaCoShebaoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,name,a_id from PubTownCoShebao where ptcs_state=1";

		try {
			list = db.find(sql, PubAreaCoShebaoModel.class,
					dbconn.parseSmap(PubAreaCoShebaoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubAreaCoShebaoModel> getStreetList() {
		List<PubAreaCoShebaoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,name,t_id from PubStreetCoShebao where pscs_state=1";

		try {
			list = db.find(sql, PubAreaCoShebaoModel.class,
					dbconn.parseSmap(PubAreaCoShebaoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubBankModel> getBankList() {
		List<PubBankModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,name from PubBank where pb_state=1";

		try {
			list = db.find(sql, PubBankModel.class,
					dbconn.parseSmap(PubBankModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubHealthModel> getHealthList() {
		List<PubHealthModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select id,spell+name name from PubHealth where ph_state=1 order by spell";

		try {
			list = db.find(sql, PubHealthModel.class,
					dbconn.parseSmap(PubHealthModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PubStateModel> getStateList(String str) {
		List<PubStateModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select statename,operate from PubState where state=1"
				+ str + " order by orderid";

		try {
			list = db.find(sql, PubStateModel.class,
					dbconn.parseSmap(PubStateModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoShebaoChangeModel> getCoShebaoChangeList(String wherestr) {
		List<CoShebaoChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select csbc_id,a.cid,a.ownmonth,csbc_addtype,csbc_cotel,csbc_town,csbc_street,"
				+ "csbc_regadd,csbc_regid,cosb_sbadd as csbc_sbadd,csbc_licdate,csbc_comid,csbc_forms,csbc_dep,"
				+ "csbc_ecoclass,csbc_industryclass,csbc_corname,csbc_coridcard,csbc_pastcode,csbc_cosb_id,"
				+ "csbc_heaname,csbc_bankname,csbc_bankcode,csbc_bankacctid,cosb_sorarea as csbc_sorarea,cosb_sorid as csbc_sorid,"
				+ "csbc_pwd,csbc_submission,csbc_paytype,csbc_payapply,csbc_attnname,csbc_attnmobile,"
				+ "csbc_laststate,csbc_state,csbc_tzlstate,csbc_remark,csbc_pdf,csbc_image,csbc_xls,"
				+ "convert(nvarchar(10),csbc_addtime,120) csbc_addtime1,csbc_addtime,csbc_addname,"
				+ "csbc_tapr_id,coba_company,coba_shortname,coba_client,statename,type,csbc_changestr,"
				+ "csbc_return_reason,isnull(csbc_unemployment_per,0) csbc_unemployment_per,"
				+ "isnull(csbc_business_per,0) csbc_business_per,csbc_if_update_compact"
				+ " from CoShebaoChange a inner join CoBase b"
				+ " on a.CID=b.CID inner join PubState c"
				+ " on a.csbc_State=c.stateid "
				+ " inner join CoShebao d on a.csbc_cosb_id=d.cosb_id"
				+ " where ((csbc_Addtype='缴存登记' and type='csoiadd') or"
				+ " (csbc_Addtype='账户接管' and type='csoiadd1') or"
				+ " (csbc_addtype='信息变更' and type='csoichange') or"
				+ " (csbc_addtype='账户注销' and type='csoican') or"
				+ " (csbc_addtype='管理终止' and type='csoiter'))"
				+ wherestr
				+ " order by csbc_id desc";

		try {
			list = db.find(sql, CoShebaoChangeModel.class,
					dbconn.parseSmap(CoShebaoChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoShebaoModel> getCoShebaoList(String wherestr) {
		List<CoShebaoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cosb_id,cosb_csbc_id,a.cid,a.ownmonth,cosb_addtype,cosb_cotel,cosb_town,"
				+ "cosb_street,cosb_regadd,cosb_regid,cosb_sbadd,cosb_licdate,cosb_comid,cosb_forms,"
				+ "cosb_dep,cosb_ecoclass,cosb_industryclass,cosb_corname,cosb_coridcard,cosb_pastcode,"
				+ "cosb_heaname,cosb_bankname,cosb_bankcode,cosb_bankacctid,cosb_sorarea,cosb_sorid,"
				+ "cosb_submission,cosb_paytype,cosb_payapply,cosb_attnname,cosb_attnmobile,cosb_state,"
				+ "cosb_pwd,cosb_remark,cosb_addtime,cosb_addname,coba_shortname,coba_company,"
				+ "csbc_pdf,csbc_xls,csbc_image,coba_client,cosb_stop_type,cosb_stop_reason,"
				+ "case cosb_state when 0 then '终止服务' when 1 then '服务中' when 2 then '申报中'"
				+ " end as statename,isnull(cosb_unemployment_per,0) cosb_unemployment_per,"
				+ "isnull(cosb_business_per,0) cosb_business_per,cosb_ukey"
				+ " from CoShebao a inner join cobase b on a.cid=b.cid"
				+ " left join coshebaochange c on a.cosb_csbc_id=c.csbc_id where cosb_state in (0,1,2)"
				+ " and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and dat_selected=1 ) "
				+ wherestr
				+ "  order by case coba_client when '"
				+ UserInfo.getUsername()
				+ "' then 0 else  1 end , case cosb_state when 1 then 0 when 2 then 1 when 0 then 2 end,cid";
		System.out.println(sql);

		try {
			list = db.find(sql, CoShebaoModel.class,
					dbconn.parseSmap(CoShebaoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public CoShebaoChangeModel getCoShebaoChangeInfo(Integer daid) {
		List<CoShebaoChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select csbc_id,a.cid,a.ownmonth,csbc_addtype,csbc_cotel,csbc_town,csbc_street,"
				+ "csbc_regadd,csbc_regid,csbc_sbadd,csbc_licdate,csbc_comid,csbc_forms,csbc_dep,"
				+ "csbc_ecoclass,csbc_industryclass,csbc_corname,csbc_coridcard,csbc_pastcode,csbc_cosb_id,"
				+ "csbc_heaname,csbc_bankname,csbc_bankcode,csbc_bankacctid,csbc_sorarea,csbc_sorid,"
				+ "csbc_pwd,csbc_submission,csbc_paytype,csbc_payapply,csbc_attnname,csbc_attnmobile,"
				+ "csbc_laststate,csbc_state,csbc_tzlstate,csbc_remark,csbc_pdf,csbc_image,csbc_xls,"
				+ "cosb_attnname,cosb_attnmobile,"
				+ "convert(nvarchar(10),csbc_addtime,120) csbc_addtime1,csbc_addtime,csbc_addname,"
				+ "csbc_tapr_id,coba_company,coba_shortname,coba_client,statename,type,csbc_changestr,"
				+ "csbc_stop_reason,csbc_return_reason,isnull(csbc_unemployment_per,0) csbc_unemployment_per,"
				+ "isnull(csbc_business_per,0) csbc_business_per,cosb_cardbank,cosb_branchbank,cosb_ukey,"
				+ "convert(nvarchar(10),cosb_ukeytruetime,120) cosb_ukeytruetime,"
				+ "convert(nvarchar(10),cosb_ukeyfailtime,120) cosb_ukeyfailtime,csbc_iclassfirst,co.cosb_sorid"
				+ " from CoShebaoChange a inner join coshebao co on a.csbc_cosb_id=co.cosb_id "
				+ " inner join CoBase b"
				+ " on a.CID=b.CID inner join PubState c"
				+ " on a.csbc_State=c.stateid where ((csbc_Addtype='缴存登记' and type='csoiadd') or"
				+ " (csbc_Addtype='账户接管' and type='csoiadd1') or"
				+ " (csbc_addtype='信息变更' and type='csoichange') or"
				+ " (csbc_addtype='账户注销' and type='csoican') or"
				+ " (csbc_addtype='管理终止' and type='csoiter')) and csbc_id="
				+ daid;

		try {
			list = db.find(sql, CoShebaoChangeModel.class,
					dbconn.parseSmap(CoShebaoChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	public CoShebaoModel getCoShebaoInfo(Integer daid) {
		List<CoShebaoModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cosb_id,a.cid,a.ownmonth,cosb_addtype,cosb_cotel,cosb_town,cosb_street,"
				+ "cosb_regadd,cosb_regid,cosb_sbadd,cosb_licdate,cosb_comid,cosb_forms,cosb_dep,"
				+ "cosb_ecoclass,cosb_industryclass,cosb_corname,cosb_coridcard,cosb_pastcode,"
				+ "cosb_heaname,cosb_bankname,cosb_bankcode,cosb_bankacctid,cosb_sorarea,cosb_sorid,"
				+ "cosb_pwd,cosb_submission,cosb_paytype,cosb_payapply,cosb_attnname,cosb_attnmobile,"
				+ "cosb_state,cosb_remark,csbc_pdf,csbc_image,csbc_xls,"
				+ "convert(nvarchar(10),cosb_addtime,120) cosb_addtime1,cosb_addtime,cosb_addname,"
				+ "coba_company,coba_shortname,coba_client,cosb_stop_type,cosb_stop_reason,"
				+ "isnull(cosb_unemployment_per,0) cosb_unemployment_per,"
				+ "isnull(cosb_business_per,0) cosb_business_per,cosb_cardbank,cosb_branchbank,cosb_ukey,"
				+ "convert(nvarchar(10),cosb_ukeytruetime,120) cosb_ukeytruetime,"
				+ "convert(nvarchar(10),cosb_ukeyfailtime,120) cosb_ukeyfailtime,cosb_iclassfirst"
				+ " from CoShebao a inner join CoBase b"
				+ " on a.CID=b.CID left join coshebaochange c"
				+ " on a.cosb_csbc_id=c.csbc_id where cosb_id=" + daid;

		try {
			list = db.find(sql, CoShebaoModel.class,
					dbconn.parseSmap(CoShebaoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<CoShebaoChangeModel> getClientList() {
		List<CoShebaoChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coba_client from CoShebaoChange a inner join cobase b on a.cid=b.cid";

		try {
			list = db.find(sql, CoShebaoChangeModel.class,
					dbconn.parseSmap(CoShebaoChangeModel.class));
		} catch (Exception e) {
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

	public List<CoShebaoInforChangeModel> getInforChangeList(Integer daid,
			String str) {
		List<CoShebaoInforChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select csic_id,csic_csbc_id,csic_changestyle,csic_changef,csic_changel,"
				+ "csic_state,csic_addtime from CoShebaoInforChange where csic_state=1 and csic_csbc_id="
				+ daid + str;

		try {
			list = db.find(sql, CoShebaoInforChangeModel.class,
					dbconn.parseSmap(CoShebaoInforChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoCompactModel> getCompactList(Integer cid) {
		List<CoCompactModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select coco_id,coco_compactid from CoCompact where cid="
				+ cid
				+ " and coco_id not in"
				+ "(select coli_coco_id from CoOfferList a inner join"
				+ " (select cgli_coli_id,gid from CoGList where cgli_state=1 and cid="
				+ cid + ")b" + " on a.coli_id=b.cgli_coli_id inner join"
				+ " (select gid from EmShebao where cid=" + cid
				+ " and esiu_ifstop=0)c"
				+ " on b.gid=c.GID where coli_name='社会保险服务')";

		try {
			list = db.find(sql, CoCompactModel.class,
					dbconn.parseSmap(CoCompactModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取制卡银行信息
	public List<PubBankModel> getBankList(String str) {
		List<PubBankModel> list = new ArrayList<PubBankModel>();
		dbconn db = new dbconn();
		try {
			String sql = "select * from Coshebaobank where bank_state=1" + str
					+ " order by bank_id";
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				PubBankModel model = new PubBankModel();
				model.setId(rs.getInt("bank_id"));
				model.setName(rs.getString("bank_name"));
				model.setBank_doc_id(rs.getInt("bank_doc_id"));
				list.add(model);
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 获取制卡银行支行信息
	public List<PubBankModel> getBankBranchList(String str) {
		List<PubBankModel> list = new ArrayList<PubBankModel>();
		dbconn db = new dbconn();
		try {
			String sql = "select * from CoshebaobankBranch where bran_state=1"
					+ str + " order by bran_bank_id";
			System.out.print("sql=" + sql);
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				PubBankModel model = new PubBankModel();
				model.setId(rs.getInt("bran_bank_id"));
				model.setName(rs.getString("bran_name"));
				list.add(model);
			}
		} catch (Exception e) {

		}
		return list;
	}
}
