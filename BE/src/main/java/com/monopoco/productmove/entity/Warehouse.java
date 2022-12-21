package com.monopoco.productmove.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Table(name = "warehouses")
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "warehouse_address")
    private String warehouseAddress;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Product> products;

    @OneToOne(mappedBy = "warehouse")
    private Branch branch;
}
