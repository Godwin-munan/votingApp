package com.munan.votingApp.service;

import com.munan.votingApp.dto.VoteDto;
import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.NotAllowedException;
import com.munan.votingApp.exception.NotFoundException;
import com.munan.votingApp.model.Candidate;
import com.munan.votingApp.model.Gender;
import com.munan.votingApp.model.Vote;
import com.munan.votingApp.model.Voter;
import com.munan.votingApp.repository.*;
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
public class VoteService {

    private VoteRepository voteRepository;
    private CandidateRepository candidateRepository;
    private VoterRepository voterRepository;

    private GenderRepository genderRepository;



    //Add new vote
    public ResponseEntity<HttpResponse> vote(VoteDto voteDto) throws NotFoundException, AlreadyExistException, NotAllowedException {

        Optional<Voter> findVoter = voterRepository.findVoterByUsername(voteDto.getVoter());
        Optional<Candidate> findCandidate = candidateRepository.findCandidateByName(voteDto.getCandidate());

        //Check for invalid voter and candidate
        if (!findVoter.isPresent()){
            throw new NotFoundException("Sorry the Voter with name "
                    +voteDto.getVoter()+" does not exist in our record!");
        }
        if (!findCandidate.isPresent()){
            throw new NotFoundException("Sorry the Candidate with name "
                    +voteDto.getCandidate()+" does not exist in our record!");
        }

        Optional<Vote> findVoteByVoterandCandidate=voteRepository
                .findVoteByVoter_IdAndCandidate_Id(findVoter.get().getId(),findCandidate.get().getId());

        //Check for existing duplicate vote
        if (findVoteByVoterandCandidate.isPresent()){
            throw new AlreadyExistException("Sorry you are not allowed to vote more than once");
        }

        //Stop duplicate vote for one position
        Optional<Vote> duplicateChecker = voteRepository.findVoteByVoter_IdAndCandidate_Position_Id(findVoter.get().getId(), findCandidate.get().getPosition().getId());

        if(duplicateChecker.isPresent())
        {
            throw new NotAllowedException("You can not vote for the same Position Again");
        }

        //Create new vote object from voteDto object fields
        Vote newVote = new Vote();
        newVote.setCandidate(findCandidate.get());
        newVote.setVoter(findVoter.get());

        Vote saveVote;


        Optional<Gender> findGender = genderRepository.findByType(findVoter.get().getGender().getType());
        String male_female = findGender.get().getType();

        //Only male and female genders are allowed to vote
        if(male_female.equalsIgnoreCase("male"))
        {
            saveVote = voteRepository.save(newVote);
        }else if(male_female.equalsIgnoreCase("female"))
        {
            saveVote = voteRepository.save(newVote);
        }else
        {
            throw new NotAllowedException("only male and female can vote");
        }


        return  ResponseEntity.ok(
                new HttpResponse(HttpStatus.OK.value(),
                        "Your Vote has been recorded successfully!",saveVote));
    }

    //Get all votes
    public ResponseEntity<HttpResponse> getAllVote()
    {
        return ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(),"Successful",voteRepository.findAll()));
    }

    //Count all candidate votes -- Native query
    public ResponseEntity<HttpResponse> countCandidateVote(String name){
        Integer count=voteRepository.findCandidateVotes(name);

        return  ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(), "Total Vote Count",count));
    }

    //Count all candidate votes -- List
    public ResponseEntity<HttpResponse> countVote(String name){
        List<Vote> voteList=voteRepository.findVoteByCandidate_Name(name);

        Integer count=voteList.size();
        return  ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(), "Total Vote Count",count));
    }

    //Get winner from votes
    public ResponseEntity<HttpResponse> winner() {
        Optional<Candidate> winner = candidateRepository.findById(voteRepository.findWinner());
        return ResponseEntity.ok(
            new HttpResponse(HttpStatus.OK.value(), "Winner", winner)
        );
    }

}
