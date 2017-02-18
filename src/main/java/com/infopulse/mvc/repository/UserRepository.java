package com.infopulse.mvc.repository;

import com.infopulse.mvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vedmant on 2/18/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
