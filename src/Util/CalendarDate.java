package Util;

import java.util.Calendar;
import java.util.Date;

public class CalendarDate {

	private Calendar cDate = Calendar.getInstance();

	private Date date;

	/**
	 * 
	 * 根据起始日期和间隔时间计算结束日期
	 * 
	 * 
	 * 
	 * @param sDate开始时间
	 * 
	 * 
	 * 
	 * @param days间隔时间
	 * 
	 * 
	 * 
	 * @return 结束时间
	 * 
	 * */

	public static Date calculateEndDate(Date sDate, int days)

	{

		// 将开始时间赋给日历实例

		Calendar sCalendar = Calendar.getInstance();

		sCalendar.setTime(sDate);

		// 计算结束时间

		sCalendar.add(Calendar.DATE, days);

		// 返回Date类型结束时间

		return sCalendar.getTime();

	}

	/**
	 * 
	 * 计算两个日期的时间间隔
	 * 
	 * 
	 * 
	 * @param sDate开始时间
	 * 
	 * 
	 * 
	 * @param eDate结束时间
	 * 
	 * 
	 * 
	 * @param type间隔类型
	 *            ("Y/y"--年 "M/m"--月 "D/d"--日)
	 * 
	 * 
	 * 
	 * @return interval时间间隔
	 * 
	 * */

	public static int calInterval(Date sDate, Date eDate, String type)

	{

		// 时间间隔，初始为0

		int interval = 0;

		/* 比较两个日期的大小，如果开始日期更大，则交换两个日期 */

		// 标志两个日期是否交换过

		boolean reversed = false;

		if (compareDate(sDate, eDate) > 0)

		{

			Date dTest = sDate;

			sDate = eDate;

			eDate = dTest;

			// 修改交换标志

			reversed = true;

		}

		/* 将两个日期赋给日历实例，并获取年、月、日相关字段值 */

		Calendar sCalendar = Calendar.getInstance();

		sCalendar.setTime(sDate);

		int sYears = sCalendar.get(Calendar.YEAR);

		int sMonths = sCalendar.get(Calendar.MONTH);

		int sDays = sCalendar.get(Calendar.DAY_OF_YEAR);

		Calendar eCalendar = Calendar.getInstance();

		eCalendar.setTime(eDate);

		int eYears = eCalendar.get(Calendar.YEAR);

		int eMonths = eCalendar.get(Calendar.MONTH);

		int eDays = eCalendar.get(Calendar.DAY_OF_YEAR);

		// 年

		if (cTrim(type).equals("Y") || cTrim(type).equals("y"))

		{

			interval = eYears - sYears;

			if (eMonths < sMonths)

			{

				--interval;

			}

		}

		// 月

		else if (cTrim(type).equals("M") || cTrim(type).equals("m"))

		{

			interval = 12 * (eYears - sYears);

			interval += (eMonths - sMonths);

		}

		// 日

		else if (cTrim(type).equals("D") || cTrim(type).equals("d"))

		{

			interval = 365 * (eYears - sYears);

			interval += (eDays - sDays);

			// 除去闰年天数

			while (sYears < eYears)

			{

				if (isLeapYear(sYears))

				{

					--interval;

				}

				++sYears;

			}

		}

		// 如果开始日期更大，则返回负值

		if (reversed)

		{

			interval = -interval;

		}

		// 返回计算结果

		return interval;

	}

	private static boolean isLeapYear(int year)

	{

		return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));

	}

	public static String cTrim(String tStr)

	{

		String ttStr = "";

		if (tStr == null)

		{
		}

		else

		{

			ttStr = tStr.trim();

		}

		return ttStr;

	}

	private static int compareDate(Date sDate, Date eDate)

	{

		int result = 0;

		// 将开始时间赋给日历实例

		Calendar sC = Calendar.getInstance();

		sC.setTime(sDate);

		// 将结束时间赋给日历实例

		Calendar eC = Calendar.getInstance();

		eC.setTime(eDate);

		// 比较

		result = sC.compareTo(eC);

		// 返回结果

		return result;

	}

}
