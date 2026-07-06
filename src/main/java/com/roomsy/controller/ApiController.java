package com.roomsy.controller;

import com.roomsy.entity.*;
import com.roomsy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired private PgListingService pgListingService;
    @Autowired private BookingService   bookingService;
    @Autowired private ClientService    clientService;
    @Autowired private UserService      userService;

    // ── PG LISTINGS ───────────────────────────────────────────
    @GetMapping("/pgs")
    public ResponseEntity<List<PgListing>> getAllPgs() {
        return ResponseEntity.ok(pgListingService.getAllActive());
    }

    @GetMapping("/pgs/{id}")
    public ResponseEntity<PgListing> getPgById(@PathVariable Long id) {
        return pgListingService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pgs/search")
    public ResponseEntity<List<PgListing>> searchPgs(@RequestParam String q) {
        return ResponseEntity.ok(pgListingService.search(q));
    }

    // ── BOOKINGS ──────────────────────────────────────────────
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAll());
    }

    // ── CLIENTS ───────────────────────────────────────────────
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,
                                               @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Map<String, String>> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        Map<String, String> res = new HashMap<>();
        res.put("message", "Client deleted successfully");
        return ResponseEntity.ok(res);
    }

    // ── STATS ─────────────────────────────────────────────────
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalPgs",      pgListingService.countAll());
        stats.put("activePgs",     pgListingService.countActive());
        stats.put("totalClients",  clientService.countAll());
        stats.put("placed",        clientService.countPlaced());
        stats.put("searching",     clientService.countSearching());
        stats.put("totalBookings", bookingService.countAll());
        stats.put("pending",       bookingService.countPending());
        stats.put("confirmed",     bookingService.countConfirmed());
        stats.put("tenants",       userService.countTenants());
        stats.put("owners",        userService.countOwners());
        return ResponseEntity.ok(stats);
    }
}
