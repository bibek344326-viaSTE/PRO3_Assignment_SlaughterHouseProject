package org.example.station3_productregistration.repository;


import org.example.station3_productregistration.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
