package com.mpeixoto.product.web.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Suppliers available.
 *
 * @author mpeixoto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
  private static final int MINIMUM_LENGTH = 6;
  private static final int MAXIMUM_LENGTH = 10;

  @NotEmpty
  @Size(min = MINIMUM_LENGTH, max = MAXIMUM_LENGTH)
  private String supplierId;

  private String name;
}
