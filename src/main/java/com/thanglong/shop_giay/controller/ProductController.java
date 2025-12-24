package com.thanglong.shop_giay.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanglong.shop_giay.dto.ProductDTO;
import com.thanglong.shop_giay.entity.Product;
import com.thanglong.shop_giay.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService product_service;
    // 1. Create 
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO dto){
        try {
            Product product = product_service.createProduct(dto);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //2. Read
    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        List<Product> listProduct = product_service.getAllProducts();
        return ResponseEntity.ok(listProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id){
        try {
            Product product = product_service.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //3. Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id,@RequestBody ProductDTO dto){
        try {
            Product product = product_service.updateById(id, dto);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //4. Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        try {
            String message = product_service.deleteById(id);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
