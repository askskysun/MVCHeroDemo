package com.hero.mvcdemo.cons.PrefercenceBean;

/**
 *
 */
public class PrefercenceAppConfigBean {
    private static final String TAG = PrefercenceAppConfigBean.class.getSimpleName();

    public static final String APP_CONFIG = "APP_CONFIG";
    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String APP_ALIAS = "APP_ALIAS";   //做唯一标识   用于推送   安装时  发送一个  其他时间也保持发送

    public static final String APP_LOGINSTATE = "APP_LOGINSTATE";     //登录状态
    public static final String APP_STATE_LOGINOUT = "APP_STATE_LOGINOUT";     //登出状态
    public static final String APP_STATE_LOGINING = "APP_STATE_LOGINING";     //登入状态

    public static final String APP_CURRENT_VENSION = "APP_CURRENT_VENSION";     //当前应用版本号   用于升级时的识别   MainActivty onPause  时设置

    //定期清缓存
    public static final String APP_CURRENTTIME = "APP_CURRENTTIME";     //当前毫秒值  用于定期清缓存

/*
    public static boolean getAppIsbackgroud() {
        return PreferenceHelper.readBoolean(IBingLiApplcation.getApplcationContext(), APP_ISBACKGROUND, APP_ISBACKGROUND, false);
    }

    public static void setAppIsbackgroud(boolean isbackgroud) {
        PreferenceHelper.write(IBingLiApplcation.getApplcationContext(), APP_ISBACKGROUND, APP_ISBACKGROUND, isbackgroud);
    }

    public static void setAppCurrentVension(int appCurrentVension) {
        PreferenceHelper.setIntPreData(APP_CURRENT_VENSION, APP_CURRENT_VENSION, appCurrentVension);
    }

    public static String getAppCurrenttime() {
        return PreferenceHelper.getPreData(APP_CURRENTTIME, APP_CURRENTTIME);
    }

    public static void setAppCurrenttime(String appCurrenttime) {
        PreferenceHelper.setPreData(APP_CURRENTTIME, APP_CURRENTTIME, appCurrenttime);
    }

    public static String getAppCurrenttimeForNetspeed() {
        return PreferenceHelper.getPreData(APP_CURRENTTIME_FORNETSPEED, APP_CURRENTTIME_FORNETSPEED);
    }

    public static void setAppCurrenttimeForNetspeed(String appCurrenttime) {
        PreferenceHelper.setPreData(APP_CURRENTTIME_FORNETSPEED, APP_CURRENTTIME_FORNETSPEED, appCurrenttime);
    }
*/

    /**
     * 获取当前用户登录状态
     *
     * @return
     */
  /*  public static String getLoginState() {
        return PreferenceHelper.getPreData(APP_LOGINSTATE, APP_LOGINSTATE);
    }*/

    /**
     * 判断当前用户是否有登录
     *
     * @return
     */
  /*  public static boolean isLoginedState() {
        String loginStatus = PreferenceHelper.getPreData(APP_LOGINSTATE, APP_LOGINSTATE);
        Logger.t(TAG).v("----isLoginedState----------" + loginStatus);
        if (loginStatus.equals(APP_STATE_LOGINING)) {
            return true;
        }
        return false;
    }*/

    /**
     * 设置登录状态
     *
     * @param loginState 当前登录状态
     * @param removeData 是否需要清空SP里面的数据
     */
   /* public static void setLoginState(String loginState, boolean removeData) {
        Logger.t(TAG).v("----setLoginState----------" + loginState + "  removeData：" + removeData);
        PreferenceHelper.setPreData(APP_LOGINSTATE, APP_LOGINSTATE, loginState);

        *//**
         * 账号还未登录的时候会启动给一个im_.db数据库，当登录成功之后需要切换到自己的数据库
         * 登出、挤出都要重置数据库对象，若此时切换账号的话，要切换到对应的数据库
         *//*
        DBHelper.getInstance().closeDBHelper();

        boolean isLoginOut = EmptyJudgeUtils.stringIsEquals(APP_STATE_LOGINOUT, loginState);
        if (!isLoginOut) {
            //登录操作，用户没有自动登录的话，就需要重新调用一下IM的登录，必须在APP_STATE_LOGINING状态设置之后调用
            IMLoginPresenter.getInstance().login();
            return;
        }

        PreferenceUserBean.setTimSig("");   //清空TIM保存的sign
        IMLoginPresenter.getInstance().logout();
        MarkConfigCache.getInstance().resertParams();

        if (removeData) { //主动登出
            PreferenceUserBean.setUserBean2SP(null, true);
            PreferenceUserBean.setInvitation("");
        } else { //被挤出
            PreferenceUserBean.setUserBean2SP(null);
        }
        //登出清除个人中心缓存统计数据
        HomeStatisticsBean.cleanStatisticsBean();
    }*/
}