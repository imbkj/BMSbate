package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormatUtil {

	private static final String SEP1 = "#";
	private static final String SEP2 = "|";
	private static final String SEP3 = "=";

	/**
	 * String转换Map
	 * 
	 * @param mapText
	 *            :需要转换的字符串
	 * @param KeySeparator
	 *            :字符串中的分隔符每一个key与value中的分割
	 * @param ElementSeparator
	 *            :字符串中每个元素的分割
	 * @return Map<?,?>
	 */
	public static Map<String, Object> StringToMap(String mapText) {

		if (mapText == null || mapText.equals("")) {
			return null;
		}
		mapText = mapText.substring(1,mapText.length()-1).replace(" ", "");

		Map<String, Object> map = new HashMap<String, Object>();
		String[] text = mapText.split(","); // 转换为数组
		for (String str : text) {
			String[] keyText = str.split(SEP3); // 转换key与value的数组
			if (keyText.length < 1) {
				continue;
			}
			String key = keyText[0]; // key
			String value = keyText[1].trim(); // value
			if (value.charAt(0) == 'M') {
				Map<?, ?> map1 = StringToMap(value);
				map.put(key, map1);
			} else if (value.charAt(0) == 'L') {
				List<?> list = StringToList(value);
				map.put(key, list);
			} else {
				map.put(key, value);
			}
		}
		return map;
	}

	/**
	 * String转换List
	 * 
	 * @param listText
	 *            :需要转换的文本
	 * @return List<?>
	 */
	public static List<Object> StringToList(String listText) {
		if (listText == null || listText.equals("")) {
			return null;
		}
		listText = listText.substring(1);
		
		List<Object> list = new ArrayList<Object>();
		String[] text = listText.split(SEP1);
		for (String str : text) {
			if (str.charAt(0) == 'M') {
				Map<?, ?> map = StringToMap(str);
				list.add(map);
			} else if (str.charAt(0) == 'L') {
				List<?> lists = StringToList(str);
				list.add(lists);
			} else {
				list.add(str);
			}
		}
		return list;
	}

}
