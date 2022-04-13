package com.hero.mvcdemo.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.hero.mvcdemo.R;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ActivityUtil {
    /**
     * 判断某一个类是否存在任务栈里面
     *
     * @return
     */

    //判断某一个类是否存在任务栈里面
    public static boolean isExistMainActivity(Context context, Class<?> activity) {
        boolean flag = false;
        if (context == null || activity == null) {
            return flag;
        }
        try {
            Intent intent = new Intent(context, activity);
            ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
            flag = false;
            if (cmpName != null) { // 说明系统中存在这个activity
                ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);  //获取从栈顶开始往下查找的10个activity
                for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                    if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                        flag = true;
                        break;  //跳出循环，优化效率
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void start(Context c, Class clazz, boolean flag) {
        if (c == null || clazz == null) {
            return;
        }

        Intent intent = new Intent(c, clazz);
        ((Activity) c).startActivity(intent);
        if (flag) {
            ((Activity) c).finish();
        }
    }

    /**
     * 打开新的Activity
     **/
    public static void changeActivity(Context context, Class<?> cls, Bundle bundle) {
        if (context == null || cls == null) {
            return;
        }

        Intent addAccountIntent = new Intent();
        //addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        if (bundle != null)
            addAccountIntent.putExtras(bundle);
        addAccountIntent.setClass(context, cls);
        context.startActivity(addAccountIntent);
    }

    /**
     * 通过动画打开新的Activty
     **/
    public static void changeActivityByAnimation(Activity activity, Class<?> cls, int enterAnim, int exitAnim, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        changeActivity(activity, cls, bundle);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 通过动画打开新的Activty
     **/
    public static void changeActivityByAnimation(Activity activity, Class<?> cls, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        changeActivity(activity, cls, bundle);
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    /**
     * 打开一个有返回值的Activity
     **/
    public static void changActivityForResult(Activity activity, Class<?> cls, int resule_code, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }

        Intent addAccountIntent = new Intent();
        addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);

        if (bundle != null)
            addAccountIntent.putExtras(bundle);

        addAccountIntent.setClass(activity, cls);
        activity.startActivityForResult(addAccountIntent, resule_code);
    }

    /**
     * 通过动画打开有返回值的Activity
     **/
    public static void changActivityForResultByAnimation(Activity activity, Class<?> cls, int enterAnim, int exitAnim, int resule_code, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        Intent addAccountIntent = new Intent();
        addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);

        if (bundle != null)
            addAccountIntent.putExtras(bundle);

        addAccountIntent.setClass(activity, cls);
        activity.startActivityForResult(addAccountIntent, resule_code);

        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 通过动画打开有返回值的Activity
     **/
    public static void changActivityForResultByAnimation(Activity activity, Class<?> cls, int resule_code, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        Intent addAccountIntent = new Intent();
        addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);

        if (bundle != null)
            addAccountIntent.putExtras(bundle);
        addAccountIntent.setClass(activity, cls);
        activity.startActivityForResult(addAccountIntent, resule_code);
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    /**
     * 打开新的Activity后结束本身
     **/
    public static void changeActivityAndFinish(Activity activity, Class<?> cls, Bundle bundle) {
        if (activity == null) {
            return;
        }
        changeActivity(activity, cls, bundle);
        activity.finish();
    }

    /**
     * 动画结束本身activity
     **/
    public static void finishActivitySelf(Activity activity) {
        if (activity == null) {
            return;
        }
        activity.finish();
        activity.overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    /**
     * 通过动画打开新的Activity后结束本身
     **/
    public static void changeActivityByAnimationAndFinish(Activity activity, Class<?> cls, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        changeActivityByAnimation(activity, cls, R.anim.right_in, R.anim.left_out, bundle);
        activity.finish();
    }

    /**
     * 给控件设置点击事件，跳转到Acivity ，传递bundle  及参数
     **/
    public static void onclickToActivity(final Context context, View view, final Class<?> cls, final Bundle bundle) {
        if (context == null || cls == null || view == null) {
            return;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.changeActivityByAnimation((Activity)
                        context, cls, R.anim.right_in, R.anim.left_out, bundle);
            }
        });
    }

    public interface OnClickToActivityListener {
        void onClickToActivity();
    }

    public static void onclickToActivity(final Context context, View view, final Class<?> cls, final Bundle bundle, final OnClickToActivityListener onClickToActivityListener) {
        if (context == null || cls == null || view == null) {
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickToActivityListener != null) {
                    onClickToActivityListener.onClickToActivity();
                }
                ActivityUtil.changeActivityByAnimation((Activity)
                        context, cls, R.anim.right_in, R.anim.left_out, bundle);
            }
        });
    }

    /**
     * 清空任务栈再通过动画打开新的Activity
     **/
    public static void changeActivityEmptyByAnimation(Activity activity, Class<?> cls, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        Intent intent = new Intent(activity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
    /**
     * 清空任务栈再通过动画打开新的Activity
     **/
    public static void changeActivityEmptyFinshSelf(Activity activity, Class<?> cls, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        Intent intent = new Intent(activity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.left_out, R.anim.right_in);
    }
    public static void setResultfinishActivitySelf(Activity activity, int resultCode, Bundle bundle) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        // 设置结果，并进行传送
        activity.setResult(resultCode, intent);
        ActivityUtil.finishActivitySelf(activity);
    }

    /**
     * 用于关闭Activity时，判断是否有MainActivity , 没有的话创建一个   主要场景是系统推送的跳转
     *
     * @param activity
     * @param finishSelf 如果已经有  MainActivity  是否要关闭自己
     */
    /*public static void goBackToMainActivity(Activity activity, boolean finishSelf) {
        if (activity == null) {
            return;
        }
        if (!ActivityUtil.isExistMainActivity(activity, MainActivity.class)) {
            //刷新时间线
            PrefercenceCaseBean.setTimelyRefresh();
            ActivityUtil.changeActivityEmptyByAnimation(activity, MainActivity.class, null);
            return;
        }
        if (finishSelf) {
            activity.finish();
        }
    }*/

    /**
     * 判断 Activity 是否是竖屏
     * @param activity
     * @return
     */
    public static boolean isActivityVertical(Activity activity) {
        if (activity == null) {
            return false;
        }
        int mCurrentOrientation = activity.getResources().getConfiguration().orientation;
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
           return true;
        }
        return false;
    }
}
