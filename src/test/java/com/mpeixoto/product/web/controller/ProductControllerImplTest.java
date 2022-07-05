package com.mpeixoto.product.web.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.mpeixoto.product.PojoProvider;
import com.mpeixoto.product.services.ProductService;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.QueryProduct;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

// TODO make the tests comparing by json path, not by the object retrieved.
/**
 * Class responsible for testing if the ProductControllerImpl class is working as expected.
 *
 * @author mpeixoto
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerImplTest {
  private static final String TOKEN = "test token";
  private static final String CORRELATION_ID = "2";
  private ProductDto domaineDto;
  private ProductDto budweiserDto;
  @MockBean private ProductService productService;
  @Autowired private ProductController productController;

  /** Method responsible for setting everything up before each test. */
  @Before
  public void setUp() {
    domaineDto = PojoProvider.getDomaineDto();
    budweiserDto = PojoProvider.getBudweiserDto();
  }

  /**
   * Method responsible for testing the 'updateAProduct' controller. The endpoint must return the
   * product that was updated containing its supplier and a productId.
   */
  @Test
  public void updateAProductGivenAProductIdAndAProductBodyShouldReturnTheUpdatedProduct() {
    final String BUDWEISER_ID = budweiserDto.getProductId();
    ProductDto budweiserDtoUpdated = PojoProvider.getBudweiserDto();
    budweiserDtoUpdated.setName("BUDWEISER UPDATED");

    when(productService.updateAProduct(BUDWEISER_ID, budweiserDto)).thenReturn(budweiserDtoUpdated);

    assertThat(
        productController
            .updateAProduct(CORRELATION_ID, TOKEN, budweiserDto, BUDWEISER_ID)
            .getBody(),
        is(budweiserDtoUpdated));
  }

  /**
   * Method responsible for testing if the retrieveAProduct controller. The endpoint must return the
   * product required given its productId and, in case the 'fetchSuppliers' is equal to true, should
   * also return its supplier.
   */
  @Test
  public void retrieveAProductGivenAProductIdShouldReturnASpecificProduct() {
    String productId = "PRD0000002";

    when(productService.retrieveAProduct(true, productId)).thenReturn(budweiserDto);

    assertThat(
        productController.retrieveAProduct(CORRELATION_ID, TOKEN, true, productId).getBody(),
        is(budweiserDto));
  }

  /**
   * Method responsible for testing the 'addProduct' controller. The endpoint must return the
   * product that was inserted containing its supplier and a productId.
   */
  @Test
  public void addProductGivenAProductShouldReturnTheProductThatWasInsertedInDatabase() {
    ProductDto productDto = PojoProvider.getBudweiserDto();
    productDto.setProductId(null);
    productDto.setSupplierDto(null);

    when(productService.addProduct(productDto)).thenReturn(budweiserDto);

    assertThat(
        productController.addProduct(CORRELATION_ID, TOKEN, productDto).getBody(),
        is(budweiserDto));
  }

  /**
   * Method responsible for verifying if the endpoint 'retrieveProducts' is returning a list of
   * products as expected.
   */
  @Test
  public void retrieveProductsGivenACorrelationIdAndATokenShouldReturnAListOfProducts() {
    final QueryProduct queryProduct = QueryProduct.builder().fetchSuppliers(true).build();
    List<ProductDto> productDtoList = Arrays.asList(domaineDto, budweiserDto);

    when(productService.retrieveProducts(queryProduct)).thenReturn(productDtoList);

    assertThat(
        productController.retrieveProducts(CORRELATION_ID, TOKEN, queryProduct).getBody(),
        is(productDtoList));
  }
}
