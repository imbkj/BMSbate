package dal.SystemControl;

import impl.UserInfoImpl;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import com.zhuozhengsoft.pageoffice.zoomseal.User;

import service.UserInfoService;

import Conn.dbconn;
import Model.MenuListModel;
import Model.RoleModel;
import Util.UserInfo;

public class Menu_RolePubDal {
	/*
	 * Author:陈耀家 Create date: 09/13/2013 Description:把菜单分配给角色，操作role和menuList表
	 */

	// 获取角色的信息
	public List<RoleModel> getRoleData() {
		boolean flag = isManager();
		ResultSet rs = null;
		List<RoleModel> roleinfo = new ArrayList<RoleModel>();
		if (!roleinfo.isEmpty())
			roleinfo.clear();
		roleinfo.add(new RoleModel(0, "", "", ""));
		try {
			String str = "";
			if (!flag) {
				str = " and rol_id not in(select a.rol_id from role a inner join logingroup b "
						+ " on a.rol_id=b.rol_id inner join Login c on b.log_id=c.log_id where "
						+ " log_name='"
						+ UserInfo.getUsername()
						+ "' and  ( rol_name like '%部门经理%' "
						+ "or rol_name like '%部门副经理%'))";
				str = str
						+ " and rol_id in(select rol_id from logingroup a inner join login b "
						+ "on a.log_id=b.log_id and b.dep_id in(select a.dep_id from Login a "
						+ "inner join department b on a.dep_id=b.dep_id "
						+ "where log_name='" + UserInfo.getUsername() + "'))";
			}
			dbconn db = new dbconn();
			String sqlstr = "select * from role where 1=1 " + str;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				roleinfo.add(new RoleModel(rs.getInt("rol_id"), rs
						.getString("rol_name"), rs.getString("rol_index"), rs
						.getString("addname")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roleinfo;
	}

	// 菜单角色分配表的某条信息
	public List<MenuListModel> getmenupub(String str) {
		ResultSet rs = null;
		List<MenuListModel> roleinfo = new ArrayList<MenuListModel>();
		if (!roleinfo.isEmpty())
			roleinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "select * from menugroup where 1=1 " + str;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				MenuListModel menumodel = new MenuListModel();
				menumodel.setMeu_id(rs.getInt("meu_id"));
				roleinfo.add(menumodel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roleinfo;
	}

	// 更新菜单权限
	public int updateMenuPub(int rol_id, int meu_id) {
		int k = 0;
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sqlstr = "select * from menugroup where rol_id=" + rol_id
				+ " and meu_id=" + meu_id;
		try {
			rs = db.GRS(sqlstr);
			if (!rs.next()) {
				Session session = Executions.getCurrent().getDesktop()
						.getSession();
				UserInfoService uservice = new UserInfoImpl(session);
				String username = uservice.getUsername();
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String meustr = "insert into menugroup(rol_id,meu_id,addname,addtime) values("
						+ rol_id
						+ ","
						+ meu_id
						+ ",'"
						+ username
						+ "','"
						+ formatter.format(currentTime) + "')";
				k = db.execQuery(meustr);
			}
			else
			{
				k=1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 删除菜单权限
	public int deleteMenuPub(String str, int rol_id) {
		int k = 0;
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sqlstr = "";
		if (str == "all" || str.equals("all")) {
			sqlstr = "delete from menugroup where rol_id=" + rol_id;
		} else {
			sqlstr = "delete from menugroup where rol_id=" + rol_id
					+ " and meu_id not in(" + str + ")";
		}
		k = db.execQuery(sqlstr);
		return k;
	}

	// 查询是否是系统管理员
	private boolean isManager() {
		String sql = "select * from role a inner join logingroup b on a.rol_id=b.rol_id "
				+ "inner join Login c on b.log_id=c.log_id "
				+ "where log_name='"
				+ UserInfo.getUsername()
				+ "' and rol_name='系统管理员'";
		boolean flag = false;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}
}
