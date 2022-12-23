package com.monopoco.productmove.requestentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequestForm {
    private String name;
    private String address;
    private String phone;
    private String type;
}
