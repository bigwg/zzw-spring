package com.zzw.trta.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhaozhiwei
 * @date 2019/1/7 22:11
 */
@Getter
@Setter
@ToString
public class ResultDTO<T> implements Serializable {

    private static final int DEFAULT_EXCEPTION_CODE = -1;
    private static final int DEFAULT_SUCCESS_CODE = 1;
    private static final int DEFAULT_ERROR_CODE = 0;
    private static final long serialVersionUID = 9100851771994890862L;

    private Integer code;
    private Boolean success;
    private String msg;
    private T data;

    public static <T> ResultDTO<T> handleSuccess(String msg, T data) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_SUCCESS_CODE);
        ret.setSuccess(true);
        ret.setMsg(msg);
        ret.setData(data);
        return ret;
    }

    public static <T> ResultDTO<T> handleSuccess(T data) {
        return handleSuccess("success", data);
    }

    public static <T> ResultDTO<T> handleError(ErrorCode errorCode, Class<T> clazz) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(errorCode.getCode());
        ret.setMsg(errorCode.getMessage());
        ret.setSuccess(false);
        return ret;
    }

    public static <T> ResultDTO<T> handleError(Integer code, String msg, Class<T> clazz) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(code);
        ret.setMsg(msg);
        ret.setSuccess(false);
        return ret;
    }

}

