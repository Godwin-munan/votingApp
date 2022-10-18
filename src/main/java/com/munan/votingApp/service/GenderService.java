package com.munan.votingApp.service;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.model.Gender;
import com.munan.votingApp.repository.GenderRepository;
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
public class GenderService {

    private final GenderRepository genderRepository;

    //Add new Gender
    public ResponseEntity<HttpResponse> addGender(Gender gender) throws AlreadyExistException {

        Optional<Gender> existingGender = genderRepository.findByType(gender.getType());

        //Check if Gender already exist
        if(existingGender.isPresent())
        {
            throw new AlreadyExistException("Gender already exist");
        }

        //Add new gender record
        Gender saveGender = genderRepository.save(gender);

        return ResponseEntity.ok(
            new HttpResponse(HttpStatus.OK.value(), "Gender successfully added", saveGender)
        );
    }
}
