package com.mpeixoto.product.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class responsible for testing if the missing request header is working well.
 *
 * @author mpeixoto
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MissingRequestHeaderFilterTest {
    @MockBean
    private HttpServletRequest request;
    @MockBean
    private HttpServletResponse response;
    @MockBean
    private FilterChain filterChain;
    @MockBean
    private PrintWriter printWriter;
    @Autowired
    private MissingRequestHeaderFilter missingRequestHeaderFilter;

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
        when(request.getHeader("Authorization")).thenReturn(null);
        when(response.getWriter()).thenReturn(printWriter);

        missingRequestHeaderFilter.doFilterInternal(request, response, filterChain);
        verify(response, times(1)).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(printWriter, times(1)).write(anyString());
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
        when(response.getWriter()).thenReturn(printWriter);

        missingRequestHeaderFilter.doFilterInternal(request, response, filterChain);
        verify(response, times(1)).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(printWriter, times(1)).write(anyString());
    }
}
