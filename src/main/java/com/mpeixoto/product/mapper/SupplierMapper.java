package com.mpeixoto.product.mapper;

import com.mpeixoto.product.persistence.domain.SupplierEntity;
import com.mpeixoto.product.web.model.SupplierDto;
import org.springframework.stereotype.Component;

/**
 * Class responsible for mapping suppliers.
 *
 * @author mpeixoto
 */
@Component
public class SupplierMapper {

  /**
   * Method responsible for mapping a SupplierEntity To SupplierDto.
   *
   * @param supplierEntity Type: SupplierEntity
   * @return A Supplier
   */
  public SupplierDto supplierEntityToSupplierDto(SupplierEntity supplierEntity) {
    return SupplierDto.builder()
        .supplierId(supplierEntity.getSupplierId())
        .name(supplierEntity.getName())
        .build();
  }

  /**
   * Method responsible for mapping a SupplierDto to SupplierEntity.
   *
   * @param supplierDto Type: SupplierDto
   * @return A Supplier
   */
  public SupplierEntity supplierDtoToSupplierEntity(SupplierDto supplierDto) {
    return SupplierEntity.builder()
        .supplierId(supplierDto.getSupplierId())
        .name(supplierDto.getName())
        .build();
  }
}
