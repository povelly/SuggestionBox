package com.sorbonne.safetyline.service;

import com.sorbonne.safetyline.dataAccess.SuggestionDoa;
import com.sorbonne.safetyline.dataAccess.UserDoa;
import com.sorbonne.safetyline.model.Strawpoll;
import com.sorbonne.safetyline.model.Suggestion;
import com.sorbonne.safetyline.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDoa userdoa;
    @Autowired
    private SuggestionDoa suggestionDoa;

    //---------------------------------------------------------------------------
    // User
    //---------------------------------------------------------------------------
    public List<User> getAllUsers() {
        return userdoa.getAllUsers();
    }

    /**
     * @see UserDoa#deleteUserById_user(String)
     */
    @Transactional
    public void deleteUserById_user(String id_user) {
        userdoa.deleteUserById_user(id_user);
    }

    /**
     * @see UserDoa#getAllAdmins()
     */
    public List<User> getAllAdmins() {
        return userdoa.getAllAdmins();
    }

    /**
     * @see UserDoa#findById(Object)
     */
    public Optional<User> getUserById(String user_id) {
        return userdoa.findById(user_id);
    }

    /**
     * @see UserDoa#findByFirst_name(String)
     */
    public List<User> getUserByFirst_name(String first_name) {
        return userdoa.findByFirst_name(first_name);
    }

    /**
     * @see UserDoa#findByLast_name(String)
     */
    public List<User> getUserByLast_name(String last_name) {
        return userdoa.findByLast_name(last_name);
    }


    //----------------------------------------------------------------------------
    // Suggestion
    //----------------------------------------------------------------------------

    /**
     * @see SuggestionDoa#findAll()
     */
    public List<Suggestion> getAllSuggestions() {
        return suggestionDoa.findAll();
    }

    /**
     * @see SuggestionDoa#findBySuggestion_author(String)
     */
    public List<Suggestion> getSuggestionByAuthor(String author) {
        return suggestionDoa.findBySuggestion_author(author);
    }

    /**
     * @see SuggestionDoa#
     */
    public List<Suggestion> getSuggestionByDateRange(Date lowerBoundDate, Date upperBoundDate)
    {
        return suggestionDoa.findBySuggestion_creation_dateRange(lowerBoundDate, upperBoundDate);
    }

}
