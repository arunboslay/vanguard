package com.challenge.intercepter;

import com.challenge.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, Bucket> bucketMap;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String apiKey = request.getParameter("apikey");
        if (apiKey == null || apiKey.isEmpty()) {
            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(mapper.writeValueAsString(Message.builder().message("Missing API Key").build()));

            return false;
        }

        Bucket tokenBucket = bucketMap.get(apiKey);
        if (tokenBucket == null) {
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(mapper.writeValueAsString(Message.builder().message("Invalid API Key").build()));

            return false;
        }
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1000000000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write(mapper.writeValueAsString(Message.builder().message("You have exhausted your API Request Quota").build()));

            return false;
        }
    }


}