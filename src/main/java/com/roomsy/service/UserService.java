package com.roomsy.service;

import com.roomsy.entity.User;
import com.roomsy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(String fullName, String email, String rawPassword,
                         String phone, String role) {
        if (userRepository.existsByEmail(email))
            throw new RuntimeException("Email already registered: " + email);
        String hashed = encoder.encode(rawPassword);
        return userRepository.save(new User(fullName, email, hashed, phone, role));
    }

    public Optional<User> login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(u -> encoder.matches(rawPassword, u.getPassword()));
    }

    public boolean checkAdminCredentials(String email, String rawPassword,
                                         String adminEmail, String adminPassword) {
        return email.equals(adminEmail) && rawPassword.equals(adminPassword);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public long countTenants() { return userRepository.countByRole("TENANT"); }
    public long countOwners()  { return userRepository.countByRole("OWNER"); }
}
