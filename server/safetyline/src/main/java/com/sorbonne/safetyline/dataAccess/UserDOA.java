package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDOA extends JpaRepository<User, String> {

    /**
     * @return                 all users in databases
     */
    @Query(value = "SELECT * FROM user", nativeQuery = true)
    public List<User> getAllUsers();

    /**
     * @return                 all admins in database
     */
    @Query(value = "SELECT * FROM user WHERE is_Admin", nativeQuery = true)
    public List<User> getAllAdmins();

    /**
     *  Remove the user with the given username
     * @param id_user           username (email)
     */
    @Modifying
    @Query(value = "DELETE FROM user WHERE id_user = ?1", nativeQuery = true)
    public void deleteUserById_user(@Param("id_user")String id_user);

    /**
     *
     * @param last_name         last_name of the user searched
     * @return                  all users with the name given
     */
    @Query(value = "SELECT * FROM user WHERE last_name = ?1", nativeQuery = true)
    public List<User> findByLast_name(@Param("name") String last_name);


    /**
     *
     * @param firstName         string username
     * @return                  all users with the first name given in parameter
     */
    @Query(value = "SELECT * FROM user WHERE first_name = ?1", nativeQuery = true)
    public List<User> findByFirst_name(@Param("firstName") String firstName);
    
    
    /**
     * 
     * @param username		string username
     * @param hashpassword	password hashed
     * @return				the user
     */
    @Query(value = "SELECT * FROM user WHERE user_id=?1 AND password=?2", nativeQuery = true)
    public List<User> findUserByIdPassword(String username, String hashpassword);

}
