package com.monopoco.productmove.entityDTO;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.monopoco.productmove.entity.ProductModel;
import com.monopoco.productmove.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String productName;

    private String productModel;

    private Long warehouseId;

}
