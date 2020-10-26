package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     *
     * @return                  all admins in database
     */
    @Query(value = "SELECT * FROM User WHERE isAdmin = TRUE", nativeQuery = true)
    public List<Admin> getAllAdmins();

    /**
     *
     * @return                  all users in database including admins
     */
    @Query(value = "SELECT * FROM User", nativeQuery = true)
    public List<User> getAllUsers();

    /**
     *  Remove the user with the given username
     * @param id_user           String username (email)
     */
    @Modifying
    @Query(value = "DELETE FROM User WHERE id_user = ?1", nativeQuery = true)
    public void removeUserId(@Param("id_user")String id_user);

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
     * @param id_user           a user
     * @return                  every strawpoll the user participated to
     */
    @Query(value = "SELECT * " +
            "FROM User u" +
            "WHERE id_user = ?1" +
            "LEFT JOIN vote sv ON u.id_user = v.id_user_transition" +
            "LEFT JOIN Choice c ON sv.id_choice_transition" +
            "LEFT JOIN Strawpoll s ON c.id_strawpoll = s.id_strawpoll ", nativeQuery = true)
    public List<Strawpoll> getAllChoicesUser(@Param("user") String id_user);

    @Query(value = "SELECT * " +
            "FROM Suggestion s WHERE s.author = ?1)", nativeQuery = true)
    public List<Suggestion> getAllSuggestionsUserNotAnonymous(@Param("user") String id_user);

}
