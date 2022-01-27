package com.jdd.community_management_system.utils.dataUtils;


/** 数据返回工具类 */
public class ResultUtils {
    // 处理返回值
    public static ResultVo<Object> Vo(String msg, int code, Object data) {
        return new ResultVo<>(msg, code, data);
    }

    // 成功返回
    public static ResultVo<Object> success() {
        return Vo(null, StatusCode.SUCCESS_CODE, null);
    }
    public static ResultVo<Object> success(String msg) {
        return Vo(msg, StatusCode.SUCCESS_CODE, null);
    }
    public static ResultVo<Object> success(String msg, Object data) {
        return Vo(msg, StatusCode.SUCCESS_CODE, data);
    }
    public static ResultVo<Object> success(String msg, int code, Object data) {
        return Vo(msg, code, data);
    }

    // 失败返回值
    public static ResultVo<Object> error() {
        return Vo(null, StatusCode.ERROR_CODE, null);
    }
    public static ResultVo<Object> error(String msg) {
        return Vo(msg, StatusCode.ERROR_CODE, null);
    }
    public static ResultVo<Object> error(String msg, int code, Object data) {
        return Vo(msg, code, data);
    }
    public static ResultVo<Object> error(String msg, int code) {
        return Vo(msg, code, null);
    }
    public static ResultVo<Object> error(String msg, Object data) {
        return Vo(msg, StatusCode.ERROR_CODE, data);
    }
}

