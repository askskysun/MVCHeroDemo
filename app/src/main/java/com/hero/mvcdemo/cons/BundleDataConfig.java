package com.hero.mvcdemo.cons;

/**
 * Bundle传输的常量
 */

public class BundleDataConfig {

    /**
     * 标识 是从启动页跳转到  活动页面    返回键 应该跳转  首页或者登陆页
     */
    public static final String USERID = "userId";
    public static final String USERNAME = "USERNAME";
    public static final String FORUSERID = "FORUSERID";

    /**
     * 发送eventbus 的code
     */
    public static final String EVENTBUSCODE = "EVENTBUSCODE";

    /**
     * 布尔值
     */
    public static final String BOOLEAN_VALUE = "BOOLEAN_VALUE";
    public static final String BOOLEAN_VALUE2 = "BOOLEAN_VALUE2";


    public static final String JSON_STR = "json_str";     //String类型的json字符串
    public static final String BUNDLEBEAN = "BUNDLEBEAN";     //javabean类型
}