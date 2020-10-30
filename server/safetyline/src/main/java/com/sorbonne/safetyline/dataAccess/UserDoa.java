package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDoa extends JpaRepository<User, String> {
    //List<User> findByIsAdminTrue();
    /**
     *
     * @return                  all admins in database
     */
    @Query(value = "SELECT * FROM User WHERE isAdmin", nativeQuery = true)
    public List<User> getAllAdmins();

    /**
     *
     * @return                  all users in database including admins
     */
    @Query(value = "SELECT * FROM User", nativeQuery = true)
    public List<User> getAllUsers();

    /**
     *  Remove the user with the given username
     //* @param id_user           String username (email)
     */
    @Modifying
    @Query(value = "DELETE FROM User WHERE id_user = ?1", nativeQuery = true)
    public void deleteUserById_user(@Param("id_user")String id_user);
    /**
     *
     * @param name              string
     * @return                  all users with the name given
     */
    @Query(value = "SELECT * FROM User WHERE name = ?1", nativeQuery = true)
    public List<User> getUsersByName(@Param("name") String name);


    /**
     *
     * @param firstName         string username
     * @return                  all users with the first name given in parameter
     */
    @Query(value = "SELECT * FROM User WHERE name = ?1", nativeQuery = true)
    public List<User> getUsersByFirstName(@Param("firstName") String firstName);

    /**
     *
    // * @param id_user           a user
     * @return                  every strawpoll the user participated to
     */
    @Query(value = "SELECT s.id_strawpoll, creation_date, deadline_time, title FROM User u" +
            " LEFT JOIN vote v ON u.id_user = v.id_user_transition" +
            " LEFT JOIN Choice c ON v.id_choice_transition = c.id_choice " +
            " LEFT JOIN Strawpoll s ON c.id_strawpoll = s.id_strawpoll " +
            " WHERE id_user = ?1 " , nativeQuery = true)
    public List<Strawpoll> getAllChoicesUser(String id_user);

    @Query(value = "SELECT Id_recommendation, suggestion_content, author " +
            "FROM Suggestions s, User u " +
             "WHERE u.id_user = ?1 AND s.author = u.id_user" ,nativeQuery=true)
    public List<Suggestion> getAllSuggestions(String id_user);

   // @Query(value = "SELECT * " +
    //        "FROM Suggestion s WHERE s.author = ?1)", nativeQuery = true)
    //public List<Suggestion> getAllSuggestionsUserNotAnonymous(@Param("user") String id_user);
}
