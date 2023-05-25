package edu.geekhub.example.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public String getRegistrationForm(Model model) {
        model.addAttribute("userRegistrationForm", new UserRegistrationForm());
        return "registration/registration.html";
    }

    @PostMapping
    public String register(UserRegistrationForm userRegistrationForm, Model model) {
        UserRegistrationResult userRegistrationResult = userRegistrationService.register(
            userRegistrationForm);
        if (userRegistrationResult.hasErrors()) {
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            model.addAttribute("userRegistrationResult", userRegistrationResult);
            return "registration/registration.html";
        } else {
            return "redirect:login";
        }
    }
}
