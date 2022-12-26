package com.mpeixoto.product.filter;

import java.util.UUID;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * Class responsible for providing variables and methods that are being used in most service
 * classes.
 *
 * @author mpeixoto
 */
@Component
@Slf4j
public class GenerateRequestId implements ServletRequestListener {
  private static final String REQUEST_ID = "RequestId";

  /**
   * Method responsible for create a requestId for each request and put it inside the MDC.
   *
   * @param arg0 Type: ServletRequestEvent
   */
  @Override
  public void requestInitialized(ServletRequestEvent arg0) {
    MDC.put(REQUEST_ID, String.valueOf(UUID.randomUUID()));
    log.info("Request with the request id {} has been started", MDC.get(REQUEST_ID));
  }

  /**
   * Method responsible for cleaning the MDC.
   *
   * @param arg0 Type: ServletRequestEvent
   */
  @Override
  public void requestDestroyed(ServletRequestEvent arg0) {
    log.info("Request with the request id {} has been completed", MDC.get(REQUEST_ID));
    MDC.clear();
  }
}
