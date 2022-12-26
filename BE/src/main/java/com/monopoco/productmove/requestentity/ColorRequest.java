package com.monopoco.productmove.requestentity;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ColorRequest {
    private Map<String, String> colors;
}
