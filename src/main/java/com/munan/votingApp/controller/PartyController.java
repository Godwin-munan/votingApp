package com.munan.votingApp.controller;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.model.Party;
import com.munan.votingApp.service.PartyService;
import com.munan.votingApp.util.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/party")
@Tag(name = "Party Controller", description = "Party Controller")
@AllArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @Operation(summary = "Add new party")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse> add(@RequestBody Party party) throws AlreadyExistException {
        return partyService.addParty(party);
    }
}
