package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.WarehouseDTO;
import com.monopoco.productmove.requestentity.AddProductToWarehouseForm;
import com.monopoco.productmove.requestentity.WarehouseRequestForm;
import com.monopoco.productmove.service.BranchService;
import com.monopoco.productmove.service.WareHouseService;
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
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/warehouses")
@Slf4j
public class WarehouseController {

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> addNewWarehouse(@RequestBody WarehouseRequestForm warehouseRequestForm) {
        WarehouseDTO warehouseDTO = modelMapper.map(warehouseRequestForm, WarehouseDTO.class);
        WarehouseDTO warehouseDTOSaved = branchService.saveNewWarehouse(warehouseDTO);
        if (warehouseDTOSaved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(warehouseDTOSaved);
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add new warehouse failed");
        }
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getWarehouse(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<WarehouseDTO> warehouseDTOPage = branchService.getWarehouses(pageable);
        List<WarehouseDTO> warehouseDTOList = warehouseDTOPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("warehouses", warehouseDTOList);
        response.put("currentPage", warehouseDTOPage.getNumber());
        response.put("totalItems", warehouseDTOPage.getTotalElements());
        response.put("totalPages", warehouseDTOPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/products")
    public ResponseEntity<?> addProductToWareHouse(@ModelAttribute AddProductToWarehouseForm request) {
        Long warehouseId = request.getWarehouseId();
        Long productId = request.getProductId();
        wareHouseService.addProductToWarehouse(productId, warehouseId);
        return ResponseEntity.status(HttpStatus.OK).body("Add Product To Warehouse successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducts(@PathVariable Long id) {
        log.info("id: {}", id);
        List<ProductDTO> productDTOList = wareHouseService.getAllProductFromWarehouse(id);
        if (productDTOList != null) {
            return  ResponseEntity.status(HttpStatus.FOUND).body(productDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("emty");
        }

    }

}
