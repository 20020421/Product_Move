package com.monopoco.productmove.service;

import com.monopoco.productmove.entity.Warehouse;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.ProductModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public ProductDTO save(ProductDTO productDTO, Warehouse warehouse);

    public ProductDTO getById(Long id);

    public void addNewColor(String color, String code);

    public void addNewCapacity(Integer capacity);

    public Map<String, String> getAllColor();

    public List<Integer> getAllCapacity();

    public ProductModelDTO saveNewProductModel(ProductModelDTO productModelDTO);

    public Page<ProductModelDTO> getAllModel(Pageable pageable);

    public List<Map<String, String>> getColor(Long id);

    public Map<String, String> getColor(String model);

    public void addNewColor(Map<String, String> colors);

    public List<ProductDTO> getProductsByWarehouse(String warehouse);

    public Map<String, String> getColorByName(String color);

    public Map<String, Integer> statisticalProduct();

}
