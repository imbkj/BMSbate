package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	/**
	 * 正则表达,字符串匹配
	 * 
	 * @param pstr
	 *            条件字符串
	 * @param mstr
	 *            匹配字符串
	 * @return is true:匹配成功 false 匹配失败
	 */
	public static boolean isExists(String pstr, String mstr) {
		pstr = pstr == null ? "" : pstr;
		mstr = mstr == null ? "" : mstr;
		Pattern p = Pattern.compile(pstr);
		Matcher m = p.matcher(mstr);
		boolean is = m.find();
		return is;
	}
}
