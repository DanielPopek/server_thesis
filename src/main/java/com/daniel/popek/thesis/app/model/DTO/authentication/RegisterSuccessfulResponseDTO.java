package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class RegisterSuccessfulResponseDTO extends CustomResponseDTO implements Serializable{
    private static String MESSAGE="REGISTERED SUCCESSFULLY";

    public RegisterSuccessfulResponseDTO(){
        super(MESSAGE);
    }

    public RegisterSuccessfulResponseDTO(String apiKey){
        super(MESSAGE);
    }
}
