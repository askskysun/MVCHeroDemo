package com.hero.mvcdemo.tools.httprequest.utils;

import com.hero.mvcdemo.tools.httprequest.HttpResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <pre>
 * 网络请求  方法实现类
 * </pre>
 */
public abstract class BaseMethodHttpUtils {
    protected OkHttpClient client;

    private Map<String, List<String>> mapUrlToIpList = new HashMap<>();       //key:请求的url  解析出来的ip 集合

    /**
     * 获取可替换的ip的原始域名
     *
     * @param host
     * @return
     */
    private String checkOrgHost(String host) {
        try {
            Iterator iter = mapUrlToIpList.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                List<String> valueList = (List<String>) entry.getValue();
                if (valueList != null && valueList.contains(host)) {
                    return (new URL(key)).getHost();   //用原始的域名匹配
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public void doGet(String url, final HttpResponse respon) {
        //如果使用okHttp->需导入okio
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            //请求失败->超时,网络异常
            @Override
            public void onFailure(Call call, IOException e) {
                respon.onDoError("网络连接异常");
            }

            //请求成功 200
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //失败
                if (!response.isSuccessful()) {
                    respon.onDoError("网络连接异常");
                }
                //请求体  string - 对象
                String content = response.body().string();
                respon.parse(content);
            }

        });
    }

    /**
     * get请求
     *
     * @param url
     * @param respon
     */
 /*   public void doGetSimpleResult(String url, final HttpResponse respon) {
        doGetSimpleResult(true, url, respon);
    }*/

    /**
     * get请求
     *
     * @param hasReqLog 是否打印请求log
     * @param url
     * @param respon
     */
   /* public void doGetSimpleResult(boolean hasReqLog, String url, final HttpResponse respon) {
        if (hasReqLog) {
            LogDefauseWriteUtils.getInstance().doLoggerRecord(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url);
        }

        //如果使用okHttp->需导入okio
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                //请求失败->超时,网络异常
                @Override
                public void onFailure(Call call, IOException e) {
                    respon.onDoError("网络连接异常：" + e.toString());
                }

                //请求成功 200
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //失败
                    if (!response.isSuccessful()) {
                        respon.onDoError("网络连接异常");
                        return;
                    }
                    //请求体  string - 对象
                    respon.onDoSuccess(response.body().string());
                }
            });
        } catch (Exception e) {
            LogDefauseWriteUtils.getInstance().doLoggerRecord(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url + "\n异常：" + e.toString());
            respon.onDoError("网络连接异常：" + e.toString());
            e.printStackTrace();
        } catch (Error el) {
            LogDefauseWriteUtils.getInstance().doLoggerRecord(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url + "\n异常：" + el.toString());
            respon.onDoError("网络连接错误：" + el.toString());
            el.printStackTrace();
        }
    }*/

    public void doPost(String url, RequestBody formBody, final HttpResponse respon) {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            //请求失败->超时,网络异常
            @Override
            public void onFailure(Call call, IOException e) {
                respon.onDoError("网络连接异常");
            }

            //请求成功 200
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //失败
                if (!response.isSuccessful()) {
                    respon.onDoError("网络连接异常");
                    return;
                }
                //请求体  string - 对象
                String content = response.body().string();
                respon.parse(content);
            }
        });
    }

    /**
     * 带进度文件下载
     * 下载文件
     *
     * @param fileUrl            文件url
     * @param destFileDir        存储目标文件夹
     * @param isSDPath           是否是在sd路径  不是则在app缓存文件中
     * @param isCallBackHttpPath 下载成功时 ，是否回调原网络图  不是则回调下载下来的本地文件
     * @param isFirstCheckFile   是否先判断本地与网络图片的大小是否一致
     */
/*
    public void downLoadFile(final String fileUrl, final String destFileDir, boolean isSDPath, final boolean isCallBackHttpPath, boolean isFirstCheckFile, Context context, final LoadingCallBack callBack) {
        //文件名 为md5值 + 文件最后一个/后的字符串 ------保证一个url对应一个文件名--------已下载过不用下载
        final File file = FileUtils.createFlieFromHttp(fileUrl, destFileDir, isSDPath, context);
        //保证文件  不小于网络获取大小  允许有2000b的误差 ----- 下载出错 会有文件残缺的情况
      */
/*  if (file.exists() &&file.length() > FileUtils.getHttpfileLength(fileUrl)-2000) {
            callBack.onDownloadSuccess(fileUrl);
            return;
        }*//*

        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, fileUrl, SystemTool.DIVIDE_LEVEL, destFileDir);

        if (!isFirstCheckFile) {
            if (file.exists() && file.length() > 0) {
                callBack.onDownloadSuccess(isCallBackHttpPath ? fileUrl : file.getAbsolutePath());
                Logger.t(destFileDir).w("file.exists(：" + fileUrl);
                return;
            }
        }

        //耗时操作  必须在判断文件是否存在后  否则回调很慢
        final int fileLength = FileUtils.getHttpfileLength(fileUrl) - 2000;

        if (isFirstCheckFile) {
            if (file.exists() && file.length() > 0) {
                callBack.onDownloadSuccess(isCallBackHttpPath ? fileUrl : file.getAbsolutePath());
                Logger.t(destFileDir).w("file.exists(：" + fileUrl);
                return;
            }
        }

        final Request request = new Request.Builder()
                .url(fileUrl)
                .tag(fileUrl)
                .build();
        final Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onDownloadFailed(fileUrl);

                if (file.exists() && file.length() < fileLength) { //把不完整的文件删掉
                    FileUtils.delFile(file);
                }
                Logger.t(destFileDir).e("onFailure：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int len = 0;
                InputStream is = null;
                byte[] buf = new byte[2048];
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    Logger.t(destFileDir).w("total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
//                    int progressMax = 0; //当到此点时停止  进度
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        Logger.t(destFileDir).d("current------>" + current);

                        fos.write(buf, 0, len);
                        int progress = (int) (current * 1.0f / total * 100);
                        callBack.onDownloading(progress);

                        //到此点  停止回调   防止下载完毕  还没获取图片大小   会继续数
                       */
/* if (progress >= 96) {
                            progressMax = 99;
                        }
                        if (progressMax == 99) {
                            callBack.onDownloading(99);
                        }else {
                            callBack.onDownloading(progress);
                        }*//*

                    }

                    fos.flush();

                    if (file.exists() && file.length() < fileLength) { //把不完整的文件删掉
                        callBack.onDownloadFailed(fileUrl);
                        FileUtils.delFile(file);
                    } else {
                        callBack.onDownloadSuccess(isCallBackHttpPath ? fileUrl : file.getAbsolutePath());//  文件完整再回调成功
                    }
                } catch (Exception e) {
                    Logger.t(destFileDir).e("onResponse--------catch (Exception--------" + e.toString());
                    callBack.onDownloadFailed(fileUrl);

                    if (file.exists() && file.length() < fileLength) { //把不完整的文件删掉
                        FileUtils.delFile(file);
                    }
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (Exception e) {
                        Logger.t(destFileDir).e("onResponse--------finally-------" + e.toString());
                        callBack.onDownloadFailed(fileUrl);

                        if (file.exists() && file.length() < fileLength) { //把不完整的文件删掉
                            FileUtils.delFile(file);
                        }
                    }
                }
            }
        });
    }

    */
/**
     * 下载文件，文件存在，则使用已经存在的文件
     * 从URL中截取文件名
     *
     * @param downURL    下载文件地址
     * @param absDirPath 文件夹的绝对路径：/mnt/sdcard/packageName/filePath
     * @param callBack   下载回调
     *//*

    public void downLoadFileUrl2Name(String downURL, String absDirPath, LoadingCallBack callBack) {
        File file = new File(absDirPath, getFileNameFromUrl(downURL));
        downLoadFile(downURL, file.getAbsolutePath(), callBack);
    }

    */
/**
     * 下载文件方法
     * 若已经存在，则不下载，直接返回下载成功
     *
     * @param downURL     下载地址
     * @param absFilePath 保存文件的绝对路径：/mnt/sdcard/packageName/filePath/read.txt
     * @param callBack    下载回调
     *//*

    public void downLoadFile(final String downURL, final String absFilePath, final LoadingCallBack callBack) {
        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, downURL, SystemTool.DIVIDE_LEVEL, absFilePath);

        final File file = new File(absFilePath);

        Request request = new Request.Builder()
                .url(downURL)
                .tag(downURL)//给请求设置一个标，用于取消请求
                .build();

        client.newCall(request).enqueue(new Callback() {

            */
/**
             * 把不完整的文件删除
             * @param fileLength 源文件大小
             * @return true:已删除不完整文件；false:没有文件需要删除
             *//*

            private boolean delfile(long fileLength) {
                if (file.exists() && file.length() < fileLength) {
                    FileUtils.delFile(file);
                    return true;
                }
                return false;
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onDownloadFailed(downURL);
                Logger.t(absFilePath).e("onFailure：" + e.toString());
                LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, absFilePath, " : onFailure : ", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long fileLength = response.body().contentLength();
                Logger.t(absFilePath).w("total------>" + fileLength);
                LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, absFilePath, " : SuccessMsg : ", "total------>" + fileLength);
                delfile(fileLength);
                if (file.exists() && file.length() == fileLength) {
                    callBack.onDownloadSuccess(file.getAbsolutePath());
                    Logger.t(absFilePath).w("file.exists：" + downURL);
                    return;
                }

                int len = 0;
                InputStream is = null;
                byte[] buf = new byte[2048];
                FileOutputStream fos = null;
                try {
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        Logger.t(absFilePath).d("current------>" + current);

                        fos.write(buf, 0, len);
                        int progress = (int) (current * 1.0f / fileLength * 100);
                        callBack.onDownloading(progress);
                    }
                    fos.flush();

                    if (delfile(fileLength)) {
                        callBack.onDownloadFailed(downURL);
                    } else {
                        callBack.onDownloadSuccess(file.getAbsolutePath());//  文件完整再回调成功
                    }
                } catch (Exception e) {
                    delfile(fileLength);
                    callBack.onDownloadFailed(downURL);
                    Logger.t(absFilePath).e("onResponse--------catch (Exception--------" + e.toString());
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (Exception e) {
                        delfile(fileLength);
                        callBack.onDownloadFailed(downURL);
                        Logger.t(absFilePath).e("onResponse--------finally-------" + e.toString());
                        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, absFilePath, " : Exception : ", e.toString());
                    }
                }
            }
        });
    }

    */
/**
     * 上传文件
     *
     * @param upUrl     文件上传URL
     * @param mapParams post参数
     * @param callBack  回调
     *//*

    public void upLoadFile(final String upUrl, Map<String, Object> mapParams, final LoadingCallBack callBack) {
        MediaType mediaType = MediaType.parse("application/octet-stream");// //MediaType 为全部类型

        //将fileBody添加进MultipartBody
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (String key : mapParams.keySet()) {  //追加参数
            Object object = mapParams.get(key);
            if (object instanceof File) {
                File file = (File) object;
                //根据文件类型，将File装进RequestBody中 RequestBody.create(mediaType, file)
                RequestBody fileBody = new ProgressRequestBody(mediaType, file, callBack);
                builder.addFormDataPart(key, file.getName(), fileBody);
            } else {
                builder.addFormDataPart(key, String.valueOf(object));
            }
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(upUrl)
                .post(requestBody)
                .tag(upUrl)//给请求设置一个标，用于取消请求
                .build();

        client.newBuilder()
                .writeTimeout(50, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        String eStr = e == null ? "IOException" : e.toString();

                        Logger.t(upUrl).e("onResponse：" + eStr);
                        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, upUrl, " : onFailure : ", eStr);
                        callBack.onDownloadFailed(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody responseBody = null;
                        try {
                            //获取请求结果 ResponseBody
                            responseBody = response.body();
                            //获取字符串
                            String info = responseBody.string();
                            Logger.t(upUrl).e("onResponse：" + info);
                            LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, upUrl, " : SuccessMsg : ", info);
                            if (response.isSuccessful()) {
                                callBack.onDownloadSuccess("文件上传成功");
                            } else {
                                callBack.onDownloadFailed(info);
                            }
                        } catch (Exception e) { //发生异常，失败回调
                            e.printStackTrace();
                            //可能还没有收到实视响应就已经重启了，导致SocketTimeOut
                            callBack.onDownloadSuccess("文件上传异常：" + e.toString());
//                            callBack.onDownloadFailed("文件上传异常：" + e.toString());
                        } finally {//记得关闭操作
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }
                    }
                });
    }

    //取消对应的请求
    public void cancleAll(Object tag) {
        Dispatcher dispatcher = client.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    */
/**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     *//*

    public void doDownload(final Context context, final String url, final String saveDir, final LoadingCallBack listener) {
        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, SystemTool.DIVIDE_LEVEL, saveDir);

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed(url);// 下载失败
                LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, " : onFailure : ", e == null ? "null" : e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int len = 0;
                InputStream is = null;
                byte[] buf = new byte[2048];
                FileOutputStream fos = null;
                // 储存下载文件的目录
//                String savePath = isExistDir(saveDir);
                String savePath = FileUtils.createAppDirFilePath(context, saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getFileNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);

                        listener.onDownloading(progress);// 下载中
                    }
                    fos.flush();

                    listener.onDownloadSuccess(file.getAbsolutePath()); // 下载完成
                } catch (Exception e) {
                    listener.onDownloadFailed(url);
                    LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, " : onFailure : Exception :", e == null ? "null" : e.toString());
                } finally {
                    try {
                        if (is != null) is.close();
                        if (fos != null) fos.close();
                        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, " : SuccessMsg : ");
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, " : onFailure : Exception :", e == null ? "null" : e.toString());
                    }
                }
            }
        });
    }

    */
/**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     *//*

    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    */
/**
     * @param url
     * @return 从下载连接中解析出文件名
     *//*

    @NonNull
    public static String getFileNameFromUrl(String url) {
        return TextUtils.isEmpty(url) ? "" : url.substring(url.lastIndexOf("/") + 1);
    }

    //没有data的请求
    public void doNoDataCommonPost(final String url, final HttpResponse respon) {
        doCommonBeanPost(url, null, respon);
    }

    public synchronized void doCommonBeanPost(final String url, Object params, final HttpResponse respon) {
        try {
            RequestRootBean requestRootBean = new RequestRootBean();
            requestRootBean.setData(params);

            Logger.t(url).d("JsonUtils.javabeanToJson(params) ：" + JsonUtils.javabeanToJson(params));
            doPostReq(url, respon, requestRootBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doPostReq(final String url, final HttpResponse respon, final Object requestRootBean) {
        */
/** 不可删除 用于替换成ip请求 **//*

//        long first = System.currentTimeMillis();
//        Logger.t("测试换IP时间前").i("--------------" + first);
//        final String urlNew = HttpDNSUtils.replaceIp(url, mapUrlToIpList);
//        long next = System.currentTimeMillis();
//        Logger.t("测试换IP时间前").i("--------------" + next + "  " + (next - first));
//        newIpReq(url, respon, requestRootBean, urlNew);

        newIpReq(url, respon, requestRootBean, url);
    }

    private void newIpReq(final String url, final HttpResponse respon, Object requestRootBean, String urlNew) {
        String reqStr = JsonUtils.javabeanToJson(requestRootBean);
//        LoggerFileDoUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, "   ", urlNew, SystemTool.DIVIDE_LEVEL, reqStr);
        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, SystemTool.DIVIDE_LEVEL, reqStr);
        Logger.t(urlNew).w(reqStr);

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, JsonUtils.javabeanToJson(requestRootBean));

        Request request = new Request.Builder()
                .url(urlNew)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            //请求失败->超时,网络异常
            @Override
            public void onFailure(Call call, IOException e) {
                String faiMsg = e.toString();
                Logger.t(url).i(faiMsg);
                LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, " : onFailure : ", faiMsg);
                respon.onDoError(faiMsg);
            }

            //请求成功 200
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //失败
                if (!response.isSuccessful()) {
                    String unSuccessMsg = response.toString();
                    Logger.t(url).i(unSuccessMsg);
                    LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, " : unSuccessMsg : ", unSuccessMsg);
                    respon.onDoError(unSuccessMsg);
                    return;
                }
                */
/**
                 * 返回请求体
                 *//*

                String content = response.body().string();
                respon.parse(content);

                Logger.t(url).i(content);
                //记录成功信息日志
                logSuccessMsg(content, url);
            }
        });
    }

    */
/**
     * 记录成功信息日志
     *
     * @param content
     * @param url
     *//*

    private void logSuccessMsg(String content, String url) {
        //httpReson String 类型的话,
        JSONObject js = null;
        try {
            js = new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String stateStr = content;
        try {
            JSONObject state = js.getJSONObject(HttpResponse.RES_FIELD_STATE);
            stateStr = state.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogDefauseWriteUtils.doRecordSimpleLog(LoggerContans.LOG_TAG_DEBUG_HTTPREQUEST, url, " SuccessMsg :  ", stateStr);
    }

    //2.16	邀请码注册
    public void doInvitationNoverifyPost(String invitationCode, Map<String, String> paramsMap, final HttpResponse respon) {
        InvitationRequestRootBean requestRootBean = new InvitationRequestRootBean();
        InvitationRegisterBean invitationRegisterBean = new InvitationRegisterBean();
        if (!EmptyJudgeUtils.stringIsEmpty(invitationCode)) {
            invitationRegisterBean.setInvitationCode(invitationCode);
        }

        if (paramsMap != null && paramsMap.size() > 0) {
            invitationRegisterBean.setRegisterDto(paramsMap);
        }

        requestRootBean.setData(invitationRegisterBean);
        String url = Api.REMOT_INVITE_CODE;
        doPostReq(url, respon, requestRootBean);
    }

    */
/**
     * 修改关注标签
     *
     * @param
     * @param paramsmap
     * @param respon
     *//*

    public void doUpdatetagsPost(Map<String, String> paramsmap, final HttpResponse respon) {
        RequestRootBean requestRootBean = new RequestRootBean();
        List<AddFollowTagBean> tagOperations = new ArrayList<>();
        if (paramsmap != null && paramsmap.size() > 0) {
            Iterator iter = paramsmap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                AddFollowTagBean addFollowTagBean = new AddFollowTagBean();
                addFollowTagBean.setId((String) entry.getKey());
                addFollowTagBean.setOperationType((String) entry.getValue());
                tagOperations.add(addFollowTagBean);
            }
        }

        AddFollowTagRootBean addFollowTagRootBean = new AddFollowTagRootBean();
        addFollowTagRootBean.setUserId(PreferenceUserBean.getUserId());
        addFollowTagRootBean.setTagOperations(tagOperations);
        Map<String, Object> mapAddTag = new HashMap<>();
        mapAddTag.put("tagsOperationDto", addFollowTagRootBean);

        requestRootBean.setData(mapAddTag);
        String url = Api.REMOT_UPDATE_FOLTAGS;
        doPostReq(url, respon, requestRootBean);
    }

    */
/**
     * 设置网络请求的根json
     *
     * @param obj
     * @param keyRet
     * @return
     *//*

    public static Object setRetRootJson(Object obj, String keyRet) {
        if (obj == null || EmptyJudgeUtils.stringIsEmpty(keyRet)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put(keyRet, obj);
        return map;
    }

    */
/**
     * 获取返回json
     *
     * @param jsonObj
     * @return
     *//*

    public static Object getResponseJson(Object jsonObj) {
        if (jsonObj == null) {
            return null;
        }
        String jsonStr = jsonObj.toString();
        if (EmptyJudgeUtils.stringIsEmpty(jsonStr)) {
            return null;
        }
        try {
            JSONObject jsonBody = new JSONObject(jsonStr);
            if (jsonBody.getInt(HttpResponse.RES_CUS_FIELD_TYPE) == 1) {    //数组
                JSONArray jsonArraydata = jsonBody.getJSONArray(HttpResponse.RES_FIELD_DATA);
                return jsonArraydata;
            } else {
                JSONObject jSONObject = jsonBody.getJSONObject(HttpResponse.RES_FIELD_DATA);
                return jSONObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/
}