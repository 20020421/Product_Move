package com.monopoco.productmove.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transaction_type")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "transactionType")
    private List<TransactionHistory> transactionHistoryList;

}
