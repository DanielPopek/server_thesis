package com.daniel.popek.thesis.app.domain.service.ml_classifier;

import org.springframework.stereotype.Service;

@Service
public interface IStemmerService {
    public String stem(String input);
}
