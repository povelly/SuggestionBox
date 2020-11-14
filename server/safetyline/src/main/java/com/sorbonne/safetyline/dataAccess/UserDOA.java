package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDOA extends JpaRepository<User, String> {

    /**
     * create a simple user in database
     * @param user_id           email of the user
     * @param first_name        first name of the new user
     * @param last_name         last name of the new user
     * @param password          password already hashed of the new user
     * @param is_admin          true if the user is admin false otherwise
     */
    @Modifying
    @Query(value = "INSERT INTO user (user_id, first_name, last_name, password, is_admin) " +
            " values (?1,?2,?3,?4,?5)", nativeQuery = true)
    public void createUser(String user_id, String first_name, String last_name, String password, boolean is_admin);

    /**
     *  Remove the user with the given username
     * @param id_user           username (email)
     */
    @Modifying
    @Query(value = "DELETE FROM user WHERE id_user = ?1", nativeQuery = true)
    public void deleteUserById_user(@Param("id_user")String id_user);

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

}
