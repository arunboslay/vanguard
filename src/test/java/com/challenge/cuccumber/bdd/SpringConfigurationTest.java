package com.challenge.cuccumber.bdd;

import com.challenge.CodingChallengeApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@SpringBootTest(classes = CodingChallengeApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_CLASS )
@CucumberContextConfiguration
public  class SpringConfigurationTest {

    @Autowired
    private  ScenarioContext scenarioContext;

    @Before
    public void setUp() {
        scenarioContext.clearContext();
    }


}