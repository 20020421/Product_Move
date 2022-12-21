package com.monopoco.productmove.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {
    private Long id;
    private String name;
    private String address;

    private String branchTypeName;

    private List<Long> userIdList;

}
