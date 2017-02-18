package com.infopulse.mvc.service;

import com.infopulse.mvc.domain.Role;
import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.domain.UserRole;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.repository.UserRepository;
import com.infopulse.mvc.repository.UserRoleRepository;
import com.infopulse.mvc.service.util.UserConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vedmant on 2/18/17.
 */
@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional(rollbackFor = UserServiceException.class)
    public void createUser(UserDTO userDTO) {
        try {
            UserRole userRole = new UserRole();
            userRole.setRole(Role.USER);
            userRoleRepository.save(userRole);

            User user = UserConverterUtil.convertUserDTOtoUser(userDTO);
            user.setRole(userRole);

            userRepository.save(user);
        } catch (JpaSystemException e) {
            throw new UserServiceException("User login exists");
        }
    }
}
