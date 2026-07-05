package com.roomsy.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @ManyToOne
    @JoinColumn(name = "pg_id", nullable = false)
    private PgListing pgListing;

    @Column(nullable = false)
    private LocalDate visitDate;

    @Column(length = 500)
    private String message;

    // PENDING / CONFIRMED / REJECTED
    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ── Constructors ──────────────────────────────────────────
    public Booking() {}

    public Booking(User tenant, PgListing pgListing,
                   LocalDate visitDate, String message) {
        this.tenant     = tenant;
        this.pgListing  = pgListing;
        this.visitDate  = visitDate;
        this.message    = message;
        this.status     = "PENDING";
        this.createdAt  = LocalDateTime.now();
    }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getTenant() { return tenant; }
    public void setTenant(User tenant) { this.tenant = tenant; }

    public PgListing getPgListing() { return pgListing; }
    public void setPgListing(PgListing pgListing) { this.pgListing = pgListing; }

    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
