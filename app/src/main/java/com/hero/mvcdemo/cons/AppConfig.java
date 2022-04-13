package com.hero.mvcdemo.cons;

public class AppConfig {
    //本地文件夹根节点
    /**
     * 服务器返回通信代号
     **/
    public static final int CORRECT_CODE = 200;             //返回正常
    public static final int ERROR_PARAMS_NULL = 400;          //参数为空
    public static final int ERROR_PARAMS_INCORRECT = 202;     //参数错误
    public static final int ERROR_OPERATE_FREQUENT = 0;     //操作过频

    /**
     * 设备信息
     */
    public static final String DEVICE_MODELVERSION = "DEVICE_MODELVERSION";     //手机型号和版本信息
    public static final String DEVICE_ID = "DEVICE_ID";

    /**
     * 登录状态
     */
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String OPEN_STATUS = "OPEN_STATUS";
    public static final String FIRST_OPEN = "FIRST_OPEN";      //安装第一次打开

    /**
     * 本地文件夹
     */


    /**
     * 资源文件夹名
     */
    public static final String RESTYPE_DRAWABLE = "drawable";
    public static final String RESTYPE_RAW = "raw";

}