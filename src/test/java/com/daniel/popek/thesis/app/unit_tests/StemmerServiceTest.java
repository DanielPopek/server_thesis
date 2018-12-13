package com.daniel.popek.thesis.app.unit_tests;

import com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation.StemmerService;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation.TokenizerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StemmerServiceTest {


    @Autowired
    StemmerService stemmerService;

    @Test
    public void shouldLematizeWord() {
        //GIVEN
        String word = "chciałbym";
        //WHEN
        String stem=stemmerService.stem(word);
        //THEN
        Assert.assertEquals("chcieć",stem);
    }

    @Test
    public void shouldStemmWordManually() {
        //GIVEN
        String word = "lematyzacja";
        //WHEN
        String stem=stemmerService.stem(word);
        //THEN
        Assert.assertEquals("lemat",stem);
    }

    @Test
    public void shouldReturnUnrecognizedWordUnchanged() {
        //GIVEN
        String word = "UNRECOGNIZED";
        //WHEN
        String stem=stemmerService.stem(word);
        //THEN
        Assert.assertEquals("UNRECOGNIZED",stem);
    }
}