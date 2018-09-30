package com.daniel.popek.thesis.app.repository;

import com.daniel.popek.thesis.app.model.entities.Answersample;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Trainingsample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AnswerSampleRepository extends CrudRepository<Answersample, Integer> {
    @Override
    Answersample save(Answersample intent);

    Answersample findById(int id);
}
