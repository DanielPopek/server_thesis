package com.daniel.popek.thesis.app.model.classification;

import java.util.List;

public class ClassificationInputIntent {
    private String name;
    private String hash;
    private List<String> sentences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
