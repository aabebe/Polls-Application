package com.andualem.polls.repository;

import com.andualem.polls.model.ChoiceVoteCount;
import com.andualem.polls.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT NEW com.andualem.polls.model.ChoiceVoteCount(v.choice.id, count(v.id)) from Vote v where v.poll.id in  :pollIds GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds")List<Long> pollIds);

    @Query("select new com.andualem.polls.model.ChoiceVoteCount(v.choice.id, count(v.id)) from Vote v where v.poll.id = :pollId GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") List pollId);

    @Query("select v from Vote v where v.user.id = : userId and v.poll.id in :pollIds")
    List<Vote> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);

    @Query("select v from Vote v where v.user.id = :userId and v.poll.id = :pollId")
    Vote findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("select count (v.id) from Vote v where v.user.id =:userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("select v.poll.id from  Vote v where v.user.id =:userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
