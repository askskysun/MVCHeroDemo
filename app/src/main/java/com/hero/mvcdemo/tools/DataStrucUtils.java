package com.hero.mvcdemo.tools;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 数据处理工具类
 */
public class DataStrucUtils {

    /**
     * 删除ArrayList中重复元素，保持顺序     走过此方法  原来对象会改变
     *
     * @param list
     */
    public static void removeDuplicateWithOrder(List list) {
        if (list != null && list.size() > 0) {
            Set set = new HashSet();
            List newList = new ArrayList();
            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                Object element = iter.next();
                if (set.add(element))
                    newList.add(element);
            }
            list.clear();
            list.addAll(newList);
        }
    }

    /**
     * 从集合中移除另一个集合    不保存原数据
     *
     * @param listAll
     * @param listPart
     */
    public static void removeListInListUnKeep(List listAll, List listPart) {
        if (listAll == null || listPart == null) {
            return;
        }
        for (int i = 0; i < listPart.size(); i++) {
            listAll.remove(listPart.get(i));
        }
    }

    /**
     * 从集合中移除另一个集合    保持原数据
     *
     * @param listAll
     * @param listPart
     */
    public static List removeListInListKeep(List listAll, List listPart) {
        if (listAll == null || listPart == null) {
            return new ArrayList();
        }
        List listAllNew = new ArrayList();
        listAllNew.addAll(listAll);
        List listPartNew = new ArrayList();
        listPartNew.addAll(listPart);

        for (int i = 0; i < listPartNew.size(); i++) {
            listAllNew.remove(listPartNew.get(i));
        }
        return listAllNew;
    }

    /**
     * String 转int
     *
     * @param str
     * @return
     */
    public static int getIntegerFromString(String str) {
        int count = 0;
        try {
            Integer integer = Integer.valueOf(str);
            if (integer != null && integer >= 0) {
                count = integer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * String 转int      大于或者等于0   默认值
     *
     * @param str
     * @return
     */
    public static int getIntegerFromString(int defaultCount, String str, boolean isMoreZ) {
        int count = defaultCount;
        try {
            Integer integer = Integer.valueOf(str);
            if (integer != null) {
                count = integer;
            }
            if (isMoreZ && count < 0) {
                count = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * String 转 long 大于或者等于0   默认值
     *
     * @param str
     * @return
     */
    public static long getLongFromString(long defaultCount, String str, boolean isMoreZ) {
        long count = defaultCount;
        try {
            Long longStr = Long.valueOf(str);
            if (longStr != null) {
                count = longStr;
            }
            if (isMoreZ && count < 0) {
                count = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 获取横向排列图片  宽度
     *
     * @param context
     * @param count
     * @param sideDis
     * @param spaceDis
     * @return
     */
    public static int getLandImgWith(Context context, int count, int sideDis, int spaceDis) {
        return getLandImgWith(context, count, sideDis, spaceDis, 0);
    }

    /**
     * 获取横向排列图片  宽度
     *
     * @param context
     * @param count
     * @param sideDis
     * @param spaceDis
     * @param reduceLegth 减去的长度
     * @return
     */
    public static int getLandImgWith(Context context, int count, int sideDis, int spaceDis, int reduceLegth) {
        if (context == null) {
            return 0;
        }
        return (DensityUtils.getScreenW(context) - reduceLegth - 2 * DensityUtils.dip2px(context, sideDis) - (count - 1) * DensityUtils.dip2px(context, spaceDis)) / count;
    }

    /**
     * Integer 转 int
     *
     * @param defaultCount 默认值
     * @param integer      数据
     * @param isMoreZ      是否要大于0
     * @return
     */
    public static int getIntFromInteger(int defaultCount, Integer integer, boolean isMoreZ) {
        int count = defaultCount;
        if (integer != null) {
            count = integer;
        }
        if (isMoreZ && count < 0) {
            count = 0;
        }
        return count;
    }

    public static float getfloatFromFloat(float defaultCount, Float floatNum, boolean isMoreZ) {
        float count = defaultCount;
        if (floatNum != null) {
            count = floatNum;
        }
        if (isMoreZ && count < 0) {
            count = 0;
        }
        return count;
    }

    public static double getdoubleFromDouble(float defaultCount, Double doubleNum, boolean isMoreZ) {
        double count = defaultCount;
        if (doubleNum != null) {
            count = doubleNum;
        }
        if (isMoreZ && count < 0) {
            count = 0;
        }
        return count;
    }

    /**
     * 字符串的值  与int是否相等
     *
     * @param str
     * @param num
     * @return
     */
    public static boolean getStringIs(String str, int num) {
        if (EmptyJudgeUtils.stringIsEmpty(str)) {
            return false;
        } else {
            try {
                return Integer.valueOf(str) == num;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 复制list  防止修改原数据
     *
     * @param listData
     * @return
     */
    public static List<?> copyList(List<?> listData) {
        List listNew = new ArrayList<>();
        if (listData != null) {
            listNew.addAll(listData);
        }
        return listNew;
    }

    /**
     * 格式化数据，显示的数字，最大是999,
     *
     * @param number
     * @return 返回格式：999+
     */
    public static String formatNumber(Integer number) {
        return formatNumber(number, "");
    }

    /**
     * 格式化数据，显示的数字，最大是999,
     *
     * @param number 阅读人数
     * @param tip    阅读_
     * @return 返回格式：收藏 999+
     */
    public static String formatNumber(Integer number, String tip) {
        StringBuilder sb = new StringBuilder(tip);
        if (number == null || number < 0) {
            sb.append("0");
        } else if (number > 999) {
            sb.append("999+");
        } else {
            sb.append(number);
        }
        return sb.toString();
    }

    /**
     * 格式化数据，若数据大于99,则显示99+
     *
     * @param number
     * @return
     */
    public static String formatNumber99(int number) {
        if (number < 0) {
            return "0";
        } else if (number > 99) {
            return "99+";
        } else {
            return String.valueOf(number);
        }
    }

    public static String getReplaceStr(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\n", "\n");
    }

    /**
     * list 转string
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {

        if (list == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }
}