package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.WarehouseDTO;
import com.monopoco.productmove.requestentity.AddProductToWarehouseForm;
import com.monopoco.productmove.requestentity.WarehouseRequestForm;
import com.monopoco.productmove.service.WareHouseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@Slf4j
public class WarehouseController {

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> addNewWarehouse(@ModelAttribute WarehouseRequestForm warehouseRequestForm) {
        WarehouseDTO warehouseDTO = modelMapper.map(warehouseRequestForm, WarehouseDTO.class);
        WarehouseDTO warehouseDTOSaved = wareHouseService.save(warehouseDTO);
        if (warehouseDTOSaved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(warehouseDTOSaved);
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add new warehouse failed");
        }
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
