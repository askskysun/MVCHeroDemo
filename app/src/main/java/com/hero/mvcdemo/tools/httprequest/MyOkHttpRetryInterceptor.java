package com.hero.mvcdemo.tools.httprequest;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InterruptedIOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 * OKhttp 重试机制
 * 未测试
 * </pre>
 */
public class MyOkHttpRetryInterceptor implements Interceptor {
    public int executionCount;//最大重试次数
    private long retryInterval;//重试的间隔

    MyOkHttpRetryInterceptor(Builder builder) {
        this.executionCount = builder.executionCount;
        this.retryInterval = builder.retryInterval;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = doRequest(chain, request);
        int retryNum = 0;
        while ((response == null || !response.isSuccessful()) && retryNum <= executionCount) {
            Logger.t("MyOkHttpRetryInterceptor").v("intercept Request is not successful - {}", response.body().toString());
            Logger.t("MyOkHttpRetryInterceptor").v("intercept Request is not successful - {} request :", request.body().toString());
            final long nextInterval = getRetryInterval();
            try {
                Logger.t("MyOkHttpRetryInterceptor").v("\"Wait for {}\"" + nextInterval);
                Thread.sleep(nextInterval);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            }
            retryNum++;
            // retry the request
            response = doRequest(chain, request);
        }
        return response;
    }

    private Response doRequest(Chain chain, Request request) {
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
        }
        return response;
    }

    /**
     * retry间隔时间
     */
    public long getRetryInterval() {
        return this.retryInterval;
    }

    public static final class Builder {
        private int executionCount;
        private long retryInterval;

        public Builder() {
            executionCount = 3;
            retryInterval = 1000;
        }

        public Builder executionCount(int executionCount) {
            this.executionCount = executionCount;
            return this;
        }

        public Builder retryInterval(long retryInterval) {
            this.retryInterval = retryInterval;
            return this;
        }

        public MyOkHttpRetryInterceptor build() {
            return new MyOkHttpRetryInterceptor(this);
        }
    }
}
