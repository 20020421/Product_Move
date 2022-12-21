package com.monopoco.productmove.service;

import com.monopoco.productmove.entity.BranchType;
import com.monopoco.productmove.entityDTO.BranchDTO;

public interface BranchService {
    public BranchDTO save(BranchDTO branchDTO);

    public BranchDTO findById(Long id);

    public Boolean addUserToBranch(String userName, String brandName);

    public void addBranchType(BranchType branchType);

}
