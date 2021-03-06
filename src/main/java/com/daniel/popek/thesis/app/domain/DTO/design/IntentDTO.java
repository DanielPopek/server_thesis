package com.daniel.popek.thesis.app.domain.DTO.design;

import java.util.List;

public class IntentDTO {
    private String name;
    private List<IntentDTO> subintents;
    private List<String> answerSamples;
    private List<String> trainingSamples;
    private List<String> events;
    private List<String> misunderstandingStatements;

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

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }


    public List<String> getMisunderstandingStatements() {
        return misunderstandingStatements;
    }

    public void setMisunderstandingStatements(List<String> misunderstandingStatements) {
        this.misunderstandingStatements = misunderstandingStatements;
    }
}
