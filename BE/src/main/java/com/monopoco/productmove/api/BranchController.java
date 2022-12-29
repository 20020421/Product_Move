package com.monopoco.productmove.api;

import com.monopoco.productmove.entity.Branch;
import com.monopoco.productmove.entityDTO.BranchDTO;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.requestentity.BranchRequestForm;
import com.monopoco.productmove.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> addNewBranch(@RequestBody BranchRequestForm request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getAuthorities().toString());
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

    @GetMapping("/names")
    public List<String> getAllBranchName() {
        return branchService.getAllBranchName();
    }

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductDTO> productDTOPage = branchService.getAllProduct(pageable);
        List<ProductDTO> productDTOList = productDTOPage.getContent();
        Map<String, Object> response = new HashMap<>();

        response.put("products", productDTOList);
        response.put("currentPage", productDTOPage.getNumber());
        response.put("totalItems", productDTOPage.getTotalElements());
        response.put("totalPages", productDTOPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);


    }
}
