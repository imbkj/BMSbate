package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.LoginModel;

public class LoginInfoStatic {
	private static Map<String, LoginModel> loginMap = new HashMap<String, LoginModel>();
	private static List<String> loginLog = new ArrayList<String>();
	private static String loginMessage = "";
	private static Boolean setupdatestate = false;

	public static Map<String, LoginModel> getLoginMap() {
		// if (loginMap == null) {
		// loginMap = new HashMap<String, LoginModel>();
		// }
		return loginMap;
	}

	public static void setLoginMap(Map<String, LoginModel> loginMap) {
		LoginInfoStatic.loginMap = loginMap;
	}

	public static List<String> getLoginLog() {
		return loginLog;
	}

	public static String getLoginMessage() {
		return loginMessage;
	}

	public static void setLoginMessage(String loginMessage) {
		LoginInfoStatic.loginMessage = loginMessage;
	}

	public static void setLoginLog(List<String> loginLog) {
		LoginInfoStatic.loginLog = loginLog;
	}

	public static Boolean getSetupdatestate() {
		return setupdatestate;
	}

	public static void setSetupdatestate(Boolean setupdatestate) {
		LoginInfoStatic.setupdatestate = setupdatestate;
	}
	
	 

}
