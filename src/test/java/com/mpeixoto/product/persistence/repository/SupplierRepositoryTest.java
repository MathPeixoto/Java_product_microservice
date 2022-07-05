package com.mpeixoto.product.persistence.repository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.mpeixoto.product.PojoProvider;
import com.mpeixoto.product.persistence.domain.SupplierEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class responsible for testing if the SupplierRepository is working as well as expected.
 *
 * @author mpeixoto
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SupplierRepositoryTest {
  private SupplierEntity supplierEntity;
  @Autowired private SupplierRepository supplierRepository;

  /** Method responsible for setting everything up before each test. */
  @Before
  public void setUp() {
    supplierEntity = PojoProvider.getDomaineEntity().getSupplierEntity();
    supplierRepository.save(supplierEntity);
  }

  /** Method responsible for cleaning the database after each test. */
  @After
  public void tearDown() {
    supplierRepository.deleteAll();
  }

  /**
   * Method responsible for testing if the 'findByName' method is returning the required supplier as
   * expected given his name.
   */
  @Test
  public void findByNameGivenASupplierNameShouldReturnTheSupplier() {
    assertThat(supplierRepository.findByName("SUPPLIER A"), is(supplierEntity));
  }

  /**
   * Method responsible for testing if the 'findBySupplierId' method is returning the required
   * supplier as expected given a supplierId.
   */
  @Test
  public void findBySupplierId() {
    assertThat(supplierRepository.findBySupplierId("SUP0000001"), is(supplierEntity));
  }
}
