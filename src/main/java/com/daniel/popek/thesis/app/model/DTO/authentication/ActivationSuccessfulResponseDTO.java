package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class ActivationSuccessfulResponseDTO extends CustomResponseDTO implements Serializable{
    private static String MESSAGE="USERS ACCOUNT ACTIVATED SUCCESSFULLY";

    public ActivationSuccessfulResponseDTO(){
        super(MESSAGE);
    }

    public ActivationSuccessfulResponseDTO(String apiKey){
        super(MESSAGE);
    }

}
