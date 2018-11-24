package com.daniel.popek.thesis.app.model.DTO.design;

import java.util.List;

public class ApplicationDTO {

    private String name;
    private String description;
    private String token;
    private String date;
    private String lastModificationDate;
    private List<String> conversations;
    private List<String> hashes;
    private List<String> designerConversationHashes;
    private List<String> designerConversationNames;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public List<String> getConversations() {
        return conversations;
    }

    public void setConversations(List<String> conversations) {
        this.conversations = conversations;
    }

    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public List<String> getHashes() {
        return hashes;
    }

    public void setHashes(List<String> hashes) {
        this.hashes = hashes;
    }

    public List<String> getDesignerConversationHashes() {
        return designerConversationHashes;
    }

    public void setDesignerConversationHashes(List<String> designerConversationHashes) {
        this.designerConversationHashes = designerConversationHashes;
    }

    public List<String> getDesignerConversationNames() {
        return designerConversationNames;
    }

    public void setDesignerConversationNames(List<String> designerConversationNames) {
        this.designerConversationNames = designerConversationNames;
    }
}
