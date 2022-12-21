package com.monopoco.productmove.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "branch_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", unique = true)
    private String typeName;

    @Column(name = "type_code", unique = true)
    private String typeCode;

    @OneToMany(mappedBy = "branchType", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Branch> branches;


}
