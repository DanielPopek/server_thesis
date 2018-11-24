package com.daniel.popek.thesis.app.persistence.repository;

import com.daniel.popek.thesis.app.persistence.entities.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ApplicationRepository  extends CrudRepository<Application, Integer> {

    @Override
    Application save(Application conversation);

    Application findById(int id);

    Application findApplicationByToken(String token);

    @Override
    void delete (Application application);

    Application deleteApplicationById(int id);

    List<Application> findAllByDesignerId(Integer id);
}