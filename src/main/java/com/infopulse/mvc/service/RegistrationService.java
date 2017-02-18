package com.infopulse.mvc.service;

import com.infopulse.mvc.domain.Role;
import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.domain.UserRole;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.repository.UserRepository;
import com.infopulse.mvc.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by vedmant on 2/18/17.
 */
@Service
@Transactional
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    public boolean createUser(UserDTO userDTO) {
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRoleRepository.save(userRole);

        User user = convertUserDTOtoUser(userDTO);
        user.setRole(userRole);

        return userRepository.save(user) != null;
    }

    private User convertUserDTOtoUser(UserDTO userDTO) {
        User u = new User();

        u.setName(userDTO.getName());
        u.setLogin(userDTO.getLogin());
        u.setPassword(userDTO.getPassword());

        return u;
    }
}
