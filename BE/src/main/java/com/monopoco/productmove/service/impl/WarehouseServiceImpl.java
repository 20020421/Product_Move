package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.Product;
import com.monopoco.productmove.entity.Warehouse;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.WarehouseDTO;
import com.monopoco.productmove.repository.ProductRepository;
import com.monopoco.productmove.repository.WarehouseRepository;
import com.monopoco.productmove.service.WareHouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WarehouseServiceImpl implements WareHouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WarehouseDTO save(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = modelMapper.map(warehouseDTO, Warehouse.class);
        Warehouse warehouseSaved = warehouseRepository.save(warehouse);
        if (warehouseSaved != null && warehouseSaved.getId() > 0) {
            return modelMapper.map(warehouseSaved, WarehouseDTO.class);
        }
        return null;
    }

    @Override
    public void addProductToWarehouse(Long productId, Long warehouseId) {
        Optional<Product> _product = productRepository.findById(productId);
        Optional<Warehouse> _warehouse = warehouseRepository.findById(warehouseId);
        if (_product.isPresent() && _warehouse.isPresent()) {
            Warehouse warehouse = _warehouse.get();
            Product product = _product.get();
            product.setWarehouse(warehouse);
        }
    }

    @Override
    public List<ProductDTO> getAllProductFromWarehouse(Long warehouseId) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseGet = warehouse.get();
            List<ProductDTO> productDTOList = new ArrayList<>();
            warehouseGet.getProducts().forEach(product -> {
                ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                productDTOList.add(productDTO);
            });

            return productDTOList;
        }

        return null;
    }
}
