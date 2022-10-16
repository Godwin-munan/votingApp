package com.munan.votingApp.controller;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.GlobalExceptionHandling;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.Voter;
import com.munan.votingApp.service.VoterService;
import com.munan.votingApp.util.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voter")
@Tag(name = "Voter Controller", description = "Voter Controller")
public class VotersController extends GlobalExceptionHandling {

    @Autowired
    private VoterService voterService;

    @Operation(summary = "Add Voter", description = "Add new voter")
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> registerVoter(@RequestBody Voter voter) throws AlreadyExistException {
        return voterService.registerVoter(voter);
    }

    @Operation(summary = "Get all Voters", description = "All Voters")
    @GetMapping("/getAll")
    public List<Voter> getAllVoters(){
        return voterService.getAllVoters();
    }

    @Operation(summary = "Delete A Voter", description = "Remove a Voter record by ID")
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<HttpResponse> deleteVoter(@PathVariable("id") Long id) throws NotFoundException {
        return voterService.deleteVoter(id);
    }

}
