package com.hero.mvcdemo.tools;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Date处理 时间处理
 */
public class DateUtils {
    /**
     * 一秒钟
     */
    public static final long SECOND = 1000L;
    /**
     * 一分钟
     */
    public static final long MINUTE = 60 * SECOND;
    /**
     * 一小时
     */
    public static final long HOUR = 60 * MINUTE;
    /**
     * 一天
     */
    public static final long DAY = 24 * HOUR;
    /**
     * 一年
     */
    public static final long YEAR = 365 * DAY;
    /**
     * 时间转换器_yyyy/MM/dd HH:mm:ss
     */
    public static final SimpleDateFormat sdfEn2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * 时间转换器_yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat sdfEn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间转换器_yyyy-MM-dd HH:mm
     */
    public static final SimpleDateFormat sdfEnYMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 时间转换器：yyyy-MM-dd
     */
    public static final SimpleDateFormat sdfEnYMD = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 时间转换器：yyyyMMdd
     */
    public static final SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");
    /**
     * 时间转换器_HH:mm
     */
    public static final SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");

    /**
     * 时间转换器_MM月dd日 HH:mm
     */
    public static final SimpleDateFormat sdfMonth = new SimpleDateFormat("MM月dd日 HH:mm");

    /**
     * 时间转换器_yyyy年MM月dd日 HH:mm
     */
    public static final SimpleDateFormat sdfYMDHm = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    /**
     * 时间转换器：yyyy年MM月dd日
     */
    public static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 时间转换器：yyyy-MM-dd HH:mm:ss:SSS
     */
    public static final SimpleDateFormat sdfMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String timeStamp2Date(long time, SimpleDateFormat sdf) {
        return sdf.format(new Date(time));
    }

    /**
     * 如何把yyyy-MM-dd HH:mm:ss转换成为毫秒数
     *
     * @param format
     * @return
     */
    public static Long timeStampToLong(String format) {
        Date sDt3 = null;
        try {
            sDt3 = sdfEn.parse(format);
            long ld3 = sDt3.getTime();
            return ld3;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 将时间转换成：yyyy-MM-dd HH:mm
     *
     * @param timeStamp
     * @return
     */
    public static String timeStamp2YMDHM(long timeStamp) {
        return sdfEnYMDHM.format(timeStamp);
    }

    /**
     * 接收：2018-05-02 12:00:00 格式的字符串
     * 返回：2018-05-02 12:00 格式的时间
     *
     * @param strYMDHMS
     * @return
     */
    public static String timeStamp2YMDHM(String strYMDHMS) {
        try {
            Date date = sdfEn.parse(strYMDHMS);
            return sdfEnYMDHM.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strYMDHMS;
    }

    /**
     * 将一个时间转换成HH:mm:ss的形式，
     * 相对时间是从0开始算的，不是以1900年开始
     *
     * @param timeStamp 以s为单位的时间
     * @return
     */
    public static String timeStampToHour(long timeStamp) {
        long times = timeStamp * SECOND;

        long hour, minute, seconds, temp;
        String strSeconds, strMinute, strHour;
        if (0 <= times && times < MINUTE) {
            seconds = times / SECOND;
            strSeconds = String.format("%02d", seconds);
            return "00:" + strSeconds;
        } else if (MINUTE <= times && times < HOUR) {
            minute = times / MINUTE;

            temp = times % MINUTE;
            seconds = temp / SECOND;

            strMinute = String.format("%02d", minute);
            strSeconds = String.format("%02d", seconds);
            return strMinute + ":" + strSeconds;
        } else if (HOUR <= times) {
            hour = times / HOUR;

            temp = times % HOUR;
            minute = temp / MINUTE;

            temp = times % MINUTE;
            seconds = temp / SECOND;

            strHour = String.format("%02d", hour);
            strMinute = String.format("%02d", minute);
            strSeconds = String.format("%02d", seconds);

            return strHour + ":" + strMinute + ":" + strSeconds;
        } else {
            return String.valueOf("00:00");
        }
    }

    /**
     * 获取当前时间:"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNowDate() {
        return getNowDate(sdfEn);
    }

    /**
     * 获取当前时间:"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNowDate(SimpleDateFormat sdf) {
        if (sdf == null) {
            return "";
        }

        String temp_str = "";
        Date dt = new Date();
        temp_str = sdf.format(dt);
        return temp_str;
    }

    /**
     * 获取30分钟后时间
     *
     * @return
     */
    public static String getAfter30minDate() {
        String temp_str = "";
        //    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        temp_str = sdf.format(System.currentTimeMillis() + 1000 * 60 * 30);
        return temp_str;
    }

    /**
     * <pre>
     *     时间格式化
     *     一分钟内：刚刚
     *     一小时内：mm分钟前
     *     一天内：今天 HH:mm
     *     两天内：昨天 HH:mm
     *     一年内：MM月dd日 HH:mm
     *     一年外：yyyy年MM月dd日
     * </pre>
     *
     * @param date 2018-03-05 11:00:00
     * @return
     */
    public static String convertTimeToFormat(String date) {
        if (TextUtils.isEmpty(date)) {
            return date;
        }

        long time = 0L;
        try {
            time = sdfEn.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.e("时间转换发生异常：" + date);
            return date;
        }
        return convertTime2FormatWithLong(time);
    }

    /**
     * <pre>
     *     时间格式化
     *     一分钟内：刚刚
     *     一小时内：mm分钟前
     *     一天内：今天 HH:mm
     *     两天内：昨天 HH:mm
     *     一年内：MM月dd日 HH:mm
     *     一年外：yyyy年MM月dd日
     * </pre>
     *
     * @param time long类型的时间戳
     * @return
     */
    public static String convertTime2FormatWithLong(long time) {
        Calendar format = Calendar.getInstance();
        format.setTimeInMillis(time);

        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);
        int hour = current.get(Calendar.HOUR_OF_DAY);
        int minute = current.get(Calendar.MINUTE);
        int second = current.get(Calendar.SECOND);

        Calendar oneMinute = Calendar.getInstance();
        oneMinute.set(year, month, day, hour, minute - 1, second);

        Calendar oneHour = Calendar.getInstance();
        oneHour.set(year, month, day, hour - 1, minute, second);

        Calendar oneDay = Calendar.getInstance();
        //考虑极端情况 2017-11-07 00:00:00应该算今天零点，能算昨天零点，所以要往前推一秒
        oneDay.set(year, month, day - 1, 23, 59, 59);

        Calendar yestoday = Calendar.getInstance();
        yestoday.set(year, month, day - 2, 23, 59, 59);

        Calendar oneYear = Calendar.getInstance();
        oneYear.set(year, 1, 1, 0, 0, 0);

        if (format.before(current) && format.after(oneMinute)) { //一分钟内
            return "刚刚";
        } else if (format.before(oneMinute) && format.after(oneHour)) { //一小时内
            long subtract = current.getTimeInMillis() - time;//单位 秒
            return String.format("%s 分钟前", (subtract / MINUTE));
        } else if (format.before(oneHour) && format.after(oneDay)) { //一天内
            return String.format("今天 %s", sdfHour.format(time));
        } else if (format.before(oneDay) && format.after(yestoday)) { //昨天内
            //date = "2017-11-07 00:00:00";
            return String.format("昨天 %s", sdfHour.format(time));
        } else if (format.before(yestoday) && format.after(oneYear)) { //一年内
            return sdfMonth.format(time);
        } else { //一年以外
            return sdfYear.format(time);
        }
    }

    /**
     * <pre>
     *     将时间转换成：今天、昨天、2019-09-29的形式；
     *     只有今天、昨天显示中文，其他显示日志
     * </pre>
     *
     * @param date 需要格式化的时间
     * @return
     */
    public static String convertTimeToDay(String date) {
        if (TextUtils.isEmpty(date)) {
            return date;
        }

        long time = 0L;
        try {
            time = sdfEnYMD.parse(date).getTime();
        } catch (ParseException e) {
            Logger.e("时间转换发生异常：" + date);
            e.printStackTrace();
            return date;
        }

        Calendar format = Calendar.getInstance();
        format.setTimeInMillis(time);

        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH);

        Calendar oneDay = Calendar.getInstance();
        //考虑极端情况 2017-11-07 00:00:00应该算今天零点，能算昨天零点，所以要往前推一秒
        oneDay.set(year, month, day - 1, 23, 59, 59);

        Calendar twoDay = Calendar.getInstance();
        twoDay.set(year, month, day - 2, 23, 59, 59);

        if (format.after(oneDay)) {
            return "今天";
        } else if (format.before(oneDay) && format.after(twoDay)) {
            return "昨天";
        } else {
            return sdfYear.format(time);
        }
    }

    /***
     * 将String的事件转换成 yyyy-MM-dd的格式
     * @param time
     * @return
     */
    public static String getData2YMD(String time) {
        try {
            Date date = sdfEnYMD.parse(time);
            return sdfEnYMD.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将时间转换成 02月24日 07:40的格式
     *
     * @param time 2018-05-03 19:21:47格式的String数据
     * @return
     */
    public static String getDate2MDHM(String time) {
        try {
            return sdfMonth.format(sdfEn.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将时间转换成：2018年06月07日14:00
     *
     * @param time 2018-05-03 19:21:47格式的String数据
     * @return
     */
    public static String getDate2YMDHM(String time) {
        try {
            return sdfYMDHm.format(sdfEn.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将时间转换成：2019年9月19日
     *
     * @param time 2018-05-03 格式的String数据
     * @return
     */
    public static String getDate2YMD(String time) {
        try {
            return sdfYear.format(sdfEnYMD.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 计算现在时间离给定的时间还有多久
     *
     * @param dueDate
     * @return
     */
    public static String convert2dueDate(long dueDate) {
        long interval = dueDate - System.currentTimeMillis();

        String symbol = "";//符号
        if (interval < 0) {
            symbol = "-";
            interval = Math.abs(interval);
        }

        String showTime = "";
        int hour = (int) (interval / HOUR);
        if (hour > 0) {
            showTime += hour + "小时";
        }

        interval = interval % HOUR;
        int minute = (int) (interval / MINUTE);
        if (minute > 0) {
            showTime += minute + "分钟";
        }

        if (TextUtils.isEmpty(showTime)) {
            showTime = "1分钟";
        }

        return symbol + showTime;
    }

    /**
     * 将时间转换成HH:mm的格式
     *
     * @param date
     * @return
     */
    public static String convert2HHMM(long date) {
        if (date <= 0) {
            return "";
        }

        return sdfHour.format(new Date(date));
    }

    /**
     * 获取指定时间格式的字符串
     *
     * @param simpleDateFormat
     * @return
     */
    public static String getDataFormat(SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String setAddTime(String time) {
        String timeFor = DateUtils.convertTimeToFormat(time);
        if (EmptyJudgeUtils.stringIsEmpty(timeFor)) {
            return "";
        }
        return timeFor + " 添加";
    }

    /**
     * 把毫秒时间  转化为00:00:00 格式  直播回放使用
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 把毫秒时间  转化为00时00分00秒 格式  直播回放使用
     *
     * @param timeMs 单位S
     * @return
     */
    public static String stringForTimeName(Long timeMs) {
        if (timeMs == null || timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00时00分00秒";
        }
        Long totalSeconds = timeMs;
        Long seconds = totalSeconds % 60;
        Long minutes = (totalSeconds / 60) % 60;
        Long hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d时%02d分%02d秒", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d分%02d秒", minutes, seconds).toString();
        }
    }
}
