package com.daniel.popek.thesis.app.persistence.repository;

import com.daniel.popek.thesis.app.persistence.entities.ApplicationConversation;
import com.daniel.popek.thesis.app.persistence.entities.ApplicationConversationPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ApplicationConversationRepository extends CrudRepository<ApplicationConversation, ApplicationConversationPK> {

    @Override
    ApplicationConversation save(ApplicationConversation applicationConversation);

    @Override
    void delete (ApplicationConversation applicationConversation);

    void deleteAllByApplicationId(int id);

    void deleteAllByConversationId(int id);

    List<ApplicationConversation> findAllByApplicationId(int id);

    List<ApplicationConversation> findAllByApplicationIdAndConversationId(int applicationId,int conversationId);
}
