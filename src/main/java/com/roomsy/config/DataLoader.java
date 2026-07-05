package com.roomsy.config;

import com.roomsy.entity.*;
import com.roomsy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Autowired private UserRepository    userRepo;
    @Autowired private PgListingRepository pgRepo;
    @Autowired private BookingRepository  bookingRepo;
    @Autowired private ClientRepository   clientRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner seed() {
        return args -> {
            if (userRepo.count() > 0) return;

            // ── Owners ────────────────────────────────────────
            User o1 = userRepo.save(new User("Rajan Mehta", "rajan@gmail.com",
                    encoder.encode("password123"), "9876543210", "OWNER"));
            User o2 = userRepo.save(new User("Sunita Reddy", "sunita@gmail.com",
                    encoder.encode("password123"), "9845678901", "OWNER"));

            // ── Tenants ───────────────────────────────────────
            User t1 = userRepo.save(new User("Priya Sharma", "priya@gmail.com",
                    encoder.encode("password123"), "9812345670", "TENANT"));
            User t2 = userRepo.save(new User("Rohit Verma", "rohit@gmail.com",
                    encoder.encode("password123"), "9765432109", "TENANT"));
            User t3 = userRepo.save(new User("Anjali Nair", "anjali@gmail.com",
                    encoder.encode("password123"), "9654321098", "TENANT"));

            // ── PG Listings ───────────────────────────────────
            PgListing pg1 = new PgListing();
            pg1.setOwner(o1);
            pg1.setPgName("Sunshine Girls PG");
            pg1.setLocation("5th Block, Koramangala");
            pg1.setCity("Bengaluru");
            pg1.setRent(8500);
            pg1.setRoomType("Single");
            pg1.setGender("Female");
            pg1.setAmenities("WiFi,Food,AC,Laundry,CCTV");
            pg1.setDescription("A safe and comfortable PG for working women and students. Home-cooked food, high-speed WiFi, and 24/7 security.");
            pg1.setPhone("9876543210");
            pg1.setStatus("Active");
            pg1.setPhotoUrl("https://images.unsplash.com/photo-1555854877-bab0e564b8d5?w=800&q=80");
            pgRepo.save(pg1);

            PgListing pg2 = new PgListing();
            pg2.setOwner(o1);
            pg2.setPgName("Urban Nest Co-living");
            pg2.setLocation("Sector 2, HSR Layout");
            pg2.setCity("Bengaluru");
            pg2.setRent(12000);
            pg2.setRoomType("Single");
            pg2.setGender("Any");
            pg2.setAmenities("WiFi,Food,AC,Gym,Parking,Attached Bath,Housekeeping");
            pg2.setDescription("Premium co-living space for working professionals. Fully furnished rooms with attached bathrooms.");
            pg2.setPhone("9876543210");
            pg2.setStatus("Active");
            pg2.setPhotoUrl("https://images.unsplash.com/photo-1484154218962-a197022b5858?w=800&q=80");
            pgRepo.save(pg2);

            PgListing pg3 = new PgListing();
            pg3.setOwner(o2);
            pg3.setPgName("GreenLeaf Boys PG");
            pg3.setLocation("12th Main, Indiranagar");
            pg3.setCity("Bengaluru");
            pg3.setRent(7000);
            pg3.setRoomType("Double");
            pg3.setGender("Male");
            pg3.setAmenities("WiFi,Attached Bath,Housekeeping,CCTV");
            pg3.setDescription("Affordable and clean PG for boys near Indiranagar metro.");
            pg3.setPhone("9845678901");
            pg3.setStatus("Active");
            pg3.setPhotoUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80");
            pgRepo.save(pg3);

            PgListing pg4 = new PgListing();
            pg4.setOwner(o2);
            pg4.setPgName("TechPark Residency");
            pg4.setLocation("Near ORR, Marathahalli");
            pg4.setCity("Bengaluru");
            pg4.setRent(9000);
            pg4.setRoomType("Double");
            pg4.setGender("Male");
            pg4.setAmenities("WiFi,Food,Parking,CCTV,Power Backup");
            pg4.setDescription("Ideal for IT professionals working in Marathahalli tech parks.");
            pg4.setPhone("9845678901");
            pg4.setStatus("Active");
            pg4.setPhotoUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=800&q=80");
            pgRepo.save(pg4);

            // ── Bookings ──────────────────────────────────────
            bookingRepo.save(new Booking(t1, pg1,
                    LocalDate.now().plusDays(3),
                    "I am a working professional looking for a safe PG. Would like to visit this weekend."));

            bookingRepo.save(new Booking(t2, pg2,
                    LocalDate.now().plusDays(5),
                    "Interested in the single room. Please confirm availability."));

            bookingRepo.save(new Booking(t3, pg3,
                    LocalDate.now().plusDays(2),
                    "Can I visit tomorrow evening?"));

            // ── Clients (admin view) ──────────────────────────
            Client c1 = new Client(t1, "Koramangala, Bengaluru", 9000, "Female", "Single");
            c1.setStatus("Placed");
            c1.setPgName("Sunshine Girls PG");
            c1.setMoveInDate(LocalDate.of(2025, 6, 1));
            clientRepo.save(c1);

            clientRepo.save(new Client(t2, "HSR Layout, Bengaluru", 12000, "Male", "Single"));
            clientRepo.save(new Client(t3, "Indiranagar, Bengaluru", 7000, "Female", "Double"));

            System.out.println("✅ Roomsy: Sample data loaded successfully!");
        };
    }
}
