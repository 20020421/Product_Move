package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.Branch;
import com.monopoco.productmove.entity.BranchType;
import com.monopoco.productmove.entity.Role;
import com.monopoco.productmove.entity.User;
import com.monopoco.productmove.entityDTO.BranchDTO;
import com.monopoco.productmove.entityDTO.UserDTO;
import com.monopoco.productmove.repository.BranchRepository;
import com.monopoco.productmove.repository.BranchTypeRepository;
import com.monopoco.productmove.repository.RoleRepository;
import com.monopoco.productmove.repository.UserRepository;
import com.monopoco.productmove.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BranchDTO save(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
//        Boolean isExisted = branchRepository.findBranchByName(branchDTO.getName()) != null;

        if (branchDTO.getType() != null) {
            Optional<BranchType> optionalBranchType = branchTypeRepository.findBranchTypeByTypeName(branchDTO.getType());
            if (optionalBranchType.isPresent()) {
                BranchType branchType = optionalBranchType.get();
                branch.setBranchType(branchType);
            }
        }



        Branch branchSaved = branchRepository.save(branch);
        if (branchSaved != null) {
            BranchDTO returnBranchDTO = modelMapper.map(branchSaved, BranchDTO.class);
            if (branchSaved.getBranchType() != null) {
                returnBranchDTO.setType(branchSaved.getBranchType().getTypeName());
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
    public List<BranchDTO> findAllBranch() {

        List<Branch> branches = branchRepository.findAll();
        List<BranchDTO> branchDTOS = new ArrayList<>();
        branches.forEach(branch -> {
            branchDTOS.add(mapBranch(branch));
        });


        return branchDTOS;
    }

    public BranchDTO mapBranch(Branch branch) {
        List<String> users = new ArrayList<>();
        branch.getUsers().forEach(user -> {
            users.add(user.getUsername());
        });
        BranchDTO branchDTO = modelMapper.map(branch, BranchDTO.class);
        branchDTO.setUsersName(users);
        branchDTO.setType(branch.getBranchType().getTypeName());
        return branchDTO;
    }

    @Override
    public List<BranchDTO> findBranchesByBranchType(String branchType) {
        List<Branch> branches = branchRepository.findBranchesByBranchType_TypeName(branchType);
        List<BranchDTO> branchDTOList = new ArrayList<>();
        if (branches.size() > 0) {
            branches.forEach(branch -> {
                branchDTOList.add(mapBranch(branch));
            });
        }
        return branchDTOList;
    }

    @Override
    public Boolean addUserToBranch(String userName, String brandName) {
        User user = userRepository.findUserByUsername(userName);
        Optional<Branch> optionalBranch = branchRepository.findBranchByName(brandName);
        if (optionalBranch.isPresent() && user != null) {
            String roleName = "";
            Branch branch = optionalBranch.get();
            switch (branch.getBranchType().getTypeCode()) {
                case "FTR":
                    roleName = "FACTORY";
                    break;
                case "DIA":
                    roleName = "DISTRIBUTOR";
                    break;
                case "WAC":
                    roleName = "WARRANTY";
                    break;
                default:

            }
            Role role = roleRepository.findRoleByName(roleName);
            user.setBranch(branch);
            user.setRole(role);


            log.info("adding username {} to branch {}", userName, brandName);
            return true;
        }

        return false;

    }

    @Override
    public List<String> getAllBranchName() {

        List<Branch> branches = branchRepository.findAll();
        List<String> branchesName = new ArrayList<>();
        if (branches.size() > 0) {
            branches.forEach(branch -> {
                branchesName.add(branch.getName());
            });
        }

        return branchesName;
    }

    @Override
    public void addBranchType(BranchType branchType) {
        log.info("adding new branch type: {}", branchType.getTypeName());
        BranchType branchTypeSaved = branchTypeRepository.save(branchType);
    }
}
