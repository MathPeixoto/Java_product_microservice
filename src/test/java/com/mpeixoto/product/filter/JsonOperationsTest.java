package com.mpeixoto.product.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mpeixoto.product.exception.JsonPathException;
import com.mpeixoto.product.web.model.CategoryDto;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.SupplierDto;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mpeixoto.product.ReadFile.readFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Class responsible for testing the ConvertObjectToJson class.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class JsonOperationsTest {
  private JsonOperations jsonOperations;

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
    assertEquals(jsonOperations.parseToJson(bean), expectedResult);
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

    assertEquals(jsonOperations.deserialize(readFile("src/test/resources/oracle/jsonBody.json")), productDto);
  }

  /**
   * Method responsible for testing if the expected exception is being thrown when a IOException
   * occurs.
   */
  @Test
  public void deserializeGivenAWrongJsonPathShouldThrowAnJsonPathException() {
    String jsonPath = "wrong/path";

    assertThatThrownBy(() -> jsonOperations.deserialize(jsonPath))
            .isInstanceOf(JsonPathException.class)
            .hasMessage("Wrong json path")
            .hasCauseInstanceOf(IOException.class);
  }
}
