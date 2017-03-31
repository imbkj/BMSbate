/**
 * @Classname pinyin4jUtil
 * @ClassInfo 中文转拼音操作工具类
 * @author 李文洁
 * @Date 2013-9-16
 */
package Util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

public class pinyin4jUtil {
	// 获取字符串的中文的首字母,英文按原样输出(如：输入"深圳s",返回"szs")
	public static String getPinYinHeadChar(String str) {
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		String convert = "";
		for (int i = 0; i < str.length(); i++) {
			char word = str.charAt(i);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word,
					outputFormat);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	// 获取字符串的首字母拼音简称(如：输入"深圳",返回"s深圳")
	public static String getHeadCharPinYin(String str) {
		String convert = "";
		try {
			HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
			char word = str.charAt(0);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word,
					outputFormat);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convert + str;
	}

}
