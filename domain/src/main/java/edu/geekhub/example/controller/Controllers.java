package edu.geekhub.example.controller;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.genre.model.Genre;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Controllers {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getWelcomePage() {
        return "index.html";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getWelcomePage(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.put("firstName", user.getFirstName());
        modelMap.put("lastName", user.getLastName());
        modelMap.put("username", user.getUsername());
        modelMap.put("role", user.getRole());
        return "main/homePage.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(ModelMap modelMap) {
        return "login/login.html";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String getErrorPage() {
        return "main/accessDenied.html";
    }

    @RequestMapping(value = "/admin/admin-page", method = RequestMethod.GET)
    public String getAdminPage(Model model) {
        model.addAttribute("genre", new Genre());
        return "admin/adminPage";
    }
}