package com.jcca.fefa.controller;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:51
 **/

import com.jcca.fefa.entity.User;
import com.jcca.fefa.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/")
    public String homeRedirect(HttpServletRequest request) {
        Object loggedUser = request.getSession().getAttribute("loggedUser");
        return loggedUser != null ? "redirect:/knowledge/list" : "redirect:/login";
    }

    // 显示登录页面
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 对应 templates/login.html
    }

    // 处理登录表单提交
    @PostMapping("/login")
    public String doLogin(HttpServletRequest request, Model model,
                          String username, String password) {
        // 根据邮箱查找用户
        User user = userService.getByName(username);
        if (user == null) {
            model.addAttribute("error", "用户不存在");
            return "login";
        }
        // 对输入密码进行MD5加密后与数据库中密码比对:contentReference[oaicite:14]{index=14}
        String encrypted = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!encrypted.equals(user.getPassword())) {
            model.addAttribute("error", "密码错误");
            return "login";
        }
        // 登录成功，在session中记录用户信息，并重定向到用户列表页面
        request.getSession().setAttribute("loggedUser", user);
        return "redirect:/knowledge/list";
    }

    // 用户登出
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();  // 清除会话
        return "redirect:/login";
    }
}