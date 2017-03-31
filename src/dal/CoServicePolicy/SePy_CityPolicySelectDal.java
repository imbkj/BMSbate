package dal.CoServicePolicy;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Model.CoServicePolicyFileModel;
import Model.CoServicePolicyModel;
import Model.CoServicePolicyTitleDropInfoModel;
import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;

public class SePy_CityPolicySelectDal {
	// 查询服务政策基本信息
	public List<CoServicePolicyModel> getCoServicePolicyList(String str) {
		List<CoServicePolicyModel> list = new ArrayList<CoServicePolicyModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),sypo_uploadtime,120) as sypo_uploadtime,"
				+ "convert(varchar(16),sypo_addtime,120) as sypo_addtime,"
				+ "convert(varchar(16),sypo_modtime,120) as sypo_modtime,"
				+ " * from CoServicePolicy where sypo_state=1" + str + "";
		try {
			list = db.find(sql, CoServicePolicyModel.class,
					dbconn.parseSmap(CoServicePolicyModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询服务政策文件信息
	public List<CoServicePolicyFileModel> getCoServicePolicyFileList(String str) {
		List<CoServicePolicyFileModel> list = new ArrayList<CoServicePolicyFileModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(16),file_addtime,120) as file_addtime,"
				+ "convert(varchar(16),file_modtime,120) as file_modtime,"
				+ "* from CoServicePolicyFile where file_state=1" + str + "";
		try {
			list = db.find(sql, CoServicePolicyFileModel.class,
					dbconn.parseSmap(CoServicePolicyFileModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询服务政策类型信息
	public List<CoServicePolicyTypeModel> getCoServicePolicyType(String str) {
		List<CoServicePolicyTypeModel> list = new ArrayList<CoServicePolicyTypeModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(16),note_addtime,120) as note_addtime,"
				+ "convert(varchar(16),note_modtime,120) as note_modtime,"
				+ "* from CoServicePolicyType where note_state=1"
				+ str
				+ " order by note_order";
		try {
			list = db.find(sql, CoServicePolicyTypeModel.class,
					dbconn.parseSmap(CoServicePolicyTypeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询服务政策标题信息
	public List<CoServicePolicyTitleModel> getCoServicePolicyTitle(String str) {
		List<CoServicePolicyTitleModel> list = new ArrayList<CoServicePolicyTitleModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(16),item_addtime,120) as item_addtime,"
				+ "convert(varchar(16),item_modtime,120) as item_modtime,"
				+ "* from CoServicePolicyTitle where item_state=1"
				+ str
				+ " order by item_order";
		try {
			list = db.find(sql, CoServicePolicyTitleModel.class,
					dbconn.parseSmap(CoServicePolicyTitleModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询服务政策标题信息
	public List<CoServicePolicyTitleModel> getCoServicePolicyTitles(String str,
			Integer sypo_cabc_id) {
		List<CoServicePolicyTitleModel> list = new ArrayList<CoServicePolicyTitleModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(16),item_addtime,120) as item_addtime,"
				+ "convert(varchar(16),item_modtime,120) as item_modtime,sypo_content as item_content,"
				+ "a.* from CoServicePolicyTitle a left join CoServicePolicy b "
				+ " on a.item_id=b.sypo_item_id and sypo_cabc_id="
				+ sypo_cabc_id + " where item_state=1" + str + "";
		try {
			list = db.find(sql, CoServicePolicyTitleModel.class,
					dbconn.parseSmap(CoServicePolicyTitleModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取城市
	public List<String> getCity() {
		List<String> list = new ArrayList<String>();
		String sql = "select * from PubProCity where id in(select distinct(sypo_cityid) sypo_cityid "
				+ " from CoServicePolicy where sypo_cityid is not null)";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {
		}
		return list;
	}

	// 查询机构
	public List<CoAgencyBaseModel> getCoAgencyBaseList(String str) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),coab_addtime,120) as coab_addtime,"
				+ "name,a.* from CoAgencyBase a,CoAgencyBaseCityRel b,PubProCity c "
				+ "where a.coab_id=b.cabc_coab_id and c.id=b.cabc_ppc_id and coab_state=1"
				+ str;
		try {
			list = db.find(sql, CoAgencyBaseModel.class,
					dbconn.parseSmap(CoAgencyBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询城市与机构
	public List<CoAgencyBaseModel> getWtAgencyList(String str) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoAgencyBaseModel m = null;
		String sql = "SELECT ca.coab_id,ca.coab_name,cabc_id,ca.coab_namespell,ca.coab_setuptype,"
				+ "cs.coas_client,name FROM CoAgencyBase ca inner join CoAgencyBaseService cs "
				+ "on ca.coab_id=cs.coas_coab_id and coas_type=1 inner join (select cabc_id,name,"
				+ "cabc_coab_id from CoAgencyBaseCityRel a,PubProCity bct "
				+ "where  a.cabc_ppc_id=bct.id) cm on ca.coab_id=cm.cabc_coab_id "
				+ "where coab_state=1" + str + " order by coab_id";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql.toString());
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_namespell(rs.getString("coab_namespell"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCabsModel(new CoAgencyBaseServiceModel());
				m.getCabsModel().setCoas_client(rs.getString("coas_client"));
				m.setCabc_id(rs.getInt("cabc_id"));
				m.setName(rs.getString("name"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询下拉列表信息
	public List<CoServicePolicyTitleDropInfoModel> getDropInfoList(String str) {
		List<CoServicePolicyTitleDropInfoModel> list = new ArrayList<CoServicePolicyTitleDropInfoModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),drop_addtime,120) as drop_addtime,"
				+ "* from CoServicePolicyTitleDropInfo where 1=1" + str;
		try {
			list = db.find(sql, CoServicePolicyTitleDropInfoModel.class,
					dbconn.parseSmap(CoServicePolicyTitleDropInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询下拉列表信息
	public List<CoServicePolicyTitleModel> getTypeAndTitleList(String str) {
		List<CoServicePolicyTitleModel> list = new ArrayList<CoServicePolicyTitleModel>();
		dbconn db = new dbconn();
		String sql = "select * from CoServicePolicyType a inner join CoServicePolicyTitle b on a.note_id=b.item_type_id where 1=1"
				+ str;
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				CoServicePolicyTitleModel model = new CoServicePolicyTitleModel();
				model.setNote_type(rs.getString("note_type"));
				model.setItem_type_id(rs.getInt("note_id"));
				model.setItem_title(rs.getString("item_title"));
				model.setItem_id(rs.getInt("item_id"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
