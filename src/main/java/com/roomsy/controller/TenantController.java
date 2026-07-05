package com.roomsy.controller;

import com.roomsy.config.SessionUtils;
import com.roomsy.entity.User;
import com.roomsy.service.BookingService;
import com.roomsy.service.PgListingService;
import com.roomsy.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/tenant")
public class TenantController {

    @Autowired private BookingService   bookingService;
    @Autowired private PgListingService pgListingService;
    @Autowired private ReviewService    reviewService;

    // ── GUARD ─────────────────────────────────────────────────
    private User requireTenant(HttpSession session) {
        if (!SessionUtils.isTenant(session))
            throw new RuntimeException("Access denied. Please login as a tenant.");
        return SessionUtils.getUser(session);
    }

    // ── DASHBOARD ─────────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User tenant = requireTenant(session);
        var bookings = bookingService.getByTenant(tenant);
        model.addAttribute("tenant",           tenant);
        model.addAttribute("bookings",         bookings);
        model.addAttribute("totalBookings",     bookings.size());
        model.addAttribute("pendingBookings",   bookings.stream().filter(b -> "PENDING".equals(b.getStatus())).count());
        model.addAttribute("confirmedBookings", bookings.stream().filter(b -> "CONFIRMED".equals(b.getStatus())).count());
        return "tenant/dashboard";
    }

    // ── MY BOOKINGS ───────────────────────────────────────────
    @GetMapping("/bookings")
    public String myBookings(HttpSession session, Model model) {
        User tenant = requireTenant(session);
        model.addAttribute("bookings", bookingService.getByTenant(tenant));
        model.addAttribute("tenant",   tenant);
        return "tenant/bookings";
    }

    // ── BOOK A VISIT ──────────────────────────────────────────
    @GetMapping("/book/{pgId}")
    public String bookForm(@PathVariable Long pgId,
                           HttpSession session, Model model) {
        User tenant = requireTenant(session);
        var pg = pgListingService.getById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));
        model.addAttribute("pg",     pg);
        model.addAttribute("tenant", tenant);
        return "tenant/book";
    }

    @PostMapping("/book/{pgId}")
    public String bookSubmit(@PathVariable Long pgId,
                             @RequestParam String visitDate,
                             @RequestParam(required = false) String message,
                             HttpSession session,
                             Model model,
                             RedirectAttributes ra) {
        User tenant = requireTenant(session);
        var pg = pgListingService.getById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        // DATE VALIDATION
        LocalDate visit = LocalDate.parse(visitDate);
        if (!visit.isAfter(LocalDate.now())) {
            model.addAttribute("pg",     pg);
            model.addAttribute("tenant", tenant);
            model.addAttribute("error",  "❌ Visit date must be a future date. Please pick tomorrow or later.");
            return "tenant/book";
        }

        try {
            bookingService.book(tenant, pg, visit, message);
            ra.addFlashAttribute("successMsg",
                    "✅ Visit request sent to " + pg.getPgName() + "! Owner will confirm shortly.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tenant/bookings";
    }

    // ── REVIEW ────────────────────────────────────────────────
    @PostMapping("/review/{pgId}")
    public String submitReview(@PathVariable Long pgId,
                               @RequestParam int rating,
                               @RequestParam(required = false) String comment,
                               HttpSession session,
                               RedirectAttributes ra) {
        User tenant = requireTenant(session);
        var pg = pgListingService.getById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));
        try {
            reviewService.addReview(tenant, pg, rating, comment);
            ra.addFlashAttribute("successMsg", "✅ Review submitted successfully!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pg/" + pgId;
    }
}
