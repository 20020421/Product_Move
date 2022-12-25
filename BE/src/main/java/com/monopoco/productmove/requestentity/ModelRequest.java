package com.monopoco.productmove.requestentity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequest {

    private String model;
    private String chip;
    private List<String> colorString;
    private List<Integer> capacityList;

}
