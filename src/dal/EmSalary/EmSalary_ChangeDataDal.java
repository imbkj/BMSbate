package dal.EmSalary;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Conn.dbconn;
import Model.EmSalaryDataModel;

public class EmSalary_ChangeDataDal {
	private dbconn conn = new dbconn();

	// 获取工资变动数据
	public Map<String, List<EmSalaryDataModel>> getChangeData(int cid,
			int ownmonth, int taba_id) {
		Map<String, List<EmSalaryDataModel>> map = new HashMap<String, List<EmSalaryDataModel>>();
		List<EmSalaryDataModel> Addlist = new ArrayList<EmSalaryDataModel>();
		List<EmSalaryDataModel> Lesslist = new ArrayList<EmSalaryDataModel>();
		List<EmSalaryDataModel> Changelist = new ArrayList<EmSalaryDataModel>();
		List<EmSalaryDataModel> list = null;
		EmSalaryDataModel m;
		try {
			ResultSet rs = conn.GRS("exec EmSalary_ChangeDataByCid_p_lwj "
					+ cid + "," + ownmonth + "," + taba_id);
			while (rs.next()) {
				m = new EmSalaryDataModel();
				m.setEsda_id(rs.getInt("esda_id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setName(rs.getString("emba_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsda_usage_type(rs.getInt("esda_usage_type"));
				m.setEsda_pay(rs.getBigDecimal("esda_pay"));
				m.setEsda_siop(rs.getBigDecimal("esda_siop"));
				m.setEsda_hafop(rs.getBigDecimal("esda_hafop"));
				m.setEsda_total_pretax(rs.getBigDecimal("esda_total_pretax"));
				m.setEsda_tax_base(rs.getBigDecimal("esda_tax_base"));
				m.setEsda_tax(rs.getBigDecimal("esda_tax"));
				m.setEsda_db(rs.getBigDecimal("esda_db"));
				m.setEsda_db_tax_base(rs.getBigDecimal("esda_db_tax_base"));
				m.setEsda_db_tax(rs.getBigDecimal("esda_db_tax"));
				m.setEsda_stock(rs.getBigDecimal("esda_stock"));
				m.setEsda_stock_tax(rs.getBigDecimal("esda_stock_tax"));
				m.setEsda_write_off(rs.getBigDecimal("esda_write_off"));
				m.setEsda_dc(rs.getBigDecimal("esda_dc"));
				m.setEsda_dc_tax(rs.getBigDecimal("esda_dc_tax"));
				switch (rs.getInt("type")) {
				case 1:
					list = Addlist;
					break;
				case 2:
					list = Lesslist;
					break;
				case 3:
					list = Changelist;
					break;
				}
				list.add(m);
			}
			map.put("add", Addlist);
			map.put("less", Lesslist);
			map.put("change", Changelist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
