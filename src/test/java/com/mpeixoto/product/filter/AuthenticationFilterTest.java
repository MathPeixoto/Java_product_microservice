package com.mpeixoto.product.filter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mpeixoto.security.exception.TokenException;
import com.mpeixoto.security.services.SecurityService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

/**
 * Class responsible for testing if the authentication filter is working well.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {
  private static final String AUTHORIZATION_HEADER = "test header";
  @Mock private HttpServletRequest request;
  @Mock private RequestWrapper requestWrapper;
  @Mock private HttpServletResponse response;
  @Mock private FilterChain filterChain;
  @Mock private SecurityService securityService;
  @Mock private PrintWriter printWriter;
  @Mock private BufferedReader bufferedReader;
  @Spy private JsonOperations jsonOperations;
  private AuthenticationFilter authenticationFilter;

  /**
   * Method responsible for setting everything up before each test.
   *
   * @throws IOException It is thrown when is not possible to run an IO command.
   */
  @Before
  public void setUp() throws IOException {
    authenticationFilter = spy(new AuthenticationFilter(securityService, jsonOperations));
    doReturn(requestWrapper).when(authenticationFilter).newRequestWrapper(request);
    when(requestWrapper.getHeader("Authorization")).thenReturn(AUTHORIZATION_HEADER);
  }

  /**
   * Method responsible for testing the 'doFilterInternal' method with a valid token.
   *
   * @throws ServletException Is thrown when an error occurs with the request or response.
   * @throws IOException Is thrown when an I/O operation occurs
   */
  @Test
  public void doFilterGivenAValidTokenShouldPass() throws ServletException, IOException {
    when(requestWrapper.getReader()).thenReturn(bufferedReader);
    when(securityService.validateToken(AUTHORIZATION_HEADER)).thenReturn(true);

    authenticationFilter.doFilterInternal(request, response, filterChain);
    verify(filterChain, times(1)).doFilter(requestWrapper, response);
  }

  /**
   * Method responsible for testing the 'doFilterInternal' method with a valid token and a blank
   * customerId.
   *
   * @throws ServletException Is thrown when an error occurs with the request or response.
   * @throws IOException Is thrown when an I/O operation occurs
   */
  @Test
  public void doFilterGivenAnInvalidTokenShouldRespondWithAnErrorResponse()
      throws ServletException, IOException {
    when(securityService.validateToken(AUTHORIZATION_HEADER))
        .thenThrow(new TokenException("error test"));
    when(response.getWriter()).thenReturn(printWriter);

    authenticationFilter.doFilterInternal(request, response, filterChain);

    verify(response, times(1)).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    verify(printWriter, times(1)).write(anyString());
  }

  /**
   * Method responsible for testing the 'doFilterInternal' method with an invalid token.
   *
   * @throws ServletException Is thrown when an error occurs with the request or response.
   * @throws IOException Is thrown when an I/O operation occurs
   */
  @Test
  public void doFilterInternalGivenAnInvalidTokenShouldNotPass()
      throws ServletException, IOException {
    when(securityService.validateToken(AUTHORIZATION_HEADER)).thenReturn(false);
    when(response.getWriter()).thenReturn(printWriter);

    authenticationFilter.doFilterInternal(request, response, filterChain);

    verify(response, times(1)).setStatus(HttpStatus.UNAUTHORIZED.value());
    verify(printWriter, times(1)).write(anyString());
  }
}
