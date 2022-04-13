/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hero.mvcdemo.tools;

import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * <p/>
 *
 * @author kymjs (https://github.com/kymjs)
 * @version 1.1
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static Pattern phone = Pattern
            .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    public static String find(String target, CharSequence rexgex) {
        Pattern p = Pattern.compile(rexgex.toString());
        Matcher m = p.matcher(target);
        String _target = "";
        while (m.find()) {
            _target += m.group();
        }
        return _target;
    }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence... strs) {
        for (CharSequence str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(CharSequence email) {
        if (isEmpty(email))
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence phoneNum) {
        if (isEmpty(phoneNum))
            return false;
        return phone.matcher(phoneNum).matches();
    }

    /*
     *检查输入内容是否符合身份证格式
     * */

    public static boolean checkCertificateNum(String s_aStr) {
        String has_x = "([0-9]{17}([0-9]|X|x))|([0-9]{15})";
        Pattern p = Pattern.compile(has_x);
        return p.matcher(s_aStr).matches();
    }

    /*
     * 检查输入是否符合邮编格式
     * */

    public static boolean checkZipCod(String str_zipcod) {
        String has_x = "[1-9]\\d{5}(?!\\d)";
        Pattern p = Pattern.compile(has_x);
        return p.matcher(str_zipcod).matches();
    }


    /**
     * 判断两个字符串是否相同,相同则返回true
     */

    public static boolean isAlike(String str_a, String str_b) {

        if (!str_a.equals(str_b)) {

        } else {
            return true;
        }
        return false;
    }

    /**
     * 当前时间
     **/
    public static String currentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * byte[]数组转换为16进制的字符串。
     *
     * @param data 要转换的字节数组。
     * @return 转换后的结果。
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return d;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        Date time = null;

        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate), TimeZone.getTimeZone("GMT+08"),
                    TimeZone.getDefault());
        }

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static String Timestamp2String(String timestamp) {
        Timestamp ts = new Timestamp(Long.parseLong(timestamp) * 1000);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String Timestamp2String(String timestamp, String format) {
        Timestamp ts = new Timestamp(Long.parseLong(timestamp) * 1000);
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     *
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     */
    public static Date transformTime(Date date, TimeZone oldZone,
                                     TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    public static String currencyForRMB(Object value) {
        NumberFormat nf = new DecimalFormat("￥,###0.00");
        if (value instanceof String) {
            return nf.format(Double.parseDouble(value + ""));
        }
        return nf.format(value);
    }

    public static String currencyForDollar(Object value) {
        NumberFormat nf = new DecimalFormat("$,###0.00");
        if (value instanceof String) {
            return nf.format(Double.parseDouble(value + ""));
        }
        return nf.format(value);
    }

    /**
     * 计价展示，数字变色
     **/
    public static SpannableStringBuilder setSpannableStringStyle(String st, String color) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < st.length(); i++) {
            if (matcherReg(String.valueOf(st.charAt(i)))) {
                list.add(i);
            }
        }

        SpannableStringBuilder style = new SpannableStringBuilder(st);
        for (int i = 0; i < list.size(); i++) {
//            style.setSpan(new BackgroundColorSpan(Color.RED),list.get(i),list.get(i)+1,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置textview的背景颜色
            style.setSpan(new ForegroundColorSpan(Color.parseColor(color)), list.get(i), list.get(i) + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置文字的颜色
        }
        return style;
    }

    /**
     * 自定义pattern，用于局部变色textview
     **/
    private static boolean matcherReg(CharSequence c) {
        String regEx = "[^￥$Xx0-9,.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(c.toString());
        if (m.matches()) {
            return false;
        }
        return true;
    }



    /*
     * 设置提醒文字hint的大小
     *
     * @param editText输入框
     * @param str_hint需要设置大小的字符串
     * @param size字体大小
     *
     * */

    public static void setHintSize(EditText editText, String str_hint, int size) {


        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(str_hint);

        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);

        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(CharSequence str) {
        try {
            Integer.parseInt(str.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*
     * 判读内容是否为数字
     * */
    public static boolean isNumber(String string) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(string);
        if (m.matches()) {
            //Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /*
     * 判断字符串是否由数字+字母组成
     * */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;

    }


    public static String htmlInColor(String s, String hexColor) {
        return "<font color=" + hexColor + ">" + s + "</font>";
    }

    public static String smallHtml(String s) {
        return "<small>" + s + "<small>";
    }

    public static String smallHtml(String s, String hexColor) {
        return "<small><font color=" + hexColor + ">" + s + "</font><small>";
    }

    public static String bigHtml(String s) {
        return "<big>" + s + "</big>";
    }

    public static String bigHtmlInColor(String s, String hexColor) {
        return "<big><font color=" + hexColor + ">" + s + "</font></big>";
    }

    //    拼接字符串
    public static String explicitUseStringBuider(String... values) {
        StringBuilder result = new StringBuilder();
        try {
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    if (values[i] != null) {
                        result.append(values[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 使用 Map按key进行排序得到key=value的字符串
     *
     * @param map
     * @param eqaulsType K与V之间的拼接字符串 = 或者其他...
     * @param spliceType K-V与K-V之间的拼接字符串  & 或者|...
     * @return
     */
    public static String stringByKey(Map<String, String> map, String eqaulsType,
                                     String spliceType) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        if (TextUtils.isEmpty(eqaulsType)) {
            eqaulsType = "  =  ";
        }
        if (TextUtils.isEmpty(spliceType)) {
            spliceType = "  &  ";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() == 0) {
                sb.append(entry.getKey()).append(eqaulsType).append(entry.getValue());
            } else {
                sb.append(spliceType).append(entry.getKey()).append(eqaulsType)
                        .append(entry.getValue());
            }
        }

        return sb.toString();
    }

    /**
     * String 转list
     *
     * @param strs
     * @param split
     * @return
     */
    public static List<String> stringToList(String strs, String split) {

        if (strs == null || split == null) {
            return new ArrayList<>();
        }

        String str[] = strs.split(split);
        return Arrays.asList(str);
    }

    public static List<String> stringToList(String strs) {
        return stringToList(strs, ",");
    }

    /**
     * list 转 String
     *
     * @param list
     * @param split
     * @return
     */
    public static String listToString(List<String> list, String split) {

        if (list == null || split == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        //第一个前面不拼接","
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (str == null) {
                continue;
            }

            if (i != 0) {
                result.append(split);
            }
            result.append(str);
        }

        return result.toString();
    }

    public static String listToString(List<String> list) {
        return listToString(list, ",");
    }

    /**
     * String 进行 Html转化
     * @param content
     * @return
     */
    public static CharSequence parseFromHtml(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }

        String contentNew = content.replace("\n", "<br>");
        Spanned spanned = Html.fromHtml(contentNew);
        return spanned;
    }
}
