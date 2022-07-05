package com.mpeixoto.product.persistence.repository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.mpeixoto.product.PojoProvider;
import com.mpeixoto.product.persistence.domain.ProductEntity;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
    assertThat(productRepository.findByProductId(productEntity.getProductId()), is(productEntity));
  }

  /**
   * Method responsible for testing if the 'findByName' method is retrieving the product expected.
   */
  @Test
  public void findByNameGivenAProductNameShouldReturnTheProduct() {
    assertThat(productRepository.findByName("DOMAINE CARNEROS"), is(productEntity));
  }

  /**
   * Method responsible for testing if the list of product is being well retrieved given a few
   * filters.
   */
  @Test
  public void findAllProductsGivenFiltersShouldReturnAListOfProducts() {
    assertThat(
        productRepository.findAllProducts(1, 0).get(),
        is(Collections.singletonList(productEntity)));
  }
}
