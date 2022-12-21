package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByProductModel_ProductModelName(String productModelName);
}
