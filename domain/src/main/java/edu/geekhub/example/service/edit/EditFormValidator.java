package edu.geekhub.example.service.edit;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.service.UserService;
import edu.geekhub.example.registration.ValidationResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EditFormValidator extends UserDetailsValidator {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public EditFormValidator(PasswordEncoder passwordEncoder, UserService userService) {
        super(userService);
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public ValidationResult validateUserEditForm(UserEditForm userForm, User user) {

        ValidationResult validationResult = new ValidationResult();

        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();

        validateEmail(validationResult, userForm, user);
        validateFirstName(validationResult, firstName);
        validateLastName(validationResult, lastName);
        return validationResult;
    }

    private void validateEmail(ValidationResult validationResult, UserEditForm userForm, User user) {
        User userFromDb = userService.getUserByEmail(userForm.getEmail());
        if (userFromDb != null && (!user.getEmail().equals(userForm.getEmail()))) {
            validationResult.addError("E-mail is already in use");
        } else {
            validateEmailTemplate(validationResult, userForm.getEmail());
        }
    }

    public ValidationResult validatePasswordEditForm(User user, UserPasswordEditForm passwordForm) {
        ValidationResult validationResult = new ValidationResult();
        String newPassword = passwordForm.getNewPassword();
        assert newPassword != null;

        if (isValidUserPassword(passwordForm.getOldPassword(), user.getPassword())) {
            validatePassword(validationResult, newPassword, passwordForm.getPasswordConfirmation());
        } else {
            validationResult.addError("Invalid user password");
        }
        return validationResult;
    }

    private boolean isValidUserPassword(String password, String userPassword) {
        return passwordEncoder.matches(password, userPassword);
    }
}
