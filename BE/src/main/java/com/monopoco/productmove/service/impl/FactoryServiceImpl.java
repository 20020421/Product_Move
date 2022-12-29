package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.Branch;
import com.monopoco.productmove.entity.Product;
import com.monopoco.productmove.entity.ProductStatus;
import com.monopoco.productmove.repository.BranchRepository;
import com.monopoco.productmove.repository.ProductRepository;
import com.monopoco.productmove.service.FactoryService;
import com.monopoco.productmove.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public int exportProduct(String distributorAgentName, List<String> productSerial) {

        Optional<Branch> distributor = branchRepository.findBranchByName(distributorAgentName);
        productSerial.forEach(serial -> {
            Product product = productRepository.findProductBySerial(serial);
            if (product != null) {
                product.setProductStatus(ProductStatus.COMING_DISTRIBUTION);
                distributor.ifPresent(product::setDistribution);
            }
        });
        return productSerial.size();
    }
}
