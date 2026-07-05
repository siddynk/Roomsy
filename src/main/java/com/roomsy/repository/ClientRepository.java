package com.roomsy.repository;

import com.roomsy.entity.Client;
import com.roomsy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);
    List<Client> findByStatus(String status);
    long countByStatus(String status);

    @Query("SELECT c FROM Client c WHERE " +
           "LOWER(c.user.fullName) LIKE LOWER(CONCAT('%',:kw,'%')) OR " +
           "LOWER(c.user.email) LIKE LOWER(CONCAT('%',:kw,'%')) OR " +
           "LOWER(c.preferredLocation) LIKE LOWER(CONCAT('%',:kw,'%'))")
    List<Client> search(@Param("kw") String keyword);

    @Query("SELECT c FROM Client c WHERE c.status = :status AND (" +
           "LOWER(c.user.fullName) LIKE LOWER(CONCAT('%',:kw,'%')) OR " +
           "LOWER(c.preferredLocation) LIKE LOWER(CONCAT('%',:kw,'%')))")
    List<Client> searchByStatusAndKeyword(@Param("status") String status,
                                          @Param("kw") String keyword);
}
