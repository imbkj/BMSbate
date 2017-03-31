/**
 * @Classname UserInfo
 * @ClassInfo  获取登录用户的信息
 * @author 李文洁
 * @Date 2013-11-4
 */
package Util;

import org.zkoss.zk.ui.Executions;

public class UserInfo {
	// 获取用户名
	public static String getUsername() {
		String username = "";
		try {
			username = Executions.getCurrent().getDesktop().getSession().getAttribute("username").toString();
		} catch (Exception e) {
			//username = "陈耀家";
		}
		return username;
	}

	// 获取用户id
	public static String getUserid() {
		String userid = "";
		try {
			userid = Executions.getCurrent().getDesktop().getSession().getAttribute("userid").toString();
		} catch (Exception e) {
			userid = "248";
		}
		return userid;
	}

	// 获取部门ID
	public static String getDepID() {
		String depid = "";
		try {
			depid = Executions.getCurrent().getDesktop().getSession().getAttribute("depid").toString();
		} catch (Exception e) {
			depid = "0";
		}
		return depid;
	}

	// 获取角色名
	public static String getRolName() {
		String rolname = "";
		try {
			rolname = Executions.getCurrent().getDesktop().getSession().getAttribute("rolname").toString();
		} catch (Exception e) {
			rolname = "err";
		}
		return rolname;
	}
}
