package com.monopoco.productmove.service;

import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.WarehouseDTO;

import java.util.List;

public interface WareHouseService {

    public WarehouseDTO save(WarehouseDTO warehouseDTO);

    public void  addProductToWarehouse(Long productId, Long warehouseId);

    public List<ProductDTO> getAllProductFromWarehouse(Long warehouseId);


}
