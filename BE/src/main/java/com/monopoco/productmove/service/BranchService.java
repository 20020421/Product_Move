package com.monopoco.productmove.service;

import com.monopoco.productmove.entity.BranchType;
import com.monopoco.productmove.entityDTO.BranchDTO;

import java.util.List;

public interface BranchService {
    public BranchDTO save(BranchDTO branchDTO);

    public BranchDTO findById(Long id);

    public List<BranchDTO> findAllBranch();

    public List<BranchDTO> findBranchesByBranchType(String branchType);

    public Boolean addUserToBranch(String userName, String brandName);

    public List<String> getAllBranchName();

    public void addBranchType(BranchType branchType);

}
