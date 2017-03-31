package dal.CoAgency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;

public class WtAgency_DisCitySelDal {
	private dbconn conn = new dbconn();

	// 获取所有城市及合作机构总数
	public List<CoAgencyBaseCityRelModel> getCoAgencyBaseCityRelList() {
		List<CoAgencyBaseCityRelModel> list = new ArrayList<CoAgencyBaseCityRelModel>();
		CoAgencyBaseCityRelModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("select pc.id,region=pr.name,province=pp.name,city=pc.name,zs=cb.zs from PubProCity pc ");
		sql.append("left join PubProvince pp on pc.provinceid = pp.id ");
		sql.append("left join PubRegion pr on pp.Regionid=pr.id ");
		sql.append("left join (select cabc_ppc_id,zs=count(cabc_id) from CoAgencyBaseCityRel cr inner join CoAgencyBaseService cs on cr.cabc_coab_id=cs.coas_coab_id and coas_type=1 and cs.coas_cabc_id=cr.cabc_id where cabc_state=1 group by cabc_ppc_id) cb on cb.cabc_ppc_id=pc.id ");
		sql.append("order by pr.name desc,pp.name,pc.name");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new CoAgencyBaseCityRelModel();
				m.setRegion(rs.getString("region"));
				m.setProvince(rs.getString("province"));
				m.setCity(rs.getString("city"));
				m.setHzAgencyCount(rs.getInt("zs"));
				m.setId(rs.getInt("id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据服务城市获取委托机构
	public List<CoAgencyBaseModel> getCoAgBaseListByCity(String city) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoAgencyBaseModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" select  city=ppc.name,id,cr.cabc_id,ca.coab_id,coab_setuptype,cabc_fee,coas_client,cabc_state,ca.coab_name,ca.coab_namespell,ca.coab_setuptype,cs.coas_client,cr.cabc_ifdefault from CoAgencyBase ca");
		sql.append(" inner join CoAgencyBaseCityRel cr on cr.cabc_coab_id=ca.coab_id 	inner join PubProCity ppc on ppc.id=cr.cabc_ppc_id ");
		sql.append("left join CoAgencyBaseService cs on cs.coas_cabc_id=cr.cabc_id and cs.coas_coab_id=ca.coab_id ");
		sql.append("where ppc.name=? ");
		sql.append("order by  city,ca.coab_name ");
		System.out.print(sql.toString());
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, city);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_namespell(rs.getString("coab_namespell"));
				m.setCoab_city(rs.getString("city"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCabc_fee(rs.getBigDecimal("cabc_fee"));
				m.setCabsModel(new CoAgencyBaseServiceModel());
				m.setCrModel(new CoAgencyBaseCityRelModel());
				m.getCabsModel().setCoas_client(rs.getString("coas_client"));
				m.getCrModel().setCabc_ifdefault(rs.getInt("cabc_ifdefault"));
				m.getCrModel().setCabc_id(rs.getInt("cabc_id"));
				m.getCrModel().setCabc_state(rs.getInt("cabc_state"));
				m.getCrModel().setId(rs.getInt("id"));
				
				list.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 根据服务城市获取未服务该城市的委托机构（用于分配服务城市界面）
	public List<CoAgencyBaseModel> getCoAgBaseListNotCity(String city) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		CoAgencyBaseModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select coab_id,coab_name,coab_namespell,coab_city,coab_setuptype,coas_client from CoAgencyBase cb ");
		sql.append("inner join CoAgencyBaseService cs on cb.coab_id=cs.coas_coab_id and coas_type=1 ");
		sql.append("where not exists((select cr.cabc_coab_id from CoAgencyBaseCityRel cr inner join PubProCity pc on cr.cabc_ppc_id=pc.id where pc.name=? and cb.coab_id=cr.cabc_coab_id and cr.cabc_state=1))");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setString(1, city);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m = new CoAgencyBaseModel();
				m.setCoab_id(rs.getInt("coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_namespell(rs.getString("coab_namespell"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setCoab_city(rs.getString("coab_city"));
				m.setCabsModel(new CoAgencyBaseServiceModel());
				m.getCabsModel().setCoas_client(rs.getString("coas_client"));
				list.add(m);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
