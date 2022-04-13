package com.hero.mvcdemo.tools.httprequest.utils;

import android.annotation.SuppressLint;
import android.util.SparseArray;

/**
 * <pre>
 * 自定义超时时间  网络请求
 * </pre>
 */
public class CusOutTimeHttpUtils extends BaseHttpUtils {
    private static final int DEFAUSE_READ_TIMEOUT = 500;       //默认读取超时时间
    private static final int DEFAUSE_WIRTE_TIMEOUT = 500;      //默认写入超时时间
    private static final int DEFAUSE_CONNECT_TIMEOUT = 500;     //默认连接超时时间

    private static final SparseArray<CusOutTimeHttpUtils> saInstance = new SparseArray<>();

    private CusOutTimeHttpUtils(int connectTimeout, int writeTimeout, int readTimeout) {
        super(connectTimeout, writeTimeout, readTimeout);
    }

    public static CusOutTimeHttpUtils getInstance() {
        return getInstance(DEFAUSE_CONNECT_TIMEOUT, DEFAUSE_WIRTE_TIMEOUT, DEFAUSE_READ_TIMEOUT);
    }

    /**
     *
     * @param timeOut  超时时间  单位 毫秒
     * @return
     */
    public static CusOutTimeHttpUtils getInstance(int timeOut) {
        return getInstance(timeOut, timeOut, timeOut);
    }

    /**
     *  超时时间  单位 毫秒
     * @param connectTimeout
     * @param writeTimeout
     * @param readTimeout
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static CusOutTimeHttpUtils getInstance(int connectTimeout, int writeTimeout, int readTimeout) {
        int key = String.format("%d%d%d", connectTimeout, writeTimeout, readTimeout).hashCode();
        CusOutTimeHttpUtils httpUtil = saInstance.get(key);
        if (httpUtil == null) {
            synchronized (CusOutTimeHttpUtils.class) {
                if (httpUtil == null) {
                    httpUtil = new CusOutTimeHttpUtils(connectTimeout, writeTimeout, readTimeout);
                    saInstance.put(key, httpUtil);
                }
            }
        }
        return httpUtil;
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