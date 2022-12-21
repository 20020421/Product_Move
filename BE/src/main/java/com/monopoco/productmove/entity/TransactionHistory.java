package com.monopoco.productmove.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "transaction_histoty")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_type")
    @JsonBackReference
    private TransactionType transactionType;



}
