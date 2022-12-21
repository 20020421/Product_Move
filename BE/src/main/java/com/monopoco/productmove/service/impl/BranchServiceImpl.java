package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.Branch;
import com.monopoco.productmove.entity.BranchType;
import com.monopoco.productmove.entityDTO.BranchDTO;
import com.monopoco.productmove.repository.BranchRepository;
import com.monopoco.productmove.repository.BranchTypeRepository;
import com.monopoco.productmove.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchTypeRepository branchTypeRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BranchDTO save(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
        if (branchDTO.getBranchTypeName() != null) {
            Optional<BranchType> optionalBranchType = branchTypeRepository.findBranchTypeByTypeName(branchDTO.getBranchTypeName());
            if (optionalBranchType.isPresent()) {
                BranchType branchType = optionalBranchType.get();
                branch.setBranchType(branchType);
            }
        }

        Branch branchSaved = branchRepository.save(branch);
        if (branchSaved != null) {
            BranchDTO returnBranchDTO = modelMapper.map(branchSaved, BranchDTO.class);
            if (branchSaved.getBranchType() != null) {
                returnBranchDTO.setBranchTypeName(branchSaved.getBranchType().getTypeName());
            }
            return returnBranchDTO;
        }
        return null;
    }

    @Override
    public BranchDTO findById(Long id) {
        return null;
    }

    @Override
    public Boolean addUserToBranch(String userName, String brandName) {
        return null;
    }

    @Override
    public void addBranchType(BranchType branchType) {
        log.info("adding new branch type: {}", branchType.getTypeName());
        BranchType branchTypeSaved = branchTypeRepository.save(branchType);
    }
}
