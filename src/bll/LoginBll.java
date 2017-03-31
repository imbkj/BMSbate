/**
 * @Classname LoginBll
 * @ClassInfo 登录页面业务处理类
 * @author 李文洁
 * @Date 2013-9-18
 */
package bll;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Session;

import Model.DepartmentModel;
import Model.LoginModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.LoginInfoStatic;
import Util.MD5;
import dal.LoginDal;

public class LoginBll {
	public String[] checkLogin(String name, String pwd, Session session) {
		String[] message = new String[2];
		message = existUser(name, pwd, session);
		return message;
	}

	// 查询数据库，检测用户信息；
	private String[] existUser(String name, String pwd, Session session) {
		String[] message = new String[2];
		LoginDal dal = new LoginDal();
		try {
			ResultSet rs = dal.getLoginInfo(name, changeMD5(pwd));
			rs.next();
			if (rs.getRow() == 0) {
				message[0] = "0";
				message[1] = "错误：您的密码不正确！";
			} else {
				int inure = rs.getInt("log_inure");
				if (inure == 0) {
					message[0] = "0";
					message[1] = "系统安全提示：您的帐号未通过审核，请联系信息组开通用户!！";
				} else if (inure == 1) {
					String userid = rs.getString("log_id");
					String username = rs.getString("log_name");
					String depid = rs.getString("dep_id");
					String rolname = rs.getString("rol_name");
					if(!"3".equals(depid)){
						String loginMes = LoginInfoStatic.getLoginMessage();
						if(!"".equals(loginMes) && loginMes!=null){
							message[0] = "2";
							message[1] = loginMes;
							return message;
						}
					}
					if (session != null)
					// 保存session
					{
						saveSession(userid, username, depid, rolname, session);
						// 记录登录信息
						savaLoginInfo(session, userid, username);
					}
					message[0] = "1";
					message[1] = "登录成功；";
				}
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "登录出错；";
			e.printStackTrace();
		}
		return message;
	}

	// 保存session
	private void saveSession(String userid, String username, String depid,
			String rolname, Session session) {
		session.setAttribute("userid", userid);
		session.setAttribute("username", username);
		session.setAttribute("depid", depid);
		session.setAttribute("rolname", rolname);

	}

	// 记录登录信息
	private void savaLoginInfo(Session session, String log_id, String username) {
		try {
			LoginModel m;
			Map<String, LoginModel> map = LoginInfoStatic.getLoginMap();
			List<String> loginLog = LoginInfoStatic.getLoginLog();
			javax.servlet.http.HttpSession httpSession = (javax.servlet.http.HttpSession) (session
					.getNativeSession());
			String sessionID = httpSession.getId();
			if (map.containsKey(sessionID)) {
				m = map.get(sessionID);
				if (!username.equals(m.getLog_name())) {
					loginLog.add(DateStringChange
							.Datestringnow("yyyy-MM-dd HH:mm")
							+ ": "
							+ m.getLog_name() + "，切换用户->" + username + "; ");
				}
				m.setLog_id(Integer.parseInt(log_id));
				m.setLog_name(username);
				m.setLog_intime(DateStringChange.Datestringnow("MM-dd HH:mm"));
			} else {
				m = new LoginModel();
				m.setLog_id(Integer.parseInt(log_id));
				m.setLog_name(username);
				m.setLog_intime(DateStringChange.Datestringnow("MM-dd HH:mm"));
				map.put(sessionID, m);
			}
			// 添加登录记录
			loginLog.add(DateStringChange.Datestringnow("yyyy-MM-dd HH:mm")
					+ ": " + username + "，登录系统; ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 转换MD5
	private String changeMD5(String pwd) {
		String md5 = MD5.GetMD5Code(pwd);
		return md5;
	}

	public Integer getUserIDByname(String name) {
		LoginDal dal=new LoginDal();
		return dal.getUserIDByname(name);
	}
	 
	 
	// 查询体检项目查看金额权限名单
	public List<LoginModel> loginList(String str) {
		LoginDal dal=new LoginDal();
		return dal.loginList(str);
	}
	
	public List<DepartmentModel> getDepartment()
	{
		LoginDal dal=new LoginDal();
		return dal.getDepartment();
	}
	
	public List<loginroleModel> userInfo(loginroleModel lmodel, String columns,
			boolean dis, String order) 
			{
				LoginDal dal=new LoginDal();
				return dal.userInfo(lmodel, columns, dis, order);
			}
}
