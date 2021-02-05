package com.sorbonne.safetyline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sorbonne.safetyline.dataAccess.ChoiceDAO;
import com.sorbonne.safetyline.model.Choice;

@Service
public class ChoiceService {
	
	@Autowired
    private ChoiceDAO choiceDAO;
	
	
	/**
     * @see ChoiceDAO#getChoicesForOneStrawpoll(Integer)
     */
	public List<Choice> getChoicesForOneStrawpoll(Integer id) {
		return choiceDAO.getChoicesForOneStrawpoll(id);
	}

}
