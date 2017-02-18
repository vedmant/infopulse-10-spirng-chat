package com.infopulse.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by vedmant on 2/4/17.
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET, name = "getAdmin")
    public ModelAndView getAdmin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");

        return modelAndView;
    }
}
