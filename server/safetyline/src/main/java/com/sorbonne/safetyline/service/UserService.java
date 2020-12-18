package com.sorbonne.safetyline.service;

import beans.Password;
import com.sorbonne.safetyline.dataAccess.*;
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
    private UserDAO userdoa;
    @Autowired
    private SuggestionDAO suggestionDAO;
    @Autowired
    private StrawpollDAO strawpollDoa;

    //---------------------------------------------------------------------------
    // User services
    //---------------------------------------------------------------------------
    public List<User> getAllUsers() {
        return userdoa.getAllUsers();
    }

    /**
     * @see UserDAO#deleteUserById_user(String)
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
     * @see UserDAO#save(Object)
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
     * @see UserDAO#getAllAdmins()
     */
    public List<User> getAllAdmins() {
        return userdoa.getAllAdmins();
    }

    /**
     * @see UserDAO#findById(Object)
     */
    public Optional<User> getUserById(String user_id) {
        return userdoa.findById(user_id);
    }

    /**
     * @see UserDAO#findByFirst_name(String)
     */
    public List<User> getUserByFirst_name(String first_name) {
        return userdoa.findByFirst_name(first_name);
    }

    /**
     * @see UserDAO#findByLast_name(String)
     */
    public List<User> getUserByLast_name(String last_name) {
        return userdoa.findByLast_name(last_name);
    }


    //----------------------------------------------------------------------------
    // Suggestion services
    //----------------------------------------------------------------------------

    /**
     * @see SuggestionDAO#findAll()
     */
    public List<Suggestion> getAllSuggestions() {
        return suggestionDAO.findAll();
    }

    /**
     * @see SuggestionDAO#findBySuggestion_author(String)
     */
    public List<Suggestion> getSuggestionByAuthor(String author) {
        return suggestionDAO.findBySuggestion_author(author);
    }

    /**
     * @see SuggestionDAO#findBySuggestion_creation_dateRange(Date, Date)
     */
    public List<Suggestion> getSuggestionByDateRange(Date lowerBoundDate, Date upperBoundDate)
    {
        return suggestionDAO.findBySuggestion_creation_dateRange(lowerBoundDate, upperBoundDate);
    }

    //----------------------------------------------------------------------------
    // Strawpoll services
    //----------------------------------------------------------------------------

    /**
     * @see StrawpollDAO#findAll()
     */
    public List<Strawpoll> getAllStrawpolls()
    {
        return strawpollDoa.findAll();
    }

    /**
     * @see StrawpollDAO#findByStrawpoll_expiration_date(Date, Date)
     */
    public List<Strawpoll> getStrawpollByExpiration(Date lowerBound, Date upperBound)
    {
        return strawpollDoa.findByStrawpoll_expiration_date(lowerBound, upperBound);
    }

    /**
     * @see StrawpollDAO#findByStrawpoll_creation_date(Date, Date)
     */
    public List<Strawpoll> getStrawpollByCreation(Date lowerBound, Date upperBound)
    {
        return strawpollDoa.findByStrawpoll_creation_date(lowerBound, upperBound);
    }

    /**
     * @see StrawpollDAO#findByStrawpoll_author(String)
     */
    public List<Strawpoll> getStrawpollByAuthor(String author)
    {
        return strawpollDoa.findByStrawpoll_author(author);
    }

    /**
     * @see StrawpollDAO#findByTitle(String)
     */
    public List<Strawpoll> getStrawpollByTitle(String title)
    {
        return strawpollDoa.findByTitle(title);
    }


    // --------------------------------------------------------------------------------
    // Connection services
    // ---------------------------------------------------------------------------------

    public List<User> authentifyUser(String username, String hashPassword)
    {
        return userdoa.findUserByIdPassword(username, hashPassword);
    }
}
