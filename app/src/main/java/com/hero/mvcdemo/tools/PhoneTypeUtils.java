package com.hero.mvcdemo.tools;

import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 手机系统 工具类
 */

public class PhoneTypeUtils {
    private static final String TAG = PhoneTypeUtils.class.getSimpleName();

    /**
     * 检查手机类型   硬件厂商
     */
    public static String getPhoneType() {
        try {
            String manufacturer = Build.MANUFACTURER;
            if (!EmptyJudgeUtils.stringIsEmpty(manufacturer)) {
                return manufacturer;
            }else {
                return "X";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "X";
        }
    }

    /**
     *  判断是否root
     * @return
     */
    public static boolean isDeviceRooted() {
        try {
            return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }


    private static boolean checkRootMethod2() {
        return new File("/system/app/Superuser.apk").exists();
    }


    private static boolean checkRootMethod3() {
        String[] paths = { "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su" };
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }


    private static boolean checkRootMethod4() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}
