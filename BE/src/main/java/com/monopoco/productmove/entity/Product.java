package com.monopoco.productmove.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Table(name = "products")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial", unique = true)
    private String serial;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus productStatus;


    @ManyToOne
    @JoinColumn(name = "manufacture_at")
    @JsonBackReference
    private Branch factory;

    @ManyToOne
    @JoinColumn(name = "color_id")
    @JsonBackReference
    private Color color;

    @ManyToOne
    @JoinColumn(name = "capacity_id")
    @JsonBackReference
    private Capacity capacity;


    @ManyToOne
    @JoinColumn(name = "distribution_at")
    @JsonBackReference
    private Branch distribution;

    @ManyToOne
    @JoinColumn(name = "warranty_at")
    @JsonBackReference
    private Branch warranty;





    @ManyToOne
    @JoinColumn(name = "product_model")
    @JsonBackReference
    private ProductModel productModel;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @JsonManagedReference
    private Warehouse warehouse;

}
