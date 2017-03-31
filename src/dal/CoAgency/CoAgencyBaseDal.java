/**
 * @Classname CoAgencyBaseDal
 * @ClassInfo 委托机构基本信息数据库访问类
 * @author 林少斌、李文洁
 * @Date 2013-9-12
 */
package dal.CoAgency;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyBaseModel;
import Model.CoAgencyLinkmanModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.zkoss.zul.ListModelList;

public class CoAgencyBaseDal {

	private dbconn conn = new dbconn();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 获取机构内省份数据集
	private ResultSet getCoAgProvince() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT distinct coab_province FROM CoAgencyBase where coab_state=1 order by coab_province";
		rs = conn.GRS(sql);
		return rs;
	}

	// 根据省份获取机构城市数据集
	private ResultSet getCoAgCityByPro(String pro) throws Exception {
		ResultSet rs = null;
		String sql = "SELECT distinct coab_city FROM CoAgencyBase where coab_state=1 and coab_province='"
				+ pro + "' order by coab_city ";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取所有委托机构(CoAgencyBase)
	public ArrayList<CoAgencyBaseModel> getCoAgBaseList() {

		ArrayList<CoAgencyBaseModel> coagBaseList = new ArrayList<CoAgencyBaseModel>();
		/*
		 * CoAgencyBaseModel m; try { ResultSet rs = getCoAgBase(); while
		 * (rs.next()) { m = new CoAgencyBaseModel();
		 * m.setCoab_id(rs.getInt("coab_id"));
		 * m.setCoab_name(rs.getString("coab_name"));
		 * m.setCoab_setuptype(rs.getString("coab_setuptype"));
		 * m.setCoab_province(rs.getString("coab_province"));
		 * m.setCoab_city(rs.getString("coab_city"));
		 * m.setCoab_zipcode(rs.getString("coab_zipcode"));
		 * m.setCoab_coaddress(rs.getString("coab_coaddress"));
		 * m.setCoab_companymanager(rs.getString("coab_companymanager"));
		 * m.setCoab_capital(rs.getString("coab_capital"));
		 * m.setCoab_regaddress(rs.getString("coab_regaddress"));
		 * m.setCoab_source(rs.getString("coab_source"));
		 * m.setCoab_business(rs.getString("coab_business"));
		 * m.setCoab_remark(rs.getString("coab_remark"));
		 * m.setCoab_addname(rs.getString("coab_addname"));
		 * m.setCoab_addtime(sdf.format(rs.getString("coab_addtime")));
		 * m.setCoab_state(rs.getInt("coab_state")); coagBaseList.add(m); }
		 * 
		 * } catch (Exception e) { System.out.println(e.toString()); }
		 */
		return coagBaseList;
	}

	// 根据查询语句获取委托机构
	public ArrayList<CoAgencyBaseModel> getCoAgBaseList(String str) {
		ArrayList<CoAgencyBaseModel> coagBaseList = new ArrayList<CoAgencyBaseModel>();
		/*
		 * CoAgencyBaseModel m; try { ResultSet rs = getCoAgBase(str); while
		 * (rs.next()) { m = new CoAgencyBaseModel();
		 * m.setCoab_id(rs.getInt("coab_id"));
		 * m.setCoab_name(rs.getString("coab_name"));
		 * m.setCoab_setuptype(rs.getString("coab_setuptype"));
		 * m.setCoab_province(rs.getString("coab_province"));
		 * m.setCoab_city(rs.getString("coab_city"));
		 * m.setCoab_zipcode(rs.getString("coab_zipcode"));
		 * m.setCoab_coaddress(rs.getString("coab_coaddress"));
		 * m.setCoab_companymanager(rs.getString("coab_companymanager"));
		 * m.setCoab_capital(rs.getString("coab_capital"));
		 * m.setCoab_regaddress(rs.getString("coab_regaddress"));
		 * m.setCoab_source(rs.getString("coab_source"));
		 * m.setCoab_business(rs.getString("coab_business"));
		 * m.setCoab_remark(rs.getString("coab_remark"));
		 * m.setCoab_addname(rs.getString("coab_addname"));
		 * m.setCoab_addtime(sdf.format(rs.getString("coab_addtime")));
		 * m.setCoab_state(rs.getInt("coab_state")); coagBaseList.add(m); }
		 * 
		 * } catch (Exception e) { System.out.println(e.toString()); }
		 */
		return coagBaseList;
	}

	// 根据服务城市获取委托机构
	public List<CoAgencyBaseCityRelViewModel> getCoAgBaseListByCity(String city) {
		List<CoAgencyBaseCityRelViewModel> coagBaseList = new ArrayList<CoAgencyBaseCityRelViewModel>();
		CoAgencyBaseCityRelViewModel m = null;
		String sql = "select cabc_id,coab_id,coab_name,coab_namespell,coab_city,coab_setuptype,coab_hz,coab_client,cabc_ifdefault from CoAgencyBaseCityRel_view where city=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, city);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoAgencyBaseCityRelViewModel();
				m.setCabc_id(rs.getInt("cabc_id"));
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_namespell(rs.getString("coab_namespell"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoab_hz(rs.getString("coab_hz"));
				m.setCoab_client(rs.getString("coab_client"));
				m.setCabc_ifdefault(rs.getInt("cabc_ifdefault"));
				coagBaseList.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return coagBaseList;
	}

	// 根据服务城市获取委托机构
	public List<CoAgencyBaseModel> getCoAgBaseByCity(String city) {
		List<CoAgencyBaseModel> coagBaseList = new ArrayList<CoAgencyBaseModel>();
		/*
		 * CoAgencyBaseModel m = null; String sql =
		 * "select * from CoAgencyBaseCityRel_view where city=?";
		 * PreparedStatement psmt = conn.getpre(sql); try { psmt.setString(1,
		 * city); ResultSet rs = psmt.executeQuery(); while (rs.next()) { m =
		 * new CoAgencyBaseModel(); m.setCoab_id(rs.getInt("coab_id"));
		 * m.setCoab_name(rs.getString("coab_name"));
		 * m.setCoab_setuptype(rs.getString("coab_setuptype"));
		 * m.setCoab_province(rs.getString("coab_province"));
		 * m.setCoab_city(rs.getString("coab_city"));
		 * m.setCoab_zipcode(rs.getString("coab_zipcode"));
		 * m.setCoab_coaddress(rs.getString("coab_coaddress"));
		 * m.setCoab_companymanager(rs.getString("coab_companymanager"));
		 * m.setCoab_capital(rs.getString("coab_capital"));
		 * m.setCoab_regaddress(rs.getString("coab_regaddress"));
		 * m.setCoab_source(rs.getString("coab_source"));
		 * m.setCoab_business(rs.getString("coab_business"));
		 * m.setCoab_remark(rs.getString("coab_remark"));
		 * m.setCoab_addname(rs.getString("coab_addname"));
		 * m.setCoab_addtime(sdf.format(rs.getString("coab_addtime")));
		 * m.setCoab_state(rs.getInt("coab_state")); coagBaseList.add(m); } }
		 * catch (Exception e) { System.out.println(e.toString()); }
		 */
		return coagBaseList;
	}

	// 获取机构省份
	public List<String> getCoAgProvinceList() {
		List<String> CoAgProvinceList = new ArrayList<String>();
		try {
			ResultSet rs = getCoAgProvince();
			while (rs.next()) {
				CoAgProvinceList.add(rs.getString("coab_province"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoAgProvinceList;
	}

	// 根据省份获取机构城市
	public List<String> searchCoAgCityByPro(String pro) {
		List<String> CoAgCityList = new ArrayList<String>();
		try {
			ResultSet rs = getCoAgCityByPro(pro);
			while (rs.next()) {
				CoAgCityList.add(rs.getString("coab_city"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoAgCityList;
	}

	// 查询城市省份地区及机构数量数据集
	private ResultSet getCityBaseCountRs() throws Exception {
		ResultSet rs = null;
		String sql = "select region=pr.name,provice=pp.name,city=pc.name,zs=(select count(cabc_id) from CoAgencyBaseCityRel cr left join CoAgencyBase cb on cr.cabc_coab_id=cb.coab_id where cabc_state=1 and cabc_ppc_id=pc.id and coab_state=1) from PubProCity pc left join PubProvince pp on pc.provinceid = pp.id left join PubRegion pr on pp.Regionid=pr.id order by pr.name desc,pp.name,pc.name";
		rs = conn.GRS(sql);
		return rs;
	}

	// 按地区省份城市查询城市省份地区及机构数量数据集
	private ResultSet getCityBaseCountRs(String region, String province,
			String city) throws Exception {
		ResultSet rs = null;
		String sql = "select region=pr.name,provice=pp.name,city=pc.name,zs=(select count(cabc_id) from CoAgencyBaseCityRel where cabc_state=1 and cabc_ppc_id=pc.id) from PubProCity pc left join PubProvince pp on pc.provinceid = pp.id left join PubRegion pr on pp.Regionid=pr.id where pr.name=? or pp.name=? or pc.name=? order by pr.name desc,pp.name,pc.name";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setString(1, region);
		psmt.setString(2, province);
		psmt.setString(3, city);
		rs = psmt.executeQuery();
		return rs;
	}

	// 查询城市省份地区及机构数量
	public List<String[]> getCityBaseCount() {
		List<String[]> cityBase = new ArrayList<String[]>();
		try {
			ResultSet rs = getCityBaseCountRs();
			while (rs.next()) {
				cityBase.add(new String[] { rs.getString("region"),
						rs.getString("provice"), rs.getString("city"),
						rs.getString("zs") });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cityBase;
	}

	// 按地区省份城市查询城市省份地区及机构数量
	public List<String[]> getCityBaseCount(String region, String province,
			String city) {
		ArrayList<String[]> cityBase = new ArrayList<String[]>();
		try {
			ResultSet rs = getCityBaseCountRs(region, province, city);
			while (rs.next()) {
				cityBase.add(new String[] { rs.getString("region"),
						rs.getString("provice"), rs.getString("city"),
						rs.getString("zs") });
			}
		} catch (Exception e) {

		}
		return cityBase;
	}

	// 查询活动联系人
	public List<CoAgencyLinkmanModel> getlinkInfo(Integer gid) {
		List<CoAgencyLinkmanModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql="select cali_id,cali_name,cali_mobile,cali_address" +
				" from EmBase a" +
				" inner join CoBaseLinkRel b on a.cid=b.cid" +
				" inner join CoAgencyLinkman c on b.cblr_cali_id=c.cali_id" +
				" where cali_datatype=2 and gid=? and cali_duty like '%活动福利联系人%'" +
				" and cali_state=1";
		System.out.println(sql);
		try {
			list= db.find(sql, CoAgencyLinkmanModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
