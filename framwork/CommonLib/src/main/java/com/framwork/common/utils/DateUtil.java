package com.framwork.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描 述: 日期格式化类
 * 作 者: ZhongXiongHui
 * 创 建：2017/8/3
 **/
public class DateUtil {
    
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT1 = "yyyy.MM.dd";
    @SuppressWarnings("rawtypes")
    private static ThreadLocal threadLocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };
    
    public static SimpleDateFormat getDateFormat1() {
        return (SimpleDateFormat) threadLocal1.get();
    }
    
    private static ThreadLocal threadLocal1 = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return new SimpleDateFormat(DATE_FORMAT1);
        }
    };
    
    public static SimpleDateFormat getDateFormat() {
        return (SimpleDateFormat) threadLocal.get();
    }
    
    public static Date parse(String textDate) throws ParseException {
        return getDateFormat().parse(textDate);
    }
    
    public static String parse(long time) {
        Date d = new Date(time);
        return getDateFormat().format(d);
    }
    
    public static String getHourAndMinute(String time) {
        try {
            Date d = getDateFormat().parse(time);
            StringBuilder sb = new StringBuilder();
            if(d.getHours() < 10) {
                sb.append("0");
            }
            sb.append(d.getHours() + ":");
            if(d.getMinutes() < 10) {
                sb.append("0");
            }
            sb.append(d.getMinutes() + "");
            return sb.toString();
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return "00:00";
    }
    
    public static String getTimeFormat(long time) {
        long times = time * 1000L;
        Date d = new Date(times);
        return getDateFormat1().format(d);
    }
    
    public static String getNextDay(long time, int nextDay) {
        long times = time * 1000L;
        Date d = new Date(times);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_MONTH, nextDay);
        return getDateFormat1().format(c.getTime());
    }
    
    public static String getNextMonth(long time, int nextMonth) {
        long times = time * 1000L;
        Date d = new Date(times);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, nextMonth);
        return getDateFormat1().format(c.getTime());
    }
    
    public static String getNextMonthNextDay(long time, int nextMonth, int nextDay) {
        long times = time * 1000L;
        Date d = new Date(times);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, nextMonth);
        c.add(Calendar.DAY_OF_MONTH, nextDay);
        return getDateFormat1().format(c.getTime());
    }
    
    public static String getLateTime(String time) {
        try {
            Date d = getDateFormat().parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            return getTwoBit(c, Calendar.MONTH) + "-" + getTwoBit(c, Calendar.DAY_OF_MONTH) + " " + getTwoBit(c, Calendar.HOUR_OF_DAY) + ":" + getTwoBit(c, Calendar.MINUTE);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return "00-00 00:00";
    }
    
    //获取年月日两位
    public static String getTwoBit(Calendar c, int type) {
        if(c != null) {
            int time = c.get(type);
            if(type == Calendar.MONTH) {
                time++;
            }
            if(time >= 10) {
                String ys = String.valueOf(time);
                return ys.substring(ys.length() - 2, ys.length());
            }
            else {
                return "0" + time;
            }
        }
        return "";
    }
}
