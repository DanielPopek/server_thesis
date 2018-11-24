package com.daniel.popek.thesis.app.model.DTO.design;

import java.util.List;

public class ConversationNamesHashesDTO {
    private List<String> names;
    private List<String> hashes;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getHashes() {
        return hashes;
    }

    public void setHashes(List<String> hashes) {
        this.hashes = hashes;
    }
}
