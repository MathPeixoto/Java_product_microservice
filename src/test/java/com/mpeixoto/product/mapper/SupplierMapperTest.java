package com.mpeixoto.product.mapper;

import com.mpeixoto.product.PojoProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Class responsible for testing the SupplierMapper class.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class SupplierMapperTest {
  private SupplierMapper supplierMapper;

  /** Method responsible for setting everything up before each test. */
  @Before
  public void setUp() {
    supplierMapper = new SupplierMapper();
  }

  /** Method responsible for testing the supplierEntityToSupplierDto given a supplierEntity. */
  @Test
  public void supplierEntityToSupplierDtoGivenASupplierEntityShouldReturnASupplierDto() {
    assertEquals(
        supplierMapper.supplierEntityToSupplierDto(
            PojoProvider.getBudweiserEntity().getSupplierEntity()),
        PojoProvider.getBudweiserDto().getSupplierDto());
  }

  /** Method responsible for testing the supplierDtoToSupplierEntity given a supplierDto. */
  @Test
  public void supplierDtoToSupplierEntityGivenASupplierDtoShouldReturnASupplierEntity() {
    assertEquals(
        supplierMapper.supplierDtoToSupplierEntity(PojoProvider.getBudweiserDto().getSupplierDto()),
        PojoProvider.getBudweiserEntity().getSupplierEntity());
  }
}
