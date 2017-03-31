package dal.CoAgency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Model.CoAgencyLinkmanModel;

public class BaseInfo_SelDal {
	private dbconn conn = new dbconn();

	// 获取全国项目部客服姓名，返回列表
	public List<String> getClient() {
		List<String> client = new ArrayList<String>();
		String sql = "select log_name from login l inner join department d on l.dep_id=d.dep_id where d.dep_name='全国项目部' order by log_name";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				client.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	// 获取所有机构
	public List<CoAgencyBaseModel> getAgency(int coas_type) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoAgencyBaseModel m = null;
		String sql = "SELECT ca.*,cs.coas_id FROM CoAgencyBase ca left join CoAgencyBaseService cs on ca.coab_id=cs.coas_coab_id and coas_type=? where coab_state=1 order by coab_name";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, coas_type);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_shortname(rs.getString("coab_shortname"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoab_province(rs.getString("coab_province"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCoab_companymanager(rs.getString("coab_companymanager"));
				m.setCoab_capital(rs.getString("coab_capital"));
				m.setCoab_regaddress(rs.getString("coab_regaddress"));
				m.setCoab_source(rs.getString("coab_source"));
				m.setCoab_business(rs.getString("coab_business"));
				m.setCoab_remark(rs.getString("coab_remark"));
				m.setCoas_id(rs.getInt("coas_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托机构列表(委托机构管理界面)
	public List<CoAgencyBaseModel> getWtAgencyList() {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoAgencyBaseModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select province=pp.name,city=ppc.name,cr.cabc_id,ca.coab_id,cabc_ppc_id,ca.coab_name,ca.coab_namespell,ca.coab_setuptype,cs.coas_client,cr.cabc_ifdefault from CoAgencyBase ca ");
		sql.append("inner join CoAgencyBaseCityRel cr on cr.cabc_coab_id=ca.coab_id ");
		sql.append("inner join PubProCity ppc on ppc.id=cr.cabc_ppc_id ");
		sql.append("inner join PubProvince pp on pp.id=ppc.provinceid ");
		sql.append("left join CoAgencyBaseService cs on cs.coas_cabc_id=cr.cabc_id and cs.coas_coab_id=ca.coab_id ");
		sql.append("order by province,city,ca.coab_name ");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_namespell(rs.getString("coab_namespell"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoas_client(rs.getString("coas_client"));
				m.setCabc_id(rs.getInt("cabc_id"));
				m.setCabc_ifdefault(rs.getInt("cabc_ifdefault"));
				m.setCoab_province(rs.getString("province"));
				m.setCoab_city(rs.getString("city"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取受托机构列表(受托机构管理界面)
	public List<CoAgencyBaseModel> getStAgencyList() {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoAgencyBaseModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ca.coab_id,ca.coab_name,ca.coab_namespell,ca.coab_setuptype,ca.coab_province,ca.coab_city,cs.coas_client,cs.coas_hz FROM CoAgencyBase ca ");
		sql.append("inner join CoAgencyBaseService cs on ca.coab_id=cs.coas_coab_id and coas_type=2 ");
		sql.append("where coab_state=1 ");
		sql.append("order by coab_province,coab_city");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_namespell(rs.getString("coab_namespell"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoab_province(rs.getString("coab_province"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCabsModel(new CoAgencyBaseServiceModel());
				m.getCabsModel().setCoas_client(rs.getString("coas_client"));
				m.getCabsModel().setCoas_hz(rs.getString("coas_hz"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据coab_id获取服务城市列表
	public List<CoAgencyBaseCityRelModel> getServiceCityList(int coab_id) {
		List<CoAgencyBaseCityRelModel> list = new ArrayList<CoAgencyBaseCityRelModel>();
		CoAgencyBaseCityRelModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select pr.name as region,pp.name AS province, pc.name AS city,cr.* from CoAgencyBaseCityRel cr ");
		sql.append("inner join PubProCity pc ON cr.cabc_ppc_id = pc.id ");
		sql.append("inner JOIN PubProvince pp ON pp.id = pc.provinceid ");
		sql.append("inner join PubRegion pr on pr.id=pp.Regionid ");
		sql.append("where cr.cabc_coab_id=? ");
		sql.append("order by cabc_state desc,pr.name,pp.name,pc.name");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoAgencyBaseCityRelModel();
				m.setCabc_id(rs.getInt("cabc_id"));
				m.setRegion(rs.getString("region"));
				m.setProvince(rs.getString("province"));
				m.setCity(rs.getString("city"));
				m.setCabc_ifdefault(rs.getInt("cabc_ifdefault"));
				m.setCabc_state(rs.getInt("cabc_state"));
				m.setHzInfo();
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据coab_id获取机构基本信息
	public CoAgencyBaseModel getCoAgencyBaseModel(int coab_id) {
		CoAgencyBaseModel m = new CoAgencyBaseModel();
		String sql = "select * from CoAgencyBase where coab_id=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_shortname(rs.getString("coab_shortname"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoab_province(rs.getString("coab_province"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCoab_source(rs.getString("coab_source"));
				m.setCoab_capital(rs.getString("coab_capital"));
				m.setCoab_regaddress(rs.getString("coab_regaddress"));
				m.setCoab_companymanager(rs.getString("coab_companymanager"));
				m.setCoab_business(rs.getString("coab_business"));
				m.setCoab_remark(rs.getString("coab_remark"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据cabc_id获取机构基本信息
	public CoAgencyBaseModel getCoAgencyBaseModelByCabcId(int cabc_id) {
		CoAgencyBaseModel m = new CoAgencyBaseModel();
		String sql = "select cb.* from CoAgencyBaseCityRel cr inner join CoAgencyBase cb on cr.cabc_coab_id=cb.coab_id where cr.cabc_id=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cabc_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_shortname(rs.getString("coab_shortname"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoab_province(rs.getString("coab_province"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCoab_source(rs.getString("coab_source"));
				m.setCoab_capital(rs.getString("coab_capital"));
				m.setCoab_regaddress(rs.getString("coab_regaddress"));
				m.setCoab_companymanager(rs.getString("coab_companymanager"));
				m.setCoab_business(rs.getString("coab_business"));
				m.setCoab_remark(rs.getString("coab_remark"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据coab_name获取机构基本信息
	public CoAgencyBaseModel getCoAgencyBaseModel(String coab_name) {
		CoAgencyBaseModel m = null;
		String sql = "select * from CoAgencyBase where coab_name=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, coab_name);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_shortname(rs.getString("coab_shortname"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoab_province(rs.getString("coab_province"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCoab_source(rs.getString("coab_source"));
				m.setCoab_capital(rs.getString("coab_capital"));
				m.setCoab_regaddress(rs.getString("coab_regaddress"));
				m.setCoab_companymanager(rs.getString("coab_companymanager"));
				m.setCoab_business(rs.getString("coab_business"));
				m.setCoab_remark(rs.getString("coab_remark"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据coab_id获取机构服务约定信息
	public CoAgencyBaseServiceModel getCoAgencyBaseServiceModel(int coab_id,
			int coas_type) {
		CoAgencyBaseServiceModel m = new CoAgencyBaseServiceModel();
		String sql = "select * from CoAgencyBaseService where coas_coab_id=? and coas_type=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			psmt.setInt(2, coas_type);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCoas_id(rs.getInt("coas_id"));
				m.setCoas_coab_id(rs.getInt("coas_coab_id"));
				m.setCoas_type(rs.getInt("coas_type"));
				m.setCoas_accountBank(rs.getString("coas_accountBank"));
				m.setCoas_accountName(rs.getString("coas_accountName"));
				m.setCoas_accountNum(rs.getString("coas_accountNum"));
				m.setCoas_wtConfirReq(rs.getString("coas_wtConfirReq"));
				m.setCoas_wtFeedbackReq(rs.getString("coas_wtFeedbackReq"));
				m.setCoas_billDay(rs.getInt("coas_billDay"));
				m.setCoas_payday(rs.getInt("coas_payday"));
				m.setCoas_paymon(rs.getString("coas_paymon"));
				m.setCoas_payMethods(rs.getString("coas_payMethods"));
				m.setCoas_ifSendInvoice(rs.getString("coas_ifSendInvoice"));
				m.setCoas_invoiceAdd(rs.getString("coas_invoiceAdd"));
				m.setCoas_invoiceLinkman(rs.getString("coas_invoiceLinkman"));
				m.setCoas_hz(rs.getString("coas_hz"));
				m.setCoas_remark(rs.getString("coas_remark"));
				m.setCoas_client(rs.getString("coas_client"));
				m.setCoas_coaddress(rs.getString("coas_coaddress"));
				m.setCoas_zipcode(rs.getString("coas_zipcode"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据cabc_id获取机构服务约定信息
	public CoAgencyBaseServiceModel getCoAgencyBaseServiceModel(int cabc_id) {
		CoAgencyBaseServiceModel m = new CoAgencyBaseServiceModel();
		String sql = "select * from CoAgencyBaseService where coas_cabc_id=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cabc_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setCoas_id(rs.getInt("coas_id"));
				m.setCoas_coab_id(rs.getInt("coas_coab_id"));
				m.setCoas_type(rs.getInt("coas_type"));
				m.setCoas_accountBank(rs.getString("coas_accountBank"));
				m.setCoas_accountName(rs.getString("coas_accountName"));
				m.setCoas_accountNum(rs.getString("coas_accountNum"));
				m.setCoas_wtConfirReq(rs.getString("coas_wtConfirReq"));
				m.setCoas_wtFeedbackReq(rs.getString("coas_wtFeedbackReq"));
				m.setCoas_billDay(rs.getInt("coas_billDay"));
				m.setCoas_payday(rs.getInt("coas_payday"));
				m.setCoas_paymon(rs.getString("coas_paymon"));
				m.setCoas_payMethods(rs.getString("coas_payMethods"));
				m.setCoas_ifSendInvoice(rs.getString("coas_ifSendInvoice"));
				m.setCoas_invoiceAdd(rs.getString("coas_invoiceAdd"));
				m.setCoas_invoiceLinkman(rs.getString("coas_invoiceLinkman"));
				m.setCoas_hz(rs.getString("coas_hz"));
				m.setCoas_remark(rs.getString("coas_remark"));
				m.setCoas_client(rs.getString("coas_client"));
				m.setCoas_coaddress(rs.getString("coas_coaddress"));
				m.setCoas_zipcode(rs.getString("coas_zipcode"));
				m.setCoas_chstate(rs.getString("coas_chstate"));
				m.setCoas_zjday(rs.getInt("coas_zjday"));
				m.setCoas_zyday(rs.getInt("coas_zyday"));
				m.setCoas_jyday(rs.getInt("coas_jyday"));
				m.setCoas_autstate(rs.getString("coas_autstate"));
				m.setCoas_autmon(rs.getString("coas_autmon"));
				m.setCoas_zyremark(rs.getString("coas_zyremark"));
				m.setCoas_jyremark(rs.getString("coas_jyremark"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据coab_id获取机构联系人列表
	public List<CoAgencyLinkmanModel> getCoAgencyLinkmanList(int coab_id,
			int cabl_type) {
		List<CoAgencyLinkmanModel> list = new ArrayList<CoAgencyLinkmanModel>();
		CoAgencyLinkmanModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cr.cabl_id,cr.cabl_state,cali_id,cali_linkman,cali_name,cali_ename,cali_job,cali_tel,cali_mobile,cali_fax,cali_email,");
		sql.append("cali_birth=CONVERT(varchar(100), cali_birth, 20),cali_hobby,cali_address,cali_personality,cali_remark,cali_vip,cali_DelReason ");
		sql.append("from CoAgencyBaseLinkRel cr inner join CoAgencyLinkman cl on cr.cabl_cali_id=cl.cali_id ");
		sql.append("where cabl_coab_id=? and (cabl_type=? or cabl_type=0) order by cabl_state desc");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			psmt.setInt(2, cabl_type);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoAgencyLinkmanModel();
				m.setCabl_id(rs.getInt("cabl_id"));
				m.setCabl_state(rs.getInt("cabl_state"));
				m.setCali_id(rs.getInt("cali_id"));
				m.setCali_linkman(rs.getString("cali_linkman"));
				m.setCali_name(rs.getString("cali_name"));
				m.setCali_ename(rs.getString("cali_ename"));
				m.setCali_job(rs.getString("cali_job"));
				m.setCali_tel(rs.getString("cali_tel"));
				m.setCali_mobile(rs.getString("cali_mobile"));
				m.setCali_fax(rs.getString("cali_fax"));
				m.setCali_email(rs.getString("cali_email"));
				m.setCali_birth(rs.getString("cali_birth"));
				m.setCali_hobby(rs.getString("cali_hobby"));
				m.setCali_address(rs.getString("cali_address"));
				m.setCali_personality(rs.getString("cali_personality"));
				m.setCali_remark(rs.getString("cali_remark"));
				m.setCali_vip(rs.getInt("cali_vip"));
				m.setCali_delReason(rs.getString("cali_delReason"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据cabc_id获取委托机构联系人列表
	public List<CoAgencyLinkmanModel> getWtCoAgencyLinkmanList(int cabc_id) {
		List<CoAgencyLinkmanModel> list = new ArrayList<CoAgencyLinkmanModel>();
		CoAgencyLinkmanModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cr.cabl_id,cr.cabl_state,cali_id,cali_linkman,cali_name,cali_ename,cali_job,cali_tel,cali_mobile,cali_fax,cali_email,");
		sql.append("cali_birth=CONVERT(varchar(100), cali_birth, 20),cali_hobby,cali_address,cali_personality,cali_remark,cali_vip,cali_DelReason ");
		sql.append("from CoAgencyLinkman cl inner join CoAgencyBaseLinkRel cr on cl.cali_id=cr.cabl_cali_id ");
		sql.append("where cr.cabl_cabc_id=? order by cabl_state desc");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cabc_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoAgencyLinkmanModel();
				m.setCabl_id(rs.getInt("cabl_id"));
				m.setCabl_state(rs.getInt("cabl_state"));
				m.setCali_id(rs.getInt("cali_id"));
				m.setCali_linkman(rs.getString("cali_linkman"));
				m.setCali_name(rs.getString("cali_name"));
				m.setCali_ename(rs.getString("cali_ename"));
				m.setCali_job(rs.getString("cali_job"));
				m.setCali_tel(rs.getString("cali_tel"));
				m.setCali_mobile(rs.getString("cali_mobile"));
				m.setCali_fax(rs.getString("cali_fax"));
				m.setCali_email(rs.getString("cali_email"));
				m.setCali_birth(rs.getString("cali_birth"));
				m.setCali_hobby(rs.getString("cali_hobby"));
				m.setCali_address(rs.getString("cali_address"));
				m.setCali_personality(rs.getString("cali_personality"));
				m.setCali_remark(rs.getString("cali_remark"));
				m.setCali_vip(rs.getInt("cali_vip"));
				m.setCali_delReason(rs.getString("cali_delReason"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据coab_id获取委托机构可操作的城市名称列表
	public List<String> getCityDisList(int coab_id) {
		List<String> list = new ArrayList<String>();
		String sql = "select pc.name from CoAgencyBaseCityRel cr inner join pubprocity pc on cr.cabc_ppc_id=pc.id where cabc_coab_id=? and cabc_state=1";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, coab_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据cabc_id获取机构信息
	public CoAgencyBaseCityRelModel getCoabModel(int cabc_id) {
		CoAgencyBaseCityRelModel m = null;
		String sql = "select cr.cabc_id,cr.cabc_ifdefault,cr.cabc_fee,cb.coab_id,cb.coab_name,city=ppc.name,cabc_ppc_id from CoAgencyBaseCityRel cr inner join CoAgencyBase cb on cr.cabc_coab_id=cb.coab_id inner join PubProCity ppc on ppc.id=cr.cabc_ppc_id where cabc_id=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, cabc_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new CoAgencyBaseCityRelModel();
				m.setCabc_id(rs.getInt("cabc_id"));
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCity(rs.getString("city"));
				m.setCabc_ifdefault(rs.getInt("cabc_ifdefault"));
				m.setCabc_fee(rs.getBigDecimal("cabc_fee"));
				m.setId(rs.getInt("cabc_ppc_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
}
