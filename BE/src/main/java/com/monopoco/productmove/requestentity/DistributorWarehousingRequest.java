package com.monopoco.productmove.requestentity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DistributorWarehousingRequest {

    private int type;
    private String warehouse;

    private String factory;
}
