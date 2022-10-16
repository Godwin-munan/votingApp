package com.munan.votingApp.service;

import com.munan.votingApp.dto.VoteDto;
import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.Candidate;
import com.munan.votingApp.model.Vote;
import com.munan.votingApp.model.Voter;
import com.munan.votingApp.repository.CandidateRepository;
import com.munan.votingApp.repository.VoteRepository;
import com.munan.votingApp.repository.VoterRepository;
import com.munan.votingApp.util.HttpResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;


    public ResponseEntity<HttpResponse> vote(VoteDto voteDto) throws NotFoundException, AlreadyExistException {
        Optional<Voter> findVoter=voterRepository.findVoterByUsername(voteDto.getVoter());
        Optional<Candidate> findCandidate=candidateRepository.findCandidateByName(voteDto.getCandidate());
        if (!findVoter.isPresent()){
            throw new NotFoundException("Sorry the Voter with name "+voteDto.getVoter()+" does not exist in our record!");
        }
        if (!findCandidate.isPresent()){
            throw new NotFoundException("Sorry the Candidate with name "
                    +voteDto.getCandidate()+" does not exist in our record!");
        }

        Optional<Vote> findVoteByVoterandCandidate=voteRepository
                .findVoteByVoter_IdAndCandidate_Id(findVoter.get().getId(),findCandidate.get().getId());

        if (findVoteByVoterandCandidate.isPresent()){
            throw new AlreadyExistException("Sorry you are not allowed to vote more than once");
        }

        Vote newVote=new Vote();
        newVote.setCandidate(findCandidate.get());
        newVote.setVoter(findVoter.get());

        Vote savedVote=voteRepository.save(newVote);

        return  ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(),"Your Vote has been recorded successfully!",savedVote));
    }

    public ResponseEntity<HttpResponse> getAllVote()
    {
        return ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(),"Successful",voteRepository.findAll()));
    }

    public ResponseEntity<HttpResponse> countCandidateVote(String name){
        Integer count=voteRepository.findCandidateVotes(name);

        return  ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(), "Total Vote Count",count));
    }

    public ResponseEntity<HttpResponse> count(String name){
        List<Vote> voteList=voteRepository.findVoteByCandidate_Name(name);

        Integer count=voteList.size();
        return  ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(), "Total Vote Count",count));
    }
}
