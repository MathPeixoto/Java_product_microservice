package com.mpeixoto.product.web.controller;

import com.mpeixoto.product.services.ProductService;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.QueryProduct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    private final ProductService productService;
    private final HttpServletRequest request;

    /**
     * Default constructor of the class, responsible for the dependency injection.
     *
     * @param productService Type: ProductService
     */
    public ProductControllerImpl(ProductService productService, HttpServletRequest request) {
        this.productService = productService;
        this.request = request;
    }

    @Override
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('SCOPE_product-write', 'SCOPE_product-read')")
    public ResponseEntity<List<ProductDto>> retrieveProducts(
            @RequestHeader(X_CORRELATION_ID) String correlationId,
            QueryProduct queryProduct) {
        log.debug("Correlation id: {} entered in retrieveProducts endpoint", correlationId);
        return new ResponseEntity<>(productService.retrieveProducts(queryProduct), HttpStatus.OK);
    }

    @Override
    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_product-write')")
    public ResponseEntity<ProductDto> addProduct(
            @RequestHeader(X_CORRELATION_ID) String correlationId,
            @RequestBody ProductDto product) {
        logUserInfo();

        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{product-id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_product-write', 'SCOPE_product-read')")
    public ResponseEntity<ProductDto> retrieveAProduct(
            @RequestHeader(X_CORRELATION_ID) String correlationId,
            @RequestParam(name = "fetch-suppliers", defaultValue = "false") boolean fetchSuppliers,
            @PathVariable("product-id") String productId) {
        return new ResponseEntity<>(
                productService.retrieveAProduct(fetchSuppliers, productId), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{product-id}")
    @PreAuthorize("hasAuthority('SCOPE_product-write')")
    public ResponseEntity<ProductDto> updateAProduct(
            @RequestHeader(X_CORRELATION_ID) String correlationId,
            @RequestBody ProductDto product,
            @PathVariable("product-id") String productId) {
        return new ResponseEntity<>(productService.updateAProduct(productId, product), HttpStatus.OK);
    }

    private void logUserInfo() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) request.getUserPrincipal();
        log.info("User: {} make {} request to {}", jwt.getToken().getClaims().get("preferred_username"), request.getMethod(), request.getRequestURI());
    }
}
