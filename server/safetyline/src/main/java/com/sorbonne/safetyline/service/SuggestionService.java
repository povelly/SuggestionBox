package com.sorbonne.safetyline.service;
/**
 *
 * @author  Georges Mathieu Memmi Sacha
 *
 * Service offering suggestions manipulation methods
 *
 */

import com.sorbonne.safetyline.dataAccess.SuggestionDAO;
import com.sorbonne.safetyline.model.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

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
     */
    public void creationSuggestion(String content, String author) {
    	Suggestion s = new Suggestion();
    	s.setSuggestionContent(content);
    	s.setSuggestionCreationDate(new Date(System.currentTimeMillis()));
    	if (author != null) { s.setSuggestionAuthor(author); }
    	
    	suggestionDAO.save(s);
    	
    	// TODO gérer mail
    	
    }

}
