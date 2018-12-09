package com.daniel.popek.thesis.app.persistence.repository;

import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConversationRepository  extends CrudRepository<Conversation, Integer> {

    @Override
    Conversation save(Conversation conversation);

//    Conversation findById(int id);

    Conversation findByHash( String hash);

    List<Conversation> findAllByDesignerId(Integer id);
}
