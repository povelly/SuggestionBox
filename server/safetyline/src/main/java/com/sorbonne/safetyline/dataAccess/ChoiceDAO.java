package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.Choice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceDAO extends JpaRepository<Choice, Integer> {
	
	/**
     * @return         all the choices for one given strawpoll
     */
    @Query(value = "SELECT * FROM choice where strawpoll_id = ?1", nativeQuery = true)
    public List<Choice> getChoicesForOneStrawpoll(Integer id);
    
    /**
     * Get all the vote of a user for a given poll
     * Used to check if the user already voter for this poll
     */
    @Query(value = "SELECT * FROM choice WHERE strawpoll_id = ?1 AND choice_id IN"
			+ " (SELECT choice_id FROM vote WHERE user_id = ?2)", nativeQuery = true)
	public List<Choice> getVoteListForOneUser(int strawpollId, String userId);
}
