package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.BranchDTO;
import com.monopoco.productmove.requestentity.BranchRequestForm;
import com.monopoco.productmove.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/branches")
//@CrossOrigin()
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addNewBranch(@RequestBody BranchRequestForm request) {
        log.info("{}", request.toString());
        BranchDTO branchDTO = modelMapper.map(request, BranchDTO.class);
        BranchDTO branchDTOSaved = branchService.save(branchDTO);
        if (branchDTOSaved != null) {
            return ResponseEntity.status(HttpStatus.OK).body(branchDTOSaved);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add new branch fail");
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUserToBranch(@RequestParam String username, @RequestParam(name = "branch_name") String branchName) {
        Boolean addSuccess = branchService.addUserToBranch(username, branchName);
        if (addSuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("Add successfully");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Add Failed");
        }

    }

    @GetMapping("")
    public List<BranchDTO> getAllBranch() {
        List<BranchDTO> branchDTOS = branchService.findAllBranch();
        return branchDTOS;
    }

    @GetMapping("/{type}")
    public List<BranchDTO> getBranchesByType(@PathVariable String type) {
        return branchService.findBranchesByBranchType(type);
    }
}
