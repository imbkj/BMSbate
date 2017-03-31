package dal.EmCommissionOut;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DateUtil;
import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Controller.systemWindowController;
import Model.EmCommissionOutZYTModel;
import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailHistoryModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutHistoryModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateFeeDetailModel;
import Model.EmCommissionOutPayUpdateModel;
import Model.EmCommissionOutStandardModel;
import Model.PubStateModel;
import Model.PubStateRelModel;
import Model.SocialInsuranceClassModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmCommissionOut_ListDal {

	public List<SocialInsuranceClassModel> getNullSoClassList(Integer soin_id) {
		List<SocialInsuranceClassModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "";
		if (soin_id != 0) {
			sql = "select distinct sicl_id,sicl_class,sicl_name from SocialInsurance a"
					+ " inner join SocialInsuranceAlgorithm b on a.soin_id=b.sial_soin_id"
					+ " inner join SocialInseranceAlgorithmInfo c on b.sial_id=c.siai_sial_id"
					+ " inner join SocialInsuranceClass d on c.siai_sicl_id=d.sicl_id"
					+ " where    d.sicl_payunit=1 and sicl_state=1 and soin_id="
					+ soin_id
					+ " order by d.sicl_id";
		} else {
			sql = "select distinct sicl_id,sicl_class,sicl_name from SocialInsuranceClass"
					+ " where sicl_state in(1,2)  and  sicl_payunit=1 order by sicl_id";
		}

		try {
			list = db.find(sql, SocialInsuranceClassModel.class,
					dbconn.parseSmap(SocialInsuranceClassModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<EmCommissionOutChangeModel> getstate_aut(Integer gid,Integer ecos_id) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select count(*) ecoc_state from EmCommissionOut where ecou_addtype='正常' and ecou_state=1 and gid="+gid+" and ecou_ecos_id="+ecos_id;

		try {
			System.out.println(sql);
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutChangeModel> getSoInAl(Integer soin_id,
			String date1, String date2) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select a.sial_id,convert(nvarchar(10),sial_execdate,120) sial_execdate from ("
				+ " select sial_id,sial_execdate,ROW_NUMBER() over(partition by soin_title"
				+ " order by sial_execdate desc) id from SocialInsuranceAlgorithm sa"
				+ " left join SocialInsurance si on sa.sial_soin_id=si.soin_id"
				+ " where si.soin_state=1 and sa.sial_state=1 and si.soin_id="
				+ soin_id
				+ " and sial_execdate<='"
				+ date2
				+ "') a"
				+ " where a.id=1 or sial_execdate>='"
				+ date1
				+ "' union"
				+ " select a.sial_id,convert(nvarchar(10),sial_execdate,120) sial_execdate from ("
				+ " select sial_id,sial_execdate,ROW_NUMBER() over(partition by soin_title"
				+ " order by sial_execdate desc) id from SocialInsuranceAlgorithm sa"
				+ " left join SocialInsurance si on sa.sial_soin_id=si.soin_id"
				+ " where si.soin_state=1 and sa.sial_state=1 and si.soin_id="
				+ soin_id
				+ " and sial_execdate<='"
				+ date1
				+ "') a where a.id=1 order by sial_execdate";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该员工基本信息
	public EmCommissionOutChangeModel getEmbase(Integer gid) {
		dbconn db = new dbconn();
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		String sql = "select a.cid,emba_name,emba_idcard ecoc_idcard,isnull(emba_phone,'') ecoc_phone,"
				+ "isnull(emba_mobile,'') ecoc_mobile,emba_hjadd ecoc_domicile,emba_email ecoc_email,"
				+ "coba_client ecoc_client"
				+ " from embase a inner join cobase b on a.cid=b.cid where gid="
				+ gid;

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	// 获取该公司标准信息
	public List<EmCommissionOutStandardModel> getStardList(Integer cid,
			Integer gid) throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "";
		String str = "";
		String str1 = "";

		System.out.println(cid + "--" + gid);

		sql = "select wtot_feeid ecos_id,wtss_title+'('+city+' / '+coab_name+')' ecos_name,wtot_fee ecos_service_fee,"
				+ "wtot_fee ecos_archvie_fee,cabc_id,wtss_shebaopayty ecos_shebao_feetype,wtss_gjjpayty ecos_gjj_feetype,"
				+ "wtss_shebaoco ecos_shebao_zhtype,wtss_gjjco ecos_gjj_zhtype,0 count,wtss_city+'-'+wtss_title+'-'+wtot_feetitle+'-'+coab_shortname fee_title"
				+ " ,wtot_feeid,cabc_id ecos_cabc_id,coab_name,city,wtss_laborcontract,wtss_archives  from"
				+ " View_wtservicestandard   "
				+ "where wtot_state=3 and wtot_ifview=1 and  cid= " + cid;

		System.out.println(sql);

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该公司标准信息
	public List<EmCommissionOutStandardModel> getStardListbyfeeid(
			Integer wtot_feeid) throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "";
		String str = "";
		String str1 = "";

		// System.out.println(cid + "--" + gid);

		sql = "select wtot_feeid ecos_id,wtss_title+'('+city+' / '+coab_name+')' ecos_name,wtot_fee ecos_service_fee,"
				+ "wtot_fee ecos_archvie_fee,cabc_id,wtss_shebaopayty ecos_shebao_feetype,wtss_gjjpayty ecos_gjj_feetype,"
				+ "wtss_shebaoco ecos_shebao_zhtype,wtss_gjjco ecos_gjj_zhtype,0 count,wtss_title+'-'+wtot_feetitle fee_title"
				+ " ,wtot_feeid,cabc_id ecos_cabc_id,coab_name,city,wtss_laborcontract,wtss_archives,cid  from"
				+ " View_wtservicestandard   "
				+ "where wtot_state=3 and  wtot_feeid= " + wtot_feeid;

		System.out.println(sql);

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该公司标准信息
	public List<EmCommissionOutStandardModel> getStardListedit(Integer cid,
			Integer gid) throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "";
		String str = "";
		String str1 = "";

		System.out.println(cid + "--" + gid);

		sql = "select wtot_feeid ecos_id,wtss_title+'('+city+' / '+coab_name+')' ecos_name,wtot_fee ecos_service_fee,"
				+ "wtot_fee ecos_archvie_fee,cabc_id,wtss_shebaopayty ecos_shebao_feetype,wtss_gjjpayty ecos_gjj_feetype,"
				+ "wtss_shebaoco ecos_shebao_zhtype,wtss_gjjco ecos_gjj_zhtype,0 count,wtss_title+'-'+wtot_feetitle fee_title"
				+ " ,wtot_feeid,cabc_id ecos_cabc_id,coab_name,city,wtss_laborcontract,wtss_archives  from"
				+ " View_wtservicestandard  a LEFT JOIN EmCommissionOut b on a.Wtot_feeid=b.ecou_ecos_id "
				+ "where wtot_state=3 and a.cid= " + cid + " AND b.gid=" + +gid;

		System.out.println(sql);

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取服务费
	public List<EmCommissionOutStandardModel> getfuwu_fee(String city,
			String fee_title) throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "";
		String str = "";
		String str1 = "";

		str = " and city='" + city + "'";

		str1 = " and wtot_feetitle='" + fee_title + "'";

		sql = "select wtot_fee ecos_service_fee,0 ecos_archvie_fee  from View_wtservicestandard where 1=1"
				+ str + str1;

		System.out.println(sql);

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public EmCommissionOutStandardModel getStardInfo(Integer ecos_id)
			throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select wtot_feeid ecos_id,city ecos_name,wtot_fee ecos_service_fee,wtot_fee ecos_archvie_fee,"
				+ "cabc_id ecos_cabc_id,wtss_shebaopayty ecos_shebao_feetype,wtss_gjjpayty ecos_gjj_feetype,wtss_shebaoco"
				+ " ecos_shebao_zhtype,wtss_gjjco ecos_gjj_zhtype,wtss_laborcontract "
				+ "from View_wtservicestandard where wtot_feeid=" + ecos_id;

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0);
	}

	// 获取城市
	public List<EmCommissionOutChangeModel> getCityList(Integer cid)
			throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		String sql = "select distinct city from View_wtservicestandard where cid="
				+ cid;

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该标准信息
	public List<EmCommissionOutChangeModel> getTitleList(Integer ecos_id)
			throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		String sql = "select distinct soin_title,soin_id,Convert(varchar(10),sial_execdate,120) sial_execdate from View_wtservicestandard a left join SocialInsurance b on b.soin_cabc_id=a.cabc_id left join (select sial_soin_id,MAX(sial_execdate) sial_execdate from SocialInsuranceAlgorithm where sial_execdate<=GETDATE() group by sial_soin_id) c on c.sial_soin_id=b.soin_id where soin_state=1 and wtot_feeid="
				+ ecos_id + " order by soin_title";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该公司服务费
	public List<EmCommissionOutStandardModel> getfuwulist(Integer cid,
			Integer title) throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select wtot_feetitle fee_title,wtot_feeid from View_wtservicestandard where  cid="
				+ cid + " and cabc_id=" + title;
		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该公司服务费 赵敏捷修改
	public List<EmCommissionOutStandardModel> getfuwulist(Integer wtot_feeid)
			throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select wtot_feetitle fee_title,wtot_feeid,wtot_fee ecos_service_fee,cabc_id from View_wtservicestandard where  wtot_feeid="
				+ wtot_feeid;
		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该公司档案费
	public List<EmCommissionOutStandardModel> getfilelist(Integer gid,
			Integer cabc_id) throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select cpfr_fee ecos_archvie_fee from CoProduct a left join CoPFeeRelation b on b.cpfr_copr_id=a.Copr_id where cpfr_cpfc_id=1 and copr_copc_id=2 and a.copr_name LIKE '%档案%'  and b.cpfr_state=1 and copr_cabc_id="
				+ cabc_id;
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取福利信息
	public List<EmCommissionOutFeeDetailChangeModel> getflList(Integer gid,
			String city, String coab_name) {
		dbconn db = new dbconn();
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		String sql = "select coli_name eofc_name,coli_content eofc_content,coli_fee eofc_month_sum,0 eofc_sicl_id,'福利项目' sicl_class,0 eofc_ecop_id from CoGList a left join CoOfferList b on b.coli_id=a.cgli_coli_id left join CoProduct c on c.copr_id=b.coli_copr_id left join CoAgencyBaseCityRel d on d.cabc_id=c.copr_cabc_id left join CoAgencyBase e on e.coab_id=d.cabc_coab_id where coli_pclass<>'档案' and coab_city='"
				+ city
				+ "' and coab_name='"
				+ coab_name
				+ "' and copr_type='福利产品' and gid=" + gid;
		System.out.println("-------");
		System.out.println(sql);
		try {
			list = db
					.find(sql,
							EmCommissionOutFeeDetailChangeModel.class,
							dbconn.parseSmap(EmCommissionOutFeeDetailChangeModel.class));
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
			System.out.println(sql);
			list = db.find(sql, PubStateModel.class,
					dbconn.parseSmap(PubStateModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public EmCommissionOutChangeModel getEmCommOutChangeInfo(Integer ecoc_id,
			String str) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecoc_id,a.gid,a.cid,ecoc_ecou_id,ecoc_soin_id,ecoc_ecos_id,"
				+ "ecoc_addtype,ecoc_idcard,ecoc_email,ecoc_phone,ecoc_mobile,ecoc_type,"
				+ "convert(nvarchar(10),ecoc_title_date,120) ecoc_title_date,ecoc_cancel_date,"
				+ "ecoc_in_date,ecoc_com_phone,ecoc_com_principal,ecoc_com_company,ecoc_domicile,"
				+ "ecoc_compact_jud,ecoc_compact_f,ecoc_compact_l,ecoc_salary,ecoc_sb_base,"
				+ "ecoc_house_base,ecoc_sb_em_sum,ecoc_sb_co_sum,ecoc_sb_sum,ecoc_gjj_em_sum,"
				+ "ecoc_gjj_co_sum,ecoc_gjj_sum,ecoc_welfare_sum,ecoc_service_fee,ecoc_file_fee,"
				+ "ecoc_sum,ecoc_stop_date,ecoc_stop_cause,ecoc_cancel_cause,ecoc_laststate,"
				+ "ecoc_state,ecoc_addname,ecoc_addtime,ecoc_remark,ecoc_tapr_id,coba_client ecoc_client,"
				+ "emba_name,coba_shortname,coba_company,d.city city,d.coab_name coab_name,statename,wtss_title ecos_name,d.cabc_id ecos_cabc_id,"
				+ "convert(nvarchar(10),ecoc_addtime,120) ecoc_addtime1,soin_title,ecoc_title_date title_date,wtss_archives,wtss_shebaoco,wtss_gjjco,wtss_employment,wtss_laborcontractbb,wtss_laborcontract"
				+ " from EmCommissionOutChange a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecoc_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join PubState f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left outer join SocialInsurance g on a.ecoc_soin_id=g.soin_id"
				+ " where ecoc_id=" + ecoc_id + str;
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));

			for (EmCommissionOutChangeModel m : list) {
				m.setTitle_date(DateStringChange.StringtoDate(
						m.getEcoc_title_date(), "yyyy-MM-dd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	public EmCommissionOutModel getEmCommOutInfoByEcouid(Integer ecou_id,
			String str) {
		List<EmCommissionOutModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecou_id,a.gid,a.cid,ecou_soin_id,ecou_ecos_id,"
				+ "ecou_addtype,ecou_idcard,ecou_email,ecou_phone,ecou_mobile,"
				+ "convert(nvarchar(10),ecou_title_date,120) ecou_title_date,"
				+ "ecou_in_date,ecou_com_phone,ecou_com_principal,ecou_com_company,ecou_domicile,"
				+ "ecou_compact_jud,ecou_compact_f,ecou_compact_l,ecou_salary,ecou_sb_base,"
				+ "ecou_house_base,ecou_sb_em_sum,ecou_sb_co_sum,ecou_sb_sum,ecou_gjj_em_sum,"
				+ "ecou_gjj_co_sum,ecou_gjj_sum,ecou_welfare_sum,ecou_service_fee,ecou_file_fee,"
				+ "ecou_sum,ecou_stop_date,ecou_stop_cause,ecou_cancel_cause,"
				+ "ecou_state,ecou_client,ecou_addname,ecou_addtime,ecou_remark,"
				+ "emba_name,coba_shortname,coba_company,d.city city,d.coab_name coab_name,statename,wtss_title ecos_name,d.cabc_id ecos_cabc_id,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1,soin_title,"
				+ "ecou_title_date title_date,case ecou_state when 1 then '在职' "
				+ "when 0 then '离职' end as statename"
				+ " from EmCommissionOut a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecou_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join PubState f on a.ecou_state=f.stateid"
				+ " inner join SocialInsurance g on a.ecou_soin_id=g.soin_id"
				+ " where ecou_id=" + ecou_id + str;
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutModel.class,
					dbconn.parseSmap(EmCommissionOutModel.class));

			for (EmCommissionOutModel m : list) {
				m.setTitle_date(DateStringChange.StringtoDate(
						m.getEcou_title_date(), "yyyy-MM-01"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<EmCommissionOutChangeModel> getEmCommOutChangeList(String str) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecoc_id,a.gid,a.cid,ecoc_ecou_id,ecoc_soin_id,ecoc_ecos_id,"
				+ "ecoc_addtype,ecoc_idcard,ecoc_email,ecoc_phone,ecoc_mobile,"
				+ "convert(nvarchar(10),ecoc_title_date,120) ecoc_title_date,"
				+ "ecoc_in_date,ecoc_com_phone,ecoc_com_principal,ecoc_com_company,ecoc_domicile,"
				+ "ecoc_compact_jud,ecoc_compact_f,ecoc_compact_l,ecoc_salary,ecoc_sb_base,"
				+ "ecoc_house_base,ecoc_sb_em_sum,ecoc_sb_co_sum,ecoc_sb_sum,ecoc_gjj_em_sum,"
				+ "ecoc_gjj_co_sum,ecoc_gjj_sum,ecoc_welfare_sum,ecoc_service_fee,ecoc_file_fee,"
				+ "ecoc_sum,ecoc_stop_date,ecoc_stop_cause,ecoc_cancel_cause,ecoc_laststate,"
				+ "ecoc_state,b.coba_client ecoc_client,ecoc_addname,ecoc_addtime,ecoc_remark,ecoc_tapr_id,"
				+ "emba_name,coba_shortname,coba_company,city,coab_shortname coab_name,e.coab_name coab_company,statename,"
				+ "convert(nvarchar(10),ecoc_addtime,120) ecoc_addtime1,soin_title,d.cabc_id ecos_cabc_id,"
				+ "cast(ecoc_title_date as smalldatetime) title_date,ecoc_type,convert(varchar(10),Ecoc_title_date,120)title_date"
				+ " from EmCommissionOutChange a left join (select gid,emba_name,coba_shortname,"
				+ "coba_company,coba_client from EmBase em left join CoBase co on em.cid=co.CID) b on a.gid=b.gid"
				+ " left join (select wtot_feeid ecos_id,cabc_id from View_wtservicestandard)d on a.ecoc_ecos_id=d.ecos_id"
				+ " left join (select cabc_id,name city,coab_name,coab_shortname from CoAgencyBaseCityRel a,CoAgencyBase b,PubProCity c"
				+ " where a.cabc_coab_id=b.coab_id and a.cabc_ppc_id=c.id)e on d.cabc_id=e.cabc_id"
				+ " left join (select stateid,type,statename,typename from PubState) f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left join SocialInsurance g on a.ecoc_soin_id=g.soin_id"

				+ " where 1=1" + str + " order by ecoc_addtime";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutChangeModel.class, null);
			/*
			 * for (EmCommissionOutChangeModel m : list) {
			 * m.setTitle_date(DateStringChange.StringtoDate(
			 * m.getEcoc_title_date(), "yyyy-MM-dd")); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutChangeModel> getEmCommOutChangeList2(String str) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecoc_id,a.gid,a.cid,ecoc_ecou_id,ecoc_soin_id,ecoc_ecos_id,"
				+ "ecoc_addtype,a.ecoc_idcard,ecoc_email,ecoc_phone,ecoc_mobile,"
				+ "convert(nvarchar(10),ecoc_title_date,120) ecoc_title_date,"
				+ "ecoc_in_date,ecoc_com_phone,ecoc_com_principal,ecoc_com_company,ecoc_domicile,"
				+ "ecoc_compact_jud,ecoc_compact_f,ecoc_compact_l,ecoc_salary,ecoc_sb_base,"
				+ "ecoc_house_base,ecoc_sb_em_sum,ecoc_sb_co_sum,ecoc_sb_sum,ecoc_gjj_em_sum,"
				+ "ecoc_gjj_co_sum,ecoc_gjj_sum,ecoc_welfare_sum,ecoc_service_fee,ecoc_file_fee,"
				+ "ecoc_sum,ecoc_stop_date,ecoc_stop_cause,ecoc_cancel_cause,ecoc_laststate,"
				+ "ecoc_state,ecoc_client,ecoc_addname,ecoc_addtime,ecoc_remark,ecoc_tapr_id,"
				+ "emba_name,coba_shortname,coba_company,city,coab_shortname coab_name,e.coab_name coab_company,statename,"
				+ "convert(nvarchar(10),ecoc_addtime,120) ecoc_addtime1,soin_title,d.cabc_id ecos_cabc_id,"
				+ "cast(ecoc_title_date as smalldatetime) title_date,ecoc_type"
				+ " from EmCommissionOutChange a left join (select gid,emba_name,coba_shortname,"
				+ "coba_company from EmBase em left join CoBase co on em.cid=co.CID) b on a.gid=b.gid"
				+ " left join (select wtot_feeid ecos_id,cabc_id from View_wtservicestandard)d on a.ecoc_ecos_id=d.ecos_id"
				+ " left join (select cabc_id,name city,coab_name,coab_shortname from CoAgencyBaseCityRel a,CoAgencyBase b,PubProCity c"
				+ " where a.cabc_coab_id=b.coab_id and a.cabc_ppc_id=c.id)e on d.cabc_id=e.cabc_id"
				+ " left join (select stateid,type,statename,typename from PubState) f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left join SocialInsurance g on a.ecoc_soin_id=g.soin_id"
				+ " left join (select ecoc_idcard,gid from EmCommissionOutChange where ecoc_state<3 group by ecoc_idcard,gid having count(*)>1) h on h.gid=a.gid"
				+ " where ecoc_tapr_id is not null and h.gid is not null  and statename<>'已完成' and statename<>'退回'"
				+ str + "  order by a.ecoc_idcard";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));

			for (EmCommissionOutChangeModel m : list) {
				m.setTitle_date(DateStringChange.StringtoDate(
						m.getEcoc_title_date(), "yyyy-MM-dd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutChangeModel> getEmCommOutChangeOutList(String str) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecoc_id,a.gid,a.cid,ecoc_ecou_id,ecoc_soin_id,ecoc_ecos_id,"
				+ "ecoc_addtype,ecoc_idcard,ecoc_email,ecoc_phone,ecoc_mobile,"
				+ "convert(nvarchar(10),ecoc_title_date,120) ecoc_title_date,"
				+ "ecoc_in_date,ecoc_com_phone,ecoc_com_principal,ecoc_com_company,ecoc_domicile,"
				+ "ecoc_compact_jud,ecoc_compact_f,ecoc_compact_l,ecoc_salary,ecoc_sb_base,"
				+ "ecoc_house_base,ecoc_sb_em_sum,ecoc_sb_co_sum,ecoc_sb_sum,ecoc_gjj_em_sum,"
				+ "ecoc_gjj_co_sum,ecoc_gjj_sum,ecoc_welfare_sum,ecoc_service_fee,ecoc_file_fee,"
				+ "ecoc_sum,ecoc_stop_date,ecoc_stop_cause,ecoc_cancel_cause,ecoc_laststate,"
				+ "ecoc_state,ecoc_client,ecoc_addname,ecoc_addtime,ecoc_remark,ecoc_tapr_id,"
				+ "emba_name,coba_shortname,coba_company,city,e.coab_name coab_name,statename,"
				+ "convert(nvarchar(10),ecoc_addtime,120) ecoc_addtime1,soin_title,d.cabc_id ecos_cabc_id,"
				+ "cast(ecoc_title_date as smalldatetime) title_date,ecoc_type"
				+ " from EmCommissionOutChange a left join (select gid,emba_name,coba_shortname,"
				+ "coba_company from EmBase em left join CoBase co on em.cid=co.CID) b on a.gid=b.gid"
				+ " left join (select wtot_feeid ecos_id,cabc_id from View_wtservicestandard)d on a.ecoc_ecos_id=d.ecos_id"
				+ " left join (select cabc_id,name city,coab_name from CoAgencyBaseCityRel a,CoAgencyBase b,PubProCity c"
				+ " where a.cabc_coab_id=b.coab_id and a.cabc_ppc_id=c.id)e on d.cabc_id=e.cabc_id"
				+ " left join (select stateid,type,statename,typename from PubState) f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left join SocialInsurance g on a.ecoc_soin_id=g.soin_id"
				+ " where ecoc_tapr_id is not null"
				+ str
				+ " order by ecoc_id desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));

			for (EmCommissionOutChangeModel m : list) {
				m.setTitle_date(DateStringChange.StringtoDate(
						m.getEcoc_title_date(), "yyyy-MM-dd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 委托外地变更列表
	public List<EmCommissionOutChangeModel> getemcommoutchangelist(String str) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT   gid,eofc_ecoc_id,emba_name,emba_idcard,cid,coba_company,coba_client,city,coab_name,wtot_fee,"
				+ "ecoc_sb_base,ecoc_house_base,ecoc_sb_em_sum,ecoc_sb_co_sum,ecoc_sb_sum,ecoc_gjj_em_sum,ecoc_gjj_co_sum,"
				+ "ecoc_gjj_sum,ecoc_addtype,soin_title,ecoc_statestr,sbownmonth,gjjownmonth,zfeofc_op,zfeofc_cp,ecoc_addname,cast(year(ecoc_addtime) as varchar(4)) addtime_y,cast(month(ecoc_addtime) as varchar(4)) addtime_m,cast(day(ecoc_addtime) as varchar(4)) addtime_d  FROM View_EmCommissionOutchange where 1=1 and  "
				+ " ecoc_statestr<>'已完成' and  CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 )" + str

				+ " order by eofc_ecoc_id desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutModel> getEmCommOutList(String str) {
		List<EmCommissionOutModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		int ecou_edit_st = 0;
		if (UserInfo.getRolName().equals("系统管理员")) {
			ecou_edit_st = 1;
		}
		String sql = "select top 10000  ecou_id,a.gid,a.cid,ecou_soin_id,ecou_ecos_id,"
				+ "ecou_addtype,ecou_idcard,ecou_email,ecou_phone,ecou_mobile,"
				+ "convert(nvarchar(10),ecou_title_date,120) ecou_title_date,"
				+ "ecou_in_date,ecou_com_phone,ecou_com_principal,ecou_com_company,ecou_domicile,"
				+ "ecou_compact_jud,ecou_compact_f,ecou_compact_l,ecou_salary,ecou_sb_base,"
				+ "ecou_house_base,ecou_sb_em_sum,ecou_sb_co_sum,ecou_sb_sum,ecou_gjj_em_sum,"
				+ "ecou_gjj_co_sum,ecou_gjj_sum,ecou_welfare_sum,ecou_service_fee,ecou_file_fee,"
				+ "ecou_sum,ecou_stop_date,ecou_stop_cause,ecou_cancel_cause,"
				+ "case when h.gid IS not null then 2 else ecou_state end ecou_state,c.coba_client ecou_client,ecou_addname,ecou_addtime,ecou_remark,"
				+ "emba_name,coba_shortname,coba_company,d.city city,d.coab_name coab_name,wtot_fee,wtot_feetitle ecos_name,d.cabc_id ecos_cabc_id,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1,soin_title,"
				+ "ecou_title_date title_date,case ecou_state when 1 then '在职' "
				+ "when 0 then '离职' when 3 then '退回' end as statename,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1,"+ecou_edit_st+" ecou_edit_st"
	
				+ " from EmCommissionOut a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecou_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join SocialInsurance g on a.ecou_soin_id=g.soin_id left join (select gid from EmCommissionOutChange where ecoc_addtype in ('新增','调整') and ecoc_state=1 group by gid) h on h.gid=a.gid"
				+ " where ecou_state in(0,1,3) and ecou_addtype<>'补缴'"
				+ str
				+ " order by case ecou_state when 1 then 0 when 3 then 1 when 0 then 2 else 3 end,ecou_id desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutModel.class, null);

			for (EmCommissionOutModel m : list) {
				if (m.getEcou_title_date() != null) {
					m.setTitle_date(Util.DateUtil.getFirstDay(DateStringChange
							.StringtoDate(m.getEcou_title_date(), "yyyy-MM-dd")));
				}
				/*
				 * m.setTitle_date(DateStringChange.StringtoDate(
				 * m.getEcou_title_date(), "yyyy-MM-01"));
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 年调获取在册委托出modellist
	public List<EmCommissionOutModel> getEmCommOutListnt(String str) {
		List<EmCommissionOutModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecou_id,a.gid,a.cid,ecou_soin_id,ecou_ecos_id,"
				+ "ecou_addtype,ecou_idcard,ecou_email,ecou_phone,ecou_mobile,"
				+ "convert(nvarchar(10),ecou_title_date,120) ecou_title_date,"
				+ "ecou_in_date,ecou_com_phone,ecou_com_principal,ecou_com_company,ecou_domicile,"
				+ "ecou_compact_jud,ecou_compact_f,ecou_compact_l,ecou_salary,ecou_sb_base,"
				+ "ecou_house_base,ecou_sb_em_sum,ecou_sb_co_sum,ecou_sb_sum,ecou_gjj_em_sum,"
				+ "ecou_gjj_co_sum,ecou_gjj_sum,ecou_welfare_sum,ecou_service_fee,ecou_file_fee,"
				+ "ecou_sum,ecou_stop_date,ecou_stop_cause,ecou_cancel_cause,"
				+ "ecou_state,ecou_client,ecou_addname,ecou_addtime,ecou_remark,"
				+ "emba_name,coba_shortname,coba_company,d.city city,d.coab_name coab_name,wtot_feetitle ecos_name,d.cabc_id ecos_cabc_id,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1,soin_title,"
				+ "ecou_title_date title_date,case ecou_state when 1 then '在职' "
				+ "when 0 then '离职' when 3 then '退回' end as statename,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1"
				+ " from EmCommissionOut a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecou_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join SocialInsurance g on a.ecou_soin_id=g.soin_id"
				+ "  inner join   ( select ecyc_cityid,gid,ecyt_id from EmCommissionYearChange )a1 on a.gid=a1.gid "
				+ "left join  (select coab_id,ecyt_id from EmCommissionyearchangetitle) b1 on a1.ecyt_id=b1.ecyt_id "
				+ " where ecou_state in(0,1,3) and ecou_addtype<>'补缴' and 	ecou_state=1 and  ecou_addtype not in ('离职','取消','一次性费用')"
				+ str
				+ " order by case ecou_state when 1 then 0 when 3 then 1 when 0 then 2 else 3 end,ecou_id desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutModel.class,
					dbconn.parseSmap(EmCommissionOutModel.class));

			for (EmCommissionOutModel m : list) {
				m.setTitle_date(DateStringChange.StringtoDate(
						m.getEcou_title_date(), "yyyy-MM-01"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 年调获取在册委托出modellist 自动更新 
		public List<EmCommissionOutModel> getEmCommOutListntzd() {
			List<EmCommissionOutModel> list = new ArrayList<>();
			dbconn db = new dbconn();
			String sql = "select ecou_id,a.gid,a.cid,ecou_soin_id,ecou_ecos_id,"
					+ "ecou_addtype,ecou_idcard,ecou_email,ecou_phone,ecou_mobile,"
					+ "convert(nvarchar(10),ecou_title_date,120) ecou_title_date,"
					+ "ecou_in_date,ecou_com_phone,ecou_com_principal,ecou_com_company,ecou_domicile,"
					+ "ecou_compact_jud,ecou_compact_f,ecou_compact_l,ecou_salary,ecou_sb_base,"
					+ "ecou_house_base,ecou_sb_em_sum,ecou_sb_co_sum,ecou_sb_sum,ecou_gjj_em_sum,"
					+ "ecou_gjj_co_sum,ecou_gjj_sum,ecou_welfare_sum,ecou_service_fee,ecou_file_fee,"
					+ "ecou_sum,ecou_stop_date,ecou_stop_cause,ecou_cancel_cause,"
					+ "ecou_state,ecou_client,ecou_addname,ecou_addtime,ecou_remark,"
					+ "emba_name,coba_shortname,coba_company,d.city city,d.coab_name coab_name,wtot_feetitle ecos_name,d.cabc_id ecos_cabc_id,"
					+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1,soin_title,"
					+ "ecou_title_date title_date,case ecou_state when 1 then '在职' "
					+ "when 0 then '离职' when 3 then '退回' end as statename,"
					+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1"
					+ " from EmCommissionOut a inner join EmBase b on a.gid=b.gid"
					+ " inner join CoBase c on a.cid=c.CID"
					+ " inner join View_wtservicestandard d on a.ecou_ecos_id=d.wtot_feeid"
					+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
					+ " inner join SocialInsurance g on a.ecou_soin_id=g.soin_id"
					+ "  inner join   ( select ecyc_cityid,gid,ecyt_id from EmCommissionYearChange where  ecyc_state>=2  and ecyc_gxstate!=5)a1 on a.gid=a1.gid "
					+ "inner join  (select coab_id,ecyt_id from EmCommissionyearchangetitle  where ecyt_startdate<=getdate()) b1 on a1.ecyt_id=b1.ecyt_id "
					+ " where ecou_state in(0,1,3) and ecou_addtype<>'补缴' and 	ecou_state=1 and  ecou_addtype not in ('离职','取消','一次性费用')"
					+ " order by case ecou_state when 1 then 0 when 3 then 1 when 0 then 2 else 3 end,ecou_id desc";
			System.out.println(sql);
			try {
				list = db.find(sql, EmCommissionOutModel.class,
						dbconn.parseSmap(EmCommissionOutModel.class));


				for (EmCommissionOutModel m : list) {
					m.setTitle_date(DateStringChange.StringtoDate(
							m.getEcou_title_date(), "yyyy-MM-01"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		/*
	// 年调获取在册委托出modellist 自动更新
	public List<EmCommissionOutModel> getEmCommOutListntzd() {
		List<EmCommissionOutModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecou_id,a.gid,a.cid,ecou_soin_id,ecou_ecos_id,"
				+ "ecou_addtype,ecou_idcard,ecou_email,ecou_phone,ecou_mobile,"
				+ "convert(nvarchar(10),ecou_title_date,120) ecou_title_date,"
				+ "ecou_in_date,ecou_com_phone,ecou_com_principal,ecou_com_company,ecou_domicile,"
				+ "ecou_compact_jud,ecou_compact_f,ecou_compact_l,ecou_salary,ecou_sb_base,"
				+ "ecou_house_base,ecou_sb_em_sum,ecou_sb_co_sum,ecou_sb_sum,ecou_gjj_em_sum,"
				+ "ecou_gjj_co_sum,ecou_gjj_sum,ecou_welfare_sum,ecou_service_fee,ecou_file_fee,"
				+ "ecou_sum,ecou_stop_date,ecou_stop_cause,ecou_cancel_cause,"
				+ "ecou_state,ecou_client,ecou_addname,ecou_addtime,ecou_remark,"
				+ "emba_name,coba_shortname,coba_company,d.city city,d.coab_name coab_name,wtot_feetitle ecos_name,d.cabc_id ecos_cabc_id,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1,soin_title,"
				+ "ecou_title_date title_date,case ecou_state when 1 then '在职' "
				+ "when 0 then '离职' when 3 then '退回' end as statename,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1"
				+ " from EmCommissionOut a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecou_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join SocialInsurance g on a.ecou_soin_id=g.soin_id"
				+ "  inner join   ( select ecyc_cityid,gid,ecyt_id from EmCommissionYearChange where   ecyc_state=3)a1 on a.gid=a1.gid "
				+ "inner join  (select coab_id,ecyt_id from EmCommissionyearchangetitle  where ecyt_startdate<=getdate()) b1 on a1.ecyt_id=b1.ecyt_id "
				+ " where ecou_state in(0,1,3) and ecou_addtype<>'补缴' and 	ecou_state=1 and  ecou_addtype not in ('离职','取消','一次性费用')"
				+ " order by case ecou_state when 1 then 0 when 3 then 1 when 0 then 2 else 3 end,ecou_id desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutModel.class,
					dbconn.parseSmap(EmCommissionOutModel.class));


			for (EmCommissionOutModel m : list) {
				m.setTitle_date(DateStringChange.StringtoDate(
						m.getEcou_title_date(), "yyyy-MM-01"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
*/
	public EmCommissionOutModel getEmCommOutInfo(Integer ecou_id) {
		List<EmCommissionOutModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecou_id,a.gid,a.cid,ecou_soin_id,ecou_ecos_id,"
				+ "ecou_addtype,ecou_idcard,ecou_email,ecou_phone,ecou_mobile,"
				+ "convert(nvarchar(10),ecou_title_date,120) ecou_title_date,"
				+ "ecou_in_date,ecou_com_phone,ecou_com_principal,ecou_com_company,ecou_domicile,"
				+ "ecou_compact_jud,ecou_compact_f,ecou_compact_l,ecou_salary,ecou_sb_base,"
				+ "ecou_house_base,ecou_sb_em_sum,ecou_sb_co_sum,ecou_sb_sum,ecou_gjj_em_sum,"
				+ "ecou_gjj_co_sum,ecou_gjj_sum,ecou_welfare_sum,ecou_service_fee,ecou_file_fee,"
				+ "ecou_sum,ecou_stop_date,ecou_stop_cause,ecou_cancel_cause,"
				+ "ecou_state,ecou_client,ecou_addname,ecou_addtime,ecou_remark,"
				+ "emba_name,coba_shortname,coba_company,d.city city,d.coab_name coab_name,wtot_feetitle ecos_name,d.cabc_id ecos_cabc_id,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1,soin_title,"
				+ "ecou_title_date title_date,case ecou_state when 1 then '在职' "
				+ "when 0 then '离职' when 3 then '退回' end as statename,"
				+ "convert(nvarchar(10),ecou_addtime,120) ecou_addtime1"
				+ " from EmCommissionOut a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecou_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join SocialInsurance g on a.ecou_soin_id=g.soin_id"
				+ " where ecou_id=" + ecou_id;
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutModel.class,
					dbconn.parseSmap(EmCommissionOutModel.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeDetailFwList(String str) {
		List<EmCommissionOutFeeDetailModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select sicl_class,eofd_id,eofd_ecou_id,eofd_ecoc_id,eofd_sicl_id,eofd_name,"
				+ "eofd_content,eofd_em_base,eofd_co_base,eofd_cp,eofd_op,eofd_em_sum,"
				+ "eofd_co_sum,eofd_month_sum,eofd_start_date,eofd_fstart_date,"
				+ "eofd_stop_date,eofd_stop_date,eofd_addtime,eofd_state"
				+ " from EmCommissionOutFeeDetail a left outer join SocialInsuranceClass b"
				+ " on a.eofd_sicl_id=b.sicl_id"
				+ " where eofd_sicl_id<>0 and eofd_state=1" + str;

		try {
			list = db.find(sql, EmCommissionOutFeeDetailModel.class,
					dbconn.parseSmap(EmCommissionOutFeeDetailModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeDetailFwListall(String str) {
		List<EmCommissionOutFeeDetailModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select sicl_class,eofd_id,eofd_ecou_id,eofd_ecoc_id,eofd_sicl_id,eofd_name,"
				+ "eofd_content,eofd_em_base,eofd_co_base,eofd_cp,eofd_op,eofd_em_sum,"
				+ "eofd_co_sum,eofd_month_sum,eofd_start_date,eofd_fstart_date,"
				+ "eofd_stop_date,eofd_stop_date,eofd_addtime,eofd_state "
				+ " from EmCommissionOutFeeDetail a left outer join SocialInsuranceClass b"
				+ " on a.eofd_sicl_id=b.sicl_id" + " where 1=1 " + str;

		try {
			list = db.find(sql, EmCommissionOutFeeDetailModel.class,
					dbconn.parseSmap(EmCommissionOutFeeDetailModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeDetailChangeFwList(
			String str) {
		System.out.println("aaa");
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select sicl_class,eofc_id,eofc_ecou_id,eofc_sicl_id,eofc_name,"
				+ "eofc_content,eofc_em_base,eofc_co_base,eofc_cp,eofc_op,eofc_em_sum,"
				+ "eofc_co_sum,eofc_month_sum,eofc_start_date,eofc_fstart_date,"
				+ "eofc_stop_date,eofc_fstop_date,eofc_addtime,eofc_state"
				+ " from EmCommissionOutFeeDetail a left outer join SocialInsuranceClass b"
				+ " on a.eofc_sicl_id=b.sicl_id"
				+ " where eofc_sicl_id<>0 and eofc_state=1" + str;

		try {
			list = db
					.find(sql,
							EmCommissionOutFeeDetailChangeModel.class,
							dbconn.parseSmap(EmCommissionOutFeeDetailChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeDetailFlList(String str) {
		List<EmCommissionOutFeeDetailModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select eofd_id,eofd_ecou_id,eofd_ecoc_id,eofd_sicl_id,eofd_name,"
				+ "eofd_content,eofd_em_base,eofd_co_base,eofd_cp,eofd_op,eofd_em_sum,"
				+ "eofd_co_sum,eofd_month_sum,eofd_start_date,eofd_fstart_date,"
				+ "eofd_stop_date,eofd_stop_date,eofd_addtime,eofd_state"
				+ " from EmCommissionOutFeeDetail"
				+ " where eofd_sicl_id=0 and eofd_state=1" + str;

		try {
			list = db.find(sql, EmCommissionOutFeeDetailModel.class,
					dbconn.parseSmap(EmCommissionOutFeeDetailModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeDetailChangeFlList(
			String str) {
		System.out.println("bbb");
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select eofc_id,eofc_ecoc_id,eofc_eofd_id,eofc_sicl_id,eofc_name,eofc_content,"
				+ "eofc_em_base,eofc_co_base,eofc_cp,eofc_op,eofc_em_sum,eofc_co_sum,eofc_month_sum,"
				+ "eofc_start_date,eofc_em_fstart_date,eofc_co_fstart_date,eofc_addtime,eofc_state,"
				+ "eofc_stop_date,eofc_em_fstop_date,eofc_co_fstop_date,'福利项目' sicl_class"
				+ " from EmCommissionOutFeeDetailChange"
				+ " where eofc_sicl_id=0 and eofc_state=1" + str;

		try {
			list = db
					.find(sql,
							EmCommissionOutFeeDetailChangeModel.class,
							dbconn.parseSmap(EmCommissionOutFeeDetailChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeDetailChangeList(
			String str) {
		System.out.println("ccc");
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select eofc_id,eofc_ecoc_id,eofc_eofd_id,eofc_sicl_id,eofc_name,eofc_content,"
				+ "eofc_em_base,eofc_co_base,eofc_cp,eofc_op,eofc_em_sum,eofc_co_sum,eofc_month_sum,"
				+ "eofc_start_date,eofc_em_fstart_date,eofc_co_fstart_date,eofc_addtime,eofc_state,"
				+ "eofc_stop_date,eofc_em_fstop_date,eofc_co_fstop_date,"
				+ "case eofc_sicl_id when 0 then '福利项目' else sicl_class end as sicl_class"
				+ " from EmCommissionOutFeeDetailChange a left outer join SocialInsuranceClass b"
				+ " on a.eofc_sicl_id=b.sicl_id where eofc_state=1"
				+ str
				+ " order by eofc_sicl_id";
		System.out.println(sql);
		try {
			list = db
					.find(sql,
							EmCommissionOutFeeDetailChangeModel.class,
							dbconn.parseSmap(EmCommissionOutFeeDetailChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeDetailList(String str) {
		List<EmCommissionOutFeeDetailModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select eofd_id,eofd_ecou_id,eofd_sicl_id,eofd_name,eofd_content,"
				+ "eofd_em_base,eofd_co_base,isnull(eofd_cp,'') eofd_cp,isnull(eofd_op,'') eofd_op,isnull(eofd_em_sum,0) eofd_em_sum,isnull(eofd_co_sum,0) eofd_co_sum,eofd_month_sum,"
				+ "eofd_start_date,eofd_em_fstart_date,eofd_co_fstart_date,eofd_addtime,eofd_state,"
				+ "eofd_stop_date,eofd_em_fstop_date,eofd_co_fstop_date,"
				+ "case eofd_sicl_id when 0 then '福利项目' else sicl_class end as sicl_class"
				+ " from EmCommissionOutFeeDetail a left outer join SocialInsuranceClass b"
				+ " on a.eofd_sicl_id=b.sicl_id where eofd_state=1"
				+ str
				+ " order by eofd_sicl_id";

		try {
			list = db.find(sql, EmCommissionOutFeeDetailModel.class,
					dbconn.parseSmap(EmCommissionOutFeeDetailModel.class));
			System.out.println("xxxxx");
			System.out.println(sql);
			for (EmCommissionOutFeeDetailModel m : list) {
				try {
					m.setTempDate(DateStringChange.StringtoDate(
							m.getEofd_start_date(), "yyyy-MM-dd"));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeDetailListanyway(String str) {
		List<EmCommissionOutFeeDetailModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select eofd_id,eofd_ecou_id,eofd_sicl_id,eofd_name,eofd_content,"
				+ "eofd_em_base,eofd_co_base,eofd_cp,eofd_op,eofd_em_sum,eofd_co_sum,eofd_month_sum,"
				+ "eofd_start_date,eofd_em_fstart_date,eofd_co_fstart_date,eofd_addtime,eofd_state,"
				+ "eofd_stop_date,eofd_em_fstop_date,eofd_co_fstop_date,"
				+ "case eofd_sicl_id when 0 then '福利项目' else sicl_class end as sicl_class"
				+ " from EmCommissionOutFeeDetail a inner join SocialInsuranceClass b"
				+ " on a.eofd_sicl_id=b.sicl_id where 1=1" + str;

		try {
			list = db.find(sql, EmCommissionOutFeeDetailModel.class,
					dbconn.parseSmap(EmCommissionOutFeeDetailModel.class));

			for (EmCommissionOutFeeDetailModel m : list) {
				try {
					m.setTempDate(DateStringChange.StringtoDate(
							m.getEofd_start_date(), "yyyy-MM-dd"));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getCityList(String typ,
			String typ2) {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct name city,cabc_ppc_id from EmCommissionOutChange a inner join"
				+ " View_wtservicestandard b on a.ecoc_ecos_id=b.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel c on b.cabc_id=c.cabc_id"
				+ " inner join PubProCity d on c.cabc_ppc_id=d.id where 1=1"
				+ typ + typ2 + " order by name";

		try {
			System.out.println(sql);
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoagencyList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct b.cabc_id,d.coab_shortname coab_name,b.coab_name coab_company,c.cabc_ppc_id from EmCommissionOutChange a inner join"
				+ " View_wtservicestandard b on a.ecoc_ecos_id=b.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel c on b.cabc_id=c.cabc_id"
				+ " inner join CoAgencyBase d on c.cabc_coab_id=d.coab_id";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutChangeModel> getClientList() {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct ecoc_client from EmCommissionOutChange";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutChangeModel> getAddtimeyList() {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cast(year(ecoc_addtime) as varchar(4)) addtime_y from EmCommissionOutChange order by cast(year(ecoc_addtime) as varchar(4))";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutChangeModel> getAddtimemList() {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cast(month(ecoc_addtime) as varchar(4)) addtime_m from EmCommissionOutChange  order by cast(month(ecoc_addtime) as varchar(4))";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutChangeModel> getAddtimedList() {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cast(day(ecoc_addtime) as varchar(4)) addtime_d from EmCommissionOutChange  order by cast(day(ecoc_addtime) as varchar(4))";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyLinkmanModel> getLinkManList(Integer cabc_id) {
		List<CoAgencyLinkmanModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cali_id,cali_datatype,cali_linkman,cali_name,cali_ename,"
				+ "cali_nickname,cali_job,cali_duty,cali_tel,cali_tel1,cali_tel2,cali_mobile,"
				+ "cali_mobile1,cali_mobile2,cali_fax,cali_email,cali_email1,cali_email2,cali_sex,"
				+ "cali_address,cali_birth,cali_folk,cali_origo,cali_hjaddress,cali_marriage,cali_height,"
				+ "cali_figure,cali_character,cali_weibo,cali_weixin,cali_qq,cali_degree,cali_school,"
				+ "cali_schoolcity,cali_specialty,cali_lastindustry,cali_lastworktime,cali_lastjob,"
				+ "cali_lastcompany,cali_lastcompanyAddress,cali_developmentPlan,cali_hobby,"
				+ "cali_hobbyActivities,cali_hobbyClub,cali_communityActivities,cali_religiousBelief,"
				+ "cali_conversationTopics,cali_hobbyFood,cali_diet,cali_ifOpInvitationMeals,"
				+ "cali_ifOpSengGift,cali_notTalkAbout,cali_attentionProblem,cali_personality,"
				+ "cali_remark,cali_vip,cali_addname,cali_modname,cali_modtime,"
				+ "cali_delname,cali_deltime,cali_delReason,cali_state from CoAgencyLinkman a"
				+ " inner join CoAgencyBaseLinkRel b on a.cali_id=b.cabl_cali_id"
				+ " where cabl_coab_id=(select cabc_coab_id"
				+ " from CoAgencyBaseCityRel where cabc_id=" + cabc_id + ")";

		try {
			System.out.println(sql);
			list = db.find(sql, CoAgencyLinkmanModel.class,
					dbconn.parseSmap(CoAgencyLinkmanModel.class));

			if (list.size() == 0) {
				list.add(new CoAgencyLinkmanModel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getEcofdidList(
			Integer ecoc_id) {
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select eofd_id eofc_eofd_id from EmCommissionOutFeeDetail"
				+ " where eofd_ecou_id=(select ecoc_ecou_id from EmCommissionOutChange"
				+ " where ecoc_id=" + ecoc_id + ")";

		try {
			System.out.println(sql);
			list = db
					.find(sql,
							EmCommissionOutFeeDetailChangeModel.class,
							dbconn.parseSmap(EmCommissionOutFeeDetailChangeModel.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean addCheck(Integer gid, Integer ecos_id) {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecoc_id from EmCommissionOutChange where gid="
				+ gid + " and ecoc_ecos_id=" + ecos_id + " and ecoc_state=1";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? false : true;
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

	public List<EmCommissionOutChangeModel> getCountList() {
		List<EmCommissionOutChangeModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecoc_addtype,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_state=1 and ecoc_addtype=a.ecoc_addtype) c1,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_state=2 and ecoc_addtype=a.ecoc_addtype) c2,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_state=4 and ecoc_addtype=a.ecoc_addtype) c3,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_state=5 and ecoc_addtype=a.ecoc_addtype) c4,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_state=3 and ecoc_addtype=a.ecoc_addtype) c5,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_addtype=a.ecoc_addtype and ecoc_state<>4 and ecoc_state<>6) c6,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_state=6 and ecoc_addtype=a.ecoc_addtype) c7,"
				+ " (select COUNT(*) from EmCommissionOutChange where ecoc_state=0 and ecoc_addtype=a.ecoc_addtype) c8"
				+ " from EmCommissionOutChange a group by ecoc_addtype"
				+ " order by case ecoc_addtype when '新增' then 0 when '调整' then 1 when '离职'"
				+ " then 2 when '取消' then 3 when '一次性费用' then 4 when '服务费调整' then 5 end";

		try {
			list = db.find(sql, EmCommissionOutChangeModel.class,
					dbconn.parseSmap(EmCommissionOutChangeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoCityList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct city,cabc_ppc_id from CoAgencyBaseCityRel_view";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoagList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cabc_id,cabc_ppc_id,coab_name from CoAgencyBaseCityRel_view where coab_state=1";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getStardList() {
		dbconn db = new dbconn();
		List<String> list = new ArrayList<>();
		String sql = "select distinct soin_title from EmCommissionOutChange a inner join SocialInsurance b"
				+ " on a.ecoc_soin_id=b.soin_id";

		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("soin_title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getFieldList(String str) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecpr_id,ecpr_ecpu_field,ecpr_sicl_id,ecpr_ecpu_fieldstr"
				+ " from EmCommissionOutPayUpdateR where 1=1" + str;

		try {
			list = db.find(sql, EmCommissionOutPayUpdateCRTModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateCRTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getEmOutPayUpdateT(String str) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecut_id,ecut_name,ecut_excelname,ecut_localexcelname,ecut_state,ecut_addname,"
				+ "ecut_addtime,convert(nvarchar(10),ecut_addtime,120) addtime1,ecut_titlerow,"
				+ "case ecut_state when 1 then '可用' when 0 then '禁用' end as statename"
				+ " from EmCommissionOutPayUpdateT where 1=1"
				+ str
				+ " order by ecut_id desc";

		try {
			list = db.find(sql, EmCommissionOutPayUpdateCRTModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateCRTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getEmOutPayUpdateC(String str) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecuc_id,ecuc_ecut_id,ecuc_ecpr_id,ecuc_excel_title,ecuc_state,ecuc_addname,"
				+ "ecuc_addtime,ecpr_id,ecpr_sicl_id,"
				+ "case when ecuc_ecpr_id=0 then 'epfd_month_sum' else ecpr_ecpu_field end as ecpr_ecpu_field,"
				+ "case when ecuc_ecpr_id=0 then ecuc_excel_title else ecpr_ecpu_fieldstr end as ecpr_ecpu_fieldstr,"
				+ "case when ecuc_ecpr_id=0 then 'BigDecimal' else ecpr_type end as ecpr_type"
				+ " from EmCommissionOutPayUpdateC a left join EmCommissionOutPayUpdateR b"
				+ " on a.ecuc_ecpr_id=b.ecpr_id where ecuc_state=1"
				+ str
				+ " order by ecuc_id";

		try {
			list = db.find(sql, EmCommissionOutPayUpdateCRTModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateCRTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutPayUpdateModel> getEmOutPayUpdateList(String str) {
		List<EmCommissionOutPayUpdateModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecpu_id,cid,gid,ecpu_company,ecpu_name,cabc_ppc_id ppc_id,city,b.coab_name coab_name,"
				+ "ecpu_cabc_id,ecpu_idcard,ecpu_hk,ownmonth,bjownmonth,ecpu_change,ecpu_lx,ecpu_base,ecpu_sb_co_total,"
				+ "ecpu_sb_em_total,ecpu_yl_co_total,ecpu_yl_em_total,ecpu_shy_em_total,ecpu_shy_co_total,"
				+ "ecpu_yil_co_total,ecpu_yil_em_total,ecpu_bchyil_co_total,ecpu_bchyil_em_total,"
				+ "ecpu_shyu_co_total,ecpu_shyu_em_total,ecpu_gsh_co_total,ecpu_gsh_em_total,"
				+ "ecpu_gjj_co_total,ecpu_gjj_em_total,ecpu_bchgjj_co_total,ecpu_bchgjj_em_total,"
				+ "ecpu_sb_total,ecpu_gjj_total,ecpu_other_total,ecpu_welfare_total,"
				+ "ecpu_total,ecpu_state,ecpu_addtime,ecpu_addname,ecpu_remark,ecpu_client"
				+ " from EmCommissionOutPayUpdate a inner join"
				+ " (select cabc_id,cabc_ppc_id,city,coab_name from CoAgencyBaseCityRel_view)b"
				+ " on a.ecpu_cabc_id=b.cabc_id where 1=1"
				+ str
				+ " order by ecpu_id desc";

		try {
			list = db.find(sql, EmCommissionOutPayUpdateModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutPayUpdateFeeDetailModel> getEmOutPayUpdateFeeDetailList(
			String str) {
		List<EmCommissionOutPayUpdateFeeDetailModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select epfd_id,epfd_ecpu_id,epfd_sicl_id,epfd_ecop_id,epfd_name,epfd_content,"
				+ "epfd_base,epfd_cp,epfd_op,epfd_em_sum,epfd_co_sum,epfd_month_sum,epfd_start_date,"
				+ "epfd_em_fstart_date,epfd_co_fstart_date,epfd_stop_date,epfd_em_fstop_date,"
				+ "epfd_co_fstop_date,epfd_addtime,epfd_state"
				+ " from EmCommissionOutPayUpdateFeeDetail where 1=1" + str;

		try {
			list = db
					.find(sql,
							EmCommissionOutPayUpdateFeeDetailModel.class,
							dbconn.parseSmap(EmCommissionOutPayUpdateFeeDetailModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getTableFieldList(
			String tableName, Integer ecut_id) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecpr_ecpu_field,ecpr_ecpu_fieldstr,colorder"
				+ " from syscolumns a inner join EmCommissionOutPayUpdateR b on a.name=b.ecpr_ecpu_field"
				+ " inner join EmCommissionOutPayUpdateC c on b.ecpr_id=c.ecuc_ecpr_id"
				+ " where id=(select id from sysobjects where name='"
				+ tableName + "' and xtype='U')" + " and ecuc_ecut_id="
				+ ecut_id + " and ecuc_state=1" + " order by a.colorder";

		try {
			list = db.find(sql, EmCommissionOutPayUpdateCRTModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateCRTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getTableFieldList1(
			String tableName, Integer ecut_id) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct ecpr_ecpu_field,colorder"
				+ " from syscolumns a inner join EmCommissionOutPayUpdateR b on a.name=b.ecpr_ecpu_field"
				+ " inner join EmCommissionOutPayUpdateC c on b.ecpr_id=c.ecuc_ecpr_id"
				+ " where id=(select id from sysobjects where name='"
				+ tableName + "' and xtype='U')" + " and ecuc_ecut_id="
				+ ecut_id + " and ecuc_state=1" + " order by a.colorder";

		try {
			list = db.find(sql, EmCommissionOutPayUpdateCRTModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateCRTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getTableFieldList(
			String tableName) {
		List<EmCommissionOutPayUpdateCRTModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecpr_ecpu_field,ecpr_ecpu_fieldstr,colorder"
				+ " from syscolumns a inner join EmCommissionOutPayUpdateR b on a.name=b.ecpr_ecpu_field"
				+ " where id=(select id from sysobjects where name='"
				+ tableName + "' and xtype='U')" + " order by a.colorder";

		try {
			list = db.find(sql, EmCommissionOutPayUpdateCRTModel.class,
					dbconn.parseSmap(EmCommissionOutPayUpdateCRTModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getPayUpdateCityList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cabc_ppc_id,city from EmCommissionOutPayUpdate a inner join"
				+ " (select cabc_id,cabc_ppc_id,city from CoAgencyBaseCityRel_view)b"
				+ " on a.ecpu_cabc_id=b.cabc_id where ecpu_state=1";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getPayUpdateCoagencyList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct cabc_id,coab_name,cabc_ppc_id from EmCommissionOutPayUpdate a"
				+ " inner join (select cabc_id,coab_name,cabc_ppc_id from CoAgencyBaseCityRel_view)b"
				+ " on a.ecpu_cabc_id=b.cabc_id where ecpu_state=1";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getPayUpdateOwnmonthList() {
		List<String> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct ownmonth from EmCommissionOutPayUpdate where ecpu_state=1 order by ownmonth desc";

		try {
			ResultSet rs = db.GRS(sql);

			while (rs.next()) {
				list.add(rs.getString("ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getEmOutHisPutCityList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select case when his.cabc_ppc_id is null then put.cabc_ppc_id else his.cabc_ppc_id end as cabc_ppc_id,"
				+ " case when his.city is null then put.city else his.city end as city"
				+ " from (select ca.cabc_ppc_id,st.city,st.cabc_id,st.coab_name from EmCommissionOutHistory hi"
				+ " inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view ca on st.cabc_id=ca.cabc_id"
				+ " where ecoh_state=1 group by ca.cabc_ppc_id,st.city,st.cabc_id,st.coab_name)his"
				+ " full join (select cabc_ppc_id,city,cabc_id from EmCommissionOutPayUpdate pu"
				+ " inner join CoAgencyBaseCityRel_view ca on pu.ecpu_cabc_id=ca.cabc_id"
				+ " where ecpu_state=1 group by cabc_ppc_id,city,cabc_id)put on his.cabc_id=put.cabc_id"
				+ " order by city";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getEmOutHisPutCoabList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select case when his.cabc_id is null then put.cabc_id else his.cabc_id end as cabc_id,"
				+ "case when his.coab_name is null then put.coab_name else his.coab_name end as coab_name,"
				+ "case when his.cabc_ppc_id is null then put.cabc_ppc_id else his.cabc_ppc_id end as cabc_ppc_id"
				+ " from (select ca.cabc_ppc_id,st.city,st.cabc_id,st.coab_name from EmCommissionOutHistory hi"
				+ " inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view ca on st.ecos_cabc_id=ca.cabc_id"
				+ " where ecoh_state=1 group by ca.cabc_ppc_id,st.city,st.cabc_id,st.coab_name)his"
				+ " full join (select  cabc_id,coab_name,cabc_ppc_id from EmCommissionOutPayUpdate pu"
				+ " inner join CoAgencyBaseCityRel_view ca on pu.ecpu_cabc_id=ca.cabc_id"
				+ " where ecpu_state=1 group by cabc_id,coab_name,cabc_ppc_id)put on his.cabc_id=put.cabc_id"
				+ " order by coab_name";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutHistoryModel> getEmOutCoabCompareList(
			String ownmonth, String str) {
		List<EmCommissionOutHistoryModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select case when his.cabc_ppc_id is null then put.cabc_ppc_id else his.cabc_ppc_id end as cabc_ppc_id,"
				+ "case when his.city is null then put.city else his.city end as city,"
				+ "case when his.cabc_id is null then put.cabc_id else his.cabc_id end as cabc_id,"
				+ "case when his.coab_name is null then put.coab_name else his.coab_name end as coab_name,"
				+ "case when his.ownmonth is null then put.ownmonth else his.ownmonth end as ownmonth,"
				+ "(select COUNT(ecoh_id) from EmCommissionOutHistory hi inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid where hi.ownmonth="
				+ ownmonth
				+ " and st.cabc_id=his.cabc_id)yf_count,"
				+ "(select COUNT(efco_id) from EmFinanceCommissionOut where ownmonth="
				+ ownmonth
				+ " and efco_state=1 and efco_cabc_id=his.cabc_id)ys_count,"
				+ "(select COUNT(ecpu_id) from EmCommissionOutPayUpdate where ownmonth="
				+ ownmonth
				+ " and ecpu_state=1 and ecpu_cabc_id=his.cabc_id)sf_count,"
				+ "(select SUM(ecoh_sum) from EmCommissionOutHistory hi inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid where hi.ownmonth="
				+ ownmonth
				+ " and st.cabc_id=his.cabc_id)yf_sum,"
				+ "(select SUM(efco_Receivable) from EmFinanceCommissionOut where ownmonth="
				+ ownmonth
				+ " and efco_state=1 and efco_cabc_id=his.cabc_id)ys_sum,"
				+ "(select SUM(ecpu_total) from EmCommissionOutPayUpdate where ownmonth="
				+ ownmonth
				+ " and ecpu_state=1 and ecpu_cabc_id=his.cabc_id)sf_sum,"
				+ "(select SUM(ecoh_sum) from EmCommissionOutHistory hi inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid where hi.ownmonth="
				+ ownmonth
				+ " and st.cabc_id=his.cabc_id)-"
				+ "(select SUM(efco_Receivable) from EmFinanceCommissionOut where ownmonth="
				+ ownmonth
				+ " and efco_state=1 and efco_cabc_id=his.cabc_id) yf_ys_diff,"
				+ "(select SUM(ecoh_sum) from EmCommissionOutHistory hi inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid where hi.ownmonth="
				+ ownmonth
				+ " and st.cabc_id=his.cabc_id)-"
				+ "(select SUM(ecpu_total) from EmCommissionOutPayUpdate where ownmonth="
				+ ownmonth
				+ " and ecpu_state=1 and ecpu_cabc_id=his.cabc_id) yf_sf_diff"
				+ " from (select cabc_ppc_id,city,cabc_id,coab_name,hi.ownmonth from EmCommissionOutHistory hi inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid inner join CoAgencyBaseCityRel_view ca on st.cabc_id=ca.cabc_id where ecoh_state=1 and hi.ownmonth="
				+ ownmonth
				+ ")his"
				+ " full join (select cabc_ppc_id,city,cabc_id,coab_name,pu.ownmonth,ecpu_state from EmCommissionOutPayUpdate pu inner join CoAgencyBaseCityRel_view ca on pu.ecpu_cabc_id=ca.cabc_id where ecpu_state=1 and ownmonth="
				+ ownmonth
				+ ")put on his.cabc_id=put.cabc_id where 1=1"
				+ str
				+ " group by his.cabc_ppc_id,put.cabc_ppc_id,his.city,put.city,his.cabc_id,put.cabc_id,his.coab_name,put.coab_name,his.ownmonth,put.ownmonth order by city";

		try {
			list = db.find(sql, EmCommissionOutHistoryModel.class,
					dbconn.parseSmap(EmCommissionOutHistoryModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutHistoryModel> getEmOutCompanyCompareList(
			String ownmonth, Integer cabc_id, String str) {
		List<EmCommissionOutHistoryModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select case when hi.cid is null then pu.cid else hi.cid end as cid,"
				+ "case when co.coba_company is null then pu.ecpu_company else co.coba_company end as company,"
				+ "(select COUNT(ecoh_id) from EmCommissionOutHistory where (cid=hi.cid or cid=pu.cid) and ownmonth="
				+ ownmonth
				+ " and ecoh_state=1 and ecoh_addtype<>'补缴')yf_count,"
				+ "(select COUNT(eo.efco_id) from EmFinanceCommissionOut eo left join EmFinanceBase eb on eo.efco_efba_id=eb.efba_id where eo.ownmonth="
				+ ownmonth
				+ " and efco_state=1 and (eb.cid=hi.cid or eb.cid=pu.cid))ys_count,"
				+ "(select COUNT(*) from EmCommissionOutPayUpdate where (cid=hi.cid or cid=pu.cid) and ownmonth="
				+ ownmonth
				+ " and ecpu_state=1)sf_count,"
				+ "(select SUM(ecoh_sum) from EmCommissionOutHistory where (cid=hi.cid or cid=pu.cid) and ownmonth="
				+ ownmonth
				+ " and ecoh_state=1)yf_sum,"
				+ "(select SUM(eo.efco_Receivable) from EmFinanceCommissionOut eo left join EmFinanceBase eb on eo.efco_efba_id=eb.efba_id where (eb.cid=hi.cid or eb.cid=pu.cid) and eo.ownmonth="
				+ ownmonth
				+ " and eo.efco_state=1)ys_sum,"
				+ "(select SUM(ecpu_total) from EmCommissionOutPayUpdate where (cid=hi.cid or cid=pu.cid) and ecpu_state=1 and ownmonth="
				+ ownmonth
				+ ")sf_sum,"
				+ "((select SUM(ecoh_sum) from EmCommissionOutHistory where (cid=hi.cid or cid=pu.cid) and ownmonth="
				+ ownmonth
				+ " and ecoh_state=1)-"
				+ "(select SUM(eo.efco_Receivable) from EmFinanceCommissionOut eo left join EmFinanceBase eb on eo.efco_efba_id=eb.efba_id where (eb.cid=hi.cid or eb.cid=pu.cid)"
				+ " and eo.ownmonth="
				+ ownmonth
				+ " and eo.efco_state=1))yf_ys_diff,"
				+ "((select SUM(ecoh_sum) from EmCommissionOutHistory where (cid=hi.cid or cid=pu.cid) and ownmonth="
				+ ownmonth
				+ " and ecoh_state=1)-"
				+ "(select SUM(ecpu_total) from EmCommissionOutPayUpdate where (cid=hi.cid or cid=pu.cid) and ecpu_state=1 and ownmonth="
				+ ownmonth
				+ "))yf_sf_diff"
				+ " from (select cid,ownmonth,ecoh_ecos_id from EmCommissionOutHistory where ecoh_addtype<>'一次性费用')hi full join EmCommissionOutPayUpdate pu on hi.cid=pu.cid"
				+ " full join (select ecos_id,ecos_cabc_id from View_wtservicestandard)st on hi.ecoh_ecos_id=st.wtot_feeid"
				+ " full join (select cabc_id from CoAgencyBaseCityRel_view)ca on st.cabc_id=ca.cabc_id"
				+ " full join (select cid,coba_company from CoBase)co on (hi.cid=co.CID or pu.cid=co.CID)"
				+ " where (ecpu_state is null and hi.ownmonth="
				+ ownmonth
				+ " and cabc_id="
				+ cabc_id
				+ ") or (ecpu_state=1 and pu.ownmonth="
				+ ownmonth
				+ " and ecpu_cabc_id="
				+ cabc_id
				+ ")"
				+ str
				+ " group by hi.cid,pu.cid,coba_company,ecpu_company order by company";

		try {
			list = db.find(sql, EmCommissionOutHistoryModel.class,
					dbconn.parseSmap(EmCommissionOutHistoryModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutHistoryModel> getEmOutEmCompareList(
			String ownmonth, Integer cid, String str) {
		List<EmCommissionOutHistoryModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select his.ecoh_id,put.ecpu_id,fin.efco_id efco_id,finh.efco_id efch_id,"
				+ "case when his.gid is null then put.gid else his.gid end as gid,"
				+ "case when his.emba_name is null then put.ecpu_name else his.emba_name end as emba_name,"
				+ "case when his.ecoh_idcard is null then put.ecpu_idcard else his.ecoh_idcard end as ecoh_idcard,"
				+ "case when his.cid is null then put.cid else his.cid end as cid,ecoh_addtype,"
				+ "case when put.bjownmonth is null then his.bjownmonth else put.bjownmonth end as bjownmonth,"
				+ "case when fin.efco_ecou_sb_sum is null then finh.efco_ecou_sb_sum else fin.efco_ecou_sb_sum end as efco_ecou_sb_sum,ecoh_sb_sum,ecpu_sb_total,"
				+ "case when fin.efco_ecou_gjj_sum is null then finh.efco_ecou_gjj_sum else fin.efco_ecou_gjj_sum end as efco_ecou_gjj_sum,ecoh_gjj_sum,ecpu_gjj_total,"
				+ "case when fin.efco_ecou_welfare_sum is null then finh.efco_ecou_welfare_sum else fin.efco_ecou_welfare_sum end as efco_ecou_welfare_sum,ecoh_welfare_sum,ecpu_welfare_total,"
				+ "case when fin.efco_Receivable is null then finh.efco_Receivable else fin.efco_Receivable end as efco_Receivable,ecoh_sum,ecpu_total"
				+ " from (select ecoh_id,hi.gid,hi.cid,ecoh_ecou_id,ecoh_soin_id,ecoh_ecos_id,ecoh_addtype,ecoh_idcard,ecoh_email,ecoh_phone,ecoh_mobile,ecoh_title_date,ecoh_in_date,ecoh_com_phone,ecoh_com_principal,ecoh_com_company,ecoh_domicile,ecoh_compact_jud,ecoh_compact_f,ecoh_compact_l,ecoh_salary,ecoh_sb_base,ecoh_house_base,ecoh_sb_em_sum,ecoh_sb_co_sum,ecoh_sb_sum,ecoh_gjj_em_sum,ecoh_gjj_co_sum,ecoh_gjj_sum,ecoh_welfare_sum,ecoh_service_fee,ecoh_file_fee,ecoh_sum,ecoh_stop_date,ecoh_stop_cause,ecoh_cancel_cause,ecoh_state,ecoh_client,ecoh_addname,ecoh_addtime,ecoh_remark,hi.ownmonth,CONVERT(nvarchar(6),hi.ecoh_title_date,112) bjownmonth,emba_name"
				+ " from EmCommissionOutHistory hi inner join View_wtservicestandard st on hi.ecoh_ecos_id=st.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view ca on st.cabc_id=ca.cabc_id inner join EmBase em on hi.gid=em.gid"
				+ " where ecoh_state=1 and hi.ownmonth="
				+ ownmonth
				+ " and hi.cid="
				+ cid
				+ ")his full join"
				+ " (select ecpu_id,cid,gid,ecpu_company,ecpu_name,ecpu_cabc_id,ecpu_idcard,ecpu_hk,ownmonth,bjownmonth,ecpu_change,ecpu_lx,ecpu_base,ecpu_sb_co_total,ecpu_sb_em_total,ecpu_yl_co_total,ecpu_yl_em_total,ecpu_shy_em_total,ecpu_shy_co_total,ecpu_yil_co_total,ecpu_yil_em_total,ecpu_bchyil_co_total,ecpu_bchyil_em_total,ecpu_shyu_co_total,ecpu_shyu_em_total,ecpu_gsh_co_total,ecpu_gsh_em_total,ecpu_gjj_co_total,ecpu_gjj_em_total,ecpu_bchgjj_co_total,ecpu_bchgjj_em_total,ecpu_sb_total,ecpu_gjj_total,ecpu_other_total,ecpu_welfare_total,ecpu_total,ecpu_state,ecpu_addtime,ecpu_addname,ecpu_remark,ecpu_client"
				+ " from EmCommissionOutPayUpdate pu inner join CoAgencyBaseCityRel_view ca on pu.ecpu_cabc_id=ca.cabc_id"
				+ " where ecpu_state=1 and pu.ownmonth="
				+ ownmonth
				+ " and pu.cid="
				+ cid
				+ ")put"
				+ " on (his.gid=put.gid and his.ecoh_addtype<>'补缴' and put.ownmonth=put.bjownmonth) or (his.gid=put.gid and his.ecoh_addtype='补缴' and his.bjownmonth=put.bjownmonth) and ecoh_addtype<>'一次性费用'"
				+ " left join (select efco_id,efco_efba_id,efco_coco_id,efco_cfmb_number,eo.gid,eo.ownmonth,efco_cabc_id,efco_soin_id,efco_ecou_id,efco_ecos_shebao_feetype,efco_ecos_gjj_feetype,efco_ecou_addtype,CONVERT(nvarchar(6),efco_ecou_title_date,112) bjownmonth,efco_ecou_sb_sum,efco_ecou_gjj_sum,efco_ecou_cb_sum,efco_ecou_welfare_sum,efco_ecou_service_fee,efco_ecou_file_fee,efco_Receivable,efco_PaidIn,efco_PayOut,efco_IfFirstPaidIn,efco_addtime,efco_FinalCheck,efco_FinalCheckTime,efco_state"
				+ " from EmFinanceCommissionOut eo left join EmFinanceBase eb on eo.efco_efba_id=eb.efba_id"
				+ " where eo.ownmonth="
				+ ownmonth
				+ " and eo.efco_state=1 and eb.cid="
				+ cid
				+ ")fin"
				+ " on his.ecoh_ecou_id=fin.efco_ecou_id and his.ownmonth=fin.ownmonth"
				+ " left join (select efco_id,efco_efba_id,efco_coco_id,efco_cfmb_number,eo.gid,eo.ownmonth,efco_cabc_id,efco_soin_id,efco_ecou_id,efco_ecos_shebao_feetype,efco_ecos_gjj_feetype,efco_ecou_addtype,CONVERT(nvarchar(6),efco_ecou_title_date,112) bjownmonth,efco_ecou_sb_sum,efco_ecou_gjj_sum,efco_ecou_cb_sum,efco_ecou_welfare_sum,efco_ecou_service_fee,efco_ecou_file_fee,efco_Receivable,efco_PaidIn,efco_PayOut,efco_IfFirstPaidIn,efco_addtime,efco_FinalCheck,efco_FinalCheckTime,efco_state"
				+ " from EmFinanceCommissionOutHistory eo left join EmFinanceBase eb on eo.efco_efba_id=eb.efba_id"
				+ " where eo.ownmonth="
				+ ownmonth
				+ " and eo.efco_state=1 and eb.cid="
				+ cid
				+ ")finh"
				+ " on his.ecoh_ecou_id=finh.efco_ecou_id and his.ownmonth=finh.ownmonth order by gid";

		try {
			list = db.find(sql, EmCommissionOutHistoryModel.class,
					dbconn.parseSmap(EmCommissionOutHistoryModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 年调--获取委托历史model
	public List<EmCommissionOutHistoryModel> getEmOutEmHistorymodel(
			String ownmonth, Integer gid, String str) {
		List<EmCommissionOutHistoryModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = " SELECT [ecoh_id],[gid],[cid],[ecoh_ecou_id],[ecoh_soin_id],[ecoh_ecos_id],[ecoh_addtype],"
				+ "[ecoh_idcard],[ecoh_email],[ecoh_phone],[ecoh_mobile],convert(varchar(10),ecoh_title_date,120) ecoh_title_date,[ecoh_in_date],"
				+ "[ecoh_com_phone],[ecoh_com_principal],[ecoh_com_company],[ecoh_domicile],[ecoh_compact_jud],"
				+ "[ecoh_compact_f],[ecoh_compact_l],[ecoh_salary],[ecoh_sb_base],[ecoh_house_base],[ecoh_sb_em_sum],"
				+ "[ecoh_sb_co_sum],[ecoh_sb_sum],[ecoh_gjj_em_sum],[ecoh_gjj_co_sum],[ecoh_gjj_sum],[ecoh_welfare_sum],"
				+ "[ecoh_service_fee],[ecoh_file_fee],[ecoh_sum],[ecoh_stop_date],[ecoh_stop_cause],"
				+ "[ecoh_cancel_cause],[ecoh_state],[ecoh_client],[ecoh_addname],[ecoh_addtime],[ecoh_remark],"
				+ "[ownmonth]FROM  [EmCommissionOutHistory] where  ecoh_addtype='正常' and ownmonth="
				+ ownmonth + " and gid=" + gid + "  " + str + " ";

		System.out.println(sql);

		try {
			list = db.find(sql, EmCommissionOutHistoryModel.class,
					dbconn.parseSmap(EmCommissionOutHistoryModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托出数据信息
	public List<EmCommissionOutZYTModel> getci_exceldim(String id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutZYTModel> list = new ArrayList<EmCommissionOutZYTModel>();
		String sqlstr = "select a.gid t1,emba_name t2,isnull(emba_sex,0) t3,isnull(ecoc_mobile,0) t4,ecoc_phone t5,a.cid t6,coba_company t7,ecoc_phone t8,'终止' t9,"
				+ "emba_idcardclass t10,emba_idcard t11,'' t12,'' t13,ecoc_email t14,cast(Convert(varchar(10),ecoc_stop_date,120) as varchar(20)) t15,'0' T16,"
				+ "substring(replace(isnull(yl_date,''),'-','.'),1,7) T17,cast(ecoc_compact_f as varchar(20)) T18,'' T19,"
				+ "'1' T20,'0' T21,'' T22,e.city T23,e.city T24,'0' T25,'' T26,'' T27,'0' T28,'0' T29,substring(replace(isnull(yl_date,''),'-','.'),1,7) T30,"
				+ "'0' T31,'0' T32,'管理费' T33,"
				+ "'管理费' T34,'0' T35,'0' T36,substring(replace(isnull(jl_date,''),'-','.'),1,7) T37,'0' T38,'0' T39,'0' T40,'0' T41,"
				+ "'管理费' T42,'管理费' T43,'0' T44,'0' T45,substring(replace(isnull(sye_date,''),'-','.'),1,7) T46,'0' T47,"
				+ "'0' T48,'0' T49,'0' T50,'管理费' T51,'管理费' T52,'0' T53,'0' T54,"
				+ "substring(replace(isnull(gs_date,''),'-','.'),1,7) T55,'0' T56,'0' T57,'0' T58,'0' T59,'管理费' T60,'管理费' T61,"
				+ "'0' T62,'0' T63,substring(replace(isnull(syu_date,''),'-','.'),1,7) T64,'0' T65,'0' T66,'0' T67,'0' T68,'管理费' T69,"
				+ "'管理费' T70,'0' T71,'0' T72,substring(replace(isnull(house_date,''),'-','.'),1,7) T73,'0' T74,'0' T75,'' T76,"
				+ "'' T77,'0' T78,'0' T79,'管理费' T80,'管理费' T81,'0' T82,'0' T83,substring(replace(isnull(bc_date,''),'-','.'),1,7) T84,'0' T85,"
				+ "'0' T86,'' T87,'' T88,'0' T89,'0' T90,'管理费' T91,"
				+ "'管理费' T92,'0' T93,'' T94,'0' T95,'0' T96,'0' T97,'0' T98,fee_total T99,'0' T100,"
				+ "substring(replace(isnull(file_date,''),'-','.'),1,7) T101,file_total T102,'0' T103,'0' T104,'0' T105,"
				+ "'' T106,'' T107,ecoc_stop_cause T108,ecoc_remark T109,'' T110,'' T111,'' T112,'' T113,'' T114,'' T115,'' T116,'' T117,'' T118,'' T119,"
				+ "'' T120,'' T121,'' T122,'' T123,'' T124,'' T125,'' T126,'' T127,'' T128,'' T129,'' T130,'' T131,'' T132,'' T133,"
				+ "'' T134,'' T135,'' T136,'' T137,'' T138,'' T139,'' T140,'' T141,'' T142,'' T143,'' T144,'' T145,'' T146,'' T147,"
				+ "'' T148,'' T149,'' T150,'' T151,'' T152,'' T153,'' T154,'' T155,'' T156,'' T157,'' T158,'' T159,'' T160,'' T161,"
				+ "'' T162,'' T163,'' T164,'' T165,'' T166,'' T167,'' T168,'' T169,'' T170,'' T171,'' T172,'' T173,'' T174,'' T175,"
				+ "'' T176,'' T177,'' T178,'' T179,'' T180,'' T181,'' T182,'' T183,'' T184,'' T185,'' T186,'' T187,'' T188,'' T189,"
				+ "'' T190,'' T191,'' T192,'' T193,'' T194,'' T195,'' T196,'' T197,'' T198,'' T199,'' T200"
				+ " from EmCommissionOutChange a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecoc_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join PubState f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left outer join SocialInsurance g on a.ecoc_soin_id=g.soin_id"
				+ " inner join (select eofc_ecoc_id,"
				+ "yl_date=max(case when eofc_name='养老保险' then eofc_stop_date else '' end),"
				+ "yl_em_base=max(case when eofc_name='养老保险' then eofc_em_base else 0 end),"
				+ "yl_co_base=max(case when eofc_name='养老保险' then eofc_co_base else 0 end),"
				+ "yl_em_total=max(case when eofc_name='养老保险' then eofc_em_sum else 0 end),"
				+ "yl_co_total=max(case when eofc_name='养老保险' then eofc_co_sum else 0 end),"
				+ "gs_date=max(case when eofc_name='工伤保险' then eofc_stop_date else '' end),"
				+ "gs_em_base=max(case when eofc_name='工伤保险' then eofc_em_base else 0 end),"
				+ "gs_co_base=max(case when eofc_name='工伤保险' then eofc_co_base else 0 end),"
				+ "gs_em_total=max(case when eofc_name='工伤保险' then eofc_em_sum else 0 end),"
				+ "gs_co_total=max(case when eofc_name='工伤保险' then eofc_co_sum else 0 end),"
				+ "jl_date=max(case when eofc_name='医疗保险' then eofc_stop_date else '' end),"
				+ "jl_em_base=max(case when eofc_name='医疗保险' then eofc_em_base else 0 end),"
				+ "jl_co_base=max(case when eofc_name='医疗保险' then eofc_co_base else 0 end),"
				+ "jl_em_total=max(case when eofc_name='医疗保险' then eofc_em_sum else 0 end),"
				+ "jl_co_total=max(case when eofc_name='医疗保险' then eofc_co_sum else 0 end),"
				+ "sye_date=max(case when eofc_name='失业保险' then eofc_stop_date else '' end),"
				+ "sye_em_base=max(case when eofc_name='失业保险' then eofc_em_base else 0 end),"
				+ "sye_co_base=max(case when eofc_name='失业保险' then eofc_co_base else 0 end),"
				+ "sye_em_total=max(case when eofc_name='失业保险' then eofc_em_sum else 0 end),"
				+ "sye_co_total=max(case when eofc_name='失业保险' then eofc_co_sum else 0 end),"
				+ "syu_date=max(case when eofc_name='生育保险' then eofc_stop_date else '' end),"
				+ "syu_em_base=max(case when eofc_name='生育保险' then eofc_em_base else 0 end),"
				+ "syu_co_base=max(case when eofc_name='生育保险' then eofc_co_base else 0 end),"
				+ "syu_em_total=max(case when eofc_name='生育保险' then eofc_em_sum else 0 end),"
				+ "syu_co_total=max(case when eofc_name='生育保险' then eofc_co_sum else 0 end),"
				+ "house_date=max(case when eofc_name='住房公积金' then eofc_stop_date else '' end),"
				+ "house_em_base=max(case when eofc_name='住房公积金' then eofc_em_base else 0 end),"
				+ "house_co_base=max(case when eofc_name='住房公积金' then eofc_co_base else 0 end),"
				+ "house_cp=max(case when eofc_name='住房公积金' then eofc_cp else '0' end),"
				+ "house_op=max(case when eofc_name='住房公积金' then eofc_op else '0' end),"
				+ "house_em_total=max(case when eofc_name='住房公积金' then eofc_em_sum else 0 end),"
				+ "house_co_total=max(case when eofc_name='住房公积金' then eofc_co_sum else 0 end),"
				+ "bc_date=max(case when eofc_name='补充公积金' then eofc_stop_date else '' end),"
				+ "bc_em_base=max(case when eofc_name='补充公积金' then eofc_em_base else 0 end),"
				+ "bc_co_base=max(case when eofc_name='补充公积金' then eofc_co_base else 0 end),"
				+ "bc_cp=max(case when eofc_name='补充公积金' then eofc_cp else '0' end),"
				+ "bc_op=max(case when eofc_name='补充公积金' then eofc_op else '0' end),"
				+ "bc_em_total=max(case when eofc_name='补充公积金' then eofc_em_sum else 0 end),"
				+ "bc_co_total=max(case when eofc_name='补充公积金' then eofc_co_sum else 0 end),"
				+ "fee_date=max(case when eofc_name='服务费' then eofc_stop_date else '' end),"
				+ "fee_total=max(case when eofc_name='服务费' then eofc_month_sum else 0 end),"
				+ "file_date=max(case when eofc_name='档案费' then eofc_stop_date else '' end),"
				+ "file_total=max(case when eofc_name='档案费' then eofc_month_sum else 0 end)"
				+ " from EmCommissionOutFeeDetailChange group by eofc_ecoc_id) h on h.eofc_ecoc_id=a.ecoc_id"
				+ " where ecoc_id=" + id + " and typename='后道状态'";
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutZYTModel model = new EmCommissionOutZYTModel();
				model.setT1(rs.getString("t1"));
				model.setT2(rs.getString("t2"));
				model.setT3(rs.getString("t3"));
				model.setT4(rs.getString("t4"));
				model.setT5(rs.getString("t5"));
				model.setT6(rs.getString("t6"));
				model.setT7(rs.getString("t7"));
				model.setT8(rs.getString("t8"));
				model.setT9(rs.getString("t9"));
				model.setT10(rs.getString("t10"));
				model.setT11(rs.getString("t11"));
				model.setT12(rs.getString("t12"));
				model.setT13(rs.getString("t13"));
				model.setT14(rs.getString("t14"));
				model.setT15(rs.getString("t15"));
				model.setT16(rs.getString("t16"));
				model.setT17(rs.getString("t17"));
				model.setT18(rs.getString("t18"));
				model.setT19(rs.getString("t19"));
				model.setT20(rs.getString("t20"));
				model.setT21(rs.getString("t21"));
				model.setT22(rs.getString("t22"));
				model.setT23(rs.getString("t23"));
				model.setT24(rs.getString("t24"));
				model.setT25(rs.getString("t25"));
				model.setT26(rs.getString("t26"));
				model.setT27(rs.getString("t27"));
				model.setT28(rs.getString("t28"));
				model.setT29(rs.getString("t29"));
				model.setT30(rs.getString("t30"));
				model.setT31(rs.getString("t31"));
				model.setT32(rs.getString("t32"));
				model.setT33(rs.getString("t33"));
				model.setT34(rs.getString("t34"));
				model.setT35(rs.getString("t35"));
				model.setT36(rs.getString("t36"));
				model.setT37(rs.getString("t37"));
				model.setT38(rs.getString("t38"));
				model.setT39(rs.getString("t39"));
				model.setT40(rs.getString("t40"));
				model.setT41(rs.getString("t41"));
				model.setT42(rs.getString("t42"));
				model.setT43(rs.getString("t43"));
				model.setT44(rs.getString("t44"));
				model.setT45(rs.getString("t45"));
				model.setT46(rs.getString("t46"));
				model.setT47(rs.getString("t47"));
				model.setT48(rs.getString("t48"));
				model.setT49(rs.getString("t49"));
				model.setT50(rs.getString("t50"));
				model.setT51(rs.getString("t51"));
				model.setT52(rs.getString("t52"));
				model.setT53(rs.getString("t53"));
				model.setT54(rs.getString("t54"));
				model.setT55(rs.getString("t55"));
				model.setT56(rs.getString("t56"));
				model.setT57(rs.getString("t57"));
				model.setT58(rs.getString("t58"));
				model.setT59(rs.getString("t59"));
				model.setT60(rs.getString("t60"));
				model.setT61(rs.getString("t61"));
				model.setT62(rs.getString("t62"));
				model.setT63(rs.getString("t63"));
				model.setT64(rs.getString("t64"));
				model.setT65(rs.getString("t65"));
				model.setT66(rs.getString("t66"));
				model.setT67(rs.getString("t67"));
				model.setT68(rs.getString("t68"));
				model.setT69(rs.getString("t69"));
				model.setT70(rs.getString("t70"));
				model.setT71(rs.getString("t71"));
				model.setT72(rs.getString("t72"));
				model.setT73(rs.getString("t73"));
				model.setT74(rs.getString("t74"));
				model.setT75(rs.getString("t75"));
				model.setT76(rs.getString("t76"));
				model.setT77(rs.getString("t77"));
				model.setT78(rs.getString("t78"));
				model.setT79(rs.getString("t79"));
				model.setT80(rs.getString("t80"));
				model.setT81(rs.getString("t81"));
				model.setT82(rs.getString("t82"));
				model.setT83(rs.getString("t83"));
				model.setT84(rs.getString("t84"));
				model.setT85(rs.getString("t85"));
				model.setT86(rs.getString("t86"));
				model.setT87(rs.getString("t87"));
				model.setT88(rs.getString("t88"));
				model.setT89(rs.getString("t89"));
				model.setT90(rs.getString("t90"));
				model.setT91(rs.getString("t91"));
				model.setT92(rs.getString("t92"));
				model.setT93(rs.getString("t93"));
				model.setT94(rs.getString("t94"));
				model.setT95(rs.getString("t95"));
				model.setT96(rs.getString("t96"));
				model.setT97(rs.getString("t97"));
				model.setT98(rs.getString("t98"));
				model.setT99(rs.getString("t99"));
				model.setT100(rs.getString("t100"));
				model.setT101(rs.getString("t101"));
				model.setT102(rs.getString("t102"));
				model.setT103(rs.getString("t103"));
				model.setT104(rs.getString("t104"));
				model.setT105(rs.getString("t105"));
				model.setT106(rs.getString("t106"));
				model.setT107(rs.getString("t107"));
				model.setT108(rs.getString("t108"));
				model.setT109(rs.getString("t109"));
				model.setT110(rs.getString("t110"));
				model.setT111(rs.getString("t111"));
				model.setT112(rs.getString("t112"));
				model.setT113(rs.getString("t113"));
				model.setT114(rs.getString("t114"));
				model.setT115(rs.getString("t115"));
				model.setT116(rs.getString("t116"));
				model.setT117(rs.getString("t117"));
				model.setT118(rs.getString("t118"));
				model.setT119(rs.getString("t119"));
				model.setT120(rs.getString("t120"));
				model.setT121(rs.getString("t121"));
				model.setT122(rs.getString("t122"));
				model.setT123(rs.getString("t123"));
				model.setT124(rs.getString("t124"));
				model.setT125(rs.getString("t125"));
				model.setT126(rs.getString("t126"));
				model.setT127(rs.getString("t127"));
				model.setT128(rs.getString("t128"));
				model.setT129(rs.getString("t129"));
				model.setT130(rs.getString("t130"));
				model.setT131(rs.getString("t131"));
				model.setT132(rs.getString("t132"));
				model.setT133(rs.getString("t133"));
				model.setT134(rs.getString("t134"));
				model.setT135(rs.getString("t135"));
				model.setT136(rs.getString("t136"));
				model.setT137(rs.getString("t137"));
				model.setT138(rs.getString("t138"));
				model.setT139(rs.getString("t139"));
				model.setT140(rs.getString("t140"));
				model.setT141(rs.getString("t141"));
				model.setT142(rs.getString("t142"));
				model.setT143(rs.getString("t143"));
				model.setT144(rs.getString("t144"));
				model.setT145(rs.getString("t145"));
				model.setT146(rs.getString("t146"));
				model.setT147(rs.getString("t147"));
				model.setT148(rs.getString("t148"));
				model.setT149(rs.getString("t149"));
				model.setT150(rs.getString("t150"));
				model.setT151(rs.getString("t151"));
				model.setT152(rs.getString("t152"));
				model.setT153(rs.getString("t153"));
				model.setT154(rs.getString("t154"));
				model.setT155(rs.getString("t155"));
				model.setT156(rs.getString("t156"));
				model.setT157(rs.getString("t157"));
				model.setT158(rs.getString("t158"));
				model.setT159(rs.getString("t159"));
				model.setT160(rs.getString("t160"));
				model.setT161(rs.getString("t161"));
				model.setT162(rs.getString("t162"));
				model.setT163(rs.getString("t163"));
				model.setT164(rs.getString("t164"));
				model.setT165(rs.getString("t165"));
				model.setT166(rs.getString("t166"));
				model.setT167(rs.getString("t167"));
				model.setT168(rs.getString("t168"));
				model.setT169(rs.getString("t169"));
				model.setT170(rs.getString("t170"));
				model.setT171(rs.getString("t171"));
				model.setT172(rs.getString("t172"));
				model.setT173(rs.getString("t173"));
				model.setT174(rs.getString("t174"));
				model.setT175(rs.getString("t175"));
				model.setT176(rs.getString("t176"));
				model.setT177(rs.getString("t177"));
				model.setT178(rs.getString("t178"));
				model.setT179(rs.getString("t179"));
				model.setT180(rs.getString("t180"));
				model.setT181(rs.getString("t181"));
				model.setT182(rs.getString("t182"));
				model.setT183(rs.getString("t183"));
				model.setT184(rs.getString("t184"));
				model.setT185(rs.getString("t185"));
				model.setT186(rs.getString("t186"));
				model.setT187(rs.getString("t187"));
				model.setT188(rs.getString("t188"));
				model.setT189(rs.getString("t189"));
				model.setT190(rs.getString("t190"));
				model.setT191(rs.getString("t191"));
				model.setT192(rs.getString("t192"));
				model.setT193(rs.getString("t193"));
				model.setT194(rs.getString("t194"));
				model.setT195(rs.getString("t195"));
				model.setT196(rs.getString("t196"));
				model.setT197(rs.getString("t197"));
				model.setT198(rs.getString("t198"));
				model.setT199(rs.getString("t199"));
				model.setT200(rs.getString("t200"));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托出数据信息
	public List<EmCommissionOutZYTModel> getci_excel(String id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutZYTModel> list = new ArrayList<EmCommissionOutZYTModel>();
		String sqlstr = "select a.gid t1,emba_name t2,isnull(emba_sex,0) t3,isnull(ecoc_mobile,0) t4,ecoc_phone t5,a.cid t6,coba_company t7,ecoc_phone t8,ecoc_addtype t9,"
				+ "emba_idcardclass t10,emba_idcard t11,'' t12,'' t13,ecoc_email t14,'' t15,substring(replace(isnull(yl_date,''),'-','.'),1,7) T16,"
				+ "'0' T17,cast(ecoc_compact_f as varchar(20)) T18,cast(ecoc_compact_l as varchar(20)) T19,"
				+ "'' T20,ecoc_salary T21,'' T22,'' T23,e.city T24,'' T25,'' T26,'' T27,soin_title T28,'' T29,substring(replace(isnull(yl_date,''),'-','.'),1,7) T30,"
				+ "'0' T31,yl_co_base T32,yl_em_base T33,"
				+ "yl_co_total T34,yl_em_total T35,'管理费' T36,'管理费' T37,'' T38,substring(replace(isnull(jl_date,''),'-','.'),1,7) T39,'0' T40,jl_co_base T41,"
				+ "jl_em_base T42,jl_co_total T43,jl_em_total T44,'管理费' T45,'管理费' T46,'' T47,"
				+ "substring(replace(isnull(sye_date,''),'-','.'),1,7) T48,'0' T49,sye_co_base T50,sye_em_base T51,sye_co_total T52,sye_em_total T53,'管理费' T54,"
				+ "'管理费' T55,'' T56,substring(replace(isnull(gs_date,''),'-','.'),1,7) T57,'0' T58,gs_co_base T59,gs_em_base T60,gs_co_total T61,"
				+ "gs_em_total T62,'管理费' T63,'管理费' T64,'' T65,substring(replace(isnull(syu_date,''),'-','.'),1,7) T66,'0' T67,syu_co_base T68,syu_em_base T69,"
				+ "syu_co_total T70,syu_em_total T71,'管理费' T72,'管理费' T73,'' T74,substring(replace(isnull(house_date,''),'-','.'),1,7) T75,'0' T76,"
				+ "house_co_base T77,house_em_base T78,house_cp T79,house_op T80,house_co_total T81,house_em_total T82,'管理费' T83,'管理费' T84,'' T85,"
				+ "substring(replace(isnull(bc_date,''),'-','.'),1,7) T86,'0' T87,bc_co_base T88,bc_em_base T89,bc_cp T90,bc_op T91,"
				+ "bc_co_total T92,bc_em_total T93,'管理费' T94,'管理费' T95,substring(replace(isnull(fee_date,''),'-','.'),1,7) T96,'0' T97,'' T98,'' T99,'' T100,"
				+ "'' T101,fee_total T102,substring(replace(isnull(file_date,''),'-','.'),1,7) T103,'0' T104,file_total T105,"
				+ "'0' T106,'0' T107,'' T108,'0' T109,d.coab_name T110,'' T111,ecoc_remark T112,'' T113,'' T114,'' T115,'' T116,'' T117,'' T118,'' T119,"
				+ "'' T120,'' T121,'' T122,'' T123,'' T124,'' T125,'' T126,'' T127,'' T128,'' T129,'' T130,'' T131,'' T132,'' T133,"
				+ "'' T134,'' T135,'' T136,'' T137,'' T138,'' T139,'' T140,'' T141,'' T142,'' T143,'' T144,'' T145,'' T146,'' T147,"
				+ "'' T148,'' T149,'' T150,'' T151,'' T152,'' T153,'' T154,'' T155,'' T156,'' T157,'' T158,'' T159,'' T160,'' T161,"
				+ "'' T162,'' T163,'' T164,'' T165,'' T166,'' T167,'' T168,'' T169,'' T170,'' T171,'' T172,'' T173,'' T174,'' T175,"
				+ "'' T176,'' T177,'' T178,'' T179,'' T180,'' T181,'' T182,'' T183,'' T184,'' T185,'' T186,'' T187,'' T188,'' T189,"
				+ "'' T190,'' T191,'' T192,'' T193,'' T194,'' T195,'' T196,'' T197,'' T198,'' T199,'' T200"
				+ " from EmCommissionOutChange a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecoc_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join PubState f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left outer join SocialInsurance g on a.ecoc_soin_id=g.soin_id"
				+ " inner join (select eofc_ecoc_id,"
				+ "yl_date=max(case when eofc_name='养老保险' then eofc_start_date else '' end),"
				+ "yl_em_base=max(case when eofc_name='养老保险' then eofc_em_base else 0 end),"
				+ "yl_co_base=max(case when eofc_name='养老保险' then eofc_co_base else 0 end),"
				+ "yl_em_total=max(case when eofc_name='养老保险' then eofc_em_sum else 0 end),"
				+ "yl_co_total=max(case when eofc_name='养老保险' then eofc_co_sum else 0 end),"
				+ "gs_date=max(case when eofc_name='工伤保险' then eofc_start_date else '' end),"
				+ "gs_em_base=max(case when eofc_name='工伤保险' then eofc_em_base else 0 end),"
				+ "gs_co_base=max(case when eofc_name='工伤保险' then eofc_co_base else 0 end),"
				+ "gs_em_total=max(case when eofc_name='工伤保险' then eofc_em_sum else 0 end),"
				+ "gs_co_total=max(case when eofc_name='工伤保险' then eofc_co_sum else 0 end),"
				+ "jl_date=max(case when eofc_name='医疗保险' then eofc_start_date else '' end),"
				+ "jl_em_base=max(case when eofc_name='医疗保险' then eofc_em_base else 0 end),"
				+ "jl_co_base=max(case when eofc_name='医疗保险' then eofc_co_base else 0 end),"
				+ "jl_em_total=max(case when eofc_name='医疗保险' then eofc_em_sum else 0 end),"
				+ "jl_co_total=max(case when eofc_name='医疗保险' then eofc_co_sum else 0 end),"
				+ "sye_date=max(case when eofc_name='失业保险' then eofc_start_date else '' end),"
				+ "sye_em_base=max(case when eofc_name='失业保险' then eofc_em_base else 0 end),"
				+ "sye_co_base=max(case when eofc_name='失业保险' then eofc_co_base else 0 end),"
				+ "sye_em_total=max(case when eofc_name='失业保险' then eofc_em_sum else 0 end),"
				+ "sye_co_total=max(case when eofc_name='失业保险' then eofc_co_sum else 0 end),"
				+ "syu_date=max(case when eofc_name='生育保险' then eofc_start_date else '' end),"
				+ "syu_em_base=max(case when eofc_name='生育保险' then eofc_em_base else 0 end),"
				+ "syu_co_base=max(case when eofc_name='生育保险' then eofc_co_base else 0 end),"
				+ "syu_em_total=max(case when eofc_name='生育保险' then eofc_em_sum else 0 end),"
				+ "syu_co_total=max(case when eofc_name='生育保险' then eofc_co_sum else 0 end),"
				+ "house_date=max(case when eofc_name='住房公积金' then eofc_start_date else '' end),"
				+ "house_em_base=max(case when eofc_name='住房公积金' then eofc_em_base else 0 end),"
				+ "house_co_base=max(case when eofc_name='住房公积金' then eofc_co_base else 0 end),"
				+ "house_cp=max(case when eofc_name='住房公积金' then eofc_cp else '0' end),"
				+ "house_op=max(case when eofc_name='住房公积金' then eofc_op else '0' end),"
				+ "house_em_total=max(case when eofc_name='住房公积金' then eofc_em_sum else 0 end),"
				+ "house_co_total=max(case when eofc_name='住房公积金' then eofc_co_sum else 0 end),"
				+ "bc_date=max(case when eofc_name='补充公积金' then eofc_start_date else '' end),"
				+ "bc_em_base=max(case when eofc_name='补充公积金' then eofc_em_base else 0 end),"
				+ "bc_co_base=max(case when eofc_name='补充公积金' then eofc_co_base else 0 end),"
				+ "bc_cp=max(case when eofc_name='补充公积金' then eofc_cp else '0' end),"
				+ "bc_op=max(case when eofc_name='补充公积金' then eofc_op else '0' end),"
				+ "bc_em_total=max(case when eofc_name='补充公积金' then eofc_em_sum else 0 end),"
				+ "bc_co_total=max(case when eofc_name='补充公积金' then eofc_co_sum else 0 end),"
				+ "fee_date=max(case when eofc_name='服务费' then eofc_start_date else '' end),"
				+ "fee_total=max(case when eofc_name='服务费' then eofc_month_sum else 0 end),"
				+ "file_date=max(case when eofc_name='档案费' then eofc_start_date else '' end),"
				+ "file_total=max(case when eofc_name='档案费' then eofc_month_sum else 0 end)"
				+ " from EmCommissionOutFeeDetailChange group by eofc_ecoc_id) h on h.eofc_ecoc_id=a.ecoc_id"
				+ " where ecoc_id=" + id + " and typename='后道状态'";
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutZYTModel model = new EmCommissionOutZYTModel();
				model.setT1(rs.getString("t1"));
				model.setT2(rs.getString("t2"));
				model.setT3(rs.getString("t3"));
				model.setT4(rs.getString("t4"));
				model.setT5(rs.getString("t5"));
				model.setT6(rs.getString("t6"));
				model.setT7(rs.getString("t7"));
				model.setT8(rs.getString("t8"));
				model.setT9(rs.getString("t9"));
				model.setT10(rs.getString("t10"));
				model.setT11(rs.getString("t11"));
				model.setT12(rs.getString("t12"));
				model.setT13(rs.getString("t13"));
				model.setT14(rs.getString("t14"));
				model.setT15(rs.getString("t15"));
				model.setT16(rs.getString("t16"));
				model.setT17(rs.getString("t17"));
				model.setT18(rs.getString("t18"));
				model.setT19(rs.getString("t19"));
				model.setT20(rs.getString("t20"));
				model.setT21(rs.getString("t21"));
				model.setT22(rs.getString("t22"));
				model.setT23(rs.getString("t23"));
				model.setT24(rs.getString("t24"));
				model.setT25(rs.getString("t25"));
				model.setT26(rs.getString("t26"));
				model.setT27(rs.getString("t27"));
				model.setT28(rs.getString("t28"));
				model.setT29(rs.getString("t29"));
				model.setT30(rs.getString("t30"));
				model.setT31(rs.getString("t31"));
				model.setT32(rs.getString("t32"));
				model.setT33(rs.getString("t33"));
				model.setT34(rs.getString("t34"));
				model.setT35(rs.getString("t35"));
				model.setT36(rs.getString("t36"));
				model.setT37(rs.getString("t37"));
				model.setT38(rs.getString("t38"));
				model.setT39(rs.getString("t39"));
				model.setT40(rs.getString("t40"));
				model.setT41(rs.getString("t41"));
				model.setT42(rs.getString("t42"));
				model.setT43(rs.getString("t43"));
				model.setT44(rs.getString("t44"));
				model.setT45(rs.getString("t45"));
				model.setT46(rs.getString("t46"));
				model.setT47(rs.getString("t47"));
				model.setT48(rs.getString("t48"));
				model.setT49(rs.getString("t49"));
				model.setT50(rs.getString("t50"));
				model.setT51(rs.getString("t51"));
				model.setT52(rs.getString("t52"));
				model.setT53(rs.getString("t53"));
				model.setT54(rs.getString("t54"));
				model.setT55(rs.getString("t55"));
				model.setT56(rs.getString("t56"));
				model.setT57(rs.getString("t57"));
				model.setT58(rs.getString("t58"));
				model.setT59(rs.getString("t59"));
				model.setT60(rs.getString("t60"));
				model.setT61(rs.getString("t61"));
				model.setT62(rs.getString("t62"));
				model.setT63(rs.getString("t63"));
				model.setT64(rs.getString("t64"));
				model.setT65(rs.getString("t65"));
				model.setT66(rs.getString("t66"));
				model.setT67(rs.getString("t67"));
				model.setT68(rs.getString("t68"));
				model.setT69(rs.getString("t69"));
				model.setT70(rs.getString("t70"));
				model.setT71(rs.getString("t71"));
				model.setT72(rs.getString("t72"));
				model.setT73(rs.getString("t73"));
				model.setT74(rs.getString("t74"));
				model.setT75(rs.getString("t75"));
				model.setT76(rs.getString("t76"));
				model.setT77(rs.getString("t77"));
				model.setT78(rs.getString("t78"));
				model.setT79(rs.getString("t79"));
				model.setT80(rs.getString("t80"));
				model.setT81(rs.getString("t81"));
				model.setT82(rs.getString("t82"));
				model.setT83(rs.getString("t83"));
				model.setT84(rs.getString("t84"));
				model.setT85(rs.getString("t85"));
				model.setT86(rs.getString("t86"));
				model.setT87(rs.getString("t87"));
				model.setT88(rs.getString("t88"));
				model.setT89(rs.getString("t89"));
				model.setT90(rs.getString("t90"));
				model.setT91(rs.getString("t91"));
				model.setT92(rs.getString("t92"));
				model.setT93(rs.getString("t93"));
				model.setT94(rs.getString("t94"));
				model.setT95(rs.getString("t95"));
				model.setT96(rs.getString("t96"));
				model.setT97(rs.getString("t97"));
				model.setT98(rs.getString("t98"));
				model.setT99(rs.getString("t99"));
				model.setT100(rs.getString("t100"));
				model.setT101(rs.getString("t101"));
				model.setT102(rs.getString("t102"));
				model.setT103(rs.getString("t103"));
				model.setT104(rs.getString("t104"));
				model.setT105(rs.getString("t105"));
				model.setT106(rs.getString("t106"));
				model.setT107(rs.getString("t107"));
				model.setT108(rs.getString("t108"));
				model.setT109(rs.getString("t109"));
				model.setT110(rs.getString("t110"));
				model.setT111(rs.getString("t111"));
				model.setT112(rs.getString("t112"));
				model.setT113(rs.getString("t113"));
				model.setT114(rs.getString("t114"));
				model.setT115(rs.getString("t115"));
				model.setT116(rs.getString("t116"));
				model.setT117(rs.getString("t117"));
				model.setT118(rs.getString("t118"));
				model.setT119(rs.getString("t119"));
				model.setT120(rs.getString("t120"));
				model.setT121(rs.getString("t121"));
				model.setT122(rs.getString("t122"));
				model.setT123(rs.getString("t123"));
				model.setT124(rs.getString("t124"));
				model.setT125(rs.getString("t125"));
				model.setT126(rs.getString("t126"));
				model.setT127(rs.getString("t127"));
				model.setT128(rs.getString("t128"));
				model.setT129(rs.getString("t129"));
				model.setT130(rs.getString("t130"));
				model.setT131(rs.getString("t131"));
				model.setT132(rs.getString("t132"));
				model.setT133(rs.getString("t133"));
				model.setT134(rs.getString("t134"));
				model.setT135(rs.getString("t135"));
				model.setT136(rs.getString("t136"));
				model.setT137(rs.getString("t137"));
				model.setT138(rs.getString("t138"));
				model.setT139(rs.getString("t139"));
				model.setT140(rs.getString("t140"));
				model.setT141(rs.getString("t141"));
				model.setT142(rs.getString("t142"));
				model.setT143(rs.getString("t143"));
				model.setT144(rs.getString("t144"));
				model.setT145(rs.getString("t145"));
				model.setT146(rs.getString("t146"));
				model.setT147(rs.getString("t147"));
				model.setT148(rs.getString("t148"));
				model.setT149(rs.getString("t149"));
				model.setT150(rs.getString("t150"));
				model.setT151(rs.getString("t151"));
				model.setT152(rs.getString("t152"));
				model.setT153(rs.getString("t153"));
				model.setT154(rs.getString("t154"));
				model.setT155(rs.getString("t155"));
				model.setT156(rs.getString("t156"));
				model.setT157(rs.getString("t157"));
				model.setT158(rs.getString("t158"));
				model.setT159(rs.getString("t159"));
				model.setT160(rs.getString("t160"));
				model.setT161(rs.getString("t161"));
				model.setT162(rs.getString("t162"));
				model.setT163(rs.getString("t163"));
				model.setT164(rs.getString("t164"));
				model.setT165(rs.getString("t165"));
				model.setT166(rs.getString("t166"));
				model.setT167(rs.getString("t167"));
				model.setT168(rs.getString("t168"));
				model.setT169(rs.getString("t169"));
				model.setT170(rs.getString("t170"));
				model.setT171(rs.getString("t171"));
				model.setT172(rs.getString("t172"));
				model.setT173(rs.getString("t173"));
				model.setT174(rs.getString("t174"));
				model.setT175(rs.getString("t175"));
				model.setT176(rs.getString("t176"));
				model.setT177(rs.getString("t177"));
				model.setT178(rs.getString("t178"));
				model.setT179(rs.getString("t179"));
				model.setT180(rs.getString("t180"));
				model.setT181(rs.getString("t181"));
				model.setT182(rs.getString("t182"));
				model.setT183(rs.getString("t183"));
				model.setT184(rs.getString("t184"));
				model.setT185(rs.getString("t185"));
				model.setT186(rs.getString("t186"));
				model.setT187(rs.getString("t187"));
				model.setT188(rs.getString("t188"));
				model.setT189(rs.getString("t189"));
				model.setT190(rs.getString("t190"));
				model.setT191(rs.getString("t191"));
				model.setT192(rs.getString("t192"));
				model.setT193(rs.getString("t193"));
				model.setT194(rs.getString("t194"));
				model.setT195(rs.getString("t195"));
				model.setT196(rs.getString("t196"));
				model.setT197(rs.getString("t197"));
				model.setT198(rs.getString("t198"));
				model.setT199(rs.getString("t199"));
				model.setT200(rs.getString("t200"));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<SocialInsuranceClassModel> getSoClassList() {
		List<SocialInsuranceClassModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select sicl_id,sicl_name from SocialInsuranceClass";

		try {
			list = db.find(sql, SocialInsuranceClassModel.class,
					dbconn.parseSmap(SocialInsuranceClassModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutFeeDetailHistoryModel> getEmOutEmFeeDetailCompareList(
			Integer ecoh_id, Integer ecpu_id, Integer efco_id, Integer efch_id) {
		List<EmCommissionOutFeeDetailHistoryModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select eofh_id,epfd_id,efd.efcd_id efcd_id,efp.efcp_id efcp_id,efdh.efcd_id efch_id,efph.efcp_id efph_id,"
				+ "eofh_ecoh_id,epfd_ecpu_id,efd.efcd_efco_id efcd_efco_id,efp.efcp_efco_id efcp_efco_id,efdh.efcd_efco_id efch_efco_id,efph.efcp_efco_id efph_efco_id,"
				+ "case when eofh_sicl_id is null then epfd_sicl_id else eofh_sicl_id end as eofh_sicl_id,"
				+ "case when efd.efcd_eofd_sicl_id is null then efdh.efcd_eofd_sicl_id else efd.efcd_eofd_sicl_id end as efcd_eofd_sicl_id,"
				+ "case when eofh_ecop_id is null then epfd_ecop_id else eofh_ecop_id end as eofh_ecop_id,"
				+ "case when efp.efcp_eofd_ecop_id is null then efph.efcp_eofd_ecop_id else efp.efcp_eofd_ecop_id end as efcp_eofd_ecop_id,"
				+ "case when eofh_name is null then epfd_name else eofh_name end as eofh_name,eofh_month_sum,epfd_month_sum,"
				+ "case when efd.efcd_eofd_month_sum is not null then efd.efcd_eofd_month_sum when efdh.efcd_eofd_month_sum is not null then efdh.efcd_eofd_month_sum"
				+ " when efp.efcp_Fee is not null then efp.efcp_Fee else efph.efcp_Fee end as efcd_eofd_month_sum"
				+ " from (select eofh_id,eofh_ecoh_id,eofh_sicl_id,eofh_ecop_id,eofh_name,eofh_month_sum from EmCommissionOutFeeDetailHistory where eofh_ecoh_id="
				+ ecoh_id
				+ ")hif full join(select epfd_id,epfd_ecpu_id,epfd_sicl_id,epfd_ecop_id,epfd_name,epfd_month_sum from EmCommissionOutPayUpdateFeeDetail where epfd_ecpu_id="
				+ ecpu_id
				+ ")puf on hif.eofh_sicl_id=puf.epfd_sicl_id or hif.eofh_ecop_id=puf.epfd_ecop_id"
				+ " left join(select efcd_id,efcd_efco_id,efcd_eofd_sicl_id,efcd_eofd_name,efcd_eofd_month_sum from EmFinanceCommissionOutDetail where efcd_efco_id="
				+ efco_id
				+ ")efd on hif.eofh_sicl_id=efd.efcd_eofd_sicl_id"
				+ " left join(select efcp_id,efcp_efco_id,efcp_eofd_ecop_id,efcp_copr_name,efcp_Fee from EmFinanceCommissionOutProduct where efcp_efco_id="
				+ efco_id
				+ ")efp on hif.eofh_ecop_id=efp.efcp_id"
				+ " left join(select efcd_id,efcd_efco_id,efcd_eofd_sicl_id,efcd_eofd_name,efcd_eofd_month_sum from EmFinanceCommissionOutDetailHistory where efcd_efco_id="
				+ efch_id
				+ " )efdh on hif.eofh_sicl_id=efdh.efcd_eofd_sicl_id"
				+ " left join(select efcp_id,efcp_efco_id,efcp_eofd_ecop_id,efcp_copr_name,efcp_Fee from EmFinanceCommissionOutProductHistory where efcp_efco_id="
				+ efch_id + ")efph on hif.eofh_ecop_id=efph.efcp_id";

		try {
			list = db
					.find(sql,
							EmCommissionOutFeeDetailHistoryModel.class,
							dbconn.parseSmap(EmCommissionOutFeeDetailHistoryModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托出历史明细
	public List<EmCommissionOutFeeDetailHistoryModel> getEmOutEmFeeDetailHistorydetail(
			Integer ecoh_id) {
		List<EmCommissionOutFeeDetailHistoryModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT [eofh_id],[eofh_ecoh_id],[eofh_sicl_id],[eofh_ecop_id],[eofh_name],[eofh_content],"
				+ "[eofh_em_base],[eofh_co_base],[eofh_cp] ,[eofh_op],[eofh_em_sum] ,[eofh_co_sum] ,[eofh_month_sum],"
				+ "[eofh_start_date],[eofh_em_fstart_date],[eofh_co_fstart_date] ,[eofh_stop_date],[eofh_em_fstop_date],"
				+ "[eofh_co_fstop_date],[eofh_addtime] ,[eofh_state],case eofh_sicl_id when 0 then '福利项目' else sicl_class end as sicl_class"
				+ "  from EmCommissionOutFeeDetailHistory a left outer join SocialInsuranceClass b on a.eofh_sicl_id=b.sicl_id where 1=1 and eofh_ecoh_id="
				+ +ecoh_id;

		try {
			list = db
					.find(sql,
							EmCommissionOutFeeDetailHistoryModel.class,
							dbconn.parseSmap(EmCommissionOutFeeDetailHistoryModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取该员工服务起始时间
	public List<EmCommissionOutModel> getgdate(int gid, String coli_name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutModel> list = new ArrayList<EmCommissionOutModel>();
		String sqlstr = "select SUBSTRING(cast(cgli_startdate2 as varchar(10)),1,4)+'-'+SUBSTRING(cast(cgli_startdate2 as varchar(10)),5,2)+'-01' a_date from CoGList a left join CoOfferList b on b.coli_id=a.cgli_coli_id where gid="
				+ gid + " and coli_name='" + coli_name + "'";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutModel model = new EmCommissionOutModel();
				model.setTitle_date(rs.getDate("a_date"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工邮箱信息
	public List<EmCommissionOutChangeModel> geteclist(int tapr_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;

		List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
		String sqlstr = "select tapr_tali_id,tali_name from EmCommissionOutChange a left join TaskProcess b on b.tapr_id=a.ecoc_tapr_id left join TaskList c on c.tali_id=b.tapr_tali_id where ecoc_tapr_id="
				+ tapr_id;
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				model.setEcoc_ecos_id(rs.getInt("tapr_tali_id"));
				model.setEcoc_addtype(rs.getString("tali_name"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 获取该员工邮箱信息
		public List<EmCommissionOutChangeModel> getci_eclist(int tapr_id)
				throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;

			List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
			String sqlstr = "select tapr_tali_id,tali_name from EmComInsuranceApply a left join TaskProcess b on b.tapr_id=a.ecia_tapr_id left join TaskList c on c.tali_id=b.tapr_tali_id where ecia_tapr_id="
					+ tapr_id;
			try {
				rs = db.GRS(sqlstr);
				System.out.println(sqlstr);
				while (rs.next()) {
					EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
					model.setEcoc_ecos_id(rs.getInt("tapr_tali_id"));
					model.setEcoc_addtype(rs.getString("tali_name"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return list;
		}

}
