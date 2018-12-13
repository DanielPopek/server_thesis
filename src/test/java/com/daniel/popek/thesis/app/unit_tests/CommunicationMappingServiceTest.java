package com.daniel.popek.thesis.app.unit_tests;


import com.daniel.popek.thesis.app.domain.model.ClassificationInputIntent;
import com.daniel.popek.thesis.app.domain.model.ClassificationQuery;
import com.daniel.popek.thesis.app.domain.model.ClassificationResult;
import com.daniel.popek.thesis.app.domain.service.mappers.implementation.CommunicationMappingService;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation.ClassifierService;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import com.daniel.popek.thesis.app.persistence.entities.Trainingsample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommunicationMappingServiceTest {

    @Autowired
    CommunicationMappingService communicationMappingService;

    @Test
    public void shouldMapIntentsToClassifiactionQuery() {
        //GIVEN
        Intent base= new Intent();
        base.setHash("BASE");
        String sentence="SENTENCE";
        Intent intent1= new Intent();
        intent1.setHash("1");

        Intent intent2= new Intent();
        intent2.setHash("2");
        Trainingsample trainingsample1= new Trainingsample();
        trainingsample1.setValue("SAMPLE1");
        Trainingsample trainingsample2= new Trainingsample();
        trainingsample2.setValue("SAMPLE2");
        Trainingsample trainingsample3= new Trainingsample();
        trainingsample3.setValue("SAMPLE3");
        Trainingsample trainingsample4= new Trainingsample();
        trainingsample4.setValue("SAMPLE4");

        intent1.setTrainingsamplesById(Arrays.asList(trainingsample1,trainingsample2));
        intent2.setTrainingsamplesById(Arrays.asList(trainingsample3,trainingsample4));

        base.setIntentsById(Arrays.asList(intent1,intent2));

        //WHEN
        ClassificationQuery query=communicationMappingService.mapIntentsToQuery(Arrays.asList(base),sentence);

        //THEN
        Assert.assertEquals(sentence,query.getSentence());
        Assert.assertEquals(2,query.getIntents().size());
        Assert.assertEquals(2,query.getIntents().get(0).getSentences().size());
        Assert.assertEquals(2,query.getIntents().get(1).getSentences().size());
        Assert.assertEquals("1",query.getIntents().get(0).getHash());
        Assert.assertEquals("2",query.getIntents().get(1).getHash());
        Assert.assertEquals("SAMPLE1",query.getIntents().get(0).getSentences().get(0));
        Assert.assertEquals("SAMPLE3",query.getIntents().get(1).getSentences().get(0));

    }
}
