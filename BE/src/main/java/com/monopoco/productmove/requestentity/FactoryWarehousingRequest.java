package com.monopoco.productmove.requestentity;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FactoryWarehousingRequest {
    private String warehouseName;
    private List<ProductRequest> products;
}
