package com.monopoco.productmove.service;

import com.monopoco.productmove.entityDTO.ProductDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface DistributorService {
    public List<ProductDTO> getAllProductComing();

    public Map<String, List<ProductDTO>> getProductsComingByFactoty();

    public int warehousing(String warehouseName);

    public int warehousingWithFactory(String warehouseName, String factoryName);

    public boolean addPayment(String customerName, String customerPhone, String productSerial);

    public ProductDTO getProductBySerial(String serial);

    public List<ProductDTO> getProductsSold(Date from, Date to);

    public List<String> getAllSerial();
}
