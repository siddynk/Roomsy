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
//            if (userRepo.count() > 0) return;

            // ── Owners ────────────────────────────────────────
            User o1 = userRepo.save(new User("Rajan Mehta", "rajan@gmail.com",
                    encoder.encode("password123"), "9876543210", "OWNER"));
            User o2 = userRepo.save(new User("Sunita Reddy", "sunita@gmail.com",
                    encoder.encode("password123"), "9845678901", "OWNER"));
            User o3 = userRepo.save(new User("Rahul Sharma", "rahul@gmail.com",
                    encoder.encode("password123"), "9876501111", "OWNER"));

            User o4 = userRepo.save(new User("Amit Kumar", "amit@gmail.com",
                    encoder.encode("password123"), "9876502222", "OWNER"));

            User o5 = userRepo.save(new User("Kavya Rao", "kavya@gmail.com",
                    encoder.encode("password123"), "9876503333", "OWNER"));
            User o6 = userRepo.save(new User("Nikhil Joshi", "nikhil@gmail.com",
                    encoder.encode("password123"), "9876511111", "OWNER"));

            User o7 = userRepo.save(new User("Meera Kapoor", "meera@gmail.com",
                    encoder.encode("password123"), "9876522222", "OWNER"));

            User o8 = userRepo.save(new User("Suresh Patel", "suresh@gmail.com",
                    encoder.encode("password123"), "9876533333", "OWNER"));
            User o9 = userRepo.save(new User("Deepak Shetty", "deepak@gmail.com",
                    encoder.encode("password123"), "9876541111", "OWNER"));

            User o10 = userRepo.save(new User("Ananya Rao", "ananya@gmail.com",
                    encoder.encode("password123"), "9876542222", "OWNER"));

            User o11 = userRepo.save(new User("Vivek Ramesh", "vivek@gmail.com",
                    encoder.encode("password123"), "9876543333", "OWNER"));

            // ── Tenants ───────────────────────────────────────
            User t1 = userRepo.save(new User("Priya Sharma", "priya@gmail.com",
                    encoder.encode("password123"), "9812345670", "TENANT"));
            User t2 = userRepo.save(new User("Rohit Verma", "rohit@gmail.com",
                    encoder.encode("password123"), "9765432109", "TENANT"));
            User t3 = userRepo.save(new User("Anjali Nair", "anjali@gmail.com",
                    encoder.encode("password123"), "9654321098", "TENANT"));
            User t4 = userRepo.save(new User("Neha Gupta", "neha@gmail.com",
                    encoder.encode("password123"), "9811111111", "TENANT"));

            User t5 = userRepo.save(new User("Arjun Singh", "arjun@gmail.com",
                    encoder.encode("password123"), "9822222222", "TENANT"));

            User t6 = userRepo.save(new User("Pooja Menon", "pooja@gmail.com",
                    encoder.encode("password123"), "9833333333", "TENANT"));

            User t7 = userRepo.save(new User("Vikram Patel", "vikram@gmail.com",
                    encoder.encode("password123"), "9844444444", "TENANT"));

            User t8 = userRepo.save(new User("Sneha Iyer", "sneha@gmail.com",
                    encoder.encode("password123"), "9855555555", "TENANT"));
            User t9 = userRepo.save(new User("Aditya Rao", "aditya@gmail.com",
                    encoder.encode("password123"), "9811112222", "TENANT"));

            User t10 = userRepo.save(new User("Riya Das", "riya@gmail.com",
                    encoder.encode("password123"), "9811113333", "TENANT"));

            User t11 = userRepo.save(new User("Harsh Jain", "harsh@gmail.com",
                    encoder.encode("password123"), "9811114444", "TENANT"));

            User t12 = userRepo.save(new User("Ishita Sen", "ishita@gmail.com",
                    encoder.encode("password123"), "9811115555", "TENANT"));

            User t13 = userRepo.save(new User("Karan Malhotra", "karan@gmail.com",
                    encoder.encode("password123"), "9811116666", "TENANT"));
            User t14 = userRepo.save(new User("Rahul Verma", "rahulv@gmail.com",
                    encoder.encode("password123"), "9801111111", "TENANT"));

            User t15 = userRepo.save(new User("Divya Menon", "divya@gmail.com",
                    encoder.encode("password123"), "9802222222", "TENANT"));

            User t16 = userRepo.save(new User("Sanjay Kulkarni", "sanjay@gmail.com",
                    encoder.encode("password123"), "9803333333", "TENANT"));

            User t17 = userRepo.save(new User("Nisha Thomas", "nisha@gmail.com",
                    encoder.encode("password123"), "9804444444", "TENANT"));

            User t18 = userRepo.save(new User("Akhil Nair", "akhil@gmail.com",
                    encoder.encode("password123"), "9805555555", "TENANT"));


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

            PgListing pg5 = new PgListing();
            pg5.setOwner(o3);
            pg5.setPgName("Skyline Residency");
            pg5.setLocation("Whitefield");
            pg5.setCity("Bengaluru");
            pg5.setRent(10500);
            pg5.setRoomType("Single");
            pg5.setGender("Any");
            pg5.setAmenities("WiFi,Food,Gym,Laundry,CCTV");
            pg5.setDescription("Modern co-living near ITPL with premium amenities.");
            pg5.setPhone("9876501111");
            pg5.setStatus("Active");
            pg5.setPhotoUrl("https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800&q=80");
            pgRepo.save(pg5);

            PgListing pg6 = new PgListing();
            pg6.setOwner(o4);
            pg6.setPgName("Comfort Stay PG");
            pg6.setLocation("Electronic City");
            pg6.setCity("Bengaluru");
            pg6.setRent(7800);
            pg6.setRoomType("Double");
            pg6.setGender("Male");
            pg6.setAmenities("WiFi,Food,CCTV,Parking");
            pg6.setDescription("Budget-friendly PG close to major IT companies.");
            pg6.setPhone("9876502222");
            pg6.setStatus("Active");
            pg6.setPhotoUrl("https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800&q=80");
            pgRepo.save(pg6);

            PgListing pg7 = new PgListing();
            pg7.setOwner(o5);
            pg7.setPgName("Elite Women's Hostel");
            pg7.setLocation("Jayanagar");
            pg7.setCity("Bengaluru");
            pg7.setRent(9800);
            pg7.setRoomType("Single");
            pg7.setGender("Female");
            pg7.setAmenities("WiFi,Food,Housekeeping,Laundry,CCTV");
            pg7.setDescription("Safe and secure accommodation for students and professionals.");
            pg7.setPhone("9876503333");
            pg7.setStatus("Active");
            pg7.setPhotoUrl("https://images.unsplash.com/photo-1484154218962-a197022b5858?w=800&q=80");
            pgRepo.save(pg7);

            PgListing pg8 = new PgListing();
            pg8.setOwner(o3);
            pg8.setPgName("Metro Living PG");
            pg8.setLocation("BTM Layout");
            pg8.setCity("Bengaluru");
            pg8.setRent(8900);
            pg8.setRoomType("Double");
            pg8.setGender("Any");
            pg8.setAmenities("WiFi,Power Backup,Parking,CCTV");
            pg8.setDescription("Comfortable rooms near BTM with metro connectivity.");
            pg8.setPhone("9876501111");
            pg8.setStatus("Active");
            pg8.setPhotoUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80");
            pgRepo.save(pg8);
            PgListing pg9 = new PgListing();
            pg9.setOwner(o6);
            pg9.setPgName("LakeView Residency");
            pg9.setLocation("Bellandur");
            pg9.setCity("Bengaluru");
            pg9.setRent(11000);
            pg9.setRoomType("Single");
            pg9.setGender("Any");
            pg9.setAmenities("WiFi,Food,Parking,Gym,CCTV");
            pg9.setDescription("Premium PG close to Bellandur tech parks.");
            pg9.setPhone("9876511111");
            pg9.setStatus("Active");
            pg9.setPhotoUrl("https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800&q=80");
            pgRepo.save(pg9);

            PgListing pg10 = new PgListing();
            pg10.setOwner(o7);
            pg10.setPgName("Silver Oak PG");
            pg10.setLocation("Hebbal");
            pg10.setCity("Bengaluru");
            pg10.setRent(9200);
            pg10.setRoomType("Double");
            pg10.setGender("Female");
            pg10.setAmenities("WiFi,Food,Laundry,CCTV");
            pg10.setDescription("Comfortable PG with peaceful surroundings.");
            pg10.setPhone("9876522222");
            pg10.setStatus("Active");
            pg10.setPhotoUrl("https://images.unsplash.com/photo-1484154218962-a197022b5858?w=800&q=80");
            pgRepo.save(pg10);

            PgListing pg11 = new PgListing();
            pg11.setOwner(o8);
            pg11.setPgName("Metro Comfort PG");
            pg11.setLocation("JP Nagar");
            pg11.setCity("Bengaluru");
            pg11.setRent(8700);
            pg11.setRoomType("Triple");
            pg11.setGender("Male");
            pg11.setAmenities("WiFi,CCTV,Parking");
            pg11.setDescription("Affordable accommodation near JP Nagar Metro.");
            pg11.setPhone("9876533333");
            pg11.setStatus("Active");
            pg11.setPhotoUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80");
            pgRepo.save(pg11);

            PgListing pg12 = new PgListing();
            pg12.setOwner(o6);
            pg12.setPgName("Elite Homes");
            pg12.setLocation("Yelahanka");
            pg12.setCity("Bengaluru");
            pg12.setRent(9800);
            pg12.setRoomType("Single");
            pg12.setGender("Any");
            pg12.setAmenities("WiFi,Food,Housekeeping,CCTV");
            pg12.setDescription("Spacious rooms with premium facilities.");
            pg12.setPhone("9876511111");
            pg12.setStatus("Active");
            pg12.setPhotoUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=800&q=80");
            pgRepo.save(pg12);

            PgListing pg13 = new PgListing();
            pg13.setOwner(o7);
            pg13.setPgName("Royal Stay PG");
            pg13.setLocation("Rajajinagar");
            pg13.setCity("Bengaluru");
            pg13.setRent(9500);
            pg13.setRoomType("Double");
            pg13.setGender("Female");
            pg13.setAmenities("WiFi,Food,Parking,Power Backup");
            pg13.setDescription("Premium women's PG in central Bengaluru.");
            pg13.setPhone("9876522222");
            pg13.setStatus("Active");
            pg13.setPhotoUrl("https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800&q=80");
            pgRepo.save(pg13);
            PgListing pg14 = new PgListing();
            pg14.setOwner(o9);
            pg14.setPgName("Whitefield Elite Residency");
            pg14.setLocation("Whitefield");
            pg14.setCity("Bengaluru");
            pg14.setRent(14500);
            pg14.setRoomType("Single");
            pg14.setGender("Any");
            pg14.setAmenities("WiFi,Food,AC,Gym,Laundry,Parking,Power Backup,CCTV");
            pg14.setDescription("Luxury PG near ITPL with premium furnished rooms.");
            pg14.setPhone("9876541111");
            pg14.setStatus("Active");
            pg14.setPhotoUrl("https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800&q=80");
            pgRepo.save(pg14);

            PgListing pg15 = new PgListing();
            pg15.setOwner(o10);
            pg15.setPgName("Koramangala Comfort Stay");
            pg15.setLocation("Koramangala");
            pg15.setCity("Bengaluru");
            pg15.setRent(9800);
            pg15.setRoomType("Double");
            pg15.setGender("Female");
            pg15.setAmenities("WiFi,Food,CCTV,Laundry");
            pg15.setDescription("Comfortable PG for women near restaurants and metro.");
            pg15.setPhone("9876542222");
            pg15.setStatus("Active");
            pg15.setPhotoUrl("https://images.unsplash.com/photo-1484154218962-a197022b5858?w=800&q=80");
            pgRepo.save(pg15);

            PgListing pg16 = new PgListing();
            pg16.setOwner(o11);
            pg16.setPgName("Student Hub PG");
            pg16.setLocation("Banashankari");
            pg16.setCity("Bengaluru");
            pg16.setRent(6800);
            pg16.setRoomType("Triple");
            pg16.setGender("Male");
            pg16.setAmenities("WiFi,Study Room,CCTV,RO Water");
            pg16.setDescription("Budget-friendly PG for students near engineering colleges.");
            pg16.setPhone("9876543333");
            pg16.setStatus("Active");
            pg16.setPhotoUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80");
            pgRepo.save(pg16);

            PgListing pg17 = new PgListing();
            pg17.setOwner(o9);
            pg17.setPgName("Tech City Residency");
            pg17.setLocation("Electronic City");
            pg17.setCity("Bengaluru");
            pg17.setRent(11900);
            pg17.setRoomType("Single");
            pg17.setGender("Any");
            pg17.setAmenities("WiFi,Food,Gym,Housekeeping,Parking");
            pg17.setDescription("Premium accommodation for software professionals.");
            pg17.setPhone("9876541111");
            pg17.setStatus("Active");
            pg17.setPhotoUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=800&q=80");
            pgRepo.save(pg17);

            PgListing pg18 = new PgListing();
            pg18.setOwner(o10);
            pg18.setPgName("HSR Urban Living");
            pg18.setLocation("HSR Layout");
            pg18.setCity("Bengaluru");
            pg18.setRent(12500);
            pg18.setRoomType("Single");
            pg18.setGender("Any");
            pg18.setAmenities("WiFi,Food,Parking,Laundry,Power Backup");
            pg18.setDescription("Modern co-living space with premium interiors.");
            pg18.setPhone("9876542222");
            pg18.setStatus("Active");
            pg18.setPhotoUrl("https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800&q=80");
            pgRepo.save(pg18);

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
            bookingRepo.save(new Booking(t4, pg5, LocalDate.now().plusDays(4),
                    "Interested in moving next week."));

            bookingRepo.save(new Booking(t5, pg6, LocalDate.now().plusDays(2),
                    "Need accommodation near office."));

            bookingRepo.save(new Booking(t6, pg7, LocalDate.now().plusDays(5),
                    "Looking for a safe ladies PG."));

            bookingRepo.save(new Booking(t7, pg8, LocalDate.now().plusDays(1),
                    "Can I visit tomorrow evening?"));

            bookingRepo.save(new Booking(t8, pg5, LocalDate.now().plusDays(6),
                    "Need a furnished single room."));
            bookingRepo.save(new Booking(t9, pg9,
                    LocalDate.now().plusDays(3),
                    "Interested in premium single room."));

            bookingRepo.save(new Booking(t10, pg10,
                    LocalDate.now().plusDays(4),
                    "Looking for ladies PG near office."));

            bookingRepo.save(new Booking(t11, pg11,
                    LocalDate.now().plusDays(2),
                    "Need accommodation this week."));

            bookingRepo.save(new Booking(t12, pg12,
                    LocalDate.now().plusDays(5),
                    "Can I schedule a visit?"));

            bookingRepo.save(new Booking(t13, pg13,
                    LocalDate.now().plusDays(6),
                    "Looking for double sharing room."));
            bookingRepo.save(new Booking(t14, pg14,
                    LocalDate.now().plusDays(3),
                    "Looking for premium accommodation near ITPL."));

            bookingRepo.save(new Booking(t15, pg15,
                    LocalDate.now().plusDays(6),
                    "Need ladies PG immediately."));

            bookingRepo.save(new Booking(t16, pg16,
                    LocalDate.now().plusDays(2),
                    "Student looking for budget room."));

            bookingRepo.save(new Booking(t17, pg17,
                    LocalDate.now().plusDays(5),
                    "Can I visit this weekend?"));

            bookingRepo.save(new Booking(t18, pg18,
                    LocalDate.now().plusDays(7),
                    "Interested in single sharing."));

            // ── Clients (admin view) ──────────────────────────
            Client c1 = new Client(t1, "Koramangala, Bengaluru", 9000, "Female", "Single");
            c1.setStatus("Placed");
            c1.setPgName("Sunshine Girls PG");
            c1.setMoveInDate(LocalDate.of(2025, 6, 1));
            clientRepo.save(c1);
            Client c4 = new Client(t4, "Whitefield", 11000, "Female", "Single");
            c4.setStatus("Searching");
            clientRepo.save(c4);

            Client c5 = new Client(t5, "Electronic City", 8000, "Male", "Double");
            c5.setStatus("Searching");
            clientRepo.save(c5);

            Client c6 = new Client(t6, "Jayanagar", 10000, "Female", "Single");
            c6.setStatus("Placed");
            c6.setPgName("Elite Women's Hostel");
            c6.setMoveInDate(LocalDate.now().plusDays(10));
            clientRepo.save(c6);

            Client c7 = new Client(t7, "BTM Layout", 9000, "Male", "Double");
            c7.setStatus("Searching");
            clientRepo.save(c7);

            Client c8 = new Client(t8, "Whitefield", 11000, "Female", "Single");
            c8.setStatus("Searching");
            clientRepo.save(c8);
            Client c9 = new Client(t9, "Bellandur", 11000, "Male", "Single");
            c9.setStatus("Searching");
            clientRepo.save(c9);

            Client c10 = new Client(t10, "Hebbal", 9500, "Female", "Double");
            c10.setStatus("Placed");
            c10.setPgName("Silver Oak PG");
            c10.setMoveInDate(LocalDate.now().plusDays(12));
            clientRepo.save(c10);

            Client c11 = new Client(t11, "JP Nagar", 9000, "Male", "Triple");
            c11.setStatus("Searching");
            clientRepo.save(c11);

            Client c12 = new Client(t12, "Yelahanka", 10000, "Female", "Single");
            c12.setStatus("Searching");
            clientRepo.save(c12);

            Client c13 = new Client(t13, "Rajajinagar", 9500, "Male", "Double");
            c13.setStatus("Searching");
            clientRepo.save(c13);
            Client c14 = new Client(t14, "Whitefield", 15000, "Male", "Single");
            c14.setStatus("Searching");
            clientRepo.save(c14);

            Client c15 = new Client(t15, "Koramangala", 10000, "Female", "Double");
            c15.setStatus("Placed");
            c15.setPgName("Koramangala Comfort Stay");
            c15.setMoveInDate(LocalDate.now().plusDays(8));
            clientRepo.save(c15);

            Client c16 = new Client(t16, "Banashankari", 7000, "Male", "Triple");
            c16.setStatus("Searching");
            clientRepo.save(c16);

            Client c17 = new Client(t17, "Electronic City", 12000, "Female", "Single");
            c17.setStatus("Searching");
            clientRepo.save(c17);

            Client c18 = new Client(t18, "HSR Layout", 13000, "Male", "Single");
            c18.setStatus("Searching");
            clientRepo.save(c18);

            clientRepo.save(new Client(t2, "HSR Layout, Bengaluru", 12000, "Male", "Single"));
            clientRepo.save(new Client(t3, "Indiranagar, Bengaluru", 7000, "Female", "Double"));

            System.out.println("✅ Roomsy: Sample data loaded successfully!");
        };
    }
}
