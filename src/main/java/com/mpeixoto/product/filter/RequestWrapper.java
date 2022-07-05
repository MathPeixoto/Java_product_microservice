package com.mpeixoto.product.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;

/**
 * Class responsible for implementing a customized HttpServletRequest.
 *
 * @author mpeixoto
 */
public class RequestWrapper extends HttpServletRequestWrapper {
  private static final int CHAR_SIZE = 128;
  @Getter private final String body;

  /**
   * Default constructor of the class.
   *
   * @param request The request that will be wrapped
   * @throws IOException It is thrown when a IO error occurred
   */
  RequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    StringBuilder stringBuilder = new StringBuilder();
    try (InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      char[] charBuffer = new char[CHAR_SIZE];
      int bytesRead;
      while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
        stringBuilder.append(charBuffer, 0, bytesRead);
      }
    }
    body = stringBuilder.toString();
  }

  @Override
  public ServletInputStream getInputStream() {
    final ByteArrayInputStream byteArrayInputStream =
        new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
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
      public void setReadListener(ReadListener listener) {
        // Method required when extending the HttpServletRequestWrapper.
      }

      public int read() {
        return byteArrayInputStream.read();
      }
    };
  }

  @Override
  public BufferedReader getReader() {
    InputStream inputStream = this.getInputStream();
    InputStreamReader inputStreamReader =
        new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    return new BufferedReader(inputStreamReader);
  }
}
