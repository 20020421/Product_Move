package com.monopoco.productmove.entityDTO;

import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductModelDTO {
    private Long id;
    private String model;
    private String chip;
    private List<String> colorString;
    private List<Integer> capacityList;
}
