package com.jcca.fefa.controller.bean;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/26 13:47
 **/
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ChallengeTaskImportDTO {

    @ExcelProperty("content")
    private String content;

    @ExcelProperty("contentEn")
    private String contentEn;

    @ExcelProperty("days")
    private String days;

    @ExcelProperty("type")
    private String type;

    @ExcelProperty("amountMin")
    private String amountMin;

    @ExcelProperty("amountMax")
    private String amountMax;
}
