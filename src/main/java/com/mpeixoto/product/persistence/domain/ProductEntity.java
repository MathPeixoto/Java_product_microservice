package com.mpeixoto.product.persistence.domain;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Product domain class.
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
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String productId;

  private String name;
  private Double price;

  @Enumerated(EnumType.STRING)
  private CategoryEntity category;

  @ManyToOne(cascade = CascadeType.ALL)
  private SupplierEntity supplierEntity;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductEntity that = (ProductEntity) o;
    return Objects.equals(id, that.id)
            && Objects.equals(productId, that.productId)
            && Objects.equals(name, that.name)
            && Objects.equals(price, that.price)
            && category == that.category
            && supplierEntity.equals(that.supplierEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productId, name, price, category, supplierEntity);
  }
}
