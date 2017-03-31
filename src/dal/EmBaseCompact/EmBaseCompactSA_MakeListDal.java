package dal.EmBaseCompact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmBaseCompactListModel;

public class EmBaseCompactSA_MakeListDal {
	// 显示劳动合同补充协议基本信息
	public List<EmBaseCompactListModel> getcompactsabase(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select top 1 ebco_id,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,ebco_work_place,ebco_wage,ebco_working_station from EmBaseCompact a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where ebco_state<5 and a.gid="+gid;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEbco_change(rs.getString("ebco_change"));
				model.setEbco_id(rs.getInt("ebco_id"));
				model.setEbco_work_place(rs.getString("ebco_work_place"));
				model.setEbco_wage(rs.getString("ebco_wage"));
				model.setEbco_working_station(rs
						.getString("ebco_working_station"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同补充协议未制作数据
	public List<EmBaseCompactListModel> getemcompactsalist(String name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str="";
		if (!name.equals("")){
			str=" and emba_name like '%"+name+"%'";
		}
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebco_id,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,ebco_work_place,ebco_wage,ebco_working_station from EmBaseCompact a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where ebco_state>2"+str;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEbco_change(rs.getString("ebco_change"));
				model.setEbco_id(rs.getInt("ebco_id"));
				model.setEbco_work_place(rs.getString("ebco_work_place"));
				model.setEbco_wage(rs.getString("ebco_wage"));
				model.setEbco_working_station(rs
						.getString("ebco_working_station"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同补充协议未审核数据
	public List<EmBaseCompactListModel> getautemcompactsalist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcu_id,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,ebco_work_place,ebco_wage,ebco_working_station,ebcu_tapr_id from EmBaseCompactUpdate a left join EmBaseCompact b on b.ebco_id=a.ebcu_ebco_id left join embase c on c.gid=b.gid left join cobase d on d.cid=b.cid where ebcu_upstate=0";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEbco_change(rs.getString("ebco_change"));
				model.setEbco_id(rs.getInt("ebcu_id"));
				model.setEbco_tapr_id(rs.getInt("ebcu_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同补充协议未打印数据
	public List<EmBaseCompactListModel> getpremcompactsalist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcu_id,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,ebco_work_place,ebco_wage,ebco_working_station,ebcu_tapr_id from EmBaseCompactUpdate a left join EmBaseCompact b on b.ebco_id=a.ebcu_ebco_id left join embase c on c.gid=b.gid left join cobase d on d.cid=b.cid where ebcu_upstate=1";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEbco_change(rs.getString("ebco_change"));
				model.setEbco_id(rs.getInt("ebcu_id"));
				model.setEbco_tapr_id(rs.getInt("ebcu_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同补充协议未签回数据
	public List<EmBaseCompactListModel> getsiemcompactsalist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcu_id,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,ebco_work_place,ebco_wage,ebco_working_station,ebcu_tapr_id from EmBaseCompactUpdate a left join EmBaseCompact b on b.ebco_id=a.ebcu_ebco_id left join embase c on c.gid=b.gid left join cobase d on d.cid=b.cid where ebcu_upstate=2";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEbco_change(rs.getString("ebco_change"));
				model.setEbco_id(rs.getInt("ebcu_id"));
				model.setEbco_tapr_id(rs.getInt("ebcu_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同补充协议未归档数据
	public List<EmBaseCompactListModel> getfilingemcompactsalist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcu_id,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,ebco_work_place,ebco_wage,ebco_working_station,ebco_wage_gz,ebcu_tapr_id from EmBaseCompactUpdate a left join EmBaseCompact b on b.ebco_id=a.ebcu_ebco_id left join embase c on c.gid=b.gid left join cobase d on d.cid=b.cid where ebcu_upstate=3";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEbco_change(rs.getString("ebco_change"));
				model.setEbco_id(rs.getInt("ebcu_id"));
				model.setEbco_wage(rs.getString("ebco_wage"));
				model.setEbco_wage_gz(rs.getString("ebco_wage_gz"));
				model.setEbco_working_station(rs
						.getString("ebco_working_station"));
				model.setEbco_tapr_id(rs.getInt("ebcu_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}
