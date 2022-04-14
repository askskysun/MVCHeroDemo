package com.hero.mvcdemo;


import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.hero.mvcdemo.base.BaseActivity;
import com.hero.mvcdemo.cons.IDiyMessage;
import com.hero.mvcdemo.domain.main.controller.GetbaiduController;

import butterknife.BindView;

/**
 * 网络请求步骤
 * 1、在IDiyMessage设定消息id；
 * 2、新建BaseController子类处理网络请求，已经在子线程中处理；
 * 3、Activiy中初始化Controller
 * 4、发送网络/其他耗时请求处理消息
 * 5、重写handlerMessage(Message msg)，处理回调，此时已经在主线程
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.getbaidu)
    TextView mGetbaidu;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activiy中初始化Controller
        initController(new GetbaiduController(this));
        initUI();
    }

    @Override
    protected void initUI() {
        mGetbaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送网络/其他耗时请求处理消息
                mController.sendAsyncMessage(IDiyMessage.MAIN_GET_BAIDU_ACTION, "参数");
            }
        });
    }

    /**
     * 5、重写handlerMessage(Message msg)，处理回调，此时已经在主线程
     * @param msg
     */
    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);
        switch (msg.what) {
            case IDiyMessage.MAIN_GET_BAIDU_ACTION_RESULT:
                handlegetBaiduResult(msg.obj);
                break;
            case IDiyMessage.MAIN_GET_BAIDU_ACTION_ERROR:
                handlegetBaiduError(msg.obj);
                break;
            default:
        }
    }

    private void handlegetBaiduResult(Object obj) {
        mGetbaidu.setText(obj.toString());
    }

    private void handlegetBaiduError(Object obj) {
        mGetbaidu.setText(obj.toString());
    }
}
/**
 待优化：
 1、线程使用线程池处理
 */