package com.monopoco.productmove.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.monopoco.productmove.entityDTO.ProductDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "distributor_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DistributorHistory extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    @JsonBackReference
    private Branch distributorAgent;




}
