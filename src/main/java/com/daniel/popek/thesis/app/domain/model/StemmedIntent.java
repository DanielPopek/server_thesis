package com.daniel.popek.thesis.app.domain.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StemmedIntent {
    private String name;
    private List<String> stems;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStems() {
        return stems;
    }

    public void setStems(List<String> stems) {
        this.stems = stems;
    }

    @Override
    public String toString() {
        String listString = stems.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        return name+": [ "+listString+" ]";
    }

}
