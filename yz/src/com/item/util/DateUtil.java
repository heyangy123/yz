package com.item.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final SimpleDateFormat format_ymd_by_ = new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat format_ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat format_ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat format_ymdhmsS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final SimpleDateFormat format_hms = new SimpleDateFormat("HH:mm:ss");
	
	
	
	public static void main(String[] args) {
		Date d = new Date();
		System.out.println(d.getTime());
		System.out.println(toDate(toString(d, format_ymdhms), format_ymdhms).getTime());
	}
	
	/**
	 * 获得指定日期的后一天
	 * 
	 * @param currentDay
	 * @return
	 */
	public static Date yesterday(Date date) {
		Calendar c = Calendar.getInstance();

		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		// String dayAfter = format.format(c.getTime());
		return c.getTime();
	}
	

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param currentDay
	 * @return
	 */
	public static Date nextDay(Date date) {
		Calendar c = Calendar.getInstance();

		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		// String dayAfter = format.format(c.getTime());
		return c.getTime();
	}

	/**
	 * 解析成Date
	 * 
	 * @param date
	 * @return
	 */
	public static Date toDate(String date, SimpleDateFormat format) {
		if (date==null || date.trim().equals("")) {
			return null;
		}
		Date d = null;
		try {
			d = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static Date toDate(String date) {
		return toDate(date, format_ymd);
	}
	
	public static Date toDateTime(String date) {
		return toDate(date, format_ymdhms);
	}

	public static String toString(Date date, SimpleDateFormat format) {
		if (date==null) {
			return null;
		}
		return format.format(date);
	}

	/**
	 * 默认：yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		return toString(date, format_ymd);
	}

	public static String toStringDataTime(Date date) {
		return toString(date, format_ymdhms);
	}
	
	public static boolean compareDate(Date date1, Date date2) {
		return toString(date1).equals(toString(date2));
	}

	public static Date lastDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	/**
	 * 获取指定时间对应的毫秒数
	 * 
	 * @param time
	 *            "HH:mm:ss"
	 * @return
	 */
	public static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " "
					+ time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 下一个月
	 * 
	 * @param tempDate
	 * @return
	 */
	public static Date nextMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}
	
	public static int getHour(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 是否在一段时间之内
	 * @param now 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isBetween(Date now, Date startTime, Date endTime) {
		if (now==null || startTime==null || endTime==null) {
			return false;
		}
		if (now.before(startTime) || now.after(endTime)) {
			return false;
		}else
			return true;
	}

	/**
	 * 加分钟
	 * @param date
	 * @param amount 数值
	 * @return
	 */
	public static Date addMinute(Date date, int amount) {
		if (date==null) {
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, amount);
		return cal.getTime();
	}


	/**
	 * 今日日期
	 * @return
	 */
	public static Date todayDate(SimpleDateFormat sdf) {
		Date today = new Date();
		return toDate(toString(today, sdf), sdf);
	}


	/**
	 * 下n个小时
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date nextHour(Date date, int n) {
		if (date==null) {
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, n);
		return cal.getTime();
	}
	/*
	 * 计算两个 -- -- 格式的日期之间的天数
	 * */
	 public static int daysBetween(String smdate,String bdate) throws ParseException{  
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(sdf.parse(smdate));    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(sdf.parse(bdate));    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));     
	    }  
}
