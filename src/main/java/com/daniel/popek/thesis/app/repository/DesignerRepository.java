package com.daniel.popek.thesis.app.repository;

import com.daniel.popek.thesis.app.model.entities.Designer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface DesignerRepository  extends CrudRepository<Designer, Integer> {

    @Override
    Designer save(Designer conversation);

    Designer findById(int id);

    Designer findByApiKey(String apiKey);

    Designer findByEmail(String email);

}
