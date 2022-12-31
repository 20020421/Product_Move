package com.monopoco.productmove.requestentity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddWarrantyRequest {

    private String serial;

    private String description;

    private String warehouse;

}
