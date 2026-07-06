package com.roomsy.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();

        if (path.startsWith("/admin")) {
            if (SessionUtils.isAdmin(session)) return true;
            response.sendRedirect("/admin/login");
            return false;
        }
        if (path.startsWith("/owner")) {
            if (SessionUtils.isOwner(session)) return true;
            response.sendRedirect("/login?error=owner");
            return false;
        }
        if (path.startsWith("/tenant")) {
            if (SessionUtils.isTenant(session)) return true;
            response.sendRedirect("/login?error=tenant");
            return false;
        }
        return true;
    }
}
