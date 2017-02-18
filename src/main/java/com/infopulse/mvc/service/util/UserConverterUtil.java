package com.infopulse.mvc.service.util;

import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.dto.UserDTO;

/**
 * Created by vedmant on 2/18/17.
 */
public class UserConverterUtil {

    public static User convertUserDTOtoUser(UserDTO userDTO) {
        User u = new User();

        u.setName(userDTO.getName());
        u.setLogin(userDTO.getLogin());
        u.setPassword(userDTO.getPassword());

        return u;
    }

    public static UserDTO convertUserToUserDTO(User user) {
        UserDTO u = new UserDTO();

        u.setName(user.getName());
        u.setLogin(user.getLogin());
        u.setPassword(user.getPassword());
        u.setRole(user.getRole().getRole());

        return u;
    }

}
