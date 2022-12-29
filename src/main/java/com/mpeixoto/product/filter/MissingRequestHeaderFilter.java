package com.mpeixoto.product.filter;

import com.mpeixoto.product.web.model.StandardError;
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
import org.springframework.http.HttpStatus;
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
    private final JsonOperations jsonOperations;
    private static final String CORRELATION_ID = "X-Correlation-Id";

    /**
     * Method responsible for setting the beans.
     *
     * @param jsonOperations Type: JsonOperations
     */
    public MissingRequestHeaderFilter(JsonOperations jsonOperations) {
        this.jsonOperations = jsonOperations;
    }

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

        if (!Strings.isBlank(requestCorrelationId)) {
            filterChain.doFilter(request, response);
        } else {
            log.error(
                    "Correlation id {} sent a request missing parameters", request.getHeader(CORRELATION_ID));
            StandardError error =
                    StandardError.builder()
                            .status(400)
                            .message("Missing required request parameters")
                            .build();

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(jsonOperations.parseToJson(error));
        }
    }
}
