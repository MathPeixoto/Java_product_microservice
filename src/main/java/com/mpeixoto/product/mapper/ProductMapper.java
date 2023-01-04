package com.mpeixoto.product.mapper;

import com.mpeixoto.product.persistence.domain.CategoryEntity;
import com.mpeixoto.product.persistence.domain.ProductEntity;
import com.mpeixoto.product.web.model.CategoryDto;
import com.mpeixoto.product.web.model.ProductDto;
import org.springframework.stereotype.Component;

/**
 * Class responsible for mapping products.
 *
 * @author mpeixoto
 */
@Component
public class ProductMapper {
    private final SupplierMapper supplierMapper;

    /**
     * Constructor responsible for setting the beans.
     *
     * @param supplierMapper Type: SupplierMapper
     */
    public ProductMapper(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    /**
     * Method responsible for mapping a ProductEntity to ProductDto.
     *
     * @param productEntity Type: ProductEntity
     * @return A ProductDto
     */
    public ProductDto productEntityToProductDto(ProductEntity productEntity) {
        return ProductDto.builder()
                .productId(productEntity.getProductId())
                .name(productEntity.getName().toUpperCase())
                .price(productEntity.getPrice())
                .category(
                        productEntity.getCategory() == CategoryEntity.BEER
                                ? CategoryDto.BEER
                                : CategoryDto.WINE)
                .supplierDto(supplierMapper.supplierEntityToSupplierDto(productEntity.getSupplierEntity()))
                .build();
    }

    /**
     * Method responsible for mapping a ProductDto to ProductEntity.
     *
     * @param productDto Type: ProductDto
     * @return A ProductEntity
     */
    public ProductEntity productDtoToProductEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .productId(productDto.getProductId())
                .name(productDto.getName().toUpperCase())
                .price(productDto.getPrice())
                .category(
                        productDto.getCategory() == CategoryDto.BEER
                                ? CategoryEntity.BEER
                                : CategoryEntity.WINE)
                .supplierEntity(supplierMapper.supplierDtoToSupplierEntity(productDto.getSupplierDto()))
                .build();
    }
}
