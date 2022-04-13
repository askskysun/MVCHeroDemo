package com.hero.mvcdemo.cons;

/**
 * 接口回调  的id
 */
public class IDInterfaceCallBackMessage {

    /**
     *  个人中心
     */
     //我的收藏  筛选框  回调   被选中tagid
    public static final int PROFILE_CUSTOMTAGDIALOG_PROFAV_ACTION = 1000000;

    /**
     *  病例
     */

    public static final int CASE_FAVCLICKDIALOG_ADDTAG = 1010000;
    // 上传图片   通知去缩小或者放大viewpager     VPphotodescriptionFragment   HomePhotodescriptionActivity
    public static final int CASE_UPLOADING_SHRINKVIEWPAGER = 1010001;
   //个人笔记
    public static final int PRIVATECASE_LOADALLIMG = 1010002;      //笔记中   下载所有图片
    public static final int PRIVATECASE_LOADONEIMG = 1010003;      //笔记中   保存单张图片
    public static final int PRIVATECASE_ADDPIC = 1010004;      //笔记中  添加图片

    /**
     *  通知
     */
    //下拉框
    public static final int NEWS_DIALOG_CHOICETYPE = 1020000;

}
