package com.hero.mvcdemo.tools.httprequest;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台返回错误码
 */
public class HttpErrorCodeConfig {
    public static final String ERRORCODE_USER_LOGOUT = "5004005";    //当前客户端用于已被登出
    public static final String ERRORCODE_USER_BINDINGED_WX = "9007001";    //该用户已绑定微信
    public static final String ERRORCODE_FILE_EXIST = "5009202";    //文件已存在   上传日志文件  如果已存在 删除
    public static final String ERRORCODE_UPLOAD_APP = "5009300";    //请升级APP   报此错误码 提示用户升级
    public static final String ERRORCODE_WEB_TIP = "5009310";    //跳转网页   报此错误码 跳转网页
    public static final String ERRORCODE_CUSTOM_CODE = "300";    //自定义错误码

    private static HttpErrorCodeConfig mHttpErrorCodeConfig;
    private static Map<String, String> mCodeMap = new HashMap<>();

    public HttpErrorCodeConfig() {
        mCodeMap.put("5004000", "图片不存在");
        mCodeMap.put("5004001", "用户不存在");
        mCodeMap.put("5004003", "病例不存在或已删除");
        mCodeMap.put("5004004", "病例评论不存在或已删除");
        mCodeMap.put(ERRORCODE_USER_LOGOUT, "当前客户端用于已被登出");  //5004005
        mCodeMap.put("5009003", "数据异常");
        mCodeMap.put("9001001", "密码错误");
        mCodeMap.put("5005007", "手机号格式验证错误");
        mCodeMap.put("9001105", "用户未完成注册流程");
        mCodeMap.put("9001003", "不能重复关注用户");
        mCodeMap.put("9001004", "不能对未关注过的用户取消关注");
        mCodeMap.put("9001002", "不能关注自己");
        mCodeMap.put("5005008", "自我介绍字数超长");
        mCodeMap.put("5004002", "邀请码不存在");
        mCodeMap.put("9001101", "错误的邀请码");
        mCodeMap.put("9001102", "邀请码已使用");
        mCodeMap.put("9005006", "验证码已过期");
        mCodeMap.put("9005004", "没有有效的验证码");
        mCodeMap.put("9005005", "错误的验证码");
        mCodeMap.put("9005007", "超出最大尝试次数");
        mCodeMap.put("5005001", "输入参数为空");
        mCodeMap.put("5005012", "密码超长,最大长度30");
        mCodeMap.put("5005010", "无效的文字内容");
        mCodeMap.put("5005005", "评论字数超长");
        mCodeMap.put("5004004", "病例评论不存在或已删除");
        mCodeMap.put("5009002", "重复操作");
        mCodeMap.put("9002002", "该病例评论未被感谢，不能取消感谢");
        mCodeMap.put("9002001", "评论重复顶或踩操作");
        mCodeMap.put("9001501", "超出病例最大图片数量");
        mCodeMap.put("9001500", "图片数量和描述数量不匹配");
        mCodeMap.put("9005002", "短信发送太过频繁");
        mCodeMap.put("9005003", "超出当天短信限额");
        mCodeMap.put("9003001", "至少需要关注一个病例标签");
        mCodeMap.put("5004007", "没有权限");
        mCodeMap.put("5004010", "上次的修改还没审核/ 已取消咨询/ 直播已经开始，不能再修改状态/操作失败，需要成员重新申请/对方正在忙，请稍后重试");      //修改个人信息
        mCodeMap.put("5009001", "尚未完成打分，不能提交质控报告");
        mCodeMap.put("5000004", "未上传图片");
        mCodeMap.put("9001103", "用户已注册");
        mCodeMap.put("5004008", "未知");    //问病理  详情中
        mCodeMap.put("5004009", "病例已结束");
        mCodeMap.put("6000001", "微信登录服务调用失败");
        mCodeMap.put("5004000", "兑换码不存在/不存在的互动实视");
        mCodeMap.put("5000000", "向咨询专家下单失败，请稍后重试");
        mCodeMap.put("4000001", "病例图片不能为空");
        mCodeMap.put("4000002", "非法访问");
        mCodeMap.put("9008001", "此图像已审核，不能修改");
        mCodeMap.put("9008002", "此图像正在审核中，不能修改");
        mCodeMap.put("9008004", "此图像已被审核者标记为存疑");
        mCodeMap.put(ERRORCODE_UPLOAD_APP, "请升级APP");
        mCodeMap.put(ERRORCODE_USER_BINDINGED_WX, "该用户已绑定微信");   //9007001
    }

    //生成http请求的单例方法
    public static HttpErrorCodeConfig getInstance() {
        if (mHttpErrorCodeConfig == null) {
            synchronized (HttpErrorCodeConfig.class) {
                if (mHttpErrorCodeConfig == null) {
                    mHttpErrorCodeConfig = new HttpErrorCodeConfig();
                }
            }
        }
        return mHttpErrorCodeConfig;
    }

    /**
     * 是约定的提示的错误码
     *
     * @param errorCode
     * @return
     */
    public static boolean isErrorCodeOpen(String errorCode) {

        if (errorCode == null) {
            return false;
        }

        mHttpErrorCodeConfig = getInstance();
        if (!TextUtils.isEmpty(mCodeMap.get(errorCode))) {
            return true;
        }
        return false;
    }
}