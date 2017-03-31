package dal.SystemControl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoLatencyClientModel;
import Model.DataPopedomModel;
import Model.LoginModel;
import Util.UserInfo;

public class Data_PopedomDal {

	public Data_PopedomDal() {

	}

	// 获取权限已签约客户列表集合
	public List<DataPopedomModel> getlist(String str) {
		List<DataPopedomModel> datapopedomm = new ArrayList<DataPopedomModel>();
		try {
			if (!datapopedomm.isEmpty())
				datapopedomm.clear();
			ResultSet rs = null;
			String sqlstr = "SELECT   cid, Dat_selected, Dat_edited,Dat_delete, log_id, log_name, dat_id, Dat_addname, Dat_addtime, coba_shortname,coba_client  FROM   View_DataPopedom where 1=1  "
					+ str + " order by coba_client ";
			System.out.print(sqlstr);
			try {
				dbconn db = new dbconn();
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					datapopedomm.add(new DataPopedomModel(rs.getInt("dat_id"),
							rs.getInt("log_id"), rs.getInt("cid"), rs
									.getBoolean("Dat_selected"), rs
									.getBoolean("Dat_edited"), rs
									.getBoolean("dat_delete"), rs
									.getString("dat_addname"), rs
									.getDate("Dat_addtime"), rs
									.getString("log_name"), rs
									.getString("coba_shortname"), rs
									.getString("coba_client")));
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return datapopedomm;
	}

	// 获取权限潜在客户列表集合

	public List<CoLatencyClientModel> getCoLatencyClientAllInfo(String str) {
		ResultSet rs = null;
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  a.*,num,CASE cola_successlevel WHEN 1 THEN '较低' WHEN 2 THEN '较低' ";
			sqlstr = sqlstr
					+ "WHEN 3 THEN '一般' WHEN 4 THEN '较高' WHEN 5 THEN '较高' ELSE '-' END AS slevel," +
					"cola_realsize,isnull(colanum,0) colanum from CoLatencyClient a  left join "
					+ "(select count(*) as num,coca_colaid from CoAgencyLinkman a,colaColiLinkRel b  where a.cali_id=b.coca_caliid "
					+ "and cali_datatype=2 group by coca_colaid) b on a.cola_id=b.coca_colaid "+
					// +"left join (select cola_id,log_id from DataPopedom where log_id="+UserInfo.getUserid()+"  ) c on a.cola_id=c.cola_id"
					// +
					// " left join (select * from login) e on c.log_id=e.log_id  "
					" left join ( select COUNT(*) colanum,coco_cola_id from CoCompact group by " +
					" coco_cola_id) c on a.cola_id=c.coco_cola_id"
					+ " where 1=1 and  a.cola_id in ( select cola_id from DataPopedom where log_id="
					+ UserInfo.getUserid() + "  and Dat_selected=1 ) " + str;
			sqlstr = sqlstr + " order by coba_LTS,cola_id desc";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				menuinfo.add(new CoLatencyClientModel(rs.getInt("cola_id"), rs
						.getInt("cid"), rs.getString("cola_company"), rs
						.getString("cola_spell"), rs
						.getString("cola_companytype"), rs
						.getString("cola_website"), rs.getInt("cola_sign"), rs
						.getString("cola_address"), rs
						.getString("cola_clientarea"), rs
						.getString("cola_trade"), rs
						.getString("cola_clientsize"), rs
						.getString("cola_clientsource"), rs
						.getString("cola_servicecontent"), rs
						.getString("cola_remark"), rs
						.getTimestamp("cola_addtime"), rs
						.getString("cola_addname"), rs
						.getInt("cola_successlevel"),
						rs.getInt("cola_ownyear"), rs.getInt("coba_LTS"), rs
								.getDate("cola_modifydate"), rs
								.getString("cola_modifyname"), 0, rs
								.getString("slevel"), rs
								.getString("cola_follower"), rs.getInt("num"),
						rs.getString("cola_realsize"),rs.getString("cola_kind"),rs.getInt("colanum")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuinfo;
	}

	// 获取权限潜在客户列表集合 管理

	public List<CoLatencyClientModel> getCoLatencyClientAllInfoedit(String str) {
		ResultSet rs = null;
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  a.*,num,CASE cola_successlevel WHEN 1 THEN '较低' WHEN 2 THEN '较低' ";
			sqlstr = sqlstr
					+ "WHEN 3 THEN '一般' WHEN 4 THEN '较高' WHEN 5 THEN '较高' ELSE '-' END AS slevel," +
					"cola_realsize,isnull(colanum,0) colanum from CoLatencyClient a  left join "
					+ "(select count(*) as num,coca_colaid from CoAgencyLinkman a,colaColiLinkRel b  where a.cali_id=b.coca_caliid "
					+ "and cali_datatype=2 group by coca_colaid) b on a.cola_id=b.coca_colaid " +
					" left join ( select COUNT(*) colanum,coco_cola_id from CoCompact where coco_state>3 " +
					" group by coco_cola_id) c on a.cola_id=c.coco_cola_id "
					+ " where 1=1  and  a.cola_id in ( select cola_id from DataPopedom where log_id="
					+ UserInfo.getUserid() + "  and Dat_edited=1 )  " + str;
			sqlstr = sqlstr + " order by coba_LTS,cola_id desc";
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				menuinfo.add(new CoLatencyClientModel(rs.getInt("cola_id"), rs
						.getInt("cid"), rs.getString("cola_company"), rs
						.getString("cola_spell"), rs
						.getString("cola_companytype"), rs
						.getString("cola_website"), rs.getInt("cola_sign"), rs
						.getString("cola_address"), rs
						.getString("cola_clientarea"), rs
						.getString("cola_trade"), rs
						.getString("cola_clientsize"), rs
						.getString("cola_clientsource"), rs
						.getString("cola_servicecontent"), rs
						.getString("cola_remark"), rs
						.getTimestamp("cola_addtime"), rs
						.getString("cola_addname"), rs
						.getInt("cola_successlevel"),
						rs.getInt("cola_ownyear"), rs.getInt("coba_LTS"), rs
								.getDate("cola_modifydate"), rs
								.getString("cola_modifyname"), 0, rs
								.getString("slevel"), rs
								.getString("cola_follower"), rs.getInt("num"),
						rs.getString("cola_realsize"),rs.getString("cola_kind"),
						rs.getInt("colanum")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuinfo;
	}

	public List<CoLatencyClientModel> getCoLatencyClientlist(String str) {
		ResultSet rs = null;
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {

			dbconn db = new dbconn();
			String sqlstr = "SELECT  *,CASE cola_successlevel WHEN 1 THEN '较低' WHEN 2 THEN '较低' ";
			sqlstr = sqlstr
					+ "WHEN 3 THEN '一般' WHEN 4 THEN '较高' WHEN 5 THEN '较高' ELSE '-' END AS slevel, "
					+ "dat_id,log_id,dat_selected,dat_edited,dat_delete,dat_addname,dat_addtime,log_name from View_ColatencyDatePopedom where 1=1 "
					+ str;
			sqlstr = sqlstr + " order by cola_id desc";
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);

			while (rs.next()) {
				menuinfo.add(new CoLatencyClientModel(rs.getInt("cola_id"), rs
						.getInt("cid"), rs.getString("cola_company"), rs
						.getString("cola_spell"), rs
						.getString("cola_companytype"), rs
						.getString("cola_website"), rs.getInt("cola_sign"), rs
						.getString("cola_address"), rs
						.getString("cola_clientarea"), rs
						.getString("cola_trade"), rs
						.getString("cola_clientsize"), rs
						.getString("cola_clientsource"), rs
						.getString("cola_servicecontent"), rs
						.getString("cola_remark"), rs
						.getTimestamp("cola_addtime"), rs
						.getString("cola_addname"), rs
						.getInt("cola_successlevel"),
						rs.getInt("cola_ownyear"), rs.getInt("coba_LTS"), rs
								.getDate("cola_modifydate"), rs
								.getString("cola_modifyname"), 0, rs
								.getString("slevel"), rs
								.getString("cola_follower"), rs
								.getInt("dat_id"), rs.getInt("log_id"), rs
								.getBoolean("dat_selected"), rs
								.getBoolean("dat_edited"), rs
								.getBoolean("dat_delete"), rs
								.getString("dat_addname"), rs
								.getDate("dat_addtime")
								,rs.getString("cola_kind")
				));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuinfo;
	}

	// 获取login 集合
	public List<LoginModel> getLoginlist() {
		List<LoginModel> loginm = new ArrayList<LoginModel>();
		try {
			if (!loginm.isEmpty())
				loginm.clear();

			ResultSet rs = null;
			String sqlstr = "SELECT log_id ,log_spell,log_ip,log_name,log_teamleader,dep_id,log_tel,log_ext,log_inure,log_sex,log_pws,log_email,log_mobile,log_pic,Addtime,Addname,log_intime,log_pid  FROM Login where log_inure=1 order by log_spell ";
			// System.out.print(sqlstr);
			try {
				dbconn db = new dbconn();
				rs = db.GRS(sqlstr);

				while (rs.next()) {
					loginm.add(new LoginModel(rs.getInt("log_id"), rs
							.getString("log_name"),
							rs.getInt("log_teamleader"), rs
									.getString("log_tel"), rs
									.getString("log_sex"), rs
									.getString("log_pws"), rs
									.getString("log_email"), rs
									.getString("log_mobile"), rs
									.getString("log_intime"), rs
									.getInt("dep_id")));

				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return loginm;
	}

	// 获取 带权限的list
	public List<LoginModel> getLoginlist(int log_id) {

		List<LoginModel> loginm = new ArrayList<LoginModel>();

		try {
			if (!loginm.isEmpty())
				loginm.clear();

			ResultSet rs = null;
			String sqlstr = "select * from GetChildbase(" + log_id + ",0)";
			// System.out.print(sqlstr);
			try {
				dbconn db = new dbconn();
				rs = db.GRS(sqlstr);

				while (rs.next()) {
					loginm.add(new LoginModel(rs.getInt("UnitId"), rs
							.getString("username")));
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return loginm;
	}

	// 角色获取
	public List<LoginModel> getroleLoginlist(String Role_name) {
		List<LoginModel> loginm = new ArrayList<LoginModel>();
		if (!loginm.isEmpty())
			loginm.clear();
		ResultSet rs = null;
		String sqlstr = "SELECT log_id, log_name, lgp_id, rol_id, rol_name FROM   View_loginrole where 1=1  "
				+ Role_name + "";
		// System.out.print(sqlstr);
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sqlstr);

			while (rs.next()) {
				loginm.add(new LoginModel(rs.getInt("log_id"), rs
						.getString("log_name")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return loginm;
	}

	// 带部门的list
	public List<LoginModel> getLoginlist(String str) {

		List<LoginModel> loginm = new ArrayList<LoginModel>();

		try {
			if (!loginm.isEmpty())
				loginm.clear();

			ResultSet rs = null;
			String sqlstr = "SELECT log_id ,log_spell,log_ip,log_name,log_teamleader,dep_id,log_tel,log_ext,log_inure,log_sex,log_pws,log_email,log_mobile,log_pic,Addtime,Addname,log_intime,log_pid  FROM Login where log_inure=1 "
					+ str + " order by log_name ";
			// System.out.print(sqlstr);
			try {
				dbconn db = new dbconn();
				rs = db.GRS(sqlstr);

				while (rs.next()) {
					loginm.add(new LoginModel(rs.getInt("log_id"), rs
							.getString("log_name"),
							rs.getInt("log_teamleader"), rs
									.getString("log_tel"), rs
									.getString("log_sex"), rs
									.getString("log_pws"), rs
									.getString("log_email"), rs
									.getString("log_mobile"), rs
									.getString("log_intime"), rs
									.getInt("dep_id")));

				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return loginm;
	}

	// 标准权限：前道上级看下级，后道部门默认拥有全部权限。

	// 特殊权限：临时调整权限 （独立于上下级权限？）

	// 上下级，包括后道部门权限，一般入职分配客服的时候使用
	public Integer DataPopedomAdd(DataPopedomModel m) {
		dbconn db = new dbconn();

		try {
			CallableStatement c = db
					.getcall("DataPopedomAdd_p_zmj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getLog_id());
			c.setInt(2, m.getCid());
			c.setBoolean(3, m.getDat_selected());
			c.setBoolean(4, m.getDat_edited());
			c.setBoolean(5, m.getDat_delete());
			c.setString(6, m.getDat_addname());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);

		} catch (SQLException e) {
			return 1;
		}
	}
	
	
	//全国项目部权限(特殊)
	public Integer DataPopedomallAdd(DataPopedomModel m) {
		dbconn db = new dbconn();

		try {
			CallableStatement c = db
					.getcall("DataPopedomallAdd_p_zmj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getLog_id());
			c.setInt(2, m.getCid());
			c.setBoolean(3, m.getDat_selected());
			c.setBoolean(4, m.getDat_edited());
			c.setBoolean(5, m.getDat_delete());
			c.setString(6, m.getDat_addname());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);

		} catch (SQLException e) {
			return 1;
		}
	}
	
	

	// 仅上下级权限：转客服,薪酬负责人使用
	public Integer DataPopedomAddone(DataPopedomModel m) {
		dbconn db = new dbconn();

		try {
			CallableStatement c = db
					.getcall("DataPopedomcsAdd_p_zmj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getLog_id());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getCola_id());
			c.setBoolean(4, m.getDat_selected());
			c.setBoolean(5, m.getDat_edited());
			c.setBoolean(6, m.getDat_delete());
			c.setString(7, m.getDat_addname());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 权限删除上下级全部删除
	public Integer DataPopedomdelete(DataPopedomModel m) {
		dbconn db = new dbconn();

		try {
			CallableStatement c = db
					.getcall("DataPopedomdelete_p_zmj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getLog_id());
			c.setInt(2, m.getCid());
			c.setBoolean(3, m.getDat_selected());
			c.setBoolean(4, m.getDat_edited());
			c.setBoolean(5, m.getDat_delete());
			c.setString(6, m.getDat_addname());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改客服，薪酬负责人，潜在客户跟进人
	public Integer DataPopedomcsedit(DataPopedomModel m) {
		dbconn db = new dbconn();

		try {
			CallableStatement c = db
					.getcall("DataPopedomcsedit_p_zmj(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getLog_id());
			c.setInt(2, m.getOldlog_id());
			c.setInt(3, m.getCid());
			c.setInt(4, m.getCola_id());
			c.setBoolean(5, m.getDat_selected());
			c.setBoolean(6, m.getDat_edited());
			c.setBoolean(7, m.getDat_delete());
			c.setString(8, m.getDat_addname());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);

		} catch (SQLException e) {
			return 1;
		}
	}
}
