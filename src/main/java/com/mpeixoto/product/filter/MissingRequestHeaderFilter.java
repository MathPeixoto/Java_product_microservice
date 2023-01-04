package com.mpeixoto.product.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter responsible for checking if the request pass every required headers.
 *
 * @author mpeixoto
 */
@Component
@Order(1)
@Log4j2
public class MissingRequestHeaderFilter extends OncePerRequestFilter {
    private static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestCorrelationId = request.getHeader(CORRELATION_ID);

        if (Strings.isBlank(requestCorrelationId)) {
            MDC.put(CORRELATION_ID, String.valueOf(UUID.randomUUID()));
        } else {
            MDC.put(CORRELATION_ID, requestCorrelationId);
        }

        filterChain.doFilter(request, response);
    }
}
