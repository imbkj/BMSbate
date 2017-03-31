package dal.SocialInsurance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Conn.dbconn;
import Model.SocialInsuranceClassInfoViewModel;

public class SocialInsuranceCalculatorDal {
	private static dbconn conn = new dbconn();

	// 根据soin_id获取算法特定项目详细信息
	public List<SocialInsuranceClassInfoViewModel> getSiAlInfo(int soin_id,
			Date execdate, String classtype) {
		List<SocialInsuranceClassInfoViewModel> list = new ArrayList<SocialInsuranceClassInfoViewModel>();
		try {
			ResultSet rs = callSiAlgorithmSel(soin_id, execdate, classtype);
			while (rs.next()) {
				list.add(new SocialInsuranceClassInfoViewModel(rs
						.getInt("sicl_id"), rs.getString("sicl_class"), rs
						.getString("sicl_name"), rs.getString("sicl_payunit"),
						rs.getBigDecimal("siai_basic_u"), rs
								.getBigDecimal("siai_basic_d"), rs
								.getBigDecimal("siai_deposit_u"), rs
								.getBigDecimal("siai_deposit_d"), rs
								.getString("siai_proportion"), rs
								.getString("side_decimal")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据soin_id获取算法所有项目详细信息返回Map
	public Map<String, SocialInsuranceClassInfoViewModel> getSiAlInfo(
			int soin_id, Date execdate) {
		Map<String, SocialInsuranceClassInfoViewModel> map = new HashMap<String, SocialInsuranceClassInfoViewModel>();
		try {
			ResultSet rs = callSiAlgorithmSel(soin_id, execdate, "all");
			while (rs.next()) {
				map.put(rs.getString("sicl_name")
						+ rs.getString("sicl_payunit"),
						new SocialInsuranceClassInfoViewModel(rs
								.getInt("sicl_id"), rs.getString("sicl_class"),
								rs.getString("sicl_name"), rs
										.getString("sicl_payunit"), rs
										.getBigDecimal("siai_basic_u"), rs
										.getBigDecimal("siai_basic_d"), rs
										.getBigDecimal("siai_deposit_u"), rs
										.getBigDecimal("siai_deposit_d"), rs
										.getString("siai_proportion"), rs
										.getString("side_decimal")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return map;
	}

	// 获取社保字典库项目类型
	public Map<Integer, String[]> getSocialInsuranceClass() {
		Map<Integer, String[]> map = new HashMap<Integer, String[]>();
		String sql = "select sicl_id,sicl_class,sicl_name from SocialInsuranceClass";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				map.put(rs.getInt("sicl_id"),
						new String[] { rs.getString("sicl_class"),
								rs.getString("sicl_name") });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 获取sion_id
	public int getSionId(String soin_title, String city, String coab_name) {
		int soin_id = 0;
		String sql = "select soin_id from SocialInsurance si left join CoAgencyBaseCityRel cr on si.soin_cabc_id=cr.cabc_id left join PubProCity pc on pc.id=cr.cabc_ppc_id left join CoAgencyBase cb on cb.coab_id=cr.cabc_coab_id where soin_title=? and pc.name=? and cb.coab_name=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, soin_title);
			psmt.setString(2, city);
			psmt.setString(3, coab_name);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			soin_id = rs.getInt("soin_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return soin_id;
	}

	// 执行查询社保算法存储过程
	private ResultSet callSiAlgorithmSel(int soin_id, Date execdate,
			String classtype) throws Exception {
		ResultSet rs = conn.GRS("exec SocialInsuranceCalculatorDalSel_p_lwj "
				+ soin_id + "," + "'" + DatetoSting(execdate) + "'" + "," + "'"
				+ classtype + "'");
		return rs;
	}

	// Date类型转换String
	private String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-1");
		String Date = sdf.format(d);
		return Date;
	}
}
