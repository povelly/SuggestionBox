package com.sorbonne.safetyline.service;
import com.sorbonne.safetyline.controller.SafetyLineController;
/**
 *
 * @author Georges Mathieu/ Memmi Sacha
 *
 * Service offering strawpolls manipulation methods
 *
 */
import com.sorbonne.safetyline.dataAccess.ChoiceDAO;
import com.sorbonne.safetyline.dataAccess.StrawpollDAO;
import com.sorbonne.safetyline.dataAccess.VoteDAO;
import com.sorbonne.safetyline.dto.VoteDTO;
import com.sorbonne.safetyline.exception.AlreadyVotedException;
import com.sorbonne.safetyline.exception.EmptyChoice;
import com.sorbonne.safetyline.exception.EmptyStrawpoll;
import com.sorbonne.safetyline.exception.StrawpollNotExists;
import com.sorbonne.safetyline.model.Choice;
import com.sorbonne.safetyline.model.Strawpoll;
import com.sorbonne.safetyline.model.Vote;

import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;


@Service
public class StrawpollService {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(StrawpollService.class);
	
    @Autowired
    private StrawpollDAO strawpollDAO;

    @Autowired
    private ChoiceDAO choiceDAO;
    
    @Autowired
    private VoteDAO voteDAO;

    /**
     * It is possible to create an empty strawpoll
     * @param title         title of the suggestion
     * @param author        author of the suggestion his user_id/mail
     * @return              true if insertion worked
     */
    @Transactional
    public Strawpoll createStrawpoll(String title, String author, Date expiracy, List<String> choices)
    throws EmptyStrawpoll {
        if(choices==null)
            throw new EmptyStrawpoll();
        
        Strawpoll strawpoll = new Strawpoll();
        strawpoll.setTitle(title);
        strawpoll.setAuthor(author);
        
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, 2);
        Date expirationDate = expiracy != null ? expiracy : cal.getTime();
        strawpoll.setDeadlineTime(expirationDate);
        
        Strawpoll s = strawpollDAO.save(strawpoll);
        
        // We create the choices and save them in the DB
        List<Choice> choicesRes = new ArrayList<>();
        if(choices!=null){
            for(String choice: choices){
                choicesRes.add(instantianteChoice(choice, strawpoll));
            }
        }

        choicesRes.stream().parallel().map(c -> choiceDAO.save(c)).collect(Collectors.toList());
        return strawpoll;
    }

    /**
     * Creates a Choice based on parameters from a Strawpoll
     * @param content,		the content of the choice
     * @param strawpoll,	the strawpoll containing the choice
     * @return
     */
    public Choice instantianteChoice(String content, Strawpoll strawpoll){
        if(content == null || content.equals(""))
            return null;
        Choice c = new Choice();
        c.setStrawpollId(strawpoll.getStrawpollId());
        c.setStrawpoll(strawpoll);
        c.setChoiceContent(content);
        return c;
    }
    
    /**
     * Saves a vote in the DB
     * @throws AlreadyVotedException 
     */
    @Transactional
    public void sauvegardeVote(VoteDTO voteDTO) throws AlreadyVotedException {
    	String author = voteDTO.getAuthor();
    	
    	// We check if the user already voted for this poll
    	if(!choiceDAO.getVoteListForOneUser(voteDTO.getId(), author).isEmpty()) {
    		throw new AlreadyVotedException();
    	} else {
    		// The user hasn't voted yet
    		for (String choiceId : voteDTO.getReponses()) {
        		voteDAO.saveVote(author, Integer.parseInt(choiceId));
    		}
    	}

    }

    /**
     * @see StrawpollDAO#findAll()
     */
    public List<Strawpoll> getAllStrawpolls()
    {
        return strawpollDAO.findAll();
    }

    /**
     * @see StrawpollDAO#findByStrawpoll_expiration_date(Date, Date)
     */
    public List<Strawpoll> getStrawpollByExpiration(Date lowerBound, Date upperBound)
    {
        return strawpollDAO.findByStrawpoll_expiration_date(lowerBound, upperBound);
    }

    /**
     * @see StrawpollDAO#findByStrawpoll_creation_date(Date, Date)
     */
    public List<Strawpoll> getStrawpollByCreation(Date lowerBound, Date upperBound)
    {
        return strawpollDAO.findByStrawpoll_creation_date(lowerBound, upperBound);
    }

    /**
     * @see StrawpollDAO#findByStrawpoll_author(String)
     */
    public List<Strawpoll> getStrawpollByAuthor(String author)
    {
        return strawpollDAO.findByStrawpoll_author(author);
    }

    /**
     * @see StrawpollDAO#findByTitle(String)
     */
    public List<Strawpoll> getStrawpollByTitle(String title)
    {
        return strawpollDAO.findByTitle(title);
    }

    /**
     * @see StrawpollDAO#deleteById(Object)
     */
    @Transactional
    public void deleteStrawpoll(int id) throws StrawpollNotExists{
        if(!strawpollDAO.existsById(id))
            throw new StrawpollNotExists();
        strawpollDAO.deleteById(id);
    }

}
