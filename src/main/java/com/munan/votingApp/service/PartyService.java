package com.munan.votingApp.service;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.model.Party;
import com.munan.votingApp.repository.PartyRepository;
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
public class PartyService {

    private final PartyRepository partyRepository;

    //Add new Party
    public ResponseEntity<HttpResponse> addParty(Party party) throws AlreadyExistException {

        Optional<Party> existingParty = partyRepository.findByInitials(party.getInitials().toUpperCase());

        //Check if Party already exist
        if(existingParty.isPresent())
        {
            throw new AlreadyExistException("Party already exist");
        }


        Party new_party = new Party();
        new_party.setInitials(party.getInitials().toUpperCase());
        new_party.setName(party.getName().toLowerCase());

        //Add new party record
        Party savedParty = partyRepository.save(new_party);

        return ResponseEntity.ok(
                new HttpResponse(HttpStatus.OK.value(), "Party successfully added", savedParty)
        );
    }
}
