package com.jcca.fefa.entity;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:30
 **/
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("app_user")  //app的用户表
public class AppUser implements Serializable {
    @TableId(value = "id")      // 主键
    private String id;
    private String username;    // 用户名称
    private String email;       // 用户邮箱
    private String password;    // 加密后的用户密码

    @TableField(exist = false)
    private double balance;
    @TableField(exist = false)//1=中文 2=英文
    private int language;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "CREATE_TIME")
    private Date createTime;
}