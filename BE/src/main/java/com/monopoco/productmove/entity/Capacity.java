package com.monopoco.productmove.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "capacities")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Capacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity", unique = true)
    private Integer capacity;

    @ManyToMany(mappedBy = "capacities")
    private Set<ProductModel> productModels;

    @OneToMany(mappedBy = "capacity")
    private List<Product> products;


}
