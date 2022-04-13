package com.hero.mvcdemo.tools.httprequest;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * <pre>
 *     上传进度处理类
 * </pre>
 */
public class ProgressRequestBody extends RequestBody {
    private File mFile;
    private MediaType mMediaType;
    private LoadingCallBack mUploadProgress;

    public ProgressRequestBody(MediaType mMediaType, File mFile, LoadingCallBack mUploadProgress) {
        this.mFile = mFile;
        this.mMediaType = mMediaType;
        this.mUploadProgress = mUploadProgress;
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public MediaType contentType() {
        return mMediaType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            long current = 0;
            long fileLength = contentLength();
            source = Okio.source(mFile);
            Buffer buf = new Buffer();
            for (long readCount; (readCount = source.read(buf, 2048)) != -1; current += readCount) {
                int progress = (int) (current * 1f / fileLength * 100);
                mUploadProgress.onDownloading(progress);
                Log.e("HttpUtilsTest", "current------>" + current + ";   fileLength：" + fileLength + "；   progress：" + progress);

                sink.write(buf, readCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != source) {
                source.close();
            }
        }
    }
}