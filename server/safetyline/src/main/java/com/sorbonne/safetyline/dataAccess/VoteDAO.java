package com.sorbonne.safetyline.dataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sorbonne.safetyline.model.Choice;
import com.sorbonne.safetyline.model.Vote;

@Repository
public interface VoteDAO extends JpaRepository<Vote, Integer> {
	
	/**
     * Saves a vote in the DB
     */
	@Modifying
    @Query(value = "INSERT INTO vote (user_id, choice_id) values (?1, ?2)", nativeQuery = true)
    public void saveVote(String userId, int choiceId);
	
}