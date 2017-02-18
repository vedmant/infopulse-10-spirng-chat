package com.infopulse.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

/**
 * Created by vedmant on 2/4/17.
 */
@Controller
public class LogoutController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET, name = "getLogout")
    public String getLogout(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:/";
    }
}
