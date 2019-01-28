package com.zzw.spring.cloud.nacos.common;

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

    private static final long serialVersionUID = 1789151585L;
    private static final int DEFAULT_EXCEPTION_CODE = -1;
    private static final int DEFAULT_SUCCESS_CODE = 1;
    private static final int DEFAULT_ERROR_CODE = 0;

    private Integer code;
    private Boolean success;
    private String msg;
    private String remark;
    private T data;
    private Throwable exception;

    public static <T> ResultDTO<T> handleSuccess(String msg, T data) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_SUCCESS_CODE);
        ret.setSuccess(true);
        ret.setMsg(msg);
        ret.setRemark("success");
        ret.setData(data);
        ret.setException(null);
        return ret;
    }

    public static <T> ResultDTO<T> handleSuccess(T data) {
        return handleSuccess("success", data);
    }

    public static <T> ResultDTO<T> handleError(String msg, T data) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_ERROR_CODE);
        ret.setMsg(msg);
        ret.setSuccess(false);
        ret.setRemark("occur an error");
        ret.setData(data);
        ret.setException(null);
        return ret;
    }

    public static <T> ResultDTO<T> handleException(String msg, T data, Throwable e) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_EXCEPTION_CODE);
        ret.setSuccess(false);
        ret.setMsg(null == msg ? e.getMessage() : msg);
        ret.setRemark("occur an exception");
        ret.setData(data);
        ret.setException(e);
        return ret;
    }

    public static <T> ResultDTO<T> handleException(Throwable e, Class<T> clazz) {
        return handleException(e.getMessage(), e, clazz);
    }

    public static <T> ResultDTO<T> handleException(String msg, Throwable e, Class<T> clazz) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_EXCEPTION_CODE);
        ret.setSuccess(false);
        ret.setMsg(msg == null || "".equals(msg) ? e.getMessage() : msg);
        ret.setRemark("occur an exception");
        ret.setData(null);
        ret.setException(e);
        return ret;
    }
}

