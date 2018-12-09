package com.daniel.popek.thesis.app;



import com.daniel.popek.thesis.app.domain.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.domain.service.data_logic.IApplicationService;
import com.daniel.popek.thesis.app.domain.service.mappers.implementation.ApplicationMappingService;
import com.daniel.popek.thesis.app.persistence.entities.Application;
import com.daniel.popek.thesis.app.persistence.entities.ApplicationConversation;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.repository.ApplicationConversationRepository;
import com.daniel.popek.thesis.app.persistence.repository.ConversationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@ActiveProfiles("test")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AppApplication.class)
//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ApplicationMappingServiceConfiguration.class,AppApplication.class})
//@SpringBootTest
public class ApplicationMappingServiceTest {

    @Mock
    ConversationRepository conversationRepository;

    @Mock
    ApplicationConversationRepository applicationConversationRepository;

    @InjectMocks
    ApplicationMappingService applicationMappingService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldMapEntityToDTOCorrectly() {
        //GIVEN
        Application entity= new Application();
        entity.setId(1);
        entity.setName("NAME");
        entity.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        entity.setLastModificationDate(Timestamp.valueOf(LocalDateTime.now()));

        List<ApplicationConversation> mockAnswer=new ArrayList<>();
        ApplicationConversation applicationConversation1=new ApplicationConversation();
        applicationConversation1.setApplicationId(1);
        applicationConversation1.setConversationId(1);
        mockAnswer.add(applicationConversation1);

        Conversation sampleConsersation= new Conversation();
        sampleConsersation.setId(1);
        sampleConsersation.setName("conversation");
        Optional<Conversation> optional= Optional.of(sampleConsersation);

        List<Conversation> designerConversations= new ArrayList<>();
        designerConversations.add(sampleConsersation);
        ///WHEN

        Mockito.when(applicationConversationRepository.findAllByApplicationId(1)).thenReturn(mockAnswer);

        Mockito.when(conversationRepository.findById(1)).thenReturn(optional);
        Mockito.when(conversationRepository.findAllByDesignerId(1)).thenReturn(designerConversations);

        ApplicationDTO dto=applicationMappingService.mapApplicationEntityTODTO(entity,1);


        //THEN
        Assert.assertEquals("NAME", dto.getName());
        Assert.assertEquals(1,dto.getConversations().size());
        Assert.assertEquals("conversation",dto.getConversations().get(0));
    }

    @Test
    public void shouldMapDTOToEntityCorrectly() {
//        //GIVEN
//        Application entity= new Application();
//        entity.setId(1);
//        entity.setName("NAME");
//        entity.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
//        entity.setLastModificationDate(Timestamp.valueOf(LocalDateTime.now()));
//
//        List<ApplicationConversation> mockAnswer=new ArrayList<>();
//        ApplicationConversation applicationConversation1=new ApplicationConversation();
//        applicationConversation1.setApplicationId(1);
//        applicationConversation1.setConversationId(1);
//        mockAnswer.add(applicationConversation1);
//
//        Conversation sampleConsersation= new Conversation();
//        sampleConsersation.setId(1);
//        sampleConsersation.setName("conversation");
//        Optional<Conversation> optional= Optional.of(sampleConsersation);
//
//        List<Conversation> designerConversations= new ArrayList<>();
//        designerConversations.add(sampleConsersation);
//        ///WHEN
//
//        Mockito.when(applicationConversationRepository.findAllByApplicationId(1)).thenReturn(mockAnswer);
//
//        Mockito.when(conversationRepository.findById(1)).thenReturn(optional);
//        Mockito.when(conversationRepository.findAllByDesignerId(1)).thenReturn(designerConversations);
//
//        ApplicationDTO dto=applicationMappingService.mapApplicationEntityTODTO(entity,1);
//
//
//        //THEN
//        Assert.assertEquals("NAME", dto.getName());
//        Assert.assertEquals(1,dto.getConversations().size());
//        Assert.assertEquals("conversation",dto.getConversations().get(0));
    }



}