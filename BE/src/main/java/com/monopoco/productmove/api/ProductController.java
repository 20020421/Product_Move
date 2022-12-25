package com.monopoco.productmove.api;

import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.requestentity.ProductRequestForm;
import com.monopoco.productmove.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;


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
