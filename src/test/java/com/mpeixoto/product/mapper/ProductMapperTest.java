package com.mpeixoto.product.mapper;

import com.mpeixoto.product.PojoProvider;
import com.mpeixoto.product.persistence.domain.ProductEntity;
import com.mpeixoto.product.web.model.ProductDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Class responsible for testing the ProductMapper class.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductMapperTest {
  @Mock private SupplierMapper supplierMapper;
  private ProductMapper productMapper;

  /** Method responsible for setting everything up before each test. */
  @Before
  public void setUp() {
    productMapper = new ProductMapper(supplierMapper);

    when(supplierMapper.supplierEntityToSupplierDto(
            PojoProvider.getBudweiserEntity().getSupplierEntity()))
        .thenReturn(PojoProvider.getBudweiserDto().getSupplierDto());
  }

  /** Method responsible for testing the productEntityToProductDto given a productEntity. */
  @Test
  public void productEntityToProductDtoGivenAProductEntityShouldReturnAProductDto() {
    ProductEntity budweiserEntity = PojoProvider.getBudweiserEntity();
    ProductDto budweiserDto = PojoProvider.getBudweiserDto();

    assertEquals(productMapper.productEntityToProductDto(budweiserEntity), budweiserDto);
  }
}
