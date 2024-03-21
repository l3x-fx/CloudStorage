package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;


@Controller
@RequestMapping
public class LoginController {
@Autowired
    private HttpSession httpSession;
    @GetMapping("/login")
    public String loginView(HttpSession session, Model model) {
        Boolean signupSuccess = (Boolean) session.getAttribute("signupSuccess");

        if (signupSuccess != null && signupSuccess) {
            model.addAttribute("signupSuccess", true);
            session.removeAttribute("signupSuccess");
        }
        return "login";
    }


}
