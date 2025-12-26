package com.jcca.fefa.controller.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/26 11:06
 **/
@Data
public class KnowledgeCardImportDTO {
    @ExcelProperty("title")
    private String title;

    @ExcelProperty("content")
    private String content;

    @ExcelProperty("titleEn")
    private String titleEn;

    @ExcelProperty("contentEn")
    private String contentEn;
}
