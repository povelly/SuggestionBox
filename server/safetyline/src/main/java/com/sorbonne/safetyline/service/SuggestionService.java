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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SuggestionService {
    @Autowired
    private SuggestionDAO suggestionDAO;


    public List<Suggestion> getSuggestions(String author, Date begin, Date end){
        if(author==null && begin==null && end==null)
            return getAllSuggestions();
        Set<Suggestion> results     = new HashSet<>();
        List<Suggestion> authorRes  = suggestionDAO.findBySuggestion_author(author);
        end = end==null?new Date(Long.MAX_VALUE):end;
        begin = begin==null?new Date(Long.MIN_VALUE):begin;
        List<Suggestion> dateRes = suggestionDAO.findBySuggestion_creation_dateRange(begin, end);
        results = authorRes.stream().distinct().filter(dateRes::contains)
                .collect(Collectors.toSet());
        List<Suggestion> res = new ArrayList<>();
        res.addAll(results);
        return res;
    }

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
    	s.setSuggestionAuthor(author);
    	suggestionDAO.save(s);
    	
    	// Send mail to all admin
	    List<String> adminsMail = admins.stream().parallel()
	    		.map(User::getUserId)
	    		.collect(Collectors.toCollection(ArrayList::new));
	    
	    /**
		 * Send an email to all the admins
		 */
		MailJetUtil.sendMultiple(adminsMail, "Nouvelle suggestion sur la SuggestionBox de Safetyline",
				"Bonjour, une nouvelle suggestion a été envoyée par un utilisateur");
    }

}
