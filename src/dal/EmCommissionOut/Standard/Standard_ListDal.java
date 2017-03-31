package dal.EmCommissionOut.Standard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.CoOfferListModel;
import Model.EmCommissionOutStandardModel;
import Model.PubProCityModel;

public class Standard_ListDal {

	public ListModelList<PubProCityModel> getCityList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ListModelList<PubProCityModel> list = new ListModelList<PubProCityModel>();
		String sqlstr = "select id,spell+name name from PubProCity order by name";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				PubProCityModel ppcModel = new PubProCityModel();
				ppcModel.setId(rs.getInt("id"));
				ppcModel.setName(rs.getString("name"));
				list.add(ppcModel);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	public ListModelList<PubProCityModel> getCityListforwt() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ListModelList<PubProCityModel> list = new ListModelList<PubProCityModel>();
		String sqlstr = "select cabc_ppc_id,province,city,coab_name,cabc_fee from CoAgencyBaseCityRel_view where cabc_ifdefault=1 order by city";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				PubProCityModel ppcModel = new PubProCityModel();
				ppcModel.setId(rs.getInt("cabc_ppc_id"));
				ppcModel.setName(rs.getString("city"));
				list.add(ppcModel);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}


	public List<PubProCityModel> getCityList1() throws SQLException {
		dbconn db = new dbconn();
		List<PubProCityModel> list = new ListModelList<PubProCityModel>();
		String sql = "select id,name from PubProCity "
				+ "where name<>'深圳' order by name";

		try {
			list = db.find(sql, PubProCityModel.class,
					dbconn.parseSmap(PubProCityModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public CoBaseModel getCobaInfo(Integer cid) {
		dbconn db = new dbconn();
		List<CoBaseModel> list = new ArrayList<>();
		String sql = "select cid,coba_company,coba_shortname from cobase where cid="
				+ cid + " and coba_servicestate=1";

		try {
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : new CoBaseModel();
	}

	public CoAgencyBaseCityRelViewModel getDefaultCoAgency(Integer id) {
		dbconn db = new dbconn();
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		String sql = "select coab_id,coab_name,cabc_id,isnull(cabc_fee,0) cabc_fee from CoAgencyBase a inner join CoAgencyBaseCityRel b "
				+ "on a.coab_id=b.cabc_coab_id where cabc_ppc_id="
				+ id
				+ " and cabc_ifdefault=1 and cabc_state=1";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0)
				: new CoAgencyBaseCityRelViewModel();
	}
	

	public List<CoAgencyBaseCityRelViewModel> getDefaultCoAgencylist(Integer id) {
		dbconn db = new dbconn();
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		String sql = "select coab_id,coab_name,cabc_id,isnull(cabc_fee,0) cabc_fee from CoAgencyBase a inner join CoAgencyBaseCityRel b "
				+ "on a.coab_id=b.cabc_coab_id where cabc_ppc_id="
				+ id
				+ "   and cabc_state=1 order by  b.cabc_ifdefault desc ";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	


	public EmCommissionOutStandardModel getStandardInfo(Integer daid) {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select ecos_id,a.cid,ownmonth,ecos_name,ecos_cabc_id,ecos_service_fee,"
				+ " ecos_archvie_fee,ecos_shebao_zhtype,ecos_shebao_feetype,"
				+ " ecos_gjj_zhtype,ecos_gjj_feetype,ecos_remark,ecos_laststate,ecos_state,"
				+ " ecos_usestate,ecos_history_id,ecos_addname,ecos_addtime,"
				+ " ecos_tapr_id,coba_company,coba_shortname,c.name city"
				+ " from EmCommissionOutStandard a inner join CoBase b"
				+ " on a.cid=b.CID inner join PubProCity c"
				+ " on a.ecos_cabc_id=c.id where ecos_id=" + daid;

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0)
				: new EmCommissionOutStandardModel();
	}

	public EmCommissionOutStandardModel getStandardInfo1(Integer daid) {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select ecos_id,a.cid,ownmonth,ecos_name,ecos_cabc_id,ecos_service_fee,"
				+ " ecos_archvie_fee,ecos_archvie_feetype,ecos_shebao_zhtype,ecos_shebao_feetype,"
				+ " ecos_gjj_zhtype,ecos_gjj_feetype,ecos_remark,ecos_laststate,ecos_state,"
				+ " ecos_usestate,ecos_history_id,ecos_addname,ecos_addtime,coab_name,"
				+ " ecos_tapr_id,coba_company,coba_shortname,case ecos_state when 1 then"
				+ " (select name from PubProCity where id=ecos_cabc_id) else d.name end as city,d.id ppc_id"
				+ " from EmCommissionOutStandard a inner join CoBase b"
				+ " on a.cid=b.CID inner join CoAgencyBaseCityRel c"
				+ " on a.ecos_cabc_id=c.cabc_id inner join PubProCity d"
				+ " on c.cabc_ppc_id=d.id left outer join CoAgencyBase e"
				+ " on c.cabc_coab_id=e.coab_id where ecos_id=" + daid;

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0)
				: new EmCommissionOutStandardModel();
	}

	public List<CoAgencyBaseCityRelViewModel> getCoAgencyList(Integer ppc_id) {
		dbconn db = new dbconn();
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		String sql = " select coab_id,city,coab_name,cabc_id from CoAgencyBaseCityRel_view "
				 
				+ " where coab_state=1 and cabc_ppc_id=" + ppc_id;

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

	public List<CoAgencyBaseCityRelViewModel> getCoAgencyList(String cityname) {
		dbconn db = new dbconn();
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		String sql = " select coab_id,city,coab_name,cabc_id,isnull(cabc_fee,0) cabc_fee from CoAgencyBaseCityRel_view "
				 
				+ " where coab_state=1 and city='"+cityname+"'";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public List<CoAgencyBaseCityRelViewModel> getCoAgencyList1(Integer cabc_id) {
		dbconn db = new dbconn();
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		String sql = "select coab_id,coab_name,cabc_id from CoAgencyBase a inner join CoAgencyBaseCityRel b"
				+ " on a.coab_id=b.cabc_coab_id"
				+ " where coab_state=1 and cabc_ppc_id=(select cabc_ppc_id from CoAgencyBaseCityRel"
				+ " where cabc_id=" + cabc_id + ")";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoAgencyListView(Integer cabc_id) {
		dbconn db = new dbconn();
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		String sql = "select coab_id,coab_name,cabc_id,isnull(cabc_fee,0) as cabc_fee,city from CoAgencyBaseCityRel_View "
				+ " where cabc_id=" + cabc_id + "";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
	public List<CoOfferListModel> getCoOfferList(Integer cid, Integer cabc_id,
			Integer ppc_id) {
		String sql = "";
		if (cabc_id == 0) {
			sql = "select coli_id,coli_name,coli_fee,coli_cpfc_name from CoCompact a"
					+ " inner join CoOffer b on a.coco_id=b.coof_coco_id"
					+ " inner join CoOfferList c on b.coof_id=c.coli_coof_id"
					+ " inner join CoProduct d on c.coli_copr_id=d.Copr_id"
					+ " inner join CoAgencyBaseCityRel e on d.copr_cabc_id=e.cabc_id"
					+ " inner join PubProCity f on e.cabc_ppc_id=f.id"
					+ " where a.coco_state>=2 and copr_type='福利产品' and a.cid="
					+ cid + " and id=" + ppc_id;
		} else {
			sql = "select coli_id,coli_name,coli_fee,coli_cpfc_name from CoCompact a"
					+ " inner join CoOffer b on a.coco_id=b.coof_coco_id"
					+ " inner join CoOfferList c on b.coof_id=c.coli_coof_id"
					+ " inner join CoProduct d on c.coli_copr_id=d.Copr_id"
					+ " inner join CoAgencyBaseCityRel e on d.copr_cabc_id=e.cabc_id"
					+ " inner join PubProCity f on e.cabc_ppc_id=f.id"
					+ " where a.coco_state>=2 and copr_type='福利产品' and a.cid="
					+ cid + " and e.cabc_id=" + cabc_id;
		}

		dbconn db = new dbconn();
		List<CoOfferListModel> list = new ArrayList<>();

		try {
			list = db.find(sql, CoOfferListModel.class,
					dbconn.parseSmap(CoOfferListModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferListRel(Integer daid) {
		dbconn db = new dbconn();
		List<CoOfferListModel> list = new ArrayList<>();
		String sql = "select coli_id,coli_name,coli_fee,coli_cpfc_name"
				+ " from EmCommissionOutCoOfferListRel a inner join CoOfferList b"
				+ " on a.ecop_coli_id=b.coli_id where ecop_ecos_id=" + daid;

		try {
			list = db.find(sql, CoOfferListModel.class,
					dbconn.parseSmap(CoOfferListModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public EmCommissionOutStandardModel getServiceCost(
			EmCommissionOutStandardModel m) {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();

		String sql = "select ISNULL(SUM(coli_fee),0) service_cost from CoOfferList a"
				+ " inner join (select Copr_id,copr_cabc_id from CoProduct)b on a.coli_copr_id=b.Copr_id"
				+ " where coli_coof_id in(select coof_id from CoOffer"
				+ " where coof_coco_id in(select coco_id from CoCompact where coco_state>3 and cid=?))"
				+ " and copr_cabc_id=? and coli_account='服务费'";

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class),
					m.getCid(), m.getEcos_cabc_id());
			m.setService_cost(list.get(0).getService_cost());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	public EmCommissionOutStandardModel getStateInfo(Integer daid,
			Integer stateid) {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select eosr_id,eosr_stateid,eosr_daid,eosr_addname,eosr_addtime,eosr_statetime,"
				+ "eosr_remark,id,stateid,statename,operate,type,typename,state,orderid"
				+ " from EmCommissionOutStateRel a inner join EmCommissionOutState b"
				+ " on a.eosr_stateid=b.stateid"
				+ " where eosr_daid="
				+ daid
				+ " and eosr_stateid=" + stateid;

		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0)
				: new EmCommissionOutStandardModel();
	}

	public List<EmCommissionOutStandardModel> getStandardList(String str) {
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ecos_id,a.cid,ownmonth,ecos_name,ecos_cabc_id,ecos_service_fee,"
				+ " ecos_archvie_fee,ecos_shebao_zhtype,ecos_shebao_feetype,"
				+ " ecos_gjj_zhtype,ecos_gjj_feetype,ecos_remark,ecos_laststate,ecos_state,"
				+ " ecos_usestate,ecos_history_id,ecos_addname,ecos_addtime,ecos_tapr_id,"
				+ " c.coab_name,case ecos_state when 1 then (select name from PubProCity"
				+ " where id=ecos_cabc_id) else d.name end as city,statename,coba_shortname,"
				+ " convert(varchar(10),ecos_addtime,120) ecos_addtime1 from EmCommissionOutStandard a"
				+ " inner join CoAgencyBaseCityRel b on a.ecos_cabc_id=b.cabc_id"
				+ " left outer join CoAgencyBase c on b.cabc_coab_id=c.coab_id"
				+ " inner join PubProCity d on b.cabc_ppc_id=d.id"
				+ " inner join EmCommissionOutState e on a.ecos_state=e.stateid"
				+ " inner join CoBase f on a.cid=f.cid"
				+ " where 1=1"
				+ str
				+ " order by ecos_id desc";
		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmCommissionOutStandardModel> getStateList(String str) {
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select stateid,statename,operate from EmCommissionOutState"
				+ " where state=1" + str + " order by orderid";
		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
