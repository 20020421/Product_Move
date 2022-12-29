package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.*;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.repository.*;
import com.monopoco.productmove.service.DistributorService;
import com.monopoco.productmove.util.UtilMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class DistributorServiceImpl implements DistributorService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getAllProductComing() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Branch> distributorOptional = branchRepository.findBranchByName(userRepository.findUserByUsername(authentication.getName()).getBranch().getName());
        if (distributorOptional.isPresent()) {
            List<Product> productList = productRepository.findProductsByDistribution_NameAndProductStatus(distributorOptional.get().getName(), ProductStatus.COMING_DISTRIBUTION);
            List<ProductDTO> productDTOList = new ArrayList<>();
            productList.forEach(product -> {
                productDTOList.add(UtilMapper.mapToProductDTO(product, modelMapper));
            });

            return productDTOList;
        }

        return null;


    }

    @Override
    public Map<String, List<ProductDTO>> getProductsComingByFactoty() {
        List<ProductDTO> productDTOList = getAllProductComing();
        Map<String, List<ProductDTO>> response = new HashMap<>();
        productDTOList.forEach(productDTO -> {
            if (response.containsKey(productDTO.getManufactureAt())) {
                response.get(productDTO.getManufactureAt()).add(productDTO);
            } else {
                List<ProductDTO> values = new ArrayList<>();
                values.add(productDTO);
                response.put(productDTO.getManufactureAt(), values);
            }
        });

        return response;
    }


    @Override
    public int warehousing(String warehouseName) {
        Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByName(warehouseName);
        if (warehouse.isPresent()) {
            List<Product> productList =
                    productRepository.findProductsByDistribution_NameAndProductStatus(
                            warehouse.get().getBranch().getName(),
                            ProductStatus.COMING_DISTRIBUTION);
            productList.forEach(product -> {
                product.setWarehouse(warehouse.get());
                product.setProductStatus(ProductStatus.AT_DISTRIBUTION);
            });
            return productList.size();
        }
        return 0;
    }

    @Override
    public int warehousingWithFactory(String warehouseName, String factoryName) {
        Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByName(warehouseName);
        Optional<Branch> factory = branchRepository.findBranchByName(factoryName);
        if (warehouse.isPresent()  && factory.isPresent()) {
            List<Product> productList =
                    productRepository.findProductsByDistribution_NameAndProductStatusAndFactory_Name(
                            warehouse.get().getBranch().getName(),
                            ProductStatus.COMING_DISTRIBUTION,
                            factory.get().getName());
            productList.forEach(product -> {
                product.setWarehouse(warehouse.get());
                product.setProductStatus(ProductStatus.AT_DISTRIBUTION);
            });
            return productList.size();
        }
        return 0;
    }

    @Override
    public boolean addPayment(String customerName, String customerPhone, String productSerial) {
        Branch distributor = getDistributor();
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setDistributorAgent(distributor);
        transactionHistory.setCustomerName(customerName);
        transactionHistory.setCustomerPhone(customerPhone);
        transactionHistory.setSerial(productSerial);
        TransactionHistory transactionHistorySaved =  transactionHistoryRepository.save(transactionHistory);
        Product product = productRepository.findProductBySerial(productSerial);
        if (product != null) {
            product.setProductStatus(ProductStatus.SOLD);
            product.setWarehouse(null);
            product.setDistribution(null);
            product.setWarranty(null);
            return true;
        } else  {
            return false;
        }


    }

    @Override
    public ProductDTO getProductBySerial(String serial) {
        Branch distribitor = getDistributor();
        Product product = productRepository.findProductBySerial(serial);
        if (product == null) {
            return null;
        }
        if (product.getDistribution().getName().equals(distribitor.getName())) {
            if (product.getProductStatus() == ProductStatus.AT_DISTRIBUTION) {
                return UtilMapper.mapToProductDTO(product, modelMapper);
            }
        }

        return null;
    }

    @Override
    public List<ProductDTO> getProductsSold(Date from, Date to) {
        Branch distributor = getDistributor();
        List<TransactionHistory> transactionHistoryList =
                transactionHistoryRepository.findTransactionHistoriesByDistributorAgent_NameAndCreatedAtBetween(
                        distributor.getName(),
                        from,
                        to
                );
        if (transactionHistoryList != null) {
            List<ProductDTO> productDTOList = new ArrayList<>();
            transactionHistoryList.forEach(transactionHistory -> {
                Product product = productRepository.findProductBySerial(transactionHistory.getSerial());
                productDTOList.add(UtilMapper.mapToProductDTO(product, modelMapper));
            });
            return productDTOList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getAllSerial() {

        Branch distributor = getDistributor();
        if (distributor != null) {
            List<Product> productList = productRepository.findProductsByDistribution_NameAndProductStatus(
                    distributor.getName(),
                    ProductStatus.AT_DISTRIBUTION
            );
            List<String> serialList = new ArrayList<>();
            productList.forEach(product -> {
                serialList.add(product.getSerial());
            });

            return serialList;
        }
        return null;
    }

    public Branch getDistributor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByUsername(authentication.getName()).getBranch();
    }

}
