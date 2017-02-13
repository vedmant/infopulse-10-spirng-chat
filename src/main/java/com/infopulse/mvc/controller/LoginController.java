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
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "getLogin")
    public ModelAndView getLogin(@ModelAttribute("user") @Validated User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("formHandler", "/login");
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, name = "postLogin")
    public ModelAndView postLogin() {

        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

}
