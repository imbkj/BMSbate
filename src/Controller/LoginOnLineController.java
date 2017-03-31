package Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.LoginModel;
import Util.LoginInfoStatic;

public class LoginOnLineController {
	private Map<String, LoginModel> loginMap;
	private List<LoginModel> logList;
	private List<String> loginLog;
	
	public LoginOnLineController() {
		loginMap = LoginInfoStatic.getLoginMap();
		// Map转List
		mapTolist();
		//读取登录记录并反向排序
		loginLog = LoginInfoStatic.getLoginLog();
		Collections.sort(loginLog);
		Collections.reverse(loginLog);
	}

	// Map转List
	private void mapTolist() {
		Map<Integer, String> userMap = new HashMap<Integer, String>();
		logList = new ArrayList<LoginModel>();
		Collection<LoginModel> collection = loginMap.values();
		for (LoginModel m : collection) {
			if(!userMap.containsKey(m.getLog_id())){
				logList.add(m);
				userMap.put(m.getLog_id(), "");
			}
		}
	}

	public List<LoginModel> getLogList() {
		return logList;
	}

	public List<String> getLoginLog() {
		return loginLog;
	}

}
