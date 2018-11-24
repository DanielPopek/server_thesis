package com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation;

import com.daniel.popek.thesis.app.domain.model.ClassificationInputIntent;
import com.daniel.popek.thesis.app.domain.model.ClassificationProcessedIntent;
import com.daniel.popek.thesis.app.domain.model.ClassificationResult;
import com.daniel.popek.thesis.app.domain.model.StemmedIntent;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.IClassifierService;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.IStemmerService;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.ITokenizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassifierService implements IClassifierService{

    @Autowired
    IStemmerService stemmerService;

    @Autowired
    ITokenizerService tokenizerService;

    @Override
    public ClassificationResult classify(String sentence, List<ClassificationInputIntent> intents) {
        //return classifySimple(sentence,intents);
        //features initialization
        List<String> features=new ArrayList<>();
        String[] sentenceTokens=tokenizeAndStem(sentence);
        for(int i=0;i<sentenceTokens.length;i++)
        {
           if(!features.contains(sentenceTokens[i])) features.add(sentenceTokens[i]);
        }
        Map<String,Integer> corpusWords=corpusWords(intents);
        int grammarSize=corpusWords.size();
        List<ClassificationProcessedIntent> processedIntents=processedIntents(intents);
        //initial state of result
        ClassificationResult result= new ClassificationResult();
        result.setScore(-1);
        result.setIntent("initial");
        result.setResults(new HashMap<>());
        result.setResultsHashes(new HashMap<>());
        //initial printouts
        System.out.println(corpusWords.toString());
        String featuresString = features.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.println("[ "+featuresString+"]");
        String listClasses = processedIntents.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.println("[ "+listClasses+"]");
        //
        for (ClassificationProcessedIntent intentClass:processedIntents
             ) {
            //double probabilty=intentClass.getClassProbability();
            double probabilty=1;
            int matchesCount=0;
            for (String feature: features
                 ) {
                double featureProbability=1.0;
                double frequenciesSum=1;
                for (Map<String,Float> sentenceFreqencies:intentClass.getRowFrequencies()
                     ) {
                    if(sentenceFreqencies.containsKey(feature)){
                        frequenciesSum+=sentenceFreqencies.get(feature);
                        matchesCount++;
                    }
                }
                featureProbability+=frequenciesSum;
                featureProbability=featureProbability/(grammarSize+intentClass.getRowFrequencies().size());
                probabilty*=featureProbability;
            }
            if(matchesCount==0)probabilty=0;
            if(probabilty>result.getScore()){
                result.setScore((float)probabilty);
                result.setIntent(intentClass.getName());
                result.setIntentHash(intentClass.getHash());
            }
            result.getResults().put(intentClass.getName(),(float)probabilty);
            result.getResultsHashes().put(intentClass.getHash(),(float)probabilty);
        }

        System.out.println("CLASSIFICATION RESULT: "+result);
        return result;
    }

    private ClassificationResult classifySimple(String sentence, List<ClassificationInputIntent> intents) {
        List<String> sentenceStems=Arrays.asList(tokenizeAndStem(sentence));
        Map<String,Integer> corpusWords=corpusWords(intents);
        List<StemmedIntent> classes=stemmedIntents(intents);
        ClassificationResult result= new ClassificationResult();
        result.setScore(-1);
        result.setIntent("initial");
        System.out.println(corpusWords.toString());
        String listClasses = classes.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.println(listClasses);
        for ( StemmedIntent stemmedClass:classes
                ) {
            double score=0;
//            for (String stem:stemmedClass.getStems()
//                 ) {
//                if(sentenceStems.contains(stem))
//                    score+=1.0/corpusWords.get(stem);
//            }
            for (String stem:sentenceStems
                    ) {
                if(stemmedClass.getStems().contains(stem))
                    score+=1.0/corpusWords.get(stem);
            }
            System.out.println(stemmedClass.getName()+" : "+score);
            if(score>result.getScore())
            {
                result.setIntent(stemmedClass.getName());
                result.setScore((float)score);
            }
        }
        return result;
    }

    @Override
    public Map<String,Integer> corpusWords(List<ClassificationInputIntent> intents)
    {
        Map<String,Integer> corpus= new HashMap<>();
        List<String> globalStems= new ArrayList<>();
        for (ClassificationInputIntent intent:intents
             ) {
            for (String sentence:intent.getSentences()
                 ) {
                globalStems.addAll(Arrays.asList(tokenizeAndStem(sentence)));
            }
        }
        for(int i=0;i<globalStems.size();i++)
        {
            String stem=globalStems.get(i);
            if(!corpus.containsKey(stem))corpus.put(stem,1);
            else corpus.replace(stem,corpus.get(stem)+1);
        }
        return corpus;
    }

    @Override
    public List<StemmedIntent> stemmedIntents(List<ClassificationInputIntent> intents)
    {
        List<StemmedIntent> stemmedIntents=new ArrayList<>();
        for (ClassificationInputIntent classificationInputIntent :intents
             ) {
            StemmedIntent stemmedIntent= new StemmedIntent();
            stemmedIntent.setName(classificationInputIntent.getName());
            stemmedIntent.setStems(new ArrayList<>());
            for (String sentence: classificationInputIntent.getSentences()
                 ) {
                stemmedIntent.getStems().addAll(Arrays.asList(tokenizeAndStem(sentence)));
            }
            stemmedIntents.add(stemmedIntent);
        }
        return stemmedIntents;
    }

    @Override
    public List<ClassificationProcessedIntent> processedIntents(List<ClassificationInputIntent> intents)
    {
       List<ClassificationProcessedIntent> processedIntents=new ArrayList<>();
       int totalSentencesCount=0;
        for (ClassificationInputIntent inputIntent:intents
             ) {
            ClassificationProcessedIntent processedIntent=new ClassificationProcessedIntent();
            List<Map<String,Float>> rowFrequencies=new ArrayList<>();
            int sentencesCount=inputIntent.getSentences().size();
            processedIntent.setName(inputIntent.getName());
            processedIntent.setHash(inputIntent.getHash());
            processedIntent.setClassProbability(sentencesCount);
            totalSentencesCount+=sentencesCount;
            for (String sentence:inputIntent.getSentences()
                 ) {
                String[] tokenizedAndStemmed=tokenizeAndStem(sentence);
                //sentence length
                int length=tokenizedAndStemmed.length;
                Map<String,Float> frequencies=new HashMap<>();
                for(int i=0;i<length;i++)
                {
                    String word=tokenizedAndStemmed[i];
                    if(!frequencies.containsKey(word))frequencies.put(word,1.0f/length);
                    else frequencies.replace(word,frequencies.get(word)+1.0f/length);
                }
                rowFrequencies.add(frequencies);
            }
            processedIntent.setRowFrequencies(rowFrequencies);
            processedIntents.add(processedIntent);
        }
        for (ClassificationProcessedIntent processedIntent:processedIntents
             ) {
            processedIntent.setClassProbability(processedIntent.getClassProbability()/totalSentencesCount);
        }

        return processedIntents;
    }


    @Override
    public String[] tokenizeAndStem(String sentence) {
        String[] tokens=tokenizerService.tokenize(sentence);
       for(int i=0;i<tokens.length;i++)
       {
           if(!tokens[i].isEmpty())
           tokens[i]=stemmerService.stem(tokens[i]);
       }
       return tokens;
    }
}
