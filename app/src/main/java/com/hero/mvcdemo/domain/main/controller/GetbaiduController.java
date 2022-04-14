package com.hero.mvcdemo.domain.main.controller;

import android.content.Context;

import com.hero.mvcdemo.base.BaseController;
import com.hero.mvcdemo.cons.IDiyMessage;
import com.hero.mvcdemo.tools.httprequest.HttpResponse;
import com.hero.mvcdemo.tools.httprequest.utils.HttpCommontUtils;

/**
 * 2、新建BaseController子类处理网络请求，已经在子线程中处理
 */
public class GetbaiduController extends BaseController {
    private static final String TAG = GetbaiduController.class.getSimpleName();

    public GetbaiduController(Context mContext) {
        super(mContext);
    }

    @Override
    public void handlerMessage(int action, Object... values) {
        HttpCommontUtils util = HttpCommontUtils.getInstance();
        switch (action) {
            case IDiyMessage.MAIN_GET_BAIDU_ACTION:
                // 获取参数     values[0]
                util.doGet("https://www.baidu.com", new HttpResponse<String>(String.class) {
                    @Override
                    public void onSuccess(String s) {
                        mListener.onModelChanged(IDiyMessage.MAIN_GET_BAIDU_ACTION_RESULT, s);
                    }

                    @Override
                    public void onError(String msg) {
                        mListener.onModelChanged(IDiyMessage.MAIN_GET_BAIDU_ACTION_ERROR, msg);
                    }
                });
                break;
            default:
        }
    }
}