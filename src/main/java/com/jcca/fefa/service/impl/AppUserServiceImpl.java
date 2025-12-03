package com.jcca.fefa.service.impl;

import com.jcca.fefa.entity.User;
import com.jcca.fefa.mapper.UserMapper;
import com.jcca.fefa.service.UserService;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:48
 **/

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByEmail(String email) {
        // 使用 MyBatis-Plus 提供的 QueryWrapper 根据 email 查询单个用户
        return userMapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<User>()
                .eq("email", email));
    }

    /**
     * 新增用户时，进行密码加密后再保存
     */
    @Override
    public boolean save(User user) {
        // 对明文密码进行MD5加密再保存:contentReference[oaicite:10]{index=10}
        if (user.getPassword() != null) {
            String encrypted = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(encrypted);
        }
        return super.save(user);  // 调用 ServiceImpl 提供的 save 方法
    }
}