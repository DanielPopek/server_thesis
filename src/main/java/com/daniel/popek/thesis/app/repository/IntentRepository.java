package com.daniel.popek.thesis.app.repository;

import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Service
public interface IntentRepository  extends CrudRepository<Intent, Integer> {

    @Override
    Intent save(Intent intent);

    @Override
    void delete (Intent intent);

    @Override
    List<Intent> findAll();

    Intent findById(int id);

    Intent findByHash(String hash);

    Intent deleteIntentById(int id);

    List<Intent> findByConversationByConversationIdAndRootIsTrue(Conversation conversation);

}
