package com.munan.votingApp.service;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.Candidate;
import com.munan.votingApp.repository.CandidateRepository;
import com.munan.votingApp.util.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    public ResponseEntity<HttpResponse> registerCandidate(Candidate candidate) throws AlreadyExistException{

        Optional<Candidate> existingCandidate = candidateRepository.findCandidateByName(candidate.getName());

        if (existingCandidate.isPresent()) {
            throw new AlreadyExistException(
                    "Candidate with name " + existingCandidate.get().getName() + " already Exist!");
        }

        Candidate savedCandidate = candidateRepository.save(candidate);
        return ResponseEntity.ok(
                new HttpResponse(
                        HttpStatus.OK.value(),
                        "Candidate Registration Successful!",
                        savedCandidate));
    }


    public ResponseEntity<HttpResponse> getAllCandidates() {

        return ResponseEntity.ok( new HttpResponse(
                HttpStatus.OK.value(),
                "Candidate Registration Successful!",
                candidateRepository.findAll()));
    }

    public ResponseEntity<HttpResponse> deleteById(Long id) throws NotFoundException{

        Optional<Candidate> foundCandidate = candidateRepository.findById(id);

        if (!foundCandidate.isPresent()){
            throw new NotFoundException("Sorry the record wasn't found!");
        }

        candidateRepository.delete(foundCandidate.get());
        return ResponseEntity.ok(
                new HttpResponse(
                        HttpStatus.OK.value(),
                        "Candidate "+foundCandidate.get().getName()+" delete successfull",
                        null
                ));
    }
}
