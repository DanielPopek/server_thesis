package com.daniel.popek.thesis.app.persistence.repository;

import com.daniel.popek.thesis.app.persistence.entities.Answersample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AnswerSampleRepository extends CrudRepository<Answersample, Integer> {
    @Override
    Answersample save(Answersample intent);

    Answersample findById(int id);
}
