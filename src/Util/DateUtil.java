package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取月份第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 获取月份最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * @Title: getSetDay
	 * @Description: TODO(获取指定日期)
	 * @param date
	 *            日期
	 * @param type
	 *            y/m/d 年/月/日
	 * @param num
	 *            加减日期
	 * @param dateType
	 *            f/l 第一天/最后一天
	 * @return
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date getSetDay(Date date, String type, Integer num,
			String dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (type != null) {
			switch (type) {
			case "y":
				calendar.add(Calendar.YEAR, num);
				break;
			case "m":
				calendar.add(Calendar.MONTH, num);
				break;
			case "d":
				calendar.add(Calendar.DAY_OF_MONTH, num);
				break;

			default:
				break;
			}
		}

		if (dateType != null) {
			switch (dateType) {
			case "f":
				calendar.set(Calendar.DATE,
						calendar.getActualMinimum(Calendar.DATE));
				break;
			case "l":
				calendar.set(Calendar.DATE,
						calendar.getActualMaximum(Calendar.DATE));
			}
		}
		return calendar.getTime();
	}

	/**
	 * @Title: getSetDay
	 * @Description: TODO(获取指定日期)
	 * @param date
	 *            日期
	 * @param type
	 *            y/m/d 年/月/日
	 * @param num
	 *            加减日期
	 * @param dateType
	 *            f/l 第一天/最后一天
	 * @return
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date getSetDay(String d, String type, Integer num,
			String dateType) {
		Date date = DateStringChange.StringtoDate(d, "yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (type != null) {
			switch (type) {
			case "y":
				calendar.add(Calendar.YEAR, num);
				break;
			case "m":
				calendar.add(Calendar.MONTH, num);
				break;
			case "d":
				calendar.add(Calendar.DAY_OF_MONTH, num);
				break;

			default:
				break;
			}
		}

		if (dateType != null) {
			switch (dateType) {
			case "f":
				calendar.set(Calendar.DATE,
						calendar.getActualMinimum(Calendar.DATE));
				break;
			case "l":
				calendar.set(Calendar.DATE,
						calendar.getActualMaximum(Calendar.DATE));
			}
		}

		return calendar.getTime();
	}

	/**
	 * @Title: dateAdd
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param date
	 *            yyyyMM
	 * @param t
	 * @param i
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String dateAdd(Date date, String t, Integer i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(getDateAdd(date, t, i));
	}

	/**
	 * @Title: datediff
	 * @Description: TODO(所属月份yyyyMM计算日期差)
	 * @param d1
	 * @param d2
	 * @param t
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public static Integer datediff(String s1, String s2, String t) {

		SimpleDateFormat sdf = null;
		if (s1.length() == 6) {
			sdf = new SimpleDateFormat("yyyyMM");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}

		Integer i = 0;
		try {
			Date d1 = sdf.parse(s1);
			Date d2 = sdf.parse(s2);
			i = datediff(d1, d2, t);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public static Integer datediff(Date d1, Date d2, String t) {

		Integer i = 0;
		switch (t) {
		case "d":
			Calendar c = Calendar.getInstance();

			c.setTime(d1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			int day_1 = c.get(Calendar.DAY_OF_YEAR);
			int day1=Integer.valueOf(String.valueOf((c.getTimeInMillis()/1000/60/60/24)));
			
			c.setTime(d2);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			
			int day_2 = c.get(Calendar.DAY_OF_YEAR);
			
		
			int day2=Integer.valueOf(String.valueOf((c.getTimeInMillis()/1000/60/60/24)));
		
			i = day2-day1;
			if (i==0) {
				i=day_2-day_1;
			}
			break;
		case "M":
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(d1);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			c2.setTime(d2);
			c2.set(Calendar.HOUR_OF_DAY, 0);
			c2.set(Calendar.MINUTE, 0);
			c2.set(Calendar.SECOND, 0);
			i = (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12
					+ c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
			break;
		default:
			break;
		}
		return i;
	}

	public static Date getDateAdd(Date date, String t, Integer i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (t.equals("M")) {
			calendar.add(Calendar.MONTH, i);
		} else if (t.equals("y")) {
			calendar.add(Calendar.YEAR, i);
		} else if (t.equals("d")) {
			calendar.add(Calendar.DATE, i);
		}
		return calendar.getTime();
	}

	// 时间格式转换
	public static java.sql.Date timechange(Date d) {
		java.sql.Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

	/**
	 * @Title: dayOfWeek
	 * @Description: TODO(判断当前日期是星期几)
	 * @param date
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public static int dayOfWeek(Date date) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(date);
		int weekDay = aCalendar.get(Calendar.DAY_OF_WEEK);
		return weekDay;
	}

	/**
	 * @Title: getNextDate
	 * @Description: TODO(获取指定日期指定天数后的日期)
	 * @param date
	 * @param index
	 * @param flag
	 * @return
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date getNextDate(Date date, int index, boolean flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);// 获得当前时间
		if (flag) {
			// 日期不变，把时间设定为00：00：00
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 00);
			cal.set(Calendar.SECOND, 00);
		}
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + index);
		return cal.getTime();
	}

	/**
	 * @Title: getDate
	 * @Description: TODO(获取指定日期指定天数后的日期,中间日期跳过星期六日)
	 * @param currentDate
	 * @param days
	 * @return
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date getDate(Date currentDate, int days) {
		/*
		 * 1，根据传入日期获取下一天日期 2，判断下一天日期是否为工作日，如果是则设置下一次循环日期为此日期
		 * 如果不为工作日，为周6，日期前进2天，为周天前进1天 3, 获取指定天数后的工作日
		 */
		Date date = currentDate;
		/*
		 * 设置循环次数 如果含最后一天则循环 days + 1 天，不需要含最后一天，则循环 days次
		 */
		for (int i = 0; i < days; i++) {

			Date nextDate = getNextDate(date, 1, true); // 获取下一天的日期
			int weekDay = dayOfWeek(nextDate); // 下一天日期为星期几
			//System.out.print(nextDate+","+weekDay);
			
			
			if (weekDay == 7 && i<(days-1)) { // 为星期六
				date = getNextDate(date, 3, true);
			} else if (weekDay == 1 && i<(days-1)) { // 为星期天
				date = getNextDate(date, 2, true);
			} else {
				date = nextDate;
			}
			//System.out.println(","+date);
		}
		return date;
	}
}
