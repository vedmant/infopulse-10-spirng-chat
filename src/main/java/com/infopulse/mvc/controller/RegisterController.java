package com.infopulse.mvc.controller;

import com.infopulse.mvc.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by vedmant on 2/4/17.
 */
@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.GET, name = "getRegister")
    public ModelAndView getRegister(@ModelAttribute("user") @Validated User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("formHandler", "/register");
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, name = "postRegister")
    public ModelAndView postRegister() {

        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

}
