package com.mpeixoto.product.web.controller;

import com.mpeixoto.product.services.ProductService;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.QueryProduct;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class responsible for the operations within web environment.
 *
 * @author mpeixoto
 */
@RestController
@RequestMapping("v1/products")
@Log4j2
@Validated
public class ProductControllerImpl implements ProductController {
  private static final String X_CORRELATION_ID = "X-Correlation-Id";
  private static final String AUTHORIZATION = "Authorization";
  private final ProductService productService;

  /**
   * Default constructor of the class, responsible for the dependency injection.
   *
   * @param productService Type: ProductService
   */
  public ProductControllerImpl(ProductService productService) {
    this.productService = productService;
  }

  @Override
  @GetMapping("/")
  public ResponseEntity<List<ProductDto>> retrieveProducts(
      @RequestHeader(X_CORRELATION_ID) String correlationId,
      @RequestHeader(AUTHORIZATION) String token,
      QueryProduct queryProduct) {
    log.debug("Correlation id: {} entered in retrieveProducts endpoint", correlationId);
    return new ResponseEntity<>(productService.retrieveProducts(queryProduct), HttpStatus.OK);
  }

  @Override
  @PostMapping("/")
  public ResponseEntity<ProductDto> addProduct(
      @RequestHeader(X_CORRELATION_ID) String correlationId,
      @RequestHeader(AUTHORIZATION) String token,
      @RequestBody ProductDto product) {
    return new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK);
  }

  @Override
  @GetMapping("/{product-id}")
  public ResponseEntity<ProductDto> retrieveAProduct(
      @RequestHeader(X_CORRELATION_ID) String correlationId,
      @RequestHeader(AUTHORIZATION) String token,
      @RequestParam(name = "fetch-suppliers", defaultValue = "false") boolean fetchSuppliers,
      @PathVariable("product-id") String productId) {
    return new ResponseEntity<>(
        productService.retrieveAProduct(fetchSuppliers, productId), HttpStatus.OK);
  }

  @Override
  @PutMapping("/{product-id}")
  public ResponseEntity<ProductDto> updateAProduct(
      @RequestHeader(X_CORRELATION_ID) String correlationId,
      @RequestHeader(AUTHORIZATION) String token,
      @RequestBody ProductDto product,
      @PathVariable("product-id") String productId) {
    return new ResponseEntity<>(productService.updateAProduct(productId, product), HttpStatus.OK);
  }
}
