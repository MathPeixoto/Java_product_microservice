package com.mpeixoto.product.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * ProductDto POJO class.
 *
 * @author mpeixoto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class ProductDto {
    private static final Double MINIMUM_VALUE = 0D;

    @JsonProperty("id")
    private String productId;
    @NotNull
    private Double price;

    private String name;
    private CategoryDto category;

    @JsonProperty("supplier")
    private SupplierDto supplierDto;

    /**
     * Setter method responsible for defining how the price variable must be serialized.
     *
     * @param price Type: Double
     */
    @JsonProperty("price")
    public void setPrice(@NotEmpty Double price) {
        this.price = price < MINIMUM_VALUE ? MINIMUM_VALUE : price;
    }
}
