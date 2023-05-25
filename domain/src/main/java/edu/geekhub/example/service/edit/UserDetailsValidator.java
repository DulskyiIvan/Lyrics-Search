package edu.geekhub.example.service.edit;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.service.UserService;
import edu.geekhub.example.registration.ValidationResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsValidator {

    private final UserService userService;

    public UserDetailsValidator(UserService userService) {
        this.userService = userService;
    }

    protected void validateUsername(String username, ValidationResult validationResult) {
        User userFromDatabase = userService.getUserByUsername(username);
        if (userFromDatabase != null) {
            validationResult.addError("Username is already in use");
        }

        if (username.length() < 12 && username.length() >= 4) {
            if (username.contains(" ")) {
                validationResult.addError("Username can't contain spaces");
            }
            if (hasSpecialCharacters(username)) {
                validationResult.addError("Username can't contain special characters");
            }
        } else {
            validationResult.addError("Username should contain 4-12 characters");
        }
    }

    protected void validatePassword(ValidationResult validationResult, String password,
        String passwordConfirmation) {
        assert password != null;
        if (!password.equals(passwordConfirmation)) {
            validationResult.addError("Password must match password confirmation");
        }
        if (password.length() > 12 || password.length() < 5) {
            validationResult.addError("Password should contain 5-12 characters");
        }
        if (password.contains(" ")) {
            validationResult.addError("Password can't have spaces");
        }
        if (hasSpecialCharacters(password)) {
            validationResult.addError(
                "Password must not contain special characters : #, !, ~, $, %, ^, &, *, (, ), -, /, :, '.', ',', <, >, ?, |)");
        }
    }

    protected void validateLastName(ValidationResult validationResult, String lastName) {
        if (lastName.length() < 10 && lastName.length() != 0) {
            if (lastName.contains(" ")) {
                validationResult.addError("Last name can't contain spaces");
            }
            if (hasSpecialCharacters(lastName)) {
                validationResult.addError("Last name can't contain special characters");
            }
        } else {
            validationResult.addError("Last name should contain less 10 characters");
        }
    }

    protected void validateFirstName(ValidationResult validationResult, String firstName) {
        if (firstName.length() < 10 && firstName.length() != 0) {
            if (firstName.contains(" ")) {
                validationResult.addError("First name can't contain spaces");
            }
            if (hasSpecialCharacters(firstName)) {
                validationResult.addError("First name can't contain special characters");
            }
        } else {
            validationResult.addError("First name should contain less 10 characters");
        }
    }

    protected void validateEmailTemplate(ValidationResult validationResult, String email) {
        String errorMessage = "The email should have the following template : \n abc@test.ua,\n abc.test@dom.org";

        if (isValidAt(email)) {
            int atIndex = email.indexOf('@');

            String name = email.substring(0, atIndex);
            String domain = email.substring(atIndex + 1);

            if (isInvalidPositionDot(name)) {
                validationResult.addError(errorMessage);
            }
            if (isInvalidPositionDot(domain)) {
                validationResult.addError(errorMessage);
            }
            String specialChars = "!#$%&'*+-/=?^`{|}~";

            if (StringUtils.containsAny(email, specialChars)) {
                validationResult.addError("Email can't contain special characters");
            }
            int domainDotIndex = domain.indexOf('.');
            if (((domain.length() - 1) - domainDotIndex) < 2) {
                validationResult.addError("Domain should contain more than 2 characters after dot");
            }


        } else {
            validationResult.addError("Email should contain one '@'");
            validationResult.addError(errorMessage);
        }
    }

    private boolean isInvalidPositionDot(String inputString) {
        return inputString.startsWith(".") || inputString.startsWith("_") || inputString.endsWith(
            ".") || inputString.endsWith("_");
    }

    private boolean isValidAt(String inputString) {
        return StringUtils.countMatches(inputString, "@") == 1 || !inputString.startsWith("@")
            || !inputString.endsWith("@");
    }

    private boolean hasSpecialCharacters(String inputString) {
        char[] specialChars = new char[]{'#', '!', '~', '$', '%', '^', '&', '*', '(', ')', '-',
            '+', '/', '\\', ':', '.', ',', '<', '>', '?', '|'};
        for (char specialChar : specialChars) {
            if (inputString.contains(Character.toString(specialChar))) {
                return true;
            }
        }
        return false;
    }

}
