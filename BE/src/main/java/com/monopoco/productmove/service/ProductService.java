package com.monopoco.productmove.service;

import com.monopoco.productmove.entityDTO.ProductDTO;

public interface ProductService {
    public ProductDTO save(ProductDTO productDTO);

    public ProductDTO getById(Long id);
}
