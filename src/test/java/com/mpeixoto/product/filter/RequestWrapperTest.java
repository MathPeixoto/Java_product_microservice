package com.mpeixoto.product.filter;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Class responsible for testing the RequestWrapper class.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestWrapperTest {
  private static final String BODY = "body test";
  @Mock private HttpServletRequest request;
  private RequestWrapper requestWrapper;

  private ServletInputStream getInputStream() {
    final ByteArrayInputStream byteArrayInputStream =
        new ByteArrayInputStream(BODY.getBytes(StandardCharsets.UTF_8));
    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener listener) {}

      public int read() {
        return byteArrayInputStream.read();
      }
    };
  }

  /**
   * Method responsible for setting everything up before each test.
   *
   * @throws IOException It is thrown when an IOException occurs inside RequestWrapper constructor.
   */
  @Before
  public void setUp() throws IOException {
    ServletInputStream servletInputStream = getInputStream();
    when(request.getInputStream()).thenReturn(servletInputStream);
    requestWrapper = new RequestWrapper(request);
  }

  /** Method responsible for testing if the ServletInputStream retrieved is the expected. */
  @Test
  public void getInputStreamShouldReturnAServletInputStream() {
    assertThat(requestWrapper.getInputStream(), is(any(ServletInputStream.class)));
  }

  /** Method responsible for testing if the BufferedReader retrieved is the expected. */
  @Test
  public void getReaderShouldReturnABufferedReader() {
    assertThat(requestWrapper.getReader(), is(any(BufferedReader.class)));
  }

  /** Method responsible for testing if the String retrieved is the expected. */
  @Test
  public void getBodyShouldReturnTheBodyOfTheRequest() {
    assertThat(requestWrapper.getBody(), is(BODY));
  }
}
