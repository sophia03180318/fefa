package com.jcca.fefa.controller;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcca.fefa.entity.User;
import com.jcca.fefa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 分页查看用户列表（可选支持按名称查询）
    @GetMapping("/list")
    public String listUsers(Model model,
                            @RequestParam(value="page", defaultValue="1") int page,
                            @RequestParam(value="nameKeyword", required=false) String nameKeyword) {
        // 构造分页参数，当前页号和每页大小（每页5条作为示例）
        Page<User> pageParam = new Page<>(page, 10);
        IPage<User> pageData;
        if (nameKeyword != null && !nameKeyword.isEmpty()) {
            // 按名称模糊查询分页
            pageData = userService.page(pageParam,
                    new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<User>()
                            .like("username", nameKeyword));
        } else {
            // 无搜索，直接分页查询所有
            pageData = userService.page(pageParam);
        }
        model.addAttribute("pageData", pageData);
        model.addAttribute("nameKeyword", nameKeyword);
        return "user-list";  // 对应 templates/user-list.html
    }

    // 进入编辑用户页面
    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam("id") Long id) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user-edit";  // 对应 templates/user-edit.html
    }

    // 提交新增用户（来自列表页底部表单）
    @PostMapping("/add")
    public String addUser(User user, Model model) {
        // user 对象会自动接收表单参数（username, email, password）
        userService.save(user);  // 内部已对密码加密
        return "redirect:/user/list";
    }

    // 提交更新用户
    @PostMapping("/update")
    public String updateUser(User user) {
        // 注意：如果更新了密码，也应在此对新密码加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encrypted = org.springframework.util.DigestUtils
                    .md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(encrypted);
        }
        userService.updateById(user);
        return "redirect:/user/list";
    }

    // 删除用户
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.removeById(id);
        return "redirect:/user/list";
    }
}