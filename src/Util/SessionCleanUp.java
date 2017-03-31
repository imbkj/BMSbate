package Util;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.SessionCleanup;

import Model.LoginModel;

public class SessionCleanUp implements SessionCleanup {

	@Override
	public void cleanup(Session arg0) throws Exception {
		if (arg0.getAttribute("username") != null) {
			try {
				Map<String, LoginModel> map = LoginInfoStatic.getLoginMap();
				javax.servlet.http.HttpSession httpSession = (javax.servlet.http.HttpSession) (arg0
						.getNativeSession());
				String sessionID = httpSession.getId();
				map.remove(sessionID);
				// 添加登出记录
				List<String> loginLog = LoginInfoStatic.getLoginLog();
				loginLog.add(DateStringChange.Datestringnow("yyyy-MM-dd HH:mm")
						+ ": " + arg0.getAttribute("username") + "，退出系统; ");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
