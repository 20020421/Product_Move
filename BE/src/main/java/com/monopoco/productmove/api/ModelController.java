package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.ProductModelDTO;
import com.monopoco.productmove.requestentity.CapacityRequest;
import com.monopoco.productmove.requestentity.ColorRequest;
import com.monopoco.productmove.requestentity.ModelRequest;
import com.monopoco.productmove.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/models")
public class ModelController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllModel(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductModelDTO> productModelDTOPage = productService.getAllModel(pageable);
        List<ProductModelDTO> productModelDTOList = productModelDTOPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("models", productModelDTOList);
        response.put("currentPage", productModelDTOPage.getNumber());
        response.put("totalItems", productModelDTOPage.getTotalElements());
        response.put("totalPages", productModelDTOPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/colors")
    public ResponseEntity<?> addColor(@RequestBody ColorRequest request) {
        log.info(request.toString());
        productService.addNewColor(request.getColors());
        return ResponseEntity.ok(request);
    }

    @GetMapping("/colors")
    public ResponseEntity<?> getAllColor() {
        Map<String, String> colors = productService.getAllColor();
        return ResponseEntity.status(HttpStatus.OK).body(colors);
    }

    @GetMapping("/{id}/colors")
    public ResponseEntity<?> getColorCode(@PathVariable Long id) {
        List<Map<String, String>> colorCode = productService.getColor(id);
        return ResponseEntity.status(HttpStatus.OK).body(colorCode);
    }

    @PostMapping("/capacities")
    public ResponseEntity<?> addCapacity(@RequestBody CapacityRequest request) {
        productService.addNewCapacity(request.getCapacity());
        return ResponseEntity.ok(request);
    }

    @GetMapping("/capacities")
    public ResponseEntity<?> getAllCapacity() {
        List<Integer> capacities = productService.getAllCapacity();
        return ResponseEntity.status(HttpStatus.OK).body(capacities);
    }

    @PostMapping("")
    public ResponseEntity<?> addNewModel(@RequestBody ModelRequest request) {

        log.info(request.toString());
        ProductModelDTO productModelDTO = modelMapper.map(request, ProductModelDTO.class);
        ProductModelDTO saved = productService.saveNewProductModel(productModelDTO);
        if (saved.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Add Fail");
        }
    }

}
