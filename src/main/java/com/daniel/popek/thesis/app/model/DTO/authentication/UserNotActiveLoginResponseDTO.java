package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class UserNotActiveLoginResponseDTO extends CustomResponseDTO implements Serializable {
    private static String MESSAGE="USER IS NOT ACTIVE";
    public UserNotActiveLoginResponseDTO(){
        super(MESSAGE);
    }
}
