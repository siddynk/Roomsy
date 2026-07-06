package com.roomsy.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class ApiAuthInterceptor implements HandlerInterceptor {

    /** Public read-only PG endpoints — everything else under /api requires admin. */
    private boolean isPublicPgEndpoint(HttpServletRequest request) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) return false;
        String path = request.getRequestURI();
        if ("/api/pgs".equals(path)) return true;
        if (path.startsWith("/api/pgs/search")) return true;
        return path.matches("/api/pgs/\\d+");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (isPublicPgEndpoint(request)) return true;

        HttpSession session = request.getSession(false);
        if (SessionUtils.isAdmin(session)) return true;

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Admin authentication required\"}");
        return false;
    }
}
