package com.daniel.popek.thesis.app.integration_tests;

import com.daniel.popek.thesis.app.domain.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.domain.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.domain.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.domain.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.domain.service.communication.implementation.CommunicationService;
import com.daniel.popek.thesis.app.domain.service.data_logic.IApplicationService;
import com.daniel.popek.thesis.app.domain.service.data_logic.IConversationService;
import com.daniel.popek.thesis.app.domain.service.data_logic.IIntentService;
import com.daniel.popek.thesis.app.persistence.entities.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ActiveProfiles("TEST_SECURITY_MOCK")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DesignerAPIControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    IConversationService conversationService;

    @MockBean
    IIntentService intentService;

    @MockBean
    IApplicationService applicationService;

    @MockBean
    ICommunicationService communicationService;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnConversationDTO() throws Exception
    {
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST:CONVERSATION");
        headers.add("Content-Type","application/json");

        String hash="CONVERSATION_HASH";

        HttpEntity<ContextDTO> entity = new HttpEntity<ContextDTO>(null, headers);

        ConversationDTO dto= new ConversationDTO();
        dto.setName("dto");

        given(conversationService.readConversationByHash("CONVERSATION_HASH"))
                .willReturn(dto);
        //WHEN
        ResponseEntity<ConversationDTO> responseEntity = restTemplate.exchange(
                "/management/conversation/CONVERSATION_HASH", HttpMethod.GET,entity, ConversationDTO.class);
        //THEN
        ConversationDTO httpResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("dto", httpResponse.getName());

    }

    @Test
    public void shouldReturnIternalServerErrorCodeForWrongConversationHash() throws Exception
    {
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST:CONVERSATION");
        headers.add("Content-Type","application/json");

        String hash="CONVERSATION_HASH";

        HttpEntity<ContextDTO> entity = new HttpEntity<ContextDTO>(null, headers);

        ConversationDTO dto= new ConversationDTO();
        dto.setName("dto");

        given(conversationService.readConversationByHash("CONVERSATION_HASH"))
                .willThrow(new Exception());
        //WHEN
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/management/conversation/CONVERSATION_HASH", HttpMethod.GET,entity, String.class);
        //THEN

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

    @Test
    public void shouldReturnApplicationListDTO() throws Exception
    {
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "DESIGNER_HASH");
        headers.add("Content-Type","application/json");

        String hash="DESIGNER_HASH";

        HttpEntity<ContextDTO> entity = new HttpEntity<ContextDTO>(null, headers);

        ApplicationDTO dto1 = new ApplicationDTO();
        dto1.setToken("111");

        ApplicationDTO dto2 = new ApplicationDTO();
        dto2.setToken("222");

        List<ApplicationDTO> mockResult= Arrays.asList(dto1,dto2);

        given(applicationService.getAllByDesignerHash("DESIGNER_HASH"))
                .willReturn(mockResult);
        //WHEN
        ResponseEntity<List<ApplicationDTO>> responseEntity = restTemplate.exchange(
                "management/application/hash", HttpMethod.GET,entity,new ParameterizedTypeReference<List<ApplicationDTO>>(){});
        //THEN
        List<ApplicationDTO> httpResponse = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("111", httpResponse.get(0).getToken());
        assertEquals("222", httpResponse.get(0).getToken());
    }

    @Test
    public void shouldReturnIternalServerErrorCodeForWrongApplicationHash() throws Exception
    {
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "DESIGNER_HASH");
        headers.add("Content-Type","application/json");

        String hash="DESIGNER_HASH";

        HttpEntity<ContextDTO> entity = new HttpEntity<ContextDTO>(null, headers);

        given(applicationService.getAllByDesignerHash("DESIGNER_HASH"))
                .willThrow(new Exception("MESSAGE"));
        //WHEN
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/management/application/hash", HttpMethod.GET,entity,String.class);
        //THEN
        String httpResponse = responseEntity.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}