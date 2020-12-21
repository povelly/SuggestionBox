package com.sorbonne.safetyline.service;
/**
 *
 * @author Georges Mathieu/ Memmi Sacha
 *
 * Service offering strawpolls manipulation methods
 *
 */
import com.sorbonne.safetyline.dataAccess.StrawpollDAO;
import com.sorbonne.safetyline.model.Strawpoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


@Service
public class StrawpollService {
    @Autowired
    private StrawpollDAO strawpollDAO;

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
