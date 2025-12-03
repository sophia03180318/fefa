package com.jcca.fefa.entity;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:30
 **/

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("knowledge_card")  // 知识卡表
public class KnowledgeCard implements Serializable {
    @TableId(value = "id")
    private Long id;
    private String title;    // 知识卡标题
    private String content;  // 知识卡内容
    private String category; // 知识卡类别
}