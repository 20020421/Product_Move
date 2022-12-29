package com.monopoco.productmove.util;

import com.monopoco.productmove.entity.Product;
import com.monopoco.productmove.entityDTO.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UtilMapper {
    public static ProductDTO mapToProductDTO(Product product, ModelMapper modelMapper) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String createdAt = formatter.format(product.getCreatedAt());
        String updateAt = formatter.format(product.getUpdatedAt());
        productDTO.setUpdateAt(updateAt);
        productDTO.setCreatedAt(createdAt);
        productDTO.setColorString(product.getColor().getColor());
        productDTO.setCapacityInt(product.getCapacity().getCapacity());
        productDTO.setProductModelName(product.getProductModel().getModel());
        productDTO.setStatus(product.getProductStatus().toString());
        productDTO.setManufactureAt(product.getFactory().getName());
        if (product.getWarehouse() != null) {
            productDTO.setWarehouseName(product.getWarehouse().getName());
        }
        if (product.getDistribution() != null) {
            productDTO.setDistributionAt(product.getDistribution().getName());
        }

        if (product.getWarranty() != null) {
            productDTO.setWarrantyAt(product.getWarranty().getName());
        }

        return productDTO;
    }
}
