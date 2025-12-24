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

import com.thanglong.shop_giay.dto.CategoryDTO;
import com.thanglong.shop_giay.entity.Category;
import com.thanglong.shop_giay.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    CategoryService categorySerive;

    //1.Create
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO dto){
        Category category = categorySerive.createCategory(dto);
        return ResponseEntity.ok(category);
    }

    //2.Read
    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        List<Category> list = categorySerive.getAllCategories();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id){
        try {
            Category category = categorySerive.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    //3. Update 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody CategoryDTO dto){
        try {
            Category category = categorySerive.updateById(id,dto);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //4. Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        try {
            String message = categorySerive.deleteById(id);
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
