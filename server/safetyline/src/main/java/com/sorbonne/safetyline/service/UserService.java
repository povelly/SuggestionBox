package com.sorbonne.safetyline.service;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.sorbonne.safetyline.dataAccess.*;
import com.sorbonne.safetyline.exception.UsernameAlreadyExists;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.utils.MailJetUtil;
import com.sorbonne.safetyline.utils.PasswordUtil;
import org.json.JSONException;
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
    public void deleteUserByIdUser(String user_id) throws JSONException, MailjetException, MailjetSocketTimeoutException {
        userdoa.deleteUserByIdUser(user_id);
        
        /**
		 * Send email to user confirming the deletion
		 */
		MailJetUtil.send(user_id, "Suppression de compte sur la SuggestionBox de Safetyline",
				"Bonjour, votre compte Safetyline SuggestionBox " + user_id + " a été supprimé");
    }

    @Transactional
    public void addUser(String user_id, String first_name, String last_name, boolean isAdmin) throws UsernameAlreadyExists, NoSuchAlgorithmException, UnsupportedEncodingException, MailjetException, MailjetSocketTimeoutException, JSONException {
        if(this.getUserById(user_id).isPresent())
            throw new UsernameAlreadyExists();
        String password = PasswordUtil.generateFirstPassword();

        String hashPassword = PasswordUtil.sha256(password);
        User u = new User();
        u.setUserId(user_id);
        u.setLastName(last_name);
        u.setFirstName(first_name);
        u.setPassword(hashPassword);
        u.setSuggestionList(new ArrayList<>());
        u.setAdmin(isAdmin);
        userdoa.save(u);
        
        /**
		 * Send email to user with his password
		 */
		MailJetUtil.send(user_id, "Creation de compte sur la SuggestionBox de Safetyline",
				"Bonjour, ci-joint vos identifiants de connexion <br> Identifiant : " + user_id + "<br> Password : "
						+ password);
    }

    @Transactional
    /**
     * Generates a new password for a User
     */
    public void forgottenPassword(String userId, User user) 
    		throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	String password = PasswordUtil.generateFirstPassword();
        String hashPassword = PasswordUtil.sha256(password);
        
        user.setPassword(hashPassword);
        userdoa.save(user);
        
        /**
		 * Send email to user with his new password
		 */
		MailJetUtil.send(userId, "Réinitialisation de mot de passe sur la SuggestionBox de Safetyline",
				"Bonjour, suite à votre demande, votre mot de passe a été réinitialisé sur la SuggestionBox <br>"
				+ "Ci-joint vos nouveaux identifiants de connexion <br> Identifiant : " + userId + "<br> Password : "
						+ password);
    }
    
    @Transactional
    /**
     * Update a  password for a User
     */
    public void updatePassword(String userId, String oldPassword, String newPassword, 
    		User user) 
    		throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	String hashOldPassword = PasswordUtil.sha256(oldPassword);
    	
    	if(hashOldPassword.equals(user.getPassword())) {
    		String hashNewPassword = PasswordUtil.sha256(newPassword);
            
            user.setPassword(hashNewPassword);
            userdoa.save(user);
            
            /**
    		 * Send email to user with his new password
    		 */
    		MailJetUtil.send(userId, "Changement de mot de passe sur la SuggestionBox de Safetyline",
    				"Bonjour, suite à votre demande, votre mot de passe a été modifié sur la SuggestionBox <br>"
    				+ "Si vous n'êtes pas à l'origine de cette demande, veuillez protéger votre compte.");
    	}
        
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
