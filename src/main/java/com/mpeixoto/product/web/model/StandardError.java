package com.mpeixoto.product.web.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StandardError POJO class.
 *
 * @author mpeixoto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
