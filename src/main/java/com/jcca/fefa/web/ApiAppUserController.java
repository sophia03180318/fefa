package com.jcca.fefa.web;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.jcca.fefa.common.MyIdUtil;
import com.jcca.fefa.common.ResultVo;
import com.jcca.fefa.common.ResultVoUtil;
import com.jcca.fefa.entity.AppUser;
import com.jcca.fefa.service.AppUserService;
import com.jcca.fefa.web.enums.ResultCodeEnum;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/api/appUser")
public class ApiAppUserController {

    @Resource
    private AppUserService appUserService;


    // 用户登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody AppUser user) {
        if (ObjectUtils.isEmpty(user.getEmail())) {
            return ResultVoUtil.error(ResultCodeEnum.PARAM_EMPTY.getMessage(user.getLanguage()));
        }
        if (ObjectUtils.isEmpty(user.getPassword())) {
            return ResultVoUtil.error(ResultCodeEnum.PARAM_EMPTY.getMessage(user.getLanguage()));
        }

        AppUser dbUser = appUserService.getOne(
                new QueryWrapper<AppUser>()
                        .eq("email", user.getEmail())
        );

        if (dbUser == null) {
            return ResultVoUtil.error(ResultCodeEnum.USER_NOT_EXIST.getMessage(user.getLanguage()));
        }
        boolean match = (user.getPassword().equals(dbUser.getPassword()));
        if (!match) {
            return ResultVoUtil.error(ResultCodeEnum.PASSWORD_ERROR.getMessage(user.getLanguage()));
        }
        return ResultVoUtil.success(ResultCodeEnum.SUCCESS.getMessage(user.getLanguage()),dbUser);
    }


    // 用户注册
    @PostMapping("/add")
    public ResultVo addAppUser(@RequestBody AppUser user) {
        if (ObjectUtils.isEmpty(user.getEmail())) {
            return ResultVoUtil.error(ResultCodeEnum.PARAM_EMPTY.getMessage(user.getLanguage()));
        }
        if (ObjectUtils.isEmpty(user.getPassword())) {
            return ResultVoUtil.error(ResultCodeEnum.PARAM_EMPTY.getMessage(user.getLanguage()));
        }
        AppUser existUser = appUserService.getOne(
                new QueryWrapper<AppUser>()
                        .eq("email", user.getEmail())
        );
        if (existUser != null) {
            return ResultVoUtil.error(ResultCodeEnum.EMAIL_ALREADY_REGISTERED.getMessage(user.getLanguage()));
        }
        user.setId(MyIdUtil.getId());
        user.setCreateTime(new Date());
        user.setPassword(user.getPassword());
        boolean save = appUserService.save(user);
        if (!save) {
            return ResultVoUtil.error(ResultCodeEnum.FAIL.getMessage(user.getLanguage()));
        }
        return ResultVoUtil.success(ResultCodeEnum.SUCCESS.getMessage(user.getLanguage()));
    }

    // 重置密码
    @PostMapping("/update")
    public ResultVo updateAppUser( @RequestBody AppUser user) {

        if (ObjectUtils.isEmpty(user.getEmail())) {
            return ResultVoUtil.error(ResultCodeEnum.PARAM_EMPTY.getMessage(user.getLanguage()));
        }
        if (ObjectUtils.isEmpty(user.getPassword())) {
            return ResultVoUtil.error(ResultCodeEnum.PARAM_EMPTY.getMessage(user.getLanguage()));
        }
        AppUser dbUser = appUserService.getOne(
                new QueryWrapper<AppUser>()
                        .eq("email", user.getEmail())
        );

        if (dbUser == null) {
            return ResultVoUtil.error(ResultCodeEnum.USER_NOT_EXIST.getMessage(user.getLanguage()));
        }
        dbUser.setPassword(user.getPassword());

        boolean update = appUserService.updateById(dbUser);
        if (!update) {
            return ResultVoUtil.error(ResultCodeEnum.FAIL.getMessage(user.getLanguage()));
        }
        return ResultVoUtil.success(ResultCodeEnum.SUCCESS.getMessage(user.getLanguage()));
    }

    // 删除用户
    @PostMapping("/delete")
    public ResultVo deleteAppUser(@RequestBody AppUser user) {
        appUserService.removeById(user.getId());
        return ResultVoUtil.success(ResultCodeEnum.SUCCESS.getMessage(user.getLanguage()));
    }

    // 发送邮箱验证码
    @GetMapping("/sendEmail/{email}")
    public ResultVo sendEmail(@PathVariable("email") String email) {
        return ResultVoUtil.success("1234");
    }




}