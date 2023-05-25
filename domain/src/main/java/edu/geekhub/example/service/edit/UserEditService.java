package edu.geekhub.example.service.edit;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.service.UserService;
import edu.geekhub.example.registration.ValidationResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEditService {

    private final EditFormValidator editFormValidator;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserEditService(EditFormValidator editFormValidator, UserService userService,
        PasswordEncoder passwordEncoder) {
        this.editFormValidator = editFormValidator;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public ValidationResult updatePassword(UserPasswordEditForm passwordForm, User user) {
        ValidationResult validationResult = editFormValidator.validatePasswordEditForm(user,
            passwordForm);
        if (!validationResult.hasErrors()) {
            user.setPassword(passwordEncoder.encode(passwordForm.getNewPassword()));
            userService.updateUser(user);
        }
        return validationResult;
    }

    public ValidationResult updateUser(UserEditForm userForm, User user) {
        ValidationResult validationResult = editFormValidator.validateUserEditForm(userForm, user);
        if (!validationResult.hasErrors()) {
            user.setFirstName(userForm.getFirstName());
            user.setLastName(userForm.getLastName());
            user.setEmail(userForm.getEmail().toLowerCase());
            userService.updateUser(user);
        }
        return validationResult;
    }
}
