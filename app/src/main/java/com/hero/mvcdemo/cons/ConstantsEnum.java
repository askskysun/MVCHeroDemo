package com.hero.mvcdemo.cons;

/**
 * <pre>
 *     全局常量Enum
 * </pre>
 * Author: hyj
 * Date: 2018/9/13　11:21
 */
public enum ConstantsEnum {
    UNKNOW(0, "未知"),
    BOY(1, "男"),
    GIRLE(2, "女"),
    NO(0, "否"),
    YES(1, "是"),
    ADD(1, "增"),
    REDUCE(-1, "减"),
    DIRECTION_DOWN(1, "正序"),
    DIRECTION_UP(-1, "倒序");

    private int code;
    private String descr;

    ConstantsEnum(int code, String descr) {
        this.code = code;
        this.descr = descr;
    }

    public int getCode() {
        return code;
    }

    public String getCode4Str() {
        return String.valueOf(code);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * 获取性别中文名
     *
     * @param genderCode
     * @return
     */
    public static String getGenderName(int genderCode) {
        if (genderCode == BOY.getCode()) {
            return BOY.getDescr();
        } else if (genderCode == GIRLE.getCode()) {
            return GIRLE.getDescr();
        } else {
            return UNKNOW.getDescr();
        }
    }
}
