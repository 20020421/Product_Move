package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Page<Warehouse> findWarehousesByBranch_Name(Pageable pageable, String brandName);

    Optional<Warehouse> findWarehouseByName(String warehouseName);
}
