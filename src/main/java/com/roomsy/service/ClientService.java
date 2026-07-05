package com.roomsy.service;

import com.roomsy.entity.Client;
import com.roomsy.entity.User;
import com.roomsy.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired private ClientRepository clientRepository;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getByUser(User user) {
        return clientRepository.findByUser(user);
    }

    public List<Client> search(String keyword, String status) {
        boolean hasKw     = keyword != null && !keyword.isBlank();
        boolean hasStatus = status  != null && !status.isBlank() && !status.equals("All");

        if (hasKw && hasStatus)
            return clientRepository.searchByStatusAndKeyword(status, keyword.trim());
        if (hasKw)
            return clientRepository.search(keyword.trim());
        if (hasStatus)
            return clientRepository.findByStatus(status);
        return clientRepository.findAll();
    }

    public Client update(Long id, Client updated) {
        return clientRepository.findById(id).map(c -> {
            c.setPreferredLocation(updated.getPreferredLocation());
            c.setBudget(updated.getBudget());
            c.setGender(updated.getGender());
            c.setRoomType(updated.getRoomType());
            c.setStatus(updated.getStatus());
            c.setPgName(updated.getPgName());
            c.setMoveInDate(updated.getMoveInDate());
            return clientRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Client not found: " + id));
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public long countAll()        { return clientRepository.count(); }
    public long countSearching()  { return clientRepository.countByStatus("Searching"); }
    public long countPlaced()     { return clientRepository.countByStatus("Placed"); }
    public long countInactive()   { return clientRepository.countByStatus("Inactive"); }
}
