package com.munan.votingApp.service;

import com.munan.votingApp.dto.VoterDto;
import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.exception.UnderAgeException;
import com.munan.votingApp.model.Gender;
import com.munan.votingApp.model.Voter;
import com.munan.votingApp.repository.GenderRepository;
import com.munan.votingApp.repository.VoterRepository;
import com.munan.votingApp.util.HttpResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class VoterService {


    private final VoterRepository voterRepository;
    private final GenderRepository genderRepository;

    //Register new Voter
    public ResponseEntity<HttpResponse> registerVoter(VoterDto voterDto) throws AlreadyExistException, UnderAgeException, NotFoundException {
        Optional<Voter> existingVoter = voterRepository.findVoterByUsername(voterDto.getUsername());

        //Check if Voter already exist
        if (existingVoter.isPresent()) {
            throw new AlreadyExistException(
                    "Voter with username: " + existingVoter.get().getUsername() + " already Exist!");
        }

        //Check if Voter Age is more than 18
        if(voterDto.getAge() < 18)
        {
            throw new UnderAgeException("Under age registration is discourage");
        }

        Optional<Gender> findGender = genderRepository.findByType(voterDto.getGender());

        //Check if gender exist in Gender table
        if(!findGender.isPresent())
        {
            throw new NotFoundException("Gender does not exist");
        }

        Voter new_voter = new Voter();
        new_voter.setName(voterDto.getName());
        new_voter.setUsername(voterDto.getUsername());
        new_voter.setAge(voterDto.getAge());
        new_voter.setGender(findGender.get());

        //Add new Voter record
        Voter savedVoter = voterRepository.save(new_voter);
        return ResponseEntity.ok(
                new HttpResponse(HttpStatus.OK.value(), "Voter Registration Successful!", savedVoter));
    }

    //Get all Voters
    public List<Voter> getAllVoters(){
                         return voterRepository.findAll();
    }

    //Delete Voter with the given Id
    public ResponseEntity<HttpResponse> deleteVoter(Long id) throws NotFoundException {
        Optional<Voter> foundVoter=voterRepository.findById(id);

        //Check if record to be deleted, exist
        if (!foundVoter.isPresent()){
            throw new NotFoundException("Sorry the record wasn't found!");
        }

        //Delete Voter record with the given id
        voterRepository.delete(foundVoter.get());
        return ResponseEntity.ok(
                new HttpResponse( HttpStatus.OK.value(), "Record Deleted Successfully!",null));
    }

}
