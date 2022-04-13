package com.hero.mvcdemo.tools.httprequest;

/**
 * 加载回调
 */
public interface LoadingCallBack {
    /**
     * 下载成功
     */
    void onDownloadSuccess(String fileUrl);

    /**
     * @param progress 下载进度
     */
    void onDownloading(int progress);

    /**
     * 下载失败
     */
    void onDownloadFailed(String fileUrl);
}