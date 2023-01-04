package com.mpeixoto.product;

import com.mpeixoto.product.persistence.domain.CategoryEntity;
import com.mpeixoto.product.persistence.domain.ProductEntity;
import com.mpeixoto.product.persistence.domain.SupplierEntity;
import com.mpeixoto.product.web.model.CategoryDto;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.SupplierDto;

/**
 * PojoProvider.
 *
 * @author mpeixoto
 */
public class PojoProvider {
    private static final Double PRODUCT_PRICE_BUDWEISER = 10D;
    private static final Double PRODUCT_PRICE_DOMAINE = 5D;

    /**
     * Provide a ProductEntity.
     *
     * @return ProductEntity
     */
    public static ProductEntity getDomaineEntity() {
        SupplierEntity supplierEntity =
                SupplierEntity.builder().supplierId("SUP0000001").name("SUPPLIER A").build();

        return ProductEntity.builder()
                .name("DOMAINE CARNEROS")
                .productId("PRD0000008")
                .price(PRODUCT_PRICE_DOMAINE)
                .category(CategoryEntity.WINE)
                .supplierEntity(supplierEntity)
                .build();
    }

    /**
     * Provide a ProductEntity.
     *
     * @return ProductEntity
     */
    public static ProductEntity getBudweiserEntity() {
        SupplierEntity supplierEntity =
                SupplierEntity.builder().supplierId("SUP0000001").name("SUPPLIER A").build();

        return ProductEntity.builder()
                .name("BUDWEISER")
                .productId("PRD0000002")
                .price(PRODUCT_PRICE_BUDWEISER)
                .category(CategoryEntity.BEER)
                .supplierEntity(supplierEntity)
                .build();
    }

    /**
     * Provide a ProductDto.
     *
     * @return ProductDto
     */
    public static ProductDto getDomaineDto() {
        SupplierDto supplierDto =
                SupplierDto.builder().supplierId("SUP0000001").name("SUPPLIER A").build();

        return ProductDto.builder()
                .name("DOMAINE CARNEROS")
                .productId("PRD0000008")
                .price(PRODUCT_PRICE_DOMAINE)
                .category(CategoryDto.WINE)
                .supplierDto(supplierDto)
                .build();
    }

    /**
     * Provide a ProductDto.
     *
     * @return ProductDto
     */
    public static ProductDto getBudweiserDto() {
        SupplierDto supplierDto =
                SupplierDto.builder().supplierId("SUP0000001").name("SUPPLIER A").build();

        return ProductDto.builder()
                .name("BUDWEISER")
                .productId("PRD0000002")
                .price(PRODUCT_PRICE_BUDWEISER)
                .category(CategoryDto.BEER)
                .supplierDto(supplierDto)
                .build();
    }
}
