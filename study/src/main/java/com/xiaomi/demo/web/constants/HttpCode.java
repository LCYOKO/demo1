package com.xiaomi.demo.web.constants;

/**
 * 统一异常码
 * @Author: liuchiyun
 * @Date: 2023/11/1
 */
public enum HttpCode {

    /**
     * HTTP异常:  100 - 600
     *
     * 身份验证异常  50000 - 50099
     *
     * 外部请求参数异常: 200000 - 209999
     *
     * 管理平台异常: 300000 - 300999
     */

    //HTTP原生code
    OK(200, "请求成功"),
    NOT_AUTH(401, "身份未认证"),
    NOT_PERMISSION(403, "未授权"),
    NOT_FOUND(404, "资源不存在"),
    ERROR(500, "服务器错误"),

    // 业务相关
    AUTH_FAILED(50000, "身份认证失败"),
    RATE_LIMIT(50001, "请求太频繁"),
    ACCOUNT_LOCKED(50002, "用户已被锁定"),
    ACCOUNT_HAS_LOGIN(50003, "用户已登录"),
    ACCOUNT_HAS_NOT_LOGIN(50004, "用户未登录"),
    AUTH_PARAM_INVALID(50005, "验签参数非法"),

    //========================= biz 业务处理异常错误码
    DEVICE_NOT_EXIST(100000, "设备不存在"),

    //========================= 外部异常错误码

    // 外部请求参数
    PARAM_ERROR(200000, "参数错误"),
    DATE_NOT_VALID(200001, "开始时间不能大于结束时间"),
    DATE_GREATER_THAN_THREE(200002, "开始日期跟结束日期不能超过3天"),
    PARAM_FORMAT_ERROR(200003, "参数格式错误"),
    PARAM_PAGE_SIZE_OVER(200004, "分页大小范围为1-100"),
    PARAM_REQUEST_METHOD_ERROR(200005, "请求方式错误"),

    // 管理平台
    APP_KEY_ADD_ERROR(300001, "新增AppKey失败"),
    APP_KEY_UPDATE_ERROR(300002, "修改appKey失败"),
    APP_KEY_EXIST(300003, "当前AppKey已经存在");
    private final int code;
    private final String msg;

    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
