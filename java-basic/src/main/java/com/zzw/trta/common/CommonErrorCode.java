package com.zzw.trta.common;

/**
 * @author zhaozhiwei
 * @date 2019/3/5 1:02
 */
public enum CommonErrorCode implements ErrorCode {

    /**
     * 系统错误
     */
    SYSTEM_ERROR(502, "系统错误");

    private Integer code;
    private String message;

    CommonErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
