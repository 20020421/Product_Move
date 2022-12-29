package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.Product;
import com.monopoco.productmove.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByProductModel_Model(String model);

    List<Product> findProductsByWarehouse_Name(String warehouse);

    Product findProductBySerial(String serial);



    List<Product> findProductsByDistribution_NameAndProductStatus(String distributorName, ProductStatus productStatus);

    List<Product> findProductsByDistribution_NameAndProductStatusAndFactory_Name(String distribution_name, ProductStatus productStatus, String factory_name);

}
