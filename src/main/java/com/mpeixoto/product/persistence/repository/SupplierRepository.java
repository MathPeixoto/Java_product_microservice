package com.mpeixoto.product.persistence.repository;

import com.mpeixoto.product.persistence.domain.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsible for providing the methods that consults the suppliers in database.
 *
 * @author mpeixoto
 */
@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
  /**
   * Method responsible for retrieving a supplier given a name.
   *
   * @param name Name of the supplier.
   * @return The founded supplier
   */
  SupplierEntity findByName(String name);

  /**
   * Method responsible for retrieving a supplier given a supplierId.
   *
   * @param supplierId Id of the supplier.
   * @return The founded supplier
   */
  SupplierEntity findBySupplierId(String supplierId);
}
