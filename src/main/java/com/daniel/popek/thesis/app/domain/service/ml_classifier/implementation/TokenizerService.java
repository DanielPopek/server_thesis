package com.daniel.popek.thesis.app.domain.service.ml_classifier.implementation;

import com.daniel.popek.thesis.app.domain.service.ml_classifier.ITokenizerService;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TokenizerService implements ITokenizerService {
    @Override
    public String[] tokenize(String sentence) {
        //System.out.println(sentence);
        String output=sentence.replaceAll("[\\-\\+\\.\\^:,;?!@*&]","");
        output=output.toLowerCase();
        output=output.trim();
        //System.out.println(output);
        String[] splitedOutput=output.split(" +");
        //System.out.println(Arrays.toString(splitedOutput));
        return splitedOutput;
    }
}
