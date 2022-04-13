package com.hero.mvcdemo.tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 *    重新加载
 */

public class LoginoutedDialogUtiles {
  //当前客户端已被登出
 /*   public static void loginoutedDialogShow(final Context context){
        PrefercenceAppConfigBean.setLoginState(PrefercenceAppConfigBean.APP_STATE_LOGINOUT, false);
        LoginoutedDialog instance = LoginoutedDialog.getInstance(context);
        instance.setMessage("您的账号在别的地方登陆了");
        instance.setYesOnclickListener("重新登录", new OnYesOnclickListener() {
            @Override
            public void onYesClick() {
                ActivityUtil.changeActivityEmptyByAnimation((Activity) context, LoginOrRegisterActivity.class, null);
//                instance.dismiss();
            }
        });
        instance.setCancelable(false);
        instance.show();
    }*/
    //网络请求失败  重新加载    单选弹出框
   /* public static void loginoutedDialogShow(final Activity activity , String msgTip, String msgClick , boolean keyBackfinishActivity ,OnYesOnclickListener listener ){
        LoginoutedDialog instance = LoginoutedDialog.getInstance(activity);
        if (!EmptyJudgeUtils.stringIsEmpty(msgTip)) {
            instance.setMessage(msgTip);
        }
        if (!EmptyJudgeUtils.stringIsEmpty(msgClick)) {
            instance.setYesOnclickListener(msgClick,listener);
        }
        if (keyBackfinishActivity) {
            instance.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dialog.dismiss();
                        activity.finish();
                        //此处把dialog dismiss掉，然后把本身的activity finish掉
                        //   BarcodeActivity.this.finish();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        instance.show();
    }

    public static void loginoutedDialogDismiss(final Context context){
        LoginoutedDialog instance = LoginoutedDialog.getInstance(context);
        instance.dismiss();
    }*/

}
