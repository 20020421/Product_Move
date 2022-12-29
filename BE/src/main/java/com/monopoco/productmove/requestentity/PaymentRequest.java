package com.monopoco.productmove.requestentity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String customerName;
    private String customerPhone;

    private String serial;

}
