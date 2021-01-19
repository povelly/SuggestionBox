package com.sorbonne.safetyline.service;
/**
 *
 * @author Georges Mathieu/ Memmi Sacha
 *
 * Service offering strawpolls manipulation methods
 *
 */
import com.sorbonne.safetyline.dataAccess.ChoiceDAO;
import com.sorbonne.safetyline.dataAccess.StrawpollDAO;
import com.sorbonne.safetyline.exception.EmptyChoice;
import com.sorbonne.safetyline.exception.EmptyStrawpoll;
import com.sorbonne.safetyline.exception.StrawpollNotExists;
import com.sorbonne.safetyline.model.Choice;
import com.sorbonne.safetyline.model.Strawpoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class StrawpollService {
    @Autowired
    private StrawpollDAO strawpollDAO;

    @Autowired
    private ChoiceDAO choiceDAO;

    /**
     * It is possible to create an empty strawpoll
     * @param title         title of the suggestion
     * @param author        author of the suggestion his user_id/mail
     * @return              true if insertion worked
     */
    public Strawpoll createStrawpoll(String title, String author, Date expiracy, List<String> choices)
    throws EmptyStrawpoll {
        if(choices==null)
            throw new EmptyStrawpoll();
        Strawpoll strawpoll = new Strawpoll();
        strawpoll.setTitle(title);
        //strawpoll.setAuthor(author);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.HOUR, 2);

        Date expirationDate = expiracy!=null?expiracy
                : cal.getTime();
        strawpoll.setDeadlineTime(expirationDate);
        return strawpollDAO.save(strawpoll);
    }

    public Optional<Strawpoll> insertChoice(int strawpollId, String content) throws StrawpollNotExists, EmptyChoice{
        if(!strawpollDAO.existsById(strawpollId))
            throw new StrawpollNotExists();
        if(content==null || content=="")
            throw new EmptyChoice();
        Choice choice = new Choice();
        choice.setChoiceContent(content);
        choice.setStrawpollId(strawpollId);
        choiceDAO.save(choice);
        return strawpollDAO.findById(strawpollId);
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


}
