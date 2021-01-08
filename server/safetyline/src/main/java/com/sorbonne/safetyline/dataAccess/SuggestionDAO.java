package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SuggestionDAO extends JpaRepository<Suggestion, Integer> {
    /**
     * @return              all suggestions
     */
    @Query(value = "SELECT * FROM suggestion",nativeQuery = true)
    public List<Suggestion> findAll();
    
    /**
     * @return              one suggestion equal to the given id
     */
    @Query(value = "SELECT * FROM suggestion where suggestion_id = ?1",nativeQuery = true)
    public Optional<Suggestion> findById(Integer id);

    /**
     * author may have submitted more suggestions but decided not to sign them
     * @param author        the author of the suggestions
     * @return              all suggestion submitted by the author given in parameter
     */
    @Query(value = "SELECT * FROM suggestion s " +
            "WHERE s.suggestion_author = ?1",nativeQuery = true)
    public List<Suggestion> findBySuggestion_author(String author);

    /**
     *
     * @param lowerBound    the lower bound of date searched
     * @param upperBound    the upper bound of date searched
     * @return              all suggestions submitted between lowerBound and upperBound
     */
    @Query(value = "SELECT * FROM suggestion s " +
            "WHERE s.suggestion_creation_date BETWEEN ?1 AND ?2",nativeQuery = true)
    public List<Suggestion> findBySuggestion_creation_dateRange(Date lowerBound, Date upperBound);
}
