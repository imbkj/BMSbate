/*
 * 创建人：林少斌
 * 创建时间：2013-10-11
 * 用途：获取月份集方法
 */
package Util;


public class MonthListUtil {
	// 获取月份集(是否显示传入月份：Boolean，传入月份：String类型，向未来获取或向历史获取：f/h，返回多少月份：int类型)
	public static String[] getMonthList(boolean ifDisplay, String month,
			String ifFH, int c) {
		String[] monthList;
		monthList = new String[c];
		MonthListUtil mlu = new MonthListUtil();

		// 第一个月份需要判断是否显示传入月份
		if (ifDisplay == true) {
			monthList[0] = month;
		} else {
			// 判断向未来还是向历史获取
			if (ifFH.equals("f")) {
				monthList[0] = mlu.getNextMonth(month);
			} else {
				monthList[0] = mlu.getLastMonth(month);
			}
		}

		// 从第二个月开始循环
		for (int i = 1; i < monthList.length; i++) {

			// 判断向未来还是向历史获取
			if (ifFH.equals("f")) {
				monthList[i] = mlu.getNextMonth(monthList[i - 1]);
			} else {
				monthList[i] = mlu.getLastMonth(monthList[i - 1]);
			}
		}

		// 返回结果
		return monthList;
	}

	// 获取月份集(是否显示传入月份：Boolean，传入月份：String类型，向未来获取多少月份：int类型，向历史获取多少月份：int类型)
	public static String[] getMonthList(boolean ifDisplay, String month,
			int fm, int hm) {

		int am; // 共返回多少个月份
		String[] monthList; // 所属月份
		String[] f_monthList; // 未来所属月份
		String[] h_monthList; // 历史所属月份
		f_monthList = new String[fm];
		h_monthList = new String[hm];
		//MonthListUtil mlu = new MonthListUtil();
		am = fm + hm;

		f_monthList = MonthListUtil.getMonthList(false, month, "f", fm);
		h_monthList = MonthListUtil.getMonthList(ifDisplay, month, "h", hm);

		monthList = new String[am];
		int j = 0;
		// 拼接未来所属月份
		for (int i = 1; i <= f_monthList.length; i++) {
			monthList[j] = f_monthList[f_monthList.length-i];
			j = j + 1;
		}

		// 拼接历史所属月份
		for (int i = 0; i < h_monthList.length; i++) {
			monthList[j] = h_monthList[i];
			j = j + 1;
		}

		// 返回结果
		return monthList;
	}

	// 获取传入月份的上个月
	public String getLastMonth(String month) {
		String lastMonth = "";
		int y;
		int m;
		int lm;

		// 获取传入月份
		lm = Integer.parseInt(month.substring(4, 6));

		// 判断是否年份是否-1
		if (lm == 1) {
			y = Integer.parseInt(month.substring(0, 4)) - 1;
			m = 12;
		} else {
			y = Integer.parseInt(month.substring(0, 4));
			m = lm - 1;
		}

		lastMonth = String.valueOf(y)
				+ ("00" + String.valueOf(m)).substring(
						("00" + String.valueOf(m)).length() - 2,
						("00" + String.valueOf(m)).length());

		return lastMonth;
	}

	// 获取传入月份的下个月
	public String getNextMonth(String month) {
		String nextMonth = "";
		int y;
		int m;
		int nm;

		// 获取传入月份
		nm = Integer.parseInt(month.substring(4, 6));

		// 判断是否年份是否+1
		if (nm == 12) {
			y = Integer.parseInt(month.substring(0, 4)) + 1;
			m = 1;
		} else {
			y = Integer.parseInt(month.substring(0, 4));
			m = nm + 1;
		}

		nextMonth = String.valueOf(y)
				+ ("00" + String.valueOf(m)).substring(
						("00" + String.valueOf(m)).length() - 2,
						("00" + String.valueOf(m)).length());
		return nextMonth;
	}
}
