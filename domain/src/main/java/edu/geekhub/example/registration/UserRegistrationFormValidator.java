package edu.geekhub.example.registration;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.service.UserService;
import edu.geekhub.example.service.edit.UserDetailsValidator;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationFormValidator extends UserDetailsValidator {

    private final UserService userService;

    public UserRegistrationFormValidator(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    public ValidationResult validate(
        UserRegistrationForm userRegistrationForm) {
        ValidationResult validationResult = new ValidationResult();
        validatePassword(validationResult, userRegistrationForm.getPassword(),
            userRegistrationForm.getPasswordConfirmation());
        validateUsername(userRegistrationForm.getUsername(), validationResult);
        validateEmail(userRegistrationForm, validationResult);
        validateFirstName(validationResult, userRegistrationForm.getFirstName());
        validateLastName(validationResult, userRegistrationForm.getLastName());
        return validationResult;
    }

    private void validateEmail(UserRegistrationForm userRegistrationForm,
        ValidationResult validationResult) {
        User userFromDatabase = userService.getUserByEmail(userRegistrationForm.getEmail());
        if (userFromDatabase != null) {
            validationResult.addError("Email is already in use");
        } else {
            validateEmailTemplate(validationResult, userRegistrationForm.getEmail());
        }
    }
}
