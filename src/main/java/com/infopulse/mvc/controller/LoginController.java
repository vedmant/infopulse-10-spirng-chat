package com.infopulse.mvc.controller;

import com.infopulse.mvc.domain.Role;
import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.service.LoginService;
import com.infopulse.mvc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by vedmant on 2/4/17.
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "getLogin")
    public ModelAndView getLogin(@ModelAttribute("user") @Validated UserDTO user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("formHandler", "/login");
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, name = "postLogin")
    public String postLogin(@RequestParam(value = "login") String login,
                            @RequestParam(value = "password") String password,
                            HttpSession session,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        UserDTO userDTO = loginService.verifyLogin(login, password);

        if (userDTO == null) {
            session.setAttribute("error", "Login incorrect");
            return "redirect:login";
        }

        session.setAttribute("success", "Successfully logged in");
        session.setAttribute("user", userDTO);

        if (userDTO.getRole() == Role.ADMIN) {
            return "redirect:admin";
        }

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                cookie.setHttpOnly(false);
                response.addCookie(cookie);
            }
        }

        session.setAttribute("sockPath", "/sock");

        return "redirect:chat";
    }

}
