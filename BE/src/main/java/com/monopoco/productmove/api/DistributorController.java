package com.monopoco.productmove.api;

import com.monopoco.productmove.entity.Product;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.requestentity.*;
import com.monopoco.productmove.service.DistributorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/distributors")
@Slf4j
public class DistributorController {

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/warehousing")
    public ResponseEntity<?> getProductComing() {
        Map<String,List<ProductDTO>> response = distributorService.getProductsComingByFactoty();
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @PostMapping("/warehousing")
    public ResponseEntity<?> warehousing(@RequestBody DistributorWarehousingRequest request) {
        log.info(request.toString());
        int response = 0;
        switch (request.getType()) {
            case 0:
                {
                    response = distributorService.warehousing(request.getWarehouse());
                }
                break;
            case 1:
                {
                    response = distributorService.warehousingWithFactory(request.getWarehouse(), request.getFactory());
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

    @PostMapping("/payments")
    public ResponseEntity<?> newPayments(@RequestBody PaymentRequest request) {
        if (distributorService.addPayment(request.getCustomerName(), request.getCustomerPhone(), request.getSerial())) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products/{serial}")
    public ResponseEntity<?> getProductsBySerial(@PathVariable String serial) {
        ProductDTO productDTO = distributorService.getProductBySerial(serial);
        if (productDTO != null) {
            return  ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/statistical/sold")
    public ResponseEntity<?> getProductsSoldStatistical(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to
    ) {
        log.info(from.toString());
        log.info(to.toString());
        Map<String,Integer> response = distributorService.getProductsSoldStatistical(from, to);

        return ResponseEntity.ok().body(response    );
    }

    @GetMapping("/serials")
    public ResponseEntity<?> getAllSerial() {
        return ResponseEntity.ok(distributorService.getAllSerial());
    }

    @PostMapping("/check_warranty")
    public ResponseEntity<?> checkUnderWarranty(@RequestBody CheckWarrantyRequest request) {
        return ResponseEntity.ok(distributorService.isUnderWarranty(request.getSerial()));
    }

    @GetMapping("/products/sold/{serial}")
    public ResponseEntity<Map<String, String>> getInfoProductSold(@PathVariable String serial) {
        log.info(serial);
        Map<String, String> response = distributorService.getDetailProductSold(serial);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/warranties")
    public ResponseEntity<?> addWarranty(@RequestBody AddWarrantyRequest request) {
        ProductDTO productDTO = distributorService.takeProductWarranty(request.getSerial(), request.getDescription(), request.getWarehouse());
        if (productDTO != null) {
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/warranties/products")
    public ResponseEntity<?> getAllProductNeedWarranty() {
        return ResponseEntity.ok(distributorService.getAllProductWarrantyInWarehouse());
    }

    @GetMapping("/warranties/products/done")
    public ResponseEntity<?> getAllProductWarrantyDone() {
        return ResponseEntity.ok(distributorService.getAllProductWarrantyDone());
    }

    @PostMapping("/warranties/return_customer")
    public ResponseEntity<?> warrantyReturnCustomer(@RequestBody WarrantyDoneRequest request) {
        return ResponseEntity.ok(distributorService.returnCustomer(request.getSerial()));
    }



    @GetMapping("/warranties/products/{serial}")
    public ResponseEntity<?> getDescriptionBySerial(@PathVariable String serial) {
        String response = distributorService.getDescriptionWithSerial(serial);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/warranties/send")
    public ResponseEntity<?> sendErrorToWarranty(@RequestBody SendErrorProductToWarrantyRequest request) {
        int response = distributorService.sendErrorProductsToWarranty(request.getWarranty(), request.getSerials());
        if (response > 0) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
