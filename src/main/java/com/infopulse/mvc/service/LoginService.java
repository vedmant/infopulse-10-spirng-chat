package com.infopulse.mvc.service;

import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.repository.UserRepository;
import com.infopulse.mvc.service.util.UserConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vedmant on 2/18/17.
 */
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO verifyLogin(String login, String password) {

        User user = userRepository.findByLogin(login);

        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return UserConverterUtil.convertUserToUserDTO(user);
        }

        return null;
    }
}
