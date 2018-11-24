package com.daniel.popek.thesis.app.model.DTO.comunication;

import com.daniel.popek.thesis.app.component.constant.enums.ContextSuccessCodeEnum;

import java.util.List;

public class ContextDTO {

    private String intentHash;
    private String message;
    private List<String> events;
    private Object data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String>  getEvents() {
        return events;
    }

    public void setEvents(List<String>  events) {
        this.events = events;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getIntentHash() {
        return intentHash;
    }

    public void setIntentHash(String intentHash) {
        this.intentHash = intentHash;
    }
}
