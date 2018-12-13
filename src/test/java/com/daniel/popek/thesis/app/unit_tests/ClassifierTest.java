package com.daniel.popek.thesis.app.unit_tests;

import com.daniel.popek.thesis.app.domain.model.ClassificationInputIntent;
import com.daniel.popek.thesis.app.domain.model.ClassificationResult;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation.ClassifierService;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation.TokenizerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClassifierTest {

    @Autowired
    ClassifierService classifierService;

    @Test
    public void shouldClassifySentenceCorrectly() {
        //GIVEN
        ClassificationInputIntent firstInput= new ClassificationInputIntent();
        ClassificationInputIntent secondInput= new ClassificationInputIntent();

        firstInput.setName("first");
        firstInput.setHash("firstHash");
        firstInput.setSentences(Arrays.asList("Chcaiłbym zamówić śniadanie","Poproszę śniadanie dla dwóch osób","Czy mogę zamówić śnadanie?"));

        secondInput.setName("second");
        secondInput.setHash("secondHash");
        secondInput.setSentences(Arrays.asList("Rezerwuję pokój dla wóch osób","Poproszę pokój dla dówch osób","Czy mają Pańswto pokoje dwuosobowe"));
        //WHEN

        ClassificationResult firstResult =classifierService.classify("Chcaiłbym zamówić śniadanie",Arrays.asList(firstInput,secondInput));
        ClassificationResult secondResult =classifierService.classify("Chciałbym zarezerwowoać pokój dwuosobowy",Arrays.asList(firstInput,secondInput));
        //THEN
        Assert.assertEquals("first",firstResult.getIntent());
        Assert.assertEquals("second",secondResult.getIntent());
        Assert.assertEquals("firstHash",firstResult.getIntentHash());
        Assert.assertEquals("secondHash",secondResult.getIntentHash());
        Assert.assertEquals(2,firstResult.getResults().size());
        Assert.assertEquals(2,secondResult.getResults().size());
    }

    @Test
    public void shouldReturnSamePredictionRateForAllClasses() {
        //GIVEN
        ClassificationInputIntent firstInput= new ClassificationInputIntent();
        ClassificationInputIntent secondInput= new ClassificationInputIntent();
        ClassificationInputIntent thirdInput= new ClassificationInputIntent();

        firstInput.setName("first");
        firstInput.setHash("firstHash");
        firstInput.setSentences(Arrays.asList("samochód","To jest inny wzorzec uczący","To jest trzeci wzorzec"));

        secondInput.setName("second");
        secondInput.setHash("secondHash");
        secondInput.setSentences(Arrays.asList("samochód","To jest inny wzorzec uczący","To jest trzeci wzorzec"));

        thirdInput.setName("third");
        thirdInput.setHash("thirdHash");
        thirdInput.setSentences(Arrays.asList("samochód","To jest inny wzorzec uczący","To jest trzeci wzorzec"));
        //WHEN

        ClassificationResult result =classifierService.classify("Chcaiłbym kupić samochód",Arrays.asList(firstInput,secondInput,thirdInput));
        //THEN
        Assert.assertEquals(3,result.getResults().size());
        Assert.assertEquals(result.getResults().get("first"),result.getResults().get("second"));
        Assert.assertEquals(result.getResults().get("second"),result.getResults().get("third"));

    }

    @Test
    public void shouldExtractCorpusWords() {
        //GIVEN
        ClassificationInputIntent firstInput= new ClassificationInputIntent();
        ClassificationInputIntent secondInput= new ClassificationInputIntent();
        ClassificationInputIntent thirdInput= new ClassificationInputIntent();

        firstInput.setName("first");
        firstInput.setHash("firstHash");
        firstInput.setSentences(Arrays.asList("Ala ma psa","Koteczek nie ma włosów"));

        secondInput.setName("second");
        secondInput.setHash("secondHash");
        secondInput.setSentences(Arrays.asList("Samochód jedzie po ulicy"));

        thirdInput.setName("third");
        thirdInput.setHash("thirdHash");
        thirdInput.setSentences(Arrays.asList("Pociągi jadą po szynach"));
        //WHEN

        Map<String,Integer> corpusWords=classifierService.corpusWords(Arrays.asList(firstInput,secondInput,thirdInput));

        System.out.println(corpusWords);

        //THEN
        Assert.assertTrue(corpusWords.containsKey("ala"));
        Assert.assertTrue(corpusWords.containsKey("włos"));
        Assert.assertTrue(corpusWords.containsKey("ulica"));
        Assert.assertTrue(corpusWords.containsKey("jechać"));
        Assert.assertTrue(corpusWords.containsKey("samochód"));
        Assert.assertTrue(corpusWords.containsKey("szyna"));
        Assert.assertEquals(1,(int)corpusWords.get("pies"));
        Assert.assertEquals(2,(long)corpusWords.get("jechać"));
        Assert.assertEquals(2,(long)corpusWords.get("po"));
        Assert.assertEquals(1,(long)corpusWords.get("samochód"));
    }


}