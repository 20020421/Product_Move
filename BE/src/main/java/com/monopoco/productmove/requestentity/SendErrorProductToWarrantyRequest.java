package com.monopoco.productmove.requestentity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendErrorProductToWarrantyRequest {
    private String warranty;

    private List<String> serials;

}
