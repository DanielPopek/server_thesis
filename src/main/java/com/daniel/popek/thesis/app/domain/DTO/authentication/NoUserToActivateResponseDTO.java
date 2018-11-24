package com.daniel.popek.thesis.app.domain.DTO.authentication;

import java.io.Serializable;

public class NoUserToActivateResponseDTO extends CustomResponseDTO implements Serializable{
    private static String MESSAGE="NO SUCH A USER TO ACTIVATE";

    public NoUserToActivateResponseDTO(){
        super(MESSAGE);
    }

    public NoUserToActivateResponseDTO(String apiKey){
        super(MESSAGE);
    }

}
