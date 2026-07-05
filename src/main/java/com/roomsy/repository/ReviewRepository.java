package com.roomsy.repository;

import com.roomsy.entity.PgListing;
import com.roomsy.entity.Review;
import com.roomsy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPgListingOrderByCreatedAtDesc(PgListing pg);

    List<Review> findByTenant(User tenant);

    Optional<Review> findByTenantAndPgListing(User tenant, PgListing pg);

    boolean existsByTenantAndPgListing(User tenant, PgListing pg);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.pgListing = :pg")
    Double findAvgRatingByPg(@Param("pg") PgListing pg);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.pgListing = :pg")
    Long countByPg(@Param("pg") PgListing pg);

    // Latest reviews for homepage testimonials
    List<Review> findTop6ByOrderByCreatedAtDesc();
}
