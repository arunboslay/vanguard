package com.challenge.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties("application")
@Data
public class AppConfig {

    private int rate;

    private List<String> apiKeys;

    @Bean
    public Map<String, Bucket> getBucketMap() {

        Map<String, Bucket> bucketMap = new HashMap<>();

        for (String apiKey : apiKeys) {

            bucketMap.put(apiKey, Bucket4j.builder()
                    .addLimit(Bandwidth.classic(rate, Refill.intervally(rate, Duration.ofMinutes(1)))).build());

        }
        return bucketMap;
    }

}
