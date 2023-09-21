package br.com.pitang.user.car.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class MDCFilter extends OncePerRequestFilter {

    private static final String REQUEST_TRACE_ID = "requestTraceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String token = generateRequestTraceId();
        MDC.put(REQUEST_TRACE_ID, token);
        MDC.put("requestUri", request.getRequestURI());

        response.addHeader(REQUEST_TRACE_ID, token);
        chain.doFilter(request, response);

        MDC.remove(REQUEST_TRACE_ID);
        MDC.remove("requestUri");
    }

    private String generateRequestTraceId() {

        String token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        log.debug("Generating requestTraceId UUID {}.", token);
        return token;
    }
}
