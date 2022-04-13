package com.hero.mvcdemo.domain.main.controller;

import  android.content.Context;

import com.hero.mvcdemo.base.BaseController;
import com.hero.mvcdemo.cons.IDiyMessage;
import com.hero.mvcdemo.tools.httprequest.HttpResponse;
import com.hero.mvcdemo.tools.httprequest.utils.HttpCommontUtils;

import java.util.HashMap;

/**
 *
 */
public class DemoLoginController extends BaseController {
    private static final String TAG = DemoLoginController.class.getSimpleName();

    public DemoLoginController(Context mContext) {
            super(mContext);
        }

        @Override
        public void handlerMessage(int action, Object... values) {
            HttpCommontUtils util = HttpCommontUtils.getInstance();
            switch (action) {
                case IDiyMessage.DEMO_LOGIN_GET_ACTION:
                    util.doGet("https://www.sina.com.cn", new HttpResponse<String>(String.class) {
                        @Override
                        public void onSuccess(String s) {
                            mListener.onModelChanged(IDiyMessage.DEMO_LOGIN_GET_ACTION_RESULT, s);
                        }

                        @Override
                        public void onError(String msg) {
                            mListener.onModelChanged(IDiyMessage.DEMO_LOGIN_GET_ACTION_ERROR, msg);
                        }
                    });
                    break;
                default:
            }
        }
    }