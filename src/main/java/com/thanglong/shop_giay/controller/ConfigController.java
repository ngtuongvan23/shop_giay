package com.thanglong.shop_giay.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanglong.shop_giay.utils.ShopConfig;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    // Khách hàng xem phí ship
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getShopInfo() {
        // Gọi getInstance() để lấy cấu hình chung
        ShopConfig config = ShopConfig.getInstance();
        
        Map<String, Object> response = new LinkedHashMap<>(); // Dùng LinkedHashMap để giữ thứ tự dòng
        response.put("message", "Chào mừng đến " + config.getShopName());
        response.put("shippingFee", config.getDefaultShippingFee());
        response.put("vatRate", config.getVatRate());
        response.put("isMaintenance", config.isMaintenanceMode() ? "Bảo trì " : "Hoạt động" );
        
        return ResponseEntity.ok(response);
    }

    // Admin đổi phí ship 
    @PostMapping("/update-vat/{vatNew}")
    public ResponseEntity<?> updateShipFee(@PathVariable float vatNew) {
        ShopConfig config = ShopConfig.getInstance();

        // Thay đổi giá trị trên đối tượng Singleton
        if (vatNew < 0 || vatNew > 1) {
            return ResponseEntity.badRequest().body("VAT phải từ 0 đến 1 (ví dụ 0.1 là 10%)");
        }
        config.setVatRate(vatNew);
        
        return ResponseEntity.ok().body("Đã cập nhật VAT toàn hệ thống thành: " + vatNew);
    }
}