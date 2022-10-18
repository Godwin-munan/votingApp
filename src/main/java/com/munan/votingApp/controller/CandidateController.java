package com.munan.votingApp.controller;

import com.munan.votingApp.dto.CandidateDto;
import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.GlobalExceptionHandling;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.Candidate;
import com.munan.votingApp.model.Voter;
import com.munan.votingApp.service.CandidateService;
import com.munan.votingApp.util.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
@Tag(name = "Candidate Controller", description = "Candidate Controller")
@AllArgsConstructor
public class CandidateController extends GlobalExceptionHandling {


    CandidateService candidateService;

    @Operation(summary = "Add Candidate", description = "Add a new Candidate")
    @PostMapping(value = "/register")
    public ResponseEntity<HttpResponse> registerCandidate(@RequestBody CandidateDto candidateDto) throws AlreadyExistException, NotFoundException {
        return candidateService.registerCandidate(candidateDto);
    }

    @Operation(summary = "All Candidate", description = "Get all Candidate")
    @GetMapping(value = "/getAll")
    public ResponseEntity<HttpResponse> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @Operation(summary = "Delete A Candidate", description = "Remove a Candidate record by ID")
    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<HttpResponse> deleteById(@PathVariable("id") Long id) throws NotFoundException{

        return candidateService.deleteById(id);
    }

}
