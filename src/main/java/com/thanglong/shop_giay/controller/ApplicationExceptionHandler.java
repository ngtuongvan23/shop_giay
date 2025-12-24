package com.thanglong.shop_giay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
// hiện thị output lỗi đẹp hơn. 
@RestControllerAdvice // 1. Đánh dấu đây là nơi chuyên bắt lỗi của toàn bộ ứng dụng
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 2. Trả về mã lỗi 400
    @ExceptionHandler(MethodArgumentNotValidException.class) // 3. Chỉ bắt lỗi liên quan đến @Valid
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // 4. Lấy danh sách lỗi từ Exception và nhét vào Map cho gọn
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // Tên trường: ví dụ "price"
            String errorMessage = error.getDefaultMessage();    // Lời nhắn: ví dụ "Giá phải dương"
            errors.put(fieldName, errorMessage);
        });
        
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonError(HttpMessageNotReadableException ex) {
        
        // Trả về lỗi 400 kèm thông báo rõ ràng
        Map<String, String> error = Collections.singletonMap("error", "Dữ liệu gửi lên không đúng định dạng hoặc bị rỗng (Body is missing or invalid).");
        
        return ResponseEntity.badRequest().body(error);
    }
}