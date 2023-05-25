package edu.geekhub.example.registration;

public class UserRegistrationForm {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String passwordConfirmation;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationForm setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationForm setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationForm setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationForm setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public UserRegistrationForm setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationForm setEmail(String email) {
        this.email = email;
        return this;
    }
}
