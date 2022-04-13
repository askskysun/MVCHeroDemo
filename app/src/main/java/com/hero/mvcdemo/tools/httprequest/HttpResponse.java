package com.hero.mvcdemo.tools.httprequest;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.hero.mvcdemo.tools.StringUtils;
import com.hero.mvcdemo.tools.httprequest.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 处理HTTP请求 响应
 */
public abstract class HttpResponse<T> {
    public static final String HTTPERROR = "网络请求失败";
    //响应体 字段
    public static final String RES_FIELD_STATE = "state";
    public static final String RES_FIELD_CODE = "code";
    public static final String RES_FIELD_DATA = "data";
    public static final String RES_FIELD_LIST = "list";

    public static final String RES_CUS_FIELD_TYPE = "type";
    public static final String RES_TYPE_OBJ = "0";
    public static final String RES_TYPE_ARRARY = "1";

    public static final String RES_SUCCESS_CODE = "200";

    //接受一个泛型类,这个泛型类代表了我需要的数据类型
    Class<T> t = null;
    Handler mHandler;

    public HttpResponse(Class<T> t) {
        this.t = t;
    }

    public HttpResponse(Class<T> t, Handler mHandler) {
        this.t = t;
        this.mHandler = mHandler;
    }

    public HttpResponse(Class<T> t, boolean toMainThread) {
        this.t = t;
        if (toMainThread) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
    }

    //网络请求成功后的回调方法
    public abstract void onSuccess(T t);

    /**
     * handler处理回调
     *
     * @param t
     */
    public void onDoSuccess(final T t) {
        if (mHandler == null) {
            onSuccess(t);
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(t);
            }
        });
    }

    //网络请求失败后的回调方法
    public abstract void onError(String resultError);

    /**
     * handler处理回调
     *
     * @param msg
     */
    public void onDoError(final String msg) {

        if (mHandler == null) {
            onError(msg);
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onError(msg);
            }
        });
    }

    public void parse(final String json) {
        //返回的json为空 "" "{}" return
        if (TextUtils.isEmpty(json)) {
            onDoError(HTTPERROR);
            return;
        }
        /**
         * 对返回的json数据的处理
         */
        //        返回的是javabean
        if (t != String.class) {
            doJavaBean(json);
            return;
        }
        //httpReson String 类型的话,
        JSONObject js = null;
        try {
            js = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject state = null;
        try {
            if (js != null) {
                state = js.getJSONObject(RES_FIELD_STATE);
            } else {
                onDoError("json == mull");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //state 字段解析问题
        if (state == null) {
            onDoError("state == mull");
            return;
        }

        String code = null;
        try {
            code = state.getString(RES_FIELD_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String codesub = null;
        try {
            codesub = code.substring(0, 3);
        } catch (final Exception e) {
            onDoError("code.substring(0,3)" + e.toString());
        }
        //处理成功
        if (codesub.equals(RES_SUCCESS_CODE)) {
            /**  使用 JSONTokener 方式解析：遇到如下结构  解析出来的值 debug显示 "Cannot find local variable 'listArray'"  不会报错 代码不会再往下走
             {"state":{"code":2000000,"msg":"success"},"data":"https://ym.ibingli.cn/invite?ref=43huzs","id":"RequestTime_2018-08-03 10:12"}  */

            JSONArray dataArray = null;
            JSONObject dataObject = null;
            try {
                dataArray = js.getJSONArray(RES_FIELD_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                dataObject = js.getJSONObject(RES_FIELD_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dataArray == null && dataObject != null) {
                //                                对象type为0
//                                String dataObjectRet= "0"+dataObject.toString();
                final String dataObjectRet = StringUtils.explicitUseStringBuider(
                        "{\"", RES_FIELD_DATA, "\":", dataObject.toString(), ",\"", RES_CUS_FIELD_TYPE, "\":", RES_TYPE_OBJ, "}");
                onDoSuccess((T) dataObjectRet);
            } else if (dataArray != null && dataObject == null) {
                //数组type为1
//                                String dataArrayRet= "1"+dataArray.toString();
                final String dataArrayRet = StringUtils.explicitUseStringBuider(
                        "{\"", RES_FIELD_DATA, "\":", dataArray.toString(), ",\"", RES_CUS_FIELD_TYPE, "\":", RES_TYPE_ARRARY, "}");
                onDoSuccess((T) dataArrayRet);
            } else {
                //data有可能是空
                onDoSuccess((T) json);
            }
            return;
        }
        //处理失败
        try {
            JSONObject jsonObjectstate = new JSONObject(state.toString());
            final String msg = code + "!" + jsonObjectstate.getString("msg");
            onDoError(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception el) {
            el.printStackTrace();
        }
    }

    private void doJavaBean(String json) {
        //        返回的是javabean
        //tmp_t 就是我们根据网络请求后解析的结果,它的类型是有T 来决定"{"abc":"a"
        final T tmp_t = JsonUtils.Json2Object(json, t);
        //转换异常,
        if (tmp_t == null) {
            onDoError("tmp_t == mull");
        } else {
            onDoSuccess(tmp_t);
        }
    }
}
