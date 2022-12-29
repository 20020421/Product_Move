package com.monopoco.productmove.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Table(name = "warehouses")
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonBackReference
    private Branch branch;
}
