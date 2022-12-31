package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.*;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.repository.*;
import com.monopoco.productmove.service.DistributorService;
import com.monopoco.productmove.util.UtilMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional

@Slf4j
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
    private DistributorHistoryRepository distributorHistoryRepository;

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

    @Override
    public boolean isUnderWarranty(String serial) {
        Product product = productRepository.findProductBySerial(serial);
        if (product == null) {
            return false;
        }
        if (product.getProductStatus() == ProductStatus.SOLD) {
            TransactionHistory transactionHistory =
                    transactionHistoryRepository.findFirstBySerial(serial);
            Date activeDate = transactionHistory.getCreatedAt();
            Date now = java.sql.Date.valueOf(LocalDate.now());
            Long diffInMillies = now.getTime() - activeDate.getTime();
            log.info(diffInMillies.toString());
            Long duration = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return duration <= 365 * 2;
        }
        return false;
    }

    @Override
    public Map<String, String> getDetailProductSold(String serial) {
        Map<String, String> response = new HashMap<>();
        TransactionHistory transactionHistory = transactionHistoryRepository.findFirstBySerial(serial);
        Product product = productRepository.findProductBySerial(serial);
        if (transactionHistory != null && product != null) {
            response.put("Model", product.getProductModel().getModel());
            response.put("Customer name", transactionHistory.getCustomerName());
            response.put("Customer phone number", transactionHistory.getCustomerPhone());
            DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            String createdAt = formatter.format(transactionHistory.getCreatedAt());
            response.put("Purchase date", createdAt);
            response.put("Purchase at", transactionHistory.getDistributorAgent().getName());
            String underWarraty = isUnderWarranty(serial) ? "true" : "false";
            response.put("Is under Warranty", underWarraty);
        }
        return response;
    }

    @Override
    public ProductDTO takeProductWarranty(String serial, String description, String warehouseName) {
        Product product = productRepository.findProductBySerial(serial);
        DistributorHistory distributorHistory = new DistributorHistory();
        if (product != null) {
            product.setProductStatus(ProductStatus.ERROR_NEED_WARRANTY);
            Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByName(warehouseName);
            if (warehouse.isPresent()) {
                product.setWarehouse(warehouse.get());
                product.setDistribution(warehouse.get().getBranch());
            }

            distributorHistory.setProduct(product);
            distributorHistory.setDistributorAgent(warehouse.get().getBranch());
            distributorHistory.setDescription(description);
            DistributorHistory distributorHistorySaved =
                    distributorHistoryRepository.save(distributorHistory);

            return UtilMapper.mapToProductDTO(product, modelMapper);
        }

        return null;
    }

    @Override
    public Map<String,Integer> getProductsSoldStatistical(Date from, Date to) {
        Branch distributor = getDistributor();

        Map<String,Integer> response = new HashMap<>();

        List<TransactionHistory> transactionHistoryList =
                transactionHistoryRepository.findTransactionHistoriesByDistributorAgent_NameAndCreatedAtBetween(
                        distributor.getName(),
                        from,
                        to
                );
        if (transactionHistoryList != null) {
            response.put("Total", transactionHistoryList.size());
            transactionHistoryList.forEach(transactionHistory -> {
                Product product = productRepository.findProductBySerial(transactionHistory.getSerial());
                response.computeIfPresent(product.getProductModel().getModel(), (key, val) -> val + 1);
                response.putIfAbsent(product.getProductModel().getModel(), 1);
            });
        }
        return response;
    }

    public Branch getDistributor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByUsername(authentication.getName()).getBranch();
    }

    @Override
    public List<ProductDTO> getAllProductWarrantyInWarehouse() {
        Branch distributor = getDistributor();
        List<Product> productList =
                productRepository.findProductsByDistribution_NameAndProductStatus(
                        distributor.getName(),
                        ProductStatus.ERROR_NEED_WARRANTY
                );
        List<ProductDTO> productDTOList = new ArrayList<>();

        productList.forEach(product ->  {

            ProductDTO productDTO = UtilMapper.mapToProductDTO(product, modelMapper);
            productDTO.setDescription(getDescriptionWithSerial(productDTO.getSerial()));
            productDTOList.add(productDTO);
        });


        return productDTOList;
    }

    @Override
    public String getDescriptionWithSerial(String serial) {
        DistributorHistory distributorHistory = distributorHistoryRepository.findFirstByProduct_Serial(serial);
        if (distributorHistory != null) {
            return distributorHistory.getDescription();
        }

        return null;
    }

    @Override
    public int sendErrorProductsToWarranty(String warranty, List<String> serials) {
        Optional<Branch> warrantyBranch = branchRepository.findBranchByName(warranty);
        if (warrantyBranch.isPresent()) {
            serials.forEach((serial -> {
                Product errorProduct = productRepository.findProductBySerial(serial);
                if (errorProduct.getProductStatus() == ProductStatus.ERROR_NEED_WARRANTY) {
                    errorProduct.setWarranty(warrantyBranch.get());
                    errorProduct.setProductStatus(ProductStatus.UNDER_WARRANTY);
                    errorProduct.setWarehouse(null);
                }
            }));
            return serials.size();
        }

        return 0;
    }

    @Override
    public List<ProductDTO> getAllProductWarrantyDone() {
        List<Product> productList = productRepository.findProductsByDistribution_NameAndProductStatus(getDistributor().getName(), ProductStatus.WARRANTY_DONE);
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
    public ProductDTO returnCustomer(String serial) {
        Product product = productRepository.findProductBySerial(serial);
        product.setProductStatus(ProductStatus.WARRANTY_RETURNED_TO_CUSTOMER);
        product.setWarranty(null);
        product.setWarehouse(null);
        product.setDistribution(null);
        return UtilMapper.mapToProductDTO(product, modelMapper);
    }
}
