package com.jcca.fefa.common;

import lombok.Data;

/**
 *
 * @author sophia
 * @date 2025/12/15
 */
@Data
public class ResultVo<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;
}
