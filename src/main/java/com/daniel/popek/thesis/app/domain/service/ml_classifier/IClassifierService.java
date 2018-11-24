package com.daniel.popek.thesis.app.domain.service.ml_classifier;

import com.daniel.popek.thesis.app.domain.model.ClassificationInputIntent;
import com.daniel.popek.thesis.app.domain.model.ClassificationProcessedIntent;
import com.daniel.popek.thesis.app.domain.model.ClassificationResult;
import com.daniel.popek.thesis.app.domain.model.StemmedIntent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IClassifierService {

    public ClassificationResult classify(String sentence, List<ClassificationInputIntent> intents);

    Map<String,Integer> corpusWords(List<ClassificationInputIntent> intents);

    List<StemmedIntent> stemmedIntents(List<ClassificationInputIntent> intents);

    List<ClassificationProcessedIntent> processedIntents(List<ClassificationInputIntent> intents);

    public String[] tokenizeAndStem(String sentence);
}
