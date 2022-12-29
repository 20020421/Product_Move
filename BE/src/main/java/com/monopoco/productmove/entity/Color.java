package com.monopoco.productmove.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "color")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Color extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color", unique = true)
    private String color;

    @Column(name = "code", unique = true)
    private String code;

    @ManyToMany(mappedBy = "color")
    private Set<ProductModel> productModels;

    @OneToMany(mappedBy = "color")
    private List<Product> products;
}
