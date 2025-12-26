package com.jcca.fefa.web.enums;

public enum ResultCodeEnum {

    SUCCESS(1, "成功", "Success"),
    FAIL(2, "失败", "Failed"),
    EMAIL_ALREADY_REGISTERED(3, "邮箱已注册，请直接登录", "Email already registered, please log in directly"),
    PASSWORD_ERROR(4, "密码错误", "Incorrect password"),
    EMAIL_VERIFY_FAILED(5, "邮箱验证不通过", "Email verification failed"),
    PARAM_EMPTY(6, "参数为空", "Parameter is empty"),
    USER_NOT_EXIST(7, "无此用户信息，请先注册", "User not found, please register first");

    private final int code;
    private final String zh;
    private final String en;

    ResultCodeEnum(int code, String zh, String en) {
        this.code = code;
        this.zh = zh;
        this.en = en;
    }

    public int getCode() {
        return code;
    }

    /**
     * 根据语言返回对应提示
     * @param language 1=中文 2=英文
     */
    public String getMessage(int language) {
        return language == 2 ? en : zh;
    }
}
