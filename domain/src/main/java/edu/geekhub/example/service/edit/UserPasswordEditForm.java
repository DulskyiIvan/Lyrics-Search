package edu.geekhub.example.service.edit;

public class UserPasswordEditForm {

    private String oldPassword;
    private String newPassword;
    private String passwordConfirmation;

    public String getOldPassword() {
        return oldPassword;
    }

    public UserPasswordEditForm setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserPasswordEditForm setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public UserPasswordEditForm setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
        return this;
    }
}
