package com.challenge.cuccumber.bdd;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@Slf4j
public class MyStepdefs {

    public static final String RESPONSE = "response";
    @Autowired(required = false)
    private TestRestTemplate restTemplate;

    @Autowired
    private ScenarioContext context;

    @Given("The user request for weather description of city and a country")
    public void theUserRequestForWeatherDescriptionOfCityAndACountry() {

    }

    @When("the user calls GET {string}")
    public void theUserCallsGET(String arg0) {
        ResponseEntity<String> entity = restTemplate.getForEntity(arg0, String.class);
        log.error(entity.getBody());
        context.getContext().put(RESPONSE, entity);

    }

    @Then("the client receives status code of {int}")
    public void theClientReceivesStatusCodeOf(int arg0) {
        ResponseEntity<String> entity = (ResponseEntity<String>) context.getContext().get(RESPONSE);
        Assert.assertEquals(arg0, entity.getStatusCodeValue());

    }

    @And("the response contains the message {string}")
    public void theResponseContainsTheMessage(String arg0) {
        ResponseEntity<String> entity = (ResponseEntity<String>) context.getContext().get(RESPONSE);
        Assert.assertEquals(arg0, entity.getBody());
    }


    @And("the response message should not be empty")
    public void theResponseMessageShouldNotBeEmpty() {
        ResponseEntity<String> entity = (ResponseEntity<String>) context.getContext().get(RESPONSE);
        Assert.assertNotNull(entity.getBody());
        Assert.assertTrue(entity.getBody().contains("message"));

    }
}
