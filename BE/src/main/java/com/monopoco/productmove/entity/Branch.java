package com.monopoco.productmove.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    @JsonBackReference
    private BranchType branchType;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<User> users;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Warehouse> warehouses;

    @OneToMany(mappedBy = "factory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> productsManufactured;

    @OneToMany(mappedBy = "distribution", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> productsAllocated;

    @OneToMany(mappedBy = "warranty", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> productsWarrantied;

    @OneToMany(mappedBy = "distributorAgent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TransactionHistory> transactionHistories;

    @OneToMany(mappedBy = "factory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FactoryHistory> factoryHistories;



}
