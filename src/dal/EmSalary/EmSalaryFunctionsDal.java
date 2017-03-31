package dal.EmSalary;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import Conn.dbconn;

public class EmSalaryFunctionsDal {
	private static dbconn conn = new dbconn();

	public Map<String, BigDecimal> getSpec(String city,Integer ownmonth) {
		String sql = "select es.essp_name,es.essp_fee from (SELECT  essp_name,essp_fee,id=(ROW_NUMBER() over(partition by essp_name order by essp_id desc)) FROM EmSalarySpec where essp_city=? and "+ownmonth+">=essp_starmonth) es where es.id=1";
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		try {
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setString(1, city);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getString("essp_name"), rs.getBigDecimal("essp_fee"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
