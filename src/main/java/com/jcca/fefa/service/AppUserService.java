package com.jcca.fefa.service;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:32
 **/
import com.baomidou.mybatisplus.extension.service.IService;
import com.jcca.fefa.entity.User;

public interface UserService extends IService<User> {
    // 可定义额外的业务方法，比如根据邮箱查找用户等
    User getByEmail(String email);
}