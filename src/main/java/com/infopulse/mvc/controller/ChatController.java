package com.infopulse.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by vedmant on 2/4/17.
 */
@Controller
public class ChatController {

    @RequestMapping(value = "/chat", method = RequestMethod.GET, name = "getChat")
    public String getChat(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:login";
        }

        return "chat";
    }
}
