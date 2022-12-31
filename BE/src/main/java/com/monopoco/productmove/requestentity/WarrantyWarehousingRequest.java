package com.monopoco.productmove.requestentity;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WarrantyWarehousingRequest {
    private int type;
    private String warehouse;

    private String distributor;
}
