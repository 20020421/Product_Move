package com.monopoco.productmove.entityDTO;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.monopoco.productmove.entity.ProductModel;
import com.monopoco.productmove.entity.Warehouse;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String serial;

    private String status;

    private String productModelName;

    private String createdAt;

    private String updateAt;

    private String description;
    private String colorString;

    private Integer capacityInt;

    private String manufactureAt;

    private String distributionAt;

    private String warrantyAt;

    private String warehouseName;

}
