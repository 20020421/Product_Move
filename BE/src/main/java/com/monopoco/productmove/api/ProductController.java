package com.monopoco.productmove.api;

import com.monopoco.productmove.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/colors")
    public ResponseEntity<Map<String, String>> getColorByName(@RequestParam(name = "color") String colorName) {

        Map<String, String> color = productService.getColorByName(colorName);
        if (color != null) {
            return ResponseEntity.status(HttpStatus.OK).body(color);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/statistical/model")
    public ResponseEntity<Map<String, Integer>> getStatisticalByModel() {
        return ResponseEntity.ok(productService.statisticalProduct());
    }

//    @PostMapping("")
//    public ResponseEntity<?> addNewProduct(@ModelAttribute ProductRequestForm productRequestForm) {
//        ProductDTO productDTO = modelMapper.map(productRequestForm, ProductDTO.class);
//        ProductDTO productDTOSaved = productService.save(productDTO);
//        if (productDTOSaved != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(productDTOSaved);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add Product faild");
//        }
//
//    }

}
