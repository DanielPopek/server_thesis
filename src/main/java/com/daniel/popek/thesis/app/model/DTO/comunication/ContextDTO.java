package com.daniel.popek.thesis.app.model.DTO.comunication;

import com.daniel.popek.thesis.app.component.constant.enums.ContextSuccessCodeEnum;

public class ContextDTO {

    private String position;
    private ContextSuccessCodeEnum code;
    private String message;
    private String event;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ContextSuccessCodeEnum getCode() {
        return code;
    }

    public void setCode(ContextSuccessCodeEnum code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
