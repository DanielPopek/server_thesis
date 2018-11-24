package com.daniel.popek.thesis.app.domain.model;

import java.util.Map;

public class ClassificationResult {
    private String intent;
    private String intentHash;
    private float score;
    private Map<String,Float> results;
    private Map<String,Float> resultsHashes;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return intent+" : "+score+ "; "+results;
    }

    public Map<String, Float> getResults() {
        return results;
    }

    public void setResults(Map<String, Float> results) {
        this.results = results;
    }

    public String getIntentHash() {
        return intentHash;
    }

    public void setIntentHash(String intentHash) {
        this.intentHash = intentHash;
    }

    public void setResultsHashes(Map<String, Float> resultsHashes) {
        this.resultsHashes = resultsHashes;
    }

    public Map<String, Float> getResultsHashes() {
        return resultsHashes;
    }
}
