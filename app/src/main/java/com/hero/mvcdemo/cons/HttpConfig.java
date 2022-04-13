package com.hero.mvcdemo.cons;

import java.util.regex.Pattern;

/**
 * <pre>
 * 网络相关的常量
 * </pre>
 */
public class HttpConfig {
    /**
     * 是网络路径  的字符串开头
     */
    public static final String ISNETURLSTART = "http";

    /**
     * 请求头域名的key
     */
    public static final String HOST_KEY = "Host";
    
    /**
     * 检测是否是有效链接的  正则
     */
    public static String REG_URL_REGEX = "((http|https)://[-a-zA-Z0-9+&#/%?=~_|!:,.;\\\\\\\\]*[-a-zA-Z0-9+&#/%=~_|])";
    public static Pattern pattern = Pattern.compile(REG_URL_REGEX, Pattern.CASE_INSENSITIVE);
    public static final int URL_MIN_LENGTH = 10;     //url最小长度
}