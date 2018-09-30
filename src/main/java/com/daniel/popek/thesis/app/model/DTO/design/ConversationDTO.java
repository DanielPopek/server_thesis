package com.daniel.popek.thesis.app.model.DTO.design;

public class ConversationDTO {
    private Integer conversationId;
    private String conversationHash;
    private String name;
    private IntentDTO root;

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationHash() {
        return conversationHash;
    }

    public void setConversationHash(String conversationHash) {
        this.conversationHash = conversationHash;
    }

    public IntentDTO getRoot() {
        return root;
    }

    public void setRoot(IntentDTO root) {
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
