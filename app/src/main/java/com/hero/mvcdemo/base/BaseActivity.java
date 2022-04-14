package com.hero.mvcdemo.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.hero.mvcdemo.cons.OtherConfig;
import com.hero.mvcdemo.helper.EventBusHelper;
import com.hero.mvcdemo.interfaces.BaseModelChangedListener;
import com.hero.mvcdemo.tools.HandlerUtils;
import com.hero.mvcdemo.tools.ToastUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity implements BaseModelChangedListener, HandlerUtils.OnReceiveMessageListener {

    /***
     * EventBus 注册配置
     */
    //不注册  默认
    protected static final int EVENTBUS_SETTING_NULL = 0;
    //在onCreate中注册
    protected static final int EVENTBUS_SETTING_CREATE = 1;
    //在onStart中注册
    protected static final int EVENTBUS_SETTING_START = 2;

    protected BaseController mController;
    protected BaseFragment currentBaseFragment;
    protected BaseActivity self;
    protected TextView textView;
    protected HandlerUtils.HandlerHolder handlerHolder;
    protected String TAG = "";

    /**
     * 初始化ui
     */
    protected abstract void initUI();

    /**
     * 初始化Controller
     */
    protected void initController(BaseController mController) {
        if (mController == null) {
            return;
        }

        this.mController = mController;
        this.mController.setListener(this);
    }

    /**
     * 绑定布局
     *
     * @return
     */
    public abstract int bindLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //需要在4.4版本以上执行
        if (Build.VERSION.SDK_INT > 18) {
            Window window = getWindow();
//设置StatusBar为透明显示,需要在setContentView之前完成操作
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(bindLayout());
        ButterKnife.bind(this);
        setStatusBarAfter();
        //false 连续点击不重新创建对象，显示时间延长 ；true 连续点击重新创建对象
        ToastUtils.init(false);//推荐false
        //handler
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        TAG = this.getClass().getSimpleName();
        self = this;

        if (getEventBusSetting() == EVENTBUS_SETTING_CREATE) {
            EventBusHelper.registerEventBus(this, true);
        }

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getEventBusSetting() == EVENTBUS_SETTING_START) {
            EventBusHelper.registerEventBus(this, true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getEventBusSetting() == EVENTBUS_SETTING_START) {
            EventBusHelper.registerEventBus(this, false);
        }
    }

    @Override
    protected void onDestroy() {
        handlerHolder.removeCallbacksAndMessages(null);
        ToastUtils.cancelToast();

        if (getEventBusSetting() == EVENTBUS_SETTING_CREATE) {
            EventBusHelper.registerEventBus(this, false);
        }
        super.onDestroy();
    }

    /**
     * 配置EventBus注册
     *
     * @return
     */
    protected int getEventBusSetting() {
        return EVENTBUS_SETTING_NULL;
    }

    /**
     * 数据处理后的回调
     */
    @Override
    public void onModelChanged(int action, Object... values) {
        handlerHolder.obtainMessage(action, values[0]).sendToTarget();
    }

    @Override
    public void handlerMessage(Message msg) {

    }

    public void setStatusBarAfter() {
        // 创建TextView用于叠加StatusBar的颜色块
        textView = new TextView(this);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        textView.setBackgroundColor(Color.parseColor(OtherConfig.STATUBAR));
        textView.setLayoutParams(lParams);
        // 获得根视图并把TextView加进去。
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.addView(textView);
    }

    public void setStatusBarAfter(String colorString) {
        // 创建TextView用于叠加StatusBar的颜色块
        textView = new TextView(this);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        textView.setBackgroundColor(Color.parseColor(colorString));
        textView.setLayoutParams(lParams);
        // 获得根视图并把TextView加进去。
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.addView(textView);
    }

    /**
     * 通过反射机制获取手机状态栏高度
     */
    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 用途：用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int resView, BaseFragment targetFragment) {
        if (targetFragment.equals(currentBaseFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
//这种方式 fragment的scroller  划不动
//			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//			transaction.replace(resView, targetFragment);
        }

        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            targetFragment.onChange();
        }
        if (currentBaseFragment != null && currentBaseFragment.isVisible()) {
            transaction.hide(currentBaseFragment);
        }

        currentBaseFragment = targetFragment;
        transaction.commit();
    }
}
