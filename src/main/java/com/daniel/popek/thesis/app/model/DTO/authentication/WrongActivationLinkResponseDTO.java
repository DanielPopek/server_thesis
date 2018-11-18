package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class WrongActivationLinkResponseDTO extends CustomResponseDTO implements Serializable{
    private static String MESSAGE="WRONG ACTIVATION LINK";

    public WrongActivationLinkResponseDTO(){
        super(MESSAGE);
    }

    public WrongActivationLinkResponseDTO(String apiKey){
        super(MESSAGE);
    }

}
