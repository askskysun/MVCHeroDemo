package com.hero.mvcdemo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 *
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity parent;
    protected View fragmentRootView;
    public BaseController mController;

    /**
     * 设置自定义layout，并返回该view
     **/
    protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle);

    /**
     * fragment生成时主动调用
     * 用于初始化布局组件及子组件
     */
    public void initWidget() {
    }
    /**
     * 初始化Controller
     */
    public void initController(){

    }

    /**
     * 当通过changeFragment()显示时会被调用(类似于onResume)
     */
    public void onChange() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = (BaseActivity) getActivity();
        fragmentRootView = inflaterView(inflater, container, savedInstanceState);
        initWidget();
        return fragmentRootView;
    }
}
