package com.monopoco.productmove.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(name = "image_data", length = 1000)
    @Lob
    private byte[] imageData;

    @OneToOne(mappedBy = "imageData")
    private User user;

}
