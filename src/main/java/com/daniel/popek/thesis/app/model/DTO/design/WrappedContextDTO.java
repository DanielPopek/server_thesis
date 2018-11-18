package com.daniel.popek.thesis.app.model.DTO.design;

import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;

public class WrappedContextDTO {

    private ContextDTO context;
    private String conversationHash;

    public String getConversationHash() {
        return conversationHash;
    }

    public void setConversationHash(String conversationHash) {
        this.conversationHash = conversationHash;
    }

    public ContextDTO getContext() {
        return context;
    }

    public void setContext(ContextDTO context) {
        this.context = context;
    }
}
