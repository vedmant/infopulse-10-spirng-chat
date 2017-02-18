package com.infopulse.mvc.repository;

import com.infopulse.mvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by vedmant on 2/18/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query(value = "select new u from com.infopulse.mvc.domain.User u where u.login = :login")
//    public User findUserByLogin(@Param("login") String login);

    public User findByLogin(String login);
}
