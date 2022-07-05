package com.mpeixoto.product.filter;

import com.mpeixoto.product.web.model.StandardError;
import com.mpeixoto.security.services.SecurityService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter responsible for checking the token before each request.
 *
 * @author mpeixoto
 */
@Component
@Order(2)
@Log4j2
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String CORRELATION_ID = "X-Correlation-Id";
    private static final String AUTHORIZATION = "Authorization";
    private final JsonOperations jsonOperations;
    private final SecurityService securityService;

    /**
     * Default constructor of the class responsible for the dependency injection.
     *
     * @param securityService Type: SecurityService
     * @param jsonOperations  Type: JsonOperations
     */
    public AuthenticationFilter(SecurityService securityService, JsonOperations jsonOperations) {
        this.securityService = securityService;
        this.jsonOperations = jsonOperations;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            RequestWrapper requestWrapper = newRequestWrapper(request);
            String correlationId = MDC.get(CORRELATION_ID);
            String authorizationToken = requestWrapper.getHeader(AUTHORIZATION);
            Boolean authorization = securityService.validateToken(authorizationToken);

            if (Boolean.TRUE.equals(authorization)) {
                log.debug("Correlation id: {} was authorized", correlationId);
                MDC.put(AUTHORIZATION, authorizationToken);
                requestWrapper.getReader().close();
                filterChain.doFilter(requestWrapper, response);
            } else {
                log.debug("Correlation id: {} was not authorized", correlationId);
                StandardError error =
                        StandardError.builder()
                                .status(401)
                                .message("Invalid System Token in request")
                                .build();

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(jsonOperations.parseToJson(error));
            }
            // TODO  just use more specifics exceptions instead of RuntimeException.
        } catch (RuntimeException e) {
            log.error("An Internal Error Server has occurred\nMessage: {}", e.getMessage());
            log.error("An Internal Error Server has occurred\nCause:", e.getCause());

            // TODO  change these messages that are being written in StandardError instance
            //       to an external file.
            StandardError error =
                    StandardError.builder()
                            .status(500)
                            .message("An Internal Server Error occurred")
                            .build();

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(jsonOperations.parseToJson(error));
        }
    }

    RequestWrapper newRequestWrapper(HttpServletRequest request) throws IOException {
        return new RequestWrapper(request);
    }
}
