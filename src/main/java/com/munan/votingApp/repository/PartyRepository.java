package com.munan.votingApp.repository;

import com.munan.votingApp.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByInitials(String toUpperCase);
}
