package com.gas.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 时间规范类
 *
 * @author Heart
 * @date 2015年5月6日
 */
public class TimeFormat {
	private static int secondUnit = 1000;// 1秒
	private static int minuUnit = 60 * secondUnit; // 1分钟
	private static int hourUnit = 60 * minuUnit; // 1小时
	private static int dayUnit = 24 * hourUnit; // 1天

	/**
	 * 把一个long型时间转成String型时间
	 *
	 * @param time
	 *            时间
	 * @param level
	 *            参考Calendar
	 * @return "yyyy-M-d k:mm:ss" 格式的时间
	 */
	public static String convertTimeLong2String(long time, int level) {
		String format = "yyyy-M-d k:mm:ss";
		switch (level) {
			case Calendar.SECOND: {
				format = "yyyy-M-d k:mm:ss";
			}
			break;
			case Calendar.MINUTE: {
				format = "yyyy-M-d k:mm";
			}
			break;
			case Calendar.HOUR: {
				format = "yyyy-M-d k";
			}
			break;
			case Calendar.DATE: {
				format = "yyyy年MM月dd日";
			}
			break;
			case Calendar.MONTH: {
				format = "yyyy-M";
			}
			break;
			case Calendar.YEAR: {
				format = "yyyy";
			}
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}
}
