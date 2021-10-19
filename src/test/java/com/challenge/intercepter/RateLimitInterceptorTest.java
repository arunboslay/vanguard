package com.challenge.intercepter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RateLimitInterceptorTest {

    @InjectMocks
    private RateLimitInterceptor rateLimitInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private final Map<String, Bucket> bucketMap = new HashMap<>();

    @Mock
    private PrintWriter printWriter;

    @Mock
    private Bucket tokenBucket;

    @Mock
    private ConsumptionProbe probe;

    @Test
    void testMissingApiKey() throws Exception {

        Mockito.when(request.getParameter("apikey")).thenReturn(null);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        assertFalse(rateLimitInterceptor.preHandle(request, response, null));
        Mockito.verify(response).setContentType("application/json");
        Mockito.verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        Mockito.verify(printWriter).write("{\"message\":\"Missing API Key\"}");

    }

    @Test
    void testInvalidApiKey() throws Exception {

        Mockito.when(request.getParameter("apikey")).thenReturn("1");
        Mockito.when(response.getWriter()).thenReturn(printWriter);
       assertFalse(rateLimitInterceptor.preHandle(request, response, null));
        Mockito.verify(response).setContentType("application/json");
        Mockito.verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        Mockito.verify(printWriter).write("{\"message\":\"Invalid API Key\"}");

    }

    @Test
    void testPrehandleProceed() throws Exception {

        Mockito.when(request.getParameter("apikey")).thenReturn("1");
        Mockito.when(bucketMap.get("1")).thenReturn(tokenBucket);
        Mockito.when(tokenBucket.tryConsumeAndReturnRemaining(1)).thenReturn(probe);
        Mockito.when(probe.isConsumed()).thenReturn(true);
        Mockito.when(probe.getRemainingTokens()).thenReturn(1l);
        assertTrue(rateLimitInterceptor.preHandle(request, response, null));

        Mockito.verify(response).addHeader("X-Rate-Limit-Remaining", 1l+"");

    }

    @Test
    void testPrehandleTriesExceeded() throws Exception {

        Mockito.when(request.getParameter("apikey")).thenReturn("1");
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(bucketMap.get("1")).thenReturn(tokenBucket);
        Mockito.when(tokenBucket.tryConsumeAndReturnRemaining(1)).thenReturn(probe);
        Mockito.when(probe.isConsumed()).thenReturn(false);
        Mockito.when(probe.getNanosToWaitForRefill()).thenReturn(1000000000L);
        assertFalse(rateLimitInterceptor.preHandle(request, response, null));
        Mockito.verify(response).addHeader("X-Rate-Limit-Retry-After-Seconds", 1l+"");
        Mockito.verify(response).setContentType("application/json");
        Mockito.verify(response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        Mockito.verify(printWriter).write("{\"message\":\"You have exhausted your API Request Quota\"}");
    }

}