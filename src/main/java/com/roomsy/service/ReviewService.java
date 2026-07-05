package com.roomsy.service;

import com.roomsy.entity.PgListing;
import com.roomsy.entity.Review;
import com.roomsy.entity.User;
import com.roomsy.repository.BookingRepository;
import com.roomsy.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    @Autowired private ReviewRepository reviewRepo;
    @Autowired private BookingRepository bookingRepo;

    /**
     * Tenant can review if:
     * 1. They have a non-REJECTED booking for this PG
     * 2. The visit date has already passed
     * 3. They haven't already reviewed this PG
     *
     * Owner approval (CONFIRMED/PENDING) does NOT control review eligibility —
     * this prevents owners from silencing negative reviews by rejecting bookings.
     */
    public boolean canReview(User tenant, PgListing pg) {
        boolean visitDatePassed = bookingRepo
                .findByTenantOrderByCreatedAtDesc(tenant)
                .stream()
                .anyMatch(b -> b.getPgListing().getId().equals(pg.getId())
                        && !"REJECTED".equals(b.getStatus())
                        && b.getVisitDate().isBefore(LocalDate.now()));
        boolean alreadyReviewed = reviewRepo.existsByTenantAndPgListing(tenant, pg);
        return visitDatePassed && !alreadyReviewed;
    }

    public boolean hasReviewed(User tenant, PgListing pg) {
        return reviewRepo.existsByTenantAndPgListing(tenant, pg);
    }

    public void addReview(User tenant, PgListing pg, int rating, String comment) {
        if (!canReview(tenant, pg))
            throw new RuntimeException("You can only review a PG after your visit date has passed.");
        if (rating < 1 || rating > 5)
            throw new RuntimeException("Rating must be between 1 and 5.");
        reviewRepo.save(new Review(tenant, pg, rating, comment));
    }

    public List<Review> getReviewsForPg(PgListing pg) {
        return reviewRepo.findByPgListingOrderByCreatedAtDesc(pg);
    }

    public Double getAvgRating(PgListing pg) {
        Double avg = reviewRepo.findAvgRatingByPg(pg);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : null;
    }

    public Long getReviewCount(PgListing pg) {
        return reviewRepo.countByPg(pg);
    }

    public List<Review> getLatestReviews() {
        return reviewRepo.findTop6ByOrderByCreatedAtDesc();
    }
}