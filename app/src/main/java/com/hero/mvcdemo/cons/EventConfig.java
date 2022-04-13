package com.hero.mvcdemo.cons;

/**
 * Event的id常量
 */
public class EventConfig {

    private static int sActionCount;
    private synchronized static int getActionId() {
        return sActionCount++;
    }

    /**
     * 首页
     */
    public static final int EVENT_HOMEDIALOG_CLICKFOLTAG = getActionId();
}