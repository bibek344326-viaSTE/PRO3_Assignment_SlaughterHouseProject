package org.example.station3_productregistration.repository;

import org.example.station3_productregistration.model.ProductTray;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTrayRepository extends JpaRepository<ProductTray, Integer> {
    List<ProductTray> findByProduct_ProductId(Integer productId);
}
