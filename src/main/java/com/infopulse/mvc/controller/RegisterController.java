package com.infopulse.mvc.controller;

import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by vedmant on 2/4/17.
 */
@Controller
public class RegisterController {

    @Autowired
    RegistrationService registrationService;

    @RequestMapping(value = "/register", method = RequestMethod.GET, name = "getRegister")
    public ModelAndView getRegister(@ModelAttribute("user") @Validated UserDTO user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("formHandler", "/register");
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, name = "postRegister")
    public ModelAndView postRegister(@ModelAttribute("user") @Validated UserDTO user,
                                     HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        registrationService.createUser(user);

        modelAndView.setViewName("login");

        return modelAndView;
    }

}
