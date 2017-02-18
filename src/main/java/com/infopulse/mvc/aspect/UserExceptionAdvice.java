package com.infopulse.mvc.aspect;

import com.infopulse.mvc.service.UserServiceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 * Created by vedmant on 2/18/17.
 */
@ControllerAdvice
public class UserExceptionAdvice {

    @ExceptionHandler(value = UserServiceException.class)
    public String getRegistrationError(UserServiceException e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attributes.getRequest().getSession(false);
        session.setAttribute("error", e.getMessage());

        return "redirect:register";
    }

}
