package com.roomsy.repository;

import com.roomsy.entity.Booking;
import com.roomsy.entity.PgListing;
import com.roomsy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTenant(User tenant);
    List<Booking> findByTenantOrderByCreatedAtDesc(User tenant);
    List<Booking> findByPgListing(PgListing pgListing);
    List<Booking> findByPgListingOwner(User owner);
    List<Booking> findByStatus(String status);
    long countByStatus(String status);
    boolean existsByTenantAndPgListing(User tenant, PgListing pgListing);
}
