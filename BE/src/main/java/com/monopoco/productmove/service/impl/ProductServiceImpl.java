package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.Product;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.repository.ProductRepository;
import com.monopoco.productmove.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product productSaved = productRepository.save(product);
        if (productSaved != null && productSaved.getId() > 0) {
            return modelMapper.map(productSaved, ProductDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public ProductDTO getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            if (product.get().getWarehouse() != null) {
                productDTO.setWarehouseId(product.get().getWarehouse().getId());
            }

            return productDTO;
        }
        return null;
    }
}
