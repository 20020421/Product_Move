package com.monopoco.productmove.requestentity;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FactoryExportRequest {

    private String distributorName;

    private List<String> productsSerial;
}
