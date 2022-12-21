package com.monopoco.productmove.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User extends EntityAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private ImageData imageData;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonBackReference
    private Branch branch;



}
