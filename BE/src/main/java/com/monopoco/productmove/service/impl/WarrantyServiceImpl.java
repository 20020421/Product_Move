package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.*;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.repository.*;
import com.monopoco.productmove.service.WarrantyService;
import com.monopoco.productmove.util.UtilMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class WarrantyServiceImpl implements WarrantyService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private DistributorHistoryRepository distributorHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Map<String, List<ProductDTO>> getProductComingWarranty() {

        Branch warranty = getWarrantyBranch();
        List<Product> productList = productRepository.findProductsByWarranty_NameAndWarehouseIsNull(warranty.getName());
        Map<String, List<ProductDTO>> response = new HashMap<>();
        productList.forEach(product -> {
            ProductDTO productDTO = UtilMapper.mapToProductDTO(product, modelMapper);
            DistributorHistory distributorHistory = distributorHistoryRepository.findFirstByProduct_Serial(product.getSerial());
            productDTO.setDescription(distributorHistory.getDescription());
            response.computeIfAbsent(product.getDistribution().getName(), k -> new ArrayList<>()).add(productDTO);
        });


        return response;
    }

    @Override
    public int warehousing(String warehouseName) {
        Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByName(warehouseName);
        if (warehouse.isPresent()) {
            List<Product> productList = productRepository.findProductsByWarranty_NameAndWarehouseIsNull(warehouse.get().getBranch().getName());
            productList.forEach(product -> {
                product.setWarehouse(warehouse.get());
            });
            return productList.size();
        }
        return 0;
    }

    @Override
    public int warehousingWithDistributor(String warehouseName, String distributor) {
        Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByName(warehouseName);
        Optional<Branch> distributorBranch = branchRepository.findBranchByName(distributor);
        if (warehouse.isPresent()  && distributorBranch.isPresent()) {
            List<Product> productList =
                    productRepository.findProductsByWarranty_NameAndWarehouseIsNullAndDistribution_Name(
                            warehouse.get().getBranch().getName(),
                            distributor
                    );
            productList.forEach(product -> {
                product.setWarehouse(warehouse.get());
            });
            return productList.size();
        }
        return 0;
    }

    @Override
    public List<ProductDTO> getAllProductWarranty() {

        Branch warrantyCenter = getWarrantyBranch();
        List<Product> productList = productRepository.findProductsByWarranty_NameAndWarehouseIsNotNull(warrantyCenter.getName());
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> {
            ProductDTO productDTO = UtilMapper.mapToProductDTO(product, modelMapper);
            DistributorHistory distributorHistory = distributorHistoryRepository.findFirstByProduct_Serial(product.getSerial());
            productDTO.setDescription(distributorHistory.getDescription());
            productDTOList.add(productDTO);
        });

        return productDTOList;
    }

    @Override
    public ProductDTO doneWarranty(String serial) {
        Product product = productRepository.findProductBySerial(serial);
        product.setProductStatus(ProductStatus.WARRANTY_DONE);
        return UtilMapper.mapToProductDTO(product, modelMapper);
    }

    private Branch getWarrantyBranch() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(authentication.getName());
        return user.getBranch();
    }
}
