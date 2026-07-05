package com.roomsy.controller;

import com.roomsy.config.SessionUtils;
import com.roomsy.entity.User;
import com.roomsy.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired private UserService userService;

    // ── LOGIN PAGE ────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(HttpSession session,
                            @RequestParam(required = false) String error,
                            Model model) {
        if (SessionUtils.isLoggedIn(session)) {
            return redirectByRole(SessionUtils.getUser(session));
        }
        if (error != null) model.addAttribute("error", "Invalid email or password.");
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes ra) {
        return userService.login(email, password).map(user -> {
            SessionUtils.setUser(session, user);
            return redirectByRole(user);
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "Invalid email or password.");
            return "redirect:/login";
        });
    }

    // ── REGISTER PAGE ─────────────────────────────────────────
    @GetMapping("/register")
    public String registerPage(HttpSession session,
                               @RequestParam(required = false, defaultValue = "TENANT") String role,
                               Model model) {
        if (SessionUtils.isLoggedIn(session)) {
            return redirectByRole(SessionUtils.getUser(session));
        }
        model.addAttribute("selectedRole", role.toUpperCase());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String phone,
                                 @RequestParam String role,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        String fullName = firstName.trim() + " " + lastName.trim();
        try {
            User user = userService.register(fullName, email, password, phone, role.toUpperCase());
            SessionUtils.setUser(session, user);
            ra.addFlashAttribute("successMsg", "Welcome to Roomsy, " + fullName + "! 🎉");
            return redirectByRole(user);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    // ── LOGOUT ────────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtils.logout(session);
        return "redirect:/";
    }

    // ── HELPER ───────────────────────────────────────────────
    private String redirectByRole(User user) {
        return switch (user.getRole()) {
            case "OWNER"  -> "redirect:/owner/dashboard";
            case "TENANT" -> "redirect:/tenant/dashboard";
            default       -> "redirect:/";
        };
    }
}
