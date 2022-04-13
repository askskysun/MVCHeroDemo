package com.hero.mvcdemo.tools;

import java.util.regex.Pattern;

/**
 * <pre>
 *     正则表达式工具类
 * </pre>
 */
public class RegularUtils {
    private static final String REGEX_IP =
            "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    /**
     * 判断填入的IP地址是否正确
     *
     * @param strIP
     * @return
     */
    public static boolean isIPAddress(String strIP) {
        Pattern pattern = Pattern.compile(REGEX_IP);
        return pattern.matcher(strIP).matches();
    }
}