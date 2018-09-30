package com.daniel.popek.thesis.app.model.DTO.design;

import java.util.List;

public class IntentDTO {
    private String name;
    private List<IntentDTO> subintents;
    private List<String> answerSamples;
    private List<String> trainingSamples;
    private EventDTO event;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IntentDTO> getSubintents() {
        return subintents;
    }

    public void setSubintents(List<IntentDTO> subintents) {
        this.subintents = subintents;
    }

    public List<String> getAnswerSamples() {
        return answerSamples;
    }

    public void setAnswerSamples(List<String> answerSamples) {
        this.answerSamples = answerSamples;
    }

    public List<String> getTrainingSamples() {
        return trainingSamples;
    }

    public void setTrainingSamples(List<String> trainingSamples) {
        this.trainingSamples = trainingSamples;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }
}
