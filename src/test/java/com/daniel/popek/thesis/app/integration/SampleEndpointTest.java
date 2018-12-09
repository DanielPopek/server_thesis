package com.daniel.popek.thesis.app.integration;

import com.daniel.popek.thesis.app.AppApplication;
import com.daniel.popek.thesis.app.communication.config.SecurityConfig;
import com.daniel.popek.thesis.app.domain.service.utils.IAuthenticationFilterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;

//import static org.springframework.http.ResponseEntity.status;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//@RunWith(SpringJUnit4ClassRunner.class)

@ActiveProfiles("SECURITY_MOCK")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SampleEndpointTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    IAuthenticationFilterService authenticationFilterService;

    @InjectMocks
    SecurityConfig securityConfig;


    @Test
    public void testEndpoint() {

        HttpHeaders headers = new HttpHeaders();
 //       headers.add("Authorization", "bfd92bf1-185b-41e8-85c8-a89688aa48ca:ec9d61fb-d108-44f0-920f-7ee07c0972a2" );
        headers.add("Authorization", "TEST" );
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        Mockito.when(authenticationFilterService.apiHeaderIsUnauthorized("TEST")).thenReturn(false);



        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/test/Daniel", HttpMethod.GET, entity, String.class);

        String response = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Daniel", response);
    }

    @Test
    public void testEndpoint2() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST" );
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        Mockito.when(authenticationFilterService.apiHeaderIsUnauthorized("TEST")).thenReturn(false);



        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/test2/Daniel", HttpMethod.GET, entity, String.class);

        String response = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("TEST", response);
    }



}
