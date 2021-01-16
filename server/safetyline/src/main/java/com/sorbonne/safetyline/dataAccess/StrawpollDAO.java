package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.Strawpoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StrawpollDAO extends JpaRepository<Strawpoll, Integer> {

    /**
     * @return                  all strawpoll in the table
     */
    @Query(value = "SELECT * FROM strawpoll", nativeQuery = true)
    public List<Strawpoll> findAll();

    /**
     *
     * @param lowerBound        the lower bound of expiration date searched
     * @param upperBound        the upper bound of expiration date searched
     * @return                  the strawpolls with expiration dates between the range
     */
    @Query(value = "SELECT * FROM strawpoll s " +
            "WHERE s.strawpoll_expiration_date BETWEEN ?1 AND ?2" , nativeQuery = true)
    public List<Strawpoll> findByStrawpoll_expiration_date(Date lowerBound, Date upperBound);

    /**
     *
     * @param lowerBound        the lower bound of creation date searched
     * @param upperBound        the upper bound of creation date searched
     * @return                  the strawpolls with creation dates between the range
     */
    @Query(value = "SELECT * FROM strawpoll s " +
            "WHERE s.strawpoll_creation_date BETWEEN ?1 AND ?2" , nativeQuery = true)
    public List<Strawpoll> findByStrawpoll_creation_date(Date lowerBound, Date upperBound);

    /**
     *
     * @param title             of the suggestions searched
     * @return                  the strawpolls with the given title
     */
    @Query(value = "SELECT * FROM strawpoll s " +
            "WHERE s.title = ?1", nativeQuery = true)
    public List<Strawpoll> findByTitle(String title);

    /**
     *
     * @param author
     * @return                  all submitted with the given author
     */
    @Query(value = "SELECT * FROM strawpoll s " +
            "WHERE s.strawpoll_author = ?1", nativeQuery = true)
    public List<Strawpoll> findByStrawpoll_author(String author);
}
