package com.hero.mvcdemo.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * 操作控件内容相关
 */
public final class ViewSetConUtils {

    /**
     * 设置ImageView图片
     * params  R.drawable.home_btn_follow_pressed3x
     * 直接使用setImageResource方法，最终都是通过java层的createBitmap来完成的，需要消耗更多内存
     * BitmapFactory.decodeStream方法 无需再使用java层的createBitmap
     * 使用glide 加载出来 控件会错乱
     */
    public static void viewSetImage(Context context,ImageView view, int resId) {
        if (view == null || context == null) {
            return;
        }
        try {
            view.setImageBitmap(readBitMap(context, resId));
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error er) {
            er.printStackTrace();
        }
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 设置TextView字体颜色
     * params  R.color.black
     */
    public static void viewSetTextColor(TextView view, Context context, int resId) {
        if (view == null || context == null) {
            return;
        }
        view.setTextColor(context.getResources().getColor(resId));
    }

    /**
     * 设置控件背景颜色
     * 根据系统
     * params  Color.RED    不是系统色 会变
     * Color.parseColor("#000000")   或者用这个   一定要6位
     */
    public static void viewSetBackColor(View view, int color) {
        if (view == null) {
            return;
        }
        view.setBackgroundColor(color);
    }

    /**
     * 设置控件背景颜色
     * @param view
     * @param color  R.color.toobar 的形式
     */
    public static void viewSetBackGroundColor(Context context,View view, int color) {
        if (view == null) {
            return;
        }
        view.setBackground(new ColorDrawable(Color.parseColor(context.getResources().getString(color))));
    }

    /**
     * 设置控件背景颜色
     * 根据shape
     * params R.drawable.shape_home_hasconclusion
     */
    public static void viewSetBackColorShape(View view, int shapeId) {
        if (view == null) {
            return;
        }
        view.setBackgroundResource(shapeId);
    }

    /**
     *  设置控件背景颜色
     *  根据颜色值
     *  params  “#D8FE54”
     *//*
    public static void viewSetBackColorparse(View view,String colorString) {
        GradientDrawable myGrad = (GradientDrawable)view.getBackground();
        myGrad.setColor(Color.parseColor (colorString));
        view.setBackgroundDrawable(myGrad);
    }*/

    /**
     * 判断TextView字体是否是某种颜色
     */
    public static boolean textIsColor(TextView view, Context context, int resId) {
        if (view == null) {
            return false;
        }
        return view.getCurrentTextColor() == context.getResources().getColor(resId);
    }

    /**
     * 给View设置背景
     * R.drawable.start_rank_bg1
     */
    public static void viewSetBackgroung(View view, int resId) {
        view.setBackgroundResource(resId);
    }

    /**
     * 给TextView设置文字
     */
    public static void textViewSetText(TextView view, String content, String replace) {
        if (view == null) {
            return;
        }
        if (!EmptyJudgeUtils.stringIsEmpty(content)) {
            view.setText(replaceLine(content));
        } else if (!EmptyJudgeUtils.stringIsEmpty(replace)) {
            view.setText(replaceLine(replace));
        } else {
            view.setText("");
        }
    }

    //默认用空字符串代替
    public static void textViewSetText(TextView view, String content) {
        if (view == null) {
            return;
        }
        view.setText(EmptyJudgeUtils.stringIsEmpty(content) ? "" : replaceLine(content));
    }

    public static void textViewSetText(TextView view, String content, EmptyJudgeUtils.EnumStringReplaceType replaceType) {
        if (view == null) {
            return;
        }
        if (!EmptyJudgeUtils.stringIsEmpty(content)) {
            if (EmptyJudgeUtils.EnumStringReplaceType.REPLACETYPE_LINEFREE.equals(replaceType)) {
                view.setText(replaceLine(content));
            } else if (EmptyJudgeUtils.EnumStringReplaceType.REPLACETYPE_NONESTRING.equals(replaceType)) {
                view.setText(content.replace("\\n", ""));
            } else {
                view.setText(content);
            }
        } else {
            view.setText("");
        }
    }

    /**
     * 如果内容为空  不显示
     *
     * @param view
     * @param content
     */
    public static void textViewSetTextShow(TextView view, String content) {
        if (view == null) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            UiUtils.setViewShowState(view, View.GONE);
            return;
        }
        UiUtils.setViewShowState(view, View.VISIBLE);
        view.setText(replaceLine(content));
    }

    public static void textViewSetTextAndColor(TextView view, String content, String color) {
        if (view == null) {
            return;
        }
        if (!EmptyJudgeUtils.stringIsEmpty(content)) {
            view.setText(Html.fromHtml("<font color='" + color + "\'>" + replaceLine(content) + "</font>"));
        } else {
            view.setText("");
        }
    }

    public static void textViewSetText(TextView view, String content, String after, String replace) {
        if (view == null) {
            return;
        }
        if (!EmptyJudgeUtils.stringIsEmpty(content)) {
            view.setText(content + after);
        } else {
            view.setText(replace + after + "");
        }
    }

    public static String replaceLine(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String replace = str.replace("\\n", "\n");
        return replace;
    }
}