package com.jcca.fefa.entity;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:30
 **/

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("knowledge_card")  // 知识卡表
public class KnowledgeCard implements Serializable {
    @TableId(value = "id")
    private Long id;
    @ExcelProperty("title")
    private String title;    // 知识卡中文标题
    @ExcelProperty("content")
    private String content;  // 知识卡中文内容
    @ExcelProperty("titleEn")
    private String titleEn;    // 知识卡英文标题
    @ExcelProperty("contentEn")
    private String contentEn;  // 知识卡英文内容


}