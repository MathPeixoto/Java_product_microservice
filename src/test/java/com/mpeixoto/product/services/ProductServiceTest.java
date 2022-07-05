package com.mpeixoto.product.services;

import com.mpeixoto.product.PojoProvider;
import com.mpeixoto.product.exception.NotFoundException;
import com.mpeixoto.product.mapper.ProductMapper;
import com.mpeixoto.product.mapper.SupplierMapper;
import com.mpeixoto.product.persistence.domain.ProductEntity;
import com.mpeixoto.product.persistence.repository.ProductRepository;
import com.mpeixoto.product.persistence.repository.SupplierRepository;
import com.mpeixoto.product.web.model.CategoryDto;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.QueryProduct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Class responsible for testing if the AvailableProductsService is working fine.
 *
 * @author mpeixoto
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
  @Mock private ProductRepository productRepository;
  @Mock private SupplierRepository supplierRepository;
  @Mock private ProductMapper productMapper;
  @Mock private SupplierMapper supplierMapper;
  @Captor private ArgumentCaptor<ProductDto> productDtoArgumentCaptor;
  private ProductDto budweiserDto;
  private ProductDto domaineDto;
  private ProductEntity budweiserEntity;
  private ProductService productService;
  /** Rule used for testing if the expected exceptions is being retrieved. */
  @Rule public ExpectedException expectedException = ExpectedException.none();

  /** Method responsible for setting everything up before each test. */
  @Before
  public void setUp() {
    domaineDto = PojoProvider.getDomaineDto();
    budweiserDto = PojoProvider.getBudweiserDto();
    budweiserEntity = PojoProvider.getBudweiserEntity();
    productService =
        new ProductService(productRepository, productMapper, supplierRepository, supplierMapper);
    QueryProduct queryProduct = QueryProduct.builder().build();
    ProductEntity domaineEntity = PojoProvider.getDomaineEntity();
    Optional<List<ProductEntity>> productList =
        Optional.of(Arrays.asList(domaineEntity, budweiserEntity));

    when(productRepository.findAllProducts(queryProduct.getLimit(), queryProduct.getOffset()))
        .thenReturn(productList);
    when(productMapper.productEntityToProductDto(domaineEntity)).thenReturn(domaineDto);
    when(productMapper.productEntityToProductDto(budweiserEntity)).thenReturn(budweiserDto);
  }

  /**
   * Method responsible for testing if the NotFoundException is being thrown when a required product
   * isn't founded.
   */
  @Test
  public void
      updateAProductGivenAProductIdOfAProductThatDoesNotExistShouldThrowANotFoundException() {
    final String PRODUCT_ID = "FAIL000000";

    when(productRepository.findByProductId(PRODUCT_ID)).thenReturn(null);
    expectedException.expect(NotFoundException.class);

    productService.updateAProduct(PRODUCT_ID, budweiserDto);
  }

  /**
   * Method responsible for testing if the 'updateAProduct' is updating the required product as
   * expected.
   */
  @Test
  public void updateAProductGivenAProductIdShouldReturnTheProductUpdated() {
    final String BUDWEISER_ID = budweiserDto.getProductId();
    ProductEntity budweiserEntityUpdated = PojoProvider.getBudweiserEntity();
    budweiserEntityUpdated.setName("BUDWEISER UPDATED");
    ProductDto budweiserDtoUpdated = PojoProvider.getBudweiserDto();
    budweiserDtoUpdated.setName("BUDWEISER UPDATED");

    when(productRepository.findByProductId(BUDWEISER_ID)).thenReturn(budweiserEntity);
    when(supplierMapper.supplierEntityToSupplierDto(budweiserEntity.getSupplierEntity()))
        .thenReturn(budweiserDto.getSupplierDto());
    when(productMapper.productDtoToProductEntity(budweiserDto)).thenReturn(budweiserEntityUpdated);
    when(productRepository.save(budweiserEntityUpdated)).thenReturn(budweiserEntityUpdated);
    when(productMapper.productEntityToProductDto(budweiserEntityUpdated))
        .thenReturn(budweiserDtoUpdated);

    assertThat(productService.updateAProduct(BUDWEISER_ID, budweiserDto), is(budweiserDtoUpdated));
  }

  /**
   * Method responsible for testing if the 'retrieveAProduct' is returning the required product as
   * expected.
   */
  @Test
  public void retrieveAProductGivenAProductIdThatDoesNotExistShouldThrowANotFoundException() {
    String productId = "FAIL000000";

    when(productRepository.findByProductId(productId)).thenReturn(null);

    expectedException.expect(NotFoundException.class);
    productService.retrieveAProduct(false, productId);
  }

  /**
   * Method responsible for testing if the 'retrieveAProduct' is returning the required product as
   * expected.
   */
  @Test
  public void retrieveAProductGivenAProductIdShouldReturnARequiredProductId() {
    String productId = budweiserDto.getProductId();
    budweiserDto.setSupplierDto(null);

    when(productRepository.findByProductId(productId)).thenReturn(budweiserEntity);

    assertThat(productService.retrieveAProduct(false, productId), is(budweiserDto));
  }

  /**
   * Method responsible for testing if the 'addProduct' method is returning the products as
   * expected.
   */
  @Test
  public void
      addProductGivenAProductEntityThatAlreadyExistsInDatabaseShouldReturnTheProductThatWasAddedInDatabase() {
    budweiserDto.getSupplierDto().setName(null);

    when(productRepository.findByName(budweiserDto.getName())).thenReturn(budweiserEntity);
    when(productMapper.productEntityToProductDto(budweiserEntity)).thenReturn(budweiserDto);

    assertThat(productService.addProduct(budweiserDto), is(budweiserDto));
  }

  /**
   * Method responsible for testing if the 'addProduct' method is returning the products as
   * expected.
   */
  @Test
  public void addProductGivenAProductDtoShouldReturnTheProductThatWasAddedInDatabase() {
    budweiserDto.getSupplierDto().setName(null);
    budweiserDto.setProductId(null);

    when(productRepository.findByName(budweiserDto.getName())).thenReturn(null);
    when(productMapper.productDtoToProductEntity(productDtoArgumentCaptor.capture()))
        .thenReturn(budweiserEntity);
    when(productRepository.save(budweiserEntity)).thenReturn(budweiserEntity);

    ProductDto productDto = productService.addProduct(budweiserDto);

    assertThat(productDto.getSupplierDto(), is(budweiserDto.getSupplierDto()));
    assertThat(productDto.getName(), is(budweiserDto.getName()));
    assertThat(productDto.getCategory(), is(budweiserDto.getCategory()));
    assertThat(productDto.getPrice(), is(budweiserDto.getPrice()));
    assertTrue(
        productService
            .addProduct(productDtoArgumentCaptor.getValue())
            .getProductId()
            .contains("PRD"));
  }

  /**
   * Method responsible for testing if the 'addProduct' method is returning the products as
   * expected.
   */
  @Test
  public void
      addProductGivenAProductDtoWithNullSupplierIdShouldReturnTheProductThatWasAddedInDatabase() {
    budweiserDto.getSupplierDto().setSupplierId(null);
    budweiserDto.setProductId(null);

    when(productRepository.findByName(budweiserDto.getName())).thenReturn(null);
    when(productMapper.productDtoToProductEntity(productDtoArgumentCaptor.capture()))
        .thenReturn(budweiserEntity);
    when(productRepository.save(budweiserEntity)).thenReturn(budweiserEntity);

    ProductDto productDto = productService.addProduct(budweiserDto);

    assertThat(productDto.getSupplierDto(), is(budweiserDto.getSupplierDto()));
    assertThat(productDto.getName(), is(budweiserDto.getName()));
    assertThat(productDto.getCategory(), is(budweiserDto.getCategory()));
    assertThat(productDto.getPrice(), is(budweiserDto.getPrice()));
    assertTrue(
        productService
            .addProduct(productDtoArgumentCaptor.getValue())
            .getProductId()
            .contains("PRD"));
  }

  /**
   * Method responsible for testing if the 'retrieveProducts' method is returning the products as
   * expected.
   */
  @Test
  public void retrieveProductsGivenAProductNameShouldReturnTheRequiredProduct() {
    List<ProductDto> productSingletonList = Collections.singletonList(domaineDto);
    QueryProduct domaineCarneros =
        QueryProduct.builder().name("DOMAINE CARNEROS").fetchSuppliers(true).build();

    assertThat(productService.retrieveProducts(domaineCarneros), is(productSingletonList));
  }

  /**
   * Method responsible for testing if the 'retrieveProducts' method is returning the products as
   * expected.
   */
  @Test
  public void retrieveProductsGivenAProductCategoryShouldReturnTheRequiredProduct() {
    List<ProductDto> productSingletonList = Collections.singletonList(budweiserDto);
    QueryProduct budweiser =
        QueryProduct.builder().category(CategoryDto.BEER).fetchSuppliers(true).build();

    assertThat(productService.retrieveProducts(budweiser), is(productSingletonList));
  }

  /**
   * Method responsible for testing if the 'retrieveProducts' method is returning the products as
   * expected.
   */
  @Test
  public void retrieveProductsGivenProductsIdsShouldReturnTheRequiredProducts() {
    List<ProductDto> productDtoList = Arrays.asList(budweiserDto, domaineDto);
    QueryProduct ids =
        QueryProduct.builder()
            .ids(Arrays.asList(domaineDto.getProductId(), budweiserDto.getProductId()))
            .fetchSuppliers(true)
            .build();

    assertThat(productService.retrieveProducts(ids), is(productDtoList));
  }

  /**
   * Method responsible for testing if the 'retrieveProducts' method is returning the products as
   * expected.
   */
  @Test
  public void retrieveProductsGivenADescPriceSortShouldReturnTheRequiredProducts() {
    List<ProductDto> productDtoList = Arrays.asList(budweiserDto, domaineDto);
    QueryProduct ids = QueryProduct.builder().sort("desc.price").fetchSuppliers(false).build();

    budweiserDto.setSupplierDto(null);
    domaineDto.setSupplierDto(null);

    assertThat(productService.retrieveProducts(ids), is(productDtoList));
  }

  /**
   * Method responsible for testing if the 'retrieveProducts' method is returning the products as
   * expected.
   */
  @Test
  public void retrieveProductsGivenAnAscPriceSortShouldReturnTheRequiredProducts() {
    List<ProductDto> productDtoList = Arrays.asList(domaineDto, budweiserDto);
    QueryProduct ids = QueryProduct.builder().sort("asc.price").fetchSuppliers(false).build();

    budweiserDto.setSupplierDto(null);
    domaineDto.setSupplierDto(null);

    assertThat(productService.retrieveProducts(ids), is(productDtoList));
  }

  /**
   * Method responsible for testing if the 'retrieveProducts' method is returning the products as
   * expected.
   */
  @Test
  public void retrieveProductsGivenADescNameSortShouldReturnTheRequiredProducts() {
    List<ProductDto> productDtoList = Arrays.asList(domaineDto, budweiserDto);
    QueryProduct ids = QueryProduct.builder().sort("desc.name").fetchSuppliers(false).build();

    budweiserDto.setSupplierDto(null);
    domaineDto.setSupplierDto(null);

    assertThat(productService.retrieveProducts(ids), is(productDtoList));
  }
}
