package com.infopulse.mvc.repository;

import com.infopulse.mvc.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vedmant on 2/18/17.
 */
@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

}
