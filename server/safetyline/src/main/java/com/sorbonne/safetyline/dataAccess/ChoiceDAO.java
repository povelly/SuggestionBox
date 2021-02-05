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
}
