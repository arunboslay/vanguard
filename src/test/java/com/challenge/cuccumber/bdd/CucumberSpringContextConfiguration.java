package com.challenge.cuccumber.bdd;

import com.challenge.CodingChallengeApplication;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CodingChallengeApplication.class, loader = SpringBootContextLoader.class)
public class CucumberSpringContextConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(CucumberSpringContextConfiguration.class);

  @Autowired
  private  ScenarioContext scenarioContext;

  @Before
  public void setUp() {
      scenarioContext.clearContext();
  }
}