package com.mpeixoto.product.filter;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import javax.servlet.ServletRequestEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.MDC;

/**
 * Class responsible for testing the GenerateRequestId class.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class GenerateRequestIdTest {
  private GenerateRequestId generateRequestId;
  @Mock private ServletRequestEvent servletRequestEvent;

  /** Method responsible for instantiating a GenerateRequestId class before each test. */
  @Before
  public void setUp() {
    generateRequestId = new GenerateRequestId();
  }

  /** Method responsible for cleaning the MDC after each test. */
  @After
  public void tearDown() {
    MDC.clear();
  }

  /** Method responsible for testing if the requestId is being well populated. */
  @Test
  public void requestInitializedGivenNothingShouldPutAStringInMDC() {
    generateRequestId.requestInitialized(servletRequestEvent);

    assertThat(MDC.get("RequestId"), isA(String.class));
    assertNotNull(MDC.get("RequestId"));
  }

  /** Method responsible for testing if the requestId is destroyed well. */
  @Test
  public void requestDestroyed() {
    MDC.put("RequestId", "test");
    generateRequestId.requestDestroyed(servletRequestEvent);
    assertNull(MDC.get("RequestId"));
  }
}
