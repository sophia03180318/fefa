package com.jcca.fefa.controller;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcca.fefa.entity.AppUser;
import com.jcca.fefa.service.AppUserService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/appUser")
@Api(value = "用户管理", tags = {"用户管理"})
public class AppUserController {

    @Resource
    private AppUserService appUserService;

    //用户列表
    @GetMapping("/list")
    public String listAppUsers(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "emailKeyword", required = false) String emailKeyword) {
        Page<AppUser> pageParam = new Page<>(page, 20);
        IPage<AppUser> pageData;
        if (ObjectUtils.isNotEmpty(emailKeyword)) {
            // 按名称模糊查询分页
            pageData = appUserService.page(pageParam,
                    new QueryWrapper<AppUser>()
                            .like("email", emailKeyword));
        } else {
            // 无搜索，直接分页查询所有
            pageData = appUserService.page(pageParam);
        }
        model.addAttribute("pageData", pageData);
        model.addAttribute("emailKeyword", emailKeyword);
        return "appuser-list";  // 对应 templates/appuser-list.html
    }

}