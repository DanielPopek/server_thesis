package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class WrongLoginDataResponseDTO extends CustomResponseDTO implements Serializable{
    private static String MESSAGE="WRONG LOGIN DATA";
    public WrongLoginDataResponseDTO(){
        super(MESSAGE);
    }
}
