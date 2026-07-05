package com.roomsy.service;

import com.roomsy.entity.PgListing;
import com.roomsy.entity.User;
import com.roomsy.repository.PgListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PgListingService {

    @Autowired private PgListingRepository pgListingRepository;

    public PgListing save(PgListing pg) {
        return pgListingRepository.save(pg);
    }

    public List<PgListing> getAllActive() {
        return pgListingRepository.findByStatus("Active");
    }

    public List<PgListing> getAll() {
        return pgListingRepository.findAll();
    }

    public Optional<PgListing> getById(Long id) {
        return pgListingRepository.findById(id);
    }

    public List<PgListing> getByOwner(User owner) {
        return pgListingRepository.findByOwner(owner);
    }

    public List<PgListing> filter(String city, String gender,
                                   String roomType, Integer maxRent) {
        boolean hasCity     = city     != null && !city.isBlank();
        boolean hasGender   = gender   != null && !gender.isBlank() && !gender.equals("Any");
        boolean hasRoomType = roomType != null && !roomType.isBlank();
        boolean hasMaxRent  = maxRent  != null && maxRent > 0;

        return pgListingRepository.filterPgs(
                hasCity     ? city     : null,
                hasGender   ? gender   : null,
                hasRoomType ? roomType : null,
                hasMaxRent  ? maxRent  : null
        );
    }

    public List<PgListing> search(String query) {
        if (query == null || query.isBlank()) return getAllActive();
        return pgListingRepository.searchActive(query.trim());
    }

    public void delete(Long id) {
        pgListingRepository.deleteById(id);
    }

    public long countActive()   { return pgListingRepository.countByStatus("Active"); }
    public long countInactive() { return pgListingRepository.countByStatus("Inactive"); }
    public long countAll()      { return pgListingRepository.count(); }
}
