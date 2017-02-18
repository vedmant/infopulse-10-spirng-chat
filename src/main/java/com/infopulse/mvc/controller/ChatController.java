package com.infopulse.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by vedmant on 2/4/17.
 */
@Controller
public class ChatController {

    @RequestMapping(value = "/chat", method = RequestMethod.GET, name = "getChat")
    public ModelAndView getChat() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chat");

        return modelAndView;
    }
}
