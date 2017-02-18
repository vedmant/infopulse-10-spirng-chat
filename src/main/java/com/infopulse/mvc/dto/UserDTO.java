package com.infopulse.mvc.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by vedmant on 2/18/17.
 */
@Setter
@Getter
public class UserDTO {

    @NotNull
    @Pattern(regexp = "\\w{3,}", message = "Login is incorrect")
    private String name;

    @NotNull
    private String login;

    @NotNull
    private String password;
}
