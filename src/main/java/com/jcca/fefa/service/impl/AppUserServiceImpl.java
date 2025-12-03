package com.jcca.fefa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcca.fefa.entity.AppUser;
import com.jcca.fefa.mapper.AppUserMapper;
import com.jcca.fefa.service.AppUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    /**
     * 新增用户时，进行密码加密后再保存
     */
    @Override
    public boolean save(AppUser user) {
        // 对明文密码进行MD5加密再保存:contentReference[oaicite:10]{index=10}
        if (user.getPassword() != null) {
            String encrypted = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(encrypted);
        }
        return super.save(user);  // 调用 ServiceImpl 提供的 save 方法
    }
}