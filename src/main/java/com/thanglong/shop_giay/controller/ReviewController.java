package com.thanglong.shop_giay.controller;

import java.util.List;
import java.util.Map;

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

import com.thanglong.shop_giay.dto.ReviewDTO;
import com.thanglong.shop_giay.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
        @Autowired
        ReviewService reviewService;
        //1. Lấy tất cả đánh giá
        @GetMapping
        public ResponseEntity<?> getAllReviews(){
            List<ReviewDTO> list = reviewService.getAllReviews();
            return ResponseEntity.ok(list);
        }

        // 2. Lấy đánh giá theo Product ID
        @GetMapping("/products/{id}")
        public ResponseEntity<?> getReviewsByProduct(@PathVariable Integer id){
            try {
                List<ReviewDTO> list = reviewService.getReviewsByProduct(id);
                return ResponseEntity.ok(list);
            } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
            }
        }

        // 3. Tạo đánh giá mới
        @PostMapping
        public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDTO dto){
            try {
                ReviewDTO reviewDTO = reviewService.createReview(dto);
                return ResponseEntity.ok(reviewDTO);
            } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
            }
        }

        // 4. Update đánh giá (Chỉ cho sửa nội dung và số sao)
        @PutMapping("/{id}")
        public ResponseEntity<?> updateReview(@PathVariable Integer id,@RequestBody ReviewDTO dto){
            try {
                ReviewDTO reviewDTO = reviewService.updateReview(id,dto);
                return ResponseEntity.ok(reviewDTO);
            } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
            }
        }
        // 5. Xóa đánh giá
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteReview(@PathVariable Integer id){
            try {
                reviewService.deleteReview(id);
                return ResponseEntity.ok("Xoá thành công review id " + id);
            } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
            }
        }
}
