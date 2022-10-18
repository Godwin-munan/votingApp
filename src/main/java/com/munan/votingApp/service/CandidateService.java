package com.munan.votingApp.service;

import com.munan.votingApp.dto.CandidateDto;
import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.*;
import com.munan.votingApp.repository.CandidateRepository;
import com.munan.votingApp.repository.PartyRepository;
import com.munan.votingApp.repository.PositionRepository;
import com.munan.votingApp.util.HttpResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CandidateService {


    CandidateRepository candidateRepository;
    PositionRepository positionRepository;
    PartyRepository partyRepository;


    //Register new Candidate
    public ResponseEntity<HttpResponse> registerCandidate(CandidateDto candidateDto) throws AlreadyExistException, NotFoundException {

        Optional<Candidate> existingCandidate = candidateRepository.findCandidateByName(candidateDto.getName());

        //Check if candidate already exist
        if (existingCandidate.isPresent()) {
            throw new AlreadyExistException(
                    "Candidate with name " + existingCandidate.get().getName() + " already Exist!");
        }

        //Check if Position exist in Position table
        Optional<Position> findPosition = positionRepository.findByType(candidateDto.getPosition());

        if(!findPosition.isPresent())
        {
            throw new NotFoundException("Position does not exist");
        }

        //Check if Party exist in Party table
        Optional<Party> findParty = partyRepository.findByInitials(candidateDto.getParty());

        if(!findPosition.isPresent())
        {
            throw new NotFoundException("Party does not exist");
        }

        Optional<Candidate> party_position = candidateRepository
                        .findCandidateByParty_InitialsAndPosition_Type
                                (findParty.get().getInitials(), findPosition.get().getType());

        //Check if record already exist
        if(party_position.isPresent())
        {
            throw new AlreadyExistException("Candidate for this Party and Position already exist");
        }

        Candidate newCandidate = new Candidate();
        newCandidate.setAge(candidateDto.getAge());
        newCandidate.setName(candidateDto.getName());
        newCandidate.setHealthStatus(candidateDto.getHealthStatus());
        newCandidate.setPosition(findPosition.get());
        newCandidate.setParty(findParty.get());

        //Save new Candidate
        Candidate savedCandidate = candidateRepository.save(newCandidate);
        return ResponseEntity.ok(
                new HttpResponse(
                        HttpStatus.OK.value(),
                        "Candidate Registration Successful!",
                        savedCandidate));
    }


    //Get all Candidates
    public ResponseEntity<HttpResponse> getAllCandidates() {

        return ResponseEntity.ok( new HttpResponse(
                HttpStatus.OK.value(),
                "Candidate Registration Successful!",
                candidateRepository.findAll()));
    }

    //Delete a Candidate by Id
    public ResponseEntity<HttpResponse> deleteById(Long id) throws NotFoundException{

        Optional<Candidate> foundCandidate = candidateRepository.findById(id);

        //Check if record to be deleted exist
        if (!foundCandidate.isPresent()){
            throw new NotFoundException("Sorry the record wasn't found!");
        }

        //Delete Candidate record with the given id
        candidateRepository.delete(foundCandidate.get());
        return ResponseEntity.ok(
                new HttpResponse(
                        HttpStatus.OK.value(),
                        "Candidate "+foundCandidate.get().getName()+" delete successfull",
                        null
                ));
    }
}
