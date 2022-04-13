package com.hero.mvcdemo.cons;

/**
 *
 */
public class Api {
    /**
     * App中的域名  在dns解析过滤使用
     */
    public static final String[] APP_HOST = {""};

//    public static String REMOT_HOST_ROOT = IBingLiApplcation.getApplcationContext().getResources().getString(R.string.api_host);
//    public static String REMOT_HOST = REMOT_HOST_ROOT + IBingLiApplcation.getApplcationContext().getResources().getString(R.string.api_root);

    static {
        initRemotHost();
    }
    /**
     * 主页
     */
    public static String REMOT_TIME_LINE;     //时间线

    /**
     * 初始化api
     */
    private static void initRemotHost() {

        /**
         * 主页
         */
//        REMOT_TIME_LINE = REMOT_HOST + "gw/get_user_timeline";     //时间线
       }

    /**
     * 重設域名
     *
     * @param remotHost
     */
    public static void setRemotHost(String remotHost) {
        String replace = remotHost.replace("．", ".").replace("：", ":").trim() + "/";

//        REMOT_HOST_ROOT = replace;
//        REMOT_HOST = REMOT_HOST_ROOT + IBingLiApplcation.getApplcationContext().getResources().getString(R.string.api_root);
        initRemotHost();
    }

}