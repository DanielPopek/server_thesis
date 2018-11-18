package com.daniel.popek.thesis.app.model.DTO.authentication;

import java.io.Serializable;

public class CustomResponseDTO implements Serializable{
    private String message;

    public CustomResponseDTO(){};

    public CustomResponseDTO(String message)
    {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
