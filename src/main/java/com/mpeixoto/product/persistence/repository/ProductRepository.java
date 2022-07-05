package com.mpeixoto.product.persistence.repository;

import com.mpeixoto.product.persistence.domain.ProductEntity;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interface responsible for providing the methods that consults the products in database.
 *
 * @author mpeixoto
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

  /**
   * Method responsible for retrieving a product given a productId.
   *
   * @param productId Type: String
   * @return The founded product
   */
  ProductEntity findByProductId(String productId);

  /**
   * Method responsible for retrieving a product given a name.
   *
   * @param name Name of the product.
   * @return The founded product
   */
  ProductEntity findByName(String name);

  /**
   * Method responsible for retrieving a list of product entities based on limit and an offset.
   *
   * @param limit Type: Integer
   * @param offset Type: Integer
   * @return A list of product entities
   */
  @NotNull
  @Query(value = "SELECT * FROM product_entity limit :limit  offset :offset", nativeQuery = true)
  Optional<List<ProductEntity>> findAllProducts(
      @Param("limit") Integer limit, @Param("offset") Integer offset);
}
