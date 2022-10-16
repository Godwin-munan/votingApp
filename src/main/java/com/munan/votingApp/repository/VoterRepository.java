package com.munan.votingApp.repository;

import com.munan.votingApp.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter,Long> {
Optional<Voter> findVoterByUsername(String username);

}
