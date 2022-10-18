package com.munan.votingApp.repository;

import com.munan.votingApp.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findCandidateByName(String name);

    Optional<Candidate> findCandidateByParty_InitialsAndPosition_Type(String party, String position);
}
