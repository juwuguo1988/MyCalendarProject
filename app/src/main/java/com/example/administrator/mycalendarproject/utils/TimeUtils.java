package com.example.administrator.mycalendarproject.utils;

import android.text.TextUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 */
public class TimeUtils {

    public static final SimpleDateFormat DATE_SDF_YMDHMS = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    public static final SimpleDateFormat DATE_SDF_YMDHM = new SimpleDateFormat("yyyy:MM:dd HH:mm");
    public static final SimpleDateFormat DATE_SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_SDF = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat WEEK_SDF = new SimpleDateFormat("EEE");


    //获取今天的date
    public static String getYesdayDate() {
        String yesDate = "";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        yesDate = new SimpleDateFormat("yyyy:MM:dd ").format(cal.getTime());
        return yesDate;
    }

    //获取今天的date
    public static String getTodayDate() {
        String todayDate = "";
        SimpleDateFormat sdfToday = new SimpleDateFormat("yyyy:MM:dd ", Locale.getDefault());
        todayDate = sdfToday.format(new Date());
        return todayDate;
    }

    //获取明天的date
    public static String getTomDate() {
        String tomDate = "";
        Calendar calto = Calendar.getInstance();
        calto.add(Calendar.DATE, 1);
        tomDate = new SimpleDateFormat("yyyy:MM:dd ").format(calto.getTime());
        return tomDate;
    }


    //获取大前天的date
    public static String getThreeDaysDate() {
        String yesDate = "";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        yesDate = new SimpleDateFormat("yyyy:MM:dd ").format(cal.getTime());
        return yesDate;
    }

    //获取前天的date
    public static String getBeforeYesdayDate() {
        String yesDate = "";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        yesDate = new SimpleDateFormat("yyyy:MM:dd ").format(cal.getTime());
        return yesDate;
    }

    //long值转换为HH:mm
    public static String formatTimeFromLongToHHmm(long time) {
        String hm = "";
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        hm = formatter.format(time);
        return hm;
    }

    //long值转换为
    public static String formatTimeFromLongToYMD(long time) {
        String ymd = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd ");
        ymd = formatter.format(time);
        return ymd;
    }

    //yyyy:MM:dd HH:mm转换为long
    public static Long formatTimeFromYMDHHmmToLong(String time) {
        long millisecond = 0L;
        try {
            Date date = new SimpleDateFormat("yyyy:MM:dd HH:mm", Locale.getDefault()).parse(time);
            millisecond = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millisecond;
    }

    //long 值转换成 yyyy:MM:dd HH:mm
    public static String formatTimeFromLongToYMDHm(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(time);
    }

    //long值转换为月日
    public static String formatLongToMMDD(long time) {
        Date date = new Date(time);
        String data = (date.getMonth() + 1) + "月" + date.getDate() + "日";
        return data;
    }

    /**
     * 星期几
     *
     * @param time long 系统时间的long类型
     * @return 星期一到星期日
     */
    public static String formatLongToWeek(long time) {

        Date date = new Date(time);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取星期字符串（周一）
     */
    public static String getWeekString(Date date) {
        String weekString = "";
        try {
            weekString = WEEK_SDF.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weekString;
    }

    /**
     * 获取当前日期年份
     */
    public static int getCurrentYear() throws ParseException {
        String tempTime = DATE_SDF_YMDHMS.format(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(tempTime));
        return calendar.get(Calendar.YEAR);
    }


    /**
     * 获取日期月份
     */
    public static int getCurrentMonth() throws ParseException {
        String tempTime = DATE_SDF_YMDHMS.format(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(tempTime));
        return (calendar.get(Calendar.MONTH) + 1);
    }


    /**
     * 获取日期年份
     * date yyyy:MM:dd HH:mm:ss
     */
    public static int getYears(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(date));
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期月份
     * date yyyy:MM:dd HH:mm:ss
     */
    public static int getMonths(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(date));
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期年份
     * date yyyy:MM:dd HH:mm
     */
    public static int getYear(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHM.parse(date));
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期年份
     * date yyyy:MM:dd HH:mm
     */
    public static int getMonth(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHM.parse(date));
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期号
     */
    public static int getDay(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(date));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取小时数
     */
    public static int getHours(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(date));
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟数
     */
    public static int getMin(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(date));
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取秒数
     */
    public static int getSecond(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE_SDF_YMDHMS.parse(date));
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 判断是否为合法的日期时间字符串
     */
    public static boolean isDate(String date, String dateFormat) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            try {
                formatter.format(formatter.parse(date));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

}
