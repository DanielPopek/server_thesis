package com.daniel.popek.thesis.app.model.classification;

import java.util.List;

public class ClassificationQuery {
    private String sentence;
    private List<ClassificationInputIntent> intents;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<ClassificationInputIntent> getIntents() {
        return intents;
    }

    public void setIntents(List<ClassificationInputIntent> intents) {
        this.intents = intents;
    }
}
