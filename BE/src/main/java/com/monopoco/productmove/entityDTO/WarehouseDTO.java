package com.monopoco.productmove.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDTO {
    private Long id;

    private String warehouseName;

    private String warehouseAddress;
    private List<ProductDTO> productDTOS;
}
