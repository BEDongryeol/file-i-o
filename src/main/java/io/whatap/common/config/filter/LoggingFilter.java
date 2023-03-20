package io.whatap.common.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright whatap Inc since 2023/03/20
 * Created by Larry on 2023/03/20
 * Email : inwoo.server@gmail.com
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 요청에 대한 UUID
        final UUID uuid = UUID.randomUUID();
        MDC.put("request_uuid", uuid.toString());

        // REQUEST LOGGING
        ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper cachedResponse = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        Map<String, Object> params = new LinkedHashMap<>();

        try {
            params.put("http_method", cachedRequest.getMethod());
            params.put("request_uri", cachedRequest.getRequestURI());
            params.put("params", mapToJsonString(cachedRequest.getParameterMap()));
            params.put("request_body", objectMapper.readTree(cachedRequest.getContentAsByteArray()));

        } catch (Exception e) {
            log.error("[LOG_ADVICE] Logging error", e);
        }

        log.info("[REQUEST_LOGGING] : {}", params);

        // 비즈니스 로직 수행
        filterChain.doFilter(cachedRequest, cachedResponse);

        // RESPONSE LOGGING
        log.info("[RESPONSE_LOGGING] ResBody : {}", objectMapper.readTree(cachedResponse.getContentAsByteArray()));

        cachedResponse.copyBodyToResponse();

        MDC.clear();
    }

    private String mapToJsonString(Map<String, String[]> parameterMap) {
        StringBuilder builder = new StringBuilder();
        builder.append("{ ");

        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            builder.append(stringEntry.getKey());
            builder.append(" : ");
            for (String value : stringEntry.getValue()) {
                builder.append(value).append(" ");
            }
            builder.append(", ");
        }

        builder.append(" }");
        return builder.toString();
    }
}
