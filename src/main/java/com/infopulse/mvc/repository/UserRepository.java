package com.infopulse.mvc.repository;

import com.infopulse.mvc.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Stepan
 */
@Repository
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select u from com.infopulse.mvc.domain.User u where u.login =:login")
    User findUserByLogin(@Param("login") String login);

}