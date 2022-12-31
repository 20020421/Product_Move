package com.monopoco.productmove.service;

import com.monopoco.productmove.entityDTO.ProductDTO;

import java.util.List;
import java.util.Map;

public interface WarrantyService {

    public Map<String, List<ProductDTO>> getProductComingWarranty();


    public int warehousing(String warehouseName);

    public int warehousingWithDistributor(String warehouseName, String distributor);

    public List<ProductDTO> getAllProductWarranty();

    public ProductDTO doneWarranty(String serial);
}
