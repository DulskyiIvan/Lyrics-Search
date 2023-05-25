package edu.geekhub.example.service.edit;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.registration.ValidationResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/edit")
public class UserEditController {

    private final UserEditService userEditService;

    public UserEditController(UserEditService userEditService) {
        this.userEditService = userEditService;
    }

    @GetMapping
    public String getEditForm(ModelMap modelMap, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        putUserInfo(modelMap, user);
        model.addAttribute("userForm", new UserEditForm());
        model.addAttribute("passwordForm", new UserPasswordEditForm());
        return "edit/editUser";
    }
    private void putUserInfo(ModelMap modelMap, User user) {
        modelMap.put("firstName", user.getFirstName());
        modelMap.put("lastName", user.getLastName());
        modelMap.put("email", user.getEmail());
    }
    @PostMapping("/update-user")
    public String changeDataOfUser(UserEditForm userForm, Model model, ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ValidationResult validationResult = userEditService.updateUser(userForm, user);
        if (validationResult.hasErrors()) {
            putUserInfo(modelMap, user);
            addEditForm(userForm, model);
            model.addAttribute("editUserValidationResult", validationResult);
            return "edit/editUser";
        }
        return "redirect:/home";
    }

    @PostMapping("/update-password")
    public String changePasswordOfUser(UserEditForm userForm, UserPasswordEditForm passwordForm, Model model, ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ValidationResult passwordValidationResult = userEditService.updatePassword(passwordForm, user);
        String successMessage = "Password changed successfully";
        putUserInfo(modelMap, user);
        addEditForm(userForm, model);
        if (passwordValidationResult.hasErrors()) {
            model.addAttribute("validationPasswordResult", passwordValidationResult);
            return "edit/editUser";
        }
        model.addAttribute("successMessage",successMessage);
        return "edit/editUser";
    }

    private void addEditForm(UserEditForm userForm, Model model) {
        model.addAttribute("userForm", userForm);
        model.addAttribute("passwordForm", new UserPasswordEditForm());
    }
}
