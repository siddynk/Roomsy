package com.roomsy.controller;

import com.roomsy.config.SessionUtils;
import com.roomsy.entity.User;
import com.roomsy.service.PgListingService;
import com.roomsy.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PublicController {

    @Autowired private PgListingService pgListingService;
    @Autowired private ReviewService    reviewService;

    // ── HOME ──────────────────────────────────────────────────
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        model.addAttribute("featuredPgs",    pgListingService.getAllActive());
        model.addAttribute("latestReviews",  reviewService.getLatestReviews());
        model.addAttribute("isLoggedIn",     SessionUtils.isLoggedIn(session));
        model.addAttribute("currentUser",    SessionUtils.getUser(session));
        return "public/home";
    }

    // ── BROWSE ALL PGS ────────────────────────────────────────
    @GetMapping("/listings")
    public String listings(@RequestParam(required = false) String city,
                           @RequestParam(required = false) String gender,
                           @RequestParam(required = false) String roomType,
                           @RequestParam(required = false) Integer maxRent,
                           @RequestParam(required = false) String query,
                           HttpSession session, Model model) {

        var pgs = (query != null && !query.isBlank())
                ? pgListingService.search(query)
                : pgListingService.filter(city, gender, roomType, maxRent);

        model.addAttribute("pgs",         pgs);
        model.addAttribute("resultCount", pgs.size());
        model.addAttribute("city",        city);
        model.addAttribute("gender",      gender);
        model.addAttribute("roomType",    roomType);
        model.addAttribute("maxRent",     maxRent);
        model.addAttribute("query",       query);
        model.addAttribute("isLoggedIn",  SessionUtils.isLoggedIn(session));
        model.addAttribute("currentUser", SessionUtils.getUser(session));
        return "public/listings";
    }

    // ── PG DETAIL ─────────────────────────────────────────────
    @GetMapping("/pg/{id}")
    public String pgDetail(@PathVariable Long id,
                           HttpSession session, Model model) {
        var pg = pgListingService.getById(id)
                .orElseThrow(() -> new RuntimeException("PG not found: " + id));

        User tenant = SessionUtils.getUser(session);
        boolean isTenant = SessionUtils.isTenant(session);

        model.addAttribute("pg",           pg);
        model.addAttribute("isLoggedIn",   SessionUtils.isLoggedIn(session));
        model.addAttribute("isTenant",     isTenant);
        model.addAttribute("currentUser",  tenant);
        model.addAttribute("reviews",      reviewService.getReviewsForPg(pg));
        model.addAttribute("avgRating",    reviewService.getAvgRating(pg));
        model.addAttribute("reviewCount",  reviewService.getReviewCount(pg));
        model.addAttribute("canReview",    isTenant && reviewService.canReview(tenant, pg));
        model.addAttribute("hasReviewed",  isTenant && reviewService.hasReviewed(tenant, pg));
        return "public/pg-detail";
    }
}
