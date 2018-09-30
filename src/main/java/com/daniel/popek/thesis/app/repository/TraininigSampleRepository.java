package com.daniel.popek.thesis.app.repository;

import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.model.entities.Trainingsample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface TraininigSampleRepository extends CrudRepository<Trainingsample, Integer> {
    @Override
    Trainingsample save(Trainingsample intent);

    Trainingsample findById(int id);
}
