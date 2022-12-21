package com.monopoco.productmove.requestentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToWarehouseForm {

    private Long warehouseId;

    private Long productId;

}
