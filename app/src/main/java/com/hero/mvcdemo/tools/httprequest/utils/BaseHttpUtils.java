package com.hero.mvcdemo.tools.httprequest.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * <pre>
 *  网络请求构造基类
 * </pre>
 */
public abstract class BaseHttpUtils extends BaseMethodHttpUtils {

    BaseHttpUtils() {
        this.client = setOkHttpClient(getConnectTimeout(), getWriteTimeout(), getReadTimeout());
    }

    BaseHttpUtils(int connectTimeout, int writeTimeout, int readTimeout) {
        this.client = setOkHttpClient(connectTimeout, writeTimeout, readTimeout);
    }

    /**
     * 设置超时时间
     *
     * @param connectTimeout
     * @param writeTimeout
     * @param readTimeout
     * @return
     */
    OkHttpClient setOkHttpClient(int connectTimeout, int writeTimeout, int readTimeout) {
        client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .build();
        return client;
    }

    /**
     * 读取超时
     *
     * @return
     */
    abstract int getReadTimeout();

    /**
     * 写入超时
     *
     * @return
     */
    abstract int getWriteTimeout();

    /**
     * 连接超时
     *
     * @return
     */
    abstract int getConnectTimeout();
}