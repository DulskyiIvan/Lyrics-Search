package edu.geekhub.example.registration;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.service.UserService;
import edu.geekhub.example.service.role.service.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {

    private final UserRegistrationFormValidator userRegistrationFormValidator;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRegistrationFormValidator userRegistrationFormValidator,
        UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRegistrationFormValidator = userRegistrationFormValidator;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserRegistrationResult register(UserRegistrationForm userRegistrationForm) {
        ValidationResult validationResult = userRegistrationFormValidator.validate(
            userRegistrationForm);
        if (!validationResult.hasErrors()) {
            userService.saveUser(getUserFromRegistrationForm(userRegistrationForm));
            return UserRegistrationResult.ok();
        } else {
            return UserRegistrationResult.fail(validationResult.getErrors());
        }
    }

    public User getUserFromRegistrationForm(UserRegistrationForm userRegistrationForm) {
        User user = new User();
        user.setFirstName(userRegistrationForm.getFirstName());
        user.setLastName(userRegistrationForm.getLastName());
        user.setUsername(userRegistrationForm.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
        user.setEmail(userRegistrationForm.getEmail());
        user.setRole(roleService.getRoleByTitle("USER"));
        return user;
    }
}
