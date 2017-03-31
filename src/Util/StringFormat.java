package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormat {

	/** 
	* @Title: replaceSpace 
	* @Description: TODO(替换空格/换行/制表符) 
	* @param t1
	* @return
	* @return String    返回类型 
	* @throws 
	*/
	public static String replaceSpace(String t1) {
		if (t1 != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(t1);
			t1 = m.replaceAll("");
		}
		return t1;

	}
}
