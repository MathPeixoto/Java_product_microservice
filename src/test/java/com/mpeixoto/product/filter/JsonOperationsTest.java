package com.mpeixoto.product.filter;

import static com.mpeixoto.product.ReadFile.readFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertNull;

import com.mpeixoto.product.exception.JsonPathException;
import com.mpeixoto.product.web.model.CategoryDto;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.SupplierDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Class responsible for testing the ConvertObjectToJson class.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class JsonOperationsTest {
  private JsonOperations jsonOperations;
  /** Rule used for testing exceptions. */
  @Rule public ExpectedException expectedException = ExpectedException.none();

  /** Method responsible for setting the jsonOperations variable. */
  @Before
  public void setUp() {
    jsonOperations = new JsonOperations();
  }

  /**
   * Method responsible for testing if the 'parseToJson' method is returning null if a null object
   * is passed as parameter.
   *
   * @throws JsonProcessingException is thrown if wasn't possible to convert the object.
   */
  @Test
  public void parseToJsonGivenNullShouldReturnNull() throws JsonProcessingException {
    assertNull(jsonOperations.parseToJson(null));
  }

  /**
   * Method responsible for testing if the 'parseToJson' method is returning the object as expected.
   *
   * @throws JsonProcessingException is thrown if wasn't possible to convert the object.
   */
  @Test
  public void parseToJsonGivenANonNullObjectShouldReturnAString() throws JsonProcessingException {
    Bean bean = new Bean();
    bean.value = "test";
    String expectedResult = "{\"value\":\"test\"}";
    assertThat(jsonOperations.parseToJson(bean), is(expectedResult));
  }

  /**
   * Method responsible for verifying that the deserialize method is returning a CustomerDto
   * instantiation as expected.
   */
  @Test
  public void deserializeGivenAJsonPathShouldReturnACustomerDtoObject() {
    final SupplierDto supplierDto =
        SupplierDto.builder().supplierId("SUP0000001").name("SUPPLIER A").build();
    ProductDto productDto =
        ProductDto.builder()
            .name("test")
            .productId("PRD000")
            .price(0D)
            .category(CategoryDto.BEER)
            .supplierDto(supplierDto)
            .build();

    assertThat(
        jsonOperations.deserialize(readFile("src/test/resources/oracle/jsonBody.json")),
        is(productDto));
  }

  /**
   * Method responsible for testing if the expected exception is being thrown when a IOException
   * occurs.
   */
  @Test
  public void deserializeGivenAWrongJsonPathShouldThrowAnJsonPathException() {
    expectedException.expect(JsonPathException.class);
    expectedException.expectCause(instanceOf(IOException.class));

    jsonOperations.deserialize("wrong/path");
  }
}
