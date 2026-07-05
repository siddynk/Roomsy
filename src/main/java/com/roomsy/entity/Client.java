package com.roomsy.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String preferredLocation;
    private Integer budget;
    private String gender;
    private String roomType;

    // Searching / Placed / Inactive
    private String status = "Searching";

    private String pgName;
    private LocalDate moveInDate;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ── Constructors ──────────────────────────────────────────
    public Client() {}

    public Client(User user, String preferredLocation, Integer budget,
                  String gender, String roomType) {
        this.user              = user;
        this.preferredLocation = preferredLocation;
        this.budget            = budget;
        this.gender            = gender;
        this.roomType          = roomType;
        this.status            = "Searching";
        this.createdAt         = LocalDateTime.now();
    }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getPreferredLocation() { return preferredLocation; }
    public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }

    public Integer getBudget() { return budget; }
    public void setBudget(Integer budget) { this.budget = budget; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPgName() { return pgName; }
    public void setPgName(String pgName) { this.pgName = pgName; }

    public LocalDate getMoveInDate() { return moveInDate; }
    public void setMoveInDate(LocalDate moveInDate) { this.moveInDate = moveInDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
