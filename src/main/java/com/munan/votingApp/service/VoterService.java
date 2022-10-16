package com.munan.votingApp.service;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.Voter;
import com.munan.votingApp.repository.VoterRepository;
import com.munan.votingApp.util.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    public ResponseEntity<HttpResponse> registerVoter(Voter voter) throws AlreadyExistException {
        Optional<Voter> existingVoter = voterRepository.findVoterByUsername(voter.getUsername());

        if (existingVoter.isPresent()) {
            throw new AlreadyExistException(
                    "Voter with username: " + existingVoter.get().getUsername() + " already Exist!");
        }
        Voter savedVoter = voterRepository.save(voter);
        return ResponseEntity.ok(
                new HttpResponse(HttpStatus.OK.value(), "Voter Registration Successful!", savedVoter));
    }

    public List<Voter> getAllVoters(){
                         return voterRepository.findAll();
    }

    public ResponseEntity<HttpResponse> deleteVoter(Long id) throws NotFoundException {
        Optional<Voter> foundVoter=voterRepository.findById(id);
        if (!foundVoter.isPresent()){
            throw new NotFoundException("Sorry the record wasn't found!");
        }

        voterRepository.delete(foundVoter.get());
        return ResponseEntity.ok(
                new HttpResponse( HttpStatus.OK.value(), "Record Deleted Successfully!",null));
    }

}
