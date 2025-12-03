package com.jcca.fefa.service;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:32
 **/
import com.baomidou.mybatisplus.extension.service.IService;
import com.jcca.fefa.entity.User;

public interface UserService extends IService<User> {
    User getByName(String name);
}