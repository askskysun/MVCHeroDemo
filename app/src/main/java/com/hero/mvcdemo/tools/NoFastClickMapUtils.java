package com.hero.mvcdemo.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 防抖动  key  用于 识别是哪个点击时间
 * </pre>
 */
public class NoFastClickMapUtils {

    private static final String TAG = "NoFastClickUtils";

    private static Map<Integer, Long> lastClickMap = new HashMap<>();
    private static final int SPACETIME_DEFAUSE = 1000;    //默认防抖动时间  单位毫秒

    public static boolean isFastClick(int key) {
        return isFastClick(key, SPACETIME_DEFAUSE);
    }

    /**
     *
     * @param key       key的常量  用于 识别是哪个点击时间
     * @param spaceTime 时间间隔
     * @return
     */
    public static boolean isFastClick(int key, int spaceTime) {

        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击

        //上次点击的时间
        Long lastClickTime = lastClickMap.get(key);
        if (lastClickTime == null) {
            lastClickTime = 0L;
        }

        if (currentTime - lastClickTime > spaceTime) {
            isAllowClick = false;
        } else {
            isAllowClick = true;
        }

        lastClickMap.put(key, lastClickTime);
        return isAllowClick;
    }

    /**
     * key的常量  用于 识别是哪个点击时间
     */
    public class KeyContants {
        public static final int INTERACTION_LIVE_LOAD = 10000;   //直播 互动部分的 加载
        public static final int INTERACTION_LIVE_INVITE_DIALOG = 10001;   //直播 互动直播 邀请上麦
    }
}
