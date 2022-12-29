package com.monopoco.productmove.requestentity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String colorString;

    private Integer capacityInt;

    private String serial;

    private String productModelName;



}
