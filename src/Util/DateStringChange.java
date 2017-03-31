package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Messagebox;

public class DateStringChange {
	// Date类型转换String
	public static String DatetoSting(Date d, String format) {
		String Date;

		if (d == null) {
			Date = null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date = sdf.format(d);
		}

		return Date;
	}
	
 

	// 2个时间月份比较 0：相等，1：小于，2：sa于
	public static int comparedate(Date d1, Date d2) {
		if (d1==null)
		{
			return 0;
		}
//		else if (d2==null)
//		{
//			return -1;
//		}
		else
		{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		System.out.println(Integer.parseInt(sdf.format(d1).toString()));

		System.out.println(Integer.parseInt(sdf.format(d2).toString()));

		if (Integer.parseInt(sdf.format(d1).toString())
				- Integer.parseInt(sdf.format(d2).toString()) == 0) {
			return 0;
		} else if (Integer.parseInt(sdf.format(d1).toString())
				- Integer.parseInt(sdf.format(d2).toString()) < 0) {
			return 1;
		} else {
			return 2;
		}
		}

	}
	
	public static Date comparedatereturndate(Date d1, Date d2) {
		if (d1==null)
		{
			if (d2==null)
			{
				return null;
			}else
			{
			return d2;
			}
		} 
		else if (d2==null)
		{
			return d1;
		}
		else if (comparedate(d1,d2)==0 || comparedate(d1,d2)==1)
		{
			return d1;
		}
		else if (comparedate(d1,d2)==2)
		{
			return d2;
		}
		else
		{
			return null;
		}
			
			
	}

	
	// String类型转换Date
	public static Date StringtoDate(String d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date Date = null;
		try {
			if (d != null)
				Date = sdf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Date;
	}
	
	// String类型转换Date
	public static String Stringformat(String d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date Date = null;
		try {
			if (d != null)
				Date = sdf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(Date);
	}

	// YYYYMM转换YYYY-MM-DD
	public static Date ownmonthTodate(String d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = d.substring(0, 4) + "-" + d.substring(4, 6) + "-01";
		Date date = null;
		try {
			date = sdf.parse(d1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	// 获取当前时间
	public static String Datestringnow(String format) {
		Date now = new Date();
		SimpleDateFormat dateFormat = null;

		dateFormat = new SimpleDateFormat(format);// 可以方便地修改日期格式

		return dateFormat.format(now);

	}
	
	// 获取当前时间
		public static Date Datenow() {
			Date now = new Date();
		
			return now;

		}

	// String格式转换成ownmonth
	public static String Stringtoownmonth(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String Date = "";
		Date = String.valueOf(sdf.format(date));
		return Date;
	}

	// 获取所属月份
	public static String getOwnmonth() {
		String ownmonth = "";
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			ownmonth = sdf.format(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ownmonth;
	}

	// 所属月份加一个月
	public static String ownmonthAddoneMonth(String ownmonth) {
		String nextMonth = ownmonth;
		try {
			int year = Integer.parseInt(ownmonth.substring(0, 4));
			int month = Integer.parseInt(ownmonth.substring(4, 6));
			if (month == 12) {
				year++;
				month = 1;
			} else {
				month++;
			}

			if (month < 10)
				nextMonth = String.valueOf(year) + "0" + month;
			else
				nextMonth = String.valueOf(year) + month;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextMonth;
	}

	// 所属月份加减
	public static String ownmonthAdd(String ownmonth, Integer i) {
//		Date d = ownmonthTodate(ownmonth);
//		Calendar c = new GregorianCalendar();
//		c.setTime(d);
//		c.add(Calendar.MONTH, i + 1);
//		String month = String.valueOf(c.get(Calendar.YEAR))
//				+ (String.valueOf(c.get(Calendar.MONTH)).length() > 1 ? String
//						.valueOf(c.get(Calendar.MONTH)) : ("0" + String
//						.valueOf(c.get(Calendar.MONTH))));
	if(i>12 || i<-12)
	{
		Messagebox.show("不能计算超过12个月!", "ERROR", Messagebox.OK,
				Messagebox.ERROR);
		
		return null;
	}
		
		String nextMonth = ownmonth;
		try {
			int year = Integer.parseInt(ownmonth.substring(0, 4));
			int month = Integer.parseInt(ownmonth.substring(4, 6));
			int newmonth=month+i;
			if (newmonth>12)
			{
				year++;
				month=newmonth-12;
			}
			else if (newmonth<0)
			{
				year--;
				month=12+newmonth;
			} else if (newmonth==0)
			{
				year--;
				month=12;
			}
			else
			{
				month=newmonth;
			}

			if (month < 10)
				nextMonth = String.valueOf(year) + "0" + month;
			else
				nextMonth = String.valueOf(year) + month;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextMonth;
		
//		return month;
	}

	// 获取2个日期差
	@SuppressWarnings("unused")
	public static List<String> getMonthBetween(String minDate, String maxDate)
			throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf1.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	// 获取2个所属月份差
	public static int diffOwnmonth(int maxOwnmonth, int minOwnmonth) {
		int diff = 0;
		try {
			int i = (maxOwnmonth / 100 - minOwnmonth / 100) * 88;
			diff = maxOwnmonth - minOwnmonth - i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff;
	}
}
