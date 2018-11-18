package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class LoginSuccessfulResponseDTO extends CustomResponseDTO implements Serializable{
    private static String MESSAGE="LOGIN SUCCESSFUL";
    private String apiKey;

    public LoginSuccessfulResponseDTO(){
        super(MESSAGE);
    }

    public LoginSuccessfulResponseDTO(String apiKey){
        super(MESSAGE);
        this.apiKey=apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
