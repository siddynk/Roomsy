package com.roomsy.repository;

import com.roomsy.entity.PgListing;
import com.roomsy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PgListingRepository extends JpaRepository<PgListing, Long> {

    List<PgListing> findByOwner(User owner);
    List<PgListing> findByStatus(String status);
    List<PgListing> findByGender(String gender);
    List<PgListing> findByCityIgnoreCase(String city);

    @Query("SELECT p FROM PgListing p WHERE p.status = 'Active' AND " +
            "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%',:city,'%')) OR LOWER(p.location) LIKE LOWER(CONCAT('%',:city,'%'))) AND " +
            "(:gender IS NULL OR p.gender = :gender OR p.gender = 'Any') AND " +
            "(:roomType IS NULL OR p.roomType = :roomType) AND " +
            "(:maxRent IS NULL OR p.rent <= :maxRent)")
    List<PgListing> filterPgs(@Param("city") String city,
                              @Param("gender") String gender,
                              @Param("roomType") String roomType,
                              @Param("maxRent") Integer maxRent);

    @Query("SELECT p FROM PgListing p WHERE p.status = 'Active' AND " +
            "(LOWER(p.pgName) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
            "LOWER(p.location) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
            "LOWER(p.city) LIKE LOWER(CONCAT('%',:q,'%')))")
    List<PgListing> searchActive(@Param("q") String query);

    long countByStatus(String status);
}