package com.hero.mvcdemo.tools;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;

/**
 *  Drawable 工具类
 */
public class DrawableUtils {

    /**
     * 获取state_selected状态下的按钮图片
     * true：android.R.attr.state_selected
     * false：-android.R.attr.state_selected
     *
     * @param cxt      上下文的对象
     * @param normalID 正常图片
     * @param pressID  选中的图片
     * @return
     */
    public static StateListDrawable getSelectedDrawable(Context cxt, int normalID, int pressID) {
        if (null == cxt) {
            return new StateListDrawable();
        }

        Resources res = cxt.getResources();
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{R.attr.state_selected}, res.getDrawable(pressID));
        drawable.addState(new int[]{}, res.getDrawable(normalID));
        return drawable;
    }

    /**
     * 获取state_checked状态下的按钮图片
     *
     * @param cxt      上下文的对象
     * @param normalID 正常图片
     * @param pressID  选中的图片
     * @return
     */
    public static StateListDrawable getCheckedDrawable(Context cxt, int normalID, int pressID) {
        if (null == cxt) {
            return new StateListDrawable();
        }

        Resources res = cxt.getResources();
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{R.attr.state_checked}, res.getDrawable(pressID));
        drawable.addState(new int[]{}, res.getDrawable(normalID));
        return drawable;
    }

    /**
     * 设置在enable：true/false两种状态下不同的颜色
     *
     * @param cxt
     * @param normalID
     * @param pressID
     * @return
     */
    public static ColorStateList getEnabledColor(Context cxt, int normalID, int pressID) {
        if (null == cxt) {
            return new ColorStateList(new int[][]{{}, {}}, new int[]{});
        }

        int[][] states = new int[][]{
                {R.attr.state_enabled},//选中状态
                {}//默认状态
        };

        Resources res = cxt.getResources();
        int[] colors = new int[]{res.getColor(pressID), res.getColor(normalID)};
        return new ColorStateList(states, colors);
    }
}