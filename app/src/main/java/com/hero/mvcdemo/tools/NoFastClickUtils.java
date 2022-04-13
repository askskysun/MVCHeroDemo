package com.hero.mvcdemo.tools;

/**
 * 快速点击工具类
 */

public class NoFastClickUtils {
    private static final String TAG = "NoFastClickUtils";

    private static String FAST_TIP = "操作过于频繁";

    private static long lastClickTime = 0;//上次点击的时间

    private static int spaceTime = 1000;//时间间隔
    private static int spaceTimeTen = 10000;//时间间隔

    public static boolean isFastClick() {
        //当前系统时间
        long currentTime = System.currentTimeMillis();
        boolean isAllowClick;//是否允许点击

        if (currentTime - lastClickTime > spaceTime) {
            isAllowClick = false;
        } else {
            isAllowClick = true;
        }
        lastClickTime = currentTime;
        return isAllowClick;
    }

    public static boolean isFastTenClick() {
        //当前系统时间
        long currentTime = System.currentTimeMillis();

        boolean isAllowClick;//是否允许点击

        if (currentTime - lastClickTime > spaceTimeTen) {

            isAllowClick = false;

        } else {

            isAllowClick = true;

        }

        lastClickTime = currentTime;

        return isAllowClick;

    }

}
