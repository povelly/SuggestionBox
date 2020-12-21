package com.sorbonne.safetyline.service;

import com.sorbonne.safetyline.dataAccess.*;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.utils.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userdoa;

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

        String password = com.sorbonne.safetyline.utils.Password.generateFirstPassword();
        /**
         * Send email to user with his password
         */
        String hashPassword = Password.sha256(password);
        User u = new User();
        u.setUserId(user_id);
        u.setLastName(last_name);
        u.setFirstName(first_name);
        u.setPassword(password);
        u.setSuggestionList(new ArrayList<>());
        u.setAdmin(false);
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
        u.setUserId(user_id);
        u.setLastName(last_name);
        u.setFirstName(first_name);
        u.setPassword(password);
        u.setSuggestionList(new ArrayList<>());
        u.setAdmin(true);
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


    // --------------------------------------------------------------------------------
    // Connection services
    // ---------------------------------------------------------------------------------

    public List<User> authentifyUser(String username, String hashPassword)
    {
        return userdoa.findUserByIdPassword(username, hashPassword);
    }
}
