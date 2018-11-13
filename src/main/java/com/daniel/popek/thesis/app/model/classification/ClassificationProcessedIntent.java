package com.daniel.popek.thesis.app.model.classification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassificationProcessedIntent {

    private String name;
    private String hash;
    private List<Map<String,Float>> rowFrequencies;
    private double classProbability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getClassProbability() {
        return classProbability;
    }

    public void setClassProbability(double classProbability) {
        this.classProbability = classProbability;
    }

    public List<Map<String, Float>> getRowFrequencies() {
        return rowFrequencies;
    }

    public void setRowFrequencies(List<Map<String, Float>> rowFrequencies) {
        this.rowFrequencies = rowFrequencies;
    }

    @Override
    public String toString() {
        String listString = rowFrequencies.stream().map(Object::toString)
                .collect(Collectors.joining("; "));
        return name+"; prob:" +classProbability+"[ "+listString+" ]";
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
