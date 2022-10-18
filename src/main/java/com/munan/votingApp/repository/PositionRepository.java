package com.munan.votingApp.repository;

import com.munan.votingApp.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByType(String type);
}
