package com.hero.mvcdemo.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Field;

/**
 * 系统屏幕的一些操作<br>
 */
public final class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取dialog宽度
     */
    public static int getDialogW(Context aty) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels - 100;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return w;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenW(Context aty) {
        Resources resources = aty.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int winWidth = dm.widthPixels;
        return winWidth;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenH(Context aty) {
        if (aty == null) {
            return -1;
        }
        Resources resources = aty.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int winHeight = dm.heightPixels;
        return winHeight;
    }

    /**
     * 获取在整个屏幕内的绝对坐标y值，含statusBar
     */
    public static int getLocationOnScreenY(Context aty, View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location); //获取在整个屏幕内的绝对坐标，含statusBar
        return location[1];
    }

    /**
     * 获取在整个屏幕内的绝对坐标x值，含statusBar
     */
    public static int getLocationOnScreenX(Context aty, View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location); //获取在整个屏幕内的绝对坐标，含statusBar
        return location[0];
    }

    /**
     * 获得状态栏的高度   失败
     *
     * @param context
     * @return
     */
    // 通过反射机制获取手机状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getStatusBarHeight(View view) {
        try {
            Rect outRect = new Rect();
            view.getWindowVisibleDisplayFrame(outRect);
            return outRect.top;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}