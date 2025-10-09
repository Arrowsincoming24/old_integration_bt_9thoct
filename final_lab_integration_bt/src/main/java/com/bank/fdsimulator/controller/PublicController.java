package com.bank.fdsimulator.controller;

import com.bank.fdsimulator.entity.FdProduct;
import com.bank.fdsimulator.service.FdProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {
    
    @Autowired
    private FdProductService productService;
    
    @GetMapping("/products/active")
    public ResponseEntity<List<FdProduct>> getActiveProducts() {
        List<FdProduct> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<FdProduct> getProductById(@PathVariable Long id) {
        FdProduct product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
