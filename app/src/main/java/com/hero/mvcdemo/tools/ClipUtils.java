package com.hero.mvcdemo.tools;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * 复制、粘贴到剪贴板工具类
 * Created by hyj on 2016/6/13.
 */
public class ClipUtils {

    /**
     * 复制文本到剪贴板
     *
     * @param context
     * @param copyText 复制文本
     * @param msg      提示信息
     */
    public static void copyText(Context context, String copyText, String msg) {
        copyText(context, copyText, msg, false);
    }

    /**
     * 复制文本到剪贴板
     *
     * @param context
     * @param copyText  复制文本
     * @param msg       提示信息
     * @param longToast 显示长Toast
     */
    public static void copyText(Context context, String copyText, String msg, boolean longToast) {
        copyText(context, copyText);

        if (longToast && !TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 复制内容到剪贴板，不弹Toast
     *
     * @param context  上下文
     * @param copyText 复制的文本
     */
    public static void copyText(Context context, String copyText) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(copyText);
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cm.getText().toString().trim();
    }
}
