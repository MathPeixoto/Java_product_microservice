package com.mpeixoto.product.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.MDC;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class responsible for testing if the missing request header is working well.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class MissingRequestHeaderFilterTest {
    private final MissingRequestHeaderFilter missingRequestHeaderFilter = new MissingRequestHeaderFilter();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    /**
     * Method responsible for testing the 'doFilterInternal' method with valid headers.
     *
     * @throws ServletException Is thrown when an error occurs with the request or response.
     * @throws IOException      Is thrown when an I/O operation occurs
     */
    @Test
    public void doFilterInternalGivenValidHeadersShouldPass() throws ServletException, IOException {
        String correlationId = "test correlation-id";

        when(request.getHeader("X-Correlation-Id")).thenReturn(correlationId);

        missingRequestHeaderFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    /**
     * Method responsible for testing the 'doFilterInternal' method with invalid headers.
     *
     * @throws ServletException Is thrown when an error occurs with the request or response.
     * @throws IOException      Is thrown when an I/O operation occurs
     */
    @Test
    public void doFilterInternalGivenInvalidHeadersShouldNotPass()
            throws ServletException, IOException {
        when(request.getHeader("X-Correlation-Id")).thenReturn(null);

        missingRequestHeaderFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(MDC.get("X-Correlation-Id"));
    }

    /**
     * Method responsible for testing the 'doFilterInternal' method with invalid correlation and a
     * valid authorization.
     *
     * @throws ServletException Is thrown when an error occurs with the request or response.
     * @throws IOException      Is thrown when an I/O operation occurs
     */
    @Test
    public void doFilterInternalGivenInvalidCorrelationIdAndAValidAuthorizationsShouldNotPass()
            throws ServletException, IOException {

        when(request.getHeader("X-Correlation-Id")).thenReturn(null);

        missingRequestHeaderFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(MDC.get("X-Correlation-Id"));
    }
}
