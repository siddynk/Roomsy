package com.roomsy.service;

import com.roomsy.entity.Booking;
import com.roomsy.entity.PgListing;
import com.roomsy.entity.User;
import com.roomsy.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired private BookingRepository bookingRepository;

    public Booking book(User tenant, PgListing pg,
                        LocalDate visitDate, String message) {
        if (bookingRepository.existsByTenantAndPgListing(tenant, pg))
            throw new RuntimeException("You have already requested a visit for this PG.");
        return bookingRepository.save(new Booking(tenant, pg, visitDate, message));
    }

    public List<Booking> getByTenant(User tenant) {
        return bookingRepository.findByTenant(tenant);
    }

    public List<Booking> getByOwner(User owner) {
        return bookingRepository.findByPgListingOwner(owner);
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking updateStatus(Long id, String status) {
        return bookingRepository.findById(id).map(b -> {
            b.setStatus(status);
            return bookingRepository.save(b);
        }).orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }

    public Booking updateStatusForOwner(User owner, Long id, String status) {
        return bookingRepository.findById(id).map(b -> {
            if (!b.getPgListing().getOwner().getId().equals(owner.getId()))
                throw new RuntimeException("You can only manage bookings for your own PGs.");
            b.setStatus(status);
            return bookingRepository.save(b);
        }).orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }

    public void delete(Long id) { bookingRepository.deleteById(id); }

    public long countPending()   { return bookingRepository.countByStatus("PENDING"); }
    public long countConfirmed() { return bookingRepository.countByStatus("CONFIRMED"); }
    public long countAll()       { return bookingRepository.count(); }
}
