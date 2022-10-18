package com.munan.votingApp.service;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.model.Position;
import com.munan.votingApp.repository.PositionRepository;
import com.munan.votingApp.util.HttpResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;


    //Add new Party
    public ResponseEntity<HttpResponse> addPosition(Position position) throws AlreadyExistException {

        Optional<Position> existingPosition = positionRepository.findByType(position.getType().toLowerCase());

        //Check if Position already exist
        if(existingPosition.isPresent())
        {
            throw new AlreadyExistException("Position already exist");
        }

        Position new_position = new Position();
        new_position.setType(position.getType().toLowerCase());

        //Add new Position record
        Position savedPosition = positionRepository.save(new_position);

        return ResponseEntity.ok(
            new HttpResponse(HttpStatus.OK.value(), "Position was successfully added", savedPosition)
        );
    }
}
