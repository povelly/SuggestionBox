package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.Admin;
import com.sorbonne.safetyline.model.User;
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
     * @return                  all users in database
     */
    @Query(value = "SELECT * FROM User", nativeQuery = true)
    public List<User> getAllUsers();

    /**
     *  Remove the user with the given username
     * @param id_user           String username (email)
     */
    @Modifying
    @Query(value = "DELETE FROM User WHERE id_user = :id_user", nativeQuery = true)
    public void removeUserId(@Param("id_user")String id_user);

    @Query(value = "SELECT * FROM User WHERE name = :name", nativeQuery = true)
    public void getUsersByName(@Param("name") String name);

    @Query(value = "SELECT * FROM User WHERE name = :firstName", nativeQuery = true)
    public void getUsersByFirstName(@Param("firstName") String firstName);
}
