package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.requestentity.FactoryExportRequest;
import com.monopoco.productmove.requestentity.FactoryWarehousingRequest;
import com.monopoco.productmove.service.BranchService;
import com.monopoco.productmove.service.FactoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/factories")
public class FactoryController {
    @Autowired
    private BranchService branchService;

    @Autowired
    private FactoryService factoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/warehousing")
    public ResponseEntity<?> factoryWarehousing(@RequestBody FactoryWarehousingRequest request) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        request.getProducts().forEach(productRequest -> {
            ProductDTO productDTO = modelMapper.map(productRequest, ProductDTO.class);
            productDTOList.add(productDTO);
        });

        int totalWarehousingPro = branchService.factoryWarehouseing(request.getWarehouseName(), productDTOList);
        if (totalWarehousingPro > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(totalWarehousingPro);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cant warehousing");
        }
    }

    @PostMapping("/export")
    public ResponseEntity<?> exportProducts(@RequestBody FactoryExportRequest request) {
        int count = factoryService.exportProduct(request.getDistributorName(), request.getProductsSerial());
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }


}
