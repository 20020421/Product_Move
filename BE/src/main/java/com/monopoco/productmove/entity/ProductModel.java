package com.monopoco.productmove.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "product_models")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model", unique = true)
    private String model;

    @Column(name = "chip")
    private String chip;

    @ManyToMany
    @JoinTable(
            name = "model_color",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")

    )
    private Set<Color> color;

    @ManyToMany
    @JoinTable(
            name = "model_capacity",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "capacity_id")
    )
    private Set<Capacity> capacities;

    @OneToMany(mappedBy = "productModel")
    private List<Product> products;

}
