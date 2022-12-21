package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.BranchDTO;
import com.monopoco.productmove.requestentity.BranchRequestForm;
import com.monopoco.productmove.service.BranchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> addNewBranch(@ModelAttribute BranchRequestForm request) {
        BranchDTO branchDTO = modelMapper.map(request, BranchDTO.class);
        BranchDTO branchDTOSaved = branchService.save(branchDTO);
        if (branchDTOSaved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(branchDTOSaved);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add new branch fail");
        }


    }
}
