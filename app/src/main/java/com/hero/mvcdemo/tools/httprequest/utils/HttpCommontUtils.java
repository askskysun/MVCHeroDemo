package com.hero.mvcdemo.tools.httprequest.utils;

/**
 * <pre>
 *  网络请求 公共使用类
 * </pre>
 */
public class HttpCommontUtils extends BaseHttpUtils {
    /**
     * 默认读取超时时间
     */
    private final int DEFAUSE_READ_TIMEOUT = 30 * 1000;
    private final int DEFAUSE_WIRTE_TIMEOUT = 10 * 1000;      //默认写入超时时间
    private final int DEFAUSE_CONNECT_TIMEOUT = 10 * 1000;     //默认连接超时时间

    /**
     * 生成http请求的单例方法
     *
     * @return
     */
    public static HttpCommontUtils getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final HttpCommontUtils INSTANCE = new HttpCommontUtils();
    }

    private HttpCommontUtils() {
        super();
    }

    /**
     * 读取超时
     *
     * @return
     */
    @Override
    int getReadTimeout() {
        return DEFAUSE_READ_TIMEOUT;
    }

    /**
     * 写入超时
     *
     * @return
     */
    @Override
    int getWriteTimeout() {
        return DEFAUSE_WIRTE_TIMEOUT;
    }

    /**
     * 连接超时
     *
     * @return
     */
    @Override
    int getConnectTimeout() {
        return DEFAUSE_CONNECT_TIMEOUT;
    }
}