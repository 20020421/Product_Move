package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductModelRepository extends JpaRepository<ProductModel, Long> {
    public ProductModel findByModel(String model);
}
