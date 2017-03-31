/**
 * @Classname MapStringChange
 * @ClassInfo  Map与String 互转
 * @author 李文洁
 * @Date 2014-6-19
 * @Remark 仅支持Map<String, String>类型
 */
package Util;

import java.util.HashMap;
import java.util.Map;

public class MapStringChange {

	// Map转String
	public static String MapToString(Map<String, String> map) {
		String str = "";
		try {
			if (map != null) {
				str = map.toString().replace("{", "").replace("}", "")
						.replace(" ", "");
			}
		} catch (Exception e) {
			str = "";
			e.printStackTrace();
		}
		return str;
	}

	// String转Map
	public static Map<String, String> StringToMap(String str) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (str != null && !"".equals(str.trim())) {
				String[] array = str.split(",");
				for (String s : array) {
					map.put(s.split("=")[0], s.split("=")[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
