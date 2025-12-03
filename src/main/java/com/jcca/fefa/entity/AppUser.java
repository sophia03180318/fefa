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
@TableName("user")  // 对应数据库中的用户表
public class User implements Serializable {
    @TableId(value = "id")      // 主键
    private Long id;
    private String username;    // 用户名称
    private String email;       // 用户邮箱
    private String password;    // 加密后的用户密码

}