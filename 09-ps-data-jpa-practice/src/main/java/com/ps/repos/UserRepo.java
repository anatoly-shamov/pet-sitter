package com.ps.repos;

import com.ps.ents.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by iuliana.cosmina on 2/23/16.
 */
//TODO 44. Complete the definition of this interface to make the tests in TestUserRepo.java pass.
public interface UserRepo extends JpaRepository<User, Long> {


    @Query("select u from User u where u.username like %:un%")
    List<User> findAllByUserName(@Param("un") String username);

    @Query("select u from User u where u.username = :un")
    User findOneByUsername(@Param("un") String username);

    @Query("select u.username from User u where u.id = :id")
    String findUsernameById(@Param("id") Long id);

    @Query("select count(u) from User u")
    long countUsers();

}