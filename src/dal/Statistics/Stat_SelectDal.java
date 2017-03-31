package dal.Statistics;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.StatisticsResultModel;

public class Stat_SelectDal {
	private dbconn conn = new dbconn();

	// 获取统计数据(按log_id)
	public List<StatisticsResultModel> getStatResult(Integer log_id,String puzu_name, String str) {
		List<StatisticsResultModel> list = new ArrayList<StatisticsResultModel>();
		StatisticsResultModel m = null;
		String sql = "SELECT * FROM StatisticsResult_V WHERE stre_state=1 AND stre_log_id="
				+ log_id+" AND puzu_page='"+puzu_name+"'"+str+" ORDER BY stre_smonth ";
		System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {

				m = new StatisticsResultModel();
				m.setStre_id(rs.getInt("stre_id"));
				m.setStre_log_id(rs.getInt("stre_log_id"));
				m.setStre_smonth(rs.getInt("stre_smonth"));
				m.setStre_emonth(rs.getInt("stre_emonth"));
				m.setStre_content(rs.getString("stre_content"));
				m.setStre_sum(rs.getString("stre_sum"));
				m.setStre_state(rs.getInt("stre_state"));
				m.setStsq_id(rs.getInt("stre_stsq_id"));
				m.setStsq_puzu_id(rs.getInt("stsq_puzu_id"));
				m.setStsq_content(rs.getString("stsq_content"));
				m.setStsq_sql(rs.getString("stsq_sql"));
				m.setStsq_state(rs.getInt("stsq_state"));
				m.setStsq_type(rs.getInt("stsq_type"));
				if (rs.getInt("stsq_type") == 1) {
					m.setS_stsq_type("按月");
				} else if (rs.getInt("stsq_type") == 2) {
					m.setS_stsq_type("按季");
				} else if (rs.getInt("stsq_type") == 3) {
					m.setS_stsq_type("按年");
				} else if (rs.getInt("stsq_type") == 4) {
					m.setS_stsq_type("按指定时间段");
				}
				m.setStsq_uom(rs.getString("stsq_uom"));
				m.setStsq_graph(rs.getInt("stsq_graph"));
				if (rs.getInt("stsq_graph") == 1) {
					m.setS_stsq_graph("列表");
				} else if (rs.getInt("stsq_graph") == 2) {
					m.setS_stsq_graph("条形");
				} else if (rs.getInt("stsq_graph") == 3) {
					m.setS_stsq_graph("饼图");
				} else if (rs.getInt("stsq_graph") == 4) {
					m.setS_stsq_graph("趋势图");
				}
				m.setPuzu_class(rs.getString("puzu_pclass"));
				m.setPuzu_page(rs.getString("puzu_page"));

				list.add(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
