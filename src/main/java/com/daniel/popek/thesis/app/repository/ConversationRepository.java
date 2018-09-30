package com.daniel.popek.thesis.app.repository;

import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConversationRepository  extends CrudRepository<Conversation, Integer> {

    @Override
    Conversation save(Conversation conversation);

    Conversation findById(int id);

    Conversation findByHash( String hash);
}
