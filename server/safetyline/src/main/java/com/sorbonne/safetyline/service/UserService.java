package com.sorbonne.safetyline.service;

import beans.JavaMailUtil;
import beans.Password;
import com.sorbonne.safetyline.dataAccess.StrawpollDOA;
import com.sorbonne.safetyline.dataAccess.SuggestionDOA;
import com.sorbonne.safetyline.dataAccess.UserDOA;
import com.sorbonne.safetyline.model.Strawpoll;
import com.sorbonne.safetyline.model.Suggestion;
import com.sorbonne.safetyline.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDOA userdoa;
    @Autowired
    private SuggestionDOA suggestionDoa;
    @Autowired
    private StrawpollDOA strawpollDoa;

    //---------------------------------------------------------------------------
    // User services
    //---------------------------------------------------------------------------
    public List<User> getAllUsers() {
        return userdoa.getAllUsers();
    }

    /**
     * @see UserDOA#deleteUserById_user(String)
     */
    @Transactional
    public void deleteUserById_user(String id_user) {
        userdoa.deleteUserById_user(id_user);
    }

    @Transactional
    public void addSimpleUser(String user_id, String first_name, String last_name) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String password = Password.generateFirstPassword();
        /**
         * Send email to user with his password
         */
        String hashPassword = Password.sha256(password);
        User u = new User();
        u.setUser_id(user_id);
        u.setLast_name(last_name);
        u.setFirst_name(first_name);
        u.setPassword(password);
        u.setSuggestionList(new ArrayList<>());
        u.setIs_admin(false);
        userdoa.save(u);
    }

    /**
     * @see UserDOA#save(Object) 
     */
    @Transactional
    public void addSimpleAdmin(String user_id, String first_name, String last_name) throws Exception {

        String password = Password.generateFirstPassword();
        /**
         * Send email to user with his password
         */
        //JavaMailUtil.send("sacha.memmi.etu@gmail.com", password);
        String hashPassword = Password.sha256(password);
        User u = new User();
        u.setUser_id(user_id);
        u.setLast_name(last_name);
        u.setFirst_name(first_name);
        u.setPassword(password);
        u.setSuggestionList(new ArrayList<>());
        u.setIs_admin(true);
        userdoa.save(u);
    }
    /**
     * @see UserDOA#getAllAdmins()
     */
    public List<User> getAllAdmins() {
        return userdoa.getAllAdmins();
    }

    /**
     * @see UserDOA#findById(Object)
     */
    public Optional<User> getUserById(String user_id) {
        return userdoa.findById(user_id);
    }

    /**
     * @see UserDOA#findByFirst_name(String)
     */
    public List<User> getUserByFirst_name(String first_name) {
        return userdoa.findByFirst_name(first_name);
    }

    /**
     * @see UserDOA#findByLast_name(String)
     */
    public List<User> getUserByLast_name(String last_name) {
        return userdoa.findByLast_name(last_name);
    }


    //----------------------------------------------------------------------------
    // Suggestion services
    //----------------------------------------------------------------------------

    /**
     * @see SuggestionDOA#findAll()
     */
    public List<Suggestion> getAllSuggestions() {
        return suggestionDoa.findAll();
    }

    /**
     * @see SuggestionDOA#findBySuggestion_author(String)
     */
    public List<Suggestion> getSuggestionByAuthor(String author) {
        return suggestionDoa.findBySuggestion_author(author);
    }

    /**
     * @see SuggestionDOA#findBySuggestion_creation_dateRange(Date, Date)
     */
    public List<Suggestion> getSuggestionByDateRange(Date lowerBoundDate, Date upperBoundDate)
    {
        return suggestionDoa.findBySuggestion_creation_dateRange(lowerBoundDate, upperBoundDate);
    }

    //----------------------------------------------------------------------------
    // Strawpoll services
    //----------------------------------------------------------------------------

    /**
     * @see StrawpollDOA#findAll()
     */
    public List<Strawpoll> getAllStrawpolls()
    {
        return strawpollDoa.findAll();
    }

    /**
     * @see StrawpollDOA#findByStrawpoll_expiration_date(Date, Date)
     */
    public List<Strawpoll> getStrawpollByExpiration(Date lowerBound, Date upperBound)
    {
        return strawpollDoa.findByStrawpoll_expiration_date(lowerBound, upperBound);
    }

    /**
     * @see StrawpollDOA#findByStrawpoll_creation_date(Date, Date)
     */
    public List<Strawpoll> getStrawpollByCreation(Date lowerBound, Date upperBound)
    {
        return strawpollDoa.findByStrawpoll_creation_date(lowerBound, upperBound);
    }

    /**
     * @see StrawpollDOA#findByStrawpoll_author(String)
     */
    public List<Strawpoll> getStrawpollByAuthor(String author)
    {
        return strawpollDoa.findByStrawpoll_author(author);
    }

    /**
     * @see StrawpollDOA#findByTitle(String)
     */
    public List<Strawpoll> getStrawpollByTitle(String title)
    {
        return strawpollDoa.findByTitle(title);
    }

}
