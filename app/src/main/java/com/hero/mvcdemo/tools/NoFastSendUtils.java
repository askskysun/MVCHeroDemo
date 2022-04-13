package com.hero.mvcdemo.tools;

/**
 * 按钮防抖动工具类
 */
public class NoFastSendUtils {

    public static void setSendLastSendTime(long sendLastSendTime) {
        NoFastSendUtils.sendLastSendTime = sendLastSendTime;
    }

    private static long sendLastSendTime = 0;//上次点击的时间

    /**
     * 点击事件：两次的时间间隔
     */
    private static int sendSpaceTime = 800;
    /**
     * 上传图片：时间间隔
     */
    private static int sendSpaceTimeTen = 8000;

    /**
     * 上传图片时使用的限制条件
     *
     * @return false：允许点击事件；true：点击过快，不响应点击事件
     */
    public static boolean isFastSend() {
        return isFastSend(sendSpaceTimeTen);
    }

    /**
     * 是否可以响应点击事件
     *
     * @return false：允许响应点击事件；true：点击过快，不响应点击事件
     */
    public static boolean isFastOnClick() {
        return isFastSend(sendSpaceTime);
    }

    /**
     * 在指定的事件间隔中不响应相应的事件
     *
     * @return false：允许响应点击事件；true：点击过快，不响应点击事件
     */
    public static boolean isFastSend(long interval) {
        boolean isAllowClick;//是否允许点击
        long currentTime = System.currentTimeMillis();//当前系统时间

        if (currentTime - sendLastSendTime > interval) {
            isAllowClick = false;
        } else {
            isAllowClick = true;
        }

        sendLastSendTime = currentTime;
        return isAllowClick;
    }
}
