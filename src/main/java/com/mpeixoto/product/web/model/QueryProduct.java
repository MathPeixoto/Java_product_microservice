package com.mpeixoto.product.web.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QueryProduct POJO class.
 *
 * @author mpeixoto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class QueryProduct {
  private static final int DEFAULT_LIMIT = 50;
  private static final int DEFAULT_OFFSET = 0;
  private static final String DEFAULT_SORT = "asc.name";
  private List<String> ids;
  private String name;
  private CategoryDto category;
  @Builder.Default private String sort = DEFAULT_SORT;
  @Builder.Default private Integer limit = DEFAULT_LIMIT;
  @Builder.Default private Integer offset = DEFAULT_OFFSET;
  @Builder.Default private Boolean fetchSuppliers = false;
}
