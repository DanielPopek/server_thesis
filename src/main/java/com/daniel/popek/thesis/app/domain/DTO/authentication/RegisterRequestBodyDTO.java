package com.daniel.popek.thesis.app.domain.DTO.authentication;

import java.io.Serializable;

/**
 *
 */
public class RegisterRequestBodyDTO implements Serializable {

//    @Pattern(regexp = ValidationUtils.EMAIL_REGEXP, flags = Pattern.Flag.UNICODE_CASE)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    public RegisterRequestBodyDTO() {
    }

    public RegisterRequestBodyDTO(String email, String password, String firstName, String lastName, int professionTypeId) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
