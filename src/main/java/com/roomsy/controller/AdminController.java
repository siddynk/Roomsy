package com.roomsy.controller;

import com.roomsy.config.SessionUtils;
import com.roomsy.entity.Client;
import com.roomsy.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private ClientService    clientService;
    @Autowired private PgListingService pgListingService;
    @Autowired private BookingService   bookingService;
    @Autowired private UserService      userService;

    @Value("${app.admin.email}")    private String adminEmail;
    @Value("${app.admin.password}") private String adminPassword;
    @Value("${app.admin.name}")     private String adminName;

    // ── GUARD ─────────────────────────────────────────────────
    private void requireAdmin(HttpSession session) {
        if (!SessionUtils.isAdmin(session))
            throw new RuntimeException("Admin access required.");
    }

    // ── ADMIN LOGIN ───────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (SessionUtils.isAdmin(session)) return "redirect:/admin/dashboard";
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes ra) {
        if (userService.checkAdminCredentials(email, password, adminEmail, adminPassword)) {
            SessionUtils.setAdmin(session, adminName);
            return "redirect:/admin/dashboard";
        }
        ra.addFlashAttribute("error", "Invalid admin credentials.");
        return "redirect:/admin/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtils.logout(session);
        return "redirect:/admin/login";
    }

    // ── DASHBOARD ─────────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        requireAdmin(session);
        model.addAttribute("adminName",      session.getAttribute(SessionUtils.SESSION_ADMIN));
        model.addAttribute("totalClients",   clientService.countAll());
        model.addAttribute("searching",      clientService.countSearching());
        model.addAttribute("placed",         clientService.countPlaced());
        model.addAttribute("inactive",       clientService.countInactive());
        model.addAttribute("totalPgs",       pgListingService.countAll());
        model.addAttribute("activePgs",      pgListingService.countActive());
        model.addAttribute("totalBookings",  bookingService.countAll());
        model.addAttribute("pendingBookings",bookingService.countPending());
        model.addAttribute("totalTenants",   userService.countTenants());
        model.addAttribute("totalOwners",    userService.countOwners());
        model.addAttribute("recentClients",  clientService.getAll().stream().limit(5).toList());
        model.addAttribute("recentBookings", bookingService.getAll().stream().limit(5).toList());
        return "admin/dashboard";
    }

    // ── CLIENTS ───────────────────────────────────────────────
    @GetMapping("/clients")
    public String clients(@RequestParam(required = false, defaultValue = "") String keyword,
                          @RequestParam(required = false, defaultValue = "All") String status,
                          HttpSession session, Model model) {
        requireAdmin(session);
        var clients = clientService.search(keyword, status);
        model.addAttribute("clients",        clients);
        model.addAttribute("keyword",        keyword);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("resultCount",    clients.size());
        model.addAttribute("totalClients",   clientService.countAll());
        model.addAttribute("searching",      clientService.countSearching());
        model.addAttribute("placed",         clientService.countPlaced());
        model.addAttribute("inactive",       clientService.countInactive());
        model.addAttribute("adminName",      session.getAttribute(SessionUtils.SESSION_ADMIN));
        return "admin/clients";
    }

    @GetMapping("/clients/{id}")
    public String viewClient(@PathVariable Long id,
                             HttpSession session, Model model) {
        requireAdmin(session);
        var client = clientService.getById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client",    client);
        model.addAttribute("adminName", session.getAttribute(SessionUtils.SESSION_ADMIN));
        return "admin/view-client";
    }

    @GetMapping("/clients/edit/{id}")
    public String editClientForm(@PathVariable Long id,
                                 HttpSession session, Model model) {
        requireAdmin(session);
        var client = clientService.getById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client",    client);
        model.addAttribute("adminName", session.getAttribute(SessionUtils.SESSION_ADMIN));
        return "admin/edit-client";
    }

    @PostMapping("/clients/edit/{id}")
    public String editClientSubmit(@PathVariable Long id,
                                   @ModelAttribute Client updated,
                                   HttpSession session,
                                   RedirectAttributes ra) {
        requireAdmin(session);
        clientService.update(id, updated);
        ra.addFlashAttribute("successMsg", "✅ Client updated successfully!");
        return "redirect:/admin/clients";
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable Long id,
                               HttpSession session,
                               RedirectAttributes ra) {
        requireAdmin(session);
        clientService.delete(id);
        ra.addFlashAttribute("successMsg", "🗑️ Client deleted.");
        return "redirect:/admin/clients";
    }

    // ── ALL PGS ───────────────────────────────────────────────
    @GetMapping("/pgs")
    public String allPgs(HttpSession session, Model model) {
        requireAdmin(session);
        model.addAttribute("pgs",       pgListingService.getAll());
        model.addAttribute("adminName", session.getAttribute(SessionUtils.SESSION_ADMIN));
        return "admin/pgs";
    }

    @GetMapping("/pgs/delete/{id}")
    public String deletePg(@PathVariable Long id,
                           HttpSession session,
                           RedirectAttributes ra) {
        requireAdmin(session);
        pgListingService.delete(id);
        ra.addFlashAttribute("successMsg", "🗑️ PG listing deleted.");
        return "redirect:/admin/pgs";
    }

    @GetMapping("/pgs/toggle/{id}")
    public String togglePgStatus(@PathVariable Long id,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        requireAdmin(session);
        pgListingService.toggleStatus(id);
        ra.addFlashAttribute("successMsg", "✅ PG status updated.");
        return "redirect:/admin/pgs";
    }

    // ── ALL BOOKINGS ──────────────────────────────────────────
    @GetMapping("/bookings")
    public String allBookings(HttpSession session, Model model) {
        requireAdmin(session);
        model.addAttribute("bookings",  bookingService.getAll());
        model.addAttribute("adminName", session.getAttribute(SessionUtils.SESSION_ADMIN));
        return "admin/bookings";
    }

    @GetMapping("/bookings/confirm/{id}")
    public String confirmBooking(@PathVariable Long id,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        requireAdmin(session);
        bookingService.updateStatus(id, "CONFIRMED");
        ra.addFlashAttribute("successMsg", "✅ Booking confirmed.");
        return "redirect:/admin/bookings";
    }

    @GetMapping("/bookings/reject/{id}")
    public String rejectBooking(@PathVariable Long id,
                                HttpSession session,
                                RedirectAttributes ra) {
        requireAdmin(session);
        bookingService.updateStatus(id, "REJECTED");
        ra.addFlashAttribute("successMsg", "❌ Booking rejected.");
        return "redirect:/admin/bookings";
    }

    @GetMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id,
                                HttpSession session,
                                RedirectAttributes ra) {
        requireAdmin(session);
        bookingService.delete(id);
        ra.addFlashAttribute("successMsg", "🗑️ Booking deleted.");
        return "redirect:/admin/bookings";
    }
}