package com.roomsy.config;

import com.roomsy.entity.User;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static final String SESSION_USER  = "loggedInUser";
    public static final String SESSION_ADMIN = "loggedInAdmin";
    public static final String SESSION_ROLE  = "userRole";

    public static User getUser(HttpSession session) {
        return (User) session.getAttribute(SESSION_USER);
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(SESSION_USER) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        return session.getAttribute(SESSION_ADMIN) != null;
    }

    public static boolean isTenant(HttpSession session) {
        User u = getUser(session);
        return u != null && "TENANT".equals(u.getRole());
    }

    public static boolean isOwner(HttpSession session) {
        User u = getUser(session);
        return u != null && "OWNER".equals(u.getRole());
    }

    public static void setUser(HttpSession session, User user) {
        user.setPassword(null);
        session.setAttribute(SESSION_USER, user);
        session.setAttribute(SESSION_ROLE, user.getRole());
    }

    public static void setAdmin(HttpSession session, String adminName) {
        session.setAttribute(SESSION_ADMIN, adminName);
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }
}
