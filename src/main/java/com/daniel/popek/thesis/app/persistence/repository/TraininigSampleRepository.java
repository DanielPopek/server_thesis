package com.daniel.popek.thesis.app.persistence.repository;

import com.daniel.popek.thesis.app.persistence.entities.Trainingsample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface TraininigSampleRepository extends CrudRepository<Trainingsample, Integer> {
    @Override
    Trainingsample save(Trainingsample intent);

    Trainingsample findById(int id);
}
