package com.jcca.fefa.config;
/**
* @description: 
* @author: sophia
* @create: 2025/12/03 17:40
**/

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object loggedUser = request.getSession().getAttribute("loggedUser");
        if (loggedUser != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}