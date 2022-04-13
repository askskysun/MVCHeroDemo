package com.hero.mvcdemo.helper;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus帮助类
 */
public class EventBusHelper {

    /**
     * HermesEventBus注册与反注册
     *
     * @param register true为注册
     */
    public static void registerEventBus(Fragment frg, boolean register) {
        try {
            boolean curRegisterStatus = EventBus.getDefault().isRegistered(frg);
            if (!curRegisterStatus && register) {
                EventBus.getDefault().register(frg);//注册EventBus
            } else if (curRegisterStatus && !register) {
                EventBus.getDefault().unregister(frg);//注销EventBus
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public static void registerEventBus(Activity act, boolean register) {
        try {
            boolean curRegisterStatus = EventBus.getDefault().isRegistered(act);
            if (!curRegisterStatus && register) {
                EventBus.getDefault().register(act);//注册EventBus
            } else if (curRegisterStatus && !register) {
                EventBus.getDefault().unregister(act);//注销EventBus
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册单进程的EventBus
     *
     * @param subscriber 接收activity、fragment两种，传入当前需要订阅的界面，不能传mcontext、mActivity
     * @param register
     */
    public static void registerEventBus(Object subscriber, boolean register) {
        try {
            boolean curRegisterStatus = EventBus.getDefault().isRegistered(subscriber);
            if (!curRegisterStatus && register) {
                EventBus.getDefault().register(subscriber);//注册EventBus
            } else if (curRegisterStatus && !register) {
                EventBus.getDefault().unregister(subscriber);//注销EventBus
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }
}
