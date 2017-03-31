package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckString {
	private static Pattern chinese = Pattern.compile("[\u4e00-\u9fa5]"); 
	private static Pattern num = Pattern.compile("[0-9]*"); 
	private static Pattern letter = Pattern.compile("[a-zA-Z]+"); 
	
	//判断字符串是否包含中文
	public static boolean isChinese(String str) {	
		Matcher isChinese = chinese.matcher(str);
		if (isChinese.find()) {
			return true;
		}
		return false;
	}
	//判断字符串是否为纯数字
	public static boolean isNum(String str) {
		Matcher isNum = num.matcher(str);
		   if( isNum.matches() ){
		       return true; 
		   } 
		   return false;
	}

	//判断字符串是否为纯字母
	public static boolean isLetter(String str) {
		Matcher isLetter = letter.matcher(str); 
		if (isLetter.matches()) {
			   return true;
			  }
		return false;
	}
}
