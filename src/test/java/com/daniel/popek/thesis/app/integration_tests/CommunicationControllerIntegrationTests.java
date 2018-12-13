package com.daniel.popek.thesis.app.integration_tests;
import com.daniel.popek.thesis.app.communication.config.SecurityConfig;
import com.daniel.popek.thesis.app.communication.controller.CommunicationController;
import com.daniel.popek.thesis.app.domain.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.domain.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.domain.service.communication.implementation.CommunicationService;
import com.daniel.popek.thesis.app.domain.service.mappers.implementation.ApplicationMappingService;
import com.daniel.popek.thesis.app.domain.service.utils.IAuthenticationFilterService;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("TEST_SECURITY_MOCK")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommunicationControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @MockBean
    CommunicationService communicationService;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnContext() throws Exception{
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST:CONVERSATION");
        headers.add("Content-Type","application/json");

        ContextDTO requestContext= new ContextDTO();

        HttpEntity<ContextDTO> entity = new HttpEntity<ContextDTO>(requestContext, headers);

        ContextDTO responseContext = new ContextDTO();
        responseContext.setMessage("RESPONSE");

        given(communicationService.respond(any(),anyString()))
                .willReturn(responseContext);
        //WHEN
        ResponseEntity<ContextDTO> responseEntity = restTemplate.exchange(
                "/api/context",HttpMethod.POST,entity, ContextDTO.class);
        //THEN
        ContextDTO httpResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("RESPONSE", httpResponse.getMessage());
    }

    @Test
    public void shouldReturnBadRequestMessge() throws Exception{
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST:CONVERSATION");
        headers.add("Content-Type","application/json");

        ContextDTO requestContext= new ContextDTO();

        HttpEntity<ContextDTO> entity = new HttpEntity<ContextDTO>(requestContext, headers);

        given(communicationService.respond(any(),anyString()))
                .willThrow(new Exception());
        //WHEN

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/context",HttpMethod.POST,entity, String.class);
        //THEN
        String httpResponse = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("INCORRECT PARAMETERS", httpResponse);
    }

    @Test
    public void shouldExtractConversationTokenFromHeader() throws Exception{
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST:CONVERSATION");
        headers.add("Content-Type","application/json");

        ContextDTO requestContext= new ContextDTO();

        HttpEntity<ContextDTO> entity = new HttpEntity<ContextDTO>(requestContext, headers);

        ContextDTO responseContext = new ContextDTO();
        responseContext.setMessage("RESPONSE");

        given(communicationService.respond(any(),eq("CONVERSATION")))
                .willReturn(responseContext);
        //WHEN
        ResponseEntity<ContextDTO> responseEntity = restTemplate.exchange(
                "/api/context",HttpMethod.POST,entity, ContextDTO.class);
        //THEN
        ContextDTO httpResponse = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("RESPONSE", httpResponse.getMessage());
    }




}
