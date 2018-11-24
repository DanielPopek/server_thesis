package com.daniel.popek.thesis.app.domain.service.ml_classifier;

import org.springframework.stereotype.Service;

@Service
public interface ITokenizerService {

    public String[] tokenize(String sentence);
}
