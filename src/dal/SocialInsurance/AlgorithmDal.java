/**
 * 
 */
/**
 * @author Lee
 *
 */
package dal.SocialInsurance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseCityRelViewModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;

public class AlgorithmDal {
	private static dbconn conn = new dbconn();

	// 获取机构服务省份数据集
	private ResultSet getCoAgProvinceRs() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT distinct province FROM CoAgencyBaseCityRel_view order by province ";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取机构服务城市数据集
	private ResultSet getCoAgCityRs() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT distinct city FROM CoAgencyBaseCityRel_view order by city ";
		rs = conn.GRS(sql);
		return rs;
	}

	// 根据省份获取城市数据集
	private ResultSet getCoAgCityByProRs(String pro) throws Exception {
		ResultSet rs = null;
		String sql = "SELECT distinct city FROM CoAgencyBaseCityRel_view where province=? order by city ";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setString(1, pro);
		rs = psmt.executeQuery();
		return rs;
	}

	// 获取机构数据集
	private ResultSet getCoAgBaseRs() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT distinct coab_name FROM CoAgencyBaseCityRel_view order by coab_name ";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取机构及城市信息数据集
	private ResultSet getCoAgBaseCityRelRs() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT * FROM CoAgencyBaseCityRel_view order by cabc_state desc,province,city,coab_name";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取机构性质数据集
	private ResultSet getSetuptypeRs() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT distinct coab_setuptype FROM CoAgencyBaseCityRel_view order by coab_setuptype";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取机构性质数据集
	public List<String> getSetuptype() {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = getSetuptypeRs();
			while (rs.next()) {
				list.add(rs.getString("coab_setuptype"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取机构服务省份数据
	public List<String> getCoAgProvince() {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = getCoAgProvinceRs();
			while (rs.next()) {
				list.add(rs.getString("province"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取机构服务城市数据
	public List<String> getCoAgCity() {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = getCoAgCityRs();
			while (rs.next()) {
				list.add(rs.getString("city"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取机构数据
	public List<String> getCoAgBase() {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = getCoAgBaseRs();
			while (rs.next()) {
				list.add(rs.getString("coab_name"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 根据省份获取城市数据
	public List<String> getCoAgCityByPro(String pro) {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = getCoAgCityByProRs(pro);
			while (rs.next()) {
				list.add(rs.getString("city"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取机构及城市信息数据集
	public List<CoAgencyBaseCityRelViewModel> getCoAgBaseCityRel() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<CoAgencyBaseCityRelViewModel>();
		CoAgencyBaseCityRelViewModel m;
		try {
			ResultSet rs = getCoAgBaseCityRelRs();
			while (rs.next()) {
				m = new CoAgencyBaseCityRelViewModel();
				m.setCabc_id(rs.getInt("cabc_id"));
				m.setCabc_coab_id(rs.getInt("cabc_coab_id"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setCoab_namespell(rs.getString("coab_namespell"));
				m.setCoab_setuptype(rs.getString("coab_setuptype"));
				m.setProvince(rs.getString("province"));
				m.setCity(rs.getString("city"));
				m.setCabc_state(rs.getInt("cabc_state"));
				list.add(m);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 查询现用社保或历史社保算法(1现用及待用2历史)
	public List<SocialInsuranceAlgorithmViewModel> getSiAlgorithmSel(
			int cabc_id, int type) {
		List<SocialInsuranceAlgorithmViewModel> list = new ArrayList<SocialInsuranceAlgorithmViewModel>();
		ResultSet rs = null;
		try {
			if (type == 1) {
				rs = callSiAlgorithmSel(cabc_id, 1);
				while (rs.next()) {
					SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
					m.setSial_id(rs.getInt("sial_id"));
					m.setSoin_title(rs.getString("soin_title"));
					m.setSial_execdatestr(rs.getString("sial_execdate"));
					m.setSial_addname(rs.getString("sial_addname"));
					m.setSoin_state(rs.getString("state"));
					list.add(m);
				}
				rs = callSiAlgorithmSel(cabc_id, 2);
				while (rs.next()) {
					SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
					m.setSial_id(rs.getInt("sial_id"));
					m.setSoin_title(rs.getString("soin_title"));
					m.setSial_execdatestr(rs.getString("sial_execdate"));
					m.setSial_addname(rs.getString("sial_addname"));
					m.setSoin_state(rs.getString("state"));
					list.add(m);
				}
			} else if (type == 2) {
				rs = callSiAlgorithmSel(cabc_id, 3);
				while (rs.next()) {
					SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
					m.setSial_id(rs.getInt("sial_id"));
					m.setSoin_title(rs.getString("soin_title"));
					m.setSial_execdatestr(rs.getString("sial_execdate"));
					m.setSial_addname(rs.getString("sial_addname"));
					m.setSoin_state(rs.getString("state"));
					m.setSoin_delname(rs.getString("soin_delname"));
					m.setSoin_delreason(rs.getString("soin_delreason"));
					list.add(m);
				}
				rs = callSiAlgorithmSel(cabc_id, 4);
				while (rs.next()) {
					SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
					m.setSial_id(rs.getInt("sial_id"));
					m.setSoin_title(rs.getString("soin_title"));
					m.setSial_execdatestr(rs.getString("sial_execdate"));
					m.setSial_addname(rs.getString("sial_addname"));
					m.setSoin_state(rs.getString("state"));
					m.setSoin_delname(rs.getString("soin_delname"));
					m.setSoin_delreason(rs.getString("soin_delreason"));
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	// 查询待审核的社保算法
	public List<SocialInsuranceChangeModel> getSiAlgorithmChangeList(int cabc_id) {
		List<SocialInsuranceChangeModel> list = new ArrayList<SocialInsuranceChangeModel>();
		SocialInsuranceChangeModel m;
		String sql = "select sich_id,sich_title,sich_change_type from SocialInsuranceChange where sich_cabc_id=? and (sich_state=0 or sich_state=2)";
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, cabc_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new SocialInsuranceChangeModel();
				m.setSich_id(rs.getInt("sich_id"));
				m.setSich_title(rs.getString("sich_title"));
				m.setSich_change_type(rs.getInt("sich_change_type"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 执行查询社保算法存储过程
	private ResultSet callSiAlgorithmSel(int cabc_id, int type)
			throws Exception {
		ResultSet rs = conn.GRS("exec SiAlgorithmSel_p_lwj " + cabc_id + ","
				+ type);
		return rs;
	}

}