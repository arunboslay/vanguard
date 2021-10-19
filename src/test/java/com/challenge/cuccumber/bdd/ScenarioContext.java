package com.challenge.cuccumber.bdd;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class ScenarioContext {

    private Map<String , Object> context = new HashMap<>();

    public void clearContext() {
        context = new HashMap<>();
    }


}
