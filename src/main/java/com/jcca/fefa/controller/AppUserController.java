package com.jcca.fefa.controller;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcca.fefa.entity.User;
import com.jcca.fefa.service.AppUserService;
import com.jcca.fefa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/appSysUser")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

}