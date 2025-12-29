package com.thanglong.shop_giay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.thanglong.shop_giay.dto.BrandDTO;
import com.thanglong.shop_giay.entity.Brand;
import com.thanglong.shop_giay.service.BrandService;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    BrandService brandService;

    //1.Create
    @PostMapping
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandDTO dto){
        Brand brand = brandService.createBrand(dto);
        return ResponseEntity.ok(brand);
    }

    //2.Read
    @GetMapping
    public ResponseEntity<?> getAllBrands(){
        List<Brand> list = brandService.getAllBrands();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Integer id){
        try {
            Brand brand = brandService.getBrandById(id);
            return ResponseEntity.ok(brand);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    //3. Update 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody BrandDTO dto){
        try {
            Brand brand = brandService.updateById(id,dto);
            return ResponseEntity.ok(brand);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //4. Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        try {
            String message = brandService.deleteById(id);
            return ResponseEntity.ok(message);
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("Không xóa được vì có sản phẩm đang dùng.");
        }catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Lỗi không xác định.");
        }
    }
}
