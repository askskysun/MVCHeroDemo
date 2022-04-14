package com.hero.mvcdemo;


import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.hero.mvcdemo.base.BaseActivity;
import com.hero.mvcdemo.cons.IDiyMessage;
import com.hero.mvcdemo.domain.main.controller.GetbaiduController;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网络请求步骤
 * 1、在IDiyMessage设定消息id；
 * 2、新建BaseController子类处理网络请求，已经在子线程中处理；
 * 3、Activiy中初始化Controller
 * 4、发送网络/其他耗时请求处理消息
 * 5、重写handlerMessage(Message msg)，处理回调，此时已经在主线程
 */
public class MainActivity extends BaseActivity {

    /******************** 静态变量/常量 *****************************/
    /******************** UI控件 *****************************/
    @BindView(R.id.getbaidu)
    TextView mGetbaidu;

    /******************** 其他变量 *****************************/

    /******************** 生命周期方法 ********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activiy中初始化Controller
        initController(new GetbaiduController(this));
        initUI();
    }

    /******************** 父类/实现方法 ********************************************/
    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {

    }

    /******************** 普通方法 ***************************************************/

    /******************** handler方法 ***************************************************/
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

    /**************************** 点击事件 *******************************************************************************************/
    @OnClick(R.id.getbaidu)
    public void getbaiduClick(View v) {
        //发送网络/其他耗时请求处理消息
        mController.sendAsyncMessage(IDiyMessage.MAIN_GET_BAIDU_ACTION, "参数");
    }

    /******************** 内部类 *****************************/
}
/**
 待优化：
 1、线程使用线程池处理
 */

/******************** 静态变量/常量 *********************************************************************************/
/******************** UI控件 **************************************************************************************/
/******************** 其他变量 *************************************************************************************/
/******************** 生命周期方法 **********************************************************************************/
/******************** 父类/实现方法 *********************************************************************************/
/******************** 普通方法 *************************************************************************************/
/******************** handler方法 *********************************************************************************/
/******************** 点击事件 *************************************************************************************/
/******************** 内部类 **************************************************************************************/
