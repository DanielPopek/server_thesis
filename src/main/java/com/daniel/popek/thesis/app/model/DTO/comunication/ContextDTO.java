package com.daniel.popek.thesis.app.model.DTO.comunication;

import com.daniel.popek.thesis.app.component.constant.enums.ContextSuccessCodeEnum;

public class ContextDTO {

//    private String conversationHash;
    private String intentHash;
    private String message;
    private String event;
    private Object data;


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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

//    public String getConversationHash() {
//        return conversationHash;
//    }
//
//    public void setConversationHash(String conversationHash) {
//        this.conversationHash = conversationHash;
//    }

    public String getIntentHash() {
        return intentHash;
    }

    public void setIntentHash(String intentHash) {
        this.intentHash = intentHash;
    }
}
