package com.daniel.popek.thesis.app.unit_tests;

import com.daniel.popek.thesis.app.domain.service.ml_classifier.ITokenizerService;
import com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation.TokenizerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TokenizerServiceTest {


    @Autowired
    TokenizerService tokenizerService;

    @Test
    public void shouldRemoveSpecialChars() {
        //GIVEN
        String sentence="fir:,;?!@*&st, @!se?co.nd third;:-";
        //WHEN
        String [] result=tokenizerService.tokenize(sentence);
        //THEN
        Assert.assertEquals(3,result.length);
        Assert.assertEquals("first",result[0]);
        Assert.assertEquals("second",result[1]);
        Assert.assertEquals("third",result[2]);
    }

    @Test
    public void shouldTrimWords() {
        //GIVEN
        String sentence="first             second        third";
        //WHEN
        String [] result=tokenizerService.tokenize(sentence);
        //THEN
        Assert.assertEquals(3,result.length);
        Assert.assertEquals("first",result[0]);
        Assert.assertEquals("second",result[1]);
        Assert.assertEquals("third",result[2]);
    }


    @Test
    public void shouldBringToLowerCase() {
        //GIVEN
        String sentence="FiRsT   sEcoNd    tHird";
        //WHEN
        String [] result=tokenizerService.tokenize(sentence);
        //THEN
        Assert.assertEquals(3,result.length);
        Assert.assertEquals("first",result[0]);
        Assert.assertEquals("second",result[1]);
        Assert.assertEquals("third",result[2]);
    }



}