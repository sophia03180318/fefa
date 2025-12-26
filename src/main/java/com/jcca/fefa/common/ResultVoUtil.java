package com.jcca.fefa.common;

import com.jcca.fefa.common.ResultVo;

/**
 * 响应数据(结果)最外层对象工具
 *
 * @author hanwone
 * @date 2018/10/15
 */
public class ResultVoUtil {


    /**
     * 操作成功
     *
     * @param msg    提示信息
     * @param object 对象
     */
    public static <T> ResultVo<T> success(String msg, T object) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setMsg(msg);
        resultVo.setCode(200);
        resultVo.setData(object);
        return resultVo;
    }

    /**
     * 操作成功，使用默认的提示信息
     *
     * @param object 对象
     */
    public static <T> ResultVo<T> success(T object) {
        String message = "sucess";
        return success(message, object);
    }

    /**
     * 操作成功，返回提示信息，不返回数据
     */
    public static <T> ResultVo<T> success(String msg) {
        return success(msg, null);
    }

    /**
     * 操作成功，不返回数据
     */
    public static <T> ResultVo<T> success() {
        return success("sucess");
    }

    /**
     * 操作有误
     *
     * @param code 错误码
     * @param msg  提示信息
     */
    public static <T> ResultVo<T> error(Integer code, String msg) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setMsg(msg);
        resultVo.setCode(code);
        return resultVo;
    }

    /**
     * 警示，校验性的错误
     * 不是不可弥补的
     * @param msg 提示信息
     */
    public static <T> ResultVo<T> warning(String msg) {
        Integer code =888;
        return error(code, msg);
    }

    /**
     * 系统报错，严重的错误，非操作校验引起
     *
     * @param msg 提示信息
     */
    public static <T> ResultVo<T> error(String msg) {
        Integer code = 999;
        return error(code, msg);
    }


}
