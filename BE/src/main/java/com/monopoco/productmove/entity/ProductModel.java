package com.monopoco.productmove.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "product_models")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_model_name")
    private String productModelName;

    @OneToMany(mappedBy = "productModel")
    private List<Product> products;

}
