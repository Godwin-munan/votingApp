package com.munan.votingApp.repository;

import com.munan.votingApp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {


    Optional<Vote> findVoteByCandidate_Id(Long id);

    Optional<Vote> findVoteByVoter_IdAndCandidate_Id(Long voterId, Long candidateId);


//    Get total number of votes per candidate
    @Query(value ="SELECT COUNT(*) AS 'Total votes'\n" +
            "FROM vote v \n" +
            "INNER JOIN candidate c \n" +
            "\t ON v.`candidate_id` = c.`id`\n" +
            "\t WHERE c.`name` =?1" ,nativeQuery = true)
    Integer findCandidateVotes(String name);

 List<Vote> findVoteByCandidate_Name(String name);

 @Query(value = "SELECT candidate_id\n" +
         "FROM vote\n" +
         "GROUP BY candidate_id\n" +
         "ORDER BY COUNT(*) DESC\n" +
         "LIMIT 1", nativeQuery = true)
 Long findWinner();

 Optional<Vote> findVoteByVoter_IdAndCandidate_Position_Id(Long voterId, Long positionId);
}
