package com.mpeixoto.product.persistence.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Suppliers available.
 *
 * @author mpeixoto
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class SupplierEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(unique = true)
  private String supplierId;

  private String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SupplierEntity that = (SupplierEntity) o;
    return Objects.equals(id, that.id)
            && Objects.equals(supplierId, that.supplierId)
            && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, supplierId, name);
  }
}
