package com.daniel.popek.thesis.app.model.DTO.authentication;


import java.io.Serializable;

public class LoginRequestBodyDTO implements Serializable {

    private String login;

    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
