package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class RegisterWrongEmailResponseDTO extends CustomResponseDTO implements Serializable{
    private static String MESSAGE="EMAIL ALREADY EXISTS";

    public RegisterWrongEmailResponseDTO(){
        super(MESSAGE);
    }

    public RegisterWrongEmailResponseDTO(String apiKey){
        super(MESSAGE);
    }
}
