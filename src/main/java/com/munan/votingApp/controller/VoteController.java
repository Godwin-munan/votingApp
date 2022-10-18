package com.munan.votingApp.controller;

import com.munan.votingApp.dto.VoteDto;
import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.GlobalExceptionHandling;
import com.munan.votingApp.exception.NotAllowedException;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.Vote;
import com.munan.votingApp.service.VoteService;
import com.munan.votingApp.util.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
@Tag(name = "Vote Controller", description = "Vote Controller")
public class VoteController extends GlobalExceptionHandling {

    private final VoteService voteService;

//    public VoteController(VoteService voteService) {
//        this.voteService = voteService;
//    }

    @Operation(summary = "Add Vote for candidate")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse> addVote(@RequestBody VoteDto voteDto)
            throws NotFoundException, AlreadyExistException, NotAllowedException {
        return voteService.vote(voteDto);
    }

    @Operation(summary = "Get all votes")
    @GetMapping("/getAll")
    public ResponseEntity<HttpResponse> getVotes(){

        return voteService.getAllVote();
    }

    @Operation(summary = "Count all Vote")
    @GetMapping("/count")
    public ResponseEntity<HttpResponse> countVote(String name){
        return voteService.countCandidateVote(name);
    }

    @Operation(summary = "Get Vote List and return count")
    @GetMapping("/countList")
    public ResponseEntity<HttpResponse> countVoteList(String name){
        return voteService.countVote(name);
    }

    @Operation(summary = "Get winner")
    @GetMapping("/winner")
    public ResponseEntity<HttpResponse> getWinner(){
        return voteService.winner();
    }

}
