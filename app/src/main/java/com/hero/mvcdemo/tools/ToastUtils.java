package com.hero.mvcdemo.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * 吐司相关工具类
 */
public final class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;

    /**
     * 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     * {@code true}: 弹出新吐司
     * {@code false}: 只修改文本内容
     * 如果为{@code false}的话可用来做显示任意时长的吐司
     * 推荐使用false
     *
     * @param isJumpWhenMore
     */
    public static void init(boolean isJumpWhenMore) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToastSafe(Context context,final CharSequence text) {
        if (sHandler == null) {
            return;
        }

        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToastSafe(Context context,final @StringRes int resId) {
        if (sHandler == null) {
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context,resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortToastSafe(Context context,final @StringRes int resId, final Object... args) {
        if (sHandler == null) {
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context,resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortToastSafe(final String format, final Object... args) {
        if (sHandler == null) {
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToastSafe(Context context, final CharSequence text) {
        if (sHandler == null) {
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToastSafe(Context context, final @StringRes int resId) {
        if (sHandler == null) {
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongToastSafe(Context context, final @StringRes int resId, final Object... args) {
        if (sHandler == null) {
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongToastSafe(final String format, final Object... args) {
        if (sHandler == null) {
            return;
        }
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToast(Context context, @StringRes int resId) {
        if (sHandler == null) {
            return;
        }
        showToast(context,resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortToast(Context context, @StringRes int resId, Object... args) {
        if (sHandler == null) {
            return;
        }
        showToast(context,resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortToast(String format, Object... args) {
        if (sHandler == null) {
            return;
        }
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToast(Context context, CharSequence text) {
        showToast(context,text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToast(Context context, @StringRes int resId) {
        showToast(context,resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongToast(Context context, @StringRes int resId, Object... args) {
        showToast(context,resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(Context context, @StringRes int resId, int duration) {
        CharSequence text = context.getResources().getText(resId);
        if (text == null) {
            return;
        }
        String str = text.toString();
        if (EmptyJudgeUtils.stringIsEmpty(str)) {
            return;
        }

        showToast(str, duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(Context context, @StringRes int resId, int duration, Object... args) {
        CharSequence text = context.getResources().getString(resId);
        if (text == null) {
            return;
        }
        String str = text.toString();
        if (EmptyJudgeUtils.stringIsEmpty(str)) {
            return;
        }

        showToast(String.format(str, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(String format, int duration, Object... args) {
        if (EmptyJudgeUtils.stringIsEmpty(format)) {
            return;
        }
        showToast(String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(Context context,CharSequence text, int duration) {
        if (isJumpWhenMore) cancelToast();
        if (sToast == null) {
            sToast = Toast.makeText(context, text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}