package com.hero.mvcdemo.tools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统信息工具包
 * <p/>
 *
 * @author kymjs (https://github.com/kymjs)
 * @version 1.1
 */
@SuppressLint("SimpleDateFormat")
public final class SystemTool {
    private static final String TAG = SystemTool.class.getSimpleName();
    public static final String FORTIME_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORTIME_SS_FULL = "yyyyMMddhhmmss";
    public static final String FORTIME_MM = "yyyy-MM-dd HH:mm";

    /**
     * 指定格式返回当前系统时间
     */
    public static String FormatDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 返回当前系统时间(格式以HH:mm形式)
     */
    public static String getDataTime() {
        return FormatDataTime("yyyy-MM-dd HH:mm");
    }

    /**
     * 返回当前系统时间(格式以HH:mm形式)
     */
    public static String getTime() {
        return FormatDataTime("HH:mm");
    }

    /**
     * 获取手机IMEI码
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneIMEI(Context cxt) {
        try {
            TelephonyManager tm = (TelephonyManager) cxt
                    .getSystemService(Context.TELEPHONY_SERVICE);

            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error el) {
            el.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 17 则返回 17
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断当前系统版本是否在6.0及以上
     *
     * @return
     */
    public static boolean isHighMVersion() {
        return getSDKVersion() >= Build.VERSION_CODES.M;
    }

    /**
     * 获取系统版本
     *
     * @return 形如2.3.3
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 调用系统发送短信
     */
    public static void sendSMS(Context cxt, String smsBody) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        cxt.startActivity(intent);
    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null;// 网络是否连接
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否为wifi联网
     */
    public static boolean isWiFi(Context cxt) {
        try {
            ConnectivityManager cm = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
            // wifi的状态：ConnectivityManager.TYPE_WIFI
            // 3G的状态：ConnectivityManager.TYPE_MOBILE
            NetworkInfo.State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            return NetworkInfo.State.CONNECTED == state;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前Wifi的名字
     *
     * @param cxt
     * @return
     */
    public static String getConnectWifiSsid(Context cxt) {
        WifiManager wifiManager = (WifiManager) cxt.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID", wifiInfo.getSSID());
        return wifiInfo.getSSID().replace("\"", "");
    }

    /**
     * 判断是什么网络   返回的是枚举类型
     */
    public static NetworkTypeEnum getNetState(Context cxt) {
        try {
            ConnectivityManager connectMgr = (ConnectivityManager)cxt
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            //没有网络
            if (info == null) {
                return NetworkTypeEnum.UNKNOW;
            }
            //移动网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
//                    case NETWORK_TYPE_GPRS:
//                    case NETWORK_TYPE_EDGE:
//                    case NETWORK_TYPE_CDMA:
//                    case NETWORK_TYPE_1xRTT:
//                    case NETWORK_TYPE_IDEN:
//                        return NETWORK_CLASS_2_G;
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetworkTypeEnum.NEMO_2G;
//                    case NETWORK_TYPE_UMTS:
//                    case NETWORK_TYPE_EVDO_0:
//                    case NETWORK_TYPE_EVDO_A:
//                    case NETWORK_TYPE_HSDPA:
//                    case NETWORK_TYPE_HSUPA:
//                    case NETWORK_TYPE_HSPA:
//                    case NETWORK_TYPE_EVDO_B:
//                    case NETWORK_TYPE_EHRPD:
//                    case NETWORK_TYPE_HSPAP:
//                        return NETWORK_CLASS_3_G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetworkTypeEnum.NEMO_3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetworkTypeEnum.NEMO_4G;
                }
            } else {
                //wifi
                return NetworkTypeEnum.WIFI;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NetworkTypeEnum.UNKNOW;
    }


    /**
     * 隐藏系统键盘
     * <p/>
     * <br>
     * <b>警告</b> 必须是确定键盘显示时才能调用
     */
    public static void hideKeyBoard(Activity aty) {
        ((InputMethodManager) aty
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        aty.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断当前应用程序是否后台运行
     * TODO 前后台判断代码有问题，需要优化
     */
    public static boolean isBackground(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            String packageName = context.getPackageName();
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                    .getRunningAppProcesses();
            if (appProcesses == null)
                return false;

            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                // The name of the process that this object is associated with.
                if (appProcess.processName.equals(packageName)
                        && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断某个Activity 界面是否在前台
     *
     * @param act
     * @return
     */
    public static boolean isForeground(Activity act) {
        if (act == null) {
            return false;
        }

        return isForeground(act, act.getClass());
    }

    /**
     * 判断某个界面是否在前台
     *
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, Class... actClass) {
        if (actClass == null || actClass.length == 0) {
            return false;
        }
        for (int i = 0; i < actClass.length; i++) {
            Class actClass1 = actClass[i];

            if (actClass1 == null) {
                continue;
            }

            if (isForeground(context, actClass1.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String... className) {

        if (className == null || className.length == 0) {
            return false;
        }

        try {
            for (int i = 0; i < className.length; i++) {
                String className1 = className[i];
                if (TextUtils.isEmpty(className1)) {
                    continue;
                }

                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
                if (list != null && list.size() > 0) {
                    ComponentName cpn = list.get(0).topActivity;
                    if (className1.equals(cpn.getClassName()))
                        return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断手机是否处理睡眠
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;
    }

    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setData(Uri.fromFile(file));
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取当前应用程序的版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取当前应用程序的版本号
     */
    public static int getAppVersionCode(Context context) {
        int version = 0;
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(SystemTool.class.getName()
                    + "the application not found");
        }
        return version;
    }

    /**
     * 回到home，后台运行
     */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }

    /**
     * 获取应用签名
     *
     * @param context
     * @param pkgName
     */
    public static String getSign(Context context, String pkgName) {
        try {
            PackageInfo pis = context.getPackageManager().getPackageInfo(
                    pkgName, PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(SystemTool.class.getName() + "the "
                    + pkgName + "'s application not found");
        }
    }

    /**
     * 将签名字符串转换成需要的32位签名
     */
    private static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取设备的可用内存大小
     *
     * @param cxt 应用上下文对象context
     * @return 当前内存大小
     */
    public static int getDeviceUsableMemory(Context cxt) {
        try {
            ActivityManager am = (ActivityManager) cxt
                    .getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            // 返回当前系统的可用内存
            return (int) (mi.availMem / (1024 * 1024));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //    Android获取本应用内存占用的方法
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static int getMemory() {
        try {
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            // dalvikPrivateClean + nativePrivateClean + otherPrivateClean;
            int totalPrivateClean = memoryInfo.getTotalPrivateClean();
            // dalvikPrivateDirty + nativePrivateDirty + otherPrivateDirty;
            Log.v(TAG, "totalPrivateClean:" + totalPrivateClean);
            int totalPrivateDirty = memoryInfo.getTotalPrivateDirty();
            Log.v(TAG, "totalPrivateDirty:" + totalPrivateDirty);

            // dalvikPss + nativePss + otherPss;
            int totalPss = memoryInfo.getTotalPss();
            Log.v(TAG, "totalPss:" + totalPss);

            // dalvikSharedClean + nativeSharedClean + otherSharedClean;
            int totalSharedClean = memoryInfo.getTotalSharedClean();
            Log.v(TAG, "totalSharedClean:" + totalSharedClean);

            // dalvikSharedDirty + nativeSharedDirty + otherSharedDirty;
            int totalSharedDirty = memoryInfo.getTotalSharedDirty();
            Log.v(TAG, "totalSharedDirty:" + totalSharedDirty);

            // dalvikSwappablePss + nativeSwappablePss + otherSwappablePss;
            int totalSwappablePss = memoryInfo.getTotalSwappablePss();
            Log.v(TAG, "totalSwappablePss:" + totalSwappablePss);

            int total = totalPrivateClean + totalPrivateDirty + totalPss + totalSharedClean + totalSharedDirty + totalSwappablePss;
            Log.v(TAG, "total:" + total);

            return total;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 清理后台进程与服务
     *
     * @param cxt 应用上下文对象context
     * @return 被清理的数量
     */
    @SuppressLint("MissingPermission")
    public static int gc(Context cxt) {
        int count = 0; // 清理掉的进程数
        try {
            long i = getDeviceUsableMemory(cxt);
            count = 0;
            ActivityManager am = (ActivityManager) cxt
                    .getSystemService(Context.ACTIVITY_SERVICE);
            // 获取正在运行的service列表
            List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(100);
            if (serviceList != null)
                for (ActivityManager.RunningServiceInfo service : serviceList) {
                    if (service.pid == Process.myPid())
                        continue;
                    try {
                        Process.killProcess(service.pid);
                        count++;
                    } catch (Exception e) {
                        e.getStackTrace();
                        continue;
                    }
                }

            // 获取正在运行的进程列表
            List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
            if (processList != null)
                for (ActivityManager.RunningAppProcessInfo process : processList) {
                    // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                    // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                    if (process.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                        // pkgList 得到该进程下运行的包名
                        String[] pkgList = process.pkgList;
                        for (String pkgName : pkgList) {
                            try {
                                am.killBackgroundProcesses(pkgName);
                                count++;
                            } catch (Exception e) { // 防止意外发生
                                e.getStackTrace();
                                continue;
                            }
                        }
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /*
     * 获取字体大小，并返回
     * */
    public static int getFontSize(int size, int screenWidth, int screenHeight) {
        screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;

        int rate = (int) (size * (float) screenWidth / 320);
        //rate = rate / 2;

        return rate < 14 ? 14 : rate; //字体太小也不好看的
    }

    /**
     * 权限已被允许
     *
     * @param activitie
     * @param permissions
     * @return
     */
    public static boolean mayRequestPermissionReGet(Activity activitie, String[] permissions) {
        try {
            boolean mayPermission = false;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true;
            }

            for (String req : permissions) {
                if (activitie.checkSelfPermission(req) != PackageManager.PERMISSION_GRANTED) {
                    mayPermission = false;
                    break;
                } else {
                    mayPermission = true;
                }
            }

            if (mayPermission) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * 获取字体大小，并返回
     * */
    public static int getDalvikPss() {
        int dalvikPss = -1;
        try {
            //                Debug.MemoryInfo memoryInfo1 = new Debug.MemoryInfo();
//                Debug.getMemoryInfo(memoryInfo1);
//                Log.v(TAG,"Dalvik的PSS值为:" + memoryInfo1.dalvikPss);
//                Log.v(TAG,"程序的PSS的值为:"+memoryInfo1.getTotalPss());
//                if (Build.VERSION.SDK_INT >= 23) {
//                    Log.v(TAG,"我是安卓6.0以上才有的方法哦，获取java-heap:"+memoryInfo1.getMemoryStat("summary.java-heap"));
//                    Log.v(TAG,"我是安卓6.0以上才有的方法哦，获取total-pss:"+memoryInfo1.getMemoryStat("summary.total-pss"));
//                }
//                ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
//                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//                activityManager.getMemoryInfo(memoryInfo);
//                Log.v(TAG,"我的机器一共有:" + memoryInfo.totalMem + "内存");
//                Log.v(TAG,"其中可用的有:" + memoryInfo.availMem + "内存");
//                Log.v(TAG,"其中达到:" + memoryInfo.threshold + "就会有可能触发LMK，系统开始杀进程了");
//                Log.v(TAG,"所以现在的状态是:" + memoryInfo.lowMemory );
//                SystemTool.getMemory();
            Debug.MemoryInfo memoryInfo1 = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo1);
            dalvikPss = memoryInfo1.dalvikPss;
            Log.v(TAG, "Dalvik的PSS值为:" + dalvikPss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dalvikPss;
    }

    /**
     * 获取当前方法名
     * \r   window系统
     * \n   linix系统
     *
     * @return
     */
    //层次分隔
    public static final String DIVIDE_LEVEL = " ;" + "\n";
    //  类 方法  行数分隔
    private static final String DIVIDE_PLACE = " , ";
    //最多打印三层
    private static final int LEVEL = 8;

    public static String getCurrentMethodName() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String methodName = "  methodName";
        for (int i = 0; i < stacks.length; i++) {
            int i1 = i + 1;
            if (stacks.length - 1 < i1) {
                break;
            }
            //处理方法层次
            if (i1 > 1) {
                methodName += (DIVIDE_PLACE + stacks[i1].getMethodName());
            } else {
                methodName = stacks[i1].getMethodName();
            }
            //限定多少层
            if (i >= LEVEL) {
                break;
            }
        }
        return methodName;
    }

    /**
     * 获取当前调用代码  信息
     *
     * @return
     */
    /**
     * @param classIgnore
     * @param lever
     * @return
     */
    public static String getCurrentCodeMsg(Context context, Class classIgnore, int lever) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String codeMsg = "  codeMsg : ";
        codeMsg += getCurProcessName(context) + "    ";
        try {
            int ignoreLever = getIgnoreLever(classIgnore);
            //最后一层
            for (int i = 0; i < stacks.length; i++) {
                //开始记录的层次
                int startLever = ignoreLever + i;
                if (stacks.length - 1 < startLever) {
                    break;
                }
                StackTraceElement stack = stacks[startLever];
                String codeMsgSingle = StringUtils.explicitUseStringBuider(stack.getFileName()
                        , DIVIDE_PLACE, stack.getClassName()
                        , DIVIDE_PLACE, stack.getMethodName()
                        , DIVIDE_PLACE, stack.getLineNumber() + "");
                //处理方法层次
                codeMsg += codeMsgSingle + ((i == lever) ? "" : DIVIDE_LEVEL);
                //限定多少层
                if (i >= lever) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeMsg;
    }

    /**
     * 找到忽略的类的层次
     *
     * @param classIgnore
     * @return <pre>
     *     打印的代码示例
     * 2019-06-20 14:33:16:336   LOG_TAG_DEBUG_THIRDLIB_ILVLIVE     codeMsg : cn.ibingli.test    BaseLogWriteUtils.java , cn.ibingli.widget.logger.logfile.base.BaseLogWriteUtils , ioDoLoggerRecord , 148 ;
     * BaseLogWriteUtils.java , cn.ibingli.widget.logger.logfile.base.BaseLogWriteUtils , baseDoLoggerRecord , 139 ;
     * BaseLogWriteUtils.java , cn.ibingli.widget.logger.logfile.base.BaseLogWriteUtils , doLoggerDirRecord , 102 ;
     * LogDefauseWriteUtils.java , cn.ibingli.widget.logger.logfile.logwrite.LogDefauseWriteUtils , doLoggerRecord , 47 ;
     * LogDefauseWriteUtils.java , cn.ibingli.widget.logger.logfile.logwrite.LogDefauseWriteUtils , doLoggerRecord , 29 ;
     * LogDefauseWriteUtils.java , cn.ibingli.widget.logger.logfile.logwrite.LogDefauseWriteUtils , doRecordLog , 111 ;
     * ILiveUtils.java , cn.ibingli.widget.ilive.ILiveUtils , login , 81 ;
     * ILiveRoomUtils.java , cn.ibingli.widget.ilive.ILiveRoomUtils , initILive , 49 ;
     * IBingLiApplcation.java , cn.ibingli.IBingLiApplcation , onCreate , 88 ;
     * Instrumentation.java , android.app.Instrumentation , callApplicationOnCreate , 1011 ;
     * </pre>
     */
    private static int getIgnoreLever(Class classIgnore) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        int lever = 0;    //忽略类的层次
        try {
            for (int i = 0; i < stacks.length; i++) {
                StackTraceElement stack = stacks[i];
                //忽略的层次  stack.getClassName() 获取的是带文件后缀      classIgnore.getSimpleName() 不带后缀
                if (stack.getClassName().contains(classIgnore.getSimpleName())) {
                    lever = i;
                    //必须等此类方法调用完
                } else {
                    if (lever != 0) {
                        return lever;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lever;
    }

    /**
     * 记录类和方法层次
     *
     * @param codeMsg
     * @param finalLever
     * @param i
     * @param codeMsgSingle
     * @return
     */
    private static boolean getCodeString(String codeMsg, int finalLever, int i, String codeMsgSingle) {
        //处理方法层次
        codeMsg += codeMsgSingle + ((i == finalLever) ? "" : DIVIDE_LEVEL);
        //限定多少层
        if (i >= finalLever) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前类名
     * level 底几层   直接调用 用 1
     *
     * @return https://blog.csdn.net/supercyclone/article/details/18599647
     */
    public static String getCurrentClassName(final int level) {
        String clazzName = "TAG";
        try {
            clazzName = new Throwable().getStackTrace()[level].getClassName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazzName;
    }

    /**
     * 获取当前进程
     *
     * @return
     */
    public static String getCurProcessName(Context context) {
        try {
            int pid = Process.myPid();
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    return process.processName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取CPU型号
     *
     * @return
     */
    public static String getCpuName() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Android获取手机cpu架构，支持的指令集
     */
    public static String getCpuAbi() {
        StringBuilder abiStr = new StringBuilder();
        try {
            String[] abis = new String[]{};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                abis = Build.SUPPORTED_ABIS;
            } else {
                abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            }
            for (String abi : abis) {
                abiStr.append(abi);
                abiStr.append(',');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abiStr.toString();
    }

    //检测进程是否是主进程
    public static boolean shouldInit(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            String mainProcessName = context.getPackageName();
            int myPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfos) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            //java.lang.NullPointerException
//            Attempt to read from field 'java.lang.String com.android.server.am.ActivityRecord.packageName' on a null object reference
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 显示与隐藏状态栏
     * baseActivity 中设置了状态栏颜色  需要再修改
     *
     * @param enable
     */
    public static void fullscreen(Activity activity, boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(lp);
        } else {
            WindowManager.LayoutParams attr = activity.getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attr);
        }
    }

    /**
     * 获取所有Activity 无序
     * @return
     */
    public static List<Activity> getAllActivitys() {
        List<Activity> list = new ArrayList<>();
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            //获取主线程对象
            Object activityThreadObject = currentActivityThread.invoke(null);
            Field mActivitiesField = activityThread.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Map<Object, Object> mActivities = (Map<Object, Object>) mActivitiesField.get(activityThreadObject);
            for (Map.Entry<Object, Object> entry : mActivities.entrySet()) {
                Object value = entry.getValue();
                Class<?> activityClientRecordClass = value.getClass();
                Field activityField = activityClientRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Object o = activityField.get(value);
                list.add((Activity) o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public enum NetworkTypeEnum {
        UNKNOW(0, "未知"),
        WIFI(1, "WIFI"),
        HOT_NEMO(2, "移动热点"),
        NEMO_2G(3, "2G网络"),
        NEMO_3G(4, "3G网络"),
        NEMO_4G(5, "4G网络"),
        NEMO_5G(6, "5G网络");
        private int code;//代码
        private String name;//描述

        NetworkTypeEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 通过key获得类型
         *
         * @param code
         * @return
         */
        public static NetworkTypeEnum getEnum(String code) {
            if (null == code) {
                return null;
            }

            NetworkTypeEnum[] types = NetworkTypeEnum.values();
            for (NetworkTypeEnum typeEnum : types) {
                if (code.equals(typeEnum.getCode())) {
                    return typeEnum;
                }
            }
            return null;
        }
    }
}