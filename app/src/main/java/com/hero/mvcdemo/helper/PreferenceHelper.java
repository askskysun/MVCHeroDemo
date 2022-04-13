package com.hero.mvcdemo.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * SharedPreferences 使用的帮助类     传的值为空  会卡顿  无法捕获  一定要判空
 */
public class PreferenceHelper {
    public static void write(Context context, String fileName, String k, int v) {
        if (context != null && fileName != null && k != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putInt(k, v);
            editor.commit();
        }
    }

    public static void write(Context context, String fileName, String k, boolean v) {
        if (context != null && fileName != null && k != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putBoolean(k, v);
            editor.commit();
        }
    }

    public static void write(Context context, String fileName, String k,
                             String v) {
        if (context != null && fileName != null && k != null && v != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(k, v);
            editor.commit();
        }
    }

    public static int readInt(Context context, String fileName, String k) {
        if (context != null && fileName != null && k != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            return preference.getInt(k, -1);
        }
        return -1;
    }

    public static int readInt(Context context, String fileName, String k,
                              int defv) {
        if (context != null && fileName != null && k != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            return preference.getInt(k, defv);
        }
        return -1;
    }

    public static boolean readBoolean(Context context, String fileName, String k) {
        if (context != null && fileName != null && k != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            return preference.getBoolean(k, false);
        }
        return false;
    }

    public static boolean readBoolean(Context context, String fileName,
                                      String k, boolean defBool) {

        if (context != null && fileName != null && k != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            return preference.getBoolean(k, defBool);
        }
        return false;
    }

    public static String readString(Context context, String fileName, String k) {
        if (context != null && fileName != null && k != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            return preference.getString(k, null);
        }
        return null;
    }

    public static String readString(Context context, String fileName, String k, String defV) {
        if (context != null && fileName != null && k != null && defV != null) {
            SharedPreferences preference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            return preference.getString(k, defV);
        }
        if (defV != null) {
            return defV;
        } else {
            return "";
        }
    }

    public static void remove(Context context, String fileName, String k) {
        try {
            if (context != null && fileName != null && k != null) {
                SharedPreferences preference = context.getSharedPreferences(fileName,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.remove(k);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clean(Context cxt, String fileName) {
        try {
            if (cxt != null && fileName != null) {
                SharedPreferences preference = cxt.getSharedPreferences(fileName,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.clear();
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setPreData(Context context, String filename, String preKey, String preValue) {
        try {
            if (!TextUtils.isEmpty(filename) && !TextUtils.isEmpty(preKey) && preValue != null) {
                PreferenceHelper.write(context, filename, preKey, preValue);
                int i = 0;
                while (PreferenceHelper.readString(context, filename, preKey) == null) {
                    i++;
                    if (i < 4) {
                        PreferenceHelper.write(context, filename, preKey, preValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPreData(Context context, String filename, String preKey) {
        try {
            if (!TextUtils.isEmpty(filename) && !TextUtils.isEmpty(preKey)) {
                int i = 0;
                while (i < 4) {
                    i++;
                    String readString = PreferenceHelper.readString(context, filename, preKey);
                    if (readString != null) {
                        return readString;
                    }
                }
                return PreferenceHelper.readString(context, filename, preKey, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void setIntPreData(Context context, String filename, String preKey, int preValue) {
        try {
            if (!TextUtils.isEmpty(filename) && !TextUtils.isEmpty(preKey)) {
                PreferenceHelper.write(context, filename, preKey, preValue);
                int i = 0;
                while (PreferenceHelper.readInt(context, filename, preKey, -1) == -1) {
                    i++;
                    if (i < 4) {
                        PreferenceHelper.write(context, filename, preKey, preValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getIntPreData(Context context, String filename, String preKey) {
        try {
            if (!TextUtils.isEmpty(filename) && !TextUtils.isEmpty(preKey)) {
                int i = 0;
                while (i < 4) {
                    i++;
                    int readInt = PreferenceHelper.readInt(context, filename, preKey, -1);
                    if (readInt != -1) {
                        return readInt;
                    }
                }
                return PreferenceHelper.readInt(context, filename, preKey, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取SharedPreferences里全部的key-value对。
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Map<String, ?> getAllKeyValue(Context context, String fileName) {
        try {
            if (context != null && fileName != null) {
                SharedPreferences preference = context.getSharedPreferences(fileName,
                        Context.MODE_PRIVATE);
                return preference.getAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * 获取SharedPreferences里全部的key  List。
     *
     * @param fileName
     * @return
     */
    public static List<String> getAllListKey(Context context, String fileName) {
        List<String> list = new ArrayList<>();
        try {
            if (fileName != null) {
                SharedPreferences preference = context.getSharedPreferences(fileName,
                        Context.MODE_PRIVATE);
                Map<String, ?> all = preference.getAll();
                if (all != null && all.size() > 0) {
                    Iterator iter = all.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        list.add((String) entry.getKey());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取SharedPreferences里全部的value  List。
     *
     * @param fileName
     * @return
     */
    public static <T> List<T> getAllListValue(Context context, String fileName) {
        List<T> list = new ArrayList<>();
        try {
            if (fileName != null) {
                SharedPreferences preference = context.getSharedPreferences(fileName,
                        Context.MODE_PRIVATE);
                Map<String, ?> all = preference.getAll();
                if (all != null && all.size() > 0) {
                    Iterator iter = all.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        list.add((T) entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}