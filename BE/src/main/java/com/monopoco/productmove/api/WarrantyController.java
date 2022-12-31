package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.requestentity.DistributorWarehousingRequest;
import com.monopoco.productmove.requestentity.WarrantyDoneRequest;
import com.monopoco.productmove.requestentity.WarrantyWarehousingRequest;
import com.monopoco.productmove.service.WarrantyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/warranties")
@Slf4j
public class WarrantyController {

    @Autowired
    private WarrantyService warrantyService;

    @GetMapping("/products/coming")
    public ResponseEntity<Map<String, List<ProductDTO>>> getAllProductComing() {
        return ResponseEntity.ok(warrantyService.getProductComingWarranty());
    }

    @PostMapping("/warehousing")
    public ResponseEntity<?> warehousing(@RequestBody WarrantyWarehousingRequest request) {
        log.info(request.toString());
        int response = 0;
        switch (request.getType()) {
            case 0:
            {
                response = warrantyService.warehousing(request.getWarehouse());
            }
            break;
            case 1:
            {
                response = warrantyService.warehousingWithDistributor(request.getWarehouse(), request.getDistributor());
            }
            break;
            default:
                break;
        }
        log.info(request.getWarehouse());

        if (response > 0) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProductAtWarranty() {
        return ResponseEntity.ok(warrantyService.getAllProductWarranty());
    }

    @PostMapping("/products/warranty_done")
    public ResponseEntity<?> warrantyDone(@RequestBody WarrantyDoneRequest request) {
        return ResponseEntity.ok(warrantyService.doneWarranty(request.getSerial()));
    }


}
