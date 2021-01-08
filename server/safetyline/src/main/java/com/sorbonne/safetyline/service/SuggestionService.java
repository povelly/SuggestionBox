package com.sorbonne.safetyline.service;
/**
 *
 * @author  Georges Mathieu Memmi Sacha
 *
 * Service offering suggestions manipulation methods
 *
 */

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.sorbonne.safetyline.dataAccess.SuggestionDAO;
import com.sorbonne.safetyline.model.Suggestion;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.utils.MailJetUtil;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuggestionService {
    @Autowired
    private SuggestionDAO suggestionDAO;

    /**
     * @see SuggestionDAO#findAll()
     */
    public List<Suggestion> getAllSuggestions() {
        return suggestionDAO.findAll();
    }
    
    /**
     * @see SuggestionDAO#findById(Integer)
     */
    public Optional<Suggestion> getSuggestionById(Integer id) {
        return suggestionDAO.findById(id);
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
    
    /**
     * Creates a suggestion
     * @throws MailjetSocketTimeoutException 
     * @throws MailjetException 
     * @throws JSONException 
     */
    public void creationSuggestion(String content, String author, List<User> admins) throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	Suggestion s = new Suggestion();
    	s.setSuggestionContent(content);
    	s.setSuggestionCreationDate(new Date(System.currentTimeMillis()));
    	if (author != null) { s.setSuggestionAuthor(author); }
    	
    	suggestionDAO.save(s);
    	
    	// Send mail to all admin
	    List<String> adminsMail = admins.stream()
	    		.map(User::getUserId)
	    		.collect(Collectors.toCollection(ArrayList::new));
	    
	    /**
		 * Send an email to all the admins
		 */
		MailJetUtil.sendMultiple(adminsMail, "Nouvelle suggestion sur la SuggestionBox de Safetyline",
				"Bonjour, une nouvelle suggestion a été envoyée par un utilisateur");
    }

}
