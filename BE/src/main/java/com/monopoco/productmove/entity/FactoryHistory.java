package com.monopoco.productmove.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "factory_history")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FactoryHistory extends EntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factory_id")
    @JsonBackReference
    private Branch factory;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    @JsonBackReference
    private HistoryType historyType;

    @Column(name = "quantity")
    private Integer quantity;

}
