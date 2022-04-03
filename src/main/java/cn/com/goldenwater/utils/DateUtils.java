package cn.com.goldenwater.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @version 创建时间：2012-8-21
 * 时间处理
 */

public class DateUtils {

    private DateUtils() {

    }

    public static Calendar set86Hour(Calendar input) {
    	Calendar ret = (Calendar)input.clone();
    	ret.set(Calendar.HOUR_OF_DAY, 8);//洪水预报用，
    	ret.set(Calendar.MINUTE, 0);
    	ret.set(Calendar.MILLISECOND, 0);
    	return ret;
    }


    public static TimeZone zone = TimeZone.getTimeZone("GMT+8");

    /**
     * change the number to Calendar
     *
     * @param numDate YYYYMMDD
     * @return Calendar
     */
    public static Calendar toCalendar(int numDate) {

        int n = numDate;
        int day = n % 100;
        n /= 100;
        int month = n % 100;
        n /= 100;
        int year = n;

        Calendar calendar = new GregorianCalendar(year, month - 1, day);
        return calendar;
    }

    /**
     * add specified months to the specified date
     *
     * @param numDate (YYYYMMDD) of date
     * @param month   number of months to be added
     * @return added date(a number) or -1 whenever error
     */
    public static int addMonth(int numDate, int month) {

        try {
            // change number to Calendar
            Calendar calendar = toCalendar(numDate);
            // add specified months
            calendar.add(Calendar.MONTH, month);

            // to check if it is a valid date
            int returnY = calendar.get(Calendar.YEAR);
            int returnM = calendar.get(Calendar.MONTH) + 1;
            int returnD = calendar.get(Calendar.DATE);
            return (returnY * 10000 + returnM * 100 + returnD);

        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    /**
     * add specified days to the specified date
     *
     * @param numDate (YYYYMMDD) of date
     * @param day     number of days to be added
     * @return added date(a number) or -1 whenever error
     */
    public static int addDay(int numDate, int day) {

        try {
            // change number to Calendar
            Calendar calendar = toCalendar(numDate);
            // add specified days
            calendar.add(Calendar.DATE, day);

            int returnY = calendar.get(Calendar.YEAR);
            int returnM = calendar.get(Calendar.MONTH) + 1;
            int returnD = calendar.get(Calendar.DATE);
            return (returnY * 10000 + returnM * 100 + returnD);

        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    /**
     * subtract of two dates
     *
     * @param minDate (YYYYMMDD) of smaller date
     * @param maxDate (YYYYMMDD) of bigger date
     * @return days between the two dates(int)<BR>
     * or -1 whenever error occurs
     */
    public static int subDate(int minDate, int maxDate) {

        // smaller date > bigger date => error
        if (minDate > maxDate) {
            return -1;
        }

        // to check if the input date is a valid date
        if (!isValidDate(minDate)) {
            return -1;
        }
        if (!isValidDate(maxDate)) {
            return -1;
        }

        try {
            // change number to Calendar
            Calendar calcMin = toCalendar(minDate);
            Calendar calcMax = toCalendar(maxDate);
            long lngMinMilSec;
            long lngMaxMilSec;

            // miliseconds
            lngMinMilSec = (calcMin.getTime()).getTime();
            lngMaxMilSec = (calcMax.getTime()).getTime();
            // substract by miliseconds
            return (int) ((lngMaxMilSec - lngMinMilSec) / (1000 * 60 * 60 * 24));

        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    /**
     * to deside if the input number is a valid date
     *
     * @param numDate (YYYYMMDD)
     * @return true(if valid), false(if invalid)
     */
    public static boolean isValidDate(int numDate) {

        String strDate = Integer.toString(numDate);
        if (strDate.length() != 8) {
            return false;
        }

        int fullYear = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(4, 6));
        int day = Integer.parseInt(strDate.substring(6, 8));

        if (isValid(fullYear, month, day) == false) {
            return false;
        }
        return true;
    }

    /**
     * to deside if the inputs(seperated year,month,day) is a valid date
     *
     * @param intYear  year
     * @param intMonth month (1~12)
     * @param intDay   day
     * @return true(if valid), false(if invalid)
     */
    public static boolean isValid(int intYear, int intMonth, int intDay) {

        Calendar calendar = new GregorianCalendar(intYear, intMonth - 1, intDay);
        if ((calendar.get(Calendar.YEAR) != intYear) ||
                (calendar.get(Calendar.MONTH) != (intMonth - 1)) ||
                (calendar.get(Calendar.DAY_OF_MONTH) != intDay)) {
            return false;
        }
        return true;
    }

    /* ************************** 转换函数 ****************************** */

    /**
     * switch a String to a Calendar
     *
     * @param input   the String to be switched
     * @param pattern the specified pattern
     * @return output the Calendar has been switched
     */
    public static Calendar Str2Calendar(String input, String pattern) {
        Calendar output = null;
        if (input != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setTimeZone(DateUtils.zone);
                Date date = sdf.parse(input);
                output = Date2Calendar(date);
            } catch (ParseException e) {
                System.err.println(e);
                return null;
            }
            return output;
        } else {
            return null;
        }
    }

    /**
     * 转换(yyyy-mm-dd) to Calendar
     *
     * @param input a String with format of yyyy-mm-dd to be switched to Date
     * @return cal  the Calendar has been switched
     */
    public static Calendar Str2CalendarYMD(String input) {

        if (input == null || "".equals(input.trim())) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Integer.parseInt(input.substring(0, 4)), Integer.parseInt(input
                .substring(5, 7)) - 1, Integer.parseInt(input.substring(8, 10)));
        return cal;
    }

    /**
     * 转换字符串格式(yyyy-mm-dd hh) to Calendar
     * 如：
     * 2007-05-01 08:00
     * 2007-06-01 08:00
     *
     * @param input a String with format of yyyy-mm-dd hh to be switched to Date
     * @return cal  the Calendar has been switched
     */
    public static Calendar Str2CalendarYMDH(String input) {

        if (input == null || input.trim().equals("")) {
            return null;
        }

        return Str2Calendar(input, "yyyy-MM-dd HH");

    }

    /**
     * 转换(yyyy-mm-dd hh:mm) to Calendar
     *
     * @param input a String with format of yyyy-mm-dd hh:mm to be switched to Date
     * @return cal  the Calendar has been switched
     */
    public static Calendar Str2CalendarYMDHM(String input) {

        if (input == null || input.trim().equals("")) {
            return null;
        }
        return Str2Calendar(input, "yyyy-MM-dd HH:mm");

    }

    /**
     * 转换(yyyy-mm-dd hh:mm:ss) to Calendar
     *
     * @param input a String with format of yyyy-mm-dd hh:mm:ss to be switched to Date
     * @return cal  the Calendar has been switched
     */
    public static Calendar Str2CalendarYMDHMS(String input) {

        if (input == null || input.trim().equals("")) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Integer.parseInt(input.substring(0, 4)),
                Integer.parseInt(input.substring(5, 7)) - 1,
                Integer.parseInt(input.substring(8, 10)),
                Integer.parseInt(input.substring(11, 13)),
                Integer.parseInt(input.substring(14, 16)),
                Integer.parseInt(input.substring(17, 19)));
        return cal;
    }

    /**
     * 转换(hh:mm) to Calendar
     *
     * @param input a String with format of hh:mm to be switched to Date
     * @return cal  the Calendar has been switched
     */
    public static Calendar Str2CalendarHM(String input) {

        if (input == null || input.trim().equals("")) {
            return null;
        }
        Calendar now = new GregorianCalendar();
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                Integer.parseInt(input.substring(0, 2)),
                Integer.parseInt(input.substring(3, 5)),
                0);
        return cal;
    }

    /**
     * 转换(hh:mm:ss) to Calendar
     *
     * @param input a String with format of hh:mm:ss to be switched to Date
     * @return cal  the Calendar has been switched
     */
    public static Calendar Str2CalendarHMS(String input) {

        if (input == null || input.trim().equals("")) {
            return null;
        }
        Calendar now = new GregorianCalendar();
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                Integer.parseInt(input.substring(0, 2)),
                Integer.parseInt(input.substring(3, 5)),
                Integer.parseInt(input.substring(6, 8)));
        return cal;
    }


    /**
     * 转换字符串 to a Date
     *
     * @param input   the String to be switched
     * @param pattern the specified pattern
     * @return output the Date has been switched
     */
    public static Date Str2Date(String input, String pattern) {
        Date output = null;
        if (input != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setTimeZone(DateUtils.zone);
                output = sdf.parse(input);
            } catch (ParseException e) {
                System.err.println(e);
                return null;
            }
            return output;
        } else {
            return null;
        }
    }

    /**
     * 转换字符串(yyyy-mm-dd) to a Date
     *
     * @param input a String with format of yyyy-mm-dd to be switched to Date
     * @return output the Date has been switched
     */
    public static Date Str2Date(String input) {
        Date output = null;
        if (input != null) {
            Calendar cal = Str2CalendarYMD(input);
            output = cal.getTime();
            return output;
        } else {
            return null;
        }
    }

    /**
     * 转换字符串(yyyy-mm-dd hh:mm) to a Date
     *
     * @param input a String with format of yyyy-mm-dd hh:mm to be switched to Date
     * @return output the Date has been switched
     */
    public static Date Str2DateYMDHM(String input) {
        Date output = null;
        if (input != null) {
            Calendar cal = Str2CalendarYMDHM(input);
            output = cal.getTime();
            return output;
        } else {
            return null;
        }
    }

    /**
     * 转换字符串(yyyy-mm-dd hh:mm:ss) to a Date
     *
     * @param input a String with format of yyyy-mm-dd hh:mm:ss to be switched to Date
     * @return output the Date has been switched
     */
    public static Date Str2DateYMDHMS(String input) {
        Date output = null;
        if (input != null) {
            Calendar cal = Str2CalendarYMDHMS(input);
            output = cal.getTime();
            return output;
        } else {
            return null;
        }
    }

    /**
     * 转换字符串(hh:mm) to a Date
     *
     * @param input a String with format of hh:mm to be switched to Date
     * @return output the Date has been switched
     */
    public static Date Str2DateHM(String input) {
        Date output = null;
        if (input != null) {
            Calendar cal = Str2CalendarHM(input);
            output = cal.getTime();
            return output;
        } else {
            return null;
        }
    }

    /**
     * 转换字符串(hh:mm:ss) to a Date
     *
     * @param input a String with format of hh:mm:ss to be switched to Date
     * @return output the Date has been switched
     */
    public static Date Str2DateHMS(String input) {
        Date output = null;
        if (input != null) {
            Calendar cal = Str2CalendarHMS(input);
            output = cal.getTime();
            return output;
        } else {
            return null;
        }
    }


    /**
     * switch Date to Calendar
     *
     * @param date
     * @return cal
     */
    public static Calendar Date2Calendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal;
    }

    /**
     * 转换日期为指定格式的字符串
     *
     * @param date
     * @param pattern 返回格式，如：yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String Date2Str(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat time = new SimpleDateFormat(pattern);
        time.setTimeZone(DateUtils.zone);
        return time.format(date);
    }

    /**
     * 转换Calendar为指定格式的字符串
     *
     * @param cal
     * @param pattern 返回格式，如：yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String Calendar2Str(Calendar cal, String pattern) {
        SimpleDateFormat time = new SimpleDateFormat(pattern);
        time.setTimeZone(DateUtils.zone);
        Date date = cal.getTime();
        return time.format(date);
    }


    /**
     * 转换时间到字符串(yyyy-MM-dd)
     *
     * @param date
     * @return String
     */
    public static String Date2Str(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        time.setTimeZone(DateUtils.zone);
        return time.format(date);
    }

    /**
     * 转换时间到字符串(yyyy-MM-dd HH:mm)
     *
     * @param date
     * @return String
     */
    public static String Date2StrYMDHM(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time.setTimeZone(DateUtils.zone);
        return time.format(date);
    }

    /**
     * 转换时间到字符串(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return String
     */
    public static String Date2StrYMDHMS(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setTimeZone(DateUtils.zone);
        return time.format(date);
    }

    /**
     * 转换时间到字符串(HH:mm)
     *
     * @param date
     * @return String
     */
    public static String Date2StrHM(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        time.setTimeZone(DateUtils.zone);
        return time.format(date);
    }

    /**
     * 转换时间到字符串(HH:mm:ss)
     *
     * @param date
     * @return String
     */
    public static String Date2StrHMS(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        time.setTimeZone(DateUtils.zone);
        return time.format(date);
    }

    /* ******************* 取得当前日期 ********************* */

    /**
     * get today (yyyy-MM-dds)
     *
     * @return
     */
    public static String getTodayYMD() {
        return getToday("yyyy-MM-dd");
    }

    /**
     * get today (yyyy-MM-dd HH:mm)
     *
     * @return
     */
    public static String getTodayYMDHM() {
        return getToday("yyyy-MM-dd HH:mm");
    }

    /**
     * get today (yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getTodayYMDHMS() {
        return getToday("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 取得当前日期按指定格式显示的字符串
     *
     * @param format
     * @return
     */
    public static String getToday(String format) {
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat(format);
        sd.setTimeZone(DateUtils.zone);
        return sd.format(date);
    }

    /* ******************* 取得上月今天 ********************* */

    /**
     * get today of last month
     *
     * @return
     */
    public static String getLastMonthDate() {

        GregorianCalendar cal = new GregorianCalendar();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (month == 1) {
            // 如果今天是1月某天，计算上月今天则为去年12月某天
            year = year - 1;
            month = 12;
        } else {
            // 如果今天不是1月某天，计算上月今天则为上月某天
            month = month - 1;
        }

        Calendar lastMonthLastDay = getBeforeLastDay(cal);
        int lastMonthDay = lastMonthLastDay.get(Calendar.DAY_OF_MONTH);
        if (lastMonthDay < day) {
            day = lastMonthDay;
        }

        Calendar lastMonthToday = new GregorianCalendar(year, month, day);
        return Date2Str(lastMonthToday.getTime());
    }

    /* ******************* 取得指定月的上月今天 ********************* */

    /**
     * get the time on designated of last month
     *
     * @return
     */
    public static String getOnDesignatedLastMonthDate(String time) {

        Calendar cal = DateUtils.Str2CalendarYMDHMS(time);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        if (month == 1) {
            // 如果天是1月某天，计算上月今天则为去年12月某天
            year = year - 1;
            month = 12;
        } else {
            // 如果天不是1月某天，计算上月今天则为上月某天
            month = month - 1;
        }

        Calendar lastMonthLastDay = getBeforeLastDay(cal);
        int lastMonthDay = lastMonthLastDay.get(Calendar.DAY_OF_MONTH);
        if (lastMonthDay < day) {
            day = lastMonthDay;
        }

        Calendar lastMonthToday = new GregorianCalendar(year, month, day, hour, minute, second);
        return Date2StrYMDHMS(lastMonthToday.getTime());
    }

    /* ******************* 取得指定月的下月今天 ********************* */

    /**
     * get the time on designated of last month
     *
     * @return
     */
    public static String getOnDesignatedNextMonthDate(String time) {

        Calendar cal = DateUtils.Str2CalendarYMDHMS(time);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        if (month == 12) {
            // 如果天是1月某天，计算上月今天则为去年12月某天
            year = year + 1;
            month = 1;
        } else {
            // 如果天不是1月某天，计算上月今天则为上月某天
            month = month + 1;
        }

        Calendar lastMonthLastDay = getAfterLastDay(cal);
        int lastMonthDay = lastMonthLastDay.get(Calendar.DAY_OF_MONTH);
        if (lastMonthDay < day) {
            day = lastMonthDay;
        }

        Calendar lastMonthToday = new GregorianCalendar(year, month, day, hour, minute, second);
        return Date2StrYMDHMS(lastMonthToday.getTime());
    }

    public static String getLastYearDate() {
        GregorianCalendar cal = new GregorianCalendar();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        year = year - 1;
        Calendar lastMonthToday = new GregorianCalendar(year, month, day);
        return Date2Str(lastMonthToday.getTime());
    }


    /* ******************* 一些关于旬的方法 ********************* */

    /**
     * 判断日期是否是一旬的开始日
     *
     * @param cal
     * @return true if 1日、11日、21日
     */
    public static boolean isStartOf10Days(Calendar cal) {

        boolean ret = false;
        int intDay = cal.get(Calendar.DAY_OF_MONTH);
        if (intDay == 1 || intDay == 11 || intDay == 21) {
            ret = true;
        }
        return ret;
    }

    /**
     * 判断日期是否是一旬的结束日
     *
     * @param cal
     * @return true if 10日、20日、当月最后一天
     */
    public static boolean isEndOf10Days(Calendar cal) {

        boolean ret = false;
        int intDay = cal.get(Calendar.DAY_OF_MONTH);
        if (intDay == 10 || intDay == 20 || calEquals(cal, getLastDay(cal)) == true) {
            ret = true;
        }
        return ret;
    }

    /**
     * 判断两个日期的年月日部分是否相同
     *
     * @param first
     * @param second
     * @return
     */
    private static boolean calEquals(Calendar first, Calendar second) {
        return Date2Str(first.getTime()).equals(Date2Str(second.getTime()));
    }

    /**
     * 判断两个日期是否在同一个旬中
     * cal1、cal2都不是某旬的开始日或结束日
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isInSame10Days(Calendar cal1, Calendar cal2) {

        boolean ret = false;
        // 取得给出日期之前出现的最后一个旬的开始日
        Calendar calStart = getS10DaysBefore(cal1);
        // 取得给出日期之后出现的第一个旬的结束日
        Calendar calEnd = getE10DaysAfter(cal1);
        if (cal2.after(calStart) == true && cal2.before(calEnd) == true) {
            ret = true;
        }
        return ret;
    }

    /**
     * 取得当月第一天
     *
     * @param cal
     * @return
     */
    private static Calendar getFirstDay(Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        ret.set(Calendar.DAY_OF_MONTH, 1);
        return ret;
    }

    /**
     * 取得当月最后一天
     *
     * @param cal
     * @return
     */
    private static Calendar getLastDay(Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        ret.add(Calendar.MONTH, 1);
        ret.set(Calendar.DAY_OF_MONTH, 1);
        ret.add(Calendar.DAY_OF_MONTH, -1);
        return ret;
    }

    /**
     * 取得次月第一天
     *
     * @param cal
     * @return
     */
    public static Calendar getNextFirstDay(Calendar cal) {
        Calendar ret = getFirstDay(cal);
        ret.add(Calendar.MONTH, 1);
        return ret;
    }

    /**
     * 取得次月最后一天
     *
     * @param cal
     * @return
     */
    public static Calendar getNextLastDay(Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        ret.add(Calendar.MONTH, 1);
        ret = getLastDay(ret);
        return ret;
    }

    /**
     * 取得前月第一天
     *
     * @param cal
     * @return
     */
    public static Calendar getBeforeFirstDay(Calendar cal) {
        Calendar ret = getFirstDay(cal);
        ret.add(Calendar.MONTH, -1);
        return ret;
    }

    /**
     * 取得前月最后一天
     *
     * @param cal
     * @return
     */
    public static Calendar getBeforeLastDay(Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        ret.add(Calendar.MONTH, -1);
        ret = getLastDay(ret);
        return ret;
    }

    /**
     * 取得下月最后一天
     *
     * @param cal
     * @return
     */
    public static Calendar getAfterLastDay(Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        ret.add(Calendar.MONTH, +1);
        ret = getLastDay(ret);
        return ret;
    }

    /**
     * 取得给出日期之前出现的最后一个旬的开始日
     *
     * @param cal
     * @return
     */
    public static Calendar getS10DaysBefore(Calendar cal) {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar ret = (Calendar) cal.clone();
        if (day == 1 || day == 11 || day == 21) {
            ret = getStart10DaysBefore(ret);
        } else if (day > 1 && day < 11) {
            ret.set(Calendar.DAY_OF_MONTH, 1);
        } else if (day > 11 && day < 21) {
            ret.set(Calendar.DAY_OF_MONTH, 11);
        } else {
            ret.set(Calendar.DAY_OF_MONTH, 21);
        }
        return ret;
    }

    /**
     * 取得给出日期之后出现的第一个旬的开始日
     *
     * @param cal
     * @return
     */
    public static Calendar getS10DaysAfter(Calendar cal) {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar ret = (Calendar) cal.clone();
        if (day == 1 || day == 11 || day == 21) {
            ret = getStart10DaysAfter(ret);
        } else if (day > 1 && day < 11) {
            ret.set(Calendar.DAY_OF_MONTH, 11);
        } else if (day > 11 && day < 21) {
            ret.set(Calendar.DAY_OF_MONTH, 21);
        } else {
            ret.set(Calendar.DAY_OF_MONTH, 21);
            ret = getStart10DaysAfter(ret);
        }
        return ret;
    }

    /**
     * 取得给出日期之前出现的最后一个旬的结束日
     *
     * @param cal
     * @return
     */
    public static Calendar getE10DaysBefore(Calendar cal) {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar ret = (Calendar) cal.clone();
        if (day == 1 || day == 11 || day == 21) {
            ret.add(Calendar.DAY_OF_MONTH, -1);
        } else if (day > 1 && day < 11) {
            ret = getBeforeLastDay(ret);
        } else if (day > 11 && day < 21) {
            ret.set(Calendar.DAY_OF_MONTH, 10);
        } else {
            ret.set(Calendar.DAY_OF_MONTH, 20);
        }
        return ret;
    }

    /**
     * 取得给出日期之后出现的第一个旬的结束日
     *
     * @param cal
     * @return
     */
    public static Calendar getE10DaysAfter(Calendar cal) {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar ret = (Calendar) cal.clone();
        if (day == 10 || day == 20 || day == getLastDay(ret).get(Calendar.DAY_OF_MONTH)) {
            // 原值返回
        } else if (day >= 1 && day < 10) {
            ret.set(Calendar.DAY_OF_MONTH, 10);
        } else if (day >= 11 && day < 20) {
            ret.set(Calendar.DAY_OF_MONTH, 20);
        } else {
            ret = getLastDay(ret);
        }
        return ret;
    }

    /**
     * 取得1旬之后的开始日期
     *
     * @param cal 旬的开始时间（1日或11日或21）
     * @return
     */
    public static Calendar getStart10DaysAfter(Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day != 21) {
            // 1日或11日，直接加10天，则为下一个旬的开始日
            ret.add(Calendar.DAY_OF_MONTH, 10);
        } else {
            // 21日，取次月1日为下一个旬的开始日
            ret = getLastDay(ret);
            ret.add(Calendar.DAY_OF_MONTH, 1);
        }
        return ret;
    }

    /**
     * 取得1旬之后的结束日期
     *
     * @param cal 旬的开始时间（1日或11日或21）
     * @return
     */
    public static Calendar getEnd10DaysAfter(Calendar cal) {
        Calendar ret = getStart10DaysAfter(cal);
        ret.add(Calendar.DAY_OF_MONTH, -1);
        return ret;
    }

    /**
     * 取得1旬之前的结束日期
     *
     * @param cal 旬的结束日 （10日或20日或月末）
     * @return
     */
    private static Calendar getEnd10DaysBefore(Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day != 10) {
            // 20日或月末，直接减10天，则为前一个旬的结束日
            ret.add(Calendar.DAY_OF_MONTH, -10);
        } else {
            // 10日，取前月月末为前一个旬的结束日
            ret = getFirstDay(ret);
            ret.add(Calendar.DAY_OF_MONTH, -1);
        }
        return ret;
    }

    /**
     * 取得1旬之前的开始日期
     *
     * @param cal 旬的结束日 （10日或20日或月末）
     * @return
     */
    private static Calendar getStart10DaysBefore(Calendar cal) {
        Calendar ret = getEnd10DaysBefore(cal);
        ret.add(Calendar.DAY_OF_MONTH, 1);
        return ret;
    }

    /**
     * 将String数组（特定的格式）转换为Date数组
     *
     * @param input
     * @param format
     * @return
     */
    public static Date[] StrArray2DateArray(String[] input, String format) {
        int size = input.length;
        Date[] output = new Date[size];
        if (input != null) {
            for (int i = 0; i < size; i++) {
                output[i] = Str2Date(input[i], format);
            }
            return output;
        } else {
            return null;
        }
    }

    /**
     * 将String数组（特定的格式）转换为Calendar数组
     *
     * @param input
     * @param format
     * @return
     */
    public static Calendar[] StrArray2CalArray(String[] input, String format) {
        int size = input.length;
        Calendar[] output = new Calendar[size];
        if (input != null) {
            for (int i = 0; i < size; i++) {
                output[i] = Str2Calendar(input[i], format);
            }
            return output;
        } else {
            return null;
        }
    }

    /**
     * 将Calendar数组（特定的格式）转换为String数组
     *
     * @param input
     * @param format
     * @return
     */
    public static String[] CalArray2StrArray(Calendar[] input, String format) {
        int size = input.length;
        String[] output = new String[size];
        if (input != null) {
            for (int i = 0; i < size; i++) {
                if (input[i] == null) {
                    output[i] = null;
                } else {
                    output[i] = Calendar2Str(input[i], format);
                }
            }
            return output;
        } else {
            return null;
        }
    }

    /**
     * 将Calendar 的list转换成String类型
     *
     * @param input
     * @param format
     * @return
     */
    public static String calList2Str(List input, String format) {
        String result = "";
        for (int i = 0; i < input.size(); i++) {
            result += i + " : " + DateUtils.Calendar2Str((Calendar) input.get(i), format) + "\n";
        }
        return result;

    }

    /**
     * 求两个日期相差的年数
     *
     * @param cal1：结束时间
     * @param cal2：开始时间
     * @return
     */
    public static int subCalendar2Year(Calendar cal1, Calendar cal2) {
        long sub = cal1.getTimeInMillis() - cal2.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60 * 24 * 30 * 12)));
    }

    /**
     * 求两个日期相差的月数
     *
     * @param cal1：结束时间
     * @param cal2：开始时间
     * @return
     */
    public static int subCalendar2Month(Calendar cal1, Calendar cal2) {
        long sub = cal1.getTimeInMillis() - cal2.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60 * 24 * 30)));
    }

    /**
     * 求两个日期相差的日数
     *
     * @param cal1：结束时间
     * @param cal2：开始时间
     * @return
     */
    public static int subCalendar2Day(Calendar cal1, Calendar cal2) {
        long sub = cal1.getTimeInMillis() - cal2.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60 * 24)));
    }

    /**
     * 求两个日期相差的小时数
     *
     * @param cal1：结束时间
     * @param cal2：开始时间
     * @return
     */
    public static int subCalendar2Hour(Calendar cal1, Calendar cal2) {
        long sub = cal1.getTimeInMillis() - cal2.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60)));
    }

    public static int subCalendar2Minute(Calendar cal1, Calendar cal2) {
        long sub = cal1.getTimeInMillis() - cal2.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60)));
    }

    public static int subCalendar2Minute(String cal1, String cal2, String pattern) {
        return subCalendar2Minute(Str2Calendar(cal1, pattern), Str2Calendar(cal2, pattern));
    }

    /**
     * 求两个日期相差的时间(cal2 - cal1)
     *
     * @param interval :
     *                 yyyy 年
     *                 q 季度
     *                 m 月
     *                 y 一年的日数
     *                 d 日
     *                 w 一周的日数
     *                 ww 周
     *                 h 小时
     *                 n 分钟
     *                 s 秒
     * @param cal1
     * @param cal2
     * @return
     */
    public static int DateDiff(String interval, Calendar cal1, Calendar cal2) {
        int ret = 0;
        if ("y".equals(interval) == true) {
            ret = DateDiff2Year(cal1, cal2);    // 年
        } else if ("m".equals(interval) == true) {
            ret = DateDiff2Month(cal1, cal2);   // 月
        } else if ("d".equals(interval) == true) {
            ret = DateDiff2Day(cal1, cal2);     // 日
        } else if ("h".equals(interval) == true) {
            ret = DateDiff2Hour(cal1, cal2);    // 小时
        } else if ("n".equals(interval) == true) {
            ret = DateDiff2Minute(cal1, cal2);  // 分钟
        }
        return ret;
    }

    /**
     * 求两个日期相加的时间(cal + number)
     *
     * @param interval :
     *                 yyyy 年
     *                 q 季度
     *                 m 月
     *                 y 一年的日数
     *                 d 日
     *                 w 一周的日数
     *                 ww 周
     *                 h 小时
     *                 n 分钟
     *                 s 秒
     * @param number   : 正负整数
     * @param cal      : 操作的日期
     * @return
     */
    public static Calendar DateAdd(String interval, int number, Calendar cal) {
        Calendar ret = (Calendar) cal.clone();
        if ("yyyy".equals(interval) == true) {
            ret.add(Calendar.YEAR, number);      // 年
        } else if ("y".equals(interval) == true) {
            ret.add(Calendar.DAY_OF_YEAR, number);      // 一年的日数
        } else if ("m".equals(interval) == true) {
            ret.add(Calendar.MONTH, number);            // 月
        } else if ("d".equals(interval) == true) {
            ret.add(Calendar.DAY_OF_MONTH, number);     // 日
        } else if ("w".equals(interval) == true) {
            ret.add(Calendar.DAY_OF_WEEK, number);      // 一周的日数
        } else if ("ww".equals(interval) == true) {
            ret.add(Calendar.WEEK_OF_MONTH, number);    // 周
        } else if ("h".equals(interval) == true) {
            ret.add(Calendar.HOUR_OF_DAY, number);      // 小时
        } else if ("n".equals(interval) == true) {
            ret.add(Calendar.MINUTE, number);           // 分钟
        } else if ("s".equals(interval) == true) {
            ret.add(Calendar.SECOND, number);           // 秒
        }
        return ret;
    }

    public static Calendar format(Calendar cal, String pattern) {
        Calendar ret = (Calendar) cal.clone();
        String strCal = Calendar2Str(cal, pattern);
        ret = Str2Calendar(strCal, "yyyy-MM-dd HH:mm");
        return ret;
    }

    /**
     * 求两个日期相差的年数(cal2 - cal1)
     *
     * @param cal1：开始时间
     * @param cal2：结束时间
     * @return
     */
    public static int DateDiff2Year(Calendar cal1, Calendar cal2) {
        long sub = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60 * 24 * 30 * 12)));
    }

    /**
     * 求两个日期相差的月数(cal2 - cal1)
     *
     * @param cal1：开始时间
     * @param cal2：结束时间
     * @return
     */
    public static int DateDiff2Month(Calendar cal1, Calendar cal2) {
        long sub = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60 * 24 * 30)));
    }

    /**
     * 求两个日期相差的日数(cal2 - cal1)
     *
     * @param cal1：开始时间
     * @param cal2：结束时间
     * @return
     */
    public static int DateDiff2Day(Calendar cal1, Calendar cal2) {
        long sub = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60 * 24)));
    }

    /**
     * 求两个日期相差的小时数(cal2 - cal1)
     *
     * @param cal1：开始时间
     * @param cal2：结束时间
     * @return
     */
    public static int DateDiff2Hour(Calendar cal1, Calendar cal2) {
        long sub = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60 * 60)));
    }

    /**
     * 求两个日期相差的分钟数(cal2 - cal1)
     *
     * @param cal1：开始时间
     * @param cal2：结束时间
     * @return
     */
    public static int DateDiff2Minute(Calendar cal1, Calendar cal2) {
        if (cal1 == null && cal2 == null) {
            return 0;
        }
        if (cal1 == null) {
            return (int) Math.round((cal2.getTimeInMillis() / (1000.0 * 60)));
        }
        if (cal2 == null) {
            return -(int) Math.round((cal1.getTimeInMillis() / (1000.0 * 60)));
        }
        long sub = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        return (int) Math.round((sub / (1000.0 * 60)));
    }

    /**
     * 将日期的小时设置为8点0分，其他不变
     *
     * @param input
     * @return
     */
    public static Calendar set8Hour(Calendar input) {
        Calendar ret = (Calendar) input.clone();
        ret.set(Calendar.HOUR_OF_DAY, 8);
        ret.set(Calendar.MINUTE, 0);
        ret.set(Calendar.MILLISECOND, 0);
        return ret;
    }

    //1:当日，2：本周，3：本月，4：本年
    public static Date getStartDateForQueryType(int type) {
        Calendar cal = Calendar.getInstance();
        if (type == 2) {
            cal.set(Calendar.DAY_OF_WEEK, 1);
            cal.add(Calendar.DATE, 1);
        } else if (type == 3) {
            cal.set(Calendar.DAY_OF_MONTH, 1);
        } else if (type == 4) {
            cal.set(Calendar.DAY_OF_YEAR, 1);
        }
        return cal.getTime();
    }

    public static Date getTorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static Date getTorrow(Date date, int amount) {
        Calendar cal = Date2Calendar(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    public static int getYear(Date date) {
        Calendar cal = Date2Calendar(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar cal = Date2Calendar(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static List<String> getLast12Month() {
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        list.add(Calendar2Str(cal, "yyyy-MM"));
        for (int i = 0; i < 11; i++) {
            cal.add(Calendar.MONTH, -1);
            list.add(Calendar2Str(cal, "yyyy-MM"));
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            list2.add(list.get(i));
        }
        return list2;
    }

}
