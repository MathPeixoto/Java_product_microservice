package com.mpeixoto.product.persistence.repository;

import com.mpeixoto.product.PojoProvider;
import com.mpeixoto.product.persistence.domain.ProductEntity;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Class responsible for testing if the ProductRepository is working as well as expected.
 *
 * @author mpeixoto
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
  private ProductEntity productEntity;
  @Autowired private ProductRepository productRepository;

  /** Method responsible for setting everything up before each test. */
  @Before
  public void setUp() {
    productEntity = PojoProvider.getDomaineEntity();
    productRepository.save(productEntity);
  }

  /** Method responsible for cleaning the database after each test. */
  @After
  public void tearDown() {
    productRepository.deleteAll();
  }

  /**
   * Method responsible for testing if the 'findByProductId' method is retrieving the product
   * expected.
   */
  @Test
  public void findByProductIdGivenAProductIdShouldReturnARequiredProduct() {
    assertEquals(productRepository.findByProductId(productEntity.getProductId()), productEntity);
  }

  /**
   * Method responsible for testing if the 'findByName' method is retrieving the product expected.
   */
  @Test
  public void findByNameGivenAProductNameShouldReturnTheProduct() {
    assertEquals(productRepository.findByName("DOMAINE CARNEROS"), productEntity);
  }

  /**
   * Method responsible for testing if the list of product is being well retrieved given a few
   * filters.
   */
  @Test
  public void findAllProductsGivenFiltersShouldReturnAListOfProducts() {
    List<ProductEntity> productEntityList = productRepository.findAllProducts(1, 0).isPresent()
        ? productRepository.findAllProducts(1, 0).get()
        : fail("The list of products is empty");
    assertEquals(productEntityList, singletonList(productEntity));
  }
}
