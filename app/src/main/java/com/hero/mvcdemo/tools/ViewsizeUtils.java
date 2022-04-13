package com.hero.mvcdemo.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 设定控件大小工具类
 */
public final class ViewsizeUtils {

    /**
     * 设定父控件为LinearLayout 的控件大小
     */
    public static void viewSizeLin(Context context, View view, int width, int height) {
        view.setLayoutParams(new LinearLayout.LayoutParams(width, height));
    }

    /**
     * 设定父控件为RelativeLayout的控件大小
     */
    public static void viewSizeRet(View view, int width, int height) {
        view.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
    }

    /**
     * 设定父控件为ViewGroup的控件大小
     */
    public static void viewSizeGroup(View view, int width, int height) {
        view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
    }

    /**
     * 设定父控件为ViewGroup的控件大小
     */
    public static void viewSizeFra(Context context, View view, int width, int height) {
        view.setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }

    /**
     * 根据测量ui的宽高  和屏幕宽度   算出高度
     */
    public static int viewHeight(Context context, float width, float height) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int winWidth = dm.widthPixels;
        int viewHeight = (int) (height / width * winWidth);
        return viewHeight;
    }

    /**
     * 根据测量ui的图片的宽和屏幕的宽，和屏幕宽度来获取控件的宽 和屏幕宽度   算出宽度
     */
    public static int viewWidthtoScreen(Context context, float width, float widthW) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int winWidth = dm.widthPixels;
        int viewWidth = (int) (width / widthW * winWidth);
        return viewWidth;
    }

    /**
     * 获取控件宽度
     */
    public static int getViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth(); // 获取宽度
    }

    /**
     * 获取控件高度
     */
    public static int getViewHeight(final View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight(); // 获取高度

        //View 宽高动态变化时  需使用以下方法  不要删除
       /* ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            private int lastWidth, lastHeight;

            @Override
            public void onGlobalLayout() {
                lastHeight = view.getHeight();
                lastWidth = view.getWidth();
            }
        };
        view.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);*/
    }

    /**
     * 设置距离屏幕顶部
     */
    public static void setViewMarginTop(Context context, View view, int count) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = (int) (DensityUtils.getScreenH(context) / count);//距顶部的距离为屏幕高度的0.2倍
        view.setLayoutParams(params);
    }

    public static void setViewMarginLeftTop(Context context, View view, double count) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (DensityUtils.getScreenH(context) / count);//距顶部的距离为屏幕高度的0.2倍
        view.setLayoutParams(params);
    }

    /**
     * 设定父控件为RelativeLayout的控件大小 并设置相对父类在底部
     */
    public static void viewSizeRetAndButtom(Context context, View view, int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
    }

    public static void setMargins(View view, int l, int t, int r, int b) {
        if (view == null) {
            return;
        }

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(l, t, r, b);
            view.requestLayout();
        }
    }

    /**
     * 判断textview是否换行   方法无效   在内部类里面做判断操作可以成功
     */
   /* public static boolean TvOverFlowed(final TextView textView) {
        final List<String> overFlowed = new ArrayList<>();
        ViewTreeObserver vto = textView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                textView.getHeight();
                double w0 = textView.getWidth();//控件宽度
                double w1 = textView.getPaint().measureText(textView.getText().toString());//文本宽度
                if (w1 >= w0) {
                    overFlowed.add("");
                } else {
                    overFlowed.clear();
                }
            }
        });

        return overFlowed.size() > 0;
    }*/

}