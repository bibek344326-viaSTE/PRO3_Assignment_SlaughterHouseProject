package org.example.station3_productregistration.repository;


import org.example.station3_productregistration.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
}
