package com.roomsy.controller;

import com.roomsy.config.SessionUtils;
import com.roomsy.entity.PgListing;
import com.roomsy.entity.User;
import com.roomsy.service.BookingService;
import com.roomsy.service.PgListingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    @Autowired private PgListingService pgListingService;
    @Autowired private BookingService   bookingService;

    // ── GUARD ─────────────────────────────────────────────────
    private User requireOwner(HttpSession session) {
        if (!SessionUtils.isOwner(session))
            throw new RuntimeException("Access denied. Please login as an owner.");
        return SessionUtils.getUser(session);
    }

    // ── DASHBOARD ─────────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User owner    = requireOwner(session);
        var myPgs     = pgListingService.getByOwner(owner);
        var myBookings = bookingService.getByOwner(owner);

        model.addAttribute("owner",          owner);
        model.addAttribute("myPgs",          myPgs);
        model.addAttribute("myBookings",     myBookings);
        model.addAttribute("totalPgs",       myPgs.size());
        model.addAttribute("pendingCount",   myBookings.stream().filter(b -> "PENDING".equals(b.getStatus())).count());
        model.addAttribute("confirmedCount", myBookings.stream().filter(b -> "CONFIRMED".equals(b.getStatus())).count());
        return "owner/dashboard";
    }

    // ── LIST NEW PG ───────────────────────────────────────────
    @GetMapping("/pg/add")
    public String addPgForm(HttpSession session, Model model) {
        requireOwner(session);
        model.addAttribute("pg", new PgListing());
        return "owner/pg-form";
    }

    @PostMapping("/pg/add")
    public String addPgSubmit(@ModelAttribute PgListing pg,
                              HttpSession session,
                              RedirectAttributes ra) {
        User owner = requireOwner(session);
        pg.setOwner(owner);
        pg.setStatus("Active");
        pgListingService.save(pg);
        ra.addFlashAttribute("successMsg", "✅ PG listed successfully!");
        return "redirect:/owner/dashboard";
    }

    // ── EDIT PG ───────────────────────────────────────────────
    @GetMapping("/pg/edit/{id}")
    public String editPgForm(@PathVariable Long id,
                             HttpSession session, Model model) {
        User owner = requireOwner(session);
        var pg = pgListingService.getById(id)
                .orElseThrow(() -> new RuntimeException("PG not found"));
        if (!pg.getOwner().getId().equals(owner.getId()))
            throw new RuntimeException("You can only edit your own PG listings.");
        model.addAttribute("pg",   pg);
        model.addAttribute("edit", true);
        return "owner/pg-form";
    }

    @PostMapping("/pg/edit/{id}")
    public String editPgSubmit(@PathVariable Long id,
                               @ModelAttribute PgListing updated,
                               HttpSession session,
                               RedirectAttributes ra) {
        User owner = requireOwner(session);
        var pg = pgListingService.getById(id)
                .orElseThrow(() -> new RuntimeException("PG not found"));
        if (!pg.getOwner().getId().equals(owner.getId()))
            throw new RuntimeException("Access denied.");

        pg.setPgName(updated.getPgName());
        pg.setLocation(updated.getLocation());
        pg.setCity(updated.getCity());
        pg.setRent(updated.getRent());
        pg.setRoomType(updated.getRoomType());
        pg.setGender(updated.getGender());
        pg.setAmenities(updated.getAmenities());
        pg.setDescription(updated.getDescription());
        pg.setPhone(updated.getPhone());
        pg.setStatus(updated.getStatus());
        pgListingService.save(pg);

        ra.addFlashAttribute("successMsg", "✅ PG listing updated!");
        return "redirect:/owner/dashboard";
    }

    // ── DELETE PG ─────────────────────────────────────────────
    @PostMapping("/pg/delete/{id}")
    public String deletePg(@PathVariable Long id,
                           HttpSession session,
                           RedirectAttributes ra) {
        User owner = requireOwner(session);
        var pg = pgListingService.getById(id)
                .orElseThrow(() -> new RuntimeException("PG not found"));
        if (!pg.getOwner().getId().equals(owner.getId()))
            throw new RuntimeException("Access denied.");
        pgListingService.delete(id);
        ra.addFlashAttribute("successMsg", "🗑️ PG listing removed.");
        return "redirect:/owner/dashboard";
    }

    // ── BOOKING REQUESTS ──────────────────────────────────────
    @GetMapping("/bookings")
    public String myBookings(HttpSession session, Model model) {
        User owner = requireOwner(session);
        var bookings = bookingService.getByOwner(owner);
        model.addAttribute("bookings", bookings);
        model.addAttribute("owner",    owner);
        return "owner/bookings";
    }

    // ── CONFIRM BOOKING ───────────────────────────────────────
    @PostMapping("/booking/confirm/{id}")
    public String confirmBooking(@PathVariable Long id,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        User owner = requireOwner(session);
        bookingService.updateStatusForOwner(owner, id, "CONFIRMED");
        ra.addFlashAttribute("successMsg", "✅ Booking confirmed!");
        return "redirect:/owner/bookings";
    }

    // ── REJECT BOOKING ────────────────────────────────────────
    @PostMapping("/booking/reject/{id}")
    public String rejectBooking(@PathVariable Long id,
                                HttpSession session,
                                RedirectAttributes ra) {
        User owner = requireOwner(session);
        bookingService.updateStatusForOwner(owner, id, "REJECTED");
        ra.addFlashAttribute("successMsg", "❌ Booking rejected.");
        return "redirect:/owner/bookings";
    }
}
