package com.monopoco.productmove.entityDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDTO {
    private Long id;

    private String name;

    private String address;
    private List<ProductDTO> productDTOS;

    private int totalProduct;

    private String branchName;
}
